package com.xiaohe66.common.web.request.repeatable;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author xiaohe
 * @since 2022.01.05 15:59
 */
@Slf4j
public class RepeatableBodyHttpServletRequestWrapperFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("---- init HttpServletRequestWrapperFilter ----");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;

            String methodType = httpRequest.getMethod();
            if (HttpMethod.POST.matches(methodType) || HttpMethod.PUT.matches(methodType)) {

                ServletRequest requestWrapper = new RepeatableBodyHttpServletRequest((HttpServletRequest) request);
                chain.doFilter(requestWrapper, response);
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
