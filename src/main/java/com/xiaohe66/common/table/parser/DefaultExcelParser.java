package com.xiaohe66.common.table.parser;

import com.xiaohe66.common.table.entity.ParserContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xiaohe
 * @time 2020.04.13 12:36
 */
public class DefaultExcelParser extends AbstractTableParser {

    private static final FastDateFormat dateFormat = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

    private ParserContext context = new ParserContext();

    @Override
    public void parse(File file, TableParseCallback callback) {
        try (Workbook workbook = WorkbookFactory.create(file)) {
            Sheet sheet = workbook.getSheetAt(0);
            int rowQty = sheet.getPhysicalNumberOfRows();

            Row titleRow = sheet.getRow(0);

            int cellQty = titleRow.getPhysicalNumberOfCells();

            context.setSheetName(sheet.getSheetName());

            Row row;
            List<Object> data;
            // callback row
            for (int i = 0; i < rowQty; i++) {

                row = sheet.getRow(i);

                data = readRow(row, cellQty);

                context.setCurrentRowIndex(i);
                callback.onNextRow(context, data);
            }

            // callback end
            callback.onEnd(context);

        } catch (IOException e) {
            throw new TableParserException(e);
        }
    }

    protected List<List<Object>> readTable(Sheet sheet, int rowStart, int rowEnd, int cellQty) {
        List<List<Object>> dataList = new ArrayList<>(rowEnd - rowStart);
        for (int i = rowStart; i < rowEnd; i++) {

            Row row = sheet.getRow(i);

            List<Object> data = readRow(row, cellQty);
            dataList.add(data);
        }
        return dataList;
    }

    protected List<Object> readRow(Row row, int cellQty) {

        List<Object> data = new ArrayList<>(cellQty);

        for (int i = 0; i < cellQty; i++) {

            Cell cell = row.getCell(i);

            data.add(readCell(cell));
        }

        return data;
    }

    protected Object readCell(Cell cell) {
        if (cell == null) {
            return null;
        }

        CellType cellType = cell.getCellType();

        if (cellType == CellType.STRING) {
            return StringUtils.trim(cell.getStringCellValue());
        }

        if (cell.getCellType() == CellType.NUMERIC) {

            String dataFormatString = cell.getCellStyle().getDataFormatString();
            if (!"General".equals(dataFormatString)) {
                Date date = cell.getDateCellValue();
                if (date != null) {
                    // todo : 日期转换格式问题
                    return dateFormat.format(date);
                }

            } else {
                return convertIntPossible(cell.getNumericCellValue());
            }
        }

        if (cell.getCellType() == CellType.FORMULA) {

            try {
                return convertIntPossible(cell.getNumericCellValue());

            } catch (IllegalStateException e) {
                return String.valueOf(cell.getRichStringCellValue());
            }
        }

        if (cell.getCellType() == CellType.BOOLEAN) {

            return cell.getBooleanCellValue();
        }

        return null;

    }

    protected Object readCell(Row row, int column) {

        return readCell(row.getCell(column));
    }
}
