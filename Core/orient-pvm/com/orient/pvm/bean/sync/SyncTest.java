package com.orient.pvm.bean.sync;

import com.orient.utils.XmlCastToModel;

import java.io.*;
import java.net.URL;

/**
 * Created by mengbin on 16/8/1.
 * Purpose:
 * Detail:
 */
public class SyncTest {

    public static  void main(String[] args){



        URL URL = SyncTest.class.getResource("/");
        String xmlContent = "";
        File xmlFile = new File(URL.getPath()+"PVMTableExample.xml");

        try {

           // String charset = getCharset(xmlFile);
            StringBuffer sb = new StringBuffer();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    new FileInputStream(xmlFile), "UTF-8"));
            String str;
            while ((str = in.readLine()) != null) {
                sb.append(str + "\r\n");
            }
            in.close();
            xmlContent = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();

        }
       // String xmlContent = FileOperator.readFile(xmlFile.getPath());
        XmlCastToModel<PVMTables> castUntil = new XmlCastToModel<PVMTables>();
        PVMTables PVMTable = (PVMTables) castUntil
                .castfromXML(PVMTables.class,
                        xmlContent,URL.getPath()+ File.separator + "PVMMapping.xml");

        int i = 0;
    }
}
