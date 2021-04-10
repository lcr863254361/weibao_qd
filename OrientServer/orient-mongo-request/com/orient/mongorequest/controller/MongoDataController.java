package com.orient.mongorequest.controller;

import com.orient.edm.init.FileServerConfig;
import com.orient.mongorequest.business.MongoDataBusiness;
import com.orient.mongorequest.config.MongoConfig;
import com.orient.mongorequest.domain.MatrixColumn;
import com.orient.mongorequest.domain.MatrixFileDesc;
import com.orient.mongorequest.model.*;
import com.orient.mongorequest.storage.service.IMatrixColumnService;

import com.orient.mongorequest.storage.service.IMatrixFileDescService;
import com.orient.utils.restful.DestURI;
import com.orient.utils.restful.RestfulClient;
import com.orient.utils.restful.RestfulResponse;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseController;
import com.orient.web.base.ExtGridData;
import com.orient.web.util.UserContextUtil;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.http.entity.ContentType;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 调用monogodb服务的控制层
 *
 * @author GNY
 * @create 2018-05-30 16:28
 */
@Controller
@RequestMapping("/mongoService")
public class MongoDataController extends BaseController {

    @Autowired
    FileServerConfig fileServerConfig;

    @Autowired
    MongoDataBusiness mongoDataBusiness;

    @Autowired
    IMatrixColumnService matrixColumnService;

    @Autowired
    IMatrixFileDescService matrixFileDescService;

    /**
     * 获取grid列头集合
     *
     * @param modelId
     * @param dataId
     * @return
     */
    @RequestMapping(value = "/getColumns")
    @ResponseBody
    @Transactional
    public AjaxResponseData<List<String>> getColumns(String modelId, String dataId,String tabName) {
        return new AjaxResponseData<>(mongoDataBusiness.getColumns(modelId, dataId,tabName));
    }

    /**
     * 获取当前show版本的数据
     *
     * @param modelId
     * @param dataId
     * @param showCols
     * @param operation
     * @param page
     * @param limit
     * @param filterJson
     * @param sortJson
     * @return
     */
    @RequestMapping(value = "/getCurrentVersionGridData")
    @ResponseBody
    @Transactional
    public ExtGridData getCurrentVersionGridData(String modelId, String dataId,String tabName, String showCols, String operation, Integer page, Integer limit, String filterJson, String sortJson) {
        return mongoDataBusiness.getCurrentVersionGridData(modelId, dataId, tabName,showCols, operation, page, limit, filterJson, sortJson);
    }

    /**
     * 获取临时版本的数据
     *
     * @param modelId
     * @param dataId
     * @param showCols
     * @param operation
     * @param page
     * @param limit
     * @param filterJson
     * @param sortJson
     * @return
     */
    @RequestMapping(value = "/getTempVersionGridData")
    @ResponseBody
    @Transactional
    public ExtGridData getTempVersionGridData(String modelId, String dataId, String tabName,String showCols, String operation, Integer page, Integer limit, String filterJson, String sortJson) {
        return mongoDataBusiness.getTempVersionGridData(modelId, dataId, tabName,showCols, operation, page, limit, filterJson, sortJson);
    }

