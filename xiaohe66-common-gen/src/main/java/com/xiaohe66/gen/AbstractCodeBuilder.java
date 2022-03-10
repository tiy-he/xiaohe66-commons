package com.xiaohe66.gen;

import com.xiaohe66.gen.convert.DefinitionConverter;
import com.xiaohe66.gen.convert.impl.SimpleDefinitionConverter;
import com.xiaohe66.gen.template.CodeTemplate;
import com.xiaohe66.gen.template.bo.JavaDefinition;
import com.xiaohe66.gen.write.CodeWriterFactory;
import com.xiaohe66.gen.write.CodeWriterWrapper;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

/**
 * @author xiaohe
 * @since 2022.02.25 10:31
 */
@Getter
@Setter
@Slf4j
public abstract class AbstractCodeBuilder {

    protected List<CodeTemplate> builders;
    protected CodeWriterFactory writerFactory;

    @NonNull
    @Setter
    @Getter
    protected DefinitionConverter definitionConverter = new SimpleDefinitionConverter();

    public void build(JavaDefinition definition) {

        for (CodeTemplate builder : builders) {

            try (CodeWriterWrapper write = writerFactory.create(builder, definition)) {

                builder.gen(definition, write);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected String uppercaseFirst(String name) {

        char c = name.charAt(0);
        char c1 = Character.toUpperCase(c);
        if (c1 == c) {
            return name;
        }

        return c1 + name.substring(1);
    }
}
