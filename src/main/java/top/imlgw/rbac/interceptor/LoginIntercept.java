package top.imlgw.rbac.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import top.imlgw.rbac.entity.SysUser;
import top.imlgw.rbac.utils.CookieUtil;
import top.imlgw.rbac.utils.RequestContext;
import top.imlgw.rbac.utils.UserContext;
import top.imlgw.rbac.validator.NeedLogin;

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
        String requestURL = request.getRequestURL().toString();
        if (requestURL.endsWith(".css") || requestURL.endsWith(".js")) {
            return true;
        }
        HandlerMethod hm=(HandlerMethod) handler;
        SysUser sysUser= getSysUser(request, response);
        //有的页面不需要登陆但是需要用户信息，所以需要先存进去
        RequestContext.add(sysUser);
        //获取方法上的注解
        NeedLogin needLogin = hm.getMethodAnnotation(NeedLogin.class);
        if(needLogin==null || ! needLogin.needLogin()){
            //没有注解或者注解为false,就直接放过
            return true;
        }
        //有注解且为true , 但是没登陆
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
        String requestURL = request.getRequestURL().toString();
        if (requestURL.endsWith(".css") || requestURL.endsWith(".js")) {
            return;
        }
        //删除ThreadLocal中的User和Request, 否则会产生错乱
        //System.err.println("UserContext.removeUser()");
        RequestContext.remove();
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
        String paramToken = request.getParameter(CookieUtil.USER_TOKEN_NAME);
        //拿cookie中的token
        String cookieToken = CookieUtil.getCookieValue(request, CookieUtil.USER_TOKEN_NAME);
        if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            //没登陆cookie为空
            return null;
        }
        String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
        SysUser sysUser= (SysUser) request.getSession().getAttribute(token);
        return sysUser;
    }
}
