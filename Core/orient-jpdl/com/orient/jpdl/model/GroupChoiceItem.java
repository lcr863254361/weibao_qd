/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * Class GroupChoiceItem.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class GroupChoiceItem implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _activityGroup.
     */
    private com.orient.jpdl.model.ActivityGroup _activityGroup;

    /**
     * Link from one activity to another.
     */
    private com.orient.jpdl.model.Transition _transition;

    /**
     * Events on which listeners can be registered.
     */
    private com.orient.jpdl.model.On _on;

    /**
     * Field _timer.
     */
    private com.orient.jpdl.model.Timer _timer;


      //----------------/
     //- Constructors -/
    //----------------/

    public GroupChoiceItem() {
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
     * Returns the value of field 'timer'.
     * 
     * @return the value of field 'Timer'.
     */
    public com.orient.jpdl.model.Timer getTimer(
    ) {
        return this._timer;
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
     * Sets the value of field 'activityGroup'.
     * 
     * @param activityGroup the value of field 'activityGroup'.
     */
    public void setActivityGroup(
            final com.orient.jpdl.model.ActivityGroup activityGroup) {
        this._activityGroup = activityGroup;
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
     * Sets the value of field 'timer'.
     * 
     * @param timer the value of field 'timer'.
     */
    public void setTimer(
            final com.orient.jpdl.model.Timer timer) {
        this._timer = timer;
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
