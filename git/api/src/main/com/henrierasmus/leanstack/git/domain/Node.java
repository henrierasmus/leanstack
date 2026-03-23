package com.henrierasmus.leanstack.git.domain;

import java.io.File;
import java.util.List;

public class Node {
    private final Node parent;
    private final File file;
    private final List<Node> children;

    public Node(Node parent, File file, List<Node> children) {
        this.parent = parent;
        this.file = file;
        this.children = children;
    }

    public Node getParent() {
        return parent;
    }

    public File getFile() {
        return file;
    }

    public List<Node> getChildren() {
        return children;
    }

    public boolean hasParent() {
        return parent != null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (parent != null) sb.append("Parent: ").append(parent.getFile().getName());
        else sb.append("Parent: null");
        sb.append(" - File: ").append(file.getName()).append(" - Is Dir: ").append(file.isDirectory());
        return sb.toString();
    }
}
