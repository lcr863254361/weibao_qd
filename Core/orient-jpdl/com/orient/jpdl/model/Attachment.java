/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * Class Attachment.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class Attachment implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Resource in the web.
     */
    private java.lang.String _url;

    /**
     * Resource in the classpath.
     */
    private java.lang.String _resource;

    /**
     * Path in the file system.
     */
    private java.lang.String _file;


      //----------------/
     //- Constructors -/
    //----------------/

    public Attachment() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'file'. The field 'file' has the
     * following description: Path in the file system.
     * 
     * @return the value of field 'File'.
     */
    public java.lang.String getFile(
    ) {
        return this._file;
    }

    /**
     * Returns the value of field 'resource'. The field 'resource'
     * has the following description: Resource in the classpath.
     * 
     * @return the value of field 'Resource'.
     */
    public java.lang.String getResource(
    ) {
        return this._resource;
    }

    /**
     * Returns the value of field 'url'. The field 'url' has the
     * following description: Resource in the web.
     * 
     * @return the value of field 'Url'.
     */
    public java.lang.String getUrl(
    ) {
        return this._url;
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
     * Sets the value of field 'file'. The field 'file' has the
     * following description: Path in the file system.
     * 
     * @param file the value of field 'file'.
     */
    public void setFile(
            final java.lang.String file) {
        this._file = file;
    }

    /**
     * Sets the value of field 'resource'. The field 'resource' has
     * the following description: Resource in the classpath.
     * 
     * @param resource the value of field 'resource'.
     */
    public void setResource(
            final java.lang.String resource) {
        this._resource = resource;
    }

    /**
     * Sets the value of field 'url'. The field 'url' has the
     * following description: Resource in the web.
     * 
     * @param url the value of field 'url'.
     */
    public void setUrl(
            final java.lang.String url) {
        this._url = url;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled com.orient.jpdl.model.Attachment
     */
    public static com.orient.jpdl.model.Attachment unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.orient.jpdl.model.Attachment) org.exolab.castor.xml.Unmarshaller.unmarshal(com.orient.jpdl.model.Attachment.class, reader);
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
