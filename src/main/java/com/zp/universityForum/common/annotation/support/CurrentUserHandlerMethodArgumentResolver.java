package com.zp.universityForum.common.annotation.support;

import com.zp.universityForum.common.annotation.CurrentUser;
import com.zp.universityForum.common.constant.Constant;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author zp
 * @date 2021-03-05 8:59
 */
public class CurrentUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    /**
     * 此处使用了Java的反射机制
     * 此方法用于检测拦截的对象是否有必要进行下面的resolveArgument处理
     * 判断便准为：是否具有 Long 参数(我们已从token获取userId)  以及是否具有 CurrentUser的注解
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(Long.class) && parameter.hasParameterAnnotation(CurrentUser.class);
    }

    /**
     * 返回已登录用户的userId
     * @throws Exception
     */
    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) throws Exception {
        return webRequest.getAttribute(Constant.LOGIN_USER_ID, RequestAttributes.SCOPE_REQUEST);
    }
}
