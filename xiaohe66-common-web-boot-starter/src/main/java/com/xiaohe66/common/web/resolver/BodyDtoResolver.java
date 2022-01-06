package com.xiaohe66.common.web.resolver;

import com.xiaohe66.common.util.JsonUtils;
import com.xiaohe66.common.web.util.WebUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;

/**
 * @author xiaohe
 * @since 2022.01.05 18:07
 */
@Slf4j
@RequiredArgsConstructor
public class BodyDtoResolver implements HandlerMethodArgumentResolver, WebMvcConfigurer {

    private final Validator validator;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (BodyDto.class.isAssignableFrom(parameter.getParameterType())) {

            HttpServletRequest request = WebUtils.getRequest();

            String methodType = request.getMethod();

            return HttpMethod.POST.matches(methodType) || HttpMethod.PUT.matches(methodType);
        }

        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {

        if (log.isDebugEnabled()) {
            log.debug("自动注入 : {}", parameter.getParameterType().getName());
        }

        HttpServletRequest request = WebUtils.getRequest();

        String body = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);

        // TODO : 如果参数是泛型怎么办
        BodyDto dto = (BodyDto) JsonUtils.formatObject(body, parameter.getParameterType());
        dto.setBody(body);

        Set<ConstraintViolation<BodyDto>> validate = validator.validate(dto);
        if (!validate.isEmpty()) {
            throw new ConstraintViolationException(validate);
        }

        return dto;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(this);
    }
}
