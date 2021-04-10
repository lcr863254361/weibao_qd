package com.orient.weibao.controller;

import com.alibaba.fastjson.JSON;
import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.impl.BusinessModelServiceImpl;
import com.orient.config.ConfigInfo;
import com.orient.edm.init.FileServerConfig;
import com.orient.modeldata.util.FtpFileUtil;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysman.bean.FuncBean;
import com.orient.sysmodel.domain.file.CwmFile;
import com.orient.utils.CommonTools;
import com.orient.utils.EncodingDetect;
import com.orient.utils.ExcelUtil.reader.ExcelReader;
import com.orient.utils.ExcelUtil.reader.TableEntity;
import com.orient.utils.FileOperator;
import com.orient.utils.UtilFactory;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.form.service.impl.FormService;
import com.orient.weibao.bean.HistoryTask.ImportHangciBean;
import com.orient.weibao.bean.HistoryTask.ImportHangduanBean;
import com.orient.weibao.business.HistoryTaskMgrBusiness;
import com.orient.weibao.constants.PropertyConstant;
import com.orient.weibao.utils.DeCompress;
import com.orient.weibao.utils.WeibaoPropertyUtil;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

@Controller
@RequestMapping("/historyTask")
public class HistoryTaskMgrController {

    @Autowired
    HistoryTaskMgrBusiness historyTaskMgrBusiness;
    @Autowired
    private FileServerConfig fileServerConfig;
    @Autowired
    FormService formService;
    @Autowired
    BusinessModelServiceImpl businessModelService;
    @Autowired
    ISqlEngine orientSqlEngine;

