module git.fs {
    requires git.api;

    provides com.henrierasmus.leanstack.git.ports.RepoLayout with com.henrierasmus.leanstack.git.RepoLayoutImpl;
    provides com.henrierasmus.leanstack.git.ports.ObjectStore with com.henrierasmus.leanstack.git.ObjectStoreImpl;
    provides com.henrierasmus.leanstack.git.ports.RefStore with com.henrierasmus.leanstack.git.RefStoreImpl;
}
