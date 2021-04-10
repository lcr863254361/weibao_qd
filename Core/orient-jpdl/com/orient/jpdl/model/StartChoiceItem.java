/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * Class StartChoiceItem.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class StartChoiceItem implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _on.
     */
    private com.orient.jpdl.model.On _on;

    /**
     * Link from one activity to another.
     */
    private com.orient.jpdl.model.Transition _transition;


      //----------------/
     //- Constructors -/
    //----------------/

    public StartChoiceItem() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'on'.
     * 
     * @return the value of field 'On'.
     */
    public com.orient.jpdl.model.On getOn(
    ) {
        return this._on;
    }

    /**
     * Returns the value of field 'transition'. The field
     * 'transition' has the following description: Link from one
     * activity to another.
     * 
     * @return the value of field 'Transition'.
     */
    public com.orient.jpdl.model.Transition getTransition(
    ) {
        return this._transition;
    }

    /**
     * Sets the value of field 'on'.
     * 
     * @param on the value of field 'on'.
     */
    public void setOn(
            final com.orient.jpdl.model.On on) {
        this._on = on;
    }

    /**
     * Sets the value of field 'transition'. The field 'transition'
     * has the following description: Link from one activity to
     * another.
     * 
     * @param transition the value of field 'transition'.
     */
    public void setTransition(
            final com.orient.jpdl.model.Transition transition) {
        this._transition = transition;
    }

}
