package com.orient.dsrestful.business;

import com.orient.web.base.dsbean.CommonDataBean;
import com.orient.web.base.dsbean.CommonResponse;
import com.orient.dsrestful.domain.lock.LockResponse;
import com.orient.dsrestful.domain.lock.LockStatus;
import com.orient.dsrestful.domain.login.LoginAndGetSchemaBean;
import com.orient.dsrestful.domain.login.LoginAndLockSchemaBean;
import com.orient.dsrestful.domain.login.LoginRequestBean;
import com.orient.dsrestful.domain.schemaBaseInfo.SchemaResponse;
import com.orient.dsrestful.enums.LockResponseEnum;
import com.orient.dsrestful.enums.LoginCheckResponseTypeEnum;
import com.orient.dsrestful.enums.SchemaLockStatusEnum;
import com.orient.dsrestful.service.BaseSchemaService;
import com.orient.dsrestful.service.SaveUserLoginService;
import com.orient.metamodel.metadomain.Schema;
import com.orient.metamodel.operationinterface.IMetaModel;
import com.orient.metamodel.operationinterface.ISchema;
import com.orient.sysmodel.domain.user.User;
import com.orient.web.base.BaseBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by GNY on 2018/3/24
 */
@Component
public class LoginValidationBusiness extends BaseBusiness {

    @Autowired
    SaveUserLoginService saveUserLoginService;

    @Autowired
    BaseSchemaService baseSchemaService;

    /**
     * 登录校验
     *
     * @param loginValidate
     * @return
     */
    public CommonResponse loginValidate(LoginRequestBean loginValidate) {
        CommonResponse retVal = new CommonResponse();
        CommonDataBean result = new CommonDataBean();
        try {
            //根据用户名获用户对象
            User user = (User) roleEngine.getRoleModel(false).getUserByUserName(loginValidate.getUsername());
            if (user != null) {
                //校验密码
                if (user.getPassword().equals(loginValidate.getPassword())) {
                    //保存本次客户端的登录记录
                    saveUserLoginService.saveUserLoginRecord(user, loginValidate.getIp(), loginValidate.getClientType());
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
            retVal.setMsg("服务端异常");
            retVal.setSuccess(false);
            retVal.setResult(result);
            return retVal;
        }
    }

    /**
     * 登录校验并获取schema列表
     *
     * @param loginAndGetSchemaBean
     * @return
     */
    public SchemaResponse validateAndGetSchema(LoginAndGetSchemaBean loginAndGetSchemaBean) {
        SchemaResponse retVal = new SchemaResponse();
        CommonResponse loginCheckResponse = loginValidate(new LoginRequestBean(loginAndGetSchemaBean.getUsername(), loginAndGetSchemaBean.getPassword(), loginAndGetSchemaBean.getIp(), loginAndGetSchemaBean.getClientType()));
        try {
            if (loginCheckResponse.isSuccess()) {
                retVal.setResult(loginAndGetSchemaBean.isGetAll() ? baseSchemaService.getSchemaList(true) : baseSchemaService.getSchemaList(false));
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
     * 登录校验并把schema上锁
     */
    public LockResponse validateAndLockSchema(LoginAndLockSchemaBean loginAndLockSchemaBean) {
        LockResponse retVal = new LockResponse();
        LockStatus result = new LockStatus();
        CommonResponse loginCheckResponse = loginValidate(new LoginRequestBean(loginAndLockSchemaBean.getUsername(), loginAndLockSchemaBean.getPassword(), loginAndLockSchemaBean.getIp(), loginAndLockSchemaBean.getClientType()));
        try {
            if (loginCheckResponse.isSuccess()) {
                IMetaModel metaModel = metaEngine.getMeta(false);
                ISchema iSchema = metaModel.getISchema(loginAndLockSchemaBean.getSchemaName(), loginAndLockSchemaBean.getVersion());
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
                    if (oldUsername.equals(loginAndLockSchemaBean.getUsername()) && oldIp.equals(loginAndLockSchemaBean.getIp())) {
                        result.setStatus(LockResponseEnum.TYPE_LOCK_SUCCESS.value());
                        retVal.setResult(result); //如果是自己上的锁返回1
                    } else {
                        result.setUsername(oldUsername);
                        result.setIp(oldIp);
                        result.setStatus(LockResponseEnum.TYPE_HAS_LOCKED.value()); //如果是别人上的锁返回2
                        retVal.setSuccess(false);
                        retVal.setMsg("当前schema已被其他用户上锁");
                        retVal.setResult(result);
                    }
                } else {   //如果没上锁则进行上锁操作
                    return baseSchemaService.lockSchema(schema, loginAndLockSchemaBean.getUsername(), loginAndLockSchemaBean.getIp(), loginAndLockSchemaBean.getSchemaName(), loginAndLockSchemaBean.getVersion(), retVal, result);
                }
            } else {
                retVal.setSuccess(false);
                retVal.setMsg("登录校验失败");
            }
            return retVal;
        } catch (Exception e) {
            e.printStackTrace();
            retVal.setSuccess(false);
            retVal.setMsg("服务端异常");
            return retVal;
        }
    }

}
