package com.orient.dsrestful.controller;

import com.orient.dsrestful.business.ShareSchemaBusiness;
import com.orient.dsrestful.domain.share.JudgeCanDeleteRequest;
import com.orient.dsrestful.domain.share.ShareTableResponse;
import com.orient.dsrestful.domain.share.TableDetailResponse;
import com.orient.web.base.BaseController;
import com.orient.web.base.dsbean.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 共享模型控制层
 * Created by GNY on 2018/3/27
 */
@Controller
@RequestMapping("/share")
public class ShareSchemaController extends BaseController {

    @Autowired
    ShareSchemaBusiness shareSchemaBusiness;

    /**
     * 获取共享模型表和系统表
     *
     * @return
     */
    @RequestMapping(value = "/getShareAndDefault", method = RequestMethod.POST)
    @ResponseBody
    public ShareTableResponse getShareAndDefault() {
        return shareSchemaBusiness.getShareAndDefault();
    }

    /**
     * 获取共享模型表和系统表的xml
     */
    /**
     * @param idList
     * @return
     */
    @RequestMapping(value = "/getTableDetail", method = RequestMethod.POST)
    @ResponseBody
    public TableDetailResponse getTableDetail(@RequestParam List<String> idList) {
        return shareSchemaBusiness.getTableDetail(idList);
    }
    
    /**
     * 判断共享数据模型里的数据类、普通属性和统计属性是否可以删除
     *
     * @param judgeCanDeleteRequest type=0为判断数据类是否可以被删除；type=1为判断普通属性、统计属性是否可以删除
     * @return 0表示未删除;1表示已删除;2表示已加锁不能删除
     */
    @RequestMapping(value = "/canDelete", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse canDelete(@RequestBody JudgeCanDeleteRequest judgeCanDeleteRequest) {
        return shareSchemaBusiness.canDelete(judgeCanDeleteRequest);
    }

}
