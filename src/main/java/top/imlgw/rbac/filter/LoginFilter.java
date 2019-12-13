package top.imlgw.rbac.filter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.imlgw.rbac.entity.SysUser;
import top.imlgw.rbac.service.SysCoreService;
import top.imlgw.rbac.utils.CookieUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author imlgw.top
 * @date 2019/12/5 19:38
 *
 *  不属于Spring管理,无法使用IOC中的Service,后面扩展需要业务层的支持,所以弃用
 *
 *  拦截器,过滤器区别
 *  https://www.cnblogs.com/panxuejun/p/7715917.html
 */
@Deprecated
public class LoginFilter implements Filter {

    /*@Autowired 类似的操作是不行的
    private  SysCoreService sysCoreService;*/

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        HttpServletResponse response= (HttpServletResponse) servletResponse;
        SysUser sysUser = getSysUser(request, response);
        if (sysUser == null){
            response.sendRedirect("/login");
        }
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }

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
