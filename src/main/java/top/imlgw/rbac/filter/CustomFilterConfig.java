package top.imlgw.rbac.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author imlgw.top
 * @date 2019/12/5 21:04
 */
@Configuration
public class CustomFilterConfig {
    @Bean
    public FilterRegistrationBean authFilterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setName("authFilter");
        LoginFilter loginFilter = new LoginFilter();
        registrationBean.setFilter(loginFilter);
        registrationBean.setOrder(1);
        List<String> urlList = new ArrayList<>();
        urlList.add("/*");
        registrationBean.setUrlPatterns(urlList);
        return registrationBean;
    }

}
