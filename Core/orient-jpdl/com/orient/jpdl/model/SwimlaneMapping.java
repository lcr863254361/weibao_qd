/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * Class SwimlaneMapping.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class SwimlaneMapping implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _swimlane.
     */
    private java.lang.String _swimlane;

    /**
     * Field _subSwimlane.
     */
    private java.lang.String _subSwimlane;


      //----------------/
     //- Constructors -/
    //----------------/

    public SwimlaneMapping() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'subSwimlane'.
     * 
     * @return the value of field 'SubSwimlane'.
     */
    public java.lang.String getSubSwimlane(
    ) {
        return this._subSwimlane;
    }

    /**
     * Returns the value of field 'swimlane'.
     * 
     * @return the value of field 'Swimlane'.
     */
    public java.lang.String getSwimlane(
    ) {
        return this._swimlane;
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
     * Sets the value of field 'subSwimlane'.
     * 
     * @param subSwimlane the value of field 'subSwimlane'.
     */
    public void setSubSwimlane(
            final java.lang.String subSwimlane) {
        this._subSwimlane = subSwimlane;
    }

    /**
     * Sets the value of field 'swimlane'.
     * 
     * @param swimlane the value of field 'swimlane'.
     */
    public void setSwimlane(
            final java.lang.String swimlane) {
        this._swimlane = swimlane;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled com.orient.jpdl.model.SwimlaneMapping
     */
    public static com.orient.jpdl.model.SwimlaneMapping unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.orient.jpdl.model.SwimlaneMapping) org.exolab.castor.xml.Unmarshaller.unmarshal(com.orient.jpdl.model.SwimlaneMapping.class, reader);
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
