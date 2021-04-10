package com.orient.flow.controller;

import com.edm.nio.util.CommonTools;
import com.orient.auditflow.business.AuditFlowCommissionBusiness;
import com.orient.flow.business.FlowDeployBusiness;
import com.orient.sysman.bean.UserBean;
import com.orient.sysmodel.operationinterface.IUser;
import com.orient.utils.JsonUtil;
import com.orient.web.base.BaseController;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import com.orient.web.util.UserContextUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/flowCommission")
public class FlowCommissionController extends BaseController{
    @Autowired
    private AuditFlowCommissionBusiness flowCommissionBusiness;

    @RequestMapping("/getSlaveUsers")
    @ResponseBody
    public ExtGridData<UserBean> getSlaveUsers(String pdid){
        ExtGridData<UserBean> retVal = new ExtGridData<>();
        String mainUserName = UserContextUtil.getUserName();
        List<IUser> users = flowCommissionBusiness.getSlaveUsers(pdid, mainUserName);
        List<UserBean> userBeans = new ArrayList<>();
        for(IUser user : users) {
            UserBean userBean = new UserBean();
            try {
                PropertyUtils.copyProperties(userBean, user);
                Map<String, String> showMap = new HashMap<String, String>() {{
                    put("id", user.getDept().getId());
                    put("text", user.getDept().getName());
                }};
                userBean.setDepartment(JsonUtil.toJson(showMap));
                userBeans.add(userBean);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        retVal.setResults(userBeans);
        retVal.setTotalProperty(userBeans.size());
        return retVal;
    }

    @RequestMapping("/addSlaveUsers")
    @ResponseBody
    public CommonResponseData addSlaveUsers(String pdid, String userNames){
        CommonResponseData retVal = new CommonResponseData();
        String mainUserName = UserContextUtil.getUserName();
        if(userNames!=null && !"".equals(userNames)) {
            String[] userNamesArr = userNames.split(",");
            List<String> addSlaveUsers = flowCommissionBusiness.addSlaveUsers(pdid, mainUserName, userNamesArr);
            if(addSlaveUsers.size() > 0) {
                retVal.setSuccess(true);
                retVal.setMsg("添加代办人【"+ CommonTools.list2String(addSlaveUsers)+"】成功");
            }
            else {
                retVal.setSuccess(false);
                retVal.setMsg("添加代办人失败");
            }
        }
        else {
            retVal.setSuccess(false);
            retVal.setMsg("代办人为空");
        }

        return retVal;
    }

    @RequestMapping("/deleteSlaveUsers")
    @ResponseBody
    public CommonResponseData deleteSlaveUsers(String pdid, String userNames){
        CommonResponseData retVal = new CommonResponseData();
        String mainUserName = UserContextUtil.getUserName();
        String[] userNamesArr = userNames.split(",");
        List<String> deleteSlaveUsers = flowCommissionBusiness.deleteSlaveUsers(pdid, mainUserName, userNamesArr);
        if(deleteSlaveUsers.size() > 0) {
            retVal.setSuccess(true);
            retVal.setMsg("删除代办人【"+ CommonTools.list2String(deleteSlaveUsers)+"】成功");
        }
        else {
            retVal.setSuccess(false);
            retVal.setMsg("删除代办人失败");
        }

        return retVal;
    }
}
