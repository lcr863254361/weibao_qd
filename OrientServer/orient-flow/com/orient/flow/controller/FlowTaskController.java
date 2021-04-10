package com.orient.flow.controller;

import com.orient.flow.business.FlowTaskBusiness;
import com.orient.web.base.BaseController;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.CommonResponseData;
import com.orient.web.util.UserContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * FlowTaskController
 *
 * @author Seraph
 *         2016-08-20 下午2:33
 */
@Controller
@RequestMapping("/flow/task")
public class FlowTaskController extends BaseController {

    @RequestMapping("/take")
    @ResponseBody
    public CommonResponseData takeTask(@RequestParam("flowTaskId") String flowTaskId){
        return this.flowTaskBusiness.taskTask(flowTaskId, UserContextUtil.getUserAllName());
    }

    @RequestMapping("/assignee/set")
    @ResponseBody
    public CommonResponseData setAssignee(@RequestParam("piId") String piId, @RequestParam("taskName") String taskName,
                                            @RequestParam("assignee") String assignee){
        return this.flowTaskBusiness.setAssignee(piId, taskName, assignee);
    }

    @Autowired
    @Qualifier("flowTaskBusiness")
    private FlowTaskBusiness flowTaskBusiness;
}
