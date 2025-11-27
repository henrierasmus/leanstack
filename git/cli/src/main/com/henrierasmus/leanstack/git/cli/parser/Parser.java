package com.henrierasmus.leanstack.git.cli.parser;

import com.henrierasmus.leanstack.git.cli.command.CommandContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {

    public CommandContext parse(String[] args) {
        String commandName = args[0];
        String[] argsAdjusted = Arrays.copyOfRange(args, 1, args.length);
        List<String> arguments = new ArrayList<>();
        List<String> options = new ArrayList<>();

        for (String arg : argsAdjusted) {
            if (arg.indexOf(0) == '-') {
                options.add(arg);
                continue;
            }

            arguments.add(arg);
        }

        return new CommandContext(commandName, arguments, options);
    }
}
