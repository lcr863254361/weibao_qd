/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * The method arguments.
 *  Each 'arg' element should have exactly one child
 *  element that represents the value of the argument.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class ArgType implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Class name representing the method type.
     *  Optional, helps resolve the appropriate method in case of
     * overloading.
     */
    private java.lang.String _type;

    /**
     * Field _wireObjectGroup.
     */
    private com.orient.jpdl.model.WireObjectGroup _wireObjectGroup;


      //----------------/
     //- Constructors -/
    //----------------/

    public ArgType() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'type'. The field 'type' has the
     * following description: Class name representing the method
     * type.
     *  Optional, helps resolve the appropriate method in case of
     * overloading.
     * 
     * @return the value of field 'Type'.
     */
    public java.lang.String getType(
    ) {
        return this._type;
    }

    /**
     * Returns the value of field 'wireObjectGroup'.
     * 
     * @return the value of field 'WireObjectGroup'.
     */
    public com.orient.jpdl.model.WireObjectGroup getWireObjectGroup(
    ) {
        return this._wireObjectGroup;
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
     * Sets the value of field 'type'. The field 'type' has the
     * following description: Class name representing the method
     * type.
     *  Optional, helps resolve the appropriate method in case of
     * overloading.
     * 
     * @param type the value of field 'type'.
     */
    public void setType(
            final java.lang.String type) {
        this._type = type;
    }

    /**
     * Sets the value of field 'wireObjectGroup'.
     * 
     * @param wireObjectGroup the value of field 'wireObjectGroup'.
     */
    public void setWireObjectGroup(
            final com.orient.jpdl.model.WireObjectGroup wireObjectGroup) {
        this._wireObjectGroup = wireObjectGroup;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled com.orient.jpdl.model.ArgType
     */
    public static com.orient.jpdl.model.ArgType unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.orient.jpdl.model.ArgType) org.exolab.castor.xml.Unmarshaller.unmarshal(com.orient.jpdl.model.ArgType.class, reader);
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
