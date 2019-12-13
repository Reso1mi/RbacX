package top.imlgw.rbac.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import top.imlgw.rbac.dao.SysRoleUserMapper;
import top.imlgw.rbac.entity.SysRoleUser;
import top.imlgw.rbac.utils.IpUtil;
import top.imlgw.rbac.utils.RequestContext;

import java.util.*;

/**
 * @author imlgw.top
 * @date 2019/12/12 14:37
 */
@Service
public class SysRoleUserService {

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    public void changeRoleUsers(Integer roleId, List<Integer> userIds) {
        //获取修改前角色下对应的用户
        List<Integer> originUserIds= sysRoleUserMapper.getUserIdListByRoleId(roleId);
        if (!CollectionUtils.isEmpty(originUserIds) && !CollectionUtils.isEmpty(userIds)
                && originUserIds.size() == userIds.size()) {
            Set<Integer> originAclIdSet = new HashSet(originUserIds);
            Set<Integer> aclIdSet = new HashSet(userIds);
            originAclIdSet.removeAll(aclIdSet);
            if (CollectionUtils.isEmpty(originAclIdSet)) { //说明没有变化
                return;
            }
        }
        updateRoleUsers(roleId, userIds);
    }

    @Transactional
    public void updateRoleUsers(Integer roleId, List<Integer> userIds) {
        //删除原本的 角色-用户 对应关系
        sysRoleUserMapper.deleteRoleUserByRoleId(roleId);
        if (CollectionUtils.isEmpty(userIds)){
            return;
        }
        //构建新的usersIds
        List<SysRoleUser> roleUsers=new ArrayList<>();
        userIds.stream().map(userId->new SysRoleUser(roleId,userId,RequestContext.getCurrentSysUser().getUsername(),
                new Date(),IpUtil.getRemoteIp(RequestContext.getCurrentRequest())))
                .forEach(roleUsers::add);
        //批量插入
        roleUsers.forEach(sysRoleUserMapper::insertSelective);
    }
}
