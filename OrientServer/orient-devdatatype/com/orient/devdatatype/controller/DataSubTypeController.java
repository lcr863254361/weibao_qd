package com.orient.devdatatype.controller;

import com.orient.devdatatype.bean.DataSubTypeBean;
import com.orient.devdatatype.business.DataSubTypeBusiness;
import com.orient.devdatatype.business.DataTypeBusiness;
import com.orient.log.annotion.Action;
import com.orient.sysmodel.domain.taskdata.DataSubTypeEntity;
import com.orient.sysmodel.domain.taskdata.DataTypeEntity;
import com.orient.sysmodel.roleengine.IRoleUtil;
import com.orient.web.base.BaseController;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.ExtGridData;
import com.orient.web.springmvcsupport.DateEditor;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author mengbin
 * @create 2016-07-11 下午10:29
 */
@Controller
@RequestMapping("/DataSubType")
public class DataSubTypeController extends BaseController {
    @Autowired
    DataSubTypeBusiness dataSubTypeBusiness;

    @Autowired
    DataTypeBusiness dataTypeBusiness;

    @Autowired
    protected IRoleUtil roleEngine;


    @RequestMapping("getDataSubType")
    @ResponseBody
    @Action(ownermodel = "研发数据类型-子数据类型", detail = "查看子数据类型")
    public ExtGridData<DataSubTypeBean> getDataSubType(String dataTypeID) {
        ExtGridData<DataSubTypeBean> ret = new ExtGridData<>();
        List<DataSubTypeEntity> dataSubTypes =  dataSubTypeBusiness.getAllDataSubTypebyDataTypeId(dataTypeID);
        List<DataSubTypeBean> resluts = new ArrayList<>();

        for (DataSubTypeEntity loop: dataSubTypes){
            DataSubTypeBean bean = new DataSubTypeBean();
            try {
                PropertyUtils.copyProperties(bean, loop);
                if(loop.getUserid()!=null&&!loop.getUserid().equals("")){
                    bean.setUsername(roleEngine.getRoleModel(false).getUserById(loop.getUserid()).getAllName());
                }
                resluts.add(bean);
            }
            catch (Exception e){
                ret.setSuccess(false);
                return ret;
            }

        }
        ret.setSuccess(true);
        ret.setTotalProperty(dataSubTypes.size());
        ret.setResults(resluts);
        return ret;
    }



