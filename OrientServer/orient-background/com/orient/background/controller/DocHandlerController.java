package com.orient.background.controller;

import com.orient.background.business.DocHandlerBusiness;
import com.orient.background.doctemplate.handler.IDocHandler;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.sysmodel.domain.doc.CwmDocHandlerEntity;
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
@RequestMapping("/DocHandler")
public class DocHandlerController extends BaseController {
    @Autowired
    DocHandlerBusiness docHandlerBusiness;

    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<CwmDocHandlerEntity> list(Integer page, Integer limit, CwmDocHandlerEntity filter) {
        return docHandlerBusiness.list(page, limit, filter);
    }

    /**
     * 新增数据
     *
     * @param formValue
     * @return
     */
    @RequestMapping("create")
    @ResponseBody
    public CommonResponseData create(CwmDocHandlerEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        docHandlerBusiness.save(formValue);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(CwmDocHandlerEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        docHandlerBusiness.update(formValue);
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
        docHandlerBusiness.deleteSpecial(toDelIds);
        retVal.setMsg("删除成功");
        return retVal;
    }

    @RequestMapping("getDocHandlerBeanList")
    @ResponseBody
    public ExtComboboxResponseData<ExtComboboxData> getDocHandlerBeanList() throws NoSuchFieldException, IllegalAccessException {
        ExtComboboxResponseData<ExtComboboxData> responseData = new ExtComboboxResponseData<>();
        String[] beanNames = OrientContextLoaderListener.Appwac.getBeanNamesForType(IDocHandler.class);
        for (String beanName : beanNames) {
            ExtComboboxData extComboboxData = new ExtComboboxData(beanName, beanName);
            responseData.getResults().add(extComboboxData);
        }
        responseData.setTotalProperty(responseData.getResults().size());
        return responseData;
    }

    @RequestMapping("getDocHandlerCombobox")
    @ResponseBody
    public ExtComboboxResponseData<ExtComboboxData> getDocHandlerCombobox(Integer startIndex, Integer maxResults, String filter, String id) {
        ExtComboboxResponseData<ExtComboboxData> retValue = docHandlerBusiness.getDocHandlerCombobox(startIndex, maxResults, filter, id);
        return retValue;
    }

}
