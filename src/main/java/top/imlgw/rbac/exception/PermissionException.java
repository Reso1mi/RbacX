package top.imlgw.rbac.exception;

import top.imlgw.rbac.result.CodeMsg;

/**
 * @author imlgw.top
 * @date 2019/11/21 14:50
 */
public class PermissionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private CodeMsg cm;

    public PermissionException(CodeMsg cm) {
        super(cm.toString());
        this.cm = cm;
    }

    public CodeMsg getCm() {
        return cm;
    }
}
