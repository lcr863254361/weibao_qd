/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * java.util.Properties instance.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class Properties implements java.io.Serializable {


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
     * File in the file system.
     */
    private java.lang.String _file;

    /**
     * Resource in the classpath.
     */
    private java.lang.String _resource;

    /**
     * Resource in the web.
     */
    private java.lang.String _url;

    /**
     * tells whether the resource referenced by
     *  attributes 'file', 'resource' or 'url' is XML. The default
     * is the
     *  plain properties format, where a space or '=' separates the
     * key
     *  from the value in each line.
     */
    private com.orient.jpdl.model.types.BooleanValueType _isXml;

    /**
     * Field _items.
     */
    private java.util.List<com.orient.jpdl.model.PropertiesItem> _items;


      //----------------/
     //- Constructors -/
    //----------------/

    public Properties() {
        super();
        this._items = new java.util.ArrayList<com.orient.jpdl.model.PropertiesItem>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vPropertiesItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addPropertiesItem(
            final com.orient.jpdl.model.PropertiesItem vPropertiesItem)
    throws java.lang.IndexOutOfBoundsException {
        this._items.add(vPropertiesItem);
    }

    /**
     * 
     * 
     * @param index
     * @param vPropertiesItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addPropertiesItem(
            final int index,
            final com.orient.jpdl.model.PropertiesItem vPropertiesItem)
    throws java.lang.IndexOutOfBoundsException {
        this._items.add(index, vPropertiesItem);
    }

    /**
     * Method enumeratePropertiesItem.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<? extends com.orient.jpdl.model.PropertiesItem> enumeratePropertiesItem(
    ) {
        return java.util.Collections.enumeration(this._items);
    }

    /**
     * Returns the value of field 'file'. The field 'file' has the
     * following description: File in the file system.
     * 
     * @return the value of field 'File'.
     */
    public java.lang.String getFile(
    ) {
        return this._file;
    }

    /**
     * Returns the value of field 'isXml'. The field 'isXml' has
     * the following description: tells whether the resource
     * referenced by
     *  attributes 'file', 'resource' or 'url' is XML. The default
     * is the
     *  plain properties format, where a space or '=' separates the
     * key
     *  from the value in each line.
     * 
     * @return the value of field 'IsXml'.
     */
    public com.orient.jpdl.model.types.BooleanValueType getIsXml(
    ) {
        return this._isXml;
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
     * Method getPropertiesItem.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * com.orient.jpdl.model.PropertiesItem at the given index
     */
    public com.orient.jpdl.model.PropertiesItem getPropertiesItem(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._items.size()) {
            throw new IndexOutOfBoundsException("getPropertiesItem: Index value '" + index + "' not in range [0.." + (this._items.size() - 1) + "]");
        }

        return (com.orient.jpdl.model.PropertiesItem) _items.get(index);
    }

    /**
     * Method getPropertiesItem.Returns the contents of the
     * collection in an Array.  <p>Note:  Just in case the
     * collection contents are changing in another thread, we pass
     * a 0-length Array of the correct type into the API call. 
     * This way we <i>know</i> that the Array returned is of
     * exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public com.orient.jpdl.model.PropertiesItem[] getPropertiesItem(
    ) {
        com.orient.jpdl.model.PropertiesItem[] array = new com.orient.jpdl.model.PropertiesItem[0];
        return (com.orient.jpdl.model.PropertiesItem[]) this._items.toArray(array);
    }

    /**
     * Method getPropertiesItemCount.
     * 
     * @return the size of this collection
     */
    public int getPropertiesItemCount(
    ) {
        return this._items.size();
    }

    /**
     * Returns the value of field 'resource'. The field 'resource'
     * has the following description: Resource in the classpath.
     * 
     * @return the value of field 'Resource'.
     */
    public java.lang.String getResource(
    ) {
        return this._resource;
    }

    /**
     * Returns the value of field 'url'. The field 'url' has the
     * following description: Resource in the web.
     * 
     * @return the value of field 'Url'.
     */
    public java.lang.String getUrl(
    ) {
        return this._url;
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
     * Method iteratePropertiesItem.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<? extends com.orient.jpdl.model.PropertiesItem> iteratePropertiesItem(
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
    public void removeAllPropertiesItem(
    ) {
        this._items.clear();
    }

    /**
     * Method removePropertiesItem.
     * 
     * @param vPropertiesItem
     * @return true if the object was removed from the collection.
     */
    public boolean removePropertiesItem(
            final com.orient.jpdl.model.PropertiesItem vPropertiesItem) {
        boolean removed = _items.remove(vPropertiesItem);
        return removed;
    }

    /**
     * Method removePropertiesItemAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public com.orient.jpdl.model.PropertiesItem removePropertiesItemAt(
            final int index) {
        java.lang.Object obj = this._items.remove(index);
        return (com.orient.jpdl.model.PropertiesItem) obj;
    }

    /**
     * Sets the value of field 'file'. The field 'file' has the
     * following description: File in the file system.
     * 
     * @param file the value of field 'file'.
     */
    public void setFile(
            final java.lang.String file) {
        this._file = file;
    }

    /**
     * Sets the value of field 'isXml'. The field 'isXml' has the
     * following description: tells whether the resource referenced
     * by
     *  attributes 'file', 'resource' or 'url' is XML. The default
     * is the
     *  plain properties format, where a space or '=' separates the
     * key
     *  from the value in each line.
     * 
     * @param isXml the value of field 'isXml'.
     */
    public void setIsXml(
            final com.orient.jpdl.model.types.BooleanValueType isXml) {
        this._isXml = isXml;
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
     * 
     * 
     * @param index
     * @param vPropertiesItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setPropertiesItem(
            final int index,
            final com.orient.jpdl.model.PropertiesItem vPropertiesItem)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._items.size()) {
            throw new IndexOutOfBoundsException("setPropertiesItem: Index value '" + index + "' not in range [0.." + (this._items.size() - 1) + "]");
        }

        this._items.set(index, vPropertiesItem);
    }

    /**
     * 
     * 
     * @param vPropertiesItemArray
     */
    public void setPropertiesItem(
            final com.orient.jpdl.model.PropertiesItem[] vPropertiesItemArray) {
        //-- copy array
        _items.clear();

        for (int i = 0; i < vPropertiesItemArray.length; i++) {
                this._items.add(vPropertiesItemArray[i]);
        }
    }

    /**
     * Sets the value of field 'resource'. The field 'resource' has
     * the following description: Resource in the classpath.
     * 
     * @param resource the value of field 'resource'.
     */
    public void setResource(
            final java.lang.String resource) {
        this._resource = resource;
    }

    /**
     * Sets the value of field 'url'. The field 'url' has the
     * following description: Resource in the web.
     * 
     * @param url the value of field 'url'.
     */
    public void setUrl(
            final java.lang.String url) {
        this._url = url;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled com.orient.jpdl.model.Properties
     */
    public static com.orient.jpdl.model.Properties unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.orient.jpdl.model.Properties) org.exolab.castor.xml.Unmarshaller.unmarshal(com.orient.jpdl.model.Properties.class, reader);
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
