package com.henrierasmus.leanstack.git;

import com.henrierasmus.leanstack.git.domain.*;
import com.henrierasmus.leanstack.git.fs.internal.FileSystemService;
import com.henrierasmus.leanstack.git.fs.internal.MessageDigestService;
import com.henrierasmus.leanstack.git.ports.ObjectStore;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

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
    public ObjectId computeId(String file, ObjectType type) throws IOException {
        GitObject gitObject = createGitObject(Files.readAllBytes(Path.of(file)), type);
        byte[] combined = concatByteArray(objectHeaderToBytes(gitObject), gitObject.serialize());
        return new ObjectId(md.toHex(md.hash(combined)));
    }

    public ObjectId computeId(GitObject object) {
        byte[] combined = concatByteArray(objectHeaderToBytes(object), object.serialize());
        return new ObjectId(md.toHex(md.hash(combined)));
    }

    @Override
    public ObjectId storeObject(String file, ObjectType type) throws IOException {
        GitObject object = createGitObject(Files.readAllBytes(Path.of(file)), type);
        ObjectId objectId = computeId(object);
        String dir = ".jgit/objects/" + objectId.getHex().substring(0, 2);
        String gitFile = dir + "/" + objectId.getHex().substring(2);
        Path filePath = Path.of(gitFile);

        fs.createDirectory(Path.of(dir));
        fs.createFile(filePath);
        byte[] toWrite = concatByteArray(objectHeaderToBytes(object), object.serialize());
        fs.write(filePath.toString(), toWrite);

        return objectId;
    }

    public void updateIndex(String path, String file, String hash) throws IOException {
        fs.write(ensureIndex(path).toString(), file, hash);
    }

    @Override
    public String catFile(String id) throws IOException {
        String dir = id.substring(0, 2);
        String file = id.substring(2);
        Path path = Path.of(".jgit/objects/" + dir + "/" + file);

        return fs.readFile(path);
    }

    private byte[] concatByteArray(byte[] header, byte[] body) {
        byte[] combined = new byte[header.length + body.length];
        ByteBuffer buffer = ByteBuffer.wrap(combined);
        buffer.put(header);
        buffer.put(body);
        return buffer.array();
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

    private byte[] objectHeaderToBytes(GitObject objects) {
        return objects.getHeader().getBytes(StandardCharsets.UTF_8);
    }
}
