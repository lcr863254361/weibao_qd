package com.orient.collabdev.business.structure.util;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.sysmodel.domain.collabdev.CollabNode;

import java.util.Map;

/**
 * Created by Administrator on 2018/7/27 0027.
 */
public interface NodePOConver {

    CollabNode converToCollabNode(Map<String,String> dataMap, IBusinessModel businessModel);
}
