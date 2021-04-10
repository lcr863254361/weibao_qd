package com.orient.collabdev.controller;

import com.google.common.collect.Lists;
import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.collabdev.business.designing.OtherSettingsBusiness;
import com.orient.modeldata.business.ModelDataBusiness;
import com.orient.web.base.BaseController;
import com.orient.web.base.ExtGridData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

import static com.orient.businessmodel.Util.EnumInter.BusinessModelEnum.Table;
import static com.orient.config.ConfigInfo.COLLAB_SCHEMA_ID;

/**
 * @Description 项目其他设置相关
 * @Author ZhangSheng
 * @Date 2018/8/7 14:39
 * @Version 1.0
 **/
@Controller
@RequestMapping("/otherSettings")
public class OtherSettingsController extends BaseController {

    @Autowired
    ModelDataBusiness modelDataBusiness;

    @Autowired
    OtherSettingsBusiness otherSettingsBusiness;

    /**
     * 获取项目其他设置信息
     *
     * @param bmDataId
     * @return
     * @author ZhangSheng
     * @create 2018-08-07 10:54 AM
     */
    @RequestMapping("/queryOtherSettings")
    @ResponseBody
    public ExtGridData<Map> doQueryOtherSettings(String modelName, String bmDataId, Integer page, Integer limit, String sort) {
        //get otherSettings BusinessModel
        IBusinessModel otherSettingsBM = modelDataBusiness.businessModelService.getBusinessModelBySName(modelName, COLLAB_SCHEMA_ID, Table);
        String modelId = otherSettingsBM.getId();
        //makeup PRJ_ID CustomFilter
        CustomerFilter customerFilter = new CustomerFilter("PRJ_ID_" + modelId, EnumInter.SqlOperation.Equal, bmDataId);
        otherSettingsBM.appendCustomerFilter(customerFilter);
        //
        otherSettingsBusiness.initData(otherSettingsBM, bmDataId);
        List<CustomerFilter> customerFilters = Lists.newArrayList(customerFilter);
        //get otherSettings ModelData
        ExtGridData<Map> retVal = modelDataBusiness.getModelDataByModelId(modelId, "", page, limit, customerFilters, true, sort);
        return retVal;
    }

}
