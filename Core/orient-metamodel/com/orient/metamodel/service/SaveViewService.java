package com.orient.metamodel.service;

import com.orient.metamodel.DBUtil;
import com.orient.metamodel.metadomain.*;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * ${DESCRIPTION}
 *
 * @author GNY
 * @create 2018-03-28 10:33
 */
@Service
public class SaveViewService extends DBUtil {

    private static final int INTERVAL_TIME = 1;

    /**
     * 数据视图的保存
     *
     * @param schema 数据模型对象
     */
    public void saveView(Schema schema, Map<String, Column> columnMap, Map<Integer, String> createViewSqlMap) throws Exception {
        Map<String, View> viewMap = new HashMap<>();              //所有的视图<Identity, View实例>
        Map<View, Integer> intervalViewMap = new HashMap<>();      //统计视图刷新时间的MAP
        //遍历schema的数据视图，保存数据视图
        for (View view : schema.getViews()) {
            view.setViewName(view.getName() + "_" + schema.getId());
            //设置View 的Expression
            if (view.getExpression() != null && !view.getExpression().isEmpty()) {
                String expression = this.convertColumnIdentity2ColumnName(view.getExpression(), columnMap);
                view.setExpression(expression);
            }
            viewMap.put(view.getIdentity(), view);
            metaDAOFactory.getViewDAO().save(view);// 保存视图
        }
        //设置统计视图被引用到的次数
        schema.getViews().forEach(view -> {
            if (view.getType().equals(View.TPYE_ARITH) && !intervalViewMap.containsKey(view)) {
                saveIntervalView(view, intervalViewMap, viewMap);
            }
        });
        Set<Column> arithColumnFromViewSet = new HashSet<>();                   //所有统计属性
        for (View view : schema.getViews()) {
            saveArithColumnForView(view, arithColumnFromViewSet, columnMap);    //保存统计视图的统计属性
            saveCanshuxiang(view);                                              //保存视图的统计参数项
            saveViewSql(view, schema, intervalViewMap, createViewSqlMap);       //保存生成视图的SQL语句
        }
        for (Column arithColumn : arithColumnFromViewSet) {          //保存视图统计属性所使用的参数
            String Id = arithColumn.getId();
            if (Id.equalsIgnoreCase("")) {
                assert (false);
            }
            saveArithColumnAttributeForView(arithColumn);            //确保所有的Column已经有Id了
        }
        metaDAOFactory.getHibernateTemplate().flush();
        // 遍历schema的数据视图，保存数据视图的其他属性
        for (View view : schema.getViews()) {
            saveViewAttribute(view, viewMap);                    //视图返回属性、关联数据类以及排序属性信息的保存.
            if (view.getType().equals(View.TPYE_ARITH)) {        //如果是统计属性
                List<String> refList = new ArrayList<>();        //引用了该视图和该视图引用的视图
                refList.add(view.getId());                       //自生也是引用的view
                addRefView(view, schema, refList);
                StringBuffer sb = new StringBuffer();
                if (!refList.isEmpty()) {
                    for (String str : refList) {
                        sb.append(str).append(",");
                    }
                    view.setRefviewid(sb.substring(0, sb.length() - 1));
                }
            }
            metaDAOFactory.getViewDAO().merge(view);//保存视图
        }
        metaDAOFactory.getHibernateTemplate().flush();
    }

    /**
     * 创建/更新数据统计视图在数据库中的实际物化视图
     *
     * @param isUpdate 是更新还是直接保存，1为更新，即删除原有物化视图，重新生产物化视图
     * @return boolean
     */
    public boolean createPhysicalView(boolean isUpdate, Map<Integer, String> createViewSqlMap) {
        if (createViewSqlMap.size() > 0) {
            for (Iterator iter = createViewSqlMap.keySet().iterator(); iter.hasNext(); ) {
                Integer iloop = (Integer) iter.next();
                String str = createViewSqlMap.get(iloop);
                String[] string = str.split("===");
                String viewName = string[0];//视图名称
                String sql = string[1];     //SQL
                if (isUpdate) {
                    BasicDataSource ds = (BasicDataSource) metaDAOFactory.getJdbcTemplate().getDataSource();
                    String name = ds.getUsername().toUpperCase();//数据库用户名称
                    List<Map<String, Object>> viewList = metaDAOFactory.getJdbcTemplate()
                            .queryForList("SELECT * FROM DBA_MVIEWS WHERE MVIEW_NAME='" + viewName.toUpperCase() + "' AND OWNER = '" + name + "'");
                    if (!viewList.isEmpty()) {
                        metaDAOFactory.getJdbcTemplate().execute("DROP MATERIALIZED VIEW " + viewName.toUpperCase() + "");
                    }
                }
                metaDAOFactory.getJdbcTemplate().execute(sql);
                metaDAOFactory.getHibernateTemplate().flush();
            }
        }
        return true;
    }

