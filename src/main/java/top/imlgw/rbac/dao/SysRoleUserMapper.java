package top.imlgw.rbac.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import top.imlgw.rbac.entity.SysRoleUser;

import java.util.List;

@Component
public interface SysRoleUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRoleUser record);

    int insertSelective(SysRoleUser record);

    SysRoleUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRoleUser record);

    int updateByPrimaryKey(SysRoleUser record);

    void deleteRoleUserByRoleId(@Param("roleId") Integer roleId);

    List<Integer> getRoleIdListByUserId(@Param("userId") Integer userId);

    List<Integer> getUserIdListByRoleId(@Param("roleId") Integer roleId);

    List<Integer> getUserIdListByRoleIdList(@Param("roleIds") List<Integer> roleIds);

}