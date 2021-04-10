/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * End cancel event.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class EndCancel implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _ends.
     */
    private com.orient.jpdl.model.types.EndCancelEndsType _ends = com.orient.jpdl.model.types.EndCancelEndsType.fromValue("process-instance");

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
     * Field _description.
     */
    private java.lang.String _description;

    /**
     * Field _onList.
     */
    private java.util.List<com.orient.jpdl.model.On> _onList;


      //----------------/
     //- Constructors -/
    //----------------/

    public EndCancel() {
        super();
        setEnds(com.orient.jpdl.model.types.EndCancelEndsType.fromValue("process-instance"));
        setContinue(com.orient.jpdl.model.types.ContinueType.fromValue("sync"));
        this._onList = new java.util.ArrayList<com.orient.jpdl.model.On>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vOn
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addOn(
            final com.orient.jpdl.model.On vOn)
    throws java.lang.IndexOutOfBoundsException {
        this._onList.add(vOn);
    }

    /**
     * 
     * 
     * @param index
     * @param vOn
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addOn(
            final int index,
            final com.orient.jpdl.model.On vOn)
    throws java.lang.IndexOutOfBoundsException {
        this._onList.add(index, vOn);
    }

    /**
     * Method enumerateOn.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<? extends com.orient.jpdl.model.On> enumerateOn(
    ) {
        return java.util.Collections.enumeration(this._onList);
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
     * Returns the value of field 'description'.
     * 
     * @return the value of field 'Description'.
     */
    public java.lang.String getDescription(
    ) {
        return this._description;
    }

    /**
     * Returns the value of field 'ends'.
     * 
     * @return the value of field 'Ends'.
     */
    public com.orient.jpdl.model.types.EndCancelEndsType getEnds(
    ) {
        return this._ends;
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
     * Method getOn.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the com.orient.jpdl.model.On at the
     * given index
     */
    public com.orient.jpdl.model.On getOn(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._onList.size()) {
            throw new IndexOutOfBoundsException("getOn: Index value '" + index + "' not in range [0.." + (this._onList.size() - 1) + "]");
        }

        return (com.orient.jpdl.model.On) _onList.get(index);
    }

    /**
     * Method getOn.Returns the contents of the collection in an
     * Array.  <p>Note:  Just in case the collection contents are
     * changing in another thread, we pass a 0-length Array of the
     * correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public com.orient.jpdl.model.On[] getOn(
    ) {
        com.orient.jpdl.model.On[] array = new com.orient.jpdl.model.On[0];
        return (com.orient.jpdl.model.On[]) this._onList.toArray(array);
    }

    /**
     * Method getOnCount.
     * 
     * @return the size of this collection
     */
    public int getOnCount(
    ) {
        return this._onList.size();
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
     * Method iterateOn.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<? extends com.orient.jpdl.model.On> iterateOn(
    ) {
        return this._onList.iterator();
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
    public void removeAllOn(
    ) {
        this._onList.clear();
    }

    /**
     * Method removeOn.
     * 
     * @param vOn
     * @return true if the object was removed from the collection.
     */
    public boolean removeOn(
            final com.orient.jpdl.model.On vOn) {
        boolean removed = _onList.remove(vOn);
        return removed;
    }

    /**
     * Method removeOnAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public com.orient.jpdl.model.On removeOnAt(
            final int index) {
        java.lang.Object obj = this._onList.remove(index);
        return (com.orient.jpdl.model.On) obj;
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
     * Sets the value of field 'description'.
     * 
     * @param description the value of field 'description'.
     */
    public void setDescription(
            final java.lang.String description) {
        this._description = description;
    }

    /**
     * Sets the value of field 'ends'.
     * 
     * @param ends the value of field 'ends'.
     */
    public void setEnds(
            final com.orient.jpdl.model.types.EndCancelEndsType ends) {
        this._ends = ends;
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
     * 
     * 
     * @param index
     * @param vOn
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setOn(
            final int index,
            final com.orient.jpdl.model.On vOn)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._onList.size()) {
            throw new IndexOutOfBoundsException("setOn: Index value '" + index + "' not in range [0.." + (this._onList.size() - 1) + "]");
        }

        this._onList.set(index, vOn);
    }

    /**
     * 
     * 
     * @param vOnArray
     */
    public void setOn(
            final com.orient.jpdl.model.On[] vOnArray) {
        //-- copy array
        _onList.clear();

        for (int i = 0; i < vOnArray.length; i++) {
                this._onList.add(vOnArray[i]);
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
     * @return the unmarshaled com.orient.jpdl.model.EndCancel
     */
    public static com.orient.jpdl.model.EndCancel unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.orient.jpdl.model.EndCancel) org.exolab.castor.xml.Unmarshaller.unmarshal(com.orient.jpdl.model.EndCancel.class, reader);
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
