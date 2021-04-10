/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * Class WireObjectType.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class WireObjectType implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Fully qualified class name
     */
    private java.lang.String _clazz;

    /**
     * Expression that provides the object.
     */
    private java.lang.String _expr;

    /**
     * Script language to interpret.
     */
    private java.lang.String _lang;

    /**
     * Name of the factory object.
     */
    private java.lang.String _factory;

    /**
     * Factory method name
     */
    private java.lang.String _method;

    /**
     * Indicates if the member fields and setter properties
     *  should be wired automatically based on matching the
     * property names and types
     *  with the object names and types.
     */
    private java.lang.String _autoWire;

    /**
     * Indicates if the user code should be cached.
     */
    private com.orient.jpdl.model.types.BooleanValueType _cache;

    /**
     * Field _items.
     */
    private java.util.List<com.orient.jpdl.model.WireObjectTypeItem> _items;


      //----------------/
     //- Constructors -/
    //----------------/

    public WireObjectType() {
        super();
        this._items = new java.util.ArrayList<com.orient.jpdl.model.WireObjectTypeItem>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vWireObjectTypeItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addWireObjectTypeItem(
            final com.orient.jpdl.model.WireObjectTypeItem vWireObjectTypeItem)
    throws java.lang.IndexOutOfBoundsException {
        this._items.add(vWireObjectTypeItem);
    }

    /**
     * 
     * 
     * @param index
     * @param vWireObjectTypeItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addWireObjectTypeItem(
            final int index,
            final com.orient.jpdl.model.WireObjectTypeItem vWireObjectTypeItem)
    throws java.lang.IndexOutOfBoundsException {
        this._items.add(index, vWireObjectTypeItem);
    }

    /**
     * Method enumerateWireObjectTypeItem.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<? extends com.orient.jpdl.model.WireObjectTypeItem> enumerateWireObjectTypeItem(
    ) {
        return java.util.Collections.enumeration(this._items);
    }

    /**
     * Returns the value of field 'autoWire'. The field 'autoWire'
     * has the following description: Indicates if the member
     * fields and setter properties
     *  should be wired automatically based on matching the
     * property names and types
     *  with the object names and types.
     * 
     * @return the value of field 'AutoWire'.
     */
    public java.lang.String getAutoWire(
    ) {
        return this._autoWire;
    }

    /**
     * Returns the value of field 'cache'. The field 'cache' has
     * the following description: Indicates if the user code should
     * be cached.
     * 
     * @return the value of field 'Cache'.
     */
    public com.orient.jpdl.model.types.BooleanValueType getCache(
    ) {
        return this._cache;
    }

    /**
     * Returns the value of field 'clazz'. The field 'clazz' has
     * the following description: Fully qualified class name
     * 
     * @return the value of field 'Clazz'.
     */
    public java.lang.String getClazz(
    ) {
        return this._clazz;
    }

    /**
     * Returns the value of field 'expr'. The field 'expr' has the
     * following description: Expression that provides the object.
     * 
     * @return the value of field 'Expr'.
     */
    public java.lang.String getExpr(
    ) {
        return this._expr;
    }

    /**
     * Returns the value of field 'factory'. The field 'factory'
     * has the following description: Name of the factory object.
     * 
     * @return the value of field 'Factory'.
     */
    public java.lang.String getFactory(
    ) {
        return this._factory;
    }

    /**
     * Returns the value of field 'lang'. The field 'lang' has the
     * following description: Script language to interpret.
     * 
     * @return the value of field 'Lang'.
     */
    public java.lang.String getLang(
    ) {
        return this._lang;
    }

    /**
     * Returns the value of field 'method'. The field 'method' has
     * the following description: Factory method name
     * 
     * @return the value of field 'Method'.
     */
    public java.lang.String getMethod(
    ) {
        return this._method;
    }

    /**
     * Method getWireObjectTypeItem.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * com.orient.jpdl.model.WireObjectTypeItem at the given index
     */
    public com.orient.jpdl.model.WireObjectTypeItem getWireObjectTypeItem(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._items.size()) {
            throw new IndexOutOfBoundsException("getWireObjectTypeItem: Index value '" + index + "' not in range [0.." + (this._items.size() - 1) + "]");
        }

        return (com.orient.jpdl.model.WireObjectTypeItem) _items.get(index);
    }

    /**
     * Method getWireObjectTypeItem.Returns the contents of the
     * collection in an Array.  <p>Note:  Just in case the
     * collection contents are changing in another thread, we pass
     * a 0-length Array of the correct type into the API call. 
     * This way we <i>know</i> that the Array returned is of
     * exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public com.orient.jpdl.model.WireObjectTypeItem[] getWireObjectTypeItem(
    ) {
        com.orient.jpdl.model.WireObjectTypeItem[] array = new com.orient.jpdl.model.WireObjectTypeItem[0];
        return (com.orient.jpdl.model.WireObjectTypeItem[]) this._items.toArray(array);
    }

    /**
     * Method getWireObjectTypeItemCount.
     * 
     * @return the size of this collection
     */
    public int getWireObjectTypeItemCount(
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
     * Method iterateWireObjectTypeItem.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<? extends com.orient.jpdl.model.WireObjectTypeItem> iterateWireObjectTypeItem(
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
    public void removeAllWireObjectTypeItem(
    ) {
        this._items.clear();
    }

    /**
     * Method removeWireObjectTypeItem.
     * 
     * @param vWireObjectTypeItem
     * @return true if the object was removed from the collection.
     */
    public boolean removeWireObjectTypeItem(
            final com.orient.jpdl.model.WireObjectTypeItem vWireObjectTypeItem) {
        boolean removed = _items.remove(vWireObjectTypeItem);
        return removed;
    }

    /**
     * Method removeWireObjectTypeItemAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public com.orient.jpdl.model.WireObjectTypeItem removeWireObjectTypeItemAt(
            final int index) {
        java.lang.Object obj = this._items.remove(index);
        return (com.orient.jpdl.model.WireObjectTypeItem) obj;
    }

    /**
     * Sets the value of field 'autoWire'. The field 'autoWire' has
     * the following description: Indicates if the member fields
     * and setter properties
     *  should be wired automatically based on matching the
     * property names and types
     *  with the object names and types.
     * 
     * @param autoWire the value of field 'autoWire'.
     */
    public void setAutoWire(
            final java.lang.String autoWire) {
        this._autoWire = autoWire;
    }

    /**
     * Sets the value of field 'cache'. The field 'cache' has the
     * following description: Indicates if the user code should be
     * cached.
     * 
     * @param cache the value of field 'cache'.
     */
    public void setCache(
            final com.orient.jpdl.model.types.BooleanValueType cache) {
        this._cache = cache;
    }

    /**
     * Sets the value of field 'clazz'. The field 'clazz' has the
     * following description: Fully qualified class name
     * 
     * @param clazz the value of field 'clazz'.
     */
    public void setClazz(
            final java.lang.String clazz) {
        this._clazz = clazz;
    }

    /**
     * Sets the value of field 'expr'. The field 'expr' has the
     * following description: Expression that provides the object.
     * 
     * @param expr the value of field 'expr'.
     */
    public void setExpr(
            final java.lang.String expr) {
        this._expr = expr;
    }

    /**
     * Sets the value of field 'factory'. The field 'factory' has
     * the following description: Name of the factory object.
     * 
     * @param factory the value of field 'factory'.
     */
    public void setFactory(
            final java.lang.String factory) {
        this._factory = factory;
    }

    /**
     * Sets the value of field 'lang'. The field 'lang' has the
     * following description: Script language to interpret.
     * 
     * @param lang the value of field 'lang'.
     */
    public void setLang(
            final java.lang.String lang) {
        this._lang = lang;
    }

    /**
     * Sets the value of field 'method'. The field 'method' has the
     * following description: Factory method name
     * 
     * @param method the value of field 'method'.
     */
    public void setMethod(
            final java.lang.String method) {
        this._method = method;
    }

    /**
     * 
     * 
     * @param index
     * @param vWireObjectTypeItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setWireObjectTypeItem(
            final int index,
            final com.orient.jpdl.model.WireObjectTypeItem vWireObjectTypeItem)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._items.size()) {
            throw new IndexOutOfBoundsException("setWireObjectTypeItem: Index value '" + index + "' not in range [0.." + (this._items.size() - 1) + "]");
        }

        this._items.set(index, vWireObjectTypeItem);
    }

    /**
     * 
     * 
     * @param vWireObjectTypeItemArray
     */
    public void setWireObjectTypeItem(
            final com.orient.jpdl.model.WireObjectTypeItem[] vWireObjectTypeItemArray) {
        //-- copy array
        _items.clear();

        for (int i = 0; i < vWireObjectTypeItemArray.length; i++) {
                this._items.add(vWireObjectTypeItemArray[i]);
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
     * @return the unmarshaled com.orient.jpdl.model.WireObjectType
     */
    public static com.orient.jpdl.model.WireObjectType unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.orient.jpdl.model.WireObjectType) org.exolab.castor.xml.Unmarshaller.unmarshal(com.orient.jpdl.model.WireObjectType.class, reader);
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
