/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * Class WireObjectTypeItem.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class WireObjectTypeItem implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Internal choice value storage
     */
    private java.lang.Object _choiceValue;

    /**
     * Field _descriptionList.
     */
    private java.util.List<java.lang.String> _descriptionList;

    /**
     * Contains one element that describes the factory object.
     */
    private com.orient.jpdl.model.Factory _factory;

    /**
     * Specifies the arguments for a non-default constructor.
     */
    private com.orient.jpdl.model.Constructor _constructor;

    /**
     * The factory method arguments.
     */
    private com.orient.jpdl.model.Arg _arg;

    /**
     * Injects a value into a member field of this object.
     *  Exactly one child element must specify the value.
     */
    private com.orient.jpdl.model.Field _field;

    /**
     * Injects a value through a setter method.
     *  Exactly one child element must specify the value.
     */
    private com.orient.jpdl.model.Property _property;

    /**
     * Invokes a method.
     */
    private com.orient.jpdl.model.Invoke _invoke;


      //----------------/
     //- Constructors -/
    //----------------/

    public WireObjectTypeItem() {
        super();
        this._descriptionList = new java.util.ArrayList<java.lang.String>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vDescription
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addDescription(
            final java.lang.String vDescription)
    throws java.lang.IndexOutOfBoundsException {
        this._descriptionList.add(vDescription);
    }

    /**
     * 
     * 
     * @param index
     * @param vDescription
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addDescription(
            final int index,
            final java.lang.String vDescription)
    throws java.lang.IndexOutOfBoundsException {
        this._descriptionList.add(index, vDescription);
    }

    /**
     * Method enumerateDescription.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<? extends java.lang.String> enumerateDescription(
    ) {
        return java.util.Collections.enumeration(this._descriptionList);
    }

    /**
     * Returns the value of field 'arg'. The field 'arg' has the
     * following description: The factory method arguments.
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
     * Returns the value of field 'constructor'. The field
     * 'constructor' has the following description: Specifies the
     * arguments for a non-default constructor.
     * 
     * @return the value of field 'Constructor'.
     */
    public com.orient.jpdl.model.Constructor getConstructor(
    ) {
        return this._constructor;
    }

    /**
     * Method getDescription.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the java.lang.String at the given index
     */
    public java.lang.String getDescription(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._descriptionList.size()) {
            throw new IndexOutOfBoundsException("getDescription: Index value '" + index + "' not in range [0.." + (this._descriptionList.size() - 1) + "]");
        }

        return (java.lang.String) _descriptionList.get(index);
    }

    /**
     * Method getDescription.Returns the contents of the collection
     * in an Array.  <p>Note:  Just in case the collection contents
     * are changing in another thread, we pass a 0-length Array of
     * the correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public java.lang.String[] getDescription(
    ) {
        java.lang.String[] array = new java.lang.String[0];
        return (java.lang.String[]) this._descriptionList.toArray(array);
    }

    /**
     * Method getDescriptionCount.
     * 
     * @return the size of this collection
     */
    public int getDescriptionCount(
    ) {
        return this._descriptionList.size();
    }

    /**
     * Returns the value of field 'factory'. The field 'factory'
     * has the following description: Contains one element that
     * describes the factory object.
     * 
     * @return the value of field 'Factory'.
     */
    public com.orient.jpdl.model.Factory getFactory(
    ) {
        return this._factory;
    }

    /**
     * Returns the value of field 'field'. The field 'field' has
     * the following description: Injects a value into a member
     * field of this object.
     *  Exactly one child element must specify the value.
     * 
     * @return the value of field 'Field'.
     */
    public com.orient.jpdl.model.Field getField(
    ) {
        return this._field;
    }

    /**
     * Returns the value of field 'invoke'. The field 'invoke' has
     * the following description: Invokes a method.
     * 
     * @return the value of field 'Invoke'.
     */
    public com.orient.jpdl.model.Invoke getInvoke(
    ) {
        return this._invoke;
    }

    /**
     * Returns the value of field 'property'. The field 'property'
     * has the following description: Injects a value through a
     * setter method.
     *  Exactly one child element must specify the value.
     * 
     * @return the value of field 'Property'.
     */
    public com.orient.jpdl.model.Property getProperty(
    ) {
        return this._property;
    }

    /**
     * Method iterateDescription.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<? extends java.lang.String> iterateDescription(
    ) {
        return this._descriptionList.iterator();
    }

    /**
     */
    public void removeAllDescription(
    ) {
        this._descriptionList.clear();
    }

    /**
     * Method removeDescription.
     * 
     * @param vDescription
     * @return true if the object was removed from the collection.
     */
    public boolean removeDescription(
            final java.lang.String vDescription) {
        boolean removed = _descriptionList.remove(vDescription);
        return removed;
    }

    /**
     * Method removeDescriptionAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public java.lang.String removeDescriptionAt(
            final int index) {
        java.lang.Object obj = this._descriptionList.remove(index);
        return (java.lang.String) obj;
    }

    /**
     * Sets the value of field 'arg'. The field 'arg' has the
     * following description: The factory method arguments.
     * 
     * @param arg the value of field 'arg'.
     */
    public void setArg(
            final com.orient.jpdl.model.Arg arg) {
        this._arg = arg;
        this._choiceValue = arg;
    }

    /**
     * Sets the value of field 'constructor'. The field
     * 'constructor' has the following description: Specifies the
     * arguments for a non-default constructor.
     * 
     * @param constructor the value of field 'constructor'.
     */
    public void setConstructor(
            final com.orient.jpdl.model.Constructor constructor) {
        this._constructor = constructor;
        this._choiceValue = constructor;
    }

    /**
     * 
     * 
     * @param index
     * @param vDescription
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setDescription(
            final int index,
            final java.lang.String vDescription)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._descriptionList.size()) {
            throw new IndexOutOfBoundsException("setDescription: Index value '" + index + "' not in range [0.." + (this._descriptionList.size() - 1) + "]");
        }

        this._descriptionList.set(index, vDescription);
    }

    /**
     * 
     * 
     * @param vDescriptionArray
     */
    public void setDescription(
            final java.lang.String[] vDescriptionArray) {
        //-- copy array
        _descriptionList.clear();

        for (int i = 0; i < vDescriptionArray.length; i++) {
                this._descriptionList.add(vDescriptionArray[i]);
        }
    }

    /**
     * Sets the value of field 'factory'. The field 'factory' has
     * the following description: Contains one element that
     * describes the factory object.
     * 
     * @param factory the value of field 'factory'.
     */
    public void setFactory(
            final com.orient.jpdl.model.Factory factory) {
        this._factory = factory;
        this._choiceValue = factory;
    }

    /**
     * Sets the value of field 'field'. The field 'field' has the
     * following description: Injects a value into a member field
     * of this object.
     *  Exactly one child element must specify the value.
     * 
     * @param field the value of field 'field'.
     */
    public void setField(
            final com.orient.jpdl.model.Field field) {
        this._field = field;
        this._choiceValue = field;
    }

    /**
     * Sets the value of field 'invoke'. The field 'invoke' has the
     * following description: Invokes a method.
     * 
     * @param invoke the value of field 'invoke'.
     */
    public void setInvoke(
            final com.orient.jpdl.model.Invoke invoke) {
        this._invoke = invoke;
        this._choiceValue = invoke;
    }

    /**
     * Sets the value of field 'property'. The field 'property' has
     * the following description: Injects a value through a setter
     * method.
     *  Exactly one child element must specify the value.
     * 
     * @param property the value of field 'property'.
     */
    public void setProperty(
            final com.orient.jpdl.model.Property property) {
        this._property = property;
        this._choiceValue = property;
    }

}
