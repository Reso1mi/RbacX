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
        String requestURL = request.getRequestURL().toString();
        Set<String> exclusionUrls= aclConfig.getExclusionUrls().stream().collect(Collectors.toSet());
        if (exclusionUrls.contains(requestURL)){
            return true;
        }
        SysUser currentSysUser = RequestContext.getCurrentSysUser();
        if (currentSysUser == null){
            log.info("{} visit{},don't auth!",IpUtil.getRemoteIp(RequestContext.getCurrentRequest()),requestURL);
            return false;
        }
        if (!sysCoreService.hasAcl(requestURL)){
            throw new PermissionException(CodeMsg.PERMISSOIN_BAN);
        }
        return true;
    }
}
