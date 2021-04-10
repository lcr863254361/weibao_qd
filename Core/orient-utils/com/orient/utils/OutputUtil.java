package com.orient.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.*;
import java.util.List;

/**
 * simplify output process
 *
 * @author supengfei1988@foxmail.com
 */
public class OutputUtil {

    /**
     * The output type U wanna
     *
     * @author supengfei1988@foxmail.com
     */
    public enum Type {
        Buffered
    }

    ;


    /**
     * output object as json string in the specified way
     *
     * @param writer    the writer
     * @param type,     see {@link com.orient.ssh.common.util.OutputUtil.Type}
     * @param outputVal the output value
     */
    static public void writeObjectInJson(Writer writer, Type type, Object outputVal) {
        String jsonString = JSONObject.fromObject(outputVal == null ? "" : outputVal).toString();
        OutputUtil.writeString(writer, type, jsonString);
    }

    /**
     * output List as json string in the specified way
     *
     * @param writer    the writer
     * @param type,     see {@link com.orient.ssh.common.util.OutputUtil.Type}
     * @param outputVal the output value
     */
    static public void writeListInJson(Writer writer, Type type, List<?> outputVal) {
        String outputString = "";
        if (outputVal != null) {
            JSONArray jsonArray = JSONArray.fromObject(outputVal);
            outputString = jsonArray.toString();
        }
        OutputUtil.writeString(writer, type, outputString.toString());
    }

    /**
     * output string in the specified way
     *
     * @param writer    the writer
     * @param type,     see {@link com.orient.ssh.common.util.OutputUtil.Type}
     * @param outputVal the output value
     */
    static public void writeString(Writer writer, Type type, String outputVal) {
        try {
            Writer out = OutputUtil.createWriter(writer, type);
            out.write(outputVal);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // the writer factory method
    static private Writer createWriter(Writer writer, Type type) {
        Writer specifiedWriter = null;
        switch (type) {
            case Buffered:
                specifiedWriter = new BufferedWriter(writer);
                break;
        }
        return specifiedWriter;
    }

    //the output stream factory method
    @SuppressWarnings("unused")
    static private OutputStream createOutputStream(OutputStream outputStream, Type type) {
        OutputStream specifiedOutputStream = null;
        switch (type) {
            case Buffered:
                specifiedOutputStream = new BufferedOutputStream(outputStream);
                break;
        }
        return specifiedOutputStream;
    }

}

