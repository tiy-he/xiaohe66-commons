module xiaohe.common.gen {

    exports com.xiaohe66.gen;
    exports com.xiaohe66.gen.convert;
    exports com.xiaohe66.gen.convert.impl;
    exports com.xiaohe66.gen.reader;
    exports com.xiaohe66.gen.reader.bo;
    exports com.xiaohe66.gen.reader.impl;
    exports com.xiaohe66.gen.template;
    exports com.xiaohe66.gen.template.bo;
    exports com.xiaohe66.gen.template.impl;
    exports com.xiaohe66.gen.write;
    exports com.xiaohe66.gen.write.console;
    exports com.xiaohe66.gen.write.file;

    requires static lombok;
    requires transitive velocity.engine.core;
    requires transitive org.apache.commons.lang3;
    requires transitive org.slf4j;
    requires logback.core;

    requires java.sql;
    requires java.naming;
    requires spring.jdbc;
    requires mysql.connector.java;

}