    /**
     * 统计了统计视图（物化表）被引用的次数
     *
     * @param view
     * @param intervalViewMap
     * @param viewMap
     */
    public void saveIntervalView(View view, Map<View, Integer> intervalViewMap, Map<String, View> viewMap) {
        //存在关联表
        if (!view.getCwmViewRelationtables().isEmpty()) {

            for (Iterator rtit = view.getCwmViewRelationtables().keySet().iterator(); rtit.hasNext(); ) {
                String order_refTableIdentity = (String) rtit.next();
                if (order_refTableIdentity.indexOf("====") > 0) {
                    //关联了数据统计视图
                    String order = order_refTableIdentity.split("====")[0];
                    String refViewIdentity = order_refTableIdentity.split("====")[1];
                    if (intervalViewMap.containsKey(viewMap.get(refViewIdentity))) {
                        int pre = intervalViewMap.get(viewMap.get(refViewIdentity));
                        intervalViewMap.put(view, pre + INTERVAL_TIME);
                    } else {
                        saveIntervalView(viewMap.get(refViewIdentity), intervalViewMap, viewMap);
                        int pre = intervalViewMap.get(viewMap.get(refViewIdentity));
                        intervalViewMap.put(view, pre + INTERVAL_TIME);
                    }
                } else {
                    //引用到了数据类
                }
            }
            if (!intervalViewMap.containsKey(view)) {
                intervalViewMap.put(view, INTERVAL_TIME);
            }
        } else {
            intervalViewMap.put(view, INTERVAL_TIME);
        }
    }

    /**
     * 将xml中描述的column转化为数据库的字段名称
     *
     * @param containIdentity 要替换的字符串		//[columnIdentity] 运算符 字段值 逻辑运算符
     * @param columnMap       目前所有的数据库字段
     * @return String         替换过后的字符串		//[CWM_TAB_COLUMN中的S_NAME] 运算符 字段值 逻辑运算符
     * @throws
     */
    public String convertColumnIdentity2ColumnName(String containIdentity, Map<String, Column> columnMap) {
        Iterator itor = columnMap.keySet().iterator();
        while (itor.hasNext()) {
            String columnIdentity = (String) itor.next();
            Column loopColumn = columnMap.get(columnIdentity);
            //得到业务表中的字段名称
            if (containIdentity.indexOf("[" + columnIdentity + "]") >= 0) {
                if (loopColumn.getCategory().equals(new Long(1))) {
                    //普通属性
                    containIdentity = containIdentity.replaceAll("\\[" + columnIdentity + "\\]", loopColumn.getColumnName());
                } else {
                    //目前不能选择关系属性作为过滤表达式的字段。貌似不会跑到这里
                    //关系属性或者统计属性
                    //关系属性的字段为  关系表名称_ID  先得到关系表名称，再拼接
                    String refTableName = (String) ((Map) metaDAOFactory.getJdbcTemplate().queryForList("SELECT S_TABLE_NAME FROM CWM_TABLES WHERE id=(SELECT REF_TABLE_ID FROM CWM_RELATION_COLUMNS WHERE COLUMN_ID='" + loopColumn.getId() + "')")
                            .get(0)).get("S_TABLE_NAME");
                    containIdentity = containIdentity.replaceAll("\\[" + columnIdentity + "\\]", loopColumn.getTable().getTableName() + "." + refTableName + "_ID");
                }
            }
        }
        return containIdentity;
    }

    /**
     * 统计视图的统计属性的保存.
     *
     * @param view                   -- 统计视图对象
     * @param arithColumnFromViewSet --统计视图的统计属性集合
     * @throws Exception
     */
    public void saveArithColumnForView(View view, Set<Column> arithColumnFromViewSet, Map<String, Column> columnMap) throws Exception {
        if (!view.getColumns().isEmpty()) {
            for (Column column : view.getColumns()) {
                column.setView(view);
                String identityName = column.getIdentity();
                String cName = column.getName();
                //这里只需要保存统计视图的属性
                if (column.getCategory().equals(Column.CATEGORY_ARITH)) {
                    column.setName(cName);
                    if (column.getPurpose() == null || column.getPurpose().length() == 0) {
                        column.setPurpose("新增,修改,详细,列表");
                    }
                    column.setColumnName(column.getName() + "_" + view.getId());
                    columnMap.put(identityName, column);
                    arithColumnFromViewSet.add(column);
                    metaDAOFactory.getColumnDAO().save(column);//保存视图的统计属性到CWM_TAB_COLUMNS
                }
            }
        }
    }

    /**
     * 统计视图的统计参数项的保存
     *
     * @param view 统计视图对象
     */
    public void saveCanshuxiang(View view) throws Exception {
        if (!view.getCanshuxiang().isEmpty()) {
            for (ArithViewAttribute ava : view.getCanshuxiang()) {
                ava.setView(view);
                metaDAOFactory.getArithViewAttributeDAO().save(ava);
            }
        }
    }

