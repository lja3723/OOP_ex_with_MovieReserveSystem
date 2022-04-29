package com.lja3723.ex.movie_reservation.reservable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ScreeningList {
    private final List<Screening> list;

    public static class ConditionSetter {
        private final List<Screening> list;
        private final List<String> movieNames = new ArrayList<>();
        private final List<Integer> theatreNumbers = new ArrayList<>();
        private LocalDate date, startDate, endDate;
        private LocalTime startTime, endTime;

        public ConditionSetter(List<Screening> list) {
            this.list = list;
        }

        public ConditionSetter movieName(String movieName) {
            this.movieNames.add(movieName);
            return this;
        }

        public ConditionSetter movieName(List<String> movieNames) {
            this.movieNames.addAll(movieNames);
            return this;
        }

        public ConditionSetter theatreNumber(int theatreNumber) {
            theatreNumbers.add(theatreNumber);
            return this;
        }

        public ConditionSetter theatreNumber(List<Integer> theatreNumbers) {
            this.theatreNumbers.addAll(theatreNumbers);
            return this;
        }

        public ConditionSetter date(LocalDate date) {
            this.date = date;
            return this;
        }

        public ConditionSetter period(LocalDate startDate, LocalDate endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
            return this;
        }

        public ConditionSetter duration(LocalTime startTime, LocalTime endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
            return this;
        }

        public List<Screening> find() {
            return list.stream().filter(this::isSatisfied).toList();
        }

        public boolean remove() {
            return list.removeIf(this::isSatisfied);
        }

        private boolean isSatisfied(Screening screening) {
            if (!movieNames.isEmpty()) {
                boolean flag = false;
                for (String movieName : movieNames) {
                    if (screening.isMovieNameEquals(movieName)) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) return false;
            }

            if (!theatreNumbers.isEmpty()) {
                boolean flag = false;
                for (int theatreNumber : theatreNumbers) {
                    if (screening.isTheatreNumberEquals(theatreNumber)) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) return false;
            }

            LocalDate screeningDate = screening.getScreenedDateTime().toLocalDate();
            if (date != null)
                if(!screeningDate.equals(date))
                    return false;

            if (startDate != null && endDate != null)
                if (!(startDate.isBefore(screeningDate) && endDate.isAfter(screeningDate)))
                    return false;

            LocalTime screeningTime = screening.getScreenedDateTime().toLocalTime();
            if (startTime != null && endTime != null)
                return startTime.isBefore(screeningTime) && endTime.isAfter(screeningTime);

            return true;
        }
    }

    public ScreeningList(List<Screening> list) {
        this.list = list;
    }

    public ConditionSetter conditionSetter() {
        return new ConditionSetter(this.list);
    }

    public void add(Screening screening) {
        if (list.contains(screening)) return;
        list.add(screening);
    }

    public void add(List<Screening> list) {
        this.list.addAll(list);
    }
}
