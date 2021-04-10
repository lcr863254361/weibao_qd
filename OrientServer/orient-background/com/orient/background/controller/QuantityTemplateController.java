package com.orient.background.controller;

import com.orient.background.business.QuantityTemplateBusiness;
import com.orient.sysmodel.domain.quantity.CfQuantityTemplateDO;
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
@RequestMapping("/QuantityTemplate")
public class QuantityTemplateController  extends BaseController{
    @Autowired
    QuantityTemplateBusiness quantityTemplateBusiness;

    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<CfQuantityTemplateDO> list(Integer page, Integer limit, CfQuantityTemplateDO filter) {
        return quantityTemplateBusiness.list(page,limit,filter);
    }

    /**
     * 新增数据
     *
     * @param formValue
     * @return
     */
    @RequestMapping("create")
    @ResponseBody
    public CommonResponseData create(CfQuantityTemplateDO formValue) {
        CommonResponseData retVal = new CommonResponseData();
        quantityTemplateBusiness.save(formValue);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(CfQuantityTemplateDO formValue) {
        CommonResponseData retVal = new CommonResponseData();
        quantityTemplateBusiness.update(formValue);
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
        quantityTemplateBusiness.delete(toDelIds);
        retVal.setMsg("删除成功");
        return retVal;
    }
}
