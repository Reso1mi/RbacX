package top.imlgw.rbac.param;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import top.imlgw.rbac.validator.IsMail;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author imlgw.top
 * @date 2019/11/30 22:10
 */
@Getter
@Setter
public class UserParam {

    private Integer id;

    @NotBlank(message = "用户名不可以为空！")
    @Length(min = 1,max = 20,message = "用户名在20字内！")
    private String username;

    @NotBlank(message = "电话不可以为空！")
    private String telephone;

    @NotBlank(message = "邮箱不可以为空！")
    @IsMail
    private String mail;

    @NotNull(message = "用户部门不可以为空！")
    private Integer deptId;

    @Min(value = 0,message = "用户状态不合法！")
    @Max(value = 2,message = "用户状态不合法")
    @NotNull(message = "用户状态不可以为空")
    private Integer status;

    @NotBlank(message = "备注不可以为空")
    @Length(max = 200,message = "备注长度在200字内")
    private String remark;
}
