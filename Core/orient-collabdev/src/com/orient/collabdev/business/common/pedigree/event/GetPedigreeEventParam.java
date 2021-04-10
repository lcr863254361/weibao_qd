package com.orient.collabdev.business.common.pedigree.event;

import com.orient.collabdev.model.PedigreeDTO;
import com.orient.web.base.OrientEventBus.OrientEventParams;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-08-04 2:35 PM
 */
public class GetPedigreeEventParam extends OrientEventParams {

    //input
    private String nodeId;

    private Integer nodeVersion;

    //是否查询研发状态
    private boolean queryStatus = false;

    //查询关联节点类型,children：查询子节点；brother：查询兄弟节点
    private String queryType = "children";

    //output
    private PedigreeDTO pedigreeDTO = new PedigreeDTO();

    public GetPedigreeEventParam(String nodeId, Integer nodeVersion) {
        this.nodeId = nodeId;
        this.nodeVersion = nodeVersion;
    }

    public GetPedigreeEventParam(String nodeId, Integer nodeVersion, boolean queryStatus, String queryType) {
        this.nodeId = nodeId;
        this.nodeVersion = nodeVersion;
        this.queryStatus = queryStatus;
        this.queryType = queryType;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public Integer getNodeVersion() {
        return nodeVersion;
    }

    public void setNodeVersion(Integer nodeVersion) {
        this.nodeVersion = nodeVersion;
    }

    public PedigreeDTO getPedigreeDTO() {
        return pedigreeDTO;
    }

    public void setPedigreeDTO(PedigreeDTO pedigreeDTO) {
        this.pedigreeDTO = pedigreeDTO;
    }

    public boolean isQueryStatus() {
        return queryStatus;
    }

    public void setQueryStatus(boolean queryStatus) {
        this.queryStatus = queryStatus;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }
}
