package com.lja3723.ex.movie_reservation.cli;

import com.lja3723.ex.movie_reservation.MovieReservationSystem;
import java.util.Objects;

public class CLIController {
    private final MovieReservationSystem MRS;
    private final CLICommand noCommand = new NoneCLICommand(null);
    private CLICommand command;
    private CLIView view;
    public CLIController(MovieReservationSystem MRS) {
        this.MRS = MRS;
        this.command = noCommand;
    }

    public void setView(CLIView view) {
        this.view = view;
    }

    public void setCommand(CLICommand command) {
        this.command = Objects.requireNonNullElseGet(command, () -> noCommand);
    }

    public void executeCommand() {
        command.execute(this);
    }

    public String getVersion() {
        return MRS.getVersion();
    }

    public void exitProgram() {
        view.exit();
    }
}
