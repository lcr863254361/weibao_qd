package com.orient.web.springmvcsupport.global;

import com.orient.web.base.CommonResponseData;
import com.orient.web.base.dsbean.CommonDataBean;
import com.orient.web.base.dsbean.CommonResponse;
import com.orient.web.springmvcsupport.exception.CommonExceptionParser;
import com.orient.web.springmvcsupport.exception.DSException;
import com.orient.utils.exception.OrientBaseAjaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2016/8/11 0011
 */
@ControllerAdvice
public class GlobalExceptionController {

    @Autowired
    private CommonExceptionParser commonExceptionParser;

    @ExceptionHandler(OrientBaseAjaxException.class)
    @ResponseBody
    public CommonResponseData handleCustomException(OrientBaseAjaxException ex) {
        ex.printStackTrace();
        CommonResponseData commonResponseData = new CommonResponseData();
        commonResponseData.setSuccess(false);
        commonResponseData.setMsg(ex.getErrorMsg());
        return commonResponseData;
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public CommonResponseData handleUnCatchException(Exception ex) {
        ex.printStackTrace();
        return this.commonExceptionParser.parseExceptionToCommonResponseData(ex);
    }

    /**
     * ds操作时出现的异常做统一处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(DSException.class)
    @ResponseBody
    public CommonResponse handleDSException(DSException e) {
        e.printStackTrace();
        CommonResponse retVal = new CommonResponse();
        CommonDataBean result = new CommonDataBean();
        result.setStatus(e.getStatus());
        retVal.setResult(result);
        retVal.setSuccess(false);
        retVal.setMsg(e.getErrorMessage());
        return retVal;
    }

}
