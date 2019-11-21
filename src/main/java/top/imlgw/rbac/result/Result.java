package top.imlgw.rbac.result;

import lombok.Getter;
import lombok.ToString;

/**
 * @author imlgw.top
 * @date 2019/11/20 21:14
 */
@Getter
@ToString
public class Result<T> {

    private int code;

    private String msg;

    private T data;

    //构造器私有
    private Result(T data){
        this.code=0;
        this.data=data;
        this.msg="success";
    }

    private Result(CodeMsg codeMsg){
        if (codeMsg==null) return;
        this.msg=codeMsg.getMsg();
        this.code=codeMsg.getCode();
    }

    /**
     * @param codeMsg
     * @param <T>
     * @return 不带数据的Result
     */
    public static <T> Result<T> success(CodeMsg codeMsg){
        return  new Result<>(codeMsg);
    }

    /**
     * @param data 带数据的Result
     * @param <T>  msg="success"
     * @return     code=0
     */
    public static <T> Result<T> success(T data){
        return  new Result<>(data);
    }

    /**
     * @param codeMsg
      @param <T>
     * @return error不带数据
     */
    public static <T> Result<T> error(CodeMsg codeMsg){
        return  new Result<>(codeMsg);
    }
}
