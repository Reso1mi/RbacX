package top.imlgw.rbac.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author imlgw.top
 * @date 2019/12/3 19:49
 */
public class CookieUtil {

    public static final String USER_TOKEN_NAME="rbacx";

    private static Integer expire=3600*12;

    public static void addCookies(HttpServletResponse response,String cookieName,String token) {
        Cookie cookie = new Cookie(cookieName, token);
        //设置Cookie过期时间 12h
        cookie.setMaxAge(expire);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }


    public static void removeCookie(HttpServletRequest request,String cookieName) {
        Cookie[]  cookies = request.getCookies();
        if(cookies == null || cookies.length <= 0){
            return;
        }
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals(cookieName)) {
                cookie.setMaxAge(0);
                return;
            }
        }
    }

    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[]  cookies = request.getCookies();
        if(cookies == null || cookies.length <= 0){
            return null;
        }
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals(cookieName)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
