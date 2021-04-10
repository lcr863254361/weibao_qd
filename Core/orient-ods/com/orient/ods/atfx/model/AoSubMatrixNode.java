package com.orient.ods.atfx.model;

import com.orient.ods.atfx.util.NameValueConvert;
import com.sun.prism.PixelFormat;
import org.asam.ods.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mengbin on 16/3/8.
 * Purpose:
 * Detail:
 */
public class AoSubMatrixNode extends ODSNode{

    private List<AoLocalColumnNode> allColumn = new ArrayList<AoLocalColumnNode>() ;
    public static String Relation_AOLocalColumn =  "local_columns";
    public AoSubMatrixNode() {
        this.nodetype = BASETYPE.AoSubMatrix;
    }
    private  SubMatrix  subMatrix;
    private  ValueMatrix vmStorage = null;
    private  ValueMatrix vmCalculated = null;
    private boolean bInitial = false;

    public long getRowCount(){

       String rowCount =  properties.get("number_of_rows").value;
        return  Double.valueOf(rowCount).longValue();
    }

    public List<AoLocalColumnNode> getAllColumn() {
        return allColumn;
    }



    private boolean initSelf(){
        try {
            if (subMatrix == null) {
                InstanceElement instance = getInstance();
                subMatrix = instance.upcastSubMatrix();
            }

            if (vmStorage == null) {
                vmStorage = subMatrix.getValueMatrixInMode(ValueMatrixMode.STORAGE);
            }
            if (vmCalculated == null){
                vmCalculated = subMatrix.getValueMatrixInMode(ValueMatrixMode.CALCULATED);
            }
            bInitial = true;

        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public int getColumnCount(){
        if (bInitial == false){
            initSelf();
        }
        try {
           return vmStorage.getColumnCount();
        }
        catch (AoException e){
            return  -1;
        }
    }

    public String[] listColNames(){
        try{
            return   vmCalculated.listColumns("*");
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public Map<String,String[]> getValuesByColumnName(String name, int start, int count)throws  Exception{

        if (bInitial == false){
            initSelf();
        }
        Map<String,String[]> ret= new HashMap<String, String[]>();

        Column[] columns =   vmCalculated.getColumns(name);
        for (int iCol = 0;iCol < columns.length;iCol++){
            String colName = columns[iCol].getName();
            DataType datatype=  columns[iCol].getDataType();
            TS_ValueSeq  values = vmCalculated.getValueVector(columns[iCol],start,count);

            String[] vals = NameValueConvert.constructOrientValue(values.u);
            ret.put(colName,vals);
        }
        return  ret;
    }


    public Map<String,Object[]> getValueDataByColumnName(String name, int start, int count)throws  Exception{

        if (bInitial == false){
            initSelf();
        }
        Map<String,Object[]> ret= new HashMap<String, Object[]>();

        Column[] columns =   vmCalculated.getColumns(name);
        for (int iCol = 0;iCol < columns.length;iCol++){
            String colName = columns[iCol].getName();
            DataType datatype=  columns[iCol].getDataType();
            TS_ValueSeq  values = vmCalculated.getValueVector(columns[iCol],start,count);
            if (values.u.discriminator()==DataType.DT_STRING||
                    values.u.discriminator()==DataType.DT_DATE||
                    values.u.discriminator()==DataType.DT_LONG||
                    values.u.discriminator()==DataType.DT_FLOAT||
                    values.u.discriminator()==DataType.DT_DOUBLE){
                Object[] vals = NameValueConvert.constructTypeData(values.u);
                ret.put(colName,vals);
            }


        }
        return  ret;
    }

    @Override
    public List<ODSNode> constructChildNodes(boolean cascade) throws Exception{

        //construct the AoLocalColumn
        constructChildrenByRealatioName(this,AtfxTagConstants.BASE_ELEM_AOLocalColumn,AoSubMatrixNode.Relation_AOLocalColumn,cascade);
        //depatch the child by type
        allColumn.clear();
        for (ODSNode node:this.child) {
            if (node.nodetype == BASETYPE.AoLocalColumn){
                this.allColumn.add((AoLocalColumnNode) node);
            }

        }

        return this.getChild();
    }

}

