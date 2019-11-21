package top.imlgw.rbac.vo;

import org.springframework.beans.BeanUtils;
import top.imlgw.rbac.entity.SysDept;

import java.util.ArrayList;
import java.util.List;

/**
 * @author imlgw.top
 * @date 2019/11/21 21:26
 */
public class DeptLevelDto extends SysDept {
    private List<DeptLevelDto> deptList= new ArrayList<>();

    public static DeptLevelDto adapt(SysDept sysDept){
        DeptLevelDto deptLevelDto=new DeptLevelDto();
        BeanUtils.copyProperties(sysDept,deptLevelDto);
        return  deptLevelDto;
    }
}
