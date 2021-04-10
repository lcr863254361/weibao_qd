package com.orient.devdatatype.controller;

import com.orient.devdatatype.bean.DataTypeBean;
import com.orient.devdatatype.business.DataTypeBusiness;
import com.orient.sysmodel.domain.taskdata.DataTypeEntity;
import com.orient.web.base.ExtGridData;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mengbin
 * @create 2016-07-06 下午3:25
 */
@Controller
@RequestMapping("/BaseDataType")
public class BaseDataTypeController extends  DataTypeController{

    @Autowired
    DataTypeBusiness dataTypeBusiness;
    @RequestMapping("getBaseDataType")
    @ResponseBody
    public ExtGridData<DataTypeBean> getBaseDataType() {

        return this.getDataTypeByRank(DataTypeEntity.Rank_BASE);

    }
}
