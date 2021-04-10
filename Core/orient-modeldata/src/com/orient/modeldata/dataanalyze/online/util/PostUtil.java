package com.orient.modeldata.dataanalyze.online.util;

import com.orient.modeldata.dataanalyze.online.bean.PostSolution;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.util.*;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-11-30 16:03
 */
public class PostUtil {

    public static Map<String, DataOutputStream> createOutFileStream(
            List<PostSolution> solutions, File file)
            throws FileNotFoundException {
        Map<String, DataOutputStream> osMap = new HashMap<>();
        for (PostSolution sol : solutions) {
            String name = sol.getName();
            List<Map<String, Object>> data = sol.getColumnData();
            Map map = data.get(0);
            Iterator ite = map.keySet().iterator();
            while (ite.hasNext()) {
                String key = ite.next().toString();
                DataOutputStream dos = new DataOutputStream(
                        new BufferedOutputStream(new FileOutputStream(new File(
                                file.getPath() + "\\" + name + "_" + key + ".dat"))));
                osMap.put(name + "_" + key, dos);
            }
        }
        return osMap;
    }

    public static void writeData(List<PostSolution> solutions, Map<String, DataOutputStream> osMap) throws Exception {
        for (PostSolution sol : solutions) {
            String name = sol.getName();
            List<Map<String, Object>> data = sol.getColumnData();
            for (Map<String, Object> map : data) {
                Iterator ite = map.keySet().iterator();
                while (ite.hasNext()) {
                    String key = ite.next().toString();
                    Object obj = map.get(key);

                    if (obj==null||obj.toString().equals("")) {
                        osMap.get(name + "_" + key).writeFloat(
                                Float.valueOf("0"));
                    } else {
                        osMap.get(name + "_" + key).writeFloat(
                                Float.valueOf(obj.toString()));
                    }
                }
            }
        }
        closeOsMap(osMap);
    }

    private static void closeOsMap(Map<String, DataOutputStream> osMap) {
        for (Map.Entry<String, DataOutputStream> entry : osMap.entrySet()) {
            DataOutputStream dos = entry.getValue();
            try {
                dos.flush();
                dos.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void writeXml(List<PostSolution> solutions, File userFile)
            throws IOException {
        Document document = null;
        document = DocumentHelper.createDocument();
        Element root = document.addElement("DataSource");
        root.addAttribute("autoconfigure", "true");
        PostSolution rootSolution = solutions.get(0);
        Element secEle = root.addElement("Solution");
        secEle.addAttribute("ColumnCount", rootSolution.getColumnCount());
        secEle.addAttribute("name", rootSolution.getName());
        secEle.addAttribute("RowCount", rootSolution.getRowCount());
        for (PostSolution sol : solutions) {
            String name = sol.getName();
            Map columnMap = sol.getColumnData().get(0);
            Iterator ite = columnMap.keySet().iterator();
            while (ite.hasNext()) {
                String key = ite.next().toString();
                secEle.addElement("Column").addAttribute("split", ",")
                        .addAttribute("type", "float")
                        .addAttribute("name", key).addAttribute("file",
                        name + "_" + key + ".dat");
            }
        }
        XMLWriter xw = new XMLWriter(new FileOutputStream(userFile.getPath()
                + "\\MetaData.xml"));
        xw.write(document);
        xw.flush();
        xw.close();
    }

    public static String createXmlBuffer(List<PostSolution> solutions) {
        Document document = null;
        document = DocumentHelper.createDocument();
        Element root = document.addElement("DataSource");
        root.addAttribute("autoconfigure", "true");
        for (PostSolution sol : solutions) {
            String name = sol.getName();
            Element secEle = root.addElement("Solution");
            secEle.addAttribute("ColumnCount", sol.getColumnCount());
            secEle.addAttribute("name", sol.getName());
            secEle.addAttribute("RowCount", sol.getRowCount());
            Map columnMap = sol.getColumnData().get(0);
            Iterator ite = columnMap.keySet().iterator();
            while (ite.hasNext()) {
                String key = ite.next().toString();
                secEle.addElement("Column").addAttribute("split", ",")
                        .addAttribute("type", "float")
                        .addAttribute("name", key).addAttribute("file",
                        name + "_" + key + ".dat");
            }

        }
        return document.asXML();
    }

    public static Map<String, byte[]> getEndData(List<PostSolution> solutions,
                                          String userDir) {
        Map<String, byte[]> m = new HashMap();
        for (PostSolution solution : solutions) {
            String name = solution.getName();
            List<Map<String, Object>> data = solution.getColumnData();
            Map sonData = data.get(0);
            Set keys = sonData.keySet();
            for (Object o : keys) {
                String mName = userDir + "\\" + name + "_" + (String) o
                        + ".dat";
                byte[] endData = new byte[4 * data.size()];
                int x = 0;
                for (Map<String, Object> mm : data) {
                    Float f = 0f;
                    if ((String) mm.get(o) != null) {
                        f = Float.valueOf((String) mm.get(o));
                    }
                    byte[] middleData = floatToBytes(f);
                    System.arraycopy(middleData, 0, endData, 4 * x,
                            middleData.length);
                    x++;
                }
                m.put(mName, endData);
            }
        }
        return m;
    }

    private static byte[] floatToBytes(float fNum) {
        byte[] by = new byte[4];
        int f = Float.floatToIntBits(fNum);
        for (int i = by.length - 1; i >= 0; i--) {
            by[i] = (byte) (f & 0xff);
            f >>>= 8;
        }
        return by;
    }
}
