package com.henrierasmus.leanstack.git.domain;

public class Commit implements GitObject {
    private final byte[] treeId;
    private final byte[] message;

    public Commit(byte[] treeId, byte[] message) {
        this.treeId = treeId;
        this.message = message;
    }

    @Override
    public ObjectType type() {
        return ObjectType.COMMIT;
    }

    @Override
    public byte[] serialize() {
        byte[] serialized = new byte[treeId.length + message.length];
        System.arraycopy(treeId, 0, serialized, 0, treeId.length);
        System.arraycopy(message, 0, serialized, treeId.length, message.length);

        return serialized;
    }

    @Override
    public String getHeader() {
        return type().typeName() + " " + serialize().length + "\0";
    }
}
