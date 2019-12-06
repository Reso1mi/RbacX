package top.imlgw.rbac.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import top.imlgw.rbac.entity.SysAclModule;

import java.util.List;

@Component
public interface SysAclModuleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysAclModule record);

    int insertSelective(SysAclModule record);

    SysAclModule selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysAclModule record);

    int updateByPrimaryKey(SysAclModule record);

    int countByNameAndParentId(@Param("modulename") String name, @Param("parentId") Integer parentId,
                               @Param("seq") Integer seq, @Param("id") Integer id);

    List<SysAclModule> getChildAclModuleListByLevel(@Param("level") String level);
}