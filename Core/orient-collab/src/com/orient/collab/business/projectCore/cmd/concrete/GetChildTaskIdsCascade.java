package com.orient.collab.business.projectCore.cmd.concrete;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.collab.business.projectCore.cmd.Command;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.utils.CommonTools;
import com.orient.utils.UtilFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.orient.collab.config.CollabConstants.TASK;
import static com.orient.config.ConfigInfo.COLLAB_SCHEMA_ID;

/**
 * get children id cascade
 *
 * @author Seraph
 *         2016-08-15 下午4:30
 */
public class GetChildTaskIdsCascade implements Command<List<String>> {

    public GetChildTaskIdsCascade(String rootModelName, String rootDataIds, String status) {
        this.rootModelName = rootModelName;
        this.rootDataIds = rootDataIds;
        this.status = status;
    }

    @Override
    public List<String> execute() throws Exception {
        List<String> retV = UtilFactory.newArrayList();
        if (CommonTools.isNullString(rootDataIds)) {
            return retV;
        }

        StringBuffer querySql = new StringBuffer();

        String startName = rootModelName + "_" + COLLAB_SCHEMA_ID + "_ID";
        querySql.append(" SELECT ID ");
        querySql.append(" FROM ").append(TASK).append("_").append(COLLAB_SCHEMA_ID).append(" T ");

        if (!CommonTools.isNullString(status)) {
            IBusinessModelService businessModelService = OrientContextLoaderListener.Appwac.getBean(IBusinessModelService.class);
            IBusinessModel taskModel = businessModelService.getBusinessModelBySName(TASK, COLLAB_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);
            querySql.append(" WHERE ").append("STATUS_").append(taskModel.getId()).append(" = '").append(status).append("' ");
        }
        querySql.append(" START WITH ").append(startName).append(" in (" + rootDataIds + ") ");
        querySql.append(" CONNECT BY PRIOR ").append(" ID = ").append(TASK).append("_").append(COLLAB_SCHEMA_ID).append("_ID ");

        MetaDAOFactory metaDaoFactory = OrientContextLoaderListener.Appwac.getBean(MetaDAOFactory.class);
        List<Map<String, Object>> results = metaDaoFactory.getJdbcTemplate().queryForList(querySql.toString());

        retV.addAll(results.stream().map(res -> CommonTools.Obj2String(res.get("ID"))).collect(Collectors.toList()));
        return retV;
    }

    private String rootModelName;
    private String rootDataIds;
    private String status;
}
