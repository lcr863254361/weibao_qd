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
        //?????????????????????
        String roleStatisticLineSql="delete from T_ROLE_STATISTICLINE_480  where C_ROLE_ID_3587 IN ("+toDelIds+")";
        jdbcTemplate.update(roleStatisticLineSql);
    }

    public List<RoleBean> search(Role roleInstance) {
        List<Role> roles = roleService.findByExampleLike(roleInstance);
        List<RoleBean> retList = new ArrayList<>();
        for (Role role : roles) {
            //???????????????????????????????????????
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
     * @param roleId   ??????id
     * @param assigned ???????????????
     * @param page     ?????????
     * @param limit    ???????????????
     * @return ?????????????????? ?????? ??????schema??????
     */
    public ExtGridData<SchemaBean> listSchemas(String roleId, Boolean assigned, Integer page, Integer limit) {
        ExtGridData<SchemaBean> retVal = new ExtGridData<>();
        if (!StringUtil.isEmpty(roleId)) {
            //??????????????????
            Role role = roleEngine.getRoleModel(false).getRoleById(roleId);
            //???????????????????????????schema??????
            List<ISchema> assignedSchemas = role.getAllSchemas();
            //????????????schema??????
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
     * @param roleId   ??????id
     * @param assigned ??????????????????
     * @param page     ?????????
     * @param limit    ???????????????
     * @return ???????????????????????? ?????? ??????????????????
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
     * @param roleId   ??????id
     * @param assigned ??????????????????
     * @param page     ?????????
     * @param limit    ???????????????
     * @return ???????????????????????? ?????? ??????????????????
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
     * @return ????????????id ??????????????? ?????????????????????
     */
    public List<FuncBean> treeRoleFunctions(String node, String roleId) {
        List<String> expandedFunctionIds = new ArrayList<String>() {{
            add("1");
            add("2");
            add("3");
            add("8");
        }};
        List<FuncBean> retList = new ArrayList<>();
        //??????????????????
        Role role = roleEngine.getRoleModel(false).getRoleById(roleId);
        //????????????schema??????
        List<ISchema> schemas = role.getAllSchemas();
        //?????????????????????
        node = "root".equals(node) ? "-1" : node;
        //??????Tbom
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
                    //?????????
                    retList.add(tbomBean);
                }
            });
        } else if (node.startsWith("T")) {
            //Tbom???????????????
        } else {
            //???????????????
            List<Function> funcs = functionService.findByPid(Long.parseLong(node));
            //????????????tbom ??????tbomj???????????????????????????
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
                    //?????????
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
     * @param roleId      ????????????
     * @param selectedIds ????????????
     * @param direction   ????????????
     * @return ??????????????????
     */
    public boolean saveAssignRights(String roleId, String[] selectedIds, String direction) {
        List<String> toSaveIds = new ArrayList<>();
        //???????????????
        OverAllOperations overAllOperations =
                (OverAllOperations) roleEngine.getRoleModel(false).getRoleById(roleId)
                        .getOverAllOperations().iterator().next();
        toSaveIds.addAll(CommonTools.arrayToList(overAllOperations.getOperationIds().split(",")));
        if ("left".equals(direction)) {
            //??????
            for (String selectedId : selectedIds) {
                toSaveIds.remove(selectedId);
            }
            if (toSaveIds.isEmpty()) {
                toSaveIds.add("0");
            }
        } else {
            toSaveIds.remove("0");
            //??????
            for (String selectedId : selectedIds) {
                toSaveIds.add(selectedId);
            }
        }
        roleService.updateOverAllOperations(roleId, CommonTools.list2String(toSaveIds));
        return true;
    }

    /**
     * @param roleId      ????????????
     * @param selectedIds ????????????
     * @param direction   ????????????
     * @return ??????????????????
     */
    public boolean saveAssignUsers(String roleId, String[] selectedIds, String direction) {
        if ("left".equals(direction)) {
            roleService.deleteRoleUsers(roleId, CommonTools.array2String(selectedIds));
        } else
            roleService.createRoleUsers(roleId, CommonTools.array2String(selectedIds));
        //????????????
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
     * @param roleId      ????????????
     * @param selectedIds ????????????
     * @param direction   ????????????
     * @return ??????schema??????
     */
    public boolean saveAssignSchema(String roleId, String[] selectedIds, String direction) {
        if ("left".equals(direction)) {
            roleService.deleteRoleSchemas(roleId, CommonTools.array2String(selectedIds));
        } else
            roleService.createRoleSchemas(roleId, CommonTools.array2String(selectedIds));
        return true;
    }


    /**
     * @param roleId      ??????id
     * @param functionIds ?????????id
     * @return ???????????????????????????
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
     * @param roleId   ??????id
     * @param assigned ??????????????????
     * @param page     ?????????
     * @param limit    ???????????????
     * @return ???????????????????????? ?????? ??????????????????
     */
    public ExtGridData<PortalBean> listPortals(String roleId, Boolean assigned, Integer page, Integer limit) {
        ExtGridData<PortalBean> retVal = new ExtGridData<>();
        List<Long> assignedPortalIds = rolePortalService.list(Restrictions.eq("roleId", roleId)).stream().map(CwmSysRolePortalEntity::getPortalId).collect(Collectors.toList());
        if (CommonTools.isEmptyList(assignedPortalIds)) {
            //??????????????????
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
            //??????
            List<CwmSysRolePortalEntity> toDelEntities = rolePortalService.list(Restrictions.eq("roleId", roleId), Restrictions.in("portalId", portalIds));
            toDelEntities.forEach(toDelEntitie -> rolePortalService.delete(toDelEntitie));
        } else {
            //??????
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
        //??????????????????
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
