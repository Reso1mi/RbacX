package top.imlgw.rbac.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Date;
import java.util.Random;

/**
 * @author imlgw.top
 * @date 2019/12/1 21:11
 */
public class PasswordUtil {
    //不要l和1,容易混淆
    private static final String chars="abcdefghijkmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ023456789";

    public static  String generatorPassword(){
        return  randomStr(8);
    }

    private static String randomStr(int count){
        Random random=new Random(new Date().getTime());
        //线程安全
        StringBuffer sb=new StringBuffer();
        //8~10位密码
        for (int i=0;i<count;i++){
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return  sb.toString();
    }

    public static String encode(CharSequence charSequence) {
        String salt=randomStr(6);
        String password=charSequence.toString();
        password=password+salt;
        return salt+DigestUtils.md5Hex(password.getBytes());
    }

    public static boolean matches(CharSequence charSequence, String encodePassword) {
        String password=charSequence.toString();
        String salt=encodePassword.substring(0,6);
        return encodePassword.equals(salt+DigestUtils.md5Hex((password+salt).getBytes()));
    }


    public static void main(String[] args) {
        System.out.println(encode("123456"));
        System.out.println(encode("123456"));
    }
}
