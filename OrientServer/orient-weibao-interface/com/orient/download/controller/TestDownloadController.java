package com.orient.download.controller;

import com.alibaba.fastjson.JSONObject;
import com.orient.download.bean.checkHeadRowCellBean.CheckListTableBean;
import com.orient.download.bean.currentTaskBean.DivingTaskBean;
import com.orient.download.bean.currentTaskBean.SysUserBean;
import com.orient.download.bean.dailyWorkEntity.DivingTaskEntity;
import com.orient.download.bean.inform.BaseEntity;
import com.orient.download.bean.inform.CurrentStateBean;
import com.orient.download.bean.inform.InformBean;
import com.orient.download.bean.productStructEntity.StructSystemEntity;
import com.orient.download.bean.queryDivingTaskBean.DiveNumModel;
import com.orient.download.bean.queryDivingTaskBean.DivingQueryModel;
import com.orient.download.bean.sparePartsBean.DeviceBean;
import com.orient.download.bean.sparePartsBean.DeviceInstBean;
import com.orient.download.bean.sparePartsBean.DeviceModel;
import com.orient.download.bean.sparePartsBean.TroubleDeviceBean;
import com.orient.download.business.TestDownloadBusiness;
import com.orient.download.enums.HttpResponse;
import com.orient.download.enums.HttpResponseStatus;
import com.orient.web.base.BaseController;
import com.orient.weibao.business.InformMgrBusiness;
import org.apache.http.client.ClientProtocolException;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2019-02-20 15:02
 */
@Controller
@RequestMapping("/api/download")
public class TestDownloadController extends BaseController {


    private static final Logger logger = LoggerFactory.getLogger(UrlFilesToZip.class);

    @Autowired
    TestDownloadBusiness testDownloadBusiness;

    /**
     * 获取系统的所有用户
     *
     * @return
     */
    @RequestMapping("getSysUser")
    @ResponseBody
    public HttpResponse<List<SysUserBean>> getSysUser() {
        return testDownloadBusiness.getSysUser();
    }

    @RequestMapping("getCurrentTask")
    @ResponseBody
    public HttpResponse<DivingTaskBean> getCurrentTask() {
        return testDownloadBusiness.getCurrentTask();
    }

    /**
     * 根据检查表实例Id下载检查表实例
     *
     * @param checkTableInstId
     * @return
     */
    @RequestMapping("getCheckTableTempInst")
    @ResponseBody
    public HttpResponse getTableById(String checkTableInstId) {
        //false表示下载的表是待填写的
        CheckListTableBean retVal = testDownloadBusiness.getTableById(checkTableInstId,false);
        HttpResponse httpResponse = new HttpResponse();
        if (retVal != null) {
            httpResponse.setData(JSONObject.toJSON(retVal));
            httpResponse.setResult(HttpResponseStatus.SUCCESS.toString());
        } else {
            httpResponse.setResult(HttpResponseStatus.SUCCESS.toString());
//            httpResponse.setMsg("服务端数据有误");
        }
        return httpResponse;
    }

    /**
     * 下载已经完成的表格
     *
     * @param checkTableInstId
     * @return
     */
    @RequestMapping("getCompleteTableById")
    @ResponseBody
    public HttpResponse getCompleteTableById(String checkTableInstId) {
        //true 表示下载的表是已经完成的表
        CheckListTableBean retVal = testDownloadBusiness.getTableById(checkTableInstId, true);
        HttpResponse httpResponse = new HttpResponse();
        if (retVal != null) {
            httpResponse.setData(JSONObject.toJSON(retVal));
            httpResponse.setResult(HttpResponseStatus.SUCCESS.toString());
        } else {
            httpResponse.setResult(HttpResponseStatus.FAIL.toString());
            httpResponse.setMsg("服务端表格待填写");
        }
        return httpResponse;
    }


    /**
     * 获取所有设备实例信息
     *
     * @return
     */
    @RequestMapping("getDeviceInstList")
    @ResponseBody
    public HttpResponse<List<DeviceModel>> getDeviceInstList() {
        return testDownloadBusiness.getDeviceInstList();
    }


    /**
     * 获取所有耗材信息
     *
     * @return
     */
    @RequestMapping("getConsumeList")
    @ResponseBody
    public HttpResponse<List<DeviceBean>> getConsumeList() {
        return testDownloadBusiness.getConsumeList();
    }

