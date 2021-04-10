package com.orient.testresource.staticNodeHandler;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.bean.IdQueryCondition;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.config.ConfigInfo;
import com.orient.modeldata.bean.TBomModel;
import com.orient.modeldata.tbomHandle.annotation.StaticNodeModel;
import com.orient.modeldata.tbomHandle.nodeModel.AddStaticNodeModel;
import com.orient.sqlengine.api.IBusinessModelQuery;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.testresource.util.TestResourceMgrConstants;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2017-04-26 10:59
 */
@StaticNodeModel(tbomName="试验件管理", order=4)
public class UnitHandler implements AddStaticNodeModel {

    @Autowired
    protected ISqlEngine orientSqlEngine;

    @Autowired
    public IBusinessModelService businessModelService;

    @Override
    public IBusinessModel getBusinessModel() {
        String schemaId = ConfigInfo.DEVICE_SCHEMA_ID;
        IBusinessModel bm = businessModelService.getBusinessModelBySName("T_SYJ", schemaId, EnumInter.BusinessModelEnum.Table);
        return bm;
    }

    @Override
    public String getTemplateName() {
        return TestResourceMgrConstants.TPL_SYJ;
    }

    @Override
    public CustomerFilter getCustomerFilter(IBusinessModel bm) {
        IBusinessModelQuery modelquery = orientSqlEngine.getBmService().createModelQuery(bm);
        IdQueryCondition idQueryCondition = modelquery.getIdQueryCondition();
        return new CustomerFilter(idQueryCondition);
    }

    @Override
    public void customAllModels(List<TBomModel> tBomModels) {

    }
}
