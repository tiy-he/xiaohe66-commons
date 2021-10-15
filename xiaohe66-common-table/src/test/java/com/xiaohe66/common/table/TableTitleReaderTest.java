package com.xiaohe66.common.table;

import com.xiaohe66.common.table.db.AbstractDbHandler;
import com.xiaohe66.common.table.db.DbHandler;
import com.xiaohe66.common.table.TableImporter;
import com.xiaohe66.common.table.entity.TableConfig;
import com.xiaohe66.common.table.entity.TableField;
import com.xiaohe66.common.table.reader.TableTitleExcelReader;
import com.xiaohe66.common.table.sqlbuilder.SimpleMySqlBuilder;
import com.xiaohe66.common.table.sqlbuilder.SqlBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author xiaohe
 * @time 2020.04.10 09:49
 */
@Slf4j
public class TableTitleReaderTest {

    private TableTitleExcelReader reader = TableTitleExcelReader.getInstance();

    private String path = "com/xiaohe66/common/excel/testExcel.xlsx";

    private File file;

    private List<TableField> fieldList;

    TableImporter tableImporter = TableImporter.getInstance();

    SimpleMySqlBuilder sqlBuilder = SimpleMySqlBuilder.getInstance();

    DbHandler dbHandler;

    TableConfig config1;
    TableConfig config2;

    @Before
    public void before() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        URL resource = classLoader.getResource(path);

        file = new File(resource.getFile());

        fieldList = Arrays.asList(
                TableField.builder().tableTitle("表头1").fieldName("f1").build(),
                TableField.builder().tableTitle("表头2").fieldName("f2").build(),
                TableField.builder().tableTitle("表头3").fieldName("f3").build(),
                TableField.builder().tableTitle("表头4").build(),
                TableField.builder().tableTitle("表头5").fieldName("f5").defVal(555).build(),
                TableField.builder().tableTitle("表头6").fieldName("f6").build(),
                TableField.builder().tableTitle("表头7").fieldName("f7").valueCreator((row, columnArr) -> {
                    log.info("row : {}", row);
                    log.info("columnArr : {}", columnArr);
                    return "数据创建者提供的值";
                }).build()
        );

        dbHandler = new AbstractDbHandler() {
            @Override
            public long save(String sql, List<List<Object>> param) {
                log.info("sql : {}", sql);
                for (List<Object> data : param) {
                    log.info("param : {}", data);
                }
                return 0;
            }

            @Override
            public List<Map<String, Object>> query(String sql, Object... param) {
                return null;
            }
        };

        config1 = TableConfig.builder()
                .fieldList(fieldList)
                .insertType(SqlBuilder.InsertType.EXIST_IGNORE)
                .reader(reader)
                .tableName("testTable")
                .readQtyOnce(0)
                .sqlBuilder(sqlBuilder)
                .dbHandler(dbHandler)
                .build();

        config2 = TableConfig.builder()
                .fieldList(fieldList)
                .insertType(SqlBuilder.InsertType.EXIST_IGNORE)
                .reader(reader)
                .tableName("testTable")
                .readQtyOnce(2)
                .sqlBuilder(sqlBuilder)
                .dbHandler(dbHandler)
                .build();
    }

    @Test
    public void test1() {

        tableImporter.importWithFile(file, config1, null);

    }

    @Test
    public void test2() {

        tableImporter.importWithFile(file, config2, null);

    }
}
