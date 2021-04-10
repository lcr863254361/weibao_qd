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
 * 扩展类型
 *
 * @author mengbin
 * @create 2016-07-06 下午9:14
 */
@Controller
@RequestMapping("/ExtendDataType")
public class ExtendDataTypeController extends  DataTypeController{

    @Autowired
    DataTypeBusiness dataTypeBusiness;

    /**
     * 获取最新版本的扩展类型列表
     * @return
     */
    @RequestMapping("getExtendDataType")
    @ResponseBody
    public ExtGridData<DataTypeBean> getExtendDataType() {

        return this.getDataTypeByRank(DataTypeEntity.Rank_EXTEND);
    }
}
