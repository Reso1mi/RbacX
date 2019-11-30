package top.imlgw.rbac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.imlgw.rbac.dao.SysDeptMapper;
import top.imlgw.rbac.entity.SysDept;
import top.imlgw.rbac.utils.LevelUtil;
import top.imlgw.rbac.vo.DeptLevelVo;

import java.util.*;

/**
 * @author imlgw.top
 * @date 2019/11/21 21:31
 */
@Service
public class SysTreeService {

    @Autowired
    private SysDeptMapper sysDeptMapper;

    public List<DeptLevelVo> createDeptTree(){
        List<SysDept> allDept = sysDeptMapper.getAllDept();
        List<DeptLevelVo> deptDtoList = new ArrayList<>();
        for (SysDept sysDept : allDept) {
            DeptLevelVo deptLevelVo =DeptLevelVo.adapt(sysDept);
            deptDtoList.add(deptLevelVo);
        }
        return  deptList2Tree(deptDtoList);
    }

    /**
     * @param deptDtoList 所有部门dto的集合
     * @return
     */
    private List<DeptLevelVo> deptList2Tree(List<DeptLevelVo> deptDtoList){
        if (CollectionUtils.isEmpty(deptDtoList)){
            return new ArrayList<>();
        }
        //创建 Level [deptDto,deptDto1,deptDto2,deptDto3...]类似的结构
        //层级相同的在同一层 , 同时按照seq形成一个链表
        HashMap<String,List<DeptLevelVo>> levelDeptMap=new HashMap<>();

        List<DeptLevelVo> treeList=new ArrayList<>();
        //遍历所有的部门信息,构建 levelDeptMap
        for (DeptLevelVo dto : deptDtoList) {
            List<DeptLevelVo> deptDtolist= levelDeptMap.get(dto.getLevel());
            if (CollectionUtils.isEmpty(deptDtolist)){
                deptDtolist=new ArrayList<>();
                levelDeptMap.put(dto.getLevel(),deptDtolist);
            }
            deptDtolist.add(dto);
            //添加根节点进入treeList中作为整棵部门树的根节点, 同时也是递归的起点,
            if (LevelUtil.ROOT.equals(dto.getLevel())){
                treeList.add(dto);
            }
        }
        //同一个层级的优先级排列
        Collections.sort(treeList, Comparator.comparingInt(DeptLevelVo::getSeq));
        dfs(treeList,levelDeptMap,LevelUtil.ROOT);
        return treeList;
    }

    /**
     * @param treeList
     * @param levelDeptMap
     * @param curentLevel
     *
     * 深搜,递归构建部门树
     */
    private void dfs(List<DeptLevelVo> treeList, Map<String,List<DeptLevelVo>> levelDeptMap , String curentLevel){
        for (DeptLevelVo deptLevelVo : treeList) {
            //构建下一层的level
            String nextLevel=LevelUtil.caculateLevel(curentLevel, deptLevelVo.getId());
            //取出下一层的deptList
            List<DeptLevelVo> nextDeptList= levelDeptMap.get(nextLevel);
            if (!CollectionUtils.isEmpty(nextDeptList)){
                Collections.sort(nextDeptList,Comparator.comparingInt(DeptLevelVo::getSeq));
                //形成树形结构,将下一层的所有dept 设置为当前层级的deptList
                deptLevelVo.setDeptList(nextDeptList);
                //递归深搜完成整个部门树的构建
                dfs(nextDeptList,levelDeptMap,nextLevel);
            }
        }
    }
}
