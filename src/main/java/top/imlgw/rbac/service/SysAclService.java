package top.imlgw.rbac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imlgw.rbac.dao.SysAclMapper;
import top.imlgw.rbac.entity.SysAcl;
import top.imlgw.rbac.exception.GlobalException;
import top.imlgw.rbac.result.CodeMsg;
import top.imlgw.rbac.utils.IpUtil;
import top.imlgw.rbac.utils.RequestContext;
import top.imlgw.rbac.vo.AclVo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;


/**
 * @author imlgw.top
 * @date 2019/12/8 22:20
 */
@Service
public class SysAclService {

    @Autowired
    private SysAclMapper sysAclMapper;

    public void save(AclVo aclVo){
        if (checkExist(aclVo.getAclModuleId(),aclVo.getName(),aclVo.getId())){
            throw new GlobalException(CodeMsg.ACL_REPEAT);
        }
        SysAcl sysAcl=new SysAcl(
                aclVo.getName(),
                aclVo.getAclModuleId(),
                aclVo.getUrl(),
                aclVo.getType(),
                aclVo.getStatus(),
                aclVo.getSeq(),
                aclVo.getRemark());
        sysAcl.setCode(generateAclCode());
        //sysAcl.setOperator(RequestContext.getCurrentSysUser().getUsername());
        sysAcl.setOperator("test");
        sysAcl.setOperateIp(IpUtil.getRemoteIp(RequestContext.getCurrentRequest()));
        sysAcl.setOperateTime(new Date());
        sysAclMapper.insertSelective(sysAcl);
    }

    public void update(AclVo aclVo){
        if (checkExist(aclVo.getAclModuleId(),aclVo.getName(),aclVo.getId())){
            throw new GlobalException(CodeMsg.ACL_REPEAT);
        }
        SysAcl oldSysAcl = sysAclMapper.selectByPrimaryKey(aclVo.getId());
        //todo
        /*if (oldSysAcl ==null){
            throw new GlobalException(CodeMsg.ACL_NOT_EXIST);
        }*/
        SysAcl newSysAcl=new SysAcl(
                aclVo.getId(),
                aclVo.getName(),
                aclVo.getAclModuleId(),
                aclVo.getUrl(),
                aclVo.getType(),
                aclVo.getStatus(),
                aclVo.getSeq(),
                aclVo.getRemark());
        newSysAcl.setOperator(RequestContext.getCurrentSysUser().getUsername());
        newSysAcl.setOperateIp(IpUtil.getRemoteIp(RequestContext.getCurrentRequest()));
        newSysAcl.setOperateTime(new Date());
    }

    public boolean checkExist(int aclModuleId,String name,Integer aclId){
        return  false;
    }

    public String generateAclCode(){
        LocalDateTime ldt=LocalDateTime.now();
        //避免相同
        return ldt.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))+"_"+(int)(Math.random()*100);
    }

    public static void main(String[] args) {
        System.out.println(new SysAclService().generateAclCode());
    }
}
