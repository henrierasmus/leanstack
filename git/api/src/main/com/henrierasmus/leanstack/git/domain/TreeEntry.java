package com.henrierasmus.leanstack.git.domain;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class TreeEntry {
    private static final int OBJECT_ID_LENGTH = 20;

    private final byte[] id;
    private final String fileName;
    private final ObjectType type;
    private final byte[] serialized;

    public TreeEntry(byte[] id, String fileName, ObjectType type) {
        this.id = Objects.requireNonNull(id);
        if (id.length != OBJECT_ID_LENGTH)
            throw new IllegalArgumentException("Object ID length expected to be 20, not: " + id.length);

        this.fileName = Objects.requireNonNull(fileName);
        if (fileName.length() >= 0xFFFF) throw new IllegalArgumentException("File names a limited to 0xFFFF");

        this.type = Objects.requireNonNull(type);
        this.serialized = serializeEntry();
    }

    public int serializedSize() {
        return serialized.length;
    }

    public byte[] serialize() {
        return serialized.clone();
    }

    public int writeTo(byte[] out, int offset) {
        System.arraycopy(serialized, 0, out, offset, serialized.length);
        return offset + serialized.length;
    }

    public static ParsedEntry parse(byte[] buffer, int offset) {
        final int TYPE_BIT_SIZE = 1;
        final int FILE_SIZE_ENDIAN = 2;
        if (offset == buffer.length) return null;
        if (offset > buffer.length) throw new IllegalArgumentException("Offset is larger than buffer, offset: " + offset + " Buffer: " + buffer.length);
        if (buffer.length - offset < (TYPE_BIT_SIZE + FILE_SIZE_ENDIAN + OBJECT_ID_LENGTH)) {
            throw new IllegalArgumentException("Malformed data: Data expected to be at least 23 bytes, data provided: " + (buffer.length - offset));
        }

        int pos = offset;
        byte typeBit = buffer[pos++];
        ObjectType type = ObjectType.getTypeByBit(typeBit);

        if (type == null) throw new IllegalArgumentException("Object type is not valid");

        byte[] id = new byte[OBJECT_ID_LENGTH];

        System.arraycopy(buffer, pos, id, 0, OBJECT_ID_LENGTH);
        pos += OBJECT_ID_LENGTH;

        byte fileSizeA = buffer[pos++];
        byte fileSizeB = buffer[pos++];

        int fileSize = ((fileSizeA & 0xFF) << 8) | (fileSizeB & 0xFF);
        if (fileSize < 1) throw new IllegalArgumentException("Malformed data: Git object does not have a name");
        if (buffer.length - pos < fileSize) throw new IllegalArgumentException("Malformed data: Git object name is malformed or truncated");
        TreeEntry entry = new TreeEntry(id, new String(buffer, pos, fileSize, StandardCharsets.UTF_8), type);
        pos += fileSize;

        return new ParsedEntry(entry, pos);
    }

    private byte[] serializeEntry() {
        final int TYPE_BIT_SIZE = 1;
        final int FILE_SIZE_ENDIAN = 2;
        byte[] fileNameBytes = (fileName).getBytes(StandardCharsets.UTF_8);

        int serializedSize = TYPE_BIT_SIZE +
                OBJECT_ID_LENGTH +
                FILE_SIZE_ENDIAN +
                fileNameBytes.length;

        int pos = 0;
        byte[] serialized = new byte[serializedSize];

        serialized[0] = type.typeBit();
        pos++;
        System.arraycopy(id, 0, serialized, pos, OBJECT_ID_LENGTH);
        pos += id.length;

        serialized[pos++] = (byte) ((fileNameBytes.length >>> 8) & 0xFF);
        serialized[pos++] = (byte) (fileNameBytes.length & 0xFF);

        System.arraycopy(fileNameBytes, 0, serialized, pos, fileNameBytes.length);
        return serialized;
    }

    public static class ParsedEntry {
        private final TreeEntry entry;
        private final int nextOffset;

        public ParsedEntry(TreeEntry entry, int nextOffset) {
            this.entry = entry;
            this.nextOffset = nextOffset;
        }

        public TreeEntry entry() {
            return entry;
        }

        public int nextOffset() {
            return nextOffset;
        }
    }
}