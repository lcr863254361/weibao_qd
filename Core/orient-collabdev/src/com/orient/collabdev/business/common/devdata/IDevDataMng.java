package com.orient.collabdev.business.common.devdata;

import com.orient.devdataobj.bean.DataObjectBean;


import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author GNY
 * @Date 2018/8/16 15:46
 * @Version 1.0
 **/
public interface IDevDataMng {

    boolean createDataObj(Map<String, List<DataObjectBean>> data, String nodeId, Integer nodeVersion);

    boolean updateDataObj(Map<String, List<DataObjectBean>> data, String nodeId, Integer nodeVersion);

    boolean deleteDataObj(Map<String, List<DataObjectBean>> data, String nodeId, Integer nodeVersion);
}
