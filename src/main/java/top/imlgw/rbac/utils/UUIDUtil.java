package top.imlgw.rbac.utils;

import java.util.UUID;

/**
 * @author imlgw.top
 * @date 2019/12/2 21:20
 */
public class UUIDUtil {

    public static String getUuid(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
