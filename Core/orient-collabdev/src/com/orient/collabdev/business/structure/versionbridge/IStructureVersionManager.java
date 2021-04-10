package com.orient.collabdev.business.structure.versionbridge;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.collabdev.business.version.type.ICRUDVersionMng;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.utils.Pair;

import java.util.Map;

/**
 * 结构管理器
 *
 * @author panduanduan
 * @create 2018-07-28 2:26 PM
 */
public interface IStructureVersionManager {

    ICRUDVersionMng getCRUDVersionMng(String modelName, String status);

    Pair<IBusinessModel, Map<String, String>> getNodeRefBmData(CollabNode node);
}
