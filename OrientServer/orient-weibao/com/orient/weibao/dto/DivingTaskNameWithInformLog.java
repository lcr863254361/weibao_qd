package com.orient.weibao.dto;

import com.orient.weibao.mbg.model.DivingTask;
import com.orient.weibao.mbg.model.InformLog;

import java.util.List;

/**
 * @Classname DivingTaskNameWithInformLog
 * @Date 2020/8/19 9:45
 * @Created by SunHao
 */
public class DivingTaskNameWithInformLog extends DivingTask {
    private List<InformLog> informLogList;

    public List<InformLog> getInformLogList() {
        return informLogList;
    }

    public void setInformLogList(List<InformLog> informLogList) {
        this.informLogList = informLogList;
    }
}
