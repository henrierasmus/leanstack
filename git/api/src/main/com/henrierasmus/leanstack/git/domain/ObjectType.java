package com.henrierasmus.leanstack.git.domain;

public enum ObjectType {
    BLOB("blob", (byte) 1),
    TREE("tree", (byte) 2),
    COMMIT("commit", (byte) 3);

    private final String typeName;
    private final byte bit;

    ObjectType(String typeName, byte bit) {
        this.typeName = typeName;
        this.bit = bit;
    }

    public String typeName() {
        return typeName;
    }

    public byte typeBit() {
        return bit;
    }

    public static ObjectType getTypeByBit(byte bit) {
        return switch (bit) {
            case 1 -> ObjectType.BLOB;
            case 2 -> ObjectType.TREE;
            case 3 -> ObjectType.COMMIT;
            default -> null;
        };
    }
}
