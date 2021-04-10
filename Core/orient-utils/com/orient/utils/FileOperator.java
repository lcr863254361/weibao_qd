package com.orient.utils;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 文件操作的底层操作类
 *
 * @author 施俊
 * @version 1.0
 * @see
 * @since 2004-11-29
 */
public class FileOperator {
    private static final Logger log = Logger.getLogger(FileOperator.class);

    public FileOperator() {
    }

    /**
     * 读取文件
     *
     * @param fileName 文件名称
     * @return
     */
    public static String readFile(String fileName) {
        try {
            File file = new File(fileName);
            String charset = getCharset(file);
            StringBuffer sb = new StringBuffer();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileName), charset));
            String str;
            while ((str = in.readLine()) != null) {
                sb.append(str + "\r\n");
            }
            in.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取文件的字符集
     *
     * @param file
     * @return
     */
    public static String getCharset(File file) {
        String charset = "GBK";
        byte[] first3Bytes = new byte[3];
        try {
            boolean checked = false;
            BufferedInputStream bis = new BufferedInputStream(
                    new FileInputStream(file));
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1)
                return charset;
            if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
                charset = "UTF-16LE";
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xFE
                    && first3Bytes[1] == (byte) 0xFF) {
                charset = "UTF-16BE";
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xEF
                    && first3Bytes[1] == (byte) 0xBB
                    && first3Bytes[2] == (byte) 0xBF) {
                charset = "UTF-8";
                checked = true;
            }
            bis.reset();

            if (!checked) {
                int loc = 0;
                while ((read = bis.read()) != -1) {
                    loc++;
                    if (read >= 0xF0)
                        break;
                    // 单独出现BF以下的，也算是GBK
                    if (0x80 <= read && read <= 0xBF)
                        break;
                    if (0xC0 <= read && read <= 0xDF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF)// 双字节 (0xC0 - 0xDF)
                            // (0x80 -
                            // 0xBF),也可能在GB编码内
                            continue;
                        else
                            break;
                        // 也有可能出错，但是几率较小
                    } else if (0xE0 <= read && read <= 0xEF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) {
                                charset = "UTF-8";
                                break;
                            } else
                                break;
                        } else
                            break;
                    }
                }

            }
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return charset;
    }

    public static boolean reNameFile(String oldName, String newName) {
        boolean jdg = true;
        try {
            File oldFile = new File(oldName);
            File newFile = new File(newName);
            oldFile.renameTo(newFile);
        } catch (Exception e) {
            e.printStackTrace();
            jdg = false;
        }
        return jdg;
    }

    /**
     * 将原文件拷贝到目标文件
     *
     * @param sourceFile
     * @param targetFile
     * @return boolean
     */
    public static boolean copyFile(String sourceFile, String targetFile) {
        String path = sourceFile;
        String target = targetFile;
        boolean flag = true;
        try {
            File file = new File(path);
            FileInputStream stream = new FileInputStream(file);//把文件读入
            OutputStream bos = new FileOutputStream(target);//建立一个上传文件的输出流
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
                bos.write(buffer, 0, bytesRead);//将文件写入服务器
            }
            bos.close();
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    /**
     * 文件夹下所有文件夹和文件的拷贝
     *
     * @param source
     * @param target
     */
    public static void copyDirectiory(String source, String target) {
        String file1 = target;
        String file2 = source;
        try {
            (new File(file1)).mkdirs();
            File[] file = (new File(file2)).listFiles();
            for (int i = 0; i < file.length; i++) {
                if (file[i].isFile()) {
                    FileInputStream input = new FileInputStream(file[i]);
                    FileOutputStream output = new FileOutputStream(file1 + "/" + file[i]
                            .getName());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (file[i].isDirectory()) {
                    copyDirectiory(file2 + "/" + file[i].getName(), file1 + "/" + file[i]
                            .getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List fileWithPath = new ArrayList();

    private void getFileWithPath(String target) {
        String file2 = target;
        try {
            File[] file = (new File(file2)).listFiles();
            for (int i = 0; i < file.length; i++) {
                if (file[i].isFile()) {
                    fileWithPath.add(file2 + File.separator + file[i].getName());
                }
                if (file[i].isDirectory()) {
                    this.getAllFileWithPath(file2 + File.separator + file[i]
                            .getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据文件路径取得该文件路径下的所有文件
     *
     * @param target 文件路径
     * @return List 返回的文件路径list
     */
    public List getAllFileWithPath(String target) {
        this.getFileWithPath(target);
        return this.fileWithPath;
    }

    /**
     * 删除文件夹，如果该文件夹下有子文件或者文件夹，则全部删除
     *
     * @param path 要删除的文件夹
     * @return boolean
     */
    public static boolean delFoldsWithChilds(String path) {
        int files = 0;
        File file = new File(path);
        File[] tmp = file.listFiles();
        if (tmp == null) {
            files = 0;
        } else {
            files = tmp.length;
        }
        for (int i = 0; i < files; i++) {
            FileOperator.delFoldsWithChilds(tmp[i].getAbsolutePath());
        }
        boolean ret = FileOperator.deleteFolder(path);
        return ret;
    }

    /**
     * 判断文件或者文件夹是否存在
     *
     * @param folderName 文件或者文件夹的绝对路径
     * @return boolean
     */
    public static boolean isFileExist(String folderName) {
        File file = new File(folderName);
        boolean returnBoolean = file.exists();
        return returnBoolean;
    }

    /**
     * 这里的路径格式必须是：c:\tmp\tmp\ 或者是c:\tmp\tmp
     *
     * @param path
     * @return boolean
     */
    public static boolean createFolders(String path) {
        return (new File(path)).mkdirs();
    }

    /**
     * 取得文件名称（带后缀）
     *
     * @param filePath 文件路径（包括文件名称）
     * @return String 文件名称
     */
    public static String getFileName(String filePath) {
        String fileName = "";
        String tmpFilePath = filePath;
        int winIndex = tmpFilePath.lastIndexOf("\\");
        int linuxIndex = tmpFilePath.lastIndexOf("/");
        if (winIndex != -1)
            fileName = tmpFilePath.substring(winIndex + 1, tmpFilePath
                    .length()).trim();
        if (linuxIndex != -1)
            fileName = tmpFilePath.substring(linuxIndex + 1, tmpFilePath
                    .length()).trim();
        return fileName;
    }

    /**
     * 得到文件的后缀
     *
     * @param fileName 文件名称
     * @return String 后缀名称
     */
    public static String getSuffix(String fileName) {
        String returnSuffix = "";
        String tmp = "";
        try {
            int index = fileName.lastIndexOf(".");
            if (index == -1) {
                tmp = "";
            } else
                tmp = fileName.substring(index + 1, fileName.length());
        } catch (Exception e) {
            tmp = "";
        }
        returnSuffix = tmp;
        return returnSuffix;
    }

    /**
     * 递归创建文件
     *
     * @param path
     * @return boolean
     */
    public static boolean createFolds(String path) {
        boolean ret = false;
        String child = path;
        if (!FileOperator.isFileExist(path)) {
            int i = path.lastIndexOf(File.separator);
            String pathTmp = path.substring(0, i);
            child = pathTmp;
            FileOperator.createFolds(pathTmp);
            ret = FileOperator.createFolder(child);
        } else {
            ret = false;
        }
        return ret;
    }

    /**
     * 将文件的路径格式转换为标准的文件路径格式
     *
     * @param inputPath 原文件路径
     * @return String 转换后的文件路径
     */
    public static String toStanderds(String inputPath) {
        String rtp = "";
        /**
         * 这是使用正则表达式进行替换 先把所有的路径格式替换为linux下的，会出现多个连接的情况
         */
        String pathChar = "/";
        String pathCharLin = "/";
        String pathCharWin = "\\";
        //		char[] mychar = path.toCharArray();
        if (pathCharLin.equalsIgnoreCase(File.separator)) {
            pathChar = "/";
        }
        if (pathCharWin.equalsIgnoreCase(File.separator)) {
            pathChar = "\\\\";
        }
        rtp = FileOperator.replaceString("\\\\+|/+", inputPath, "/");
        rtp = FileOperator.replaceString("/+", rtp, pathChar);
        /**
         * 这是使用正常的循环进行替换
         */
        /***********************************************************************
         * / String path = inputPath; char pathChar = '/'; char pathCharLin =
         * '/'; char pathCharWin = '\\'; char[] mychar = path.toCharArray();
         * if(String.valueOf((pathCharWin)).equalsIgnoreCase(File.separator)) {
         * pathChar = pathCharWin; }
         * if(String.valueOf((pathCharLin)).equalsIgnoreCase(File.separator)) {
         * pathChar = pathCharLin; } for(int i = 0;i <mychar.length;i++) {
         * if(mychar[i] == pathCharWin || mychar[i] == pathCharLin) { mychar[i] =
         * pathChar; } if(mychar[i] != pathCharLin && mychar[i] != pathCharWin)
         * rtp += String.valueOf(mychar[i]); if(i <mychar.length-1) {
         * if(mychar[i] == pathChar && mychar[i+1] != pathChar && mychar[i+1] !=
         * pathCharWin && mychar[i+1] != pathCharLin) { rtp +=
         * String.valueOf(mychar[i]); } } } /
         **********************************************************************/
        return rtp;
    }

    /**
     * 将路径转换为linux路径－也可使用为将http的相对路径进行转换
     *
     * @param inputPath
     * @return String
     */
    public static String toLinuxStanderds(String inputPath) {
        String rtp = "";
        /**
         * 这是使用正则表达式进行替换
         */
        rtp = FileOperator.replaceString("\\\\+|/+", inputPath, "/");
        rtp = FileOperator.replaceString("/+", rtp, "/");
        /**
         * 这是使用正常的循环进行替换
         */
        /***********************************************************************
         * / String path = inputPath; char pathChar = '/'; char pathCharLin =
         * '/'; char pathCharWin = '\\'; char[] mychar = path.toCharArray();
         * if(String.valueOf((pathCharWin)).equalsIgnoreCase(File.separator)) {
         * pathChar = pathCharWin; }
         * if(String.valueOf((pathCharLin)).equalsIgnoreCase(File.separator)) {
         * pathChar = pathCharLin; } pathChar = '/'; for(int i = 0;i
         * <mychar.length;i++) { if(mychar[i] == pathCharWin || mychar[i] ==
         * pathCharLin) { mychar[i] = pathChar; } if(mychar[i] != pathCharLin &&
         * mychar[i] != pathCharWin) rtp += String.valueOf(mychar[i]); if(i
         * <mychar.length-1) { if(mychar[i] == pathChar && mychar[i+1] !=
         * pathChar && mychar[i+1] != pathCharWin && mychar[i+1] != pathCharLin) {
         * rtp += String.valueOf(mychar[i]); } } } /
         **********************************************************************/
        return rtp;
    }

    /**
     * 在已经存在的路径下创建文件夹
     *
     * @param path
     * @return boolean
     */
    public static boolean createFolder(String path/* ,String folderName */) {
        //String fPath = path + File.separator + folderName;
        File file = new File(path);
        boolean returnBoolean = file.mkdir();
        return returnBoolean;
    }

    /**
     * 删除文件夹，当该文件夹下有文件或者文件夹的时候不能删除该文件夹
     *
     * @param path
     * @return boolean
     */
    public static boolean deleteFolder(String path/* ,String folderName */) {
        //String fPath = path + File.separator + folderName;
        File file = new File(path);
        boolean returnBoolean = file.delete();
        return returnBoolean;
    }

    /**
     * 创建文件或者文件夹
     *
     * @param path
     * @param fileName
     * @return boolean
     */
    public static boolean createFile(String path, String fileName) {
        String fPath = path + File.separator + fileName;
        File file = new File(fPath);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean createFile(String path, byte[] bytes) {
        try {
            if (!new File(path).exists()) {
                new File(path).createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(path);
            fos.write(bytes);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean createFile(String path, InputStream inputStream) {
        try {
            if (!new File(path).exists()) {
                new File(path).createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(path);
            IOUtils.copyLarge(inputStream, fos);

            if (inputStream != null) {
                inputStream.close();
            }
            if (fos != null) {
                fos.flush();
                fos.close();

            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean createFile(String fileName) {
        String fPath = fileName;
        File file = new File(fPath);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 替换函数
     *
     * @param pattern    正则表达式
     * @param inputStr   要替换的字符串
     * @param replaceStr 要被替换的字符串
     * @return String 替换之后的结果
     */
    public static String replaceString(String pattern, String inputStr, String replaceStr) {
        java.util.regex.Pattern p = null; //正则表达式
        java.util.regex.Matcher m = null; //操作的字符串
        String value = "";
        try {//['%\"|\\\\]校验非法字符.'"|\正则表达式
            //^[0-9]*[1-9][0-9]*$
            //"['%\"|\n\t\\\\]"
            //校验是否全部是空格：[^ ]
            p = java.util.regex.Pattern.compile(pattern);
            m = p.matcher(inputStr);
            value = m.replaceAll(replaceStr);
            m = p.matcher(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }


    /**
     * 压缩文件
     *
     * @param inputFileName  要压缩的文件或文件夹路径，例如：c:\\a.txt,c:\\a\
     * @param outputFileName 输出zip文件的路径，例如：c:\\a.zip
     */
    public static void zip(String inputFileName, String outputFileName, String base)
            throws Exception {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
                outputFileName));
        out.setEncoding("GBk");
        zip(out, new File(inputFileName), base);
        log.debug("压缩完成！");
        out.closeEntry();
        out.close();
    }

    /**
     * 压缩文件
     *
     * @param out  org.apache.tools.zip.ZipOutputStream
     * @param file 待压缩的文件
     * @param base 压缩的根目录
     */
    private static void zip(ZipOutputStream out, File file, String base)
            throws Exception {
        if (file.isDirectory()) {
            File[] fl = file.listFiles();
            base = base.length() == 0 ? "" : base + File.separator;
            for (int i = 0; i < fl.length; i++) {
                zip(out, fl[i], base + fl[i].getName());
            }
        } else {
            if ("".equals(base)) {
                out.putNextEntry(new ZipEntry(file.getName()));
            } else {
                out.putNextEntry(new ZipEntry(base));
            }
            log.debug("添加压缩文件：" + base);
            FileInputStream in = new FileInputStream(file);
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = in.read(b)) != -1) {
                out.write(b, 0, len);
            }
            in.close();
        }
    }

    /**
     * 解压zip文件
     *
     * @param zipFileName     待解压的zip文件路径，例如：c:\\a.zip
     * @param outputDirectory 解压目标文件夹,例如：c:\\a\
     */
    public static boolean unZip(String zipFileName, String outputDirectory)
            throws Exception {
        boolean flag = false;
        ZipFile zipFile = new ZipFile(zipFileName);
        try {
            Enumeration e = zipFile.getEntries();
            ZipEntry zipEntry = null;
            createDirectory(outputDirectory, "");
            while (e.hasMoreElements()) {
                zipEntry = (ZipEntry) e.nextElement();//文件名或文件夹名
                log.debug("解压：" + zipEntry.getName());
                if (zipEntry.isDirectory()) {
                    String name = zipEntry.getName();
                    name = name.substring(0, name.length() - 1);
                    File f = new File(outputDirectory + File.separator + name);
                    f.mkdir();
                    log.debug("创建目录：" + outputDirectory + File.separator + name);
                } else {
                    String fileName = zipEntry.getName();
                    fileName = fileName.replace('\\', '/');
                    if (fileName.indexOf("/") != -1) {
                        createDirectory(outputDirectory, fileName.substring(0, fileName.lastIndexOf("/")));
                        fileName = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.length());
                    }
                    File f = new File(outputDirectory + File.separator + zipEntry.getName());
                    f.createNewFile();
                    InputStream in = zipFile.getInputStream(zipEntry);
                    FileOutputStream out = new FileOutputStream(f);
                    byte[] by = new byte[1024];
                    int c;
                    while ((c = in.read(by)) != -1) {
                        out.write(by, 0, c);
                    }
                    in.close();
                    out.close();
                }
                flag = true;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            flag = false;

        } finally {
            zipFile.close();
            log.debug("解压完成！");
        }
        return flag;
    }

    private static void createDirectory(String directory, String subDirectory) {
        String dir[];
        File fl = new File(directory);
        try {
            if (subDirectory == "" && fl.exists() != true) {
                fl.mkdir();
            } else if (subDirectory != "") {
                dir = subDirectory.replace('\\', '/').split("/");
                for (int i = 0; i < dir.length; i++) {
                    File subFile = new File(directory + File.separator + dir[i]);
                    if (subFile.exists() == false)
                        subFile.mkdir();
                    directory += File.separator + dir[i];
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] s) {
        try {
            FileOperator.zip("d:/temp.txt", "d:/temp.zip", "first/second/temp.txt");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void doDeleteFile(File parentFile) {
        File[] childFiles = parentFile.listFiles();
        for (File singleFile : childFiles) {
            if (singleFile.isDirectory()) {
                //递归
                doDeleteFile(singleFile);
            } else {
                singleFile.delete();
            }
        }
        parentFile.delete();
    }

    public static void downLoadFile(HttpServletRequest request, HttpServletResponse response, String filePath, String filename) {
        OutputStream outp = null;
        try {
            outp = response.getOutputStream();
            File file = new File(filePath);
            if (file.exists()) {
                response.setContentType("APPLICATION/OCTET-STREAM");
                String filedisplay = filename;
                if (filename.indexOf("/") >= 0) {
                    filedisplay = filename.substring(filename.lastIndexOf("/") + 1);
                } else if (filename.indexOf("\\") >= 0) {
                    filedisplay = filename.substring(filename.lastIndexOf("\\") + 1);
                }
                String agent = (String) request.getHeader("USER-AGENT");
                // firefox
                if (agent != null && agent.indexOf("MSIE") == -1) {
                    String enableFileName = "=?UTF-8?B?"
                            + (new String(Base64.getBase64(filedisplay))) + "?=";
                    response.setHeader("Content-Disposition",
                            "attachment; filename=" + enableFileName);
                } else {
                    filedisplay = URLEncoder.encode(filedisplay, "utf-8");
                    response.addHeader("Content-Disposition",
                            "attachment;filename=" + filedisplay);
                }
                FileInputStream in = null;
                try {
                    outp = response.getOutputStream();
                    in = new FileInputStream(filePath);
                    byte[] b = new byte[1024 * 64];
                    int i = 0;
                    while ((i = in.read(b)) > 0) {
                        outp.write(b, 0, i);
                    }
                    outp.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (in != null) {
                        in.close();
                        in = null;
                    }
                    if (outp != null) {
                        outp.close();
                        outp = null;
                        response.flushBuffer();
                    }
                }
            } else {
                outp.write("文件不存在!".getBytes("GBK"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> uploadFile(HttpServletRequest request, String destPath) {
        List<String> retVal = new ArrayList<>();
        //解析器解析request的上下文
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //先判断request中是否包涵multipart类型的数据，
        if (multipartResolver.isMultipart(request)) {
            if (!FileOperator.isFileExist(destPath)) {
                FileOperator.createDirectory(destPath, destPath);
            }
            //再将request中的数据转化成multipart类型的数据
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                MultipartFile file = multiRequest.getFile((String) iter.next());
                if (file.isEmpty()) {
                    continue;
                }
                String fileName = file.getOriginalFilename();
                //保存文件至本地
                String finalName = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date()) + "_" + fileName;
                String fullPath = destPath + File.separator + finalName;
                try {
                    if (FileOperator.createFile(fullPath, file.getBytes())) {
                        retVal.add(fullPath);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return retVal;
    }


    /**
     * 获取目录下面所有文件的路径列表
     *
     * @param folderPath
     * @return
     */
    public static List<String> getChildFilePath(String folderPath) {

        List<String> filePathList = new ArrayList<>();
        File parentFile = new File(folderPath);
        if (parentFile.exists() == false) {
            return filePathList;
        }
        File[] childFiles = parentFile.listFiles();
        for (File singleFile : childFiles) {
            if (singleFile.isDirectory()) {
                continue;
            } else {
                filePathList.add(singleFile.getPath());
            }
        }
        return filePathList;
    }


    /**
     * 获取文件夹及其子文件夹中的文件路径
     *
     * @param folderPath
     * @param pathList
     * @return
     */
    public static boolean getChildFilePath(String folderPath, List<String> pathList) {
        // List<String> filePathList = new ArrayList<>();
        File parentFile = new File(folderPath);
        if (parentFile.exists() == false) {
            return true;
        }
        File[] childFiles = parentFile.listFiles();
        for (File singleFile : childFiles) {
            if (singleFile.isDirectory()) {
                getChildFilePath(singleFile.getPath(), pathList);
            } else {
                // filePathList.add(singleFile.getPath());
                pathList.add(singleFile.getPath());
            }
        }
        return true;
    }

    /**
     * 判断是否是图片类型
     *
     * @param filePath
     * @return
     */
    public static Boolean isImage(String filePath) {
        Boolean retVal = false;
        try {
            BufferedImage image = ImageIO.read(new File(filePath));
            if (null != image) {
                retVal = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retVal;
    }

}