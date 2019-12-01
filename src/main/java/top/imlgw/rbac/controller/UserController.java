package top.imlgw.rbac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imlgw.rbac.result.CodeMsg;
import top.imlgw.rbac.result.Result;
import top.imlgw.rbac.service.SysUserService;
import top.imlgw.rbac.vo.LoginVo;

/**
 * @author imlgw.top
 * @date 2019/12/1 23:02
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/login")
    public Result login(@Validated LoginVo loginVo){
        System.err.println(loginVo);
        sysUserService.doLogin(loginVo);
        return Result.success(CodeMsg.SUCCESS);
    }


}
