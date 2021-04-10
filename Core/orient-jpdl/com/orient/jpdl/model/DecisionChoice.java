/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * Class DecisionChoice.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class DecisionChoice implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _items.
     */
    private java.util.List<com.orient.jpdl.model.DecisionChoiceItem> _items;


      //----------------/
     //- Constructors -/
    //----------------/

    public DecisionChoice() {
        super();
        this._items = new java.util.ArrayList<com.orient.jpdl.model.DecisionChoiceItem>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vDecisionChoiceItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addDecisionChoiceItem(
            final com.orient.jpdl.model.DecisionChoiceItem vDecisionChoiceItem)
    throws java.lang.IndexOutOfBoundsException {
        this._items.add(vDecisionChoiceItem);
    }

    /**
     * 
     * 
     * @param index
     * @param vDecisionChoiceItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addDecisionChoiceItem(
            final int index,
            final com.orient.jpdl.model.DecisionChoiceItem vDecisionChoiceItem)
    throws java.lang.IndexOutOfBoundsException {
        this._items.add(index, vDecisionChoiceItem);
    }

    /**
     * Method enumerateDecisionChoiceItem.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<? extends com.orient.jpdl.model.DecisionChoiceItem> enumerateDecisionChoiceItem(
    ) {
        return java.util.Collections.enumeration(this._items);
    }

    /**
     * Method getDecisionChoiceItem.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * com.orient.jpdl.model.DecisionChoiceItem at the given index
     */
    public com.orient.jpdl.model.DecisionChoiceItem getDecisionChoiceItem(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._items.size()) {
            throw new IndexOutOfBoundsException("getDecisionChoiceItem: Index value '" + index + "' not in range [0.." + (this._items.size() - 1) + "]");
        }

        return (com.orient.jpdl.model.DecisionChoiceItem) _items.get(index);
    }

    /**
     * Method getDecisionChoiceItem.Returns the contents of the
     * collection in an Array.  <p>Note:  Just in case the
     * collection contents are changing in another thread, we pass
     * a 0-length Array of the correct type into the API call. 
     * This way we <i>know</i> that the Array returned is of
     * exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public com.orient.jpdl.model.DecisionChoiceItem[] getDecisionChoiceItem(
    ) {
        com.orient.jpdl.model.DecisionChoiceItem[] array = new com.orient.jpdl.model.DecisionChoiceItem[0];
        return (com.orient.jpdl.model.DecisionChoiceItem[]) this._items.toArray(array);
    }

    /**
     * Method getDecisionChoiceItemCount.
     * 
     * @return the size of this collection
     */
    public int getDecisionChoiceItemCount(
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
     * Method iterateDecisionChoiceItem.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<? extends com.orient.jpdl.model.DecisionChoiceItem> iterateDecisionChoiceItem(
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
    public void removeAllDecisionChoiceItem(
    ) {
        this._items.clear();
    }

    /**
     * Method removeDecisionChoiceItem.
     * 
     * @param vDecisionChoiceItem
     * @return true if the object was removed from the collection.
     */
    public boolean removeDecisionChoiceItem(
            final com.orient.jpdl.model.DecisionChoiceItem vDecisionChoiceItem) {
        boolean removed = _items.remove(vDecisionChoiceItem);
        return removed;
    }

    /**
     * Method removeDecisionChoiceItemAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public com.orient.jpdl.model.DecisionChoiceItem removeDecisionChoiceItemAt(
            final int index) {
        java.lang.Object obj = this._items.remove(index);
        return (com.orient.jpdl.model.DecisionChoiceItem) obj;
    }

    /**
     * 
     * 
     * @param index
     * @param vDecisionChoiceItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setDecisionChoiceItem(
            final int index,
            final com.orient.jpdl.model.DecisionChoiceItem vDecisionChoiceItem)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._items.size()) {
            throw new IndexOutOfBoundsException("setDecisionChoiceItem: Index value '" + index + "' not in range [0.." + (this._items.size() - 1) + "]");
        }

        this._items.set(index, vDecisionChoiceItem);
    }

    /**
     * 
     * 
     * @param vDecisionChoiceItemArray
     */
    public void setDecisionChoiceItem(
            final com.orient.jpdl.model.DecisionChoiceItem[] vDecisionChoiceItemArray) {
        //-- copy array
        _items.clear();

        for (int i = 0; i < vDecisionChoiceItemArray.length; i++) {
                this._items.add(vDecisionChoiceItemArray[i]);
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
     * @return the unmarshaled com.orient.jpdl.model.DecisionChoice
     */
    public static com.orient.jpdl.model.DecisionChoice unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.orient.jpdl.model.DecisionChoice) org.exolab.castor.xml.Unmarshaller.unmarshal(com.orient.jpdl.model.DecisionChoice.class, reader);
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
