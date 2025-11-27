package com.henrierasmus.leanstack.git.internal;

import java.util.ServiceLoader;

public class StoreService {
    public StoreService() {}

    /**
     * Helper method to get providers from ServiceLoader
     *
     * @param loader - ServiceLoader for provider
     * @param provider - Provider type name
     * @return Provider Instance
     * @param <T> Expected ServiceLoader Type
     */
    public <T> Object getStore(ServiceLoader<T> loader, String provider) {
        return loader.stream()
                .filter(p -> p.type().getName().equals(provider))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Provider not found: " + provider))
                .get();
    }
}
