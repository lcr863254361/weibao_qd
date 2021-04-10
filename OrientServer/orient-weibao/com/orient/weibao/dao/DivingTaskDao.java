package com.orient.weibao.dao;

import com.orient.weibao.dto.DivingTaskNameWithInformLog;

import java.util.List;

/**
 * @Classname DivingTaskDao
 * @Date 2020/8/19 9:44
 * @Created by SunHao
 */
public interface DivingTaskDao {
    List<DivingTaskNameWithInformLog> queryCurrentHangduanDivingTaskWithInfomLog();

    List<DivingTaskNameWithInformLog> queryCurrentHangduanDivingTaskWithPlanInfomLog();
}
