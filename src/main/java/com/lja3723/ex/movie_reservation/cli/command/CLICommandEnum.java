package com.lja3723.ex.movie_reservation.cli.command;

import java.util.*;

public enum CLICommandEnum {
    none,    //특수 명령
    help, exit, version, movie, screening;
    //신규 명령어를 위에 추가합니다.

    private final static NoneCLICommand noneCommand = new NoneCLICommand(null);
    private final static HelpCLICommand helpCommand = new HelpCLICommand(null);
    private final static ExitCLICommand exitCommand = new ExitCLICommand(null);
    private final static VersionCLICommand versionCommand = new VersionCLICommand(null);
    private final static MovieCLICommand movieCommand = new MovieCLICommand(null);
    private final static ScreeningCLICommand screeningCommand = new ScreeningCLICommand(null);
    //신규 명령어의 싱글톤 변수를 위에 추가합니다.

    public static CLICommandEnum[] valuesExecutable() {
        final List<CLICommandEnum> executableEnums = new ArrayList<>(Arrays.stream(values()).toList());
        executableEnums.remove(none);
        return executableEnums.toArray(new CLICommandEnum[0]);
    }

    public static CLICommandEnum getEnum(String commandName) {
        try {
            return valueOf(commandName.toLowerCase());
        } catch (IllegalArgumentException e) {
            return none;
        }
    }

    public static CLICommand getCommand(String commandName) {
        return switch (getEnum(commandName)) {
            default -> noneCommand;
            case help -> helpCommand;
            case exit -> exitCommand;
            case version -> versionCommand;
            case movie -> movieCommand;
            case screening -> screeningCommand;
        };
    }
    //신규 명령어의 객체 반환 구문을 getCommand(String) 내부 switch 에 추가합니다.

    public static CLICommand getCommand(String commandName, List<String> parameters) {
        if (getEnum(commandName) == none) {
            return getCommand(commandName).setParameters(List.of(commandName));
        }

        return getCommand(commandName).setParameters(parameters);
    }
}
