package com.henrierasmus.leanstack.git.fs.internal;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

// TODO: Moved this class might cause issues
public class FileSystemService {
    public FileSystemService() {
    }

    public void createDirectory(Path dirPath) throws IOException {
        Files.createDirectory(dirPath);
    }

    public void createFile(Path filePath) throws IOException {
        Files.createFile(filePath);
    }
}
