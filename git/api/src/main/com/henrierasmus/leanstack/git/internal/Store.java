package com.henrierasmus.leanstack.git.internal;

public enum Store {
    DEFAULT_REPO_LAYOUT_PROVIDER("com.henrierasmus.leanstack.git.RepoLayoutImpl"),
    DEFAULT_OBJECT_STORE_PROVIDER("com.henrierasmus.leanstack.git.ObjectStoreImpl"),
    DEFAULT_REF_STORE_PROVIDER("com.henrierasmus.leanstack.git.RefStoreImpl");

    private final String provider;

    Store(String provider) {
        this.provider = provider;
    }

    public String provider() {
        return provider;
    }
}
