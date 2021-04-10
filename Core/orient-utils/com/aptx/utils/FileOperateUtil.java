package com.aptx.utils;

import com.google.common.base.Strings;
import com.google.common.collect.FluentIterable;
import com.google.common.io.ByteSink;
import com.google.common.io.ByteSource;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

public abstract class FileOperateUtil {
    public static final String TYPE_BINARY = "binary";
    public static final String TYPE_ARCHIVE = "archive";
    public static final String TYPE_AUDIO = "audio";
    public static final String TYPE_HTML = "html";
    public static final String TYPE_IMAGE = "image";
    public static final String TYPE_OFFICE = "office";
    public static final String TYPE_PROGRAM = "program";
    public static final String TYPE_SCRIPT = "script";
    public static final String TYPE_SOURCE = "source";
    public static final String TYPE_TEXT = "text";
    public static final String TYPE_VIDEO = "video";
    public static final String TYPE_XML = "xml";

    private static final Logger logger = LoggerFactory.getLogger(FileOperateUtil.class);
    private static final Map<String, String> FILE_TYPE_MAP;

    static {
        logger.debug("Loading file type map: extension.map");
        FILE_TYPE_MAP = FileContentUtil.readMap(Resources.getResource("extension.map"), CharsetUtil.UTF_8);
    }

    public static String getFileType(String fileName) {
        String fileType;
        String extension = Files.getFileExtension(fileName).toLowerCase();
        if(Strings.isNullOrEmpty(extension)) {
            fileType = FILE_TYPE_MAP.get(fileName);
        }
        else {
            fileType = FILE_TYPE_MAP.get(extension);
        }
        return Strings.isNullOrEmpty(fileType) ? TYPE_BINARY : fileType;
    }

    public static String getFileAllExtension(String fullName) {
        String fileName = new File(fullName).getName();
        int dotIndex = fileName.indexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

    /***************************** Commons *****************************/
    public static URI toUri(URL url) {
        try {
            return url.toURI();
        }
        catch (URISyntaxException e) {
            return null;
        }
    }

    public static URL toUrl(URI uri) {
        try {
            return uri.toURL();
        }
        catch (MalformedURLException e) {
            return null;
        }
    }

    public static URL toUrl(File file) {
        return toUrl(file.toURI());
    }

    public static boolean delete(File fileOrPath, boolean stopOnError) {
        if(fileOrPath==null || !fileOrPath.exists()) {
            return false;
        }
        if(fileOrPath.isDirectory()) {
            boolean allSuccess = true;
            File[] files = fileOrPath.listFiles();
            if(files!=null && files.length>0) {
                for(File file : files) {
                    if(!delete(file, stopOnError)) {
                        allSuccess = false;
                        if(stopOnError) {
                            return false;
                        }
                    }
                }
            }
            if(!fileOrPath.delete()) {
                allSuccess = false;
            }
            return allSuccess;
        }
        else {
            return fileOrPath.delete();
        }
    }

    public static boolean close(InputStream inputStream) {
        if(inputStream == null) {
            return false;
        }
        try {
            inputStream.close();
        }
        catch (IOException e) {
            return false;
        }
        return true;
    }

    public static boolean close(OutputStream outputStream) {
        if(outputStream == null) {
            return false;
        }
        try {
            outputStream.flush();
        }
        catch (IOException e) {
            return false;
        }
        finally {
            try {
                outputStream.close();
            }
            catch (IOException e) {
                return false;
            }
        }
        return true;
    }

    public static boolean close(Reader reader) {
        if(reader == null) {
            return false;
        }
        try {
            reader.close();
        }
        catch (IOException e) {
            return false;
        }
        return true;
    }

    public static boolean close(Writer writer) {
        if(writer == null) {
            return false;
        }
        try {
            writer.flush();
        }
        catch (IOException e) {
            return false;
        }
        finally {
            try {
                writer.close();
            }
            catch (IOException e) {
                return false;
            }
        }
        return true;
    }

    /***************************** Paths *****************************/
    public static String getClassRoot() {
        URL url = Resources.getResource("");
        String retVal = null;
        try {
            if(ResourceUtils.isJarURL(url)) {
                String jarFile = ResourceUtils.extractJarFileURL(url).getFile();
                String jarFolder = jarFile.substring(0, jarFile.lastIndexOf("/")+1);
                retVal = jarFolder.replace("/WEB-INF/lib", "/WEB-INF/classes");
            }
            else {
                retVal = url.getFile();
            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return retVal;
    }

    public static String getWebRoot() {
        String classRoot = getClassRoot();
        if(classRoot!=null && classRoot.contains("/WEB-INF")) {
            return classRoot.substring(0, classRoot.indexOf("/WEB-INF")+1);
        }
        else {
            throw new IllegalStateException("Illegal web class path: "+classRoot);
        }
    }

    public static String getRelativePath(String parent, String child) {
        try {
            String parentPath = new File(parent).getCanonicalPath();
            String childPath = new File(child).getCanonicalPath();
            String relativePath = childPath.replace(parentPath, "");
            relativePath = relativePath.replaceAll("\\\\", "/");
            return relativePath.startsWith("/") ? relativePath.substring(1) : relativePath;
        }
        catch (Exception e) {
            return null;
        }
    }
}
