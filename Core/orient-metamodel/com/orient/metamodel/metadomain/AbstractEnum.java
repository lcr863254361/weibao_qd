package com.orient.metamodel.metadomain;

/**
 * 枚举值信息表
 *
 * @author mengbin@cssrc.com.cn
 * @date Feb 8, 2012
 */
public abstract class AbstractEnum extends BaseMetaBean {

    /**
     * The id.
     */
    private String id;

    /**
     * 枚举值所属的约束
     */
    private Restriction cwmRestriction;

    private String restrictionID;

    public String getRestrictionID() {
        return restrictionID;
    }

    public void setRestrictionID(String restrictionID) {
        this.restrictionID = restrictionID;
    }

    /**
     * The value.
     */
    private String value;

    /**
     * The display value.
     */
    private String displayValue;

    /**
     * The image url.
     */
    private String imageURL;

    /**
     * The description.
     */
    private String description;

    /**
     * 枚举值的前后顺序.
     */
    private Long order;

    /**
     * 枚举值是否启用，1表示启用，0表示停用
     */
    private Long isopen;

    public AbstractEnum() {
    }

    /**
     * minimal constructor.
     *
     * @param @param cwmRestriction
     * @param @param value
     * @param @param displayValue
     * @throws
     */
    public AbstractEnum(Restriction cwmRestriction, String value, String displayValue) {
        this.cwmRestriction = cwmRestriction;
        this.value = value;
        this.displayValue = displayValue;
    }

    /**
     * full constructor
     *
     * @param @param cwmRestriction
     * @param @param value
     * @param @param displayValue
     * @param @param imageURL
     * @param @param description
     * @throws
     */
    public AbstractEnum(Restriction cwmRestriction, String value, String displayValue, String imageURL, String description) {
        this.cwmRestriction = cwmRestriction;
        this.value = value;
        this.displayValue = displayValue;
        this.imageURL = imageURL;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Restriction getCwmRestriction() {
        return cwmRestriction;
    }

    public void setCwmRestriction(Restriction cwmRestriction) {
        this.cwmRestriction = cwmRestriction;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public Long getIsopen() {
        return isopen;
    }

    public void setIsopen(Long isopen) {
        this.isopen = isopen;
    }

}