package com.orient.download.controller;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2019-03-30 15:36
 */
@Controller
@RequestMapping("/seasync")
public class SeaFileController {


        private static final Logger logger = LoggerFactory.getLogger(UrlFilesToZip.class);
        /**
         * 下载多个文件!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!这里是正确方法！！！！！！！！！！！！！！！！！！！！！！！
         * @return
         */
        @RequestMapping("multipleDownLoad")
        public static void getFile(HttpServletRequest request, HttpServletResponse response)
                throws ClientProtocolException, IOException {

            List<String> fileNames = new ArrayList<String>();
            fileNames.add("E:\\108\\验收汇报资料\\【交付】技术准备测试流程分系统测试报告.doc");
            fileNames.add("E:\\108\\验收汇报资料\\【交付】技术准备测试流程分系统接口设计报告.doc");
            fileNames.add("E:\\108\\验收汇报资料\\ExtJs的在线表格设计方案.doc");

            String destFileName = request.getParameter("destFileName");

//            List<String> urlList = new ArrayList<>();
//            for(String id : StringUtils.splitToList(urls,",")){
//                urlList.add(id);
//            }
            try {
                String filename = new String((destFileName+".zip").getBytes("UTF-8"), "ISO8859-1");//控制文件名编码
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ZipOutputStream zos = new ZipOutputStream(bos);
                UrlFilesToZip s = new UrlFilesToZip();
                int idx = 1;
                String postfix = "";
                for (String oneFile : fileNames) {
                    if (!(oneFile == null || oneFile.indexOf(".") == -1)){
                    //如果图片地址为null或者地址中没有"."就返回""
                        postfix = oneFile.substring(oneFile.lastIndexOf(".") + 1).trim().toLowerCase();
                    }
                    if(postfix!=null && !postfix.isEmpty()) {
                        postfix = "."+postfix;
                    }
                    //destFileName + idx+postfix :  图片名称
                    //destFileName ： 压缩包名称
                    zos.putNextEntry(new ZipEntry(destFileName + idx+postfix));
//                    byte[] bytes = s.getImageFromURL(oneFile);
                    File file = new File(oneFile);
                    InputStream in = new FileInputStream(file);
                    byte[] bytes = s.readInputStream(in);
                    zos.write(bytes, 0, bytes.length);
                    zos.closeEntry();
                    idx++;
                }
                zos.close();
                response.setContentType("application/octet-stream; charset=utf-8");
//                 response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition", "attachment;fileName=" + filename);// 设置文件名
                OutputStream os = response.getOutputStream();
                os.write(bos.toByteArray());
                os.close();
            } catch (FileNotFoundException ex) {
                logger.error("FileNotFoundException", ex);
            } catch (Exception ex) {
                logger.error("Exception", ex);
            }
//            return os;
        }


//    public static String uploadFile(String username, String localAbsoluteFilePath, String serverSaveFilePath) {
//        RestTemplate restTemplate = RestTemplateUtil.getInstance();
//        FileSystemResource file = new FileSystemResource(new File(localAbsoluteFilePath));
//        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
//        params.add("userName", username);
//        params.add("filePath", "/" + username + "/public" + serverSaveFilePath);
//        params.add("file", file);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.parseMediaType("multipart/form-data"));
//        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
//        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(params, headers);
//        String result = restTemplate.postForObject(GlobalConstant.DATA_CENTRE_UPLOAD_FILE_API, requestEntity, String.class);
//        System.out.println(result);
//        return result;
//    }

