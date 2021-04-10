package com.orient.log.aop;

import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.log.annotion.Action;
import com.orient.log.annotion.ActionExecOrder;
import com.orient.sysmodel.domain.sys.SysLog;
import com.orient.sysmodel.domain.user.User;
import com.orient.sysmodel.service.sys.impl.SysLogService;
import com.orient.utils.CommonTools;
import com.orient.utils.Log.LogThreadLocalHolder;
import com.orient.utils.StringUtil;
import com.orient.web.form.engine.FreemarkEngine;
import com.orient.web.util.RequestUtil;
import com.orient.web.util.UserContextUtil;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateException;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModelException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用AOP拦截Controller的方式记录日志。<br>
 * 如果控制器方法需要被拦截，请在方法之前添加注解 {@link Action Action},这样才能记录日志。
 *
 * @author zhulongchao
 */
@Component("tdmLogAspect")
public class LogAspect {
    private Log logger = LogFactory.getLog(LogAspect.class);

    private static WorkQueue wq = new WorkQueue(10);

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    FreemarkEngine freemarkEngine;

    private static boolean isCommonServicesInited = false;
    private static Map<String, Object> commonServices = new HashMap<String, Object>();

    // 添加FreeMarker可访问的类静态方法的字段
    static Map<String, TemplateHashModel> STATIC_CLASSES = new HashMap<String, TemplateHashModel>();

    static {
        try {
            BeansWrapper beansWrapper = BeansWrapper.getDefaultInstance();
            TemplateHashModel staticModel = beansWrapper.getStaticModels();
            STATIC_CLASSES.put(Long.class.getSimpleName(),
                    (TemplateHashModel) staticModel.get(Long.class
                            .getName()));
            STATIC_CLASSES.put(Integer.class.getSimpleName(),
                    (TemplateHashModel) staticModel.get(Integer.class
                            .getName()));
            STATIC_CLASSES.put(String.class.getSimpleName(),
                    (TemplateHashModel) staticModel.get(String.class
                            .getName()));
            STATIC_CLASSES.put(Short.class.getSimpleName(),
                    (TemplateHashModel) staticModel.get(Short.class
                            .getName()));
            STATIC_CLASSES.put(Boolean.class.getSimpleName(),
                    (TemplateHashModel) staticModel.get(Boolean.class
                            .getName()));
            STATIC_CLASSES.put(CommonTools.class.getSimpleName(),
                    (TemplateHashModel) staticModel
                            .get(com.orient.utils.CommonTools.class
                                    .getName()));
            STATIC_CLASSES
                    .put(StringUtil.class.getSimpleName(),
                            (TemplateHashModel) staticModel
                                    .get(com.orient.utils.StringUtil.class
                                            .getName()));
        } catch (TemplateModelException e) {
            e.printStackTrace();
        }
    }

