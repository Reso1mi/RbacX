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

    //权限模块相关
    public static final CodeMsg ACLMODULE_REPEAT = new CodeMsg(500401,"同一层级下存在相同的权限模块！");
    public static final CodeMsg ACLMODULE_NOT_EXIST = new CodeMsg(500202, "权限模块不存在！");

    //用户相关
    public static final CodeMsg USER_NOT_EXIST = new CodeMsg(500301,"用户不存在");
    public static final CodeMsg MAIL_REPEAT = new CodeMsg(500302, "邮箱已经被注册！");
    public static final CodeMsg PHONE_REPEAT = new CodeMsg(500303, "手机号已经被注册！");

    public static final CodeMsg PASSWORD_EMPTY = new CodeMsg(500211, "密码不能为空");
    public static final CodeMsg MOBILE_EMPTY = new CodeMsg(500212, "号码不能为空");
    public static final CodeMsg MOBILE_ERROR = new CodeMsg(500213, "号码格式错误");
    public static final CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500214, "号码不存在");
    public static final CodeMsg PASSWORD_ERROR= new CodeMsg(500215, "密码错误");
    public static final CodeMsg NICKNAME_EMPTY = new CodeMsg(500216, "昵称不能为空");
    public static final CodeMsg ID_REPEAT  = new CodeMsg(500217, "该号码已经注册");
    //秒杀异常
    public static final CodeMsg STOCK_EMPTY=new CodeMsg(500310,"库存不足！！！");
    public static final CodeMsg SPIKE_REPEAT=new CodeMsg(500311,"已经秒杀过该商品！！");
    public static final CodeMsg LOGIN_NONE=new CodeMsg(500312,"尚未登陆！！！");
    //订单异常
    public static final CodeMsg ORDRER_NOT_EXIST=new CodeMsg(500400,"订单不存在！！！");

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
