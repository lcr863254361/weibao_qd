package com.orient.collabdev.business.designing;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.web.base.BaseBusiness;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-08-07 3:25 PM
 */
@Component
public class OtherSettingsBusiness extends BaseBusiness {

    public void initData(IBusinessModel otherSettingsBM, String prjId) {
        long count = orientSqlEngine.getBmService().createModelQuery(otherSettingsBM).count();
        String modelid = otherSettingsBM.getId();
        if (count == 0) {
            otherSettingsBM.clearCustomFilter();
            otherSettingsBM.setReserve_filter(" AND ID < 0 ");
            List<Map<String, String>> queryList = orientSqlEngine.getBmService().createModelQuery(otherSettingsBM).list();
            queryList.forEach(dataMap -> {
                dataMap.remove("ID");
                dataMap.put("PRJ_ID_" + modelid, prjId);
                orientSqlEngine.getBmService().insertModelData(otherSettingsBM, dataMap);
            });
        }
    }
}
