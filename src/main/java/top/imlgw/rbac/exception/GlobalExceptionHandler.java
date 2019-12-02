package top.imlgw.rbac.exception;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.imlgw.rbac.result.CodeMsg;
import top.imlgw.rbac.result.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author imlgw.top
 * @date 2019/11/21 14:40
 */
@ControllerAdvice
@ResponseBody //直接返回给客户端，需要json的转换
public class GlobalExceptionHandler {

    /**
     * @param e
     * @return
     * 多个Exception会先处理小的,这个相当于兜底的
     */
    @ExceptionHandler(value = Exception.class) //处理GlobalException异常
    public Result<String> globalExceptionHandle(Exception e) {
        if (e instanceof GlobalException) {
            //全局异常
            GlobalException ex = (GlobalException) e;
            return Result.error(ex.getCm());
        } else {
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }

    @ExceptionHandler(value = PermissionException.class) //处理PermissionException
    public Result<String> permissionExceptionHandle(Exception e) {
        e.printStackTrace();
        PermissionException ex=(PermissionException) e;
        return Result.error(ex.getCm());
    }

    /**
     * @param e
     * @return
     * 处理 BindException类型的参数校验异常
     * 也就是 Content type=application/x-www-form-urlencoded;charset=UTF-8类型的POST提交
     * 表单提交默认方式就是这种,不加@RequestBody就是代表这种提交方式
     */
    @ExceptionHandler(value = BindException.class) //处理BindException
    public Result<String> bindExceptionHandle(Exception e) {
        BindException ex = (BindException) e;
        List<ObjectError> errors = ex.getAllErrors();
        ObjectError error = errors.get(0);
        String msg = error.getDefaultMessage();
        return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
    }

    /**
     * @param e
     * @return
     * 处理MethodArgumentNotValidException 类型的参数校验异常
     * 也就是 Content type=application/json 类型的POST提交
     * 也是目前比较流行的方式, 加上@RequestBody就是代表这种提交方式
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class) //处理MethodArgumentNotValidException
    public Result<String> methodExceptionHandle( Exception e) {
        MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
        BindingResult bindingResult = ex.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        ObjectError objectError = allErrors.get(0);
        return Result.error(CodeMsg.BIND_ERROR.fillArgs(objectError.getDefaultMessage()));
    }
}
