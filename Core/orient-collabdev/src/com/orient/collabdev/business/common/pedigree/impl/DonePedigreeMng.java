package com.orient.collabdev.business.common.pedigree.impl;

import com.orient.collabdev.constant.ManagerStatusEnum;
import com.orient.collabdev.business.common.annotation.MngStatus;
import com.orient.collabdev.business.common.pedigree.IPedigreeMng;
import com.orient.sysmodel.domain.collabdev.CollabNodeRelation;
import com.orient.utils.exception.OrientBaseAjaxException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-08-06 2:38 PM
 */
@MngStatus(status = ManagerStatusEnum.DONE)
@Component
public class DonePedigreeMng implements IPedigreeMng {

    @Override
    public void saveRelations(List<CollabNodeRelation> relations, String nodeId, Integer nodeVersion) {
        throw new OrientBaseAjaxException("", "已完成项目无法修改数据谱系信息");
    }
}
