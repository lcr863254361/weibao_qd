/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * Class MailRecipientType.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class MailRecipientType implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * List of email addresses separated by ',' (comma)
     *  or whitespace.
     */
    private java.lang.String _addresses;

    /**
     * List of users, resolved to email addresses thought
     *  the configured identity component. Users are separated by
     * ',' (comma)
     *  ';' (semicolon) or whitespace.
     */
    private java.lang.String _users;

    /**
     * List of groups, that are resolved to the email address
     * against
     *  configured identity component. Groups are separated by ','
     * (comma)
     *  ';' (semicolon) or whitespace
     */
    private java.lang.String _groups;


      //----------------/
     //- Constructors -/
    //----------------/

    public MailRecipientType() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'addresses'. The field
     * 'addresses' has the following description: List of email
     * addresses separated by ',' (comma)
     *  or whitespace.
     * 
     * @return the value of field 'Addresses'.
     */
    public java.lang.String getAddresses(
    ) {
        return this._addresses;
    }

    /**
     * Returns the value of field 'groups'. The field 'groups' has
     * the following description: List of groups, that are resolved
     * to the email address against
     *  configured identity component. Groups are separated by ','
     * (comma)
     *  ';' (semicolon) or whitespace
     * 
     * @return the value of field 'Groups'.
     */
    public java.lang.String getGroups(
    ) {
        return this._groups;
    }

    /**
     * Returns the value of field 'users'. The field 'users' has
     * the following description: List of users, resolved to email
     * addresses thought
     *  the configured identity component. Users are separated by
     * ',' (comma)
     *  ';' (semicolon) or whitespace.
     * 
     * @return the value of field 'Users'.
     */
    public java.lang.String getUsers(
    ) {
        return this._users;
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
     * Sets the value of field 'addresses'. The field 'addresses'
     * has the following description: List of email addresses
     * separated by ',' (comma)
     *  or whitespace.
     * 
     * @param addresses the value of field 'addresses'.
     */
    public void setAddresses(
            final java.lang.String addresses) {
        this._addresses = addresses;
    }

    /**
     * Sets the value of field 'groups'. The field 'groups' has the
     * following description: List of groups, that are resolved to
     * the email address against
     *  configured identity component. Groups are separated by ','
     * (comma)
     *  ';' (semicolon) or whitespace
     * 
     * @param groups the value of field 'groups'.
     */
    public void setGroups(
            final java.lang.String groups) {
        this._groups = groups;
    }

    /**
     * Sets the value of field 'users'. The field 'users' has the
     * following description: List of users, resolved to email
     * addresses thought
     *  the configured identity component. Users are separated by
     * ',' (comma)
     *  ';' (semicolon) or whitespace.
     * 
     * @param users the value of field 'users'.
     */
    public void setUsers(
            final java.lang.String users) {
        this._users = users;
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
     * com.orient.jpdl.model.MailRecipientType
     */
    public static com.orient.jpdl.model.MailRecipientType unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.orient.jpdl.model.MailRecipientType) org.exolab.castor.xml.Unmarshaller.unmarshal(com.orient.jpdl.model.MailRecipientType.class, reader);
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
