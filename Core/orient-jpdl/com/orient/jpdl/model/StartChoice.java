/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * Class StartChoice.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class StartChoice implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _items.
     */
    private java.util.List<com.orient.jpdl.model.StartChoiceItem> _items;


      //----------------/
     //- Constructors -/
    //----------------/

    public StartChoice() {
        super();
        this._items = new java.util.ArrayList<com.orient.jpdl.model.StartChoiceItem>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vStartChoiceItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addStartChoiceItem(
            final com.orient.jpdl.model.StartChoiceItem vStartChoiceItem)
    throws java.lang.IndexOutOfBoundsException {
        this._items.add(vStartChoiceItem);
    }

    /**
     * 
     * 
     * @param index
     * @param vStartChoiceItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addStartChoiceItem(
            final int index,
            final com.orient.jpdl.model.StartChoiceItem vStartChoiceItem)
    throws java.lang.IndexOutOfBoundsException {
        this._items.add(index, vStartChoiceItem);
    }

    /**
     * Method enumerateStartChoiceItem.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<? extends com.orient.jpdl.model.StartChoiceItem> enumerateStartChoiceItem(
    ) {
        return java.util.Collections.enumeration(this._items);
    }

    /**
     * Method getStartChoiceItem.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * com.orient.jpdl.model.StartChoiceItem at the given index
     */
    public com.orient.jpdl.model.StartChoiceItem getStartChoiceItem(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._items.size()) {
            throw new IndexOutOfBoundsException("getStartChoiceItem: Index value '" + index + "' not in range [0.." + (this._items.size() - 1) + "]");
        }

        return (com.orient.jpdl.model.StartChoiceItem) _items.get(index);
    }

    /**
     * Method getStartChoiceItem.Returns the contents of the
     * collection in an Array.  <p>Note:  Just in case the
     * collection contents are changing in another thread, we pass
     * a 0-length Array of the correct type into the API call. 
     * This way we <i>know</i> that the Array returned is of
     * exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public com.orient.jpdl.model.StartChoiceItem[] getStartChoiceItem(
    ) {
        com.orient.jpdl.model.StartChoiceItem[] array = new com.orient.jpdl.model.StartChoiceItem[0];
        return (com.orient.jpdl.model.StartChoiceItem[]) this._items.toArray(array);
    }

    /**
     * Method getStartChoiceItemCount.
     * 
     * @return the size of this collection
     */
    public int getStartChoiceItemCount(
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
     * Method iterateStartChoiceItem.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<? extends com.orient.jpdl.model.StartChoiceItem> iterateStartChoiceItem(
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
    public void removeAllStartChoiceItem(
    ) {
        this._items.clear();
    }

    /**
     * Method removeStartChoiceItem.
     * 
     * @param vStartChoiceItem
     * @return true if the object was removed from the collection.
     */
    public boolean removeStartChoiceItem(
            final com.orient.jpdl.model.StartChoiceItem vStartChoiceItem) {
        boolean removed = _items.remove(vStartChoiceItem);
        return removed;
    }

    /**
     * Method removeStartChoiceItemAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public com.orient.jpdl.model.StartChoiceItem removeStartChoiceItemAt(
            final int index) {
        java.lang.Object obj = this._items.remove(index);
        return (com.orient.jpdl.model.StartChoiceItem) obj;
    }

    /**
     * 
     * 
     * @param index
     * @param vStartChoiceItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setStartChoiceItem(
            final int index,
            final com.orient.jpdl.model.StartChoiceItem vStartChoiceItem)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._items.size()) {
            throw new IndexOutOfBoundsException("setStartChoiceItem: Index value '" + index + "' not in range [0.." + (this._items.size() - 1) + "]");
        }

        this._items.set(index, vStartChoiceItem);
    }

    /**
     * 
     * 
     * @param vStartChoiceItemArray
     */
    public void setStartChoiceItem(
            final com.orient.jpdl.model.StartChoiceItem[] vStartChoiceItemArray) {
        //-- copy array
        _items.clear();

        for (int i = 0; i < vStartChoiceItemArray.length; i++) {
                this._items.add(vStartChoiceItemArray[i]);
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
     * @return the unmarshaled com.orient.jpdl.model.StartChoice
     */
    public static com.orient.jpdl.model.StartChoice unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.orient.jpdl.model.StartChoice) org.exolab.castor.xml.Unmarshaller.unmarshal(com.orient.jpdl.model.StartChoice.class, reader);
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
