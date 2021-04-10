package com.orient.collabdev.business.structure;

import com.orient.businessmodel.bean.IBusinessModel;

import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-08-13 4:12 PM
 */
public interface StructCore {

    /**
     * after add model data call this method to build struct
     *
     * @param bm
     * @param dataMap
     * @param id
     */
    void add(IBusinessModel bm, Map<String, String> dataMap, String id);

    /**
     * before delete model data call this method to build struct
     *
     * @param bm
     * @param dataIds
     */
    void delete(IBusinessModel bm, String dataIds);


    /**
     * before update model data call this method to build struct
     *
     * @param bm
     * @param dataMap
     * @param dataId
     */
    void update(IBusinessModel bm, Map<String, String> dataMap, String dataId);

}
