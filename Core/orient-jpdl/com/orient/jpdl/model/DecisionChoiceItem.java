/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * Class DecisionChoiceItem.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class DecisionChoiceItem implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _onList.
     */
    private java.util.List<com.orient.jpdl.model.On> _onList;

    /**
     * Field _transitionList.
     */
    private java.util.List<com.orient.jpdl.model.Transition> _transitionList;


      //----------------/
     //- Constructors -/
    //----------------/

    public DecisionChoiceItem() {
        super();
        this._onList = new java.util.ArrayList<com.orient.jpdl.model.On>();
        this._transitionList = new java.util.ArrayList<com.orient.jpdl.model.Transition>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vOn
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addOn(
            final com.orient.jpdl.model.On vOn)
    throws java.lang.IndexOutOfBoundsException {
        this._onList.add(vOn);
    }

    /**
     * 
     * 
     * @param index
     * @param vOn
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addOn(
            final int index,
            final com.orient.jpdl.model.On vOn)
    throws java.lang.IndexOutOfBoundsException {
        this._onList.add(index, vOn);
    }

    /**
     * 
     * 
     * @param vTransition
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addTransition(
            final com.orient.jpdl.model.Transition vTransition)
    throws java.lang.IndexOutOfBoundsException {
        this._transitionList.add(vTransition);
    }

    /**
     * 
     * 
     * @param index
     * @param vTransition
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addTransition(
            final int index,
            final com.orient.jpdl.model.Transition vTransition)
    throws java.lang.IndexOutOfBoundsException {
        this._transitionList.add(index, vTransition);
    }

    /**
     * Method enumerateOn.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<? extends com.orient.jpdl.model.On> enumerateOn(
    ) {
        return java.util.Collections.enumeration(this._onList);
    }

    /**
     * Method enumerateTransition.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<? extends com.orient.jpdl.model.Transition> enumerateTransition(
    ) {
        return java.util.Collections.enumeration(this._transitionList);
    }

    /**
     * Method getOn.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the com.orient.jpdl.model.On at the
     * given index
     */
    public com.orient.jpdl.model.On getOn(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._onList.size()) {
            throw new IndexOutOfBoundsException("getOn: Index value '" + index + "' not in range [0.." + (this._onList.size() - 1) + "]");
        }

        return (com.orient.jpdl.model.On) _onList.get(index);
    }

    /**
     * Method getOn.Returns the contents of the collection in an
     * Array.  <p>Note:  Just in case the collection contents are
     * changing in another thread, we pass a 0-length Array of the
     * correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public com.orient.jpdl.model.On[] getOn(
    ) {
        com.orient.jpdl.model.On[] array = new com.orient.jpdl.model.On[0];
        return (com.orient.jpdl.model.On[]) this._onList.toArray(array);
    }

    /**
     * Method getOnCount.
     * 
     * @return the size of this collection
     */
    public int getOnCount(
    ) {
        return this._onList.size();
    }

    /**
     * Method getTransition.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the com.orient.jpdl.model.Transition at
     * the given index
     */
    public com.orient.jpdl.model.Transition getTransition(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._transitionList.size()) {
            throw new IndexOutOfBoundsException("getTransition: Index value '" + index + "' not in range [0.." + (this._transitionList.size() - 1) + "]");
        }

        return (com.orient.jpdl.model.Transition) _transitionList.get(index);
    }

    /**
     * Method getTransition.Returns the contents of the collection
     * in an Array.  <p>Note:  Just in case the collection contents
     * are changing in another thread, we pass a 0-length Array of
     * the correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public com.orient.jpdl.model.Transition[] getTransition(
    ) {
        com.orient.jpdl.model.Transition[] array = new com.orient.jpdl.model.Transition[0];
        return (com.orient.jpdl.model.Transition[]) this._transitionList.toArray(array);
    }

    /**
     * Method getTransitionCount.
     * 
     * @return the size of this collection
     */
    public int getTransitionCount(
    ) {
        return this._transitionList.size();
    }

    /**
     * Method iterateOn.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<? extends com.orient.jpdl.model.On> iterateOn(
    ) {
        return this._onList.iterator();
    }

    /**
     * Method iterateTransition.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<? extends com.orient.jpdl.model.Transition> iterateTransition(
    ) {
        return this._transitionList.iterator();
    }

    /**
     */
    public void removeAllOn(
    ) {
        this._onList.clear();
    }

    /**
     */
    public void removeAllTransition(
    ) {
        this._transitionList.clear();
    }

    /**
     * Method removeOn.
     * 
     * @param vOn
     * @return true if the object was removed from the collection.
     */
    public boolean removeOn(
            final com.orient.jpdl.model.On vOn) {
        boolean removed = _onList.remove(vOn);
        return removed;
    }

    /**
     * Method removeOnAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public com.orient.jpdl.model.On removeOnAt(
            final int index) {
        java.lang.Object obj = this._onList.remove(index);
        return (com.orient.jpdl.model.On) obj;
    }

    /**
     * Method removeTransition.
     * 
     * @param vTransition
     * @return true if the object was removed from the collection.
     */
    public boolean removeTransition(
            final com.orient.jpdl.model.Transition vTransition) {
        boolean removed = _transitionList.remove(vTransition);
        return removed;
    }

    /**
     * Method removeTransitionAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public com.orient.jpdl.model.Transition removeTransitionAt(
            final int index) {
        java.lang.Object obj = this._transitionList.remove(index);
        return (com.orient.jpdl.model.Transition) obj;
    }

    /**
     * 
     * 
     * @param index
     * @param vOn
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setOn(
            final int index,
            final com.orient.jpdl.model.On vOn)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._onList.size()) {
            throw new IndexOutOfBoundsException("setOn: Index value '" + index + "' not in range [0.." + (this._onList.size() - 1) + "]");
        }

        this._onList.set(index, vOn);
    }

    /**
     * 
     * 
     * @param vOnArray
     */
    public void setOn(
            final com.orient.jpdl.model.On[] vOnArray) {
        //-- copy array
        _onList.clear();

        for (int i = 0; i < vOnArray.length; i++) {
                this._onList.add(vOnArray[i]);
        }
    }

    /**
     * 
     * 
     * @param index
     * @param vTransition
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setTransition(
            final int index,
            final com.orient.jpdl.model.Transition vTransition)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._transitionList.size()) {
            throw new IndexOutOfBoundsException("setTransition: Index value '" + index + "' not in range [0.." + (this._transitionList.size() - 1) + "]");
        }

        this._transitionList.set(index, vTransition);
    }

    /**
     * 
     * 
     * @param vTransitionArray
     */
    public void setTransition(
            final com.orient.jpdl.model.Transition[] vTransitionArray) {
        //-- copy array
        _transitionList.clear();

        for (int i = 0; i < vTransitionArray.length; i++) {
                this._transitionList.add(vTransitionArray[i]);
        }
    }

}
