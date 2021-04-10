package com.orient.devdatatype.controller;

import com.orient.devdatatype.bean.DataTypeBean;
import com.orient.devdatatype.business.DataTypeBusiness;
import com.orient.sysmodel.domain.taskdata.DataTypeEntity;
import com.orient.web.base.ExtGridData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author mengbin
 * @create 2016-07-11 下午10:35
 */
@Controller
@RequestMapping("/ComplicateDataType")
public class ComplicateDataTypeController extends DataTypeController{
    @Autowired
    DataTypeBusiness dataTypeBusiness;

    @RequestMapping("getComplicateDataType")
    @ResponseBody
    public ExtGridData<DataTypeBean> getComplicateDataType() {

        return this.getDataTypeByRank(DataTypeEntity.Rank_PHYSIC);

    }
}
