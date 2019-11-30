package top.imlgw.rbac.utils;

import org.apache.commons.lang3.StringUtils;
import org.mockito.internal.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author imlgw.top
 * @date 2019/11/30 22:25
 */
public class ValidatorUtil {

    //匹配电话号码1开头10个
    private static  final Pattern mobile_pattern =Pattern.compile("1\\d{10}");

    private static  final Pattern mail_pattern =Pattern.compile("/^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$/");

    public static boolean isMobile(String mobile){
        if(StringUtils.isEmpty(mobile)){
            return  false;
        }
        Matcher matcher=mobile_pattern.matcher(mobile);
        return matcher.matches();
    }

    public static boolean isMail(String mail){
        if (StringUtils.isEmpty(mail)){
            return false;
        }
        Matcher matcher = mail_pattern.matcher(mail);
        return matcher.matches();
    }
}
