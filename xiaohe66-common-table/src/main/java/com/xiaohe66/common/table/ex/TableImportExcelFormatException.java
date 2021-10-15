package com.xiaohe66.common.table.ex;

/**
 * 导入的表格式错误
 *
 * @author xiaohe
 * @time 2020.04.09 17:31
 */
public class TableImportExcelFormatException extends TableImportException {

    public TableImportExcelFormatException() {
    }

    public TableImportExcelFormatException(String message) {
        super(message);
    }

    public TableImportExcelFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public TableImportExcelFormatException(Throwable cause) {
        super(cause);
    }

    public TableImportExcelFormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
