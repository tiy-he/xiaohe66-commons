package com.xiaohe66.gen.write;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.Writer;

/**
 * @author xiaohe
 * @since 2022.02.25 14:43
 */
@RequiredArgsConstructor
@Slf4j
public class CodeWriterWrapper extends Writer {

    protected final Writer writer;

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        writer.write(cbuf, off, len);
    }

    @Override
    public void flush() throws IOException {
        writer.flush();
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }

    @Override
    public String toString() {
        return writer.toString();
    }
}
