/*
 * Copyright (c) 2016. Orient Company
 *
 */

package com.orient.web.base.OrientEventBus;

import com.orient.edm.init.IContextLoadRun;
import com.orient.utils.FileOperator;
import com.orient.utils.PathTools;
import com.orient.utils.XmlCastToModel;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.util.List;

/**
 * Created by mengbin on 16/3/25.
 * Purpose:
 * Detail:
 */

@Service
public class ListenerOrderService implements IContextLoadRun {

    private static ListenerOrder listenerOrder = null;
    private static String listenerConfigFolderName = "listenerconfig";

    public static ListenerOrder  getListenerOrder(){

        if (listenerOrder==null){

            XmlCastToModel<ListenerOrder> castUtil = new XmlCastToModel<>();
            String mappingFilePath = PathTools.getClassPath()+ File.separator+"listenermapping.xml";
            String configFolder = PathTools.getClassPath()+File.separator+listenerConfigFolderName;
           List<String> filePaths =   FileOperator.getChildFilePath(configFolder);
            filePaths.forEach(filepath->{
                String xmlContent = FileOperator.readFile(filepath);
                if (listenerOrder==null){
                    listenerOrder = new ListenerOrder();
                }
                ListenerOrder  appendlistenerOrder = castUtil.castfromXML(ListenerOrder.class,xmlContent,mappingFilePath);
                listenerOrder.getEventSourceList().addAll(appendlistenerOrder.getEventSourceList());
            });
        }
        return listenerOrder;
    }


    /**
     * 校验配置文件是否正确
     * @return
     */
    public static boolean validate(){


        if (listenerOrder==null){
            return  false;
        }
        else
        {
            //TODO::校验extenderListener的顺序是否正确
            return  true;
        }


    }


    /**
     *
     * @param sourceName :事件出发的名称
     * @param eventClass:事件类型的类名称
     * @param listenerName:
     * @return
     */
    public int  getOrder(String sourceName, String eventClass,String listenerName){
        if (listenerOrder==null) return -1;
        for (EventSource source:  listenerOrder.getEventSourceList()) {

            if(!source.getName().equals(sourceName)||!source.getEventType().equals(eventClass)){
                continue;
            }
            for (Listener listener: source.getExtenderOderList().getListenerList()) {

                if (listenerName.contains(listener.getListnerName())){
                    return listener.getOrder();
                }
            }
        }
        return -1;
    }




    @Override
    public boolean modelLoadRun(WebApplicationContext contextLoad) {
        ListenerOrderService.getListenerOrder();
        ListenerOrderService.validate();
        return true;
    }
}
