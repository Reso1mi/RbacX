package top.imlgw.rbac.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import top.imlgw.rbac.validator.NeedLogin;

/**
 * @author imlgw.top
 * @date 2019/12/2 18:47
 */
@Controller
public class RouterController {

    @GetMapping("/admin")
    @NeedLogin
    public String admin(){
        return "admin";
    }

    @GetMapping("/dept")
    @NeedLogin
    public String dept(){
        return "dept";
    }


    @GetMapping("/acl")
    @NeedLogin
    public String acl(){
        return "acl";
    }
}
