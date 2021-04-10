package com.orient.sysman.bussiness;


import com.google.common.base.Joiner;
import com.orient.metamodel.metadomain.Enum;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.sysman.bean.RoleBean;
import com.orient.sysman.bean.UserBean;
import com.orient.sysman.bean.UserFilter;
import com.orient.sysmodel.domain.user.Department;
import com.orient.sysmodel.domain.user.LightweightUser;
import com.orient.sysmodel.domain.user.User;
import com.orient.sysmodel.operationinterface.IRole;
import com.orient.sysmodel.operationinterface.IRoleModel;
import com.orient.sysmodel.service.role.RoleService;
import com.orient.sysmodel.service.user.DepartmentService;
import com.orient.sysmodel.service.user.UserColumnService;
import com.orient.sysmodel.service.user.UserService;
import com.orient.utils.*;
import com.orient.utils.ExcelUtil.Excel;
import com.orient.utils.ExcelUtil.reader.DataEntity;
import com.orient.utils.ExcelUtil.reader.FieldEntity;
import com.orient.utils.ExcelUtil.reader.TableEntity;
import com.orient.web.base.BaseBusiness;
import com.orient.web.base.ExtComboboxResponseData;
import com.orient.web.base.ExtGridData;
import com.orient.web.form.service.impl.FormService;
import com.orient.web.util.UserContextUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2016/3/25.
 */
@Service
@Transactional
public class UserBusiness extends BaseBusiness {
    @Resource(name = "UserService")
    UserService userService;

    @Autowired
    FormService formService;

    @Autowired
    UserColumnService UserColumnService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    RoleBusiness roleBusiness;

    @Autowired
    MetaDAOFactory metaDAOFactory;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Resource(name = "RoleService")
    RoleService roleService;

    public List<UserBean> findAllUser() {
        Map<String, User> users = roleEngine.getRoleModel(false).getUsers();
//        List<Enum> personClassifyEnums = formService.getEnums("u5");
        List<UserBean> retList = new ArrayList<>();
        String deptJsonTemp = "{\"id\":\"#ID#\",\"text\":\"#TEXT#\"}";
        users.forEach((userId, user) -> {
            //列表页面不列出三元账号信息
            if (Integer.parseInt(user.getId()) > 0 && !"0".equals(user.getState())) {
                UserBean ub = new UserBean();
                ub.setId(user.getId());
                ub.setUserName(user.getUserName());
                ub.setAllName(user.getAllName());
                ub.setPassword(user.getPassword());
                ub.setSex(user.getSex());
                ub.setBirthday(user.getBirthday());
                ub.setMobile(user.getMobile());
                ub.setPhone(user.getPhone());
                ub.setEmail(user.getEmail());
                ub.setPost(user.getPost());
                ub.setGrade(user.getGrade());
                ub.setNotes(user.getNotes());
                ub.setUnit(user.getUnit());
                ub.setPersonClassify(user.getPersonClassify());
                ub.setCountry(user.getCountry());
                ub.setNation(user.getNation());
                ub.setIdentityCardNumber(user.getIdentityCardNumber());
                //设置显示具体的分类名称
//                for (Enum e : personClassifyEnums) {
//                    if (e.getValue().equals(user.getPersonClassify())) {
//                        ub.setPersonClassify(e.getDisplayValue());
//                    }
//                }
                Department dept = user.getDept();
                if (dept != null) {
                    String deptJson = deptJsonTemp.replace("#ID#", dept.getId()).replace("#TEXT#", dept.getName());
                    ub.setDepartment(deptJson);
                    ub.setDepartmentId(user.getDept().getId());
                }
                retList.add(ub);
            }
        });

        //对用户按账号进行排序
        List<UserBean> sortedRetList = retList.stream()
                .sorted(Comparator.comparing(UserBean::getUserName))
                .collect(Collectors.toList());
        dataChange(sortedRetList);
        return sortedRetList;
    }

