/*
 * Copyright (c) 2016. Orient Company
 *
 */

package com.orient.ods.controller;

import com.orient.ods.FileMangr.ODSFileMangrBusiness;
import com.orient.ods.atfx.business.AtfxFileMangrBusiness;
import com.orient.ods.atfx.business.AtfxFileTreeBusiness;
import com.orient.ods.atfx.business.AtfxMatrixBusiness;
import com.orient.ods.atfx.business.AtfxMeasurmentBusiness;
import com.orient.ods.atfx.model.*;
import com.orient.ods.model.ODSTreeItemVO;
import com.orient.utils.StringUtil;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseController;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mengbin on 16/3/18.
 * Purpose:
 * Detail:
 */
@Controller
@RequestMapping("/afxload")
public class AtfxLoadController extends BaseController {

    @Autowired
    private AtfxFileMangrBusiness atfxFileMangrBusiness;
    @Autowired
    private AtfxFileTreeBusiness atfxFileTreeBusiness;
    @Autowired
    private AtfxMeasurmentBusiness atfxMeasurmentBusiness;
    @Autowired
    private AtfxMatrixBusiness atfxMatrixBusiness;
    @Autowired
    private ODSFileMangrBusiness odsFileBusiness;


    /**
     * 根据文件获取所有的测试
     *
     * @param atfxFileId
     * @return
     */
    @RequestMapping("/initATFXFileSummery")
    @ResponseBody
    public AjaxResponseData<List<ODSTreeItemVO>> initATFXFileSummury(@RequestParam("atfxFileId") String atfxFileId) {

        StringBuffer errorInfo = new StringBuffer();
        String atfxFilePath = odsFileBusiness.getAtfxFilePath(atfxFileId);
        if (StringUtil.isEmpty(atfxFilePath)) {
            CommonResponseData ret = new CommonResponseData();
            ret.setSuccess(false);
            ret.setMsg("ODS 文件不存在");
            return null;
        }
        AtfxSession session = AtfxFileMangrBusiness.openFile(atfxFilePath, errorInfo);
        if (session == null) {
            CommonResponseData ret = new CommonResponseData();
            ret.setSuccess(false);
            ret.setMsg(errorInfo.toString());
        }
        try {
            ODSNode rootNode = atfxFileTreeBusiness.getRootNode(session);
            List<AoMeasurementNode> measureNodes = atfxMeasurmentBusiness.getMesurenmentNodes(rootNode);
            AjaxResponseData<List<ODSTreeItemVO>> retObj = new AjaxResponseData<>();

            List<ODSTreeItemVO> itemVOs = new ArrayList<>();
            for (AoMeasurementNode node : measureNodes) {
                ODSTreeItemVO childNode = new ODSTreeItemVO(node.getStringId(), node.getNodeName(), "Freemarker模板管理.png", true);
                itemVOs.add(childNode);
            }
            retObj.setResults(itemVOs);
            return retObj;
        } catch (Exception e) {
            CommonResponseData ret = new CommonResponseData();
            ret.setSuccess(false);
            ret.setMsg(e.toString());
        }

        return null;

    }


    /**
     * 获取当前测试的信息
     *
     * @param atfxFileId
     * @param measurementName
     * @param id
     * @return
     */
    @RequestMapping("/getMeasurementInfo")
    @ResponseBody

    public CommonResponseData getMeasurementInfo(@RequestParam("atfxFileId") String atfxFileId, @RequestParam("measurementName") String measurementName
            , @RequestParam("id") String id) {
        String atfxFilePath = odsFileBusiness.getAtfxFilePath(atfxFileId);
        if (StringUtil.isEmpty(atfxFilePath)) {
            CommonResponseData ret = new CommonResponseData();
            ret.setSuccess(false);
            ret.setMsg("ODS 文件不存在");
            return ret;
        }
        AjaxResponseData ret = new AjaxResponseData<>();
        StringBuffer errorInfo = new StringBuffer();
        AtfxSession session = AtfxFileMangrBusiness.openFile(atfxFilePath, errorInfo);
        if (session == null) {

            ret.setSuccess(false);
            ret.setMsg(errorInfo.toString());
        }
        try {
            ODSNode rootNode = atfxFileTreeBusiness.getRootNode(session);
            AoMeasurementNode node = atfxMeasurmentBusiness.getAoMeasureMentNodeById(rootNode, id);

            ret.setSuccess(true);
            ret.setResults(atfxMeasurmentBusiness.getMeasurementInfo(node));

        } catch (Exception e) {

            ret.setSuccess(false);
            ret.setMsg(e.toString());
        }

        return ret;
    }


