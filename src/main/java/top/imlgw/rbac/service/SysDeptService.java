package top.imlgw.rbac.service;

import org.apache.commons.lang3.StringUtils;
import org.hamcrest.core.Is;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imlgw.rbac.dao.SysDeptMapper;
import top.imlgw.rbac.entity.SysDept;
import top.imlgw.rbac.exception.GlobalException;
import top.imlgw.rbac.result.CodeMsg;
import top.imlgw.rbac.utils.LevelUtil;
import top.imlgw.rbac.vo.DeptVo;

import java.util.Date;
import java.util.Optional;


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
            throw new GlobalException(CodeMsg.DEPT_ID_REPEAT);
        }
        SysDept sysDept =new SysDept(deptVo.getName(),deptVo.getParentId(),deptVo.getSeq(),deptVo.getRemark());
        //设置层级level类似 0.1.2.3这种
        sysDept.setLevel(LevelUtil.caculateLevel(getLevel(deptVo.getParentId()),deptVo.getParentId()));
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
        return true;
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
}
