package com.aptx.utils;

import com.google.common.base.Strings;

import javax.activation.MimetypesFileTypeMap;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;

public abstract class HttpUtil {
    public static final MimetypesFileTypeMap MIMETYPES_MAP = new MimetypesFileTypeMap();

    public static String getContentType(String fileName) {
        return MIMETYPES_MAP.getContentType(fileName);
    }

    public static String getLocalIpAddress() {
        try {
            InetAddress ia = InetAddress.getLocalHost();
            String ip = ia.getHostAddress();
            return ip;
        }
        catch (UnknownHostException e) {
            //No operation
        }
        return null;
    }

    public static String getRemoteIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")) {
                //获取本机的IP
                ip = getLocalIpAddress();
            }
        }

        //通过多个代理情况的特殊处理
        if(ip!=null && ip.indexOf(",") > 0) {
            ip = ip.substring(0, ip.indexOf(","));
        }

        return ip;
    }

    /***************************** File Download *****************************/
    public static String extractContentDispositionFileName(String contentDisposition) {
        String fileNameKey = "filename=";
        if(contentDisposition == null) {
            return null;
        }
        int startIndex = contentDisposition.indexOf(fileNameKey);
        if(startIndex == -1) {
            return null;
        }
        String fileName = contentDisposition.substring(startIndex + fileNameKey.length());
        if(fileName.startsWith("\"")) {
            int endIndex = fileName.indexOf("\"", 1);
            if(endIndex != -1) {
                return fileName.substring(1, endIndex);
            }
        }
        else {
            int endIndex = fileName.indexOf(";");
            if (endIndex != -1) {
                return fileName.substring(0, endIndex);
            }
        }
        return fileName;
    }

    public static String decodeContentDispositionFileName(String fileName) {
        if(Strings.isNullOrEmpty(fileName)) {
            return null;
        }
        else if(fileName.startsWith("=?")) {
            try {
                return MimeUtility.decodeText(fileName);
            }
            catch (UnsupportedEncodingException e) {
                throw new IllegalStateException(e);
            }
        }
        else {
            try {
                return URLDecoder.decode(fileName, CharsetUtil.UTF_8);
            }
            catch (UnsupportedEncodingException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    public static String encodeContentDispositionFileName(String fileName, String charset) {
        if(Strings.isNullOrEmpty(fileName)) {
            return null;
        }
        else {
            try {
                return MimeUtility.encodeText(fileName, charset, null);
            }
            catch (UnsupportedEncodingException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
