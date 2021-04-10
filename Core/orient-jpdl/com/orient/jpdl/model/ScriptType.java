/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * Class ScriptType.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class ScriptType implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The value of this attribute is the script to evaluate.
     *  This attribute and the text element are mutually exclusive.
     */
    private java.lang.String _expr;

    /**
     * Identifies the scripting language in use.
     */
    private java.lang.String _lang;

    /**
     * Name of the variable in which the result
     *  of the script evaluation will be stored.
     */
    private java.lang.String _var;

    /**
     * Field _description.
     */
    private java.lang.String _description;

    /**
     * The text content of this element is the script to evaluate.
     *  This element and the expression attribute are mutually
     * exclusive.
     */
    private java.lang.String _text;


      //----------------/
     //- Constructors -/
    //----------------/

    public ScriptType() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

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
     * Returns the value of field 'expr'. The field 'expr' has the
     * following description: The value of this attribute is the
     * script to evaluate.
     *  This attribute and the text element are mutually exclusive.
     * 
     * @return the value of field 'Expr'.
     */
    public java.lang.String getExpr(
    ) {
        return this._expr;
    }

    /**
     * Returns the value of field 'lang'. The field 'lang' has the
     * following description: Identifies the scripting language in
     * use.
     * 
     * @return the value of field 'Lang'.
     */
    public java.lang.String getLang(
    ) {
        return this._lang;
    }

    /**
     * Returns the value of field 'text'. The field 'text' has the
     * following description: The text content of this element is
     * the script to evaluate.
     *  This element and the expression attribute are mutually
     * exclusive.
     * 
     * @return the value of field 'Text'.
     */
    public java.lang.String getText(
    ) {
        return this._text;
    }

    /**
     * Returns the value of field 'var'. The field 'var' has the
     * following description: Name of the variable in which the
     * result
     *  of the script evaluation will be stored.
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
     * Sets the value of field 'description'.
     * 
     * @param description the value of field 'description'.
     */
    public void setDescription(
            final java.lang.String description) {
        this._description = description;
    }

    /**
     * Sets the value of field 'expr'. The field 'expr' has the
     * following description: The value of this attribute is the
     * script to evaluate.
     *  This attribute and the text element are mutually exclusive.
     * 
     * @param expr the value of field 'expr'.
     */
    public void setExpr(
            final java.lang.String expr) {
        this._expr = expr;
    }

    /**
     * Sets the value of field 'lang'. The field 'lang' has the
     * following description: Identifies the scripting language in
     * use.
     * 
     * @param lang the value of field 'lang'.
     */
    public void setLang(
            final java.lang.String lang) {
        this._lang = lang;
    }

    /**
     * Sets the value of field 'text'. The field 'text' has the
     * following description: The text content of this element is
     * the script to evaluate.
     *  This element and the expression attribute are mutually
     * exclusive.
     * 
     * @param text the value of field 'text'.
     */
    public void setText(
            final java.lang.String text) {
        this._text = text;
    }

    /**
     * Sets the value of field 'var'. The field 'var' has the
     * following description: Name of the variable in which the
     * result
     *  of the script evaluation will be stored.
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
     * @return the unmarshaled com.orient.jpdl.model.ScriptType
     */
    public static com.orient.jpdl.model.ScriptType unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.orient.jpdl.model.ScriptType) org.exolab.castor.xml.Unmarshaller.unmarshal(com.orient.jpdl.model.ScriptType.class, reader);
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
