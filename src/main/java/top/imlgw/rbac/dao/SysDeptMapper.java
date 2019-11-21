package top.imlgw.rbac.dao;

import org.springframework.stereotype.Component;
import top.imlgw.rbac.entity.SysDept;

import java.util.List;

@Component //只是为了不报红....接口上的注解会被忽略
public interface SysDeptMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysDept record);

    int insertSelective(SysDept record);

    SysDept selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysDept record);

    int updateByPrimaryKey(SysDept record);

    List<SysDept> getAllDept();
}