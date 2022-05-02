package com.lja3723.ex.movie_reservation.cli;

import java.util.Scanner;

public class CLIView {
    private final CLIController controller;
    private boolean exit;

    public CLIView(CLIController controller) {
        this.controller = controller;
        this.controller.setView(this);
        this.controller.setCommand(new IntroCLICommand(null));
        this.exit = false;
        updateUI();
    }

    public boolean isExit() {
        return exit;
    }

    public void exit() {
        this.exit = true;
    }

    public void updateUI() {
        controller.executeCommand();
    }

    public void getClientMessage() {
        System.out.print("> ");
        controller.setCommand(CLICommandFactory.getCommand(new Scanner(System.in).nextLine()));
    }
}
