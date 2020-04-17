package com.xiaohe66.commons.test.excel;

import com.xiaohe66.common.table.DbHandler;
import com.xiaohe66.common.table.TableExporter;
import com.xiaohe66.common.table.entity.TableConfig;
import com.xiaohe66.common.table.entity.TableField;
import com.xiaohe66.common.table.sqlbuilder.SimpleMySqlBuilder;
import com.xiaohe66.common.table.sqlbuilder.SqlBuilder;
import com.xiaohe66.common.table.writer.SimpleTableWriter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaohe
 * @time 2020.04.16 14:11
 */
@Slf4j
public class TableExporterTest {


    private File file;

    private List<TableField> fieldList;

    SimpleMySqlBuilder sqlBuilder = SimpleMySqlBuilder.getInstance();

    DbHandler dbHandler;

    TableConfig config;


    @Before
    public void before() {


        fieldList = Arrays.asList(
                TableField.builder().tableTitle("表头1").fieldName("f1").isShow(true).build(),
                TableField.builder().tableTitle("表头2").fieldName("f2").isShow(true).build(),
                TableField.builder().tableTitle("表头3").fieldName("f3").isShow(true).build(),
                TableField.builder().tableTitle("表头4").build(),
                TableField.builder().tableTitle("表头5").fieldName("f5").isShow(true).defVal(555).build(),
                TableField.builder().tableTitle("表头6").fieldName("f6").isShow(true).build(),
                TableField.builder().tableTitle("表头7").fieldName("f7").valueCreator((row, columnArr) -> {
                    log.info("row : {}", row);
                    log.info("columnArr : {}", columnArr);
                    return "数据创建者提供的值";
                }).build()
        );

        dbHandler = new DbHandler() {
            @Override
            public void save(String sql, List<List<Object>> param) {
                log.info("sql : {}", sql);
                for (List<Object> data : param) {
                    log.info("param : {}", data);
                }
            }

            @Override
            public List<Map<String, Object>> query(String sql, Object... param) {

                log.info("sql : {}", sql);
                log.info("param : {}", param);

                Map<String, Object> map = new HashMap<>();
                map.put("f1", 1);
                map.put("f2", 2);
                map.put("f3", 3);
                map.put("f5", 5);
                map.put("f6", 6);

                return Arrays.asList(
                        map, map
                );
            }
        };

        config = TableConfig.builder()
                .fieldList(fieldList)
                .insertType(SqlBuilder.InsertType.EXIST_IGNORE)
                .tableName("testTable")
                .readQtyOnce(0)
                .sqlBuilder(sqlBuilder)
                .dbHandler(dbHandler)
                .writer(SimpleTableWriter.getInstance())
                .build();
    }


    @Test
    public void test1() {

        String json = TableExporter.getInstance().exportToJson(config, "and 1=? and 2=?", new Object[]{1, 2});
        log.info("result : {}", json);


    }
}
