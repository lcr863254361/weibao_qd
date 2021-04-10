/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * Class RulesChoiceItem.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class RulesChoiceItem implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _fact.
     */
    private com.orient.jpdl.model.Fact _fact;

    /**
     * Field _transition.
     */
    private com.orient.jpdl.model.Transition _transition;

    /**
     * Events on which listeners can be registered.
     */
    private com.orient.jpdl.model.On _on;


      //----------------/
     //- Constructors -/
    //----------------/

    public RulesChoiceItem() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'fact'.
     * 
     * @return the value of field 'Fact'.
     */
    public com.orient.jpdl.model.Fact getFact(
    ) {
        return this._fact;
    }

    /**
     * Returns the value of field 'on'. The field 'on' has the
     * following description: Events on which listeners can be
     * registered.
     * 
     * @return the value of field 'On'.
     */
    public com.orient.jpdl.model.On getOn(
    ) {
        return this._on;
    }

    /**
     * Returns the value of field 'transition'.
     * 
     * @return the value of field 'Transition'.
     */
    public com.orient.jpdl.model.Transition getTransition(
    ) {
        return this._transition;
    }

    /**
     * Sets the value of field 'fact'.
     * 
     * @param fact the value of field 'fact'.
     */
    public void setFact(
            final com.orient.jpdl.model.Fact fact) {
        this._fact = fact;
    }

    /**
     * Sets the value of field 'on'. The field 'on' has the
     * following description: Events on which listeners can be
     * registered.
     * 
     * @param on the value of field 'on'.
     */
    public void setOn(
            final com.orient.jpdl.model.On on) {
        this._on = on;
    }

    /**
     * Sets the value of field 'transition'.
     * 
     * @param transition the value of field 'transition'.
     */
    public void setTransition(
            final com.orient.jpdl.model.Transition transition) {
        this._transition = transition;
    }

}
