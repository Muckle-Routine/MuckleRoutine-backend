package dapp.mvp.muckleroutine.config;

import java.util.List;

import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import dapp.mvp.muckleroutine.annotation.component.RequestParamObjectResolver;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final RequestParamObjectResolver paramObjectResolver;
 
 
    @Override
    public void addArgumentResolvers(
            final List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(paramObjectResolver);
    }
    
    //URI에 대문자가 아닌 소문자로 입력시 ConversionFailedException 예외, PathVariable 소문자도 인식
    @Override
    public void addFormatters(
            final FormatterRegistry registry) {
    	ApplicationConversionService.configure(registry);
    }
}