    public Object execute(ProceedingJoinPoint point) throws Throwable {
        Object returnVal = null;
        String methodName = point.getSignature().getName();
        // 类
        Class<?> targetClass = point.getTarget().getClass();
        // 方法
        Method[] methods = targetClass.getMethods();
        Method method = null;
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getName() == methodName) {
                method = methods[i];
                break;
            }
        }
        // 如果横切点不是方法，返回
        if (method == null)
            return point.proceed();
        // 方法Action
        Action annotation = method.getAnnotation(Action.class);
        // 如果方法上没有注解@Action，返回
        if (annotation == null) {
            return point.proceed();
        }
        if (ActionExecOrder.BEFORE.equals(annotation.execOrder())) {
            doLog(point, false);
            returnVal = point.proceed();
        } else if (ActionExecOrder.AFTER.equals(annotation.execOrder())) {
            returnVal = point.proceed();
            doLog(point, true);
        } else {
            returnVal = point.proceed();
            doLog(point, true);
        }
        return returnVal;
    }

    @Transactional
    public void doLog(ProceedingJoinPoint point, boolean async) {
        try {
            String methodName = point.getSignature().getName();
            if (CommonTools.isNullString(methodName)) {
                return;
            }
            // 类
            Class<?> targetClass = point.getTarget().getClass();
            // 类Action
            Action classAction = targetClass.getAnnotation(Action.class);
            // 方法
            Method[] methods = targetClass.getMethods();
            Method method = null;
            for (int i = 0; i < methods.length; i++) {
                if (methods[i].getName() == methodName) {
                    method = methods[i];
                    break;
                }
            }
            // 如果横切点不是方法，返回
            if (method == null)
                return;
            // 方法Action
            Action annotation = method.getAnnotation(Action.class);
            // 如果方法上没有注解@Action，返回
            if (annotation == null) {
                return;
            }
            // Action描述
            String methodDescp = annotation.description();
            // 增加归属模块
            String modelType = annotation.ownermodel();
            // 日志类型
            String exectype = annotation.exectype();

            if ("".endsWith(modelType)) {
                if (classAction != null) {
                    modelType = classAction.ownermodel();
                }
            }
            String ownermodel = modelType.toString();
            // 日志开关
            /*
             * SysLogSwitch sysLogSwitch =
			 * sysLogSwitchService.getByModel(ownermodel);
			 * if(sysLogSwitch==null){ return ; } short status
			 * =sysLogSwitch.getStatus
			 * ()==null?SysLogSwitch.STATUS_CLOSE:sysLogSwitch.getStatus();
			 * if(status!=SysLogSwitch.STATUS_OPEN){ return ; }
			 */
            SysLog log = new SysLog();
            //添加日志属性
            log.setOpTypeId(methodDescp);
            log.setOpTarget(ownermodel);
            log.setOpDate(new Date());
            Boolean successFlag = (Boolean) LogThreadLocalHolder.getParamerter("success");
            log.setOpResult((null != successFlag && successFlag == false) ? "失败" : "成功");
            User user = UserContextUtil.getCurrentUser();
            if (null != user) {
                log.setOpUserId(user.getUserName());
            } else {
                //如果session中不存在用户信息
                log.setOpUserId((String) LogThreadLocalHolder.getParamerter("userName"));
            }
            HttpServletRequest request = RequestUtil.getHttpServletRequest();
            if (request != null) {
                String fromIp = RequestUtil.getIpAddr(request);
                log.setOpIpAddress(fromIp);
            }
            // 添加明细信息
            String detail = LogThreadLocalHolder.getDetail();
            if (async) {
                LogHolder logHolder = new LogHolder();
                if (CommonTools.isNullString(detail)) {
                    detail = annotation.detail();
                    if (!CommonTools.isNullString(detail)) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        // 添加Request查询参数
                        if (request != null) {
                            map.putAll(RequestUtil.getQueryMap(request));
                        }
                        // 添加线程相关变量
                        map.putAll(LogThreadLocalHolder.getParamerters());
                        initCommonServices();
                        map.putAll(commonServices);
                        // 添加通用静态类
                        map.putAll(STATIC_CLASSES);
                        logHolder.setParseDataModel(map);
                        logHolder.setNeedParse(true);
                    }
                }
                log.setOpRemark(detail);
                logHolder.setSyslog(log);
                doLogAsync(logHolder);
            } else {
                if (CommonTools.isNullString(detail)) {
                    detail = annotation.detail();
                    if (!CommonTools.isNullString(detail)) {
                        try {
                            detail = parseDetail(detail, request);
                        } catch (Exception ex) {
                            logger.error(ex.getMessage());
                            ex.printStackTrace();
                            detail = null;
                        }
                    }
                }
                sysLogService.save(log);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doLogAsync(LogHolder holder) {
        LogExecutor logExecutor = new LogExecutor();
        logExecutor.setLogHolders(holder);
        wq.execute(logExecutor);
    }

    private String parseDetail(String detail, HttpServletRequest request)
            throws TemplateException, IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        // 添加Request查询参数
        if (request != null) {
            map.putAll(RequestUtil.getQueryMap(request));
        }
        map.put("request", request);
        // 添加线程相关变量
        map.putAll(LogThreadLocalHolder.getParamerters());
        initCommonServices();
        map.putAll(commonServices);
        // 添加通用静态类
        map.putAll(STATIC_CLASSES);

        return freemarkEngine.parseByStringTemplate(map, detail);
    }

    private void initCommonServices() {
        if (isCommonServicesInited) {
            return;
        }
        String[] beanNames = OrientContextLoaderListener.Appwac.getBeanNamesForType(com.orient.web.base.BaseBusiness.class);
        for (String beanName : beanNames) {
            Object bean = OrientContextLoaderListener.Appwac.getBean(beanName);
            commonServices.put(beanName, bean);
        }
        isCommonServicesInited = true;
    }
}