    @RequestMapping("createEnumDataSubType")
    @ResponseBody
    @Action(ownermodel = "研发数据类型-子数据类型", detail = "创建枚举子数据类型")
    public AjaxResponseData createEnumDataSubType(@RequestBody Map<String,List<DataSubTypeBean>> data,String dataTypeID){

        AjaxResponseData ret = new AjaxResponseData();
        List<DataSubTypeBean> dataSubTypes =   data.get("data");
        //先校验是否重名
        boolean bSuc = true;
        for(DataSubTypeBean loopDst : dataSubTypes ) {
            if (true == dataSubTypeBusiness.checkReName(loopDst)) {
                bSuc &= false;
                ret.setSuccess(false);
                ret.setMsg("子数据类型[" + loopDst.getDatasubname() + "] 创建失败.有可能是参数名称重名!");
                return ret;
            }
        }

        DataTypeEntity  parentDataType = dataTypeBusiness.upVersion(dataTypeID);    //先升级版本
        try{

            for(DataSubTypeBean loopDst : dataSubTypes ) {

                loopDst.setDatatype(parentDataType.getDatatype());// 枚举的情况下,子类型和父类型的一致
                loopDst.setIsref(parentDataType.getRank());


                DataSubTypeEntity newDst = new DataSubTypeEntity();
                PropertyUtils.copyProperties(newDst, (DataSubTypeEntity) loopDst);

                if (false == dataSubTypeBusiness.createNewDataSubType(newDst,parentDataType)) {
                    bSuc &= false;
                    ret.setMsg("子数据类型["+loopDst.getDatasubname()+"] 创建失败.有可能是参数名称重名!");
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
            //  ExtAjaxResponseData ret = new ExtAjaxResponseData();
            ret.setSuccess(false);
            ret.setMsg("系统异常: ["+ e.getMessage()+"]");
            return ret;
        }

        ret.setSuccess(bSuc);
        return  ret;

    }



    @RequestMapping("createDataSubType")
    @ResponseBody
    @Action(ownermodel = "研发数据类型-子数据类型", detail = "创建复杂子数据类型")
    public AjaxResponseData createDataSubType(@RequestBody Map<String,List<DataSubTypeBean>> data,String dataTypeID){

        AjaxResponseData ret = new AjaxResponseData();



        List<DataSubTypeBean> dataSubTypes =   data.get("data");
        //校验是否有重名
        boolean bSuc = true;
        for(DataSubTypeBean loopDst : dataSubTypes ) {
            if (true == dataSubTypeBusiness.checkReName(loopDst)) {
                bSuc &= false;
                ret.setSuccess(false);
                ret.setMsg("子数据类型[" + loopDst.getDatasubname() + "] 创建失败.有可能是参数名称重名!");
                return ret;
            }
        }

        DataTypeEntity  parentDataType = dataTypeBusiness.upVersion(dataTypeID);    //先升级版本

        try{

            for(DataSubTypeBean loopDst : dataSubTypes ) {

                DataSubTypeEntity newDst = new DataSubTypeEntity();
                PropertyUtils.copyProperties(newDst, (DataSubTypeEntity) loopDst);

                if (false == dataSubTypeBusiness.createNewDataSubType(newDst,parentDataType)) {
                    bSuc &= false;
                    ret.setMsg("子数据类型["+loopDst.getDatasubname()+"] 创建失败.有可能是参数名称重名!");
                }

            }
        }
        catch(Exception e){
            e.printStackTrace();
            //  ExtAjaxResponseData ret = new ExtAjaxResponseData();
            ret.setSuccess(false);
            ret.setMsg("系统异常: ["+ e.getMessage()+"]");
            return ret;
        }

        ret.setSuccess(bSuc);
        return  ret;

    }


    @RequestMapping("updateDataSubType")
    @ResponseBody
    @Action(ownermodel = "研发数据类型-子数据类型", detail = "更新子数据类型")
    public AjaxResponseData updateDataSubType(@RequestBody Map<String,List<DataSubTypeBean>> data,String dataTypeID){

        AjaxResponseData ret = new AjaxResponseData();

        List<DataSubTypeBean> dataSubTypes =   data.get("data");
        //校验是否有重名
        boolean bSuc = true;
        for(DataSubTypeBean loopDst : dataSubTypes ) {
            if (true == dataSubTypeBusiness.checkReName(loopDst)) {
                bSuc &= false;
                ret.setSuccess(false);
                ret.setMsg("子数据类型[" + loopDst.getDatasubname() + "] 创建失败.有可能是参数名称重名!");
                return ret;
            }
        }

        DataTypeEntity  parentDataType = dataTypeBusiness.upVersion(dataTypeID);    //先升级版本

        try{

            for(DataSubTypeBean loopDst : dataSubTypes ) {

                DataSubTypeEntity newDst = new DataSubTypeEntity();
                PropertyUtils.copyProperties(newDst, (DataSubTypeEntity) loopDst);

                if (null == dataSubTypeBusiness.updateDataSubType(newDst,parentDataType)) {
                    bSuc &= false;
                    ret.setMsg("子数据类型["+loopDst.getDatasubname()+"] 创建失败.有可能是参数名称重名!");
                }

            }
        }
        catch(Exception e){
            e.printStackTrace();
            //  ExtAjaxResponseData ret = new ExtAjaxResponseData();
            ret.setSuccess(false);
            ret.setMsg("系统异常: ["+ e.getMessage()+"]");
            return ret;
        }
        ret.setSuccess(bSuc);
        return  ret;
    }


    @RequestMapping("deleteDataSubType")
    @ResponseBody
    @Action(ownermodel = "研发数据类型-子数据类型", detail = "删除子数据类型")
    public AjaxResponseData deleteDataSubType(@RequestBody Map<String,List<DataSubTypeBean>> data,String dataTypeID) {
        AjaxResponseData ret = new AjaxResponseData();
        DataTypeEntity  parentDataType = dataTypeBusiness.upVersion(dataTypeID);    //先升级版本
        List<DataSubTypeBean> dataSubTypes =   data.get("data");
        boolean bSuc = true;
        try{

            for(DataSubTypeBean loop : dataSubTypes ) {

                DataSubTypeEntity modifiedDataSubType = new DataSubTypeEntity();
                PropertyUtils.copyProperties(modifiedDataSubType, (DataSubTypeEntity) loop);
                dataSubTypeBusiness.deleteDataSubType(modifiedDataSubType,parentDataType);
            }
        }
        catch(Exception e){
            e.printStackTrace();

            ret.setSuccess(false);
            return ret;
        }

        ret.setSuccess(bSuc);
        return  ret;
    }






    /**
     * 绑定日期转换器
     *
     * <p>
     *
     *
     * detailed comment
     *
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder)
    {

        binder.registerCustomEditor(Date.class, new DateEditor());
    }


}
