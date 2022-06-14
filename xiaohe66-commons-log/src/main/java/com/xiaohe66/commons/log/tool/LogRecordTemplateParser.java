package com.xiaohe66.commons.log.tool;

import com.xiaohe66.commons.log.context.LogContext;
import com.xiaohe66.commons.log.context.LogRecordDefaultVariableKey;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.EvaluationContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xiaohe
 * @since 2022.05.23 16:03
 */
@RequiredArgsConstructor
public class LogRecordTemplateParser {

    private static final Pattern PATTERN = Pattern.compile("\\{\\s*(\\w*)\\s*\\{(.*?)}}");

    private static final LogRecordCachedExpressionEvaluator expressionEvaluator = new LogRecordCachedExpressionEvaluator();

    public String process(LogContext logContext) {

        String template = logContext.isException() ? logContext.getFail() : logContext.getSuccess();

        if (StringUtils.isEmpty(template)) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder();

        EvaluationContext context = expressionEvaluator.createEvaluationContext(logContext.getMethod(), logContext.getArgs(), logContext.getTargetClass());

        context.setVariable(LogRecordDefaultVariableKey.TYPE, logContext.getType());
        context.setVariable(LogRecordDefaultVariableKey.SUB_TYPE, logContext.getSubType());
        context.setVariable(LogRecordDefaultVariableKey.CURRENT_ACCOUNT_ID, logContext.getCurrentAccount().getId());
        context.setVariable(LogRecordDefaultVariableKey.CURRENT_ACCOUNT_NAME, logContext.getCurrentAccount().getName());
        context.setVariable(LogRecordDefaultVariableKey.RET, logContext.getResult());

        logContext.getVariableMap().forEach(context::setVariable);

        AnnotatedElementKey methodKey = new AnnotatedElementKey(logContext.getMethod(), logContext.getTargetClass());

        Matcher matcher = PATTERN.matcher(template);
        while (matcher.find()) {

            String expression = matcher.group(2);

            Object value = expressionEvaluator.parseExpression(expression, methodKey, context);

            matcher.appendReplacement(stringBuilder, Matcher.quoteReplacement(String.valueOf(value)));
        }

        matcher.appendTail(stringBuilder);

        return stringBuilder.toString();
    }

}
