package top.imlgw.rbac.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;
import top.imlgw.rbac.entity.SysDept;

import java.util.ArrayList;
import java.util.List;

/**
 * @author imlgw.top
 * @date 2019/11/21 21:26
 */
@Getter
@Setter
@ToString
public class DeptLevelVo extends SysDept {

    //添加一个List属性,形成部门树的结构
    private List<DeptLevelVo> deptList= new ArrayList<>();

    public static DeptLevelVo adapt(SysDept sysDept){
        DeptLevelVo deptLevelVo =new DeptLevelVo();
        //copybean的工具,还挺好用
        BeanUtils.copyProperties(sysDept, deptLevelVo);
        return deptLevelVo;
    }
}
