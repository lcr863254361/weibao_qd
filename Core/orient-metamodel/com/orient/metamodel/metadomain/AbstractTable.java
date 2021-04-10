package com.orient.metamodel.metadomain;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 数据类信息
 *
 * @author mengbin@cssrc.com.cn
 * @date Feb 8, 2012
 */
public abstract class AbstractTable extends BaseMetaBean {

    /**
     * The id.
     */
    private String id;

    /**
     * The schema.
     */
    private Schema schema;

    /**
     * 数据类名.
     */
    private String name;

    /**
     * 显示名.
     */
    private String displayName;

    /**
     * 表名
     */
    private String tableName;

    /**
     * The parent table.
     */
    private Table parentTable;

    /**
     * 排序方向（排序方向，指定数据类记录的排序方向，ASC表示升序，DESC表示降序）
     */
    private String paiXu;

    /**
     * The is connect table.
     */
    private String isConnectTable;

    /**
     * The is show.
     */
    private String isShow;

    /**
     * 详细文字.
     */
    private String detailText;

    /**
     * 描述
     */
    private String description;

    /**
     * The big image.
     */
    private String bigImage;

    /**
     * The nor image.
     */
    private String norImage;

    /**
     * The sma image.
     */
    private String smaImage;

    /**
     * 数据类的类型.
     */
    private String category;

    /**
     * The is valid.
     */
    private Long isValid;

    /**
     * 数据类的展现顺序.
     */
    private Long order;

    /**
     * 形成shema的xml时使用.
     */
    private String cite;

    /**
     * The exist data.
     */
    private String existData;

    /**
     * 对应于SHARE_TYPE   0:不共享 1:系统表共享 2:共享模型共享 3:动态类数据共享
     */
    private String type;

    /**
     * 对应于SHARE_TYPE  0:为空 1:系统表名 2:共享表的Id 3:动态对应的父表
     */
    private String mapTable;

    /**
     * 只有共享模型才会设置值
     */
    private String shareable;

    /**
     * WEB页面展现时，表格的列数（偶数）,应该没用了
     */
    private Long colSum;

    /**
     * 子数据类
     */
    private Set<Table> childTables = new HashSet<>(0);

    /**
     * 该使用该表的所有（数据视图的关联数据类信息表）的对象
     */
    private Set<ViewRefColumn> cwmViewRelationtables = new HashSet<>(0);

    /**
     * 该表关联的动态范围约束
     */
    private Set<ConsExpression> cwmConsExpressions = new HashSet<>(0);

    /**
     * 该表的所有数据字段
     */
    private Set<Column> cwmTabColumnses = new HashSet<>(0);

    /**
     * 所有使用该数据类作为主数据类的视图
     */
    private Set<View> cwmViewses = new HashSet<>(0);

    /**
     * 所有使用该数据类的数据表约束
     */
    private Set<TableEnum> cwmTableEnums = new HashSet<>(0);

    /**
     * 使用该数据类所有（数据类枚举约束的关联数据类信息表）的对象.
     */
    private Set<RelationTableEnum> cwmRelationTableEnums = new HashSet<>(0);

    /**
     * 所有关系属性
     */
    private Set<RelationColumns> cwmRelationColumnses = new HashSet<>(0);

    /**
     * 该数据类的所有的复合属性(tableColumn)
     */
    private Set<TableColumn> tableColumnSet = new LinkedHashSet<>(0);

    /**
     * 主键显示值集合.
     */
    private Map pkColumns = new LinkedHashMap<>();

    /**
     * 唯一性约束集合
     */
    private Map ukColumns = new LinkedHashMap<>();

    /**
     * 排序属性集合
     */
    private Map skColumns = new LinkedHashMap<>();

    /**
     * 字段展现顺序集合
     */
    private Map zxColumns = new LinkedHashMap<>();

    /**
     * 数据类的密级
     */
    private String secrecy;

    /**
     * 是否启用密级SECRECYABLE
     */
    private String useSecrecy;

    public static final String SORT_UP = "ASC";
    public static final String SORT_DOWN = "DESC";

    public static final long VALID = 1;
    public static final long INVALID = 0;


    public AbstractTable() {
    }

    /**
     * minimal constructor
     *
     * @param cwmSchema   the cwm schema
     * @param name        the name
     * @param displayName the display name
     * @param isValid     the is valid
     */
    public AbstractTable(Schema cwmSchema, String name, String displayName, Long isValid) {
        this.schema = cwmSchema;
        this.name = name;
        this.displayName = displayName;
        this.isValid = isValid;
    }

