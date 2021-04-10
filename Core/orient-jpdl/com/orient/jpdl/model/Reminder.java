/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * Class Reminder.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class Reminder implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _duedate.
     */
    private java.lang.String _duedate;

    /**
     * Field _repeat.
     */
    private java.lang.String _repeat;

    /**
     * Field _continue.
     */
    private com.orient.jpdl.model.types.ContinueType _continue = com.orient.jpdl.model.types.ContinueType.fromValue("sync");

    /**
     * Field _template.
     */
    private java.lang.String _template;


      //----------------/
     //- Constructors -/
    //----------------/

    public Reminder() {
        super();
        setContinue(com.orient.jpdl.model.types.ContinueType.fromValue("sync"));
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'continue'.
     * 
     * @return the value of field 'Continue'.
     */
    public com.orient.jpdl.model.types.ContinueType getContinue(
    ) {
        return this._continue;
    }

    /**
     * Returns the value of field 'duedate'.
     * 
     * @return the value of field 'Duedate'.
     */
    public java.lang.String getDuedate(
    ) {
        return this._duedate;
    }

    /**
     * Returns the value of field 'repeat'.
     * 
     * @return the value of field 'Repeat'.
     */
    public java.lang.String getRepeat(
    ) {
        return this._repeat;
    }

    /**
     * Returns the value of field 'template'.
     * 
     * @return the value of field 'Template'.
     */
    public java.lang.String getTemplate(
    ) {
        return this._template;
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
     * Sets the value of field 'continue'.
     * 
     * @param _continue
     * @param continue the value of field 'continue'.
     */
    public void setContinue(
            final com.orient.jpdl.model.types.ContinueType _continue) {
        this._continue = _continue;
    }

    /**
     * Sets the value of field 'duedate'.
     * 
     * @param duedate the value of field 'duedate'.
     */
    public void setDuedate(
            final java.lang.String duedate) {
        this._duedate = duedate;
    }

    /**
     * Sets the value of field 'repeat'.
     * 
     * @param repeat the value of field 'repeat'.
     */
    public void setRepeat(
            final java.lang.String repeat) {
        this._repeat = repeat;
    }

    /**
     * Sets the value of field 'template'.
     * 
     * @param template the value of field 'template'.
     */
    public void setTemplate(
            final java.lang.String template) {
        this._template = template;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled com.orient.jpdl.model.Reminder
     */
    public static com.orient.jpdl.model.Reminder unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.orient.jpdl.model.Reminder) org.exolab.castor.xml.Unmarshaller.unmarshal(com.orient.jpdl.model.Reminder.class, reader);
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
