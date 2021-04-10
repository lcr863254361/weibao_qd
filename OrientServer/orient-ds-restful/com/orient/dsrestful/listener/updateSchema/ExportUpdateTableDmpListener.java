package com.orient.dsrestful.listener.updateSchema;

import com.orient.dsrestful.event.UpdateSchemaEvent;
import com.orient.dsrestful.eventparam.UpdateSchemaParam;
import com.orient.edm.init.DBConfig;
import com.orient.edm.init.FileServerConfig;
import com.orient.utils.FileOperator;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * 把更新的表导出dmp
 *
 * @author GNY
 * @create 2018-04-16 15:07
 */
@Component
public class ExportUpdateTableDmpListener extends OrientEventListener {

    @Autowired
    DBConfig dbConfig;

    @Autowired
    FileServerConfig fileServerConfig;

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return eventType == UpdateSchemaEvent.class || UpdateSchemaEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (super.isAbord(event)) {
            return;
        }
        OrientEvent orientEvent = (OrientEvent) event;
        UpdateSchemaParam param = (UpdateSchemaParam) orientEvent.getParams();
        Set<String> updateTableList = param.getUpdateTableList();
        String tableNames = updateTableList.stream()
                .collect(Collectors.joining(","));
        //获取数据库连接信息
        String dbUserName = dbConfig.getDbUserName();
        String dbPassword = dbConfig.getDbPassword();
        String dbSid = dbConfig.getDbSid();
        String dbIp = dbConfig.getDbIp();

        String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String flashBackDir = fileServerConfig.getFtpHome() + File.separator + "dsrecovery" + File.separator +
                date.substring(0, 4) + File.separator + date.substring(4, 6) + File.separator +
                date.substring(6, 8) + File.separator + date.substring(8);
        FileOperator.createFolds(flashBackDir + File.separator);
        String dmpPath = flashBackDir + File.separator + date + ".dmp";

        StringBuffer sql = new StringBuffer();
        sql.append("exp ")
                .append(dbUserName)
                .append("/")
                .append(dbPassword)
                .append("@")
                .append(dbIp)
                .append("/")
                .append(dbSid)
                .append(" file=")
                .append(dmpPath)
                .append(" statistics=none TABLES=(")
                .append(tableNames)
                .append(")");

        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec(sql.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
