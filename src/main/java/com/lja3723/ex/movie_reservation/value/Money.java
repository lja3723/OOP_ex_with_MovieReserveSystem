package com.lja3723.ex.movie_reservation.value;

import java.math.BigDecimal;

public class Money {
	public static final Money ZERO = Money.wons(0);

	private final BigDecimal amount;

	public static Money wons(long amount) {
		return new Money(BigDecimal.valueOf(amount));
	}
	
	public static Money wons(double amount) {
		return new Money(BigDecimal.valueOf(amount));
	}

	Money(BigDecimal amount) {
		this.amount = amount;
	}

	public Money plus(Money amount) {
		return new Money(this.amount.add(amount.amount));
	}

	public Money minus(Money amount) {
		return new Money(this.amount.subtract(amount.amount));
	}

	public Money times(Percentage percent) {
		return new Money(this.amount.multiply(BigDecimal.valueOf(percent.value())));
	}
	
	public Money times(int value) {
		return new Money(this.amount.multiply(BigDecimal.valueOf(value)));
	}

	public boolean isLessThen(Money other) {
		return amount.compareTo(other.amount) < 0;
	}

	public boolean isGreaterThanOrEqual(Money other) {
		return !isLessThen(other);
	}

	public boolean equals(Money money) {
		return this.amount == money.amount;
	}
	
	@Override
	public String toString() {
		return amount.toString();
	}
}