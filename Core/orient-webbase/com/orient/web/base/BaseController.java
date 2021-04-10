package com.orient.web.base;

import com.orient.utils.Log.LogThreadLocalHolder;
import com.orient.web.springmvcsupport.DateEditor;
import com.orient.web.util.UserContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class BaseController {

    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    public static String CommonResponseDataSessionName = "CommonResponseDataSessionName";

    @ModelAttribute
    public void central(HttpServletRequest request) {
        String userId = UserContextUtil.getUserId();

        LogThreadLocalHolder.putParamerter("userId", userId);
    }

    /**
     * 时间格式处理
     *
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new DateEditor());
    }
}
