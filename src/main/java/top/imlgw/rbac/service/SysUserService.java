package top.imlgw.rbac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imlgw.rbac.dao.SysUserMapper;
import top.imlgw.rbac.entity.SysUser;
import top.imlgw.rbac.exception.GlobalException;
import top.imlgw.rbac.result.CodeMsg;
import top.imlgw.rbac.utils.PasswordUtil;
import top.imlgw.rbac.utils.UUIDUtil;
import top.imlgw.rbac.vo.LoginVo;
import top.imlgw.rbac.vo.UserVo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @author imlgw.top
 * @date 2019/11/30 23:44
 */
@Service
public class SysUserService {

    public static final String TOKEN_NAME="rbacx";

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 保存用户
     * @param userVo
     */
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

    /**
     * 修改用户
     * @param userVo
     */
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

    /*
    * 检查用户是否存在
    * */
    private boolean phoneExist(String phone,Integer userId) {
        return  sysUserMapper.countByTelephone(phone,userId)>0;
    }

    private boolean mailExist(String mail,Integer userId) {
        return  sysUserMapper.countByMail(mail,userId)>0;
    }


    /**
     * 做登陆操作
     * @param loginVo
     * @param request
     * @param response
     */
    public void doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response){
        String username=loginVo.getUsername();
        String password = loginVo.getPassword();
        SysUser user = findUserByMailOrPhone(username);
        if (user==null){
            throw new GlobalException(CodeMsg.USER_NOT_EXIST);
        }
        if (!PasswordUtil.matches(password,user.getPassword())){
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        String token=UUIDUtil.getUuid();
        //将User存到服务端(Tomcat)session中
        //扩展的话就只需要将这个存到Redis等中间件中就行了
        request.getSession().setAttribute(token,user);
        //添加token到cookie中
        addCookies(response,token);
    }


    public List<SysUser> getUsersByDept(Integer deptId){
        return sysUserMapper.selectByDeptId(deptId);
    }



    /*
     * Redis - Session相关
     * */
    //todo
/*    public SysUser getUserByToken(HttpServletResponse response, String token) {
        if(StringUtils.isEmpty(token)) {
            return null;
        }
        SysUser user = redisService.get(SpikeUserKey.tkPrefix, token, SpikeUser.class);
        //重新设置cookie有效期（延长有效期）
        if(user != null) {
            addCookies(response, token, user);
        }
        return user;
    }*/

    private void addCookies(HttpServletResponse response,String token) {
        Cookie cookie = new Cookie(TOKEN_NAME, token);
        //设置Cookie过期时间 12h
        cookie.setMaxAge(3600*12);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    private SysUser findUserByMailOrPhone(String keyword){
        return sysUserMapper.findUserByMailOrPhone(keyword);
    }
}
