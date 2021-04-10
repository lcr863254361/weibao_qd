package com.orient.background.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.orient.sysmodel.domain.statistic.CfStatiscticEntity;
import com.orient.sysmodel.domain.statistic.CfStatisticFilterEntity;
import com.orient.utils.BeanUtils;

/**
 * Created by Administrator on 2017/4/5 0005.
 */
public class StatisticFilterSetUpWrapper extends CfStatisticFilterEntity {

    @JsonIgnore
    @Override
    public CfStatiscticEntity getCfStatiscticByCfStatisticId() {
        return super.getCfStatiscticByCfStatisticId();
    }

    public StatisticFilterSetUpWrapper(CfStatisticFilterEntity source) {
        if (null != source) {
            BeanUtils.copyProperties(this, source);
        }
    }
}
