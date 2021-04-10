package com.orient.sysman.eventListener;

import com.orient.edm.init.DBConfig;
import com.orient.sysman.event.BackUpEvent;
import com.orient.sysman.eventtParam.BackUpEventParam;
import com.orient.sysman.util.BackUpCommandUtil;
import com.orient.sysmodel.domain.sys.CwmBackEntity;
import com.orient.utils.CommonTools;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-06-29 18:07
 */
@Component
public class PrepareDmpListeners extends OrientEventListener {

    @Autowired
    DBConfig dbConfig;


    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        if (!isOrientEvent(eventType)) {
            return false;
        }
        return eventType == BackUpEvent.class || BackUpEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (super.isAbord(event)) {
            return;
        }
        OrientEvent orientEvent = (OrientEvent) event;
        //获取事件参数
        BackUpEventParam param = (BackUpEventParam) orientEvent.getParams();
        CwmBackEntity backEntity = param.getBackEntity();
        //获取备份位置
        String backDir = backEntity.getFilePath();
        //备份dmp bat路径
        String backBatPath = CommonTools.getRootPath() + File.separator + "bats" + File.separator + "backup.bat";
        String dmpPath = backDir + File.separator + "data.dmp";
        String dbUserName = dbConfig.getDbUserName();
        String dbPassword = dbConfig.getDbPassword();
        String dbSid = dbConfig.getDbSid();
        String dbIp = dbConfig.getDbIp();
        //执行备份
        BackUpCommandUtil backUpCommandUtil = new BackUpCommandUtil(backBatPath, dmpPath, dbIp, dbUserName, dbPassword, dbSid);
        backUpCommandUtil.execBackUp();
    }
}
