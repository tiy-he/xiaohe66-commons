package com.xiaohe66.common.table;

import com.xiaohe66.common.table.db.DbHandler;
import com.xiaohe66.common.table.entity.ImportResult;
import com.xiaohe66.common.table.entity.ReaderContext;
import com.xiaohe66.common.table.entity.TableConfig;
import com.xiaohe66.common.table.reader.TableImportReader;
import com.xiaohe66.common.table.sqlbuilder.SqlBuilder;

import java.io.File;
import java.util.List;

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

    public ImportResult importWithFile(File file, TableConfig config, Object otherParam) {

        TableImportReader reader = config.getReader();
        SqlBuilder sqlBuilder = config.getSqlBuilder();
        DbHandler dbHandler = config.getDbHandler();


//        String sql = sqlBuilder.buildInsertSql(config.getTableName(), config.getFieldList(), config.getInsertType());

        ReaderContext context = new ReaderContext(file, config.getFieldList(), config.getReadQtyOnce(), otherParam);

        ImportResult result = new ImportResult();

        reader.read(context, table -> {

            List<List<Object>> dataList = table.getDataList();

            // 执行sql输入的数量
            long executeSqlQty = result.getExecuteSqlQty();
            result.setExecuteSqlQty(executeSqlQty + dataList.size());

            // 成功执行sql的数量
            long successQty = dbHandler.importHandle(config, dataList);
            result.setSuccessQty(result.getSuccessQty() + successQty);

        });

        // 从Excel表中读取到的数据行数
        result.setExcelQty(context.getDataTotal());

        if (context.isExistError()) {
            List<String> errorReasonList = context.getErrorReasonList();
            result.setErrorReason(errorReasonList.isEmpty() ? "" : errorReasonList.toString());

        } else {
            result.setSuccess(result.getSuccessQty() == result.getExcelQty());
        }

        // todo : 存在即更新的操作怎么返回sql？
        result.setSql(null);
        return result;
    }

}
