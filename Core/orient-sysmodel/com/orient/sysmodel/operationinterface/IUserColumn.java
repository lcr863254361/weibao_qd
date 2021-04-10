/**
 * IUserColumn.java
 * com.orient.sysmodel.operationinterface
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2012-4-11 		zhang yan
 *
 * Copyright (c) 2012, TNT All Rights Reserved.
*/ 

package com.orient.sysmodel.operationinterface;

import java.util.List;

import com.orient.metamodel.operationinterface.IEnum;

/**
 * ClassName:IUserColumn
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   zhang yan
 * @version  
 * @since    Ver 1.1
 * @Date	 2012-4-11		上午10:30:12
 *
 * @see 	 
 */
public interface IUserColumn {
	/**
	 * 
	
	 * @Method: getId 
	
	 * id
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getId();
	
	/**
	 * 
	
	 * @Method: getDisplayName 
	
	 * 字段显示名
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getDisplayName();
	
	/**
	 * 
	
	 * @Method: getSColumnName 
	
	 * 字段名
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getSColumnName();
	
	/**
	 * 
	
	 * @Method: getIsForSearch 
	
	 * 是否做为检索条件 1:是 0:不是
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getIsForSearch();
	
	/**
	 * 
	
	 * @Method: getIsNullable 
	
	 * 是否允许为空 1:为空 0:不为空
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getIsNullable();
	
	/**
	 * 
	
	 * @Method: getIsOnly 
	
	 * 是否唯一 1:唯一 0:不唯一
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getIsOnly();
	
	/**
	 * 
	
	 * @Method: getIsPk 
	
	 * 是否是主键 1:是 0:不是
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getIsPk();
	
	/**
	 * 
	
	 * @Method: getEnmuId 
	
	 * 枚举类型
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getEnmuId();
	
	/**
	 * 
	
	 * @Method: getColType 
	
	 * 数据类型,用于queryList() 方法中查询条件类型判断
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getColType();
	
	/**
	 * 
	
	 * @Method: getSequenceName 
	
	 * 自增生成器
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getSequenceName();
	
	/**
	 * 
	
	 * @Method: getIsAutoincrement 
	
	 * 是否自增 1:自增 
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getIsAutoincrement();
	
	/**
	 * 
	
	 * @Method: getMaxLength 
	
	 * 最大长度
	
	 * @return
	
	 * @return Long
	
	 * @throws
	 */
	public Long getMaxLength();
	
	/**
	 * 
	
	 * @Method: getMinLength 
	
	 * 最小长度
	
	 * @return
	
	 * @return Long
	
	 * @throws
	 */
	public Long getMinLength();
	
	/**
	 * 
	
	 * @Method: getIsWrap 
	
	 * 是否是多行 1:多行 0单行
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getIsWrap();
	
	/**
	 * 
	
	 * @Method: getCheckType 
	
	 * 校验类型 1:是否唯一.2:是否为数字.3:最大长度.4:最小长度.5:是否不为空
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getCheckType();
	
	/**
	 * 
	
	 * @Method: getIsMultiSelected 
	
	 * 枚举类型前提下是否多选 1：多选 0: 单选
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getIsMultiSelected();
	
	/**
	 * 
	
	 * @Method: getDefaultValue 
	
	 * 初始值
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getDefaultValue();
	
	/**
	 * 
	
	 * @Method: getDisplayShow 
	
	 * 检索画面显示标志 1:显示 0:不显示
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getDisplayShow();
	
	/**
	 * 
	
	 * @Method: getEditShow 
	
	 * 新建画面与编辑画面显示标志 1:显示 0:不显示
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getEditShow();
	
	/**
	 * 
	
	 * @Method: getShot 
	
	 * 页面表示顺序
	
	 * @return
	
	 * @return Long
	
	 * @throws
	 */
	public Long getShot();
	
	/**
	 * 
	
	 * @Method: getInputType 
	
	 * 页面输入类型 1:文本框 2:大文本框 3:下拉列表:4:日期控件 5:复选框 6:单选框 7:弹出窗口 8:密码
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getInputType();
	
	/**
	 * 
	
	 * @Method: getIsReadonly 
	
	 * 是否只读 1:只读 0:读写
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getIsReadonly();
	
	/**
	 * 
	
	 * @Method: getRefTable 
	
	 * 关联表
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getRefTable();
	
	/**
	 * 
	
	 * @Method: getRefTableColumnId 
	
	 * 关联表字段
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getRefTableColumnId();
	
	/**
	 * 
	
	 * @Method: getRefTableColumnShowname 
	
	 * 关联表字段显示数据
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getRefTableColumnShowname();
	
	/**
	 * 
	
	 * @Method: getPopWindowFunction 
	
	 * 弹出窗口调用的页面js
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getPopWindowFunction();
	
	/**
	 * 
	
	 * @Method: getIsForInfosearch 
	
	 * 是否用于用户角色信息查询条件 1:是 0:不是
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getIsForInfosearch();
	
	/**
	 * 
	
	 * @Method: getIsDispalyinfoShow 
	
	 * 是否用于用户角色信息列表显示  1:是 0:不是
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getIsDispalyinfoShow();
	
	/**
	 * 
	
	 * @Method: getIsViewinfoShow 
	
	 * 是否用于用户角色信息详细页面显示
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getIsViewinfoShow();
	
	/**
	 * 
	
	 * @Method: getEnumList 
	
	 * 枚举类型前提下, 枚举数据列表
	
	 * @return
	
	 * @return List<IEnum>
	
	 * @throws
	 */
	public List<IEnum> getEnumList(); 
}

