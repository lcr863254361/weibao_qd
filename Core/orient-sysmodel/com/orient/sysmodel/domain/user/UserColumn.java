package com.orient.sysmodel.domain.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.orient.metamodel.operationinterface.IEnum;

// default package



/**
 * UserColumn entity. @author MyEclipse Persistence Tools
 */
public class UserColumn extends AbstractUserColumn implements java.io.Serializable {

	private Map enumContent = new HashMap();
    // Constructors

    /** default constructor */
    public UserColumn() {
    }

	/** minimal constructor */
    public UserColumn(String displayName, String SColumnName, String colType) {
        super(displayName, SColumnName, colType);        
    }
    
    /** full constructor */
    public UserColumn(String displayName, String SColumnName, String isForSearch, String isNullable, String isOnly, String isPk, String enmuId, String colType, String sequenceName, String isAutoincrement, Long maxLength, Long minLength, String isWrap, String checkType, String isMultiSelected, String defaultValue, String displayShow, String editShow, Long shot, String inputType, String isReadonly, String refTable, String refTableColumnId, String refTableColumnShowname, String popWindowFunction, String isForInfosearch, String isDispalyinfoShow, String isViewinfoShow) {
        super(displayName, SColumnName, isForSearch, isNullable, isOnly, isPk, enmuId, colType, sequenceName, isAutoincrement, maxLength, minLength, isWrap, checkType, isMultiSelected, defaultValue, displayShow, editShow, shot, inputType, isReadonly, refTable, refTableColumnId, refTableColumnShowname, popWindowFunction, isForInfosearch, isDispalyinfoShow, isViewinfoShow);        
    }

	/**
	 * enumContent
	 *
	 * @return  the enumContent
	 * @since   CodingExample Ver 1.0
	 */
	
	public Map getEnumContent() {
		return enumContent;
	}

	/**
	 * enumContent
	 *
	 * @param   enumContent    the enumContent to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setEnumContent(Map enumContent) {
		this.enumContent = enumContent;
	}
   
	/**
	 * 
	
	 * @Method: getEnumList 
	
	 * 枚举类型前提下, 枚举数据列表
	
	 * @return
	
	 * @return List<IEnum>
	
	 * @throws
	 */
	public List<IEnum> getEnumList(){
		if(this.getEnmuId()!=null && !this.getEnmuId().equalsIgnoreCase("")){
			List<IEnum> enumList = this.getUserDAOFactory().getEnumDAO().findByRestrictionId(this.getEnmuId());
			return enumList;
		}
		return new ArrayList<IEnum>();
	}
}
