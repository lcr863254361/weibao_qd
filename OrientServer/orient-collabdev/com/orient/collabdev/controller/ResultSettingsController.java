package com.orient.collabdev.controller;

import com.orient.collabdev.business.designing.ResultSettingsBusiness;
import com.orient.collabdev.model.ResultBind;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseController;
import com.orient.web.base.CommonResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description 交付物绑定
 * @Author GNY
 * @Date 2018/8/9 9:48
 * @Version 1.0
 **/
@Controller
@RequestMapping("/resultSettings")
public class ResultSettingsController extends BaseController {

    @Autowired
    ResultSettingsBusiness resultSettingsBusiness;

    /**
     * 节点绑定研发数据类型
     *
     * @param nodeId
     * @param devType  DEV("1"), COMPONENT("2"), PVM("3")
     * @return
     */
    @RequestMapping("/nodeBind")
    @ResponseBody
    public CommonResponseData modifyNodeBind(String nodeId, String devType, boolean checked) {
        return resultSettingsBusiness.modifyNodeBind(nodeId, devType, checked);
    }

    /**
     * 获取节点下是否有研发数据，组件数据，离线数据
     *
     * @param nodeId
     * @return
     */
    @RequestMapping("/getTabs")
    @ResponseBody
    public AjaxResponseData<ResultBind> getTabs(String nodeId) {
        return resultSettingsBusiness.getTabs(nodeId);
    }

    /**
     * 通过组件id获取组件id
     *
     * @param nodeId
     * @return
     */
    @RequestMapping("/getCompomentId")
    @ResponseBody
    public AjaxResponseData getCompomentIdByNodeId(String nodeId) {
        return resultSettingsBusiness.getCompomentIdByNodeId(nodeId);
    }

    @RequestMapping("/reBindCompoment")
    @ResponseBody
    public CommonResponseData modifyNodeComponentBind(String nodeId, long componentId) {
        return resultSettingsBusiness.modifyNodeComponentBind(nodeId, componentId);
    }

}
