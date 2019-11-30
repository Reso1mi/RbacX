package top.imlgw.rbac.dao;

import org.springframework.stereotype.Component;
import top.imlgw.rbac.entity.SysUser;

@Component //只是为了不报红....接口上的注解会被忽略
public interface SysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);
}