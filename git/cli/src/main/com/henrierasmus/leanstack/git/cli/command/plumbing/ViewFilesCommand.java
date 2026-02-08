package com.henrierasmus.leanstack.git.cli.command.plumbing;

import com.henrierasmus.leanstack.git.cli.command.Command;
import com.henrierasmus.leanstack.git.cli.command.CommandContext;
import com.henrierasmus.leanstack.git.cli.command.CommandFactory;
import com.henrierasmus.leanstack.git.cli.runtime.RuntimeContext;
import com.henrierasmus.leanstack.git.domain.Node;

import java.io.IOException;

public class ViewFilesCommand implements Command {
    private final CommandContext args;
    private final RuntimeContext ctx;

    public ViewFilesCommand(CommandContext args, RuntimeContext ctx) {
        this.args = args;
        this.ctx = ctx;
    }

    @Override
    public String execute() throws IOException {
        Node root = ctx.objectStore().getNodes(System.getProperty("user.dir"), 0);
//        printNode(root);
        return buildResponse(root, new StringBuilder()).toString();
    }

    private void printNode(Node node) {
        if (node.getChildren() == null) {
            System.out.println("\u2514 " + node.getFile().getName());
            return;
        }

        System.out.println("\u2502 " + node.getFile().getName());
        for (Node child : node.getChildren()) {
            printNode(child);
        }
    }

    private StringBuilder buildResponse(Node node, StringBuilder sb) {
        boolean addedToResponse = false;
        if (node.getParent() == null) {
            sb.append(node.getFile().getName()).append("\n");
            addedToResponse = true;
        }

        if (node.getChildren() != null) {
            if (!addedToResponse) sb.append("\u2514 ").append(node.getFile().getName()).append("\n").append("  ");
            for (Node child : node.getChildren()) {
                sb = buildResponse(child, sb);
            }
        } else {
            sb.append("\u2502 ").append(node.getFile().getName()).append("\n");
        }

        return sb;
    }

    public static CommandFactory factory() {
        return ViewFilesCommand::new;
    }
}
