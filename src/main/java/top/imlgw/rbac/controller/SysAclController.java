package top.imlgw.rbac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.imlgw.rbac.dto.PageQuery;
import top.imlgw.rbac.entity.SysAcl;
import top.imlgw.rbac.entity.SysRole;
import top.imlgw.rbac.entity.SysUser;
import top.imlgw.rbac.result.CodeMsg;
import top.imlgw.rbac.result.PageResult;
import top.imlgw.rbac.result.Result;
import top.imlgw.rbac.service.SysAclService;
import top.imlgw.rbac.service.SysCoreService;
import top.imlgw.rbac.validator.NeedLogin;
import top.imlgw.rbac.param.AclParam;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;

/**
 * @author imlgw.top
 * @date 2019/12/6 23:44
 */
@RestController
@RequestMapping("/system/acl")
public class SysAclController {

    @Autowired
    private SysAclService sysAclService;

    @Autowired
    private SysCoreService sysCoreService;

    @PostMapping("/save")
    @NeedLogin
    public Result saveAclModule(@Validated @RequestBody AclParam aclParam) {
        sysAclService.save(aclParam);
        return Result.success(CodeMsg.SUCCESS);
    }

    @PostMapping("/update")
    @NeedLogin
    public Result updateAclModule(@Validated @RequestBody AclParam aclParam) {
        sysAclService.update(aclParam);
        return Result.success(CodeMsg.SUCCESS);
    }

    @GetMapping("/list/{aclModuleId}")
    @NeedLogin
    public Result updateDept(@NotNull(message = "模块id不能为空!") @PathVariable(name = "aclModuleId") Integer aclModuleId,
                             PageQuery pageQuery) {
        PageResult<SysAcl> aclList = sysAclService.getAclByModuleId(aclModuleId, pageQuery);
        return Result.success(aclList);
    }

    @GetMapping("/role_user/{aclId}")
    @NeedLogin
    public Result roleAndUser(@NotNull(message = "权限ID不能为空!") @PathVariable("aclId") Integer aclId){
        HashMap<String,Object> map=new HashMap<>();
        List<SysRole> roles = sysCoreService.getRoleListByAclId(aclId);
        List<SysUser> users= sysCoreService.getUserListByAclId(aclId);
        map.put("roles",roles);
        map.put("users",users);
        return Result.success(map);
    }
}
