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
import top.imlgw.rbac.vo.DeptParam;

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

    @GetMapping("/tree")
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


    //todo
    @PostMapping("/delete")
    @NeedLogin
    public Result deleteDept(Integer id){
        //todo 删除和用户模块相关,等用户模块开发完成再实现
        sysDeptService.delete(id);
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
