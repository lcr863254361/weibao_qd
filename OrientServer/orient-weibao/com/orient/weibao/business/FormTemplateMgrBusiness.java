package com.orient.weibao.business;

import com.alibaba.fastjson.JSON;
import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.edm.init.FileServerConfig;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.modeldata.controller.ModelDataController;
import com.orient.modeldata.event.SaveModelDataEvent;
import com.orient.modeldata.event.UpdateModelDataEvent;
import com.orient.modeldata.eventParam.SaveModelDataEventParam;
import com.orient.modeldata.util.FtpFileUtil;
import com.orient.mongorequest.utils.GsonUtil;
import com.orient.sysman.bean.FuncBean;
import com.orient.utils.*;
import com.orient.utils.ExcelUtil.Excel;
import com.orient.utils.ExcelUtil.reader.DataEntity;
import com.orient.utils.ExcelUtil.reader.FieldEntity;
import com.orient.utils.ExcelUtil.reader.TableEntity;
import com.orient.utils.ExcelUtil.style.Align;
import com.orient.utils.ExcelUtil.style.BorderStyle;
import com.orient.utils.ExcelUtil.style.Color;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseBusiness;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import com.orient.web.springmvcsupport.exception.DSException;
import com.orient.weibao.bean.CheckTypeTreeNode;
import com.orient.weibao.bean.FormProductTreeNode;
import com.orient.weibao.bean.ProductStructureTreeNode;
import com.orient.weibao.bean.flowPost.Column;
import com.orient.weibao.bean.flowPost.Field;
import com.orient.weibao.bean.flowPost.FlowPostData;
import com.orient.weibao.constants.PropertyConstant;
import com.orient.weibao.enums.CheckCellInstCellType;
import com.orient.weibao.enums.CheckNodeType;
import com.orient.weibao.enums.ProductStructureNodeType;
import com.orient.weibao.utils.SqlUtil;
import com.orient.weibao.utils.ZipFileUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2018-12-12 20:22
 */
@Service
public class FormTemplateMgrBusiness extends BaseBusiness {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    FileServerConfig fileServerConfig;

    String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;


    @Autowired
    private TaskPrepareMgrBusiness taskPrepareMgrBusiness;

    @Autowired
    MetaDAOFactory metaDAOFactory;

    @Autowired
    ProductStructureBusiness productStructureBusiness;

    private String filename;

    public List<Map> queryCheckTypeList() {

        IBusinessModel checkTypeModel = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_TYPE, schemaId, EnumInter.BusinessModelEnum.Table);
        String modelId = checkTypeModel.getId();
        String sql = "select * from T_CHECK_TYPE_" + schemaId + " order by id ";
        List<Map<String, Object>> checkTypeList = jdbcTemplate.queryForList(sql);
        List checkList = new ArrayList<>();
        if (checkTypeList.size() > 0) {
            for (Map map : checkTypeList) {
                Map checkMap = new HashMap<>();
                checkMap.put("id", map.get("ID"));
                checkMap.put("checkType", map.get("C_CHECK_NAME_" + modelId));
                checkList.add(checkMap);
            }
        }
        return checkList;
    }

    public AjaxResponseData saveCheckTypeData(String modelId, String formData) {
        AjaxResponseData retVal = new AjaxResponseData();
        if (StringUtil.isEmpty(formData)) {
            retVal.setMsg("保存内容为空");
            retVal.setSuccess(false);
            return retVal;
        }
        Map formDataMap = JsonUtil.json2Map(formData);
        String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;
        Map dataMap = (Map) formDataMap.get("fields");
        dataMap.put("C_CHECK_NAME_" + modelId, dataMap.get("C_CHECK_NAME_" + modelId));
        SaveModelDataEventParam eventParam = new SaveModelDataEventParam();
        eventParam.setModelId(modelId);
        eventParam.setDataMap(dataMap);
        eventParam.setCreateData(true);
        OrientContextLoaderListener.Appwac.publishEvent(new SaveModelDataEvent(ModelDataController.class, eventParam));
        retVal.setMsg("保存成功");
        return retVal;
    }

    /**
     * 插入检查表模板数据,即Excel文件名到数据库中
     *
     * @param: fileName
     * @param: checkTypeId
     * @return:
     */
    public String insertCheckListTemplate(String fileName, String checkTypeId, String checkTypeName) {
        String tableName = PropertyConstant.CHECK_TEMP;
        String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;
        IBusinessModel bm = businessModelService.getBusinessModelBySName(tableName, schemaId, EnumInter.BusinessModelEnum.Table);
        int index = fileName.lastIndexOf(".");
        if (index != -1) {
            fileName = fileName.substring(0, index);
        }
        String modelId = bm.getId();
        bm.setReserve_filter("and C_NAME_" + modelId + "='" + fileName + "'" +
                " and T_CHECK_TYPE_" + schemaId + "_ID='" + checkTypeId + "'");
        List<Map<String, String>> list = orientSqlEngine.getBmService().createModelQuery(bm).list();
        if (list.size() > 0) {
            Map<String, String> oldCheckTemp = list.get(0);
            String oldCheckTempId = oldCheckTemp.get("ID");
            oldCheckTemp.put("ID", "");
            String newCheckTempId = orientSqlEngine.getBmService().insertModelData(bm, oldCheckTemp);
            return oldCheckTempId + "," + newCheckTempId;
        }
        Map<String, String> data = UtilFactory.newHashMap();
        data.put("C_NAME_" + bm.getId(), fileName);
        data.put("C_TEMP_TYPE_" + bm.getId(), "1");
        data.put("C_IS_REPEAT_UPLOAD_" + bm.getId(), "否");
        if (StringUtil.isNotEmpty(checkTypeId)) {
            data.put("T_CHECK_TYPE_" + schemaId + "_ID", checkTypeId);
        }
        return orientSqlEngine.getBmService().insertModelData(bm, data);
    }

    public Map<String, Object> importHeadCellList(TableEntity excelEntity, List<String> headers, String tempId, String filename) {
        this.filename = filename;
        String[] ids = tempId.split(",");
        Map<String, Object> retValue = null;
        if (ids.length == 2) {
            String oldId = ids[0];
            String newId = ids[1];
            retValue = handleRepeat(oldId, newId, excelEntity, headers);
            retValue.put("isRepeat", true);
            return retValue;
        }
        insertTableHeaderAndTabletTail(excelEntity, tempId);
        retValue = insertTableDetail(excelEntity, headers, tempId);
        retValue.put("isRepeat", false);
        return retValue;
    }

    public AjaxResponseData<List<CheckTypeTreeNode>> getCheckTempTreeNodes(String id, String type, String checkTypeId) {
        List<CheckTypeTreeNode> list = new ArrayList<>();
        String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;
        IBusinessModel businessModel = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_TEMP, schemaId, EnumInter.BusinessModelEnum.Table);
