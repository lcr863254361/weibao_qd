package com.orient.sysman.controller;

import com.orient.log.annotion.Action;
import com.orient.utils.Log.LogThreadLocalHolder;
import com.orient.sysman.bean.DeptBean;
import com.orient.sysman.bean.UserBean;
import com.orient.sysman.bussiness.DeptBussiness;
import com.orient.sysmodel.domain.user.Department;
import com.orient.sysmodel.domain.user.User;
import com.orient.sysmodel.service.user.DepartmentService;
import com.orient.utils.StringUtil;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import com.orient.web.springmvcsupport.DateEditor;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Administrator on 2015/9/18 0018.
 */
@Controller
@RequestMapping("/dept")
public class DeptController {
    @Autowired
    DeptBussiness deptBussiness;

    @Resource(name = "DepartmentService")
    DepartmentService deptService;

    /**
     * 展现数据树
     *
     * @return
     */
    @Action(ownermodel = "系统管理-部门管理", detail = "查看部门列表")
    @RequestMapping("getByPid")
    @ResponseBody
    public AjaxResponseData<List<DeptBean>> getByPid(String node) {
        return new AjaxResponseData<>(deptBussiness.findByPid(node));
    }

    /**
     * 新增数据
     *
     * @return
     */
    @RequestMapping("create")
    @ResponseBody
    @Action(ownermodel = "系统管理-部门管理", detail = "创建部门-${name}")
    public CommonResponseData create(Department dept) {
        deptService.createDept(dept);
        CommonResponseData retVal = new CommonResponseData();
        LogThreadLocalHolder.putParamerter("name", dept.getName());
        retVal.setMsg("新增成功");
        return retVal;
    }

    /**
     * 更新表格
     *
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    @Action(ownermodel = "系统管理-部门管理", detail = "更新部门-${name}")
    public CommonResponseData update(Department dept) {
        Department tempDept = deptService.findById(dept.getId());
        tempDept.setPid(dept.getPid());
        tempDept.setName(dept.getName());
        tempDept.setFunction(dept.getFunction());
        tempDept.setNotes(dept.getNotes());
        tempDept.setOrder(dept.getOrder());
        CommonResponseData retVal = new CommonResponseData();
        deptService.updateDept(tempDept);;
        LogThreadLocalHolder.putParamerter("name", dept.getName());
        retVal.setMsg("更新成功");
        return retVal;
    }

    /**
     * 删除表格
     *
     * @param toDelIds
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    @Action(ownermodel = "系统管理-部门管理", detail = "删除部门")
    public CommonResponseData delete(String toDelIds) {
        CommonResponseData retVal = new CommonResponseData();
        deptService.deleteDept(toDelIds);
        retVal.setMsg("删除成功");
        return retVal;
    }

    /**
     * 查询
     *
     * @return
     */
    @RequestMapping("getDeptUsers")
    @ResponseBody
    @Action(ownermodel = "系统管理-部门管理", detail = "获取部门用户信息")
    public ExtGridData<UserBean> getDeptUsers(String deptId) {
        Department dept = deptService.findById(deptId);
        List<UserBean> retList = new ArrayList<>();

        Set<User> users = dept.getUsers();
        if (users == null) {
            users = new HashSet<>();
        }
        for (User user : users) {
            UserBean ub = new UserBean();
            try {
                PropertyUtils.copyProperties(ub, user);
            } catch (Exception e) {
                e.printStackTrace();
            }
            retList.add(ub);
        }

        ExtGridData<UserBean> retVal = new ExtGridData<>();
        retVal.setTotalProperty(retList.size());
        retVal.setResults(retList);
        return retVal;
    }

    /**
     * 展现数据树
     *
     * @return
     */
    @Action(ownermodel = "系统管理-部门管理", detail = "查看部门列表")
    @RequestMapping("getDepByFilter")
    @ResponseBody
    public List<DeptBean> getDepByFilter(String node, String ids) {
        return !"-1".equals(node) ? new ArrayList<>() : deptBussiness.listByIdFilter(ids);
    }

    /**
     * 展现数据树
     *
     * @return
     */
    @Action(ownermodel = "系统管理-部门管理", detail = "按层级展现部门列表")//归属模块?
    @RequestMapping("list")
    @ResponseBody
    public AjaxResponseData<List<DeptBean>> getListTree(String node) {
        List<DeptBean> result = deptBussiness.treeFindByPid(node);
        return new AjaxResponseData<>(result);
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
