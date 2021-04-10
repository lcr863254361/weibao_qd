/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * Class EventListenerGroup.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class EventListenerGroup implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Internal choice value storage
     */
    private java.lang.Object _choiceValue;

    /**
     * Field _eventListener.
     */
    private com.orient.jpdl.model.EventListener _eventListener;

    /**
     * Field _hql.
     */
    private com.orient.jpdl.model.Hql _hql;

    /**
     * Field _sql.
     */
    private com.orient.jpdl.model.Sql _sql;

    /**
     * Field _java.
     */
    private com.orient.jpdl.model.Java _java;

    /**
     * Field _assign.
     */
    private com.orient.jpdl.model.Assign _assign;

    /**
     * Field _script.
     */
    private com.orient.jpdl.model.Script _script;

    /**
     * Field _mail.
     */
    private com.orient.jpdl.model.Mail _mail;


      //----------------/
     //- Constructors -/
    //----------------/

    public EventListenerGroup() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'assign'.
     * 
     * @return the value of field 'Assign'.
     */
    public com.orient.jpdl.model.Assign getAssign(
    ) {
        return this._assign;
    }

    /**
     * Returns the value of field 'choiceValue'. The field
     * 'choiceValue' has the following description: Internal choice
     * value storage
     * 
     * @return the value of field 'ChoiceValue'.
     */
    public java.lang.Object getChoiceValue(
    ) {
        return this._choiceValue;
    }

    /**
     * Returns the value of field 'eventListener'.
     * 
     * @return the value of field 'EventListener'.
     */
    public com.orient.jpdl.model.EventListener getEventListener(
    ) {
        return this._eventListener;
    }

    /**
     * Returns the value of field 'hql'.
     * 
     * @return the value of field 'Hql'.
     */
    public com.orient.jpdl.model.Hql getHql(
    ) {
        return this._hql;
    }

    /**
     * Returns the value of field 'java'.
     * 
     * @return the value of field 'Java'.
     */
    public com.orient.jpdl.model.Java getJava(
    ) {
        return this._java;
    }

    /**
     * Returns the value of field 'mail'.
     * 
     * @return the value of field 'Mail'.
     */
    public com.orient.jpdl.model.Mail getMail(
    ) {
        return this._mail;
    }

    /**
     * Returns the value of field 'script'.
     * 
     * @return the value of field 'Script'.
     */
    public com.orient.jpdl.model.Script getScript(
    ) {
        return this._script;
    }

    /**
     * Returns the value of field 'sql'.
     * 
     * @return the value of field 'Sql'.
     */
    public com.orient.jpdl.model.Sql getSql(
    ) {
        return this._sql;
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
     * Sets the value of field 'assign'.
     * 
     * @param assign the value of field 'assign'.
     */
    public void setAssign(
            final com.orient.jpdl.model.Assign assign) {
        this._assign = assign;
        this._choiceValue = assign;
    }

    /**
     * Sets the value of field 'eventListener'.
     * 
     * @param eventListener the value of field 'eventListener'.
     */
    public void setEventListener(
            final com.orient.jpdl.model.EventListener eventListener) {
        this._eventListener = eventListener;
        this._choiceValue = eventListener;
    }

    /**
     * Sets the value of field 'hql'.
     * 
     * @param hql the value of field 'hql'.
     */
    public void setHql(
            final com.orient.jpdl.model.Hql hql) {
        this._hql = hql;
        this._choiceValue = hql;
    }

    /**
     * Sets the value of field 'java'.
     * 
     * @param java the value of field 'java'.
     */
    public void setJava(
            final com.orient.jpdl.model.Java java) {
        this._java = java;
        this._choiceValue = java;
    }

    /**
     * Sets the value of field 'mail'.
     * 
     * @param mail the value of field 'mail'.
     */
    public void setMail(
            final com.orient.jpdl.model.Mail mail) {
        this._mail = mail;
        this._choiceValue = mail;
    }

    /**
     * Sets the value of field 'script'.
     * 
     * @param script the value of field 'script'.
     */
    public void setScript(
            final com.orient.jpdl.model.Script script) {
        this._script = script;
        this._choiceValue = script;
    }

    /**
     * Sets the value of field 'sql'.
     * 
     * @param sql the value of field 'sql'.
     */
    public void setSql(
            final com.orient.jpdl.model.Sql sql) {
        this._sql = sql;
        this._choiceValue = sql;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled
     * com.orient.jpdl.model.EventListenerGroup
     */
    public static com.orient.jpdl.model.EventListenerGroup unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.orient.jpdl.model.EventListenerGroup) org.exolab.castor.xml.Unmarshaller.unmarshal(com.orient.jpdl.model.EventListenerGroup.class, reader);
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
