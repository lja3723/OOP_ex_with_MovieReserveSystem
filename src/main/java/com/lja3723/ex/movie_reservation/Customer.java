package com.lja3723.ex.movie_reservation;

import java.util.*;

import com.lja3723.ex.movie_reservation.value.Money;
import com.lja3723.ex.movie_reservation.Reservation;

public class Customer {
	private String name;
	private Money ownMoney;
	private final int ReservationLimit = 8;
	private List<Reservation> reservations = new ArrayList<>();

	public Customer(String name, Money ownMoney) {
		this.name = name;
		this.ownMoney = ownMoney;
	}

	public String getName() {
		return name;
	}
	
	public Money getOwnMoney() {
		return ownMoney;
	}

	public void reserve(Reservation reservation) {
		reservations.add(reservation);
	}

	public boolean payMoney(Money amount) {
		if (ownMoney.isLessThen(amount)) {
			return false;	
		}
		else {
			ownMoney = ownMoney.minus(amount);
			return true;
		}
	}

	public void rechargeMoney(Money money) {
		ownMoney.plus(money);
	}
}