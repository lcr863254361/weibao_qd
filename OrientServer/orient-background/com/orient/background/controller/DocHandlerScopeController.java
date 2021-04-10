package com.orient.background.controller;

import com.orient.background.business.DocHandlerScopeBusiness;
import com.orient.businessmodel.Util.EnumInter;
import com.orient.sysmodel.domain.doc.CwmDocColumnScopeEntity;
import com.orient.web.base.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Controller
@RequestMapping("/DocHandlerScope")
public class DocHandlerScopeController extends BaseController {
    @Autowired
    DocHandlerScopeBusiness docHandlerScopeBusiness;

    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<CwmDocColumnScopeEntity> list(Integer page, Integer limit, CwmDocColumnScopeEntity filter, Long belongHandler) {
        return docHandlerScopeBusiness.listSpecial(page, limit, filter, belongHandler);
    }

    /**
     * 新增数据
     *
     * @param formValue
     * @return
     */
    @RequestMapping("create")
    @ResponseBody
    public CommonResponseData create(CwmDocColumnScopeEntity formValue, Long belongHandler) {
        CommonResponseData retVal = new CommonResponseData();
        docHandlerScopeBusiness.saveSpecial(formValue, belongHandler);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(CwmDocColumnScopeEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        docHandlerScopeBusiness.update(formValue);
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
        docHandlerScopeBusiness.delete(toDelIds);
        retVal.setMsg("删除成功");
        return retVal;
    }

    @RequestMapping("getColumnTypeList")
    @ResponseBody
    public ExtComboboxResponseData<ExtComboboxData> getColumnTypeList() throws NoSuchFieldException, IllegalAccessException {
        ExtComboboxResponseData<ExtComboboxData> responseData = new ExtComboboxResponseData<>();
        for (EnumInter.BusinessModelEnum.BusinessColumnEnum businessColumnEnum : EnumInter.BusinessModelEnum.BusinessColumnEnum.values()) {
            ExtComboboxData extComboboxData = new ExtComboboxData(businessColumnEnum.name(), businessColumnEnum.name());
            responseData.getResults().add(extComboboxData);
        }
        responseData.setTotalProperty(responseData.getResults().size());
        return responseData;
    }
}
