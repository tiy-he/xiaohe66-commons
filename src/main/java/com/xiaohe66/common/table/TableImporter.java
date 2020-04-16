package com.xiaohe66.common.table;

import com.xiaohe66.common.table.entity.ReaderContext;
import com.xiaohe66.common.table.entity.TableConfig;
import com.xiaohe66.common.table.ex.TableImportException;
import com.xiaohe66.common.table.reader.TableImportReader;
import com.xiaohe66.common.table.sqlbuilder.SqlBuilder;

import java.io.File;
import java.io.IOException;

/**
 * @author xiaohe
 * @time 2020.04.09 11:32
 */
public class TableImporter {

    private static TableImporter tableImporter = new TableImporter();

    private TableImporter() {
    }

    public static TableImporter getInstance() {
        return tableImporter;
    }

    public void importWithFile(File file, TableConfig config) {
        importWithFile(file, config, null);
    }

    public void importWithFile(File file, TableConfig config, Object otherParam) {

        TableImportReader reader = config.getReader();
        SqlBuilder sqlBuilder = config.getSqlBuilder();
        DbHandler dbHandler = config.getDbHandler();

        try {
            String sql = sqlBuilder.buildInsertSql(config.getTableName(), config.getFieldList(), config.getInsertType());

            ReaderContext context = new ReaderContext(file, config.getFieldList(), config.getReadQtyOnce(), otherParam);

            reader.read(context, table ->
                    dbHandler.save(sql, table.getDataList()));

        } catch (IOException e) {
            throw new TableImportException(e);
        }
    }

}
