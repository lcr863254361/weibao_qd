package com.orient.modeldata.controller;

import com.orient.edm.init.FileServerConfig;
import com.orient.log.annotion.Action;
import com.orient.modeldata.analyze.bean.Configuration;
import com.orient.modeldata.bean.preview.Column;
import com.orient.modeldata.bean.preview.PreviewGridModel;
import com.orient.modeldata.business.DataImportExportBusiness;
import com.orient.modeldata.business.ModelDataBusiness;
import com.orient.modeldata.util.FtpFileUtil;
import com.orient.utils.FileOperator;
import com.orient.utils.Log.LogThreadLocalHolder;
import com.orient.web.base.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数据导入导出
 */
@Controller
@RequestMapping("/dataImportExport")
public class DataImportExportController extends BaseController {

    @Autowired
    FileServerConfig fileServerConfig;

    @Autowired
    ModelDataBusiness modelDataBusiness;

    @Autowired
    DataImportExportBusiness dataImportExportBusiness;

    @Action(ownermodel = "数据管理", detail = "上传文件【${filePath}】")
    @RequestMapping("uploadAndPreview")
    @ResponseBody
    public PreviewGridModel uploadAndPreview(HttpServletRequest request, Configuration config) {
        PreviewGridModel retVal = new PreviewGridModel();

        String columnSplit = config.getColumnSplit();
        if(!(columnSplit==null)) {
            try {
                config.setColumnSplit(URLDecoder.decode(columnSplit, "UTF-8"));
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        String rowSplit = config.getRowSplit();
        if(!(rowSplit==null)) {
            try {
                config.setRowSplit(URLDecoder.decode(rowSplit, "UTF-8"));
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        String folder = FtpFileUtil.getRelativeUploadPath(FtpFileUtil.IMPORT_ROOT);
        FtpFileUtil.makeDir(fileServerConfig.getFtpHome(), folder);
        List<String> files = FileOperator.uploadFile(request, fileServerConfig.getFtpHome()+folder);
        if(files.size() == 0) {
            retVal.setSuccess(false);
            retVal.setMsg("文件上传失败！");
            return retVal;
        }

        for(int i=0; i<files.size(); i++) {
            String path = files.get(i);
            if("jar".equalsIgnoreCase(FileOperator.getSuffix(path))) {
                config.setJarPath(path);
            }
            else {
                config.setFilePath(path);
            }
        }
        Map<String, Object> resultMap = dataImportExportBusiness.doPreviewAnalyze(config);
        config.setAnalyzeType(null);
        retVal.setConfig(config);
        retVal.setData((List<Map<String, String>>) resultMap.get("content"));

        List<Column> columns = new ArrayList<>();
        List<String> headers = (List<String>) resultMap.get("headers");
        for(String header : headers) {
            Column column = new Column();
            column.setHeader(header);
            column.setDataIndex(header);
            column.setFlex(1);
            columns.add(column);
        }
        retVal.setHeaders(headers);
        retVal.setColumns(columns);

        LogThreadLocalHolder.putParamerter("filePath", config.getFilePath());
        return retVal;
    }

    @Action(ownermodel = "数据管理", detail = "导入模型【${tableName}】")
    @RequestMapping("saveImportData")
    @ResponseBody
    public CommonResponseData saveImportData(@RequestBody Configuration config) {
        CommonResponseData retVal = new CommonResponseData();

        String info = dataImportExportBusiness.doDBInputAnalyze(config);
        if(info == null) {
            retVal.setMsg("数据导入成功");
        }
        else {
            retVal.setSuccess(false);
            retVal.setMsg(info);
        }

        LogThreadLocalHolder.putParamerter("tableName", config.getTableName());
        return retVal;
    }

    @Action(ownermodel = "数据管理", detail = "导出模型【${modelDataBusiness.getModelDisplayName(modelId,\"0\",userId)}】")
    @RequestMapping("downloadTemplateFile")
    public void downloadTemplateFile(HttpServletRequest request, HttpServletResponse response, String modelId) {
        String filePath = dataImportExportBusiness.prepareModelTemplateFile(modelId);
        FileOperator.downLoadFile(request, response, filePath, "数据导入模板.xls");
    }

    @Action(ownermodel = "数据管理", detail = "导入模型【${modelDataBusiness.getModelDisplayName(modelId,\"0\",userId)}】数据预处理")
    @RequestMapping("preapareExportData")
    @ResponseBody
    public AjaxResponseData<String> preapareExportData(String modelId, String isView, String customerFilter) {
        AjaxResponseData<String> retVal = new AjaxResponseData();
        String fileName = dataImportExportBusiness.preapareExportData(modelId, isView, customerFilter);
        retVal.setResults(fileName);
        return retVal;
    }
}

