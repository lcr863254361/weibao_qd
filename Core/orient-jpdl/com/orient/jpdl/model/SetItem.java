/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * Class SetItem.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class SetItem implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Internal choice value storage
     */
    private java.lang.Object _choiceValue;

    /**
     * Field _wireObjectGroup.
     */
    private com.orient.jpdl.model.WireObjectGroup _wireObjectGroup;


      //----------------/
     //- Constructors -/
    //----------------/

    public SetItem() {
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
     * Returns the value of field 'wireObjectGroup'.
     * 
     * @return the value of field 'WireObjectGroup'.
     */
    public com.orient.jpdl.model.WireObjectGroup getWireObjectGroup(
    ) {
        return this._wireObjectGroup;
    }

    /**
     * Sets the value of field 'wireObjectGroup'.
     * 
     * @param wireObjectGroup the value of field 'wireObjectGroup'.
     */
    public void setWireObjectGroup(
            final com.orient.jpdl.model.WireObjectGroup wireObjectGroup) {
        this._wireObjectGroup = wireObjectGroup;
        this._choiceValue = wireObjectGroup;
    }

}
