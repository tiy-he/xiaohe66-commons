module xiaohe.common.api {

    exports com.xiaohe66.common.api;
    exports com.xiaohe66.common.api.okhttp;
    exports com.xiaohe66.common.api.restful;

    requires static lombok;
    requires transitive xiaohe.common.base;

    requires transitive okhttp3;

}