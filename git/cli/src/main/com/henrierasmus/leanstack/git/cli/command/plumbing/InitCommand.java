package com.henrierasmus.leanstack.git.cli.command.plumbing;

import com.henrierasmus.leanstack.git.cli.command.Command;
import com.henrierasmus.leanstack.git.cli.command.CommandContext;
import com.henrierasmus.leanstack.git.cli.command.CommandFactory;
import com.henrierasmus.leanstack.git.cli.runtime.RuntimeContext;
import com.henrierasmus.leanstack.git.usecase.InitRepository;

import java.io.IOException;

public class InitCommand implements Command {
    private final CommandContext args;
    private final RuntimeContext ctx;
    private final InitRepository service = InitRepository.getInstance();

    private InitCommand(CommandContext args, RuntimeContext ctx) {
        this.args = args;
        this.ctx = ctx;
    }

    @Override
    public String execute() {
        try {
            service.initRepo(System.getProperty("user.dir"));
        } catch (IOException e) {
            System.out.println("IOExceptions");
        }
        return "Created";
    }

    public static CommandFactory factory() {
        return InitCommand::new;
    }
}
