package com.orient.sysman.controller;

import com.orient.log.annotion.Action;
import com.orient.sysman.bean.FuncBean;
import com.orient.sysman.bean.LuceneSearchResult;
import com.orient.sysman.bussiness.HomePageBusiness;
import com.orient.sysmodel.domain.sys.CwmPortalEntity;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.ExtGridData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 首页控制层
 *
 * @author enjoy
 * @createTime 2016-06-01 16:47
 */
@Controller
@RequestMapping("/home")
public class HomePageController {

    @Autowired
    HomePageBusiness homePageBusiness;


    /**
     * @return 获取当前登录用户的未设置在首页中的功能
     */
    @Action(ownermodel = "系统管理-首页管理", detail = "查看功能点信息")
    @RequestMapping("listUnSelectedFunction")
    @ResponseBody
    public AjaxResponseData<List<FuncBean>> getUnSelectedFunction() {
        return new AjaxResponseData(homePageBusiness.getUnSelectedFunction(false));
    }

    /**
     * @return 获取所有可设置在首页中的功能
     */
    @Action(ownermodel = "系统管理-首页管理", detail = "查看功能点信息")
    @RequestMapping("listAllFunction")
    @ResponseBody
    public AjaxResponseData<List<FuncBean>> getAllFunction() {
        return new AjaxResponseData(homePageBusiness.getAllFunction());
    }

    @Action(ownermodel = "系统管理-首页管理", detail = "保存快捷功能点信息")
    @RequestMapping("saveUserLink")
    @ResponseBody
    public AjaxResponseData<String> saveUserLink(Long functionId) {
        AjaxResponseData<String> retVal = new AjaxResponseData<>();
        homePageBusiness.saveUserLink(functionId);
        return retVal;
    }

    @Action(ownermodel = "系统管理-首页管理", detail = "获取快捷功能点信息")
    @RequestMapping("listUserQuickLink")
    @ResponseBody
    public AjaxResponseData<List<FuncBean>> getUserQuickLink() {
        return new AjaxResponseData(homePageBusiness.getUnSelectedFunction(true));
    }


    @Action(ownermodel = "系统管理-首页管理", detail = "移除快捷功能点信息")
    @RequestMapping("removeUserQuickLink")
    @ResponseBody
    public AjaxResponseData<String> removeUserQuickLink(Long functionId) {
        AjaxResponseData<String> retVal = new AjaxResponseData<>();
        homePageBusiness.removeUserQuickLink(functionId);
        return retVal;
    }

    @Action(ownermodel = "系统管理-首页管理", detail = "修改快捷功能点顺序")
    @RequestMapping("saveUserQuickLinkOrder")
    @ResponseBody
    public AjaxResponseData<String> saveUserQuickLinkOrder(Long functionId, String direction) {
        AjaxResponseData<String> retVal = new AjaxResponseData<>();
        homePageBusiness.saveUserQuickLinkOrder(functionId, direction);
        return retVal;
    }

    @Action(ownermodel = "系统管理-首页管理", detail = "查看磁贴信息")
    @RequestMapping("listUserPortal")
    @ResponseBody
    public AjaxResponseData<List<CwmPortalEntity>> listUserPortal() {
        return new AjaxResponseData(homePageBusiness.listUserSelectPortals());
    }

    @Action(ownermodel = "系统管理-首页管理", detail = "查看可选磁贴信息")
    @RequestMapping("listUnSelectedUserPortal")
    @ResponseBody
    public AjaxResponseData<List<CwmPortalEntity>> listUnSelectedUserPortal() {
        return new AjaxResponseData(homePageBusiness.listUserPortal(false));
    }

    @Action(ownermodel = "系统管理-首页管理", detail = "新增首页磁贴设置")
    @RequestMapping("saveUserPortal")
    @ResponseBody
    public AjaxResponseData<String> saveUserPortal(Long[] portalIds) {
        AjaxResponseData<String> retVal = new AjaxResponseData<>();
        homePageBusiness.saveUserPortal(portalIds);
        return retVal;
    }

    @Action(ownermodel = "系统管理-首页管理", detail = "移除首页磁贴设置")
    @RequestMapping("removeUserPortal")
    @ResponseBody
    public AjaxResponseData<String> removeUserPortal(Long portalId) {
        AjaxResponseData<String> retVal = new AjaxResponseData<>();
        homePageBusiness.removeUserPortal(portalId);
        return retVal;
    }

    @Action(ownermodel = "系统管理-首页管理", detail = "使用全文检索功能,搜索关键词为")
    @RequestMapping("luceneSearch")
    @ResponseBody
    public ExtGridData<LuceneSearchResult> doluceneSearch(Integer page, Integer limit, String keyWord) {
        ExtGridData<LuceneSearchResult> retVal = homePageBusiness.doLuceneSearch(keyWord, page, limit);
        return retVal;
    }
}
