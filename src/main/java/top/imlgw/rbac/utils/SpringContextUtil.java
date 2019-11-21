package top.imlgw.rbac.utils;

import org.springframework.context.ApplicationContext;

/**
 * @author imlgw.top
 * @date 2019/11/21 15:57
 */
public class SpringContextUtil {

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext){
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static <T> T getBean(String name){
        return (T)applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz){
        return applicationContext.getBean(clazz);
    }


    public static <T> T getBean(String name,Class<T> clazz){
        return applicationContext.getBean(name,clazz);
    }
}
