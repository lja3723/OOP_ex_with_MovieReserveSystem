package com.lja3723.ex.movie_reservation.cli;

import org.apache.commons.cli.*;

import java.util.*;

abstract class CLICommand {
    private final static List<String> emptyParameter = new ArrayList<>();
    protected List<String> parameters;

    public CLICommand(List<String> parameters) {
        setParameters(parameters);
    }

    public CLICommand setParameters(List<String> parameters) {
        this.parameters = (null == parameters) ? emptyParameter : parameters;
        return this;
    }

    abstract public void execute(CLIController controller);

    abstract public String description();
}

final class CLICommandFactory {
    private CLICommandFactory() {}

    public static CLICommand getCommand(String clientMessage) {
        List<String> msgList = Arrays.asList(clientMessage.split(" "));
        CLICommandEnum commandName = CLICommandEnum.getEnum(msgList.get(0));
        List<String> parameters = translateParameters(msgList.subList(1, msgList.size()));
        return CLICommandEnum.getCommand(commandName, parameters);
    }

    public static CLICommand getCommand(CLICommandEnum commandName) {
        return CLICommandEnum.getCommand(commandName);
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
    public String description() {
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
    public String description() {
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

        for (CLICommandEnum cmd : CLICommandEnum.valuesExecutable())
            cmdList.put(cmd.name(), CLICommandFactory.getCommand(cmd).description());

        int longestLength = 0;
        for (String command: cmdList.keySet())
            longestLength = Math.max(longestLength, command.length());

        for (String command: cmdList.keySet()) {
            System.out.printf("%-" + (longestLength + 3) + "s %s%n", command, cmdList.get(command));
        }
    }

    @Override
    public String description() {
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
    public String description() {
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
    public String description() {
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
    }

    @Override
    public String description() {
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
    public String description() {
        return "상영 정보와 관련된 명령을 수행합니다.";
    }
}

//apache commons cli test 명령
final class CmdTestCLICommand extends CLICommand {

    public CmdTestCLICommand(List<String> parameters) {
        super(parameters);
    }

    @Override
    public void execute(CLIController controller) {
        List<String> params = List.of("-ls", "arg1", "arg2 arg3", "--value", "arg4 arg5", "arg6");
        List<String> lineParam = List.of("-ls arg1 \"arg2 arg3\" --value \"arg4 arg5\" arg6");

        System.out.println("parameters: " + params);
        Options options = new Options()
                .addOption("ls", "list", false, "리스트를 출력합니다.")
                .addOption("val", "value", true, "옵션 값이 있는 옵션 테스트")
                .addOption("params", false, "주어진 파라미터를 출력합니다.");
        CommandLineParser parser = new DefaultParser();

        if (params.isEmpty()) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("movie", options);
        }

        try {
            CommandLine command = parser.parse(options, params.toArray(new String[0]));

            System.out.print("\noption list: ");
            for (var option : command.getOptions()) {
                System.out.print(option.getOpt() + "; ");
            }
            System.out.println();

            System.out.println("[option values]");
            for (var option : command.getOptions()) {
                System.out.println("option name: " + option.getOpt());
                if (option.getValues() == null) {
                    System.out.println("value: null");
                    continue;
                }
                System.out.println("values length: " + option.getValues().length);
                for (String value : option.getValues()) {
                    System.out.println("value: " + value);
                }
            }

            System.out.print("\nargs: ");
            for (var option: command.getArgs()) {
                System.out.print(option + "; ");
            }

        } catch (ParseException e) {
            System.err.println("Parsing failed.  Reason: " + e.getMessage());
        }
    }

    @Override
    public String description() {
        return "org.apache.commons.cli를 테스트합니다.";
    }
}