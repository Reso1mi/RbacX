package top.imlgw.rbac.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author imlgw.top
 * @date 2019/12/1 23:04
 */
@Getter
@Setter
@ToString
public class LoginParam {
    @NotBlank(message = "用户名不能为空！")
    private String username;
    @NotNull(message = "密码不能为空！")
    private String password;
}
