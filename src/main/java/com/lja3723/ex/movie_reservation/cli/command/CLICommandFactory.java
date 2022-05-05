package com.lja3723.ex.movie_reservation.cli.command;

import java.util.ArrayList;
import java.util.List;

public final class CLICommandFactory {
    private CLICommandFactory() {}

    public static CLICommand getCommand(String clientMessage) {
        CLICommandEnum commandName;
        String parameter;

        if (clientMessage.contains(" ")) {
            commandName = CLICommandEnum.getEnum(clientMessage.substring(0, clientMessage.indexOf(" ")));
            parameter = clientMessage.substring(clientMessage.indexOf(" "));
        }
        else {
            commandName = CLICommandEnum.getEnum(clientMessage);
            parameter = "";
        }

        return CLICommandEnum.getCommand(commandName, parseParameter(parameter));
    }

    private static List<String> parseParameter(String parameter) {
    /*
        parseParameter(arg1 -arg2 "arg3 -arg4" --arg5 "arg6 arg7" arg8 -arg9])
            return -> [arg1, -arg2, arg3 -arg4, --arg5, arg6 arg7, arg8, -arg9]
        parseParameter(a1 "" a2 "a3" a4)
            return -> [a1, a2, a3, a4]
        parseParameter(a1 a2 "a3 a4" a5 "a6 a7 a8" a9)
            return -> [a1, a2, a3 a4, a5, a6 a7 a8, a9]
        parseParameter(a1 a2 "a3 a4 \"a5 a6\" a7" a8 a9)
            return -> [a1, a2, a3 a4 "a5 a6" a7, a8, a9]
        parseParameter(a1 a2"a3\"\"\" \"a4""a5""\"a6\"a7"a8 a9)
            return -> [a1, a2, a3""" "a4, a5, "a6"a7, a8, a9]
     */
        List<String> translated = new ArrayList<>();
        if (parameter.length() > 0) {
            //큰따옴표 기준으로 문자열을 나눔
            translated = List.of(parameter.split("\""));
            //escape 된 큰따옴표(\")를 복구
            translated = repairQuote(translated);
            //큰따옴표 내부에 있지 않은 문자열을 공백을 기준으로 나눔
            translated = splitUnquotedString(translated);
            //빈 문자열 제거
            for (int i = translated.size() - 1; i >= 0; i--) {
                if (translated.get(i).length() == 0)
                    translated.remove(i);
            }
        }

        return translated;
    }

    private static List<String> repairQuote(List<String> splitWithQuote) {
    /*
        escaping 된 큰따옴표 문자(\")를 다시 복구
        ex)
        (a1 a2 "a3 a4 \"a5 a6\" a7" a8 a9).split(") -> [ a1 a2 , a3 a4 \, a5 a6\,  a7,  a8 a9]
        repairQuote([ a1 a2 , a3 a4 \, a5 a6\,  a7,  a8 a9]) -> [ a1 a2 , a3 a4 "a5 a6" a7,  a8 a9]
     */
        List<String> result = new ArrayList<>(splitWithQuote);

        //1. \가 있는 문자들을 하나로 뭉친다.
        for (int i = result.size() - 1; i >= 0; i--) {
            if (result.get(i).contains("\\") && i != result.size() - 1) {
                result.set(i, result.get(i) + result.get(i + 1));
                result.remove(i + 1);
            }
        }
        //2. \를 "로 치환한다.
        result.replaceAll(s -> s.replace('\\', '\"'));

        return result;
    }

    private static List<String> splitUnquotedString(List<String> translated) {
    /*
        큰따옴표로 감싸지지 않았던 문자열을 공백으로 분리
        splitUnquotedString([ a1 a2 , a3 a4 "a5 a6" a7,  a8 a9]) -> [ a1, a2, a3 a4 "a5 a6" a7, a8, a9]
     */
        List<String> result = new ArrayList<>();
        for (int i = 0; i < translated.size(); i++) {
            //짝수번째 요소가 큰따옴표로 둘러쌓인 문자열임
            if (i % 2 == 0) {
                result.addAll(List.of(translated.get(i).trim().split(" ")));
            }
            else {
                result.add(translated.get(i));
            }
        }

        return result;
    }
}

