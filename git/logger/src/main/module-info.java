module git.logger {
    exports com.henrierasmus.leanstack.logger;
    exports com.henrierasmus.leanstack.logger.internal;
    provides com.henrierasmus.leanstack.logger.LoggerFactory with com.henrierasmus.leanstack.logger.internal.LoggerFactoryImpl;
}
