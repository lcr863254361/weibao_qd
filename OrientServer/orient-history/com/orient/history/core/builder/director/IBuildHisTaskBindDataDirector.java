package com.orient.history.core.builder.director;

import com.orient.history.core.binddata.model.TaskBindData;
import com.orient.history.core.builder.IHisTaskBindDataBuilder;

import java.util.List;

/**
 * Created by Administrator on 2016/10/4 0004.
 */
public interface IBuildHisTaskBindDataDirector {

    List<TaskBindData> doBuild(IHisTaskBindDataBuilder builder);
}
