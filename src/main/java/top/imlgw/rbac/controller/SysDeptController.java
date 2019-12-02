package top.imlgw.rbac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.imlgw.rbac.result.CodeMsg;
import top.imlgw.rbac.result.Result;
import top.imlgw.rbac.service.SysDeptService;
import top.imlgw.rbac.service.SysTreeService;
import top.imlgw.rbac.validator.NeedLogin;
import top.imlgw.rbac.vo.DeptLevelVo;
import top.imlgw.rbac.vo.DeptVo;

import java.util.List;

/**
 * @author imlgw.top
 * @date 2019/11/21 20:21
 */
@Controller
@RequestMapping("/system/dept")
public class SysDeptController {

    @Autowired
    private SysDeptService sysDeptService;

    @Autowired
    private SysTreeService sysTreeService;

    @RequestMapping("/tree")
    @ResponseBody
    @NeedLogin
    public Result treeDept(){
        List<DeptLevelVo> deptTree = sysTreeService.createDeptTree();
        return Result.success(deptTree);
    }

    @PostMapping("/save")
    @ResponseBody
    @NeedLogin
    public Result saveDept(@Validated @RequestBody DeptVo deptVo){
        sysDeptService.save(deptVo);
        return Result.success(CodeMsg.SUCCESS);
    }


    //todo
    @PostMapping("/delete")
    @ResponseBody
    @NeedLogin
    public Result deleteDept(Integer id){
        //todo 删除和用户模块相关,等用户模块开发完成再实现
        sysDeptService.delete(id);
        return Result.success(CodeMsg.SUCCESS);
    }

    @PostMapping("/update")
    @ResponseBody
    @NeedLogin
    public Result updateDept(@Validated @RequestBody DeptVo deptVo){
        sysDeptService.update(deptVo);
        System.out.println(deptVo);
        return Result.success(CodeMsg.SUCCESS);
    }
}
