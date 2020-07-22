package com.xiaohe66.common.net.ex;

/**
 * @author xiaohe
 * @time 2020.07.22 10:59
 */
public class RequesterJsonSyntaxException extends RequesterException {

    private final String body;

    public RequesterJsonSyntaxException(String message, String body) {
        super(message);
        this.body = body;
    }

    public String getBody() {
        return body;
    }
}
