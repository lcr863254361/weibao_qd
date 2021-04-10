package com.orient.modeldata.controller;

import com.orient.edm.init.FileServerConfig;
import com.orient.log.annotion.Action;
import com.orient.modeldata.business.DataAnalysisBusiness;
import com.orient.modeldata.business.ModelDataBusiness;
import com.orient.modeldata.dataanalyze.online.bean.PostSolution;
import com.orient.modeldata.dataanalyze.online.util.PostUtil;
import com.orient.modeldata.util.FtpFileUtil;
import com.orient.utils.CommonTools;
import com.orient.web.base.*;
import com.orient.web.util.UserContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.DataOutputStream;
import java.io.File;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据导入导出
 */
@Controller
@RequestMapping("/dataAnalysis")
public class DataAnalysisController extends BaseController {
    @Autowired
    FileServerConfig fileServerConfig;

    @Autowired
    ModelDataBusiness modelDataBusiness;

    @Autowired
    DataAnalysisBusiness dataAnalysisBusiness;

    @Action(ownermodel = "数据分析", detail = "Post绘图获取模型【${modelDataBusiness.getModelDisplayName(modelId,\"0\",userId)}】数据")
    @RequestMapping("postCharting")
    @ResponseBody
    public Map<String,String> postCharting(String modelId, String xAxis, String[] yAxis, String ids) {
        String userId= UserContextUtil.getUserId();
        String userName = UserContextUtil.getUserName();
        List<PostSolution> solutions = dataAnalysisBusiness.postCharting(modelId, xAxis, CommonTools.array2String(yAxis), ids);
        String folder = FtpFileUtil.getRelativeUploadPath(FtpFileUtil.EXPORT_ROOT);
        FtpFileUtil.makeDir(fileServerConfig.getFtpHome(), folder);
        File dataFolder = new File(fileServerConfig.getFtpHome() + folder);
        Map<String, DataOutputStream> osMap = null;
        try {
            osMap = PostUtil.createOutFileStream(solutions, dataFolder);
            PostUtil.writeData(solutions, osMap);
            PostUtil.writeXml(solutions, dataFolder);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        byte[] ipAddr = addr.getAddress();
        String ipAddrStr = "";
        for (int i = 0; i < ipAddr.length; i++) {
            if (i > 0) {
                ipAddrStr += ".";
            }
            ipAddrStr += ipAddr[i] & 0xFF;
        }
        Map<String,String> map = new HashMap<>();
        map.put("metaxml", dataFolder.getAbsolutePath()+"\\MetaData.xml");
        map.put("userid", userName);
        map.put("SocketIP", ipAddrStr);
        map.put("SocketPort", "6667");
        return map;
    }

    @Action(ownermodel = "数据分析", detail = "JS绘图获取模型【${modelDataBusiness.getModelDisplayName(modelId,\"0\",userId)}】数据")
    @RequestMapping("jsCharting")
    @ResponseBody
    public Map<String, Object> jsCharting(String modelId, String xAxis, String[] yAxis, String ids) {
        String userId= UserContextUtil.getUserId();
        String userName = UserContextUtil.getUserName();
        Map<String, Object> dataMap= dataAnalysisBusiness.jsCharting(modelId, xAxis, yAxis, ids);
        return dataMap;
    }

    @Action(ownermodel = "数据分析", detail = "Plot分析获取模型【${modelDataBusiness.getModelDisplayName(modelId,modelType,userId)}】数据")
    @RequestMapping("/plotAnalysis")
    @ResponseBody
    public Map<String,String> plotAnalysis(String modelId, String customFilter, String modelType, HttpServletRequest request) {
        String userId = UserContextUtil.getUserId();
        String result = dataAnalysisBusiness.plotAnalysis(modelId, customFilter, modelType);

        String ip = "127.0.0.1";
        String port = "8080";
        try {
            InetAddress ia = InetAddress.getLocalHost();
            ip = ia.getHostAddress();
            port = "" + request.getServerPort();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        Map<String,String> retMap = new HashMap<>();
        retMap.put("serviceAddr",ip);
        retMap.put("servicePort", port);
        retMap.put("fileName", result);
        return retMap;
    }

    @Action(ownermodel = "数据分析", detail = "Origin分析获取模型【${modelDataBusiness.getModelDisplayName(modelId,modelType,userId)}】数据")
    @RequestMapping("/originAnalysis")
    @ResponseBody
    public Map<String,String> originAnalysis(String modelId, String customFilter, String modelType, HttpServletRequest request) {
        String userId = UserContextUtil.getUserId();
        String result = dataAnalysisBusiness.originAnalysis(modelId, customFilter, modelType);

        String ip = "127.0.0.1";
        String port = "6669";
        try {
            InetAddress ia = InetAddress.getLocalHost();
            ip = ia.getHostAddress();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        Map<String,String> retMap = new HashMap<>();
        retMap.put("serviceAddr",ip);
        retMap.put("servicePort", port);
        retMap.put("fileName", result);
        return retMap;
    }
}

