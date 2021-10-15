package com.xiaohe66.common.table;

import com.xiaohe66.common.table.TableImporter;
import com.xiaohe66.common.table.db.AbstractDbHandler;
import com.xiaohe66.common.table.db.DbHandler;
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
 * @time 2020.04.09 15:21
 */
@Slf4j
public class ExcelImportTest {

    private TableTitleExcelReader reader = TableTitleExcelReader.getInstance();

    private TableImporter importer = TableImporter.getInstance();

    private DbHandler dbHandler = new AbstractDbHandler() {

        int count = 0;

        @Override
        public long save(String sql, List<List<Object>> param) {
            log.info("param : {}", param);
            return 0;
        }

        @Override
        public long saveOne(String sql, List<Object> param) {
            log.info("save one : {}, data : {}", sql, param);
            return 0;
        }

        @Override
        public long update(String sql, List<Object> param) {
            log.info("update: {}, data : {}", sql, param);
            return 0;
        }

        @Override
        public boolean checkExist(String sql, List<Object> param) {
            log.info("check : {}, data : {}", sql, param);

            return count++ % 2 == 0;
        }

        @Override
        public List<Map<String, Object>> query(String sql, Object... param) {
            return null;
        }
    };

    private String path = "com/xiaohe66/common/excel/testExcel.xlsx";

    private File file;

    private TableConfig config;

    @Before
    public void before() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        URL resource = classLoader.getResource(path);

        file = new File(resource.getFile());

        List<TableField> fieldList = Arrays.asList(
                TableField.builder().tableTitle("表头1").fieldName("f1").isUnique(true).build(),
                TableField.builder().tableTitle("表头2").fieldName("f2").build(),
                TableField.builder().tableTitle("表头3").fieldName("f3").build(),
                TableField.builder().tableTitle("表头4").build(),
                TableField.builder().tableTitle("表头5").fieldName("f5").defVal(555).build(),
                TableField.builder().tableTitle("表头6").fieldName("f6").isUnique(true).build(),
                TableField.builder().tableTitle("表头7").fieldName("f7").valueCreator((row, columnArr) -> {
                    log.info("row : {}", row);
                    log.info("columnArr : {}", columnArr);
                    return "数据创建者提供的值";
                }).build()
        );

        config = TableConfig.builder()
                .dbHandler(dbHandler)
                .fieldList(fieldList)
                .insertType(SqlBuilder.InsertType.EXIST_UPDATE_SELECT)
                .reader(reader)
                .sqlBuilder(SimpleMySqlBuilder.getInstance())
                .tableName("testTable")
                .build();
    }

    @Test
    public void test() {
        importer.importWithFile(file, config, "otherParam");
    }

    @Test
    public void testSqlBuilder() {

    }
}
