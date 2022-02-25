package com.xiaohe66.gen.write.console;

import com.xiaohe66.gen.write.CodeWriterWrapper;

import java.io.IOException;
import java.io.Writer;

/**
 * @author xiaohe
 * @since 2022.02.25 15:50
 */
public class ConsoleCodeWriterWrapper extends CodeWriterWrapper {

    public ConsoleCodeWriterWrapper(Writer writer) {
        super(writer);
    }

    @Override
    public void close() throws IOException {
        // TODO : 换成 log
        System.out.println("----------------");
        System.out.println(writer);
        super.close();
    }
}
