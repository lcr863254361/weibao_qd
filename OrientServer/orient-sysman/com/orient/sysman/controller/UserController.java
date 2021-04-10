package com.orient.sysman.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.orient.log.annotion.Action;
import com.orient.sysman.bean.UserBean;
import com.orient.sysman.bussiness.UserBusiness;
import com.orient.sysmodel.domain.user.LightweightUser;
import com.orient.sysmodel.domain.user.User;
import com.orient.sysmodel.service.user.UserService;
import com.orient.utils.CommonTools;
import com.orient.utils.ExcelUtil.reader.ExcelReader;
import com.orient.utils.ExcelUtil.reader.TableEntity;
import com.orient.utils.JsonUtil;
import com.orient.utils.PageUtil;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtComboboxResponseData;
import com.orient.web.base.ExtGridData;
import com.orient.web.springmvcsupport.DateEditor;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by Administrator on 2015/9/18 0018
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserBusiness userBusiness;

    @Resource(name = "UserService")
    UserService userService;

    /**
     * 展现数据表格
     *
     * @return
     */
    @Action(ownermodel = "系统管理-用户管理", detail = "查看系统用户信息")
    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<UserBean> list() {
        List<UserBean> users = userBusiness.findAllUser();
        ExtGridData<UserBean> retVal = new ExtGridData<>();
        retVal.setTotalProperty(users.size());
        retVal.setResults(users);
        return retVal;
    }

    /**
     * 新增数据
     *
     * @return
     */
    @Action(ownermodel = "系统管理-用户管理", detail = "创建系统用户信息")
    @RequestMapping("create")
    @ResponseBody
    public CommonResponseData create(User user, String department,String roleId) {
        if (department.equals("未分组")) {
            department = "";
        }
        userBusiness.create(user, department,"");

        CommonResponseData retVal = new CommonResponseData();
        retVal.setMsg("新增成功");
        return retVal;
    }

    /**
     * 更新表格
     *
     * @return
     */
    @Action(ownermodel = "系统管理-用户管理", detail = "更新系统用户信息")
    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(User fromUser, String department,String roleId) {
        userBusiness.update(fromUser, department,"");
        CommonResponseData retVal = new CommonResponseData();
        retVal.setMsg("更新成功");
        return retVal;
    }

    /**
     * 删除表格
     *
     * @param toDelIds
     * @return
     */
    @Action(ownermodel = "系统管理-用户管理", detail = "删除系统用户信息")
    @RequestMapping("delete")
    @ResponseBody
    public CommonResponseData delete(String toDelIds) {
        userBusiness.delete(toDelIds);
        CommonResponseData retVal = new CommonResponseData();
        retVal.setMsg("删除成功");
        return retVal;
    }

    /**
     * 查询
     *
     * @param queryUser
     * @return
     */
    @RequestMapping("search")
    @ResponseBody
    @Action(ownermodel = "系统管理-用户管理", detail = "过滤系统用户信息")
    public ExtGridData<UserBean> search(LightweightUser queryUser, String birthdayFrom, String birthdayTo, String department) {
        Map<String, String> betweens = new HashMap<>();
        betweens.put("birthday", birthdayFrom + "," + birthdayTo);
        List<UserBean> users = userBusiness.search(queryUser, betweens, department);
        ExtGridData<UserBean> retVal = new ExtGridData<>();
        retVal.setTotalProperty(users.size());
        retVal.setResults(users);
        return retVal;
    }

    @RequestMapping("importUsers")
    @ResponseBody
    @Action(ownermodel = "系统管理-用户管理", detail = "导入系统用户信息")
    public void importUsers(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String fileName = null;
        //解析器解析request的上下文
        CommonsMultipartResolver multipartResolver =
                new CommonsMultipartResolver(request.getSession().getServletContext());
        //先判断request中是否包涵multipart类型的数据，
        if (multipartResolver.isMultipart(request)) {
            //再将request中的数据转化成multipart类型的数据
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator iter = multiRequest.getFileNames();
            if (iter.hasNext()) {
                MultipartFile file = multiRequest.getFile((String) iter.next());
                fileName = file.getOriginalFilename();
                File dst = new File(fileName);
                try {
                    file.transferTo(dst);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        ExcelReader excelReader = new ExcelReader();
        File excelFile = new File(fileName);
        InputStream input = new FileInputStream(excelFile);
        boolean after2007 = fileName.substring(fileName.length() - 4).equals("xlsx");
        TableEntity excelEntity = excelReader.readFile(input, after2007);
        Map<String, Object> retVal = userBusiness.importUsers(excelEntity);
        try {
            response.setContentType("text/html");
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), retVal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        excelFile.delete();
    }

    @RequestMapping("exportUsers")
    @ResponseBody
    @Action(ownermodel = "系统管理-用户管理", detail = "导出系统用户信息")
    public void exportUsers(boolean exportAll, String toExportIds, HttpServletResponse response) {
        userBusiness.exportUsers(exportAll, toExportIds);
        try {
            response.setContentType("aplication/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("users.xls", "UTF-8"));
            BufferedInputStream in = new BufferedInputStream(new FileInputStream("users.xls"));
            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
            byte[] buffer = new byte[8192];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            in.close();
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("getUserColumCombobox")
    @ResponseBody
    public ExtComboboxResponseData<Map<String, String>> getUserColumCombobox() {
        ExtComboboxResponseData<Map<String, String>> retValue = userBusiness.getUserColumCombobox();
        return retValue;
    }

    /**
     * 从内存中获取用户，如果从数据库中找会很慢，并增加了分页（modify by gny 2018/4/17）
     *
     * @param extraFilter
     * @param userName
     * @param allName
     * @param grade
     * @param page
     * @param limit
     * @param filterName  这个参数只在用户选择界面传递，system的用户管理中不会传这个参数
     * @return
     */
    @Action(ownermodel = "系统管理-用户管理", detail = "查看系统用户信息")
    @RequestMapping("listByFilter")
    @ResponseBody
    public ExtGridData<UserBean> listByFilter(String extraFilter, String userName, String allName, String grade,String unit, Integer page, Integer limit, String filterName) {
        if (userName != null && userName.trim().length() == 0) {  //如果用户输入了空字符串，则转为null
            userName = null;
        }
        if (allName != null && allName.trim().length() == 0) {
            allName = null;
        }
        if (grade != null && grade.trim().length() == 0) {
            grade = null;
        }
        if (unit != null && unit.trim().length() == 0) {
            unit = null;
        }
        ExtGridData<UserBean> retVal = new ExtGridData<>();
        ExtGridData<UserBean> results = userBusiness.listByFilter(extraFilter);
        if (filterName != null) {
            if(filterName.equals("")){
                retVal.setResults(PageUtil.page(results.getResults(), page, limit));
                retVal.setTotalProperty(results.getResults().size());
                return retVal;
            }
            List<UserBean> result = Lists.newArrayList();
            for (UserBean userBean : results.getResults()) {
                boolean flag = true;
                if (!userBean.getUserName().contains(filterName) && !userBean.getAllName().contains(filterName)) {
                    flag = false;
                }
                if (flag) {
                    result.add(userBean);
                }
            }
            retVal.setResults(PageUtil.page(result, page, limit));
            retVal.setTotalProperty(result.size());
            return retVal;
        } else {
            if (userName != null || allName != null || grade != null||unit!=null) {  //查询面板中有过滤条件时
                List<UserBean> result = Lists.newArrayList();
                for (UserBean userBean : results.getResults()) {
                    boolean flag = true;
                    if (userName != null && !"".equals(userName) && !userBean.getUserName().contains(userName)) {
                        flag = false;
                    }
                    if (allName != null && !"".equals(allName) && !userBean.getAllName().contains(allName)) {
                        flag = false;
                    }
                    if (grade != null && !"".equals(grade) && !userBean.getGrade().contains(grade)) {
                        flag = false;
                    }
                    if (unit != null && !"".equals(unit) && !CommonTools.Obj2String(userBean.getUnit()).contains(unit)) {
                        flag = false;
                    }
                    if (flag) {
                        result.add(userBean);
                    }
                }
                retVal.setResults(PageUtil.page(result, page, limit));
                retVal.setTotalProperty(result.size());
                return retVal;
            }
        }
        //下面是查询面板没有过滤条件时
        retVal.setResults(PageUtil.page(results.getResults(), page, limit));
        retVal.setTotalProperty(results.getResults().size());
        return retVal;
    }

    @RequestMapping("getUserInfo")
    @ResponseBody
    public AjaxResponseData<UserBean> getUserInfo(String userId, String userName) {
        AjaxResponseData retVal = new AjaxResponseData();
        User user = null;
        if (userId != null && !"".equals(userId)) {
            user = userService.findById(userId);
        } else if (userName != null && !"".equals(userName)) {
            user = userService.findUserByUserName(userName);
        } else {
            retVal.setSuccess(false);
            retVal.setMsg("参数传递有误");
            return retVal;
        }

        if (user != null) {
            UserBean ub = new UserBean();
            try {
                PropertyUtils.copyProperties(ub, user);
                Map<String, String> deptMap = new HashMap<>();
                deptMap.put("id", user.getDept().getId());
                deptMap.put("text", user.getDept().getName());
                ub.setDepartment(JsonUtil.toJson(deptMap));
                ub.setDepartmentId(user.getDept().getId());
                retVal.setResults(ub);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return retVal;
    }

    /**
     * 时间格式处理
     *
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new DateEditor());
    }

}
