package top.imlgw.rbac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.imlgw.rbac.result.CodeMsg;
import top.imlgw.rbac.result.Result;
import top.imlgw.rbac.service.SysDeptService;
import top.imlgw.rbac.service.SysTreeService;
import top.imlgw.rbac.vo.DeptLevelDto;
import top.imlgw.rbac.vo.DeptVo;

import java.util.List;

/**
 * @author imlgw.top
 * @date 2019/11/21 20:21
 */
@RestController
@RequestMapping("/system/dept")
public class SysDeptController {

    @Autowired
    private SysDeptService sysDeptService;

    @Autowired
    private SysTreeService sysTreeService;

    @RequestMapping("/tree")
    @ResponseBody
    public Result treeDept(){
        List<DeptLevelDto> deptTree = sysTreeService.createDeptTree();
        return Result.success(deptTree);
    }


    @PostMapping("/save")
    @ResponseBody
    public Result saveDept(@Validated @RequestBody DeptVo deptVo){
        sysDeptService.save(deptVo);
        return Result.success(CodeMsg.SUCCESS);
    }

    @PostMapping("/update")
    @ResponseBody
    public Result updateDept(@Validated @RequestBody DeptVo deptVo){
        sysDeptService.update(deptVo);
        System.out.println(deptVo);
        return Result.success(CodeMsg.SUCCESS);
    }
}
