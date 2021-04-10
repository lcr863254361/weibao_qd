/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * Class ProcessChoiceItem.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class ProcessChoiceItem implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Process role.
     */
    private com.orient.jpdl.model.Swimlane _swimlane;

    /**
     * Field _on.
     */
    private com.orient.jpdl.model.On _on;

    /**
     * Field _timer.
     */
    private com.orient.jpdl.model.Timer _timer;

    /**
     * Field _variable.
     */
    private com.orient.jpdl.model.Variable _variable;

    /**
     * Field _activityGroup.
     */
    private com.orient.jpdl.model.ActivityGroup _activityGroup;


      //----------------/
     //- Constructors -/
    //----------------/

    public ProcessChoiceItem() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'activityGroup'.
     * 
     * @return the value of field 'ActivityGroup'.
     */
    public com.orient.jpdl.model.ActivityGroup getActivityGroup(
    ) {
        return this._activityGroup;
    }

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
     * Returns the value of field 'swimlane'. The field 'swimlane'
     * has the following description: Process role.
     * 
     * @return the value of field 'Swimlane'.
     */
    public com.orient.jpdl.model.Swimlane getSwimlane(
    ) {
        return this._swimlane;
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
     * Returns the value of field 'variable'.
     * 
     * @return the value of field 'Variable'.
     */
    public com.orient.jpdl.model.Variable getVariable(
    ) {
        return this._variable;
    }

    /**
     * Sets the value of field 'activityGroup'.
     * 
     * @param activityGroup the value of field 'activityGroup'.
     */
    public void setActivityGroup(
            final com.orient.jpdl.model.ActivityGroup activityGroup) {
        this._activityGroup = activityGroup;
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
     * Sets the value of field 'swimlane'. The field 'swimlane' has
     * the following description: Process role.
     * 
     * @param swimlane the value of field 'swimlane'.
     */
    public void setSwimlane(
            final com.orient.jpdl.model.Swimlane swimlane) {
        this._swimlane = swimlane;
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
     * Sets the value of field 'variable'.
     * 
     * @param variable the value of field 'variable'.
     */
    public void setVariable(
            final com.orient.jpdl.model.Variable variable) {
        this._variable = variable;
    }

}
