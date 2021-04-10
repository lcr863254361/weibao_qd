package com.orient.weibao.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.impl.BusinessModelServiceImpl;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.modeldata.controller.ModelDataController;
import com.orient.modeldata.event.SaveModelDataEvent;
import com.orient.modeldata.event.UpdateModelDataEvent;
import com.orient.modeldata.eventParam.SaveModelDataEventParam;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.utils.CommonTools;
import com.orient.utils.ExcelUtil.reader.ExcelReader;
import com.orient.utils.ExcelUtil.reader.TableEntity;
import com.orient.utils.JsonUtil;
import com.orient.utils.StringUtil;
import com.orient.utils.UtilFactory;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseController;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import com.orient.weibao.bean.CellData;
import com.orient.weibao.bean.ProductStructureTreeNode;
import com.orient.weibao.business.ProductStructureBusiness;
import com.orient.weibao.constants.PropertyConstant;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2019-01-12 16:03
 */
@Controller
@RequestMapping("/ProductStructrue")
public class ProductStructureController extends BaseController {

    @Autowired
    ProductStructureBusiness productStructureBusiness;
    @Autowired
    BusinessModelServiceImpl businessModelService;
    @Autowired
    ISqlEngine orientSqlEngine;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    MetaDAOFactory metaDAOFactory;

    String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;

    @RequestMapping("queryCellDataView")
    @ResponseBody
    public AjaxResponseData<CellData> queryCellDataView(String productId, String deviceInstId) {
        return new AjaxResponseData<>(productStructureBusiness.queryCellDataView(productId, deviceInstId, -1, -1));
    }

    @RequestMapping("queryCellDataViewPage")
    @ResponseBody
    public AjaxResponseData<CellData> queryCellDataViewPage(String productId, String deviceInstId, String page, String start, String limit, String size) {
        return new AjaxResponseData<>(productStructureBusiness.queryCellDataView(productId, deviceInstId, Integer.parseInt(page),
                Integer.parseInt(limit) - Integer.parseInt(start)));
    }

    /**
     * 一层一层的获取产品结构树节点
     *
     * @param id
     * @param type
     * @param level
     * @param version 判断产品结构树中的母船及其它节点是否显示
     * @return
     */
    @RequestMapping("getProductTreeNodes")
    @ResponseBody
    public AjaxResponseData<List<ProductStructureTreeNode>> getProductTreeNodes(String id, String type, String level, String version) {
        return productStructureBusiness.getProductTreeNodes(id, type, level, version);
    }

