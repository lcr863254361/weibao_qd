package com.orient.sysman.bussiness;

import com.google.common.collect.Lists;
import com.orient.businessmodel.Util.BusinessModelCacheHelper;
import com.orient.metamodel.metadomain.Schema;
import com.orient.metamodel.operationinterface.ISchema;
import com.orient.sysman.bean.*;
import com.orient.sysmodel.domain.form.ModelBtnTypeEntity;
import com.orient.sysmodel.domain.role.CwmSysRolePortalEntity;
import com.orient.sysmodel.domain.role.Function;
import com.orient.sysmodel.domain.role.OverAllOperations;
import com.orient.sysmodel.domain.role.Role;
import com.orient.sysmodel.domain.sys.CwmPortalEntity;
import com.orient.sysmodel.domain.user.Department;
import com.orient.sysmodel.domain.user.User;
import com.orient.sysmodel.operationinterface.IDepartment;
import com.orient.sysmodel.operationinterface.IRoleFunctionTbom;
import com.orient.sysmodel.operationinterface.IUser;
import com.orient.sysmodel.service.PageBean;
import com.orient.sysmodel.service.form.IModelBtnTypeService;
import com.orient.sysmodel.service.role.FunctionService;
import com.orient.sysmodel.service.role.IRolePortalService;
import com.orient.sysmodel.service.role.RoleService;
import com.orient.sysmodel.service.sys.IPortalService;
import com.orient.utils.CommonTools;
import com.orient.utils.FileOperator;
import com.orient.utils.PageUtil;
import com.orient.utils.StringUtil;
import com.orient.web.base.BaseBusiness;
import com.orient.web.base.ExtGridData;
import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2016/4/27.
 */
@Service
@Transactional
public class RoleBusiness extends BaseBusiness {


    @Resource(name = "RoleService")
    RoleService roleService;

    @Autowired
    FunctionService functionService;

    @Autowired
    IModelBtnTypeService modelBtnTypeService;

    @Autowired
    BusinessModelCacheHelper businessModelCacheHelper;

    @Autowired
    IRolePortalService rolePortalService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    IPortalService portalService;

    public List<RoleBean> findAll(Integer page, Integer limit) {
        List<RoleBean> allRoles = new ArrayList<>();
        Iterator<Map.Entry<String, Role>> iter = roleEngine.getRoleModel(false).getRoles().entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Role> entry = iter.next();
            if (Integer.parseInt(entry.getKey()) > 0) {
                Role role = entry.getValue();
                RoleBean roleBean = new RoleBean();
                try {
                    PropertyUtils.copyProperties(roleBean, role);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                allRoles.add(roleBean);
            }
        }
        ArrayList<RoleBean> sublist = new ArrayList<>();
        if (null != page && null != limit) {
            int count = Math.min(page * limit, allRoles.size());
            for (int i = (page - 1) * limit; i < count; i++) {
                sublist.add(allRoles.get(i));
            }
        } else {
            sublist.addAll(allRoles);
        }
        return sublist;
    }

    public long getRoleCount() {
        return roleEngine.getRoleModel(false).getRoles().entrySet().size();
    }

    public void create(Role role) {
        role.setType("0");
        role.setStatus("Y");
        role.setFlg("1");
        roleService.createRole(role);
    }

    public void update(Role role) {
        role.setType("0");
        role.setStatus("Y");
        role.setFlg("1");
        roleService.updateRole(role);
    }

    public void delete(String toDelIds) {
        String[] ids = toDelIds.split(",");
        roleService.deleteRole(ids);
        //删除角色统计列
        String roleStatisticLineSql="delete from T_ROLE_STATISTICLINE_480  where C_ROLE_ID_3587 IN ("+toDelIds+")";
        jdbcTemplate.update(roleStatisticLineSql);
    }

