/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * Class Timer.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class Timer implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Timer duedate expression that defines the duedate of this
     *  timer relative to the creation time of the timer.
     *  E.g. '2 hours' or '4 business days'.
     */
    private java.lang.String _duedate;

    /**
     * Timer duedate expression that defines repeated scheduling
     *  relative to the last timer fire event.
     *  E.g. '2 hours' or '4 business days'
     */
    private java.lang.String _repeat;

    /**
     * Field _duedatetime.
     */
    private java.lang.String _duedatetime;

    /**
     * Field _description.
     */
    private java.lang.String _description;

    /**
     * Field _eventListenerGroupList.
     */
    private java.util.List<com.orient.jpdl.model.EventListenerGroup> _eventListenerGroupList;


      //----------------/
     //- Constructors -/
    //----------------/

    public Timer() {
        super();
        this._eventListenerGroupList = new java.util.ArrayList<com.orient.jpdl.model.EventListenerGroup>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vEventListenerGroup
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addEventListenerGroup(
            final com.orient.jpdl.model.EventListenerGroup vEventListenerGroup)
    throws java.lang.IndexOutOfBoundsException {
        this._eventListenerGroupList.add(vEventListenerGroup);
    }

    /**
     * 
     * 
     * @param index
     * @param vEventListenerGroup
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addEventListenerGroup(
            final int index,
            final com.orient.jpdl.model.EventListenerGroup vEventListenerGroup)
    throws java.lang.IndexOutOfBoundsException {
        this._eventListenerGroupList.add(index, vEventListenerGroup);
    }

    /**
     * Method enumerateEventListenerGroup.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<? extends com.orient.jpdl.model.EventListenerGroup> enumerateEventListenerGroup(
    ) {
        return java.util.Collections.enumeration(this._eventListenerGroupList);
    }

    /**
     * Returns the value of field 'description'.
     * 
     * @return the value of field 'Description'.
     */
    public java.lang.String getDescription(
    ) {
        return this._description;
    }

    /**
     * Returns the value of field 'duedate'. The field 'duedate'
     * has the following description: Timer duedate expression that
     * defines the duedate of this
     *  timer relative to the creation time of the timer.
     *  E.g. '2 hours' or '4 business days'.
     * 
     * @return the value of field 'Duedate'.
     */
    public java.lang.String getDuedate(
    ) {
        return this._duedate;
    }

    /**
     * Returns the value of field 'duedatetime'.
     * 
     * @return the value of field 'Duedatetime'.
     */
    public java.lang.String getDuedatetime(
    ) {
        return this._duedatetime;
    }

    /**
     * Method getEventListenerGroup.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * com.orient.jpdl.model.EventListenerGroup at the given index
     */
    public com.orient.jpdl.model.EventListenerGroup getEventListenerGroup(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._eventListenerGroupList.size()) {
            throw new IndexOutOfBoundsException("getEventListenerGroup: Index value '" + index + "' not in range [0.." + (this._eventListenerGroupList.size() - 1) + "]");
        }

        return (com.orient.jpdl.model.EventListenerGroup) _eventListenerGroupList.get(index);
    }

    /**
     * Method getEventListenerGroup.Returns the contents of the
     * collection in an Array.  <p>Note:  Just in case the
     * collection contents are changing in another thread, we pass
     * a 0-length Array of the correct type into the API call. 
     * This way we <i>know</i> that the Array returned is of
     * exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public com.orient.jpdl.model.EventListenerGroup[] getEventListenerGroup(
    ) {
        com.orient.jpdl.model.EventListenerGroup[] array = new com.orient.jpdl.model.EventListenerGroup[0];
        return (com.orient.jpdl.model.EventListenerGroup[]) this._eventListenerGroupList.toArray(array);
    }

    /**
     * Method getEventListenerGroupCount.
     * 
     * @return the size of this collection
     */
    public int getEventListenerGroupCount(
    ) {
        return this._eventListenerGroupList.size();
    }

    /**
     * Returns the value of field 'repeat'. The field 'repeat' has
     * the following description: Timer duedate expression that
     * defines repeated scheduling
     *  relative to the last timer fire event.
     *  E.g. '2 hours' or '4 business days'
     * 
     * @return the value of field 'Repeat'.
     */
    public java.lang.String getRepeat(
    ) {
        return this._repeat;
    }

    /**
     * Method isValid.
     * 
     * @return true if this object is valid according to the schema
     */
    public boolean isValid(
    ) {
        try {
            validate();
        } catch (org.exolab.castor.xml.ValidationException vex) {
            return false;
        }
        return true;
    }

    /**
     * Method iterateEventListenerGroup.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<? extends com.orient.jpdl.model.EventListenerGroup> iterateEventListenerGroup(
    ) {
        return this._eventListenerGroupList.iterator();
    }

    /**
     * 
     * 
     * @param out
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     */
    public void marshal(
            final java.io.Writer out)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        org.exolab.castor.xml.Marshaller.marshal(this, out);
    }

    /**
     * 
     * 
     * @param handler
     * @throws java.io.IOException if an IOException occurs during
     * marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     */
    public void marshal(
            final org.xml.sax.ContentHandler handler)
    throws java.io.IOException, org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        org.exolab.castor.xml.Marshaller.marshal(this, handler);
    }

    /**
     */
    public void removeAllEventListenerGroup(
    ) {
        this._eventListenerGroupList.clear();
    }

    /**
     * Method removeEventListenerGroup.
     * 
     * @param vEventListenerGroup
     * @return true if the object was removed from the collection.
     */
    public boolean removeEventListenerGroup(
            final com.orient.jpdl.model.EventListenerGroup vEventListenerGroup) {
        boolean removed = _eventListenerGroupList.remove(vEventListenerGroup);
        return removed;
    }

    /**
     * Method removeEventListenerGroupAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public com.orient.jpdl.model.EventListenerGroup removeEventListenerGroupAt(
            final int index) {
        java.lang.Object obj = this._eventListenerGroupList.remove(index);
        return (com.orient.jpdl.model.EventListenerGroup) obj;
    }

    /**
     * Sets the value of field 'description'.
     * 
     * @param description the value of field 'description'.
     */
    public void setDescription(
            final java.lang.String description) {
        this._description = description;
    }

    /**
     * Sets the value of field 'duedate'. The field 'duedate' has
     * the following description: Timer duedate expression that
     * defines the duedate of this
     *  timer relative to the creation time of the timer.
     *  E.g. '2 hours' or '4 business days'.
     * 
     * @param duedate the value of field 'duedate'.
     */
    public void setDuedate(
            final java.lang.String duedate) {
        this._duedate = duedate;
    }

    /**
     * Sets the value of field 'duedatetime'.
     * 
     * @param duedatetime the value of field 'duedatetime'.
     */
    public void setDuedatetime(
            final java.lang.String duedatetime) {
        this._duedatetime = duedatetime;
    }

    /**
     * 
     * 
     * @param index
     * @param vEventListenerGroup
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setEventListenerGroup(
            final int index,
            final com.orient.jpdl.model.EventListenerGroup vEventListenerGroup)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._eventListenerGroupList.size()) {
            throw new IndexOutOfBoundsException("setEventListenerGroup: Index value '" + index + "' not in range [0.." + (this._eventListenerGroupList.size() - 1) + "]");
        }

        this._eventListenerGroupList.set(index, vEventListenerGroup);
    }

    /**
     * 
     * 
     * @param vEventListenerGroupArray
     */
    public void setEventListenerGroup(
            final com.orient.jpdl.model.EventListenerGroup[] vEventListenerGroupArray) {
        //-- copy array
        _eventListenerGroupList.clear();

        for (int i = 0; i < vEventListenerGroupArray.length; i++) {
                this._eventListenerGroupList.add(vEventListenerGroupArray[i]);
        }
    }

    /**
     * Sets the value of field 'repeat'. The field 'repeat' has the
     * following description: Timer duedate expression that defines
     * repeated scheduling
     *  relative to the last timer fire event.
     *  E.g. '2 hours' or '4 business days'
     * 
     * @param repeat the value of field 'repeat'.
     */
    public void setRepeat(
            final java.lang.String repeat) {
        this._repeat = repeat;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled com.orient.jpdl.model.Timer
     */
    public static com.orient.jpdl.model.Timer unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.orient.jpdl.model.Timer) org.exolab.castor.xml.Unmarshaller.unmarshal(com.orient.jpdl.model.Timer.class, reader);
    }

    /**
     * 
     * 
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     */
    public void validate(
    )
    throws org.exolab.castor.xml.ValidationException {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    }

}
