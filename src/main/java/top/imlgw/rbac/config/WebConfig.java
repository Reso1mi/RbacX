package top.imlgw.rbac.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.imlgw.rbac.interceptor.HttpInterceptor;
import top.imlgw.rbac.interceptor.LoginIntercept;

import java.util.List;


/**
 * @author imlgw.top
 * @date 2019/11/21 16:44
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private HttpInterceptor httpInterceptor;

    @Autowired
    private LoginIntercept loginIntercept;

    @Autowired
    private SysUserArgumentResolver sysUserArgumentResolver;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //这里如果是用的模板引擎，就只能是模板引擎template里面的文件
        //这里后面默认指的是static里面的文件，后缀为html
        /*registry.addViewController("/").setViewName("login");
        registry.addViewController("/goodslist").setViewName("goods_list");
        registry.addViewController("/register").setViewName("register");*/
        registry.addViewController("/login").setViewName("signin");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(httpInterceptor).addPathPatterns("/**").excludePathPatterns("/login/*");
        registry.addInterceptor(loginIntercept);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(sysUserArgumentResolver); //User参数解析器
    }
}
