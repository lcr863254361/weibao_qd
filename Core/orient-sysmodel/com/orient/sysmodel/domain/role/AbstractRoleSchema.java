package com.orient.sysmodel.domain.role;

import com.orient.metamodel.metadomain.Schema;

// default package



/**
 * AbstractRoleSchema entity provides the base persistence definition of the RoleSchema entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractRoleSchema extends com.orient.sysmodel.domain.BaseBean implements java.io.Serializable {


    // Fields    

     private RoleSchemaId id;

     private Role role;
     
     private Schema schema;

    // Constructors

    /** default constructor */
    public AbstractRoleSchema() {
    }

    
    /** full constructor */
    public AbstractRoleSchema(RoleSchemaId id) {
        this.id = id;
    }

   
    // Property accessors

    public RoleSchemaId getId() {
        return this.id;
    }
    
    public void setId(RoleSchemaId id) {
        this.id = id;
    }


	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}


	/**
	 * schema
	 *
	 * @return  the schema
	 * @since   CodingExample Ver 1.0
	 */
	
	public Schema getSchema() {
		return schema;
	}


	/**
	 * schema
	 *
	 * @param   schema    the schema to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setSchema(Schema schema) {
		this.schema = schema;
	}
   








}