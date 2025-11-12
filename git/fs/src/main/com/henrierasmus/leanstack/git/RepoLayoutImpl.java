package com.henrierasmus.leanstack.git;

import com.henrierasmus.leanstack.git.ports.RepoLayout;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RepoLayoutImpl implements RepoLayout {
    FileSystemService fs = new FileSystemService();

    @Override
    public void ensureGitDir(String dirLocation) {
        Path dirPath = Paths.get(dirLocation);
        if (!Files.exists(dirPath)) {
            fs.createDirectory(dirPath);
        }
    }
}
