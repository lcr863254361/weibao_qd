package com.orient.collabdev.business.structure.extensions.mng;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.collabdev.business.structure.constant.StructOperateType;
import com.orient.sysmodel.domain.collabdev.CollabNode;

import java.util.List;
import java.util.Map;

public interface CollabDevStructInterceptor {

    boolean preHandle(IBusinessModel bm, List<Map<String, String>> sourceMap, List<Map<String, String>> targetMap, CollabNode rootNode
            ,StructOperateType structOperateType) throws Exception;

    void afterCompletion(IBusinessModel bm, List<Map<String, String>> sourceMap, List<Map<String, String>> targetMap, CollabNode rootNode
            ,StructOperateType structOperateType,Object processResult) throws Exception;
}
