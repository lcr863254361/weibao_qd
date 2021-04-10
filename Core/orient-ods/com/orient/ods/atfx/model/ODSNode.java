package com.orient.ods.atfx.model;

import com.orient.ods.atfx.business.AtfxFileTreeBusiness;
import com.orient.ods.atfx.util.NameValueConvert;
import org.asam.ods.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mengbin on 16/3/5.
 * Purpose:
 * Detail:
 */
public class ODSNode {
    /**
     * 节点的显示名称
     */
    public enum BASETYPE            {AOEnvirment,AOTest,AOSubTest,AoMeasurement,AoMeasurementQuantity,
                                    AoSubMatrix,AoLocalColumn,AoExternamComponet};
    protected String                nodeName;
    protected List<ODSNode>         child = new ArrayList<ODSNode>();
    protected BASETYPE              nodetype;
    protected String                applicationElemName;
    protected T_LONGLONG            id;         // two int
    protected ApplicationElement    baseAppElem;
    protected Map<String,OrientNameValueUnit> properties = new HashMap<String,OrientNameValueUnit>();
    protected ODSNode               parentNode;

    public ODSNode getParentNode() {
        return parentNode;
    }

    protected InstanceElement getInstance(){
        try {
          return   this.baseAppElem.getInstanceById(id);
        }
       catch (AoException e)
       {
           e.printStackTrace();
           return null;
       }
    }

    public Map<String, OrientNameValueUnit> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, OrientNameValueUnit> properties) {
        this.properties = properties;
    }





    public ApplicationElement getBaseAppElem() {
        return baseAppElem;
    }

    public void setBaseAppElem(ApplicationElement baseAppElem) {
        this.baseAppElem = baseAppElem;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public List<ODSNode> getChild() {
        return child;
    }

    public void setChild(List<ODSNode> child) {
        this.child = child;
    }

    public BASETYPE getNodetype() {
        return nodetype;
    }

    public void setNodetype(BASETYPE nodetype) {
        this.nodetype = nodetype;
    }

    public String getApplicationElemName() {
        return applicationElemName;
    }

    public void setApplicationElemName(String applicationElemName) {
        this.applicationElemName = applicationElemName;
    }

    public T_LONGLONG getId() {
        return id;
    }

    public String getStringId(){
        String hight = "";
        String low = "";
        if(id.high!=0){
            hight = String.valueOf(id.high);
        }
        low = String.valueOf(id.low);

       return hight+low;

    }

    public void setId(T_LONGLONG id) {
        this.id = id;
    }

    public List<ODSNode> constructChildNodes(boolean cascade) throws Exception{
        return this.getChild();
    }

    /**
     *
     * @param parentNode                : 父节点
     * @param childBaseElemName         :子节点的BaseElementType
     * @param relationName              :关系名称
     * @param cascade                   :是否及联创建
     * @return
     * @throws Exception
     */
    protected boolean constructChildrenByRealatioName(ODSNode parentNode,String childBaseElemName,String relationName,boolean cascade) throws  Exception{
        ApplicationRelation[] relations = parentNode.getBaseAppElem().getRelationsByBaseName(relationName);
        for(ApplicationRelation relation :relations){
            ApplicationElement relaElem_refer = relation.getElem2();
            String refName = relaElem_refer.getName();

            if (relaElem_refer.getBaseElement().getType().equals(childBaseElemName)){
                InstanceElementIterator itor =    this.getBaseAppElem().getInstanceById(this.getId()).getRelatedInstances(relation,"*");
                for(int i = 0 ; i <itor.getCount();i++ )
                {
                    InstanceElement instanceElem  = itor.nextOne();
                    //AoMeasurementQuantityNode aoChildNode = new AoMeasurementQuantityNode();
                    ODSNode aoChildNode = createODSNodeByBaseElemName(childBaseElemName);
                    aoChildNode.parentNode = parentNode;
                    aoChildNode.setNodeName(instanceElem.getName());
                    aoChildNode.setId(instanceElem.getId());
                    aoChildNode.setBaseAppElem(relaElem_refer);
                    String[]  attributesNames =  instanceElem.listAttributes("*", AttrType.ALL);
                    for(int attriIndex=0; attriIndex<attributesNames.length ;attriIndex++){

                        NameValueUnit  nameValueUnit =  instanceElem.getValue(attributesNames[attriIndex]);
                        BaseAttribute baseAttribute =  instanceElem.getApplicationElement().getAttributeByName(nameValueUnit.valName).getBaseAttribute();
                        if(baseAttribute==null){
                            aoChildNode.getProperties().put(nameValueUnit.valName, NameValueConvert.constructOrientNameValueUnit(nameValueUnit));

                        }
                        else{
                            aoChildNode.getProperties().put(baseAttribute.getName(), NameValueConvert.constructOrientNameValueUnit(nameValueUnit));
                        }

                    }
                    this.getChild().add(aoChildNode);
                    if(cascade){
                        AtfxFileTreeBusiness atfxFileTreeBusiness = new AtfxFileTreeBusiness();
                        atfxFileTreeBusiness.getChildNode(aoChildNode,cascade);
                    }
                }
            }
        }
        return true;
    }

    private ODSNode createODSNodeByBaseElemName(String baseElemName )
    {
        if (baseElemName.equals(AtfxTagConstants.BASE_ELEM_AOMeasurement)) {
            ODSNode aoChildNode = new AoMeasurementNode();
            return aoChildNode;
        }
        else if(baseElemName.equals(AtfxTagConstants.BASE_ELEM_AOMeasurementQuantity)){
            ODSNode aoChildNode = new AoMeasurementQuantityNode();
            return aoChildNode;
        }
        else if(baseElemName.equals(AtfxTagConstants.BASE_ELEM_AOTest)){
            ODSNode aoChildNode = new AoTestNode();
            return aoChildNode;
        }
        else if(baseElemName.equals(AtfxTagConstants.BASE_ELEM_AOSubTest)){
            ODSNode aoChildNode = new AoSubTestNode();
            return aoChildNode;
        }
        else if(baseElemName.equals(AtfxTagConstants.BASE_ELEM_AOSubMatrix)){
            ODSNode aoChildNode = new AoSubMatrixNode();
            return aoChildNode;
        }
        else if(baseElemName.equals(AtfxTagConstants.BASE_ELEM_AOLocalColumn)){
            ODSNode aoChildNode = new AoLocalColumnNode();
            return aoChildNode;
        }
        return  null;
    }



}
