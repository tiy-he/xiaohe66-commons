package com.xiaohe66.common.table;

import com.xiaohe66.common.table.entity.ExcelWriterContext;
import com.xiaohe66.common.table.entity.SimpleWriterContext;
import com.xiaohe66.common.table.entity.TableConfig;
import com.xiaohe66.common.table.entity.TableField;
import com.xiaohe66.common.table.sqlbuilder.SqlBuilder;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * @author xiaohe
 * @time 2020.04.16 10:37
 */
public class TableExporter {

    private static TableExporter tableExporter = new TableExporter();

    private TableExporter() {
    }

    public static TableExporter getInstance() {
        return tableExporter;
    }


    public String exportToJson(TableConfig config, String where, Object[] whereParam) {

        List<Map<String, Object>> dataList = queryDataList(config, where, whereParam);

        SimpleWriterContext writerContext = new SimpleWriterContext();

        config.getWriter().writer(writerContext, config, dataList);

        return writerContext.getData().toString();
    }

    /**
     * @param where      where条件,以and开头。limit和order by 也可以写在这里。例： and a = 1 and a = 2
     * @param whereParam where条件中?对应的参数，若没有，则传入null
     */
    public void exportToExcelStream(TableConfig config, String where, Object[] whereParam, OutputStream outputStream) {

        List<Map<String, Object>> dataList = queryDataList(config, where, whereParam);

        ExcelWriterContext context = new ExcelWriterContext(outputStream);

        config.getWriter().writer(context, config, dataList);
    }

    /**
     * @param where      where条件,以and开头。limit和order by 也可以写在这里。例： and a = 1 and a = 2
     * @param whereParam where条件中?对应的参数，若没有，则传入null
     */
    private List<Map<String, Object>> queryDataList(TableConfig config, String where, Object[] whereParam) {
        List<TableField> fieldList = config.getFieldList();
        SqlBuilder sqlBuilder = config.getSqlBuilder();
        DbHandler dbHandler = config.getDbHandler();

        String sql = sqlBuilder.buildSelectSql(config.getTableName(), fieldList, where);

        return dbHandler.query(sql, whereParam);
    }
}
