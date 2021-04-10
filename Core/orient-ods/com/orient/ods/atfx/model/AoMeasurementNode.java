package com.orient.ods.atfx.model;

import com.orient.ods.atfx.business.AtfxFileTreeBusiness;
import com.orient.ods.atfx.util.NameValueConvert;
import org.asam.ods.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengbin on 16/3/7.
 * Purpose:
 * Detail:
 */
public class AoMeasurementNode extends  ODSNode {

    public AoMeasurementNode() {
        this.nodetype = BASETYPE.AoMeasurement;
    }
    public static String Relation_AoMQuantity =  "measurement_quantities";
    public static String Relation_AoSubMatrix =  "submatrices";



    public List<AoMeasurementQuantityNode> measureQuantities = new ArrayList<AoMeasurementQuantityNode>();
    public List<AoSubMatrixNode>           subMatrices = new ArrayList<AoSubMatrixNode>();
    public List<AoMeasurementQuantityNode> getMeasureQuantities() {
        return measureQuantities;
    }

    public List<AoSubMatrixNode> getSubMatrices() {
        return subMatrices;
    }

    @Override
    public List<ODSNode> constructChildNodes(boolean cascade) throws Exception{

        //construct the measurmentquantities 测试变量
        constructChildrenByRealatioName(this,AtfxTagConstants.BASE_ELEM_AOMeasurementQuantity,AoMeasurementNode.Relation_AoMQuantity,cascade);

        //construct the aosubmatrix 测试矩阵
        constructChildrenByRealatioName(this,AtfxTagConstants.BASE_ELEM_AOSubMatrix,AoMeasurementNode.Relation_AoSubMatrix,cascade);

        //depatch the child by type
        for (ODSNode node:this.child) {
            if (node.nodetype == BASETYPE.AoMeasurementQuantity){
                this.measureQuantities.add((AoMeasurementQuantityNode)node);
            }
            if (node.nodetype == BASETYPE.AoSubMatrix){
                this.subMatrices.add((AoSubMatrixNode)node);
            }
        }

        return this.getChild();
    }





}