    @RequestMapping("importHangciHangduan")
    @ResponseBody
    public Map<String, Object> importHangciHangduan(HttpServletRequest request, HttpServletResponse response) throws Exception {
        IBusinessModel skillDocumentBM = businessModelService.getBusinessModelBySName(PropertyConstant.SKILL_DOCUMENT, PropertyConstant.WEI_BAO_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);
        String fileName = "";
        Map<String, Object> retVal = null;
        String encoding = WeibaoPropertyUtil.getPropertyValueConfigured("zip.encoding", "config.properties", "C:");
        String filePath = fileServerConfig.getFtpHome() + File.separator + FtpFileUtil.IMPORT_ROOT + "/导入航次航段/";
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator iter = multiRequest.getFileNames();
            if (iter.hasNext()) {
                MultipartFile file = multiRequest.getFile((String) iter.next());
                fileName = file.getOriginalFilename();
                String originalFilename = fileName;
                String fileSufix = fileName.substring(fileName.lastIndexOf(".") + 1);
                File dst = new File(fileName);
                if ("zip".equals(fileSufix)) {
                    File desZip = new File(filePath + fileName);
                    if (!desZip.exists()) {
                        desZip.mkdirs();
                    }
                    file.transferTo(desZip);
                    String zipUrl = filePath + fileName;
                    //解压zip存放文件的路径
                    String deCompressUrl = filePath + fileName.substring(0, fileName.length() - 4);
                    if (!new File(deCompressUrl).exists()) {
                        new File(deCompressUrl).mkdirs();
                    }
//                    FileOperator.unZip(zipUrl,deCompressUrl);
                    DeCompress.DecompressUtil(zipUrl, deCompressUrl, encoding);
                    FileOperator fileOperator = new FileOperator();
//                    List<String> fileList = fileOperator.getAllFileWithPath(deCompressUrl);
                    //获取Excel文件路径集合
                    List<String> fileList = fileOperator.getChildFilePath(deCompressUrl);
                    List<ImportHangciBean> importHangciBeanList = null;
                    if (fileList.size() == 1) {
                        for (String path : fileList) {
                            System.out.println(path);
                            String sufix = path.substring(path.lastIndexOf(".") + 1);
                            if ("xls".equals(sufix) || "xlsx".equals(sufix)) {
                                retVal = handleHangciHangduanExcel(request, path);
                                if ("false".equals(retVal.get("success").toString())) {
                                    return retVal;
                                } else {
                                    //json字符串转为list
                                    String hangciBeanListString = CommonTools.Obj2String(retVal.get("hangciBeanList"));
                                    importHangciBeanList = com.alibaba.fastjson.JSONArray.parseArray(hangciBeanListString, ImportHangciBean.class);
//                                 jsonArrayHangciList=JSONArray.fromObject(hangciBeanListString);
                                    System.out.println(importHangciBeanList);
//                                List<ImportHangciBean> importHangciBeanList=JSONArray.toList(jsonArray,new ImportHangciBean(), new JsonConfig());
                                }
                            } else {
                                retVal = new HashMap<>();
                                retVal.put("success", false);
                                retVal.put("msg", "压缩包必须存在一个Excel文件！");
                                desZip.delete();
                                DeCompress.deleteDirectory(filePath);
                                return retVal;
                            }
                        }
                        List<String> fileImgVideoUrlList = UtilFactory.newArrayList();
                        //获取图片视频等子文件夹下的路径集合
                        FileOperator.getChildFilePath(deCompressUrl, fileImgVideoUrlList);
                        if (fileImgVideoUrlList.size() > 0) {
                            for (String path : fileImgVideoUrlList) {
                                String urlDecodePath = "";
                                if (path.contains("\\") || path.contains("/")) {
                                    urlDecodePath = URLDecoder.decode(path, "UTF-8");
                                    System.out.println(urlDecodePath);
                                    fileName = FileOperator.getFileName(urlDecodePath);
                                }
//                            String fileType = fileName.substring(fileName.lastIndexOf("."));
                                String hangduanId = "";
                                if (importHangciBeanList != null && importHangciBeanList.size() > 0) {
                                    for (ImportHangciBean importHangciBean : importHangciBeanList) {
                                        List<ImportHangduanBean> importHangduanBeanList = importHangciBean.getImportHangduanBeanList();
                                        if (importHangciBeanList.size() > 0) {
                                            for (ImportHangduanBean importHangduanBean : importHangduanBeanList) {
                                                String importHangduanId = importHangduanBean.getHangduanId();
                                                String hangciReportVideo = importHangduanBean.getHangciReportVideo();
                                                String hangciFile = importHangduanBean.getHangciFile();
                                                String hangci = importHangduanBean.getHangciDivingMap();
                                                if (hangciReportVideo.contains(fileName)) {
                                                    hangduanId = importHangduanId;
                                                    break;
                                                } else if (hangciFile.contains(fileName)) {
                                                    hangduanId = importHangduanId;
                                                    break;
                                                } else if (hangci.contains(fileName)) {
                                                    hangduanId = importHangduanId;
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                                if (!"".equals(hangduanId) && hangduanId != null) {
                                    File rawFile = new File(urlDecodePath);
                                    FileInputStream fileInputStream = new FileInputStream(rawFile);
                                    MultipartFile multipartFile = new MockMultipartFile(rawFile.getName(), rawFile.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
                                    CwmFile cwmFile = formService.saveUploadFile(multipartFile, "common");
                                    List<Map> fileMapList = UtilFactory.newArrayList();
                                    Map packageMap = UtilFactory.newHashMap();
                                    packageMap.put("name", cwmFile.getFilename());
                                    packageMap.put("id", cwmFile.getFileid());
                                    packageMap.put("fileType", "C_File");
                                    fileMapList.add(packageMap);
                                    String fileListJsonString = JSON.toJSONString(fileMapList);
                                    Map skillDocumentMap = UtilFactory.newHashMap();
                                    skillDocumentMap.put("C_FILE_NAME_" + skillDocumentBM.getId(), fileListJsonString);
                                    skillDocumentMap.put("C_FILE_TYPE_" + skillDocumentBM.getId(), cwmFile.getFiletype());
                                    skillDocumentMap.put("T_HANGDUAN_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID", hangduanId);
                                    orientSqlEngine.getBmService().insertModelData(skillDocumentBM, skillDocumentMap);
                                }
                            }
                        }
                        retVal.put("success", true);
                        retVal.put("msg", "导入成功！");
                    } else if (fileList.size() == 0) {
                        retVal = new HashMap<>();
                        retVal.put("success", false);
                        retVal.put("msg", "压缩包必须存在一个Excel文件！");
                    } else {
                        retVal = new HashMap<>();
                        retVal.put("success", false);
                        retVal.put("msg", "压缩包中只能有一个Excel文件！");
                    }
                    desZip.delete();
                } else if ("xls".equals(fileSufix) || "xlsx".equals(fileSufix)) {
                    file.transferTo(dst);
                    retVal = handleHangciHangduanExcel(request, fileName);
                    retVal.remove("hangciBeanList");
                    if ("false".equals(retVal.get("success").toString())) {
                        return retVal;
                    } else {
                        dst.delete();
                        retVal.put("success", true);
                        retVal.put("msg", "导入成功！");
                    }
                } else {
                    retVal = new HashMap<>();
                    retVal.put("msg", "目前仅支持.xls,.xlsx,.zip文件格式");
                }
            }
        }
        DeCompress.deleteDirectory(filePath);
        return retVal;
    }

    public Map handleHangciHangduanExcel(HttpServletRequest request, String fileName) throws Exception {
        Map retVal = new HashMap();
        ExcelReader excelReader = new ExcelReader();
        File excelFile = new File(fileName);
        InputStream input = new FileInputStream(excelFile);
        boolean after2007 = fileName.substring(fileName.length() - 4).equals("xlsx");
        TableEntity excelEntity = excelReader.readFile(input, after2007);
        System.out.println("filename:" + fileName);
        if (fileName.contains("\\") || fileName.contains("/")) {
            fileName = FileOperator.getFileName(fileName);
        }
        //插入检查表模板数据
        retVal = historyTaskMgrBusiness.importHangciHangduan(excelEntity);
        excelFile.delete();
        return retVal;
    }

    @RequestMapping("importPersonWeight")
    @ResponseBody
    public Map<String, Object> importPersonWeight(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
                    boolean after2007 = sufix.equals("xlsx");
                    TableEntity excelEntity = excelReader.readFile(inputStream, after2007);
                    String hangduanId = request.getParameter("hangduanId");
                    retVal = historyTaskMgrBusiness.importPersonWeight(excelEntity, hangduanId);
                    excelFile.delete();
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
     * 批量导出人员体重
     * @param exportAll
     * @param toExportIds
     * @param hangduanId
     * @param response
     */
    @RequestMapping("exportPersonWeightData")
    @ResponseBody
    public void exportPersonWeightData(boolean exportAll, String toExportIds, String hangduanId,HttpServletResponse response) {
        String filePath = historyTaskMgrBusiness.exportPersonWeightData(exportAll, toExportIds, hangduanId);
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

    @RequestMapping("importDivingTaskData")
    @ResponseBody
    public Map<String, Object> importDivingTaskData(HttpServletRequest request, HttpServletResponse response) throws Exception {
        IBusinessModel skillDocumentBM = businessModelService.getBusinessModelBySName(PropertyConstant.SKILL_DOCUMENT, PropertyConstant.WEI_BAO_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);
        IBusinessModel divingTaskBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_TASK, PropertyConstant.WEI_BAO_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);
        IBusinessModel divingStatisticsBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_STATISTICS, PropertyConstant.WEI_BAO_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);

        String fileName = "";
        Map<String, Object> retVal = null;
        String encoding = WeibaoPropertyUtil.getPropertyValueConfigured("zip.encoding", "config.properties", "C:");
        String filePath = fileServerConfig.getFtpHome() + File.separator + FtpFileUtil.IMPORT_ROOT + "/导入潜次/";
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator iter = multiRequest.getFileNames();
            if (iter.hasNext()) {
                MultipartFile file = multiRequest.getFile((String) iter.next());
                fileName = file.getOriginalFilename();
                String originalFilename = fileName;
                String fileSufix = fileName.substring(fileName.lastIndexOf(".") + 1);
                File dst = new File(fileName);
                if ("zip".equals(fileSufix)) {
                    File desZip = new File(filePath + fileName);
                    if (!desZip.exists()) {
                        desZip.mkdirs();
                    }
                    file.transferTo(desZip);
                    String zipUrl = filePath + fileName;
                    //解压zip存放文件的路径
                    String deCompressUrl = filePath + fileName.substring(0, fileName.length() - 4);
                    if (!new File(deCompressUrl).exists()) {
                        new File(deCompressUrl).mkdirs();
                    }
//                    FileOperator.unZip(zipUrl,deCompressUrl);
                    DeCompress.DecompressUtil(zipUrl, deCompressUrl, encoding);
                    FileOperator fileOperator = new FileOperator();
//                    List<String> fileList = fileOperator.getAllFileWithPath(deCompressUrl);
                    //获取Excel文件路径集合
                    List<String> fileList = fileOperator.getChildFilePath(deCompressUrl);
                    List<ImportHangciBean> importHangciBeanList = null;
                    if (fileList.size() == 1) {
                        List excelList = UtilFactory.newArrayList();
                        for (String path : fileList) {
                            System.out.println(path);
                            String sufix = path.substring(path.lastIndexOf(".") + 1);
                            if ("xls".equals(sufix) || "xlsx".equals(sufix)) {
                                List<Map> resultList = handleDivingTaskExcel(request, path);
                                if (resultList.size() > 0) {
                                    Map resultMap = resultList.get(0);
                                    Object excelObj = resultMap.get("divingTaskDataList");
                                    if (excelObj instanceof ArrayList<?>) {
                                        for (Object o : (List<?>) excelObj) {
                                            excelList.add(Map.class.cast(o));
                                        }
                                    }
                                }
                            } else {
                                retVal = new HashMap<>();
                                retVal.put("success", false);
                                retVal.put("msg", "压缩包必须存在一个Excel文件！");
                                desZip.delete();
                                DeCompress.deleteDirectory(filePath);
                                return retVal;
                            }
                        }
                        List<String> fileImgVideoUrlList = UtilFactory.newArrayList();
                        //获取图片视频等子文件夹下的路径集合
                        FileOperator.getChildFilePath(deCompressUrl, fileImgVideoUrlList);
                        if (fileImgVideoUrlList.size() > 0) {
                            for (String path : fileImgVideoUrlList) {
                                String urlDecodePath = "";
                                if (path.contains("\\") || path.contains("/")) {
                                    urlDecodePath = URLDecoder.decode(path, "UTF-8");
                                    System.out.println(urlDecodePath);
                                    fileName = FileOperator.getFileName(urlDecodePath);
                                }
                                String divingTaskId = "";
                                if (excelList != null && excelList.size() > 0) {
                                    for (Object object : excelList) {
//                                        System.out.println(object);
                                        String waterPictureFileName = CommonTools.Obj2String(((HashMap) object).get("WATERDOWNPICTURES_" + divingStatisticsBM.getId()));
                                        String getDivingTaskId = CommonTools.Obj2String(((HashMap) object).get("divingTaskId"));
                                        if (waterPictureFileName.contains(fileName)) {
                                            divingTaskId = getDivingTaskId;
                                            break;
                                        }
                                    }
                                }
                                if (!"".equals(divingTaskId)) {
                                    File rawFile = new File(urlDecodePath);
                                    FileInputStream fileInputStream = new FileInputStream(rawFile);
                                    MultipartFile multipartFile = new MockMultipartFile(rawFile.getName(), rawFile.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
                                    CwmFile cwmFile = formService.saveUploadFile(multipartFile, "common");
                                    List<Map> fileMapList = UtilFactory.newArrayList();
                                    Map packageMap = UtilFactory.newHashMap();
                                    packageMap.put("name", cwmFile.getFilename());
                                    packageMap.put("id", cwmFile.getFileid());
                                    packageMap.put("fileType", "C_File");
                                    fileMapList.add(packageMap);
                                    String fileListJsonString = JSON.toJSONString(fileMapList);
                                    Map skillDocumentMap = UtilFactory.newHashMap();
                                    skillDocumentMap.put("C_FILE_NAME_" + skillDocumentBM.getId(), fileListJsonString);
                                    skillDocumentMap.put("C_FILE_TYPE_" + skillDocumentBM.getId(), cwmFile.getFiletype());
                                    skillDocumentMap.put("T_DIVING_TASK_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID", divingTaskId);
                                    orientSqlEngine.getBmService().insertModelData(skillDocumentBM, skillDocumentMap);
                                }
                                retVal = new HashMap<>();
                                retVal.put("success", true);
                                retVal.put("msg", "导入成功！");
                            }
                        }
                    } else if (fileList.size() == 0) {
                        retVal = new HashMap<>();
                        retVal.put("success", false);
                        retVal.put("msg", "压缩包必须存在一个Excel文件！");
                    } else {
                        retVal = new HashMap<>();
                        retVal.put("success", false);
                        retVal.put("msg", "压缩包中只能有一个Excel文件！");
                    }
                    desZip.delete();
                } else if ("xls".equals(fileSufix) || "xlsx".equals(fileSufix)) {
                    file.transferTo(dst);
                    handleDivingTaskExcel(request, fileName);
                    retVal = new HashMap<>();
                    dst.delete();
                    retVal.put("success", true);
                    retVal.put("msg", "导入成功！");
                } else {
                    retVal = new HashMap<>();
                    retVal.put("msg", "目前仅支持.xls,.xlsx,.zip文件格式");
                }
            }
        }
        DeCompress.deleteDirectory(filePath);
        return retVal;
    }

    public List handleDivingTaskExcel(HttpServletRequest request, String fileName) throws Exception {
        File excelFile = new File(fileName);
        FileInputStream fileInputStream = new FileInputStream(excelFile);
        MultipartFile multipartFile = new MockMultipartFile(excelFile.getName(), excelFile.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
        //插入检查表模板数据
        List resultList = historyTaskMgrBusiness.divingTaskDataImport(multipartFile);
        excelFile.delete();
        return resultList;
    }

    /**
     * 导出历史任务航次航段数据
     * @param exportAll
     * @param response
     */
    @RequestMapping("exportHangciHangduanData")
    @ResponseBody
    public void exportHangciHangduanData(boolean exportAll,HttpServletResponse response) {
        String filePath = historyTaskMgrBusiness.exportHangciHangduanData(exportAll);
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
}
