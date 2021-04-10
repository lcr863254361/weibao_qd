package com.orient.metamodel.metadomain;

import com.orient.utils.CommonTools;

import java.util.HashSet;
import java.util.Set;

/**
 * 数据类属性
 *
 * @author mengbin@cssrc.com.cn
 * @date Feb 8, 2012
 */
public abstract class AbstractColumn extends BaseMetaBean {

    /**
     * The id.
     */
    private String id;

    /**
     * 所属数据类.
     */
    private Table table;

    /**
     * The name.
     */
    private String name;

    /**
     * The display name.
     */
    private String displayName;

    private String dataValue = "";

    private String displayValue = "";

    /**
     * 字段的类别，1表示普通属性，2表示关系属性 3.表示统计属性
     */
    private Long category;

    /**
     * The description.
     */
    private String description;

    /**
     * 建表字段名（字段在数据库中建表后具体的字段名称）
     */
    private String columnName;

    /**
     * 是否全文检索 True表示是，False表示不是
     */
    private String isAllSearch;

    /**
     * 是否适用于全文检索 True表示适用，False表示不是适用
     */
    private String isForSearch;

    /**
     * 是否索引.
     */
    private String isIndex;

    /**
     * 操作符.
     */
    private String operateSign;

    /**
     * 用途 新增、修改、详细、列表的任意组合
     */
    private String purpose;

    /**
     * 大小写规定 CaseSensitive表示“区分大小写”，Upper表示“全大写”，Lower表示“全小写”，CaseInsensitive表示“不区分大小写”
     */
    private String casesensitive;

    /**
     * The default value.
     */
    private String defaultValue;

    /**
     * The is null.
     */
    private String isNull;

    /**
     * The is need.
     */
    private String isNeed;

    /**
     * 是否唯一.
     */
    private String isOnly;

    /**
     * The is pk.
     */
    private Long isPK;

    /**
     * 字段是否自增字段，True表示是，False表示否
     */
    private String isAutoIncrement;

    /**
     * 该字段使用的数据约束
     */
    private Restriction restriction;

    /**
     * 数据类型
     */
    private String type;

    /**
     * 自增值生成器，自增序列的名称.
     */
    private String sequenceName;

    /**
     * 字符型最大长度，默认100
     */
    private Long maxLength;

    /**
     * 字符型最小长度，默认0
     */
    private Long minLength;

    /**
     * True表示显示，False表示不显示
     */
    private String isShow;

    /**
     * 字段是否是多行显示，True表示是，False表示不是
     */
    private String isWrap;

    /**
     * 属性段落 （目前没有使用）
     */
    private String propertyParagraph;

    /**
     * 表示属性的修改的位置
     * 0：表示无修改
     * 1：将不全文检索设置为全文检索
     * 2：修改了数据类型
     * 3：数据的长度的最大值修改了
     */
    private String propertyCategory;

    /**
     * 行数
     */
    private Long linage;

    /**
     * 是否有效，0表示无效，1表示有效,2表示待修改，3表示已删除
     */
    private Long isValid;

    /**
     * 是否是约束属性（貌似没有使用）
     */
    private Long isMutiUk;

    /**
     * 是否是排序属性（貌似没有使用）
     */
    private Long isUsedPaixu;

    /**
     * 用于标记数据字段的前后顺序
     */
    private Long order;

    /**
     * 用于打开模型时关联引用的信息
     */
    private String cite;

    /**
     * The exist data.
     */
    private String existData;

    /**
     * The numlength.
     */
    private Long numlength;

    /**
     * The numprecision.
     */
    private Long numprecision;

    /**
     * 该属性属于的统计视图（该属性和table互斥）
     */
    private View view;

    /**
     * 该属性属于的统计视图的Id（该属性和table互斥）
     * 数据统计视图ID，用户定义数据统计视图时的统计参数项中的统计属性保存成字段模式，放在column中
     */
    private String viewid;

    /**
     * 统计属性的算法ID
     */
    private String arithId;

    /**
     * 统计属性算法的名称.
     */
    private String arithName;

    /**
     * 统计属性算法的公式
     */
    private String arithMethod;

    /**
     * 统计属性的类别，是数据库内置算法还是自定义算法，0表示内置，1表示自定义算法.
     */
    private String arithType;

    /**
     * 映射字段，当前字段属性映射的共享表中的字段信息
     */
    private String mapColumn;

