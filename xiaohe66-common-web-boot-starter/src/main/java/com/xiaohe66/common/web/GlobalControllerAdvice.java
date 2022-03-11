package com.xiaohe66.common.web;

import com.xiaohe66.common.dto.R;
import com.xiaohe66.common.util.ex.BusinessException;
import com.xiaohe66.common.util.ex.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.joining;

/**
 * @author xiaohe
 * @since 2021.11.12 10:30
 */
@RestControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

    @ExceptionHandler({Exception.class})
    public R<Void> exceptionHandler(Exception e) {

        log.error("system error", e);

        return R.build(ErrorCodeEnum.ERROR.getCode(), ErrorCodeEnum.ERROR.getMsg());
    }

    /**
     * 不支持的接口，就是 404
     */
    @ExceptionHandler({NoHandlerFoundException.class})
    public R<Void> exceptionHandler(NoHandlerFoundException e) {

        // 想要这个方法生效，需要在yml添加以下两个参数
        // spring.resources.add-mappings = false
        // spring.mvc.throw-exception-if-no-handler-found = true
        return R.build(ErrorCodeEnum.NOT_FOUND_URL.getCode(), ErrorCodeEnum.NOT_FOUND_URL.getMsg());
    }

    /**
     * 不支持的请求类型，如GET请求使用POST方式
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public R<Void> exceptionHandler(HttpRequestMethodNotSupportedException e) {

        return R.build(ErrorCodeEnum.NOT_FOUND_URL_METHOD.getCode(), ErrorCodeEnum.NOT_FOUND_URL_METHOD.getMsg());
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public R<Void> exceptionHandler(MethodArgumentTypeMismatchException e) {

        String type = e.getRequiredType() == null ? "" : e.getRequiredType().getSimpleName();

        String msg = String.format("请求参数[%s]的类型应为[%s]", e.getName(), type);

        return R.build(ErrorCodeEnum.PARAM_ERROR.getCode(), msg);
    }

    @ExceptionHandler({MissingRequestValueException.class,})
    public R<Void> exceptionHandler(MissingRequestValueException e) {

        String msg;
        if (e instanceof MissingServletRequestParameterException) {
            MissingServletRequestParameterException e1 = (MissingServletRequestParameterException) e;
            msg = String.format("请求参数[%s]错误", e1.getParameterName());

        } else if (e instanceof MissingPathVariableException) {
            MissingPathVariableException e1 = (MissingPathVariableException) e;
            msg = String.format("路径参数[%s]错误", e1.getVariableName());

        } else if (e instanceof MissingRequestHeaderException) {
            MissingRequestHeaderException e1 = (MissingRequestHeaderException) e;
            msg = String.format("请求头参数[%s]错误", e1.getHeaderName());

        } else {

            msg = ErrorCodeEnum.PARAM_ERROR.getMsg();
        }

        return R.build(ErrorCodeEnum.PARAM_ERROR.getCode(), msg);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class,})
    public R<Void> exceptionHandler(HttpMessageNotReadableException e) {

        return R.build(ErrorCodeEnum.PARAM_EMPTY.getCode(), "body不能为空");
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public R<Void> bindExceptionHandler(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();

        String collect = constraintViolations.stream()
                .map(item -> item.getPropertyPath() + item.getMessage())
                .collect(joining(","));

        return R.build(ErrorCodeEnum.PARAM_ERROR.getCode(), collect);
    }

    @ExceptionHandler({BindException.class})
    public R<Void> bindExceptionHandler(BindException e) {

        List<FieldError> fieldErrorList = e.getBindingResult().getFieldErrors();

        String msg = fieldErrorList.stream()
                .map(fieldError -> fieldError.getField() + fieldError.getDefaultMessage())
                .collect(joining(","));

        return R.build(ErrorCodeEnum.PARAM_ERROR.getCode(), msg);
    }

    @ExceptionHandler(BusinessException.class)
    public R<Void> businessExceptionHandler(BusinessException e) {

        return R.build(e.getCode(), e.getMsg());
    }

}
