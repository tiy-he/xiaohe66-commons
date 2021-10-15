package com.xiaohe66.common.table.parser;

/**
 * @author xiaohe
 * @time 2020.04.13 14:43
 */
public class TableParserException extends RuntimeException {

    public TableParserException() {
    }

    public TableParserException(String message) {
        super(message);
    }

    public TableParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public TableParserException(Throwable cause) {
        super(cause);
    }

    public TableParserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
