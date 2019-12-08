package top.imlgw.rbac.dao;

import org.springframework.stereotype.Component;
import top.imlgw.rbac.entity.SysAcl;

@Component
public interface SysAclMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysAcl record);

    int insertSelective(SysAcl record);

    SysAcl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysAcl record);

    int updateByPrimaryKey(SysAcl record);
}