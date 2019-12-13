package top.imlgw.rbac.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import top.imlgw.rbac.dto.PageQuery;
import top.imlgw.rbac.entity.SysAcl;

import java.util.List;

@Component
public interface SysAclMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysAcl record);

    int insertSelective(SysAcl record);

    SysAcl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysAcl record);

    int updateByPrimaryKey(SysAcl record);

    int countByModuleId(@Param("aclModuleId") Integer aclModuleId);

    List<SysAcl> selectByModuleId(@Param("aclModuleId") Integer aclModuleId,@Param("page") PageQuery page);

    int countByNameAndAclModuleId(@Param("aclModuleId") Integer aclModuleId,@Param("name") String name,
                                  @Param("seq") Integer seq, @Param("aclId") Integer aclId);

    List<SysAcl> getAllAcl();

    List<SysAcl> getAclListByIds(@Param("aclIdList") List<Integer> aclIdList);

    List<SysAcl> getAclListByUrl(@Param("url") String url);
}