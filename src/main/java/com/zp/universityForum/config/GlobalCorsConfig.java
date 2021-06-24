package com.zp.universityForum.config;


import com.zp.universityForum.common.annotation.support.CurrentUserHandlerMethodArgumentResolver;
import com.zp.universityForum.common.annotation.support.CurrentUserNameHandlerMethodArgumentResolver;
import com.zp.universityForum.common.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 全局跨域配置
 */
/*@Configuration
public class GlobalCorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            //重写父类提供的跨域请求处理的接口
            public void addCorsMappings(CorsRegistry registry) {
                //放行哪些原始域
                registry.addMapping("/**")
                        //是否发送Cookie信息
                        .allowCredentials(true)
                        //放行哪些原始域(头部信息)
                        .allowedHeaders("*")
                        //放行哪些原始域(请求方式)
                        .allowedMethods("GET","POST","PUT","DELETE","OPTIONS")
                         //放行哪些原始域
                        .allowedOrigins("*")
                        //暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
                        .exposedHeaders("Header1","Header2");
            }
        };
    }
}*/

@Configuration
public class GlobalCorsConfig extends WebMvcConfigurationSupport {

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
    public void addCorsMappings(CorsRegistry registry) {
        //放行哪些原始域
        registry.addMapping("/**")
                //放行哪些原始域
                   //放行哪些原始域(请求方式)
                .allowedMethods("GET","HEAD","POST","PUT","DELETE","OPTIONS")
                //是否发送Cookie信息
                .allowCredentials(true)
                //放行哪些原始域(头部信息)
                .allowedHeaders("*")
                .maxAge(3600);
    }
}
