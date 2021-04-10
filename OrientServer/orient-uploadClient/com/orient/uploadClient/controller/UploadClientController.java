package com.orient.uploadClient.controller;

import com.orient.modeldata.business.ModelFileBusiness;
import com.orient.sysmodel.domain.user.User;
import com.orient.sysmodel.operationinterface.IRoleModel;
import com.orient.sysmodel.operationinterface.IUser;
import com.orient.sysmodel.roleengine.IRoleUtil;
import com.orient.sysmodel.service.user.UserService;
import com.orient.uploadClient.business.UploadClientBusiness;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseController;
import com.orient.web.base.CommonResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by qjs on 2017/2/15.
 * 调用客户端上传文件
 */
@Controller
@RequestMapping("/uploadClient")
public class UploadClientController extends BaseController {
    @Autowired
    UserService UserService;

    @Autowired
    UploadClientBusiness uploadClientBusiness;

    @Autowired
    protected IRoleUtil roleEngine;

    /**
     * 测试连接
     * @return
     */
    @RequestMapping("checkConnect")
    @ResponseBody
    public AjaxResponseData<Boolean> checkConnect() {
        boolean flag = true;
        AjaxResponseData retVal = new AjaxResponseData();
        retVal.setResults(flag);
        return retVal;
    }

    /**
     * 根据userId,返回userName
     * @param userId
     * @return allName真实姓名
     */
    @RequestMapping("getUserName")
    @ResponseBody
    public AjaxResponseData<String> getUserName(String userId) {
        AjaxResponseData retVal = new AjaxResponseData();
        //获取角色缓存信息
        IRoleModel roleModel = roleEngine.getRoleModel(false);
        //获取用户对象
        IUser user = roleModel.getUserById(userId);
        retVal.setResults(user.getAllName());
        return retVal;
    }

    /**
     * 保存上传信息
     * @param userId
     * @param xml 发送xml
     * @return
     */
    @RequestMapping("saveImportInfo")
    @ResponseBody
    public CommonResponseData saveImportInfo(String userId,@RequestBody String xml) {
        CommonResponseData retVal = new CommonResponseData();
        boolean flag = uploadClientBusiness.saveImportInfo(userId,xml);
        retVal.setMsg(flag ? "保存成功!" : "保存失败!");
        return retVal;
    }

}
