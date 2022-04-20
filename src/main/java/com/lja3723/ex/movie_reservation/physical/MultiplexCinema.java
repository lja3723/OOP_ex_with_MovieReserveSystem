package com.lja3723.ex.movie_reservation.physical;

import java.sql.Array;
import java.util.*;
import com.lja3723.ex.movie_reservation.value.Money;

public class MultiplexCinema {
    private String name;
    private List<Theatre> theatres = new ArrayList<Theatre>();
    private Money finance; //재정

    public MultiplexCinema(String name, Money finance) {
        this.name = name;
        this.finance = finance;
    }
}
