module xiaohe.common.web.boot.starter {

    opens com.xiaohe66.common.web;
    opens com.xiaohe66.common.web.mybatis.base;
    opens com.xiaohe66.common.web.mybatis.injector;
    opens com.xiaohe66.common.web.request.repeatable;
    opens com.xiaohe66.common.web.resolver;
    opens com.xiaohe66.common.web.sec;
    opens com.xiaohe66.common.web.sec.config;
    opens com.xiaohe66.common.web.sign;
    opens com.xiaohe66.common.web.sign.config;

    exports com.xiaohe66.common.web;
    exports com.xiaohe66.common.web.base;
    exports com.xiaohe66.common.web.mybatis.base;
    exports com.xiaohe66.common.web.mybatis.injector;
    exports com.xiaohe66.common.web.request.repeatable;
    exports com.xiaohe66.common.web.sign;
    exports com.xiaohe66.common.web.resolver;
    exports com.xiaohe66.common.web.sec;
    exports com.xiaohe66.common.web.sec.annotation;
    exports com.xiaohe66.common.web.sign.config;

    requires static lombok;
    requires static org.apache.tomcat.embed.core;

    requires transitive xiaohe.common.base;
    requires transitive org.apache.commons.io;
    requires transitive org.aspectj.weaver;

    requires transitive spring.beans;
    requires transitive spring.boot;
    requires transitive spring.boot.autoconfigure;
    requires transitive spring.core;
    requires transitive spring.context;
    requires transitive spring.web;
    requires transitive spring.webmvc;
    requires transitive java.validation;

    requires transitive mybatis.plus.core;
    requires transitive org.mybatis;
    requires mybatis.plus.extension;
    requires mybatis.plus.annotation;

}