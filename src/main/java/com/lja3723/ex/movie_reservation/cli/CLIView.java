package com.lja3723.ex.movie_reservation.cli;

import com.lja3723.ex.movie_reservation.cli.command.CLICommandFactory;

import java.util.Scanner;

public class CLIView {
    private final CLIController controller;
    private boolean exit;

    public CLIView(CLIController controller) {
        this.controller = controller;
        this.controller.setView(this);
        this.exit = false;

        System.out.println(controller.getVersion() + "이 실행되었습니다.");
        System.out.println("명령어 목록을 보시려면 help를 입력하십시오.");
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
        System.out.print("\n> ");
        controller.setCommand(CLICommandFactory.getCommand(new Scanner(System.in).nextLine()));
    }
}
