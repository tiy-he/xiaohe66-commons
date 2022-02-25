package com.xiaohe66.gen.template.bo;

import com.xiaohe66.gen.reader.bo.TableDefinition;
import lombok.Data;

import java.util.List;

/**
 * @author xiaohe
 * @since 2022.02.24 17:45
 */
@Data
public class JavaDefinition {

    private String className;
    private TableDefinition tableDefinition;
    private List<JavaField> fields;

}
