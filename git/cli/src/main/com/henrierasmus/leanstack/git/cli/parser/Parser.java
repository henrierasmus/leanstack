package com.henrierasmus.leanstack.git.cli.parser;

import com.henrierasmus.leanstack.git.cli.command.CommandContext;

import java.util.*;

public class Parser {

    public CommandContext parse(String[] args) {
        String commandName = args[0];
        String[] argsAdjusted = Arrays.copyOfRange(args, 1, args.length);
        List<String> arguments = new ArrayList<>();
        Map<String, String> options = new HashMap<>();

        for (String arg : argsAdjusted) {
            if (arg.startsWith("--")) {
                String[] keyValue = arg.split("=");
                if (keyValue.length != 2) throw new IllegalArgumentException("Option does not have a value or is illegal: " + keyValue[0]);
                options.put(keyValue[0].substring(2), keyValue[1]);
                continue;
            }

            arguments.add(arg);
        }

        return new CommandContext(commandName, arguments, options);
    }
}
