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
public class DeptVo {

    private  Integer id;

    @NotBlank(message = "部门名称不能为空！")
    @Length(max = 5,min = 2,message ="部门名称需要在2~15之间！")
    private String name;

    private  Integer parentId;

    @NotNull(message = "展示顺序不可以为空")
    private Integer seq;

    @Length(max = 150,message = "备注长度不要超过150字！")
    private String remark;
}
