package com.orient.collabdev.controller;

import com.orient.collabdev.business.processing.ShareFileBusiness;
import com.orient.log.annotion.Action;
import com.orient.modeldata.business.ModelDataBusiness;
import com.orient.sysmodel.domain.collabdev.datashare.CollabShareFile;
import com.orient.web.base.BaseController;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description 数据内容相关
 * @Author ZhangSheng
 * @Date 2018/8/20 09:39
 * @Version 1.0
 **/
@Controller
@RequestMapping("/shareFile")
public class ShareFileController extends BaseController {

    @Autowired
    ModelDataBusiness modelDataBusiness;

    @Autowired
    ShareFileBusiness shareFileBusiness;


    /**
     * 获取数据列表
     *
     * @param cbShareFolderId
     * @return
     */
    @Action(ownermodel = "协同设计-数据内容管理", detail = "查看数据内容项信息")
    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<CollabShareFile> list(String cbShareFolderId, Integer page, Integer limit) {
        return shareFileBusiness.list(cbShareFolderId, page, limit);
    }

//    /**
//     * 新增数据
//     *
//     * @param formValue
//     * @return
//     */
//    @Action(ownermodel = "协同设计-数据内容管理", detail = "新增数据内容项信息")
//    @RequestMapping("create")
//    @ResponseBody
//    public CommonResponseData create(CollabShareFile formValue) {
//        CommonResponseData retVal = new CommonResponseData();
//        shareFileBusiness.save(formValue);
//        retVal.setMsg("保存成功");
//        return retVal;
//    }

    /**
     * 更新数据
     *
     * @param formValue
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    @Action(ownermodel = "协同设计-数据内容管理", detail = "更新数据内容项信息")
    public CommonResponseData update(CollabShareFile formValue) {
        CommonResponseData retVal = new CommonResponseData();
        shareFileBusiness.update(formValue);
        retVal.setMsg("保存成功");
        return retVal;
    }


    /**
     * 删除数据
     *
     * @param toDelIds
     * @return
     */
    @Action(ownermodel = "协同设计-数据内容管理", detail = "删除数据内容项信息")
    @RequestMapping("delete")
    @ResponseBody
    public CommonResponseData delete(String[] toDelIds) {
        CommonResponseData retVal = new CommonResponseData();
        shareFileBusiness.delete(toDelIds);
        retVal.setMsg("删除成功");
        return retVal;
    }

    /**
     * 上传数据文件
     *
     * @param cbShareFolderId
     * @return
     */
    @Action(ownermodel = "协同设计-数据内容管理", detail = "上传数据文件")
    @RequestMapping("uploadShareFile")
    @ResponseBody
    public CommonResponseData uploadShareFile(String cbShareFolderId, HttpServletRequest request, HttpServletResponse response) {
        CommonResponseData retVal = new CommonResponseData();
        shareFileBusiness.uploadShareFile(cbShareFolderId, request, response);
        return retVal;
    }

    /**
     * 下载数据文件
     *
     * @param shareFileId
     * @return
     */
    @Action(ownermodel = "协同设计-数据内容管理", detail = "下载数据文件")
    @RequestMapping("downloadShareFile")
    @ResponseBody
    public void downloadShareFile(String shareFileId, HttpServletRequest request, HttpServletResponse response) {
        shareFileBusiness.downloadShareFile(shareFileId, request, response);
    }
}
