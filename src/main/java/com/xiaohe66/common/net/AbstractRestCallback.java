package com.xiaohe66.common.net;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * @author xiaohe
 * @time 2020.07.17 17:28
 */
public abstract class AbstractRestCallback implements Callback {

    @Override
    public void onFailure(@NotNull Call call, @NotNull IOException e) {
        onFail(call, new RestResponseException("request is failure", e));
    }

    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

        if (!response.isSuccessful()) {
            onFail(call, new RestResponseException("http code is unsuccessful : " + response.code()));
            return;
        }

        ResponseBody body = response.body();

        if (body == null) {
            onFail(call, new RestResponseException("response body is null"));
            return;
        }

        try {
            String str = body.string();
            onSuccess(str);
        } catch (IOException e) {
            onFail(call, new RestResponseException("cannot get body string", e));
        }
    }

    public abstract void onSuccess(String response);

    public abstract void onFail(Call call, Exception e);
}
