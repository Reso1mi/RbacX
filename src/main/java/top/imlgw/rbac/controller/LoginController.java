package top.imlgw.rbac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.imlgw.rbac.result.CodeMsg;
import top.imlgw.rbac.result.Result;
import top.imlgw.rbac.service.SysUserService;
import top.imlgw.rbac.validator.NeedLogin;
import top.imlgw.rbac.param.LoginParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author imlgw.top
 * @date 2019/12/1 23:02
 */
@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginParam loginParam, HttpServletRequest request, HttpServletResponse response){
        System.err.println(loginParam);
        sysUserService.doLogin(loginParam,request,response);
        return Result.success(CodeMsg.SUCCESS);
    }


    @GetMapping("/logout")
    @NeedLogin
    public Result logout(HttpServletRequest request) {
        sysUserService.doLogout(request);
        return Result.success(CodeMsg.SUCCESS);
    }

}
