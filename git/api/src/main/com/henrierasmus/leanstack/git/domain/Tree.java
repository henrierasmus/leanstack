package com.henrierasmus.leanstack.git.domain;

import java.util.ArrayList;
import java.util.List;

public class Tree implements GitObject {
    private final List<TreeEntry> treeEntries;
    private final byte[] serialized;

    public Tree(List<TreeEntry> treeEntries) {
        this.treeEntries = List.copyOf(treeEntries);
        this.serialized = serializeTree();
    }

    public Tree(byte[] data) {
        this.serialized = data;
        this.treeEntries = new ArrayList<>();

        int offset = 0;

        while (offset < data.length) {
            TreeEntry.ParsedEntry parsedEntry = TreeEntry.parse(data, offset);
            assert parsedEntry != null;
            offset = parsedEntry.nextOffset();
            treeEntries.add(parsedEntry.entry());
        }
    }

    @Override
    public ObjectType type() {
        return ObjectType.TREE;
    }

    @Override
    public byte[] serialize() {
        return serialized.clone();
    }

    public byte[] serializeTree() {
        int size = 0;

        for (TreeEntry entry : treeEntries) {
            size += entry.serializedSize();
        }

        byte[] serialized = new byte[size];
        int position = 0;

        for (TreeEntry entry : treeEntries) {
            position = entry.writeTo(serialized, position);
        }

        return serialized;
    }

    @Override
    public String getHeader() {
        return type().typeName() + " " + serialized.length + "\0";
    }

    public List<TreeEntry> getObjects() {
        return treeEntries;
    }
}