    /**
     * 生成数据视图的SQL语句的方法
     *
     * @param view            当前视图的对象
     * @param schema          当前模型的对象
     * @param intervalViewMap
     */
    public void saveViewSql(View view, Schema schema, Map<View, Integer> intervalViewMap, Map<Integer, String> createViewSqlMap) {
        String expression = view.getExpression();
        StringBuffer relation = new StringBuffer();        // 关联数据类的关系字符串
        boolean bl = false;                                //是否有多对多的数据类关联，如果有，则需加入数据表CWM_RELATION_DATA
        boolean isRefView = false;                         //关联的数据类是否有统计视图
        /*
         * 0表示关联的统计视图并非多对多关联
		 * 1、2都表示关联的统计视图是多对多关联
		 * 1表示group by的字段是 MAIN_DATA_ID，2表示group by的字段时SUB_DATA_ID
		 * */
        int isMultiRefView = 0;
        /*
         * 0表示关联的不是一对一或一对多关联统计视图
		 * 1、2都表示关联的视图是一对一、一对多或多对一的统计视图
		 * 1表示group by的字段是引用关联表的表名_id，2表示group by的字段是当前数据视图的主数据类的表名_id
		 * */
        int isSimRefView = 0;
        String refColumn = null;
        int jointype = view.getJoinType().intValue();        //视图连接类型 :0为内连接，1为左连接，2为右连接，3为全连接
        //遍历数据类关联明细，将关联明细拼凑成相应的表达式供组合视图SQL语句使用
        for (Iterator relationIt = view.getTabledetailSet().iterator(); relationIt.hasNext(); ) {

            RelationDetail rd = (RelationDetail) relationIt.next();                   //取得的关联明细实例
            if (rd.getViewName() == null || rd.getViewName().trim().length() == 0) {  //关联的不是统计视图，仅仅是数据类

                if (rd.getType().equals("0")) {
                    //type为0,表示数据类A和数据类B之间是A.id=B.A_id;(A是leftTable也就是关系所在的表，B是rightTable)
                    relation.append(" and ").append(rd.getFromTable().getTableName()).append(".ID=")
                            .append(rd.getToTable().getTableName()).append(".").append(rd.getRfromTable().getTableName()).append("_ID");
                } else if (rd.getType().equals("1")) {
                    //type为1,表示数据类A和数据类B之间是A.B_id=B.id;
                    relation.append(" and ").append(rd.getToTable().getTableName()).append(".ID=")
                            .append(rd.getFromTable().getTableName()).append(".").append(rd.getRtoTable().getTableName()).append("_ID");
                } else if (rd.getType().equals("2")) {
                    //type为2,表示两个数据类(A,B)之间的关系是多对多
                    //c1.main_table_name = A.NAME and c1.main_data_id=A.id and c1.sub_table_name = B.NAME and c1.sub_data_id = B.ID
                    bl = true;
                    relation.append(" and ")
                            .append("CWM_RELATION_DATA.MAIN_TABLE_NAME='")
                            .append(rd.getToTable().getTableName())
                            .append("' and ")
                            .append("CWM_RELATION_DATA.MAIN_DATA_ID=")
                            .append(rd.getToTable().getTableName())
                            .append(".ID")
                            .append(" and ")
                            .append("CWM_RELATION_DATA.SUB_TABLE_NAME='")
                            .append(rd.getFromTable().getTableName())
                            .append("' and ")
                            .append("CWM_RELATION_DATA.SUB_DATA_ID=")
                            .append(rd.getFromTable().getTableName())
                            .append(".ID");
                } else if (rd.getType().equals("3")) {
                    //和2相反
                    bl = true;
                    relation.append(" and ")
                            .append("CWM_RELATION_DATA.MAIN_TABLE_NAME='")
                            .append(rd.getFromTable().getTableName())
                            .append("' and ")
                            .append("CWM_RELATION_DATA.MAIN_DATA_ID=")
                            .append(rd.getFromTable().getTableName())
                            .append(".ID").append(" and ").append("CWM_RELATION_DATA.SUB_TABLE_NAME='")
                            .append(rd.getToTable().getTableName())
                            .append("' and ").append("CWM_RELATION_DATA.SUB_DATA_ID=")
                            .append(rd.getToTable().getTableName())
                            .append(".ID");
                }

            } else if (rd.getViewName() != null) {       //关联的是数据统计视图
                isRefView = true;
                String vname;                    //取得统计视图的名称
                if (rd.getViewName().contains("view=")) {
                    vname = rd.getViewName().substring(5) + "_" + schema.getId();
                } else {
                    vname = rd.getViewName() + "_" + schema.getId();
                }
                if (rd.getType().equals("0")) {
                    //type为0,表示数据类A和数据类B之间是A.id=B.A_id;(A是leftTable，B是rightTable)
                    isSimRefView = 1;    //1表示group by的字段是引用关联表的表名_id，即一的关系在A表
                    relation.append(" and ")
                            .append(rd.getFromTable().getTableName())
                            .append(".ID=")
                            .append(vname)
                            .append(".")
                            .append(rd.getRfromTable().getTableName())
                            .append("_ID");
                    refColumn = vname.toUpperCase() + "." + rd.getRfromTable().getTableName().toUpperCase() + "_ID";
                } else if (rd.getType().equals("1")) {
                    //type为1,表示数据类A和数据类B之间是A.B_id=B.id;
                    isSimRefView = 2;
                    relation.append(" and ")
                            .append(vname)
                            .append(".ID=")
                            .append(rd.getFromTable().getTableName())
                            .append(".")
                            .append(rd.getRtoTable().getTableName())
                            .append("_ID");
                    refColumn = rd.getFromTable().getTableName().toUpperCase() + "." + rd.getRtoTable().getTableName().toUpperCase() + "_ID";
                } else if (rd.getType().equals("3")) {
                    //和2相反
                    isMultiRefView = 1;
                    bl = true;
                    relation.append(" and ")
                            .append("CWM_RELATION_DATA.MAIN_TABLE_NAME='")
                            .append(rd.getFromTable().getTableName())
                            .append("' and ")
                            .append("CWM_RELATION_DATA.MAIN_DATA_ID=")
                            .append(rd.getFromTable().getTableName())
                            .append(".ID")
                            .append(" and ")
                            .append("CWM_RELATION_DATA.SUB_TABLE_NAME='")
                            .append(rd.getToTable().getTableName())
                            .append("' and ")
                            .append("CWM_RELATION_DATA.SUB_DATA_ID=")
                            .append(vname)
                            .append(".ID");
                    refColumn = "CWM_RELATION_DATA.MAIN_DATA_ID";
                } else if (rd.getType().equals("2")) {
                    //type为2,表示两个数据类(A,B)之间的关系是多对多
                    //c1.main_table_name = A.NAME and c1.main_data_id=A.id and c1.sub_table_name = B.NAME and c1.sub_data_id = B.ID
                    isMultiRefView = 2;
                    bl = true;
                    relation.append(" and ")
                            .append("CWM_RELATION_DATA.MAIN_TABLE_NAME='")
                            .append(rd.getToTable().getTableName())
                            .append("' and ")
                            .append("CWM_RELATION_DATA.MAIN_DATA_ID=")
                            .append(vname)
                            .append(".ID")
                            .append(" and ")
                            .append("CWM_RELATION_DATA.SUB_TABLE_NAME='")
                            .append(rd.getFromTable().getTableName())
                            .append("' and ")
                            .append("CWM_RELATION_DATA.SUB_DATA_ID=")
                            .append(rd.getFromTable().getTableName())
                            .append(".ID");
                    refColumn = "CWM_RELATION_DATA.SUB_DATA_ID";
                }
            }
        }
        StringBuffer tableList = new StringBuffer();//数据类的集合，from之后的实际表或统计视图的名称的字符串
        int c = 0;
        /**此map为记录查询语句from后的所有数据类或实体视图，key为Table或View，value为相应的名称*/
        Map<BaseMetaBean, String> bbMap = new HashMap<>();
        // 遍历视图的关联数据类或关联统计视图
        for (Iterator rtit = view.getCwmViewRelationtables().keySet().iterator(); rtit.hasNext(); ) {
            String str = (String) rtit.next();
            if (str.indexOf("====") > 0) {
                //统计视图
                String x = str.split("====")[0];
                String viewName = str.split("====")[1];
                String vname;
                if (viewName.contains("view=")) {
                    vname = viewName.substring(5) + "_" + schema.getId();
                } else {
                    vname = viewName + "_" + schema.getId();
                }
                tableList.append(vname).append(",");
            } else {//数据类
                Table table = (Table) view.getCwmViewRelationtables().get(str);
                tableList.append(table.getTableName()).append(",");
                bbMap.put(table, table.getTableName());
            }
        }
        for (Iterator rfvit = view.getRefviewSet().iterator(); rfvit.hasNext(); ) {
            View rfv = (View) rfvit.next();
            String vname;
            if (rfv.getName().contains("view=")) {
                vname = rfv.getName().substring(5) + "_" + schema.getId();
            } else {
                vname = rfv.getName() + "_" + schema.getId();
            }
            bbMap.put(rfv, vname);
        }
        if (bl) {
            //含有多对多关联的数据视图则需添加数据表CWM_RELATION_DATA
            tableList.append("CWM_RELATION_DATA,");
        }
        String tableStr = tableList.toString();
        StringBuffer resultColumnList = new StringBuffer();//返回属性的字符串，包含视图主数据类的ID和外键ID
        /*为了统计属性为自定义统计属性时使用，当返回属性为统计属性，并且该统计属性的算法是自定义算法时，
         * 保存在物化视图中的字段不为统计属性字段，而是该统计属性参数的字段集合*/
        resultColumnList.append(view.getTable().getTableName().toUpperCase()).append(".ID,");//视图主数据类的主键ID
        resultColumnList.append(view.getTable().getTableName().toUpperCase()).append(".SYS_DATE_TIME,");//视图主数据类的系统时间字段
        resultColumnList.append(view.getTable().getTableName().toUpperCase()).append(".SYS_USERNAME,");//视图主数据类的用户字段
        resultColumnList.append(view.getTable().getTableName().toUpperCase()).append(".SYS_IS_DELETE,");//视图主数据类的是否删除字段
        resultColumnList.append(view.getTable().getTableName().toUpperCase()).append(".SYS_SECRECY,");//视图主数据类的密级信息字段
        resultColumnList.append(view.getTable().getTableName().toUpperCase()).append(".SYS_SCHEMA,");//视图主数据类的数据所在数据模型
        resultColumnList.append(view.getTable().getTableName().toUpperCase()).append(".SYS_OPERATE,");//视图主数据类的数据操作权限
        resultColumnList.append(view.getTable().getTableName().toUpperCase()).append(".SYS_FLOW,");//视图主数据类的数据所属流程
        //多对多时，group by 的时候使用到了
        List<String> returnColumnList = new ArrayList<>();
        if (isRefView) {
            returnColumnList.add(refColumn);
            returnColumnList.add(view.getTable().getTableName().toUpperCase() + ".ID");
            returnColumnList.add(view.getTable().getTableName().toUpperCase() + ".SYS_DATE_TIME");
            returnColumnList.add(view.getTable().getTableName().toUpperCase() + ".SYS_USERNAME");
            returnColumnList.add(view.getTable().getTableName().toUpperCase() + ".SYS_IS_DELETE");
            returnColumnList.add(view.getTable().getTableName().toUpperCase() + ".SYS_SECRECY");
            returnColumnList.add(view.getTable().getTableName().toUpperCase() + ".SYS_SCHEMA");
            returnColumnList.add(view.getTable().getTableName().toUpperCase() + ".SYS_OPERATE");
            returnColumnList.add(view.getTable().getTableName().toUpperCase() + ".SYS_FLOW");
        }
        //遍历视图主数据类的所有外键字段以及普通属性，（主数据类的属性都放在返回属性中）
        Set<String> columnSet = new HashSet<>();//返回属性名称的集合，确保名称的唯一
        for (int x = 0; x < view.getTable().getZxColumns().size(); x++) {
            Column column = (Column) view.getTable().getZxColumns().get(x);
            if (column.getCategory().equals(new Long(2))) {//关系属性
                if (columnSet.contains(column.getColumnName())) {//查询返回属性集合中是否已经存在该字段，存在则不添加
                    break;
                } else {
                    columnSet.add(column.getColumnName());
                }
                if (!column.getRelationColumn().getRelationtype().equals(new Long(4))) {
                    StringBuffer rc = new StringBuffer();
                    rc.append(view.getTable().getTableName()).append(".").append(column.getRelationColumn().getRefTable().
                            getTableName()).append("_id");
                    resultColumnList.append(rc.toString()).append(",");    //外键ID
                    if (isRefView && !returnColumnList.contains(rc.toString().toUpperCase())) {
                        returnColumnList.add(rc.toString().toUpperCase());    //外键ID
                    }
                }
            } else if (column.getCategory().equals(new Long(1))) {    //普通属性
                if (columnSet.contains(column.getColumnName())) {
                    //查询返回属性集合中是否已经存在该字段，存在则不添加
                    break;
                } else {
                    columnSet.add(column.getColumnName());
                }
                if (!view.getType().equals(new Long(2))) {//如果不是数据统计视图
                    if (column.getType().equals("Date")) {//字段为日期类型，进行如下转化
                        resultColumnList.append("to_char(")
                                .append(column.getColumnName())
                                .append(",'yyyy-mm-dd') ")
                                .append(column.getColumnName())
                                .append(",");
                    } else if (column.getType().equals("DateTime")) {//字段为时间类型，进行如下转化
                        resultColumnList.append("to_char(")
                                .append(column.getColumnName())
                                .append(",'yyyy-mm-dd hh24:mi:ss') ")
                                .append(column.getColumnName())
                                .append(",");
                    } else if (column.getType().equals("Boolean")) {//字段为布尔类型，进行如下转化
                        resultColumnList.append("decode(")
                                .append(column.getColumnName())
                                .append(",'0','False','1','True') ")
                                .append(column.getColumnName())
                                .append(",");
                    } else
                        resultColumnList.append(column.getColumnName()).append(",");
                } else {
                    resultColumnList.append(column.getColumnName()).append(",");
                }
                if (isRefView && !returnColumnList.contains(column.getColumnName().toUpperCase())) {
                    returnColumnList.add(column.getColumnName().toUpperCase());
                }
            }
        }
        int a = 0;    // 在DS中选择的返回属性
        for (a = 0; a < view.getCwmReturnViewColumns().size(); ) {
            Column column = (Column) view.getCwmReturnViewColumns().get(a);
            if (columnSet.contains(column.getColumnName())) {//查询返回属性集合中是否已经存在该字段，存在则不添加
                a++;
                continue;
            } else {
                columnSet.add(column.getColumnName());
            }
            if (column.getCategory().equals(new Long(2))) {
                //字段为关系属性
                String refTableName = (String) ((Map) metaDAOFactory.getJdbcTemplate().queryForList(
                        "select S_TABLE_NAME from CWM_TABLES where id=(select REF_TABLE_ID from CWM_RELATION_COLUMNS where COLUMN_ID='" + column.getId() + "')").get(0)).get("S_TABLE_NAME");
                String tn = null;
                //获得数据类或者View的名称
                for (Iterator tvit = bbMap.keySet().iterator(); tvit.hasNext(); ) {
                    BaseMetaBean metaBean = (BaseMetaBean) tvit.next();
                    if (metaBean instanceof Table) {
                        if (((Table) metaBean).getZxColumns().containsValue(column)) {
                            tn = bbMap.get(metaBean);
                            break;
                        }
                    } else if (metaBean instanceof View) {
                        if (((View) metaBean).getCwmReturnViewColumns().containsValue(column)) {
                            tn = bbMap.get(metaBean);
                            break;
                        }
                    }
                }
                StringBuffer rc = new StringBuffer();
                rc.append(tn).append(".").append(refTableName).append("_id");
                resultColumnList.append(rc.toString()).append(",");
                if (isRefView && !returnColumnList.contains(rc.toString().toUpperCase())) {
                    returnColumnList.add(rc.toString().toUpperCase());
                }
            } else if (column.getCategory().equals(new Long(1))) {//字段为普通属性
                if (!view.getType().equals(new Long(2))) {    //如果不是数据统计视图
                    if (column.getType().equals("Date")) {//字段为日期类型，进行如下转化
                        resultColumnList.append("to_char(")
                                .append(column.getColumnName())
                                .append(",'yyyy-mm-dd') ")
                                .append(column.getColumnName())
                                .append(",");
                    } else if (column.getType().equals("DateTime")) {//字段为时间类型，进行如下转化
                        resultColumnList.append("to_char(")
                                .append(column.getColumnName())
                                .append(",'yyyy-mm-dd hh24:mi:ss') ")
                                .append(column.getColumnName())
                                .append(",");
                    } else if (column.getType().equals("Boolean")) {//字段为布尔类型，进行如下转化
                        resultColumnList.append("decode(")
                                .append(column.getColumnName())
                                .append(",'0','False','1','True') ")
                                .append(column.getColumnName())
                                .append(",");
                    } else
                        resultColumnList.append(column.getColumnName()).append(",");
                } else {
                    resultColumnList.append(column.getColumnName()).append(",");
                }
                if (isRefView && !returnColumnList.contains(column.getColumnName().toUpperCase())) {
                    returnColumnList.add(column.getColumnName().toUpperCase());
                }
            } else if (column.getCategory().equals(new Long(3))) {    //字段为统计属性
                boolean stu = true;//统计属性不在引用的统计视图
                for (Iterator viewit = view.getRefviewSet().iterator(); viewit.hasNext(); ) {
                    View refView = (View) viewit.next();
                    if (refView.getCwmReturnViewColumns().containsValue(column)) {
                        resultColumnList.append(column.getColumnName()).append(",");
                        if (isRefView && !returnColumnList.contains(column.getColumnName().toUpperCase())) {
                            returnColumnList.add(column.getColumnName().toUpperCase());
                        }
                        stu = false;
                        break;
                    }
                }
                if (!stu) {
                    a++;
                    continue;
                }
                String arithMethod = column.getArithMethod();//取得统计属性的公式，只有数据库内置算法才有统计属性
                StringBuffer noneArith = new StringBuffer();//统计属性的字符创
                Set<ArithAttribute> list = column.getArithAttribute();//统计属性的参数集合
                Map<Integer, String> lmap = new HashMap<>();//统计属性的替代Map
                if (arithMethod.indexOf("=1=") > 0) {//限定参数的算法公式拼凑
                    for (Iterator arithit = list.iterator(); arithit.hasNext(); ) {
                        ArithAttribute aa = (ArithAttribute) arithit.next();
                        if (aa.getColumn() != null) {//如果参数是字段，即变量参数
                            arithMethod = arithMethod.replaceAll("=" + aa.getOrder() + "=", aa.getColumn().getColumnName());
                        } else if (aa.getValue() != null) {//如果参数是字符串，即常量参数
                            if (aa.getType().equals("Number") || aa.getType().equals("Integer") ||
                                    aa.getType().equals("Float") || aa.getType().equals("Double") ||
                                    aa.getType().equals("BigInteger") || aa.getType().equals("Decimal") ||
                                    aa.getType().equals("")) {//如果常量是数字，则不添加单引号
                                arithMethod = arithMethod.replaceAll("=" + aa.getOrder() + "=", aa.getValue());
                            } else {//如果常量不是数字，则添加单引号
                                arithMethod = arithMethod.replaceAll("=" + aa.getOrder() + "=", "'" + aa.getValue() + "'");
                            }
                        }
                    }
                    resultColumnList.append(arithMethod).append(" ").append(column.getColumnName()).append(",");
                } else if (arithMethod != null) {//不限定参数的算法公式拼凑
                    for (Iterator arithit = list.iterator(); arithit.hasNext(); ) {
                        ArithAttribute aa = (ArithAttribute) arithit.next();
                        if (aa.getColumn() != null) {//如果参数是字段，即变量参数
                            lmap.put(aa.getOrder().intValue(), aa.getColumn().getColumnName());
                        } else if (aa.getValue() != null) {//如果参数是字符串，即常量参数
                            lmap.put(aa.getOrder().intValue(), aa.getValue());
                        }
                    }
                    for (int i = 1; i <= lmap.keySet().size(); i++) {//不限定参数的统计属性的替换
                        String str = lmap.get(i);
                        noneArith.append(str).append(arithMethod);
                    }
                    resultColumnList.append(noneArith.substring(0, noneArith.length() - arithMethod.length())).append(" ").append(column.getColumnName()).append(",");
                } else if (arithMethod == null) {//返回属性为统计属性，并且该统计属性的算法是自定义算法时，保存在物化视图中的字段不为统计属性字段，而是该统计属性参数的字段集合
                    returnColumnFromArith(resultColumnList, list, columnSet, isRefView, returnColumnList);
                }
            }
            a++;
        }
        String returnString = resultColumnList.toString();
        StringBuffer sortColumnList = new StringBuffer();//排序属性的字符串
        String order = " ASC";//排序顺序
        if (view.getPaixuFx().intValue() == 0) {
            order = " ASC";
        } else {
            order = " DESC";
        }
        String sortString = "";    //排序SQL
        if (view.getCwmPaixuViewColumns().size() != 0) {//排序属性sql语句的拼凑
            int m = 0;
            for (; m < view.getCwmPaixuViewColumns().size(); ) {
                Column column = (Column) view.getCwmPaixuViewColumns().get(m);
                if (column.getCategory().equals(new Long(2))) {
                    String tableName = (String) ((Map) metaDAOFactory.getJdbcTemplate().queryForList(
                            "SELECT S_TABLE_NAME FROM CWM_TABLES WHERE id=(SELECT REF_TABLE_ID FROM CWM_RELATION_COLUMNS WHERE COLUMN_ID='"
                                    + column.getId() + "')").get(0)).get("S_TABLE_NAME");
                    String tn = null;
                    //查找关系字段所属的数据类或视图
                    for (Iterator tvit = bbMap.keySet().iterator(); tvit.hasNext(); ) {
                        BaseMetaBean bb = (BaseMetaBean) tvit.next();
                        if (bb instanceof Table) {
                            if (((Table) bb).getZxColumns().containsValue(column)) {
                                tn = bbMap.get(bb);
                                break;
                            }
                        } else if (bb instanceof View) {
                            if (((View) bb).getCwmReturnViewColumns().containsValue(column)) {
                                tn = bbMap.get(bb);
                                break;
                            }
                        }
                    }
                    sortColumnList.append(tn).append(".").append(tableName).append("_id ").append(order).append(",");
                } else {
                    sortColumnList.append(column.getColumnName()).append(order).append(",");
                }
                m++;
            }
            sortString = sortColumnList.toString();
        }
        StringBuffer viewSql = new StringBuffer();//视图的sql字符串
        if (view.getType().equals(new Long(2))) {//如果是统计视图，添加如下字符串，以创建物化视图
            if (intervalViewMap.get(view) == INTERVAL_TIME) {
                viewSql.append("CREATE MATERIALIZED VIEW " + view.getViewName() + " BUILD DEFERRED REFRESH FORCE ON DEMAND START WITH SYSDATE NEXT SYSDATE + 1/144 AS ");
            } else {
                viewSql.append("CREATE MATERIALIZED VIEW " + view.getViewName() + " BUILD DEFERRED REFRESH FORCE ON DEMAND START WITH SYSDATE+" + (intervalViewMap.get(view) - INTERVAL_TIME) + "*1/1440 NEXT SYSDATE + 1/144 AS ");
            }
        }
        viewSql.append("select ")
                .append(returnString.substring(0, returnString.length() - 1))
                .append(" from ")
                .append(tableStr.substring(0, tableStr.length() - 1));
        if (expression != null && expression.length() > 0) {//有无查询表达式的sql语句拼凑
            viewSql.append(" where ").append(expression);
            if (relation.length() != 0) {
                viewSql.append(relation.toString());
            }
        } else {
            if (relation.length() != 0) {
                viewSql.append(" where 1=1 ").append(relation.toString());
            }
        }
        //GRUOP BY 组拼凑
        if (isRefView) {
            viewSql.append(" GROUP BY ");
            StringBuffer groupStr = new StringBuffer();
            for (String str : returnColumnList) {
                groupStr.append(str).append(",");
            }
            viewSql.append(groupStr.substring(0, groupStr.length() - 1));
        }
        if (view.getCwmPaixuViewColumns().size() != 0 && sortString.length() != 0) {//有无排序属性的sql语句拼凑
            viewSql.append(" order by ")
                    .append(sortString.substring(0, sortString.length() - 1));
        }
        view.setViewSql(viewSql.toString());
        if (view.getType().equals(new Long(2))) {//当视图为统计视图时，将视图SQL语句放到createViewSql的Map中，以便后来生成物化视图时使用
            createViewSqlMap.put(view.getOrder().intValue(), view.getViewName() + "===" + viewSql.toString());
        }
    }

