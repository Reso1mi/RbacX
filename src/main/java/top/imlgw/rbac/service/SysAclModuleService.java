package top.imlgw.rbac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import top.imlgw.rbac.dao.SysAclModuleMapper;
import top.imlgw.rbac.entity.SysAclModule;
import top.imlgw.rbac.exception.GlobalException;
import top.imlgw.rbac.result.CodeMsg;
import top.imlgw.rbac.utils.IpUtil;
import top.imlgw.rbac.utils.LevelUtil;
import top.imlgw.rbac.utils.RequestContext;
import top.imlgw.rbac.vo.AclModuleVo;

import java.util.Date;
import java.util.List;

/**
 * @author imlgw.top
 * @date 2019/12/6 23:57
 */
@Service
public class SysAclModuleService {

    @Autowired
    private SysAclModuleMapper sysAclModuleMapper;

    public void save(AclModuleVo aclModuleVo) {
        if (isExist(aclModuleVo.getParentId(), aclModuleVo.getName(),aclModuleVo.getSeq(),aclModuleVo.getId())) {
            throw new GlobalException(CodeMsg.ACLMODULE_REPEAT);
        }
        SysAclModule sysAclModule=new SysAclModule(aclModuleVo.getName(),aclModuleVo.getParentId(),aclModuleVo.getSeq(),aclModuleVo.getRemark());
        //设置层级level类似 0.1.2.3这种
        //11.25 fix a bug
        //又改回来了,是自己想错了
        sysAclModule.setLevel(LevelUtil.caculateLevel(getLevel(aclModuleVo.getParentId()),aclModuleVo.getParentId()));
        //sysAclModule.setOperator(RequestContext.getCurrentSysUser().getUsername());
        sysAclModule.setOperator("test");
        //todo
        sysAclModule.setOperateIp(IpUtil.getRemoteIp(RequestContext.getCurrentRequest()));
        sysAclModule.setOperateTime(new Date());
        //insert是全量插入,插入所有字段
        sysAclModuleMapper.insertSelective(sysAclModule);
    }

    public void update(AclModuleVo aclModuleVo) {
        if (isExist(aclModuleVo.getParentId(), aclModuleVo.getName(),aclModuleVo.getSeq(),aclModuleVo.getId())) {
            throw new GlobalException(CodeMsg.ACLMODULE_REPEAT);
        }
        SysAclModule oldSysAclModule= sysAclModuleMapper.selectByPrimaryKey(aclModuleVo.getId());
        if (oldSysAclModule==null){
            throw new GlobalException(CodeMsg.ACLMODULE_NOT_EXIST);
        }
        SysAclModule newSysAclModule=new SysAclModule(
                aclModuleVo.getId(),
                aclModuleVo.getName(),
                aclModuleVo.getParentId(),
                aclModuleVo.getSeq(),
                aclModuleVo.getStatus(),
                aclModuleVo.getRemark()
        );
        //设置层级level类似 0.1.2.3. 这种
        //0.1.2.  -->  0.3.4.
        newSysAclModule.setLevel(LevelUtil.caculateLevel(getLevel(aclModuleVo.getParentId()),aclModuleVo.getParentId()));
        //newSysAclModule.setOperator(RequestContext.getCurrentSysUser().getUsername());
        newSysAclModule.setOperator("test");
        newSysAclModule.setOperateIp(IpUtil.getRemoteIp(RequestContext.getCurrentRequest()));
        newSysAclModule.setOperateTime(new Date());
        updateWithChild(oldSysAclModule,newSysAclModule);
    }

    private boolean isExist(Integer parentId, String aclModuleName,Integer seq,Integer aclModuleId) {
        return sysAclModuleMapper.countByNameAndParentId(aclModuleName,parentId,seq,aclModuleId)>0;
    }

    private String getLevel(Integer aclModuleId){
        SysAclModule sysAclModule= sysAclModuleMapper.selectByPrimaryKey(aclModuleId);
        if (sysAclModule==null){
            return null;
        }
        return sysAclModule.getLevel();
    }

    @Transactional
    public void updateWithChild(SysAclModule oldSysAclModule,SysAclModule newSysAclModule){
        //子部门的level前缀,需要加上当前部门的id,这也是原项目中的bug
        String newLevelPrefix = newSysAclModule.getLevel()+oldSysAclModule.getId();
        String oldLevelPrefix = oldSysAclModule.getLevel()+oldSysAclModule.getId();
        if (!newLevelPrefix.equals(oldLevelPrefix)){
            //根据当前的 level+id+.% 查询待更新部门的所有子部门
            //这一块要捋清楚最好画一下部门树的图
            List<SysAclModule> childModule= sysAclModuleMapper.getChildAclModuleListByLevel(oldLevelPrefix+".%");
            if (!CollectionUtils.isEmpty(childModule)){
                for (SysAclModule sysAclModule: childModule) {
                    //子部门后缀
                    String levelSuffix = sysAclModule.getLevel().substring(oldLevelPrefix.length());
                    sysAclModule.setLevel(newLevelPrefix+levelSuffix);
                }
                //更新所有子部门的level
                childModule.forEach(sysAclModuleMapper::updateByPrimaryKey);
            }
        }
        sysAclModuleMapper.updateByPrimaryKey(newSysAclModule);
    }
}
