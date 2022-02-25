package com.xiaohe66.gen.write.file;

import com.xiaohe66.gen.template.CodeTemplate;
import com.xiaohe66.gen.template.bo.CodeTemplateProperty;
import com.xiaohe66.gen.template.bo.JavaDefinition;
import com.xiaohe66.gen.write.CodeWriterFactory;
import com.xiaohe66.gen.write.CodeWriterWrapper;
import com.xiaohe66.gen.write.console.ConsoleCodeWriterWrapper;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author xiaohe
 * @since 2022.02.25 14:23
 */
@RequiredArgsConstructor
public class FileCodeWriteFactory implements CodeWriterFactory {

    private final String basePath;

    @Override
    public CodeWriterWrapper create(CodeTemplate codeBuilder, JavaDefinition definition) {

        CodeTemplateProperty property = codeBuilder.getProperty();
        String packageName = property.getClassPackage().replace(".", File.separator);

        String fileName = codeBuilder.genFileName(definition);

        String fullPath = basePath + File.separator + packageName + File.separator + fileName;

        File file = new File(fullPath);

        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }

        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fileWriter = new FileWriter(file);
            return new FileCodeWriterWrapper(fileWriter);

        } catch (IOException e) {
            // TODO : replace this ex
            throw new RuntimeException(e);
        }
    }
}