    @RequestMapping("upload2")
    @ResponseBody
    public void upload2(HttpServletRequest request, HttpServletResponse response){

        List<String> fileNames = new ArrayList<String>();
        fileNames.add("E:\\108\\验收汇报资料\\【交付】技术准备测试流程分系统测试报告.doc");
        fileNames.add("E:\\108\\验收汇报资料\\【交付】技术准备测试流程分系统接口设计报告.doc");
        fileNames.add("E:\\108\\验收汇报资料\\ExtJs的在线表格设计方案.doc");
        List<String> names = new ArrayList<String>();
        names.add("【交付】技术准备测试流程分系统测试报告.doc");
        names.add("【交付】技术准备测试流程分系统接口设计报告.doc");
        names.add("ExtJs的在线表格设计方案.doc");


        List<String> list = new ArrayList<String>();
        // 获得项目的路径
        ServletContext sc = request.getSession().getServletContext();
        // 上传位置
        String path = sc.getRealPath("/img") + "/"; // 设定文件保存的目录
        File f = new File(path);
        if (!f.exists())
            f.mkdirs();

        for (int i = 0; i < fileNames.size(); i++) {
            // 获得原始文件名
            String fileName = names.get(i);
            System.out.println("原始文件名:" + fileName);
            // 新文件名
            String newFileName = UUID.randomUUID() + fileName;
            File file = new File(fileNames.get(i));
            if (file!=null) {
                try {
                    FileOutputStream fos = new FileOutputStream(path + newFileName);
                    InputStream in = new FileInputStream(file);
                    int b = 0;
                    while ((b = in.read()) != -1) {
                        fos.write(b);
                    }
                    fos.close();
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("上传图片到:" + path + newFileName);
            list.add(path + newFileName);
        }
    }

    @RequestMapping("upload")
    @ResponseBody
    public void addOrUpdateUser(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, FileUploadException {

        List<String> fileNames = new ArrayList<String>();
        fileNames.add("E:\\108\\验收汇报资料\\【交付】技术准备测试流程分系统测试报告.doc");
        fileNames.add("E:\\108\\验收汇报资料\\【交付】技术准备测试流程分系统接口设计报告.doc");
        fileNames.add("E:\\108\\验收汇报资料\\ExtJs的在线表格设计方案.doc");
        List<String> names = new ArrayList<String>();
        names.add("【交付】技术准备测试流程分系统测试报告.doc");
        names.add("【交付】技术准备测试流程分系统接口设计报告.doc");
        names.add("ExtJs的在线表格设计方案.doc");

        // 转码，建议使用过滤器转码
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        // 判断提交过来的表单是否为文件上传菜单，只适用于servlet
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        if (isMultipart) {
            // 构造一个文件上传处理对象
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);

//            Iterator<FileItem> items;
            List<FileItem> items = (List<FileItem>)upload.parseRequest(request);

            try {
                // 解析表单中提交的所有文件内容
//                items = upload.parseRequest(request).iterator();

                for(int i=0; i<3; i++){

                    // 取出上传文件的文件名称
                    String name = names.get(i);

                    // 取得上传文件以后的存储路径
                    String fileName = name.substring(
                            name.lastIndexOf('\\') + 1, name.length());

                    // 上传文件以后的存储路径 ， 有关路径问题，请参考另一篇博文：http://www.cnblogs.com/xiaoMzjm/p/3878758.html
                    String path = request.getServletContext().getRealPath("/")+"\\UpLoadFile"
                            + File.separatorChar + fileName;

                    // 上传文件
//                    File uploaderFile = new File(path);
//                    item.write(uploaderFile);


                    OutputStream out = new FileOutputStream(new File(path, name));
//                    OutputStream out = new FileOutputStream();
                    InputStream in = items.get(i).getInputStream();
                    int length = 0 ;
                    byte [] buf = new byte[1024] ;
                    System.out.println("获取上传文件的总共的容量："+items.get(i).getSize());
                    // in.read(buf) 每次读到的数据存放在   buf 数组中
                    while( (length = in.read(buf) ) != -1)
                    {
                        //在   buf 数组中 取出数据 写到 （输出流）磁盘上
                        out.write(buf, 0, length);
                    }
                    in.close();
                    out.close();


                    // 打印上传成功信息
//                    PrintWriter out = response.getWriter();
//                    out.print("success");

                }
//                while (items.hasNext()) {
//
//                    FileItem item = (FileItem) items.next();
//
//                    if (!item.isFormField()) {
//
//                        // 取出上传文件的文件名称
//                        String name = item.getName();
//
//                        // 取得上传文件以后的存储路径
//                        String fileName = name.substring(
//                                name.lastIndexOf('\\') + 1, name.length());
//
//                        // 上传文件以后的存储路径 ， 有关路径问题，请参考另一篇博文：http://www.cnblogs.com/xiaoMzjm/p/3878758.html
//                        String path = request.getServletContext().getRealPath("/")+"\\UpLoadFile"
//                                + File.separatorChar + fileName;
//
//                        // 上传文件
//                        File uploaderFile = new File(path);
//                        item.write(uploaderFile);
//
//                        // 打印上传成功信息
//                        PrintWriter out = response.getWriter();
//                        out.print("success");
//                    }
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        return syncUserBusiness.addOrUpdateUser(userModelList);
    }

}
