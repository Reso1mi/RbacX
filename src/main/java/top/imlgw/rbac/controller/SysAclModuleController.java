package top.imlgw.rbac.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.imlgw.rbac.result.CodeMsg;
import top.imlgw.rbac.result.Result;
import top.imlgw.rbac.validator.NeedLogin;
import top.imlgw.rbac.vo.AclModuleVo;

/**
 * @author imlgw.top
 * @date 2019/12/6 23:45
 */
@Controller
@RequestMapping("/system/aclModule")
public class SysAclModuleController {


    @PostMapping("/save")
    @ResponseBody
    @NeedLogin
    public Result saveDept(@Validated @RequestBody AclModuleVo aclModuleVo){
        return Result.success(CodeMsg.SUCCESS);
    }

    @PostMapping("/update")
    @ResponseBody
    @NeedLogin
    public Result updateDept(@Validated @RequestBody AclModuleVo aclModuleVo){
        return Result.success(CodeMsg.SUCCESS);
    }

}
