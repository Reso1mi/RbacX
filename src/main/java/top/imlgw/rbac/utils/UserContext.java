package top.imlgw.rbac.utils;

import top.imlgw.rbac.entity.SysUser;

/**
 * @author imlgw.top
 * @date 2019/12/2 21:08
 */
@Deprecated
public class UserContext {
    private static  ThreadLocal<SysUser> userHolder =new ThreadLocal<>();

    public static void setUser(SysUser sysUser){
        userHolder.set(sysUser);
    }

    public static SysUser getUser(){
        return userHolder.get();
    }

    public static void removeUser(){
        userHolder.remove();
    }
}
