package com.henrierasmus.leanstack.git;

import com.henrierasmus.leanstack.git.ports.ObjectStore;

import java.nio.file.Files;
import java.nio.file.Path;

public class ObjectStoreImpl implements ObjectStore {
    FileSystemService fs = new FileSystemService();

    @Override
    public void ensureObjectDir(String gitDir) {
        Path path = Path.of(gitDir + "/object");
        if (!Files.exists(path)) {
            fs.createDirectory(path);
        }
    }
}
