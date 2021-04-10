package com.orient.download.controller;

import com.alibaba.fastjson.JSONObject;
import com.orient.download.bean.dailyWorkEntity.DailyWorkEntity;
import com.orient.download.bean.deviceInstCheckBean.DeviceInstCheckEntity;
import com.orient.download.bean.updateConsume.UpdateConsumeReq;
import com.orient.download.bean.uploadCheckInstBean.CheckListTableBean;
import com.orient.download.bean.uploadCheckInstBean.UploadCheckInst;
import com.orient.download.bean.sparePartsBean.*;
import com.orient.download.bean.uploadSignBean.SignModel;
import com.orient.download.bean.uploadSignBean.UploadSignReq;
import com.orient.download.bean.uploadWaterRunning.DiveModel;
import com.orient.download.bean.uploadWaterRunning.UploadDiveReq;
import com.orient.download.business.TestUploadBusiness;
import com.orient.download.enums.HttpResponse;
import com.orient.download.enums.HttpResponseStatus;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseController;
import com.orient.web.base.CommonResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2019-02-21 16:51
 */
@Controller
@RequestMapping("/api/upload")
public class TestUploadController extends BaseController {

    @Autowired
    TestUploadBusiness testUploadBusiness;

    /**
     * 更新设备信息
     *
     * @param deviceInstListBeans
     * @return
     */
    @RequestMapping("deviceDataUpdate")
    @ResponseBody
    public HttpResponse<String> deviceDataUpdate(@RequestBody DeviceInstListBeans deviceInstListBeans) throws Exception {
        HttpResponse httpResponse = new HttpResponse();
        if (deviceInstListBeans.getListSize() > 0) {
            List<DeviceInstBean> deviceInstBeanList = deviceInstListBeans.getDeviceModelList();
            for (DeviceInstBean deviceInstBean : deviceInstBeanList) {
                httpResponse = testUploadBusiness.deviceDataUpdate(deviceInstBean);
                if (httpResponse.getResult().equals("fail")) {
                    return httpResponse;
                }
            }
        } else {
            //终端未传数据给服务端
            httpResponse.setResult(HttpResponseStatus.FAIL.toString());
            httpResponse.setMsg("更新的设备信息为空！");
            httpResponse.setData(new ArrayList<>());
        }
        return httpResponse;
    }

    /**
     * 替换设备
     *
     * @param replaceDeviceListBeans
     * @return
     */
    @RequestMapping("replaceDevice")
    @ResponseBody
    public HttpResponse<String> replaceDevice(@RequestBody ReplaceDeviceListBeans replaceDeviceListBeans) {
        HttpResponse httpResponse = new HttpResponse();
        String recorder = replaceDeviceListBeans.getRecorderId();
        String taskId = replaceDeviceListBeans.getTaskId();
        if (replaceDeviceListBeans.getListSize() > 0) {
            List<ReplaceDeviceBean> replaceDeviceBeanList = replaceDeviceListBeans.getReplaceDeviceBeanList();
            for (ReplaceDeviceBean replaceDeviceBean : replaceDeviceBeanList) {
                httpResponse = testUploadBusiness.replaceDevice(replaceDeviceBean, recorder, taskId);
            }
        } else {
            //终端未传数据给服务端
            httpResponse.setResult(HttpResponseStatus.FAIL.toString());
            httpResponse.setMsg("替换的设备信息为空！");
            httpResponse.setData(new ArrayList<>());
        }
        return httpResponse;
    }

    /**
     * 故障设备
     *
     * @param troubleDeviceListBeans
     * @return
     */
    @RequestMapping("troubleDevice")
    @ResponseBody
    public HttpResponse<String> troubleDevice(@RequestBody TroubleDeviceListBeans troubleDeviceListBeans) {
        HttpResponse httpResponse = new HttpResponse();
        String recorder = troubleDeviceListBeans.getRecorderId();
        String taskId = troubleDeviceListBeans.getTaskId();
        if (troubleDeviceListBeans.getListSize() > 0) {
            testUploadBusiness.troubleDevice(troubleDeviceListBeans.getTroubleDeviceBeanList(), recorder, taskId);
            httpResponse.setResult(HttpResponseStatus.SUCCESS.toString());
            httpResponse.setData("1");
//            for (TroubleDeviceBean troubleDeviceBean : troubleDeviceListBeans.getTroubleDeviceBeanList()) {
//                httpResponse = testUploadBusiness.troubleDevice(troubleDeviceBean, recorder, taskId);
//            }
        } else {
            httpResponse.setResult(HttpResponseStatus.FAIL.toString());
            httpResponse.setMsg("故障设备信息为空！");
            httpResponse.setData(new ArrayList<>());
        }
        return httpResponse;
    }

