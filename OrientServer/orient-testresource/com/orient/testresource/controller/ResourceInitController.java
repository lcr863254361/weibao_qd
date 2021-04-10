package com.orient.testresource.controller;

import com.orient.testresource.business.ResourceInitBusiness;
import com.orient.testresource.util.TestResourceUtil;
import com.orient.web.base.BaseController;
import com.orient.web.base.CommonResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileInputStream;
import java.util.Map;

/**
 * 试验资源管理数据库初始化
 */
@Controller
@RequestMapping("/resourceInit")
public class ResourceInitController extends BaseController {
    @Autowired
    ResourceInitBusiness business;

    @RequestMapping("initResourceDB")
    @ResponseBody
    public CommonResponseData initResourceDB() {
        String sqlFilePath = TestResourceUtil.getAbsolutePath()+"../init/dsInit.sql";
        sqlFilePath = TestResourceUtil.getFormatedPath(sqlFilePath);
        Map<String, String> mapper = business.importSchema(sqlFilePath);

        sqlFilePath = TestResourceUtil.getAbsolutePath()+"../init/tableInit.sql";
        sqlFilePath = TestResourceUtil.getFormatedPath(sqlFilePath);
        business.createTables(sqlFilePath, mapper);

        sqlFilePath = TestResourceUtil.getAbsolutePath()+"../init/tempInit.sql";
        sqlFilePath = TestResourceUtil.getFormatedPath(sqlFilePath);
        business.importTemplate(sqlFilePath, mapper);

        sqlFilePath = TestResourceUtil.getAbsolutePath()+"../init/tbomInit.sql";
        sqlFilePath = TestResourceUtil.getFormatedPath(sqlFilePath);
        business.importTBom(sqlFilePath, mapper);

        return new CommonResponseData(true, "试验资源管理数据库初始化成功");
    }
}

