/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * Information to migrate instances of previously deployed
 *  process definitions to the new version.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class MigrateInstances implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _action.
     */
    private com.orient.jpdl.model.types.MigrationActionType _action = com.orient.jpdl.model.types.MigrationActionType.fromValue("migrate");

    /**
     * Field _items.
     */
    private java.util.List<com.orient.jpdl.model.MigrateInstancesItem> _items;


      //----------------/
     //- Constructors -/
    //----------------/

    public MigrateInstances() {
        super();
        setAction(com.orient.jpdl.model.types.MigrationActionType.fromValue("migrate"));
        this._items = new java.util.ArrayList<com.orient.jpdl.model.MigrateInstancesItem>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vMigrateInstancesItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addMigrateInstancesItem(
            final com.orient.jpdl.model.MigrateInstancesItem vMigrateInstancesItem)
    throws java.lang.IndexOutOfBoundsException {
        this._items.add(vMigrateInstancesItem);
    }

    /**
     * 
     * 
     * @param index
     * @param vMigrateInstancesItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addMigrateInstancesItem(
            final int index,
            final com.orient.jpdl.model.MigrateInstancesItem vMigrateInstancesItem)
    throws java.lang.IndexOutOfBoundsException {
        this._items.add(index, vMigrateInstancesItem);
    }

    /**
     * Method enumerateMigrateInstancesItem.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<? extends com.orient.jpdl.model.MigrateInstancesItem> enumerateMigrateInstancesItem(
    ) {
        return java.util.Collections.enumeration(this._items);
    }

    /**
     * Returns the value of field 'action'.
     * 
     * @return the value of field 'Action'.
     */
    public com.orient.jpdl.model.types.MigrationActionType getAction(
    ) {
        return this._action;
    }

    /**
     * Method getMigrateInstancesItem.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * com.orient.jpdl.model.MigrateInstancesItem at the given index
     */
    public com.orient.jpdl.model.MigrateInstancesItem getMigrateInstancesItem(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._items.size()) {
            throw new IndexOutOfBoundsException("getMigrateInstancesItem: Index value '" + index + "' not in range [0.." + (this._items.size() - 1) + "]");
        }

        return (com.orient.jpdl.model.MigrateInstancesItem) _items.get(index);
    }

    /**
     * Method getMigrateInstancesItem.Returns the contents of the
     * collection in an Array.  <p>Note:  Just in case the
     * collection contents are changing in another thread, we pass
     * a 0-length Array of the correct type into the API call. 
     * This way we <i>know</i> that the Array returned is of
     * exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public com.orient.jpdl.model.MigrateInstancesItem[] getMigrateInstancesItem(
    ) {
        com.orient.jpdl.model.MigrateInstancesItem[] array = new com.orient.jpdl.model.MigrateInstancesItem[0];
        return (com.orient.jpdl.model.MigrateInstancesItem[]) this._items.toArray(array);
    }

    /**
     * Method getMigrateInstancesItemCount.
     * 
     * @return the size of this collection
     */
    public int getMigrateInstancesItemCount(
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
     * Method iterateMigrateInstancesItem.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<? extends com.orient.jpdl.model.MigrateInstancesItem> iterateMigrateInstancesItem(
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
    public void removeAllMigrateInstancesItem(
    ) {
        this._items.clear();
    }

    /**
     * Method removeMigrateInstancesItem.
     * 
     * @param vMigrateInstancesItem
     * @return true if the object was removed from the collection.
     */
    public boolean removeMigrateInstancesItem(
            final com.orient.jpdl.model.MigrateInstancesItem vMigrateInstancesItem) {
        boolean removed = _items.remove(vMigrateInstancesItem);
        return removed;
    }

    /**
     * Method removeMigrateInstancesItemAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public com.orient.jpdl.model.MigrateInstancesItem removeMigrateInstancesItemAt(
            final int index) {
        java.lang.Object obj = this._items.remove(index);
        return (com.orient.jpdl.model.MigrateInstancesItem) obj;
    }

    /**
     * Sets the value of field 'action'.
     * 
     * @param action the value of field 'action'.
     */
    public void setAction(
            final com.orient.jpdl.model.types.MigrationActionType action) {
        this._action = action;
    }

    /**
     * 
     * 
     * @param index
     * @param vMigrateInstancesItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setMigrateInstancesItem(
            final int index,
            final com.orient.jpdl.model.MigrateInstancesItem vMigrateInstancesItem)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._items.size()) {
            throw new IndexOutOfBoundsException("setMigrateInstancesItem: Index value '" + index + "' not in range [0.." + (this._items.size() - 1) + "]");
        }

        this._items.set(index, vMigrateInstancesItem);
    }

    /**
     * 
     * 
     * @param vMigrateInstancesItemArray
     */
    public void setMigrateInstancesItem(
            final com.orient.jpdl.model.MigrateInstancesItem[] vMigrateInstancesItemArray) {
        //-- copy array
        _items.clear();

        for (int i = 0; i < vMigrateInstancesItemArray.length; i++) {
                this._items.add(vMigrateInstancesItemArray[i]);
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
     * @return the unmarshaled com.orient.jpdl.model.MigrateInstance
     */
    public static com.orient.jpdl.model.MigrateInstances unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.orient.jpdl.model.MigrateInstances) org.exolab.castor.xml.Unmarshaller.unmarshal(com.orient.jpdl.model.MigrateInstances.class, reader);
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
