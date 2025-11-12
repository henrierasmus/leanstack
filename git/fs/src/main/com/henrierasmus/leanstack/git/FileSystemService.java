package com.henrierasmus.leanstack.git;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileSystemService {
    public FileSystemService() {}

    protected void createDirectory(Path dirPath) {
        try {
            Files.createDirectory(dirPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
