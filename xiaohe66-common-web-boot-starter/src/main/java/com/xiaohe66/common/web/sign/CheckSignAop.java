package com.xiaohe66.common.web.sign;

import com.xiaohe66.common.util.Assert;
import com.xiaohe66.common.util.SignUtils;
import com.xiaohe66.common.util.SystemClock;
import com.xiaohe66.common.util.ex.BusinessException;
import com.xiaohe66.common.util.ex.ErrorCodeEnum;
import com.xiaohe66.common.web.CommonWebProperties;
import com.xiaohe66.common.web.util.WebUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xiaohe
 * @since 2021.12.25 16:22
 */
@Aspect
@Slf4j
@RequiredArgsConstructor
public class CheckSignAop {

    private final CheckSignSecretSupplier secretSupplier;
    private final CommonWebProperties commonWebProperties;

    @Pointcut("@annotation(com.xiaohe66.common.web.sign.CheckSign)")
    private void loginPointcut() {
        // aop
    }

    @Before("loginPointcut()")
    public void before(JoinPoint joinPoint) {

        log.debug("check sign");

        try {

            HttpServletRequest request = WebUtils.getRequest();

            String appIdStr = getHeader(request, "appId");
            String timestampStr = getHeader(request, "timestamp");
            String sign = getHeader(request, "sign");

            long appId = Long.parseLong(appIdStr);
            long timestamp = Long.parseLong(timestampStr);
            long currentTime = SystemClock.currentTimeMillis();

            if (Math.abs(currentTime - timestamp) >= commonWebProperties.getCheckSignTimeoutMs()) {
                throw new BusinessException(ErrorCodeEnum.ILLEGAL_OPERATE, "请求超时");
            }

            String body = getBody(request, joinPoint);
            String secret = secretSupplier.getSecret(appId);
            Assert.notEmpty(secret, ErrorCodeEnum.NOT_FOUND_ACCOUNT);

            String queryUrl = request.getRequestURI() + '?' + StringUtils.trimToEmpty(request.getQueryString());

            if (!SignUtils.verifyMd5(request.getMethod(), queryUrl, timestamp, body, secret, sign)) {
                throw new BusinessException(ErrorCodeEnum.ILLEGAL_OPERATE, "签名错误");
            }

        } catch (NumberFormatException e) {
            throw new BusinessException(ErrorCodeEnum.ILLEGAL_OPERATE, "[appId]或[timestamp]错误");

        } catch (BusinessException e) {
            throw e;

        } catch (Exception e) {
            log.error("check sign error", e);
            throw new BusinessException(ErrorCodeEnum.ERROR);
        }
    }

    protected String getHeader(HttpServletRequest request, String name) {

        String value = request.getHeader(name);
        Assert.notEmpty(value, ErrorCodeEnum.ILLEGAL_OPERATE, "缺少" + name);

        return value;
    }

    protected String getBody(HttpServletRequest request, JoinPoint joinPoint) {

        boolean isBodyRequest = HttpMethod.POST.matches(request.getMethod()) || HttpMethod.PUT.matches(request.getMethod());

        if (isBodyRequest) {
            Object[] args = joinPoint.getArgs();
            CheckSignDto dto = null;
            for (Object arg : args) {
                if (arg instanceof CheckSignDto) {
                    dto = (CheckSignDto) arg;
                    break;
                }
            }
            if (dto == null) {
                log.warn("cannot get CheckSignDto param, maybe controller is wrong.");
                throw new BusinessException(ErrorCodeEnum.ERROR);
            }
            return dto.getBody();
        }

        return "";
    }
}
