package com.orient.background.controller;

import com.orient.background.business.QuantityTemplateRelationBusiness;
import com.orient.sysmodel.domain.quantity.CfQuantityTemplateRelationDO;
import com.orient.web.base.BaseController;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Controller
@RequestMapping("/QuantityTemplateRelation")
public class QuantityTemplateRelationController extends BaseController {
    @Autowired
    QuantityTemplateRelationBusiness quantityTemplateRelationBusiness;

    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<CfQuantityTemplateRelationDO> list(Integer page, Integer limit, CfQuantityTemplateRelationDO filter) {
        return quantityTemplateRelationBusiness.list(page, limit, filter);
    }

    /**
     * 新增数据
     *
     * @param formValue
     * @return
     */
    @RequestMapping("create")
    @ResponseBody
    public CommonResponseData create(CfQuantityTemplateRelationDO formValue) {
        CommonResponseData retVal = new CommonResponseData();
        quantityTemplateRelationBusiness.save(formValue);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    @RequestMapping("createRelation")
    @ResponseBody
    public CommonResponseData createRelation(Long templateId, Long[] quantityIds) {
        CommonResponseData retVal = new CommonResponseData();
        quantityTemplateRelationBusiness.createRelation(templateId, quantityIds);
        retVal.setMsg("绑定成功!");
        return retVal;
    }

    @RequestMapping("removeRelation")
    @ResponseBody
    public CommonResponseData removeRelation(Long templateId, Long[] quantityIds) {
        CommonResponseData retVal = new CommonResponseData();
        quantityTemplateRelationBusiness.removeRelation(templateId, quantityIds);
        retVal.setMsg("解除绑定成功!");
        return retVal;
    }



    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(CfQuantityTemplateRelationDO formValue) {
        CommonResponseData retVal = new CommonResponseData();
        quantityTemplateRelationBusiness.update(formValue);
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
        quantityTemplateRelationBusiness.delete(toDelIds);
        retVal.setMsg("删除成功");
        return retVal;
    }
}
