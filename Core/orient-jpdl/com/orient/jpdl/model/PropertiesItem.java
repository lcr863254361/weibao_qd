/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * Class PropertiesItem.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class PropertiesItem implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Internal choice value storage
     */
    private java.lang.Object _choiceValue;

    /**
     * Field _property.
     */
    private com.orient.jpdl.model.Property _property;


      //----------------/
     //- Constructors -/
    //----------------/

    public PropertiesItem() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'choiceValue'. The field
     * 'choiceValue' has the following description: Internal choice
     * value storage
     * 
     * @return the value of field 'ChoiceValue'.
     */
    public java.lang.Object getChoiceValue(
    ) {
        return this._choiceValue;
    }

    /**
     * Returns the value of field 'property'.
     * 
     * @return the value of field 'Property'.
     */
    public com.orient.jpdl.model.Property getProperty(
    ) {
        return this._property;
    }

    /**
     * Sets the value of field 'property'.
     * 
     * @param property the value of field 'property'.
     */
    public void setProperty(
            final com.orient.jpdl.model.Property property) {
        this._property = property;
        this._choiceValue = property;
    }

}
