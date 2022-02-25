package com.xiaohe66.gen.template;

import com.xiaohe66.gen.template.bo.CodeTemplateProperty;
import com.xiaohe66.gen.template.bo.JavaDefinition;

import java.io.Writer;

/**
 * @author xiaohe
 * @since 2022.02.21 17:18
 */
public interface CodeTemplate {

    void gen(JavaDefinition definition, Writer writer);

    String genFileName(JavaDefinition definition);

    String getTemplateName();

    CodeTemplateProperty getProperty();
}
