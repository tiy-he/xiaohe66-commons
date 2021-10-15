package com.xiaohe66.common.table.ex;

/**
 * @author xiaohe
 * @time 2020.04.09 15:11
 */
public class TableImportSqlBuilderException extends TableImportException{
    public TableImportSqlBuilderException() {
    }

    public TableImportSqlBuilderException(String message) {
        super(message);
    }

    public TableImportSqlBuilderException(String message, Throwable cause) {
        super(message, cause);
    }

    public TableImportSqlBuilderException(Throwable cause) {
        super(cause);
    }

    public TableImportSqlBuilderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
