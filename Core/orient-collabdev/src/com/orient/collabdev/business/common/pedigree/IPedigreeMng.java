package com.orient.collabdev.business.common.pedigree;

import com.orient.sysmodel.domain.collabdev.CollabNodeRelation;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-08-06 2:37 PM
 */
public interface IPedigreeMng {

    void saveRelations(List<CollabNodeRelation> relations, String nodeId, Integer nodeVersion);
}
