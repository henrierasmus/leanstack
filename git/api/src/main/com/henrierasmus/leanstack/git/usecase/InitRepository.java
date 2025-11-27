package com.henrierasmus.leanstack.git.usecase;

import com.henrierasmus.leanstack.git.internal.Store;
import com.henrierasmus.leanstack.git.internal.StoreService;
import com.henrierasmus.leanstack.git.ports.ObjectStore;
import com.henrierasmus.leanstack.git.ports.RefStore;
import com.henrierasmus.leanstack.git.ports.RepoLayout;

import java.io.IOException;
import java.util.ServiceLoader;

/**
 * Public API that will expose orchestrated logic to create directories and files
 */
public class InitRepository {
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

        StoreService storeService = new StoreService();
        repoLayout = (RepoLayout) storeService.getStore(repoLayoutLoader, Store.DEFAULT_REPO_LAYOUT_PROVIDER.provider());
        objectStore = (ObjectStore) storeService.getStore(objectStoreLoader, Store.DEFAULT_OBJECT_STORE_PROVIDER.provider());
        refStore = (RefStore) storeService.getStore(refStoreLoader, Store.DEFAULT_REF_STORE_PROVIDER.provider());
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
    public void initRepo(String dirPath) throws IOException {
        String gitDir = dirPath + "/.jgit";
        repoLayout.ensureGitDir(gitDir);
        objectStore.ensureObjectDir(gitDir);
        refStore.ensureRefDir(gitDir);
    }

    private static class Holder {
        static final InitRepository instance = new InitRepository();
    }
}
