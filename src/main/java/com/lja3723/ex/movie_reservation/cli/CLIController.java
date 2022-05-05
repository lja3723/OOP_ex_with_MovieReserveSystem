package com.lja3723.ex.movie_reservation.cli;

import com.lja3723.ex.movie_reservation.MovieReservationSystem;
import com.lja3723.ex.movie_reservation.cli.command.*;
import com.lja3723.ex.movie_reservation.reservable.Movie;

import java.util.List;
import java.util.Objects;

public class CLIController {
    private final MovieReservationSystem MRS;
    private final CLICommand noCommand = CLICommandFactory.getCommand("none");
    private CLICommand command;
    private CLIView view;
    public CLIController(MovieReservationSystem MRS) {
        this.MRS = MRS;
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
    public List<String> getMovieList() {
        return MRS.getMovieList().getMovieList();
    }

    public Movie getMovie(String movieName) {
        return MRS.getMovieList().getMovie(movieName);
    }


    //**************************************/
    //*** ScreeningController Interface ****/
    //**************************************/

}
