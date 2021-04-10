package com.orient.weibao.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.impl.BusinessModelServiceImpl;
import com.orient.edm.init.OrientContextLoaderListener;
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
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseController;
import com.orient.weibao.business.ConsumeMaterialMgrBusiness;
import com.orient.weibao.constants.PropertyConstant;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
 * @create 2019-05-17 17:04
 */
@Controller
@RequestMapping("/ConsumeMaterialMgr")
public class ConsumeMaterialController extends BaseController {
    @Autowired
    ConsumeMaterialMgrBusiness consumeMaterialMgrBusiness;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    BusinessModelServiceImpl businessModelService;
    @Autowired
    ISqlEngine orientSqlEngine;

    String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;

    /*
    查找所有耗材类别列表
     */
    @RequestMapping("queryConsumeTypeList")
    @ResponseBody
    public AjaxResponseData queryConsumeTypeList() {
        AjaxResponseData retVal = new AjaxResponseData();
        List<Map> consumeTypeList = consumeMaterialMgrBusiness.queryConsumeTypeList();
        JSONArray jsonArray = JSONArray.fromObject(consumeTypeList);
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.element("results", jsonArray);
        retVal.setResults(jsonArray);
        return retVal;
    }

    /***
     * 新增耗材类别数据
     * @param modelId
     * @param formData
     * @return
     */
    @RequestMapping("addConsumeTypeData")
    @ResponseBody
    public AjaxResponseData addConsumeTypeData(String modelId, String formData) {
        return consumeMaterialMgrBusiness.addConsumeTypeData(modelId, formData);
    }

    /**
     * 修改耗材类别数据
     *
     * @param modelId
     * @param consumeTypeId
     * @param formData
     * @return
     */
    @RequestMapping("updateConsumeTypeData")
    @ResponseBody
    public AjaxResponseData updateConsumeTypeData(String modelId, String consumeTypeId, String formData) {
        return consumeMaterialMgrBusiness.updateConsumeTypeData(modelId, consumeTypeId, formData);
    }

    /**
     * 删除耗材类别数据
     *
     * @param consumeTypeId
     * @return
     */
    @RequestMapping("delConsumeTypeById")
    @ResponseBody
    public AjaxResponseData delConsumeTypeById(String consumeTypeId) {
        AjaxResponseData retVal = new AjaxResponseData();
        consumeMaterialMgrBusiness.delConsumeTypeById(consumeTypeId);
        retVal.setSuccess(true);
        retVal.setMsg("删除成功");
        return retVal;
    }

    /**
     * 新增耗材数据
     *
     * @param modelId
     * @param formData
     * @param consumeTypeId
     * @return
     */
    @RequestMapping("addConsumeMaterialData")
    @ResponseBody
    public AjaxResponseData<String> addConsumeMaterialData(String modelId, String formData, String consumeTypeId) {
        AjaxResponseData retVal = new AjaxResponseData();
        if (StringUtil.isEmpty(formData)) {
            retVal.setMsg("表单内容为空");
            retVal.setSuccess(false);
            return retVal;
        } else {
            Map formDataMap = JsonUtil.json2Map(formData);
            Map dataMap = (Map) formDataMap.get("fields");
            dataMap.put("T_CONSUME_MATERIAL_" + schemaId + "_ID", consumeTypeId);
            String consumeNumber = (String) dataMap.get("C_NUMBER_" + modelId);
            if (consumeNumber == null || "".equals(consumeNumber)) {
                dataMap.put("C_NUMBER_" + modelId, 0);
            }
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
     * 删除耗材
     *
     * @param id
     * @return
     */
    @RequestMapping("delConsumeData")
    @ResponseBody
    public AjaxResponseData delConsumeData(String id) {
        AjaxResponseData retVal = new AjaxResponseData();
        consumeMaterialMgrBusiness.delConsumeData(id);
        retVal.setSuccess(true);
        return retVal;
    }

    @RequestMapping("updateConsumeMaterialData")
    @ResponseBody
    public AjaxResponseData<String> updateConsumeMaterialData(String modelId, String formData) {
        AjaxResponseData retVal = new AjaxResponseData();
        if (StringUtil.isEmpty(formData)) {
            retVal.setMsg("表单内容为空");
            retVal.setSuccess(false);
            return retVal;
        } else {
            Map formDataMap = JsonUtil.json2Map(formData);
            Map dataMap = (Map) formDataMap.get("fields");
            String version = (String) dataMap.get("C_VERSION_" + modelId);
            version = String.valueOf(Integer.parseInt(version) + 1);
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
     * 导入耗材
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("importConsumeData")
    @ResponseBody
    public Map<String, Object> importConsumeData(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> retVal = null;
        String fileName = null;
        //解析器解析request的上下文
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //先判断request中是否包涵multipart类型的数据
        if (multipartResolver.isMultipart(request)) {
            //再将request中的数据转化成multipart类型的数据
            MultipartHttpServletRequest mutiRequest = (MultipartHttpServletRequest) request;
            Iterator iterator = mutiRequest.getFileNames();
            if (iterator.hasNext()) {
                MultipartFile file = mutiRequest.getFile((String) iterator.next());
                fileName = file.getOriginalFilename();
                String sufix = fileName.substring(fileName.lastIndexOf(".") + 1);
                if ("xls".equals(sufix) || "xlsx".equals(sufix)) {
                    File dst = new File(fileName);
                    file.transferTo(dst);
                    //导入的文件若是xlsx后缀，则为true,否则为false
                    boolean after2007=sufix.equals("xlsx");
                    ExcelReader excelReader = new ExcelReader();
                    File excelFile = new File(fileName);
                    InputStream inputStream = new FileInputStream(excelFile);
                    TableEntity excelEntity = excelReader.readFile(inputStream, after2007);
                    String consumeTypeId = request.getParameter("consumeTypeId");
                    List<String> headers = Arrays.asList(excelReader.getExcelReaderConfig().getColumns());
                    retVal = consumeMaterialMgrBusiness.importConsumeData(excelEntity, consumeTypeId);
                    excelFile.delete();
                } else {
                    retVal = new HashMap<>();
                    retVal.put("success", false);
                    retVal.put("msg", "目前仅支持.xls,.xlsx文件格式");
                }
            }
        }
//        boolean after2007=fileName.substring(fileName.length()-4).equals("xlsx");


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
     * 导出耗材
     *
     * @param exportAll
     * @param toExportIds
     * @param response
     */
    @RequestMapping("exportConsumeData")
    @ResponseBody
    public void exportConsumeData(boolean exportAll, String toExportIds, String consumeTypeId, HttpServletResponse response) {
        String filePath=consumeMaterialMgrBusiness.exportConsumeData(exportAll, toExportIds, consumeTypeId);
        String fileName=filePath.substring(filePath.lastIndexOf("/")+1);
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
}
