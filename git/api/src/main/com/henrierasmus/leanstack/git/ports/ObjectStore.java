package com.henrierasmus.leanstack.git.ports;

/*
    Manages git objects
    Types of objects: blob, tree
 */
public interface ObjectStore {
    /**
     * Ensure the object/ directory exists. Creates the directory if it does not exist.
     */
    void ensureObjectDir(String gitDir);
}
