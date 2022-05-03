package com.lja3723.ex.movie_reservation.cli;

import java.util.*;

public enum CLICommandEnum {
    none, intro,    //특수 명령
    help, exit, version, movie, screening;
    //새로운 명령어를 위에 추가합니다.

    private final static List<CLICommandEnum> nonExecutableEnums = new ArrayList<>(Arrays.asList(
            none, intro
    ));
    private final static NoneCLICommand noneCommand = new NoneCLICommand(null);
    private final static HelpCLICommand helpCommand = new HelpCLICommand(null);
    private final static ExitCLICommand exitCommand = new ExitCLICommand(null);
    private final static VersionCLICommand versionCommand = new VersionCLICommand(null);
    private final static MovieCLICommand movieCommand = new MovieCLICommand(null);
    private final static ScreeningCLICommand screeningCommand = new ScreeningCLICommand(null);
    //새로운 명령어 싱글톤 변수를 위에 추가합니다.

    private static boolean isNonExecutableEnum(CLICommandEnum command) {
        return nonExecutableEnums.contains(command);
    }

    public static CLICommandEnum[] valuesExecutable() {
        final List<CLICommandEnum> executableEnums = new ArrayList<>(Arrays.stream(values()).toList());

        for (CLICommandEnum each: nonExecutableEnums) {
            executableEnums.remove(each);
        }

        return executableEnums.toArray(new CLICommandEnum[0]);
    }

    public static CLICommandEnum getEnum(String commandName) {
        try {
            CLICommandEnum command = valueOf(commandName.toLowerCase());
            return isNonExecutableEnum(command) ? none : command;
        } catch (IllegalArgumentException e) {
            return none;
        }
    }

    public static CLICommand getCommand(CLICommandEnum commandName) {
        if (isNonExecutableEnum(commandName)) {
            return noneCommand;
        }
        return switch (commandName) {
            default -> noneCommand;
            case help -> helpCommand;
            case exit -> exitCommand;
            case version -> versionCommand;
            case movie -> movieCommand;
            case screening -> screeningCommand;
        };
    }
    //새로운 명령어 객체 반환 구문을 메서드 내부 스위치 구문에 추가합니다.

    public static CLICommand getCommand(CLICommandEnum commandName, List<String> parameters) {
        return getCommand(commandName).setParameters(parameters);
    }
}
