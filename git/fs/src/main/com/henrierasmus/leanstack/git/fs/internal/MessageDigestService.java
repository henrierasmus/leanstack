package com.henrierasmus.leanstack.git.fs.internal;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class MessageDigestService {
    MessageDigest md = MessageDigest.getInstance("SHA-1");

    public MessageDigestService() throws NoSuchAlgorithmException {
    }

    public byte[] hash(byte[] data) {
        return md.digest(data);
    }

    public String toHex(byte[] data) {
        return HexFormat.of().formatHex(data);
    }
}
