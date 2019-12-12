package top.imlgw.rbac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.imlgw.rbac.dao.SysRoleAclMapper;
import top.imlgw.rbac.entity.SysRoleAcl;
import top.imlgw.rbac.utils.IpUtil;
import top.imlgw.rbac.utils.RequestContext;
import java.util.*;

/**
 * @author imlgw.top
 * @date 2019/12/11 23:27
 */
@Service
public class SysRoleAclService {

    @Autowired
    private SysRoleAclMapper sysRoleAclMapper;

    public void changeRoleAcls(Integer roleId, List<Integer> aclIdList) {
        //获取修改前角色的aclIds
        List<Integer> originAclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(Arrays.asList(roleId));

        if (originAclIdList.size() == aclIdList.size()) {
            Set<Integer> originAclIdSet = new HashSet(originAclIdList);
            Set<Integer> aclIdSet = new HashSet(aclIdList);
            originAclIdSet.removeAll(aclIdSet);
            if (CollectionUtils.isEmpty(originAclIdSet)) { //说明没有变化
                return;
            }
        }
        updateRoleAcls(roleId, aclIdList);
    }

    private void updateRoleAcls(Integer roleId, List<Integer> aclIdList) {
        //删除原本的角色ACL对应关系
        sysRoleAclMapper.deleteRoleAclByRoleId(roleId);
        if (CollectionUtils.isEmpty(aclIdList)){
            return;
        }
        //重新构建新的roleAcl对应关系
        List<SysRoleAcl> roleAcls =new ArrayList<>();
        aclIdList.stream()
                .map(aclId->new SysRoleAcl(roleId,aclId, RequestContext.getCurrentSysUser().getUsername(), IpUtil.getRemoteIp(RequestContext.getCurrentRequest()),new Date()))
                .forEach(roleAcls::add);
        //批量插入
        roleAcls.forEach(sysRoleAclMapper::insertSelective);
    }
}
