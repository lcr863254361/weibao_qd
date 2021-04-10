package com.orient.modeldata.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2017-06-12 11:26
 */
public class FtpFileUtil {
    public static final String UPLOAD_ROOT = "上传文件";
    public static final String IMPORT_ROOT = "导入文件";
    public static final String EXPORT_ROOT = "导出文件";


    public static final SimpleDateFormat pathFormat = new SimpleDateFormat("-yyyy-MM-dd-HHmmss-");
    public static final SimpleDateFormat fileNameFormat = new SimpleDateFormat("yyyyMMddHHmmss_");

    public static String getRelativeUploadPath(String root) {
        String subFolder = pathFormat.format(new Date()).replaceAll("-", "/");
        String relativePath = "/" + root + subFolder;
        return relativePath;
    }

    public static void makeDir(String parentFolder, String subFolder) {
        new File(parentFolder + subFolder).mkdirs();
    }

    public static String getOnlyFileName(String fileName) {
        String prefix = fileNameFormat.format(new Date());
        return prefix + fileName;
    }
}
