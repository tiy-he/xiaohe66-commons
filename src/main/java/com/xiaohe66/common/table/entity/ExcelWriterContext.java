package com.xiaohe66.common.table.entity;

import lombok.Getter;

import java.io.OutputStream;

/**
 * @author xiaohe
 * @time 2020.04.16 12:18
 */
public class ExcelWriterContext extends WriterContext{

    @Getter
    private OutputStream outputStream;

    public ExcelWriterContext(OutputStream outputStream) {
        this.outputStream = outputStream;
    }
}
