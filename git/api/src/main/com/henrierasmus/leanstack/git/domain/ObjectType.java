package com.henrierasmus.leanstack.git.domain;

public enum ObjectType {
    BLOB("blob"),
    TREE("tree"),
    COMMIT("commit");

    private final String typeName;

    ObjectType(String typeName) {
        this.typeName = typeName;
    }

    public String typeName() {
        return typeName;
    }
}
