package com.orient.dsrestful.listener.updateSchema;

import com.orient.dsrestful.event.UpdateSchemaEvent;
import com.orient.dsrestful.eventparam.UpdateSchemaParam;
import com.orient.metamodel.metadomain.Schema;
import com.orient.metamodel.metaengine.ErrorInfo;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import com.orient.utils.exception.OrientBaseAjaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by GNY on 2018/4/2
 */
@Component
public class UpdatePhysicalFieldListener extends OrientEventListener {

    @Autowired
    MetaDAOFactory metaDAOFactory;

    private boolean status = false;

    private UpdateSchemaParam param;

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
        param = (UpdateSchemaParam) orientEvent.getParams();
        Schema newSchema = param.getNewSchema();
        Schema oldSchema = param.getOldSchema();
        newSchema.setId(oldSchema.getId());
        //修改实际业务表的字段属性
        try {
            alterColumn(newSchema, param.getAlterColumnMap());
        } catch (Exception e) {
            e.printStackTrace();
            resume(param.getAlterColumnMap(), param.getAlterInfo());
            throw new OrientBaseAjaxException("-1", e.getMessage());
         /*   try {
                throw new Exception("数据库更新实际表字段类型长度发生异常，数据库已回滚到最近一次正常状态，请重新打开数据模型进行操作！");
            } catch (Exception e1) {
                e1.printStackTrace();
            }*/
        }
    }

    /**
     * 恢复被修改了的实际表的字段.
     */
    private void resume(Map<Integer, String> alterColumnMap, Map<String, String> alterInfo) {
        try {
            for (int i = 0; i < alterColumnMap.keySet().size(); i++) {
                String id = alterColumnMap.get(i);
                String[] columnInfo = alterInfo.get(id).split("==");
                String type = columnInfo[0];
                String length = columnInfo[1];
                List list = metaDAOFactory.getJdbcTemplate().queryForList("select S_COLUMN_NAME,TABLE_ID from CWM_TAB_COLUMNS where id='" + id + "'");
                if (!list.isEmpty()) {
                    for (Iterator it = list.iterator(); it.hasNext(); ) {
                        Map map = (Map) it.next();
                        String tableId = (String) map.get("TABLE_ID");
                        int a = Integer.parseInt(length) * 2;
                        String columnName = (String) map.get("S_COLUMN_NAME");
                        String tableName = (String) ((Map) (metaDAOFactory.getJdbcTemplate().queryForList("select S_TABLE_NAME as c from CWM_TABLES WHERE ID='" + tableId + "' and IS_VALID=1").get(0))).get("c");
                        alter(a, columnName, type, tableName);
                        alterChildTableColumn(a, columnName, type, tableId);
                    }
                }
            }
            status = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改业务表字段的类型和长度等
     *
     * @param schema         数据模型
     * @param alterColumnMap
     * @throws Exception 异常信息
     */
    private void alterColumn(Schema schema, Map<Integer, String> alterColumnMap) throws Exception {
        try {
            status = true;
            List list = metaDAOFactory.getJdbcTemplate().queryForList("SELECT ID,DISPLAY_NAME,S_COLUMN_NAME,TABLE_ID,TYPE,MAX_LENGTH FROM CWM_TAB_COLUMNS WHERE IS_VALID=2");
            if (!list.isEmpty()) {
                verifyExistData(list);
                int i = 0;
                for (Iterator it = list.iterator(); it.hasNext(); i++) {
                    Map map = (Map) it.next();
                    String tableId = (String) map.get("TABLE_ID");
                    int a = ((BigDecimal) map.get("MAX_LENGTH")).intValue() * 2;
                    String columnName = (String) map.get("S_COLUMN_NAME");
                    String oldType = (String) map.get("TYPE");
                    String tableName = (String) ((Map) (metaDAOFactory.getJdbcTemplate().queryForList("SELECT S_TABLE_NAME AS C FROM CWM_TABLES WHERE ID='" + tableId + "' and IS_VALID=1").get(0))).get("c");
                    alter(a, columnName, oldType, tableName);
                    alterChildTableColumn(a, columnName, oldType, tableId);
                    alterColumnMap.put(i, map.get("ID").toString());//记录修改字段的记录
                }
            }
            String updateSignSql = "UPDATE CWM_TAB_COLUMNS SET IS_VALID=1 WHERE IS_VALID=2";
            metaDAOFactory.getJdbcTemplate().execute(updateSignSql);
        } catch (Exception e) {
            e.printStackTrace();
            resume(param.getAlterColumnMap(), param.getAlterInfo());
            throw e;
        }
    }

    /**
     * 修改字段在实际表中的类型、长度等信息.
     *
     * @param a          字段长度
     * @param columnName 字段实际名称
     * @param type       字段类型
     * @param tableName  实际表名称
     * @throws Exception
     */
    private void alter(int a, String columnName, String type, String tableName) throws Exception {
        try {
            List list = metaDAOFactory.getJdbcTemplate().queryForList("SELECT * FROM USER_TABLES WHERE TABLE_NAME ='" + tableName.toUpperCase() + "'");
            if (!list.isEmpty()) {
                StringBuffer alterSql = new StringBuffer();
                alterSql.append(" alter table ")
                        .append(tableName)
                        .append(" modify (")
                        .append(columnName);
                if (type.equals("String")) {
                    alterSql.append(" varchar2")
                            .append("(")
                            .append(a)
                            .append("))");
                } else if (type.equals("Byte")) {
                    alterSql.append(" varchar2(1))");
                } else if (type.equals("ODS") || type.equals("Hadoop")) {
                    alterSql.append(" varchar2(400))");
                } else if (type.equals("Boolean")) {
                    alterSql.append(" number(1,0))");
                } else if (type.equals("Date")) {
                    alterSql.append(" timestamp(6))");
                } else if (type.equals("DateTime")) {
                    alterSql.append(" timestamp)");
                } else if (type.equals("Float") || type.equals("Double")) {
                    alterSql.append(" float)");
                } else if (type.equals("Integer")) {
                    alterSql.append(" number(10,0))");
                } else if (type.equals("BigInteger")) {
                    alterSql.append(" number(19,0))");
                } else if (type.equals("Text") || type.equals("Check")) {
                    StringBuffer dropSql = new StringBuffer();
                    dropSql.append(" alter table ")
                            .append(tableName)
                            .append(" drop (")
                            .append(columnName)
                            .append(")");
                    metaDAOFactory.getJdbcTemplate().execute(dropSql.toString());
                    StringBuffer aSql = new StringBuffer();
                    aSql.append(" alter table ").append(tableName)
                            .append(" add (")
                            .append(columnName)
                            .append(" clob")
                            .append(")");
                    metaDAOFactory.getJdbcTemplate().execute(aSql.toString());
                } else if (type.equals("Decimal")) {
                    alterSql.append(" number(30,10))");
                }
                if (!type.equals("Text")) {
                    metaDAOFactory.getJdbcTemplate().execute(alterSql.toString());
                }
            }
        } catch (Exception e) {
            resume(param.getAlterColumnMap(), param.getAlterInfo());
            throw e;
        }
    }

    /**
     * 修改子数据类中该字段的数据类型、长度等信息.
     *
     * @param a          字段长度
     * @param columnName 字段实际名称
     * @param type       字段类型
     * @param tableId    数据类id
     * @throws Exception 异常信息
     */
    private void alterChildTableColumn(final int a, final String columnName, final String type, String tableId) throws Exception {
        try {
            List list = metaDAOFactory.getJdbcTemplate().queryForList("SELECT ID AS a,S_TABLE_NAME AS b FROM CWM_TABLES WHERE PID ='" + tableId + "' AND IS_VALID = 1");
            if (!list.isEmpty()) {
                for (Iterator it = list.iterator(); it.hasNext(); ) {
                    Map map = (Map) it.next();
                    String name = (String) map.get("b");
                    String id = (String) map.get("a");
                    if (name != null) {
                        alter(a, columnName, type, name);
                    }
                    if (id != null) {
                        alterChildTableColumn(a, columnName, type, id);
                    }
                }
            }
        } catch (Exception e) {
            resume(param.getAlterColumnMap(), param.getAlterInfo());
            throw e;
        }
    }

    /**
     * 校验该字段在实际表中是否存在数据，若不存在数据，则可以修改该字段的类型长度等.
     *
     * @param list 要修改的字段属性集合
     * @throws Exception 异常信息
     */
    private void verifyExistData(List list) throws Exception {
        ErrorInfo info = new ErrorInfo();
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            Map map = (Map) it.next();
            String tableId = (String) map.get("TABLE_ID");
            String columnName = (String) map.get("S_COLUMN_NAME");
            String cdn = (String) map.get("DISPLAY_NAME");
            List tableList = metaDAOFactory.getJdbcTemplate().queryForList("select S_TABLE_NAME as c ,DISPLAY_NAME as b from CWM_TABLES WHERE ID='" + tableId + "' and IS_VALID=1");
            String tableName = (String) ((Map) tableList.get(0)).get("c");
            String tdn = (String) ((Map) tableList.get(0)).get("c");
            List a = metaDAOFactory.getJdbcTemplate().queryForList("SELECT * FROM user_tables where table_name='" + tableName.toUpperCase() + "'");
            if (!a.isEmpty()) {
                if (metaDAOFactory.getJdbcTemplate().queryForObject("SELECT count(*) A FROM " + tableName + " WHERE " + columnName + " IS NOT NULL", Integer.class) != 0) {
                    info.ErrorId = 1;
                    info.Warning = "数据表'" + tdn + "(" + tableName + ")'的字段'" + cdn + "(" + columnName + ")'含有数据，无法修改该字段的类型或长度，修改保存失败";
                }
            }
            verifyChild(columnName, cdn, tableId);
            if (info.ErrorId != 0) throw new Exception(info.Warning);
        }
    }

    /**
     * 校验该字段在子数据类的实际表中是否存在数据.
     *
     * @param columnName 实际字段名称
     * @param cdn        字段显示名
     * @param tableId    父数据类id
     * @throws Exception 异常信息
     */
    private void verifyChild(String columnName, String cdn, String tableId) throws Exception {
        List list = metaDAOFactory.getJdbcTemplate()
                .queryForList("SELECT ID AS a,S_TABLE_NAME AS b,DISPLAY_NAME AS c FROM CWM_TABLES WHERE PID='" + tableId + "' AND IS_VALID=1");
        if (!list.isEmpty()) {
            for (Iterator it = list.iterator(); it.hasNext(); ) {
                Map map = (Map) it.next();
                String name = (String) map.get("b");
                String tdn = (String) map.get("c");
                String id = (String) map.get("a");
                if (name != null) {
                    List a = metaDAOFactory.getJdbcTemplate().queryForList("SELECT * FROM user_tables WHERE table_name='" + name.toUpperCase() + "'");
                    if (!a.isEmpty()) {
                        if (metaDAOFactory.getJdbcTemplate().queryForObject("SELECT COUNT(*) A FROM " + name + " WHERE " + columnName + " IS NOT NULL", Integer.class) != 0) {
                            String errorMsg = "数据表'" + tdn + "(" + name + ")'的字段'" + cdn + "(" + columnName + ")'含有数据，无法修改该字段的类型或长度，修改保存失败";
                            throw new Exception(errorMsg);
                        }
                    }
                }
                if (id != null) {
                    verifyChild(columnName, cdn, id);
                }
            }
        }
    }

}
