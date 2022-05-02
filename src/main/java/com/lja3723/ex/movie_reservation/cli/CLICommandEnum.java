package com.lja3723.ex.movie_reservation.cli;

import java.util.List;

public enum CLICommandEnum {
    none,
    help, exit, version, movie, screening;

    public static CLICommandEnum getEnum(String commandName) {
        try {
            return valueOf(commandName);
        } catch (IllegalArgumentException e) {
            return none;
        }
    }

    public static String getUsage(CLICommandEnum command) {
        return switch (command) {
            case none -> "";
            case help -> "명령어 목록을 출력합니다.";
            case exit -> "프로그램을 종료합니다.";
            case version -> "프로그램 버전을 출력합니다.";
            case movie -> "영화와 관련된 명령을 수행합니다.";
            case screening -> "상영 정보와 관련된 명령을 수행합니다.";
        };
    }

    public static CLICommand getCommand(String commandName, List<String> parameters) {
        return switch (getEnum(commandName)) {
            case none -> new NoneCLICommand(parameters);
            case help -> new HelpCLICommand(parameters);
            case exit -> new ExitCLICommand(parameters);
            case version -> new VersionCLICommand(parameters);
            case movie -> new MovieCLICommand(parameters);
            case screening -> new ScreeningCLICommand(parameters);
        };
    }
}
