package com.lja3723.ex.movie_reservation.cli.command;

import com.lja3723.ex.movie_reservation.cli.CLIController;
import org.apache.commons.cli.*;
import java.util.*;

abstract public class CLICommand {
    private final static List<String> emptyParameter = new ArrayList<>();
    private List<String> parameters;
    private final Options options;

    public CLICommand(List<String> parameters) {
        setParameters(parameters);
        this.options = new Options();
        options.addOption(Option.builder("help")
                .desc("명령어 사용법을 출력합니다.")
                .build());
        initOptions(options);
    }

    public CLICommand setParameters(List<String> parameters) {
        this.parameters = (null == parameters) ? emptyParameter : parameters;
        return this;
    }

    public void execute(CLIController controller) {
        if (parameters.size() == 1 && parameters.get(0).equals("-help")) {
            printHelp();
        }
        else {
            try {
                execute(controller, new DefaultParser().parse(options, parameters.toArray(new String[0])));
            } catch (ParseException e) {
                System.out.println("잘못된 파라미터입니다. (원인: " + e.getMessage() + ")");
            }
        }
    }

    public void printHelp() {
        System.out.println(description());
        new HelpFormatter().printHelp(getCmdLineSyntax(), options);
    }

    protected String getCmdLineSyntax() {
        return commandName() + " [옵션]";
    }
    abstract public String commandName();
    abstract protected void initOptions(Options options);
    abstract protected void execute(CLIController controller, CommandLine command);
    abstract public String description();
}

//특수 명령
final class NoneCLICommand extends CLICommand {
    public NoneCLICommand(List<String> parameters) { super(parameters); }

    @Override
    public String commandName() {
        return "none";
    }

    @Override
    protected void initOptions(Options options) {  }

    @Override
    public void execute(CLIController controller, CommandLine command) {
        System.out.println("알 수 없는 명령어입니다. 명령어 목록을 보시려면 help를 입력하십시오.");
    }

    @Override
    public String description() {
        return "";
    }
}