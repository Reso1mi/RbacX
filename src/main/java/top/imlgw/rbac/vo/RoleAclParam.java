package top.imlgw.rbac.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author imlgw.top
 * @date 2019/12/12 0:17
 */
@Getter
@Setter
@ToString
public class RoleAclParam {

    @NotNull(message = "角色ID不能为空")
    private Integer roleId;

    private List<Integer> aclList;
}
