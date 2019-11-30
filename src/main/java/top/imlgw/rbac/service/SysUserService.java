package top.imlgw.rbac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imlgw.rbac.dao.SysUserMapper;
import top.imlgw.rbac.entity.SysUser;
import top.imlgw.rbac.exception.GlobalException;
import top.imlgw.rbac.result.CodeMsg;
import top.imlgw.rbac.vo.UserVo;

/**
 * @author imlgw.top
 * @date 2019/11/30 23:44
 */
@Service
public class SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    public void save(UserVo userVo){
        if (phoneExist(userVo.getTelephone(),userVo.getId())){
            throw new GlobalException(CodeMsg.PHONE_REPEAT);
        }
        if (mailExist(userVo.getMail(),userVo.getId())){
            throw new GlobalException(CodeMsg.MAIL_REPEAT);
        }
        //todo
        String password="123456";
        SysUser sysUser = new SysUser();

    }

    private boolean phoneExist(String phone,Integer userId) {
        return  false;
    }

    private boolean mailExist(String mail,Integer userId) {
        return  false;
    }
}
