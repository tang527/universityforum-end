package com.zp.universityForum.common.interceptor;

import com.zp.universityForum.common.annotation.support.CurrentUserHandlerMethodArgumentResolver;
import com.zp.universityForum.common.annotation.support.CurrentUserNameHandlerMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * 拦截器配置
 * @author zp
 * @date 2021-04-02 17:18
 */
/*
@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器, 拦截路径, 和排除拦截路径
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**")
                .excludePathPatterns(
                        "/user/login",
                        "/user/register",
                        "/article_type/get**",
                        "/article/get**",
//                        "/article/like_article",
                        "/comment/get**",
                        "/report/get**"
                );
        super.addInterceptors(registry);
    }

    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new CurrentUserHandlerMethodArgumentResolver());
        argumentResolvers.add(new CurrentUserNameHandlerMethodArgumentResolver());
    }

    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://1.15.243.160")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "TRACE")
                .allowCredentials(true)
                .allowedHeaders("*");
    }


}
*/
