package com.orient.edm.web.validator.controller;

import com.orient.edm.web.validator.service.IValidatorService;
import com.orient.web.base.CommonResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by enjoy on 2016/3/17 0017.
 */
@Controller
@RequestMapping("/orientValidator")
public class OrientValidatorController {

    @Autowired
    IValidatorService validatorService;

    @RequestMapping("unique")
    @ResponseBody
    public CommonResponseData unique(String columnName, String modelName, String toValidateValue) {
        CommonResponseData retVal = new CommonResponseData();
        Boolean pass = validatorService.validateUnique(columnName, modelName, toValidateValue);
        retVal.setSuccess(pass);
        retVal.setAlertMsg(false);
        return retVal;
    }
}
