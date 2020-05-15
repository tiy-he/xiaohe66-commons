package com.xiaohe66.common.table.sqlbuilder;

import com.xiaohe66.common.table.entity.TableField;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class SimpleMySqlBuilderTest {

    private SimpleMySqlBuilder builder = new SimpleMySqlBuilder();

    private List<TableField> fieldList = Arrays.asList(
            TableField.builder().tableTitle("表头1").fieldName("f1").isUnique(true).build(),
            TableField.builder().tableTitle("表头2").fieldName("f2").build(),
            TableField.builder().tableTitle("表头3").fieldName("f3").isUnique(true).build(),
            TableField.builder().tableTitle("表头4").build(),
            TableField.builder().tableTitle("表头5").fieldName("f5").defVal(555).build(),
            TableField.builder().tableTitle("表头6").fieldName("f6").build(),
            TableField.builder().tableTitle("表头7").fieldName("f7").valueCreator((row, columnArr) -> {
                log.info("row : {}", row);
                log.info("columnArr : {}", columnArr);
                return "数据创建者提供的值";
            }).build()
    );


    @Test
    public void test1() {
        String sql = builder.checkExistSql("testTable", fieldList);
        log.info("sql : {}", sql);
    }
}