    private void dataChange(List<UserBean> retVal) {
        String jsonTemp = "{\"id\":\"#ID#\",\"value\":\"#VALUE#\"}";
        List<Enum> sexEnums = formService.getEnums("u1");
        Map<String, String> sexMap = new HashMap<>();
        for (Enum sexEnum : sexEnums) {
            sexMap.put(sexEnum.getValue(), sexEnum.getDisplayValue());
        }

        List<Enum> postEnums = formService.getEnums("u2");
        Map<String, String> postMap = new HashMap<>();
        for (Enum postEnum : postEnums) {
            postMap.put(postEnum.getValue(), postEnum.getDisplayValue());
        }

        List<Enum> gradeEnums = formService.getEnums("u3");
        Map<String, String> gradeMap = new HashMap<>();
        for (Enum gradeEnum : gradeEnums) {
            gradeMap.put(gradeEnum.getValue(), gradeEnum.getDisplayValue());
        }

        List<Enum> personClassifyEnums = formService.getEnums("u5");
        Map<String, String> personClassifyMap = new HashMap<>();
        for (Enum classifyEnum : personClassifyEnums) {
            personClassifyMap.put(classifyEnum.getValue(), classifyEnum.getDisplayValue());
        }

        retVal.forEach(userBean -> {
            String sexId = userBean.getSex();
            String postId = userBean.getPost();
            String gradeId = userBean.getGrade();
            String classifyId = userBean.getPersonClassify();
            if (!StringUtil.isEmpty(sexId)) {
                String json = jsonTemp.replace("#ID#", sexId).replace("#VALUE#", sexMap.get(sexId));
                userBean.setSex(json);
            }
            if (!StringUtil.isEmpty(postId)) {
                String json = jsonTemp.replace("#ID#", postId).replace("#VALUE#", postMap.get(postId));
                userBean.setPost(json);
            }
            if (!StringUtil.isEmpty(gradeId)) {
                String json = jsonTemp.replace("#ID#", gradeId).replace("#VALUE#", gradeMap.get(gradeId));
                userBean.setGrade(json);
            }
            if (!StringUtil.isEmpty(classifyId)) {
                String json = jsonTemp.replace("#ID#", classifyId).replace("#VALUE#", personClassifyMap.get(classifyId));
                userBean.setPersonClassify(json);
            }
        });
    }

    public void create(User user, String deptId, String roleIds) {
        user.setPassword(PasswordUtil.generatePassword(user.getPassword()));
        User currentUser = UserContextUtil.getCurrentUser();
        user.setCreateUser(currentUser.getUserName());
        user.setCreateTime(new Date());
        user.setState("1");
        if (deptId != null && !deptId.equals("")) {
            Department dept = roleEngine.getRoleModel(false).getDepartments().get(Integer.parseInt(deptId));
            user.setDept(dept);
            dept.getUsers().add(user);
        }
        userService.createUser(user);
        String newUserId[] = {user.getId()};
        if (!"".equals(roleIds) && roleIds != null) {
            String roleIdsArray[] = roleIds.split(",");
            List<String> roleIdsList = Arrays.asList(roleIdsArray);
            for (String roleId : roleIdsList) {
                roleService.createRoleUsers(roleId, CommonTools.array2String(newUserId));
            }
        }
    }

