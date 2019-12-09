package top.imlgw.rbac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.imlgw.rbac.bean.PageQuery;
import top.imlgw.rbac.entity.SysAcl;
import top.imlgw.rbac.entity.SysUser;
import top.imlgw.rbac.result.CodeMsg;
import top.imlgw.rbac.result.PageResult;
import top.imlgw.rbac.result.Result;
import top.imlgw.rbac.service.SysAclService;
import top.imlgw.rbac.validator.NeedLogin;
import top.imlgw.rbac.vo.AclVo;

import javax.validation.constraints.NotNull;

/**
 * @author imlgw.top
 * @date 2019/12/6 23:44
 */
@RestController
@RequestMapping("/system/acl")
public class SysAclController {
    @Autowired
    private SysAclService sysAclService;

    @PostMapping("/save")
    @NeedLogin
    public Result saveAclModule(@Validated @RequestBody AclVo aclVo) {
        sysAclService.save(aclVo);
        return Result.success(CodeMsg.SUCCESS);
    }

    @PostMapping("/update")
    @NeedLogin
    public Result updateAclModule(@Validated @RequestBody AclVo aclVo) {
        sysAclService.update(aclVo);
        return Result.success(CodeMsg.SUCCESS);
    }

    @GetMapping("/list/{aclModuleId}")
    @NeedLogin
    public Result updateDept(@NotNull(message = "模块id不能为空！") @PathVariable(name = "aclModuleId") Integer aclModuleId,
                             PageQuery pageQuery) {
        PageResult<SysAcl> aclList = sysAclService.getAclByModuleId(aclModuleId, pageQuery);
        return Result.success(aclList);
    }
}
