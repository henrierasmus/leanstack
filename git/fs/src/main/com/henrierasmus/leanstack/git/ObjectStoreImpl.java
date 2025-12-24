package com.henrierasmus.leanstack.git;

import com.henrierasmus.leanstack.git.domain.*;
import com.henrierasmus.leanstack.git.fs.internal.FileSystemService;
import com.henrierasmus.leanstack.git.fs.internal.MessageDigestService;
import com.henrierasmus.leanstack.git.ports.ObjectStore;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class ObjectStoreImpl implements ObjectStore {
    FileSystemService fs = new FileSystemService();
    MessageDigestService md = new MessageDigestService();
    Map<ObjectType, Class<? extends GitObject>> objectTypeMap = new HashMap<>();

    public ObjectStoreImpl() throws NoSuchAlgorithmException {
        objectTypeMap.put(ObjectType.BLOB, Blob.class);
        objectTypeMap.put(ObjectType.TREE, Tree.class);
//        objectTypeMap.put(ObjectType.COMMIT, Commit.class);
    }

    @Override
    public void ensureObjectDir(String gitDir) throws IOException {
        Path path = Path.of(gitDir + "/objects");
        if (!Files.exists(path)) {
            fs.createDirectory(path);
        }
    }

    @Override
    public Path ensureIndex(String gitDir) throws IOException {
        Path path = Path.of(gitDir + "/.jgit/index");

        if (Files.exists(path)) {
            return path;
        }

        return fs.createFile(path);
    }

    @Override
    public ObjectId storeObject(String file, ObjectType type) throws IOException {
        GitObject object = createGitObject(Files.readAllBytes(Path.of(file)), type);
        ObjectId objectId = computeId(object);
        String dir = ".jgit/objects/" + objectId.getDir();
        String gitFile = dir + "/" + objectId.getFile();
        Path filePath = Path.of(gitFile);

        fs.createDirectory(Path.of(dir));
        fs.createFile(filePath);
        byte[] toWrite = concatByteArray(objectHeaderToBytes(object), object.serialize());
        fs.write(filePath.toString(), toWrite);

        return objectId;
    }

    // TODO: some similarities should be found to have 1 "writeObject" method
    @Override
    public ObjectId writeTree() throws IOException, IndexOutOfBoundsException {
        Path indexPath = Path.of(".jgit/index");
        List<TreeEntry> entries = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(indexPath.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] objectData = line.split(" ", 2);
                ObjectId objectId = new ObjectId(objectData[0].getBytes(StandardCharsets.UTF_8));

                Path objectPath = Path.of(".jgit/objects/" + objectId.getDir() + "/" + objectId.getFile());
                byte[] fileData = fs.readFileBytes(objectPath);
                int bodyStartIndex = 0;

                for (int i = 0; i < fileData.length; i++) {
                    if (fileData[i] == 0) {
                        bodyStartIndex = i + 1;
                        break;
                    }
                }

                int bodyLength = fileData.length - bodyStartIndex;
                byte[] body = new byte[bodyLength];
                System.arraycopy(fileData, bodyStartIndex, body, 0, bodyLength);

                // TODO: handle trees as well. Currently I am just handling blobs in Index and Trees
                GitObject blob = new Blob(body);
                entries.add(new TreeEntry(objectId.getId(), String.valueOf(objectData[2]),blob.type()));
            }
        }

        Tree tree = new Tree(entries);
        ObjectId treeId = computeId(tree);

        fs.createDirectory(Path.of(".jgit/objects/" + treeId.getDir()));
        fs.createFile(Path.of(".jgit/objects/" + treeId.getDir() + "/" + treeId.getFile()));
        byte[] toWrite = concatByteArray(objectHeaderToBytes(tree), tree.serialize());
        fs.write(".jgit/objects/" + treeId.getDir() + "/" + treeId.getFile(), toWrite);

        return treeId;
    }

    @Override
    public void updateIndex(String path, String file, String hash) throws IOException {
        fs.write(ensureIndex(path).toString(), file, hash);
    }

    @Override
    public void updateIndex(String path, String data) throws IOException {
        fs.write(ensureIndex(path).toString(), data);
    }

    @Override
    public String catFile(String id) throws IOException {
        String dir = id.substring(0, 2);
        String file = id.substring(2);
        Path path = Path.of(".jgit/objects/" + dir + "/" + file);

        return fs.readFile(path);
    }

    private GitObject createGitObject(byte[] data, ObjectType type) {
        try {
            Constructor<? extends GitObject> constructor = objectTypeMap.get(type).getConstructor(byte[].class);
            return constructor.newInstance(data);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private ObjectId computeId(GitObject object) {
        byte[] combined = concatByteArray(objectHeaderToBytes(object), object.serialize());
        return new ObjectId(md.hash(combined));
    }

    private byte[] concatByteArray(byte[] header, byte[] body) {
        byte[] combined = new byte[header.length + body.length];
        ByteBuffer buffer = ByteBuffer.wrap(combined);
        buffer.put(header);
        buffer.put(body);
        return buffer.array();
    }

    private byte[] objectHeaderToBytes(GitObject objects) {
        return objects.getHeader().getBytes(StandardCharsets.UTF_8);
    }
}
