package top.imlgw.rbac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imlgw.rbac.bean.PageQuery;
import top.imlgw.rbac.dao.SysAclMapper;
import top.imlgw.rbac.entity.SysAcl;
import top.imlgw.rbac.exception.GlobalException;
import top.imlgw.rbac.result.CodeMsg;
import top.imlgw.rbac.result.PageResult;
import top.imlgw.rbac.utils.IpUtil;
import top.imlgw.rbac.utils.RequestContext;
import top.imlgw.rbac.vo.AclVo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;


/**
 * @author imlgw.top
 * @date 2019/12/8 22:20
 */
@Service
public class SysAclService {

    @Autowired
    private SysAclMapper sysAclMapper;

    public void save(AclVo aclVo){
        System.out.println(aclVo);
        if (checkExist(aclVo.getAclModuleId(),aclVo.getName(),aclVo.getSeq(),aclVo.getId())){
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
        sysAcl.setOperator(RequestContext.getCurrentSysUser().getUsername());
        sysAcl.setOperateIp(IpUtil.getRemoteIp(RequestContext.getCurrentRequest()));
        sysAcl.setOperateTime(new Date());
        sysAclMapper.insertSelective(sysAcl);
    }

    public void update(AclVo aclVo){
        if (checkExist(aclVo.getAclModuleId(),aclVo.getName(),aclVo.getSeq(),aclVo.getId())){
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
        sysAclMapper.updateByPrimaryKeySelective(newSysAcl);
    }

    public boolean checkExist(int aclModuleId,String name,Integer seq,Integer aclId){
        //todo: 和dept一样seq没有做校验,后面看需要再来做
        return  sysAclMapper.countByNameAndAclModuleId(aclModuleId,name,seq,aclId)>0;
    }

    public String generateAclCode(){
        LocalDateTime ldt=LocalDateTime.now();
        //避免相同
        return ldt.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))+"_"+(int)(Math.random()*100);
    }

    public PageResult<SysAcl> getAclByModuleId(Integer aclModuleId, PageQuery pageQuery) {
        PageResult<SysAcl> result = new PageResult<>();
        //统计当前模块下所有权限点
        int count = sysAclMapper.countByModuleId(aclModuleId);
        if (count>=0){
            List<SysAcl> sysUsers = sysAclMapper.selectByModuleId(aclModuleId, pageQuery);
            result.setTotal(count);
            result.setData(sysUsers);
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(new SysAclService().generateAclCode());
    }
}
