package com.imooc;

import com.imooc.controller.interceptor.MiniInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/META-INF/resources/").addResourceLocations("file:C:/imooc-videos-dev/");
    }

//    @Bean(initMethod = "init")
//    public ZKCuratorClient zkCuratorClient(){
//        return new ZKCuratorClient();
//    }

    @Bean
    public MiniInterceptor miniInterceptor(){
        return new MiniInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(miniInterceptor())
                .addPathPatterns("/user/**")
                .addPathPatterns("/bgm/**")
                .addPathPatterns("/video/upload","/video/userLike","/video/userUnlike");
    }
}
