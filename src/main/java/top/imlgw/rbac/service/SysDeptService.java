package top.imlgw.rbac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import top.imlgw.rbac.dao.SysDeptMapper;
import top.imlgw.rbac.dao.SysUserMapper;
import top.imlgw.rbac.entity.SysDept;
import top.imlgw.rbac.exception.GlobalException;
import top.imlgw.rbac.result.CodeMsg;
import top.imlgw.rbac.utils.IpUtil;
import top.imlgw.rbac.utils.LevelUtil;
import top.imlgw.rbac.utils.RequestContext;
import top.imlgw.rbac.param.DeptParam;
import java.util.Date;
import java.util.List;

/**
 * @author imlgw.top
 * @date 2019/11/21 20:25
 */
@Service
public class SysDeptService {

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    public void save(DeptParam deptParam) {
        if (isExist(deptParam.getParentId(), deptParam.getName(), deptParam.getSeq(), deptParam.getId())) {
            throw new GlobalException(CodeMsg.DEPT_REPEAT);
        }
        SysDept sysDept =new SysDept(deptParam.getName(), deptParam.getParentId(), deptParam.getSeq(), deptParam.getRemark());
        //设置层级level类似 0.1.2.3这种
        //11.25 fix a bug
        //又改回来了,是自己想错了
        sysDept.setLevel(LevelUtil.caculateLevel(getLevel(deptParam.getParentId()), deptParam.getParentId()));
        sysDept.setOperator(RequestContext.getCurrentSysUser().getUsername());
        //todo
        sysDept.setOperateIp(IpUtil.getRemoteIp(RequestContext.getCurrentRequest()));
        sysDept.setOperateTime(new Date());
        //insert是全量插入,插入所有字段
        sysDeptMapper.insertSelective(sysDept);
    }

    private boolean isExist(Integer parentId, String deptName,Integer seq,Integer deptId) {
        return sysDeptMapper.countByNameAndParentId(deptName,parentId,seq,deptId)>0;
    }

    /**
     * @param deptId 部门id
     * @return 获取db中当前部门的父部门的层级,没有就返回null
     */
    private String getLevel(Integer deptId){
        SysDept sysDept = sysDeptMapper.selectByPrimaryKey(deptId);
        if (sysDept==null){
            return null;
        }
        return sysDept.getLevel();
    }

    public void update(DeptParam deptParam) {
        if (isExist(deptParam.getParentId(), deptParam.getName(), deptParam.getSeq(), deptParam.getId())) {
            throw new GlobalException(CodeMsg.DEPT_REPEAT);
        }
        SysDept oldSysDept = sysDeptMapper.selectByPrimaryKey(deptParam.getId());
        if (oldSysDept==null){
            throw new GlobalException(CodeMsg.DEPT_NOT_EXIST);
        }
        SysDept newSysDept =new SysDept(
                deptParam.getId(),
                deptParam.getName(),
                deptParam.getParentId(),
                deptParam.getSeq(),
                deptParam.getRemark()
        );
        //设置层级level类似 0.1.2.3. 这种
        //0.1.2.  -->  0.3.4.
        newSysDept.setLevel(LevelUtil.caculateLevel(getLevel(deptParam.getParentId()), deptParam.getParentId()));
        newSysDept.setOperator(RequestContext.getCurrentSysUser().getUsername());
        newSysDept.setOperateIp(IpUtil.getRemoteIp(RequestContext.getCurrentRequest()));
        newSysDept.setOperateTime(new Date());
        updateWithChild(oldSysDept,newSysDept);
    }

    @Transactional
    public void updateWithChild(SysDept oldSysDept,SysDept newSysDept){
        //子部门的level前缀,需要加上当前部门的id,这也是原项目中的bug
        String newLevelPrefix = newSysDept.getLevel()+oldSysDept.getId();
        String oldLevelPrefix = oldSysDept.getLevel()+oldSysDept.getId();
        if (!newLevelPrefix.equals(oldLevelPrefix)){
            //根据当前的 level+id+.% 查询待更新部门的所有子部门
            //这一块要捋清楚最好画一下部门树的图
            List<SysDept> childDept= sysDeptMapper.getChildDeptByLevel(oldLevelPrefix+".%");
            System.out.println("old: "+oldLevelPrefix+" , new: "+newLevelPrefix);
            System.out.println(childDept);
            if (!CollectionUtils.isEmpty(childDept)){
                for (SysDept sysDept : childDept) {
                    //子部门后缀
                    String levelSuffix = sysDept.getLevel().substring(oldLevelPrefix.length());
                    sysDept.setLevel(newLevelPrefix+levelSuffix);
                }
                //更新所有子部门的level
                childDept.forEach(sysDeptMapper::updateByPrimaryKey);
            }
        }
        //这里可以直接全量更新,因为传过来的对象就是全量的
        sysDeptMapper.updateByPrimaryKey(newSysDept);
    }

    /**
     * 删除部门,前提是部门下没有子部门,且部门下没有用户
     * @param deptId
     */
    public void delete(Integer deptId) {
        SysDept sysDept = sysDeptMapper.selectByPrimaryKey(deptId);
        if (sysDept==null){
            throw new GlobalException(CodeMsg.DEPT_NOT_EXIST);
        }
        if (sysDeptMapper.countChildDept(deptId)>0){
            throw new GlobalException(CodeMsg.DEPT_CHILDREN_EXIST);
        }
        if (sysUserMapper.countByDeptId(deptId)>0){
            throw new GlobalException(CodeMsg.DEPT_USER_EXIST);
        }
        sysDeptMapper.deleteByPrimaryKey(deptId);
    }
}
