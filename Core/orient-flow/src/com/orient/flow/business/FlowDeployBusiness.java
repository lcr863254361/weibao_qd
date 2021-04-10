package com.orient.flow.business;

import com.orient.flow.util.FlowTypeHelper;
import com.orient.sysmodel.operationinterface.IRoleModel;
import com.orient.utils.CommonTools;
import com.orient.utils.FileOperator;
import com.orient.web.base.BaseBusiness;
import com.orient.web.base.CommonResponseData;
import com.orient.workflow.WorkFlowConstants;
import com.orient.workflow.service.impl.DeployServiceImpl;
import org.jbpm.api.ProcessEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * the flow deploy business
 *
 * @author Seraph
 *         2016-08-02 下午4:19
 */
@Component
public class FlowDeployBusiness extends BaseBusiness {

    public CommonResponseData deploy(String fileName, String userName, String dataId, String rootType, MultipartFile file) {
        CommonResponseData retV = new CommonResponseData(false, "");

        String tempFolder = CommonTools.getRootPath() + File.separator + "JBPMTemp" + File.separator;
        if (!new File(tempFolder).exists()) {
            new File(tempFolder).mkdirs();
        }
        String downloadFilePath = tempFolder + fileName + ".zip";
        try {
            FileOperator.createFile(downloadFilePath, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            retV.setMsg("下载文件失败");
            return retV;
        }

        String pdId = doDeploy(userName, rootType, dataId, downloadFilePath, null, null);
        retV.setSuccess(true);
        retV.setMsg(pdId);
        return retV;
    }

    public String deployJpdl(String userName, String rootType, String dataId, String resourceName, InputStream jdplInputStream){

        String pdId = doDeploy(userName, rootType, dataId, null, resourceName, jdplInputStream);
        return pdId;
    }

    private String doDeploy(String userName, String rootType, String dataId, String filePath, String resourceName, InputStream inputStream){
        IRoleModel roleModel = roleEngine.getRoleModel(false);
        String userShowName = roleModel.getUserByUserName(userName).getAllName();

        Map<String, String> variables = new HashMap<>();
        if ("".equals(rootType)) {
            variables.put(WorkFlowConstants.AUDIT_FLOW, WorkFlowConstants.AUDIT_FLOW);
        } else {
            variables.put(rootType, dataId);
        }

        String pdId;
        if(!CommonTools.isNullString(filePath)){
            pdId = deployService.deploy(filePath, userShowName, variables, false);
        }else{
            pdId = deployService.deploy(resourceName, inputStream, userShowName, variables, false);
        }
        //更新审批流程定义的内存
        FlowTypeHelper.refresh();

        return pdId;
    }

    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private DeployServiceImpl deployService;
}
