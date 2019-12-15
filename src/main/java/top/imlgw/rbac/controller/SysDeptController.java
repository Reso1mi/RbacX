package top.imlgw.rbac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.imlgw.rbac.result.CodeMsg;
import top.imlgw.rbac.result.Result;
import top.imlgw.rbac.service.SysDeptService;
import top.imlgw.rbac.service.SysTreeService;
import top.imlgw.rbac.validator.NeedLogin;
import top.imlgw.rbac.dto.DeptLevelDto;
import top.imlgw.rbac.param.DeptParam;
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

    @GetMapping("/tree.page")
    @NeedLogin
    public Result treeDept(){
        List<DeptLevelDto> deptTree = sysTreeService.createDeptTree();
        return Result.success(deptTree);
    }

    @PostMapping("/save")
    @NeedLogin
    public Result saveDept(@Validated @RequestBody DeptParam deptParam){
        sysDeptService.save(deptParam);
        return Result.success(CodeMsg.SUCCESS);
    }


    @PostMapping("/delete")
    @NeedLogin
    public Result deleteDept(@RequestParam("deptId") Integer deptId){
        sysDeptService.delete(deptId);
        return Result.success(CodeMsg.SUCCESS);
    }

    @PostMapping("/update")
    @NeedLogin
    public Result updateDept(@Validated @RequestBody DeptParam deptParam){
        sysDeptService.update(deptParam);
        System.out.println(deptParam);
        return Result.success(CodeMsg.SUCCESS);
    }
}
