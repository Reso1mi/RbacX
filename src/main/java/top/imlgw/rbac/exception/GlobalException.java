package top.imlgw.rbac.exception;

import top.imlgw.rbac.result.CodeMsg;

/**
 * @author imlgw.top
 * @date 2019/11/21 14:42
 */
public class GlobalException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private CodeMsg cm;

    public GlobalException(CodeMsg cm) {
        super(cm.toString());
        this.cm = cm;
    }

    public CodeMsg getCm() {
        return cm;
    }

}
