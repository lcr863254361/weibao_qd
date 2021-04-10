/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * create a analysis activity.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class Analysis extends com.orient.jpdl.model.WireObjectType 
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Name of this activity. The name should be unique
     *  in the complete scope of the process.
     */
    private java.lang.String _name;

    /**
     * Graphical information used by process designer tool.
     */
    private java.lang.String _g;

    /**
     * Specifies async continuation.
     */
    private com.orient.jpdl.model.types.ContinueType _continue = com.orient.jpdl.model.types.ContinueType.fromValue("sync");

    /**
     * Field _items.
     */
    private java.util.List<com.orient.jpdl.model.AnalysisItem> _items;


      //----------------/
     //- Constructors -/
    //----------------/

    public Analysis() {
        super();
        setContinue(com.orient.jpdl.model.types.ContinueType.fromValue("sync"));
        this._items = new java.util.ArrayList<com.orient.jpdl.model.AnalysisItem>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vAnalysisItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addAnalysisItem(
            final com.orient.jpdl.model.AnalysisItem vAnalysisItem)
    throws java.lang.IndexOutOfBoundsException {
        this._items.add(vAnalysisItem);
    }

    /**
     * 
     * 
     * @param index
     * @param vAnalysisItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addAnalysisItem(
            final int index,
            final com.orient.jpdl.model.AnalysisItem vAnalysisItem)
    throws java.lang.IndexOutOfBoundsException {
        this._items.add(index, vAnalysisItem);
    }

    /**
     * Method enumerateAnalysisItem.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<? extends com.orient.jpdl.model.AnalysisItem> enumerateAnalysisItem(
    ) {
        return java.util.Collections.enumeration(this._items);
    }

    /**
     * Method getAnalysisItem.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the com.orient.jpdl.model.AnalysisItem
     * at the given index
     */
    public com.orient.jpdl.model.AnalysisItem getAnalysisItem(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._items.size()) {
            throw new IndexOutOfBoundsException("getAnalysisItem: Index value '" + index + "' not in range [0.." + (this._items.size() - 1) + "]");
        }

        return (com.orient.jpdl.model.AnalysisItem) _items.get(index);
    }

    /**
     * Method getAnalysisItem.Returns the contents of the
     * collection in an Array.  <p>Note:  Just in case the
     * collection contents are changing in another thread, we pass
     * a 0-length Array of the correct type into the API call. 
     * This way we <i>know</i> that the Array returned is of
     * exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public com.orient.jpdl.model.AnalysisItem[] getAnalysisItem(
    ) {
        com.orient.jpdl.model.AnalysisItem[] array = new com.orient.jpdl.model.AnalysisItem[0];
        return (com.orient.jpdl.model.AnalysisItem[]) this._items.toArray(array);
    }

    /**
     * Method getAnalysisItemCount.
     * 
     * @return the size of this collection
     */
    public int getAnalysisItemCount(
    ) {
        return this._items.size();
    }

    /**
     * Returns the value of field 'continue'. The field 'continue'
     * has the following description: Specifies async continuation.
     * 
     * @return the value of field 'Continue'.
     */
    public com.orient.jpdl.model.types.ContinueType getContinue(
    ) {
        return this._continue;
    }

    /**
     * Returns the value of field 'g'. The field 'g' has the
     * following description: Graphical information used by process
     * designer tool.
     * 
     * @return the value of field 'G'.
     */
    public java.lang.String getG(
    ) {
        return this._g;
    }

    /**
     * Returns the value of field 'name'. The field 'name' has the
     * following description: Name of this activity. The name
     * should be unique
     *  in the complete scope of the process.
     * 
     * @return the value of field 'Name'.
     */
    public java.lang.String getName(
    ) {
        return this._name;
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
     * Method iterateAnalysisItem.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<? extends com.orient.jpdl.model.AnalysisItem> iterateAnalysisItem(
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
    public void removeAllAnalysisItem(
    ) {
        this._items.clear();
    }

    /**
     * Method removeAnalysisItem.
     * 
     * @param vAnalysisItem
     * @return true if the object was removed from the collection.
     */
    public boolean removeAnalysisItem(
            final com.orient.jpdl.model.AnalysisItem vAnalysisItem) {
        boolean removed = _items.remove(vAnalysisItem);
        return removed;
    }

    /**
     * Method removeAnalysisItemAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public com.orient.jpdl.model.AnalysisItem removeAnalysisItemAt(
            final int index) {
        java.lang.Object obj = this._items.remove(index);
        return (com.orient.jpdl.model.AnalysisItem) obj;
    }

    /**
     * 
     * 
     * @param index
     * @param vAnalysisItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setAnalysisItem(
            final int index,
            final com.orient.jpdl.model.AnalysisItem vAnalysisItem)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._items.size()) {
            throw new IndexOutOfBoundsException("setAnalysisItem: Index value '" + index + "' not in range [0.." + (this._items.size() - 1) + "]");
        }

        this._items.set(index, vAnalysisItem);
    }

    /**
     * 
     * 
     * @param vAnalysisItemArray
     */
    public void setAnalysisItem(
            final com.orient.jpdl.model.AnalysisItem[] vAnalysisItemArray) {
        //-- copy array
        _items.clear();

        for (int i = 0; i < vAnalysisItemArray.length; i++) {
                this._items.add(vAnalysisItemArray[i]);
        }
    }

    /**
     * Sets the value of field 'continue'. The field 'continue' has
     * the following description: Specifies async continuation.
     * 
     * @param _continue
     * @param continue the value of field 'continue'.
     */
    public void setContinue(
            final com.orient.jpdl.model.types.ContinueType _continue) {
        this._continue = _continue;
    }

    /**
     * Sets the value of field 'g'. The field 'g' has the following
     * description: Graphical information used by process designer
     * tool.
     * 
     * @param g the value of field 'g'.
     */
    public void setG(
            final java.lang.String g) {
        this._g = g;
    }

    /**
     * Sets the value of field 'name'. The field 'name' has the
     * following description: Name of this activity. The name
     * should be unique
     *  in the complete scope of the process.
     * 
     * @param name the value of field 'name'.
     */
    public void setName(
            final java.lang.String name) {
        this._name = name;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled com.orient.jpdl.model.Analysis
     */
    public static com.orient.jpdl.model.Analysis unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.orient.jpdl.model.Analysis) org.exolab.castor.xml.Unmarshaller.unmarshal(com.orient.jpdl.model.Analysis.class, reader);
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
