package top.imlgw.rbac.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author imlgw.top
 * @date 2019/11/21 18:59
 */
@Getter
@Setter
@ToString
public class DeptParam {

    private  Integer id;

    @NotBlank(message = "部门名称不能为空！")
    @Length(max = 15,min = 2,message ="部门名称需要在2~15之间！")
    private String name;

    //给一个默认值,避免空指针
    private  Integer parentId=0;

    @NotNull(message = "部门序列不可以为空！")
    private Integer seq;

    @Length(max = 150,message = "备注长度不要超过150字！")
    private String remark;
}
