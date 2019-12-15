package top.imlgw.rbac.service;

import org.apache.ibatis.javassist.util.HotSwapAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.imlgw.rbac.dao.*;
import top.imlgw.rbac.entity.SysAcl;
import top.imlgw.rbac.entity.SysRole;
import top.imlgw.rbac.entity.SysUser;
import top.imlgw.rbac.utils.RequestContext;

import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;


    /**
     * @return 获取当前用户的权限点(根据角色查询)
     */
    public List<SysAcl> getCurrentUserAclList() {
        int userId=RequestContext.getCurrentSysUser().getId();
        //todo : cache the acl&user in the redis
        return getAclListByUserId(userId);
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
        return sysAclMapper.getAclListByIds(aclIdListByRoleId);
    }

    /**
     * @param aclId
     * @return 获取某个权限点被授予的角色
     */
    public List<SysRole> getRoleListByAclId(Integer aclId) {
        //当前权限点对应的所有的角色ID
        List<Integer> roleIds= sysRoleAclMapper.getRoleIdListByAclId(aclId);
        if (CollectionUtils.isEmpty(roleIds)) {
            return Collections.EMPTY_LIST;
        }
        //根据角色ID获取角色实体类
        return sysRoleMapper.getRoleByRoleIds(roleIds);
    }


    /**
     * @param userId
     * @return 获取某个用户所分配的权限点
     */
    public List<SysAcl> getAclListByUserId(int userId){
        List<Integer> roleIds = sysRoleUserMapper.getRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(roleIds)){
            return Collections.EMPTY_LIST;
        }
        //根据上面获取到的用户的角色ids 查询对应的acls
        List<Integer> aclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(roleIds);
        if (CollectionUtils.isEmpty(aclIdList)){
            return Collections.EMPTY_LIST;
        }
        return sysAclMapper.getAclListByIds(aclIdList);
    }

    /**
     * @param aclId
     * @return 获取拥有某个权限点的用户
     */
    public List<SysUser> getUserListByAclId(int aclId){
        List<Integer> roleIdList = sysRoleAclMapper.getRoleIdListByAclId(aclId);
        if (CollectionUtils.isEmpty(roleIdList)){
            return Collections.EMPTY_LIST;
        }
        //根据上面获取到的用户的角色roleIds 查询对应的userIds
        List<Integer> userIds= sysRoleUserMapper.getUserIdListByRoleIdList(roleIdList);
        if (CollectionUtils.isEmpty(userIds)){
            return Collections.EMPTY_LIST;
        }
        return sysUserMapper.getUsersByIds(userIds);
    }


    /**
     * @param roleId
     * @return 获取某个角色已分配给的用户
     */
    public List<SysUser> getUserListByRoleId(Integer roleId){
        List<Integer> userIdListByRoleId = sysRoleUserMapper.getUserIdListByRoleId(roleId);
        if (CollectionUtils.isEmpty(userIdListByRoleId)){
            return Collections.EMPTY_LIST;
        }
        return sysUserMapper.getUsersByIds(userIdListByRoleId);
    }


    /**
     * @param userId
     * @return 获取某个用户被分配的角色
     */
    public List<SysRole> getRoleListByUserId(Integer userId) {
        //当前用户所有的角色ID
        List<Integer> roleIds= sysRoleUserMapper.getRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(roleIds)) {
            return Collections.EMPTY_LIST;
        }
        //根据角色ID获取角色实体类
        return sysRoleMapper.getRoleByRoleIds(roleIds);
    }

    public boolean hasAcl(String requestURL) {
        if (isSuperAdmin()){
            return true;
        }
        List<SysAcl> acls=sysAclMapper.getAclListByUrl(requestURL);
        if (CollectionUtils.isEmpty(acls)){ //没有权限点限制该url
            return true;
        }
        //md fix bug
        Set<Integer> userAclSet = getCurrentUserAclList().stream().map(SysAcl::getId).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(userAclSet)){
            return false;
        }
        //currentAcls.stream().map(SysAcl::getId).filter(aclSet::contains).count();
        boolean hasValidAcl=false; //没有合法的权限点
        for (SysAcl acl : acls) {
            if (acl.getStatus()==-1){
                continue;
            }
            hasValidAcl=true; //fix bug
            if (acl.getStatus()!=-1&&userAclSet.contains(acl.getId())){
                return true;
            }
        }
        return !hasValidAcl;
    }


    /**
     * @return 是否是超级管理员
     *
     *
     * 超级管理员是在权限还没分配的时候可以将这些权限分配出去的人
     */
    public boolean isSuperAdmin() {
        SysUser sysUser = RequestContext.getCurrentSysUser();
        if (sysUser.getMail().contains("1111")) {
            return true;
        }
        return false;
    }
}
