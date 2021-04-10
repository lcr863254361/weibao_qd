package com.orient.background.statistic.parse;

import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.utils.CommonTools;
import com.orient.utils.Log.LogThreadLocalHolder;
import com.orient.utils.StringUtil;
import com.orient.web.form.engine.FreemarkEngine;
import com.orient.web.util.RequestUtil;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateException;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/5 0005.
 * 采用freemarkerengine 将 sql语句转化为最终执行形态
 */
@Component
public class StatisticSqlParser {

    @Autowired
    FreemarkEngine freemarkEngine;

    //
    private static boolean isCommonBusinessInited = false;

    private static Map<String, Object> commonBusiness = new HashMap<String, Object>();

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

    private void initCommonServices() {
        if (isCommonBusinessInited) {
            return;
        }
        String[] beanNames = OrientContextLoaderListener.Appwac.getBeanNamesForType(com.orient.web.base.BaseBusiness.class);
        for (String beanName : beanNames) {
            Object bean = OrientContextLoaderListener.Appwac.getBean(beanName);
            commonBusiness.put(beanName, bean);
        }
        isCommonBusinessInited = true;
    }

    public String doParser(String sql, HttpServletRequest request) throws IOException, TemplateException {
        Map<String, Object> map = new HashMap<String, Object>();
        // 添加Request查询参数
        if (request != null) {
            map.putAll(RequestUtil.getQueryMap(request));
        }
        map.put("request", request);
        // 添加线程相关变量
        map.putAll(StatisticThreadLocalHolder.getParamerters());
        initCommonServices();
        map.putAll(commonBusiness);
        // 添加通用静态类
        map.putAll(STATIC_CLASSES);
        return freemarkEngine.parseByStringTemplate(map, sql);
    }
}
