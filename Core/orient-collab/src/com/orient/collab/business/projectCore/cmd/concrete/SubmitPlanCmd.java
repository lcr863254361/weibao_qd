package com.orient.collab.business.projectCore.cmd.concrete;

import com.orient.collab.business.projectCore.cmd.Command;
import com.orient.collab.model.Plan;
import com.orient.devdataobj.business.DataObjectBusiness;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.domain.taskdata.DataObjectEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.orient.collab.config.CollabConstants.STATUS_FINISHED;

/**
 * submit a plan
 *
 * @author Seraph
 * 2016-08-18 上午9:23
 */
public class SubmitPlanCmd implements Command<Boolean> {

    public SubmitPlanCmd(Plan plan) {
        this.plan = plan;
    }

    @Override
    public Boolean execute() throws Exception {
        String sDate = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
        plan.setStatus(STATUS_FINISHED);
        plan.setActualEndDate(sDate);

        ISqlEngine sqlEngine = OrientContextLoaderListener.Appwac.getBean(ISqlEngine.class);

        sqlEngine.getTypeMappingBmService().update(plan);
        String modelId = sqlEngine.getTypeMappingBmService().getModelId(Plan.class);
        //升级设计数据的版本
        DataObjectBusiness dataObjectBusiness = OrientContextLoaderListener.Appwac.getBean(DataObjectBusiness.class);
        //todo 代码待修改
       /* List<DataObjectEntity> dataObjectEntityList= dataObjectBusiness.getAllCurrentDataObject(modelId,plan.getId(),3,true);
        for(DataObjectEntity dataObjectEntity: dataObjectEntityList){
            dataObjectBusiness.jobSubmitUpVersion(dataObjectEntity);
        }*/
        return true;
    }

    private Plan plan;
}