    @RequestMapping(value = "/uploadFile")
    @ResponseBody
    public AjaxResponseData<FileVO> uploadFile(HttpServletRequest request, String modelId, String dataId) {
        AjaxResponseData<FileVO> retVal = new AjaxResponseData<>();
        retVal.setAlertMsg(false);
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            DefaultMultipartHttpServletRequest multipartHttpServletRequest = (DefaultMultipartHttpServletRequest) request;
            MultiValueMap<String, MultipartFile> multiFileMap = multipartHttpServletRequest.getMultiFileMap();
            multiFileMap.forEach((key, value) -> value.forEach(multipartFile -> {
                //访问文件服务
                DestURI destURI = new DestURI(MongoConfig.FILE_SERVER_HOST, MongoConfig.FILE_SERVER_PORT, MongoConfig.FILE_UPLOAD_URL, new HashMap<>());
                Map<String, Object> requestObj = new HashMap<>();
                requestObj.put("modelId", modelId);
                requestObj.put("dataId", dataId);
                requestObj.put("userId", UserContextUtil.getUserId());
                requestObj.put("fileName", multipartFile.getOriginalFilename());
                File tempFile = null;
                try {
                    //创建临时文件
                    String tempFileName = UUID.randomUUID() + multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
                    String tempFilePath = fileServerConfig.getTemp() + tempFileName;
                    tempFile = new File(tempFilePath);
                    multipartFile.transferTo(tempFile);
                    requestObj.put("file", tempFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                RestfulResponse<FileResponse> result = RestfulClient.getHttpRestfulClient().upload(destURI, requestObj, FileResponse.class, ContentType.APPLICATION_JSON);
                tempFile.delete();
                FileVO results = result.getResult().getResults();
                retVal.setResults(results);
            }));
        }
        return retVal;
    }

    @RequestMapping(value = "/insertData")
    @ResponseBody
    @Transactional
    public AjaxResponseData insertData(String fileId, String fileName, String modelId, String dataId) {
        //访问mongo服务
        DestURI insertDataUri = new DestURI(MongoConfig.MONGO_HOST, MongoConfig.MONGO_PORT, MongoConfig.MONGO_INSERT_DATA_URL + fileId + "/" + fileName + "/" + modelId + "/" + dataId, new HashMap<>());
        RestfulResponse<ColumnResponse> columnResponse = RestfulClient.getHttpRestfulClient().getRequest(insertDataUri, ColumnResponse.class, ContentType.APPLICATION_JSON);
        List<DataInsertResult> results = columnResponse.getResult().getResults();
        results.forEach(dataInsertResult -> {
            MatrixFileDesc matrixFileDesc = new MatrixFileDesc();
            matrixFileDesc.setDataId(dataId);
            matrixFileDesc.setModelId(modelId);
            matrixFileDesc.setFileName(dataInsertResult.getFileName());
            matrixFileDescService.save(matrixFileDesc);
            List<String> columnList = dataInsertResult.getColumnList();
            for (int i = 0; i < columnList.size(); i++) {
                MatrixColumn matrixColumn = new MatrixColumn();
                matrixColumn.setColumnName(columnList.get(i));
                matrixColumn.setColumnOrder(i);
                matrixColumn.setBelongMatrixFileDesc(matrixFileDesc);
                matrixColumnService.save(matrixColumn);
            }
        });

        return new AjaxResponseData();
    }

    /**
     * 删除数据
     *
     * @param modelId
     * @param dataId
     * @return
     */
    @RequestMapping(value = "/deleteData")
    @ResponseBody
    public AjaxResponseData deleteData(String modelId, String dataId) {
        return mongoDataBusiness.deleteData(modelId, dataId);
    }

    /**
     * 更新版本
     *
     * @param modelId
     * @param dataId
     * @return
     */
    @RequestMapping(value = "/updateVersion")
    @ResponseBody
    public AjaxResponseData updateVersion(String modelId, String dataId,String tabName) {
        return mongoDataBusiness.updateVersion(modelId, dataId,tabName);
    }

    @RequestMapping(value = "/queryVersionList")
    @ResponseBody
    public AjaxResponseData<List<DataVersion>> queryVersionList(String modelId, String dataId,String tabName) {
        return mongoDataBusiness.queryVersionList(modelId, dataId,tabName);
    }

    /**
     * 切换当前显示的版本
     *
     * @param modelId
     * @param dataId
     * @param versionId
     * @return
     */
    @RequestMapping(value = "/switchVersion")
    @ResponseBody
    public AjaxResponseData switchVersion(String modelId, String dataId,String tabName, String versionId) {
        return mongoDataBusiness.switchVersion(modelId, dataId, tabName,versionId);
    }

    /**
     * 获取当前用户的修改数据的权限
     *
     * @param modelId
     * @param dataId
     * @param versionId
     * @return
     */
    @RequestMapping(value = "/beginEdit")
    @ResponseBody
    public AjaxResponseData<String> beginEdit(String modelId, String dataId, String tabName,String versionId) {
        return mongoDataBusiness.beginEdit(modelId, dataId, tabName,versionId);
    }

    /**
     * 修改数据
     *
     * @param modelId
     * @param dataId
     * @param modifyDataList
     * @return
     */
    @RequestMapping(value = "/modifyData")
    @ResponseBody
    public AjaxResponseData modifyData(String modelId, String dataId, String tabName,String modifyDataList) {
        return mongoDataBusiness.modifyData(modelId, dataId, tabName,modifyDataList);
    }

    /**
     * 判断当前用户是否处于编辑数据中
     * 1.如果处于编辑数据中，显示temp表中的数据
     * 2.如果没有处于编辑中，显示设置版本的数据
     *
     * @param modelId
     * @param dataId
     * @return
     */
    @RequestMapping(value = "/judgeCanEditData")
    @ResponseBody
    public AjaxResponseData<CanEditData> judgeCanEditData(String modelId, String dataId,String tabName) {
        return mongoDataBusiness.judgeCanEditData(modelId, dataId,tabName);
    }

    /**
     * 把temp数据回滚到最新版本的数据
     *
     * @param modelId
     * @param dataId
     * @return
     */
    @RequestMapping(value = "/rollbackLastVersion")
    @ResponseBody
    public AjaxResponseData rollbackLastVersion(String modelId, String dataId,String tabName) {
        return mongoDataBusiness.rollbackLastVersion(modelId, dataId,tabName);
    }

    /**
     * 数据分析
     *
     * @param modelId
     * @param dataId
     * @param request
     * @return
     */
    @RequestMapping("/dataAnalysis")
    @ResponseBody
    @Transactional
    public Map<String, String> dataAnalysis(String modelId, String dataId, String tabName,String filterJson, boolean canEditData, String[] dataIdFilter, HttpServletRequest request) {
        String result = mongoDataBusiness.dataAnalysis(modelId, dataId, tabName,filterJson, canEditData, dataIdFilter, request);
        String ip = "127.0.0.1";
        String port = "8088";
        try {
            InetAddress ia = InetAddress.getLocalHost();
            ip = ia.getHostAddress();
            port = "" + request.getServerPort();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> retMap = new HashMap<>();
        retMap.put("serviceAddr", ip);
        retMap.put("servicePort", port);
        retMap.put("fileName", result);
        return retMap;
    }

    @RequestMapping("/getTabs")
    @ResponseBody
    public AjaxResponseData<List<String>> getTabs(String modelId, String dataId) {
       return new AjaxResponseData<>(mongoDataBusiness.getTabs(modelId,dataId));
    }

}
