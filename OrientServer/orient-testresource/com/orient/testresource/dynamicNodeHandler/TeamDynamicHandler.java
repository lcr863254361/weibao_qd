package com.orient.testresource.dynamicNodeHandler;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.config.ConfigInfo;
import com.orient.modeldata.bean.TBomDynamicNode;
import com.orient.modeldata.bean.TBomModel;
import com.orient.modeldata.bean.TBomNode;
import com.orient.modeldata.tbomHandle.annotation.RecurrentlyDynamicNode;
import com.orient.modeldata.tbomHandle.nodeModel.BuildRecurrentlyDynamicNode;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.utils.CommonTools;
import com.orient.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2017-04-26 10:59
 */
@RecurrentlyDynamicNode(modelName="T_SYTD", order=7)
public class TeamDynamicHandler implements BuildRecurrentlyDynamicNode {

    @Autowired
    protected ISqlEngine orientSqlEngine;

    @Autowired
    public IBusinessModelService businessModelService;

    @Override
    public List<CustomerFilter> getDynamicFatherNodeFilters(IBusinessModel bm, TBomNode fatherNode) {
        List<CustomerFilter> retList = new ArrayList<>();
        List<String> idList = fatherNode.getIdList();
        if(idList!=null && idList.size()>0) {
            String schemaId = bm.getSchema().getId();
            CustomerFilter filter = new CustomerFilter("T_SYTD_"+schemaId+"_ID", EnumInter.SqlOperation.In, CommonTools.list2String(idList));
            retList.add(filter);
        }
        return retList;
    }

    @Override
    public List<CustomerFilter> getStaticFatherNodeFilters(IBusinessModel bm, TBomNode fatherNode) {
        List<CustomerFilter> retList = new ArrayList<>();
        String schemaId = ConfigInfo.DEVICE_SCHEMA_ID;
        CustomerFilter filter = new CustomerFilter("T_SYTD_"+schemaId+"_ID", EnumInter.SqlOperation.IsNull, "");
        retList.add(filter);
        return retList;
    }

    @Override
    public String getNodeText(IBusinessModel bm, Map<String, Object> resultNode) {
        return (String) resultNode.get("C_MC_" + bm.getId());
    }

    @Override
    public CustomerFilter getCustomerFilter(IBusinessModel refBm, TBomDynamicNode dynamicNode, List<Map<String, Object>> results) {
        List<String> dataIds = new ArrayList<>();
        String schemaId = ConfigInfo.DEVICE_SCHEMA_ID;
        if(results == null) {
            results = new ArrayList<>();
        }
        for(Map<String, Object> result : results) {
            String dataId = null;
            if("T_TD_RY".equals(refBm.getName())) {
                dataId = CommonTools.Obj2String(result.get("T_SYRY_"+schemaId+"_ID"));
            }
            else {
                dataId = CommonTools.Obj2String(result.get("ID"));
            }
            if (!StringUtil.isEmpty(dataId)) {
                dataIds.add(dataId);
            }
        }

        CustomerFilter filter = null;
        if("T_SYTD".equals(refBm.getName())) {
            filter = new CustomerFilter("T_SYTD_"+schemaId+"_ID", EnumInter.SqlOperation.In, CommonTools.list2String(dataIds));
        }
        else {
            filter = new CustomerFilter("ID", EnumInter.SqlOperation.In, CommonTools.list2String(dataIds));
        }
        return filter;
    }

    @Override
    public void customAllModels(List<TBomModel> tBomModels) {

    }
}
