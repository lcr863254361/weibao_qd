package com.orient.background.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.orient.sysmodel.domain.statistic.CfChartInstanceEntity;
import com.orient.sysmodel.domain.statistic.CfChartTypeEntity;
import com.orient.utils.BeanUtils;

import java.util.Collection;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2017-04-04 11:19
 */
public class StaticticChartTypeWrapper extends CfChartTypeEntity {

    @JsonIgnore
    @Override
    public Collection<CfChartInstanceEntity> getCfChartInstancesById() {
        return super.getCfChartInstancesById();
    }

    public StaticticChartTypeWrapper(CfChartTypeEntity source) {
        if (null != source) {
            BeanUtils.copyProperties(this, source);
        }
    }

    private String cls = "icon-data";

    private boolean leaf = true;

    public String getCls() {
        return cls;
    }

    public boolean isLeaf() {
        return leaf;
    }
}
