package com.henrierasmus.leanstack.git;

import com.henrierasmus.leanstack.git.fs.internal.FileSystemService;
import com.henrierasmus.leanstack.git.ports.RefStore;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class RefStoreImpl implements RefStore {
    FileSystemService fs = new FileSystemService();

    @Override
    public void ensureRefDir(String gitDir) throws IOException {
        Path path = Path.of(gitDir + "/refs");
        if (!Files.exists(path)) {
            fs.createDirectory(path);
        }
    }
}
