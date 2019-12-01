package top.imlgw.rbac.controller;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imlgw.rbac.result.CodeMsg;
import top.imlgw.rbac.result.Result;
import top.imlgw.rbac.service.SysUserService;
import top.imlgw.rbac.vo.UserVo;


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
    public Result saveDept(@Validated @RequestBody UserVo userVo){
        sysUserService.save(userVo);
        return Result.success(CodeMsg.SUCCESS);
    }


    @PostMapping("/update")
    public Result updateDept(@Validated @RequestBody UserVo userVo){
        sysUserService.update(userVo);
        return Result.success(CodeMsg.SUCCESS);
    }
}
