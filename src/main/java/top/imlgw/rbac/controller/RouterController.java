package top.imlgw.rbac.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import top.imlgw.rbac.validator.NeedLogin;

/**
 * @author imlgw.top
 * @date 2019/12/2 18:47
 */
@Controller
public class AdminController {

    @RequestMapping("/admin")
    @NeedLogin
    public String index(){
        return "admin";
    }

    @RequestMapping("/dept")
    @NeedLogin
    public String dept(){
        return "dept";
    }
}
