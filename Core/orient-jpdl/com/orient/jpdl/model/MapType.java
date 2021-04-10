/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * java.util.Map instance.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class MapType implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Map implementation class.
     */
    private java.lang.String _clazz = "java.util.HashMap";

    /**
     * Indicates if this collection should be synchronized
     *  with Collections.synchronizedCollection()
     */
    private com.orient.jpdl.model.types.BooleanValueType _synchronized = com.orient.jpdl.model.types.BooleanValueType.fromValue("false");

    /**
     * Field _entryList.
     */
    private java.util.List<com.orient.jpdl.model.Entry> _entryList;


      //----------------/
     //- Constructors -/
    //----------------/

    public MapType() {
        super();
        setClazz("java.util.HashMap");
        setSynchronized(com.orient.jpdl.model.types.BooleanValueType.fromValue("false"));
        this._entryList = new java.util.ArrayList<com.orient.jpdl.model.Entry>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vEntry
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addEntry(
            final com.orient.jpdl.model.Entry vEntry)
    throws java.lang.IndexOutOfBoundsException {
        this._entryList.add(vEntry);
    }

    /**
     * 
     * 
     * @param index
     * @param vEntry
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addEntry(
            final int index,
            final com.orient.jpdl.model.Entry vEntry)
    throws java.lang.IndexOutOfBoundsException {
        this._entryList.add(index, vEntry);
    }

    /**
     * Method enumerateEntry.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<? extends com.orient.jpdl.model.Entry> enumerateEntry(
    ) {
        return java.util.Collections.enumeration(this._entryList);
    }

    /**
     * Returns the value of field 'clazz'. The field 'clazz' has
     * the following description: Map implementation class.
     * 
     * @return the value of field 'Clazz'.
     */
    public java.lang.String getClazz(
    ) {
        return this._clazz;
    }

    /**
     * Method getEntry.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the com.orient.jpdl.model.Entry at the
     * given index
     */
    public com.orient.jpdl.model.Entry getEntry(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._entryList.size()) {
            throw new IndexOutOfBoundsException("getEntry: Index value '" + index + "' not in range [0.." + (this._entryList.size() - 1) + "]");
        }

        return (com.orient.jpdl.model.Entry) _entryList.get(index);
    }

    /**
     * Method getEntry.Returns the contents of the collection in an
     * Array.  <p>Note:  Just in case the collection contents are
     * changing in another thread, we pass a 0-length Array of the
     * correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public com.orient.jpdl.model.Entry[] getEntry(
    ) {
        com.orient.jpdl.model.Entry[] array = new com.orient.jpdl.model.Entry[0];
        return (com.orient.jpdl.model.Entry[]) this._entryList.toArray(array);
    }

    /**
     * Method getEntryCount.
     * 
     * @return the size of this collection
     */
    public int getEntryCount(
    ) {
        return this._entryList.size();
    }

    /**
     * Returns the value of field 'synchronized'. The field
     * 'synchronized' has the following description: Indicates if
     * this collection should be synchronized
     *  with Collections.synchronizedCollection()
     * 
     * @return the value of field 'Synchronized'.
     */
    public com.orient.jpdl.model.types.BooleanValueType getSynchronized(
    ) {
        return this._synchronized;
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
     * Method iterateEntry.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<? extends com.orient.jpdl.model.Entry> iterateEntry(
    ) {
        return this._entryList.iterator();
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
    public void removeAllEntry(
    ) {
        this._entryList.clear();
    }

    /**
     * Method removeEntry.
     * 
     * @param vEntry
     * @return true if the object was removed from the collection.
     */
    public boolean removeEntry(
            final com.orient.jpdl.model.Entry vEntry) {
        boolean removed = _entryList.remove(vEntry);
        return removed;
    }

    /**
     * Method removeEntryAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public com.orient.jpdl.model.Entry removeEntryAt(
            final int index) {
        java.lang.Object obj = this._entryList.remove(index);
        return (com.orient.jpdl.model.Entry) obj;
    }

    /**
     * Sets the value of field 'clazz'. The field 'clazz' has the
     * following description: Map implementation class.
     * 
     * @param clazz the value of field 'clazz'.
     */
    public void setClazz(
            final java.lang.String clazz) {
        this._clazz = clazz;
    }

    /**
     * 
     * 
     * @param index
     * @param vEntry
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setEntry(
            final int index,
            final com.orient.jpdl.model.Entry vEntry)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._entryList.size()) {
            throw new IndexOutOfBoundsException("setEntry: Index value '" + index + "' not in range [0.." + (this._entryList.size() - 1) + "]");
        }

        this._entryList.set(index, vEntry);
    }

    /**
     * 
     * 
     * @param vEntryArray
     */
    public void setEntry(
            final com.orient.jpdl.model.Entry[] vEntryArray) {
        //-- copy array
        _entryList.clear();

        for (int i = 0; i < vEntryArray.length; i++) {
                this._entryList.add(vEntryArray[i]);
        }
    }

    /**
     * Sets the value of field 'synchronized'. The field
     * 'synchronized' has the following description: Indicates if
     * this collection should be synchronized
     *  with Collections.synchronizedCollection()
     * 
     * @param _synchronized
     * @param synchronized the value of field 'synchronized'.
     */
    public void setSynchronized(
            final com.orient.jpdl.model.types.BooleanValueType _synchronized) {
        this._synchronized = _synchronized;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled com.orient.jpdl.model.MapType
     */
    public static com.orient.jpdl.model.MapType unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.orient.jpdl.model.MapType) org.exolab.castor.xml.Unmarshaller.unmarshal(com.orient.jpdl.model.MapType.class, reader);
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
