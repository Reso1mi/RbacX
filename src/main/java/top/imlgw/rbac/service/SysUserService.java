package top.imlgw.rbac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imlgw.rbac.dao.SysUserMapper;
import top.imlgw.rbac.entity.SysUser;
import top.imlgw.rbac.exception.GlobalException;
import top.imlgw.rbac.result.CodeMsg;
import top.imlgw.rbac.utils.PasswordUtil;
import top.imlgw.rbac.vo.LoginVo;
import top.imlgw.rbac.vo.UserVo;

import java.util.Date;
import java.util.Optional;

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
        //TODO
        String password=PasswordUtil.encode("123456");
        SysUser sysUser = new SysUser(userVo.getUsername(),
                userVo.getTelephone(), userVo.getMail(),
                password, userVo.getDeptId(),
                userVo.getStatus(), userVo.getRemark());
        sysUser.setOperator("system");
        sysUser.setOperateIp("127.0.0.1");
        sysUser.setOperateTime(new Date());
        //TODO: sendEmail
        sysUserMapper.insertSelective(sysUser);
    }

    public void update(UserVo userVo){
        if (phoneExist(userVo.getTelephone(),userVo.getId())){
            throw new GlobalException(CodeMsg.PHONE_REPEAT);
        }
        if (mailExist(userVo.getMail(),userVo.getId())){
            throw new GlobalException(CodeMsg.MAIL_REPEAT);
        }
        SysUser oldSysUser = sysUserMapper.selectByPrimaryKey(userVo.getId());
        if (oldSysUser==null){
            throw new GlobalException(CodeMsg.USER_NOT_EXIST);
        }
        //update
        SysUser newSysUser=new SysUser(userVo.getId(),userVo.getUsername(),userVo.getTelephone(),userVo.getMail(),userVo.getDeptId(),userVo.getStatus(),userVo.getRemark());
        //有选择的update
        sysUserMapper.updateByPrimaryKeySelective(newSysUser);
    }

    //todo
    private boolean phoneExist(String phone,Integer userId) {
        return  false;
    }

    //todo
    private boolean mailExist(String mail,Integer userId) {
        return  false;
    }


    public void doLogin(LoginVo loginVo){
        String username=loginVo.getUsername();
        String password = loginVo.getPassword();
        SysUser dbUser = findUserByMailOrPhone(username);
        if (dbUser==null){
            throw new GlobalException(CodeMsg.USER_NOT_EXIST);
        }
        if (!PasswordUtil.matches(password,dbUser.getPassword())){
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
    }

    private SysUser findUserByMailOrPhone(String keyword){
        return null;
    }
}
