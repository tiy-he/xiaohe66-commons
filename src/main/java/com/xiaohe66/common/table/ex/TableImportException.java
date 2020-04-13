package com.xiaohe66.common.table.ex;

/**
 * @author xiaohe
 * @time 2020.04.09 11:45
 */
public class TableImportException extends RuntimeException{

    public TableImportException() {
    }

    public TableImportException(String message) {
        super(message);
    }

    public TableImportException(String message, Throwable cause) {
        super(message, cause);
    }

    public TableImportException(Throwable cause) {
        super(cause);
    }

    public TableImportException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