    /**
     * full constructor
     *
     * @param id                    the id
     * @param schema                the schema
     * @param name                  the name
     * @param displayName           the display name
     * @param tableName             the table name
     * @param parentTable           the parent table
     * @param paiXu                 the pai xu
     * @param isConnectTable        the is connect table
     * @param isShow                the is show
     * @param detailText            the detail text
     * @param description           the description
     * @param bigImage              the big image
     * @param norImage              the nor image
     * @param smaImage              the sma image
     * @param category              the category
     * @param isValid               the is valid
     * @param order                 the order
     * @param cite                  the cite
     * @param existData             the exist data
     * @param childTables           the child tables
     * @param cwmViewRelationtables the cwm view relationtables
     * @param cwmConsExpressions    the cwm cons expressions
     * @param cwmTabColumnses       the cwm tab columnses
     * @param cwmViewses            the cwm viewses
     * @param cwmTableEnums         the cwm table enums
     * @param cwmRelationTableEnums the cwm relation table enums
     * @param cwmRelationColumnses  the cwm relation columnses
     * @param pkColumns             the pk columns
     * @param ukColumns             the uk columns
     * @param skColumns             the sk columns
     */
    public AbstractTable(String id, Schema schema, String name, String displayName, String tableName, Table parentTable,
                         String paiXu, String isConnectTable, String isShow, String detailText, String description, String bigImage,
                         String norImage, String smaImage, String category, Long isValid, Long order, String cite, String existData, Set<Table> childTables,
                         Set<ViewRefColumn> cwmViewRelationtables, Set<ConsExpression> cwmConsExpressions, Set<Column> cwmTabColumnses, Set<View> cwmViewses, Set<TableEnum> cwmTableEnums,
                         Set<RelationTableEnum> cwmRelationTableEnums, Set<RelationColumns> cwmRelationColumnses, Map pkColumns, Map ukColumns, Map skColumns) {
        this.id = id;
        this.schema = schema;
        this.name = name;
        this.displayName = displayName;
        this.tableName = tableName;
        this.parentTable = parentTable;
        this.paiXu = paiXu;
        this.isConnectTable = isConnectTable;
        this.isShow = isShow;
        this.detailText = detailText;
        this.description = description;
        this.bigImage = bigImage;
        this.norImage = norImage;
        this.smaImage = smaImage;
        this.category = category;
        this.isValid = isValid;
        this.order = order;
        this.cite = cite;
        this.existData = existData;
        this.childTables = childTables;
        this.cwmViewRelationtables = cwmViewRelationtables;
        this.cwmConsExpressions = cwmConsExpressions;
        this.cwmTabColumnses = cwmTabColumnses;
        this.cwmViewses = cwmViewses;
        this.cwmTableEnums = cwmTableEnums;
        this.cwmRelationTableEnums = cwmRelationTableEnums;
        this.cwmRelationColumnses = cwmRelationColumnses;
        this.pkColumns = pkColumns;
        this.ukColumns = ukColumns;
        this.skColumns = skColumns;
    }


