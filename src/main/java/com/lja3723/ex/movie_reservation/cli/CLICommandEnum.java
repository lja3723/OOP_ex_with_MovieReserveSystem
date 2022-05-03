package com.lja3723.ex.movie_reservation.cli;

import java.util.*;

public enum CLICommandEnum {
    none, intro,    //특수 명령
    help, exit, version, movie, screening;

    private final static List<CLICommandEnum> nonExecutableEnums = new ArrayList<>(Arrays.asList(
            none, intro
    ));
    private final static NoneCLICommand noneCmd = new NoneCLICommand(null);
    private final static HelpCLICommand helpCmd = new HelpCLICommand(null);
    private final static ExitCLICommand exitCmd = new ExitCLICommand(null);
    private final static VersionCLICommand versionCmd = new VersionCLICommand(null);
    private final static MovieCLICommand movieCmd = new MovieCLICommand(null);
    private final static ScreeningCLICommand screeningCmd = new ScreeningCLICommand(null);

    private static boolean isNonExecutableEnum(CLICommandEnum command) {
        return nonExecutableEnums.contains(command);
    }

    public static CLICommandEnum[] executableValues() {
        final List<CLICommandEnum> executableEnums = new ArrayList<>(Arrays.stream(values()).toList());

        for (CLICommandEnum each: nonExecutableEnums) {
            executableEnums.remove(each);
        }

        return executableEnums.toArray(new CLICommandEnum[0]);
    }

    public static CLICommandEnum getEnum(String cmdName) {
        try {
            var command = valueOf(cmdName.toLowerCase());
            return isNonExecutableEnum(command) ? none : command;
        } catch (IllegalArgumentException e) {
            return none;
        }
    }

    public static CLICommand getCommand(CLICommandEnum cmdName) {
        if (isNonExecutableEnum(cmdName)) {
            return noneCmd;
        }
        return switch (cmdName) {
            default -> noneCmd;
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
