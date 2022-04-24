package com.lja3723.ex.movie_reservation.physical;

import java.util.*;
import com.lja3723.ex.movie_reservation.value.Money;

public class MultiplexCinema {
    private final String name;
    private Money finance; //재정
    private final List<Theatre> theatres;

    public MultiplexCinema(String name, Money finance, List<Theatre> theatres) {
        this.name = name;
        this.finance = finance;
        this.theatres = theatres;
    }

    public String getName() {
        return name;
    }

    public Money getFinance() {
        return finance;
    }

    public final List<Theatre> getTheatres() {
        return theatres;
    }
}
