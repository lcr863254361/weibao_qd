/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * Class On.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class On implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Event identification. start, end, take or
     *  any other custom event.
     */
    private java.lang.String _event;

    /**
     * Field _continue.
     */
    private com.orient.jpdl.model.types.ContinueType _continue = com.orient.jpdl.model.types.ContinueType.fromValue("sync");

    /**
     * Field _timer.
     */
    private com.orient.jpdl.model.Timer _timer;

    /**
     * List of event listeners that will
     *  be notified when the event is fired
     */
    private java.util.List<com.orient.jpdl.model.EventListenerGroup> _eventListenerGroupList;


      //----------------/
     //- Constructors -/
    //----------------/

    public On() {
        super();
        setContinue(com.orient.jpdl.model.types.ContinueType.fromValue("sync"));
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
     * Returns the value of field 'continue'.
     * 
     * @return the value of field 'Continue'.
     */
    public com.orient.jpdl.model.types.ContinueType getContinue(
    ) {
        return this._continue;
    }

    /**
     * Returns the value of field 'event'. The field 'event' has
     * the following description: Event identification. start, end,
     * take or
     *  any other custom event.
     * 
     * @return the value of field 'Event'.
     */
    public java.lang.String getEvent(
    ) {
        return this._event;
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
     * Returns the value of field 'timer'.
     * 
     * @return the value of field 'Timer'.
     */
    public com.orient.jpdl.model.Timer getTimer(
    ) {
        return this._timer;
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
     * Sets the value of field 'continue'.
     * 
     * @param _continue
     * @param continue the value of field 'continue'.
     */
    public void setContinue(
            final com.orient.jpdl.model.types.ContinueType _continue) {
        this._continue = _continue;
    }

    /**
     * Sets the value of field 'event'. The field 'event' has the
     * following description: Event identification. start, end,
     * take or
     *  any other custom event.
     * 
     * @param event the value of field 'event'.
     */
    public void setEvent(
            final java.lang.String event) {
        this._event = event;
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
     * Sets the value of field 'timer'.
     * 
     * @param timer the value of field 'timer'.
     */
    public void setTimer(
            final com.orient.jpdl.model.Timer timer) {
        this._timer = timer;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled com.orient.jpdl.model.On
     */
    public static com.orient.jpdl.model.On unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.orient.jpdl.model.On) org.exolab.castor.xml.Unmarshaller.unmarshal(com.orient.jpdl.model.On.class, reader);
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
