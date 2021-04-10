package com.orient.devdataobj.event.param;

import com.orient.devdataobj.bean.DataObjectBean;
import com.orient.sysmodel.domain.taskdata.DataObjectEntity;
import com.orient.web.base.OrientEventBus.OrientEventParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengbin on 16/8/6.
 * Purpose:
 * Detail:
 */
public class GetDataObjParam extends OrientEventParams{

    public String nodeId;
    public Integer nodeVersion;
    public int globalFlag;
    public boolean isOnlyRoot;
    public List<DataObjectBean> retDataObjs = new ArrayList<>();

}
