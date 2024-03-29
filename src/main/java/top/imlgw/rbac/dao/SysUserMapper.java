package top.imlgw.rbac.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import top.imlgw.rbac.entity.SysUser;
import top.imlgw.rbac.dto.PageQuery;

import java.util.List;

@Component //只是为了不报红....接口上的注解会被忽略
public interface SysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    List<SysUser> selectByDeptId(@Param("deptId") Integer id,@Param("page") PageQuery page);

    int countByDeptId(@Param("deptId") Integer deptId);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser findUserByMailOrPhone(@Param("keyword") String keyword);

    int countByMail(@Param("mail") String mail,@Param("id") Integer id);

    int countByTelephone(@Param("telephone") String telephone,@Param("id") Integer id);

    List<SysUser> getUsersByIds(@Param("userIds") List<Integer> userIds);

    List<SysUser> getAllUser();

}