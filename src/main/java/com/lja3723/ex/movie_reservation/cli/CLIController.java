package com.lja3723.ex.movie_reservation.cli;

import com.lja3723.ex.movie_reservation.*;
import com.lja3723.ex.movie_reservation.cli.command.*;
import com.lja3723.ex.movie_reservation.reservable.Movie;
import com.lja3723.ex.movie_reservation.reservable.Screening;

import java.util.List;
import java.util.Objects;

public class CLIController {
    private final MovieReservationSystem MRS;
    private final CLICommand noCommand = CLICommandFactory.getCommand("none");
    private CLICommand command;
    private CLIView view;
    private final MovieList movieList;
    private final ScreeningList screeningList;
    public CLIController(MovieReservationSystem MRS) {
        this.MRS = MRS;
        this.movieList = MRS.getMovieList();
        this.screeningList = MRS.getScreeningList();
        this.command = noCommand;
    }


    //**************************************/
    //********** Basic Operations **********/
    //**************************************/
    public void setView(CLIView view) {
        this.view = view;
    }

    public void setCommand(CLICommand command) {
        this.command = Objects.requireNonNullElse(command, noCommand);
    }

    public void executeCommand() {
        command.execute(this);
    }

    public String getVersion() {
        return MRS.version();
    }

    public void exitProgram() {
        view.exit();
    }


    //**************************************/
    //***** MovieController Interface ******/
    //**************************************/
    public List<String> getMovieList() { return movieList.getMovieList(); }

    public Movie getMovie(String movieName) throws IllegalArgumentException {
        return movieList.getMovie(movieName);
    }


    //**************************************/
    //*** ScreeningController Interface ****/
    //**************************************/
    public List<Screening> getScreeningList() { return screeningList.conditionSetter().find(); }

}
