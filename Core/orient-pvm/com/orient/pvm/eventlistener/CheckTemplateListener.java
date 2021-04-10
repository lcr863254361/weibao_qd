package com.orient.pvm.eventlistener;

import com.orient.edm.init.FileServerConfig;
import com.orient.pvm.bean.CheckTemplateParseResult;
import com.orient.pvm.event.ImportCheckTemplateEvent;
import com.orient.pvm.eventparam.ImportCheckTemplateEventParam;
import com.orient.pvm.validate.builderpattern.Builder;
import com.orient.pvm.validate.builderpattern.director.DefaultDirector;
import com.orient.pvm.validate.builderpattern.director.Director;
import com.orient.utils.CommonTools;
import com.orient.utils.FileOperator;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventCheck;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import com.orient.web.util.UserContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mengbin on 16/7/30.
 * Purpose:
 * Detail: 检查上传的模版是否符合规则
 */
@Service
public class CheckTemplateListener extends OrientEventListener {

    @Autowired
    FileServerConfig fileServerConfig;

    @Autowired
    @Qualifier("defaultValidatorBuilder")
    Builder builder;


    @Override
    @OrientEventCheck()
    public boolean supportsEventType(Class<? extends ApplicationEvent> aClass) {

        //该监听器能够处理对应事件及其子类事件
        return ImportCheckTemplateEvent.class == aClass || ImportCheckTemplateEvent.class.isAssignableFrom(aClass);

    }


    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (this.isAbord(applicationEvent)) {
            return;
        }
        ImportCheckTemplateEventParam param = (ImportCheckTemplateEventParam) ((OrientEvent) applicationEvent).getParams();
        String modelId = param.getCheckModelDataTemplate().getCheckmodelid().toString();
        //保存文件
        String realFileStoragePath = "";
        CheckTemplateParseResult checkTemplateParseResult = null;
        if (null != param.getTemplateFile()) {
            //保存模板文件
            String timeSuffix = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String fileName = param.getTemplateFile().getOriginalFilename();
            String finalFileName = timeSuffix + "_" + fileName;
            realFileStoragePath = fileServerConfig.getPvmTemplateHome() + File.separator + finalFileName;
            try {
                FileOperator.createFile(realFileStoragePath, param.getTemplateFile().getBytes());
                //校验
                Director defaultDirector = new DefaultDirector(realFileStoragePath, modelId);
                checkTemplateParseResult = defaultDirector.doBuild(builder);
                String userDisplayName = UserContextUtil.getUserAllName();
                param.getCheckModelDataTemplate().setCreateuser(userDisplayName);
                param.getCheckModelDataTemplate().setUploadtime(new Date());
                param.getCheckModelDataTemplate().setTemplatepath(realFileStoragePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (null != checkTemplateParseResult && !checkTemplateParseResult.getErrors().isEmpty()) {
            ((OrientEvent) applicationEvent).aboardEvetn();
            param.setCheckResult(CommonTools.list2String(checkTemplateParseResult.getErrors(), "\r\n"));
        }
    }
}
