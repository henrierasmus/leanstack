package com.henrierasmus.leanstack.git.domain;

import java.nio.charset.StandardCharsets;

public class Blob implements GitObject {
    private final byte[] data;

    public Blob(byte[] data) {
        this.data = data;
    }

    @Override
    public ObjectType type() {
        return ObjectType.BLOB;
    }

    @Override
    public byte[] serialize() {
        return data;
    }

    @Override
    public String getHeader() {
        return type().typeName() + " " + data.length + "\0";
    }
}
