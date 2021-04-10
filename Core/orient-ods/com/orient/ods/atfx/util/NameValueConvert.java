package com.orient.ods.atfx.util;

import com.orient.ods.atfx.model.OrientNameValueUnit;
import com.sun.org.glassfish.gmbal.NameValue;
import org.asam.ods.DataType;
import org.asam.ods.NameValueUnit;
import org.asam.ods.TS_UnionSeq;
import org.asam.ods.TS_Value;

/**
 * Created by mengbin on 16/3/7.
 * Purpose:
 * Detail:
 */
public class NameValueConvert {

    public static OrientNameValueUnit constructOrientNameValueUnit(NameValueUnit name_value_unit){

        OrientNameValueUnit ret = new OrientNameValueUnit();
        ret.name = name_value_unit.valName;
        ret.unit = name_value_unit.unit;
        DataType datatype =  name_value_unit.value.u.discriminator();
        if(datatype == DataType.DT_STRING)
        {
            ret.value = name_value_unit.value.u.stringVal();
        }
        else if (datatype==DataType.DT_DATE){
            ret.value = name_value_unit.value.u.dateVal();
        }
        else if (datatype==DataType.DT_FLOAT){
            ret.value =String.valueOf( name_value_unit.value.u.floatVal());
        }
        else if (datatype==DataType.DT_DOUBLE){
            ret.value =String.valueOf( name_value_unit.value.u.doubleVal());
        }
        else if (datatype.value()==DataType.DT_BYTE.value()){
            ret.value =String.valueOf( name_value_unit.value.u.byteVal());
        }
        else if (datatype.value()==DataType.DT_SHORT.value()){
            ret.value =String.valueOf( name_value_unit.value.u.shortVal());
        }
        else if (datatype.value()==DataType.DT_LONG.value()){
            ret.value =String.valueOf( name_value_unit.value.u.longVal());
        }
        else if (datatype.value()==DataType.DT_LONGLONG.value()){
            ret.value =String.valueOf( name_value_unit.value.u.longlongVal().high)+String.valueOf( name_value_unit.value.u.longlongVal().low);
        }
        else if (datatype.value()==DataType.DT_ENUM.value()){
            ret.value = String.valueOf(name_value_unit.value.u.enumVal());
        }
        return ret;
    }

    public static String contructOrientValue(TS_Value value)
    {
        DataType datatype =  value.u.discriminator();
        if(datatype == DataType.DT_STRING)
        {
             return  value.u.stringVal();
        }
        else if (datatype==DataType.DT_DATE){
           return  value.u.dateVal();
        }
        else if (datatype==DataType.DT_FLOAT){
           return String.valueOf(  value.u.floatVal());
        }
        else if (datatype==DataType.DT_DOUBLE){
           return String.valueOf(  value.u.doubleVal());
        }
        else if (datatype.value()==DataType.DT_BYTE.value()){
           return String.valueOf(  value.u.byteVal());
        }
        else if (datatype.value()==DataType.DT_SHORT.value()){
           return String.valueOf(  value.u.shortVal());
        }
        else if (datatype.value()==DataType.DT_LONG.value()){
           return String.valueOf(  value.u.longVal());
        }
        else if (datatype.value()==DataType.DT_LONGLONG.value()){
           return String.valueOf(  value.u.longlongVal().high)+String.valueOf(  value.u.longlongVal().low);
        }
        return  "";
    }


    public static String[] constructOrientValue(TS_UnionSeq seq){
        DataType datatype =  seq.discriminator();
        if(datatype == DataType.DT_STRING) {

            return  seq.stringVal();
        }
        else if (datatype==DataType.DT_DATE){
            return seq.dateVal();
        }
        else if (datatype==DataType.DT_FLOAT){
            float[] val =  seq.floatVal();
            String[] str = new String[val.length];
            for (int i = 0 ; i < val.length;i++){
                str[i] = String.valueOf(val[i]);
            }
            return str;
        }
        else if (datatype==DataType.DT_LONG){
            int[] val =  seq.longVal();
            String[] str = new String[val.length];
            for (int i = 0 ; i < val.length;i++){
                str[i] = String.valueOf(val[i]);
            }
            return str;
        }
        else if (datatype==DataType.DT_DOUBLE){
            double[] val =  seq.doubleVal();
            String[] str = new String[val.length];
            for (int i = 0 ; i < val.length;i++){
                str[i] = String.valueOf(val[i]);
            }
            return str;
        }
        else {
            return new String[]{"not support"+datatype.toString()};
        }

    }


    public static Object[] constructTypeData(TS_UnionSeq seq){
        DataType datatype =  seq.discriminator();
        if(datatype == DataType.DT_STRING) {

            return  seq.stringVal();
        }
        else if (datatype==DataType.DT_DATE){
            return seq.dateVal();
        }
        else if (datatype==DataType.DT_FLOAT){
            float[] val =  seq.floatVal();
            Float[] str = new Float[val.length];
            for (int i = 0 ; i < val.length;i++){
                str[i] = Float.valueOf(val[i]);
            }
            return str;
        }
        else if (datatype==DataType.DT_LONG){
            int [] val =  seq.longVal();
            Integer[] str = new Integer[val.length];
            for (int i = 0 ; i < val.length;i++){
                str[i] = Integer.valueOf(val[i]);
            }
            return str;
        }
        else if (datatype==DataType.DT_DOUBLE){
            double[] val =  seq.doubleVal();
            Double[] str = new Double[val.length];
            for (int i = 0 ; i < val.length;i++){
                str[i] = Double.valueOf(val[i]);
            }
            return str;
        }
        else {
            return new String[]{"not support"+datatype.toString()};
        }

    }




}
