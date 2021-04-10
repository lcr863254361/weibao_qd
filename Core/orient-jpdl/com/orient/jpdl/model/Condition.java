/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * Class Condition.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class Condition implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Expression to evaluate.
     */
    private java.lang.String _expr;

    /**
     * Script language to interpret.
     */
    private java.lang.String _lang;

    /**
     * Field _handler.
     */
    private com.orient.jpdl.model.Handler _handler;


      //----------------/
     //- Constructors -/
    //----------------/

    public Condition() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'expr'. The field 'expr' has the
     * following description: Expression to evaluate.
     * 
     * @return the value of field 'Expr'.
     */
    public java.lang.String getExpr(
    ) {
        return this._expr;
    }

    /**
     * Returns the value of field 'handler'.
     * 
     * @return the value of field 'Handler'.
     */
    public com.orient.jpdl.model.Handler getHandler(
    ) {
        return this._handler;
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
     * Sets the value of field 'expr'. The field 'expr' has the
     * following description: Expression to evaluate.
     * 
     * @param expr the value of field 'expr'.
     */
    public void setExpr(
            final java.lang.String expr) {
        this._expr = expr;
    }

    /**
     * Sets the value of field 'handler'.
     * 
     * @param handler the value of field 'handler'.
     */
    public void setHandler(
            final com.orient.jpdl.model.Handler handler) {
        this._handler = handler;
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
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled com.orient.jpdl.model.Condition
     */
    public static com.orient.jpdl.model.Condition unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.orient.jpdl.model.Condition) org.exolab.castor.xml.Unmarshaller.unmarshal(com.orient.jpdl.model.Condition.class, reader);
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
