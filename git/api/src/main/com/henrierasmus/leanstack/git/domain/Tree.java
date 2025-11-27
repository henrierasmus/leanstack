package com.henrierasmus.leanstack.git.domain;

import java.util.ArrayList;
import java.util.List;

public class Tree implements GitObject {
    private final List<GitObject> objects;

    public Tree() {
        objects = new ArrayList<>();
    }

    public Tree(GitObject... object) {
        objects = new ArrayList<>(List.of(object));
    }

    @Override
    public ObjectType type() {
        return ObjectType.TREE;
    }

    @Override
    public byte[] serialize() {
        return null;
    }

    @Override
    public String getHeader() {
        return "";
    }

    public void addObject(GitObject object) {
        objects.add(object);
    }

    public List<GitObject> getObjects() {
        return objects;
    }
}
