package com.lja3723.ex.movie_reservation.cli;

import java.util.*;

public enum CLICommandEnum {
    none,
    help, exit, version, movie, screening;

    private final static NoneCLICommand noneCmd = new NoneCLICommand(null);
    private final static HelpCLICommand helpCmd = new HelpCLICommand(null);
    private final static ExitCLICommand exitCmd = new ExitCLICommand(null);
    private final static VersionCLICommand versionCmd = new VersionCLICommand(null);
    private final static MovieCLICommand movieCmd = new MovieCLICommand(null);
    private final static ScreeningCLICommand screeningCmd = new ScreeningCLICommand(null);

    public static CLICommandEnum getEnum(String cmdName) {
        try {
            return valueOf(cmdName.toLowerCase());
        } catch (IllegalArgumentException e) {
            return none;
        }
    }

    public static String getUsage(CLICommandEnum cmdName) {
        return switch(cmdName) {
            case none -> "";
            case help -> "명령어 목록을 출력합니다.";
            case exit -> "프로그램을 종료합니다.";
            case version -> "프로그램 버전을 출력합니다.";
            case movie -> "영화와 관련된 명령을 수행합니다.";
            case screening -> "상영 정보와 관련된 명령을 수행합니다.";
        };
    }

    public static CLICommand getCommand(CLICommandEnum cmdName) {
        return switch (cmdName) {
            case none -> noneCmd;
            case help -> helpCmd;
            case exit -> exitCmd;
            case version -> versionCmd;
            case movie -> movieCmd;
            case screening -> screeningCmd;
        };
    }

    public static CLICommand getCommand(CLICommandEnum cmdName, List<String> parameters) {
        return getCommand(cmdName).setParameters(parameters);
    }
}
