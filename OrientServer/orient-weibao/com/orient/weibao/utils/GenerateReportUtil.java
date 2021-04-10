package com.orient.weibao.utils;

import com.google.gson.Gson;
import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.edm.init.FileServerConfig;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.operationinterface.IFunction;
import com.orient.utils.CommonTools;
import com.orient.utils.UtilFactory;
import com.orient.weibao.constants.PropertyConstant;
import com.orient.weibao.enums.CheckCellInstCellType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GenerateReportUtil {

    @Autowired
    IBusinessModelService businessModelService;

    @Autowired
    ISqlEngine orientSqlEngine;

    @Autowired
    FileServerConfig fileServerConfig;
    @Autowired
    JdbcTemplate jdbcTemplate;

    String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;

    public void getTableContentHtml(StringBuffer htmlStr, Map checkTableCaseMap) {
        long startTime = System.currentTimeMillis();
        String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;
        IBusinessModel checkTableCaseModel = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_TEMP_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        String tableName = CommonTools.Obj2String(checkTableCaseMap.get("C_NAME_" + checkTableCaseModel.getId()));
        String tableTempId = CommonTools.Obj2String(checkTableCaseMap.get("C_TEMPLATE_" + checkTableCaseModel.getId()));
        String tableCaseId = CommonTools.Obj2String(checkTableCaseMap.get("ID"));
        appendTableName(htmlStr, tableName);
        appendTableBody(htmlStr, tableCaseId, tableTempId);
        long endTime = System.currentTimeMillis();
        System.out.println("耗时："+(endTime-startTime)/1000);
    }

    /**
     * 拼接表单标题
     *
     * @param htmlStr
     * @param tableName 表单名称
     * @return
     */
    public void appendTableName(StringBuffer htmlStr, String tableName) {
        htmlStr.append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
        htmlStr.append("<table width='100%' border='0'>");
        htmlStr.append("<tr>");
        htmlStr.append("	<td width='100%' align='center' style='font-size:30px;font-weight:bold'>").append(tableName).append("</td>");
        htmlStr.append("</tr>");
    }

    /**
     * 拼接表单内容
     *
     * @param htmlStr
     * @param tableCaseId
     * @param tableTempId
     */
    private void appendTableBody(StringBuffer htmlStr, String tableCaseId, String tableTempId) {

        IBusinessModel headInstModel = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_HEADER_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel cellInstModel = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_CELL_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel uploadCellModel = businessModelService.getBusinessModelBySName(PropertyConstant.CELL_INST_DATA, schemaId, EnumInter.BusinessModelEnum.Table);

        headInstModel.setReserve_filter("AND T_CHECK_TEMP_INST_" + schemaId + "_ID='" + tableCaseId + "'"
        );
        List<Map> columnMapList = orientSqlEngine.getBmService().createModelQuery(headInstModel).orderAsc("TO_NUMBER(ID)").list();

        cellInstModel.setReserve_filter("AND T_CHECK_TEMP_INST_" + schemaId + "_ID='" + tableCaseId + "'" + " AND C_IS_HEADER_" + cellInstModel.getId() + "='" + "true" + "'");
        List<Map> headerContentList = orientSqlEngine.getBmService().createModelQuery(cellInstModel).orderAsc("TO_NUMBER(ID)").list();
        List<Map<String, String>> uploadCellMapList = orientSqlEngine.getBmService().createModelQuery(uploadCellModel).list();//查出所有实例行

        appendHeaderEndContent(htmlStr, headerContentList, cellInstModel, uploadCellModel, columnMapList, uploadCellMapList,true);

        appendColumns(htmlStr, columnMapList, headInstModel);                   //拼接列头
        appendRows(htmlStr, tableCaseId, columnMapList, uploadCellMapList);         //拼接行

        cellInstModel.clearAllFilter();
        cellInstModel.setReserve_filter("AND T_CHECK_TEMP_INST_" + schemaId + "_ID='" + tableCaseId + "'" + " AND C_IS_HEADER_" + cellInstModel.getId() + "='" + "false" + "'");
        List<Map> endContentList = orientSqlEngine.getBmService().createModelQuery(cellInstModel).orderAsc("TO_NUMBER(ID)").list();
        appendHeaderEndContent(htmlStr, endContentList, cellInstModel, uploadCellModel, columnMapList, uploadCellMapList,false);
        htmlStr.append("</table>");
    }

    private void appendHeaderEndContent(StringBuffer htmlStr, List<Map> headerContentList, IBusinessModel cellInstModel, IBusinessModel uploadCellModel, List<Map> columnMapList, List<Map<String, String>> uploadCellMapList,boolean isHeader) {
       if (isHeader){
           htmlStr.append("<br/><table width='100%' border='1' cellspacing='0'>");
       }
        htmlStr.append("<tr>");
        for (Map headerContentMap : headerContentList) {
            String headerCellId = headerContentMap.get("ID").toString();
            String headerName = CommonTools.Obj2String(headerContentMap.get("C_CONTENT_" + cellInstModel.getId()));
            String headerType = CommonTools.Obj2String(headerContentMap.get("C_CELL_TYPE_" + cellInstModel.getId()));
            htmlStr.append("<td style='text-align:center'>");
            htmlStr.append(headerName).append("</td>");
            headerEndHtml(htmlStr, headerType, headerCellId, cellInstModel, uploadCellModel, columnMapList,uploadCellMapList);

            htmlStr.append("</tr>");
        }

    }

    private void headerEndHtml(StringBuffer htmlStr, String headerType, String cellId, IBusinessModel cellInstBM, IBusinessModel uploadCellModel,List<Map> columnMapList, List<Map<String, String>> uploadCellMapList) {
        if (headerType.indexOf("#") == 0 && headerType.length() != 0) {
            htmlStr.append("<td  style='text-align:center' colspan='"+(columnMapList.size()-1)+"'>");
            List<Map<String, Object>> fileList = UtilFactory.newArrayList();
            String fileSql = "";
            switch (headerType) {
                case CheckCellInstCellType.TYPE_CHECK:
                    if (uploadCellMapList.size() > 0) {
                        for (Map uploadMap : uploadCellMapList) {
                            String refCellInstId = CommonTools.Obj2String(uploadMap.get("T_CHECK_CELL_INST_" + schemaId + "_ID"));
                            if (cellId.equals(refCellInstId)) {
                                String uploadCellContent = CommonTools.Obj2String(uploadMap.get("C_CONTENT_" + uploadCellModel.getId()));
                                htmlStr.append(uploadCellContent.equals("是") ? "通过" : "不通过");
                                break;
                            }
                        }
                    }
                    break;
                case CheckCellInstCellType.TYPE_FILL:
                    htmlStr = commonHtml(uploadCellMapList, htmlStr, cellId, uploadCellModel);
                    break;
                case CheckCellInstCellType.TYPE_SHUZI:
                    htmlStr = commonHtml(uploadCellMapList, htmlStr, cellId, uploadCellModel);
                    break;
                case CheckCellInstCellType.TYPE_TIME:
                    htmlStr = commonHtml(uploadCellMapList, htmlStr, cellId, uploadCellModel);
                    break;
                case CheckCellInstCellType.TYPE_PICTURE:
                    fileSql= "select * from cwm_file where DATAID='" + cellId + "' and TABLEID='" + cellInstBM.getId() + "'";
                    fileList = jdbcTemplate.queryForList(fileSql);
                    if (fileList.size() > 0) {
                        htmlStr.append("<div  style='text-align:center'><img src='./app/images/icons/default/upload/preview.png' style='cursor:pointer' onclick ='OrientTdm.CurrentTaskMgr.CheckTableCaseHtmlPanel.showPictures(" + cellId + ")'>")
                                .append("</img></div></td>");
                    }
                    break;
                case CheckCellInstCellType.TYPE_SIGN:
                    fileSql = "select * from cwm_file where DATAID='" + cellId + "' and TABLEID='" + cellInstBM.getId() + "'";
                    fileList = jdbcTemplate.queryForList(fileSql);
                    if (fileList.size() > 0) {
                        String fileName = (String) fileList.get(0).get("FINALNAME");
                        String signPicturePath = "preview" + File.separator + "imagePreview" + File.separator + fileName;
                        htmlStr.append("<div  style='text-align:center'>").append("<img width='40px' height=40px' src=").append(signPicturePath)
                                .append(">")
                                .append("</img></div>");
                    }
                    break;
                case CheckCellInstCellType.TYPE_TROUBLE:
                    fileSql = "select * from cwm_file where DATAID='" + cellId + "' and TABLEID='" + cellInstBM.getId() + "'";
                    fileList = jdbcTemplate.queryForList(fileSql);
                    if (fileList.size() > 0) {
                        htmlStr.append("<div  style='text-align:center'><img src='./app/images/icons/default/upload/preview.png' style='cursor:pointer' onclick ='OrientTdm.CurrentTaskMgr.CheckTableCaseHtmlPanel.showPictures(" + cellId + ")'>")
                                .append("</img></div></td>");
                    }
                    break;
                case CheckCellInstCellType.TYPE_YDN:
                    if (uploadCellMapList.size() > 0) {
                        for (Map uploadMap : uploadCellMapList) {
                            String refCellInstId = CommonTools.Obj2String(uploadMap.get("T_CHECK_CELL_INST_" + schemaId + "_ID"));
                            if (cellId.equals(refCellInstId)) {
                                String uploadCellContent = CommonTools.Obj2String(uploadMap.get("C_CONTENT_" + uploadCellModel.getId()));
                                if (uploadCellContent.equals("是")) {
                                    htmlStr.append("通过");
                                } else if (uploadCellContent.equals("否")) {
                                    htmlStr.append("不通过");
                                } else if (uploadCellContent.equals("无")) {
                                    htmlStr.append("无");
                                }
                                break;
                            }
                        }
                    }
                    break;
                case CheckCellInstCellType.TYPE_JWD:
                    htmlStr = commonHtml(uploadCellMapList, htmlStr, cellId, uploadCellModel);
                    break;
            }
            htmlStr.append("</td>");
        }
    }

    /**
     * 拼接列头
     *
     * @param htmlStr
     * @param columnMapList
     * @param columnModel
     */
    private void appendColumns(StringBuffer htmlStr, List<Map> columnMapList, IBusinessModel columnModel) {
//        htmlStr.append("<br/><table width='100%' border='1' cellspacing='0'>");
        htmlStr.append("<tr>");
//        columnMapList.forEach(
//                columnMap -> htmlStr.append("<th>")
//                        .append(CommonTools.Obj2String(columnMap.get("C_NAME_" + columnModel.getId())))
//                        .append("</th>"));
        for (Map columnMap : columnMapList) {
            String columnName = CommonTools.Obj2String(columnMap.get("C_NAME_" + columnModel.getId()));
            htmlStr.append("<th>");
            if (columnName.indexOf("#") == 0 && columnName.length() != 0) {
                columnName = columnName.substring(1, columnName.length());
            }
            htmlStr.append(columnName).append("</th>");
        }
        htmlStr.append("</tr>");
    }

    /**
     * 拼接行
     *
     * @param htmlStr
     * @param tableCaseId
     * @param columnMapList
     */
    private void appendRows(StringBuffer htmlStr, String tableCaseId, List<Map> columnMapList, List<Map<String, String>> uploadCellMapList) {
        IBusinessModel rowModel = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_ROW_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel cellModel = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_CELL_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel headInstModel = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_HEADER_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel uploadCellModel = businessModelService.getBusinessModelBySName(PropertyConstant.CELL_INST_DATA, schemaId, EnumInter.BusinessModelEnum.Table);
        rowModel.setReserve_filter("AND T_CHECK_TEMP_INST_" + schemaId + "_ID='" + tableCaseId + "'");
//        uploadCellModel.setReserve_filter("AND T_CHECK_TEMP_INST_" + schemaId + "_ID='" + tableCaseId + "'");
        List<Map<String, String>> rowCaseMapList = orientSqlEngine.getBmService().createModelQuery(rowModel).list();//查出所有实例行
        Collections.reverse(rowCaseMapList);
//        List<Map<String, String>> uploadCellMapList = orientSqlEngine.getBmService().createModelQuery(uploadCellModel).list();//查出所有实例行
        for (int i = 0; i < rowCaseMapList.size(); i++) {
            Map<String, String> rowCaseMap = rowCaseMapList.get(i);
            String rowId = CommonTools.Obj2String(rowCaseMap.get("ID"));//实例行主键
            String rowNum = CommonTools.Obj2String(rowCaseMap.get("C_ROW_NUMBER_" + rowModel.getId()));
            htmlStr.append("<tr>");  //开始拼接html里的一行表单内容
            for (int j = 0; j < columnMapList.size(); j++) {
                Map columnMap = columnMapList.get(j);
                String columnInstId = CommonTools.Obj2String(columnMap.get("ID"));
                String columnName = CommonTools.Obj2String(columnMap.get("C_NAME_" + headInstModel.getId()));
                cellModel.clearAllFilter();
                cellModel.setReserve_filter("AND T_CHECK_TEMP_INST_" + schemaId + "_ID='" + tableCaseId + "'" +
                        " AND T_CHECK_ROW_INST_" + schemaId + "_ID='" + rowId + "'" +
                        " AND T_CHECK_HEADER_INST_" + schemaId + "_ID='" + columnInstId + "'");
                List<Map> cellInstMapList = orientSqlEngine.getBmService().createModelQuery(cellModel).list();
                String isCheck = "false";
                if (columnName.indexOf("#") == 0 && columnName.length() != 0) {
                    isCheck = "true";
                }
                switch (isCheck) {
                    case "false"://如果是描述项，去单元格实例里找
                        String descContent = "";
                        if (cellInstMapList.size() > 0) {
                            Map cellTempMap = cellInstMapList.get(0);
                            descContent = CommonTools.Obj2String(cellTempMap.get("C_CONTENT_" + cellModel.getId()));
                            if ("".equals(descContent)) {
                                descContent = "---";
                            }
                        }
                        htmlStr.append("<td style='text-align:center'>")
                                .append(descContent)
                                .append("</td>");
                        break;

                    case "true":
                        if (cellInstMapList.size() > 0) {
                            htmlStr.append("<td  style='text-align:center'>");
                            Map cellTempMap = cellInstMapList.get(0);
                            String cellInstId = CommonTools.Obj2String(cellTempMap.get("ID"));
                            String cellType = CommonTools.Obj2String(cellTempMap.get("C_CELL_TYPE_" + cellModel.getId()));
                            String sql = "select * from cwm_file where DATAID='" + cellInstId + "' and TABLEID='" + cellModel.getId() + "'";
                            List<Map<String, Object>> fileList = jdbcTemplate.queryForList(sql);
                            switch (cellType) {
                                case CheckCellInstCellType.TYPE_CHECK:
                                    if (uploadCellMapList.size() > 0) {
                                        for (Map uploadMap : uploadCellMapList) {
                                            String refCellInstId = CommonTools.Obj2String(uploadMap.get("T_CHECK_CELL_INST_" + schemaId + "_ID"));
                                            if (cellInstId.equals(refCellInstId)) {
                                                String uploadCellContent = CommonTools.Obj2String(uploadMap.get("C_CONTENT_" + uploadCellModel.getId()));
                                                htmlStr.append(uploadCellContent.equals("是") ? "通过" : "不通过");
                                                break;
                                            }
                                        }
                                    }
                                    break;
                                case CheckCellInstCellType.TYPE_FILL:
                                    htmlStr = commonHtml(uploadCellMapList, htmlStr, cellInstId, uploadCellModel);
                                    break;
                                case CheckCellInstCellType.TYPE_SHUZI:
                                    htmlStr = commonHtml(uploadCellMapList, htmlStr, cellInstId, uploadCellModel);
                                    break;
                                case CheckCellInstCellType.TYPE_KONG:
                                    htmlStr = commonHtml(uploadCellMapList, htmlStr, cellInstId, uploadCellModel);
                                    break;
                                case CheckCellInstCellType.TYPE_TIME:
                                    htmlStr = commonHtml(uploadCellMapList, htmlStr, cellInstId, uploadCellModel);
                                    break;
                                case CheckCellInstCellType.TYPE_PICTURE:
                                    if (fileList.size() > 0) {
                                        htmlStr.append("<div  style='text-align:center'><img src='./app/images/icons/default/upload/preview.png' style='cursor:pointer' onclick ='OrientTdm.CurrentTaskMgr.CheckTableCaseHtmlPanel.showPictures(" + cellInstId + ")'>")
                                                .append("</img></div></td>");
                                    }
                                    break;
                                case CheckCellInstCellType.TYPE_SIGN:
                                    if (fileList.size() > 0) {
                                        String fileName = (String) fileList.get(0).get("FINALNAME");
                                        String signPicturePath = "preview" + File.separator + "imagePreview" + File.separator + fileName;
                                        htmlStr.append("<div  style='text-align:center'>").append("<img width='40px' height=40px' src=").append(signPicturePath)
                                                .append(">")
                                                .append("</img></div>");
                                    }
                                    break;
                                case CheckCellInstCellType.TYPE_TROUBLE:
                                    if (fileList.size() > 0) {
                                        htmlStr.append("<div  style='text-align:center'><img src='./app/images/icons/default/upload/preview.png' style='cursor:pointer' onclick ='OrientTdm.CurrentTaskMgr.CheckTableCaseHtmlPanel.showPictures(" + cellInstId + ")'>")
                                                .append("</img></div></td>");
                                    }
                                    break;
                                case CheckCellInstCellType.TYPE_YDN:
                                    if (uploadCellMapList.size() > 0) {
                                        for (Map uploadMap : uploadCellMapList) {
                                            String refCellInstId = CommonTools.Obj2String(uploadMap.get("T_CHECK_CELL_INST_" + schemaId + "_ID"));
                                            if (cellInstId.equals(refCellInstId)) {
                                                String uploadCellContent = CommonTools.Obj2String(uploadMap.get("C_CONTENT_" + uploadCellModel.getId()));
                                                if (uploadCellContent.equals("是")) {
                                                    htmlStr.append("通过");
                                                } else if (uploadCellContent.equals("否")) {
                                                    htmlStr.append("不通过");
                                                } else if (uploadCellContent.equals("无")) {
                                                    htmlStr.append("无");
                                                }
                                                break;
                                            }
                                        }
                                    }
                                    break;
                                case CheckCellInstCellType.TYPE_JWD:
                                    htmlStr = commonHtml(uploadCellMapList, htmlStr, cellInstId, uploadCellModel);
                                    break;
                            }
                            htmlStr.append("</td>");
                            break;
                        }
                }
            }
            htmlStr.append("</tr>");
        }
    }

    private StringBuffer commonHtml(List<Map<String, String>> uploadCellMapList, StringBuffer htmlStr, String cellInstId, IBusinessModel uploadCellModel) {
        if (uploadCellMapList.size() > 0) {
            for (Map uploadMap : uploadCellMapList) {
                String refCellInstId = CommonTools.Obj2String(uploadMap.get("T_CHECK_CELL_INST_" + schemaId + "_ID"));
                if (cellInstId.equals(refCellInstId)) {
                    String uploadCellContent = CommonTools.Obj2String(uploadMap.get("C_CONTENT_" + uploadCellModel.getId()));
                    if ("".equals(uploadCellContent)) {
                        htmlStr.append("---");
                    } else {
                        htmlStr.append(uploadCellContent);
                    }
                    break;
                }
            }
        }
        return htmlStr;
    }
}
