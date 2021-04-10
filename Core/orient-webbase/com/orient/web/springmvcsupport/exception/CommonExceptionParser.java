package com.orient.web.springmvcsupport.exception;

import com.orient.web.base.CommonResponseData;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

/**
 * parse some common exception, and translate it to #{link CommonResponseData}
 *
 * @author Seraph
 *         2016-09-23 下午3:21
 */
@Component
public class CommonExceptionParser {

    public CommonResponseData parseExceptionToCommonResponseData(Exception exception){
        CommonResponseData commonResponseData = new CommonResponseData();
        commonResponseData.setSuccess(false);

        //TODO: and some other exceptions...
        if(exception instanceof DataIntegrityViolationException){
            commonResponseData.setMsg(getRootCause(exception).getMessage());
        }else{
            commonResponseData.setMsg("程序错误：" + exception.getMessage());
        }

        return commonResponseData;
    }

    private Throwable getRootCause(Throwable throwable){
        if(throwable == null){
            throw new IllegalArgumentException();
        }

        Throwable tempThrowable = throwable;
        while(tempThrowable.getCause() != null){
            tempThrowable = tempThrowable.getCause();
        }

        return tempThrowable;
    }

}
