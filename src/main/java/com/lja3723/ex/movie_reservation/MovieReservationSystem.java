package com.lja3723.ex.movie_reservation;

import com.lja3723.ex.movie_reservation.physical.*;
import com.lja3723.ex.movie_reservation.reservable.*;
import com.lja3723.ex.movie_reservation.resource_reader.*;
import com.lja3723.ex.movie_reservation.value.Money;

import java.io.FileNotFoundException;
import java.util.List;

public class MovieReservationSystem {
	private MultiplexCinema cinema;
	private MovieList movieList;
	private ScreeningList screeningList;

	public MovieReservationSystem(String cinemaName, Money cinemaFinance) {
		try {
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
			this.movieList = new MovieList(moviesReader.getList());
			this.screeningList = new ScreeningList(screeningsReader.getList());

			System.out.println("MRS 생성자 실행 완료");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public List<String> getMovieList() {
		return movieList.getMovieList();
	}

	public Reservation createReservation(Customer customer, Screening screening, Seat seat) {
		return new Reservation(customer, screening, seat);
	}

}