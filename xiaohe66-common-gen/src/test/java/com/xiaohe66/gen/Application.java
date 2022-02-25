package com.xiaohe66.gen;

import com.xiaohe66.gen.base.BaseEntity;
import com.xiaohe66.gen.base.BaseRepository;
import com.xiaohe66.gen.base.IBaseMapper;
import com.xiaohe66.gen.write.file.FileCodeWriteFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

/**
 * @author xiaohe
 * @since 2022.02.22 12:10
 */
@Slf4j
public class Application {

    private static DataSourceProperty getDataSourceProperty() {

        DataSourceProperty property = new DataSourceProperty();
        property.setUrl("jdbc:mysql://localhost:3306/test?characterEncoding=UTF-8&useUnicode=true&autoReconnect=true&useSSL=false&serverTimezone=GMT");
        property.setUsername("root");
        property.setPassword("root");

        return property;
    }

    public static DefaultCodeBuildProperty getDefaultCodeBuildProperty() {

        return DefaultCodeBuildProperty.builder()
                .entityPackage("com.xiaohe66.gen.test.entity")
                .entityBaseClass(BaseEntity.class)
                .mapperPackage("com.xiaohe66.gen.test.mapper")
                .mapperBaseClass(IBaseMapper.class)
                .repositoryPackage("com.xiaohe66.gen.test.repository")
                .repositoryBaseClass(BaseRepository.class)
                .build();

    }

    public static void buildWithSql() throws IOException {

        SqlCodeBuilder builder = new SqlCodeBuilder(getDefaultCodeBuildProperty());

        // 加上这个就是输出文件，不加就是输出控制台
        // String path = new File("").getCanonicalPath() + "\\src\\test\\java";
        // builder.setWriterFactory(new FileCodeWriteFactory(path));

        builder.build("CREATE TABLE `user_join_b_h` (\n" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `user_name` varchar(16) NOT NULL,\n" +
                "  `sex` varchar(16) NOT NULL,\n" +
                "  `position` varchar(16) NOT NULL DEFAULT '',\n" +
                "  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  KEY `idx_name_sex_position` (`user_name`) USING BTREE\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=110006 DEFAULT CHARSET=utf8;", "User");
    }

    public static void buildWithDataSource() throws IOException {

        JdbcCodeBuilder builder = new JdbcCodeBuilder(getDefaultCodeBuildProperty(), getDataSourceProperty());

        // 加上这个就是输出文件，不加就是输出控制台
        // String path = new File("").getCanonicalPath() + "\\src\\test\\java";
        // builder.setWriterFactory(new FileCodeWriteFactory(path));

        builder.build("user_join_b_h", "User");
    }

    public static void main(String[] args) throws IOException {

        buildWithSql();
        // buildWithDataSource();
    }

}
