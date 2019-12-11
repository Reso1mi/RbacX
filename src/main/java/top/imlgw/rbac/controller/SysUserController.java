package top.imlgw.rbac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.imlgw.rbac.entity.SysUser;
import top.imlgw.rbac.result.CodeMsg;
import top.imlgw.rbac.result.PageResult;
import top.imlgw.rbac.result.Result;
import top.imlgw.rbac.service.SysUserService;
import top.imlgw.rbac.validator.NeedLogin;
import top.imlgw.rbac.dto.PageQuery;
import top.imlgw.rbac.vo.UserParam;

import javax.validation.constraints.NotNull;


/**
 * @author imlgw.top
 * @date 2019/11/30 22:07
 */
@RestController
@RequestMapping("/system/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/save")
    @NeedLogin
    public Result saveUser(@Validated @RequestBody UserParam userParam){
        sysUserService.save(userParam);
        return Result.success(CodeMsg.SUCCESS);
    }


    @PostMapping("/update")
    @NeedLogin
    public Result updateUser(@Validated @RequestBody UserParam userParam){
        sysUserService.update(userParam);
        return Result.success(CodeMsg.SUCCESS);
    }


    @GetMapping("/list/{deptId}")
    @NeedLogin
    public Result updateDept(@NotNull(message = "部门id不能为空！") @PathVariable(name = "deptId") Integer deptId, PageQuery pageQuery){
        PageResult<SysUser> usersByDept = sysUserService.getUsersByDept(deptId, pageQuery);
        return Result.success(usersByDept);
    }

}
