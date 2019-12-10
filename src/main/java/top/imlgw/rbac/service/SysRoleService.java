package top.imlgw.rbac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imlgw.rbac.dao.SysRoleMapper;
import top.imlgw.rbac.entity.SysRole;
import top.imlgw.rbac.exception.GlobalException;
import top.imlgw.rbac.result.CodeMsg;
import top.imlgw.rbac.utils.IpUtil;
import top.imlgw.rbac.utils.RequestContext;
import top.imlgw.rbac.vo.RoleVo;
import java.util.Date;
import java.util.List;

/**
 * @author imlgw.top
 * @date 2019/12/10 23:19
 */
@Service
public class SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    public List<SysRole> getAll() {
        return sysRoleMapper.getAllRole();
    }

    public void save(RoleVo roleVo) {
        if (checkExist(roleVo.getName(), roleVo.getId())) {
            throw new GlobalException(CodeMsg.ROLE_REPEAT);
        }
        SysRole sysRole = new SysRole(
                roleVo.getName(),
                roleVo.getStatus(),
                roleVo.getType(),
                roleVo.getRemark());
        //sysRole.setOperator(RequestContext.getCurrentSysUser().getUsername());
        sysRole.setOperator("test");
        sysRole.setOperateIp(IpUtil.getRemoteIp(RequestContext.getCurrentRequest()));
        sysRole.setOperateTime(new Date());
        sysRoleMapper.insertSelective(sysRole);
    }

    public void update(RoleVo roleVo) {
        if (checkExist(roleVo.getName(), roleVo.getId())) {
            throw new GlobalException(CodeMsg.ROLE_NOT_EXIST);
        }
        SysRole oldSysRole= sysRoleMapper.selectByPrimaryKey(roleVo.getId());
        SysRole newSysRole = new SysRole(
                roleVo.getId(),
                roleVo.getName(),
                roleVo.getStatus(),
                roleVo.getType(),
                roleVo.getRemark());
        //newSysRole.setOperator(RequestContext.getCurrentSysUser().getUsername());
        newSysRole.setOperator("test");
        newSysRole.setOperateIp(IpUtil.getRemoteIp(RequestContext.getCurrentRequest()));
        newSysRole.setOperateTime(new Date());
        System.out.println(newSysRole);
        sysRoleMapper.updateByPrimaryKeySelective(newSysRole);
        //todo:log
    }

    private boolean checkExist(String name, Integer id) {
        return sysRoleMapper.countByName(name, id)> 0;
    }
}
