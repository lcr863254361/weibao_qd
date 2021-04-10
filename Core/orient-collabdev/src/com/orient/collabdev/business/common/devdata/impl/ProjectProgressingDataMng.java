package com.orient.collabdev.business.common.devdata.impl;

import com.orient.collabdev.business.common.annotation.MngStatus;
import com.orient.collabdev.constant.ManagerStatusEnum;
import org.springframework.stereotype.Component;

/**
 * @Description 项目运行情况下，研发数据的增删改处理类
 * 因为只有在任务提交的时候，才提升任务节点的版本，所以项目进行中的处理和项目没有启动时的处理一样
 * @Author GNY
 * @Date 2018/8/16 16:07
 * @Version 1.0
 **/
@MngStatus(status = ManagerStatusEnum.PROCESSING)
@Component
public class ProjectProgressingDataMng extends ProjectUnStartDevDataMng {

}