    @RequestMapping("/getMeasurementQuantityList")
    @ResponseBody
    public CommonResponseData getMeasurementQuantityList(@RequestParam("atfxFileId") String atfxFileId
            , @RequestParam("measurementId") String measurementId) {


        String atfxFilePath = odsFileBusiness.getAtfxFilePath(atfxFileId);
        if (StringUtil.isEmpty(atfxFilePath)) {
            CommonResponseData ret = new CommonResponseData();
            ret.setSuccess(false);
            ret.setMsg("ODS 文件不存在");
            return ret;
        }
        AjaxResponseData ret = new AjaxResponseData<>();
        StringBuffer errorInfo = new StringBuffer();
        AtfxSession session = AtfxFileMangrBusiness.openFile(atfxFilePath, errorInfo);
        if (session == null) {

            ret.setSuccess(false);
            ret.setMsg(errorInfo.toString());
        }
        try {
            ODSNode rootNode = atfxFileTreeBusiness.getRootNode(session);
            AoMeasurementNode node = atfxMeasurmentBusiness.getAoMeasureMentNodeById(rootNode, measurementId);
            List<AoMeasurementQuantityNode> quantities = node.getMeasureQuantities();
            List<Map<String, String>> results = new ArrayList<>();
            for (AoMeasurementQuantityNode quaintityNode : quantities
                    ) {
                Map<String, String> nodeMap = new HashMap<>();
                nodeMap.put("id", quaintityNode.getStringId());
                nodeMap.put("name", quaintityNode.getNodeName());
                nodeMap.put("datatype", quaintityNode.getProperties().get("datatype").value);
                results.add(nodeMap);
            }

            ret.setSuccess(true);
            ret.setResults(results);

        } catch (Exception e) {

            ret.setSuccess(false);
            ret.setMsg(e.toString());
        }
        return ret;
    }

    @RequestMapping("/getSubMatrixList")
    @ResponseBody
    public CommonResponseData getSubMatrixList(@RequestParam("atfxFileId") String atfxFileId
            , @RequestParam("measurementId") String measurementId) {

        String atfxFilePath = odsFileBusiness.getAtfxFilePath(atfxFileId);
        if (StringUtil.isEmpty(atfxFilePath)) {
            CommonResponseData ret = new CommonResponseData();
            ret.setSuccess(false);
            ret.setMsg("ODS 文件不存在");
            return ret;
        }
        AjaxResponseData ret = new AjaxResponseData();
        StringBuffer errorInfo = new StringBuffer();
        AtfxSession session = AtfxFileMangrBusiness.openFile(atfxFilePath, errorInfo);
        if (session == null) {

            ret.setSuccess(false);
            ret.setMsg(errorInfo.toString());
        }
        try {
            ODSNode rootNode = atfxFileTreeBusiness.getRootNode(session);
            AoMeasurementNode node = atfxMeasurmentBusiness.getAoMeasureMentNodeById(rootNode, measurementId);
            List<AoSubMatrixNode> matrices = node.getSubMatrices();
            List<Map<String, String>> results = new ArrayList<>();
            for (AoSubMatrixNode matrix : matrices
                    ) {
                Map<String, String> nodeMap = new HashMap<>();
                nodeMap.put("id", matrix.getStringId());
                nodeMap.put("name", matrix.getNodeName());
                nodeMap.put("rowcount", String.valueOf(matrix.getRowCount()));
                nodeMap.put("colcount", String.valueOf(matrix.getColumnCount()));
                results.add(nodeMap);
            }

            ret.setSuccess(true);
            ret.setResults(results);

        } catch (Exception e) {

            ret.setSuccess(false);
            ret.setMsg(e.toString());
        }
        return ret;
    }


    @RequestMapping("/getSubMatrixTree")
    @ResponseBody
    public Map<String, Object> getSubMatrixTree(@RequestParam("atfxFileId") String atfxFileId
            , @RequestParam("measurementId") String measurementId) {


        Map<String, Object> retMap = new HashMap<>();
        String atfxFilePath = odsFileBusiness.getAtfxFilePath(atfxFileId);
        if (StringUtil.isEmpty(atfxFilePath)) {
            return retMap;
        }
        StringBuffer errorInfo = new StringBuffer();
        AtfxSession session = AtfxFileMangrBusiness.openFile(atfxFilePath, errorInfo);
        if (session == null) {

            return retMap;
        }
        try {
            ODSNode rootNode = atfxFileTreeBusiness.getRootNode(session);
            AoMeasurementNode node = atfxMeasurmentBusiness.getAoMeasureMentNodeById(rootNode, measurementId);
            List<AoSubMatrixNode> matrices = node.getSubMatrices();
            List<Map<String, Object>> results = new ArrayList<>();
            for (AoSubMatrixNode matrix : matrices
                    ) {
                Map<String, Object> nodeMap = new HashMap<>();
                nodeMap.put("id", matrix.getStringId());
                nodeMap.put("name", matrix.getNodeName());
                nodeMap.put("rowcount", String.valueOf(matrix.getRowCount()));
                nodeMap.put("colcount", String.valueOf(matrix.getColumnCount()));
                nodeMap.put("leaf", false);
                nodeMap.put("thumb", "matrix.png");
                nodeMap.put("checked", false);
                nodeMap.put("type", "subMatrix");
                nodeMap.put("expanded", true);

                List<Map<String, Object>> children = new ArrayList<>();
                List<AoLocalColumnNode> columns = matrix.getAllColumn();
                for (AoLocalColumnNode col : columns) {
                    Map<String, Object> colMap = new HashMap<>();
                    colMap.put("id", col.getStringId());
                    colMap.put("name", col.getNodeName());
                    colMap.put("rowcount", "");
                    colMap.put("colcount", "");
                    colMap.put("leaf", true);
                    colMap.put("checked", false);
                    colMap.put("expanded", false);
                    colMap.put("thumb", "col.png");
                    colMap.put("type", "localColumn");
                    children.add(colMap);
                }
                nodeMap.put("children", children);
                results.add(nodeMap);
            }


            retMap.put("name", ".");

            retMap.put("children", results);


        } catch (Exception e) {


        }
        return retMap;
    }


