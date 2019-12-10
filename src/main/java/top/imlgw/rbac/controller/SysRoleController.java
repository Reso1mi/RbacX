package top.imlgw.rbac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.imlgw.rbac.result.CodeMsg;
import top.imlgw.rbac.result.Result;
import top.imlgw.rbac.service.SysRoleService;
import top.imlgw.rbac.service.SysTreeService;
import top.imlgw.rbac.validator.NeedLogin;
import top.imlgw.rbac.vo.RoleVo;

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

    @PostMapping("/save")
    //@NeedLogin
    public Result saveRole(@Validated @RequestBody RoleVo roleVo) {
        sysRoleService.save(roleVo);
        return Result.success(CodeMsg.SUCCESS);
    }

    @PostMapping("/update")
    //@NeedLogin
    public Result updateRole(@Validated @RequestBody RoleVo roleVo) {
        sysRoleService.update(roleVo);
        return Result.success(CodeMsg.SUCCESS);
    }

    @GetMapping("/list")
    //@NeedLogin
    public Result list() {
        return Result.success(sysRoleService.getAll());
    }
}
