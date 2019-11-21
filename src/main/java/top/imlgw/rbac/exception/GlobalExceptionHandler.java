package top.imlgw.rbac.exception;

import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
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
    @ExceptionHandler(value = Exception.class) //处理GlobalException异常
    public Result<String> globalExceptionHandle(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        if (e instanceof GlobalException) {
            //全局异常
            GlobalException ex = (GlobalException) e;
            return Result.error(ex.getCm());
        } else {
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }

    @ExceptionHandler(value = PermissionException.class) //处理PermissionException
    public Result<String> permissionExceptionHandle(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        PermissionException ex=(PermissionException) e;
        return Result.error(ex.getCm());
    }

    //多个Exception会先处理小的,最上面哪个相当于兜底的
    @ExceptionHandler(value = BindException.class) //处理BindException
    public Result<String> bindExceptionHandle(HttpServletRequest request, Exception e) {
        BindException ex = (BindException) e;
        List<ObjectError> errors = ex.getAllErrors();
        ObjectError error = errors.get(0);
        String msg = error.getDefaultMessage();
        return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
    }
}
