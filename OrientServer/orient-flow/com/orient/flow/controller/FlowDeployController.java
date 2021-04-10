package com.orient.flow.controller;

import com.orient.flow.business.FlowDeployBusiness;
import com.orient.web.base.BaseController;
import com.orient.web.base.CommonResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * the flow deploy controller
 *
 * @author Seraph
 *         2016-08-02 下午4:18
 */
@Controller
@RequestMapping("/flow")
public class FlowDeployController extends BaseController{

    @RequestMapping("/deploy")
    @ResponseBody
    public CommonResponseData deploy(@RequestParam("fileName") String fileName, @RequestParam("userName") String userName,
                                       @RequestParam("dataId") String dataId, @RequestParam("rootType") String rootType, MultipartFile file){
        try {
            return this.flowDeployBusiness.deploy(fileName, userName, dataId, rootType, file);
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResponseData(false, "读取部署文件出错");
        }
    }

    @Autowired
    private FlowDeployBusiness flowDeployBusiness;
}
