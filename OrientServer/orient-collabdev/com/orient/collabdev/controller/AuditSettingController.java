package com.orient.collabdev.controller;

import com.orient.background.business.ModelFormViewBusiness;
import com.orient.collabdev.business.designing.AuditSettingBusiness;
import com.orient.collabdev.model.CollabApprovalSettingDetailVO;
import com.orient.collabdev.model.CollabApprovalSettingVO;
import com.orient.flow.util.FlowTypeHelper;
import com.orient.sysmodel.domain.collabdev.approval.CollabSettingsApproval;
import com.orient.sysmodel.domain.collabdev.approval.CollabSettingsApprovalDetail;
import com.orient.sysmodel.domain.form.ModelFormViewEntity;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseController;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-08-07 10:38 AM
 */
@Controller
@RequestMapping("/auditSettings")
public class AuditSettingController extends BaseController {

    /**
     * 根据节点获取审批绑定信息
     *
     * @param nodeId
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<CollabApprovalSettingVO> getApprovalSettings(String nodeId) {
        List<CollabSettingsApproval> collabSettingsApprovals = auditSettingBusiness.getCollabSettingsApprovalByNode(nodeId);
        List<CollabApprovalSettingVO> vos = new ArrayList<>();
        collabSettingsApprovals.forEach(collabSettingsApproval -> vos.add(CollabApprovalSettingVO.buildFromDTO(collabSettingsApproval)));
        ExtGridData<CollabApprovalSettingVO> responseData = new ExtGridData<>();
        responseData.setResults(vos);
        return responseData;
    }

    /**
     * 创建审批绑定信息
     *
     * @param datas
     * @return
     */
    @RequestMapping("createMulti")
    @ResponseBody
    public CommonResponseData createMulti(@RequestBody List<CollabApprovalSettingVO> datas) {
        CommonResponseData retVal = new CommonResponseData();
        List<CollabSettingsApproval> collabSettingsApprovals = new ArrayList<>();
        datas.forEach(vo -> collabSettingsApprovals.add(vo.converToDTO()));
        auditSettingBusiness.createMulti(collabSettingsApprovals);
        return retVal;
    }

    /**
     * 删除审批绑定信息
     *
     * @param toDelIds
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public CommonResponseData deleteBind(String[] toDelIds) {
        CommonResponseData retVal = new CommonResponseData();
        auditSettingBusiness.deleteBind(toDelIds);
        retVal.setMsg("删除成功");
        return retVal;
    }

    /**
     * 更新审批绑定信息
     *
     * @param collabSettingsApproval
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(@RequestBody CollabSettingsApproval collabSettingsApproval) {
        CommonResponseData retVal = new CommonResponseData();
        auditSettingBusiness.update(collabSettingsApproval);
        retVal.setMsg("修改成功");
        return retVal;
    }

    /**
     * 获取所有未绑定的流程定义信息
     *
     * @param nodeId
     * @return
     */
    @RequestMapping("listAuditPds")
    @ResponseBody
    public AjaxResponseData<List<Map<String, String>>> listAuditPds(String nodeId) {
        List<String> pdIds = FlowTypeHelper.getMainAuditFlowPdIds();
        List<Map<String, String>> unSelectedAuditFlowInfos = auditSettingBusiness.listAuditPds(nodeId, pdIds);
        return new AjaxResponseData(unSelectedAuditFlowInfos);
    }

    @RequestMapping("listAuditDetail")
    @ResponseBody
    public ExtGridData<CollabApprovalSettingDetailVO> initAuditDetail(String belongAuditBind) {
        List<CollabSettingsApprovalDetail> details = auditSettingBusiness.listAuditDetail(belongAuditBind);
        List<Long> formIds = details.stream().filter(collabSettingsApprovalDetail -> null != collabSettingsApprovalDetail.getFormId())
                .map(CollabSettingsApprovalDetail::getFormId).collect(Collectors.toList());
        List<ModelFormViewEntity> modelFormViewEntities = modelFormViewBusiness.getByIds(formIds);
        List<CollabApprovalSettingDetailVO> results = new ArrayList<>();
        //get form mapping data
        details.forEach(detail -> {
            CollabApprovalSettingDetailVO collabApprovalSettingDetailVO = CollabApprovalSettingDetailVO.buildFromDTO(detail, modelFormViewEntities);
            results.add(collabApprovalSettingDetailVO);
        });
        //set to vo
        ExtGridData<CollabApprovalSettingDetailVO> responeData = new ExtGridData<>();
        responeData.setResults(results);
        return responeData;
    }

    @RequestMapping("updateAuditDetail")
    @ResponseBody
    public CommonResponseData updateAuditDetail(@RequestBody CollabSettingsApprovalDetail detail) {
        CommonResponseData retVal = new CommonResponseData();
        auditSettingBusiness.updateAuditDetail(detail);
        retVal.setMsg("修改成功");
        return retVal;
    }


    @Autowired
    AuditSettingBusiness auditSettingBusiness;

    @Autowired
    ModelFormViewBusiness modelFormViewBusiness;
}
