package com.henrierasmus.leanstack.git.fs.internal;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileSystemService {
    public FileSystemService() {
    }

    public void createDirectory(Path dirPath) throws IOException {
        Files.createDirectory(dirPath);
    }

    public Path createFile(Path filePath) throws IOException {
        return Files.createFile(filePath);
    }

    public void write(String path, byte[] data) throws IOException {
        try (OutputStream out = new BufferedOutputStream(new FileOutputStream(path))) {
            out.write(data);
        }
    }

    public void write(String path, String... data) throws IOException {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(path, true))) {
            for (String d : data) {
                out.write(d);
            }
        }
    }

    public void write(String path, String data) throws IOException {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(path, true))) {
            out.write(data);
        }
    }

    public String readFile(Path filePath) throws IOException {
        StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        }

        return result.toString();
    }

    public byte[] readFileBytes(Path filePath) throws IOException {
        return Files.readAllBytes(filePath);
    }

    public void cleanFile(String filePath) throws IOException {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.print("");
            writer.close();
        }
    }
}
