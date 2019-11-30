package top.imlgw.rbac.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author imlgw.top
 * @date 2019/11/30 23:34
 */
@Target({ElementType.METHOD,ElementType.FIELD,ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {IsMailValidator.class}
)
public @interface IsMail {
    boolean required()default true;

    String message()default "邮箱格式错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload()default {};
}
