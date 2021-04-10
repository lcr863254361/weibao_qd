package com.orient.devdataobj.controller;

import com.orient.devdataobj.bean.HisDataObjBean;
import com.orient.devdataobj.business.DataObjectBusiness;
import com.orient.devdataobj.business.HisDataObjectBusiness;
import com.orient.log.annotion.Action;
import com.orient.sysmodel.domain.taskdata.DataObjectOldVersionEntity;
import com.orient.sysmodel.roleengine.IRoleUtil;
import com.orient.utils.StringUtil;
import com.orient.web.base.BaseController;
import com.orient.web.base.ExtGridData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mengbin
 * @create 2016-07-22 下午3:22
 */
@Controller
@RequestMapping("/HisDataObj")
public class HisDataObjController extends BaseController {

    @RequestMapping("getHisDataObj")
    @ResponseBody
    @Action(ownermodel = "研发数据-数据对象", detail = "查看数据对象历史")
    public ExtGridData<HisDataObjBean> getHisDataObjControl(String dataObjId, String node) {
        ExtGridData<HisDataObjBean> ret = new ExtGridData<>();
        if (StringUtil.isEmpty(node) || StringUtil.isEmpty(dataObjId)) {
            ret.setSuccess(true);
            ret.setTotalProperty(0);
            ret.setResults(null);
            return ret;
        }
        try {
            List<DataObjectOldVersionEntity> objs = null;
            if (node.equals("root")) {
                objs = hisDataObjectBusiness.getRootHisDataObj(new Integer(dataObjId));
            } else {
                DataObjectOldVersionEntity topHisDataObj = hisDataObjectBusiness.getHisDataObj(new Integer(node));
                objs = hisDataObjectBusiness.getHisDataObjFamily(topHisDataObj);
            }

            List<HisDataObjBean> resluts = new ArrayList<>();
            for (DataObjectOldVersionEntity loop : objs) {
                HisDataObjBean bean = new HisDataObjBean();
                dataObjectBusiness.changeDataObjectEntityToDataObject(loop, bean);
                resluts.add(bean);
            }
            ret.setSuccess(true);
            ret.setTotalProperty(resluts.size());
            ret.setResults(resluts);
        } catch (Exception e) {
            e.printStackTrace();
            ret.setSuccess(false);
            return ret;
        }
        return ret;
    }

    @RequestMapping("getSimpleHisDevDatas")
    @ResponseBody
    @Action(ownermodel = "研发数据-数据对象", detail = "查看数据对象历史")
    public ExtGridData<HisDataObjBean> getSimpleHisDevDatas(String nodeId, String originalObjId, Integer isglobal) {
        ExtGridData<HisDataObjBean> retVal = new ExtGridData<>();
        if (StringUtil.isEmpty(nodeId) || StringUtil.isEmpty(originalObjId)) {
            retVal.setSuccess(true);
            retVal.setTotalProperty(0);
            retVal.setResults(null);
            return retVal;
        }
        return hisDataObjectBusiness.getSimpleHisDevDatas(nodeId, originalObjId, isglobal);
    }

    @RequestMapping("getComplexHisDevDatas")
    @ResponseBody
    @Action(ownermodel = "研发数据-数据对象", detail = "查看数据对象历史")
    public ExtGridData<HisDataObjBean> getComplexHisDevDatas(String nodeId, String originalObjId, String rootId, Integer isglobal) {
        ExtGridData<HisDataObjBean> retVal = new ExtGridData<>();
        if (StringUtil.isEmpty(nodeId) || StringUtil.isEmpty(originalObjId)) {
            retVal.setSuccess(true);
            retVal.setTotalProperty(0);
            retVal.setResults(null);
            return retVal;
        }
        return hisDataObjectBusiness.getComplexHisDevDatas(nodeId, originalObjId, rootId, isglobal);
    }

    @Autowired
    DataObjectBusiness dataObjectBusiness;

    @Autowired
    HisDataObjectBusiness hisDataObjectBusiness;

    @Autowired
    IRoleUtil roleEngine;

}