    @RequestMapping("/getMatrixDataList")
    @ResponseBody
    public CommonResponseData getMatrixDataList(@RequestParam("atfxFileId") String atfxFileId
            , @RequestParam("subMatrixId") String subMatrixId, @RequestParam("columnNames") String columns, HttpServletRequest request) {
        ExtGridData ret = new ExtGridData();
        String[] colNames = columns.split(",");
        int start = Integer.valueOf(request.getParameter("start"));
        int limit = Integer.valueOf(request.getParameter("limit"));

        StringBuffer errorInfo = new StringBuffer();

        String atfxFilePath = odsFileBusiness.getAtfxFilePath(atfxFileId);
        if (StringUtil.isEmpty(atfxFilePath)) {

            ret.setSuccess(false);
            ret.setMsg("ODS 文件不存在");
            return ret;
        }
        AtfxSession session = AtfxFileMangrBusiness.openFile(atfxFilePath, errorInfo);
        if (session == null) {

            ret.setSuccess(false);
            ret.setMsg(errorInfo.toString());
        }
        try {
            ODSNode rootNode = atfxFileTreeBusiness.getRootNode(session);
            AoSubMatrixNode matrix = atfxMatrixBusiness.getAoSubMatrixNodeById(subMatrixId, rootNode);
            long rowCount = matrix.getRowCount();
            ret.setTotalProperty(rowCount);
            if (start + limit > rowCount) {
                limit = (int) rowCount - start;
            }
            String[][] values = new String[colNames.length][];
            for (int i = 0; i < colNames.length; i++) {
                values[i] = matrix.getValuesByColumnName(colNames[i], start, limit).get(colNames[i]);
            }

            List<Map<String, String>> rowList = new ArrayList<>();
            for (int row = 0; row < limit; row++) {
                Map<String, String> rowData = new HashMap<>();
                for (int colIndex = 0; colIndex < colNames.length; colIndex++) {
                    rowData.put(String.valueOf(colIndex), values[colIndex][row]);
                }
                rowList.add(rowData);
            }

            ret.setSuccess(true);
            ret.setResults(rowList);

        } catch (Exception e) {

            ret.setSuccess(false);
        }

        return ret;
    }


    @RequestMapping("/getMatrixPlotData")
    @ResponseBody
    public CommonResponseData getMatrixPlotData(@RequestParam("atfxFileId") String atfxFileId
            , @RequestParam("subMatrixId") String subMatrixId, @RequestParam("columnNames") String columns, HttpServletRequest request) {
        AjaxResponseData ret = new AjaxResponseData();
        String[] colNames = columns.split(",");
        String atfxFilePath = odsFileBusiness.getAtfxFilePath(atfxFileId);
        if (StringUtil.isEmpty(atfxFilePath)) {

            ret.setSuccess(false);
            ret.setMsg("ODS 文件不存在");
            return ret;
        }
        StringBuffer errorInfo = new StringBuffer();
        AtfxSession session = AtfxFileMangrBusiness.openFile(atfxFilePath, errorInfo);
        if (session == null) {

            ret.setSuccess(false);
            ret.setMsg(errorInfo.toString());
        }
        try {
            ODSNode rootNode = atfxFileTreeBusiness.getRootNode(session);
            AoSubMatrixNode matrix = atfxMatrixBusiness.getAoSubMatrixNodeById(subMatrixId, rootNode);
            long rowCount = matrix.getRowCount();


            Object[][] values = new Object[colNames.length][];
            List<Map<String, Object>> retObj = new ArrayList<>();
            for (int i = 0; i < colNames.length; i++) {
                Map<String, Object> colRet = new HashMap<>();
                colRet.put("colName", colNames[i]);
                values[i] = matrix.getValueDataByColumnName(colNames[i], 0, 0).get(colNames[i]);
                colRet.put("data", values[i]);
                colRet.put("type", values[i][0].getClass());
                retObj.add(colRet);
            }
            ret.setSuccess(true);
            ret.setResults(retObj);

        } catch (Exception e) {

            ret.setSuccess(false);
        }
        return ret;
    }
}
