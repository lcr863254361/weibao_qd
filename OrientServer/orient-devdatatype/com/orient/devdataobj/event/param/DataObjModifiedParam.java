package com.orient.devdataobj.event.param;

import com.orient.sysmodel.domain.taskdata.DataObjectEntity;
import com.orient.web.base.OrientEventBus.OrientEventParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengbin on 16/7/22.
 * Purpose:
 * Detail: 数据修改后发起通知的事件参数
 */
public class DataObjModifiedParam extends OrientEventParams{



    public List<DataObjectEntity> modifiedDataObjList = new ArrayList<>();  //所有修改后的数据对象




}
