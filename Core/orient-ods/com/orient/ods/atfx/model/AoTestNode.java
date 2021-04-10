package com.orient.ods.atfx.model;

import com.orient.ods.atfx.business.AtfxFileTreeBusiness;
import com.orient.ods.atfx.util.NameValueConvert;
import org.asam.ods.*;

import java.util.List;

/**
 * Created by mengbin on 16/3/7.
 * Purpose:
 * Detail:
 */
public class AoTestNode extends ODSNode {

    public static String Relation_AOSubTest_AoMeasurement =  "children";
    public AoTestNode(){
        this.setNodetype(BASETYPE.AOTest);
    }

    @Override
    public List<ODSNode> constructChildNodes(boolean cascade) throws Exception{

        //construct the subtest 子测试任务
        constructChildrenByRealatioName(this,AtfxTagConstants.BASE_ELEM_AOSubTest,AoTestNode.Relation_AOSubTest_AoMeasurement,cascade);
        //construct the measurmentquantities 测试变量
        constructChildrenByRealatioName(this,AtfxTagConstants.BASE_ELEM_AOMeasurement,AoTestNode.Relation_AOSubTest_AoMeasurement,cascade);


        return this.getChild();
    }
}
