package com.orient.background.controller;

import com.orient.background.bean.PIBean;
import com.orient.background.business.PIMgrBusiness;
import com.orient.web.base.BaseController;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2016/9/2 0002.
 * 流程实例管理
 */
@Controller
@RequestMapping("/PIMgr")
public class PIMgrController extends BaseController {

    @Autowired
    PIMgrBusiness piMgrBusiness;

    /**
     * @param pdId
     * @retu 根据过滤条件展现流程定义信息
     */
    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<PIBean> list(String pdId) {
        List<PIBean> piBeans = piMgrBusiness.list(pdId);
        ExtGridData<PIBean> resp = new ExtGridData<>();
        resp.setResults(piBeans);
        resp.setTotalProperty(piBeans.size());
        return resp;
    }

    @RequestMapping("delete")
    @ResponseBody
    public CommonResponseData delete(String piId) {
        String[] toDelPiIds = piId.split(",");
        for (String toDelPiId : toDelPiIds) {
            Boolean deleteSuccess = piMgrBusiness.delete(toDelPiId);
        }
        CommonResponseData responseData = new CommonResponseData();
        responseData.setSuccess(true);
        responseData.setMsg("删除成功!");
        return responseData;
    }

}
