/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * Class AssignType.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class AssignType implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Expression that resolves the source value.
     */
    private java.lang.String _fromExpr;

    /**
     * Language in which from-expr is written.
     */
    private java.lang.String _lang;

    /**
     * Variable that provides the source value.
     */
    private java.lang.String _fromVar;

    /**
     * Expression that resolves the target location.
     */
    private java.lang.String _toExpr;

    /**
     * Variable that provides the target location.
     */
    private java.lang.String _toVar;

    /**
     * Field _description.
     */
    private java.lang.String _description;

    /**
     * Descriptor that constructs the source value.
     */
    private com.orient.jpdl.model.From _from;


      //----------------/
     //- Constructors -/
    //----------------/

    public AssignType() {
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
     * Returns the value of field 'from'. The field 'from' has the
     * following description: Descriptor that constructs the source
     * value.
     * 
     * @return the value of field 'From'.
     */
    public com.orient.jpdl.model.From getFrom(
    ) {
        return this._from;
    }

    /**
     * Returns the value of field 'fromExpr'. The field 'fromExpr'
     * has the following description: Expression that resolves the
     * source value.
     * 
     * @return the value of field 'FromExpr'.
     */
    public java.lang.String getFromExpr(
    ) {
        return this._fromExpr;
    }

    /**
     * Returns the value of field 'fromVar'. The field 'fromVar'
     * has the following description: Variable that provides the
     * source value.
     * 
     * @return the value of field 'FromVar'.
     */
    public java.lang.String getFromVar(
    ) {
        return this._fromVar;
    }

    /**
     * Returns the value of field 'lang'. The field 'lang' has the
     * following description: Language in which from-expr is
     * written.
     * 
     * @return the value of field 'Lang'.
     */
    public java.lang.String getLang(
    ) {
        return this._lang;
    }

    /**
     * Returns the value of field 'toExpr'. The field 'toExpr' has
     * the following description: Expression that resolves the
     * target location.
     * 
     * @return the value of field 'ToExpr'.
     */
    public java.lang.String getToExpr(
    ) {
        return this._toExpr;
    }

    /**
     * Returns the value of field 'toVar'. The field 'toVar' has
     * the following description: Variable that provides the target
     * location.
     * 
     * @return the value of field 'ToVar'.
     */
    public java.lang.String getToVar(
    ) {
        return this._toVar;
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
     * Sets the value of field 'from'. The field 'from' has the
     * following description: Descriptor that constructs the source
     * value.
     * 
     * @param from the value of field 'from'.
     */
    public void setFrom(
            final com.orient.jpdl.model.From from) {
        this._from = from;
    }

    /**
     * Sets the value of field 'fromExpr'. The field 'fromExpr' has
     * the following description: Expression that resolves the
     * source value.
     * 
     * @param fromExpr the value of field 'fromExpr'.
     */
    public void setFromExpr(
            final java.lang.String fromExpr) {
        this._fromExpr = fromExpr;
    }

    /**
     * Sets the value of field 'fromVar'. The field 'fromVar' has
     * the following description: Variable that provides the source
     * value.
     * 
     * @param fromVar the value of field 'fromVar'.
     */
    public void setFromVar(
            final java.lang.String fromVar) {
        this._fromVar = fromVar;
    }

    /**
     * Sets the value of field 'lang'. The field 'lang' has the
     * following description: Language in which from-expr is
     * written.
     * 
     * @param lang the value of field 'lang'.
     */
    public void setLang(
            final java.lang.String lang) {
        this._lang = lang;
    }

    /**
     * Sets the value of field 'toExpr'. The field 'toExpr' has the
     * following description: Expression that resolves the target
     * location.
     * 
     * @param toExpr the value of field 'toExpr'.
     */
    public void setToExpr(
            final java.lang.String toExpr) {
        this._toExpr = toExpr;
    }

    /**
     * Sets the value of field 'toVar'. The field 'toVar' has the
     * following description: Variable that provides the target
     * location.
     * 
     * @param toVar the value of field 'toVar'.
     */
    public void setToVar(
            final java.lang.String toVar) {
        this._toVar = toVar;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled com.orient.jpdl.model.AssignType
     */
    public static com.orient.jpdl.model.AssignType unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.orient.jpdl.model.AssignType) org.exolab.castor.xml.Unmarshaller.unmarshal(com.orient.jpdl.model.AssignType.class, reader);
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
