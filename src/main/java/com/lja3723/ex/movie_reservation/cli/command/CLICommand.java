package com.lja3723.ex.movie_reservation.cli.command;

import com.lja3723.ex.movie_reservation.cli.CLIController;
import org.apache.commons.cli.*;
import java.util.*;

abstract public class CLICommand {
    private final static List<String> emptyParameter = new ArrayList<>();
    private List<String> parameters;
    private final Options options = new Options();

    public CLICommand(List<String> parameters) {
        setParameters(parameters);
        options.addOption(Option.builder("?")
                .longOpt("help")
                .desc("명령어와 함께 사용할 수 있는 옵션 목록을 출력합니다.")
                .build());
        initOptions(options);
    }

    public CLICommand setParameters(List<String> parameters) {
        this.parameters = (null == parameters) ? emptyParameter : parameters;
        return this;
    }

    public void execute(CLIController controller) {
        try {
            CommandLine commandLine = new DefaultParser().parse(options, parameters.toArray(new String[0]));
            if (commandLine.hasOption("help")) { printHelp(); }
            else { execute(controller, commandLine); }
        } catch (ParseException e) {
            System.out.println("잘못된 매개변수입니다. (원인: " + e.getMessage() + ")");
        }
    }

    public void printHelp() {
        System.out.println("description: " + description());
        new HelpFormatter().printHelp(getCmdLineSyntax(), options);
    }

    protected String getCmdLineSyntax() {
        return commandName() + " [옵션]";
    }

    protected int getLongestStringLengthOf(Collection<String> collection) {
        int longest = 0;
        for (String string: collection) {
            longest = Math.max(longest, string.length());
        }
        return longest;
    }

    abstract public String commandName();
    abstract protected void initOptions(Options options);
    abstract protected void execute(CLIController controller, CommandLine commandLine);
    abstract public String description();
}

//특수 명령
final class NoneCLICommand extends CLICommand {
    public NoneCLICommand(List<String> parameters) { super(parameters); }

    @Override
    public String commandName() { return "none"; }

    @Override
    protected void initOptions(Options options) {  }

    @Override
    public void execute(CLIController controller, CommandLine commandLine) {
        System.out.print(unknownCommand(commandLine.getArgs()[0]));
    }

    @Override
    public String description() { return ""; }

    public static String unknownCommand(String unknownCommand) {
        return unknownCommand + " -> 알 수 없는 명령어입니다. 명령어 목록을 보시려면 help를 입력하십시오.";
    }
}