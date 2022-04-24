package com.lja3723.ex.movie_reservation.physical;

import java.util.*;
import com.lja3723.ex.movie_reservation.value.Money;

public class MultiplexCinema {
    private final String name;
    private final List<Theatre> theatres;
    private Money finance; //재정

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

    public void getMoney(Money getAmount) {
        finance = finance.plus(getAmount);
    }

    public boolean spentMoney(Money spendAmount) {
        if (finance.isLessThen(spendAmount)) {
            return false;
        }
        finance = finance.minus(spendAmount);
        return true;
    }
}
