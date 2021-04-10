package com.orient.web.modelDesc.column;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.utils.JsonUtil;
import com.orient.utils.StringUtil;
import com.orient.web.modelDesc.util.OrientColumnHelper;
import com.orient.web.util.UserContextUtil;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 表单元素基类
 *
 * @author enjoy
 * @creare 2016-03-30 9:37
 */
public abstract class ColumnDesc implements Serializable {

    private String id;

    private String text;

    private String dbName;

    private String type;

    private String modelId;

    private String sModelName;

    private String sColumnName;

    //页面展现形式
    private Integer controlType;

    //最大长度
    private Long charLen;

    //是否必填
    private Boolean isRequired;

    //是否唯一
    private Boolean isUnique;

    //是否组合唯一
    private Boolean isMultiUnique;

    //校验规则
    private String validRule;

    private String defaultValue;

    //0:不限制 1：限制修改 2：限制新增修改
    private String editAbleType = "0";

    public String getEditAbleType() {
        return editAbleType;
    }

    public void setEditAbleType(String editAbleType) {
        this.editAbleType = editAbleType;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getsModelName() {
        return sModelName;
    }

    public void setsModelName(String sModelName) {
        this.sModelName = sModelName;
    }

    private String className = this.getClass().getSimpleName();

    public Long getCharLen() {
        return charLen;
    }

    public void setCharLen(Long charLen) {
        this.charLen = charLen;
    }

    public Boolean getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }

    public String getValidRule() {
        return validRule;
    }

    public void setValidRule(String validRule) {
        this.validRule = validRule;
    }

    public Integer getControlType() {
        return controlType;
    }

    public void setControlType(Integer controlType) {
        this.controlType = controlType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getClassName() {
        return className;
    }

    public String getsColumnName() {
        return sColumnName;
    }

    public void setsColumnName(String sColumnName) {
        this.sColumnName = sColumnName;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Boolean getIsUnique() {
        return isUnique;
    }

    public void setIsUnique(Boolean isUnique) {
        this.isUnique = isUnique;
    }

    public Boolean getIsMultiUnique() {
        return isMultiUnique;
    }

    public void setIsMultiUnique(Boolean isMultiUnique) {
        this.isMultiUnique = isMultiUnique;
    }

    /**
     * 子类特殊化处理
     */
    public abstract void specialInit(IBusinessColumn iBusinessColumn);

    public ColumnDesc init(IBusinessColumn iBusinessColumn) {
        //初始化共同属性
        iBusinessColumn.getEditable();
        String modelID = iBusinessColumn.getParentModel().getId();
        EnumInter.BusinessModelEnum.BusinessColumnEnum colEnum = (EnumInter.BusinessModelEnum.BusinessColumnEnum) iBusinessColumn.getColType();
        //设置columnId
        this.setId(iBusinessColumn.getCol().getId());
        //设置label展现值
        this.setText(iBusinessColumn.getDisplay_name());
        this.setsColumnName(iBusinessColumn.getS_column_name());
        //设置字段真实数据库名称
        this.setDbName("m:" + modelID + ":" + iBusinessColumn.getS_column_name());
        //设置所属模型
        this.setModelId(modelID);
        this.setsModelName(iBusinessColumn.getParentModel().getS_table_name());
        //设置类型
        this.setType(colEnum.toString());
        //最大长度
        this.setCharLen(iBusinessColumn.getCol().getMaxLength());
        //是否必填
        this.setEditAbleType(iBusinessColumn.getEditType());
        this.setIsRequired(getIsColumnRequire(iBusinessColumn));
        this.setIsUnique(iBusinessColumn.getCol().getIsOnly() == null || iBusinessColumn.getCol().getIsOnly().toLowerCase().equals("false") ? false : true);
        this.setIsMultiUnique(iBusinessColumn.getParentModel().getMultyUkColumns().stream().filter(column -> column.getId().equals(iBusinessColumn.getId())).count() > 0);
        //设置展现形式
        this.setControlType(OrientColumnHelper.getColumnControlType(colEnum));
        //设置缺省值
        this.setDefaultValue(getRealDefaultValue(iBusinessColumn.getCol().getDefaultValue()));
        specialInit(iBusinessColumn);
        return this;
    }

    private Boolean getIsColumnRequire(IBusinessColumn iBusinessColumn) {
        Boolean retVal = false;
        if (iBusinessColumn.getRelationColumnIF() != null) {
            //关系属性
            retVal = iBusinessColumn.getCol().getIsNeed() == null || iBusinessColumn.getCol().getIsNeed().toLowerCase().equals("false") ? false : true;
        } else {
            retVal = iBusinessColumn.getCol().getIsNull() == null || iBusinessColumn.getCol().getIsNull().toLowerCase().equals("false") ? false : true;
        }
        return retVal;
    }

    private String getRealDefaultValue(String defaultValue) {

        if (!StringUtil.isEmpty(defaultValue) && defaultValue.indexOf("$") != -1) {
            String defaultDefinition = defaultValue.substring(1);
            if ("系统用户".equals(defaultDefinition)) {
                Map<String, String> realValue = new HashMap<>();
                realValue.put("hiddenValue", UserContextUtil.getUserId());
                realValue.put("showValue", UserContextUtil.getUserAllName());
                defaultValue = JsonUtil.toJson(realValue);
            } else if ("系统日期".equals(defaultDefinition)) {
                defaultValue = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            } else if ("系统时间".equals(defaultDefinition)) {
                defaultValue = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            }
            if ("系统部门".equals(defaultDefinition)) {
                Map<String, String> realValue = new HashMap<>();
                realValue.put("hiddenValue", UserContextUtil.getUserDepId());
                realValue.put("showValue", UserContextUtil.getUserDepName());
                defaultValue = JsonUtil.toJson(realValue);
            }
        }
        return defaultValue;
    }


}

