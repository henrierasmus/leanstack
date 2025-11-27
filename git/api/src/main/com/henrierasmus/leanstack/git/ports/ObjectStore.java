package com.henrierasmus.leanstack.git.ports;

import com.henrierasmus.leanstack.git.domain.GitObject;
import com.henrierasmus.leanstack.git.domain.ObjectId;
import com.henrierasmus.leanstack.git.domain.ObjectType;

import java.io.IOException;

public interface ObjectStore {
    /**
     * Ensure the object/ directory exists. Creates the directory if it does not exist.
     */
    void ensureObjectDir(String gitDir) throws IOException;

    ObjectId computeId(String file, ObjectType type) throws IOException;

    ObjectId storeObject(String file, ObjectType type) throws IOException;
}
