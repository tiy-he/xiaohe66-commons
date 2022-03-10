package com.xiaohe66.gen;

import com.xiaohe66.gen.reader.TableDefinitionReader;
import com.xiaohe66.gen.reader.bo.TableDefinition;
import com.xiaohe66.gen.reader.impl.SqlTableDefinitionReader;
import com.xiaohe66.gen.template.CodeTemplate;
import com.xiaohe66.gen.template.bo.JavaDefinition;
import com.xiaohe66.gen.template.impl.EntityCodeTemplate;
import com.xiaohe66.gen.template.impl.MapperCodeTemplate;
import com.xiaohe66.gen.template.impl.RepositoryCodeTemplate;
import com.xiaohe66.gen.write.console.ConsoleCodeWriteFactory;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

/**
 * @author xiaohe
 * @since 2022.02.25 10:27
 */
public class SqlCodeBuilder extends AbstractCodeBuilder {

    @NonNull
    @Setter
    private TableDefinitionReader reader = new SqlTableDefinitionReader();

    public SqlCodeBuilder() {

    }

    public SqlCodeBuilder(DefaultCodeBuildProperty property) {

        List<CodeTemplate> builders = List.of(new EntityCodeTemplate(property.toEntityCodeBuildProperty()),
                new MapperCodeTemplate(property.toMapperCodeBuildProperty()),
                new RepositoryCodeTemplate(property.toRepositoryCodeBuildProperty()));

        setBuilders(builders);

        setWriterFactory(new ConsoleCodeWriteFactory());
    }

    public void build(String sql, String name) {

        TableDefinition tableDefinition = reader.read(sql);

        JavaDefinition definition = definitionConverter.convert(tableDefinition);
        definition.setClassName(uppercaseFirst(name));

        this.build(definition);
    }
}