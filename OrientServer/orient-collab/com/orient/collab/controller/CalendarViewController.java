package com.orient.collab.controller;

import com.orient.collab.business.CalendarViewBusiness;
import com.orient.collab.model.CalendarViewResponse;
import com.orient.utils.StringUtil;
import com.orient.web.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * CalendarViewController
 *
 * @author Seraph
 *         2016-08-29 下午3:23
 */
@Controller
@RequestMapping("/calendarView")
public class CalendarViewController extends BaseController{

    @RequestMapping("/events/currentUser")
    @ResponseBody
    public CalendarViewResponse getCalendarViewEvents(String start, String end, String userId){
        CalendarViewResponse retV = new CalendarViewResponse();
        if(!StringUtil.isEmpty(userId)){
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yy");
            try {
                Date formatedStartDate = dateFormat.parse(start);
                Date formatedEndDate = dateFormat.parse(end);
                retV.setEvts(this.calendarViewBusiness.getCalendarEvent(userId, formatedStartDate, formatedEndDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return retV;
    }

    @Autowired
    private CalendarViewBusiness calendarViewBusiness;
}
