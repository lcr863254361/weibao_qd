package com.orient.weibao.controller;

import com.itextpdf.text.DocumentException;
import com.orient.utils.StringUtil;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseController;
import com.orient.web.base.ExtGridData;
import com.orient.weibao.bean.DivingTaskTreeNode;
import com.orient.weibao.business.CurrentTaskMgrBusiness;
import com.orient.weibao.business.GenerateHtmlBusiness;
import com.orient.weibao.mbg.mapper.CheckTempInstMapper;
import com.orient.weibao.mbg.model.CheckTempInst;
import com.orient.weibao.mbg.model.CheckTempInstExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2019-02-13 10:12
 */
@Controller
@RequestMapping("/CurrentTaskMgr")
public class CurrentTaskMgrController extends BaseController{

    @Autowired
    CurrentTaskMgrBusiness currentTaskMgrBusiness;

    @Autowired
    private GenerateHtmlBusiness generateHtmlBusiness;

    /**
     *当前任务中获取下潜任务树节点
     *
     * @param: id
     * @param: type
     * @return:
     */
    @RequestMapping("getDivingTaskTreeNodeId")
    @ResponseBody
    public AjaxResponseData<DivingTaskTreeNode> getDivingTaskTreeNodeId(){
        return currentTaskMgrBusiness.getDivingTaskTreeNodeId();
    }

    /**
     * 当前任务中节点状态
     * @param taskId
     * @param cellNodeId
     * @return
     */
    @RequestMapping("checkStageNodeStatus")
    @ResponseBody
    public AjaxResponseData checkStageNodeStatus(String taskId,String cellNodeId){
        AjaxResponseData retVal=new AjaxResponseData();
        String[] cellNodeIdList = {};
        if (StringUtil.isNotEmpty(cellNodeId)) {
            cellNodeIdList = cellNodeId.split(",");
            List<String> status = currentTaskMgrBusiness.checkStageNodeStatus(taskId, cellNodeIdList);
            retVal.setResults(status);
        }
        return retVal;
    }

    @RequestMapping("getHangciHangduanData")
    @ResponseBody
    public AjaxResponseData getHangciHangduanData(String taskId){
        AjaxResponseData retVal=new AjaxResponseData();
        String hangciName= currentTaskMgrBusiness.getHangciHangduanData(taskId);
        retVal.setSuccess(true);
        retVal.setResults(hangciName);
        return retVal;
    }

    /**
     * 产品结构中更换设备中获取设备实例数据，及根据条件分页查询数据
     * @param start
     * @param limit
     * @param nodeContent  产品结构树的节点Id
     * @param queryName  输入的设备名称
     * @return
     */
    @RequestMapping("getDeviceInstData")
    @ResponseBody
    public ExtGridData getDeviceInstData(String start,String limit, String productId,String nodeContent,String queryName) throws Exception{
        ExtGridData str=currentTaskMgrBusiness.getDeviceInstData(start, limit, productId,nodeContent,queryName);
        return str;
    }

    /**
     * 获取检查表实例数据
     * @param orientModelId
     * @param isView
     * @param page
     * @param limit
     * @param customerFilter
     * @param sort
     * @return
     */
    @RequestMapping("getCheckInstData")
    @ResponseBody
    public ExtGridData<Map> getCheckInstData(String orientModelId, String isView, Integer page, Integer limit, String customerFilter, String sort){

        ExtGridData<Map> retVal=currentTaskMgrBusiness.getCheckInstData(orientModelId, isView, page, limit, customerFilter, true, sort);
        return  retVal;
    }

    /**
     * 当前任务中的下潜作业流程中获取水下记录单数据
     * @param orientModelId
     * @param isView
     * @param page
     * @param limit
     * @param customerFilter
     * @param sort
     * @return
     */
    @RequestMapping("getWaterDownRecordData")
    @ResponseBody
    public ExtGridData<Map> getWaterDownRecordData(String orientModelId, String isView, Integer page, Integer limit, String customerFilter, String sort){

        ExtGridData<Map> retVal=currentTaskMgrBusiness.getWaterDownRecordData(orientModelId, isView, page, limit, customerFilter, true, sort);
        return  retVal;
    }

