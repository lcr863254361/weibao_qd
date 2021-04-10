package com.orient.collabdev.model;

import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-08-04 10:58 AM
 */
public class PedigreeRelationVO implements Serializable {

    private String srcId;

    private String destId;

    public String getSrcId() {
        return srcId;
    }

    public void setSrcId(String srcId) {
        this.srcId = srcId;
    }

    public String getDestId() {
        return destId;
    }

    public void setDestId(String destId) {
        this.destId = destId;
    }

    public static PedigreeRelationVO converDTOToVO(PedigreeNodeRelation collabNodeRelation) {
        PedigreeRelationVO relationVO = new PedigreeRelationVO();
        relationVO.setSrcId(collabNodeRelation.getSrcDevNodeId());
        relationVO.setDestId(collabNodeRelation.getDestDevNodeId());
        return relationVO;
    }
}
