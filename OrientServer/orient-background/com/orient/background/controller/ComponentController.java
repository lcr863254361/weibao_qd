package com.orient.background.controller;

import com.orient.background.business.ComponentBusiness;
import com.orient.component.ComponentInterface;
import com.orient.component.bean.ValidateComponentBean;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.sysmodel.domain.component.CwmComponentEntity;
import com.orient.utils.StringUtil;
import com.orient.web.base.*;
import com.orient.utils.exception.OrientBaseAjaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

/**
 * 组件管理
 *
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Controller
@RequestMapping("/Component")
public class ComponentController extends BaseController {
    @Autowired
    ComponentBusiness componentBusiness;

    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<CwmComponentEntity> list(Integer page, Integer limit, CwmComponentEntity filter) {
        return componentBusiness.list(page, limit, filter);
    }

    @RequestMapping("listByIds")
    @ResponseBody
    public ExtGridData<CwmComponentEntity> listByIds(@RequestBody ArrayList<Long> ids) {
        return componentBusiness.listByIds(ids);
    }


    /**
     * 新增数据
     *
     * @param formValue
     * @return
     */
    @RequestMapping("create")
    @ResponseBody
    public CommonResponseData create(CwmComponentEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        componentBusiness.save(formValue);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(CwmComponentEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        componentBusiness.update(formValue);
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
        componentBusiness.delete(toDelIds);
        retVal.setMsg("删除成功");
        return retVal;
    }

    @RequestMapping("getComponentBeanList")
    @ResponseBody
    public ExtComboboxResponseData<ExtComboboxData> getComponentBeanList() throws NoSuchFieldException, IllegalAccessException {
        ExtComboboxResponseData<ExtComboboxData> responseData = new ExtComboboxResponseData<>();
        String[] beanNames = OrientContextLoaderListener.Appwac.getBeanNamesForType(ComponentInterface.class);
        for (String beanName : beanNames) {
            ExtComboboxData extComboboxData = new ExtComboboxData();
            extComboboxData.setId(beanName);
            extComboboxData.setValue(beanName);
            responseData.getResults().add(extComboboxData);
        }
        responseData.setTotalProperty(responseData.getResults().size());
        return responseData;
    }

    /**
     * @param componentId
     * @return 获取组件绑定的前端页面类
     */
    @RequestMapping("getComponentJSClass")
    @ResponseBody
    public AjaxResponseData<String> getComponentJSClass(Long componentId) {
        AjaxResponseData retVal = new AjaxResponseData();
        String jsClass = null;
        try {
            jsClass = componentBusiness.getComponentJSClass(componentId);
        } catch (Exception e) {
            throw new OrientBaseAjaxException("", e.toString());
        }
        retVal.setResults(jsClass);
        return retVal;
    }


    @RequestMapping("validateComponent")
    @ResponseBody
    public AjaxResponseData<Boolean> validateComponent(@RequestBody ValidateComponentBean validateComponentBean) {
        AjaxResponseData<Boolean> retVal = new AjaxResponseData();
        String validateResult = componentBusiness.validateComponent(validateComponentBean);
        if (!StringUtil.isEmpty(validateResult)) {
            throw new OrientBaseAjaxException("", validateResult);
        }
        retVal.setResults(StringUtil.isEmpty(validateResult));
        return retVal;
    }

}
