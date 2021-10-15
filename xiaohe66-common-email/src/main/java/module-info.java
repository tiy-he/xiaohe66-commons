module xiaohe.common.email {

    exports com.xiaohe66.common.email.entity;
    exports com.xiaohe66.common.email.ex;
    exports com.xiaohe66.common.email.service;
    exports com.xiaohe66.common.email.util;

    requires transitive xiaohe.common.base;

    requires static lombok;
    requires transitive mail;
    requires transitive activation;
    requires transitive java.datatransfer;

}