    /**
     * 视图的统计属性的参数定义的保存.这个必须是所有的统计属性都已经有获取了Id了才能调用。
     *
     * @param column --统计视图的实例
     * @return void
     * @throws Exception
     */
    public void saveArithColumnAttributeForView(Column column) throws Exception {
        if (!column.getArithAttribute().isEmpty()) {
            for (ArithAttribute aa : column.getArithAttribute()) {
                assert (!aa.getColumn().getId().equals(""));
                aa.setAcolumn(column);
                metaDAOFactory.getArithAttributeDAO().save(aa);
            }
        }
    }

    /**
     * 视图返回属性、关联数据类以及排序属性信息的保存.
     *
     * @param view    --视图
     * @param viewMap -- 视图Map，引用视图的时候可以快速取得引用的试图
     * @return void
     * @throws Exception
     */
    public void saveViewAttribute(View view, Map<String, View> viewMap) throws Exception {
        //保存数据视图的关联数据类

        if (!view.getCwmViewRelationtables().isEmpty()) {
            // 遍历视图的关联数据类，保存其关联数据类
            Set<ViewRefColumn> viewRefTables = new HashSet<>();
            for (Iterator rtit = view.getCwmViewRelationtables().keySet().iterator(); rtit.hasNext(); ) {
                String str = (String) rtit.next();
                ViewRefColumn viewRefTable = new ViewRefColumn();    //数据视图的关联数据类信息表
                if (str.indexOf("====") > 0) {//如果是统计视图
                    assert (false);//目前没有引用统计视图
                    String x = str.split("====")[0];
                    String viewName = str.split("====")[1];
                    View refView = viewMap.get(viewName);
                    for (Iterator m = view.getTabledetailSet().iterator(); m.hasNext(); ) {
                        RelationDetail rd = (RelationDetail) m.next();
                        if (rd.getViewName() != null && rd.getViewName().equals(viewName)) {
                            viewRefTable.setOriginTable(rd.getFromTable());
                            viewRefTable.setFromTable(rd.getRfromTable());
                            viewRefTable.setToTable(rd.getRtoTable());
                            viewRefTable.setType(rd.getType());
                            viewRefTable.setViewId(view.getId());
                            viewRefTable.setCwmTables(rd.getToTable());
                            viewRefTable.setOrtView(refView.getId());
                            break;
                        }
                    }
                    viewRefTable.setOrder(new Long(Integer.valueOf(x)));
                } else {
                    Table refTable = (Table) view.getCwmViewRelationtables().get(str);
                    for (Iterator m = view.getTabledetailSet().iterator(); m.hasNext(); ) {
                        RelationDetail rd = (RelationDetail) m.next();
                        if (rd.getViewName() == null && rd.getToTable().equals(refTable)) {
                            viewRefTable.setOriginTable(rd.getFromTable());
                            viewRefTable.setFromTable(rd.getRfromTable());
                            viewRefTable.setToTable(rd.getRtoTable());
                            viewRefTable.setType(rd.getType());
                            viewRefTable.setViewId(view.getId());
                            viewRefTable.setCwmTables(refTable);
                            viewRefTable.setOrtView(null);
                            break;
                        }
                    }
                    viewRefTable.setOrder(new Long(Integer.valueOf(str)));
                }
                metaDAOFactory.getViewRefColumnDAO().save(viewRefTable);// 保存关联数据类
                viewRefTables.add(viewRefTable);
            }
            view.setViewRelationTables(viewRefTables);
        }
        Set<ViewResultColumn> viewResultColumns = new HashSet<>();
        for (int x = 0; x < view.getCwmReturnViewColumns().size(); x++) {// 遍历视图的返回属性并保存
            ViewResultColumn viewResultColumn = new ViewResultColumn();
            viewResultColumn.setCwmViews(view);
            viewResultColumn.setCwmTabColumns((Column) view.getCwmReturnViewColumns().get(x));
            viewResultColumn.setOrder(new Long(x));
            metaDAOFactory.getViewResultColumnDAO().save(viewResultColumn);// 保存返回属性
            viewResultColumns.add(viewResultColumn);
        }
        view.setReturnColumns(viewResultColumns);
        if (view.getCwmPaixuViewColumns().size() != 0) {
            Set<ViewSortColumn> viewSortColumns = new HashSet<>();
            for (int y = 0; y < view.getCwmPaixuViewColumns().size(); y++) {// 遍历视图的排序属性并保存
                ViewSortColumn viewSortColumn = new ViewSortColumn();
                viewSortColumn.setCwmViews(view);
                viewSortColumn.setCwmTabColumns((Column) view.getCwmPaixuViewColumns().get(y));
                viewSortColumn.setOrder(new Long(y));
                metaDAOFactory.getViewSortColumnDAO().save(viewSortColumn);// 保存排序属性
                viewSortColumns.add(viewSortColumn);
            }
            view.setPaixuColumns(viewSortColumns);
        }
    }