    /**
     * 终端提交填写的检查表实例
     *
     * @param uploadCheckInst
     * @return
     */
//    @RequestMapping("uploadCheckInst")
//    @ResponseBody
//    public HttpResponse uploadCheckInst(@RequestBody UploadCheckInst uploadCheckInst) {
//        HttpResponse httpResponse = new HttpResponse();
//        if (uploadCheckInst.getListSize() > 0) {
//            List<CheckListTableBean> checkListTableBeanList = uploadCheckInst.getCheckListTableBeanList();
//            String taskId=uploadCheckInst.getTaskId();
//            String taskName=uploadCheckInst.getTaskName();
//            for (CheckListTableBean checkListTableBean : checkListTableBeanList) {
//                httpResponse = testUploadBusiness.uploadCheckInst(checkListTableBean,taskId,taskName);
//                if (httpResponse.getResult().equals("fail")) {
//                    return httpResponse;
//                }
//            }
//        } else {
//            //终端未传数据给服务端
//            httpResponse.setResult(HttpResponseStatus.FAIL.toString());
//            httpResponse.setMsg("表格信息为空！");
//            httpResponse.setData(new ArrayList<>());
//        }
//        return httpResponse;
//    }
    @RequestMapping("uploadCheckInst")
    @ResponseBody
    public HttpResponse uploadCheckTableInst(@RequestBody UploadCheckInst uploadCheckInst) {
        HttpResponse httpResponse = new HttpResponse();
        if (uploadCheckInst.getListSize() > 0) {

            List<CheckListTableBean> checkListTableBeanList = uploadCheckInst.getCheckListTableBeanList();
            String taskId = uploadCheckInst.getTaskId();
            String taskName = uploadCheckInst.getTaskName();
//            for (CheckListTableBean checkListTableBean : checkListTableBeanList) {
            List<CheckListTableBean> increaseRowTableList = testUploadBusiness.uploadCheckTableInst(checkListTableBeanList, taskId, taskName);
            if (increaseRowTableList != null && increaseRowTableList.size() > 0) {
                httpResponse.setResult(HttpResponseStatus.SUCCESS.toString());
                httpResponse.setData(increaseRowTableList);
            } else {
                httpResponse.setResult(HttpResponseStatus.SUCCESS.toString());
                httpResponse.setData("1");
            }
            // if (httpResponse.getResult().equals("fail")) {
            //                    return httpResponse;//                }
        } else {
            //终端未传数据给服务端
            httpResponse.setResult(HttpResponseStatus.FAIL.toString());
            httpResponse.setMsg("表格信息为空！");
            httpResponse.setData(new ArrayList<>());
        }
        return httpResponse;
    }

    @RequestMapping("uploadSignInfo")
    @ResponseBody
    public HttpResponse uploadSignInfo(@RequestBody UploadSignReq uploadSignReq) {
        HttpResponse httpResponse = new HttpResponse();
        if (uploadSignReq.getListSize() > 0) {
            for (SignModel signModel : uploadSignReq.getSignModelList()) {
                httpResponse = testUploadBusiness.uploadSignInfo(signModel);
                if (httpResponse.getResult().equals("fail")) {
                    return httpResponse;
                }
            }
        } else {
            httpResponse.setResult(HttpResponseStatus.FAIL.toString());
            httpResponse.setMsg("上传签署信息为空！");
            httpResponse.setData(new ArrayList<>());
        }
        return httpResponse;
    }


    /**
     * 上传检查表实例、故障设备、更换设备、图片，故障设备录音文件、更换设备录音文件
     *
     * @param id
     * @param images
     * @param request
     * @return
     */
    @RequestMapping("commonUpload")
    @ResponseBody
    public HttpResponse uploadImage(@RequestParam(value = "id") String id,
                                    @RequestParam(value = "type") String type,
                                    @RequestParam(value = "images") CommonsMultipartFile[] images,
                                    HttpServletRequest request) {
        HttpResponse httpResponse = testUploadBusiness.uploadCheckInstImage(id, type, images, request);
        return httpResponse;
    }

