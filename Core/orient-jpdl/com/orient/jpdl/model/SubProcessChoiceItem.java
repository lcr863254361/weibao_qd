/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * Class SubProcessChoiceItem.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class SubProcessChoiceItem implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _parameterIn.
     */
    private com.orient.jpdl.model.ParameterIn _parameterIn;

    /**
     * Field _parameterOut.
     */
    private com.orient.jpdl.model.ParameterOut _parameterOut;

    /**
     * Field _timer.
     */
    private com.orient.jpdl.model.Timer _timer;

    /**
     * Events on which listeners can be registered.
     */
    private com.orient.jpdl.model.On _on;

    /**
     * Field _swimlaneMapping.
     */
    private com.orient.jpdl.model.SwimlaneMapping _swimlaneMapping;

    /**
     * Field _transition.
     */
    private com.orient.jpdl.model.Transition _transition;


      //----------------/
     //- Constructors -/
    //----------------/

    public SubProcessChoiceItem() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

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
     * Returns the value of field 'parameterIn'.
     * 
     * @return the value of field 'ParameterIn'.
     */
    public com.orient.jpdl.model.ParameterIn getParameterIn(
    ) {
        return this._parameterIn;
    }

    /**
     * Returns the value of field 'parameterOut'.
     * 
     * @return the value of field 'ParameterOut'.
     */
    public com.orient.jpdl.model.ParameterOut getParameterOut(
    ) {
        return this._parameterOut;
    }

    /**
     * Returns the value of field 'swimlaneMapping'.
     * 
     * @return the value of field 'SwimlaneMapping'.
     */
    public com.orient.jpdl.model.SwimlaneMapping getSwimlaneMapping(
    ) {
        return this._swimlaneMapping;
    }

    /**
     * Returns the value of field 'timer'.
     * 
     * @return the value of field 'Timer'.
     */
    public com.orient.jpdl.model.Timer getTimer(
    ) {
        return this._timer;
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
     * Sets the value of field 'parameterIn'.
     * 
     * @param parameterIn the value of field 'parameterIn'.
     */
    public void setParameterIn(
            final com.orient.jpdl.model.ParameterIn parameterIn) {
        this._parameterIn = parameterIn;
    }

    /**
     * Sets the value of field 'parameterOut'.
     * 
     * @param parameterOut the value of field 'parameterOut'.
     */
    public void setParameterOut(
            final com.orient.jpdl.model.ParameterOut parameterOut) {
        this._parameterOut = parameterOut;
    }

    /**
     * Sets the value of field 'swimlaneMapping'.
     * 
     * @param swimlaneMapping the value of field 'swimlaneMapping'.
     */
    public void setSwimlaneMapping(
            final com.orient.jpdl.model.SwimlaneMapping swimlaneMapping) {
        this._swimlaneMapping = swimlaneMapping;
    }

    /**
     * Sets the value of field 'timer'.
     * 
     * @param timer the value of field 'timer'.
     */
    public void setTimer(
            final com.orient.jpdl.model.Timer timer) {
        this._timer = timer;
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
