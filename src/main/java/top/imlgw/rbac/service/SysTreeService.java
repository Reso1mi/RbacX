package top.imlgw.rbac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.imlgw.rbac.dao.SysAclModuleMapper;
import top.imlgw.rbac.dao.SysDeptMapper;
import top.imlgw.rbac.entity.SysAclModule;
import top.imlgw.rbac.entity.SysDept;
import top.imlgw.rbac.utils.LevelUtil;
import top.imlgw.rbac.vo.AclModuleLevelVo;
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

    @Autowired
    private SysAclModuleMapper sysAclModuleMapper;

    public List<DeptLevelVo> createDeptTree() {
        List<SysDept> allDept = sysDeptMapper.getAllDept();
        List<DeptLevelVo> deptDtoList = new ArrayList<>();
        for (SysDept sysDept : allDept) {
            DeptLevelVo deptLevelVo = DeptLevelVo.adapt(sysDept);
            deptDtoList.add(deptLevelVo);
        }
        return deptList2Tree(deptDtoList);
    }

    /**
     * @param deptDtoList 所有部门dto的集合
     * @return
     */
    private List<DeptLevelVo> deptList2Tree(List<DeptLevelVo> deptDtoList) {
        if (CollectionUtils.isEmpty(deptDtoList)) {
            return new ArrayList<>();
        }
        //创建 { Level : [deptDto,deptDto1,deptDto2,deptDto3...] }类似的结构
        //层级相同的在同一层 ,并且按照seq形成一个链表
        HashMap<String, List<DeptLevelVo>> levelDeptMap = new HashMap<>();

        List<DeptLevelVo> treeList = new ArrayList<>();
        //遍历所有的部门信息,构建 levelDeptMap
        for (DeptLevelVo dto : deptDtoList) {
            List<DeptLevelVo> deptDtolist = levelDeptMap.get(dto.getLevel());
            if (CollectionUtils.isEmpty(deptDtolist)) {
                deptDtolist = new ArrayList<>();
                levelDeptMap.put(dto.getLevel(), deptDtolist);
            }
            deptDtolist.add(dto);
            //添加根节点进入treeList中作为整棵部门树的根节点, 同时也是递归的起点,
            if (LevelUtil.ROOT.equals(dto.getLevel())) {
                treeList.add(dto);
            }
        }
        //同一个层级的优先级排列
        Collections.sort(treeList, Comparator.comparingInt(DeptLevelVo::getSeq));
        dfsCreateDeptTree(treeList, levelDeptMap, LevelUtil.ROOT);
        return treeList;
    }

    /**
     * @param treeList
     * @param levelDeptMap
     * @param curentLevel  深搜,递归构建部门树
     */
    private void dfsCreateDeptTree(List<DeptLevelVo> treeList, Map<String, List<DeptLevelVo>> levelDeptMap, String
            curentLevel) {
        for (DeptLevelVo deptLevelVo : treeList) {
            //构建下一层的level
            String nextLevel = LevelUtil.caculateLevel(curentLevel, deptLevelVo.getId());
            //取出下一层的deptList
            List<DeptLevelVo> nextDeptList = levelDeptMap.get(nextLevel);
            if (!CollectionUtils.isEmpty(nextDeptList)) {
                Collections.sort(nextDeptList, Comparator.comparingInt(DeptLevelVo::getSeq));
                //形成树形结构,将下一层的所有dept 设置为当前层级的deptList
                deptLevelVo.setDeptList(nextDeptList);
                //递归深搜完成整个部门树的构建
                dfsCreateDeptTree(nextDeptList, levelDeptMap, nextLevel);
            }
        }
    }

    public List<AclModuleLevelVo> createAclModuleTree() {
        List<SysAclModule> allAclModule = sysAclModuleMapper.getAllAclModule();
        List<AclModuleLevelVo> aclModuleLevelVos = new ArrayList<>();
        for (SysAclModule sysAclModule : allAclModule) {
            AclModuleLevelVo aclModuleLevelVo = AclModuleLevelVo.adapt(sysAclModule);
            aclModuleLevelVos.add(aclModuleLevelVo);
        }
        return aclModuleList2Tree(aclModuleLevelVos);
    }

    private List<AclModuleLevelVo> aclModuleList2Tree(List<AclModuleLevelVo> aclModuleLevelVos) {
        if (CollectionUtils.isEmpty(aclModuleLevelVos)) {
            return new ArrayList<>();
        }
        //创建 { Level:[Dto,Dto1,Dto2,Dto3...] }类似的结构
        //层级相同的在同一层,同时按照seq形成一个链表
        HashMap<String, List<AclModuleLevelVo>> levelAclModuleMap = new HashMap<>();
        //需要构建的树型list
        List<AclModuleLevelVo> treeList = new ArrayList<>();
        //遍历所有的部门信息,构建 levelAclModuleMap
        for (AclModuleLevelVo dto : aclModuleLevelVos) {
            //从Map中取当前层级的list
            List<AclModuleLevelVo> dtolist = levelAclModuleMap.get(dto.getLevel());
            if (CollectionUtils.isEmpty(dtolist)) {
                dtolist = new ArrayList<>(); //为空就新创建一个list并添加进map
                levelAclModuleMap.put(dto.getLevel(), dtolist);
            }
            dtolist.add(dto); //不为空就直接添加进去
            //添加根节点进入treeList中作为整棵模块树的根节点, 同时也是递归的起点,
            if (LevelUtil.ROOT.equals(dto.getLevel())) {
                treeList.add(dto);
            }
        }
        //同一个层级的优先级排列
        Collections.sort(treeList, Comparator.comparingInt(AclModuleLevelVo::getSeq));
        dfsCreateAclModuleTree(treeList, levelAclModuleMap, LevelUtil.ROOT);
        return treeList;
    }

    private void dfsCreateAclModuleTree(List<AclModuleLevelVo> treeList, HashMap<String, List<AclModuleLevelVo>>
            levelAclModuleMap, String curentLevel) {
        for (AclModuleLevelVo aclModuleLevelVo : treeList) {
            //构建下一层的level
            String nextLevel = LevelUtil.caculateLevel(curentLevel, aclModuleLevelVo.getId());
            //取出下一层的deptList
            List<AclModuleLevelVo> nextAclModuleList = levelAclModuleMap.get(nextLevel);
            if (!CollectionUtils.isEmpty(nextAclModuleList)) {
                Collections.sort(nextAclModuleList, Comparator.comparingInt(AclModuleLevelVo::getSeq));
                //形成树形结构,将下一层的所有dept 设置为当前层级的deptList
                aclModuleLevelVo.setAclModuleLevelList(nextAclModuleList);
                //递归深搜完成整个部门树的构建
                dfsCreateAclModuleTree(nextAclModuleList, levelAclModuleMap, nextLevel);
            }
        }
    }
}
