package com.orient.example.component.dataextend;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.metamodel.metaengine.MetaUtil;
import com.orient.modeldata.bean.TBomModel;
import com.orient.modeldata.bean.TBomNode;
import com.orient.modeldata.tbomHandle.annotation.NodeHandle;
import com.orient.modeldata.tbomHandle.handle.TbomHandle;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.roleengine.IRoleUtil;
import com.orient.utils.CommonTools;
import com.orient.web.model.BaseNode;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2017-06-01 15:15
 */
@NodeHandle(order = 1, tbomName = "试验数据")
public class OriginalDataHandler implements TbomHandle {

    @Override
    public void handleTreeNodes(List<BaseNode> nodes, TBomNode fatherNode) {
        //返回值
        if (!CommonTools.isEmptyList(nodes)) {
            int level = ((TBomNode) nodes.get(0)).getLevel();
            if (4 == level) {
                handlerFourthNodes(nodes);
            }
        }
    }

    /**
     * 处理第四层节点
     *
     * @param nodes
     */
    private void handlerFourthNodes(List<BaseNode> nodes) {
        nodes.forEach(node -> {
            TBomNode tBomNode = (TBomNode) node;
            List<TBomModel> tBomModels = tBomNode.gettBomModels();
            TBomModel firstModel = tBomModels.get(0);
            //获取试验信息
            String refModelId = firstModel.getModelId();
            IBusinessModel refModel = businessModelService.getBusinessModelById(refModelId, EnumInter.BusinessModelEnum.Table);
            refModel.appendCustomerFilter(firstModel.getDefaultFilter());
            List<Map<String, String>> refDatas = orientSqlEngine.getBmService().createModelQuery(refModel).list();
            List<TBomModel> toRemoveModels = new ArrayList<>();
            if (!CommonTools.isEmptyList(refDatas)) {
                Map<String, String> refData = refDatas.get(0);
                String schemaId = refModel.getSchema().getId();
                String testTaskId = refData.get("T_TESTINFO_" + schemaId + "_ID");
                IBusinessModel mainModel = businessModelService.getBusinessModelBySName("T_TESTINFO", schemaId, EnumInter.BusinessModelEnum.Table);
                Map<String, String> mainData = orientSqlEngine.getBmService().createModelQuery(mainModel).findById(testTaskId);
                String testTaskType = mainData.get("C_TESTTYPE_" + mainModel.getId());
                for (int i = 1; i < tBomModels.size(); i++) {
                    TBomModel tBomModel = tBomModels.get(i);
                    if (!tBomModel.getModelName().contains(testTaskType.substring(0, 2))) {
                        toRemoveModels.add(tBomModel);
                    }
                }
            }
            tBomModels.removeAll(toRemoveModels);
        });
    }

    @Autowired
    protected ISqlEngine orientSqlEngine;

    @Autowired
    public IBusinessModelService businessModelService;

    @Autowired
    protected IRoleUtil roleEngine;

    @Autowired
    protected MetaUtil metaEngine;
}
