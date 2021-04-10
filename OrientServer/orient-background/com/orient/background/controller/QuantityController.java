package com.orient.background.controller;

import com.orient.background.bean.CfQuantityVO;
import com.orient.background.bean.UnitTreeVO;
import com.orient.background.business.QuantityBusiness;
import com.orient.sysmodel.domain.quantity.CfQuantityDO;
import com.orient.web.base.BaseController;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Controller
@RequestMapping("/Quantity")
public class QuantityController extends BaseController {
    @Autowired
    QuantityBusiness quantityBusiness;

    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<CfQuantityVO> list(Integer page, Integer limit, CfQuantityDO filter, String excludeIds) {
        return quantityBusiness.listSpecial(page, limit, filter, excludeIds);
    }

    @RequestMapping("listByTemplate")
    @ResponseBody
    public ExtGridData<CfQuantityVO> listByTemplate(Integer type, Long templateId) {
        return quantityBusiness.listByTemplate(type, templateId);
    }

    /**
     * 新增数据
     *
     * @param formValue
     * @return
     */
    @RequestMapping("create")
    @ResponseBody

    public CommonResponseData create(CfQuantityVO formValue) {
        CommonResponseData retVal = new CommonResponseData();
        quantityBusiness.saveSpecial(formValue);
        retVal.setMsg("保存成功!");
        return retVal;
    }

    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(CfQuantityVO formValue) {
        CommonResponseData retVal = new CommonResponseData();
        quantityBusiness.updateSpecial(formValue);
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
        quantityBusiness.deleteWithCascade(toDelIds);
        retVal.setMsg("删除成功");
        return retVal;
    }

    @RequestMapping("getAllUnits")
    @ResponseBody
    public List<UnitTreeVO> getAllUnits() {
        return quantityBusiness.getAllUnits();
    }

}
