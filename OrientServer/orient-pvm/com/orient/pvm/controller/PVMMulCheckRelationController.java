package com.orient.pvm.controller;

import com.orient.pvm.bean.MulCheckModelTreeNode;
import com.orient.pvm.business.CheckModelBusiness;
import com.orient.pvm.business.PVMMulCheckRelationBusiness;
import com.orient.sysmodel.domain.pvm.CheckModelDataTemplate;
import com.orient.sysmodel.domain.pvm.CwmTaskmulcheckrelationEntity;
import com.orient.utils.JsonUtil;
import com.orient.utils.StringUtil;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseController;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Controller
@RequestMapping("/PVMMulCheckRelation")
public class PVMMulCheckRelationController extends BaseController {
    @Autowired
    PVMMulCheckRelationBusiness pVMMulCheckRelationBusiness;

    @Autowired
    CheckModelBusiness checkModelBusiness;

    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<CwmTaskmulcheckrelationEntity> list(Integer page, Integer limit, CwmTaskmulcheckrelationEntity filter) {
        return pVMMulCheckRelationBusiness.list(page, limit, filter);
    }

    /**
     * 新增数据
     *
     * @param formValue
     * @return
     */
    @RequestMapping("create")
    @ResponseBody
    public CommonResponseData create(CwmTaskmulcheckrelationEntity formValue, String templateId) {
        CommonResponseData retVal = new CommonResponseData();
        String name = pVMMulCheckRelationBusiness.getCheckModelName(formValue.getCheckmodelid());
        formValue.setName(name);
        formValue.setTemplateid(templateId);
        pVMMulCheckRelationBusiness.save(formValue);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(CwmTaskmulcheckrelationEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        pVMMulCheckRelationBusiness.update(formValue);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    /**
     * 删除表格
     *
     * @param toDelIds
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public CommonResponseData delete(Long[] toDelIds) {
        CommonResponseData retVal = new CommonResponseData();
        pVMMulCheckRelationBusiness.delete(toDelIds);
        retVal.setMsg("删除成功");
        return retVal;
    }

    @RequestMapping("canAdd")
    @ResponseBody
    public AjaxResponseData<String> checkCanAdd(String checkmodelid, String templateId) {
        String retVal = pVMMulCheckRelationBusiness.canAdd(checkmodelid, templateId);
        AjaxResponseData<String> mvcResp = new AjaxResponseData<>(retVal);
        mvcResp.setMsg(retVal.equals("true") ? "" : retVal);
        return mvcResp;
    }

    @RequestMapping("getByPid")
    @ResponseBody
    public AjaxResponseData<List<MulCheckModelTreeNode>> getByPid(String actionType, String templateId) {
        List<MulCheckModelTreeNode> checkModelTreeNodes = pVMMulCheckRelationBusiness.getBindCheckModelNodes(actionType, templateId);
        AjaxResponseData<List<MulCheckModelTreeNode>> retVal = new AjaxResponseData<>(checkModelTreeNodes);
        return retVal;
    }

    @RequestMapping("insertTemplateId")
    @ResponseBody
    public CommonResponseData setTemplateId(String templateId) {
        CommonResponseData retVal = new CommonResponseData();
        pVMMulCheckRelationBusiness.insertTemplateId(templateId);
        return retVal;
    }

    @RequestMapping("deleteEmptyTmpIdData")
    @ResponseBody
    public CommonResponseData deleteEmptyTmpIdData() {
        CommonResponseData retVal = new CommonResponseData();
        pVMMulCheckRelationBusiness.deleteEmptyTmpIdData();
        return retVal;
    }

    @RequestMapping("deleteRelationByTemplateId")
    @ResponseBody
    public CommonResponseData deleteRelationByTemplateId(String templateId) {
        CommonResponseData retVal = new CommonResponseData();
        pVMMulCheckRelationBusiness.deleteRelationByTemplateId(templateId);
        return retVal;
    }

    /**
     * @param orientModelId mulCheckRelation表中的checkmodelid字段
     * @param templateId    绑定模板Id
     * @return 表中modeldata字段的数据
     */
    @RequestMapping("getModelGridData")
    @ResponseBody
    public ExtGridData<Map> getModelGridData(String orientModelId, String templateId) {
        ExtGridData<Map> retVal = pVMMulCheckRelationBusiness.getModelGridData(orientModelId, templateId);
        return retVal;
    }

    @RequestMapping("createModelGridData")
    @ResponseBody
    public AjaxResponseData<String> saveModelData(String modelId, String formData, String templateId) {
        AjaxResponseData retVal = new AjaxResponseData();
        if (StringUtil.isEmpty(formData)) {
            retVal.setMsg("表单内容为空");
            retVal.setSuccess(false);
        } else {
            Map formDataMap = JsonUtil.json2Map(formData);
            Map dataMap = (Map) formDataMap.get("fields");
            pVMMulCheckRelationBusiness.saveModelData(modelId, templateId, dataMap);
            retVal.setMsg("保存成功");
        }
        return retVal;
    }

    @RequestMapping("deleteModelGridData")
    @ResponseBody
    public CommonResponseData deleteModelData(String modelId, Long[] toDelIds, String templateId) {
        CommonResponseData retVal = new CommonResponseData();
        pVMMulCheckRelationBusiness.deleteModelData(modelId, toDelIds, templateId);
        retVal.setMsg("删除成功");
        return retVal;
    }

    @RequestMapping("updateModelGridData")
    @ResponseBody
    public CommonResponseData updateModelData(String modelId, String dataId, String templateId, String formData) {
        CommonResponseData retVal = new CommonResponseData();
        if (StringUtil.isEmpty(formData)) {
            retVal.setMsg("表单内容为空");
            retVal.setSuccess(false);
        } else {
            Map formDataMap = JsonUtil.json2Map(formData);
            Map dataMap = (Map) formDataMap.get("fields");
            pVMMulCheckRelationBusiness.updateModelData(modelId, dataId, templateId, dataMap);
            retVal.setMsg("修改成功");
        }
        return retVal;
    }

    /**
     * 更新数据检查表中的html
     *
     * @param formValue
     * @return
     */
    @RequestMapping("updateDataHtml")
    @ResponseBody
    public CommonResponseData updateDataHtml(CwmTaskmulcheckrelationEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        pVMMulCheckRelationBusiness.updateDataHtml(formValue);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    /**
     * @param templateIds 检查表模板id
     * @param templateId  综合模板id
     * @return
     */
    @RequestMapping("canAddFromTemplate")
    @ResponseBody
    public AjaxResponseData<Boolean> canAddFromTemplate(Long[] templateIds, String templateId) {
        String errInfo = pVMMulCheckRelationBusiness.canAddFromTemplate(templateIds, templateId);
        Boolean canAdd = StringUtil.isEmpty(errInfo);
        AjaxResponseData<Boolean> mvcResp = new AjaxResponseData<>(canAdd);
        mvcResp.setMsg(StringUtil.isEmpty(errInfo) ? "" : errInfo);
        mvcResp.setSuccess(canAdd);
        return mvcResp;
    }

    @RequestMapping("canAddFromHtmlTemplate")
    @ResponseBody
    public AjaxResponseData<Boolean> canAddFromHtmlTemplate(Long[] htmlTemplateIds, String modelId, String templateId) {
        String errInfo = pVMMulCheckRelationBusiness.canAddFromHtmlTemplate(htmlTemplateIds, modelId, templateId);
        Boolean canAdd = StringUtil.isEmpty(errInfo);
        AjaxResponseData<Boolean> mvcResp = new AjaxResponseData<>(canAdd);
        mvcResp.setMsg(StringUtil.isEmpty(errInfo) ? "" : errInfo);
        mvcResp.setSuccess(canAdd);
        return mvcResp;
    }

    /**
     * @param templateIds 检查表模板id
     * @return
     */
    @RequestMapping("createByTemplate")
    @ResponseBody
    public CommonResponseData createByTemplate(String templateId, Long[] templateIds) {
        CommonResponseData retVal = new CommonResponseData();
        //获取模型ID集合
        List<CheckModelDataTemplate> checkModelDataTemplates = checkModelBusiness.getByIds(templateIds);
        pVMMulCheckRelationBusiness.createByTemplate(templateId, checkModelDataTemplates);
        retVal.setMsg("保存成功");
        return retVal;
    }

    @RequestMapping("createByHtmlTemplate")
    @ResponseBody
    public CommonResponseData createByHtmlTemplate(String modelId, String templateId, Long[] htmlTemplateIds) {
        CommonResponseData retVal = new CommonResponseData();
        pVMMulCheckRelationBusiness.createByHtmlTemplate(modelId, templateId, htmlTemplateIds);
        retVal.setMsg("保存成功");

        return retVal;
    }

    @RequestMapping("saveRemark")
    @ResponseBody
    public CommonResponseData saveRemark(String id, String remark) {
        CommonResponseData retVal = new CommonResponseData();
        pVMMulCheckRelationBusiness.saveRemark(id, remark);
        retVal.setMsg("设置成功");
        return retVal;
    }

    @RequestMapping("saveAssignRoles")
    @ResponseBody
    public CommonResponseData saveAssignRoles(String dataId, String signroles) {
        CommonResponseData retVal = new CommonResponseData();
        pVMMulCheckRelationBusiness.saveAssignRoles(dataId, signroles);
        retVal.setMsg("设置成功");
        return retVal;
    }

    @RequestMapping("preapareExportData")
    @ResponseBody
    public AjaxResponseData<String> createExportData(String modelId, String templateId, Long[] toExportDataIds) {
        AjaxResponseData<String> retVal = new AjaxResponseData();
        String fileName = pVMMulCheckRelationBusiness.preapareExportData(modelId, templateId, toExportDataIds);
        retVal.setResults(fileName);
        return retVal;
    }
}
