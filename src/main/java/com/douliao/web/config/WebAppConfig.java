package com.douliao.web.config;

import javax.servlet.MultipartConfigElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.douliao.web.interceptor.LogInterceptor;

@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {
	@Autowired  
    private LogInterceptor logInterceptor;  
	
    @Value("${web.maxFileSize}")
    private String maxFileSize;
    
    @Value("${web.maxRequestSize}")
    private String maxRequestSize;

    @Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大KB,MB
        factory.setMaxFileSize(maxFileSize);
        //设置总上传数据总大小
        factory.setMaxRequestSize(maxRequestSize);
        return factory.createMultipartConfig();
    }
    
    @Override  
    public void addInterceptors(InterceptorRegistry registry) {  
        registry.addInterceptor(logInterceptor);  
        super.addInterceptors(registry);  
    }
	
}
