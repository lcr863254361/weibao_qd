package com.orient.collabdev.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-08-04 10:54 AM
 */
public class PedigreeVO implements Serializable {

    private List<PedigreeNodeVO> nodes = new ArrayList<>();

    private List<PedigreeRelationVO> relations = new ArrayList<>();

    public List<PedigreeNodeVO> getNodes() {
        return nodes;
    }

    public void setNodes(List<PedigreeNodeVO> nodes) {
        this.nodes = nodes;
    }

    public List<PedigreeRelationVO> getRelations() {
        return relations;
    }

    public void setRelations(List<PedigreeRelationVO> relations) {
        this.relations = relations;
    }

    /**
     * conver data transfer object to view object
     *
     * @param pedigreeDTO data transfer object
     * @return
     */
    public static PedigreeVO converDTOToVO(PedigreeDTO pedigreeDTO) {
        PedigreeVO pedigreeVO = new PedigreeVO();
        if (null != pedigreeDTO) {
            List<PedigreeNode> collabNodes = pedigreeDTO.getNodes();
            List<PedigreeNodeRelation> collabNodeRelations = pedigreeDTO.getRelations();
            collabNodes.forEach(collabNode -> {
                PedigreeNodeVO pedigreeNodeVO = PedigreeNodeVO.converDTOtoVO(collabNode);
                pedigreeVO.getNodes().add(pedigreeNodeVO);
            });
            collabNodeRelations.forEach(collabNodeRelation -> {
                PedigreeRelationVO pedigreeRelationVO = PedigreeRelationVO.converDTOToVO(collabNodeRelation);
                pedigreeVO.getRelations().add(pedigreeRelationVO);
            });
        }
        return pedigreeVO;
    }
}