    /**
     * 上传水下记录单
     *
     * @param uploadDiveReq
     * @return
     */
    @RequestMapping("waterDownRecord")
    @ResponseBody
    public HttpResponse uploadWaterDownRecord(@RequestBody UploadDiveReq uploadDiveReq) {
        HttpResponse httpResponse = new HttpResponse();
        if (uploadDiveReq.getListsize() > 0) {
            for (DiveModel diveModel : uploadDiveReq.getDiveList()) {
                httpResponse = testUploadBusiness.uploadWaterDownRecord(diveModel);
                if (httpResponse.getResult().equals("fail")) {
                    return httpResponse;
                }
            }
        } else {
            httpResponse.setResult(HttpResponseStatus.FAIL.toString());
            httpResponse.setMsg("下潜任务信息为空！");
            httpResponse.setData(new ArrayList<>());
        }
        return httpResponse;
    }

    /**
     * 更新耗材
     *
     * @param updateConsumeReq
     * @return
     */
    @RequestMapping("updateConsumeData")
    @ResponseBody
    public HttpResponse updateConsumeData(@RequestBody UpdateConsumeReq updateConsumeReq) {
        HttpResponse httpResponse = new HttpResponse();
        if (updateConsumeReq.getConsumeListSize() > 0) {
            for (ConsumeBean consumeBean : updateConsumeReq.getConsumeBeanList()) {
                httpResponse = testUploadBusiness.updateConsumeData(consumeBean);
                if (httpResponse.getResult().equals("fail")) {
                    return httpResponse;
                }
            }
        } else {
            httpResponse.setResult(HttpResponseStatus.FAIL.toString());
            httpResponse.setMsg("耗材信息为空！");
            httpResponse.setData(new ArrayList<>());
        }
        return httpResponse;
    }

    /**
     * 上传设备实例检查事件
     *
     * @param deviceInstCheckEntityList
     * @return
     */
    @RequestMapping("uploadDeviceInstCheckEvent")
    @ResponseBody
    public HttpResponse uploadDeviceInstCheckEvent(@RequestBody List<DeviceInstCheckEntity> deviceInstCheckEntityList) {
        HttpResponse httpResponse = new HttpResponse();
        if (deviceInstCheckEntityList.size() > 0) {
            LinkedList<DeviceInstCheckEntity> giveClientDeviceInstCheckList=testUploadBusiness.uploadDeviceInstCheckEvent(deviceInstCheckEntityList);
            httpResponse.setResult(HttpResponseStatus.SUCCESS.toString());
            httpResponse.setData(giveClientDeviceInstCheckList);
        } else {
            //终端未传数据给服务端
            httpResponse.setResult(HttpResponseStatus.SUCCESS.toString());
            httpResponse.setMsg("设备实例检查信息为空！");
            httpResponse.setData(new ArrayList<>());
        }
        return httpResponse;
    }

    /**
     * 上传每日工作
     * @param dailyWorkEntityList
     * @return
     * @throws ParseException
     */
    @RequestMapping("uploadDailyWorkData")
    @ResponseBody
    public HttpResponse uploadDailyWorkData(@RequestBody List<DailyWorkEntity> dailyWorkEntityList) throws ParseException {
        HttpResponse httpResponse=new HttpResponse();
        if (dailyWorkEntityList != null&&dailyWorkEntityList.size() > 0) {
            LinkedList<DailyWorkEntity> dailyWorkEntityLinkedList=testUploadBusiness.uploadDailyWorkData(dailyWorkEntityList);
            httpResponse.setResult(HttpResponseStatus.SUCCESS.toString());
            httpResponse.setData(dailyWorkEntityLinkedList);
        }else{
            //终端未传数据给服务端
            httpResponse.setResult(HttpResponseStatus.SUCCESS.toString());
            httpResponse.setMsg("每日工作信息为空！");
            httpResponse.setData(new ArrayList<>());
        }
        return httpResponse;
    }
}
