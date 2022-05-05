package com.lja3723.ex.movie_reservation.cli.command;


import com.lja3723.ex.movie_reservation.cli.CLIController;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

import java.util.List;

final class ExitCLICommand extends CLICommand {
    public ExitCLICommand(List<String> parameters) { super(parameters); }

    @Override
    public String commandName() {
        return "exit";
    }

    @Override
    protected void initOptions(Options options) { }

    @Override
    public void execute(CLIController controller, CommandLine command) {
        System.out.println(controller.getVersion() + "을 종료합니다.");
        controller.exitProgram();
    }

    @Override
    public String description() {
        return "프로그램을 종료합니다.";
    }
}

