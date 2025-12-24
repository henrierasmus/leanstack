package com.henrierasmus.leanstack.git.fs.internal;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageDigestService {
    MessageDigest md = MessageDigest.getInstance("SHA-1");

    public MessageDigestService() throws NoSuchAlgorithmException {
    }

    public byte[] hash(byte[] data) {
        return md.digest(data);
    }
}
