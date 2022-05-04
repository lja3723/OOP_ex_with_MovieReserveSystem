package com.lja3723.ex.movie_reservation.cli;

import org.apache.commons.cli.*;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
        List<String> msgList = Arrays.asList(clientMessage.split(" "));
        CLICommandEnum commandName = CLICommandEnum.getEnum(msgList.get(0));
        List<String> parameters = translateParameters(msgList.subList(1, msgList.size()));
        return CLICommandEnum.getCommand(commandName, parameters);
    }

    private static List<String> translateParameters(List<String> parameters) {
    /*
        translateParameters([arg1, -arg2, "arg3, -arg4", --arg5, "arg6, arg7", arg8, -arg9])
            return -> [arg1, -arg2, arg3 -arg4, --arg5, arg6 arg7, arg8, -arg9]
        translateParameters([a1, "", a2, "a3", a4])
            return -> [a1, a2, a3, a4]
        translateParameters([a1, a2, "a3, a4", a5, "a6, a7, a8", a9]
            return -> [a1, a2, a3 a4, a5, a6 a7 a8, a9]

     */

        List<String> translated = new ArrayList<>(); //가공된 parameters
        StringBuilder collector = new StringBuilder(); //큰따옴표 안의 단어들을 모으는 버퍼 콜렉터
        boolean inQuote = false; //현재 분석중인 요소가 큰따옴표 안에 있는지 판단함

        Predicate<String> isStartsQuote = (str) -> str.charAt(0) == '\"';
        Predicate<String> isEndsQuote = (str) -> str.charAt(str.length() - 1) == '\"';

        for (String str : parameters) {
            //str이 큰따옴표로 둘러쌓인 경우
            if (isStartsQuote.test(str) && isEndsQuote.test(str)) {
                if (str.equals("\"\"")) {
                    continue;
                }
                translated.add(str.substring(1, str.length() - 1));
            }

            //str이 큰따옴표로 시작할 경우
            else if (isStartsQuote.test(str)) {
                inQuote = true;
                collector = new StringBuilder(str.substring(1));

            }

            //str이 큰따옴표로 끝날 경우
            else if (isEndsQuote.test(str)) {
                inQuote = false;
                collector.append(" ").append(str.substring(0, str.length() - 1));
                translated.add(collector.toString());
            }

            //str에 큰따옴표 문자가 없는 경우
            else {
                //큰따옴표 내부에 있는 경우
                if (inQuote) {
                    collector.append(" ").append(str);
                }

                //큰따옴표 외부에 있는 경우
                else {
                    translated.add(str);
                }
            }
        }

        return translated;
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
        ///////////////////첫 번째 옵션 해석//////////////////////////////
        Options preOptions = new Options();
        preOptions.addOption(Option.builder("ver")
                .longOpt("version")
                .hasArg()
                .argName("version number(1~3)")
                .desc("실행한다")
                .build()
        );

        DefaultParser preParser = new DefaultParser();
        CommandLine preCommand;
        try {
            preCommand = preParser.parse(preOptions, parameters.toArray(new String[0]));
        } catch (ParseException e) {
            System.out.println("인자가 잘못되었습니다. 원인: " + e.getMessage());
            return;
        }

        //////////////////본격 옵션 테스트///////////////////////////
        List<String> params;
        if (preCommand.hasOption("ver")) {
            if (preCommand.getOptionValue("ver").equals("1"))
                params = List.of("-ls", "arg1", "\"arg2 arg3\"", "--value", "\"arg4 arg5\"", "arg6");
            else if (preCommand.getOptionValue("ver").equals("2"))
                params = List.of("-ls", "arg1", "arg2 arg3", "--value", "arg4 arg5", "arg6");
            else if (preCommand.getOptionValue("ver").equals("3")) {
                //통으로 들어온 문자열을 플래그가 1인 경우(위 리스트)로 가공하는 알고리즘임
                String inlineString = "-ls arg1 \"arg2 arg3\" --value \"arg4 arg5\" arg6";

                //큰따옴표(") 로 먼저 나눈 뒤 나눠진 각 요소를 trim한다.
                List<String> firstParseList = new ArrayList<>(Arrays.stream(inlineString.split("\"")).map(String::trim).toList());

                //큰따옴표로 감싸지지 않았던 나머지 요소를 스페이스 문자( )로 나누면서, 하나의 리스트에 담아낸다.
                List<String> secondParseList = new ArrayList<>();
                for (int i = 0; i < firstParseList.size(); i++) {
                    if (i % 2 == 0) { //짝수인 경우
                        secondParseList.addAll(Arrays.asList(firstParseList.get(i).split(" ")));
                    } else {
                        secondParseList.add(firstParseList.get(i));
                    }
                }

                //결과를 저장한다.
                params = secondParseList;
            }
            else {
                params = new ArrayList<>();
            }
        }
        else {
            params = new ArrayList<>();
        }

        System.out.println("parameters: " + params);
        Options options = new Options()
                .addOption("ls", "list", false, "리스트를 출력합니다.")
                .addOption("val", "value", true, "옵션 값이 있는 옵션 테스트")
                .addOption("params", false, "주어진 파라미터를 출력합니다.");
        CommandLineParser parser = new DefaultParser();

        /*
        if (params.isEmpty()) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("movie", options);
        }
         */

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