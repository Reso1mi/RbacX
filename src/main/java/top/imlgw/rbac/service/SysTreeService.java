package top.imlgw.rbac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.imlgw.rbac.dao.SysDeptMapper;
import top.imlgw.rbac.entity.SysDept;
import top.imlgw.rbac.utils.LevelUtil;
import top.imlgw.rbac.vo.DeptLevelDto;

import java.util.*;

/**
 * @author imlgw.top
 * @date 2019/11/21 21:31
 */
@Service
public class SysTreeService {

    @Autowired
    private SysDeptMapper sysDeptMapper;

    public List<DeptLevelDto> createDeptTree(){
        List<SysDept> allDept = sysDeptMapper.getAllDept();
        List<DeptLevelDto> deptDtoList = new ArrayList<>();
        for (SysDept sysDept : allDept) {
            DeptLevelDto deptLevelDto=DeptLevelDto.adapt(sysDept);
            deptDtoList.add(deptLevelDto);
        }
        return  deptList2Tree(deptDtoList);
    }

    /**
     * @param deptDtoList 所有部门dto的集合
     * @return
     */
    private List<DeptLevelDto> deptList2Tree(List<DeptLevelDto> deptDtoList){
        if (CollectionUtils.isEmpty(deptDtoList)){
            return new ArrayList<>();
        }
        //创建 Level [deptDto,deptDto1,deptDto2,deptDto3...]类似的结构
        //层级相同的在同一层 , 同时按照seq形成一个链表
        HashMap<String,List<DeptLevelDto>> levelDeptMap=new HashMap<>();

        List<DeptLevelDto> treeList=new ArrayList<>();
        //遍历所有的部门信息,构建 levelDeptMap
        for (DeptLevelDto dto : deptDtoList) {
            List<DeptLevelDto> deptDtolist= levelDeptMap.get(dto.getLevel());
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
        Collections.sort(treeList, Comparator.comparingInt(DeptLevelDto::getSeq));
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
    private void dfs(List<DeptLevelDto> treeList,Map<String,List<DeptLevelDto>> levelDeptMap ,String curentLevel){
        for (DeptLevelDto deptLevelDto : treeList) {
            //构建下一层的level
            String nextLevel=LevelUtil.caculateLevel(curentLevel,deptLevelDto.getId());
            //取出下一层的deptList
            List<DeptLevelDto> nextDeptList= levelDeptMap.get(nextLevel);
            if (!CollectionUtils.isEmpty(nextDeptList)){
                Collections.sort(nextDeptList,Comparator.comparingInt(DeptLevelDto::getSeq));
                //形成树形结构,将下一层的所有dept 设置为当前层级的deptList
                deptLevelDto.setDeptList(nextDeptList);
                //递归深搜完成整个部门树的构建
                dfs(nextDeptList,levelDeptMap,nextLevel);
            }
        }
    }
}
