/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * Decision gateway: selects one path out of many alternatives.
 *  When an execution arrives, it takes exactly one outgoing
 * transition.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class Decision implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The script that will be evaluated and resolve to
     *  the name of the outgoing transition.
     */
    private java.lang.String _expr;

    /**
     * Identification of the scripting language
     *  to use for the expr attribute.
     */
    private java.lang.String _lang;

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
     * Field _handler.
     */
    private com.orient.jpdl.model.Handler _handler;

    /**
     * Field _decisionChoice.
     */
    private com.orient.jpdl.model.DecisionChoice _decisionChoice;


      //----------------/
     //- Constructors -/
    //----------------/

    public Decision() {
        super();
        setContinue(com.orient.jpdl.model.types.ContinueType.fromValue("sync"));
    }


      //-----------/
     //- Methods -/
    //-----------/

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
     * Returns the value of field 'decisionChoice'.
     * 
     * @return the value of field 'DecisionChoice'.
     */
    public com.orient.jpdl.model.DecisionChoice getDecisionChoice(
    ) {
        return this._decisionChoice;
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
     * Returns the value of field 'expr'. The field 'expr' has the
     * following description: The script that will be evaluated and
     * resolve to
     *  the name of the outgoing transition.
     * 
     * @return the value of field 'Expr'.
     */
    public java.lang.String getExpr(
    ) {
        return this._expr;
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
     * following description: Identification of the scripting
     * language
     *  to use for the expr attribute.
     * 
     * @return the value of field 'Lang'.
     */
    public java.lang.String getLang(
    ) {
        return this._lang;
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
     * Sets the value of field 'decisionChoice'.
     * 
     * @param decisionChoice the value of field 'decisionChoice'.
     */
    public void setDecisionChoice(
            final com.orient.jpdl.model.DecisionChoice decisionChoice) {
        this._decisionChoice = decisionChoice;
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
     * following description: The script that will be evaluated and
     * resolve to
     *  the name of the outgoing transition.
     * 
     * @param expr the value of field 'expr'.
     */
    public void setExpr(
            final java.lang.String expr) {
        this._expr = expr;
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
     * following description: Identification of the scripting
     * language
     *  to use for the expr attribute.
     * 
     * @param lang the value of field 'lang'.
     */
    public void setLang(
            final java.lang.String lang) {
        this._lang = lang;
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
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled com.orient.jpdl.model.Decision
     */
    public static com.orient.jpdl.model.Decision unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.orient.jpdl.model.Decision) org.exolab.castor.xml.Unmarshaller.unmarshal(com.orient.jpdl.model.Decision.class, reader);
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
