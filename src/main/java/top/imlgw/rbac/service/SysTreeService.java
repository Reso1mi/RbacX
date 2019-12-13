package top.imlgw.rbac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.imlgw.rbac.dao.SysAclMapper;
import top.imlgw.rbac.dao.SysAclModuleMapper;
import top.imlgw.rbac.dao.SysDeptMapper;
import top.imlgw.rbac.dto.AclDto;
import top.imlgw.rbac.entity.SysAcl;
import top.imlgw.rbac.entity.SysAclModule;
import top.imlgw.rbac.entity.SysDept;
import top.imlgw.rbac.utils.LevelUtil;
import top.imlgw.rbac.dto.AclModuleLevelDto;
import top.imlgw.rbac.dto.DeptLevelDto;

import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private SysAclMapper sysAclMapper;

    @Autowired
    private SysCoreService sysCoreService;

    /**
     * @return 部门树构建
     */
    public List<DeptLevelDto> createDeptTree() {
        List<SysDept> allDept = sysDeptMapper.getAllDept();
        List<DeptLevelDto> deptDtoList = new ArrayList<>();
        for (SysDept sysDept : allDept) {
            DeptLevelDto deptLevelDto = DeptLevelDto.adapt(sysDept);
            deptDtoList.add(deptLevelDto);
        }
        return deptList2Tree(deptDtoList);
    }

    private List<DeptLevelDto> deptList2Tree(List<DeptLevelDto> deptDtoList) {
        if (CollectionUtils.isEmpty(deptDtoList)) {
            return new ArrayList<>();
        }
        //创建 { Level : [deptDto,deptDto1,deptDto2,deptDto3...] }类似的结构
        //层级相同的在同一层 ,并且按照seq形成一个链表
        HashMap<String, List<DeptLevelDto>> levelDeptMap = new HashMap<>();

        List<DeptLevelDto> treeList = new ArrayList<>();
        //遍历所有的部门信息,构建 levelDeptMap
        for (DeptLevelDto dto : deptDtoList) {
            List<DeptLevelDto> deptDtolist = levelDeptMap.get(dto.getLevel());
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
        Collections.sort(treeList, Comparator.comparingInt(DeptLevelDto::getSeq));
        dfsCreateDeptTree(treeList, levelDeptMap, LevelUtil.ROOT);
        return treeList;
    }

    /**
     * @param treeList
     * @param levelDeptMap
     * @param curentLevel  深搜,递归构建部门树
     */
    private void dfsCreateDeptTree(List<DeptLevelDto> treeList, Map<String, List<DeptLevelDto>> levelDeptMap, String
            curentLevel) {
        for (DeptLevelDto deptLevelDto : treeList) {
            //构建下一层的level
            String nextLevel = LevelUtil.caculateLevel(curentLevel, deptLevelDto.getId());
            //取出下一层的deptList
            List<DeptLevelDto> nextDeptList = levelDeptMap.get(nextLevel);
            if (!CollectionUtils.isEmpty(nextDeptList)) {
                Collections.sort(nextDeptList, Comparator.comparingInt(DeptLevelDto::getSeq));
                //形成树形结构,将下一层的所有dept设置为当前层级的deptList
                deptLevelDto.setDeptList(nextDeptList);
                //递归深搜完成整个部门树的构建
                dfsCreateDeptTree(nextDeptList, levelDeptMap, nextLevel);
            }
        }
    }

    /**
     * @return 权限模块树构建
     */
    public List<AclModuleLevelDto> createAclModuleTree() {
        List<SysAclModule> allAclModule = sysAclModuleMapper.getAllAclModule();
        List<AclModuleLevelDto> aclModuleLevelDtos = new ArrayList<>();
        for (SysAclModule sysAclModule : allAclModule) {
            AclModuleLevelDto aclModuleLevelDto = AclModuleLevelDto.adapt(sysAclModule);
            aclModuleLevelDtos.add(aclModuleLevelDto);
        }
        return aclModuleList2Tree(aclModuleLevelDtos);
    }

    private List<AclModuleLevelDto> aclModuleList2Tree(List<AclModuleLevelDto> aclModuleLevelDtos) {
        if (CollectionUtils.isEmpty(aclModuleLevelDtos)) {
            return new ArrayList<>();
        }
        //创建 { Level:[Dto,Dto1,Dto2,Dto3...] }类似的结构
        //层级相同的在同一层,同时按照seq形成一个链表
        HashMap<String, List<AclModuleLevelDto>> levelAclModuleMap = new HashMap<>();
        //需要构建的树型list
        List<AclModuleLevelDto> treeList = new ArrayList<>();
        //遍历所有的部门信息,构建 levelAclModuleMap
        for (AclModuleLevelDto dto : aclModuleLevelDtos) {
            //从Map中取当前层级的list
            List<AclModuleLevelDto> dtolist = levelAclModuleMap.get(dto.getLevel());
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
        Collections.sort(treeList, Comparator.comparingInt(AclModuleLevelDto::getSeq));
        dfsCreateAclModuleTree(treeList, levelAclModuleMap, LevelUtil.ROOT);
        return treeList;
    }

    /**
     * dfs构建权限模块树结构
     * @param treeList
     * @param levelAclModuleMap
     * @param curentLevel
     */
    private void dfsCreateAclModuleTree(List<AclModuleLevelDto> treeList, HashMap<String, List<AclModuleLevelDto>>
            levelAclModuleMap, String curentLevel) {
        for (AclModuleLevelDto aclModuleLevelDto : treeList) {
            //构建下一层的level
            String nextLevel = LevelUtil.caculateLevel(curentLevel, aclModuleLevelDto.getId());
            //取出下一层的deptList
            List<AclModuleLevelDto> nextAclModuleList = levelAclModuleMap.get(nextLevel);
            if (!CollectionUtils.isEmpty(nextAclModuleList)) {
                Collections.sort(nextAclModuleList, Comparator.comparingInt(AclModuleLevelDto::getSeq));
                //形成树形结构,将下一层的所有module设置为当前层级的module
                aclModuleLevelDto.setAclModuleList(nextAclModuleList);
                //递归深搜完成整个部门树的构建
                dfsCreateAclModuleTree(nextAclModuleList, levelAclModuleMap, nextLevel);
            }
        }
    }

    /**
     * @param roleId
     * @return 某个角色对应的权限树
     */
    public List<AclModuleLevelDto> roleAclTree(int roleId) {
        // 1、当前用户已分配的权限点
        // 2、当前角色分配的权限点
        List<SysAcl> userAclList = sysCoreService.getCurrentUserAclList();
        List<SysAcl> roleAclList = sysCoreService.getRoleAclList(roleId);
        // 3、当前系统所有权限点Dto(带有额外属性的)
        List<AclDto> aclDtoList = new ArrayList<>();
        // java8新语法
        Set<Integer> userAclIdSet = userAclList.stream().map(sysAcl -> sysAcl.getId()).collect(Collectors.toSet());
        Set<Integer> roleAclIdSet = roleAclList.stream().map(sysAcl -> sysAcl.getId()).collect(Collectors.toSet());
        // 4、所有权限,用来和上面的进行对比
        List<SysAcl> allAclList = sysAclMapper.getAllAcl();
        for (SysAcl acl : allAclList) {
            AclDto dto = AclDto.adapt(acl);
            if (userAclIdSet.contains(acl.getId())) {
                dto.setHasAcl(true); //是否有权限处理
            }
            if (roleAclIdSet.contains(acl.getId())) {
                dto.setChecked(true);//是否默认选中
            }
            aclDtoList.add(dto);
        }
        return aclListToTree(aclDtoList);
    }

    public List<AclModuleLevelDto> aclListToTree(List<AclDto> aclDtoList) {
        if (CollectionUtils.isEmpty(aclDtoList)) {
            return Collections.EMPTY_LIST;
        }
        List<AclModuleLevelDto> aclModuleLevelList = createAclModuleTree();
        //相同module的acl在同一层
        HashMap<Integer, List<AclDto>> moduleIdAclMap = new HashMap<>();
        for (AclDto acl : aclDtoList) {
            if (acl.getStatus() == 1) { //正常
                List<AclDto> dtoList = moduleIdAclMap.get(acl.getId());
                if (CollectionUtils.isEmpty(dtoList)) {
                    dtoList = new ArrayList<>();
                    moduleIdAclMap.put(acl.getId(), dtoList);
                }
                dtoList.add(acl);
            }
        }
        dfsCreateRoleAcls(aclModuleLevelList, moduleIdAclMap);
        return aclModuleLevelList;
    }

    /**
     * @param aclModuleLevelList
     * @param moduleIdAclMap dfs构建角色对应的权限树
     */
    public void dfsCreateRoleAcls(List<AclModuleLevelDto> aclModuleLevelList, HashMap<Integer, List<AclDto>>
            moduleIdAclMap) {
        if (CollectionUtils.isEmpty(aclModuleLevelList)) {
            return;
        }
        for (AclModuleLevelDto dto : aclModuleLevelList) {
            List<AclDto> aclDtoList = moduleIdAclMap.get(dto.getId());
            if (!CollectionUtils.isEmpty(aclDtoList)) {
                Collections.sort(aclDtoList, Comparator.comparingInt(AclDto::getSeq));
                dto.setAclList(aclDtoList);
            }
            dfsCreateRoleAcls(dto.getAclModuleList(), moduleIdAclMap);
        }
    }


    /**
     * @param userId
     * @return 用户权限树结构
     */
    public List<AclModuleLevelDto> userAclTree(Integer userId) {
        // 1、当前用户已分配的权限点
        List<SysAcl> userAclList = sysCoreService.getAclListByUserId(userId);
        // 2、前台所需要的权限点Dto(带有额外属性的)
        List<AclDto> aclDtoList = new ArrayList<>();
        // java8
        Set<Integer> userAclIdSet = userAclList.stream().map(sysAcl -> sysAcl.getId()).collect(Collectors.toSet());
        // 3、所有权限,用来和上面的进行对比
        List<SysAcl> allAclList = sysAclMapper.getAllAcl();
        //allAclList.stream().map(AclDto::adapt).filter(aclDto -> userAclIdSet.contains(aclDto.getId())).forEach();
        for (SysAcl acl : allAclList) {
            AclDto dto = AclDto.adapt(acl);
            if (userAclIdSet.contains(acl.getId())) {
                dto.setChecked(true);//是否默认选中
            }
            aclDtoList.add(dto);
        }
        return aclListToTree(aclDtoList);
    }
}
