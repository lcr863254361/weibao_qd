/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * Class JmsType.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class JmsType implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * JNDI name of the connection factory.
     */
    private java.lang.String _connectionFactory;

    /**
     * JNDI name of the destination.
     */
    private java.lang.String _destination;

    /**
     * Tells whether the message should be sent in a transaction.
     */
    private com.orient.jpdl.model.types.BooleanValueType _transacted = com.orient.jpdl.model.types.BooleanValueType.fromValue("false");

    /**
     * Indicates the acknowledgment mode.
     */
    private com.orient.jpdl.model.types.AcknowledgeType _acknowledge = com.orient.jpdl.model.types.AcknowledgeType.fromValue("auto");

    /**
     * Internal choice value storage
     */
    private java.lang.Object _choiceValue;

    /**
     * Field _object.
     */
    private com.orient.jpdl.model.Object _object;

    /**
     * Field _text.
     */
    private java.lang.String _text;

    /**
     * Field _map.
     */
    private com.orient.jpdl.model.Map _map;


      //----------------/
     //- Constructors -/
    //----------------/

    public JmsType() {
        super();
        setTransacted(com.orient.jpdl.model.types.BooleanValueType.fromValue("false"));
        setAcknowledge(com.orient.jpdl.model.types.AcknowledgeType.fromValue("auto"));
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'acknowledge'. The field
     * 'acknowledge' has the following description: Indicates the
     * acknowledgment mode.
     * 
     * @return the value of field 'Acknowledge'.
     */
    public com.orient.jpdl.model.types.AcknowledgeType getAcknowledge(
    ) {
        return this._acknowledge;
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
     * Returns the value of field 'connectionFactory'. The field
     * 'connectionFactory' has the following description: JNDI name
     * of the connection factory.
     * 
     * @return the value of field 'ConnectionFactory'.
     */
    public java.lang.String getConnectionFactory(
    ) {
        return this._connectionFactory;
    }

    /**
     * Returns the value of field 'destination'. The field
     * 'destination' has the following description: JNDI name of
     * the destination.
     * 
     * @return the value of field 'Destination'.
     */
    public java.lang.String getDestination(
    ) {
        return this._destination;
    }

    /**
     * Returns the value of field 'map'.
     * 
     * @return the value of field 'Map'.
     */
    public com.orient.jpdl.model.Map getMap(
    ) {
        return this._map;
    }

    /**
     * Returns the value of field 'object'.
     * 
     * @return the value of field 'Object'.
     */
    public com.orient.jpdl.model.Object getObject(
    ) {
        return this._object;
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
     * Returns the value of field 'transacted'. The field
     * 'transacted' has the following description: Tells whether
     * the message should be sent in a transaction.
     * 
     * @return the value of field 'Transacted'.
     */
    public com.orient.jpdl.model.types.BooleanValueType getTransacted(
    ) {
        return this._transacted;
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
     * Sets the value of field 'acknowledge'. The field
     * 'acknowledge' has the following description: Indicates the
     * acknowledgment mode.
     * 
     * @param acknowledge the value of field 'acknowledge'.
     */
    public void setAcknowledge(
            final com.orient.jpdl.model.types.AcknowledgeType acknowledge) {
        this._acknowledge = acknowledge;
    }

    /**
     * Sets the value of field 'connectionFactory'. The field
     * 'connectionFactory' has the following description: JNDI name
     * of the connection factory.
     * 
     * @param connectionFactory the value of field
     * 'connectionFactory'.
     */
    public void setConnectionFactory(
            final java.lang.String connectionFactory) {
        this._connectionFactory = connectionFactory;
    }

    /**
     * Sets the value of field 'destination'. The field
     * 'destination' has the following description: JNDI name of
     * the destination.
     * 
     * @param destination the value of field 'destination'.
     */
    public void setDestination(
            final java.lang.String destination) {
        this._destination = destination;
    }

    /**
     * Sets the value of field 'map'.
     * 
     * @param map the value of field 'map'.
     */
    public void setMap(
            final com.orient.jpdl.model.Map map) {
        this._map = map;
        this._choiceValue = map;
    }

    /**
     * Sets the value of field 'object'.
     * 
     * @param object the value of field 'object'.
     */
    public void setObject(
            final com.orient.jpdl.model.Object object) {
        this._object = object;
        this._choiceValue = object;
    }

    /**
     * Sets the value of field 'text'.
     * 
     * @param text the value of field 'text'.
     */
    public void setText(
            final java.lang.String text) {
        this._text = text;
        this._choiceValue = text;
    }

    /**
     * Sets the value of field 'transacted'. The field 'transacted'
     * has the following description: Tells whether the message
     * should be sent in a transaction.
     * 
     * @param transacted the value of field 'transacted'.
     */
    public void setTransacted(
            final com.orient.jpdl.model.types.BooleanValueType transacted) {
        this._transacted = transacted;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled com.orient.jpdl.model.JmsType
     */
    public static com.orient.jpdl.model.JmsType unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.orient.jpdl.model.JmsType) org.exolab.castor.xml.Unmarshaller.unmarshal(com.orient.jpdl.model.JmsType.class, reader);
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
