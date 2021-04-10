package com.orient.collab.business.projectCore.cmd.concrete;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.collab.business.projectCore.cmd.AbstractCommand;
import com.orient.collab.business.projectCore.cmd.Command;
import com.orient.collab.business.projectCore.exception.CollabFlowControlException;
import com.orient.collab.model.Plan;
import com.orient.collab.model.StatefulModel;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.utils.CommonTools;

import java.util.List;

import static com.orient.collab.config.CollabConstants.PLAN;
import static com.orient.collab.config.CollabConstants.TASK;
import static com.orient.config.ConfigInfo.COLLAB_SCHEMA_ID;

/**
 * change state cascade command
 *
 * @author Seraph
 *         2016-08-15 下午4:11
 */
public class ChangeStateCascadeCmd extends AbstractCommand<Boolean> {

    public ChangeStateCascadeCmd(StatefulModel rootModel, String fromStatus, String toStatus, boolean includeRoot){
        this.rootModel = rootModel;
        this.fromStatus = fromStatus;
        this.toStatus = toStatus;
        this.includeRoot = includeRoot;
    }

    @Override
    public Boolean execute() throws Exception {
        List<String> ids = this.getCommandService().execute(new GetChildTaskIdsCascade((rootModel instanceof Plan)? PLAN : TASK, rootModel.getId(), fromStatus));

        if(includeRoot){
            ISqlEngine sqlEngine = OrientContextLoaderListener.Appwac.getBean(ISqlEngine.class);
            rootModel.setStatus(toStatus);
            sqlEngine.getTypeMappingBmService().update(rootModel);
        }

        IBusinessModelService businessModelService = OrientContextLoaderListener.Appwac.getBean(IBusinessModelService.class);
        IBusinessModel taskModel = businessModelService.getBusinessModelBySName(TASK, COLLAB_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);

        if(ids.size() > 0){
            StringBuffer updateSql = new StringBuffer();
            updateSql.append(" UPDATE ").append(TASK).append("_").append(COLLAB_SCHEMA_ID);
            updateSql.append(" SET STATUS_").append(taskModel.getId()).append(" = ? ");
            updateSql.append(" WHERE ID IN(").append(CommonTools.listJoinCommaToString(ids)).append(")");

            MetaDAOFactory metaDaoFactory = OrientContextLoaderListener.Appwac.getBean(MetaDAOFactory.class);
            metaDaoFactory.getJdbcTemplate().update(updateSql.toString(), new Object[]{toStatus});
        }

        return true;
    }


    private String fromStatus;
    private String toStatus;
    private boolean includeRoot;
    private StatefulModel rootModel;
}
