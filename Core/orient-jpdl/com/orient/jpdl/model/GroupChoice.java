/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * Class GroupChoice.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class GroupChoice implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _items.
     */
    private java.util.List<com.orient.jpdl.model.GroupChoiceItem> _items;


      //----------------/
     //- Constructors -/
    //----------------/

    public GroupChoice() {
        super();
        this._items = new java.util.ArrayList<com.orient.jpdl.model.GroupChoiceItem>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vGroupChoiceItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addGroupChoiceItem(
            final com.orient.jpdl.model.GroupChoiceItem vGroupChoiceItem)
    throws java.lang.IndexOutOfBoundsException {
        this._items.add(vGroupChoiceItem);
    }

    /**
     * 
     * 
     * @param index
     * @param vGroupChoiceItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addGroupChoiceItem(
            final int index,
            final com.orient.jpdl.model.GroupChoiceItem vGroupChoiceItem)
    throws java.lang.IndexOutOfBoundsException {
        this._items.add(index, vGroupChoiceItem);
    }

    /**
     * Method enumerateGroupChoiceItem.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<? extends com.orient.jpdl.model.GroupChoiceItem> enumerateGroupChoiceItem(
    ) {
        return java.util.Collections.enumeration(this._items);
    }

    /**
     * Method getGroupChoiceItem.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * com.orient.jpdl.model.GroupChoiceItem at the given index
     */
    public com.orient.jpdl.model.GroupChoiceItem getGroupChoiceItem(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._items.size()) {
            throw new IndexOutOfBoundsException("getGroupChoiceItem: Index value '" + index + "' not in range [0.." + (this._items.size() - 1) + "]");
        }

        return (com.orient.jpdl.model.GroupChoiceItem) _items.get(index);
    }

    /**
     * Method getGroupChoiceItem.Returns the contents of the
     * collection in an Array.  <p>Note:  Just in case the
     * collection contents are changing in another thread, we pass
     * a 0-length Array of the correct type into the API call. 
     * This way we <i>know</i> that the Array returned is of
     * exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public com.orient.jpdl.model.GroupChoiceItem[] getGroupChoiceItem(
    ) {
        com.orient.jpdl.model.GroupChoiceItem[] array = new com.orient.jpdl.model.GroupChoiceItem[0];
        return (com.orient.jpdl.model.GroupChoiceItem[]) this._items.toArray(array);
    }

    /**
     * Method getGroupChoiceItemCount.
     * 
     * @return the size of this collection
     */
    public int getGroupChoiceItemCount(
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
     * Method iterateGroupChoiceItem.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<? extends com.orient.jpdl.model.GroupChoiceItem> iterateGroupChoiceItem(
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
    public void removeAllGroupChoiceItem(
    ) {
        this._items.clear();
    }

    /**
     * Method removeGroupChoiceItem.
     * 
     * @param vGroupChoiceItem
     * @return true if the object was removed from the collection.
     */
    public boolean removeGroupChoiceItem(
            final com.orient.jpdl.model.GroupChoiceItem vGroupChoiceItem) {
        boolean removed = _items.remove(vGroupChoiceItem);
        return removed;
    }

    /**
     * Method removeGroupChoiceItemAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public com.orient.jpdl.model.GroupChoiceItem removeGroupChoiceItemAt(
            final int index) {
        java.lang.Object obj = this._items.remove(index);
        return (com.orient.jpdl.model.GroupChoiceItem) obj;
    }

    /**
     * 
     * 
     * @param index
     * @param vGroupChoiceItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setGroupChoiceItem(
            final int index,
            final com.orient.jpdl.model.GroupChoiceItem vGroupChoiceItem)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._items.size()) {
            throw new IndexOutOfBoundsException("setGroupChoiceItem: Index value '" + index + "' not in range [0.." + (this._items.size() - 1) + "]");
        }

        this._items.set(index, vGroupChoiceItem);
    }

    /**
     * 
     * 
     * @param vGroupChoiceItemArray
     */
    public void setGroupChoiceItem(
            final com.orient.jpdl.model.GroupChoiceItem[] vGroupChoiceItemArray) {
        //-- copy array
        _items.clear();

        for (int i = 0; i < vGroupChoiceItemArray.length; i++) {
                this._items.add(vGroupChoiceItemArray[i]);
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
     * @return the unmarshaled com.orient.jpdl.model.GroupChoice
     */
    public static com.orient.jpdl.model.GroupChoice unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.orient.jpdl.model.GroupChoice) org.exolab.castor.xml.Unmarshaller.unmarshal(com.orient.jpdl.model.GroupChoice.class, reader);
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
