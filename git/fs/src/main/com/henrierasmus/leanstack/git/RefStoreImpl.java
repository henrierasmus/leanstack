package com.henrierasmus.leanstack.git;

import com.henrierasmus.leanstack.git.ports.RefStore;

import java.nio.file.Files;
import java.nio.file.Path;

public class RefStoreImpl implements RefStore {
    FileSystemService fs = new FileSystemService();

    @Override
    public void ensureRefDir(String gitDir) {
        Path path = Path.of(gitDir + "/refs");
        if (!Files.exists(path)) {
            fs.createDirectory(path);
        }
    }
}
