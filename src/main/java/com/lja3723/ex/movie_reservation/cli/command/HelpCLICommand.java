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
        return commandName() + " [명령어]";
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

            int longest = getLongestStringLengthOf(cmdList.keySet());

            for (String cmd: cmdList.keySet()) {
                System.out.printf("%-" + (longest + 3) + "s %s%n", cmd, cmdList.get(cmd));
            }
        }
        else {
            String arg = commandLine.getArgs()[0];
            if (CLICommandEnum.getEnum(arg) == CLICommandEnum.none) {
                System.out.println(NoneCLICommand.unknownCommand(arg));
            }
            else {
                CLICommandEnum.getCommand(arg).printHelp();
            }
        }
    }

    @Override
    public String description() {
        return "명령어 목록을 출력합니다.";
    }
}
