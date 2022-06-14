package com.xiaohe66.commons.log.context;


import com.xiaohe66.common.util.JsonUtils;
import com.xiaohe66.commons.log.context.impl.CacheParameterNameDiscoverer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaohe
 * @since 2022.05.20 17:07
 */
@Getter
@Setter
@ToString(exclude = "args")
public class LogContext {

    /**
     * 注解上的原始值
     */
    private String success;

    /**
     * 注解上的原始值
     */
    private String fail;

    private String type;

    private String subType;

    private String extra;

    /*-------------------*/

    /**
     * 执行切面方法后，是否存在异常
     */
    private boolean exception = false;

    /**
     * 经过解析日志模板后生成的日志内容
     */
    private String content;

    /**
     * 切面方法的执行结果
     */
    private Object result;

    private LogRecordAccount currentAccount;

    /*-------------------*/

    /**
     * 切面切入的方法
     */
    private Method method;

    private Class<?> targetClass;

    /**
     * 切面方法上的参数
     */
    private Object[] args;

    /**
     * 切面方法上的参数，懒加载
     */
    private Map<String, Object> argsMap;

    private Object argsBean;

    /**
     * 自行添加的参数，模板的变量会从这里取。
     */
    private Map<String, Object> variableMap = new HashMap<>();

    public Map<String, Object> getArgsMap() {

        if (argsMap == null) {

            argsMap = new HashMap<>();

            String[] parameterNames = CacheParameterNameDiscoverer.getParameterNames(method);

            if (parameterNames != null) {

                for (int i = 0; i < parameterNames.length; i++) {
                    argsMap.put(parameterNames[i], args[i]);
                }
            }
        }
        return argsMap;
    }

    @SuppressWarnings("unchecked")
    public <T> T getArg(String key) {
        return (T) getArgsMap().get(key);
    }

    @SuppressWarnings("unchecked")
    public <T> T getArgsBean() {
        return (T) argsBean;
    }

    @SuppressWarnings("unchecked")
    public <T> T formatArgsBean(Class<T> cls) {
        argsBean = JsonUtils.formatObject(getArgsMap(), cls);
        return (T) argsBean;
    }

}
