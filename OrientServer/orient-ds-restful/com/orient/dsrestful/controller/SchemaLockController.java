package com.orient.dsrestful.controller;

import com.orient.dsrestful.business.SchemaLockBusiness;
import com.orient.dsrestful.domain.lock.LockResponse;
import com.orient.dsrestful.domain.lock.SchemaLockBean;
import com.orient.web.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * schema上锁，解锁，强制解锁
 *
 * @author GNY
 * @create 2018-03-24 11:01
 */
@Controller
@RequestMapping("/schemaLock")
public class SchemaLockController extends BaseController {

    @Autowired
    SchemaLockBusiness schemaLockBusiness;

    /**
     * 设置某个schema上锁或解锁 0表示客户端想解锁，1表示客户端想上锁
     *
     * @param schemaLockBean
     * @return
     */
    @RequestMapping(value = "/setLock", method = RequestMethod.POST)
    @ResponseBody
    public LockResponse setLock(@RequestBody SchemaLockBean schemaLockBean) {
        return schemaLockBusiness.setLock(schemaLockBean);
    }

    /**
     * 强制解锁
     *
     * @param schemaIdList
     * @return
     */
    @RequestMapping(value = "/forceUnlock", method = RequestMethod.POST)
    @ResponseBody
    public LockResponse forceUnlock(@RequestBody List<String> schemaIdList) {
        return schemaLockBusiness.forceUnlock(schemaIdList);
    }

}
