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
        options.addOption(Option.builder("D")
                .argName("property=value")
                .numberOfArgs(2)
                .valueSeparator()
                .desc("use value for given property")
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
        else if (commandLine.hasOption("D")) {
            executeOptionD(controller, commandLine);
        }
        else {
            printHelp();
        }
    }

    public void executeOptionList(CLIController controller, CommandLine commandLine) {
        int index = 0;
        for (String movieName: controller.getMovieList()) {
            System.out.println("Movie " + (++index) + ": " + movieName);
        }
    }

    public void executeOptionInfo(CLIController controller, CommandLine commandLine) {
        String movieName = commandLine.getOptionValue("info");
        try {
            System.out.println(controller.getMovie(movieName));
        } catch (IllegalArgumentException e) {
            System.out.println("다음의 이름을 가진 영화를 찾을 수 없습니다: " + movieName);
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

    private void executeOptionD(CLIController controller, CommandLine commandLine) {
        System.out.println("getArgList() (length=" + commandLine.getArgList().size() + ") list:");
        for (String arg: commandLine.getArgList()) {
            System.out.println(arg);
        }

        String getOptionValue = commandLine.getOptionValue("D");
        if (getOptionValue != null) {
            System.out.println("getOptionValue()=" + getOptionValue);
        }
        else {
            System.out.println("getOptionValue() is null");
        }

        if (commandLine.getOptionValues("D") != null) {
            System.out.println("getOptionValues(\"D\") (length=" + commandLine.getOptionValues("D").length + ") list:");
            for (String optValue: commandLine.getOptionValues("D")) {
                System.out.println(optValue);
            }
        }
        else {
            System.out.println("getOptionValues(\"D\") is null");
        }
    }

    @Override
    public String description() {
        return "영화와 관련된 명령을 수행합니다.";
    }
}

