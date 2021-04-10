package com.orient.weibao.dao;

import com.orient.weibao.dto.DivingPlanTableDaoWithToolsAndDeepth;
import com.orient.weibao.mbg.model.DivingPlanTable;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DivingPlanTableDao {

    List<DivingPlanTableDaoWithToolsAndDeepth> selectListWithToolsAndDeepth(@Param("taskId") String taskId);

    String getHangDuanIdByDivingTaskId(@Param("taskId") String taskId);
}
