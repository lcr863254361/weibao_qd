package com.orient.weibao.controller;

import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseController;
import com.orient.weibao.bean.Point;
import com.orient.weibao.business.TaskPrepareMgrBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author js_liuyangchao@163.com
 * @create 2019-03-05 15:17
 * @desc
 **/
@Controller
@RequestMapping("/mapDatas")
public class MapDataController extends BaseController {
    @Autowired
    TaskPrepareMgrBusiness taskPrepareMgrBusiness;

    @RequestMapping("getPoints")
    @ResponseBody
    public AjaxResponseData<Object> getPoints(String schemaId, String modelId) {
        AjaxResponseData<Object> responseData = new AjaxResponseData<>();
        List<Point> pointList = taskPrepareMgrBusiness.getPoints(schemaId, modelId);
        responseData.setResults(pointList);
        return responseData;
    }


}
