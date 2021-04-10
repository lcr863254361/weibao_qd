package com.orient.devdatatype.controller;

import com.orient.devdatatype.bean.DataTypeBean;
import com.orient.devdatatype.bean.DataTypeTreeBean;
import com.orient.devdatatype.bean.RankTypeTreeBean;
import com.orient.devdatatype.business.DataTypeBusiness;
import com.orient.log.annotion.Action;
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

import java.util.*;

/**
 * @author mengbin
 * @create 2016-07-06 下午9:18
 */

@Controller
@RequestMapping("/DataType")
public class DataTypeController extends BaseController{
    @Autowired
    DataTypeBusiness dataTypeBusiness;

    @Autowired
    protected IRoleUtil roleEngine;


    @RequestMapping("getAllDataTypes")
    @ResponseBody
    @Action(ownermodel = "研发数据类型-数据类型", detail = "获取所有数据类型")
    public List<RankTypeTreeBean> getAllDataTypes(){

        try {

            //获取基础类型
            List<RankTypeTreeBean> retList = new ArrayList<>();


            RankTypeTreeBean baseRank = new RankTypeTreeBean();
            baseRank.setText("基础数据类型");
            baseRank.setExpanded(true);
            baseRank.setTag("basetype");
            List<DataTypeEntity> baseDataTypes = dataTypeBusiness.getAllDataTypeListByRank(DataTypeEntity.Rank_BASE);
            for (DataTypeEntity looDataType : baseDataTypes){
                DataTypeTreeBean bean = new DataTypeTreeBean();
                bean.setText(looDataType.getDatatypename());
                bean.setDataTypeId(looDataType.getId());
                bean.setDataType(looDataType.getDatatype());
                bean.setIsref(Short.valueOf(DataTypeEntity.Rank_BASE));
                bean.setIconCls("icon-" + looDataType.getDatatype());
                bean.setDataTypeShowName(looDataType.getDatatypename());
                bean.setExtendsTypeRealName(looDataType.getDatatype());
                baseRank.getResults().add(bean);

            }

            RankTypeTreeBean extendRank = new RankTypeTreeBean();
            extendRank.setText("扩展数据类型");
            extendRank.setExpanded(true);
            extendRank.setTag("extendtype");
            List<DataTypeEntity> extendDataTypes = dataTypeBusiness.getAllDataTypeListByRank(DataTypeEntity.Rank_EXTEND);
            for (DataTypeEntity looDataType : extendDataTypes){
                DataTypeTreeBean bean = new DataTypeTreeBean();
                bean.setText(looDataType.getDatatypename());
                bean.setDataTypeId(looDataType.getId());
                bean.setDataType(looDataType.getDatatype());
                bean.setIsref(Short.valueOf(DataTypeEntity.Rank_EXTEND));
                bean.setDataTypeShowName(looDataType.getDatatypename());
                bean.setExtendsTypeRealName(looDataType.getDatatype());
                bean.setIconCls("icon-extendDataType");
                extendRank.getResults().add(bean);
            }


            RankTypeTreeBean enumRank = new RankTypeTreeBean();
            enumRank.setText("枚举数据类型");
            enumRank.setExpanded(true);
            enumRank.setTag("enumtype");
            List<DataTypeEntity> enumDataTypes = dataTypeBusiness.getAllDataTypeListByRank(DataTypeEntity.Rank_ENUM);
            for (DataTypeEntity looDataType : enumDataTypes){
                DataTypeTreeBean bean = new DataTypeTreeBean();
                bean.setText(looDataType.getDatatypename());
                bean.setDataTypeId(looDataType.getId());
                bean.setDataType(looDataType.getDatatype());
                bean.setIsref(Short.valueOf(DataTypeEntity.Rank_ENUM));
                bean.setIconCls("icon-enumDataType");
                bean.setDataTypeShowName(looDataType.getDatatypename());
                bean.setExtendsTypeRealName(looDataType.getDatatype());
                enumRank.getResults().add(bean);
            }

            RankTypeTreeBean phyiscayRank = new RankTypeTreeBean();
            phyiscayRank.setText("复杂数据类型");
            phyiscayRank.setExpanded(true);
            phyiscayRank.setTag("physicstype");
            List<DataTypeEntity> compliactedDataTypes = dataTypeBusiness.getAllDataTypeListByRank(DataTypeEntity.Rank_PHYSIC);
            for (DataTypeEntity looDataType : compliactedDataTypes){
                DataTypeTreeBean bean = new DataTypeTreeBean();
                bean.setText(looDataType.getDatatypename());
                bean.setDataTypeId(looDataType.getId());
                bean.setDataType(looDataType.getDatatype());
                bean.setIsref(Short.valueOf(DataTypeEntity.Rank_PHYSIC));
                bean.setIconCls("icon-complexDataType");
                bean.setDataTypeShowName(looDataType.getDatatypename());
                bean.setExtendsTypeRealName(looDataType.getDatatype());
                phyiscayRank.getResults().add(bean);
            }
            retList.add(baseRank);
            retList.add(enumRank);
            retList.add(extendRank);
            retList.add(phyiscayRank);
            return retList;

        }

        catch (Exception e){
            return null;

        }




    }