    /**
     * 自增属性初始值,默认值为1
     */
    private Long autoAddDefault;

    /**
     * 数据操作,0为不限制，1为限制修改，2为限制创建修改
     */
    private String editable;

    /**
     * 自增序列的自增间隔，默认为1，最小为1的正整数
     */
    private Long seqInterval;

    /**
     * 选择器配置，json格式存储{selector:"",filterType:"",filterIds:""}
     */
    private String selector;

    /**
     * 单位配置
     */
    private String unit;

    /**
     * 该统计属性的算法属性集合.
     */
    private Set<ArithAttribute> arithAttribute = new HashSet<>(0);

    /**
     * 使用该属性的视图的排序属性集合.
     */
    private Set cwmViewPaixuColumns = new HashSet<>(0);

    /**
     * 使用该属性的视图的显示属性集合
     */
    private Set cwmViewReturnColumns = new HashSet<>(0);

    /**
     * 使用该属性的表枚举约束集合
     */
    private Set cwmTableEnums = new HashSet<>(0);

    /**
     * 使用该属性的所有数据类符合属性.
     */
    private Set columnSet = new HashSet<>(0);

    /**
     * 如果是关系属性，该属性所对应的关系属性信息
     */
    private RelationColumns relationColumn;


    /**
     * 字段的类别，1表示普通属性，2表示关系属性 3.表示统计属性
     */
    //public static long CATEGORY_COMMON= 1;
    //public static long category_Relation = 2;
    // static long category_Arith = 3;

    public static final String PURPOSE_ADD = "新增";
    public static final String PURPOSE_MODIFY = "修改";
    public static final String PURPOSE_DETAIL = "详细";
    public static final String PURPOSE_LIST = "列表";

    public AbstractColumn() {
    }

    /**
     * minimal constructor.
     *
     * @param cwmTables   the cwm tables
     * @param name        the name
     * @param displayName the display name
     * @param category    the category
     * @param type        the type
     * @param isValid     the is valid
     */
    public AbstractColumn(Table cwmTables, String name, String displayName, Long category, String type, Long isValid) {
        this.table = cwmTables;
        this.name = name;
        this.displayName = displayName;
        this.category = category;
        this.type = type;
        this.isValid = isValid;
    }

    /**
     * full constructor.
     *
     * @param cwmTables                          the cwm tables
     * @param name                               the name
     * @param displayName                        the display name
     * @param category                           the category
     * @param description                        the description
     * @param columnName                         the column name
     * @param isWhoSearch                        the is who search
     * @param isForSearch                        the is for search
     * @param isIndex                            the is index
     * @param operateSign                        the operate sign
     * @param purpose                            the purpose
     * @param casesensitive                      the casesensitive
     * @param defalutValue                       the defalut value
     * @param isNullable                         the is nullable
     * @param isOnly                             the is only
     * @param isPK                               the is pk
     * @param isAutoIncrement                    the is auto increment
     * @param restrictionID                      the restriction id
     * @param type                               the type
     * @param sequenceName                       the sequence name
     * @param maxLength                          the max length
     * @param minLength                          the min length
     * @param isShow                             the is show
     * @param isWarp                             the is warp
     * @param propertyParagraph                  the property paragraph
     * @param properyCategory                    the propery category
     * @param linage                             the linage
     * @param isValid                            the is valid
     * @param isMutiUK                           the is muti uk
     * @param isUsedPaiXu                        the is used pai xu
     * @param cwmViewPaixuColumns                the cwm view paixu columns
     * @param cwmViewReturnColumns               the cwm view return columns
     * @param cwmTableEnums                      the cwm table enums
     * @param cwmRelationColumnsesForRefColumnId the cwm relation columnses for ref column id
     * @param cwmRelationColumnsesForColumnId    the cwm relation columnses for column id
     */
    public AbstractColumn(Table cwmTables, String name, String displayName, Long category, String description, String columnName, String isWhoSearch, String isForSearch, String isIndex, String operateSign, String purpose, String casesensitive, String defalutValue, String isNullable, String isOnly, String isPK, String isAutoIncrement, String restrictionID, String type, String sequenceName, Long maxLength, Long minLength, String isShow, String isWarp, String propertyParagraph, String properyCategory, Long linage, Long isValid, Long isMutiUK, Long isUsedPaiXu, String selector, String unit, Set cwmViewPaixuColumns, Set cwmViewReturnColumns, Set cwmTableEnums, Set cwmRelationColumnsesForRefColumnId, Set cwmRelationColumnsesForColumnId) {
        this.table = cwmTables;
        this.name = name;
        this.displayName = displayName;
        this.category = category;
        this.description = description;
        this.columnName = columnName;
        this.isAllSearch = isWhoSearch;
        this.isForSearch = isForSearch;
        this.isIndex = isIndex;
        this.operateSign = operateSign;
        this.purpose = purpose;
        this.casesensitive = casesensitive;
        this.defaultValue = defalutValue;
        this.isNull = isNullable;
        this.isOnly = isOnly;
        this.isPK = Long.valueOf("".equals(CommonTools.null2String(isPK)) ? "0" : isPK);
        this.isAutoIncrement = isAutoIncrement;
        this.type = type;
        this.sequenceName = sequenceName;
        this.maxLength = maxLength;
        this.minLength = minLength;
        this.isShow = isShow;
        this.isWrap = isWarp;
        this.propertyParagraph = propertyParagraph;
        this.propertyCategory = properyCategory;
        this.linage = linage;
        this.isValid = isValid;
        this.isMutiUk = isMutiUK;
        this.isUsedPaixu = isUsedPaiXu;
        this.selector = selector;
        this.unit = unit;
        this.cwmViewPaixuColumns = cwmViewPaixuColumns;
        this.cwmViewReturnColumns = cwmViewReturnColumns;
        this.cwmTableEnums = cwmTableEnums;
    }

