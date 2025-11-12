package com.henrierasmus.leanstack.git.ports;

/**
 * This class will ensure that the directory and files are created
 */
public interface RepoLayout {
    void ensureGitDir(String dirLocation);
}
