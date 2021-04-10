package com.aptx.utils;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.*;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public abstract class FileContentUtil {
    public static Map<String, String> readMap(URL url, String charset) {
        Map<String, String> retMap = Maps.newLinkedHashMap();
        List<String> lines = readNonEmptyLines(url, charset);
        for(String line : lines) {
            if(line.startsWith("#")) {
                continue;
            }
            List<String> splitLine = StringUtil.WHITESPACES_SPLITTER.splitToList(line);
            if(splitLine.size() != 2) {
                throw new RuntimeException("Error line in file "+url.getPath()+": " + line);
            }
            else {
                String key = splitLine.get(0);
                if(retMap.containsKey(key)) {
                    throw new RuntimeException("Duplicated key in file "+url.getPath()+": " + key);
                }
                else {
                    retMap.put(key, splitLine.get(1));
                }
            }
        }
        return retMap;
    }

    public static List<String> readNonEmptyLines(URL url, String charset) {
        if(Strings.isNullOrEmpty(charset)) {
            charset = CharsetUtil.UTF_8;
        }
        List<String> retList = Lists.newLinkedList();
        try {
            retList = Resources.readLines(url, Charset.forName(charset), new LineProcessor<List<String>>() {
                List<String> lines = Lists.newLinkedList();

                @Override
                public boolean processLine(String line) throws IOException {
                    if (!Strings.isNullOrEmpty(line) && !"".equals(line.trim())) {
                        lines.add(line);
                    }
                    return true;
                }

                @Override
                public List<String> getResult() {
                    return lines;
                }
            });
        }
        catch (IOException e) {
            throw new RuntimeException("Exception occurred on reading file: "+url.getPath());
        }
        return retList;
    }

    public static Properties readProperties(URL url) {
        CharSource charSource = Resources.asCharSource(url, Charset.forName(CharsetUtil.UTF_8));
        Properties props = new Properties();
        Reader reader = null;
        try {
            reader = charSource.openBufferedStream();
            props.load(reader);
        }
        catch (IOException e) {
            throw new RuntimeException("Exception occurred on reading file: "+url.getPath());
        }
        finally {
            FileOperateUtil.close(reader);
        }
        return props;
    }

    public static void writeToProperties(File file, Properties props, String comments) {
        if(file==null || props==null || props.size()==0) {
            return;
        }
        if(file.exists()) {
            Properties oriProps = readProperties(FileOperateUtil.toUrl(file));
            for(String key : props.stringPropertyNames()) {
                oriProps.setProperty(key, props.getProperty(key));
            }
            props = oriProps;
        }
        CharSink charSink = Files.asCharSink(file, Charset.forName(CharsetUtil.UTF_8));
        Writer writer = null;
        try {
            writer = charSink.openBufferedStream();
            props.store(writer, comments);
        }
        catch (IOException e) {
            throw new RuntimeException("Exception occurred on write file："+file.getAbsolutePath(), e);
        }
        finally {
            FileOperateUtil.close(writer);
        }
    }

}
