package com.orient.sysman.eventListener;

import com.orient.edm.init.FileServerConfig;
import com.orient.sysman.event.BackUpEvent;
import com.orient.sysman.eventtParam.BackUpEventParam;
import com.orient.sysmodel.domain.sys.CwmBackEntity;
import com.orient.utils.CommonTools;
import com.orient.utils.FileOperator;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 生成清空表数据的sql文件
 *
 * @author Administrator
 * @create 2016-06-29 18:07
 */
@Component
public class PrepareTruncateSqlListeners extends OrientEventListener {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    FileServerConfig fileServerConfig;

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
        //创建备份目录
        String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String backDir = fileServerConfig.getFtpHome() + File.separator + "数据库备份" + File.separator + date.substring(0,4) + File.separator + date.substring(4,6) + File.separator
                        + date.substring(6,8) + File.separator + date.substring(8);
        FileOperator.createFolds(backDir + File.separator);
        backEntity.setFilePath(backDir);
        //获取当前用户的所有表信息
        List<Map<String, Object>> userTables = jdbcTemplate.queryForList("select * from user_tables");
        //创建truncate sql文件
        String truncateSqlPath = backDir + File.separator + "truncate.sql";
        StringBuffer truncateSqlStr = new StringBuffer();
        userTables.forEach(userTable -> {
            //获取表名称
            String tableName = CommonTools.Obj2String(userTable.get("TABLE_NAME"));
            //忽略备份恢复表
            if(!"CWM_BACK".equals(tableName.toUpperCase()) && !"CWM_TIME_INFO".equals(tableName.toUpperCase())){
                //写入移除数据SQL语句
                truncateSqlStr.append("alter table " + tableName + " disable primary key cascade;\r\n");
                truncateSqlStr.append("TRUNCATE TABLE " + tableName + " ;\r\n");
                truncateSqlStr.append("alter table " + tableName + " enable  primary key;\r\n");
            }
        });
        truncateSqlStr.append("exit;\\r\\n");
        //保存文件
        try {
            FileOperator.createFile(truncateSqlPath, truncateSqlStr.toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
