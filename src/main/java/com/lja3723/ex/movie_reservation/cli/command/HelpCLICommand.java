package com.lja3723.ex.movie_reservation.cli.command;


import com.lja3723.ex.movie_reservation.cli.CLIController;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void execute(CLIController controller, CommandLine command) {
        if (command.getArgList().isEmpty()) {
            final Map<String, String> cmdList = new HashMap<>();

            for (CLICommandEnum cmd : CLICommandEnum.valuesExecutable())
                cmdList.put(cmd.name(), CLICommandEnum.getCommand(cmd).description());

            int longestLength = 0;
            for (String cmd: cmdList.keySet())
                longestLength = Math.max(longestLength, cmd.length());

            for (String cmd: cmdList.keySet()) {
                System.out.printf("%-" + (longestLength + 3) + "s %s%n", cmd, cmdList.get(cmd));
            }
        }
        else {
            String arg = command.getArgs()[0];
            CLICommandEnum cmdEnum = CLICommandEnum.getEnum(arg);

            if (cmdEnum == CLICommandEnum.none) {
                System.out.println("\"" + arg + "\": " + "알 수 없는 명령어입니다.");
                return;
            }

            CLICommandEnum.getCommand(cmdEnum).printHelp();
        }
    }

    @Override
    public String description() {
        return "명령어 목록을 출력합니다.";
    }
}
