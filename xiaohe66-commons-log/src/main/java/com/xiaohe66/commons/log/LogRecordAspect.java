package com.xiaohe66.commons.log;

import com.xiaohe66.commons.log.annotation.LogRecord;
import com.xiaohe66.commons.log.context.LogContext;
import com.xiaohe66.commons.log.context.LogContextHolder;
import com.xiaohe66.commons.log.tool.LogRecordAsyncExecutor;
import com.xiaohe66.commons.log.tool.LogRecordExtend;
import com.xiaohe66.commons.log.tool.LogRecordService;
import com.xiaohe66.commons.log.tool.LogRecordTemplateParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * TODO : 需要处理嵌套的场景，即 @LogRecord 调用 @LogRecord 方法
 *
 * @author xiaohe
 * @since 2022.05.20 17:18
 */
@Aspect
@Slf4j
@RequiredArgsConstructor
public class LogRecordAspect implements ApplicationContextAware {

    private final LogContextHolder logContextHolder;
    private final LogRecordService logRecordService;
    private final LogRecordAsyncExecutor asyncExecutor;

    private final LogRecordTemplateParser logTemplateParser = new LogRecordTemplateParser();

    private ApplicationContext applicationContext;

    @Pointcut("@annotation(com.xiaohe66.commons.log.annotation.LogRecord)")
    public void pointcut() {
        // pointcut
    }

    @Around(value = "pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        LogRecord logRecord = signature.getMethod().getAnnotation(LogRecord.class);

        LogContext logContext = convertToContext(logRecord, joinPoint, signature);

        logContextHolder.set(logContext);

        LogRecordExtend<Object> extend = getExtend(logRecord);

        if (extend != null) {

            Object data = extend.executeBefore(logContext);

            try {

                Object ret = joinPoint.proceed();

                logContext.setResult(ret);

                handleHaveExtend(logContext, extend, data, logRecord.async());

                return ret;

            } catch (Throwable e) {

                logContext.setException(true);

                handleHaveExtend(logContext, extend, data, logRecord.async());

                throw e;

            } finally {
                logContextHolder.remove();
            }

        } else {

            try {

                Object ret = joinPoint.proceed();

                logContext.setResult(ret);

                handleNoExtend(logContext, logRecord.async());

                return ret;

            } catch (Throwable e) {

                logContext.setException(true);

                handleNoExtend(logContext, logRecord.async());

                throw e;

            } finally {
                logContextHolder.remove();
            }
        }
    }

    private void handleNoExtend(LogContext logContext, boolean isAsync) {
        if (isAsync) {
            asyncExecutor.execute(() -> {

                doHandleNoExtend(logContext);
            });
        } else {
            doHandleNoExtend(logContext);
        }
    }

    private void doHandleNoExtend(LogContext logContext) {

        try {
            String content = logTemplateParser.process(logContext);

            logContext.setContent(content);

            logRecordService.saveLog(logContext);

        } catch (Exception e) {
            log.error("save log error, logContext : {}", logContext, e);
        }
    }

    private void handleHaveExtend(LogContext logContext, LogRecordExtend<Object> extend, Object data, boolean isAsync) {

        if (isAsync) {
            asyncExecutor.execute(() -> {

                doHandleHaveExtend(logContext, extend, data);
            });
        } else {
            doHandleHaveExtend(logContext, extend, data);
        }
    }

    private void doHandleHaveExtend(LogContext logContext, LogRecordExtend<Object> extend, Object data) {
        try {
            boolean go = extend.parseBefore(logContext, data);
            if (!go) {
                return;
            }

            String content = logTemplateParser.process(logContext);

            logContext.setContent(content);

            go = extend.parseAfter(logContext, data);
            if (!go) {
                return;
            }

            logRecordService.saveLog(logContext);

            extend.saveAfter(logContext, data);

        } catch (Exception e) {
            log.error("save log error, logContext : {}", logContext, e);
        }
    }

    private LogContext convertToContext(LogRecord logRecord, ProceedingJoinPoint joinPoint, MethodSignature signature) {

        LogContext context = new LogContext();
        context.setSuccess(logRecord.success());
        context.setFail(logRecord.fail());
        context.setType(logRecord.type());
        context.setSubType(logRecord.subType());
        context.setExtra(logRecord.extra());
        context.setMethod(signature.getMethod());
        context.setTargetClass(joinPoint.getTarget().getClass());
        context.setArgs(joinPoint.getArgs());
        context.setCurrentAccount(logRecordService.getCurrentAccount());

        return context;
    }

    @SuppressWarnings("unchecked")
    private LogRecordExtend<Object> getExtend(LogRecord logRecord) {
        if (logRecord.extendClass() != LogRecordExtend.class) {
            try {
                return applicationContext.getBean(logRecord.extendClass());

            } catch (BeansException e) {
                log.error("cannot get extend bean : {}", logRecord.extendClass().getSimpleName(), e);
            }
        }
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
