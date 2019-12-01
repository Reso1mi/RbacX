package top.imlgw.rbac.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.imlgw.rbac.interceptor.HttpInterceptor;


/**
 * @author imlgw.top
 * @date 2019/11/21 16:44
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    HttpInterceptor httpInterceptor;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //这里如果是用的模板引擎，就只能是模板引擎template里面的文件
        //这里后面默认指的是static里面的文件，后缀为html
        /*registry.addViewController("/").setViewName("login");
        registry.addViewController("/goodslist").setViewName("goods_list");
        registry.addViewController("/register").setViewName("register");*/
        registry.addViewController("/dept").setViewName("dept");
        registry.addViewController("/").setViewName("signin");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(httpInterceptor).addPathPatterns("/**").excludePathPatterns("/login/*");
    }
}
