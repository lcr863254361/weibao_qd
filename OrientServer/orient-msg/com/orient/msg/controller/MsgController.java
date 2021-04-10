package com.orient.msg.controller;

import com.orient.msg.bussiness.MsgBussiness;
import com.orient.sysmodel.domain.mq.CwmMsg;
import com.orient.web.util.UserContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/9/18 0018.
 */
@Controller
@RequestMapping("/msg")
public class MsgController {
    @Autowired
    MsgBussiness msgBussiness;

    @RequestMapping("getUserMsgs")
    @ResponseBody
    public Map<String, Object> getUserMsgs(Boolean readed) {
        Map<String, Object> retMap = new HashMap<>();
        Long userId = Long.valueOf(UserContextUtil.getUserId());
        List<CwmMsg> msgs = msgBussiness.getMsgByUserId(userId, readed);

        retMap.put("success", true);
        retMap.put("total", msgs.size());
        retMap.put("results", msgs);
        return retMap;
    }

    @RequestMapping("getUserMsgsCnt")
    @ResponseBody
    public Integer getUserMsgsCnt(Boolean readed) {
        Long userId = Long.valueOf(UserContextUtil.getUserId());
        return msgBussiness.getMsgCntByUserId(userId, readed);
    }

    @RequestMapping("markReaded")
    @ResponseBody
    public Boolean markReaded(@RequestBody ArrayList<Long> ids, Boolean readed) {
        msgBussiness.markReaded(ids, readed);
        return true;
    }

    @RequestMapping("deleteMsgs")
    @ResponseBody
    public Boolean deleteMsgs(@RequestBody ArrayList<Long> ids) {
        msgBussiness.deleteMsgs(ids);
        return true;
    }
}
