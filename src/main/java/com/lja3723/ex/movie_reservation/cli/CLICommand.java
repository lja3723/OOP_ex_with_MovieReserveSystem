package com.lja3723.ex.movie_reservation.cli;

import org.apache.commons.cli.*;
import java.util.*;

abstract class CLICommand {
    private final static List<String> emptyParameter = new ArrayList<>();
    private List<String> parameters;

    public CLICommand(List<String> parameters) {
        setParameters(parameters);
    }

    public CLICommand setParameters(List<String> parameters) {
        this.parameters = (null == parameters) ? emptyParameter : parameters;
        return this;
    }

    public void execute(CLIController controller) {
        execute(controller, parameters);
    }
    abstract protected void execute(CLIController controller, List<String> parameters);

    abstract public String description();
}

final class CLICommandFactory {
    private CLICommandFactory() {}

    public static CLICommand getCommand(String clientMessage) {
        CLICommandEnum commandName;
        String parameter;

        if (clientMessage.contains(" ")) {
            commandName = CLICommandEnum.getEnum(clientMessage.substring(0, clientMessage.indexOf(" ")));
            parameter = clientMessage.substring(clientMessage.indexOf(" "));
        }
        else {
            commandName = CLICommandEnum.getEnum(clientMessage);
            parameter = "";
        }

        return CLICommandEnum.getCommand(commandName, parseParameter(parameter));
    }


    private static List<String> parseParameter(String parameter) {
    /*
        parseParameter(arg1 -arg2 "arg3 -arg4" --arg5 "arg6 arg7" arg8 -arg9])
            return -> [arg1, -arg2, arg3 -arg4, --arg5, arg6 arg7, arg8, -arg9]
        parseParameter(a1 "" a2 "a3" a4)
            return -> [a1, a2, a3, a4]
        parseParameter(a1 a2 "a3 a4" a5 "a6 a7 a8" a9)
            return -> [a1, a2, a3 a4, a5, a6 a7 a8, a9]
        parseParameter(a1 a2 "a3 a4 \"a5 a6\" a7" a8 a9)
            return -> [a1, a2, a3 a4 "a5 a6" a7, a8, a9]
        parseParameter(a1 a2"a3\"\"\" \"a4""a5""\"a6\"a7"a8 a9)
            return -> [a1, a2, a3""" "a4, a5, "a6"a7, a8, a9]
     */
        List<String> translated = new ArrayList<>();
        if (parameter.length() > 0) {
            //큰따옴표 기준으로 문자열을 나눔
            translated = List.of(parameter.split("\""));
            //escape 된 큰따옴표(\")를 복구
            translated = repairQuote(translated);
            //큰따옴표 내부에 있지 않은 문자열을 공백을 기준으로 나눔
            translated = splitUnquotedString(translated);
            //빈 문자열 제거
            for (int i = translated.size() - 1; i >= 0; i--) {
                if (translated.get(i).length() == 0)
                    translated.remove(i);
            }
        }

        return translated;
    }

    private static List<String> repairQuote(List<String> splitWithQuote) {
    /*
        escaping 된 큰따옴표 문자(\")를 다시 복구
        ex)
        (a1 a2 "a3 a4 \"a5 a6\" a7" a8 a9).split(") -> [ a1 a2 , a3 a4 \, a5 a6\,  a7,  a8 a9]
        repairQuote([ a1 a2 , a3 a4 \, a5 a6\,  a7,  a8 a9]) -> [ a1 a2 , a3 a4 "a5 a6" a7,  a8 a9]
     */
        List<String> result = new ArrayList<>(splitWithQuote);

        //1. \가 있는 문자들을 하나로 뭉친다.
        for (int i = result.size() - 1; i >= 0; i--) {
            if (result.get(i).contains("\\") && i != result.size() - 1) {
                result.set(i, result.get(i) + result.get(i + 1));
                result.remove(i + 1);
            }
        }
        //2. \를 "로 치환한다.
        result.replaceAll(s -> s.replace('\\', '\"'));

        return result;
    }

    private static List<String> splitUnquotedString(List<String> translated) {
    /*
        큰따옴표로 감싸지지 않았던 문자열을 공백으로 분리
        splitUnquotedString([ a1 a2 , a3 a4 "a5 a6" a7,  a8 a9]) -> [ a1, a2, a3 a4 "a5 a6" a7, a8, a9]
     */
        List<String> result = new ArrayList<>();
        for (int i = 0; i < translated.size(); i++) {
            //짝수번째 요소가 큰따옴표로 둘러쌓인 문자열임
            if (i % 2 == 0) {
                result.addAll(List.of(translated.get(i).trim().split(" ")));
            }
            else {
                result.add(translated.get(i));
            }
        }

        return result;
    }
}

//특수 명령
final class NoneCLICommand extends CLICommand {
    public NoneCLICommand(List<String> parameters) { super(parameters); }
    @Override
    public void execute(CLIController controller, List<String> parameters) {
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
    public void execute(CLIController controller, List<String> parameters) {
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
    public void execute(CLIController controller, List<String> parameters) {
        final Map<String, String> cmdList = new HashMap<>();

        for (CLICommandEnum cmd : CLICommandEnum.valuesExecutable())
            cmdList.put(cmd.name(), CLICommandEnum.getCommand(cmd).description());

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
    public void execute(CLIController controller, List<String> parameters) {
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
    public void execute(CLIController controller, List<String> parameters) {
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
    public void execute(CLIController controller, List<String> parameters) {
        System.out.println("This is Movie Command");
        System.out.println("parameters: " + parameters);

        Options options = new Options();
        options.addOption(Option.builder("ls")
                .longOpt("list")
                .hasArg(false)
                .desc("영화 목록을 출력합니다.")
                .build());
        options.addOption(Option.builder("help")
                .hasArg(false)
                .desc("명령어 사용법을 출력합니다.")
                .build());
        options.addOption(Option.builder("info")
                .longOpt("information")
                .hasArg()
                .argName("movie name")
                        .desc("영화 이름에 대한 영화 정보를 출력합니다.")
                .build());

        if (parameters.isEmpty())
            new HelpFormatter() .printHelp("movie [option]", options);

        try {
            CommandLine command = new DefaultParser().parse(options, parameters.toArray(new String[0]));

            if (command.hasOption("help")) {
                new HelpFormatter() .printHelp("movie [option]", options);
            }
            else if (command.hasOption("ls")) {
                int index = 0;
                for (String movieName: controller.getMovieList()) {
                    System.out.println("Movie " + (++index) + ": " + movieName);
                }
            }
            else if (command.hasOption("info")) {
                String movieName = command.getOptionValue("info");
                try {
                    System.out.println(controller.getMovie(movieName));
                } catch (IllegalArgumentException e) {
                    System.out.println("다음의 이름을 가진 영화를 찾을 수 없습니다.");
                    System.out.println(movieName);
                }
            }

        } catch (ParseException e) {
            System.out.println("인수가 잘못되었습니다. 원인: " + e.getMessage());
        }
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
    public void execute(CLIController controller, List<String> parameters) {
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
    public void execute(CLIController controller, List<String> parameters) {

    }

    @Override
    public String description() {
        return "org.apache.commons.cli를 테스트합니다.";
    }
}