package top.imlgw.rbac.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;
import top.imlgw.rbac.entity.SysAclModule;
import java.util.ArrayList;
import java.util.List;

/**
 * @author imlgw.top
 * @date 2019/12/8 0:37
 */
@Getter
@Setter
@ToString
public class AclModuleLevelVo extends SysAclModule{
    //添加一个List属性,形成部门树的结构
    private List<AclModuleLevelVo> aclModuleList= new ArrayList<>();

    public static AclModuleLevelVo adapt(SysAclModule sysAclModule){
        AclModuleLevelVo aclModuleVo=new AclModuleLevelVo();
        //copybean的工具,还挺好用
        BeanUtils.copyProperties(sysAclModule, aclModuleVo);
        return aclModuleVo;
    }

}
