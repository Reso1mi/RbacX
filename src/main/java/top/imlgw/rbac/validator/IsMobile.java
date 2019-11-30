package top.imlgw.rbac.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author imlgw.top
 * @date 2019/11/30 22:21
 */
@Target({ElementType.METHOD,ElementType.FIELD,ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {IsMobileValidator.class}
)
public @interface IsMobile {
    boolean required()default true;

    String message()default "号码格式错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload()default {};
}
