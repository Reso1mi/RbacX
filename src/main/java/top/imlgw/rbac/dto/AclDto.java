package top.imlgw.rbac.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;
import top.imlgw.rbac.entity.SysAcl;

/**
 * @author imlgw.top
 * @date 2019/12/11 17:57
 */
@Getter
@Setter
@ToString
public class AclDto extends SysAcl{
    // 是否要默认选中
    private boolean checked = false;

    // 是否有权限操作
    private boolean hasAcl = false;

    public static AclDto adapt(SysAcl acl) {
        AclDto dto = new AclDto();
        BeanUtils.copyProperties(acl, dto);
        return dto;
    }
}
