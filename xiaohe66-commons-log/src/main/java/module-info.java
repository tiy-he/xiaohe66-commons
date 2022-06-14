module xiaohe.commons.log {

    opens com.xiaohe66.commons.log;
    opens com.xiaohe66.commons.log.annotation;
    opens com.xiaohe66.commons.log.context;
    opens com.xiaohe66.commons.log.context.impl;
    opens com.xiaohe66.commons.log.tool;
    opens com.xiaohe66.commons.log.tool.impl;

    exports com.xiaohe66.commons.log.annotation;
    exports com.xiaohe66.commons.log.context;
    exports com.xiaohe66.commons.log.tool;

    requires org.aspectj.weaver;
    requires org.apache.commons.lang3;
    requires xiaohe.common.base;

    requires transitive org.slf4j;

    requires spring.context;
    requires spring.beans;
    requires spring.boot.autoconfigure;
    requires spring.expression;
    requires spring.core;
    requires spring.aop;

    requires static lombok;
}
