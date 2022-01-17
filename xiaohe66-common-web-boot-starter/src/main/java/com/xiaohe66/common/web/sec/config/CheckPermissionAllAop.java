package com.xiaohe66.common.web.sec.config;

import com.xiaohe66.common.web.sec.SecurityService;
import com.xiaohe66.common.web.sec.annotation.NeedPermissionAll;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author xiaohe
 * @since 2021.11.19 16:52
 */
@Component
@Aspect
@Slf4j
@RequiredArgsConstructor
public class CheckPermissionAllAop {

    private final SecurityService securityService;

    @Pointcut("@annotation(com.xiaohe66.common.web.sec.annotation.NeedPermissionAll)")
    private void pointcut() {
        // aop
    }

    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {

        securityService.checkLogin();

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        Method method = methodSignature.getMethod();
        NeedPermissionAll needRoles = method.getAnnotation(NeedPermissionAll.class);

        String[] permission = needRoles.value();

        if (log.isDebugEnabled()) {
            log.debug("check permissionAll : {}", Arrays.toString(permission));
        }

        if (permission != null) {
            securityService.checkPermissionAll(permission);
        }

    }
}
