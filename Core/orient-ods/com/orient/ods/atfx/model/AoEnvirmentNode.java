package com.orient.ods.atfx.model;

import com.orient.ods.atfx.business.AtfxFileTreeBusiness;
import com.orient.ods.atfx.util.NameValueConvert;
import org.asam.ods.*;

import java.util.List;

/**
 * Created by mengbin on 16/3/6.
 * Purpose:
 * Detail:
 */
public class AoEnvirmentNode extends ODSNode {

    public static String Relation_AOTest =  "tests";

    public AoEnvirmentNode(){
        this.setNodetype(BASETYPE.AOEnvirment);
    }

    @Override
    public List<ODSNode> constructChildNodes(boolean cascade) throws Exception{


            ApplicationElement applElem = this.getBaseAppElem();
            //constuct the AoTestNode
            ApplicationRelation[] relations = applElem.getRelationsByBaseName(AoEnvirmentNode.Relation_AOTest);
            for(ApplicationRelation relation :relations)
            {
                ApplicationElement relaElem_refer = relation.getElem2();
                String refName=  relaElem_refer.getName();
                InstanceElementIterator itor =   relaElem_refer.getInstances("*");
                //InstanceElementIterator itor =    this.getBaseAppElem().getInstanceById(this.getId()).getRelatedInstances(relation,"*");

                for(int i = 0 ; i <itor.getCount();i++ )
                {
                    InstanceElement instanceElem  = itor.nextOne();
                    AoTestNode aoTestNode = new AoTestNode();
                    aoTestNode.parentNode = this;
                    aoTestNode.setNodeName(instanceElem.getName());
                    aoTestNode.setId(instanceElem.getId());
                    aoTestNode.setNodetype(ODSNode.BASETYPE.AOTest);
                    aoTestNode.setBaseAppElem(relaElem_refer);
                    String[]  attributesNames =  instanceElem.listAttributes("*", AttrType.ALL);
                    for(int attriIndex=0; attriIndex<attributesNames.length ;attriIndex++){
                        NameValueUnit  nameValueUnit =  instanceElem.getValue(attributesNames[attriIndex]);
                        aoTestNode.getProperties().put(nameValueUnit.valName, NameValueConvert.constructOrientNameValueUnit(nameValueUnit));
                    }
                    this.getChild().add(aoTestNode);
                    if(cascade){
                        AtfxFileTreeBusiness atfxFileTreeBusiness = new AtfxFileTreeBusiness();
                        atfxFileTreeBusiness.getChildNode(aoTestNode,true);
                    }
                }
            }




        return this.child;
    }

}
