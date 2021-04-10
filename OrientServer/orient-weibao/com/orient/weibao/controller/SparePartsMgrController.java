package com.orient.weibao.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.impl.BusinessModelServiceImpl;
import com.orient.download.bean.inform.BaseEntity;
import com.orient.download.bean.inform.CurrentStateBean;
import com.orient.edm.init.FileServerConfig;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.modeldata.controller.ModelDataController;
import com.orient.modeldata.event.SaveModelDataEvent;
import com.orient.modeldata.event.UpdateModelDataEvent;
import com.orient.modeldata.eventParam.SaveModelDataEventParam;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.utils.*;
import com.orient.utils.ExcelUtil.reader.ExcelReader;
import com.orient.utils.ExcelUtil.reader.TableEntity;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseController;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import com.orient.weibao.bean.DeviceInstBean;
import com.orient.weibao.business.SparePartsMgrBusiness;
import com.orient.weibao.constants.PropertyConstant;
import com.orient.weibao.utils.MatrixToImageWriter;
import com.orient.weibao.utils.PrintImage;
import org.apache.poi.hssf.record.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

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
 * @create 2018-12-12 9:26
 */
@Controller
@RequestMapping("/spareParts")
public class SparePartsMgrController extends BaseController {

    @Autowired
    SparePartsMgrBusiness sparePartsMgrBusiness;
    @Autowired
    BusinessModelServiceImpl businessModelService;
    @Autowired
    ISqlEngine orientSqlEngine;
    @Autowired
    FileServerConfig fileServerConfig;

    String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;

    /**
     * 快速新增
     *
     * @param count   新增的数量
     * @param modelId 模型ID
     * @param spareId 设备ID
     * @return
     */
    @RequestMapping("easyAdd")
    @ResponseBody
    public AjaxResponseData<String> easyAdd(String count, String modelId, String spareId, String productId) {
        AjaxResponseData retVal = new AjaxResponseData();
        String msg = sparePartsMgrBusiness.easyAdd(count, modelId, spareId, productId);
        retVal.setMsg(msg);
        return retVal;
    }

