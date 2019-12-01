package top.imlgw.rbac.validator;

import org.apache.commons.lang3.StringUtils;
import top.imlgw.rbac.utils.ValidatorUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author imlgw.top
 * @date 2019/11/30 23:36
 */
public class IsMailValidator implements ConstraintValidator<IsMail, String> {
    private boolean required=false;

    @Override
    public void initialize(IsMail constraintAnnotation) {
        required=constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(required){
            return  ValidatorUtil.isMail(s);
        }else {
            if(StringUtils.isEmpty(s)){
                return true;
            }else {
                return  ValidatorUtil.isMail(s);
            }
        }
    }
}
