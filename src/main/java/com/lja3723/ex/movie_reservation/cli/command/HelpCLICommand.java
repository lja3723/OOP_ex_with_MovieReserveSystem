package com.lja3723.ex.movie_reservation.cli.command;

import com.lja3723.ex.movie_reservation.cli.CLIController;
import org.apache.commons.cli.*;
import java.util.*;

final class HelpCLICommand extends CLICommand {
    public HelpCLICommand(List<String> parameters) {
        super(parameters);
    }

    @Override
    protected String getCmdLineSyntax() {
        return super.getCmdLineSyntax() + " [명령어]";
    }

    @Override
    public String commandName() {
        return "help";
    }

    @Override
    protected void initOptions(Options options) { }

    @Override
    public void execute(CLIController controller, CommandLine commandLine) {
        if (commandLine.getArgList().isEmpty()) {
            final Map<String, String> cmdList = new HashMap<>();

            for (CLICommandEnum cmd : CLICommandEnum.valuesExecutable())
                cmdList.put(cmd.name(), CLICommandEnum.getCommand(cmd.name()).description());

            int longestLength = 0;
            for (String cmd: cmdList.keySet())
                longestLength = Math.max(longestLength, cmd.length());

            for (String cmd: cmdList.keySet()) {
                System.out.printf("%-" + (longestLength + 3) + "s %s%n", cmd, cmdList.get(cmd));
            }
        }
        else {
            String arg = commandLine.getArgs()[0];
            CLICommand command = CLICommandEnum.getCommand(arg);
            if (command instanceof NoneCLICommand noneCommand) {
                System.out.println(noneCommand.unknownCommand(arg));
            }
            else {
                command.printHelp();
            }
        }
    }

    @Override
    public String description() {
        return "명령어 목록을 출력합니다.";
    }
}
