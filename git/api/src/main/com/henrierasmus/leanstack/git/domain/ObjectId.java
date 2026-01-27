package com.henrierasmus.leanstack.git.domain;

import java.util.HexFormat;

public class ObjectId {
    private final byte[] id;

    public ObjectId(byte[] id) {
        this.id = id;
    }

    public ObjectId(String id) {
        this.id = HexFormat.of().parseHex(id);
    }

    public byte[] getId() {
        return id;
    }

    public String getHex() {
        return HexFormat.of().formatHex(id);
    }

    public String getDir() {
        return getHex().substring(0, 2);
    }

    public String getFile() {
        return getHex().substring(2);
    }
}
