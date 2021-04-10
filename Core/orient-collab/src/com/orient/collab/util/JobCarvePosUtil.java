package com.orient.collab.util;

import com.orient.collab.model.DataFlowActivity;

import java.util.List;

/**
 * Created by mengbin on 16/8/22.
 * Purpose:
 * Detail:
 */
public class JobCarvePosUtil {

    protected  static float jobHeight = 30;     //一个框的高度
    protected  static float jobWidthPer = 15;    //一个中文字符的宽度
    protected  static float heightSpilte = 20;  //高度间隔
    protected  static float widthSpilte = 30;



    public static boolean setDefaultCarvePostion(List<DataFlowActivity> newActivityList){

        int size =  newActivityList.size();
        float startX = widthSpilte;
        float startY = heightSpilte;

        for(int i = 0 ; i < size ; i ++){
            DataFlowActivity  activity = newActivityList.get(i);
            int length = activity.getDispalyName().length();
            activity.setHeight(String.valueOf(JobCarvePosUtil.jobHeight));
            activity.setWidth(String.valueOf(length*jobWidthPer));

            startX = startX+length*jobWidthPer+widthSpilte;
            startY = startY+jobHeight+heightSpilte;
            activity.setxPos(String.valueOf(startX));
            activity.setyPos(String.valueOf(startY));
        }
        return true;
    }
}
