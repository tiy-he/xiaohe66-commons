package com.xiaohe66.common.table.reader;

import com.xiaohe66.common.table.entity.ReaderContext;
import com.xiaohe66.common.table.entity.TableField;
import com.xiaohe66.common.table.ex.TableImportExcelFormatException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author xiaohe
 * @time 2020.04.09 11:57
 */
@Slf4j
public class TableTitleExcelReader extends AbstractExcelReader {

    private static TableTitleExcelReader tableTitleReader = new TableTitleExcelReader();

    protected TableTitleExcelReader() {
    }

    public static TableTitleExcelReader getInstance() {
        return tableTitleReader;
    }

    @Override
    public List<List<Object>> convertDbData(ReaderContext context,
                                            List<List<Object>> sourceDataList) {

        int[] columnArr = checkAndGetIndex(context);

        if (log.isInfoEnabled()) {
            log.info("Excel文件中的表头 : {}", context.getTableTitleList());
            log.info("数据库数据所对应的下标 : {}", Arrays.toString(columnArr));
        }

        int rowQty = sourceDataList.size();
        List<List<Object>> dataList = new ArrayList<>(rowQty);

        for (List<Object> row : sourceDataList) {

            List<Object> data = doConvertDbData(row, context, columnArr);

            if (data != null) {
                dataList.add(data);
            }

        }

        return dataList;
    }

    protected List<Object> doConvertDbData(List<Object> row, ReaderContext context, int[] columnArr) {

        List<TableField> fieldList = context.getFieldList();

        List<Object> data = new ArrayList<>(columnArr.length);
        for (int i = 0; i < columnArr.length; i++) {

            TableField field = fieldList.get(i);
            if (field.getFieldName() == null) {
                continue;
            }

            int column = columnArr[i];

            Object value = null;
            if (column >= 0) {
                value = row.get(column);

            }

            if (value == null) {

                if (field.isRequire()) {
                    log.info("字段{}缺少必填项, data : {}", field.getFieldName(), row);
                    return null;

                } else if (field.getDefVal() != null) {
                    value = field.getDefVal();

                } else if (field.getValueCreator() != null) {
                    try {
                        value = field.getValueCreator().apply(context, row);
                    } catch (Exception e) {
                        log.error("数据生成器调用时发生异常, msg : {}", e.getMessage());
                        log.debug("数据生成器调用时发生异常, msg : {}", e.getMessage(), e);
                    }
                }
            }

            // todo : 正则校验

            data.add(value);
        }
        return data;
    }

    protected int[] checkAndGetIndex(ReaderContext context) {

        int[] indexArr = context.cache("indexArr");
        if (indexArr != null) {
            return indexArr;
        }

        List<TableField> fieldList = context.getFieldList();
        List<Object> tableTitleList = context.getTableTitleList();

        indexArr = new int[fieldList.size()];
        context.cache("indexArr", indexArr);

        List<String> missingTitleList = new ArrayList<>();
        List<Object> configTitleList = new ArrayList<>(fieldList.size());

        for (int i = 0, n = fieldList.size(); i < n; i++) {
            TableField field = fieldList.get(i);

            String title = field.getTableTitle();
            configTitleList.add(title);

            if (title == null) {
                indexArr[i] = -1;
                continue;
            }

            int index = indexOf(tableTitleList, title);

            if (index >= 0) {
                indexArr[i] = index;

            } else {

                // 用 -1标记未找到的表头
                indexArr[i] = -1;

                // 必填项缺失，记录缺失的表头
                if (field.isRequire()) {
                    missingTitleList.add(title);
                }
            }
        }

        if (!missingTitleList.isEmpty()) {
            String msg = missingTitleList.toString();
            log.error("表格格式错误，缺少表头 : {}", msg);
            throw new TableImportExcelFormatException(msg);
        }
        context.setConfigTitleList(configTitleList);

        return indexArr;
    }

    private int indexOf(List<Object> tableTitleList, String title) {

        for (int i = 0; i < tableTitleList.size(); i++) {

            Object tableTitle = tableTitleList.get(i);

            if (title.equals(tableTitle.toString())) {

                return i;
            }
        }
        return -1;
    }
}