    /**
     * 下载压缩文件的接口
     * @param id  单元格Id
     * @param type “1"是单元格的多张照片的压缩文件，”2“是故障记录的多张照片的压缩文件
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("downloadImage")
    @ResponseBody
    public void downloadImage(@RequestParam(value = "id") String id,@RequestParam(value = "type") String type,HttpServletResponse response) throws Exception {
//        OutputStream os = null;
//        os = testDownloadBusiness.downLoadImages(id,type,response);
        testDownloadBusiness.downLoadImages(id,type,response);
//        return os;
    }

    @RequestMapping("multipleDownLoad")
    @ResponseBody
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
            String filename = new String((destFileName + ".zip").getBytes("UTF-8"), "ISO8859-1");//控制文件名编码
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            java.util.zip.ZipOutputStream zos = new java.util.zip.ZipOutputStream(bos);
            UrlFilesToZip s = new UrlFilesToZip();
            int idx = 1;
            String postfix = "";
            for (String oneFile : fileNames) {
                if (!(oneFile == null || oneFile.indexOf(".") == -1)) {
                    //如果图片地址为null或者地址中没有"."就返回""
                    postfix = oneFile.substring(oneFile.lastIndexOf(".") + 1).trim().toLowerCase();
                }
                if (postfix != null && !postfix.isEmpty()) {
                    postfix = "." + postfix;
                }
                //destFileName + idx+postfix :  图片名称
                //destFileName ： 压缩包名称
                zos.putNextEntry(new ZipEntry(destFileName + idx + postfix));
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

    /**
     * 获取通知信息
     *
     * @return
     */
    @RequestMapping("getInform")
    @ResponseBody
    public BaseEntity<CurrentStateBean> getInformList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return testDownloadBusiness.getInformList(request, response);
    }

    /**
     * 获取拆解任务管理中的当前任务结构
     *
     * @return
     */
    @RequestMapping("getDestroyCurrentTask")
    @ResponseBody
    public HttpResponse<DivingTaskBean> getDestroyCurrentTask() {
        return testDownloadBusiness.getDestroyCurrentTask();
    }

    /**
     * 下载文件接口
     *
     * @param destroyFileId 文件id
     * @param response      response对象
     * @return
     */
    @RequestMapping(value = "downloadFile")
    @ResponseBody
    public void downloadFile(String destroyFileId, HttpServletResponse response) {
        testDownloadBusiness.downloadFile(destroyFileId, response);
    }

    /***
     * 针对自增的表格，表格类型是2
     * @param checkTableId 检查实例表的ID
     * @param headerId   检查实例表头的ID
     * @param rowNumber   检查行实例的行号
     * @return 单元格ID
     */
    @RequestMapping("getIncreaseTableCellId")
    @ResponseBody
    public HttpResponse getIncreaseTableCellId(@RequestParam(value = "checkTableInstId") String checkTableId, @RequestParam(value = "headerId") String headerId, @RequestParam(value = "rowNumber") String rowNumber) {
        return testDownloadBusiness.getIncreaseTableCellId(checkTableId, headerId, rowNumber);
    }

    /**
     * 获取结束的任务及任务详情及表格
     * @param
     * @return
     */
    @RequestMapping("getDivingTaskList")
    @ResponseBody
    public HttpResponse<DivingQueryModel> getEndTaskList() {
        return testDownloadBusiness.getEndTaskList();
    }

    /**
     * 通过单元格的服务器ID查询具体的故障
     * @param cellId
     * @return
     */
    @RequestMapping(value = "queryCellTrouble")
    @ResponseBody
    public HttpResponse<TroubleDeviceBean> queryCellTrouble(@RequestParam(value = "cellId") String cellId){
        return testDownloadBusiness.queryCellTrouble(cellId);
    }

    /**\
     * 下载单个文件的接口
     * @param id
     * @param type “1"是签署图片，”2“是故障录音
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "downloadSingleFile")
    @ResponseBody
    public void downloadSingleFile(@RequestParam(value = "id") String id, @RequestParam(value = "type") String type,HttpServletResponse response) throws Exception{
        testDownloadBusiness.downloadSingleFile(id,type,response);
    }

    /**
     * 获取产品结构树
     * @return
     */
    @RequestMapping("getProductStructList")
    @ResponseBody
    public HttpResponse<List<StructSystemEntity>> getProductStructList() {
        return testDownloadBusiness.getProductStructList();
    }

    /**
     * 获取当前航段的下潜任务
     * @return
     */
    @RequestMapping("getCurrentHdDivingTaskList")
    @ResponseBody
    public HttpResponse<List<DivingTaskEntity>> getCurrentHdDivingTaskList(){
        return testDownloadBusiness.getCurrentHdDivingTaskList();
    }

}
