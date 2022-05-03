package com.lja3723.ex.movie_reservation.cli;

import java.util.*;

abstract class CLICommand {
    private final static List<String> emptyParam = new ArrayList<>();
    protected List<String> parameters;

    public CLICommand(List<String> parameters) {
        setParameters(parameters);
    }

    public CLICommand setParameters(List<String> parameters) {
        if (parameters == null)
            parameters = emptyParam;
        this.parameters = parameters;
        return this;
    }

    abstract public void execute(CLIController controller);

    abstract public String getUsage();
}

final class CLICommandFactory {
    private CLICommandFactory() {}

    public static CLICommand getCommand(String clientMessage) {
        List<String> msgList = Arrays.asList(clientMessage.split(" "));
        CLICommandEnum commandName = CLICommandEnum.getEnum(msgList.get(0));
        List<String> parameters = translateParameters(msgList.subList(1, msgList.size()));
        return CLICommandEnum.getCommand(commandName, parameters);
    }

    private static List<String> translateParameters(List<String> parameters) {
        return parameters;
    }
}

//특수 명령
final class NoneCLICommand extends CLICommand {
    public NoneCLICommand(List<String> parameters) { super(parameters); }
    @Override
    public void execute(CLIController controller) {
        System.out.println("알 수 없는 명령어입니다. 명령어 목록을 보시려면 help를 입력하십시오.");
    }

    @Override
    public String getUsage() {
        return "";
    }
}

//특수 명령
final class IntroCLICommand extends CLICommand {
    public IntroCLICommand(List<String> parameters) { super(parameters); }

    @Override
    public void execute(CLIController controller) {
        System.out.println(controller.getVersion() + "이 실행되었습니다.");
        System.out.println("명령어 목록을 보시려면 help를 입력하십시오.");
    }

    @Override
    public String getUsage() {
        return "";
    }
}

final class HelpCLICommand extends CLICommand {
    public HelpCLICommand(List<String> parameters) {
        super(parameters);
    }

    @Override
    public void execute(CLIController controller) {
        final Map<String, String> cmdList = new HashMap<>();

        for (CLICommandEnum cmd : CLICommandEnum.executableValues())
            cmdList.put(cmd.name(), CLICommandEnum.getCommand(cmd).getUsage());

        int longestLength = 0;
        for (String command: cmdList.keySet())
            longestLength = Math.max(longestLength, command.length());

        for (String command: cmdList.keySet()) {
            System.out.printf("%-" + (longestLength + 3) + "s %s%n", command, cmdList.get(command));
        }
    }

    @Override
    public String getUsage() {
        return "명령어 목록을 출력합니다.";
    }
}

final class ExitCLICommand extends CLICommand {
    public ExitCLICommand(List<String> parameters) { super(parameters); }
    @Override
    public void execute(CLIController controller) {
        System.out.println(controller.getVersion() + "을 종료합니다.");
        controller.exitProgram();
    }

    @Override
    public String getUsage() {
        return "프로그램을 종료합니다.";
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

    @Override
    public String getUsage() {
        return "프로그램 버전을 출력합니다.";
    }
}

final class MovieCLICommand extends CLICommand {

    public MovieCLICommand(List<String> parameters) {
        super(parameters);
    }

    @Override
    public void execute(CLIController controller) {
        System.out.println("This is Movie Command");
        System.out.println(Math.random());
    }

    @Override
    public String getUsage() {
        return "영화와 관련된 명령을 수행합니다.";
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

    @Override
    public String getUsage() {
        return "상영 정보와 관련된 명령을 수행합니다.";
    }
}