/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * Class ParameterType.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class ParameterType implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Name of the sub process variable.
     */
    private java.lang.String _subvar;

    /**
     * Expression that provides the variable value.
     */
    private java.lang.String _expr;

    /**
     * Language of the expression.
     */
    private java.lang.String _lang;

    /**
     * Name of the process variable
     *  in the super process.
     */
    private java.lang.String _var;


      //----------------/
     //- Constructors -/
    //----------------/

    public ParameterType() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'expr'. The field 'expr' has the
     * following description: Expression that provides the variable
     * value.
     * 
     * @return the value of field 'Expr'.
     */
    public java.lang.String getExpr(
    ) {
        return this._expr;
    }

    /**
     * Returns the value of field 'lang'. The field 'lang' has the
     * following description: Language of the expression.
     * 
     * @return the value of field 'Lang'.
     */
    public java.lang.String getLang(
    ) {
        return this._lang;
    }

    /**
     * Returns the value of field 'subvar'. The field 'subvar' has
     * the following description: Name of the sub process variable.
     * 
     * @return the value of field 'Subvar'.
     */
    public java.lang.String getSubvar(
    ) {
        return this._subvar;
    }

    /**
     * Returns the value of field 'var'. The field 'var' has the
     * following description: Name of the process variable
     *  in the super process.
     * 
     * @return the value of field 'Var'.
     */
    public java.lang.String getVar(
    ) {
        return this._var;
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
     * following description: Expression that provides the variable
     * value.
     * 
     * @param expr the value of field 'expr'.
     */
    public void setExpr(
            final java.lang.String expr) {
        this._expr = expr;
    }

    /**
     * Sets the value of field 'lang'. The field 'lang' has the
     * following description: Language of the expression.
     * 
     * @param lang the value of field 'lang'.
     */
    public void setLang(
            final java.lang.String lang) {
        this._lang = lang;
    }

    /**
     * Sets the value of field 'subvar'. The field 'subvar' has the
     * following description: Name of the sub process variable.
     * 
     * @param subvar the value of field 'subvar'.
     */
    public void setSubvar(
            final java.lang.String subvar) {
        this._subvar = subvar;
    }

    /**
     * Sets the value of field 'var'. The field 'var' has the
     * following description: Name of the process variable
     *  in the super process.
     * 
     * @param var the value of field 'var'.
     */
    public void setVar(
            final java.lang.String var) {
        this._var = var;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled com.orient.jpdl.model.ParameterType
     */
    public static com.orient.jpdl.model.ParameterType unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.orient.jpdl.model.ParameterType) org.exolab.castor.xml.Unmarshaller.unmarshal(com.orient.jpdl.model.ParameterType.class, reader);
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
