package com.lja3723.ex.movie_reservation.cli.command;

import com.lja3723.ex.movie_reservation.cli.CLIController;
import org.apache.commons.cli.*;
import java.util.List;

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
        options.addOption(Option.builder("i")
                .longOpt("info")
                .hasArg()
                .argName("movie name")
                .desc("영화 이름에 대한 영화 정보를 출력합니다.")
                .build());
        options.addOption(Option.builder()
                .option("t")
                .longOpt("test")
                .desc("옵션 빌더 테스트")
                .hasArgs()
                .numberOfArgs(2)
                .optionalArg(false)
                .argName("testArg> <testarg2")
                .build());
    }

    @Override
    public void execute(CLIController controller, CommandLine commandLine) {
        if (commandLine.hasOption("ls")) {
            executeOptionList(controller, commandLine);
        }
        else if (commandLine.hasOption("info")) {
            executeOptionInfo(controller, commandLine);
        }
        else if (commandLine.hasOption("t")) {
            executeOptionTest(controller, commandLine);
        }
        else {
            printHelp();
        }
    }

    public void executeOptionList(CLIController controller, CommandLine commandLine) {
        List<String> list = controller.getMovieList();
        int longest = getLongestStringLengthOf(list);

        //print legend
        System.out.printf("%-4s%-" + (longest + 3) + "s%n", "번호", "영화 이름");

        //print elements
        for (int i = 0; i < list.size(); i++) {
            System.out.printf("%-5d%-" + (longest + 3) + "s%n", (i + 1), list.get(i));
        }
    }

    public void executeOptionInfo(CLIController controller, CommandLine commandLine) {
        String movieName = commandLine.getOptionValue("info");
        try {
            System.out.println(controller.getMovie(movieName));
        } catch (IllegalArgumentException e) {
            System.out.println("영화 \"" + movieName + "\"(을)를 목록에서 찾을 수 없습니다.");
        }
    }

    private void executeOptionTest(CLIController controller, CommandLine commandLine) {
        System.out.println("getArgList() (length=" + commandLine.getArgList().size() + ") list:");
        for (String arg: commandLine.getArgList()) {
            System.out.println(arg);
        }

        String getOptionValue = commandLine.getOptionValue("t");
        if (getOptionValue != null) {
            System.out.println("getOptionValue()=" + getOptionValue);
        }
        else {
            System.out.println("getOptionValue() is null");
        }

        if (commandLine.getOptionValues("t") != null) {
            System.out.println("getOptionValues(\"t\") (length=" + commandLine.getOptionValues("t").length + ") list:");
            for (String optValue: commandLine.getOptionValues("t")) {
                System.out.println(optValue);
            }
        }
        else {
            System.out.println("getOptionValues(\"t\") is null");
        }
    }

    @Override
    public String description() {
        return "영화와 관련된 명령을 수행합니다.";
    }
}

