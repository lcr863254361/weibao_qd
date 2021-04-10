package com.orient.sysmodel.service.template.impl;

import com.orient.sysmodel.domain.template.CollabTemplate;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.template.ICollabTemplateService;
import com.orient.utils.CommonTools;
import com.orient.utils.UtilFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author Seraph
 *         2016-09-21 上午10:56
 */
@Service
public class CollabTemplateService extends BaseService<CollabTemplate> implements ICollabTemplateService {

    @Override
    public List<String> getDistinctTypes() {
        List<Map<String, Object>> types = this.jdbcTemplate.queryForList("SELECT DISTINCT TYPE FROM COLLAB_TEMPLATE");

        List<String> retV = UtilFactory.newArrayList();
        for(Map<String, Object> type : types){
            retV.add(CommonTools.Obj2String(type.get("TYPE")));
        }
        return retV;
    }

    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;
}
