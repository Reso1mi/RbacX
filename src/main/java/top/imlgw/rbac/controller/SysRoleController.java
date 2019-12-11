package top.imlgw.rbac.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.imlgw.rbac.dao.SysRoleAclMapper;
import top.imlgw.rbac.dto.AclModuleLevelDto;
import top.imlgw.rbac.result.CodeMsg;
import top.imlgw.rbac.result.Result;
import top.imlgw.rbac.service.SysRoleAclService;
import top.imlgw.rbac.service.SysRoleService;
import top.imlgw.rbac.service.SysTreeService;
import top.imlgw.rbac.validator.NeedLogin;
import top.imlgw.rbac.vo.RoleAclParam;
import top.imlgw.rbac.vo.RoleParam;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author imlgw.top
 * @date 2019/12/10 23:17
 */
@RestController
@RequestMapping("/system/role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysTreeService sysTreeService;

    @Autowired
    private SysRoleAclService sysRoleAclService;

    @PostMapping("/save")
    @NeedLogin
    public Result saveRole(@Validated @RequestBody RoleParam roleParam) {
        sysRoleService.save(roleParam);
        return Result.success(CodeMsg.SUCCESS);
    }

    @PostMapping("/update")
    @NeedLogin
    public Result updateRole(@Validated @RequestBody RoleParam roleParam) {
        sysRoleService.update(roleParam);
        return Result.success(CodeMsg.SUCCESS);
    }

    @GetMapping("/list")
    @NeedLogin
    public Result list() {
        return Result.success(sysRoleService.getAll());
    }

    @GetMapping("/tree/{roleId}")
    @NeedLogin
    public Result tree(@NotNull(message = "角色ID不能为空") @PathVariable("roleId") Integer roleId){
        List<AclModuleLevelDto> dtoList = sysTreeService.roleTree(roleId);
        return Result.success(dtoList);
    }

    @PostMapping("/changeAcl")
    //@NeedLogin
    public Result changeAcls(@RequestBody RoleAclParam roleAclParam){
        sysRoleAclService.changeRoleAcls(roleAclParam.getRoleId(),roleAclParam.getAclList());
        return Result.success(CodeMsg.SUCCESS);
    }
}
