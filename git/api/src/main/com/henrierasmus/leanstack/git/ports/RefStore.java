package com.henrierasmus.leanstack.git.ports;

import java.io.IOException;

public interface RefStore {
    void ensureRefDir(String path) throws IOException;
}
