package com.xiaohe66.gen.write.console;

import com.xiaohe66.gen.template.CodeTemplate;
import com.xiaohe66.gen.template.bo.JavaDefinition;
import com.xiaohe66.gen.write.CodeWriterFactory;
import com.xiaohe66.gen.write.CodeWriterWrapper;
import lombok.extern.slf4j.Slf4j;

import java.io.StringWriter;

/**
 * @author xiaohe
 * @since 2022.02.25 13:54
 */
@Slf4j
public class ConsoleCodeWriteFactory implements CodeWriterFactory {

    @Override
    public CodeWriterWrapper create(CodeTemplate codeBuilder, JavaDefinition definition) {
        StringWriter stringWriter = new StringWriter();
        return new ConsoleCodeWriterWrapper(stringWriter);
    }
}