    public void setNULL() {
        this.table = null;
        this.name = null;
        this.displayName = null;
        this.category = null;
        this.description = null;
        this.columnName = null;
        this.isAllSearch = null;
        this.isForSearch = null;
        this.isIndex = null;
        this.operateSign = null;
        this.purpose = null;
        this.casesensitive = null;
        this.defaultValue = null;
        this.isNull = null;
        this.isOnly = null;
        this.isPK = null;
        this.isAutoIncrement = null;
        this.type = null;
        this.sequenceName = null;
        this.maxLength = null;
        this.minLength = null;
        this.isShow = null;
        this.isWrap = null;
        this.propertyParagraph = null;
        this.propertyCategory = null;
        this.linage = null;
        this.isValid = null;
        this.isMutiUk = null;
        this.isUsedPaixu = null;
        this.selector = null;
        this.unit = null;
        this.cwmViewPaixuColumns = null;
        this.cwmViewReturnColumns = null;
        this.cwmTableEnums = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
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

    public String getDataValue() {
        return dataValue;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getIsAllSearch() {
        return isAllSearch;
    }

    public void setIsAllSearch(String isAllSearch) {
        this.isAllSearch = isAllSearch;
    }

    public String getIsForSearch() {
        return isForSearch;
    }

    public void setIsForSearch(String isForSearch) {
        this.isForSearch = isForSearch;
    }

    public String getIsIndex() {
        return isIndex;
    }

    public void setIsIndex(String isIndex) {
        this.isIndex = isIndex;
    }

    public String getOperateSign() {
        return operateSign;
    }

    public void setOperateSign(String operateSign) {
        this.operateSign = operateSign;
    }

    public String getPurpose() {
        return CommonTools.null2String(purpose);
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getCasesensitive() {
        return casesensitive;
    }

    public void setCasesensitive(String casesensitive) {
        this.casesensitive = casesensitive;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getIsNull() {
        return isNull;
    }

    public void setIsNull(String isNull) {
        this.isNull = isNull;
    }

    public String getIsNeed() {
        return isNeed;
    }

    public void setIsNeed(String isNeed) {
        this.isNeed = isNeed;
    }

    public String getIsOnly() {
        return isOnly;
    }

    public void setIsOnly(String isOnly) {
        this.isOnly = isOnly;
    }

    public Long getIsPK() {
        return isPK;
    }

    public void setIsPK(Long isPK) {
        this.isPK = isPK;
    }

    public String getIsAutoIncrement() {
        return isAutoIncrement;
    }

    public void setIsAutoIncrement(String isAutoIncrement) {
        this.isAutoIncrement = isAutoIncrement;
    }

    public Restriction getRestriction() {
        return restriction;
    }

    public void setRestriction(Restriction restriction) {
        this.restriction = restriction;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSequenceName() {
        return sequenceName;
    }

    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }

    public Long getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Long maxLength) {
        this.maxLength = maxLength;
    }

    public Long getMinLength() {
        return minLength;
    }

    public void setMinLength(Long minLength) {
        this.minLength = minLength;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getIsWrap() {
        return isWrap;
    }

    public void setIsWrap(String isWrap) {
        this.isWrap = isWrap;
    }

    public String getPropertyParagraph() {
        return propertyParagraph;
    }

    public void setPropertyParagraph(String propertyParagraph) {
        this.propertyParagraph = propertyParagraph;
    }

    public String getPropertyCategory() {
        return propertyCategory;
    }

    public void setPropertyCategory(String propertyCategory) {
        this.propertyCategory = propertyCategory;
    }

    public Long getLinage() {
        return linage;
    }

    public void setLinage(Long linage) {
        this.linage = linage;
    }

    public Long getIsValid() {
        return isValid;
    }

    public void setIsValid(Long isValid) {
        this.isValid = isValid;
    }

    public Long getIsMutiUk() {
        return isMutiUk;
    }

    public void setIsMutiUk(Long isMutiUk) {
        this.isMutiUk = isMutiUk;
    }

    public Long getIsUsedPaixu() {
        return isUsedPaixu;
    }

    public void setIsUsedPaixu(Long isUsedPaixu) {
        this.isUsedPaixu = isUsedPaixu;
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

    public Long getNumlength() {
        return numlength;
    }

    public void setNumlength(Long numlength) {
        this.numlength = numlength;
    }

    public Long getNumprecision() {
        return numprecision;
    }

    public void setNumprecision(Long numprecision) {
        this.numprecision = numprecision;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public String getViewid() {
        return viewid;
    }

    public void setViewid(String viewid) {
        this.viewid = viewid;
    }

    public String getArithId() {
        return arithId;
    }

    public void setArithId(String arithId) {
        this.arithId = arithId;
    }

    public String getArithName() {
        return arithName;
    }

    public void setArithName(String arithName) {
        this.arithName = arithName;
    }

    public String getArithMethod() {
        return arithMethod;
    }

    public void setArithMethod(String arithMethod) {
        this.arithMethod = arithMethod;
    }

    public String getArithType() {
        return arithType;
    }

    public void setArithType(String arithType) {
        this.arithType = arithType;
    }

    public String getMapColumn() {
        return mapColumn;
    }

    public void setMapColumn(String mapColumn) {
        this.mapColumn = mapColumn;
    }

    public Long getAutoAddDefault() {
        return autoAddDefault;
    }

    public void setAutoAddDefault(Long autoAddDefault) {
        this.autoAddDefault = autoAddDefault;
    }

    public String getEditable() {
        return editable;
    }

    public void setEditable(String editable) {
        this.editable = editable;
    }

    public Long getSeqInterval() {
        return seqInterval;
    }

    public void setSeqInterval(Long seqInterval) {
        this.seqInterval = seqInterval;
    }

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Set<ArithAttribute> getArithAttribute() {
        return arithAttribute;
    }

    public void setArithAttribute(Set<ArithAttribute> arithAttribute) {
        this.arithAttribute = arithAttribute;
    }

    public Set getCwmViewPaixuColumns() {
        return cwmViewPaixuColumns;
    }

    public void setCwmViewPaixuColumns(Set cwmViewPaixuColumns) {
        this.cwmViewPaixuColumns = cwmViewPaixuColumns;
    }

    public Set getCwmViewReturnColumns() {
        return cwmViewReturnColumns;
    }

    public void setCwmViewReturnColumns(Set cwmViewReturnColumns) {
        this.cwmViewReturnColumns = cwmViewReturnColumns;
    }

    public Set getCwmTableEnums() {
        return cwmTableEnums;
    }

    public void setCwmTableEnums(Set cwmTableEnums) {
        this.cwmTableEnums = cwmTableEnums;
    }

    public Set getColumnSet() {
        return columnSet;
    }

    public void setColumnSet(Set columnSet) {
        this.columnSet = columnSet;
    }

    public RelationColumns getRelationColumn() {
        return relationColumn;
    }

    public void setRelationColumn(RelationColumns relationColumn) {
        this.relationColumn = relationColumn;
    }
}