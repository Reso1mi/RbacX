package top.imlgw.rbac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.imlgw.rbac.dto.AclModuleLevelDto;
import top.imlgw.rbac.entity.SysUser;
import top.imlgw.rbac.result.CodeMsg;
import top.imlgw.rbac.result.Result;
import top.imlgw.rbac.service.*;
import top.imlgw.rbac.validator.NeedLogin;
import top.imlgw.rbac.param.RoleAclParam;
import top.imlgw.rbac.param.RoleParam;
import top.imlgw.rbac.param.RoleUserParam;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private SysRoleUserService sysRoleUserService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysCoreService sysCoreService;

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

    @GetMapping("/list.page")
    @NeedLogin
    public Result list() {
        return Result.success(sysRoleService.getAll());
    }

    @GetMapping("/tree/{roleId}")
    @NeedLogin
    public Result tree(@NotNull(message = "角色ID不能为空") @PathVariable("roleId") Integer roleId){
        List<AclModuleLevelDto> dtoList = sysTreeService.roleAclTree(roleId);
        return Result.success(dtoList);
    }

    @PostMapping("/changeAcl")
    @NeedLogin
    public Result changeAcls(@RequestBody RoleAclParam roleAclParam){
        System.out.println(roleAclParam);
        sysRoleAclService.changeRoleAcls(roleAclParam.getRoleId(),roleAclParam.getAclIds());
        return Result.success(CodeMsg.SUCCESS);
    }


    @PostMapping("/changeUser")
    @NeedLogin
    public Result changeRoleUsers(@RequestBody RoleUserParam roleUserParam){
        System.out.println(roleUserParam);
        sysRoleUserService.changeRoleUsers(roleUserParam.getRoleId(),roleUserParam.getUserIds());
        return Result.success(CodeMsg.SUCCESS);
    }

    @GetMapping("/user/{roleId}")
    @NeedLogin
    public Result users(@PathVariable("roleId") int roleId){
        //根据角色ID查询出当前角色对应的所有的用户
        List<SysUser> selectedUsers = sysCoreService.getUserListByRoleId(roleId);
        //所有用户
        List<SysUser> allUser = sysUserService.getAllUser();
        List<SysUser> unselectedUsers = new ArrayList<>();
        //将用户列表转为UserIdSet
        Set<Integer> selectUser= selectedUsers.stream().map(SysUser::getId).collect(Collectors.toSet());
        allUser.stream().filter(sysUser -> sysUser.getStatus()==1 && !selectUser.contains(sysUser.getId())).forEach(unselectedUsers::add);
        //用map进行封装
        Map<String,List<SysUser>> map=new HashMap<>();
        map.put("selected",selectedUsers);
        map.put("unselected",unselectedUsers);
        return Result.success(map);
    }
}
