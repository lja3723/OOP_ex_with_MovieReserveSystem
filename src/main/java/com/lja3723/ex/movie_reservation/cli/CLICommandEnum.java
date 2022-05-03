package com.lja3723.ex.movie_reservation.cli;

import java.util.*;

public enum CLICommandEnum {
    none, intro,    //특수 명령
    help, exit, version, movie, screening;

    private final static NoneCLICommand noneCmd = new NoneCLICommand(null);
    private final static HelpCLICommand helpCmd = new HelpCLICommand(null);
    private final static ExitCLICommand exitCmd = new ExitCLICommand(null);
    private final static VersionCLICommand versionCmd = new VersionCLICommand(null);
    private final static MovieCLICommand movieCmd = new MovieCLICommand(null);
    private final static ScreeningCLICommand screeningCmd = new ScreeningCLICommand(null);

    public static CLICommandEnum[] executableValues() {
        List<CLICommandEnum> executableEnums = new ArrayList<>(Arrays.stream(values()).toList());

        //특수 명령 제거
        executableEnums.remove(none);
        executableEnums.remove(intro);

        //특수 명령 제거한 리스트 반환
        return executableEnums.toArray(new CLICommandEnum[0]);
    }

    public static CLICommandEnum getEnum(String cmdName) {
        try {
            return valueOf(cmdName.toLowerCase());
        } catch (IllegalArgumentException e) {
            return none;
        }
    }

    public static CLICommand getCommand(CLICommandEnum cmdName) {
        return switch (cmdName) {
            case none, intro -> noneCmd;    //특수 명령
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
