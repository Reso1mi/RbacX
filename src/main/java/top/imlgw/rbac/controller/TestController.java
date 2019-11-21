package top.imlgw.rbac.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imlgw.rbac.dao.SysAclModuleMapper;
import top.imlgw.rbac.entity.SysAclModule;
import top.imlgw.rbac.result.Result;
import top.imlgw.rbac.utils.SpringContextUtil;

/**
 * @author imlgw.top
 * @date 2019/11/21 15:13
 */
@RequestMapping("test")
@RestController
@Slf4j
public class TestController {

    @RequestMapping("hello")
    public Result test(){
        //throw new PermissionException(CodeMsg.BIND_ERROR);
        //throw new RuntimeException("dadsadsadasd");
        SysAclModuleMapper bean = SpringContextUtil.getBean(SysAclModuleMapper.class);
        SysAclModule sysAclModule = bean.selectByPrimaryKey(1);
        log.info(sysAclModule.getName());
        return  Result.success(sysAclModule);
    }
}
