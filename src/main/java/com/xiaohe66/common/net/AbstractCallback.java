package com.xiaohe66.common.net;

import com.google.gson.JsonSyntaxException;
import com.xiaohe66.common.util.JsonUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author xiaohe
 * @time 2020.07.17 17:28
 */
public abstract class AbstractCallback<T> implements Callback {

    private Type beanType;

    protected void setBeanType(Type beanType) {
        this.beanType = beanType;
    }

    @Override
    public void onFailure(@NotNull Call call, @NotNull IOException e) {
        onFail(call, new ResponseException("request is failure", e));
    }

    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

        if (!response.isSuccessful()) {
            onFail(call, new ResponseException("http code is unsuccessful : " + response.code()));
            return;
        }

        ResponseBody body = response.body();

        if (body == null) {
            onFail(call, new ResponseException("response body is null"));
            return;
        }

        try {
            String str = body.string();
            T result = toBean(str);
            onSuccess(call, result);

        } catch (ResponseException e) {
            onFail(call, e);

        } catch (Exception e) {
            onFail(call, new ResponseException("cannot get body string", e));
        }
    }

    public T toBean(String response) throws ResponseException {
        try {
            return JsonUtils.formatObject(response, beanType);

        } catch (JsonSyntaxException e) {
            throw new ResponseException("cannot syntax json", e);
        }
    }

    public abstract void onSuccess(Call call, T bean);

    public abstract void onFail(Call call, ResponseException e);
}
