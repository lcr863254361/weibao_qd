package com.orient.modeldata.eventParam;

import com.orient.web.model.BaseNode;
import com.orient.modeldata.bean.TBomDynamicNode;
import com.orient.modeldata.bean.TBomNode;
import com.orient.modeldata.bean.TBomStaticNode;
import com.orient.utils.JsonUtil;
import com.orient.utils.StringUtil;
import com.orient.web.base.OrientEventBus.OrientEventParams;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取TBOM事件参数
 *
 * @author enjoy
 * @createTime 2016-05-21 9:27
 */
public class GetTbomNodesEventParam extends OrientEventParams {

    public GetTbomNodesEventParam() {
    }

    public GetTbomNodesEventParam(String schemaId, Long belongFunctionId, String node, String nodeAttr) {
        this.schemaId = schemaId;
        this.node = node;
        this.belongFunctionId = belongFunctionId;
        this.nodeAttr = nodeAttr;
    }

    //输入
    String schemaId;

    //父节点ID
    String node;

    //所属功能点ID
    Long belongFunctionId;

    //父节点前端Json描述
    String nodeAttr;

    //父节点前段描述
    TBomNode fatherNode;

    //输出
    //tbom节点描述
    List<BaseNode> tbomNodes = new ArrayList<>();

    public String getSchemaId() {
        return schemaId;
    }

    public void setSchemaId(String schemaId) {
        this.schemaId = schemaId;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public Long getBelongFunctionId() {
        return belongFunctionId;
    }

    public void setBelongFunctionId(Long belongFunctionId) {
        this.belongFunctionId = belongFunctionId;
    }

    public String getNodeAttr() {
        return nodeAttr;
    }

    public void setNodeAttr(String nodeAttr) {
        this.nodeAttr = nodeAttr;
    }

    public List<BaseNode> getTbomNodes() {
        return tbomNodes;
    }

    public void setTbomNodes(List<BaseNode> tbomNodes) {
        this.tbomNodes = tbomNodes;
    }


    public TBomNode getFatherNode() {
        if (!StringUtil.isEmpty(nodeAttr) && null == fatherNode) {
            setFatherNode(converFromJson(nodeAttr));
        }
        return fatherNode;
    }

    public void setFatherNode(TBomNode fatherNode) {
        this.fatherNode = fatherNode;
    }

    /**
     * @param nodeAttr 父节点信息描述
     * @return 根据父节点信息 获取 子节点信息
     */
    private TBomNode converFromJson(String nodeAttr) {
        //转化为json对象
        JSONObject jobj = JSONObject.fromObject(nodeAttr);
        //获取节点类型
        String nodeType = jobj.getString("nodeType");
        TBomNode parentNode = null;
        if (nodeType.equalsIgnoreCase(TBomNode.STATIC_NODE)) {
            parentNode = JsonUtil.jsonToObj(new TBomStaticNode(), nodeAttr);
        } else if (nodeType.equalsIgnoreCase(TBomNode.DYNAMIC_NODE)) {
            parentNode = JsonUtil.jsonToObj(new TBomDynamicNode(), nodeAttr);
        }
        return parentNode;
    }
}

