package top.imlgw.rbac.controller;

import com.sun.org.apache.bcel.internal.classfile.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.imlgw.rbac.result.CodeMsg;
import top.imlgw.rbac.result.Result;
import top.imlgw.rbac.service.SysAclService;
import top.imlgw.rbac.vo.AclVo;

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
    @ResponseBody
    public Result saveAclModule(@Validated @RequestBody AclVo aclVo) {
        sysAclService.save(aclVo);
        return Result.success(CodeMsg.SUCCESS);
    }

    @PostMapping("/update")
    @ResponseBody
    public Result updateAclModule(@Validated @RequestBody AclVo aclVo) {
        sysAclService.update(aclVo);
        return Result.success(CodeMsg.SUCCESS);
    }
}
