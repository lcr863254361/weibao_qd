/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * Specifies the arguments for a non-default constructor.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class Constructor implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _items.
     */
    private java.util.List<com.orient.jpdl.model.ConstructorItem> _items;


      //----------------/
     //- Constructors -/
    //----------------/

    public Constructor() {
        super();
        this._items = new java.util.ArrayList<com.orient.jpdl.model.ConstructorItem>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vConstructorItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addConstructorItem(
            final com.orient.jpdl.model.ConstructorItem vConstructorItem)
    throws java.lang.IndexOutOfBoundsException {
        this._items.add(vConstructorItem);
    }

    /**
     * 
     * 
     * @param index
     * @param vConstructorItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addConstructorItem(
            final int index,
            final com.orient.jpdl.model.ConstructorItem vConstructorItem)
    throws java.lang.IndexOutOfBoundsException {
        this._items.add(index, vConstructorItem);
    }

    /**
     * Method enumerateConstructorItem.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<? extends com.orient.jpdl.model.ConstructorItem> enumerateConstructorItem(
    ) {
        return java.util.Collections.enumeration(this._items);
    }

    /**
     * Method getConstructorItem.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * com.orient.jpdl.model.ConstructorItem at the given index
     */
    public com.orient.jpdl.model.ConstructorItem getConstructorItem(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._items.size()) {
            throw new IndexOutOfBoundsException("getConstructorItem: Index value '" + index + "' not in range [0.." + (this._items.size() - 1) + "]");
        }

        return (com.orient.jpdl.model.ConstructorItem) _items.get(index);
    }

    /**
     * Method getConstructorItem.Returns the contents of the
     * collection in an Array.  <p>Note:  Just in case the
     * collection contents are changing in another thread, we pass
     * a 0-length Array of the correct type into the API call. 
     * This way we <i>know</i> that the Array returned is of
     * exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public com.orient.jpdl.model.ConstructorItem[] getConstructorItem(
    ) {
        com.orient.jpdl.model.ConstructorItem[] array = new com.orient.jpdl.model.ConstructorItem[0];
        return (com.orient.jpdl.model.ConstructorItem[]) this._items.toArray(array);
    }

    /**
     * Method getConstructorItemCount.
     * 
     * @return the size of this collection
     */
    public int getConstructorItemCount(
    ) {
        return this._items.size();
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
     * Method iterateConstructorItem.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<? extends com.orient.jpdl.model.ConstructorItem> iterateConstructorItem(
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
    public void removeAllConstructorItem(
    ) {
        this._items.clear();
    }

    /**
     * Method removeConstructorItem.
     * 
     * @param vConstructorItem
     * @return true if the object was removed from the collection.
     */
    public boolean removeConstructorItem(
            final com.orient.jpdl.model.ConstructorItem vConstructorItem) {
        boolean removed = _items.remove(vConstructorItem);
        return removed;
    }

    /**
     * Method removeConstructorItemAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public com.orient.jpdl.model.ConstructorItem removeConstructorItemAt(
            final int index) {
        java.lang.Object obj = this._items.remove(index);
        return (com.orient.jpdl.model.ConstructorItem) obj;
    }

    /**
     * 
     * 
     * @param index
     * @param vConstructorItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setConstructorItem(
            final int index,
            final com.orient.jpdl.model.ConstructorItem vConstructorItem)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._items.size()) {
            throw new IndexOutOfBoundsException("setConstructorItem: Index value '" + index + "' not in range [0.." + (this._items.size() - 1) + "]");
        }

        this._items.set(index, vConstructorItem);
    }

    /**
     * 
     * 
     * @param vConstructorItemArray
     */
    public void setConstructorItem(
            final com.orient.jpdl.model.ConstructorItem[] vConstructorItemArray) {
        //-- copy array
        _items.clear();

        for (int i = 0; i < vConstructorItemArray.length; i++) {
                this._items.add(vConstructorItemArray[i]);
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
     * @return the unmarshaled com.orient.jpdl.model.Constructor
     */
    public static com.orient.jpdl.model.Constructor unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.orient.jpdl.model.Constructor) org.exolab.castor.xml.Unmarshaller.unmarshal(com.orient.jpdl.model.Constructor.class, reader);
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
