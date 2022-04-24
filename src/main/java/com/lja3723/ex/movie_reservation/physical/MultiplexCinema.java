package com.lja3723.ex.movie_reservation.physical;

import java.sql.Array;
import java.util.*;
import com.lja3723.ex.movie_reservation.value.Money;

public class MultiplexCinema {
    private final String name;
    private List<Theatre> theatres;
    private Money finance; //재정

    public MultiplexCinema(String name, Money finance, List<Theatre> theatres) {
        this.name = name;
        this.finance = finance;
        this.theatres = theatres;
    }

    public String getName() {
        return name;
    }

    public final List<Theatre> getTheatres() {
        return theatres;
    }
}
