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
import top.imlgw.rbac.vo.PageQueryVo;
import top.imlgw.rbac.vo.UserVo;

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
    public Result saveUser(@Validated @RequestBody UserVo userVo){
        sysUserService.save(userVo);
        return Result.success(CodeMsg.SUCCESS);
    }


    @PostMapping("/update")
    @NeedLogin
    public Result updateUser(@Validated @RequestBody UserVo userVo){
        sysUserService.update(userVo);
        return Result.success(CodeMsg.SUCCESS);
    }


    @GetMapping("/list/{deptId}")
    @NeedLogin
    public Result updateDept(@NotNull(message = "部门id不能为空！") @PathVariable(name = "deptId") Integer deptId, PageQueryVo pageQueryVo){
        PageResult<SysUser> usersByDept = sysUserService.getUsersByDept(deptId, pageQueryVo);
        return Result.success(usersByDept);
    }

}
