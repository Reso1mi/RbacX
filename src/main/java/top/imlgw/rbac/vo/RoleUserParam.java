package top.imlgw.rbac.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author imlgw.top
 * @date 2019/12/12 16:02
 */
@Getter
@Setter
@ToString
public class RoleUserParam {

    @NotNull(message = "角色ID不能为空")
    private Integer roleId;

    private List<Integer> userIds;
}
