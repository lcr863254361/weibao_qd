/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * java.util.List instance.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class List implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The name of the object. Optional, serves as an identifier
     *  to refer to this object from other object descriptions.
     *  Also used to fetch the object programmatically.
     */
    private java.lang.String _name;

    /**
     * List implementation class.
     */
    private java.lang.String _clazz = "java.util.ArrayList";

    /**
     * Indicates if this collection should be synchronized
     *  with Collections.synchronizedList()
     */
    private com.orient.jpdl.model.types.BooleanValueType _synchronized = com.orient.jpdl.model.types.BooleanValueType.fromValue("false");

    /**
     * Field _items.
     */
    private java.util.List<com.orient.jpdl.model.ListItem> _items;


      //----------------/
     //- Constructors -/
    //----------------/

    public List() {
        super();
        setClazz("java.util.ArrayList");
        setSynchronized(com.orient.jpdl.model.types.BooleanValueType.fromValue("false"));
        this._items = new java.util.ArrayList<com.orient.jpdl.model.ListItem>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vListItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addListItem(
            final com.orient.jpdl.model.ListItem vListItem)
    throws java.lang.IndexOutOfBoundsException {
        this._items.add(vListItem);
    }

    /**
     * 
     * 
     * @param index
     * @param vListItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addListItem(
            final int index,
            final com.orient.jpdl.model.ListItem vListItem)
    throws java.lang.IndexOutOfBoundsException {
        this._items.add(index, vListItem);
    }

    /**
     * Method enumerateListItem.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<? extends com.orient.jpdl.model.ListItem> enumerateListItem(
    ) {
        return java.util.Collections.enumeration(this._items);
    }

    /**
     * Returns the value of field 'clazz'. The field 'clazz' has
     * the following description: List implementation class.
     * 
     * @return the value of field 'Clazz'.
     */
    public java.lang.String getClazz(
    ) {
        return this._clazz;
    }

    /**
     * Method getListItem.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the com.orient.jpdl.model.ListItem at
     * the given index
     */
    public com.orient.jpdl.model.ListItem getListItem(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._items.size()) {
            throw new IndexOutOfBoundsException("getListItem: Index value '" + index + "' not in range [0.." + (this._items.size() - 1) + "]");
        }

        return (com.orient.jpdl.model.ListItem) _items.get(index);
    }

    /**
     * Method getListItem.Returns the contents of the collection in
     * an Array.  <p>Note:  Just in case the collection contents
     * are changing in another thread, we pass a 0-length Array of
     * the correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public com.orient.jpdl.model.ListItem[] getListItem(
    ) {
        com.orient.jpdl.model.ListItem[] array = new com.orient.jpdl.model.ListItem[0];
        return (com.orient.jpdl.model.ListItem[]) this._items.toArray(array);
    }

    /**
     * Method getListItemCount.
     * 
     * @return the size of this collection
     */
    public int getListItemCount(
    ) {
        return this._items.size();
    }

    /**
     * Returns the value of field 'name'. The field 'name' has the
     * following description: The name of the object. Optional,
     * serves as an identifier
     *  to refer to this object from other object descriptions.
     *  Also used to fetch the object programmatically.
     * 
     * @return the value of field 'Name'.
     */
    public java.lang.String getName(
    ) {
        return this._name;
    }

    /**
     * Returns the value of field 'synchronized'. The field
     * 'synchronized' has the following description: Indicates if
     * this collection should be synchronized
     *  with Collections.synchronizedList()
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
     * Method iterateListItem.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<? extends com.orient.jpdl.model.ListItem> iterateListItem(
    ) {
        return this._items.iterator();
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
    public void removeAllListItem(
    ) {
        this._items.clear();
    }

    /**
     * Method removeListItem.
     * 
     * @param vListItem
     * @return true if the object was removed from the collection.
     */
    public boolean removeListItem(
            final com.orient.jpdl.model.ListItem vListItem) {
        boolean removed = _items.remove(vListItem);
        return removed;
    }

    /**
     * Method removeListItemAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public com.orient.jpdl.model.ListItem removeListItemAt(
            final int index) {
        java.lang.Object obj = this._items.remove(index);
        return (com.orient.jpdl.model.ListItem) obj;
    }

    /**
     * Sets the value of field 'clazz'. The field 'clazz' has the
     * following description: List implementation class.
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
     * @param vListItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setListItem(
            final int index,
            final com.orient.jpdl.model.ListItem vListItem)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._items.size()) {
            throw new IndexOutOfBoundsException("setListItem: Index value '" + index + "' not in range [0.." + (this._items.size() - 1) + "]");
        }

        this._items.set(index, vListItem);
    }

    /**
     * 
     * 
     * @param vListItemArray
     */
    public void setListItem(
            final com.orient.jpdl.model.ListItem[] vListItemArray) {
        //-- copy array
        _items.clear();

        for (int i = 0; i < vListItemArray.length; i++) {
                this._items.add(vListItemArray[i]);
        }
    }

    /**
     * Sets the value of field 'name'. The field 'name' has the
     * following description: The name of the object. Optional,
     * serves as an identifier
     *  to refer to this object from other object descriptions.
     *  Also used to fetch the object programmatically.
     * 
     * @param name the value of field 'name'.
     */
    public void setName(
            final java.lang.String name) {
        this._name = name;
    }

    /**
     * Sets the value of field 'synchronized'. The field
     * 'synchronized' has the following description: Indicates if
     * this collection should be synchronized
     *  with Collections.synchronizedList()
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
     * @return the unmarshaled com.orient.jpdl.model.List
     */
    public static com.orient.jpdl.model.List unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.orient.jpdl.model.List) org.exolab.castor.xml.Unmarshaller.unmarshal(com.orient.jpdl.model.List.class, reader);
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
