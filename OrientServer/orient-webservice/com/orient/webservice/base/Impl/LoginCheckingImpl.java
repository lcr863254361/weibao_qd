package com.orient.webservice.base.Impl;

import com.orient.web.base.dsbean.CommonResponse;
import com.orient.dsrestful.enums.ClientTypeEnum;
import com.orient.dsrestful.enums.LockResponseEnum;
import com.orient.dsrestful.enums.LoginCheckResponseTypeEnum;
import com.orient.dsrestful.enums.SchemaLockStatusEnum;
import com.orient.metamodel.metadomain.Schema;
import com.orient.metamodel.operationinterface.IMetaModel;
import com.orient.metamodel.operationinterface.ISchema;
import com.orient.sysmodel.domain.user.User;
import com.orient.sysmodel.domain.user.UserLoginHistory;
import com.orient.utils.CommonTools;
import com.orient.utils.Commons;
import com.orient.web.base.dsbean.CommonDataBean;
import com.orient.dsrestful.domain.schemaBaseInfo.FrontSchema;
import com.orient.dsrestful.domain.schemaBaseInfo.SchemaResponse;
import com.orient.dsrestful.domain.lock.LockResponse;
import com.orient.dsrestful.domain.lock.LockStatus;
import org.codehaus.xfire.transport.http.XFireServletController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginCheckingImpl extends BaseBean {

    /**
     * 检查licence信息
     *
     * @param request request对象
     * @return string licence是否存在
     */
    private String checkLicence(HttpServletRequest request) {
        String message = "success";
        Map<String, String> localInfo = new HashMap<>();
        //读取本地信息;
        localInfo.put("userIP", request.getRemoteAddr());
        //判断licence信息是否为空 如果为空则读取license信息
//		Map licenceInfo = null;
//		if(licenceInfo==null){
//			//根据RCP类型校验系统licence信息 如果不通过则返回-2
//			String licence = request.getSession().getServletContext().getRealPath("/") + "WEB-INF\\license.license";
//			if(ParseLicence.validateSignInfo(request.getSession().getServletContext().getRealPath("/")+"WEB-INF\\")){
//			    //读取licence信息;
//				try{
//				    licenceInfo = readLicence.read(licence);
//				} catch (Exception e){
//					e.printStackTrace();
//					return "-2";
//				}
//			}			
//		}
//		String message = validateLicence.validateRCP(licenceInfo, localInfo);	
        return message;
    }

    /**
     * 保存用户登录ds记录，记录到数据库
     *
     * @param user    登录用户
     * @param type    登录类别
     * @param request request对象
     */
    private void saveUserLoginRecord(User user, String type, HttpServletRequest request) {
        UserLoginHistory userLoginHistory = new UserLoginHistory();
        userLoginHistory.setUserIp(request.getRemoteAddr());
        userLoginHistory.setUserName(user.getUserName());
        userLoginHistory.setUserDisplayName(user.getAllName());
        userLoginHistory.setUserDeptName(CommonTools.Obj2String(user.getDept().getName()));
        userLoginHistory.setUserDeptid(CommonTools.Obj2String(user.getDept().getId()));
        userLoginHistory.setLoginTime(Commons.getSysDate());
        switch (ClientTypeEnum.fromString(type)) {
            case TYPE_DESIGN_STUDIO:
                userLoginHistory.setOpType(ClientTypeEnum.TYPE_DESIGN_STUDIO.toString());
                userLoginHistory.setOpMessage((ClientTypeEnum.TYPE_DESIGN_STUDIO.toString() + "登录"));
                break;
            case TYPE_TBOM_STUDIO:
                userLoginHistory.setOpType(ClientTypeEnum.TYPE_TBOM_STUDIO.toString());
                userLoginHistory.setOpMessage((ClientTypeEnum.TYPE_TBOM_STUDIO.toString() + "登录"));
                break;
            case TYPE_ETL_STUDIO:
                userLoginHistory.setOpType(ClientTypeEnum.TYPE_ETL_STUDIO.toString());
                userLoginHistory.setOpMessage((ClientTypeEnum.TYPE_ETL_STUDIO.toString() + "登录"));
                break;
            case TYPE_WORK_FLOW_STUDIO:
                userLoginHistory.setOpType(ClientTypeEnum.TYPE_WORK_FLOW_STUDIO.toString());
                userLoginHistory.setOpMessage((ClientTypeEnum.TYPE_WORK_FLOW_STUDIO.toString() + "登录"));
                break;
        }
        userLoginHistoryService.saveUserLoginRecord(userLoginHistory);
    }

    /**
     * -2   "登陆受限licence导致"
     * -1   "服务器出现异常"
     * 1   "登录校验成功"
     * 2   "已有用户将该业务库锁定"
     * 0   "用户名密码不正确或该用户没有权限"
     *
     * @param username
     * @param ip
     * @param password
     * @param clientType
     * @return
     */
    public CommonResponse loginCheck(String username, String ip, String password, String clientType) {
        CommonResponse retVal = new CommonResponse();
        CommonDataBean result = new CommonDataBean();
        try {
            HttpServletRequest request = XFireServletController.getRequest();
            String message = checkLicence(request);
            //校验licence，-2表示登陆受限由licence导致（这个校验暂时注销掉了，到时候根据需要再加方法实现）
            if (!"success".equals(message)) {
                result.setStatus(LoginCheckResponseTypeEnum.TYPE_LICENCE_ERROR.toString());
                retVal.setMsg(message);
                retVal.setSuccess(false);
                retVal.setResult(result);
                return retVal;
            }
            //根据用户名获用户信息,保存到userInfo对象中去
            User user = (User) roleEngine.getRoleModel(false).getUserByUserName(username);
            if (user != null) {
                //保存到userInfo对象中去
                saveUserLoginRecord(user, clientType, request);
                //校验密码
                if (user.getPassword().equals(password)) {
                    result.setStatus(LoginCheckResponseTypeEnum.TYPE_LOGIN_SUCCESS.toString());
                    retVal.setResult(result);
                    return retVal;
                } else {
                    result.setStatus(LoginCheckResponseTypeEnum.TYPE_LOGIN_FAIL.toString());
                    retVal.setMsg("密码错误，请重新输入");
                    retVal.setSuccess(false);
                    retVal.setResult(result);
                    return retVal;
                }
            } else {
                result.setStatus(LoginCheckResponseTypeEnum.TYPE_LOGIN_FAIL.toString());
                retVal.setMsg("当前用户名不存在，请重新输入");
                retVal.setSuccess(false);
                retVal.setResult(result);
                return retVal;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(LoginCheckResponseTypeEnum.TYPE_EXCEPTION.toString());
            retVal.setMsg("服务器异常");
            retVal.setSuccess(false);
            retVal.setResult(result);
            return retVal;
        }

    }

    public SchemaResponse loginCheckAndGetSchema(String username, String ip, String password, String clientType, Integer isGetAll) {
        SchemaResponse retVal = new SchemaResponse();
        CommonResponse loginCheckResponse = loginCheck(username, ip, password, clientType);
        try {
            if (loginCheckResponse.isSuccess()) {
                switch (isGetAll) {
                    case 0:
                        retVal.setResult(getSchemaList(0));
                        break;
                    case 1:
                        retVal.setResult(getSchemaList(1));
                    default:
                        retVal.setSuccess(false);
                        retVal.setMsg("请求参数错误");
                }
            } else {
                retVal.setSuccess(false);
                retVal.setMsg("登录校验失败");
            }
        } catch (Exception e) {
            retVal.setSuccess(false);
            retVal.setMsg("服务端异常");
            return retVal;
        }
        return retVal;
    }

    /**
     * 获取schema列表
     *
     * @param isGetAll 0表述获取所有数据模型，1表示获取上锁的数据模型
     * @return schema列表
     */
    private List<FrontSchema> getSchemaList(Integer isGetAll) {
        List<FrontSchema> retVal = new ArrayList<>();
        try {
            List<ISchema> schemaList;
            IMetaModel metaModel = metaEngine.getMeta(true);
            if (isGetAll == 1) {
                schemaList = metaModel.getISchemaByIsLockAndIsDelete(1, 1);
            } else {
                schemaList = metaModel.getISchemaByIsDelete(1);
            }
            schemaList.forEach(schema -> {
                FrontSchema frontSchema = new FrontSchema();
                frontSchema.setSchemaId(schema.getId());
                frontSchema.setSchemaName(schema.getName());
                frontSchema.setVersion(schema.getVersion());
                String schemaType = schema.getType();
                if (schemaType == null) {
                    schemaType = "0";
                }
                frontSchema.setSchemaType(schemaType);
                frontSchema.setLockType(schema.getIsLock() == null ? "0" : schema.getIsLock().toString());
                retVal.add(frontSchema);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retVal;
    }

    /**
     * 登录并把当前schema上锁
     *
     * @param username
     * @param ip
     * @param password
     * @param clientType
     * @param schemaName
     * @param version
     * @return
     */
    public LockResponse loginCheckAndLockSchema(String username, String ip, String password, String clientType, String schemaName, String version) {
        LockResponse retVal = new LockResponse();
        LockStatus result = new LockStatus();
        CommonResponse loginCheckResponse = loginCheck(username, ip, password, clientType);
        try {
            if (loginCheckResponse.isSuccess()) {
                IMetaModel metaModel = metaEngine.getMeta(false);
                ISchema iSchema = metaModel.getISchema(schemaName, version);
                if (iSchema == null) {
                    result.setStatus(LockResponseEnum.TYPE_SCHEMA_NOT_EXIST.value()); //schema不存在
                    retVal.setResult(result);
                    retVal.setSuccess(false);
                    return retVal;
                }
                Schema schema = (Schema) iSchema;
                Integer oldLock = schema.getIsLock();
                String oldUsername = schema.getUsername();
                String oldIp = schema.getIp();
                if (oldLock == SchemaLockStatusEnum.TYPE_LOCKED.value()) {  //如果已经上锁
                    if (oldUsername.equals(username) && oldIp.equals(ip)) {
                        result.setStatus(LockResponseEnum.TYPE_LOCK_SUCCESS.value());
                        retVal.setResult(result); //如果是自己上的锁返回1
                    } else {
                        result.setStatus(LockResponseEnum.TYPE_HAS_LOCKED.value()); //如果是别人上的锁返回2
                        retVal.setSuccess(false);
                        retVal.setMsg("当前schema已被其他用户上锁，您无法上锁");
                        retVal.setResult(result);
                    }
                } else {
                    return lock(schema, username, ip, schemaName, version, retVal, result);
                }
            } else {
                retVal.setSuccess(false);
                retVal.setMsg("登录校验失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param schema
     * @param username
     * @param ip
     * @param schemaName
     * @param version
     * @param retVal
     * @param result
     * @return
     * @throws Exception
     */
    private LockResponse lock(Schema schema, String username, String ip, String schemaName, String version, LockResponse retVal, LockStatus result) {
        try {
            schema.setIsLock(SchemaLockStatusEnum.TYPE_LOCKED.value());
            schema.setUsername(username);
            schema.setLockModifiedTime(Commons.getSysDate());
            metadaofactory.getSchemaDAO().attachDirty(schema);
            IMetaModel metaModel = metaEngine.getMeta(false);
            ISchema iSchema = metaModel.getISchema(schemaName, version);
            Schema newSchema;
            if (iSchema != null) {
                newSchema = (Schema) iSchema;
            } else {
                result.setStatus(LockResponseEnum.TYPE_SCHEMA_NOT_EXIST.value()); //schema不存在
                retVal.setResult(result);
                retVal.setSuccess(false);
                return retVal;
            }
            Integer newLock = newSchema.getIsLock();
            if (newLock == SchemaLockStatusEnum.TYPE_LOCKED.value()) {
                result.setStatus(LockResponseEnum.TYPE_LOCK_SUCCESS.value()); //上锁成功
                retVal.setResult(result);
                return retVal;
            }
        } catch (Exception e) {
            result.setStatus(LockResponseEnum.TYPE_EXCEPTION.value());       //上锁异常
            retVal.setResult(result);
            retVal.setSuccess(false);
            return retVal;
        }
        return null;
    }

}
