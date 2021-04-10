/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * Invokes a method.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class Invoke implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Method name.
     */
    private java.lang.String _method;

    /**
     * Field _items.
     */
    private java.util.List<com.orient.jpdl.model.InvokeItem> _items;


      //----------------/
     //- Constructors -/
    //----------------/

    public Invoke() {
        super();
        this._items = new java.util.ArrayList<com.orient.jpdl.model.InvokeItem>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vInvokeItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addInvokeItem(
            final com.orient.jpdl.model.InvokeItem vInvokeItem)
    throws java.lang.IndexOutOfBoundsException {
        this._items.add(vInvokeItem);
    }

    /**
     * 
     * 
     * @param index
     * @param vInvokeItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addInvokeItem(
            final int index,
            final com.orient.jpdl.model.InvokeItem vInvokeItem)
    throws java.lang.IndexOutOfBoundsException {
        this._items.add(index, vInvokeItem);
    }

    /**
     * Method enumerateInvokeItem.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<? extends com.orient.jpdl.model.InvokeItem> enumerateInvokeItem(
    ) {
        return java.util.Collections.enumeration(this._items);
    }

    /**
     * Method getInvokeItem.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the com.orient.jpdl.model.InvokeItem at
     * the given index
     */
    public com.orient.jpdl.model.InvokeItem getInvokeItem(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._items.size()) {
            throw new IndexOutOfBoundsException("getInvokeItem: Index value '" + index + "' not in range [0.." + (this._items.size() - 1) + "]");
        }

        return (com.orient.jpdl.model.InvokeItem) _items.get(index);
    }

    /**
     * Method getInvokeItem.Returns the contents of the collection
     * in an Array.  <p>Note:  Just in case the collection contents
     * are changing in another thread, we pass a 0-length Array of
     * the correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public com.orient.jpdl.model.InvokeItem[] getInvokeItem(
    ) {
        com.orient.jpdl.model.InvokeItem[] array = new com.orient.jpdl.model.InvokeItem[0];
        return (com.orient.jpdl.model.InvokeItem[]) this._items.toArray(array);
    }

    /**
     * Method getInvokeItemCount.
     * 
     * @return the size of this collection
     */
    public int getInvokeItemCount(
    ) {
        return this._items.size();
    }

    /**
     * Returns the value of field 'method'. The field 'method' has
     * the following description: Method name.
     * 
     * @return the value of field 'Method'.
     */
    public java.lang.String getMethod(
    ) {
        return this._method;
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
     * Method iterateInvokeItem.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<? extends com.orient.jpdl.model.InvokeItem> iterateInvokeItem(
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
    public void removeAllInvokeItem(
    ) {
        this._items.clear();
    }

    /**
     * Method removeInvokeItem.
     * 
     * @param vInvokeItem
     * @return true if the object was removed from the collection.
     */
    public boolean removeInvokeItem(
            final com.orient.jpdl.model.InvokeItem vInvokeItem) {
        boolean removed = _items.remove(vInvokeItem);
        return removed;
    }

    /**
     * Method removeInvokeItemAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public com.orient.jpdl.model.InvokeItem removeInvokeItemAt(
            final int index) {
        java.lang.Object obj = this._items.remove(index);
        return (com.orient.jpdl.model.InvokeItem) obj;
    }

    /**
     * 
     * 
     * @param index
     * @param vInvokeItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setInvokeItem(
            final int index,
            final com.orient.jpdl.model.InvokeItem vInvokeItem)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._items.size()) {
            throw new IndexOutOfBoundsException("setInvokeItem: Index value '" + index + "' not in range [0.." + (this._items.size() - 1) + "]");
        }

        this._items.set(index, vInvokeItem);
    }

    /**
     * 
     * 
     * @param vInvokeItemArray
     */
    public void setInvokeItem(
            final com.orient.jpdl.model.InvokeItem[] vInvokeItemArray) {
        //-- copy array
        _items.clear();

        for (int i = 0; i < vInvokeItemArray.length; i++) {
                this._items.add(vInvokeItemArray[i]);
        }
    }

    /**
     * Sets the value of field 'method'. The field 'method' has the
     * following description: Method name.
     * 
     * @param method the value of field 'method'.
     */
    public void setMethod(
            final java.lang.String method) {
        this._method = method;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled com.orient.jpdl.model.Invoke
     */
    public static com.orient.jpdl.model.Invoke unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.orient.jpdl.model.Invoke) org.exolab.castor.xml.Unmarshaller.unmarshal(com.orient.jpdl.model.Invoke.class, reader);
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
