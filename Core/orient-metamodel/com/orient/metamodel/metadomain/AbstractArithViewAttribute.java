package com.orient.metamodel.metadomain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 统计视图统计项模型抽象类，供hibernate持久化使用.
 *
 * @author XIUJUN XU
 * @since Nov 5, 2009
 */
public abstract class AbstractArithViewAttribute extends BaseMetaBean implements Serializable {

    /**
     * 主键ID.
     */
    private String id;

    /**
     * 是否统计项.
     */
    private String isForArith;

    /**
     * 统计参数项名称.
     */
    private String name;

    /**
     * 统计参数项显示名.
     */
    private String displayName;

    /**
     * 算法id.
     */
    private String arithId;

    /**
     * 算法名称.
     */
    private String arithName;

    /**
     * 统计项引用字段.
     */
    private Column column;

    /**
     * 所属视图.
     */
    private View view;

    /**
     * 顺序号.
     */
    private Long order;

    /**
     * 算法使用到的算法参数定义集合.
     */
    private Set<ArithAttribute> arithAttribute = new HashSet<>(0);

    // Constructors

    /**
     * 缺省构造方法.
     */
    public AbstractArithViewAttribute() {
    }

    /**
     * 全参数构造方法.
     *
     * @param isForArith     是否统计项
     * @param name           统计参数项名称
     * @param arithId        算法id
     * @param arithName      算法名称
     * @param column         统计项引用字段
     * @param arithAttribute 算法参数定义
     * @param view           统计项所属视图
     */
    public AbstractArithViewAttribute(String isForArith, String name, String arithId, String arithName, Column column, Set<ArithAttribute> arithAttribute, View view) {
        this.isForArith = isForArith;
        this.name = name;
        this.arithId = arithId;
        this.arithName = arithName;
        this.column = column;
        this.arithAttribute = arithAttribute;
        this.view = view;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<ArithAttribute> getArithAttribute() {
        return arithAttribute;
    }

    public void setArithAttribute(Set<ArithAttribute> arithAttribute) {
        this.arithAttribute = arithAttribute;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public String getArithName() {
        return arithName;
    }

    public void setArithName(String arithName) {
        this.arithName = arithName;
    }

    public String getArithId() {
        return arithId;
    }

    public void setArithId(String arithId) {
        this.arithId = arithId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsForArith() {
        return isForArith;
    }

    public void setIsForArith(String isForArith) {
        this.isForArith = isForArith;
    }

}