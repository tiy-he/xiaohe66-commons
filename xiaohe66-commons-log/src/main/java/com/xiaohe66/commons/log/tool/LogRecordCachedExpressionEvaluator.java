package com.xiaohe66.commons.log.tool;

import org.springframework.aop.support.AopUtils;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.context.expression.CachedExpressionEvaluator;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiaohe
 * @see org.springframework.cache.interceptor.CacheOperationExpressionEvaluator
 * @since 2022.05.23 15:23
 */
public class LogRecordCachedExpressionEvaluator extends CachedExpressionEvaluator {

    private final Map<ExpressionKey, Expression> keyCache = new ConcurrentHashMap<>(64);
    private final Map<AnnotatedElementKey, Method> targetMethodCache = new ConcurrentHashMap<>(64);

    public EvaluationContext createEvaluationContext(Method method, Object[] args, Class<?> targetClass) {

        Method targetMethod = getTargetMethod(targetClass, method);

        return new MethodBasedEvaluationContext(null, targetMethod, args, getParameterNameDiscoverer());
    }

    public Object parseExpression(String conditionExpression,
                                  AnnotatedElementKey methodKey,
                                  EvaluationContext context) {

        return getExpression(this.keyCache, methodKey, conditionExpression).getValue(context, Object.class);
    }

    private Method getTargetMethod(Class<?> targetClass, Method method) {

        AnnotatedElementKey methodKey = new AnnotatedElementKey(method, targetClass);

        return targetMethodCache.computeIfAbsent(methodKey, (key) -> AopUtils.getMostSpecificMethod(method, targetClass));
    }
}
