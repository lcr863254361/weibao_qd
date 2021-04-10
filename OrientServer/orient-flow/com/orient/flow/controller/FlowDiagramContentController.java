package com.orient.flow.controller;

import com.orient.flow.business.FlowDiagramContentBusiness;
import com.orient.flow.business.ProcessDefinitionBusiness;
import com.orient.workflow.bean.FlowInfo;
import com.orient.flow.model.FlowTaskNodeModel;
import com.orient.flow.model.FlowTaskTrackInfo;
import com.orient.flow.model.FlowTaskWithAssigner;
import com.orient.utils.CommonTools;
import com.orient.web.base.AjaxResponseData;
import org.jbpm.api.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * the flow diagram content controller
 *
 * @author Seraph
 *         2016-07-28 下午4:48
 */
@Controller
@RequestMapping("/flowDiagramContent")
public class FlowDiagramContentController {

    @RequestMapping("/trackInfos")
    @ResponseBody
    public List<FlowTaskTrackInfo> getFlowTrackInfos(HttpServletRequest request,
                                                     HttpServletResponse response) {
        List<FlowTaskTrackInfo> retVal = null;
//        String piId = CommonTools.null2String(request.getParameter("piId"));
//        if(piId.equals("")){
//            String  flowTaskId = CommonTools.null2String(request.getParameter("flowTaskId"));
//            retVal = flowDiagramContentBusiness.getFlowTrackInfosByActivityFlowTaskId(flowTaskId);
//        }else{
//            retVal = flowDiagramContentBusiness.getFlowTrackInfos(piId);
//        }

        return retVal;
    }

    /**
     * get the latest flow diagram node's info
     *
     * @param request
     * @param response
     */
    @RequestMapping("/monitorInfo")
    @ResponseBody
    public List<FlowTaskNodeModel> getLatestFlowDiagMonitorInfo(HttpServletRequest request,
                                                                HttpServletResponse response) {

        List<FlowTaskNodeModel> retVal = null;
        String piId = CommonTools.null2String(request.getParameter("piId"));
        if (piId.equals("")) {
            String flowTaskId = CommonTools.null2String(request.getParameter("flowTaskId"));
            retVal = flowDiagramContentBusiness.getLatestFlowDiagMonitorModelByFlowTaskId(flowTaskId);
        } else {
            retVal = flowDiagramContentBusiness.getLatestFlowDiagMonitorModelByPiId(piId);
        }

        return retVal;
    }


    /**
     * get the flow's JPDL file's content
     *
     * @param request
     * @param response
     */
    @RequestMapping("/JPDLContent")
    public void getFlowJPDLContent(HttpServletRequest request, HttpServletResponse response) {
        String pdId = CommonTools.null2String(request.getParameter("pdId"));
        InputStream is = null;
        if (pdId.equals("")) {
            String piId = CommonTools.null2String(request.getParameter("piId"));
            if (piId.equals("")) {
                String flowTaskId = CommonTools.null2String(request.getParameter("flowTaskId"));
                is = flowDiagramContentBusiness.getFlowJPDLContentAsStreamByFlowTaskId(flowTaskId);
            } else {
                is = flowDiagramContentBusiness.getFlowJPDLContentAsStreamByPiId(piId);
            }
        } else {
            is = flowDiagramContentBusiness.getFlowJPDLContentAsStream(pdId);
        }

        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        try {
            bos = new BufferedOutputStream(response.getOutputStream());
            bis = new BufferedInputStream(is);
            byte[] b = new byte[2048];
            int len = -1;
            while ((len = bis.read(b)) != -1) {
                bos.write(b, 0, len);
            }
            bos.flush();
            bos.close();
            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param pdId
     * @return 获取流程图中真实的用户信息
     */
    @RequestMapping("/taskAssign")
    @ResponseBody
    public AjaxResponseData<List<FlowTaskWithAssigner>> taskAssign(String piId, String pdId) {
        if(piId!=null && !"".equals(piId)) {
            ProcessDefinition pd =  processDefinitionBusiness.getPrcDefByPrcInstId(piId);
            pdId = pd.getId();
        }
        List<FlowTaskWithAssigner> flowTaskWithAssigners = processDefinitionBusiness.getTasksAssignByPdId(pdId);
        return new AjaxResponseData(flowTaskWithAssigners);
    }

    @RequestMapping("/getMainAndSubPIs")
    @ResponseBody
    public AjaxResponseData<List<FlowInfo>> getMainAndSubPIs(String piId) {
        List<FlowInfo> flowTaskWithAssigners = processDefinitionBusiness.getMainAndSubPIs(piId);
        return new AjaxResponseData(flowTaskWithAssigners);
    }




    @Autowired
    private ProcessDefinitionBusiness processDefinitionBusiness;

    @Autowired
    private FlowDiagramContentBusiness flowDiagramContentBusiness;
}
