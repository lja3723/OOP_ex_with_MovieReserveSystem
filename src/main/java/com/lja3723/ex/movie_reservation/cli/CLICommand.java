package com.lja3723.ex.movie_reservation.cli;

import org.apache.commons.cli.*;
import java.util.*;

abstract class CLICommand {
    private final static List<String> emptyParameter = new ArrayList<>();
    private List<String> parameters;
    private final Options options;

    public CLICommand(List<String> parameters) {
        setParameters(parameters);
        this.options = new Options();
        options.addOption(Option.builder("help")
                .desc("명령어 사용법을 출력합니다.")
                .build());
        initOptions(options);
    }

    public CLICommand setParameters(List<String> parameters) {
        this.parameters = (null == parameters) ? emptyParameter : parameters;
        return this;
    }

    public void execute(CLIController controller) {
        if (parameters.size() == 1 && parameters.get(0).equals("-help")) {
            printHelp();
        }
        else {
            try {
                execute(controller, new DefaultParser().parse(options, parameters.toArray(new String[0])));
            } catch (ParseException e) {
                System.out.println("잘못된 파라미터입니다. (원인: " + e.getMessage() + ")");
            }
        }
    }

    public void printHelp() {
        System.out.println(description());
        new HelpFormatter().printHelp(getCmdLineSyntax(), options);
    }

    protected String getCmdLineSyntax() {
        return commandName() + " [옵션]";
    }
    abstract public String commandName();
    abstract protected void initOptions(Options options);
    abstract protected void execute(CLIController controller, CommandLine command);
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
    public String commandName() {
        return "none";
    }

    @Override
    protected void initOptions(Options options) {  }

    @Override
    public void execute(CLIController controller, CommandLine command) {
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
    public String commandName() {
        return "intro";
    }

    @Override
    protected void initOptions(Options options) { }

    @Override
    public void execute(CLIController controller, CommandLine command) {
        System.out.println(controller.getVersion() + "이 실행되었습니다.");
        System.out.println("명령어 목록을 보시려면 help를 입력하십시오.");
    }

    @Override
    public String description() { return ""; }
}

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

final class VersionCLICommand extends CLICommand {

    public VersionCLICommand(List<String> parameters) {
        super(parameters);
    }

    @Override
    public String commandName() {
        return "version";
    }

    @Override
    protected void initOptions(Options options) { }

    @Override
    public void execute(CLIController controller, CommandLine command) {
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
    public String commandName() {
        return "movie";
    }

    @Override
    protected void initOptions(Options options) {
        options.addOption(Option.builder("ls")
                .longOpt("list")
                .hasArg(false)
                .desc("영화 목록을 출력합니다.")
                .build());
        options.addOption(Option.builder("info")
                .longOpt("information")
                .hasArg()
                .argName("movie name")
                .desc("영화 이름에 대한 영화 정보를 출력합니다.")
                .build());
    }

    @Override
    public void execute(CLIController controller, CommandLine command) {
        if (command.hasOption("ls")) {
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
                System.out.println("다음의 이름을 가진 영화를 찾을 수 없습니다: " + movieName);
            }
        }
        else {
            printHelp();
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
    public String commandName() {
        return "screening";
    }

    @Override
    protected void initOptions(Options options) { }

    @Override
    public void execute(CLIController controller, CommandLine command) {
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
    public String commandName() {
        return "cmdtest";
    }

    @Override
    protected void initOptions(Options options) { }

    @Override
    public void execute(CLIController controller, CommandLine command) {

    }

    @Override
    public String description() {
        return "org.apache.commons.cli를 테스트합니다.";
    }
}