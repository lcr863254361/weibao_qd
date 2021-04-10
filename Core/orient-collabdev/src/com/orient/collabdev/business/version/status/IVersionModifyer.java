package com.orient.collabdev.business.version.status;

import com.orient.businessmodel.bean.IBusinessModel;

import java.util.Map;

/**
 * 结构管理中 有关结构变化引起的版本变化
 *
 * @author panduanduan
 * @create 2018-07-28 10:01 AM
 */
public interface IVersionModifyer {

    void influentByCreate(IBusinessModel bm, Map<String, String> dataMap, String id);

    void influentByUpdate(IBusinessModel bm, Map<String, String> dataMap, String dataId, String type);

    void influentByDelete(IBusinessModel bm, String dataIds, String type);
}
