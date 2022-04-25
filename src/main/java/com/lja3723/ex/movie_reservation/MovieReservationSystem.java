package com.lja3723.ex.movie_reservation;

import com.lja3723.ex.movie_reservation.physical.*;
import com.lja3723.ex.movie_reservation.resource_reader.*;
import com.lja3723.ex.movie_reservation.value.Money;
import java.util.*;

public class MovieReservationSystem {
	private final MultiplexCinema cinema;
	private final List<Movie> movies;
	private final List<Screening> screenings;

	public MovieReservationSystem(String cinemaName, Money cinemaFinance) {
		TheatreJsonReader theatresReader =
				new TheatreJsonReader("src/main/resources/theatres.json");
		MovieJsonReader moviesReader =
				new MovieJsonReader("src/main/resources/movies.json",
						new MoviePricesJsonReader("src/main/resources/movie_prices.json"),
						new DiscountPoliciesJsonReader("src/main/resources/discount_policies.json"));
		ScreeningsJsonReader screeningsReader =
				new ScreeningsJsonReader("src/main/resources/screenings.json",
						moviesReader.getList(), theatresReader.getList());

		this.cinema = new MultiplexCinema(cinemaName, cinemaFinance, theatresReader.getList());
		this.movies = moviesReader.getList();
		this.screenings = screeningsReader.getList();

		System.out.println("MRS 생성자 실행 완료");
	}

	public Reservation createReservation(Customer customer, Screening screening, Seat seat) {
		return new Reservation(customer, screening, seat);
	}

}