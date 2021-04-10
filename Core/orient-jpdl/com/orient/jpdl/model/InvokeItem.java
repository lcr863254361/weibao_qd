/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * Class InvokeItem.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class InvokeItem implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Internal choice value storage
     */
    private java.lang.Object _choiceValue;

    /**
     * Field _arg.
     */
    private com.orient.jpdl.model.Arg _arg;


      //----------------/
     //- Constructors -/
    //----------------/

    public InvokeItem() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'arg'.
     * 
     * @return the value of field 'Arg'.
     */
    public com.orient.jpdl.model.Arg getArg(
    ) {
        return this._arg;
    }

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
     * Sets the value of field 'arg'.
     * 
     * @param arg the value of field 'arg'.
     */
    public void setArg(
            final com.orient.jpdl.model.Arg arg) {
        this._arg = arg;
        this._choiceValue = arg;
    }

}
