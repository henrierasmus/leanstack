package com.henrierasmus.leanstack.git.cli.command.plumbing;

import com.henrierasmus.leanstack.git.cli.command.AbstractCommand;
import com.henrierasmus.leanstack.git.usecase.InitRepository;

public class InitCommand extends AbstractCommand {
    private final InitRepository service = InitRepository.getInstance();

    public InitCommand(String commandName) {
        super(commandName);
    }

    @Override
    public void execute() {
        service.initRepo(System.getProperty("user.dir"));
    }
}
