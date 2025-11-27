package com.henrierasmus.leanstack.git.domain;

public interface GitObject {
    ObjectType type();
    byte[] serialize();
    String getHeader();
}
