package top.imlgw.rbac.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import top.imlgw.rbac.entity.SysRole;

import java.util.List;

@Component
public interface SysRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    int countByName(@Param("name") String name, @Param("id") Integer id);

    List<SysRole> getAllRole();

    List<SysRole> getRoleByUserIds(@Param("roleIds") List<Integer> roleIds);
}