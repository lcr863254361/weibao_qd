package com.orient.auditflow.controller;

import com.orient.auditflow.business.AuditFlowOpinionBusiness;
import com.orient.sysmodel.domain.flow.AuditFlowOpinionEntity;
import com.orient.web.base.BaseController;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Controller
@RequestMapping("/AuditFlowOpinion")
public class AuditFlowOpinionController  extends BaseController{
    @Autowired
    AuditFlowOpinionBusiness auditFlowOpinionBusiness;

    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<AuditFlowOpinionEntity> list(Integer page, Integer limit, AuditFlowOpinionEntity filter) {
        return auditFlowOpinionBusiness.list(page,limit,filter);
    }

    /**
     * 新增数据
     *
     * @param formValue
     * @return
     */
    @RequestMapping("create")
    @ResponseBody
    public CommonResponseData create(AuditFlowOpinionEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        auditFlowOpinionBusiness.save(formValue);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(AuditFlowOpinionEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        auditFlowOpinionBusiness.update(formValue);
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
        auditFlowOpinionBusiness.delete(toDelIds);
        retVal.setMsg("删除成功");
        return retVal;
    }
}
