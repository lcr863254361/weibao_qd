package com.orient.collabdev.controller;

import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description 综合查询
 * @Author GNY
 * @Date 2018/8/21 16:14
 * @Version 1.0
 **/
@Controller
@RequestMapping("/compositeQuery")
public class CompositeQueryController extends BaseController {

    @RequestMapping("/getData")
    @ResponseBody
    public AjaxResponseData query() {
        //todo 待实现
        return null;
    }

}