    public List<RoleBean> search(Role roleInstance) {
        List<Role> roles = roleService.findByExampleLike(roleInstance);
        List<RoleBean> retList = new ArrayList<>();
        for (Role role : roles) {
            //列表页面不列出三元账号信息
            if (Integer.parseInt(role.getId()) <= 0) {
                continue;
            }
            RoleBean roleBean = new RoleBean();
            try {
                PropertyUtils.copyProperties(roleBean, role);
            } catch (Exception e) {
                e.printStackTrace();
            }
            retList.add(roleBean);
        }
        return retList;
    }

    /**
     * @param roleId   角色id
     * @param assigned 是否已分配
     * @param page     第几页
     * @param limit    每页多少条
     * @return 根据当前角色 获取 分配schema信息
     */
    public ExtGridData<SchemaBean> listSchemas(String roleId, Boolean assigned, Integer page, Integer limit) {
        ExtGridData<SchemaBean> retVal = new ExtGridData<>();
        if (!StringUtil.isEmpty(roleId)) {
            //获取角色信息
            Role role = roleEngine.getRoleModel(false).getRoleById(roleId);
            //获取角色已分配所有schema信息
            List<ISchema> assignedSchemas = role.getAllSchemas();
            //获取所有schema信息
            Map<String, Schema> allSchemas = metaEngine.getMeta(false).getSchemas();
            List<ISchema> allSchemasList = new ArrayList<>();
            for (ISchema schema : allSchemas.values()) {
                allSchemasList.add(schema);
            }

            List<ISchema> unassignedSchemas = new ArrayList<>();
            allSchemasList.forEach(schema -> {
                boolean flag = true;
                String id = schema.getId();
                for (ISchema ass : assignedSchemas) {
                    if (id.equals(ass.getId())) {
                        flag = false;
                    }
                }
                if (flag) {
                    unassignedSchemas.add(schema);
                }
            });

            assigned = null == assigned ? false : assigned;
            List<ISchema> schemas = assigned ? assignedSchemas : unassignedSchemas;
            retVal.setTotalProperty(schemas.size());
            List<ISchema> subList = PageUtil.page(schemas, page, limit);
            List<SchemaBean> results = new ArrayList<>();
            subList.forEach(schema -> {
                SchemaBean schemaBean = new SchemaBean();
                try {
                    PropertyUtils.copyProperties(schemaBean, schema);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                results.add(schemaBean);
            });
            retVal.setResults(results);
        } else {
            Map<String, Schema> allSchemas = metaEngine.getMeta(false).getSchemas();
            List<ISchema> allSchemasList = new ArrayList<>();
            for (ISchema schema : allSchemas.values()) {
                allSchemasList.add(schema);
            }
            List<ISchema> subList = PageUtil.page(allSchemasList, page, limit);
            List<SchemaBean> results = new ArrayList<>();
            subList.forEach(schema -> {
                SchemaBean schemaBean = new SchemaBean();
                try {
                    PropertyUtils.copyProperties(schemaBean, schema);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                results.add(schemaBean);
            });
            retVal.setResults(results);
        }
        return retVal;
    }

    /**
     * @param roleId   角色id
     * @param assigned 是否已经分配
     * @param page     第几页
     * @param limit    每页多少条
     * @return 根据当前角色信息 获取 分配用户信息
     */
    public ExtGridData<UserBean> listUsers(String roleId, Boolean assigned, Integer page, Integer limit, UserBean userFilter, String isSearch) {
        ExtGridData<UserBean> retVal = new ExtGridData<>();
        if (!StringUtil.isEmpty(roleId)) {
            Role role = roleEngine.getRoleModel(false).getRoleById(roleId);
            List<IUser> assignUsers = role.getAllUsers();
            Map<String, User> allUsers = roleEngine.getRoleModel(false).getUsers();
            List<IUser> allUsersList = new ArrayList<>();
            for (IUser user : allUsers.values()) {
//                if(user.getDept()!=null){
                if (!"-1".equals(user.getId())&&!"-2".equals(user.getId())&&!"-3".equals(user.getId())){
                    allUsersList.add(user);
                }
//                }
            }
            List<IUser> unassignedUsers = new ArrayList<>();
            allUsersList.forEach(user -> {
                boolean flag = true;
                String id = user.getId();
                for (IUser ass : assignUsers) {
                    if (id.equals(ass.getId())) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    unassignedUsers.add(user);
                }
            });
            assigned = null == assigned ? false : assigned;
            List<IUser> users = assigned ? assignUsers : unassignedUsers;
            if ("true".equals(isSearch)) {
                String userName = userFilter.getUserName();
                String deptId = userFilter.getDepartment();
                List<String> allDeptIds = Lists.newArrayList();
                if (deptId != null && !"".equals(deptId)) {
                    allDeptIds = getAllDepartmentIds(deptId);
                }
                for (IUser user : Lists.newArrayList(users)) {
                    boolean flag = true;
                    if (userName != null && !"".equals(userName) && !user.getUserName().contains(userName) && !user.getAllName().contains(userName)) {
                        flag = false;
                    }
                    if (deptId != null && !"".equals(deptId) && !allDeptIds.contains(user.getDept().getId())) {
                        flag = false;
                    }
                    if (!flag) {
                        users.remove(user);
                    }
                }
            }
            List<IUser> subList = PageUtil.page(users, page, limit);
            List<UserBean> result = new ArrayList<>();
            subList.forEach(user -> {
                if (Long.valueOf(user.getId()) > 0) {
                    UserBean userBean = new UserBean();
                    userBean.setId(user.getId());
                    userBean.setUserName(user.getUserName());
                    userBean.setAllName(user.getAllName());
                    userBean.setPassword(user.getPassword());
                    userBean.setSex(user.getSex());
                    userBean.setBirthday(user.getBirthday());
                    userBean.setMobile(user.getMobile());
                    userBean.setPhone(user.getPhone());
                    userBean.setEmail(user.getEmail());
                    userBean.setPost(user.getPost());
                    userBean.setGrade(user.getGrade());
                    userBean.setNotes(user.getNotes());
                    IDepartment department = user.getDept();
                    if (department != null) {
                        userBean.setDepartment(department.getName());
                        userBean.setDepartmentId(department.getId());
                    }
                    result.add(userBean);
                }
            });
            retVal.setResults(result);
            retVal.setTotalProperty(users.size());
        }

        return retVal;
    }

    /**
     * @param roleId   角色id
     * @param assigned 是否已经分配
     * @param page     第几页
     * @param limit    每页多少条
     * @return 根据当前角色信息 获取 分配权限信息
     */
    public ExtGridData<OperationBean> listRights(String roleId, Boolean assigned, Integer page, Integer limit) {
        ExtGridData<OperationBean> retVal = new ExtGridData<>();
        if (!StringUtil.isEmpty(roleId)) {
            assigned = null == assigned ? false : assigned;
            List<ModelBtnTypeEntity> btnTypes = roleService.getBtnTypesByRoleId(roleId, assigned);
            retVal.setTotalProperty(btnTypes.size());
            List<ModelBtnTypeEntity> subList = PageUtil.page(btnTypes, page, limit);
            List<OperationBean> results = new ArrayList<>();
            subList.forEach(operation -> {
                OperationBean operationBean = new OperationBean();
                operationBean.setId(operation.getId());
                operationBean.setName(operation.getName());
                results.add(operationBean);
            });
            retVal.setResults(results);
        }
        return retVal;
    }

    /**
     * @param node
     * @param roleId
     * @return 根据角色id 以及父节点 展现功能点信息
     */
    public List<FuncBean> treeRoleFunctions(String node, String roleId) {
        List<String> expandedFunctionIds = new ArrayList<String>() {{
            add("1");
            add("2");
            add("3");
            add("8");
        }};
        List<FuncBean> retList = new ArrayList<>();
        //获取角色信息
        Role role = roleEngine.getRoleModel(false).getRoleById(roleId);
        //获取所有schema信息
        List<ISchema> schemas = role.getAllSchemas();
        //默认根节点转化
        node = "root".equals(node) ? "-1" : node;
        //动态Tbom
        if (node.startsWith("S")) {
            String schemaId = node.split("_")[1];
            String functionId = node.split("_")[2];
            //schema
            roleEngine.getRoleModel(false).getTbomDirs().forEach((tbomDirId, tbomDir) -> {
                if (!CommonTools.isNullString(tbomDir.getId())
                        && tbomDir.getSchemaid().equals(schemaId)) {
                    CheckFuncBean tbomBean = new CheckFuncBean();
                    tbomBean.setId("T_" + tbomDir.getId() + "_" + functionId);
                    tbomBean.setText(tbomDir.getName());
                    tbomBean.setIconCls("icon-function");
                    tbomBean.setChecked(checkChecked(role, tbomBean.getId()));
                    //子节点
                    retList.add(tbomBean);
                }
            });
        } else if (node.startsWith("T")) {
            //Tbom节点子节点
        } else {
            //普通功能点
            List<Function> funcs = functionService.findByPid(Long.parseLong(node));
            //如果包含tbom 则将tbomj节点增加至返回值中
            Function currentFunction = functionService.findById(node);
            if (null != currentFunction && currentFunction.getTbomFlg().equals("1")) {
                final String finalNode = node;
                schemas.forEach(schema -> {
                    CheckFuncBean schemaBean = new CheckFuncBean();
                    schemaBean.setId("S_" + schema.getId() + "_" + finalNode);
                    schemaBean.setText(schema.getName() + "[" + schema.getVersion() + "]");
                    schemaBean.setIconCls("icon-function");
                    schemaBean.setResults(treeRoleFunctions(schemaBean.getId(), roleId));
                    schemaBean.setChecked(checkChecked(role, schemaBean.getId()));
                    schemaBean.setExpanded(false);
                    //子节点
                    retList.add(schemaBean);
                });
            }
            for (Function func : funcs) {
                if (func.getFunctionid() == 100)
                    continue;
                CheckFuncBean funcBean = new CheckFuncBean();
                try {
                    PropertyUtils.copyProperties(funcBean, func);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                funcBean.setId(Long.toString(func.getFunctionid()));
                funcBean.setPid(Long.toString(func.getParentid()));
                funcBean.setText(funcBean.getName());
                funcBean.setIconCls("icon-function");
                funcBean.setResults(treeRoleFunctions(String.valueOf(funcBean.getId()), roleId));
                funcBean.setChecked(checkChecked(role, funcBean.getId()));
                String originalIcon = funcBean.getIcon();
                String newIcon = StringUtil.isEmpty(originalIcon) ? "" : originalIcon.substring(0, originalIcon.indexOf(FileOperator.getSuffix(originalIcon)) - 1) + "_small." + FileOperator.getSuffix(originalIcon);
                funcBean.setIcon(newIcon);
                if (!expandedFunctionIds.contains(Long.toString(func.getFunctionid()))) {
                    funcBean.setExpanded(false);
                }
                retList.add(funcBean);
            }
        }
        return retList;
    }

    private Boolean checkChecked(Role role, String functionId) {
        Boolean exists = false;
        Set<IRoleFunctionTbom> functionTboms = role.getRoleFunctionTboms();
        if (functionId.startsWith("T")) {
            String[] tbomFunctionDesc = functionId.split("_");
            String tbomId = tbomFunctionDesc[1];
            String realFunctionId = tbomFunctionDesc[2];
            exists = functionTboms.stream().filter(functionTbom ->
                    functionTbom.getFunction().getFunctionid().equals(Long.valueOf(realFunctionId)) && functionTbom.getTbomDir() != null && !StringUtil.isEmpty(functionTbom.getTbomDir().getId()) && Integer.valueOf(functionTbom.getTbomDir().getId()).equals(Integer.valueOf(tbomId))).count() > 0;
        } else if (functionId.startsWith("S")) {

        } else {
            exists = functionTboms.stream().filter(functionTbom -> Objects.equals(functionTbom.getFunction().getFunctionid(), Long.valueOf(functionId))).count() > 0;
        }
        return exists;
    }

    public void repairSchemaNodeChecked(List<FuncBean> retVal) {
        retVal.forEach(funcBean -> {
            CheckFuncBean checkFuncBean = (CheckFuncBean) funcBean;
            String id = checkFuncBean.getId();
            if (id.startsWith("S") && !checkFuncBean.getResults().isEmpty()) {
                final Boolean[] sonAllChecked = {true};
                checkFuncBean.getResults().forEach(sonFunctionBean -> {
                    CheckFuncBean sonCheckfunctionBean = (CheckFuncBean) sonFunctionBean;
                    sonAllChecked[0] = sonAllChecked[0] && sonCheckfunctionBean.getChecked();
                });
                checkFuncBean.setChecked(sonAllChecked[0]);
            }
            repairSchemaNodeChecked(checkFuncBean.getResults());
        });
    }

    /**
     * @param roleId      所属角色
     * @param selectedIds 所选数据
     * @param direction   分配方向
     * @return 保存权限分配
     */
    public boolean saveAssignRights(String roleId, String[] selectedIds, String direction) {
        List<String> toSaveIds = new ArrayList<>();
        //已经存在的
        OverAllOperations overAllOperations =
                (OverAllOperations) roleEngine.getRoleModel(false).getRoleById(roleId)
                        .getOverAllOperations().iterator().next();
        toSaveIds.addAll(CommonTools.arrayToList(overAllOperations.getOperationIds().split(",")));
        if ("left".equals(direction)) {
            //移除
            for (String selectedId : selectedIds) {
                toSaveIds.remove(selectedId);
            }
            if (toSaveIds.isEmpty()) {
                toSaveIds.add("0");
            }
        } else {
            toSaveIds.remove("0");
            //增加
            for (String selectedId : selectedIds) {
                toSaveIds.add(selectedId);
            }
        }
        roleService.updateOverAllOperations(roleId, CommonTools.list2String(toSaveIds));
        return true;
    }

    /**
     * @param roleId      所属角色
     * @param selectedIds 所选数据
     * @param direction   分配方向
     * @return 保存用户分配
     */
    public boolean saveAssignUsers(String roleId, String[] selectedIds, String direction) {
        if ("left".equals(direction)) {
            roleService.deleteRoleUsers(roleId, CommonTools.array2String(selectedIds));
        } else
            roleService.createRoleUsers(roleId, CommonTools.array2String(selectedIds));
        //刷新缓存
        for (String userId : selectedIds) {
            clearECache(userId);
        }
        return true;
    }

    private void clearECache(String userId) {
        businessModelCacheHelper.businessModelCache.getKeys().forEach(key -> {
            if (((String) key).endsWith(userId)) {
                businessModelCacheHelper.businessModelCache.remove(key);
            }
        });
    }

    /**
     * @param roleId      所属角色
     * @param selectedIds 所选数据
     * @param direction   分配方向
     * @return 保存schema分配
     */
    public boolean saveAssignSchema(String roleId, String[] selectedIds, String direction) {
        if ("left".equals(direction)) {
            roleService.deleteRoleSchemas(roleId, CommonTools.array2String(selectedIds));
        } else
            roleService.createRoleSchemas(roleId, CommonTools.array2String(selectedIds));
        return true;
    }


    /**
     * @param roleId      角色id
     * @param functionIds 功能点id
     * @return 保存功能点分配信息
     */
    public boolean saveAssignFunctions(String roleId, String[] functionIds) {
        roleService.updateRoleFunctions(roleId, functionIds);
        return true;
    }

    public List<RoleBean> listByIdFilter(String ids) {
        List<RoleBean> allRoles = new ArrayList<>();
        Iterator<Map.Entry<String, Role>> iter = roleEngine.getRoleModel(false).getRoles().entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Role> entry = iter.next();
            if (Integer.parseInt(entry.getKey()) > 0) {
                Role role = entry.getValue();
                if (ids.indexOf(role.getId()) != -1) {
                    RoleBean roleBean = new RoleBean();
                    try {
                        PropertyUtils.copyProperties(roleBean, role);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    allRoles.add(roleBean);
                }
            }
        }
        return allRoles;
    }

    /**
     * @param roleId   角色id
     * @param assigned 是否已经分配
     * @param page     第几页
     * @param limit    每页多少条
     * @return 根据当前角色信息 获取 分配磁贴信息
     */
    public ExtGridData<PortalBean> listPortals(String roleId, Boolean assigned, Integer page, Integer limit) {
        ExtGridData<PortalBean> retVal = new ExtGridData<>();
        List<Long> assignedPortalIds = rolePortalService.list(Restrictions.eq("roleId", roleId)).stream().map(CwmSysRolePortalEntity::getPortalId).collect(Collectors.toList());
        if (CommonTools.isEmptyList(assignedPortalIds)) {
            //增加默认过滤
            assignedPortalIds.add(-1l);
        }
        Criterion criterion = assigned ? Restrictions.in("id", assignedPortalIds) : Restrictions.not(Restrictions.in("id", assignedPortalIds));
        PageBean pageBean = new PageBean();
        pageBean.setRows(null == limit ? Integer.MAX_VALUE : limit);
        pageBean.setPage(null == page ? -1 : page);
        pageBean.addCriterion(criterion);
        pageBean.addOrder(Order.asc("id"));
        List<CwmPortalEntity> queryResult = portalService.listByPage(pageBean);
        List<PortalBean> results = new ArrayList<>(queryResult.size());
        queryResult.forEach(cwmPortalEntity -> {
            PortalBean portalBean = new PortalBean();
            portalBean.setId(cwmPortalEntity.getId().toString());
            portalBean.setName(cwmPortalEntity.getTitle());
            results.add(portalBean);
        });
        retVal.setTotalProperty(pageBean.getTotalCount());
        retVal.setResults(results);
        return retVal;
    }

    public boolean saveAssignPortals(String roleId, Long[] portalIds, String direction) {
        if ("left".equals(direction)) {
            //移除
            List<CwmSysRolePortalEntity> toDelEntities = rolePortalService.list(Restrictions.eq("roleId", roleId), Restrictions.in("portalId", portalIds));
            toDelEntities.forEach(toDelEntitie -> rolePortalService.delete(toDelEntitie));
        } else {
            //添加
            for (Long portalId : portalIds) {
                CwmSysRolePortalEntity cwmSysRolePortalEntity = new CwmSysRolePortalEntity();
                cwmSysRolePortalEntity.setPortalId(portalId);
                cwmSysRolePortalEntity.setRoleId(roleId);
                rolePortalService.save(cwmSysRolePortalEntity);
            }
        }
        return true;
    }

    private List<String> getAllDepartmentIds(String rootDeptId) {
        //获取所有部门
        Map<Integer, Department> departments = roleEngine.getRoleModel(false).getDepartments();
        List<Department> allDepartmentList = new ArrayList<>();
        for (Department department : departments.values()) {
            allDepartmentList.add(department);
        }
        List<String> resultIds = new ArrayList<>();
        recurGetDeptIds(rootDeptId, allDepartmentList, resultIds);
        return resultIds;
    }

    private void recurGetDeptIds(String rootDeptId, List<Department> allDepartmentList, List<String> resultIds) {
        resultIds.add(rootDeptId);
        boolean end = true;
        for (Department department : allDepartmentList) {
            if (department.getPid().equals(rootDeptId)) {
                end = false;
                recurGetDeptIds(rootDeptId, allDepartmentList, resultIds);
            }
        }
        if (end) {
            return;
        }
    }

}
