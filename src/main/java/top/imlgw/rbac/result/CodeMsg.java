package top.imlgw.rbac.result;

/**
 * @author imlgw.top
 * @date 2019/11/21 14:20
 */
public class CodeMsg {
    private Integer code;
    private String msg;

    //通用
    public static final CodeMsg SUCCESS = new CodeMsg(0, "success");
    public static final CodeMsg SERVER_ERROR = new CodeMsg(-1, "server error !");
    public static final CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常：%s");

    //部门相关
    public static final CodeMsg DEPT_REPEAT = new CodeMsg(500201, "同一层级下存在相同名称的部门");
    public static final CodeMsg DEPT_NOT_EXIST = new CodeMsg(500202, "部门不存在");
    public static final CodeMsg DEPT_CHILDREN_EXIST= new CodeMsg(500203, "待删除的部门下存在子部门");
    public static final CodeMsg DEPT_USER_EXIST= new CodeMsg(500203, "待删除的部门下存在用户");

    //权限模块相关
    public static final CodeMsg ACLMODULE_REPEAT = new CodeMsg(500301,"同一层级下存在相同的权限模块！");
    public static final CodeMsg ACLMODULE_NOT_EXIST = new CodeMsg(500302, "权限模块不存在！");

    //权限点相关
    public static final CodeMsg ACL_REPEAT = new CodeMsg(500401,"同一权限模块下存在相同的权限点！");
    public static final CodeMsg ACL_NOT_EXIST = new CodeMsg(500402, "权限点不存在！");

    //用户相关
    public static final CodeMsg USER_NOT_EXIST = new CodeMsg(500501,"用户不存在");
    public static final CodeMsg MAIL_REPEAT = new CodeMsg(500502, "邮箱已经被注册！");
    public static final CodeMsg PHONE_REPEAT = new CodeMsg(500503, "手机号已经被注册！");
    public static final CodeMsg PASSWORD_ERROR= new CodeMsg(500504, "密码错误");

    //角色相关
    public static final CodeMsg ROLE_REPEAT = new CodeMsg(500601,"角色名称已经存在");
    public static final CodeMsg ROLE_NOT_EXIST = new CodeMsg(500602, "角色不存在");

    //权限异常
    public static final CodeMsg PERMISSOIN_BAN= new CodeMsg(500601, "权限不足！！！");
    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CodeMsg fillArgs(Object... args) {
        int code = this.code;
        String message = String.format(this.msg, args);
        return new CodeMsg(code, message);
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
