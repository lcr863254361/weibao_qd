package com.orient.collab.controller;

import com.orient.collab.business.CollabFlowInfoBusiness;
import com.orient.collab.model.CollabFlowInfo;
import com.orient.collab.model.CollabFlowJpdlInfo;
import com.orient.flow.model.FlowTaskNodeModel;
import com.orient.utils.CommonTools;
import com.orient.web.base.BaseController;
import com.orient.web.base.CommonResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * the collab flow information controller
 *
 * @author Seraph
 *         2016-08-09 上午10:41
 */
@Controller
@RequestMapping("/collabFlow")
public class CollabFlowInfoController extends BaseController{

    @RequestMapping("/flowInfo")
    @ResponseBody
    public CollabFlowInfo getCurrentFlowInfo(String modelName, String dataId){
        return this.collabFlowInfoBusiness.getCurrentFlowInfoByModelNameAndDataId(modelName, dataId);
    }

    @RequestMapping("/jpdl/generateOrUpdate")
    @ResponseBody
    public CollabFlowJpdlInfo generateOrUpdateFlowJpdl(String modelName, String dataId){
        return this.collabFlowInfoBusiness.generateOrUpdateFlowJpdl(modelName, dataId);
    }

    /**
     * get the latest flow diagram node's info
     * @param request
     * @param response
     */
    @RequestMapping("/monitorInfo")
    @ResponseBody
    public List<FlowTaskNodeModel> getLatestFlowDiagMonitorInfo(HttpServletRequest request,
                                                                HttpServletResponse response) {

        List<FlowTaskNodeModel> retVal = null;
        String piId = CommonTools.null2String(request.getParameter("piId"));
        if(piId.equals("")){
            String flowTaskId = CommonTools.null2String(request.getParameter("flowTaskId"));
            retVal = collabFlowInfoBusiness.getLatestFlowDiagMonitorModelByFlowTaskId(flowTaskId);
        }else{
            retVal = collabFlowInfoBusiness.getLatestFlowDiagMonitorModelByPiId(piId);
        }

        return retVal;
    }

    @Autowired
    private CollabFlowInfoBusiness collabFlowInfoBusiness;
}
