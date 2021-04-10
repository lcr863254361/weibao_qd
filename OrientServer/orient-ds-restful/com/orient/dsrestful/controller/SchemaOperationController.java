package com.orient.dsrestful.controller;

import com.orient.dsrestful.business.SchemaOperationBusiness;
import com.orient.web.base.dsbean.CommonResponse;
import com.orient.dsrestful.domain.CommonSchema;
import com.orient.dsrestful.domain.SchemaContentResponse;
import com.orient.dsrestful.domain.lock.LockResponse;
import com.orient.dsrestful.domain.schemaBaseInfo.SchemaResponse;
import com.orient.dsrestful.domain.schemaXml.XmlContent;
import com.orient.web.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 1.获取一个schema的xml
 * 2.获取schema基本信息列表
 * 3.删除一个schema
 * 4.删除schema时校验该数据模型是否已经存在数据记录
 * 5.导入一份schema
 * 6.更新一份schema
 * <p>
 * Created by GNY on 2018/3/26
 */
@Controller
@RequestMapping("/schema")
public class SchemaOperationController extends BaseController {

    @Autowired
    SchemaOperationBusiness schemaOperationBusiness;

    /**
     * 获取一个schema的xml
     *
     * @param commonSchema
     * @return
     */
    @RequestMapping(value = "/getSchemaXml", method = RequestMethod.POST)
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED)
    public SchemaContentResponse obtainSchemaXml(@RequestBody CommonSchema commonSchema) {
        return schemaOperationBusiness.obtainSchemaXml(commonSchema);
    }

    /**
     * 获取所有schema的基本信息
     *
     * @return
     */
    @RequestMapping(value = "/getSchemaInfoList", method = RequestMethod.POST)
    @ResponseBody
    public SchemaResponse obtainSchemaInfoList() {
        return schemaOperationBusiness.obtainSchemaInfoList();
    }

    /**
     * 删除一个schema
     *
     * @param commonSchema
     * @return
     */
    @RequestMapping(value = "/deleteSchema", method = RequestMethod.POST)
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED)
    public CommonResponse deleteSchema(@RequestBody CommonSchema commonSchema) {
        return schemaOperationBusiness.deleteSchema(commonSchema);
    }

    /**
     * 删除数据模型时校验该数据模型是否已经存在数据记录
     *
     * @param commonSchema
     * @return
     */
    @RequestMapping(value = "/isExistData", method = RequestMethod.POST)
    @ResponseBody
    public LockResponse isExistData(@RequestBody CommonSchema commonSchema) {
        return schemaOperationBusiness.isExistData(commonSchema);
    }

    /**
     * 导入一份schema
     *
     * @param xmlContent 数据模型信息字符串
     * @return 0:成功, 1:schema已经存在, -1:异常
     */
    @RequestMapping(value = "/saveSchema", method = RequestMethod.POST)
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED)
    public CommonResponse saveSchema(@RequestBody XmlContent xmlContent) {
        return schemaOperationBusiness.saveSchema(xmlContent);
    }

    /**
     * 更新一份schema
     *
     * @param xmlContent 数据模型信息字符串
     * @return 0:成功, 1:schema已经存在, -1:异常
     */
    @RequestMapping(value = "/updateSchema", method = RequestMethod.POST)
    @ResponseBody
   // @Transactional(propagation = Propagation.REQUIRED)
    public CommonResponse updateSchema(@RequestBody XmlContent xmlContent) {
        return schemaOperationBusiness.updateSchema(xmlContent);
    }

}
