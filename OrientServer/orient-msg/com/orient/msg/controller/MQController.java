package com.orient.msg.controller;

import com.orient.msg.bussiness.MQBussiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2015/9/18 0018.
 */
@Controller
@RequestMapping("/mq")
public class MQController {
    @Autowired
    MQBussiness mqBussiness;

//    @RequestMapping("sendCommonMsg")
//    @ResponseBody
//    public void sendCommonMsg(CommonProducerMsg msg) {
//        mqBussiness.sendCommonMsg("CommonMsg", msg);
//    }
}
