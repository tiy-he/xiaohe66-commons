package com.xiaohe66.common.web.sec.config;

import com.xiaohe66.common.web.sec.SecurityService;
import com.xiaohe66.common.web.sec.annotation.NeedRoleAny;
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
 * @since 2021.11.19 15:17
 */
@Component
@Aspect
@Slf4j
@RequiredArgsConstructor
public class CheckRoleAnyAop {

    private final SecurityService securityService;

    @Pointcut("@annotation(com.xiaohe66.common.web.sec.annotation.NeedRoleAny)")
    private void pointcut() {
        // aop
    }

    @Before(value = "pointcut()")
    public void before(JoinPoint joinPoint) {

        securityService.checkLogin();

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        Method method = methodSignature.getMethod();
        NeedRoleAny needRoles = method.getAnnotation(NeedRoleAny.class);

        String[] roles = needRoles.value();

        if (log.isDebugEnabled()) {
            log.debug("check roleAny : {}", Arrays.toString(roles));
        }

        if (roles != null) {
            securityService.checkRoleAny(roles);
        }
    }
}
