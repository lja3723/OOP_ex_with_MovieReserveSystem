package com.lja3723.ex.movie_reservation.cli.command;

import com.lja3723.ex.movie_reservation.cli.CLIController;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

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

