package top.imlgw.rbac.utils;


import org.apache.commons.lang3.StringUtils;

/**
 * @author imlgw.top
 * @date 2019/11/21 20:34
 */
public class LevelUtil {
    public final static String SEPARATOR=".";

    public final static String ROOT="0";

    /**
     * @param parentLevel
     * @param parentId
     * @return id = 1  parentId=0 level = 0
     *         id = 2  parentId=1 level = 0.1
     *         id = 3  parentId=2 level = 0.1.2
     */
    public static  String caculateLevel(String parentLevel,int parentId){
        if (StringUtils.isBlank(parentLevel)){
            return ROOT;
        }
        return StringUtils.join(parentLevel,SEPARATOR,parentId);
    }

}
