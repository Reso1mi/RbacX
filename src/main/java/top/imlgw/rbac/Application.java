package top.imlgw.rbac;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import top.imlgw.rbac.utils.SpringContextUtil;

/**
 * @author imlgw.top
 * @date 2019/11/20 16:59
 */
@SpringBootApplication
@MapperScan("top.imlgw.rbac.dao")
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Application.class);
        //注入上下文Context
        SpringContextUtil.setApplicationContext(run);
    }
}
