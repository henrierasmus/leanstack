package com.henrierasmus.leanstack.git.ports;

import com.henrierasmus.leanstack.git.domain.Node;
import com.henrierasmus.leanstack.git.domain.ObjectId;
import com.henrierasmus.leanstack.git.domain.ObjectType;

import java.io.IOException;
import java.nio.file.Path;

public interface ObjectStore {
    /**
     * Ensure the object/ directory exists. Creates the directory if it does not exist.
     */
    void ensureObjectDir(String gitDir) throws IOException;

    Path ensureIndex(String gitDir) throws IOException;

    ObjectId storeObject(String file, ObjectType type) throws IOException;

    String catFile(String id) throws IOException;

    void updateIndex(String path, String file, String hash) throws IOException;

    void updateIndex(String path, String data) throws IOException;

    /**
     * This is going to take the data in the index file and create trees
     *
     * @return
     */
    ObjectId writeTree() throws IOException;

    boolean validateObjectType(String objectId, ObjectType type) throws IOException;

    ObjectId commitTree(String treeId, String message) throws IOException;

    Node getNodes(String path, Integer offset);
}
