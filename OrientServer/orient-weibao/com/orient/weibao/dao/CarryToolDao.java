package com.orient.weibao.dao;

import com.orient.weibao.dto.CarryToolWithParams;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CarryToolDao {
    List<CarryToolWithParams> selectByDivingTaskId(@Param("taskId") String taskId,@Param("divingPlanId") String divingPlanId);
}
