package top.imlgw.rbac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import top.imlgw.rbac.dao.SysDeptMapper;
import top.imlgw.rbac.entity.SysDept;
import top.imlgw.rbac.exception.GlobalException;
import top.imlgw.rbac.result.CodeMsg;
import top.imlgw.rbac.utils.LevelUtil;
import top.imlgw.rbac.vo.DeptVo;

import java.util.Collection;
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

    public void save(DeptVo deptVo) {
        if (checkExist(deptVo.getParentId(), deptVo.getName(), deptVo.getId())) {
            throw new GlobalException(CodeMsg.DEPT_REPEAT);
        }
        SysDept sysDept =new SysDept(deptVo.getName(),deptVo.getParentId(),deptVo.getSeq(),deptVo.getRemark());
        //设置层级level类似 0.1.2.3这种
        sysDept.setLevel(LevelUtil.caculateLevel(getParentLevel(deptVo.getParentId()),deptVo.getParentId()));
        //todo
        sysDept.setOperator("system");
        //todo
        sysDept.setOperateIp("127.0.0.1");
        //todo
        sysDept.setOperateTime(new Date());
        //insert是全量插入,插入所有字段
        sysDeptMapper.insertSelective(sysDept);
    }

    private boolean checkExist(Integer parentId, String deptName, Integer deptId) {
        return sysDeptMapper.countByNameAndParentId(deptName,parentId,deptId)>0;
    }

    /**
     * @param deptId 部门id
     * @return 获取db中当前部门的父部门的层级,没有就返回null
     */
    private String getParentLevel(Integer deptId){
        SysDept sysDept = sysDeptMapper.selectByPrimaryKey(deptId);
        if (sysDept==null){
            return null;
        }
        return sysDept.getLevel();
    }

    public void update(DeptVo deptVo) {
        if (checkExist(deptVo.getParentId(), deptVo.getName(), deptVo.getId())) {
            throw new GlobalException(CodeMsg.DEPT_REPEAT);
        }
        SysDept oldSysDept = sysDeptMapper.selectByPrimaryKey(deptVo.getId());
        if (oldSysDept==null){
            throw new GlobalException(CodeMsg.DEPT_NOT_EXIST);
        }
        SysDept newSysDept =new SysDept(deptVo.getName(),deptVo.getParentId(),deptVo.getSeq(),deptVo.getRemark());
        //设置层级level类似 0.1.2.3这种
        newSysDept.setLevel(LevelUtil.caculateLevel(getParentLevel(deptVo.getParentId()),deptVo.getParentId()));
        //todo
        newSysDept.setOperator("system");
        //todo
        newSysDept.setOperateIp("127.0.0.1");
        //todo
        newSysDept.setOperateTime(new Date());
        updateWithChild(oldSysDept,newSysDept);
    }

    @Transactional
    public void updateWithChild(SysDept oldSysDept,SysDept newSysDept){
        //子部门的level前缀
        String newLevelPrefix = newSysDept.getLevel();
        String oldLevelPrefix = oldSysDept.getLevel();
        if (!newLevelPrefix.equals(oldLevelPrefix)){
            //待更新部门的所有子部门
            List<SysDept> childDept= sysDeptMapper.getChildDeptByLevel(oldLevelPrefix);
            if (!CollectionUtils.isEmpty(childDept)){
                for (SysDept sysDept : childDept) {
                    //子部门后缀
                    String levelSuffix = sysDept.getLevel().substring(oldLevelPrefix.length());
                    sysDept.setLevel(newLevelPrefix+levelSuffix);
                }
            }
            //批量更新子部门的level
            sysDeptMapper.batchUpdateLevel(childDept);
        }

        sysDeptMapper.updateByPrimaryKey(newSysDept);
    }
}
