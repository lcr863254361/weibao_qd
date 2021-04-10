package com.orient.weibao.controller;

import com.orient.utils.StringUtil;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseController;
import com.orient.weibao.business.WeibaoDataAnalysisBusiness;
import com.orient.weibao.utils.WeibaoPropertyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;


@Controller
@RequestMapping("analysis")
public class WeibaoDataAnalysisController extends BaseController {

    @Autowired
    private WeibaoDataAnalysisBusiness weibaoDataAnalysisBusiness;
    public static   HashMap params = new HashMap();
    static {

        params.put("C_CONDUCTIVITY","电导率");
        params.put("C_TEMPERATURE","温度");
        params.put("C_DEPTH","深度1");
        params.put("C_SOUND","声度");
        params.put("C_SALINITY","盐度");
        params.put("C_DENSITY","密度");
        params.put("C_DEPTH_2","深度2");
        params.put("C_AFTER_LONGITUDE","融合后经度");
        params.put("C_AFTER_LATITUDE","融合后纬度");
        params.put("C_TIME","时间");
    }

    @ResponseBody
    @RequestMapping("getField")
    public AjaxResponseData  getAvailableFieldToChart(@RequestParam("checkTempId") String checkTempId ){
        System.out.println("checkTempId:"+checkTempId);
       Map<String, List> retValue = weibaoDataAnalysisBusiness.getAvailableFields(checkTempId);

        AjaxResponseData ajaxResponseData = new AjaxResponseData(retValue);
        return ajaxResponseData;

    }
    @ResponseBody
    @RequestMapping("generateChart")
    public AjaxResponseData  generateChart(@RequestParam("cellId") String cellId ) throws IOException {
        List<Map> retList = weibaoDataAnalysisBusiness.getCellInstanceData(cellId);

        AjaxResponseData ajaxResponseData = new AjaxResponseData(retList);
        return ajaxResponseData;

    }

    @ResponseBody
    @RequestMapping("uploadChartData")
    public AjaxResponseData uploadChartData(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "voyageId",defaultValue = "123") String voyageId) throws IOException, ParseException {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        String location = WeibaoPropertyUtil.getPropertyValueConfigured("zip.upload.path","config.properties","C:");
        String filePath = location+"\\data\\orient\\txt\\";
        if(multipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator iter = multiRequest.getFileNames();
            if(iter.hasNext()){
                MultipartFile file = multiRequest.getFile((String) iter.next());
                String fileName = file.getOriginalFilename();
                System.out.println("fileName:"+fileName);
                File destFile = new File(filePath+fileName);
                File parentFile = destFile.getParentFile();
                if(!parentFile.exists()){
                    parentFile.mkdirs();
                }
                destFile.createNewFile();
                file.transferTo(destFile);
                weibaoDataAnalysisBusiness.uploadChartData(destFile,voyageId);
            }
        }
        AjaxResponseData ajaxResponseData = new AjaxResponseData();
        ajaxResponseData.setMsg("上传成功");
        ajaxResponseData.setSuccess(true);
        return  ajaxResponseData;
    }


    @RequestMapping("getChartByParam")
    @ResponseBody
    public AjaxResponseData getChartByParam(@RequestParam("x") String x,@RequestParam(value = "y1",required = true) String y1,
                                            @RequestParam(value = "y2",required = false) String y2, @RequestParam(value = "y3",required = false) String y3,
                                            @RequestParam("voyageId") String voyageId,
                                            @RequestParam(value = "beginTime",required = false) String beginTime,
                                            @RequestParam(value = "endTime",required = false) String endTime) throws ParseException {
        String msg="";
        long startTime = System.currentTimeMillis();
        List<Map> list = new ArrayList<>();
        if(StringUtil.isNotEmpty(y1)){
            Map map1 = getData(x,y1,voyageId);
            list.add(map1);
            msg += weibaoDataAnalysisBusiness.getMaxAndMinValue(y1,voyageId);
        }
        if(StringUtil.isNotEmpty(y2)){
            Map map2 = getData(x,y2,voyageId);
            list.add(map2);
            msg += weibaoDataAnalysisBusiness.getMaxAndMinValue(y2,voyageId);
        }
        if(StringUtil.isNotEmpty(y3)){
            Map map3 = getData(x,y3,voyageId);
            list.add(map3);
            msg += weibaoDataAnalysisBusiness.getMaxAndMinValue(y3,voyageId);
        }
        long completeTime = System.currentTimeMillis();
        System.out.println((completeTime-startTime)+"s");
        AjaxResponseData ajaxResponseData = new AjaxResponseData(list);
        ajaxResponseData.setAlertMsg(false);
        ajaxResponseData.setMsg(msg);
        return ajaxResponseData;
    }

    public Map getData(String x,String y,String voyageId) throws ParseException {
        Map map = new HashMap();
        map.put("name",params.get(y));
        List<List> results = weibaoDataAnalysisBusiness.getChartByParamFromMongo(x,y,voyageId);
        map.put("data",results);
        return map;
    }

    @RequestMapping("/getChart")
    @ResponseBody
    public  AjaxResponseData   getCharts(@RequestParam("voyageId") String voyageId) throws IOException, ParseException {
        List<List> results = weibaoDataAnalysisBusiness.getChartByParamFromMongo("C_TIME","C_TEMPERATURE",voyageId);
        AjaxResponseData ajaxResponseData = new AjaxResponseData(results);
        return ajaxResponseData;
    }


}
