package top.imlgw.rbac.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import top.imlgw.rbac.config.AclConfig;
import top.imlgw.rbac.entity.SysUser;
import top.imlgw.rbac.exception.PermissionException;
import top.imlgw.rbac.result.CodeMsg;
import top.imlgw.rbac.service.SysCoreService;
import top.imlgw.rbac.utils.IpUtil;
import top.imlgw.rbac.utils.RequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author imlgw.top
 * @date 2019/12/13 20:19
 */
@Slf4j
@Component
public class AclInterceptor implements HandlerInterceptor {

    @Autowired
    private SysCoreService sysCoreService;

    @Autowired
    private AclConfig aclConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURL = request.getRequestURI();
        System.err.println(requestURL);
        if (requestURL.endsWith(".css")
                || requestURL.endsWith(".js")
                || requestURL.endsWith(".html")
                ||  requestURL.endsWith(".woff")
                ||  requestURL.endsWith(".gif")
                ||  requestURL.endsWith(".png")) {
            return true;
        }
        Set<String> exclusionUrls= aclConfig.getExclusionUrls().stream().collect(Collectors.toSet());
        if (exclusionUrls.contains(requestURL)){
            return true;
        }
        SysUser currentSysUser = RequestContext.getCurrentSysUser();
        if (currentSysUser == null){
            log.info("{} visit {},don't auth!",IpUtil.getRemoteIp(RequestContext.getCurrentRequest()),requestURL);
            response.sendRedirect("/login.page");
            return false;
        }
        if (!sysCoreService.hasAcl(requestURL)){
            noAuth(request,response,requestURL);
            return false;
        }
        return true;
    }

    private void noAuth(HttpServletRequest request, HttpServletResponse response, String requestURL) throws IOException {
        if (requestURL.endsWith(".page")){
            response.setHeader("Content-Type", "text/html");
            response.getWriter().print("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
                    + "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" + "<head>\n" + "<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\"/>\n"
                    + "<title>Redirect....</title>\n" + "</head>\n" + "<body>\n" + "Redirect...Waiting\n" + "<script " +
                    "type=\"text/javascript\">//<![CDATA[\n"
                    + "window.location.href='" + aclConfig.getNoAuthUrl()+ "?ret='+encodeURIComponent(window.location.href);\n" +
                    "//]]></script>\n" + "</body>\n" + "</html>\n");
            return;
        }else {
            throw new PermissionException(CodeMsg.PERMISSOIN_BAN);
        }
    }
}
