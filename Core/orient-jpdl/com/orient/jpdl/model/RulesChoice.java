/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * Class RulesChoice.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class RulesChoice implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _items.
     */
    private java.util.List<com.orient.jpdl.model.RulesChoiceItem> _items;


      //----------------/
     //- Constructors -/
    //----------------/

    public RulesChoice() {
        super();
        this._items = new java.util.ArrayList<com.orient.jpdl.model.RulesChoiceItem>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vRulesChoiceItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addRulesChoiceItem(
            final com.orient.jpdl.model.RulesChoiceItem vRulesChoiceItem)
    throws java.lang.IndexOutOfBoundsException {
        this._items.add(vRulesChoiceItem);
    }

    /**
     * 
     * 
     * @param index
     * @param vRulesChoiceItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addRulesChoiceItem(
            final int index,
            final com.orient.jpdl.model.RulesChoiceItem vRulesChoiceItem)
    throws java.lang.IndexOutOfBoundsException {
        this._items.add(index, vRulesChoiceItem);
    }

    /**
     * Method enumerateRulesChoiceItem.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<? extends com.orient.jpdl.model.RulesChoiceItem> enumerateRulesChoiceItem(
    ) {
        return java.util.Collections.enumeration(this._items);
    }

    /**
     * Method getRulesChoiceItem.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * com.orient.jpdl.model.RulesChoiceItem at the given index
     */
    public com.orient.jpdl.model.RulesChoiceItem getRulesChoiceItem(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._items.size()) {
            throw new IndexOutOfBoundsException("getRulesChoiceItem: Index value '" + index + "' not in range [0.." + (this._items.size() - 1) + "]");
        }

        return (com.orient.jpdl.model.RulesChoiceItem) _items.get(index);
    }

    /**
     * Method getRulesChoiceItem.Returns the contents of the
     * collection in an Array.  <p>Note:  Just in case the
     * collection contents are changing in another thread, we pass
     * a 0-length Array of the correct type into the API call. 
     * This way we <i>know</i> that the Array returned is of
     * exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public com.orient.jpdl.model.RulesChoiceItem[] getRulesChoiceItem(
    ) {
        com.orient.jpdl.model.RulesChoiceItem[] array = new com.orient.jpdl.model.RulesChoiceItem[0];
        return (com.orient.jpdl.model.RulesChoiceItem[]) this._items.toArray(array);
    }

    /**
     * Method getRulesChoiceItemCount.
     * 
     * @return the size of this collection
     */
    public int getRulesChoiceItemCount(
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
     * Method iterateRulesChoiceItem.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<? extends com.orient.jpdl.model.RulesChoiceItem> iterateRulesChoiceItem(
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
    public void removeAllRulesChoiceItem(
    ) {
        this._items.clear();
    }

    /**
     * Method removeRulesChoiceItem.
     * 
     * @param vRulesChoiceItem
     * @return true if the object was removed from the collection.
     */
    public boolean removeRulesChoiceItem(
            final com.orient.jpdl.model.RulesChoiceItem vRulesChoiceItem) {
        boolean removed = _items.remove(vRulesChoiceItem);
        return removed;
    }

    /**
     * Method removeRulesChoiceItemAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public com.orient.jpdl.model.RulesChoiceItem removeRulesChoiceItemAt(
            final int index) {
        java.lang.Object obj = this._items.remove(index);
        return (com.orient.jpdl.model.RulesChoiceItem) obj;
    }

    /**
     * 
     * 
     * @param index
     * @param vRulesChoiceItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setRulesChoiceItem(
            final int index,
            final com.orient.jpdl.model.RulesChoiceItem vRulesChoiceItem)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._items.size()) {
            throw new IndexOutOfBoundsException("setRulesChoiceItem: Index value '" + index + "' not in range [0.." + (this._items.size() - 1) + "]");
        }

        this._items.set(index, vRulesChoiceItem);
    }

    /**
     * 
     * 
     * @param vRulesChoiceItemArray
     */
    public void setRulesChoiceItem(
            final com.orient.jpdl.model.RulesChoiceItem[] vRulesChoiceItemArray) {
        //-- copy array
        _items.clear();

        for (int i = 0; i < vRulesChoiceItemArray.length; i++) {
                this._items.add(vRulesChoiceItemArray[i]);
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
     * @return the unmarshaled com.orient.jpdl.model.RulesChoice
     */
    public static com.orient.jpdl.model.RulesChoice unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.orient.jpdl.model.RulesChoice) org.exolab.castor.xml.Unmarshaller.unmarshal(com.orient.jpdl.model.RulesChoice.class, reader);
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
