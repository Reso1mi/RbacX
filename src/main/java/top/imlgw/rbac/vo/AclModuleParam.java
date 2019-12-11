package top.imlgw.rbac.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author imlgw.top
 * @date 2019/12/6 23:48
 */
@Getter
@Setter
@ToString
public class AclModuleParam {

    private Integer id;

    @NotBlank(message = "权限模块名称不可以为空")
    @Length(min = 2,max = 20,message = "权限模块长度为2~20之间")
    private String name;

    private Integer parentId =0;

    @NotNull(message = "权限模块顺序不能为空")
    private Integer seq;

    @Min(value = 0,message = "模块状态不合法！")
    @Max(value = 2,message = "模块状态不合法")
    @NotNull(message = "模块状态不可以为空")
    private Integer status;

    @Length(max = 150,message = "备注长度不要超过150字！")
    private String remark;
}
