package com.xiaohe66.common.api.okhttp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.xiaohe66.common.api.ApiException;
import com.xiaohe66.common.api.BaseApiRequest;
import com.xiaohe66.common.api.BuildRequestBodyException;
import com.xiaohe66.common.api.IApiResponse;
import com.xiaohe66.common.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.RequestBody;

import java.lang.reflect.InvocationTargetException;

/**
 * @author xiaohe
 * @time 2021.07.21 09:46
 */
@Slf4j
public abstract class BaseOkHttpApiRequest<R extends IApiResponse> extends BaseApiRequest<R> {

    public BaseOkHttpApiRequest(Method method) {
        super(method);
    }

    @Override
    public abstract RequestBody buildRequestBody() throws BuildRequestBodyException;

    @SuppressWarnings("unchecked")
    @Override
    public R buildResponseBody(String response) throws ApiException {

        JavaType type = getResponseType();
        Class<?> rawClass = type.getRawClass();

        try {
            R object = JsonUtils.formatObject(response, type);
            object.setBody(response);
            return object;

        } catch (ClassCastException e) {
            throw new OkHttpApiException("cannot cast javaType to ResponseBody class, javaType : " + rawClass.getName(), e);

        } catch (JsonProcessingException e) {

            try {
                R object = (R) rawClass.getDeclaredConstructor().newInstance();
                object.setBody(response);

                log.error("parse json fail, response : {}", response, e);

                return object;

            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e1) {
                throw new OkHttpApiException("cannot parse json : " + response, e);

            } catch (ClassCastException e1) {
                throw new OkHttpApiException("cannot cast javaType to ResponseBody class, javaType : " + rawClass.getName(), e);
            }
        }
    }
}
