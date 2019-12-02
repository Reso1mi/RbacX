package top.imlgw.rbac.validator;

import java.lang.annotation.*;

/**
 * @author imlgw.top
 * @date 2019/12/2 21:11
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NeedLogin {
    boolean needLogin() default true;
}