    /**
     * 获取水下记录单详细内容
     * @param waterDownId
     * @return
     */
    @RequestMapping("getWaterDownDetailContent")
    @ResponseBody
    public AjaxResponseData<Map> queryDetailContent(String waterDownId){
        AjaxResponseData retVal=new AjaxResponseData();
       Map map=currentTaskMgrBusiness.queryDetailContent(waterDownId);
        retVal.setResults(map);
        return retVal;
    }

    /**
     * 获取检查单详情以html格式输出
     * @param instanceId
     * @return
     */
    @RequestMapping("getCheckTableCaseHtml")
    @ResponseBody
    public void getCheckTableCaseHtml(String instanceId, boolean isShowByInform,HttpServletRequest request,HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter printWriter = null;
        try {
            printWriter = response.getWriter();
            printWriter.println(generateHtmlBusiness.generateHtml(instanceId,isShowByInform,request,true).toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        //printWriter.println(currentTaskMgrBusiness.getCheckTableCaseHtml(instanceId));
        //return currentTaskMgrBusiness.getCheckTableCaseHtml(instanceId);
    }

    @Autowired
    CheckTempInstMapper checkTempInstMapper;

    @RequestMapping("getCheckTableCaseHtmlList")
    @ResponseBody
    public void getCheckTableCaseHtmlByTaskId(String taskId, boolean isShowByInform,HttpServletRequest request,HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter printWriter = null;
        CheckTempInstExample checkTempInstExample = new CheckTempInstExample();
        checkTempInstExample.createCriteria().andTDivingTask480IdEqualTo(taskId);
        List<CheckTempInst> checkTempInsts = checkTempInstMapper.selectByExample(checkTempInstExample);
        checkTempInsts=checkTempInsts.stream().sorted((item1,item2)->{
            String[] prefix1=item1.getcName3222().substring(0,5).split("-");
            String[] prefix2=item2.getcName3222().substring(0,5).split("-");
            try {
                if(Integer.parseInt(prefix1[0]+prefix1[1]+prefix1[2])>Integer.parseInt(prefix2[0]+prefix2[1]+prefix2[2])){
                    return 1;
                }else {
                    return -1;
                }
            }catch (Exception e){
                return 1;
            }

        }).collect(Collectors.toList());
        try {
            printWriter = response.getWriter();
            StringBuffer stringBuffer=new StringBuffer();
            for(CheckTempInst item :checkTempInsts){
                stringBuffer.append( generateHtmlBusiness.generateHtml(item.getId(),isShowByInform,request,false).toString());
            }
            printWriter.println(stringBuffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }finally {
            printWriter.close();
        }

        //printWriter.println(currentTaskMgrBusiness.getCheckTableCaseHtml(instanceId));
        //return currentTaskMgrBusiness.getCheckTableCaseHtml(instanceId);
    }

    @RequestMapping("exportPdf")
    public ResponseEntity downLoadPdf(String instanceId, HttpServletRequest request,HttpServletResponse response){
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();
        FileInputStream fileInputStream = null;
        ResponseEntity responseEntity=null;
        PrintWriter writer = null;
        try {
            File file = generateHtmlBusiness.generateToPDf(instanceId,serverPort,contextPath,request);
            int length = (int) file.length();
            fileInputStream = new FileInputStream(file);
            byte[] bytes = new byte[length];
            fileInputStream.read(bytes);
            HttpHeaders httpHeaders = new HttpHeaders();
            String filename = file.getName();
            filename  = URLEncoder.encode(filename,"UTF-8");
            httpHeaders.add("Content-Disposition","attchement;filename="+filename);
            HttpStatus httpStatus = HttpStatus.OK;
            responseEntity = new ResponseEntity(bytes,httpHeaders,httpStatus);
        } catch (IOException e) {
            e.printStackTrace();
            try {
                writer = response.getWriter();
                response.setContentType("text/html;charset=utf-8");
                writer.println(e.toString()+" 系统内部错误，导出pdf失败");
            } catch (IOException e1) {
                e1.printStackTrace();
            }finally {
                if(writer!=null){
                    writer.close();
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return responseEntity;
    }

    /**
     * 根据单元格实例Id获取troubleId
     * @param cellInstId
     * @return
     */
    @RequestMapping("getTroubleIdByCellId")
    @ResponseBody
    public AjaxResponseData getTroubleIdByCellId(String cellInstId) {
        String troubleId=currentTaskMgrBusiness.getTroubleIdByCellId(cellInstId);
        return new AjaxResponseData(troubleId);
    }
}
