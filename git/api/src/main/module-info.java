module git.api {
    exports com.henrierasmus.leanstack.git.ports;
    exports com.henrierasmus.leanstack.git.usecase;
    exports com.henrierasmus.leanstack.git.domain;

    uses com.henrierasmus.leanstack.git.ports.RepoLayout;
    uses com.henrierasmus.leanstack.git.ports.ObjectStore;
    uses com.henrierasmus.leanstack.git.ports.RefStore;
}
