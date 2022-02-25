package com.xiaohe66.gen.write;

import com.xiaohe66.gen.template.CodeTemplate;
import com.xiaohe66.gen.template.bo.JavaDefinition;

/**
 * @author xiaohe
 * @since 2022.02.25 10:52
 */
public interface CodeWriterFactory {

    CodeWriterWrapper create(CodeTemplate codeBuilder, JavaDefinition definition);

}
