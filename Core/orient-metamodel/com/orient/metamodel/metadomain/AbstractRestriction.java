package com.orient.metamodel.metadomain;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 数据约束信息
 *
 * @author mengbin@cssrc.com.cn
 * @date Feb 8, 2012
 */
public abstract class AbstractRestriction extends BaseMetaBean {

    /**
     * The id.
     */
    private String id;

    /**
     * 所属业务库.
     */
    private Schema schema;

    /**
     * The name.
     */
    private String name;

    /**
     * The display name.
     */
    private String displayName;

    /**
     * 数据约束的类型，1表示枚举约束，2表示数据表枚举约束，3表示范围约束4.动态范围约束
     */
    private Long type;

    /**
     * 是否多选，TRUE为当前记录可以选择多个枚举值，FALSE不可以多选
     */
    private String isMultiSelected;

    /**
     * 错误信息，用户出现错误操作弹出的错误信息
     */
    private String errorInfo;

    /**
     * The description.
     */
    private String description;

    /**
     * 枚举约束的显示方式，0为文字显示，1为图片显示，此只针对枚举约束
     */
    private Long displayType;

    /**
     * 此约束的数据类型
     */
    private String dataType;

    /**
     * 范围约束的最大值
     */
    private BigDecimal maxLength;

    /**
     * 范围约束的最小值
     */
    private BigDecimal minLength;

    /**
     * The is valid.
     */
    private Long isValid;

    /**
     * 不同约束之间的先后顺序.
     */
    private Long order;

    /**
     * 用于打开模型时关联引用的信息
     */
    private String cite;

    /**
     * 对应的枚举约束值集合
     */
    private Set<Enum> cwmEnums = new LinkedHashSet<>(0);

    /**
     * 对应的数据类枚举约束集合
     */
    private TableEnum tableEnum;


    public static Long EnumType = 1L;
    public static Long TabelEnumType = 2L;
    public static Long RangeEnumType = 3L;
    public static Long DynamicRangeEnumType = 4L;

    public static String MULTISELECT_ALLOWED = "True";
    public static String MULTISELECT_UNALLOWED = "False";

    public static Long ISVALID_VALID = 1L;
    public static Long ISVALID_INVALID = 0L;
    public static long VALID = 1;
    public static long INVALID = 0;

    public AbstractRestriction() {
    }

    /**
     * @param name
     * @param displayName
     * @param type        --类型1表示枚举约束，2表示数据表枚举约束，3表示范围约束，4动态范围约束
     * @param displayType -- 枚举约束的显示方式，0为文字显示，1为图片显示，此值针对枚举约束
     * @param isValid
     */
    public AbstractRestriction(String name, String displayName, Long type, Long displayType, Long isValid) {
        this.name = name;
        this.displayName = displayName;
        this.type = type;
        this.displayType = displayType;
        this.isValid = isValid;
    }

    /**
     * full constructor.
     *
     * @param cwmSchema       --所属的Schema
     * @param name            --真实名称
     * @param displayName     --显示名称
     * @param type            --约束类型
     * @param idMultiSelected --是否多选，TRUE为当前记录可以选择多个枚举值，FALSE不可以多选
     * @param errorInfo       --错误信息，用户出现错误操作弹出的错误信息
     * @param description
     * @param displayType
     * @param maxLength       --范围约束的最大值
     * @param minLength       --范围约束的最小值
     * @param isValid
     * @param cwmEnums        --对应的枚举约束值集合
     * @param cwmTableEnums   --对应的数据类约束集合
     */
    public AbstractRestriction(Schema cwmSchema, String name, String displayName, Long type, String idMultiSelected, String errorInfo, String description, Long displayType, BigDecimal maxLength, BigDecimal minLength, Long isValid, Set<Enum> cwmEnums, Set cwmTableEnums) {
        this.schema = cwmSchema;
        this.name = name;
        this.displayName = displayName;
        this.type = type;
        this.isMultiSelected = idMultiSelected;
        this.errorInfo = errorInfo;
        this.description = description;
        this.displayType = displayType;
        this.maxLength = maxLength;
        this.minLength = minLength;
        this.isValid = isValid;
        this.cwmEnums = cwmEnums;
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

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getIsMultiSelected() {
        return isMultiSelected;
    }

    public void setIsMultiSelected(String isMultiSelected) {
        this.isMultiSelected = isMultiSelected;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDisplayType() {
        return displayType;
    }

    public void setDisplayType(Long displayType) {
        this.displayType = displayType;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public BigDecimal getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(BigDecimal maxLength) {
        this.maxLength = maxLength;
    }

    public BigDecimal getMinLength() {
        return minLength;
    }

    public void setMinLength(BigDecimal minLength) {
        this.minLength = minLength;
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

    public Set<Enum> getCwmEnums() {
        return cwmEnums;
    }

    public void setCwmEnums(Set<Enum> cwmEnums) {
        this.cwmEnums = cwmEnums;
    }

    public TableEnum getTableEnum() {
        return tableEnum;
    }

    public void setTableEnum(TableEnum tableEnum) {
        this.tableEnum = tableEnum;
    }
}