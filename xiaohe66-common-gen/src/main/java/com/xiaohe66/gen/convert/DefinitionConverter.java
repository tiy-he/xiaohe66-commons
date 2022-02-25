package com.xiaohe66.gen.convert;

import com.xiaohe66.gen.reader.bo.TableDefinition;
import com.xiaohe66.gen.template.bo.JavaDefinition;

import java.util.List;

/**
 * @author xiaohe
 * @since 2022.02.24 18:22
 */
public interface DefinitionConverter {

    void setIgnoreFieldName(List<String> ignoreFieldName);

    void setFieldNameConverter(FieldNameConverter fieldNameConverter);

    JavaDefinition convert(TableDefinition tableDefinition);

}
