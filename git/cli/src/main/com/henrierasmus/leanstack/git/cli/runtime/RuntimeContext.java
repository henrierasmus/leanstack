package com.henrierasmus.leanstack.git.cli.runtime;

import com.henrierasmus.leanstack.git.ports.ObjectStore;
import com.henrierasmus.leanstack.git.usecase.InitRepository;

public class RuntimeContext {
    private final String path;
    private final ObjectStore objectStore;
    private final InitRepository initRepo;

    public RuntimeContext(String path, ObjectStore objectStore, InitRepository initRepo) {
        this.path = path;
        this.objectStore = objectStore;
        this.initRepo = initRepo;
    }

    public String path() {
        return path;
    }

    public ObjectStore objectStore() {
        return objectStore;
    }

    public InitRepository initRepo() {
        return initRepo;
    }
}