    public void update(User userUpdate, String deptId, String roleIds) {
        User currentUser = UserContextUtil.getCurrentUser();
        userUpdate.setUpdateUser(currentUser.getUserName());
        userUpdate.setUpdateTime(new Date());

        //将不能显示的隐藏字段复制到userUpdate中
        User userDB = roleEngine.getRoleModel(false).getUsers().get(userUpdate.getId());
        userUpdate.setState(userDB.getState());
        userUpdate.setCreateTime(userDB.getCreateTime());
        userUpdate.setCreateUser(userDB.getCreateUser());
        userUpdate.setLoginFailures(userDB.getLoginFailures());
        userUpdate.setLastFailureTime(userDB.getLastFailureTime());
        userUpdate.setLockTime(userDB.getLockTime());
        userUpdate.setLockState(userDB.getLockState());
        userUpdate.setPasswordSetTime(userDB.getPasswordSetTime());

//        if (!userUpdate.getPassword().equals(userDB.getPassword())) {
        userUpdate.setPassword(PasswordUtil.generatePassword(userUpdate.getPassword()));
//        }
        Department dept = null;
        if (deptId != null && !deptId.equals("")) {
            dept = roleEngine.getRoleModel(false).getDepartments().get(Integer.parseInt(deptId));
        }
        userUpdate.setDept(dept);
        if (userDB.getDept() != null) {
            userDB.getDept().deleteUser(userDB);
        }
        if (userUpdate.getDept() != null) {
            userUpdate.getDept().getUsers().add(userDB);
        }

        userService.updateUser(userUpdate);
        try {
            BeanUtils.copyProperties(userUpdate, userDB);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String newUserId[] = {userUpdate.getId()};
        if (!"".equals(roleIds) && roleIds != null) {
            IRoleModel roleModel = roleEngine.getRoleModel(false);
            List<IRole> roles = roleModel.getRolesOfUser(userUpdate.getId());
            if (roles != null && roles.size() > 0) {
//                String roleUserSql = "delete from CWM_SYS_ROLE_USER WHERE 1=1 AND ROLE_ID=? AND USER_ID=?";
                for (IRole role : roles) {
                    String roleId = role.getId();
//                    jdbcTemplate.update(roleUserSql, roleId, userUpdate.getId());
                    roleService.deleteRoleUsers(roleId, CommonTools.array2String(newUserId));
                }
            }
            String roleIdsArray[] = roleIds.split(",");
            List<String> roleIdsList = Arrays.asList(roleIdsArray);
            for (String roleId : roleIdsList) {
                roleService.createRoleUsers(roleId, CommonTools.array2String(newUserId));
            }
        }
    }

    public void delete(String toDelIds) {
        Map<String, User> users = roleEngine.getRoleModel(false).getUsers();
        String[] ids = toDelIds.split(",");
        for (int i = 0; i < ids.length; i++) {
            User delUser = users.get(ids[i]);
            if (delUser.getDept() != null)
                delUser.getDept().deleteUser(delUser);
        }
        userService.delete(toDelIds);
    }

    public List<UserBean> search(LightweightUser queryUser, Map<String, String> betweens, String deptId) {
        List<LightweightUser> users = userService.findByExampleLike(queryUser, betweens);
        List<UserBean> retList = new ArrayList<>();
        List<String> alldepFilterValues = getAllDepartmentsIds(deptId);
        for (LightweightUser user : users) {
            //列表页面不列出三元账号信息
            if (Integer.parseInt(user.getId()) < 0) {
                continue;
            }
            if ("0".equals(user.getState())) {
                continue;
            }
            //底层是通过like匹配的，选项性属性还需进行严格的等值匹配
            if (queryUser.getSex() != null) {
                if (!user.getSex().equals(queryUser.getSex())) {
                    continue;
                }
            }
            if (queryUser.getPost() != null) {
                if (!user.getPost().equals(queryUser.getPost())) {
                    continue;
                }
            }
            if (queryUser.getGrade() != null) {
                if (!user.getGrade().equals(queryUser.getGrade())) {
                    continue;
                }
            }

            //查询子部门
            if (!(deptId != null && user.getDept() != null)) {
                continue;
            }
            boolean flag = false;
            for (String id : alldepFilterValues) {
                if (!StringUtil.isEmpty(user.getDept().getId()) && user.getDept().getId().equals(id)) {
                    flag = true;
                }
            }
            if (!flag) {
                continue;
            }
//            if (deptId != null && user.getDept() != null) {
//                if (!user.getDept().getId().equals(deptId)) {
//                    continue;
//                }
//            }

            UserBean ub = new UserBean();
            try {
                PropertyUtils.copyProperties(ub, user);
                Map<String, String> showMap = new HashMap<String, String>() {{
                    put("id", user.getDept().getId());
                    put("text", user.getDept().getName());
                }};
                ub.setDepartment(JsonUtil.toJson(showMap));
            } catch (Exception e) {
                e.printStackTrace();
            }
            retList.add(ub);
        }
        dataChange(retList);
        return retList;
    }

    public Map<String, Object> importUsers(TableEntity excelEntity) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<User> newUsers = new ArrayList<>();
        List<String> newUserDeptIds = new ArrayList<>();
        List<User> updateUsers = new ArrayList<>();
        List<String> updateUserDeptIds = new ArrayList<>();
        List<String> updateUserRoleIds = new ArrayList<>();
        List<String> newUserRoleIds = new ArrayList<>();

        Map<String, Object> retVal = new HashMap<>();

        List<User> dbUsers = new ArrayList();
        dbUsers.addAll(roleEngine.getRoleModel(false).getUsers().values());
        List<Department> dbDepts = departmentService.findAll();
        List<Enum> sexEnums = formService.getEnums("u1");
        List<Enum> postEnums = formService.getEnums("u2");
        List<Enum> gradeEnums = formService.getEnums("u3");
        List<Enum> personClassifyEnums = formService.getEnums("u5");
        List<DataEntity> dataEntities = excelEntity.getDataEntityList();
        List<String> invalidUserList = UtilFactory.newArrayList();

        List<RoleBean> roleBeanList = roleBusiness.findAll(null, null);

        for (DataEntity dataEntity : dataEntities) {
            List<FieldEntity> fieldEntities = dataEntity.getFieldEntityList();
            String rowValue = dataEntity.getPkVal();
            if (rowValue == null) {
                continue;
            }
            int rowNumber = Integer.parseInt(rowValue) + 1;
            User excelUser = new User();
            String excelDeptId = null;
            StringBuffer excelRoleIdBuffer = new StringBuffer();
            int fieldNum = fieldEntities.size();
            if (fieldNum > 12) {
                fieldNum = 12;
            }
            switch (fieldNum) {
                case 12:
                    String phone = fieldEntities.get(12).getValue();
                    if (phone == null || "".equals(phone)) {
                        retVal.put("success", false);
                        retVal.put("msg", "第" + rowNumber + "行" + "电话" + " 不能为空，请修正后导入！");
                        return retVal;
                    }
                    excelUser.setMobile(fieldEntities.get(12).getValue());
                case 11:
                    excelUser.setNation(fieldEntities.get(11).getValue());
                case 10:
                    excelUser.setCountry(fieldEntities.get(10).getValue());
                case 9:
                    String birthday = CommonTools.Obj2String(fieldEntities.get(9).getValue());
                    if (!"".equals(birthday) && birthday != null) {
                        excelUser.setBirthday(simpleDateFormat.parse(birthday));
                    }
                case 8:
                    excelUser.setEmail(fieldEntities.get(8).getValue());
                case 7:
                    String identityCardNumbers = fieldEntities.get(7).getValue();
                    if (!"".equals(identityCardNumbers) && identityCardNumbers != null && identityCardNumbers.length() < 6) {
                        retVal.put("success", false);
                        retVal.put("msg", "第" + rowNumber + "行" + "身份证号" + " 长度至少为6位，请修正后导入！");
                        return retVal;
                    }
                    excelUser.setIdentityCardNumber(identityCardNumbers);
                case 6:
                    String unitField = fieldEntities.get(6).getValue();
                    excelUser.setUnit(StringUtil.isEmpty(unitField) ? null : unitField);
                case 5:
                    String sexField = fieldEntities.get(5).getValue();
                    if (sexField != null && !sexField.equals("")) {
                        //String sexValue = formService.getValueByDisplayValue("u1", sexField);
                        String sexValue = null;
                        for (Enum e : sexEnums) {
                            if (e.getDisplayValue().equals(sexField)) {
                                sexValue = e.getValue();
                            }
                        }
                        if (sexValue == null) {
                            retVal.put("success", false);
                            retVal.put("msg", "第" + rowNumber + "行性别 " + sexField + " 不存在，请修正后导入！");
                            return retVal;
                        } else {
                            excelUser.setSex(sexValue);
                        }
                    } else {
                        excelUser.setSex("");
                    }
                case 4: {
                    String roleField = fieldEntities.get(4).getValue();
                    if (roleField != null && !roleField.equals("")) {
                        String regex = ",|，";
                        String roleFieldArray[] = roleField.split(regex);
                        List<String> roleNamesList = Arrays.asList(roleFieldArray);
                        List<String> existRoleNameList = UtilFactory.newArrayList();
                        for (String roleName : roleNamesList) {
                            for (RoleBean roleBean : roleBeanList) {
                                if (roleBean.getName().equals(roleName)) {
                                    excelRoleIdBuffer.append(roleBean.getId()).append(",");
                                    existRoleNameList.add(roleName);
                                    break;
                                }
                            }
                        }
                        for (String roleName : roleNamesList) {
                            if (existRoleNameList.size() > 0) {
                                if (!existRoleNameList.contains(roleName)) {
                                    retVal.put("success", false);
                                    retVal.put("msg", "第" + rowNumber + "行权限 " + roleName + " 不存在，请修正后导入！");
                                    return retVal;
                                }
                            }
                        }
                        if (excelRoleIdBuffer == null) {
                            retVal.put("success", false);
                            retVal.put("msg", "第" + rowNumber + "行权限 " + roleField + " 不存在，请修正后导入！");
                            return retVal;
                        }
                    }
                }
                case 3:
                    String personClassifyField = fieldEntities.get(3).getValue();
                    if (personClassifyField != null && !personClassifyField.equals("")) {
                        String personClassifyValue = null;
                        for (Enum e : personClassifyEnums) {
                            if (e.getDisplayValue().equals(personClassifyField)) {
                                personClassifyValue = e.getValue();
                            }
                        }
                        if (personClassifyValue == null) {
                            retVal.put("success", false);
                            retVal.put("msg", "第" + rowNumber + "行分类 " + personClassifyField + " 不存在，请修正后导入！");
                            return retVal;
                        } else {
                            excelUser.setPersonClassify(personClassifyValue);
                        }
                    } else {
                        excelUser.setPersonClassify("");
                    }
                case 2:
                    String deptField = fieldEntities.get(2).getValue();
                    if (deptField != null && !deptField.equals("")) {
                        for (Department department : dbDepts) {
                            if (department.getName().equals(deptField)) {
                                excelDeptId = department.getId();
                                break;
                            }
                        }
                        if (excelDeptId == null) {
                            retVal.put("success", false);
                            retVal.put("msg", "第" + rowNumber + "行部门 " + deptField + " 不存在，请修正后导入！");
                            return retVal;
                        }
                    }
                case 1:
                    String allNameField = fieldEntities.get(1).getValue();
                    if (allNameField == null || allNameField.equals("")) {
                        retVal.put("success", false);
                        retVal.put("msg", "第" + rowNumber + "行真实姓名不可为空，请修正后导入！");
                        return retVal;
                    }
                    String userNameField = "";
//                    String identityCardNumber = excelUser.getIdentityCardNumber();
//                    if (!"".equals(identityCardNumber) && identityCardNumber != null) {
//                        userNameField = identityCardNumber.substring(identityCardNumber.length() - 6);
//                    }
                    //用户名默认为手机号
                    String mobile = excelUser.getMobile();
                    if (!"".equals(mobile) && mobile != null) {
//                        userNameField = mobile.substring(mobile.length() - 6);
                        userNameField = mobile;
                    }

                    //密码默认为身份证号后六位
                    String passwordField = "";
                    String identityCardNumber = excelUser.getIdentityCardNumber();
                    if (!"".equals(identityCardNumber) && identityCardNumber != null) {
                        passwordField = identityCardNumber.substring(identityCardNumber.length() - 6);
                    } else {
                        passwordField = "123456";
                    }
                    if (userNameField == null || userNameField.equals("")) {
                        if (!"".equals(allNameField) && allNameField != null) {
                            invalidUserList.add(allNameField);
                        }
                    } else {
                        excelUser.setUserName(userNameField);
                        excelUser.setAllName(allNameField);
                        excelUser.setPassword(StringUtil.isEmpty(passwordField) ? null : passwordField);
                        String excelRoleIds = excelRoleIdBuffer.toString();
                        if (!"".equals(excelRoleIds) && excelRoleIds != null) {
                            excelRoleIds = excelRoleIds.substring(0, excelRoleIds.length() - 1);
                        }
                        boolean userExist = false;
                        for (User dbUser : dbUsers) {
                            if (dbUser.getUserName().equals(excelUser.getUserName())) {
                                userExist = true;
                                excelUser.setId(dbUser.getId());
                                try {
                                    excelUser.setPassword(StringUtil.isEmpty(excelUser.getPassword()) ? dbUser.getPassword() : excelUser.getPassword());
                                    com.orient.utils.BeanUtils.copyNotNullProperties(dbUser, excelUser);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                //用户存在
                                updateUsers.add(dbUser);
                                updateUserDeptIds.add(excelDeptId);
                                updateUserRoleIds.add(excelRoleIds);
                                break;
                            }
                        }
                        //用户不存在
                        if (userExist == false) {
                            newUsers.add(excelUser);
                            newUserDeptIds.add(excelDeptId);
                            newUserRoleIds.add(excelRoleIds);
                        }
                    }
                    break;
                default:
                    retVal.put("success", false);
                    retVal.put("msg", "电话、真实姓名、身份证号不可为空，请修正后导入！");
                    return retVal;
            }
        }
        for (int i = 0; i < newUsers.size(); i++) {
            create(newUsers.get(i), newUserDeptIds.get(i), newUserRoleIds.get(i));
        }
        for (int i = 0; i < updateUsers.size(); i++) {
            update(updateUsers.get(i), updateUserDeptIds.get(i), updateUserRoleIds.get(i));
        }
        String invalidUserStr = "";
        if (invalidUserList != null && invalidUserList.size() > 0) {
            invalidUserStr = Joiner.on(",").join(invalidUserList);
//            retVal.put("msg", "导入成功,发现"+invalidUserStr+"用户身份证号或电话为空，无法导入！");
        }
        retVal.put("invalidUser", invalidUserStr);
        retVal.put("msg", "导入成功！");
        retVal.put("success", true);
        return retVal;
    }

    public void exportUsers(boolean exportAll, String toExportIds) {
        Excel excel = new Excel();
//        Object[] headers = new Object[]{"用户名称", "真实姓名", "密码", "单位", "分类", "出生年月日", "专业", "电话", "手机", "邮箱", "备注", "性别", "国家", "民族", "身份证号", "职务", "密级", "部门"};
        Object[] headers = new Object[]{"真实姓名", "权限部门", "权限岗位", "权限", "性别", "单位", "身份证号", "邮箱", "出生年月日", "国家", "民族", "电话"};
        excel.row(0).value(headers);
        List<Enum> personClassifyEnums = formService.getEnums("u5");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (exportAll) {
            Map<String, User> users = roleEngine.getRoleModel(false).getUsers();
            final int[] i = {1};
            users.forEach((userId, user) -> {
                if (Integer.parseInt(user.getId()) > 0 && user.getState().equals("1")) {
                    excel.cell(i[0], 0).value(user.getAllName());
                    if (user.getDept() != null) {
                        excel.cell(i[0], 1).value(user.getDept().getName());
                    }
                    //设置显示具体的分类名称,权限岗位
                    for (Enum e : personClassifyEnums) {
                        if (e.getValue().equals(user.getPersonClassify())) {
                            excel.cell(i[0], 2).value(e.getDisplayValue());
                        }
                    }
                    List<IRole> roleList=roleEngine.getRoleModel(false).getRolesOfUser(userId);
                    List<String> roleNameList=UtilFactory.newArrayList();
                    if (roleList!=null&&roleList.size()>0){
                        for (IRole role:roleList){
                            String roleName=role.getName();
                            roleNameList.add(roleName);
                        }
                    }
                    String roleNames="";
                    if (roleNameList!=null&&roleNameList.size()>0){
                         roleNames=Joiner.on(',').join(roleNameList);
                    }
                    excel.cell(i[0], 3).value(roleNames);
                    excel.cell(i[0], 4).value(user.getSexValue());
                    excel.cell(i[0], 5).value(user.getUnit());
                    excel.cell(i[0], 6).value(user.getIdentityCardNumber());
                    excel.cell(i[0], 7).value(user.getEmail());
                    if (user.getBirthday() != null) {
                        excel.cell(i[0], 8).value(simpleDateFormat.format(user.getBirthday()));
                    }
                    excel.cell(i[0], 9).value(user.getCountry());
                    excel.cell(i[0], 10).value(user.getNation());
                    excel.cell(i[0], 11).value(user.getMobile());

//                    excel.cell(i[0], 0).value(user.getUserName());
//                    excel.cell(i[0], 1).value(user.getAllName());
//                    excel.cell(i[0], 2).value("");
//                    excel.cell(i[0], 3).value(user.getUnit());
//                    //设置显示具体的分类名称
//                    for (Enum e : personClassifyEnums) {
//                        if (e.getValue().equals(user.getPersonClassify())) {
//                            excel.cell(i[0], 4).value(e.getDisplayValue());
//                        }
//                    }
//                    if (user.getBirthday() != null) {
//                        excel.cell(i[0], 5).value(simpleDateFormat.format(user.getBirthday()));
//                    }
//                    excel.cell(i[0], 6).value(user.getSpecialty());
//                    excel.cell(i[0], 7).value(user.getPhone());
//                    excel.cell(i[0], 8).value(user.getMobile());
//                    excel.cell(i[0], 9).value(user.getEmail());
//                    excel.cell(i[0], 10).value(user.getNotes());
//                    excel.cell(i[0], 11).value(user.getSexValue());
//
//                    excel.cell(i[0], 12).value(user.getCountry());
//                    excel.cell(i[0], 13).value(user.getNation());
//                    excel.cell(i[0], 14).value(user.getIdentityCardNumber());
//
//                    excel.cell(i[0], 15).value(user.getPostValue());
//                    excel.cell(i[0], 16).value(user.getGradeValue());
//                    if (user.getDept() != null) {
//                        excel.cell(i[0], 17).value(user.getDept().getName());
//                    }
                    i[0]++;
                }
            });

        } else {
            Map<String, User> users = roleEngine.getRoleModel(false).getUsers();
            if (!toExportIds.equals("")) {
                String[] ids = toExportIds.split(",");
                for (int i = 0; i < ids.length; i++) {
                    User user = users.get(ids[i]);
                    excel.cell(i + 1, 0).value(user.getAllName());
                    if (user.getDept() != null) {
                        excel.cell(i + 1, 1).value(user.getDept().getName());
                    }
                    //设置显示具体的分类名称,权限岗位
                    for (Enum e : personClassifyEnums) {
                        if (e.getValue().equals(user.getPersonClassify())) {
                            excel.cell(i + 1, 2).value(e.getDisplayValue());
                        }
                    }
                    List<IRole> roleList=roleEngine.getRoleModel(false).getRolesOfUser(user.getId());
                    List<String> roleNameList=UtilFactory.newArrayList();
                    if (roleList!=null&&roleList.size()>0){
                        for (IRole role:roleList){
                            String roleName=role.getName();
                            roleNameList.add(roleName);
                        }
                    }
                    String roleNames="";
                    if (roleNameList!=null&&roleNameList.size()>0){
                        roleNames=Joiner.on(',').join(roleNameList);
                    }
                    excel.cell(i + 1, 3).value(roleNames);
                    excel.cell(i + 1, 4).value(user.getSexValue());
                    excel.cell(i + 1, 5).value(user.getUnit());
                    excel.cell(i + 1, 6).value(user.getIdentityCardNumber());
                    excel.cell(i + 1, 7).value(user.getEmail());
                    if (user.getBirthday() != null) {
                        excel.cell(i + 1, 8).value(simpleDateFormat.format(user.getBirthday()));
                    }
                    excel.cell(i + 1, 9).value(user.getCountry());
                    excel.cell(i + 1, 10).value(user.getNation());
                    excel.cell(i + 1, 11).value(user.getMobile());

//                    excel.cell(i + 1, 0).value(user.getUserName());
//                    excel.cell(i + 1, 1).value(user.getAllName());
//                    excel.cell(i + 1, 2).value("");
//                    excel.cell(i + 1, 3).value(user.getUnit());
//                    //设置显示具体的分类名称
//                    for (Enum e : personClassifyEnums) {
//                        if (e.getValue().equals(user.getPersonClassify())) {
//                            excel.cell(i + 1, 4).value(e.getDisplayValue());
//                        }
//                    }
//                    if (user.getBirthday() != null) {
//                        excel.cell(i + 1, 5).value(simpleDateFormat.format(user.getBirthday()));
//                    }
//                    excel.cell(i + 1, 6).value(user.getSpecialty());
//                    excel.cell(i + 1, 7).value(user.getPhone());
//                    excel.cell(i + 1, 8).value(user.getMobile());
//                    excel.cell(i + 1, 9).value(user.getEmail());
//                    excel.cell(i + 1, 10).value(user.getNotes());
//                    excel.cell(i + 1, 11).value(user.getSexValue());
//
//                    excel.cell(i + 1, 12).value(user.getCountry());
//                    excel.cell(i + 1, 13).value(user.getNation());
//                    excel.cell(i + 1, 14).value(user.getIdentityCardNumber());
//
//                    excel.cell(i + 1, 15).value(user.getPostValue());
//                    excel.cell(i + 1, 16).value(user.getGradeValue());
//                    if (user.getDept() != null) {
//                        excel.cell(i + 1, 17).value(user.getDept().getName());
//                    }
                }
            }
        }
        for (int i = 0; i < 15; i++) {
            excel.column(i).autoWidth();
        }
        excel.saveExcel("users.xls");
    }

    public ExtComboboxResponseData<Map<String, String>> getUserColumCombobox() {
        ExtComboboxResponseData<Map<String, String>> retVal = new ExtComboboxResponseData<>();
        List<Map<String, String>> columnDesc = new ArrayList<>();
        UserColumnService.findAllUserColumn().forEach(userColumn -> {
            Map<String, String> map = new HashMap<String, String>();
            map.put("columnName", userColumn.getSColumnName());
            map.put("displayName", userColumn.getDisplayName());
            columnDesc.add(map);
        });
        retVal.setTotalProperty(columnDesc.size());
        retVal.setResults(columnDesc);
        return retVal;
    }

    public ExtGridData<UserBean> listByFilter(String extraFilter) {
        ExtGridData<UserBean> retVal = new ExtGridData<>();
        List<UserBean> users = findAllUser();
        List<UserBean> finalUserBeans = new ArrayList<>();
        if (!StringUtil.isEmpty(extraFilter)) {
            UserFilter userFilter = JsonUtil.jsonToObj(new UserFilter(), extraFilter);
            String idFilterType = null != userFilter.getIdFilter() ? userFilter.getIdFilter().keySet().iterator().next() : "";
            String idFilterValue = null != userFilter.getIdFilter() ? userFilter.getIdFilter().get(idFilterType) : "";
            String roleFilterType = null != userFilter.getRoleFilter() ? userFilter.getRoleFilter().keySet().iterator().next() : "";
            String roleFilterValue = null != userFilter.getRoleFilter() ? userFilter.getRoleFilter().get(roleFilterType) : "";
            String depFilterType = null != userFilter.getDepFilter() ? userFilter.getDepFilter().keySet().iterator().next() : "";
            String depFilterValue = null != userFilter.getDepFilter() ? userFilter.getDepFilter().get(depFilterType) : "";
            List<String> alldepFilterValues = getAllDepartmentsIds(depFilterValue);
            finalUserBeans = users.stream().filter(userBean -> {
                Boolean flag = true;
                if (!StringUtil.isEmpty(idFilterType)) {
                    if ("in".equals(idFilterType)) {
                        flag = idFilterValue.contains(userBean.getId());
                    } else
                        flag = !idFilterValue.contains(userBean.getId());
                }
                if (!StringUtil.isEmpty(roleFilterType)) {
                    List<IRole> roles = roleEngine.getRoleModel(false).getRolesOfUser(userBean.getId());
                    List<String> roleFilterArray = CommonTools.arrayToList(roleFilterValue.split(","));
                    if ("in".equals(roleFilterType)) {
                        flag = flag && roles.stream().filter(iRole -> roleFilterArray.contains(iRole.getId())).count() > 0;
                    } else
                        flag = flag && roles.stream().filter(iRole -> roleFilterArray.contains(iRole.getId())).count() == 0l;
                }
                if (!StringUtil.isEmpty(depFilterType)) {
                    if ("in".equals(depFilterType)) {
                        flag = flag && !StringUtil.isEmpty(userBean.getDepartmentId());
                        //查询子部门
                        boolean childFlag = false;
                        for (String id : alldepFilterValues) {
                            if (!StringUtil.isEmpty(userBean.getDepartmentId()) && userBean.getDepartmentId().equals(id)) {
                                childFlag = true;
                            }
                        }
                        flag = flag && childFlag;
                    } else if ("noDep".equals(depFilterType)) {
                        flag = flag && StringUtil.isEmpty(userBean.getDepartmentId());
                    } else
                        flag = flag && !StringUtil.isEmpty(userBean.getDepartmentId()) && !depFilterValue.contains(userBean.getDepartmentId());
                }
                return flag;
            }).collect(Collectors.toList());
        } else {
            finalUserBeans.addAll(users);
        }
        retVal.setSuccess(true);
        retVal.setTotalProperty(finalUserBeans.size());
        retVal.setResults(finalUserBeans);
        return retVal;
    }

    /**
     * @param rootDeptId 根部门的id
     * @return 包括rootId在内的所有子部门id
     */
    private List<String> getAllDepartmentsIds(String rootDeptId) {
        //获取所有部门
        Map<Integer, Department> allDepartments = roleEngine.getRoleModel(false).getDepartments();
        List<Department> allDepartmentsList = new ArrayList<>();
        for (Department department : allDepartments.values()) {
            allDepartmentsList.add(department);
        }

        List<String> resultIds = new ArrayList<>();
        recurGetDeptIds(rootDeptId, allDepartmentsList, resultIds);
        return resultIds;
    }

    /**
     * 递归查询所有部门id
     */
    private void recurGetDeptIds(String rootDeptId, List<Department> allDepartmentsList, List<String> resultIds) {
        resultIds.add(rootDeptId);
        boolean end = true;
        for (Department department : allDepartmentsList) {
            if (department.getPid().equals(rootDeptId)) {
                end = false;
                recurGetDeptIds(department.getId(), allDepartmentsList, resultIds);
            }
        }
        //递归终止
        if (end == true) {
            return;
        }
    }
}