    /**
     * 获取相关分类的数据类型
     * @param rank
     * @return
     */
    public ExtGridData<DataTypeBean> getDataTypeByRank(short rank) {
        ExtGridData<DataTypeBean> ret = new ExtGridData<>();
        try {

            List<DataTypeEntity> baseDataTypes = dataTypeBusiness.getAllDataTypeListByRank(rank);

            List<DataTypeBean> resluts = new ArrayList<>();
            for (DataTypeEntity loop : baseDataTypes) {
                DataTypeBean bean = new DataTypeBean();
                PropertyUtils.copyProperties(bean, loop);
                if(loop.getUserid()!=null&&!loop.getUserid().equals("")){
                    bean.setUsername(roleEngine.getRoleModel(false).getUserById(loop.getUserid()).getAllName());
                }
                resluts.add(bean);
            }
            ret.setSuccess(true);
            ret.setTotalProperty(baseDataTypes.size());
            ret.setResults(resluts);
            return ret;
        }catch (Exception e){
            ret.setSuccess(false);
            return ret;
        }

    }

    /**
     * 新增数据类型
     * @param data
     * @return
     */
    @RequestMapping("createDataType")
    @ResponseBody
    @Action(ownermodel = "研发数据类型-数据类型", detail = "创建数据类型")
    public AjaxResponseData createDataType(@RequestBody Map<String,List<DataTypeBean>> data) {

        AjaxResponseData ret = new AjaxResponseData();
        List<DataTypeBean> dataTypes =   data.get("data");
        boolean bSuc = true;
        try{

            for(DataTypeBean dataType : dataTypes ) {

                DataTypeEntity newDataType = new DataTypeEntity();
                PropertyUtils.copyProperties(newDataType, (DataTypeEntity) dataType);

                if (null == dataTypeBusiness.createDataType(newDataType)) {
                    bSuc &= false;
                    ret.setMsg("数据类型[" + dataType.getDatatypename() + "] 创建失败.有可能是参数名称重名!");
                }

            }
        }
        catch(Exception e){
            e.printStackTrace();
          //  ExtAjaxResponseData ret = new ExtAjaxResponseData();
            ret.setSuccess(false);
            ret.setMsg("系统异常: [" + e.getMessage() + "]");
            return ret;
        }

        ret.setSuccess(bSuc);
        return  ret;
    }

    @RequestMapping("updateDataType")
    @ResponseBody
    @Action(ownermodel = "研发数据类型-数据类型", detail = "更新数据类型")
    public AjaxResponseData updateDataType(@RequestBody Map<String,List<DataTypeBean>> data) {
        AjaxResponseData ret = new AjaxResponseData();

        List<DataTypeBean> dataTypes =   data.get("data");
        boolean bSuc = true;
        try{

            for(DataTypeBean dataType : dataTypes ) {

                DataTypeEntity modifiedDataType = new DataTypeEntity();
                PropertyUtils.copyProperties(modifiedDataType, (DataTypeEntity) dataType);

                if ( dataTypeBusiness.updateDataType(modifiedDataType)==false) {
                    bSuc &= false;
                    ret.setMsg("数据类型[" + dataType.getDatatypename() + "] 修改失败.有可能是参数名称重名!");
                }

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


    @RequestMapping("deleteDataType")
    @ResponseBody
    @Action(ownermodel = "研发数据类型-数据类型", detail = "删除数据类型")
    public AjaxResponseData deleteDataType(@RequestBody Map<String,List<DataTypeBean>> data) {
        AjaxResponseData ret = new AjaxResponseData();

        List<DataTypeBean> dataTypes =   data.get("data");
        boolean bSuc = true;
        try{

            for(DataTypeBean dataType : dataTypes ) {

                DataTypeEntity modifiedDataType = new DataTypeEntity();
                PropertyUtils.copyProperties(modifiedDataType, (DataTypeEntity) dataType);
                int retCode =  dataTypeBusiness.deleteDataTypeCascade(dataType.getId());

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



    @RequestMapping("getHistoryDataType")
    @ResponseBody
    @Action(ownermodel = "研发数据类型-数据类型", detail = "查看历史数据类型")
    public  ExtGridData<DataTypeBean> getHistoryDataType(String dataTypeId){

        ExtGridData<DataTypeBean> ret = new ExtGridData<>();
        try {

            DataTypeEntity dataType =  dataTypeBusiness.getDataType(dataTypeId);
            List<DataTypeEntity> baseDataTypes = dataTypeBusiness.getHistoryVersionDataType(dataType);

            List<DataTypeBean> resluts = new ArrayList<>();
            for (DataTypeEntity loop : baseDataTypes) {
                DataTypeBean bean = new DataTypeBean();
                PropertyUtils.copyProperties(bean, loop);
                if(loop.getUserid()!=null&&!loop.getUserid().equals("")){
                    bean.setUsername(roleEngine.getRoleModel(false).getUserById(loop.getUserid()).getAllName());
                }
                resluts.add(bean);
            }
            ret.setSuccess(true);
            ret.setTotalProperty(baseDataTypes.size());
            ret.setResults(resluts);
            return ret;
        }catch (Exception e){
            e.printStackTrace();
            ret.setSuccess(false);
            return ret;
        }
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
