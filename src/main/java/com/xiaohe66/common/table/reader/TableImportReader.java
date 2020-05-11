package com.xiaohe66.common.table.reader;

import com.xiaohe66.common.table.entity.ReaderContext;
import com.xiaohe66.common.table.entity.Table;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * @author xiaohe
 * @time 2020.04.08 17:58
 */
public interface TableImportReader {

    /**
     * 读取数据
     *
     * @param context  读取器上下文
     * @param consumer 读取成功后的回调，可能读取全表后回调，也可能读取一部分后回调，即可能会回调多次
     * @throws IOException IOException
     */
    void read(ReaderContext context, Consumer<Table> consumer);

}
