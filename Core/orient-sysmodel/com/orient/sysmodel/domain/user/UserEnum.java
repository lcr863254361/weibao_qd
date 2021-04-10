package com.orient.sysmodel.domain.user;
// default package



/**
 * UserEnum entity. @author MyEclipse Persistence Tools
 */
public class UserEnum extends AbstractUserEnum implements java.io.Serializable {

    // Constructors

    /** default constructor */
    public UserEnum() {
    }

	/** minimal constructor */
    public UserEnum(String enumId, String value, String displayValue) {
        super(enumId, value, displayValue);        
    }
    
    /** full constructor */
    public UserEnum(String enumId, String value, String displayValue, String imageUrl, String description) {
        super(enumId, value, displayValue, imageUrl, description);        
    }
   
}
