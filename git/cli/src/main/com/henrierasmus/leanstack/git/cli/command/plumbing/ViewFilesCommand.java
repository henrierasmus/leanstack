package com.henrierasmus.leanstack.git.cli.command.plumbing;

import com.henrierasmus.leanstack.git.cli.command.Command;
import com.henrierasmus.leanstack.git.cli.command.CommandContext;
import com.henrierasmus.leanstack.git.cli.command.CommandFactory;
import com.henrierasmus.leanstack.git.cli.runtime.RuntimeContext;
import com.henrierasmus.leanstack.git.domain.Node;

import java.io.IOException;
import java.util.List;

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
        return buildResponse(root, new StringBuilder(), "", "", true).toString();
    }

    private StringBuilder buildResponse(Node node, StringBuilder sb, String prefix, String connector, boolean isLastSibling) {
        sb.append("\n").append(prefix).append(connector).append(node.getFile().getName());

        List<Node> children = node.getChildren();
        if (children != null) {
            for (int i = 0; i < children.size(); i++) {
                String childPrefix = prefix + "  ";
                if (!isLastSibling) {
                    childPrefix = prefix + "\u2502" + "  ";
                }
                if (i == children.size() - 1) {
                    buildResponse(children.get(i), sb, childPrefix, "\u2514 ", true);
                } else {
                    buildResponse(children.get(i), sb, childPrefix, "\u251C ", false);
                }
            }
        }

        return sb;
    }

    public static CommandFactory factory() {
        return ViewFilesCommand::new;
    }
}