    /**
     * 导入产品结构树
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("importProductTree")
    @ResponseBody
    public Map<String, Object> importProductTree(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> retVal = null;
        String fileName = null;
        //解析器解析request的上下文
        CommonsMultipartResolver multipartResolver =
                new CommonsMultipartResolver(request.getSession().getServletContext());
        //先判断request中是否包涵multipart类型的数据，
        if (multipartResolver.isMultipart(request)) {
            //再将request中的数据转化成multipart类型的数据
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator iter = multiRequest.getFileNames();
            if (iter.hasNext()) {
                MultipartFile file = multiRequest.getFile((String) iter.next());
                fileName = file.getOriginalFilename();
                String sufix = fileName.substring(fileName.lastIndexOf(".") + 1);
                if ("xls".equals(sufix) || "xlsx".equals(sufix)) {
                    //导入的文件若是xlsx后缀，则为true,否则为false
                    boolean after2007 = sufix.equals("xlsx");
                    File dst = new File(fileName);
                    file.transferTo(dst);
                    ExcelReader excelReader = new ExcelReader();
                    File excelFile = new File(fileName);
                    InputStream input = new FileInputStream(excelFile);
                    TableEntity excelEntity = excelReader.readFile(input, after2007);
                    retVal = productStructureBusiness.importProductTree(excelEntity);
                    excelFile.delete();
                } else {
                    retVal = new HashMap<>();
                    retVal.put("success", false);
                    retVal.put("msg", "目前仅支持.xls,.xlsx文件格式");
                }
            }
        }
//        boolean after2007 = fileName.substring(fileName.length() - 4).equals("xlsx");


//        try {
//            response.setContentType("text/html");
//            ObjectMapper mapper = new ObjectMapper();
//            mapper.writeValue(response.getOutputStream(), retVal);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return retVal;
    }

    /**
     * 更新产品结构树
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("updateProductTree")
    @ResponseBody
    public void updateProductTree(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String fileName = null;
        //解析器解析request的上下文
        CommonsMultipartResolver multipartResolver =
                new CommonsMultipartResolver(request.getSession().getServletContext());
        //先判断request中是否包涵multipart类型的数据，
        if (multipartResolver.isMultipart(request)) {
            //再将request中的数据转化成multipart类型的数据
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator iter = multiRequest.getFileNames();
            if (iter.hasNext()) {
                MultipartFile file = multiRequest.getFile((String) iter.next());
                fileName = file.getOriginalFilename();
                File dst = new File(fileName);
                try {
                    file.transferTo(dst);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        ExcelReader excelReader = new ExcelReader();
        File excelFile = new File(fileName);
        InputStream input = new FileInputStream(excelFile);
        boolean after2007 = fileName.substring(fileName.length() - 4).equals("xlsx");
        TableEntity excelEntity = excelReader.readFile(input, after2007);
        Map<String, Object> retVal = productStructureBusiness.updateProductTree(excelEntity);
        try {
            response.setContentType("text/html");
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), retVal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        excelFile.delete();
    }

    /**
     * 新增技术文档
     *
     * @param modelId
     * @param formData
     * @param productId
     * @return
     */
    @RequestMapping("saveSkillDocument")
    @ResponseBody
    public AjaxResponseData<String> saveSkillDocument(String modelId, String formData, String productId, String flowId, String hangciId, String hangduanId, String taskId) {

        AjaxResponseData retVal = new AjaxResponseData();
        String tableName = PropertyConstant.SKILL_DOCUMENT;
        IBusinessModel skillDocumentModel = businessModelService.getBusinessModelBySName(tableName, schemaId, EnumInter.BusinessModelEnum.Table);
        if (StringUtil.isEmpty(formData)) {
            retVal.setMsg("表单内容为空");
            retVal.setSuccess(false);
        } else {
            if (CommonTools.isNullString(productId) || productId.equals("undefined")) {
                productId = "";
            }
            if (CommonTools.isNullString(flowId) || flowId.equals("undefined")) {
                flowId = "";
            }
            if (CommonTools.isNullString(hangciId) || hangciId.equals("undefined")) {
                hangciId = "";
            }
            if (CommonTools.isNullString(hangduanId) || hangduanId.equals("undefined")) {
                hangduanId = "";
            }
            if (CommonTools.isNullString(taskId) || taskId.equals("undefined")) {
                taskId = "";
            }
            Map formDataMap = JsonUtil.json2Map(formData);
            Map dataMap = (Map) formDataMap.get("fields");

            String zhidaoFileName = CommonTools.Obj2String(dataMap.get("C_FILE_NAME_" + modelId));
            if (zhidaoFileName != null && !"".equals(zhidaoFileName)) {
                try {
                    JSONArray jsonArray = new JSONArray(zhidaoFileName);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Map map = new HashMap<>();
                        JSONObject fileName = jsonArray.getJSONObject(i);
                        int index = fileName.getString("name").lastIndexOf(".");
                        String fileType = fileName.getString("name").substring(index + 1, fileName.getString("name").length());
                        String name = "[" + fileName + "]";
                        map.put("C_FILE_NAME_" + modelId, name);
                        map.put("T_PRODUCT_STRUCTURE_" + schemaId + "_ID", productId);
                        map.put("T_DESTROY_FLOW_" + schemaId + "_ID", flowId);
                        map.put("T_HANGCI_" + schemaId + "_ID", hangciId);
                        map.put("T_HANGDUAN_" + schemaId + "_ID", hangduanId);
                        map.put("T_DIVING_TASK_" + schemaId + "_ID", taskId);
                        map.put("C_FILE_TYPE_" + modelId, fileType);
                        orientSqlEngine.getBmService().insertModelData(skillDocumentModel, map);
                    }
//                    JSONArray jsonArray=new JSONArray(zhidaoFileName);
                    retVal.setMsg("保存成功");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return retVal;
    }

    /**
     * 更换设备
     *
     * @param productId
     * @param newDeviceInstId
     * @return
     */
    @RequestMapping("replaceDeviceInstData")
    @ResponseBody
    public CommonResponseData saveDeviceInstData(String productId, String newDeviceInstId) {
        return productStructureBusiness.saveDeviceInstData(productId, newDeviceInstId);
    }

    /**
     * 获取当前关联的设备
     *
     * @param productId
     * @return
     */
    @RequestMapping("getCurrentRefDevice")
    @ResponseBody
    public AjaxResponseData<Map> getCurrentRefDevice(String productId) {
        AjaxResponseData retVal = new AjaxResponseData();
        Map map = productStructureBusiness.getCurrentRefDevice(productId);
        retVal.setSuccess(true);
        retVal.setResults(map);
        return retVal;
    }

    /**
     * 获取关联的检查表实例
     *
     * @param orientModelId
     * @param isView
     * @param page
     * @param limit
     * @param customerFilter
     * @param sort
     * @param productId
     * @return
     */
    @RequestMapping("queryRefCheckInstData")
    @ResponseBody
    public ExtGridData<Map> queryRefCheckInstData(String orientModelId, String isView, Integer page, Integer limit, String customerFilter, String sort, String productId, String deviceInstId) {

        ExtGridData<Map> retVal = productStructureBusiness.queryRefCheckInstData(orientModelId, isView, page, limit, customerFilter, true, sort, productId, deviceInstId);
        return retVal;
    }

    @RequestMapping("getHistoryCheckInstData")
    @ResponseBody
    public ExtGridData<Map> getHistoryCheckInstData(String orientModelId, String isView, Integer page, Integer limit, String customerFilter, String sort, String productId) {

        ExtGridData<Map> retVal = productStructureBusiness.getHistoryCheckInstData(orientModelId, isView, page, limit, customerFilter, true, sort, productId);
        return retVal;
    }

    /**
     * 获取历史检查记录数据
     *
     * @param start
     * @param limit
     * @param productId
     * @return
     */
    @RequestMapping("getHistoryCheckData")
    @ResponseBody
    public ExtGridData getHistoryCheckData(String start, String limit, String productId) {
        ExtGridData str = productStructureBusiness.getHistoryCheckData(start, limit, productId);
        return str;
    }

    /**
     * 新增产品结构树节点
     *
     * @param modelId
     * @param formData
     * @param productId
     * @return
     */
    @RequestMapping("addProductTreeNode")
    @ResponseBody
    public AjaxResponseData<String> addProductTreeNode(String modelId, String formData, String productId, String level) {
        AjaxResponseData retVal = new AjaxResponseData();
        if (StringUtil.isEmpty(formData)) {
            retVal.setMsg("表单内容为空");
            retVal.setSuccess(false);
            return retVal;
        } else {
            Map formDataMap = JsonUtil.json2Map(formData);
            Map dataMap = (Map) formDataMap.get("fields");
            //零件及零件子节点
            if (!"1".equals(level) && !"2".equals(level)) {
                dataMap.put("C_TYPE_" + modelId, "part");
            }
            dataMap.put("C_PID_" + modelId, productId);
            SaveModelDataEventParam eventParam = new SaveModelDataEventParam();
            eventParam.setModelId(modelId);
            eventParam.setDataMap(dataMap);
            eventParam.setCreateData(true);
            OrientContextLoaderListener.Appwac.publishEvent(new SaveModelDataEvent(ModelDataController.class, eventParam));
            retVal.setMsg("保存成功");
//            Map permsMap = new HashMap<>();
//            permsMap.put("ID", eventParam.getDataMap().get("ID"));
//            permsMap.put("refreshTree", true);
//            retVal.setResults(permsMap);
            retVal.setResults(eventParam.getDataMap().get("ID"));
            return retVal;
        }
    }

    /**
     * 修改产品结构树节点
     *
     * @param modelId
     * @param formData
     * @return
     */
    @RequestMapping("updateProductTreeNode")
    @ResponseBody
    public AjaxResponseData updateProductTreeNode(String modelId, String formData, String level) {
        AjaxResponseData retVal = new AjaxResponseData();
        if (StringUtil.isEmpty(formData)) {
            retVal.setMsg("表单内容为空");
            retVal.setSuccess(false);
        } else {
            Map formDataMap = JsonUtil.json2Map(formData);
            Map dataMap = (Map) formDataMap.get("fields");
            //零件及零件子节点
            if (!"1".equals(level) && !"2".equals(level)) {
                dataMap.put("C_TYPE_" + modelId, "part");
            }
            SaveModelDataEventParam eventParam = new SaveModelDataEventParam();
            eventParam.setModelId(modelId);
            eventParam.setDataMap(dataMap);
            OrientContextLoaderListener.Appwac.publishEvent(new UpdateModelDataEvent(ModelDataController.class, eventParam));
            retVal.setMsg("保存成功");
//            Map permsMap = new HashMap<>();
//            permsMap.put("refreshTree", true);
//            retVal.setResults(permsMap);
        }
        return retVal;
    }

    @RequestMapping("delProductTreeData")
    @ResponseBody
    public AjaxResponseData delProductTreeData(String id) {
        AjaxResponseData retVal = new AjaxResponseData();
        productStructureBusiness.delProductTreeData(id);
        retVal.setSuccess(true);
        retVal.setMsg("删除成功");
        return retVal;
    }

    /**
     * 导出所有产品结构节点
     *
     * @param request
     * @param response
     */
    @RequestMapping("exportAllProductNodes")
    @ResponseBody
    public void exportAllProductNodes(HttpServletRequest request, HttpServletResponse response) {
        productStructureBusiness.exportAllProductNodes(request, response);
    }

    /*************************************************************************************************************************/

    /**
     * 导入三亚产品结构树
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("importSyProductStructTree")
    @ResponseBody
    public Map<String, Object> importSyProductStructTree(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> retVal = null;
        String fileName = null;
        //解析器解析request的上下文
        CommonsMultipartResolver multipartResolver =
                new CommonsMultipartResolver(request.getSession().getServletContext());
        //先判断request中是否包涵multipart类型的数据，
        if (multipartResolver.isMultipart(request)) {
            //再将request中的数据转化成multipart类型的数据
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator iter = multiRequest.getFileNames();
            if (iter.hasNext()) {
                MultipartFile file = multiRequest.getFile((String) iter.next());
                fileName = file.getOriginalFilename();
                String sufix = fileName.substring(fileName.lastIndexOf(".") + 1);
                File dst = new File(fileName);
                if ("xls".equals(sufix) || "xlsx".equals(sufix)) {
                    file.transferTo(dst);
                    retVal = productStructureBusiness.importSyProductStructTree(request, fileName);
                    dst.delete();
                } else {
                    retVal = new HashMap<>();
                    retVal.put("success", false);
                    retVal.put("msg", "目前仅支持.xls,.xlsx文件格式");
                }
            }
        }
        return retVal;
    }

    /**
     * 导出三亚产品结构树
     *
     * @param exportAll
     * @param response
     */
    @RequestMapping("exportSyProductStructTree")
    @ResponseBody
    public void exportSyProductStructTree(boolean exportAll, HttpServletResponse response) {
        String filePath = productStructureBusiness.exportSyProductStructTree(exportAll);
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        try {
            response.setContentType("aplication/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(filePath));
            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
            byte[] buffer = new byte[8192];
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

    /**
     * 保存产品结构分系统数据
     *
     * @param modelId
     * @param formData
     * @return
     */
    @RequestMapping("saveStructSystemData")
    @ResponseBody
    public AjaxResponseData<String> saveStructSystemData(String modelId, String formData) {
        AjaxResponseData retVal = new AjaxResponseData();
        if (StringUtil.isEmpty(formData)) {
            retVal.setMsg("表单内容为空");
            retVal.setSuccess(false);
            return retVal;
        } else {
            Map formDataMap = JsonUtil.json2Map(formData);
            Map dataMap = (Map) formDataMap.get("fields");
            dataMap.put("C_VERSION_" + modelId, 0);
            SaveModelDataEventParam eventParam = new SaveModelDataEventParam();
            eventParam.setModelId(modelId);
            eventParam.setDataMap(dataMap);
            eventParam.setCreateData(true);
            OrientContextLoaderListener.Appwac.publishEvent(new SaveModelDataEvent(ModelDataController.class, eventParam));
            retVal.setMsg("保存成功");
            return retVal;
        }
    }

    /**
     * 修改产品结构分系统数据
     *
     * @param modelId
     * @param formData
     * @return
     */
    @RequestMapping("updateStructSystemData")
    @ResponseBody
    public AjaxResponseData<String> updateStructSystemData(String modelId, String formData) {
        AjaxResponseData retVal = new AjaxResponseData();
        if (StringUtil.isEmpty(formData)) {
            retVal.setMsg("表单内容为空");
            retVal.setSuccess(false);
            return retVal;
        } else {
            Map formDataMap = JsonUtil.json2Map(formData);
            Map dataMap = (Map) formDataMap.get("fields");
            String version = CommonTools.Obj2String(dataMap.get("C_VERSION_" + modelId));
            if ("".equals(version)) {
                version = "".equals(version) ? "0" : version;
            } else {
                version = String.valueOf(Integer.parseInt(version) + 1);
            }
            dataMap.put("C_VERSION_" + modelId, version);
            SaveModelDataEventParam eventParam = new SaveModelDataEventParam();
            eventParam.setModelId(modelId);
            eventParam.setDataMap(dataMap);
            OrientContextLoaderListener.Appwac.publishEvent(new UpdateModelDataEvent(ModelDataController.class, eventParam));
            retVal.setSuccess(true);
            retVal.setMsg("保存成功");
            return retVal;
        }
    }

    /**
     * 保存结构设备实例数据
     * @param modelId
     * @param formData
     * @param deviceId
     * @return
     */
    @RequestMapping("saveStructDeviceInstData")
    @ResponseBody
    public AjaxResponseData<String> saveStructDeviceInstData(String modelId, String formData, String deviceId) {
        IBusinessModel structSystemBM = businessModelService.getBusinessModelBySName(PropertyConstant.STRUCT_SYSTEM, schemaId, EnumInter.BusinessModelEnum.Table);
        AjaxResponseData retVal = new AjaxResponseData();
        if (StringUtil.isEmpty(formData)) {
            retVal.setMsg("表单内容为空");
            retVal.setSuccess(false);
            return retVal;
        } else {
            Map formDataMap = JsonUtil.json2Map(formData);
            Map dataMap = (Map) formDataMap.get("fields");
            String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;
            String systemId = CommonTools.Obj2String(dataMap.get("T_STRUCT_SYSTEM_" + schemaId + "_ID"));
            dataMap.put("C_VERSION_" + modelId, 0);
            //插入产品结构设备实例
            SaveModelDataEventParam eventParam = new SaveModelDataEventParam();
            eventParam.setModelId(modelId);
            eventParam.setDataMap(dataMap);
            eventParam.setCreateData(true);
            OrientContextLoaderListener.Appwac.publishEvent(new SaveModelDataEvent(ModelDataController.class, eventParam));
            //修改分系统版本
            structSystemBM.setReserve_filter("AND ID='" + systemId + "'");
            List<Map<String, Object>> systemList = orientSqlEngine.getBmService().createModelQuery(structSystemBM).list();
            Map systemMap = systemList.get(0);
            String systemVersion = CommonTools.Obj2String(systemMap.get("C_VERSION_" + structSystemBM.getId()));
            if ("".equals(systemVersion)){
                systemVersion="0";
            }else{
                systemVersion = String.valueOf(Integer.parseInt(systemVersion) + 1);
            }
            systemMap.put("C_VERSION_" + structSystemBM.getId(), systemVersion);
            orientSqlEngine.getBmService().updateModelData(structSystemBM, systemMap, systemId);
            retVal.setSuccess(true);
            retVal.setMsg("保存成功");
            return retVal;
        }
    }

    @RequestMapping("updateStructDeviceInstData")
    @ResponseBody
    public AjaxResponseData<String> updateStructDeviceInstData(String modelId, String formData) {
        IBusinessModel structDeviceInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.STRUCT_DEVICE_INS, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel structSystemBM = businessModelService.getBusinessModelBySName(PropertyConstant.STRUCT_SYSTEM, schemaId, EnumInter.BusinessModelEnum.Table);
        AjaxResponseData retVal = new AjaxResponseData();
        if (StringUtil.isEmpty(formData)) {
            retVal.setMsg("表单内容为空");
            retVal.setSuccess(false);
            return retVal;
        } else {
            Map formDataMap = JsonUtil.json2Map(formData);
            Map dataMap = (Map) formDataMap.get("fields");
            String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;
            String version = (String) dataMap.get("C_VERSION_" + modelId);
            version = String.valueOf(Integer.parseInt(version) + 1);
            dataMap.put("C_VERSION_" + modelId, version);
            String deviceInstId = (String) dataMap.get("ID");
            orientSqlEngine.getBmService().updateModelData(structDeviceInstBM, dataMap, deviceInstId);
            //修改设备实例版本，也要修改系统版本
            String systemId = CommonTools.Obj2String(dataMap.get("T_STRUCT_SYSTEM_" + schemaId + "_ID"));
            structSystemBM.setReserve_filter("AND ID='" + systemId + "'");
            List<Map<String, Object>> systemList = orientSqlEngine.getBmService().createModelQuery(structSystemBM).list();
            Map systemMap = systemList.get(0);
            String systemVersion = CommonTools.Obj2String(systemMap.get("C_VERSION_" + structSystemBM.getId()));
            if ("".equals(systemVersion)){
                systemVersion="0";
            }else{
                systemVersion = String.valueOf(Integer.parseInt(systemVersion) + 1);
            }
            systemMap.put("C_VERSION_" + structSystemBM.getId(), systemVersion);
            orientSqlEngine.getBmService().updateModelData(structSystemBM, systemMap, systemId);
            retVal.setSuccess(true);
            retVal.setMsg("保存成功");
            return retVal;
        }
    }
}