//        IBusinessModel checkTypeBM=businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_TYPE, schemaId, EnumInter.BusinessModelEnum.Table);
        switch (type) {
            case CheckNodeType.TYPE_ROOT:
                businessModel.setReserve_filter("AND T_CHECK_TYPE_" + schemaId + "_ID='" + checkTypeId + "'");
                List<Map> checkTempList = orientSqlEngine.getBmService().createModelQuery(businessModel).orderAsc("C_NAME_" + businessModel.getId()).list();
                if (checkTempList.size() > 0) {
                    for (Map map : checkTempList) {
                        CheckTypeTreeNode checkTypeTreeNode = new CheckTypeTreeNode();
//                       String sql="select c_check_name_"+checkTypeBM.getId()+" from "+PropertyConstant.CHECK_TYPE+"_"+schemaId+" where id='"+checkTypeId+"'";
//                       List<Map<String,Object>> checkTypeList=jdbcTemplate.queryForList(sql);
//                       String checkTypeName=CommonTools.Obj2String(checkTypeList.get(0).get("c_check_name_" + checkTypeBM.getId()));
//                       String fileName=CommonTools.Obj2String(map.get("C_NAME_" + businessModel.getId()));
//                       fileName=fileName+"("+checkTypeName+")";
                        checkTypeTreeNode.setText(CommonTools.Obj2String(map.get("C_NAME_" + businessModel.getId())));
                        String checkTempId = CommonTools.Obj2String(map.get("ID"));
                        checkTypeTreeNode.setId(checkTempId);
                        checkTypeTreeNode.setDataId(checkTempId);
                        checkTypeTreeNode.setExpanded(false);
                        checkTypeTreeNode.setType(CheckNodeType.TYPE_ROOT);
                        String tempType = CommonTools.Obj2String(map.get("C_TEMP_TYPE_" + businessModel.getId()));
                        checkTypeTreeNode.setTempType(tempType);
                        String isRepeatUpload = CommonTools.Obj2String(map.get("C_IS_REPEAT_UPLOAD_" + businessModel.getId()));
                        checkTypeTreeNode.setIsRepeatUpload(isRepeatUpload);
                        checkTypeTreeNode.setIcon("app/images/function/数据建模.png");
                        checkTypeTreeNode.setIconCls("icon-function");
                        String tempName = CommonTools.Obj2String(map.get("C_NAME_" + businessModel.getId()));
                        tempName = tempName + "-" + tempType;
                        checkTypeTreeNode.setQtip(tempName);
                        checkTypeTreeNode.setLeaf(true);
                        list.add(checkTypeTreeNode);
                    }
                }
                break;
        }

        return new AjaxResponseData<>(list);

    }

    /**
     * 删除检查表模板及其关联的表头、行、单元格
     *
     * @param: checkTempId
     * @return:
     */
    public void delCheckList(String checkTempId) {
        String tableName = PropertyConstant.CHECK_TEMP;
        String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;
        IBusinessModel bm = businessModelService.getBusinessModelBySName(tableName, schemaId, EnumInter.BusinessModelEnum.Table);
        String[] ids = checkTempId.split("\\,");
        for (String str : ids) {
            orientSqlEngine.getBmService().deleteCascade(bm, str);
        }
    }

    public List<String> getTemplateHeadersById(String checkTempId, boolean isInst) {

        String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;
        String tempTableName = PropertyConstant.CHECK_TEMP;
        String headerTableName = PropertyConstant.CHECK_HEADER;

        if (isInst) {
            tempTableName = PropertyConstant.CHECK_TEMP_INST;
            headerTableName = PropertyConstant.CHECK_HEADER_INST;
        }

        IBusinessModel headerBM = businessModelService.getBusinessModelBySName(headerTableName, schemaId, EnumInter.BusinessModelEnum.Table);
        headerBM.setReserve_filter("AND " + tempTableName + "_" + schemaId + "_ID='" + checkTempId + "'");
        List<Map<String, String>> headerList = orientSqlEngine.getBmService().createModelQuery(headerBM).list();

        List<String> retVal = UtilFactory.newArrayList();

        String nameCol = "C_NAME_" + headerBM.getId();

        for (Map<String, String> map : headerList) {
            String name = map.get(nameCol);
            String id = map.get("ID");
            String dataTemp = name + "[+]" + id;
            retVal.add(dataTemp);
        }
        return retVal;
    }

    public List<Map<String, String>> getCellContent(String checkTempId, boolean isInst, boolean withData, String productId) {

        List<Map<String, String>> retVal = UtilFactory.newArrayList();
        String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;
        String tempTable = PropertyConstant.CHECK_TEMP;
        String headerTable = PropertyConstant.CHECK_HEADER;
        String rowTable = PropertyConstant.CHECK_ROW;
        String cellTable = PropertyConstant.CHECK_CELL;

        String cellDataTable = PropertyConstant.CELL_INST_DATA;

        //表单模板实例
        if (isInst) {
            tempTable = PropertyConstant.CHECK_TEMP_INST;
            headerTable = PropertyConstant.CHECK_HEADER_INST;
            rowTable = PropertyConstant.CHECK_ROW_INST;
            cellTable = PropertyConstant.CHECK_CELL_INST;
        }

        IBusinessModel rowBM = businessModelService.getBusinessModelBySName(rowTable, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel cellBM = businessModelService.getBusinessModelBySName(cellTable, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel cellDataBM = businessModelService.getBusinessModelBySName(cellDataTable, schemaId, EnumInter.BusinessModelEnum.Table);
        if (StringUtil.isNotEmpty(productId)) {
            rowBM.setReserve_filter(" AND " + tempTable + "_" + schemaId + "_ID='" + checkTempId + "'" +
                    " AND C_PRODUCT_ID_" + rowBM.getId() + "='" + productId + "'");
        } else {
//            CustomerFilter customerFilterRows = new CustomerFilter(tempTable + "_" + schemaId + "_ID", EnumInter.SqlOperation.Equal, checkTempId);
//            rowBM.appendCustomerFilter(customerFilterRows);
            rowBM.setReserve_filter(" AND " + tempTable + "_" + schemaId + "_ID='" + checkTempId + "'");
        }
        List<Map<String, String>> rowData = orientSqlEngine.getBmService().createModelQuery(rowBM).list();
        Collections.reverse(rowData);

        Map<String, Integer> rowPositionMap = UtilFactory.newHashMap();
        List<Map<String, String>> cellData = new ArrayList<>();
        for (int i = 0; i < rowData.size(); i++) {
            Map<String, String> map = UtilFactory.newHashMap();
            rowPositionMap.put(rowData.get(i).get("ID"), i);
            retVal.add(map);

            cellBM.setReserve_filter(" AND " + tempTable + "_" + schemaId + "_ID='" + checkTempId + "'" +
                    " AND " + rowTable + "_" + schemaId + "_ID='" + rowData.get(i).get("ID") + "'");
            cellData = orientSqlEngine.getBmService().createModelQuery(cellBM).list();

            //拼接返回值
            String contentCol = "C_CONTENT_" + cellBM.getId();
            String checkRow = rowTable + "_" + schemaId + "_ID";
            String checkHeader = headerTable + "_" + schemaId + "_ID";

            List<Map<String, String>> cellDataWithData = UtilFactory.newArrayList();
            String cellDataCol = cellTable + "_" + schemaId + "_ID";
            if (isInst && withData) {
                cellDataBM.appendCustomerFilter(new CustomerFilter(tempTable + "_" + schemaId + "_ID", EnumInter.SqlOperation.Equal, checkTempId));
                cellDataWithData = orientSqlEngine.getBmService().createModelQuery(cellDataBM).list();
            }

            for (Map<String, String> cellMap : cellData) {
                String content = cellMap.get(contentCol);
                String cellId = cellMap.get("ID");
                content = content + "::" + cellId;

                if (cellDataWithData.size() != 0) {
//                for (Map dataMap:cellDataWithData){
//                    if (dataMap.get(cellDataCol).equals(cellId)){
//                         content += "::" + dataMap.get("C_NAME_"+cellDataBM.getId());
//                    }
//                }
                    List<Map<String, String>> dataMapList = cellDataWithData.stream().filter(dataMap -> dataMap.get(cellDataCol).equals(cellId)).collect(Collectors.toList());
                    if (dataMapList.size() != 0) {
                        String uploadContent = dataMapList.get(0).get("C_CONTENT_" + cellDataBM.getId());
                        if (uploadContent != null && !"".equals(uploadContent)) {
                            content += "::" + dataMapList.get(0).get("C_CONTENT_" + cellDataBM.getId());
                        }
                    }
                }

                String rowId = cellMap.get(checkRow);
                String headerId = cellMap.get(checkHeader);
                int position = rowPositionMap.get(rowId);
                retVal.get(position).put(headerId, StringUtil.isEmpty(content) ? "" : content);
            }
        }

//        //得到cell
//        CustomerFilter customerFilterCells = new CustomerFilter(tempTable + "_" + schemaId + "_ID", EnumInter.SqlOperation.Equal, checkTempId);
//        cellBM.appendCustomerFilter(customerFilterCells);
//        List<Map<String, String>> cellData = orientSqlEngine.getBmService().createModelQuery(cellBM).list();

        return retVal;
    }

    public Map getTemplateCellIds(String checkTempId, String modelId) {
        Map<String, String> cellIdsMap = UtilFactory.newHashMap();
        List<Map<String, String>> retVal = UtilFactory.newArrayList();
        //表单模板实例
        String tempTable = PropertyConstant.CHECK_TEMP_INST;
        String cellTable = PropertyConstant.CHECK_CELL_INST;

        IBusinessModel cellBM = businessModelService.getBusinessModelBySName(cellTable, schemaId, EnumInter.BusinessModelEnum.Table);
        cellBM.setReserve_filter(" AND " + tempTable + "_" + schemaId + "_ID='" + checkTempId + "'" +
                " AND C_CELL_TYPE_" + cellBM.getId() + "='" + "#3" + "'");
        List<Map<String, String>> cellData = orientSqlEngine.getBmService().createModelQuery(cellBM).orderAsc("TO_NUMBER(T_CHECK_ROW_INST_" + schemaId + "_ID)").list();

        List<String> cellIdsList = UtilFactory.newArrayList();
        String cellIds = "";
        if (cellData.size() > 0) {
            for (Map<String, String> cellMap : cellData) {
                String cellId = cellMap.get("ID");
                cellIdsList.add(cellId);
                cellIds += cellId + ",";
            }
            cellIds = cellIds.substring(0, cellIds.length() - 1);
        }
        if (cellIds != null && !"".equals(cellIds)) {
            //distinct去重
            String sql = "select DISTINCT DATAID from cwm_file where DATAID IN (" + cellIds + ") and TABLEID='" + modelId + "'";
            List<Map<String, Object>> hasPhotoList = jdbcTemplate.queryForList(sql);
            if (cellIdsList.size() >= hasPhotoList.size()) {
                for (int i = 0; i < cellIdsList.size(); i++) {
                    String cellId = cellIdsList.get(i);
                    for (Map hasMap : hasPhotoList) {
                        String dataId = (String) hasMap.get("DATAID");
                        if (cellId.equals(dataId)) {
//                            if (!"true".equals(cellIdsMap.get(cellId))){
                            cellIdsMap.put(cellId, "true");
//                            }
                        }
                    }
                    if (cellIdsMap.get(cellId) == null) {
                        cellIdsMap.put(cellId, "false");
                    }
                }
            }
        }
        return cellIdsMap;
    }

    public String exportCheckTempFromOracle(String checkTempId, String checkName, boolean isExportAll, String checkTypeFolderPath) {

        String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;

        //创建列
        IBusinessModel headerBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_HEADER, schemaId, EnumInter.BusinessModelEnum.Table);
        headerBM.setReserve_filter("AND T_CHECK_TEMP_" + schemaId + "_ID='" + checkTempId + "'");
        List<Map<String, String>> headerData = orientSqlEngine.getBmService().createModelQuery(headerBM).list();

        //对data集合中的数据进行倒叙排序
        Collections.reverse(headerData);
        String nameCol = "C_NAME_" + headerBM.getId();
        Excel excel = new Excel();
        final int[] columnIndex = {0};
        final int[] cell = {0};
        for (Map<String, String> map : headerData) {
            String name = map.get(nameCol);
            String id = map.get("ID");
            excel.column(columnIndex[0]).autoWidth().borderFull(BorderStyle.DASH_DOT, Color.BLACK).align(Align.CENTER);
            excel.cell(0, columnIndex[0])//选择第5行，但忽略第1个单元格，从第2个单元格开始操作
                    .value(name);
            columnIndex[0]++;

            List<Map<String, String>> cellList = this.getCellContent(checkTempId, false, false, "");
            final int[] row = {1};
            for (Map map1 : cellList) {
                Set<String> keys = map1.keySet();
                for (String key : keys) {
                    if (key.equals(id)) {
                        String cellContent = CommonTools.Obj2String(map1.get(id));
                        String[] content = cellContent.split("::");
                        excel.cell(row[0], cell[0])
                                .value(content[0]).warpText(true);
                        row[0]++;
                    } else {
                        continue;
                    }
                }
            }
            cell[0]++;
        }

        fillTableHeaderAndTail(checkTempId, excel);
        String folder = FtpFileUtil.getRelativeUploadPath(FtpFileUtil.EXPORT_ROOT);
        String fileName = checkName + ".xls";
        String finalFileName = FtpFileUtil.getOnlyFileName(fileName);
        if (isExportAll) {
            excel.saveExcel(checkTypeFolderPath + File.separator + fileName);
        } else {
            FtpFileUtil.makeDir(fileServerConfig.getFtpHome(), folder);
            excel.saveExcel(fileServerConfig.getFtpHome() + folder + finalFileName);
        }
        return folder + finalFileName;
    }

    public void exportAllCheckTempByCheckTypeId(String checkTypeId, String checkTypeName, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (checkTypeName.contains(":")) {
            checkTypeName = checkTypeName.replaceAll(":", "-");
        }
        IBusinessModel checkTempBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_TEMP, schemaId, EnumInter.BusinessModelEnum.Table);
        checkTempBM.setReserve_filter("AND T_CHECK_TYPE_" + schemaId + "_ID='" + checkTypeId + "'");
        List<Map<String, Object>> checkTempList = orientSqlEngine.getBmService().createModelQuery(checkTempBM).orderAsc("C_NAME_" + checkTempBM.getId()).list();
        if (checkTempList.size() > 0) {
            //根据日期创建表单模板目录
            SimpleDateFormat pathFormat = new SimpleDateFormat("-yyyy-MM-dd-");
            String subFolder = "/" + FtpFileUtil.EXPORT_ROOT + pathFormat.format(new Date()).replaceAll("-", "/");
            String checkTypeFolderPath = fileServerConfig.getFtpHome() + subFolder + "/表单模板" + File.separator + checkTypeName;
            if (!new File(checkTypeFolderPath).exists()) {
                new File(checkTypeFolderPath).mkdirs();
            } else {
                //删除当前表单模板类型下的所有文件夹及文件
                File childFile = new File(checkTypeFolderPath);
                File[] childFiles = childFile.listFiles();
                for (File singleFile : childFiles) {
                    if (singleFile.isDirectory()) {
                        //递归
                        FileOperator.doDeleteFile(singleFile);
                    } else {
                        singleFile.delete();
                    }
                }
                //删除压缩的检查表类型
                File historyZipFile = new File(checkTypeFolderPath + ".zip");
                if (historyZipFile.exists()) {
                    historyZipFile.delete();
                }
            }
            for (Map checkMap : checkTempList) {
                String checkName = CommonTools.Obj2String(checkMap.get("C_NAME_" + checkTempBM.getId()));
                String checkTempId = CommonTools.Obj2String(checkMap.get("ID"));
                exportCheckTempFromOracle(checkTempId, checkName, true, checkTypeFolderPath);
            }
            FileOperator.zip(checkTypeFolderPath, checkTypeFolderPath + ".zip", "");
//            ZipFileUtils zipFileUtils = new ZipFileUtils(checkTypeFolderPath, checkTypeFolderPath);
//            zipFileUtils.zip();
            try {
                //通知浏览器解析时使用的码表
                response.setContentType("aplication/octet-stream;charset=UTF-8");
                //通知浏览器当前服务器发送的数据格式
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(checkTypeName + ".zip", "UTF-8"));
                BufferedInputStream in = new BufferedInputStream(new FileInputStream(checkTypeFolderPath + ".zip"));
                BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
                byte[] buffer = new byte[in.available()];
                int len = 0;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
                in.close();
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void fillTableHeaderAndTail(String checkTempId, Excel excel) {
        IBusinessModel cellBussinessModel = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_CELL, schemaId, EnumInter.BusinessModelEnum.Table);
        cellBussinessModel.setReserve_filter(" and T_CHECK_TEMP_" + schemaId + "_ID = " + checkTempId + " and C_IS_HEADER_" + cellBussinessModel.getId() + " is not null");
        List<Map> cellList = orientSqlEngine.getBmService().createModelQuery(cellBussinessModel).orderAsc("ID").list();
        if (cellList != null && cellList.size() >= 1) {
            excel.setWorkingSheet(1).sheetName("表头表尾");
            String[] header = new String[]{"行类型", "字段名", "字段类型"};
            excel.row(0).value(header);
            for (int i = 0; i < cellList.size(); i++) {
                Map<String, String> map = cellList.get(i);
                String[] row = transferMapToArray(map, cellBussinessModel);
                excel.row(i + 1).value(row);
            }
            excel.sheet().groupColumn(0, 3);//按列分组
        }
    }

    public String[] transferMapToArray(Map<String, String> map, IBusinessModel cellmodel) {
        String[] strings = new String[3];
        String content = map.get("C_CONTENT_" + cellmodel.getId());
        strings[1] = content;
        String cellType = getDisplayType(map.get("C_CELL_TYPE_" + cellmodel.getId()));
        strings[2] = cellType;
        String isHeader = map.get("C_IS_HEADER_" + cellmodel.getId());
        if ("true".equalsIgnoreCase(isHeader)) {
            strings[0] = "表头";
        } else if ("false".equalsIgnoreCase(isHeader)) {
            strings[0] = "表尾";
        }
        return strings;
    }

    public void delCheckTypeById(String checkTypeId) {
        String tableName = PropertyConstant.CHECK_TYPE;
        String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;
        IBusinessModel bm = businessModelService.getBusinessModelBySName(tableName, schemaId, EnumInter.BusinessModelEnum.Table);
        orientSqlEngine.getBmService().deleteCascade(bm, checkTypeId);

    }

    public AjaxResponseData updateCheckTypeData(String modelId, String checkTypeId, String formData) {
        AjaxResponseData retVal = new AjaxResponseData();
        if (StringUtil.isEmpty(formData)) {
            retVal.setMsg("保存内容为空");
            retVal.setSuccess(false);
            return retVal;
        }
        Map formDataMap = JsonUtil.json2Map(formData);
        String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;
        String sql = "update T_CHECK_TYPE_" + schemaId + " set C_CHECK_NAME_" + modelId + "=?" + " where id=?";
        Map dataMap = (Map) formDataMap.get("fields");
        jdbcTemplate.update(sql, dataMap.get("C_CHECK_NAME_" + modelId), checkTypeId);
        retVal.setMsg("修改成功");
        return retVal;
    }

    public AjaxResponseData<List<FormProductTreeNode>> getProductTreeNodes(String id, String type, String level, String version, String checkTableInstId, String rowNumber, String checkTempId, boolean isInst) {
        List<FormProductTreeNode> retVal = new ArrayList<>();
        IBusinessModel productBM = businessModelService.getBusinessModelBySName(PropertyConstant.PRODUCT_STRUCTURE, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel checkTempInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_TEMP_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel checkRowBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_ROW, schemaId, EnumInter.BusinessModelEnum.Table);

        String choosedProductId = "";
        String refProductId = "";
        if (isInst) {
            checkRowBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_ROW_INST, schemaId, EnumInter.BusinessModelEnum.Table);
            checkTempInstBM.setReserve_filter("AND ID='" + checkTableInstId + "'");
            List<Map<String, Object>> checkInstList = orientSqlEngine.getBmService().createModelQuery(checkTempInstBM).list();
            if (checkInstList.size() > 0) {
                refProductId = CommonTools.Obj2String(checkInstList.get(0).get("C_PRODUCT_ID_" + checkTempInstBM.getId()));
            }
            checkRowBM.setReserve_filter("AND T_CHECK_TEMP_INST_" + schemaId + "_ID='" + checkTempId + "'" + " AND C_ROW_NUMBER_" + checkRowBM.getId() + "='" + rowNumber + "'");
            List<Map> checkRowList = orientSqlEngine.getBmService().createModelQuery(checkRowBM).list();
            if (checkRowList.size() > 0) {
                choosedProductId = CommonTools.Obj2String(checkRowList.get(0).get("C_PRODUCT_ID_" + checkRowBM.getId()));
            }
        } else {
            checkRowBM.setReserve_filter("AND T_CHECK_TEMP_" + schemaId + "_ID='" + checkTempId + "'" + " AND C_ROW_NUMBER_" + checkRowBM.getId() + "='" + rowNumber + "'");
            List<Map> checkRowList = orientSqlEngine.getBmService().createModelQuery(checkRowBM).list();
            if (checkRowList.size() > 0) {
                choosedProductId = CommonTools.Obj2String(checkRowList.get(0).get("C_PRODUCT_ID_" + checkRowBM.getId()));
            }
        }

        Boolean belongVersion = Boolean.valueOf(version);
        switch (type) {
            case ProductStructureNodeType.TYPE_ROOT:
                productBM.setReserve_filter("AND C_PID_" + productBM.getId() + " = '" + id + "'");
                List<Map> productList = orientSqlEngine.getBmService().createModelQuery(productBM).orderAsc("TO_NUMBER(ID)").list();

                if (productList.size() > 0) {
                    for (Map map : productList) {
                        FormProductTreeNode treeNode = new FormProductTreeNode();
                        String productName = CommonTools.Obj2String(map.get("C_NAME_" + productBM.getId()));
                        if (productName.equals("母船") || productName.equals("其它") || productName.equals("携带的作业工具") || productName.equals("舱内携带的作业工具")) {
                            //belongVersion判断产品结构中是否要显示母船、其它 节点
                            if (belongVersion == true) {
                                continue;
                            }
                        }
                        treeNode.setText(CommonTools.Obj2String(map.get("C_NAME_" + productBM.getId())));
                        String productId = CommonTools.Obj2String(map.get("ID"));
                        treeNode.setId(productId);
                        treeNode.setDataId(productId);
                        treeNode.setExpanded(false);
                        treeNode.setType(ProductStructureNodeType.TYPE_ROOT);
                        treeNode.setIcon("app/images/function/数据建模.png");
                        treeNode.setIconCls("icon-function");
                        if (!"".equals(refProductId) && refProductId.equals(productId)) {
                            treeNode.setChecked(true);
                        }
                        if (!"".equals(choosedProductId) && choosedProductId.equals(productId)) {
                            treeNode.setChecked(true);
                        }
                        treeNode.setQtip(CommonTools.Obj2String(map.get("C_NAME_" + productBM.getId())));
                        int levell = Integer.parseInt(level);
                        treeNode.setLevel(++levell);
                        productBM.setReserve_filter("AND C_PID_" + productBM.getId() + " = '" + productId + "'");
                        List<Map> subList = orientSqlEngine.getBmService().createModelQuery(productBM).orderAsc("TO_NUMBER(ID)").list();
                        if (subList.size() == 0) {
                            treeNode.setLeaf(true);
                        }

                        List<FormProductTreeNode> childsList = getChildrenNode(subList, String.valueOf(levell), refProductId, choosedProductId);
                        treeNode.setResults(childsList);
                        retVal.add(treeNode);
                    }
                }
                break;
            default:
                break;
        }
        return new AjaxResponseData<>(retVal);
    }


    public List<FormProductTreeNode> getChildrenNode(List<Map> subList, String level, String refProductId, String choosedProductId) {
        List<FormProductTreeNode> lists = new ArrayList<>();
        IBusinessModel productBM = businessModelService.getBusinessModelBySName(PropertyConstant.PRODUCT_STRUCTURE, schemaId, EnumInter.BusinessModelEnum.Table);
        if (subList != null && subList.size() > 0) {
            for (Map subMap : subList) {
                String productId = CommonTools.Obj2String(subMap.get("ID"));
                FormProductTreeNode pstn = new FormProductTreeNode();
                pstn.setText(CommonTools.Obj2String(subMap.get("C_NAME_" + productBM.getId())));
                pstn.setId(productId);
                pstn.setDataId(productId);
                pstn.setExpanded(false);
                pstn.setType(ProductStructureNodeType.TYPE_ROOT);
                pstn.setIcon("app/images/function/数据建模.png");
                pstn.setIconCls("icon-function");
                int levell = Integer.parseInt(level);
                pstn.setLevel(++levell);
                pstn.setQtip(CommonTools.Obj2String(subMap.get("C_NAME_" + productBM.getId())));
                //是否打勾
                if (!"".equals(refProductId) && refProductId.equals(productId)) {
                    pstn.setChecked(true);
                }
                if (!"".equals(choosedProductId) && choosedProductId.equals(productId)) {
                    pstn.setChecked(true);
                }
                productBM.clearAllFilter();
                productBM.setReserve_filter("AND C_PID_" + productBM.getId() + " = '" + productId + "'");
                List<Map> childsList = orientSqlEngine.getBmService().createModelQuery(productBM).orderAsc("TO_NUMBER(ID)").list();
                if (childsList.size() == 0) {
                    pstn.setLeaf(true);
                }
                pstn.setResults(getChildrenNode(childsList, String.valueOf(levell), refProductId, choosedProductId));
                lists.add(pstn);
            }
        }
        return lists;
    }

    public AjaxResponseData saveChooseProductTree(String treeId, String rowNumber, String checkTempId, boolean isInst) {
        AjaxResponseData retVal = new AjaxResponseData();
        IBusinessModel checkBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_TEMP, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel rowBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_ROW, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel headBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_HEADER, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel cellBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_CELL, schemaId, EnumInter.BusinessModelEnum.Table);
        String modelId = rowBM.getId();
        String checkTempTableName = PropertyConstant.CHECK_TEMP;
        String checkRowTableName = PropertyConstant.CHECK_ROW;
        String checkHeadTableName = PropertyConstant.CHECK_HEADER;
        if (isInst) {
            rowBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_ROW_INST, schemaId, EnumInter.BusinessModelEnum.Table);
            headBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_HEADER_INST, schemaId, EnumInter.BusinessModelEnum.Table);
            cellBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_CELL_INST, schemaId, EnumInter.BusinessModelEnum.Table);
            modelId = rowBM.getId();
            checkTempTableName = PropertyConstant.CHECK_TEMP_INST;
            checkRowTableName = PropertyConstant.CHECK_ROW_INST;
            checkHeadTableName = PropertyConstant.CHECK_HEADER_INST;
        }

        rowBM.setReserve_filter(" AND " + checkTempTableName + "_" + schemaId + "_ID='" + checkTempId + "'" +
                " AND C_ROW_NUMBER_" + modelId + "='" + rowNumber + "'");
        List<Map> rowList = orientSqlEngine.getBmService().createModelQuery(rowBM).list();
        String rowId = CommonTools.Obj2String(rowList.get(0).get("ID"));
        String prodcutId = CommonTools.Obj2String(rowList.get(0).get("C_PRODUCT_ID_" + modelId));

        rowList.get(0).put("C_PRODUCT_ID_" + modelId, treeId);
        orientSqlEngine.getBmService().updateModelData(rowBM, rowList.get(0), rowId);

        cellBM.setReserve_filter(" AND " + checkTempTableName + "_" + schemaId + "_ID='" + checkTempId + "'" +
                " AND " + checkRowTableName + "_" + schemaId + "_ID='" + rowId + "'");
        List<Map<String, Object>> cellList = orientSqlEngine.getBmService().createModelQuery(cellBM).list();
        //查找到所有表头Ids
        String headerIds = "";
        for (Map cellMap : cellList) {
            String headId = CommonTools.Obj2String(cellMap.get(checkHeadTableName + "_" + schemaId + "_ID"));
            headerIds += headId + ",";
        }
        headerIds = headerIds.substring(0, headerIds.length() - 1);
        headBM.setReserve_filter("AND ID IN (" + headerIds + ")");
        List<Map<String, Object>> headList = orientSqlEngine.getBmService().createModelQuery(headBM).list();
        //过滤查找到包含"#"的表头Ids
        List<String> headerList = UtilFactory.newArrayList();
        for (Map headMap : headList) {
            String headerName = CommonTools.Obj2String(headMap.get("C_NAME_" + headBM.getId()));
            String headerId = CommonTools.Obj2String(headMap.get("ID"));
            if (headerName.indexOf("#") == -1) {
                continue;
            }
            headerList.add(headerId);
        }
        for (String headerId : headerList) {
            for (Map cellMap : cellList) {
                String headId = CommonTools.Obj2String(cellMap.get(checkHeadTableName + "_" + schemaId + "_ID"));
                String cellId = CommonTools.Obj2String(cellMap.get("ID"));
                if (headerId.equals(headId)) {
                    cellMap.put("C_PRODUCT_ID_" + cellBM.getId(), treeId);
                    orientSqlEngine.getBmService().updateModelData(cellBM, cellMap, cellId);
                }
            }
        }
        retVal.setSuccess(true);
        retVal.setMsg("保存成功！");
        return retVal;
    }

    public AjaxResponseData getChooseProductTree(String rowNumber, String checkTempId, boolean isInst) {
        AjaxResponseData retVal = new AjaxResponseData();
        IBusinessModel rowBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_ROW, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel productBM = businessModelService.getBusinessModelBySName(PropertyConstant.PRODUCT_STRUCTURE, schemaId, EnumInter.BusinessModelEnum.Table);
        String modelId = rowBM.getId();
        String checkTempTableName = PropertyConstant.CHECK_TEMP;
        if (isInst) {
            rowBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_ROW_INST, schemaId, EnumInter.BusinessModelEnum.Table);
            modelId = rowBM.getId();
            checkTempTableName = PropertyConstant.CHECK_TEMP_INST;
        }
        rowBM.setReserve_filter(" AND " + checkTempTableName + "_" + schemaId + "_ID='" + checkTempId + "'" +
                " AND C_ROW_NUMBER_" + modelId + "='" + rowNumber + "'");
        List<Map> rowList = orientSqlEngine.getBmService().createModelQuery(rowBM).list();
        if (rowList.size() > 0) {
            String prodcutId = CommonTools.Obj2String(rowList.get(0).get("C_PRODUCT_ID_" + modelId));
            String sql = "select c_name_" + productBM.getId() + " from T_PRODUCT_STRUCTURE_" + schemaId + " where id='" + prodcutId + "'";
            List<Map<String, Object>> productList = jdbcTemplate.queryForList(sql);
            if (StringUtil.isEmpty(prodcutId)) {
                retVal.setResults("未选择");
            } else {
                if (productList.size() > 0) {
                    retVal.setResults(productList.get(0).get("c_name_" + productBM.getId()));
                } else {
                    retVal.setResults("未选择");
                }
            }
        } else {
            retVal.setResults("未选择");
        }
        return retVal;
    }


    public AjaxResponseData<Map> getChooseProductNode(String checkTempId, boolean isInst) {
        AjaxResponseData retVal = new AjaxResponseData<>();
        Map resultMap = UtilFactory.newHashMap();
        IBusinessModel rowBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_ROW, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel productBM = businessModelService.getBusinessModelBySName(PropertyConstant.PRODUCT_STRUCTURE, schemaId, EnumInter.BusinessModelEnum.Table);
        String rowModelId = rowBM.getId();
        String checkTempTableName = PropertyConstant.CHECK_TEMP;
        if (isInst) {
            rowBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_ROW_INST, schemaId, EnumInter.BusinessModelEnum.Table);
            rowModelId = rowBM.getId();
            checkTempTableName = PropertyConstant.CHECK_TEMP_INST;
        }
        rowBM.setReserve_filter(" AND " + checkTempTableName + "_" + schemaId + "_ID='" + checkTempId + "'");
        List<Map> rowList = orientSqlEngine.getBmService().createModelQuery(rowBM).list();
        Collections.reverse(rowList);
        if (rowList.size() > 0) {
            for (Map rowMap : rowList) {
                String rowNumber = CommonTools.Obj2String(rowMap.get("C_ROW_NUMBER_" + rowModelId));
                String prodcutId = CommonTools.Obj2String(rowMap.get("C_PRODUCT_ID_" + rowModelId));
                if (StringUtil.isEmpty(prodcutId)) {
                    resultMap.put(rowNumber, "未选择");
                } else {
                    String sql = "select c_name_" + productBM.getId() + " from T_PRODUCT_STRUCTURE_" + schemaId + " where id='" + prodcutId + "'";
                    List<Map<String, Object>> productList = jdbcTemplate.queryForList(sql);
                    if (productList.size() > 0) {
                        resultMap.put(rowNumber, productList.get(0).get("c_name_" + productBM.getId()));
                    } else {
                        resultMap.put(rowNumber, "未选择");
                    }
                }
            }
        }
        retVal.setResults(resultMap);
        return retVal;
    }

    public AjaxResponseData selectChooseProductTree(String checkTempId) {
        AjaxResponseData retVal = new AjaxResponseData();
        IBusinessModel rowBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_ROW, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel productBM = businessModelService.getBusinessModelBySName(PropertyConstant.PRODUCT_STRUCTURE, schemaId, EnumInter.BusinessModelEnum.Table);
        String modelId = rowBM.getId();
        String checkTempTableName = PropertyConstant.CHECK_TEMP;
//        if (isInst) {
//            rowBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_ROW_INST, schemaId, EnumInter.BusinessModelEnum.Table);
//            modelId=rowBM.getId();
//            checkTempTableName=PropertyConstant.CHECK_TEMP_INST;
//        }
        rowBM.setReserve_filter(" AND " + checkTempTableName + "_" + schemaId + "_ID='" + checkTempId + "'");
        List<Map> rowList = orientSqlEngine.getBmService().createModelQuery(rowBM).orderAsc("TO_NUMBER(C_ROW_NUMBER_" + rowBM.getId() + ")").list();
        if (rowList.size() > 0) {
            String prodcutId = CommonTools.Obj2String(rowList.get(0).get("C_PRODUCT_ID_" + modelId));
            String sql = "select c_name_" + productBM.getId() + " from T_PRODUCT_STRUCTURE_" + schemaId + " where id='" + prodcutId + "'";
            List<Map<String, Object>> productList = jdbcTemplate.queryForList(sql);
            if (StringUtil.isEmpty(prodcutId)) {
                retVal.setResults("未选择");
            } else {
                if (productList.size() > 0) {
                    retVal.setResults(productList.get(0).get("c_name_" + productBM.getId()));
                } else {
                    retVal.setResults("未选择");
                }
            }
        } else {
            retVal.setResults("未选择");
        }
        return retVal;
    }

    public AjaxResponseData updateCheckTemp(String modelId, String checkTempId, String formData) {
        AjaxResponseData retVal = new AjaxResponseData();
        IBusinessModel bm = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_TEMP,
                schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel divingTaskBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_TASK, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel checkTempInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_TEMP_INST, schemaId, EnumInter.BusinessModelEnum.Table);

        if (StringUtil.isEmpty(formData)) {
            retVal.setMsg("保存内容为空");
            retVal.setSuccess(false);
            return retVal;
        }
        Map formDataMap = JsonUtil.json2Map(formData);
        Map dataMap = (Map) formDataMap.get("fields");
        String checkTempType = CommonTools.Obj2String(dataMap.get("C_TEMP_TYPE_" + modelId));
        String isRepeatUpload = CommonTools.Obj2String(dataMap.get("C_IS_REPEAT_UPLOAD_" + modelId));
        boolean result = orientSqlEngine.getBmService().updateModelData(bm, dataMap, checkTempId);
        if (result) {
            //修改任务管理下的检查表类型
            String checkTempInstSql = "select c.* from T_CHECK_TEMP_INST_" + schemaId + " c right join (SELECT T.ID FROM T_DIVING_TASK_" + schemaId + " T WHERE T.C_STATE_" + divingTaskBM.getId() + "!='已结束') y on c.T_DIVING_TASK_" + schemaId + "_ID=y.ID where c.c_check_temp_id_" + checkTempInstBM.getId() + "='" + checkTempId + "'";
            List<Map<String, Object>> checkTempInstList = jdbcTemplate.queryForList(checkTempInstSql);
            if (checkTempInstList != null && checkTempInstList.size() > 0) {
                for (Map checkInstMap : checkTempInstList) {
                    String checkTempInstId = checkInstMap.get("ID").toString();
                    checkInstMap.put("C_INS_TYPE_" + checkTempInstBM.getId(), checkTempType);
                    checkInstMap.put("C_IS_REPEAT_UPLOAD_" + checkTempInstBM.getId(), isRepeatUpload);
                    orientSqlEngine.getBmService().updateModelData(checkTempInstBM, checkInstMap, checkTempInstId);
                }
            }
            //修改任务模板管理下的检查表的类型
            String flowTempCheckSql = "SELECT ID,C_INS_TYPE_" + checkTempInstBM.getId() + " FROM T_CHECK_TEMP_INST_" + schemaId + " WHERE 1=1 AND C_CHECK_TEMP_ID_" + checkTempInstBM.getId() + "='" + checkTempId + "'" + " AND T_FLOW_TEMP_TYPE_" + schemaId + "_ID IN(SELECT ID FROM T_FLOW_TEMP_TYPE_" + schemaId + ")";
            checkTempInstList = jdbcTemplate.queryForList(flowTempCheckSql);
            if (checkTempInstList != null && checkTempInstList.size() > 0) {
                for (Map flowCheckInstMap : checkTempInstList) {
                    String checkTempInstId = flowCheckInstMap.get("ID").toString();
                    flowCheckInstMap.put("C_INS_TYPE_" + checkTempInstBM.getId(), checkTempType);
                    flowCheckInstMap.put("C_IS_REPEAT_UPLOAD_" + checkTempInstBM.getId(), isRepeatUpload);
                    orientSqlEngine.getBmService().updateModelData(checkTempInstBM, flowCheckInstMap, checkTempInstId);
                }
            }
            //修改设备周期检查下的检查表实例的类型
            String cycleTempCheckSql = "SELECT ID,C_INS_TYPE_" + checkTempInstBM.getId() + " FROM T_CHECK_TEMP_INST_" + schemaId + " WHERE 1=1 AND C_CHECK_TEMP_ID_" + checkTempInstBM.getId() + "='" + checkTempId + "'" + " AND T_CYCLE_DEVICE_" + schemaId + "_ID IN(SELECT ID FROM T_CYCLE_DEVICE_" + schemaId + ")";
            checkTempInstList = jdbcTemplate.queryForList(cycleTempCheckSql);
            if (checkTempInstList != null && checkTempInstList.size() > 0) {
                for (Map flowCheckInstMap : checkTempInstList) {
                    String checkTempInstId = flowCheckInstMap.get("ID").toString();
                    flowCheckInstMap.put("C_INS_TYPE_" + checkTempInstBM.getId(), checkTempType);
                    flowCheckInstMap.put("C_IS_REPEAT_UPLOAD_" + checkTempInstBM.getId(), isRepeatUpload);
                    orientSqlEngine.getBmService().updateModelData(checkTempInstBM, flowCheckInstMap, checkTempInstId);
                }
            }
            retVal.setMsg("修改成功");
            retVal.setSuccess(true);
        } else {
            retVal.setMsg("修改失败");
            retVal.setSuccess(false);
        }
        return retVal;
    }

    /**
     * 处理重复的
     *
     * @param newId 表单模板id
     */
    private Map<String, Object> handleRepeat(String oldId, String newId, TableEntity excelEntity, List<String> headers) {
        HashMap ret = new HashMap();
        //查询任务模板下的实例id
        IBusinessModel checkTempInstIBusinessModel = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_TEMP_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        checkTempInstIBusinessModel.setReserve_filter("and C_CHECK_TEMP_ID_" + checkTempInstIBusinessModel.getId() + "=" + oldId);
        List<Map> tempInstanceList = orientSqlEngine.getBmService().createModelQuery(checkTempInstIBusinessModel).list();
        //插入新的表格模板、行、列、单元格数据,同时删除旧的
        deleteFormTemplateAndInsertnew(oldId, newId, excelEntity, headers);
        //插入新的表格模板实例、行、列、单元格数据,同时删除旧的
        deleteOldFormInstanceDataAndInsertnew(oldId, newId, tempInstanceList);
        ret.put("success", true);
        ret.put("msg", "导入成功！");
        return ret;
    }

    /**
     * 删除旧的表单模板数据，同时插入新的,表格模板、行、列、以及单元格
     *
     * @param oldId       旧的模板id
     * @param newId       新的模板id
     * @param excelEntity excel数据实体
     * @param headers     excel头
     * @return
     */
    private Map<String, Object> deleteFormTemplateAndInsertnew(String oldId, String newId, TableEntity excelEntity, List<String> headers) {
        IBusinessModel checkTempModel = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_TEMP, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel checkHeaderModel = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_HEADER, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel rowModel = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_ROW, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel cellModel = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_CELL, schemaId, EnumInter.BusinessModelEnum.Table);
        // cellModel.setReserve_filter("and T_CHECK_TEMP_"+schemaId+"_ID="+oldId+" and C_IS_HEADER_"+cellModel.getId()+" is not null");
        //List<Map> cellTailList = orientSqlEngine.getBmService().createModelQuery(cellModel).list();
        cellModel.clearAllFilter();
        checkHeaderModel.setReserve_filter("and T_CHECK_TEMP_" + schemaId + "_ID=" + oldId);
        rowModel.setReserve_filter("and T_CHECK_TEMP_" + schemaId + "_ID=" + oldId);
        cellModel.setReserve_filter("and T_CHECK_TEMP_" + schemaId + "_ID=" + oldId);
        orientSqlEngine.getBmService().delete(checkTempModel, oldId);
        getdeleteIdsAndDelete(checkHeaderModel);
        getdeleteIdsAndDelete(rowModel);
        getdeleteIdsAndDelete(cellModel);
        //插入表头和表尾
   /*     for(Map<String,String> cellHeader:cellTailList){
            cellHeader.put("ID",null);
            cellHeader.put("T_CHECK_TEMP_"+schemaId+"_ID",newId);
            orientSqlEngine.getBmService().insertModelData(cellModel,cellHeader);
        }*/

        insertTableHeaderAndTabletTail(excelEntity, newId);
        Map<String, Object> ret = insertTableDetail(excelEntity, headers, newId);
        return ret;
    }

    /**
     * 插入单元格中的表头和表尾
     *
     * @param entity
     * @param checkTempId
     */
    private void insertTableHeaderAndTabletTail(TableEntity entity, String checkTempId) {
        List<TableEntity> tableEntityList = entity.getSubTableEntityList();
        if (tableEntityList != null) {
            IBusinessModel cellBusinessModel = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_CELL, schemaId, EnumInter.BusinessModelEnum.Table);
            Map<String, String> dataMap = new HashMap();

            for (TableEntity tableEntity : tableEntityList) {
                if (tableEntity != null) {
                    List<DataEntity> dataEntities = tableEntity.getDataEntityList();
                    for (DataEntity dataEntity : dataEntities) {
                        dataMap.clear();
                        dataMap.put("T_CHECK_TEMP_" + schemaId + "_ID", checkTempId);
                        List<FieldEntity> fieldEntityList = dataEntity.getFieldEntityList();
                        String cellCategory = fieldEntityList.get(1).getValue();
                        if ("表头".equals(cellCategory)) {
                            dataMap.put("C_IS_HEADER_" + cellBusinessModel.getId(), "true");
                        } else if ("表尾".equals(cellCategory)) {
                            dataMap.put("C_IS_HEADER_" + cellBusinessModel.getId(), "false");
                        } else {
                            continue;
                        }
                        String cellFieldname = fieldEntityList.get(2).getValue();
                        dataMap.put("C_CONTENT_" + cellBusinessModel.getId(), cellFieldname);
                        String cellType = fieldEntityList.get(3).getValue();
                        cellType = getCellType(cellType);
                        dataMap.put("C_CELL_TYPE_" + cellBusinessModel.getId(), cellType);
                        orientSqlEngine.getBmService().insertModelData(cellBusinessModel, dataMap);
                    }
                }
            }
        }
    }

    /**
     * 获取存入数据库的值
     *
     * @param cellContent
     * @return
     */
    private String getCellType(String cellContent) {
        switch (cellContent) {
            case "对勾":
                return CheckCellInstCellType.TYPE_CHECK;
            case "填写":
                return CheckCellInstCellType.TYPE_FILL;
            case "拍照":
                return CheckCellInstCellType.TYPE_PICTURE;
            case "数字":
                return CheckCellInstCellType.TYPE_SHUZI;
            case "时间":
                return CheckCellInstCellType.TYPE_TIME;
            case "签署":
                return CheckCellInstCellType.TYPE_SIGN;
            case "故障":
                return CheckCellInstCellType.TYPE_TROUBLE;
            case "是否无":
                return CheckCellInstCellType.TYPE_YDN;
            case "经纬度":
                return CheckCellInstCellType.TYPE_JWD;
            default:
                return CheckCellInstCellType.TYPE_KONG;
        }
    }

    /**
     * 根据数据库存的的标识，获取显示类型
     *
     * @param type
     * @return
     */
    public String getDisplayType(String type) {
        switch (type) {
            case CheckCellInstCellType.TYPE_CHECK:
                return "对勾";
            case CheckCellInstCellType.TYPE_FILL:
                return "填写";
            case CheckCellInstCellType.TYPE_PICTURE:
                return "拍照";
            case CheckCellInstCellType.TYPE_SHUZI:
                return "数字";
            case CheckCellInstCellType.TYPE_TIME:
                return "时间";
            case CheckCellInstCellType.TYPE_SIGN:
                return "签署";
            case CheckCellInstCellType.TYPE_TROUBLE:
                return "故障";
            case CheckCellInstCellType.TYPE_YDN:
                return "是否无";
            case CheckCellInstCellType.TYPE_JWD:
                return "经纬度";
            case CheckCellInstCellType.TYPE_KONG:
                return "";
            default:
                return "未知项";
        }
    }

    /**
     * 删除旧的表单模板实例数据，同时插入新的,表格模板、行、列、以及单元格
     *
     * @param oldId
     * @param newId
     * @param tempInstanceList
     */
    private void deleteOldFormInstanceDataAndInsertnew(String oldId, String newId, List<Map> tempInstanceList) {
        IBusinessModel checkTempInstModel = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_TEMP_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel taskModel = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_TASK, schemaId, EnumInter.BusinessModelEnum.Table);
        for (Map<String, String> map : tempInstanceList) {
            String taskId = map.get("T_DIVING_TASK_" + schemaId + "_ID");
            String flowTempId = map.get("T_FLOW_TEMP_TYPE_" + schemaId + "_ID");
            String tempInstanceId = map.get("ID");
            String tableState = map.get("C_CHECK_STATE_" + checkTempInstModel.getId());
            if (StringUtil.isEmpty(taskId) && StringUtil.isNotEmpty(flowTempId)) {
                orientSqlEngine.getBmService().deleteCascade(checkTempInstModel, tempInstanceId);
                handleInsert(map, checkTempInstModel, newId);
            } else if (StringUtil.isEmpty(flowTempId) && StringUtil.isNotEmpty(taskId)) {
                Map<String, String> task = orientSqlEngine.getBmService().createModelQuery(taskModel).findById(taskId);
                if (!"已结束".equals(task.get("C_STATE_" + taskModel.getId())) && "未完成".equals(tableState)) {
                    orientSqlEngine.getBmService().deleteCascade(checkTempInstModel, tempInstanceId);
                    handleInsert(map, checkTempInstModel, newId);
                }
            }
        }
    }

    private void handleInsert(Map<String, String> tempInstance, IBusinessModel checkTempInstIBusinessModel, String newId) {
        tempInstance.put("ID", null);
        tempInstance.put("C_CHECK_TEMP_ID_" + checkTempInstIBusinessModel.getId(), newId);
        String newTempinstanceid = orientSqlEngine.getBmService().insertModelData(checkTempInstIBusinessModel, tempInstance);
        StringBuilder headerSql = new StringBuilder();
        headerSql.append("select * from T_CHECK_HEADER_" + schemaId).append(" where 1=1").append(" and T_CHECK_TEMP_" + schemaId + "_ID =?").append("order by ID ASC");
        List<Map<String, Object>> headerList = metaDAOFactory.getJdbcTemplate().queryForList(headerSql.toString(), newId);
        taskPrepareMgrBusiness.importCheckList(headerList, newTempinstanceid, newId);
    }


    private void getdeleteIdsAndDelete(IBusinessModel businessModel) {
        String deleteStrIds = "";
        List<Map> list = orientSqlEngine.getBmService().createModelQuery(businessModel).list();
        for (Map<String, String> map : list) {
            deleteStrIds = deleteStrIds + map.get("ID") + ",";
        }
        if (StringUtil.isNotEmpty(deleteStrIds)) {
            deleteStrIds = deleteStrIds.substring(0, deleteStrIds.length() - 1);
            orientSqlEngine.getBmService().delete(businessModel, deleteStrIds);
        }
    }

    /**
     * 根据产品结构名称，获取产品结构id
     *
     * @param productname
     * @return
     */
    public Map getProductIdbyProductName(String productname) {
        IBusinessModel productStructModel = businessModelService.getBusinessModelBySName(PropertyConstant.PRODUCT_STRUCTURE, schemaId, EnumInter.BusinessModelEnum.Table);
        productStructModel.setReserve_filter(" and C_NAME_" + productStructModel.getId() + " =" + "\'" + productname + "\'");
        List<Map<String, String>> productStructList = orientSqlEngine.getBmService().createModelQuery(productStructModel).list();
        return productStructList.size() == 0 ? null : productStructList.get(0);
    }

    /**
     * 插入表单模板详情，行、列、单元格
     *
     * @param excelEntity
     * @param headers
     * @param tempId
     * @return
     */
    private Map<String, Object> insertTableDetail(TableEntity excelEntity, List<String> headers, String tempId) {
        Map<String, Object> retVal = UtilFactory.newHashMap();
        String tempTableName = PropertyConstant.CHECK_TEMP;
        String headerTableName = PropertyConstant.CHECK_HEADER;
        String rowTableName = PropertyConstant.CHECK_ROW;
        String cellTableName = PropertyConstant.CHECK_CELL;

        IBusinessModel headerBM = businessModelService.getBusinessModelBySName(headerTableName, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel rowBM = businessModelService.getBusinessModelBySName(rowTableName, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel cellBM = businessModelService.getBusinessModelBySName(cellTableName, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel productStructBm = businessModelService.getBusinessModelBySName(PropertyConstant.PRODUCT_STRUCTURE, schemaId, EnumInter.BusinessModelEnum.Table);
        //数据库中插入表头
        //数据库中插入表头
        List<String> headerIds = UtilFactory.newArrayList();
        for (String header : headers) {
            if (!PropertyConstant.EXCEL_STRUCT_HEADER.equals(header)) {
                Map<String, String> data = UtilFactory.newHashMap();
                data.put("C_NAME_" + headerBM.getId(), header);
                data.put("T_CHECK_TEMP_" + schemaId + "_ID", tempId);
                String headerId = orientSqlEngine.getBmService().insertModelData(headerBM, data);
                headerIds.add(headerId);
            }
        }

        //数据库中插入行
        List<DataEntity> dataEntities = excelEntity.getDataEntityList();

        for (DataEntity dataEntity : dataEntities) {
            List<FieldEntity> fieldEntities = dataEntity.getFieldEntityList();
            String rowValue = dataEntity.getPkVal();
            if (rowValue == null) {
                continue;
            }
            int fieldEntitiesSize = fieldEntities.size();
            String productStructId = "";
            Map<String, String> rowMap = UtilFactory.newHashMap();
            //如果最后一列是产品结构
            if (headers.get(headers.size() - 1).equals(PropertyConstant.EXCEL_STRUCT_HEADER)) {
                String productStructName = fieldEntities.get(headers.size()).getValue();
                Map<String, String> productStruct = getProductIdbyProductName(productStructName);
                if (productStruct == null) {
                    throw new DSException("请检查", "未查询到文件名为：" + filename + "中，" + productStructName + " 相关结构产品，请检查第" + (Integer.parseInt(rowValue) + 1) + "行");
                }
                String productStructType = productStruct.get("C_TYPE_" + productStructBm.getId());
                if (StringUtil.isEmpty(productStructType)) {
                    throw new DSException("请检查", "文件名为：" + filename + "中，" + productStructName + " 产品结构不能选择，请更改,第" + (Integer.parseInt(rowValue) + 1) + "行");
                }
                productStructId = productStruct.get("ID");
                fieldEntitiesSize = fieldEntitiesSize - 1;
            }
            if (StringUtil.isNotEmpty(productStructId)) {
                rowMap.put("C_PRODUCT_ID_" + rowBM.getId(), productStructId);
            }

            rowMap.put("C_ROW_NUMBER_" + rowBM.getId(), rowValue);
            rowMap.put(tempTableName + "_" + schemaId + "_ID", tempId);
            String rowId = orientSqlEngine.getBmService().insertModelData(rowBM, rowMap);
            //数据库中插入单元格数据
            String checkJoin = "";
            int val = 0;

            for (int i = 0; i < fieldEntitiesSize; i++) {
                FieldEntity fieldEntity = fieldEntities.get(i);
                if (fieldEntity.getIsKey() == 1) {
                    continue;
                }
                String headerId = headerIds.get(i - 1);
                String value = fieldEntity.getValue();
                String name = fieldEntity.getName();
//                if (headers.get(i-1).equals(name)){
//                    if (value.contains(".")){
//                        value=value.split(".")[0];
//                    }
//                }

                Map<String, String> cellMap = UtilFactory.newHashMap();
                if (headers.get(i - 1).equals(name) && name.contains("#")) {
                    if (value.equals("对勾")) {
                        cellMap.put("C_CONTENT_" + cellBM.getId(), value);
                        cellMap.put(headerTableName + "_" + schemaId + "_ID", headerId);
                        cellMap.put(rowTableName + "_" + schemaId + "_ID", rowId);
                        cellMap.put("C_CELL_TYPE_" + cellBM.getId(), "#1");
                        //主要是方便获取单元格，不需要多次查询数据库
                        cellMap.put(tempTableName + "_" + schemaId + "_ID", tempId);
                    } else if (value.equals("填写")) {
                        cellMap.put("C_CONTENT_" + cellBM.getId(), value);
                        cellMap.put(headerTableName + "_" + schemaId + "_ID", headerId);
                        cellMap.put(rowTableName + "_" + schemaId + "_ID", rowId);
                        cellMap.put("C_CELL_TYPE_" + cellBM.getId(), "#2");
                        //主要是方便获取单元格，不需要多次查询数据库
                        cellMap.put(tempTableName + "_" + schemaId + "_ID", tempId);
                    } else if (value.equals("时间")) {
                        cellMap.put("C_CONTENT_" + cellBM.getId(), value);
                        cellMap.put(headerTableName + "_" + schemaId + "_ID", headerId);
                        cellMap.put(rowTableName + "_" + schemaId + "_ID", rowId);
                        cellMap.put("C_CELL_TYPE_" + cellBM.getId(), "#6");
                        //主要是方便获取单元格，不需要多次查询数据库
                        cellMap.put(tempTableName + "_" + schemaId + "_ID", tempId);
                    } else if (value.equals("签署")) {
                        cellMap.put("C_CONTENT_" + cellBM.getId(), value);
                        cellMap.put(headerTableName + "_" + schemaId + "_ID", headerId);
                        cellMap.put(rowTableName + "_" + schemaId + "_ID", rowId);
                        cellMap.put("C_CELL_TYPE_" + cellBM.getId(), "#8");
                        //主要是方便获取单元格，不需要多次查询数据库
                        cellMap.put(tempTableName + "_" + schemaId + "_ID", tempId);
                    } else if (value.equals("拍照")) {
                        cellMap.put("C_CONTENT_" + cellBM.getId(), value);
                        cellMap.put(headerTableName + "_" + schemaId + "_ID", headerId);
                        cellMap.put(rowTableName + "_" + schemaId + "_ID", rowId);
                        cellMap.put("C_CELL_TYPE_" + cellBM.getId(), "#3");
                        //主要是方便获取单元格，不需要多次查询数据库
                        cellMap.put(tempTableName + "_" + schemaId + "_ID", tempId);
                    } else if (value.equals("故障")) {
                        cellMap.put("C_CONTENT_" + cellBM.getId(), value);
                        cellMap.put(headerTableName + "_" + schemaId + "_ID", headerId);
                        cellMap.put(rowTableName + "_" + schemaId + "_ID", rowId);
                        cellMap.put("C_CELL_TYPE_" + cellBM.getId(), "#9");
                        //主要是方便获取单元格，不需要多次查询数据库
                        cellMap.put(tempTableName + "_" + schemaId + "_ID", tempId);
                    } else if (value.equals("是否无")) {
                        cellMap.put("C_CONTENT_" + cellBM.getId(), value);
                        cellMap.put(headerTableName + "_" + schemaId + "_ID", headerId);
                        cellMap.put(rowTableName + "_" + schemaId + "_ID", rowId);
                        cellMap.put("C_CELL_TYPE_" + cellBM.getId(), "#10");
                        //主要是方便获取单元格，不需要多次查询数据库
                        cellMap.put(tempTableName + "_" + schemaId + "_ID", tempId);
                    } else if (value.equals("经纬度")) {
                        cellMap.put("C_CONTENT_" + cellBM.getId(), value);
                        cellMap.put(headerTableName + "_" + schemaId + "_ID", headerId);
                        cellMap.put(rowTableName + "_" + schemaId + "_ID", rowId);
                        cellMap.put("C_CELL_TYPE_" + cellBM.getId(), "#11");
                        //主要是方便获取单元格，不需要多次查询数据库
                        cellMap.put(tempTableName + "_" + schemaId + "_ID", tempId);
                    } else if (value.equals("数字")) {
                        cellMap.put("C_CONTENT_" + cellBM.getId(), value);
                        cellMap.put(headerTableName + "_" + schemaId + "_ID", headerId);
                        cellMap.put(rowTableName + "_" + schemaId + "_ID", rowId);
                        cellMap.put("C_CELL_TYPE_" + cellBM.getId(), "#4");
                        //主要是方便获取单元格，不需要多次查询数据库
                        cellMap.put(tempTableName + "_" + schemaId + "_ID", tempId);
                    } else {
                        //value为空或者是value为识别不了的单元格类型
                        cellMap.put("C_CONTENT_" + cellBM.getId(), "---");
                        cellMap.put(headerTableName + "_" + schemaId + "_ID", headerId);
                        cellMap.put(rowTableName + "_" + schemaId + "_ID", rowId);
                        //5表示不填值的
                        cellMap.put("C_CELL_TYPE_" + cellBM.getId(), "#5");
                        //主要是方便获取单元格，不需要多次查询数据库
                        cellMap.put(tempTableName + "_" + schemaId + "_ID", tempId);
                    }
//                        checkJoin=checkJoin.substring(0,checkJoin.length()-1);
//                        boolean split=true;

                    if (val == 0) {
                        if (!"".equals(checkJoin)) {
                            checkJoin = checkJoin.substring(0, checkJoin.length() - 1);
                        }
                    }
                    cellMap.put("C_CHECK_JOIN_" + cellBM.getId(), checkJoin);
                    orientSqlEngine.getBmService().insertModelData(cellBM, cellMap);
                    val++;
                } else if (headers.get(i - 1).equals(name) && name.indexOf("#") == -1) {
                    if ("".equals(value)) {
                        value = "---";
                    }
                    checkJoin += value + "-";
                    cellMap.put("C_CONTENT_" + cellBM.getId(), value);
                    cellMap.put(headerTableName + "_" + schemaId + "_ID", headerId);
                    cellMap.put(rowTableName + "_" + schemaId + "_ID", rowId);
                    cellMap.put("C_CELL_TYPE_" + cellBM.getId(), "#0");
                    //主要是方便获取单元格，不需要多次查询数据库
                    cellMap.put(tempTableName + "_" + schemaId + "_ID", tempId);
                    if (StringUtil.isNotEmpty(productStructId)) {
                        cellMap.put("C_PRODUCT_ID_" + cellBM.getId(), productStructId);
                    }
                    orientSqlEngine.getBmService().insertModelData(cellBM, cellMap);
                    continue;
                }

            }
        }
        retVal.put("success", true);
        retVal.put("msg", "导入成功！");
        return retVal;
    }

    public AjaxResponseData<FlowPostData> getHistoryCheckInstHead(String cellId) {
        AjaxResponseData retVal = new AjaxResponseData();
        IBusinessModel checkCellBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_CELL,
                schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel checkHeaderBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_HEADER,
                schemaId, EnumInter.BusinessModelEnum.Table);
        Map cellMap=orientSqlEngine.getBmService().createModelQuery(checkCellBM).findById(cellId);
        String checkTempId = CommonTools.Obj2String(cellMap.get("T_CHECK_TEMP_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID"));
        String headerId=CommonTools.Obj2String(cellMap.get("T_CHECK_HEADER_"+PropertyConstant.WEI_BAO_SCHEMA_ID+"_ID"));
        checkHeaderBM.setReserve_filter("AND T_CHECK_TEMP_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID='" + checkTempId + "'");
        List<Map<String, Object>> checkHeaderList = orientSqlEngine.getBmService().createModelQuery(checkHeaderBM).orderAsc("TO_NUMBER(ID)").list();
        FlowPostData flowPostData = new FlowPostData();
        List<Field> fieldList = initFields(checkHeaderList, checkHeaderBM, headerId);
        List<Column> columnList = initColumn(checkHeaderList, checkHeaderBM, headerId);
//        List<Map> content = initContent(postBM, divingTaskBM, hangciId);
        flowPostData.setFields(fieldList);
        flowPostData.setColumns(columnList);
//        flowPostData.setContent(content);
        retVal.setResults(flowPostData);
        return retVal;
    }

    private List<Field> initFields(List<Map<String, Object>> checkHeaderList, IBusinessModel headerBM, String headerId) {
        List<Field> fieldList = new ArrayList<>();
        int count = 0;
        boolean isSkipOut = false;
        if (checkHeaderList != null && checkHeaderList.size() > 0) {
            for (int i = 0; i < checkHeaderList.size(); i++) {
                if (isSkipOut) {
                    break;
                }
                if (headerId.equals(CommonTools.Obj2String(checkHeaderList.get(i).get("ID")))) {
                    isSkipOut = true;
                }
                Field field = new Field();
                field.setName(String.valueOf(i));
                fieldList.add(field);
                count = i;
            }
            count++;
            fieldList.add(new Field(String.valueOf(count)));
            fieldList.add(new Field(String.valueOf(count + 1)));
        }
        return fieldList;
    }

    private List<Column> initColumn(List<Map<String, Object>> checkHeaderList, IBusinessModel headerBM, String headerId) {
        List<Column> columnList = new ArrayList<>();
        if (checkHeaderList != null && checkHeaderList.size() > 0) {
            int count = 0;
            boolean isSkipOut = false;
            for (int i = 0; i < checkHeaderList.size(); i++) {
                if (isSkipOut) {
                    break;
                }
                if (headerId.equals(CommonTools.Obj2String(checkHeaderList.get(i).get("ID")))) {
                    isSkipOut = true;
                }
                Column column = new Column();
                column.setFlex(2);
                column.setWidth("auto");
                String columnName = (String) checkHeaderList.get(i).get("C_NAME_" + headerBM.getId());
                if (columnName.indexOf("#") == 0) {
                    columnName = columnName.substring(1);
                }
                column.setHeader(columnName);
                column.setDataIndex(String.valueOf(i));
                columnList.add(column);
                count = i;
            }
            count++;
            Column checkTimeCol = new Column();
            checkTimeCol.setFlex(2);
            checkTimeCol.setWidth("auto");
            checkTimeCol.setDataIndex(String.valueOf(count));
            checkTimeCol.setHeader("检查时间");
            columnList.add(checkTimeCol);
            Column divingTaskCol = new Column();
            divingTaskCol.setFlex(2);
            divingTaskCol.setWidth("auto");
            divingTaskCol.setDataIndex(String.valueOf(count + 1));
            divingTaskCol.setHeader("所属任务");
            columnList.add(divingTaskCol);
        }
        return columnList;
    }

    public ExtGridData<Map<String, Object>> getHistoryCheckInstContent(Integer page, Integer limit, String cellId, int columnLength, String checkTempName, @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                                       @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) throws Exception {
        ExtGridData<Map<String, Object>> retVal = new ExtGridData<>();
        IBusinessModel checkCellBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_CELL,
                schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel checkHeaderBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_HEADER,
                schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel checkRowBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_ROW,
                schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel checkRowInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_ROW_INST,
                schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel checkCellInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_CELL_INST,
                schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel cellInstDataBM = businessModelService.getBusinessModelBySName(PropertyConstant.CELL_INST_DATA,
                schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel divingTaskBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_TASK,
                schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel checkTempInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_TEMP_INST,
                schemaId, EnumInter.BusinessModelEnum.Table);
        List checkInstContentList = new LinkedList();
        List<Map<String, Object>> checkInstList = getCheckInstList(cellId, checkTempName, startDate, endDate);
        if (checkInstList != null && checkInstList.size() > 0) {
            for (Map checkInstMap : checkInstList) {
                String checkInstId = checkInstMap.get("ID").toString();
                String checkRowInstId = checkInstMap.get("rowInstId").toString();
                String checkHeadInstId = checkInstMap.get("headerInstId").toString();
                String taskName = CommonTools.Obj2String(checkInstMap.get("C_TASK_NAME_" + divingTaskBM.getId()));
                String checkTime = CommonTools.Obj2String(checkInstMap.get("C_CHECK_TIME_" + checkTempInstBM.getId()));
                StringBuilder sql = new StringBuilder();
                //找到检查单元格内容
                sql.append("select t.*,d.c_content_" + cellInstDataBM.getId() + " as cellData from T_CHECK_CELL_INST_" + PropertyConstant.WEI_BAO_SCHEMA_ID);
                sql.append(" t left join T_CELL_INST_DATA_" + PropertyConstant.WEI_BAO_SCHEMA_ID);
                sql.append(" d on t.id=d.T_CHECK_CELL_INST_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID");
                sql.append(" where t.T_CHECK_TEMP_INST_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID='" + checkInstId + "'");
                sql.append(" and t.T_CHECK_ROW_INST_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID='" + checkRowInstId + "'");
                sql.append(" order by t.T_CHECK_TEMP_INST_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID,t.T_CHECK_HEADER_INST_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID ASC");


//                sql.append("select t.*,d.c_content_" + cellInstDataBM.getId() + " as cellData from T_CHECK_CELL_INST_" + PropertyConstant.WEI_BAO_SCHEMA_ID);
//                sql.append(" t left join T_CELL_INST_DATA_" + PropertyConstant.WEI_BAO_SCHEMA_ID);
//                sql.append(" d on t.id=d.T_CHECK_CELL_INST_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID");
//                sql.append(" where t.T_CHECK_TEMP_INST_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID='" + checkInstId + "'");
//                sql.append(" and t.T_CHECK_ROW_INST_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID='" + checkRowInstId + "'");
//                sql.append(" order by t.T_CHECK_TEMP_INST_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID,t.T_CHECK_HEADER_INST_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID ASC");
//
                String cellInstSql = sql.toString();
                List<Map<String, Object>> checkInstCellList = jdbcTemplate.queryForList(cellInstSql);
                Map checkCellInstContentMap = UtilFactory.newHashMap();
                if (checkInstCellList != null && checkInstCellList.size() > 0) {
                    int count = 0;
                    boolean isSkipCell=false;
                    for (int i = 0; i < checkInstCellList.size(); i++) {
                        Map checkCellInstMap = checkInstCellList.get(i);
                        String content = CommonTools.Obj2String(checkCellInstMap.get("C_CONTENT_" + checkCellInstBM.getId()));
                        String cellType = CommonTools.Obj2String(checkCellInstMap.get("C_CELL_TYPE_" + checkCellInstBM.getId()));
                        String headerInstId=CommonTools.Obj2String(checkCellInstMap.get("T_CHECK_HEADER_INST_"+schemaId+"_ID"));
                        if (CheckCellInstCellType.TYPE_CHECK.equals(cellType) || CheckCellInstCellType.TYPE_FILL.equals(cellType) ||
                                CheckCellInstCellType.TYPE_SHUZI.equals(cellType) || CheckCellInstCellType.TYPE_TIME.equals(cellType) ||
                                CheckCellInstCellType.TYPE_YDN.equals(cellType) || CheckCellInstCellType.TYPE_JWD.equals(cellType)) {
                            content = CommonTools.Obj2String(checkCellInstMap.get("cellData"));
                        }
                         if (isSkipCell){
                             break;
                         }
                         if (headerInstId.equals(checkHeadInstId)){
                             isSkipCell=true;
                         }
//                        if (i == (columnLength - 2)) {
//                            break;
//                        }
                        checkCellInstContentMap.put(i, content);
                        count = i;
                    }
                    count++;
                    checkCellInstContentMap.put(count, checkTime);
                    checkCellInstContentMap.put(count + 1, taskName);
                    checkInstContentList.add(checkCellInstContentMap);
                }
            }
        }
        retVal.setTotalProperty(checkInstContentList.size());
        retVal.setResults(PageUtil.page(checkInstContentList, page, limit));
        return retVal;
    }

    private List<Map<String, Object>> getCheckInstList(String cellId, String checkTempName, @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        IBusinessModel checkCellBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_CELL,
                schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel checkRowBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_ROW,
                schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel checkRowInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_ROW_INST,
                schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel divingTaskBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_TASK,
                schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel checkTempInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_TEMP_INST,
                schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel checkTempHeaderBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_HEADER,
                schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel checkTempInstHeaderBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_HEADER_INST,
                schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel checkCellInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_CELL_INST,
                schemaId, EnumInter.BusinessModelEnum.Table);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map checkCellMap = orientSqlEngine.getBmService().createModelQuery(checkCellBM).findById(cellId);
        String checkTempId = CommonTools.Obj2String(checkCellMap.get("T_CHECK_TEMP_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID"));
        String checkItemJoin = CommonTools.Obj2String(checkCellMap.get("C_CHECK_JOIN_" + checkCellBM.getId()));
        String checkHeaderId = CommonTools.Obj2String(checkCellMap.get("T_CHECK_HEADER_" + schemaId + "_ID"));
        String headerName = orientSqlEngine.getBmService().createModelQuery(checkTempHeaderBM).findById(checkHeaderId).get("C_NAME_" + checkTempHeaderBM.getId());

//        String rowId = CommonTools.Obj2String(checkCellMap.get("T_CHECK_ROW_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID"));
//        checkRowBM.setReserve_filter("AND ID='" + rowId + "' AND T_CHECK_TEMP_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID='" + checkTempId + "'");
//        List<Map> checkTempRowList = orientSqlEngine.getBmService().createModelQuery(checkRowBM).list();
//        String rowNumber = checkTempRowList.get(0).get("C_ROW_NUMBER_" + checkRowBM.getId()).toString();

        StringBuilder stringBuilder = new StringBuilder();
        //先找出行实例Id以及表头实例Id
        stringBuilder.append("select distinct t.*,r.T_CHECK_ROW_INST_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID AS rowInstId,d.C_TASK_NAME_" + divingTaskBM.getId() + ",h.id as headerInstId,h.c_name_" + checkTempInstHeaderBM.getId() + " from T_CHECK_TEMP_INST_" + PropertyConstant.WEI_BAO_SCHEMA_ID + " t left join T_CHECK_CELL_INST_" + PropertyConstant.WEI_BAO_SCHEMA_ID + " r on t.id=r.T_CHECK_TEMP_INST_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID");
        stringBuilder.append(" left join T_DIVING_TASK_" + PropertyConstant.WEI_BAO_SCHEMA_ID + " d on t.T_DIVING_TASK_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID=d.ID");
        stringBuilder.append(" left join T_CHECK_HEADER_INST_" + PropertyConstant.WEI_BAO_SCHEMA_ID + " h on t.id=h.T_CHECK_TEMP_INST_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID");
        stringBuilder.append(" where t.C_NAME_" + checkTempInstBM.getId() + " like '%" + checkTempName + "%' AND h.c_name_" + checkTempInstHeaderBM.getId() + " LIKE '%" + headerName + "%'");
        stringBuilder.append(" AND t.C_CHECK_STATE_" + checkTempInstBM.getId() + "='已上传'");
        stringBuilder.append(" AND r.C_CHECK_JOIN_" + checkCellInstBM.getId() + "='" + checkItemJoin + "'");

//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("select t.*,r.ID AS rowInstId,d.C_TASK_NAME_" + divingTaskBM.getId() + " from T_CHECK_TEMP_INST_" + PropertyConstant.WEI_BAO_SCHEMA_ID + " t left join T_CHECK_ROW_INST_" + PropertyConstant.WEI_BAO_SCHEMA_ID + " r on t.id=r.T_CHECK_TEMP_INST_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID");
//        stringBuilder.append(" left join T_DIVING_TASK_" + PropertyConstant.WEI_BAO_SCHEMA_ID + " d on t.T_DIVING_TASK_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID=d.ID");
//        stringBuilder.append(" where t.C_CHECK_TEMP_ID_" + checkTempInstBM.getId() + "='" + checkTempId + "' AND r.C_ROW_NUMBER_");
//        stringBuilder.append(checkRowInstBM.getId() + "='" + rowNumber + "'");
//        stringBuilder.append(" AND t.C_CHECK_STATE_" + checkTempInstBM.getId() + "='已上传'");
        if (startDate != null && endDate != null) {
            stringBuilder.append(" AND to_date(t.C_CHECK_TIME_" + checkTempInstBM.getId()).append(",'yyyy-mm-dd hh24:mi:ss')").append(">=")
                    .append("to_date('").append(sdf.format(startDate)).append("','yyyy-mm-dd hh24:mi:ss')");
            stringBuilder.append(" AND to_date(t.C_CHECK_TIME_" + checkTempInstBM.getId()).append(",'yyyy-mm-dd hh24:mi:ss')").append("<=")
                    .append("to_date('").append(sdf.format(endDate)).append("','yyyy-mm-dd hh24:mi:ss')");
        } else if (startDate != null) {
            stringBuilder.append(" AND to_date(t.C_CHECK_TIME_" + checkTempInstBM.getId()).append(",'yyyy-mm-dd hh24:mi:ss')").append(">=")
                    .append("to_date('").append(sdf.format(startDate)).append("','yyyy-mm-dd hh24:mi:ss')");
        } else if (endDate != null) {
            stringBuilder.append(" AND to_date(t.C_CHECK_TIME_" + checkTempInstBM.getId()).append(",'yyyy-mm-dd hh24:mi:ss')").append("<=")
                    .append("to_date('").append(sdf.format(endDate)).append("','yyyy-mm-dd hh24:mi:ss')");
        }
//        String checkInstSql = "select t.* from T_CHECK_TEMP_INST_" + PropertyConstant.WEI_BAO_SCHEMA_ID + " t left join T_CHECK_ROW_INST_" + PropertyConstant.WEI_BAO_SCHEMA_ID + " r on t.id=r.T_CHECK_TEMP_INST_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID where t.C_CHECK_TEMP_ID_" +
//                checkTempInstBM.getId() + "='" + checkTempId + "' AND r.C_ROW_NUMBER_" +
//                checkRowInstBM.getId() + "='"+rowNumber+"'";
        stringBuilder.append(" order by t.C_CHECK_TIME_"+checkTempInstBM.getId() +" DESC");
        String checkInstSql = stringBuilder.toString();
        List<Map<String, Object>> checkInstList = jdbcTemplate.queryForList(checkInstSql);
        return checkInstList;
    }

    public String exportHistoryCheckItemData(boolean exportAll, String filters, String cellId, String columns, String checkTempName) {
        IBusinessModel divingTaskBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_TASK,
                schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel cellInstDataBM = businessModelService.getBusinessModelBySName(PropertyConstant.CELL_INST_DATA,
                schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel checkTempInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_TEMP_INST,
                schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel checkCellInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_CELL_INST,
                schemaId, EnumInter.BusinessModelEnum.Table);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Excel excel = new Excel();
        excel.setWorkingSheet(0).sheetName(checkTempName + "-历史检查数据");
        if (exportAll) {
            Map filterMap = UtilFactory.newHashMap();
            if (!"".equals(filters) && filters != null) {
//                filterMap = GsonUtil.toMap(filters);
                //json字符串对象转为Map对象
                filterMap = JSON.parseObject(filters, Map.class);
            }
            List<Column> columnsList = UtilFactory.newArrayList();
            if (!"".equals(columns) && columns != null) {
                columnsList = GsonUtil.toList(columns, Column.class);
            }
            Date startDate = null;
            Date endDate = null;
            if (filterMap != null && filterMap.size() > 0) {
                for (Object key : filterMap.keySet()) {
                    Object value = filterMap.get(key);
                    try {
                        if (key.equals("startDate") && !StringUtil.isEmpty(CommonTools.Obj2String(value))) {
                            startDate = sdf.parse(value.toString());
                        } else if (key.equals("endDate") && !StringUtil.isEmpty(CommonTools.Obj2String(value))) {
                            endDate = sdf.parse(value.toString());
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
            List<Map<String, Object>> checkInstList = getCheckInstList(cellId, checkTempName, startDate, endDate);
            exportHistoryCheckInfoData(excel, columnsList, checkInstList, divingTaskBM, cellInstDataBM, checkTempInstBM, checkCellInstBM);
        }
        String folder = FtpFileUtil.getRelativeUploadPath(FtpFileUtil.EXPORT_ROOT + File.separator + "历史检查数据");
        FtpFileUtil.makeDir(fileServerConfig.getFtpHome(), folder);
        String fileName = checkTempName + "-历史检查数据.xls";
        String finalFileName = FtpFileUtil.getOnlyFileName(fileName);
        excel.saveExcel(fileServerConfig.getFtpHome() + folder + finalFileName);
        return fileServerConfig.getFtpHome() + folder + finalFileName;
    }

    public void exportHistoryCheckInfoData(Excel excel, List<Column> columnsList, List<Map<String, Object>> checkInstList, IBusinessModel divingTaskBM, IBusinessModel cellInstDataBM, IBusinessModel checkTempInstBM, IBusinessModel checkCellInstBM) {
        final int[] columnIndex = {0};
        for (Column column : columnsList) {
            String columnName = column.getHeader();
            excel.column(columnIndex[0]).autoWidth().borderFull(BorderStyle.DASH_DOT, Color.BLACK).align(Align.CENTER);
            excel.cell(0, columnIndex[0])//选择第5行，但忽略第1个单元格，从第2个单元格开始操作
                    .value(columnName);
            columnIndex[0]++;
        }
        final int[] row1 = {1};
        if (checkInstList != null && checkInstList.size() > 0) {
            for (Map checkInstMap : checkInstList) {
                String checkInstId = checkInstMap.get("ID").toString();
                String checkRowInstId = checkInstMap.get("rowInstId").toString();
                String taskName = CommonTools.Obj2String(checkInstMap.get("C_TASK_NAME_" + divingTaskBM.getId()));
                String checkTime = CommonTools.Obj2String(checkInstMap.get("C_CHECK_TIME_" + checkTempInstBM.getId()));
                StringBuilder sql = new StringBuilder();
//                sql.append("select t.*,d.c_content_" + cellInstDataBM.getId() + " as cellData from T_CHECK_CELL_INST_" + PropertyConstant.WEI_BAO_SCHEMA_ID);
//                sql.append(" t left join T_CELL_INST_DATA_" + PropertyConstant.WEI_BAO_SCHEMA_ID);
//                sql.append(" d on t.id=d.T_CHECK_CELL_INST_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID");
//                sql.append(" where t.T_CHECK_TEMP_INST_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID='" + checkInstId + "'");
//                sql.append(" and t.T_CHECK_ROW_INST_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID='" + checkRowInstId + "'");
//                sql.append(" order by t.T_CHECK_TEMP_INST_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID,t.T_CHECK_HEADER_INST_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID ASC");

                //找到检查单元格内容
                sql.append("select t.*,d.c_content_" + cellInstDataBM.getId() + " as cellData from T_CHECK_CELL_INST_" + PropertyConstant.WEI_BAO_SCHEMA_ID);
                sql.append(" t left join T_CELL_INST_DATA_" + PropertyConstant.WEI_BAO_SCHEMA_ID);
                sql.append(" d on t.id=d.T_CHECK_CELL_INST_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID");
                sql.append(" where t.T_CHECK_TEMP_INST_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID='" + checkInstId + "'");
                sql.append(" and t.T_CHECK_ROW_INST_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID='" + checkRowInstId + "'");
                sql.append(" order by t.T_CHECK_TEMP_INST_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID,t.T_CHECK_HEADER_INST_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID ASC");

                String cellInstSql = sql.toString();

                List<Map<String, Object>> checkInstCellList = jdbcTemplate.queryForList(cellInstSql);
                if (checkInstCellList != null && checkInstCellList.size() > 0) {
                    final int[] cell = {0};
                    for (int i = 0; i < checkInstCellList.size(); i++) {
                        Map checkCellInstMap = checkInstCellList.get(i);
                        String content = CommonTools.Obj2String(checkCellInstMap.get("C_CONTENT_" + checkCellInstBM.getId()));
                        String cellType = CommonTools.Obj2String(checkCellInstMap.get("C_CELL_TYPE_" + checkCellInstBM.getId()));
                        if (CheckCellInstCellType.TYPE_CHECK.equals(cellType) || CheckCellInstCellType.TYPE_FILL.equals(cellType) ||
                                CheckCellInstCellType.TYPE_SHUZI.equals(cellType) || CheckCellInstCellType.TYPE_TIME.equals(cellType) ||
                                CheckCellInstCellType.TYPE_YDN.equals(cellType) || CheckCellInstCellType.TYPE_JWD.equals(cellType)) {
                            content = CommonTools.Obj2String(checkCellInstMap.get("cellData"));
                        }
                        if (i == (columnsList.size() - 2)) {
                            break;
                        }
                        excel.cell(row1[0], cell[0])
                                .value(StringUtil.decodeUnicode(content)).warpText(true);
                        cell[0]++;
                    }
                    excel.cell(row1[0], cell[0])
                            .value(StringUtil.decodeUnicode(checkTime)).warpText(true);
                    excel.cell(row1[0], cell[0] + 1)
                            .value(StringUtil.decodeUnicode(taskName)).warpText(true);
                    row1[0]++;
                }
            }
        }
    }


    public List<FuncBean> getHistoryFormTemplateTreeByPid(String pid, IBusinessModel model, String schemaId) {
        List<FuncBean> retList = new ArrayList<>();
        //获取第一层功能点节点信息
        if ("-1".equals(pid)) {
            model = businessModelService.getBusinessModelBySName
                    (PropertyConstant.CHECK_TYPE, schemaId, EnumInter.BusinessModelEnum.Table);
        }else{
            model=businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_TEMP,schemaId, EnumInter.BusinessModelEnum.Table);
        }
        List<Map<String, String>> funcs = findTreeByPid(pid, model, schemaId);
        for (Map<String, String> function : funcs) {
            FuncBean funcBean = new FuncBean();
            try {
                PropertyUtils.copyProperties(funcBean, function);
            } catch (Exception e) {
                e.printStackTrace();
            }
            funcBean.setId(function.get("FUNCTIONID"));
            funcBean.setText(function.get("NAME"));
            if ((PropertyConstant.CHECK_TEMP+"_"+schemaId).equals(model.getS_table_name())){
                funcBean.setPid(function.get("PARENTID"));
                funcBean.setIconCls("icon-basicInfo");
            }
            funcBean.setResults(getHistoryFormTemplateTreeByPid(function.get("FUNCTIONID"), model, schemaId));
            retList.add(funcBean);
        }
        return retList;
    }

    public List<Map<String, String>> findTreeByPid(String pid,   IBusinessModel model, String schemaId) {
        String modelId=model.getId();
        String sql="";
        if ((PropertyConstant.CHECK_TYPE+"_"+schemaId).equals(model.getS_table_name())){
             sql = "select t.id as functionid,t.C_CHECK_NAME_" + modelId + " as name"
                    + " FROM T_CHECK_TYPE_" + schemaId + " t"
                    + " ORDER BY TO_NUMBER(ID) ASC";
        }else{
             sql = "select t.id as functionid,t.c_name_" + modelId + " as name"
                    + ",t.T_CHECK_TYPE_" + schemaId + "_id as parentid"
                    + " FROM T_CHECK_TEMP_" + schemaId + " t WHERE t.T_CHECK_TYPE_" + schemaId + "_ID='" + pid +
                    "' ORDER BY t.c_name_"+modelId+" ASC";
        }

        List<Map<String, Object>> objList = metaDaoFactory.getJdbcTemplate().queryForList(sql);
        List<Map<String, String>> stringList = SqlUtil.getStringList(objList);
        return stringList;
    }

    public AjaxResponseData<String> saveCheckHeaderOrEndData(String modelId, String formData){
        IBusinessModel checkTempInsBM=businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_TEMP_INST,PropertyConstant.WEI_BAO_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);
        IBusinessModel checkInsCellBM=businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_CELL_INST,PropertyConstant.WEI_BAO_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);
        AjaxResponseData retVal = new AjaxResponseData();
        if (StringUtil.isEmpty(formData)) {
            retVal.setMsg("表单内容为空");
            retVal.setSuccess(false);
            return retVal;
        } else {
            Map formDataMap = JsonUtil.json2Map(formData);
            Map dataMap = (Map) formDataMap.get("fields");
            String checkTempId=CommonTools.Obj2String(dataMap.get("T_CHECK_TEMP_"+PropertyConstant.WEI_BAO_SCHEMA_ID+"_ID"));
            String isHeaderOrEnd=CommonTools.Obj2String(dataMap.get("C_IS_HEADER_"+modelId));
            String cellType=CommonTools.Obj2String(dataMap.get("C_CELL_TYPE_"+modelId));
            String content=CommonTools.Obj2String(dataMap.get("C_CONTENT_"+modelId));
            checkTempInsBM.setReserve_filter("AND C_CHECK_TEMP_ID_"+checkTempInsBM.getId()+"='"+checkTempId+"' AND C_CHECK_STATE_"+checkTempInsBM.getId()+"='未完成'");
            List<Map<String,Object>> checkTempInstList=orientSqlEngine.getBmService().createModelQuery(checkTempInsBM).list();
            if (checkTempInstList!=null&&checkTempInstList.size() > 0){
                for (Map checkTempInstMap:checkTempInstList){
                    String checktempInsId=checkTempInstMap.get("ID").toString();
                    Map checkInsCellMap=UtilFactory.newHashMap();
                    checkInsCellMap.put("C_CONTENT_"+checkInsCellBM.getId(),content);
                    checkInsCellMap.put("C_CELL_TYPE_"+checkInsCellBM.getId(), cellType);
                    checkInsCellMap.put("T_CHECK_TEMP_INST_"+schemaId+"_ID",checktempInsId);
                    checkInsCellMap.put("C_IS_HEADER_"+checkInsCellBM.getId(),isHeaderOrEnd);
                    orientSqlEngine.getBmService().insertModelData(checkInsCellBM,checkInsCellMap);
                }
            }
            SaveModelDataEventParam eventParam = new SaveModelDataEventParam();
            eventParam.setModelId(modelId);
            eventParam.setDataMap(dataMap);
            eventParam.setCreateData(true);
            OrientContextLoaderListener.Appwac.publishEvent(new SaveModelDataEvent(ModelDataController.class, eventParam));
            retVal.setMsg("保存成功");
            return retVal;
        }
    }
}
