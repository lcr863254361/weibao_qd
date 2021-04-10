package com.orient.collabdev.business.common.devdata.impl;

import com.orient.collabdev.business.common.annotation.MngStatus;
import com.orient.collabdev.business.common.devdata.IDevDataMng;
import com.orient.collabdev.constant.ManagerStatusEnum;
import com.orient.devdataobj.bean.DataObjectBean;
import com.orient.utils.exception.OrientBaseAjaxException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Description 项目完成情况下，研发数据的增删改处理类
 * @Author GNY
 * @Date 2018/8/16 16:07
 * @Version 1.0
 **/
@MngStatus(status = ManagerStatusEnum.DONE)
@Component
public class ProjectDoneDataMng implements IDevDataMng {

    @Override
    public boolean createDataObj(Map<String, List<DataObjectBean>> data, String nodeId, Integer nodeVersion) {
        throw new OrientBaseAjaxException("", "已完成项目无法新增研发数据");
    }

    @Override
    public boolean updateDataObj(Map<String, List<DataObjectBean>> data, String nodeId, Integer nodeVersion) {
        throw new OrientBaseAjaxException("", "已完成项目无法修改研发数据");
    }

    @Override
    public boolean deleteDataObj(Map<String, List<DataObjectBean>> data, String nodeId, Integer nodeVersion) {
        throw new OrientBaseAjaxException("", "已完成项目无法删除研发数据");
    }

}
