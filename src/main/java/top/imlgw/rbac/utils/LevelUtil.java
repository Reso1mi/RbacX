package top.imlgw.rbac.utils;


import org.apache.commons.lang3.StringUtils;

/**
 * @author imlgw.top
 * @date 2019/11/21 20:34
 */
public class LevelUtil {
    public final static String SEPARATOR=".";

    public final static String ROOT="0";

    //0.1
    //0.1.2
    //0.1.2.3 ....
    public static  String caculateLevel(String parentLevel,int parentId){
        if (StringUtils.isBlank(parentLevel)){
            return ROOT;
        }
        return StringUtils.join(parentLevel,SEPARATOR,parentId);
    }

}
