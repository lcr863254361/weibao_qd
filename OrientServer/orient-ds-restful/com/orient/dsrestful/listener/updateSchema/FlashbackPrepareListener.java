package com.orient.dsrestful.listener.updateSchema;

import com.orient.dsrestful.event.UpdateSchemaEvent;
import com.orient.dsrestful.eventparam.UpdateSchemaParam;
import com.orient.edm.init.DBConfig;
import com.orient.edm.init.FileServerConfig;
import com.orient.metamodel.metadomain.Schema;
import com.orient.utils.CommonTools;
import com.orient.utils.FileOperator;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import com.orient.utils.exception.OrientBaseAjaxException;
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
 * 1.所有业务表和ds相关的系统表设置row movement为enable
 * 2.回滚脚本文件生成
 * 3.调用脚本的bat文件生成
 *
 * Created by GNY on 2018/4/10
 */
@Component
public class FlashbackPrepareListener extends OrientEventListener {

    @Autowired
    DBConfig dbConfig;

    @Autowired
    FileServerConfig fileServerConfig;

    @Autowired
    JdbcTemplate jdbcTemplate;

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
        Schema oldSchema = param.getOldSchema();
        //获取数据库连接信息
        String dbUserName = dbConfig.getDbUserName();
        String dbPassword = dbConfig.getDbPassword();
        String dbSid = dbConfig.getDbSid();
        String dbIp = dbConfig.getDbIp();
        /*StringBuffer connectSql = new StringBuffer();
        connectSql.append("sqlplus ")
                .append(dbUserName)
                .append("/")
                .append(dbPassword)
                .append("@")
                .append(dbIp)
                .append("/")
                .append(dbSid);
        jdbcTemplate.execute(connectSql.toString());*/
        oldSchema.getTables().forEach(table -> jdbcTemplate.execute("ALTER TABLE " + table.getTableName() + " ENABLE ROW MOVEMENT"));
        jdbcTemplate.execute("ALTER TABLE CWM_SCHEMA ENABLE ROW MOVEMENT");
        jdbcTemplate.execute("ALTER TABLE CWM_TABLES ENABLE ROW MOVEMENT");
        jdbcTemplate.execute("ALTER TABLE CWM_TABLE_COLUMN ENABLE ROW MOVEMENT");
        jdbcTemplate.execute("ALTER TABLE CWM_TAB_COLUMNS ENABLE ROW MOVEMENT");
        jdbcTemplate.execute("ALTER TABLE CWM_ENUM ENABLE ROW MOVEMENT");
        jdbcTemplate.execute("ALTER TABLE CWM_TABLE_ENUM ENABLE ROW MOVEMENT");
        jdbcTemplate.execute("ALTER TABLE CWM_RESTRICTION ENABLE ROW MOVEMENT");
        jdbcTemplate.execute("ALTER TABLE CWM_RELATION_COLUMNS ENABLE ROW MOVEMENT");
        jdbcTemplate.execute("ALTER TABLE CWM_VIEWS ENABLE ROW MOVEMENT");
        jdbcTemplate.execute("ALTER TABLE CWM_VIEW_RETURN_COLUMN ENABLE ROW MOVEMENT");
        jdbcTemplate.execute("ALTER TABLE CWM_VIEW_PAIXU_COLUMN ENABLE ROW MOVEMENT");
        jdbcTemplate.execute("ALTER TABLE CWM_VIEW_RELATIONTABLE ENABLE ROW MOVEMENT");
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("SELECT CURRENT_SCN FROM V$DATABASE");
        String scn = null;
        if (maps.size() > 0) {
            scn = CommonTools.Obj2String(maps.get(0).get("CURRENT_SCN"));
        }

        String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String flashBackDir = fileServerConfig.getFtpHome() + File.separator + "dsflashback" + File.separator +
                date.substring(0, 4) + File.separator + date.substring(4, 6) + File.separator +
                date.substring(6, 8) + File.separator + date.substring(8);
        FileOperator.createFolds(flashBackDir + File.separator);

        //创建flashback.sql文件
        String flashbackSqlPath = flashBackDir + File.separator + "flashback.sql";
        StringBuffer flashbackSql = new StringBuffer();
       /* flashbackSql.append("sqlplus ")
                .append(dbUserName)
                .append("/")
                .append(dbPassword)
                .append("@")
                .append(dbIp)
                .append("/")
                .append(dbSid)
                .append("\r\n");*/
       /* for (Table table : oldSchema.getTables()) {
            flashbackSql.append("FLASHBACK TABLE ").append(table.getTableName()).append(" TO SCN ").append(scn).append(";").append("\r\n");
        }*/
        flashbackSql.append("FLASHBACK TABLE CWM_SCHEMA TO SCN ").append(scn).append(";\r\n");
        flashbackSql.append("FLASHBACK TABLE CWM_TABLES TO SCN ").append(scn).append(";\r\n");
        flashbackSql.append("FLASHBACK TABLE CWM_TABLE_COLUMN TO SCN ").append(scn).append(";\r\n");
        flashbackSql.append("FLASHBACK TABLE CWM_TAB_COLUMNS TO SCN ").append(scn).append(";\r\n");
        flashbackSql.append("FLASHBACK TABLE CWM_ENUM TO SCN ").append(scn).append(";\r\n");
        flashbackSql.append("FLASHBACK TABLE CWM_TABLE_ENUM TO SCN ").append(scn).append(";\r\n");
        flashbackSql.append("FLASHBACK TABLE CWM_RESTRICTION TO SCN ").append(scn).append(";\r\n");
        flashbackSql.append("FLASHBACK TABLE CWM_RELATION_COLUMNS TO SCN ").append(scn).append(";\r\n");
        flashbackSql.append("FLASHBACK TABLE CWM_VIEWS TO SCN ").append(scn).append(";\r\n");
        flashbackSql.append("FLASHBACK TABLE CWM_VIEW_RETURN_COLUMN TO SCN ").append(scn).append(";\r\n");
        flashbackSql.append("FLASHBACK TABLE CWM_VIEW_PAIXU_COLUMN TO SCN ").append(scn).append(";\r\n");
        flashbackSql.append("FLASHBACK TABLE CWM_VIEW_RELATIONTABLE TO SCN ").append(scn).append(";\r\n");
        flashbackSql.append("exit;\r\n");

        //保存flashback.sql文件
        try {
            FileOperator.createFile(flashbackSqlPath, flashbackSql.toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new OrientBaseAjaxException("-1", e.getMessage());
        }

        //创建bat文件
        String flashbackBatPath = flashBackDir + File.separator + "flashback.bat";
        param.setFlashbackBatPath(flashbackBatPath);
        StringBuffer flashbackBatStr = new StringBuffer();
        flashbackBatStr.append("sqlplus ")
                .append(dbUserName)
                .append("/").append(dbPassword).append("@")
                .append(dbIp);
        flashbackBatStr.append("/").append(dbSid).append(" @").append(flashBackDir + File.separator + "flashback.sql").append("\r\n");
        //保存bat文件
        try {
            FileOperator.createFile(flashbackBatPath, flashbackBatStr.toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new OrientBaseAjaxException("-1", e.getMessage());
        }
    }

}
