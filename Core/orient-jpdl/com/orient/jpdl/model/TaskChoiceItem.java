/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * Class TaskChoiceItem.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class TaskChoiceItem implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _on.
     */
    private com.orient.jpdl.model.On _on;

    /**
     * Field _timer.
     */
    private com.orient.jpdl.model.Timer _timer;

    /**
     * Field _transition.
     */
    private com.orient.jpdl.model.Transition _transition;


      //----------------/
     //- Constructors -/
    //----------------/

    public TaskChoiceItem() {
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
     * Sets the value of field 'on'.
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
     * Sets the value of field 'transition'.
     * 
     * @param transition the value of field 'transition'.
     */
    public void setTransition(
            final com.orient.jpdl.model.Transition transition) {
        this._transition = transition;
    }

}
