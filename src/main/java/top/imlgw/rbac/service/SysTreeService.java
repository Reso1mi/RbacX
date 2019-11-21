package top.imlgw.rbac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.imlgw.rbac.dao.SysDeptMapper;
import top.imlgw.rbac.entity.SysDept;
import top.imlgw.rbac.vo.DeptLevelDto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author imlgw.top
 * @date 2019/11/21 21:31
 */
@Service
public class SysTreeService {
    @Autowired
    private SysDeptMapper sysDeptMapper;

    public List<DeptLevelDto> deptTree(){
        List<SysDept> allDept = sysDeptMapper.getAllDept();
        List<DeptLevelDto> deptDtoList = new ArrayList<>();
        for (SysDept sysDept : allDept) {
            DeptLevelDto deptLevelDto=DeptLevelDto.adapt(sysDept);
            deptDtoList.add(deptLevelDto);
        }
        return  deptDtoList;
    }

    public List<DeptLevelDto> deptList2Tree(List<DeptLevelDto> deptLevelDtos){
        if (CollectionUtils.isEmpty(deptLevelDtos)){
            return new ArrayList<>();
        }
        return new ArrayList<>();
    }
}
