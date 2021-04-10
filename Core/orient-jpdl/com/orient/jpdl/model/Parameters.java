/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * Query parameters.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class Parameters implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _wireObjectGroupList.
     */
    private java.util.List<com.orient.jpdl.model.WireObjectGroup> _wireObjectGroupList;


      //----------------/
     //- Constructors -/
    //----------------/

    public Parameters() {
        super();
        this._wireObjectGroupList = new java.util.ArrayList<com.orient.jpdl.model.WireObjectGroup>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vWireObjectGroup
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addWireObjectGroup(
            final com.orient.jpdl.model.WireObjectGroup vWireObjectGroup)
    throws java.lang.IndexOutOfBoundsException {
        this._wireObjectGroupList.add(vWireObjectGroup);
    }

    /**
     * 
     * 
     * @param index
     * @param vWireObjectGroup
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addWireObjectGroup(
            final int index,
            final com.orient.jpdl.model.WireObjectGroup vWireObjectGroup)
    throws java.lang.IndexOutOfBoundsException {
        this._wireObjectGroupList.add(index, vWireObjectGroup);
    }

    /**
     * Method enumerateWireObjectGroup.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<? extends com.orient.jpdl.model.WireObjectGroup> enumerateWireObjectGroup(
    ) {
        return java.util.Collections.enumeration(this._wireObjectGroupList);
    }

    /**
     * Method getWireObjectGroup.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * com.orient.jpdl.model.WireObjectGroup at the given index
     */
    public com.orient.jpdl.model.WireObjectGroup getWireObjectGroup(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._wireObjectGroupList.size()) {
            throw new IndexOutOfBoundsException("getWireObjectGroup: Index value '" + index + "' not in range [0.." + (this._wireObjectGroupList.size() - 1) + "]");
        }

        return (com.orient.jpdl.model.WireObjectGroup) _wireObjectGroupList.get(index);
    }

    /**
     * Method getWireObjectGroup.Returns the contents of the
     * collection in an Array.  <p>Note:  Just in case the
     * collection contents are changing in another thread, we pass
     * a 0-length Array of the correct type into the API call. 
     * This way we <i>know</i> that the Array returned is of
     * exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public com.orient.jpdl.model.WireObjectGroup[] getWireObjectGroup(
    ) {
        com.orient.jpdl.model.WireObjectGroup[] array = new com.orient.jpdl.model.WireObjectGroup[0];
        return (com.orient.jpdl.model.WireObjectGroup[]) this._wireObjectGroupList.toArray(array);
    }

    /**
     * Method getWireObjectGroupCount.
     * 
     * @return the size of this collection
     */
    public int getWireObjectGroupCount(
    ) {
        return this._wireObjectGroupList.size();
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
     * Method iterateWireObjectGroup.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<? extends com.orient.jpdl.model.WireObjectGroup> iterateWireObjectGroup(
    ) {
        return this._wireObjectGroupList.iterator();
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
    public void removeAllWireObjectGroup(
    ) {
        this._wireObjectGroupList.clear();
    }

    /**
     * Method removeWireObjectGroup.
     * 
     * @param vWireObjectGroup
     * @return true if the object was removed from the collection.
     */
    public boolean removeWireObjectGroup(
            final com.orient.jpdl.model.WireObjectGroup vWireObjectGroup) {
        boolean removed = _wireObjectGroupList.remove(vWireObjectGroup);
        return removed;
    }

    /**
     * Method removeWireObjectGroupAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public com.orient.jpdl.model.WireObjectGroup removeWireObjectGroupAt(
            final int index) {
        java.lang.Object obj = this._wireObjectGroupList.remove(index);
        return (com.orient.jpdl.model.WireObjectGroup) obj;
    }

    /**
     * 
     * 
     * @param index
     * @param vWireObjectGroup
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setWireObjectGroup(
            final int index,
            final com.orient.jpdl.model.WireObjectGroup vWireObjectGroup)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._wireObjectGroupList.size()) {
            throw new IndexOutOfBoundsException("setWireObjectGroup: Index value '" + index + "' not in range [0.." + (this._wireObjectGroupList.size() - 1) + "]");
        }

        this._wireObjectGroupList.set(index, vWireObjectGroup);
    }

    /**
     * 
     * 
     * @param vWireObjectGroupArray
     */
    public void setWireObjectGroup(
            final com.orient.jpdl.model.WireObjectGroup[] vWireObjectGroupArray) {
        //-- copy array
        _wireObjectGroupList.clear();

        for (int i = 0; i < vWireObjectGroupArray.length; i++) {
                this._wireObjectGroupList.add(vWireObjectGroupArray[i]);
        }
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled com.orient.jpdl.model.Parameters
     */
    public static com.orient.jpdl.model.Parameters unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.orient.jpdl.model.Parameters) org.exolab.castor.xml.Unmarshaller.unmarshal(com.orient.jpdl.model.Parameters.class, reader);
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
