/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * Class StateChoice.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class StateChoice implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _items.
     */
    private java.util.List<com.orient.jpdl.model.StateChoiceItem> _items;


      //----------------/
     //- Constructors -/
    //----------------/

    public StateChoice() {
        super();
        this._items = new java.util.ArrayList<com.orient.jpdl.model.StateChoiceItem>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vStateChoiceItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addStateChoiceItem(
            final com.orient.jpdl.model.StateChoiceItem vStateChoiceItem)
    throws java.lang.IndexOutOfBoundsException {
        this._items.add(vStateChoiceItem);
    }

    /**
     * 
     * 
     * @param index
     * @param vStateChoiceItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addStateChoiceItem(
            final int index,
            final com.orient.jpdl.model.StateChoiceItem vStateChoiceItem)
    throws java.lang.IndexOutOfBoundsException {
        this._items.add(index, vStateChoiceItem);
    }

    /**
     * Method enumerateStateChoiceItem.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<? extends com.orient.jpdl.model.StateChoiceItem> enumerateStateChoiceItem(
    ) {
        return java.util.Collections.enumeration(this._items);
    }

    /**
     * Method getStateChoiceItem.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * com.orient.jpdl.model.StateChoiceItem at the given index
     */
    public com.orient.jpdl.model.StateChoiceItem getStateChoiceItem(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._items.size()) {
            throw new IndexOutOfBoundsException("getStateChoiceItem: Index value '" + index + "' not in range [0.." + (this._items.size() - 1) + "]");
        }

        return (com.orient.jpdl.model.StateChoiceItem) _items.get(index);
    }

    /**
     * Method getStateChoiceItem.Returns the contents of the
     * collection in an Array.  <p>Note:  Just in case the
     * collection contents are changing in another thread, we pass
     * a 0-length Array of the correct type into the API call. 
     * This way we <i>know</i> that the Array returned is of
     * exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public com.orient.jpdl.model.StateChoiceItem[] getStateChoiceItem(
    ) {
        com.orient.jpdl.model.StateChoiceItem[] array = new com.orient.jpdl.model.StateChoiceItem[0];
        return (com.orient.jpdl.model.StateChoiceItem[]) this._items.toArray(array);
    }

    /**
     * Method getStateChoiceItemCount.
     * 
     * @return the size of this collection
     */
    public int getStateChoiceItemCount(
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
     * Method iterateStateChoiceItem.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<? extends com.orient.jpdl.model.StateChoiceItem> iterateStateChoiceItem(
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
    public void removeAllStateChoiceItem(
    ) {
        this._items.clear();
    }

    /**
     * Method removeStateChoiceItem.
     * 
     * @param vStateChoiceItem
     * @return true if the object was removed from the collection.
     */
    public boolean removeStateChoiceItem(
            final com.orient.jpdl.model.StateChoiceItem vStateChoiceItem) {
        boolean removed = _items.remove(vStateChoiceItem);
        return removed;
    }

    /**
     * Method removeStateChoiceItemAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public com.orient.jpdl.model.StateChoiceItem removeStateChoiceItemAt(
            final int index) {
        java.lang.Object obj = this._items.remove(index);
        return (com.orient.jpdl.model.StateChoiceItem) obj;
    }

    /**
     * 
     * 
     * @param index
     * @param vStateChoiceItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setStateChoiceItem(
            final int index,
            final com.orient.jpdl.model.StateChoiceItem vStateChoiceItem)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._items.size()) {
            throw new IndexOutOfBoundsException("setStateChoiceItem: Index value '" + index + "' not in range [0.." + (this._items.size() - 1) + "]");
        }

        this._items.set(index, vStateChoiceItem);
    }

    /**
     * 
     * 
     * @param vStateChoiceItemArray
     */
    public void setStateChoiceItem(
            final com.orient.jpdl.model.StateChoiceItem[] vStateChoiceItemArray) {
        //-- copy array
        _items.clear();

        for (int i = 0; i < vStateChoiceItemArray.length; i++) {
                this._items.add(vStateChoiceItemArray[i]);
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
     * @return the unmarshaled com.orient.jpdl.model.StateChoice
     */
    public static com.orient.jpdl.model.StateChoice unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.orient.jpdl.model.StateChoice) org.exolab.castor.xml.Unmarshaller.unmarshal(com.orient.jpdl.model.StateChoice.class, reader);
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