    /**
     * 返回属性为统计属性，并且该统计属性的算法是自定义算法时，保存在物化视图中的字段不为统计属性字段，而是该统计属性参数的字段集合.
     *
     * @param resultColumnList --返回属性的StringBuffer字符串
     * @param list             --统计属性的参数列表
     * @param columnset        --返回属性集合
     * @param isRefView        --是否是被引用视图
     * @param returnColumnList --返回属性名称集合
     * @return void
     * @throws
     */
    private void returnColumnFromArith(StringBuffer resultColumnList, Set<ArithAttribute> list, Set<String> columnset, Boolean isRefView, List<String> returnColumnList) {
        //遍历统计属性的参数列表
        for (Iterator arithit = list.iterator(); arithit.hasNext(); ) {
            ArithAttribute aa = (ArithAttribute) arithit.next();
            if (aa.getColumn() != null) {//参数为字段时才添加到返回属性字符串中
                if (columnset.contains(aa.getColumn().getColumnName())) {//查询返回属性集合中是否已经存在该字段，存在则不添加
                    break;
                } else {
                    columnset.add(aa.getColumn().getColumnName());
                }
                if (!aa.getColumn().getCategory().equals(new Long(3))) {//参数字段是普通属性
                    resultColumnList.append(aa.getColumn().getColumnName()).append(",");
                    if (isRefView && !returnColumnList.contains(aa.getColumn().getColumnName().toUpperCase())) {
                        returnColumnList.add(aa.getColumn().getColumnName().toUpperCase());
                    }
                } else if (aa.getColumn().getCategory().equals(new Long(3))) {//参数字段是统计属性
                    //TODO 是否需要递归
                    if (aa.getColumn().getArithMethod() != null) {//统计属性的算法是数据库内置算法
                        resultColumnList.append(aa.getColumn().getColumnName()).append(",");
                    } else {//统计属性的算法是自定义算法
                        returnColumnFromArith(resultColumnList, aa.getColumn().getArithAttribute(), columnset, isRefView, returnColumnList);
                    }
                }
            }
        }
    }

    /**
     * 将该统计视图所引用的所有视图ID进行累加，放入refList中
     *
     * @param view    --当前视图
     * @param schema  --当前的Schema
     * @param refList --返回的ViewID列表
     * @return void
     * @throws Exception
     */
    public void addRefView(View view, Schema schema, List<String> refList) throws Exception {
        if (!view.getRefviewSet().isEmpty()) {
            for (Iterator viewit = view.getRefviewSet().iterator(); viewit.hasNext(); ) {
                View refview = (View) viewit.next();
                refList.add(0, refview.getId());
                addRefView(refview, schema, refList);
            }
        }
    }

}
