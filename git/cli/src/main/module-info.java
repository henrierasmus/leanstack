module git.cli {
    uses com.henrierasmus.leanstack.git.ports.ObjectStore;

    requires git.api;
    requires git.logger;
}