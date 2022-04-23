package com.lja3723.ex.movie_reservation.physical;

import java.sql.Array;
import java.util.*;
import com.lja3723.ex.movie_reservation.value.Money;

public class MultiplexCinema {
    private final String name;
    private List<Theatre> theatres;
    private Money finance; //재정

    private boolean isInitTheatres = false;

    public MultiplexCinema(String name, Money finance) {
        this.name = name;
        this.finance = finance;
    }

    public void initTheatres(List<Theatre> theatres) {
        if (isInitTheatres) { return; }
        this.theatres = theatres;
        isInitTheatres = true;
    }

    public String getName() {
        return name;
    }

    public final List<Theatre> getTheatres() {
        return theatres;
    }
}
