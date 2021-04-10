package com.orient.background.controller;

import com.orient.background.bean.PDBean;
import com.orient.background.business.PDMgrBusiness;
import com.orient.flow.util.FlowTypeHelper;
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
 * 流程定义管理
 */
@Controller
@RequestMapping("/PDMgr")
public class PDMgrController extends BaseController {

    @Autowired
    PDMgrBusiness pdMgrBusiness;

    /**
     * @param filter
     * @return 根据过滤条件展现流程定义信息
     */
    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<PDBean> list(PDBean filter) {
        List<String> auditFlowPdIds = FlowTypeHelper.getMainAuditFlowPdIds();
        List<String> collabFlowPdIds = FlowTypeHelper.getCollabFlowPdIds();
        List<PDBean> pdBeans = pdMgrBusiness.list(auditFlowPdIds, collabFlowPdIds, filter);
        ExtGridData<PDBean> resp = new ExtGridData<>();
        resp.setResults(pdBeans);
        resp.setTotalProperty(pdBeans.size());
        return resp;
    }

    @RequestMapping("delete")
    @ResponseBody
    public CommonResponseData delete(PDBean pdBean) {
        Boolean deleteSuccess = pdMgrBusiness.delete(pdBean);
        CommonResponseData responseData = new CommonResponseData();
        //刷新缓存
        FlowTypeHelper.refresh();
        responseData.setSuccess(deleteSuccess);
        responseData.setMsg(deleteSuccess ? "删除成功!" : "删除失败!");
        return responseData;
    }

}
