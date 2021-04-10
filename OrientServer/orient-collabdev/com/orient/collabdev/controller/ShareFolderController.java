package com.orient.collabdev.controller;

import com.orient.collabdev.business.processing.ShareFolderBusiness;
import com.orient.collabdev.model.ShareFolderWrapper;
import com.orient.log.annotion.Action;
import com.orient.utils.JsonUtil;
import com.orient.web.base.BaseController;
import com.orient.web.base.CommonResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Description 数据内容相关
 * @Author ZhangSheng
 * @Date 2018/8/21 09:39
 * @Version 1.0
 **/
@Controller
@RequestMapping("/shareFolder")
public class ShareFolderController extends BaseController {

//    @Autowired
//    ModelDataBusiness modelDataBusiness;

    @Autowired
    ShareFolderBusiness shareFolderBusiness;


    @Action(ownermodel = "协同设计-共享文件夹管理", detail = "查看共享文件夹项信息")
    @RequestMapping("list")
    @ResponseBody
    public List<ShareFolderWrapper> list(String node, String collabNodeId) {
        return shareFolderBusiness.list(node, collabNodeId);
    }

    @Action(ownermodel = "协同设计-共享文件夹管理", detail = "新增共享文件夹")
    @RequestMapping("create")
    @ResponseBody
    public CommonResponseData create(String formData, String collabNodeId) {
        List<ShareFolderWrapper> toSaveData = JsonUtil.getJavaCollection(new ShareFolderWrapper(), formData);
        CommonResponseData retVal = shareFolderBusiness.create(toSaveData, collabNodeId);
        return retVal;
    }

    @Action(ownermodel = "协同设计-共享文件夹管理", detail = "更新共享文件夹")
    @RequestMapping("delete")
    @ResponseBody
    public CommonResponseData delete(String formData) {
        List<ShareFolderWrapper> toDeleteData = JsonUtil.getJavaCollection(new ShareFolderWrapper(), formData);
        CommonResponseData retVal = shareFolderBusiness.delete(toDeleteData);
        return retVal;
    }

    @Action(ownermodel = "协同设计-共享文件夹管理", detail = "删除共享文件夹")
    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(String formData) {
        List<ShareFolderWrapper> toUpdateData = JsonUtil.getJavaCollection(new ShareFolderWrapper(), formData);
        CommonResponseData retVal = shareFolderBusiness.update(toUpdateData);
        return retVal;
    }

}
