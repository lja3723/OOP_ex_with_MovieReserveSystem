package com.lja3723.ex.movie_reservation.cli;

import java.util.List;

public enum CLICommandEnum {
    none,
    help, exit, version, movie, screening;

    public static String getUsage(CLICommandEnum command) {
        return switch (command) {
            case help -> "명령어 목록을 출력합니다.";
            case exit -> "프로그램을 종료합니다.";
            case version -> "프로그램 버전을 출력합니다.";
            case movie -> "영화와 관련된 명령을 수행합니다.";
            case screening -> "상영 정보와 관련된 명령을 수행합니다.";
            default -> "";
        };
    }

    public static CLICommand getCommand(CLICommandEnum command, List<String> parameters) {
        return switch (command) {
            case help -> new HelpCLICommand(parameters);
            case exit -> new ExitCLICommand(parameters);
            case version -> new VersionCLICommand(parameters);
            case movie -> new MovieCLICommand(parameters);
            case screening -> new ScreeningCLICommand(parameters);
            case none -> new NoneCLICommand(parameters);
        };
    }
}
