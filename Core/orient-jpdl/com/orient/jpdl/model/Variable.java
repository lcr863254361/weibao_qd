/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * Class Variable.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class Variable implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Name of the variable. Must differ from other variable
     *  names.
     */
    private java.lang.String _name;

    /**
     * Type of the variable.
     */
    private java.lang.String _type;

    /**
     * Field _initExpr.
     */
    private java.lang.String _initExpr;

    /**
     * Field _history.
     */
    private com.orient.jpdl.model.types.BooleanValueType _history;

    /**
     * Field _wireObjectGroup.
     */
    private com.orient.jpdl.model.WireObjectGroup _wireObjectGroup;


      //----------------/
     //- Constructors -/
    //----------------/

    public Variable() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'history'.
     * 
     * @return the value of field 'History'.
     */
    public com.orient.jpdl.model.types.BooleanValueType getHistory(
    ) {
        return this._history;
    }

    /**
     * Returns the value of field 'initExpr'.
     * 
     * @return the value of field 'InitExpr'.
     */
    public java.lang.String getInitExpr(
    ) {
        return this._initExpr;
    }

    /**
     * Returns the value of field 'name'. The field 'name' has the
     * following description: Name of the variable. Must differ
     * from other variable
     *  names.
     * 
     * @return the value of field 'Name'.
     */
    public java.lang.String getName(
    ) {
        return this._name;
    }

    /**
     * Returns the value of field 'type'. The field 'type' has the
     * following description: Type of the variable.
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
     * Sets the value of field 'history'.
     * 
     * @param history the value of field 'history'.
     */
    public void setHistory(
            final com.orient.jpdl.model.types.BooleanValueType history) {
        this._history = history;
    }

    /**
     * Sets the value of field 'initExpr'.
     * 
     * @param initExpr the value of field 'initExpr'.
     */
    public void setInitExpr(
            final java.lang.String initExpr) {
        this._initExpr = initExpr;
    }

    /**
     * Sets the value of field 'name'. The field 'name' has the
     * following description: Name of the variable. Must differ
     * from other variable
     *  names.
     * 
     * @param name the value of field 'name'.
     */
    public void setName(
            final java.lang.String name) {
        this._name = name;
    }

    /**
     * Sets the value of field 'type'. The field 'type' has the
     * following description: Type of the variable.
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
     * @return the unmarshaled com.orient.jpdl.model.Variable
     */
    public static com.orient.jpdl.model.Variable unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.orient.jpdl.model.Variable) org.exolab.castor.xml.Unmarshaller.unmarshal(com.orient.jpdl.model.Variable.class, reader);
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
