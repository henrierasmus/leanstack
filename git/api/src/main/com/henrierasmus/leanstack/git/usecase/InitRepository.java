package com.henrierasmus.leanstack.git.usecase;

import com.henrierasmus.leanstack.git.ports.ObjectStore;
import com.henrierasmus.leanstack.git.ports.RefStore;
import com.henrierasmus.leanstack.git.ports.RepoLayout;

import java.util.ServiceLoader;

/**
 * Public API that will expose orchestrated logic to create directories and files
 */
public class InitRepository {
    private final String DEFAULT_REPO_LAYOUT_PROVIDER = "com.henrierasmus.leanstack.git.RepoLayoutImpl";
    private final String DEFAULT_OBJECT_STORE_PROVIDER = "com.henrierasmus.leanstack.git.ObjectStoreImpl";
    private final String DEFAULT_REF_STORE_PROVIDER = "com.henrierasmus.leanstack.git.RefStoreImpl";

    private final RepoLayout repoLayout;
    private final ObjectStore objectStore;
    private final RefStore refStore;

    /**
     * Constructor for InitRepository.
     */
    private InitRepository() {
        ServiceLoader<RepoLayout> repoLayoutLoader = ServiceLoader.load(RepoLayout.class);
        ServiceLoader<ObjectStore> objectStoreLoader = ServiceLoader.load(ObjectStore.class);
        ServiceLoader<RefStore> refStoreLoader = ServiceLoader.load(RefStore.class);

        repoLayout = (RepoLayout) getStore(repoLayoutLoader, DEFAULT_REPO_LAYOUT_PROVIDER);
        objectStore = (ObjectStore) getStore(objectStoreLoader, DEFAULT_OBJECT_STORE_PROVIDER);
        refStore = (RefStore) getStore(refStoreLoader, DEFAULT_REF_STORE_PROVIDER);
    }

    /**
     * Initialize and return an instance of {@link InitRepository}
     *
     * @return {@link InitRepository}
     */
    public static InitRepository getInstance() {
        return Holder.instance;
    }

    /**
     * Initialize jgit dir where all object files will be stored
     *
     * @param dirPath - Path to the directory jgit is going to be initialized in
     */
    public void initRepo(String dirPath) {
        String gitDir = dirPath + "/.jgit";
        repoLayout.ensureGitDir(gitDir);
        objectStore.ensureObjectDir(gitDir);
        refStore.ensureRefDir(gitDir);
    }

    private static class Holder {
        static final InitRepository instance = new InitRepository();
    }

    /**
     * Helper method to get providers from ServiceLoader
     *
     * @param loader - ServiceLoader for provider
     * @param provider - Provider type name
     * @return Provider Instance
     * @param <T> Expected ServiceLoader Type
     */
    private <T> Object getStore(ServiceLoader<T> loader, String provider) {
        return loader.stream()
                .filter(p -> p.type().getName().equals(provider))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Provider not found: " + provider))
                .get();
    }
}
