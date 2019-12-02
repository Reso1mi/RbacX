package top.imlgw.rbac.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import top.imlgw.rbac.entity.SysUser;
import top.imlgw.rbac.service.SysUserService;
import top.imlgw.rbac.utils.UserContext;
import top.imlgw.rbac.validator.NeedLogin;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author imlgw.top
 * @date 2019/12/2 21:06
 */
@Component
public class LoginIntercept implements HandlerInterceptor {

    /*@Autowired
    private SysUserService sysUserService;*/

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod hm=(HandlerMethod) handler;
        SysUser sysUser= getSysUser(request, response);
        //有的页面不需要登陆但是需要用户信息，所以需要先存进去
        UserContext.setUser(sysUser);
        //获取方法上的注解
        NeedLogin needLogin = hm.getMethodAnnotation(NeedLogin.class);
        if(needLogin==null || ! needLogin.needLogin()){
            //没有注解后者注解为false,就直接放过
            return true;
        }
        //有注解，没登陆
        if(sysUser==null){
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }


    /** 视图渲染完毕后调用(收尾工作)
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //删除ThreadLocal中的User否则会产生错乱
        UserContext.removeUser();
    }


    /**
     * 获取 SysUser
     * @param request
     * @param response
     * @return
     * todo
     */
    private SysUser getSysUser(HttpServletRequest request, HttpServletResponse response){
        //拿参数中的token
        String paramToken = request.getParameter(SysUserService.TOKEN_NAME);
        //拿cookie中的token
        String cookieToken = getCookieValue(request, SysUserService.TOKEN_NAME);
        if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            //没登陆cookie为空
            return null;
        }
        String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
        SysUser sysUser= (SysUser) request.getSession().getAttribute(token);
        return sysUser;
    }


    /*
     * 获取cookie中的User
     * */
    private String getCookieValue(HttpServletRequest request, String cookieName) {
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
