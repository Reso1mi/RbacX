package top.imlgw.rbac.utils;


import org.apache.commons.lang3.StringUtils;

/**
 * @author imlgw.top
 * @date 2019/11/21 20:34
 */
public class LevelUtil {
    public final static String SEPARATOR=".";

    //0.
    public final static String ROOT="0.";

    /**
     * @param parentLevel
     * @param parentId
     * @return id = 1  parentId=0 level = 0.
     *         id = 2  parentId=1 level = 0.1.
     *         id = 3  parentId=2 level = 0.1.2.
     * level代表的是当前的部门属于那一层下, 这样同一层级别下部门level是相同的
     * 我一直想用父类level+当前部门id构成level其实并不好,插入的时候并没有id,需要根据mysql的函数才能得到主键
     * 而且这样的level也并没有啥意义了
    */
    public static  String caculateLevel(String parentLevel,int parentId){
        if (StringUtils.isBlank(parentLevel)){
            return ROOT;
        }
        //以.结尾,算是解决之前的bug
        /*
        * 这里 SEPARATOR 一开始是放在中间的,但是放在中间在通过 level+id+.% 查询当前部门所有得子部门得时候就可能会有问题
        * 比如 当前level为0.1 id为2 ,子部门为 0.1.2 和 0.1.2.3,利用上面得规则查询得时候就会查不到0.1.2这个子部门
        * 那可以通过 level+id+% 查不就行了么？也不行,这样会查到 0.1.21 0.1.22 类似的部门,这些明显不是它的子部门
         * */
        return StringUtils.join(parentLevel,parentId,SEPARATOR);
    }
}
