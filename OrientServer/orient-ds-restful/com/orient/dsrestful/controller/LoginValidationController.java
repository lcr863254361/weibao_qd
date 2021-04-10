package com.orient.dsrestful.controller;

import com.orient.dsrestful.business.LoginValidationBusiness;
import com.orient.web.base.dsbean.CommonResponse;
import com.orient.dsrestful.domain.login.LoginAndGetSchemaBean;
import com.orient.dsrestful.domain.login.LoginAndLockSchemaBean;
import com.orient.dsrestful.domain.login.LoginRequestBean;
import com.orient.web.base.BaseController;
import com.orient.dsrestful.domain.schemaBaseInfo.SchemaResponse;
import com.orient.dsrestful.domain.lock.LockResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 登录校验控制层
 *
 * @author GNY
 * @create 2018-03-23 20:04
 */
@Controller
@RequestMapping("/loginCheck")
public class LoginValidationController extends BaseController {

    @Autowired
    LoginValidationBusiness loginValidationBusiness;

    /**
     * 登录校验
     *
     * @param loginValidate
     * @return
     */
    @RequestMapping(value = "check", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse loginValidate(@RequestBody LoginRequestBean loginValidate) {
        return loginValidationBusiness.loginValidate(loginValidate);
    }

    /**
     * 登录校验并获取schema集合
     *
     * @param loginAndGetSchemaBean
     * @return
     */
    @RequestMapping(value = "/getSchema", method = RequestMethod.POST)
    @ResponseBody
    public SchemaResponse validateAndGetSchema(@RequestBody LoginAndGetSchemaBean loginAndGetSchemaBean) {
        return loginValidationBusiness.validateAndGetSchema(loginAndGetSchemaBean);
    }

    /**
     * 登录校验并对某个schema上锁
     *
     * @param loginAndLockSchemaBean
     * @return
     */
    @RequestMapping(value = "/lockSchema", method = RequestMethod.POST)
    @ResponseBody
    public LockResponse validateAndLockSchema(@RequestBody LoginAndLockSchemaBean loginAndLockSchemaBean) {
        return loginValidationBusiness.validateAndLockSchema(loginAndLockSchemaBean);
    }

}
