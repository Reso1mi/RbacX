package top.imlgw.rbac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.imlgw.rbac.dao.SysAclMapper;
import top.imlgw.rbac.dao.SysRoleAclMapper;
import top.imlgw.rbac.dao.SysRoleUserMapper;
import top.imlgw.rbac.entity.SysAcl;
import top.imlgw.rbac.utils.RequestContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author imlgw.top
 * @date 2019/12/11 18:08
 */
@Service
public class SysCoreService {

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    @Autowired
    private SysRoleAclMapper sysRoleAclMapper;

    @Autowired
    private SysAclMapper sysAclMapper;


    /**
     * @return 获取当前用户的权限点(根据角色查询)
     */
    public List<SysAcl> getCurrentUserAclList() {
        int userId=RequestContext.getCurrentSysUser().getId();
        return getUserAclList(userId);
    }

    /**
     * @param roleId
     * @return 获取某个角色所分配的权限点
     */
    public List<SysAcl> getRoleAclList(int roleId) {
        //直接利用写好的方法,就不重新写了
        List<Integer> aclIdListByRoleId = sysRoleAclMapper.getAclIdListByRoleIdList(Arrays.asList(roleId));
        if (CollectionUtils.isEmpty(aclIdListByRoleId)){
            return Collections.EMPTY_LIST;
        }
        return sysAclMapper.getAclByIds(aclIdListByRoleId);
    }

    /**
     * @param userId
     * @return 获取某个用户的权限点
     */
    public List<SysAcl> getUserAclList(int userId){
        List<Integer> roleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(roleIdList)){
            return Collections.EMPTY_LIST;
        }
        //根据上面获取到的用户的角色ids 查询对应的acls
        List<Integer> aclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(roleIdList);
        if (CollectionUtils.isEmpty(aclIdList)){
            return Collections.EMPTY_LIST;
        }
        return sysAclMapper.getAclByIds(aclIdList);
    }
}
