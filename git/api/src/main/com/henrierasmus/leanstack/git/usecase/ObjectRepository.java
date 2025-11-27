package com.henrierasmus.leanstack.git.usecase;

import com.henrierasmus.leanstack.git.domain.ObjectId;
import com.henrierasmus.leanstack.git.domain.ObjectType;
import com.henrierasmus.leanstack.git.internal.Store;
import com.henrierasmus.leanstack.git.internal.StoreService;
import com.henrierasmus.leanstack.git.ports.ObjectStore;

import java.io.IOException;
import java.util.ServiceLoader;

public class ObjectRepository {
    private final ObjectStore objectStore;

    public ObjectRepository() {
        StoreService storeService = new StoreService();

        ServiceLoader<ObjectStore> loader = ServiceLoader.load(ObjectStore.class);
        objectStore = (ObjectStore) storeService.getStore(loader, Store.DEFAULT_OBJECT_STORE_PROVIDER.provider());
    }

    public ObjectId computeId(String filePath, ObjectType type) throws IOException {
        return objectStore.computeId(filePath, type);
    }
}
