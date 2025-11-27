package com.henrierasmus.leanstack.git.domain;

public class ObjectId {
    private final String hex;

    public ObjectId(String objectId) {
        this.hex = objectId;
    }

    public String getHex() {
        return hex;
    }

    public String getDirId() {
        return hex.substring(0, 2);
    }

    public String getFileId() {
        return hex.substring(2);
    }
}
