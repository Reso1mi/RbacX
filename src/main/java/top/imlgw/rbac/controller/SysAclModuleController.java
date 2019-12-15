package top.imlgw.rbac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.imlgw.rbac.result.CodeMsg;
import top.imlgw.rbac.result.Result;
import top.imlgw.rbac.service.SysAclModuleService;
import top.imlgw.rbac.service.SysTreeService;
import top.imlgw.rbac.validator.NeedLogin;
import top.imlgw.rbac.dto.AclModuleLevelDto;
import top.imlgw.rbac.param.AclModuleParam;

import java.util.List;

/**
 * @author imlgw.top
 * @date 2019/12/6 23:45
 */
@RestController
@RequestMapping("/system/aclModule")
public class SysAclModuleController {

    @Autowired
    private SysAclModuleService sysAclModuleService;


    @Autowired
    private SysTreeService sysTreeService;

    @PostMapping("/save")
    @NeedLogin
    public Result saveAclModule(@Validated @RequestBody AclModuleParam aclModuleParam){
        sysAclModuleService.save(aclModuleParam);
        return Result.success(CodeMsg.SUCCESS);
    }

    @PostMapping("/update")
    @NeedLogin
    public Result updateAclModule(@Validated @RequestBody AclModuleParam aclModuleParam){
        sysAclModuleService.update(aclModuleParam);
        return Result.success(CodeMsg.SUCCESS);
    }


    @GetMapping("/tree.page")
    @NeedLogin
    public Result treeAclModule(){
        List<AclModuleLevelDto> aclModuleTree = sysTreeService.createAclModuleTree();
        return Result.success(aclModuleTree);
    }

    @PostMapping("/delete")
    @NeedLogin
    public Result deleteAclModule(@RequestParam("aclModuleId") Integer aclModuleId){
        sysAclModuleService.delete(aclModuleId);
        return Result.success(CodeMsg.SUCCESS);
    }

}
