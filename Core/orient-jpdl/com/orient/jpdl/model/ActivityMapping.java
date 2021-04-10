/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * One activity mapping will be present for each activity of which
 * the
 *  name changed.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class ActivityMapping implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Name of the activity in the previously deployed
     *  process definition.
     */
    private java.lang.String _oldName;

    /**
     * Name of the activity in the newly deployed
     *  process definition.
     */
    private java.lang.String _newName;


      //----------------/
     //- Constructors -/
    //----------------/

    public ActivityMapping() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'newName'. The field 'newName'
     * has the following description: Name of the activity in the
     * newly deployed
     *  process definition.
     * 
     * @return the value of field 'NewName'.
     */
    public java.lang.String getNewName(
    ) {
        return this._newName;
    }

    /**
     * Returns the value of field 'oldName'. The field 'oldName'
     * has the following description: Name of the activity in the
     * previously deployed
     *  process definition.
     * 
     * @return the value of field 'OldName'.
     */
    public java.lang.String getOldName(
    ) {
        return this._oldName;
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
     * Sets the value of field 'newName'. The field 'newName' has
     * the following description: Name of the activity in the newly
     * deployed
     *  process definition.
     * 
     * @param newName the value of field 'newName'.
     */
    public void setNewName(
            final java.lang.String newName) {
        this._newName = newName;
    }

    /**
     * Sets the value of field 'oldName'. The field 'oldName' has
     * the following description: Name of the activity in the
     * previously deployed
     *  process definition.
     * 
     * @param oldName the value of field 'oldName'.
     */
    public void setOldName(
            final java.lang.String oldName) {
        this._oldName = oldName;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled com.orient.jpdl.model.ActivityMapping
     */
    public static com.orient.jpdl.model.ActivityMapping unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.orient.jpdl.model.ActivityMapping) org.exolab.castor.xml.Unmarshaller.unmarshal(com.orient.jpdl.model.ActivityMapping.class, reader);
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
