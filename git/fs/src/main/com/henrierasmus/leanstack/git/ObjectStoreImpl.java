package com.henrierasmus.leanstack.git;

import com.henrierasmus.leanstack.git.domain.*;
import com.henrierasmus.leanstack.git.fs.internal.FileSystemService;
import com.henrierasmus.leanstack.git.fs.internal.MessageDigestService;
import com.henrierasmus.leanstack.git.domain.Node;
import com.henrierasmus.leanstack.git.ports.ObjectStore;

import java.io.BufferedReader;
import java.io.File;
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

    // TODO: I need to handle files that already exists in some way
    @Override
    public ObjectId storeObject(String file, ObjectType type) throws IOException {
        GitObject object = createGitObject(Files.readAllBytes(Path.of(file)), type);
        ObjectId objectId = computeId(object);
        String dir = ".jgit/objects/" + objectId.getDir();
        String gitFile = dir + "/" + objectId.getFile();

        byte[] toWrite = concatByteArray(objectHeaderToBytes(object), object.serialize());
        createFileAndWrite(dir, gitFile, toWrite);

        return objectId;
    }

    @Override
    public ObjectId writeTree() throws IOException, IndexOutOfBoundsException {
        Path indexPath = Path.of(".jgit/index");
        List<TreeEntry> entries = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(indexPath.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] objectData = line.split(" ", 3);
                System.out.println(objectData[0]);
                ObjectId objectId = new ObjectId(objectData[0]);

                Path objectPath = Path.of(".jgit/objects/" + objectId.getDir() + "/" + objectId.getFile());
                byte[] fileData = fs.readFileBytes(objectPath);
                int bodyStartIndex = findIndexOf(fileData, (byte) 0) + 1;
                int typeEndIndex = findIndexOf(fileData, (byte) ' ');

                byte[] body = new byte[fileData.length - bodyStartIndex];
                byte[] typeAsBytes = new byte[typeEndIndex + 1];
                System.arraycopy(fileData, bodyStartIndex, body, 0, body.length);
                System.arraycopy(fileData, 0, typeAsBytes, 0, typeAsBytes.length);

                ObjectType type = ObjectType.getTypeByName(new String(typeAsBytes));
                if (type == null) throw new IllegalStateException("Object Type not found: " + new String(typeAsBytes));

                GitObject object = switch (type) {
                    case BLOB -> new Blob(body);
                    case TREE -> new Tree(body);
                    case COMMIT -> null;
                };

                if (object == null)
                    throw new IllegalStateException("Object is null, an object must be present in the current state");

                entries.add(new TreeEntry(objectId.getId(), String.valueOf(objectData[1]), object.type()));
            }
        }

        Tree tree = new Tree(entries);
        ObjectId treeId = computeId(tree);

        byte[] toWrite = concatByteArray(objectHeaderToBytes(tree), tree.serialize());

        createFileAndWrite(
                ".jgit/objects/" + treeId.getDir(),
                ".jgit/objects/" + treeId.getDir() + "/" + treeId.getFile(),
                toWrite
        );

        fs.cleanFile(".jgit/index");
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

    @Override
    public boolean validateObjectType(String objectId, ObjectType objectType) throws IOException {
        GitObject object = getObject(objectId);

        if (object == null) throw new IllegalArgumentException("Object not found when validating type");

        return object.type() == objectType;
    }

    @Override
    public ObjectId commitTree(String treeId, String message) throws IOException {
        Commit commit = new Commit(treeId.getBytes(StandardCharsets.UTF_8), message.getBytes(StandardCharsets.UTF_8));
        ObjectId objectId = computeId(commit);
        byte[] toWrite = concatByteArray(commit.getHeader().getBytes(StandardCharsets.UTF_8), commit.serialize());
        fs.write(objectId.getDir() + "/" + objectId.getFile(), toWrite);
        return objectId;
    }

    @Override
    public Node getNodes(String path, Integer iteration) {
        Node root = new Node(null, new File(path), new ArrayList<Node>());
        if (!root.getFile().isDirectory()) throw new IllegalArgumentException("Path does not point to a directory");
        File[] files = root.getFile().listFiles();
        if (files == null) return null;
        createNodes(files, root);
        return root;
    }

    private void createNodes(File[] files, Node parent) {
        if (files == null) return;

        for (File file : files) {
//            if (file.getName().equals(".jgit")) continue;

            if (file.isDirectory()) {
                Node node = new Node(parent, file, new ArrayList<Node>());
                parent.getChildren().add(node);
                createNodes(file.listFiles(), node);
                continue;
            }

            parent.getChildren().add(new Node(parent, file, null));
        }
    }

    private GitObject getObject(String hash) throws IOException {
        ObjectId objectId = new ObjectId(hash);
        byte[] file = fs.readFileBytes(Path.of(".jgit/objects/" + objectId.getDir() + "/" + objectId.getFile()));

        int headerSize = getHeaderSize(file);
        byte[] headerBytes = new byte[headerSize];
        System.arraycopy(file, 0, headerBytes, 0, headerSize);

        byte[] body = new byte[file.length - headerSize];
        System.arraycopy(file, headerSize, body, 0, file.length - headerSize);

        String header = new String(headerBytes, StandardCharsets.UTF_8);
        String objectType = header.split(" ", 2)[0];

        if (Objects.equals(objectType, "tree")) return new Tree(body);

        if (Objects.equals(objectType, "blob")) return new Blob(body);

        return null;
    }

    // TODO: This can change
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

    private String getObjectHeader(byte[] data) {
        int headerIndex = 0;

        for (int i = 0; i < data.length; i++) {
            if (data[i] == '\0') {
                headerIndex = i;
            }
        }

        if (headerIndex == 0) {
            throw new IllegalArgumentException("No header found for provided object");
        }

        byte[] headerData = new byte[headerIndex + 1];
        System.arraycopy(data, 0, headerData, headerIndex, headerIndex + 1);

        return new String(headerData, StandardCharsets.UTF_8);
    }

    private int getHeaderSize(byte[] data) {
        int headerSize = 0;

        for (int i = 0; i < data.length; i++) {
            if (data[i] == '\0') {
                headerSize = i + 1;
                break;
            }
        }

        return headerSize;
    }

    private void createFileAndWrite(String dirPath, String filePath, byte[] data) throws IOException {
        fs.createDirectory(Path.of(dirPath));
        fs.createFile(Path.of(filePath));
        fs.write(filePath, data);
    }

    private int findIndexOf(byte[] a, byte c) {
        int result = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] == c) {
                result = i;
                break;
            }
        }

        return result;
    }
}
