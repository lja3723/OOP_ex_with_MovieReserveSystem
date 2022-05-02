package com.lja3723.ex.movie_reservation.cli;

import java.util.*;

abstract class CLICommand {
    protected List<String> parameters;
    public CLICommand(List<String> parameters) {
        this.parameters = parameters;
    }
    abstract public void execute(CLIController controller);
}

final class CLICommandFactory {
    public static CLICommand getCommand(String clientMessage) {
        List<String> msgList = Arrays.asList(clientMessage.split(" "));

        String commandName = msgList.get(0);
        List<String> parameters = translateParameters(msgList.subList(1, msgList.size()));

        return CLICommandEnum.getCommand(commandName, parameters);
    }

    private static List<String> translateParameters(List<String> parameters) {
        return parameters;
    }
}

final class NoneCLICommand extends CLICommand {
    public NoneCLICommand(List<String> parameters) { super(parameters); }
    @Override
    public void execute(CLIController controller) {
        System.out.println("알 수 없는 명령어입니다. 명령어 목록을 보시려면 help를 입력하십시오.");
    }
}

final class IntroCLICommand extends CLICommand {
    public IntroCLICommand(List<String> parameters) { super(parameters); }

    @Override
    public void execute(CLIController controller) {
        System.out.println(controller.getVersion() + "이 실행되었습니다.");
        System.out.println("명령어 목록을 보시려면 help를 입력하십시오.");
    }
}

final class HelpCLICommand extends CLICommand {
    private final Map<String, String> cmdList = new HashMap<>();
    public HelpCLICommand(List<String> parameters) {
        super(parameters);
        for (CLICommandEnum cmd : CLICommandEnum.values())
            cmdList.put(cmd.name(), CLICommandEnum.getUsage(cmd));
    }

    @Override
    public void execute(CLIController controller) {
        int longestLength = 0;
        for (String command: cmdList.keySet())
            longestLength = Math.max(longestLength, command.length());

        for (String command: cmdList.keySet()) {
            if (command.equals("none")) continue;
            System.out.printf("%-" + (longestLength + 3) + "s %s%n", command, cmdList.get(command));
        }
    }
}

final class ExitCLICommand extends CLICommand {
    public ExitCLICommand(List<String> parameters) { super(parameters); }
    @Override
    public void execute(CLIController controller) {
        System.out.println(controller.getVersion() + "을 종료합니다.");
        controller.exitProgram();
    }
}

final class VersionCLICommand extends CLICommand {

    public VersionCLICommand(List<String> parameters) {
        super(parameters);
    }

    @Override
    public void execute(CLIController controller) {
        System.out.println(controller.getVersion());
    }
}

final class MovieCLICommand extends CLICommand {

    public MovieCLICommand(List<String> parameters) {
        super(parameters);
    }

    @Override
    public void execute(CLIController controller) {
        System.out.println("This is Movie Command");
    }
}

final class ScreeningCLICommand extends CLICommand {

    public ScreeningCLICommand(List<String> parameters) {
        super(parameters);
    }

    @Override
    public void execute(CLIController controller) {
        System.out.println("This is Screening Command");
    }
}