    public void setNULL() {
        this.id = null;
        this.schema = null;
        this.name = null;
        this.displayName = null;
        this.tableName = null;
        this.parentTable = null;
        this.paiXu = null;
        this.isConnectTable = null;
        this.isShow = null;
        this.detailText = null;
        this.description = null;
        this.bigImage = null;
        this.norImage = null;
        this.smaImage = null;
        this.category = null;
        this.isValid = null;
        this.order = null;
        this.cite = null;
        this.existData = null;
        this.childTables = null;
        this.cwmViewRelationtables = null;
        this.cwmConsExpressions = null;
        this.cwmTabColumnses = null;
        this.cwmViewses = null;
        this.cwmTableEnums = null;
        this.cwmRelationTableEnums = null;
        this.cwmRelationColumnses = null;
        this.pkColumns = null;
        this.ukColumns = null;
        this.skColumns = null;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Schema getSchema() {
        return schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Table getParentTable() {
        return parentTable;
    }

    public void setParentTable(Table parentTable) {
        this.parentTable = parentTable;
    }

    public String getPaiXu() {
        return paiXu;
    }

    public void setPaiXu(String paiXu) {
        this.paiXu = paiXu;
    }

    public String getIsConnectTable() {
        return isConnectTable;
    }

    public void setIsConnectTable(String isConnectTable) {
        this.isConnectTable = isConnectTable;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getDetailText() {
        return detailText;
    }

    public void setDetailText(String detailText) {
        this.detailText = detailText;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBigImage() {
        return bigImage;
    }

    public void setBigImage(String bigImage) {
        this.bigImage = bigImage;
    }

    public String getNorImage() {
        return norImage;
    }

    public void setNorImage(String norImage) {
        this.norImage = norImage;
    }

    public String getSmaImage() {
        return smaImage;
    }

    public void setSmaImage(String smaImage) {
        this.smaImage = smaImage;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getIsValid() {
        return isValid;
    }

    public void setIsValid(Long isValid) {
        this.isValid = isValid;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public String getCite() {
        return cite;
    }

    public void setCite(String cite) {
        this.cite = cite;
    }

    public String getExistData() {
        return existData;
    }

    public void setExistData(String existData) {
        this.existData = existData;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMapTable() {
        return mapTable;
    }

    public void setMapTable(String mapTable) {
        this.mapTable = mapTable;
    }

    public String getShareable() {
        return shareable;
    }

    public void setShareable(String shareable) {
        this.shareable = shareable;
    }

    public Long getColSum() {
        return colSum;
    }

    public void setColSum(Long colSum) {
        this.colSum = colSum;
    }

    public Set<Table> getChildTables() {
        return childTables;
    }

    public void setChildTables(Set<Table> childTables) {
        this.childTables = childTables;
    }

    public Set<ViewRefColumn> getCwmViewRelationtables() {
        return cwmViewRelationtables;
    }

    public void setCwmViewRelationtables(Set<ViewRefColumn> cwmViewRelationtables) {
        this.cwmViewRelationtables = cwmViewRelationtables;
    }

    public Set<ConsExpression> getCwmConsExpressions() {
        return cwmConsExpressions;
    }

    public void setCwmConsExpressions(Set<ConsExpression> cwmConsExpressions) {
        this.cwmConsExpressions = cwmConsExpressions;
    }

    public Set<Column> getCwmTabColumnses() {
        return cwmTabColumnses;
    }

    public void setCwmTabColumnses(Set<Column> cwmTabColumnses) {
        this.cwmTabColumnses = cwmTabColumnses;
    }

    public Set<View> getCwmViewses() {
        return cwmViewses;
    }

    public void setCwmViewses(Set<View> cwmViewses) {
        this.cwmViewses = cwmViewses;
    }

    public Set<TableEnum> getCwmTableEnums() {
        return cwmTableEnums;
    }

    public void setCwmTableEnums(Set<TableEnum> cwmTableEnums) {
        this.cwmTableEnums = cwmTableEnums;
    }

    public Set<RelationTableEnum> getCwmRelationTableEnums() {
        return cwmRelationTableEnums;
    }

    public void setCwmRelationTableEnums(Set<RelationTableEnum> cwmRelationTableEnums) {
        this.cwmRelationTableEnums = cwmRelationTableEnums;
    }

    public Set<RelationColumns> getCwmRelationColumnses() {
        return cwmRelationColumnses;
    }

    public void setCwmRelationColumnses(Set<RelationColumns> cwmRelationColumnses) {
        this.cwmRelationColumnses = cwmRelationColumnses;
    }

    public Set<TableColumn> getTableColumnSet() {
        return tableColumnSet;
    }

    public void setTableColumnSet(Set<TableColumn> tableColumnSet) {
        this.tableColumnSet = tableColumnSet;
    }

    public Map getPkColumns() {
        tableColumnSet.forEach(tableColumn -> {
            if (tableColumn.getType() == 0) {
                pkColumns.put(tableColumn.getOrder().intValue(), tableColumn.getColumn());
            }
        });
        return pkColumns;
    }

    public void setPkColumns(Map pkColumns) {
        this.pkColumns = pkColumns;
    }

    public Map getUkColumns() {
        tableColumnSet.forEach(tableColumn -> {
            if (tableColumn.getType() == 1) {
                ukColumns.put(tableColumn.getOrder().intValue(), tableColumn.getColumn());
            }
        });
        return ukColumns;
    }

    public void setUkColumns(Map ukColumns) {
        this.ukColumns = ukColumns;
    }

    public Map getSkColumns() {
        tableColumnSet.forEach(tableColumn -> {
            if (tableColumn.getType() == 2) {
                skColumns.put(tableColumn.getOrder().intValue(), tableColumn.getColumn());
            }
        });
        return skColumns;
    }

    public void setSkColumns(Map skColumns) {
        this.skColumns = skColumns;
    }

    public Map getZxColumns() {
        List<TableColumn> sortedTableColumns = tableColumnSet.stream().sorted((t1, t2) -> {
            return t1.getOrder().intValue() - t2.getOrder().intValue();
        }).collect(Collectors.toList());
        for (TableColumn tc : sortedTableColumns) {
            if (tc.getType() == 3) {
                zxColumns.put(tc.getOrder().intValue(), tc.getColumn());
            }
        }
        return zxColumns;
    }

    public void setZxColumns(Map zxColumns) {
        this.zxColumns = zxColumns;
    }

    public String getSecrecy() {
        return secrecy;
    }

    public void setSecrecy(String secrecy) {
        this.secrecy = secrecy;
    }

    public String getUseSecrecy() {
        return useSecrecy;
    }

    public void setUseSecrecy(String useSecrecy) {
        this.useSecrecy = useSecrecy;
    }

}