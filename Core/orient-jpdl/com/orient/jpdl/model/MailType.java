/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * Class MailType.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class MailType extends com.orient.jpdl.model.WireObjectType 
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _template.
     */
    private java.lang.String _template;

    /**
     * Field _from.
     */
    private com.orient.jpdl.model.From _from;

    /**
     * Field _to.
     */
    private com.orient.jpdl.model.To _to;

    /**
     * Field _cc.
     */
    private com.orient.jpdl.model.Cc _cc;

    /**
     * Field _bcc.
     */
    private com.orient.jpdl.model.Bcc _bcc;

    /**
     * Field _subject.
     */
    private java.lang.String _subject;

    /**
     * Field _text.
     */
    private java.lang.String _text;

    /**
     * Field _html.
     */
    private java.lang.String _html;

    /**
     * Field _attachments.
     */
    private com.orient.jpdl.model.Attachments _attachments;


      //----------------/
     //- Constructors -/
    //----------------/

    public MailType() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'attachments'.
     * 
     * @return the value of field 'Attachments'.
     */
    public com.orient.jpdl.model.Attachments getAttachments(
    ) {
        return this._attachments;
    }

    /**
     * Returns the value of field 'bcc'.
     * 
     * @return the value of field 'Bcc'.
     */
    public com.orient.jpdl.model.Bcc getBcc(
    ) {
        return this._bcc;
    }

    /**
     * Returns the value of field 'cc'.
     * 
     * @return the value of field 'Cc'.
     */
    public com.orient.jpdl.model.Cc getCc(
    ) {
        return this._cc;
    }

    /**
     * Returns the value of field 'from'.
     * 
     * @return the value of field 'From'.
     */
    public com.orient.jpdl.model.From getFrom(
    ) {
        return this._from;
    }

    /**
     * Returns the value of field 'html'.
     * 
     * @return the value of field 'Html'.
     */
    public java.lang.String getHtml(
    ) {
        return this._html;
    }

    /**
     * Returns the value of field 'subject'.
     * 
     * @return the value of field 'Subject'.
     */
    public java.lang.String getSubject(
    ) {
        return this._subject;
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
     * Returns the value of field 'text'.
     * 
     * @return the value of field 'Text'.
     */
    public java.lang.String getText(
    ) {
        return this._text;
    }

    /**
     * Returns the value of field 'to'.
     * 
     * @return the value of field 'To'.
     */
    public com.orient.jpdl.model.To getTo(
    ) {
        return this._to;
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
     * Sets the value of field 'attachments'.
     * 
     * @param attachments the value of field 'attachments'.
     */
    public void setAttachments(
            final com.orient.jpdl.model.Attachments attachments) {
        this._attachments = attachments;
    }

    /**
     * Sets the value of field 'bcc'.
     * 
     * @param bcc the value of field 'bcc'.
     */
    public void setBcc(
            final com.orient.jpdl.model.Bcc bcc) {
        this._bcc = bcc;
    }

    /**
     * Sets the value of field 'cc'.
     * 
     * @param cc the value of field 'cc'.
     */
    public void setCc(
            final com.orient.jpdl.model.Cc cc) {
        this._cc = cc;
    }

    /**
     * Sets the value of field 'from'.
     * 
     * @param from the value of field 'from'.
     */
    public void setFrom(
            final com.orient.jpdl.model.From from) {
        this._from = from;
    }

    /**
     * Sets the value of field 'html'.
     * 
     * @param html the value of field 'html'.
     */
    public void setHtml(
            final java.lang.String html) {
        this._html = html;
    }

    /**
     * Sets the value of field 'subject'.
     * 
     * @param subject the value of field 'subject'.
     */
    public void setSubject(
            final java.lang.String subject) {
        this._subject = subject;
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
     * Sets the value of field 'text'.
     * 
     * @param text the value of field 'text'.
     */
    public void setText(
            final java.lang.String text) {
        this._text = text;
    }

    /**
     * Sets the value of field 'to'.
     * 
     * @param to the value of field 'to'.
     */
    public void setTo(
            final com.orient.jpdl.model.To to) {
        this._to = to;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled com.orient.jpdl.model.MailType
     */
    public static com.orient.jpdl.model.MailType unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.orient.jpdl.model.MailType) org.exolab.castor.xml.Unmarshaller.unmarshal(com.orient.jpdl.model.MailType.class, reader);
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
