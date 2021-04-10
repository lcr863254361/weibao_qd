package com.orient.ods.atfx;

import com.orient.ods.atfx.business.AtfxFileTreeBusiness;
import com.orient.ods.atfx.business.AtfxMatrixBusiness;
import com.orient.ods.atfx.model.AoSubMatrixNode;
import com.orient.ods.atfx.model.ODSNode;
import de.rechner.openatfx.AoServiceFactory;

import org.asam.ods.AoException;
import org.asam.ods.AoSession;
import org.asam.ods.ApplicationElement;
import org.omg.CORBA.ORB;

import java.io.File;
import java.net.URL;
import java.util.Map;

/**
 * Created by mengbin on 16/3/5.
 * Purpose:
 * Detail:
 */
public class ReadAtfxFile {
    public ReadAtfxFile() {
    }





    public static void main(String[] args) {
//        try {
//            ORB orb = ORB.init(new String[0], System.getProperties());
//
//            URL url = ReadAtfxFile.class.getResource("/demofiles/Example_ATF_XML-asam30.atfx");
//            AoSession   aoSession = AoServiceFactory.getInstance().newAoFactory(orb).newSession("FILENAME=" + new File(url.getFile()));
//
//            AtfxFileTreeBusiness bus = new AtfxFileTreeBusiness();
//            ODSNode root = bus.getRootNode(aoSession.getApplicationStructure());
//
//            AtfxMatrixBusiness matrixBusiness = new AtfxMatrixBusiness();
//            AoSubMatrixNode  matrix = matrixBusiness.getAoSubMatrixNodeByName("MyMeasurement",root);
//            long rowCount = matrix.getRowCount();
//            int colCount = matrix.getColumnCount();
//            System.out.print("Row count :  "+rowCount + "\n");
//            System.out.print("Col count :  "+colCount + "\n");
//            System.out.flush();
//            String[] colNames   = matrix.listColNames();
//            String[][] values = new String[colNames.length][];
//            for (int i = 0 ; i < colNames.length;i++)
//            {
//                values[i] =  matrix.getValuesByColumnName(colNames[i],0,0).get(colNames[i]);
//            }
//
//            for(int i = 0 ; i < rowCount+1;i++){
//                if(i==0){
//                    String lineString = "";
//                    for(int j = 0 ; j < colCount;j++){
//                        lineString =  lineString+"              "+colNames[j];
//                    }
//                    System.out.print(lineString +"\n");
//                }
//                else
//                {
//                    int rowIndex = i-1;
//                    String lineString = "";
//                    for(int j = 0 ; j < colCount;j++){
//                        lineString =  lineString+"              "+values[j][rowIndex];
//                    }
//                    System.out.print(lineString +"\n");
//                }
//            }
//        }
//
//        catch (AoException e) {
//            e.printStackTrace();
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();;
//        }
    }
}
