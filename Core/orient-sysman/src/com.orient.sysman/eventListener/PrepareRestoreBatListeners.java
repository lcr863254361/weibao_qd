package com.orient.sysman.eventListener;

import com.orient.edm.init.DBConfig;
import com.orient.sysman.event.BackUpEvent;
import com.orient.sysman.eventtParam.BackUpEventParam;
import com.orient.sysmodel.domain.sys.CwmBackEntity;
import com.orient.utils.FileOperator;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.UnsupportedEncodingException;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-06-29 18:07
 */
@Component
public class PrepareRestoreBatListeners extends OrientEventListener {

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
        //获取数据库连接信息
        String dbUserName = dbConfig.getDbUserName();
        String dbPassword = dbConfig.getDbPassword();
        String dbSid = dbConfig.getDbSid();
        String dbIp = dbConfig.getDbIp();
        //恢复bat路径
        String restoreBatPath = backDir + File.separator + "restore.bat";
        StringBuffer restoreStr = new StringBuffer();
        restoreStr.append("sqlplus ").append(dbUserName).append("/").append(dbPassword).append("@").append(dbIp);
        restoreStr.append("/").append(dbSid).append(" @").append(backDir + File.separator + "truncate.sql").append("\r\n");
        restoreStr.append("imp ").append(dbUserName).append("/").append(dbPassword).append("@").append(dbIp).append("/");
        restoreStr.append(dbSid).append(" file=").append(backDir + File.separator + "data.dmp").append(" full=y ignore=y\r\n");
        //保存文件
        try {
            FileOperator.createFile(restoreBatPath, restoreStr.toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
