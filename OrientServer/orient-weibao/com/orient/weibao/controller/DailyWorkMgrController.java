package com.orient.weibao.controller;

import com.orient.web.base.BaseController;
import com.orient.weibao.business.DailyWorkMgrBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/dailyWork")
public class DailyWorkMgrController extends BaseController {
    @Autowired
    DailyWorkMgrBusiness dailyWorkMgrBusiness;

    /**
     * 每日工作附件详情
     * @param request
     * @param dailyWorkId
     * @return
     */
    @RequestMapping("dailyWorkAttachDetail")
    public ModelAndView dailyWorkAttachDetail(HttpServletRequest request, String dailyWorkId) {
        ModelAndView modelAndView = new ModelAndView();
        Map map =dailyWorkMgrBusiness.dailyWorkAttachDetail(dailyWorkId);
        if (map != null) {
            request.setAttribute("detailContent", map.get("detailContent"));
            request.setAttribute("voiceUrl", map.get("voiceUrl"));
            request.setAttribute("imageUrls",map.get("imageUrls"));
        }
        String viewName = "/app/javascript/orientjs/extjs/DailyWorkMgr/DailyWorkAttachView.jsp";
        modelAndView.setViewName(viewName);
        return modelAndView;
    }
}
