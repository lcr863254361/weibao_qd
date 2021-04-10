package com.orient.pvm.sync;

import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.collab.model.Task;
import com.orient.login.business.LoginBusiness;
import com.orient.pvm.bean.sync.PVMTables;
import com.orient.pvm.bean.sync.PVMUsers;
import com.orient.pvm.business.PVMSyncBusiness;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.domain.user.User;
import com.orient.sysmodel.operationinterface.IRoleModel;
import com.orient.sysmodel.roleengine.IRoleUtil;
import com.orient.utils.XmlCastToModel;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;

/**
 * Created by mengbin on 16/8/8.
 * Purpose:
 * Detail:
 */



//@Path("/PVMSyncService")
@Component("PVMSyncService")
public class PVMSyncService implements IPVMSyncService  {

    @Autowired
    LoginBusiness loginService;

    @Autowired
    protected PVMSyncBusiness pvmSyncBusiness;

    @Autowired
    protected IRoleUtil roleEngine;

    @Autowired
    protected ISqlEngine orientSqlEngine;

    @Autowired
    protected IBusinessModelService businessModelService;




    //@Path("/login")
    public boolean login(String userName, String password,String PADCode) {
        Subject subject = SecurityUtils.getSubject();
        try {
            if (subject.isAuthenticated()) {
                userName = (String) subject.getPrincipal();
            } else {
                UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
                token.setHost("pad");
                token.setRememberMe(true);
                subject.login(token);
                //保存会话
                IRoleModel sysmodel = roleEngine.getRoleModel(false);
                User currentUser = (User) sysmodel.getUserByUserName(userName);
                subject.getSession().setAttribute("currentUser", currentUser);
                //保存當前用戶IP地址
                subject.getSession().setAttribute("userIp", "pvmPad");
            }
        } catch (AuthenticationException e) {
            return false;

        }
       return true;
    }

    //@Path("/getUsers")

    public String getUsers(){

        PVMUsers pvmUsers =pvmSyncBusiness.getAllUsers();
        String mappingFilePath =getSyncMappingFile();
        XmlCastToModel<PVMUsers> castUntil = new XmlCastToModel<PVMUsers>();
        String ret = castUntil.beanToXml(pvmUsers,mappingFilePath);
        return ret;
    }

    //@Path("/getCheckTables")

    public String getCheckTables(String userId){
        String taskModelId = orientSqlEngine.getTypeMappingBmService().getModelId(Task.class);
        List<String> checkTableIds = pvmSyncBusiness.getCheckTables(userId,taskModelId);
        String ret = "";
        for (String id: checkTableIds){
            ret=ret+","+id;
        }
        if (ret.length()==0){
            return "";
        }
        else {
            return ret.substring(1);
        }

    }

   // @Path("getCheckTablesById")

    public String getCheckTableInfo(String checkTableId){
        String taskModelId = orientSqlEngine.getTypeMappingBmService().getModelId(Task.class);
        PVMTables pvmTables =  pvmSyncBusiness.getCheckModelInfo(checkTableId,taskModelId);
        String mappingFilePath =getSyncMappingFile();
        XmlCastToModel<PVMTables> castUntil = new XmlCastToModel<PVMTables>();
        String ret = castUntil.beanToXml(pvmTables,mappingFilePath);
        return ret;
    }

    //@Path("upLoadCheckTable")

    public String uploadCheckTable(String  xmlContent){
        XmlCastToModel<PVMTables> castUntil = new XmlCastToModel<PVMTables>();
        String mappingFilePath =getSyncMappingFile();
        PVMTables pvmTables = castUntil.castfromXML(PVMTables.class,xmlContent,mappingFilePath);
        StringBuffer errofInfo =  new StringBuffer();
        boolean bSuc = pvmSyncBusiness.setCheckModelInfo(pvmTables,errofInfo);
        if (bSuc){
            return "true";
        }
        else {
            return errofInfo.toString();
        }

    }


    //@Path("getHTMLContentById")
    public  String getHTMLContentById(String checkModelId){

        return pvmSyncBusiness.getTaskCheckHtmlBy(checkModelId);
    }


    //@Path("uploadHTMLContentById")
    public String uploadHTMLContentById(String checkModelId, String htmlContent){
        boolean bSuc = pvmSyncBusiness.setTaskCheckHtmlBy(checkModelId,htmlContent);
        if (bSuc){
            return "true";
        }
        else {
            return "检查内容上传失败!";
        }
    }

    private  String getSyncMappingFile(){
        URL URL = PVMSyncService.class.getResource("PVMMapping.xml");
        return URL.getPath();
    }

}
