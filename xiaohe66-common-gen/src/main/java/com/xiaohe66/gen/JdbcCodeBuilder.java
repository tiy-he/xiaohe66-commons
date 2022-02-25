package com.xiaohe66.gen;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.xiaohe66.gen.reader.bo.TableDefinition;
import com.xiaohe66.gen.reader.impl.JdbcTableDefinitionReader;
import com.xiaohe66.gen.template.CodeTemplate;
import com.xiaohe66.gen.template.bo.JavaDefinition;
import com.xiaohe66.gen.template.impl.EntityCodeTemplate;
import com.xiaohe66.gen.template.impl.MapperCodeTemplate;
import com.xiaohe66.gen.template.impl.RepositoryCodeTemplate;
import com.xiaohe66.gen.write.console.ConsoleCodeWriteFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author xiaohe
 * @since 2022.02.25 16:02
 */
public class JdbcCodeBuilder extends AbstractCodeBuilder {

    private final JdbcTableDefinitionReader reader;

    public JdbcCodeBuilder(DefaultCodeBuildProperty property, DataSourceProperty dataSourceProperty) {

        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL(dataSourceProperty.getUrl());
        dataSource.setUser(dataSourceProperty.getUsername());
        dataSource.setPassword(dataSourceProperty.getPassword());

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        this.reader = new JdbcTableDefinitionReader(jdbcTemplate);

        List<CodeTemplate> builders = List.of(new EntityCodeTemplate(property.toEntityCodeBuildProperty()),
                new MapperCodeTemplate(property.toMapperCodeBuildProperty()),
                new RepositoryCodeTemplate(property.toRepositoryCodeBuildProperty()));

        setBuilders(builders);

        setWriterFactory(new ConsoleCodeWriteFactory());
    }

    public void build(String tableName, String name) {

        TableDefinition tableDefinition = reader.read(tableName);

        JavaDefinition definition = definitionConverter.convert(tableDefinition);
        definition.setClassName(name);

        this.build(definition);
    }
}
