package top.imlgw.rbac.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

/**
 * @author imlgw.top
 * @date 2019/12/3 21:07
 */
public class PageQueryVo {
    @Getter
    @Setter
    @Min(value = 1,message = "当前页码不合法")
    private int pageNo=1;

    @Getter
    @Setter
    @Min(value = 1,message = "每页展示数量不合法")
    private int pageSize=10;

    @Setter
    private  int offset;

    public int getOffset(){
        return (pageNo-1) * pageSize;
    }
}
