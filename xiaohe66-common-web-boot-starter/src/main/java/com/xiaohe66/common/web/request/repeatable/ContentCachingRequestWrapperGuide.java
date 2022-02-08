package com.xiaohe66.common.web.request.repeatable;

import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 在 spring mvc 中的这个 {@link ContentCachingRequestWrapper} 类
 * 可以实现 request body 的多次读取，即 {@link ServletRequest#getInputStream()} 方法的多次使用
 * 具体实现需要添加一个过滤器，使所有的 request 都被该类 {@link ContentCachingRequestWrapper} 装饰。
 *
 * @author xiaohe
 * @see org.springframework.web.util.ContentCachingRequestWrapper
 * @since 2022.02.08 13:57
 */
class ContentCachingRequestWrapperGuide {

    private ContentCachingRequestWrapperGuide() {

    }

    /**
     * 过滤器示例
     */
    static class FilterDemo extends GenericFilterBean {

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

            HttpServletRequest currentRequest = (HttpServletRequest) request;
            // 装饰
            ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(currentRequest);
            chain.doFilter(wrappedRequest, response);
        }
    }

}