    /**
     * 保存备品备件实例数据
     *
     * @param: modelId
     * @param: formData
     * @param: spareId  备品备件ID
     * @return:
     */
    @RequestMapping("saveSparePartsInstData")
    @ResponseBody
    public AjaxResponseData<String> saveSparePartsInstData(String modelId, String formData, String spareId, String productId) {

        IBusinessModel troubleBM = businessModelService.getBusinessModelBySName(PropertyConstant.TROUBLE_DEVICE_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel spareInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.SPARE_PARTS_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel deviceLifeBM = businessModelService.getBusinessModelBySName(PropertyConstant.DEVICE_LIFE_CYCLE, schemaId, EnumInter.BusinessModelEnum.Table);
        AjaxResponseData retVal = new AjaxResponseData();
        if (StringUtil.isEmpty(formData)) {
            retVal.setMsg("表单内容为空");
            retVal.setSuccess(false);
            return retVal;
        } else {
            Map formDataMap = JsonUtil.json2Map(formData);
            Map dataMap = (Map) formDataMap.get("fields");
            String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;
            dataMap.put("T_SPARE_PARTS_" + schemaId + "_ID", spareId);
            dataMap.put("C_DEVICE_NAME_" + modelId, spareId);
            dataMap.put("T_PRODUCT_STRUCTURE_" + schemaId + "_ID", productId);
            dataMap.put("C_VERSION_" + modelId, 0);
            String startTime = CommonTools.Obj2String(dataMap.get("C_LIEZHUANG_TIME_" + spareInstBM.getId()));
            String deviceState = CommonTools.Obj2String(dataMap.get("C_STATE_" + spareInstBM.getId()));
            //判断数据库中是否已经存在相同数据
            String numberId = CommonTools.Obj2String(dataMap.get("C_SERIAL_NUMBER_" + modelId));
            spareInstBM.setReserve_filter(" AND T_SPARE_PARTS_" + schemaId + "_ID='" + spareId + "'" +
                    " AND C_SERIAL_NUMBER_" + modelId + "='" + numberId + "'");
            List<Map<String, Object>> spareInstList = orientSqlEngine.getBmService().createModelQuery(spareInstBM).list();
            if (spareInstList.size() == 0) {
                //插入设备实例
                String spareInstId = orientSqlEngine.getBmService().insertModelData(spareInstBM, dataMap);
                //修改设备版本
                IBusinessModel deviceBM = businessModelService.getBusinessModelBySName(PropertyConstant.SPARE_PARTS, schemaId, EnumInter.BusinessModelEnum.Table);
                deviceBM.setReserve_filter("AND ID='" + spareId + "'");
                List<Map<String, Object>> spareList = orientSqlEngine.getBmService().createModelQuery(deviceBM).list();
                Map spareMap = spareList.get(0);
                String deviceVersion = CommonTools.Obj2String(spareMap.get("C_VERSION_" + deviceBM.getId()));
                deviceVersion = String.valueOf(Integer.parseInt(deviceVersion) + 1);
                spareMap.put("C_VERSION_" + deviceBM.getId(), deviceVersion);
                orientSqlEngine.getBmService().updateModelData(deviceBM, spareMap, spareId);

                Map lifeCycleMap = UtilFactory.newHashMap();
                lifeCycleMap.put("T_SPARE_PARTS_SHILI_" + schemaId + "_ID", spareInstId);
                lifeCycleMap.put("C_START_TIME_" + deviceLifeBM.getId(), startTime);
                lifeCycleMap.put("C_DEVICE_STATE_" + deviceLifeBM.getId(), deviceState);
                SimpleDateFormat editDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                lifeCycleMap.put("C_UPDATE_TIME_" + deviceLifeBM.getId(), editDate.format(new Date()));
//                lifeCycleMap.put("C_DEVICE_INST_ID_" + deviceLifeBM.getId(), spareInstId);
                orientSqlEngine.getBmService().insertModelData(deviceLifeBM, lifeCycleMap);
                retVal.setSuccess(true);
                retVal.setMsg("保存成功");
                return retVal;
            } else {
                retVal.setSuccess(false);
                retVal.setMsg("不能重复新增！");
            }
            return retVal;
        }
    }

    /**
     * 修改备品备件实例数据
     *
     * @param modelId
     * @param formData
     * @param spareId
     * @return
     */
    @RequestMapping("updateSparePartsInstData")
    @ResponseBody
    public AjaxResponseData<String> updateSparePartsInstData(String modelId, String formData, String spareId) {

        IBusinessModel troubleBM = businessModelService.getBusinessModelBySName(PropertyConstant.TROUBLE_DEVICE_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel spareInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.SPARE_PARTS_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel spareBM = businessModelService.getBusinessModelBySName(PropertyConstant.SPARE_PARTS, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel deviceLifeBM = businessModelService.getBusinessModelBySName(PropertyConstant.DEVICE_LIFE_CYCLE, schemaId, EnumInter.BusinessModelEnum.Table);
        AjaxResponseData retVal = new AjaxResponseData();
        if (StringUtil.isEmpty(formData)) {
            retVal.setMsg("表单内容为空");
            retVal.setSuccess(false);
            return retVal;
        } else {
            Map formDataMap = JsonUtil.json2Map(formData);
            Map dataMap = (Map) formDataMap.get("fields");
            String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;
//            dataMap.put("T_SPARE_PARTS_" + schemaId + "_ID", spareId);
            dataMap.put("C_DEVICE_NAME_" + modelId, spareId);
            String version = (String) dataMap.get("C_VERSION_" + modelId);
            version = String.valueOf(Integer.parseInt(version) + 1);
            dataMap.put("C_VERSION_" + modelId, version);
            String startTime = CommonTools.Obj2String(dataMap.get("C_LIEZHUANG_TIME_" + spareInstBM.getId()));
            String deviceState = CommonTools.Obj2String(dataMap.get("C_STATE_" + spareInstBM.getId()));
            String spareInstId = (String) dataMap.get("ID");
            orientSqlEngine.getBmService().updateModelData(spareInstBM, dataMap, spareInstId);
            //修改设备实例版本，也要修改设备版本
            String deviceId = CommonTools.Obj2String(dataMap.get("T_SPARE_PARTS_" + schemaId + "_ID"));
            spareBM.setReserve_filter("AND ID='" + deviceId + "'");
            List<Map<String, Object>> spareList = orientSqlEngine.getBmService().createModelQuery(spareBM).list();
            Map spareMap = spareList.get(0);
            String deviceVersion = CommonTools.Obj2String(spareMap.get("C_VERSION_" + spareBM.getId()));
            deviceVersion = String.valueOf(Integer.parseInt(deviceVersion) + 1);
            spareMap.put("C_VERSION_" + spareBM.getId(), deviceVersion);
            orientSqlEngine.getBmService().updateModelData(spareBM, spareMap, deviceId);
            deviceLifeBM.clearAllFilter();
            deviceLifeBM.setReserve_filter("AND T_SPARE_PARTS_SHILI_" + schemaId + "_ID='" + spareInstId + "'" +
                    " AND C_END_TIME_" + deviceLifeBM.getId() + " IS NULL");
            List<Map<String, Object>> lifeCycleList = orientSqlEngine.getBmService().createModelQuery(deviceLifeBM).list();
            if (lifeCycleList.size() > 0) {
                Map lifeCycleMap = lifeCycleList.get(0);
                String lifeId = (String) lifeCycleMap.get("ID");
                lifeCycleMap.put("C_START_TIME_" + deviceLifeBM.getId(), startTime);
                lifeCycleMap.put("C_DEVICE_STATE_" + deviceLifeBM.getId(), deviceState);
                SimpleDateFormat editDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                lifeCycleMap.put("C_UPDATE_TIME_" + deviceLifeBM.getId(), editDate.format(new Date()));
                orientSqlEngine.getBmService().updateModelData(deviceLifeBM, lifeCycleMap, lifeId);
            }
            retVal.setSuccess(true);
            retVal.setMsg("保存成功");
            return retVal;
        }
    }

    /**
     * 获取备品备件实例数据
     *
     * @param: orientModelId
     * @param: isView
     * @param: page
     * @param: limit
     * @param: customerFilter
     * @param: sort
     * @param: spareName 备品备件名称
     * @return: nodeContent:产品结构更换设备中输入的条件查询
     */
    @RequestMapping("querySparePartsInstData")
    @ResponseBody
    public ExtGridData<Map> getSparePartsInstData(String orientModelId, String isView, Integer page, Integer limit, String customerFilter, String sort, String spareName, String spareId, String nodeContent) {

        ExtGridData<Map> retVal = sparePartsMgrBusiness.getSparePartsInstData(orientModelId, isView, page, limit, customerFilter, true, sort, spareName, spareId, nodeContent);
        return retVal;
    }

    /**
     * 生成二维码图片
     *
     * @param: id
     * @param: serialNumer
     * @param: spareName
     * @return: 存储图片的路径
     */
    @RequestMapping("generateQRCode")
    @ResponseBody
//    public AjaxResponseData<String> generateQRCode(String id, String serialNumber, String spareName) {
//        AjaxResponseData retVal = new AjaxResponseData();
//        String fileName = sparePartsMgrBusiness.encoderQRCode(id, serialNumber, spareName);
////        String qrcodeUrl=fileServerConfig.getFtpHome()+fileName;
//        retVal.setResults(fileName);
//        return retVal;
//    }
    /**
     *生成二维码
     * @param path
     */
    public AjaxResponseData<T> generateQRCode(String id, String serialNumber, String spareName, String deviceModel) {
        AjaxResponseData retVal = new AjaxResponseData();
        String qrCodeFileName = sparePartsMgrBusiness.generateQRCode(id, serialNumber, spareName, deviceModel, false);
        String previewQrcodeUrl = "preview" + File.separator + "imagePreview" + File.separator + qrCodeFileName;
        String printQrcodeUrl = CommonTools.getPreviewImagePath() + File.separator + qrCodeFileName;
        List<String> result = new ArrayList<>();
        result.add(previewQrcodeUrl);
        result.add(printQrcodeUrl);
        retVal.setResults(result);
        return retVal;
    }

    /**
     * 批量打印二维码
     *
     * @param deviceInstIds
     * @param spareId
     * @param spareName
     * @param deviceModel
     * @param startAll
     * @return
     */
    @RequestMapping("morePrintQRCode")
    @ResponseBody
    public CommonResponseData morePrintQRCode(String deviceInstIds, String spareId, String spareName, String deviceModel, Boolean startAll) {
        CommonResponseData retVal = new CommonResponseData();
        IBusinessModel spareInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.SPARE_PARTS_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        List<Map<String, Object>> deviceInstList = UtilFactory.newArrayList();
        if (startAll) {
            spareInstBM.setReserve_filter("AND T_SPARE_PARTS_" + schemaId + "_ID='" + spareId + "'");
            deviceInstList = orientSqlEngine.getBmService().createModelQuery(spareInstBM).list();
        } else {
            spareInstBM.clearAllFilter();
            spareInstBM.setReserve_filter("AND ID IN (" + deviceInstIds + ")");
            deviceInstList = orientSqlEngine.getBmService().createModelQuery(spareInstBM).list();
        }
        if (deviceInstList.size() > 0) {
            List<String> qrcodeUrlList = UtilFactory.newArrayList();
            for (Map deviceInstMap : deviceInstList) {
                String deviceInstId = CommonTools.Obj2String(deviceInstMap.get("ID"));
                String serialNumber = CommonTools.Obj2String(deviceInstMap.get("C_SERIAL_NUMBER_" + spareInstBM.getId()));
                String fileName = sparePartsMgrBusiness.generateQRCode(deviceInstId, serialNumber, spareName, deviceModel, true);
                String qrcodeUrl = fileServerConfig.getFtpHome() + fileName;
                qrcodeUrlList.add(qrcodeUrl);
            }
            if (qrcodeUrlList.size() > 0) {
                for (String path : qrcodeUrlList) {
                    new PrintImage().drawImage(path);
                }
            }
            retVal.setSuccess(true);
        } else {
            retVal.setMsg("设备实例还未生成！");
            retVal.setSuccess(false);
        }
        return retVal;
    }

    /**
     * 新增备品备件
     *
     * @param modelId
     * @param formData
     * @param productId 产品结构树Id
     * @return
     */
    @RequestMapping("saveSparePartsData")
    @ResponseBody
    public AjaxResponseData<String> saveSparePartsData(String modelId, String formData, String productId, boolean isCarryTool, String isCabinOutOrIn) {
        AjaxResponseData retVal = new AjaxResponseData();
        if (StringUtil.isEmpty(formData)) {
            retVal.setMsg("表单内容为空");
            retVal.setSuccess(false);
            return retVal;
        } else {
            Map formDataMap = JsonUtil.json2Map(formData);
            Map dataMap = (Map) formDataMap.get("fields");
            String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;
            dataMap.put("T_PRODUCT_STRUCTURE_" + schemaId + "_ID", productId);
            dataMap.put("C_VERSION_" + modelId, 0);
            if (isCarryTool) {
                dataMap.put("C_IS_CARRY_TYPE_" + modelId, true);
                dataMap.put("C_CABIN_INOROUT_" + modelId, isCabinOutOrIn);
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

    /***
     * 修改备品备件后记录备品备件的版本变化
     *
     * @param modelId
     * @param formData
     * @return
     */
    @RequestMapping("updateSparePartsData")
    @ResponseBody
    public AjaxResponseData<String> updateSparePartsData(String modelId, String formData) {
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
     * 删除设备、设备实例、产品结构关联的设备实例ID
     *
     * @param id
     * @param productId
     * @return
     */
    @RequestMapping("delSparePartsData")
    @ResponseBody
    public AjaxResponseData delSparePartsData(String id, String productId) {
        AjaxResponseData retVal = new AjaxResponseData();
        sparePartsMgrBusiness.delSparePartsData(id, productId);
        retVal.setSuccess(true);
        retVal.setMsg("删除成功");
        return retVal;
    }

    /**
     * 删除设备实例数据及产品结构关联的设备实例ID
     *
     * @param id
     * @param productId
     * @return
     */
    @RequestMapping("delSparePartsInstData")
    @ResponseBody
    public AjaxResponseData delSparePartsInstData(String id, String productId, String deviceId) {
        AjaxResponseData retVal = new AjaxResponseData();
        sparePartsMgrBusiness.delSparePartsInstData(id, productId, deviceId);
        retVal.setSuccess(true);
        retVal.setMsg("删除成功");
        return retVal;
    }

    @RequestMapping("getSparePartsData")
    @ResponseBody
    public ExtGridData<Map> getSparePartsData(String orientModelId, String isView, Integer page, Integer limit, String customerFilter, String sort, String productId, String leaf) {

        ExtGridData<Map> retVal = sparePartsMgrBusiness.getSparePartsData(orientModelId, isView, page, limit, customerFilter, true, sort, productId, leaf);
        return retVal;
    }

    /**
     * 获取故障设备数据
     *
     * @param orientModelId
     * @param isView
     * @param page
     * @param limit
     * @param sort
     * @return
     */
    @RequestMapping("getTroubleDeviceData")
    @ResponseBody
    public ExtGridData<Map> getTroubleDeviceData(String orientModelId, String isView, Integer page, Integer limit, String sort, String deviceInstId, boolean isTrouble) {

        ExtGridData<Map> retVal = sparePartsMgrBusiness.getTroubleDeviceData(orientModelId, isView, page, limit, true, sort, deviceInstId, isTrouble);
        return retVal;
    }

    /**
     * 获取设备实例寿命全周期数据
     *
     * @param start
     * @param limit
     * @param deviceInstId
     * @return
     */
    @RequestMapping("getDeviceInstLifeCycle")
    @ResponseBody
    public ExtGridData getDeviceInstLifeCycle(String start, String limit, String deviceInstId) {
        ExtGridData str = sparePartsMgrBusiness.getDeviceInstLifeCycle(start, limit, deviceInstId);
        return str;
    }

    @RequestMapping("getTroubleDeviceDetail")
    @ResponseBody
    public AjaxResponseData<Map> getTroubleDeviceDetail(String troubleId) {
        AjaxResponseData retVal = new AjaxResponseData();
        Map map = sparePartsMgrBusiness.getTroubleDeviceDetail(troubleId);
        retVal.setResults(map);
        return retVal;
    }

    /**
     * 获取故障设备图片的详细路径
     *
     * @param modelId
     * @param dataId
     * @return
     */
    @RequestMapping("getImageUrl")
    @ResponseBody
    public ModelAndView addFile(String modelId, String dataId) {
        ModelAndView retVal = new ModelAndView();
        retVal.setViewName("/app/views/file/imageViewByFancybox.jsp");
        retVal.addObject("modelId", modelId);
        retVal.addObject("dataId", dataId);
        return retVal;
    }

    /**
     * 导入设备及设备实例
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("importDeviceData")
    @ResponseBody
    public Map<String, Object> importDeviceData(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
                    //复制文件
                    file.transferTo(dst);
                    ExcelReader excelReader = new ExcelReader();
                    File excelFile = new File(fileName);
                    InputStream inputStream = new FileInputStream(excelFile);
                    //导入的文件若是xlsx后缀，则为true,否则为false
                    boolean after2007=sufix.equals("xlsx");
                    TableEntity excelEntity = excelReader.readFile(inputStream, after2007);
                    String productId = request.getParameter("productId");
                    retVal = sparePartsMgrBusiness.importDeviceData(excelEntity, productId);
                    excelFile.delete();
                }else{
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

    @RequestMapping("exportDeviceData")
    @ResponseBody
    public void exportDeviceData(boolean exportAll, String toExportIds, String productId, HttpServletResponse response) {
        String filePath = sparePartsMgrBusiness.exportDeviceData(exportAll, toExportIds, productId);
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
     * 打印二维码
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("printQRcode")
    @ResponseBody
    public ModelAndView printQRcode(HttpServletRequest request, HttpServletResponse response, String spareInstId) throws Exception {
        return sparePartsMgrBusiness.printQRcode(request, response, spareInstId);
    }

    /**
     * 批量打印二维码
     *
     * @param path
     */
    @RequestMapping("printQRCodeImage")
    @ResponseBody
    public void printQRCodeImage(String path) {
        new PrintImage().drawImage(path);
    }

    /**
     * 批量获取打印信息
     *
     * @param deviceInstIds
     * @param spareId
     * @param spareName
     * @param deviceModel
     * @param startAll
     * @return
     */
    @RequestMapping("batchPrintQrcode")
    @ResponseBody
    public AjaxResponseData<T> batchPrintQrcode(String deviceInstIds, String spareId, String spareName, String deviceModel, boolean startAll) {
        return sparePartsMgrBusiness.batchPrintQrcode(deviceInstIds, spareId, spareName, deviceModel, startAll);
    }
}
