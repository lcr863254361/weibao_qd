/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * Class WireObjectGroup.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class WireObjectGroup implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Internal choice value storage
     */
    private java.lang.Object _choiceValue;

    /**
     * The null value.
     */
    private java.lang.Object _null;

    /**
     * Reference to an object in the current environment.
     */
    private com.orient.jpdl.model.Ref _ref;

    /**
     * The current environment.
     */
    private java.lang.Object _envRef;

    /**
     * JNDI lookup based off the initial context.
     */
    private com.orient.jpdl.model.Jndi _jndi;

    /**
     * java.util.List instance.
     */
    private com.orient.jpdl.model.List _list;

    /**
     * Field _map.
     */
    private com.orient.jpdl.model.Map _map;

    /**
     * java.util.Set instance.
     */
    private com.orient.jpdl.model.Set _set;

    /**
     * java.util.Properties instance.
     */
    private com.orient.jpdl.model.Properties _properties;

    /**
     * Field _object.
     */
    private com.orient.jpdl.model.Object _object;

    /**
     * Field _string.
     */
    private com.orient.jpdl.model.String _string;

    /**
     * 8-bit signed integer.
     */
    private com.orient.jpdl.model.Byte _byte;

    /**
     * 16-bit Unicode character.
     */
    private com.orient.jpdl.model.Char _char;

    /**
     * 64-bit floating point number.
     */
    private com.orient.jpdl.model.Double _double;

    /**
     * Boolean false value
     */
    private com.orient.jpdl.model.False _false;

    /**
     * 32-bit floating point number.
     */
    private com.orient.jpdl.model.Float _float;

    /**
     * 32-bit signed integer.
     */
    private com.orient.jpdl.model.Int _int;

    /**
     * 64-bit signed integer.
     */
    private com.orient.jpdl.model.Long _long;

    /**
     * 16-bit signed integer.
     */
    private com.orient.jpdl.model.Short _short;

    /**
     * Boolean true value.
     */
    private com.orient.jpdl.model.True _true;


      //----------------/
     //- Constructors -/
    //----------------/

    public WireObjectGroup() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'byte'. The field 'byte' has the
     * following description: 8-bit signed integer.
     * 
     * @return the value of field 'Byte'.
     */
    public com.orient.jpdl.model.Byte getByte(
    ) {
        return this._byte;
    }

    /**
     * Returns the value of field 'char'. The field 'char' has the
     * following description: 16-bit Unicode character.
     * 
     * @return the value of field 'Char'.
     */
    public com.orient.jpdl.model.Char getChar(
    ) {
        return this._char;
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
     * Returns the value of field 'double'. The field 'double' has
     * the following description: 64-bit floating point number.
     * 
     * @return the value of field 'Double'.
     */
    public com.orient.jpdl.model.Double getDouble(
    ) {
        return this._double;
    }

    /**
     * Returns the value of field 'envRef'. The field 'envRef' has
     * the following description: The current environment.
     * 
     * @return the value of field 'EnvRef'.
     */
    public java.lang.Object getEnvRef(
    ) {
        return this._envRef;
    }

    /**
     * Returns the value of field 'false'. The field 'false' has
     * the following description: Boolean false value
     * 
     * @return the value of field 'False'.
     */
    public com.orient.jpdl.model.False getFalse(
    ) {
        return this._false;
    }

    /**
     * Returns the value of field 'float'. The field 'float' has
     * the following description: 32-bit floating point number.
     * 
     * @return the value of field 'Float'.
     */
    public com.orient.jpdl.model.Float getFloat(
    ) {
        return this._float;
    }

    /**
     * Returns the value of field 'int'. The field 'int' has the
     * following description: 32-bit signed integer.
     * 
     * @return the value of field 'Int'.
     */
    public com.orient.jpdl.model.Int getInt(
    ) {
        return this._int;
    }

    /**
     * Returns the value of field 'jndi'. The field 'jndi' has the
     * following description: JNDI lookup based off the initial
     * context.
     * 
     * @return the value of field 'Jndi'.
     */
    public com.orient.jpdl.model.Jndi getJndi(
    ) {
        return this._jndi;
    }

    /**
     * Returns the value of field 'list'. The field 'list' has the
     * following description: java.util.List instance.
     * 
     * @return the value of field 'List'.
     */
    public com.orient.jpdl.model.List getList(
    ) {
        return this._list;
    }

    /**
     * Returns the value of field 'long'. The field 'long' has the
     * following description: 64-bit signed integer.
     * 
     * @return the value of field 'Long'.
     */
    public com.orient.jpdl.model.Long getLong(
    ) {
        return this._long;
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
     * Returns the value of field 'null'. The field 'null' has the
     * following description: The null value.
     * 
     * @return the value of field 'Null'.
     */
    public java.lang.Object getNull(
    ) {
        return this._null;
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
     * Returns the value of field 'properties'. The field
     * 'properties' has the following description:
     * java.util.Properties instance.
     * 
     * @return the value of field 'Properties'.
     */
    public com.orient.jpdl.model.Properties getProperties(
    ) {
        return this._properties;
    }

    /**
     * Returns the value of field 'ref'. The field 'ref' has the
     * following description: Reference to an object in the current
     * environment.
     * 
     * @return the value of field 'Ref'.
     */
    public com.orient.jpdl.model.Ref getRef(
    ) {
        return this._ref;
    }

    /**
     * Returns the value of field 'set'. The field 'set' has the
     * following description: java.util.Set instance.
     * 
     * @return the value of field 'Set'.
     */
    public com.orient.jpdl.model.Set getSet(
    ) {
        return this._set;
    }

    /**
     * Returns the value of field 'short'. The field 'short' has
     * the following description: 16-bit signed integer.
     * 
     * @return the value of field 'Short'.
     */
    public com.orient.jpdl.model.Short getShort(
    ) {
        return this._short;
    }

    /**
     * Returns the value of field 'string'.
     * 
     * @return the value of field 'String'.
     */
    public com.orient.jpdl.model.String getString(
    ) {
        return this._string;
    }

    /**
     * Returns the value of field 'true'. The field 'true' has the
     * following description: Boolean true value.
     * 
     * @return the value of field 'True'.
     */
    public com.orient.jpdl.model.True getTrue(
    ) {
        return this._true;
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
     * Sets the value of field 'byte'. The field 'byte' has the
     * following description: 8-bit signed integer.
     * 
     * @param _byte
     * @param byte the value of field 'byte'.
     */
    public void setByte(
            final com.orient.jpdl.model.Byte _byte) {
        this._byte = _byte;
        this._choiceValue = _byte;
    }

    /**
     * Sets the value of field 'char'. The field 'char' has the
     * following description: 16-bit Unicode character.
     * 
     * @param _char
     * @param char the value of field 'char'.
     */
    public void setChar(
            final com.orient.jpdl.model.Char _char) {
        this._char = _char;
        this._choiceValue = _char;
    }

    /**
     * Sets the value of field 'double'. The field 'double' has the
     * following description: 64-bit floating point number.
     * 
     * @param _double
     * @param double the value of field 'double'.
     */
    public void setDouble(
            final com.orient.jpdl.model.Double _double) {
        this._double = _double;
        this._choiceValue = _double;
    }

    /**
     * Sets the value of field 'envRef'. The field 'envRef' has the
     * following description: The current environment.
     * 
     * @param envRef the value of field 'envRef'.
     */
    public void setEnvRef(
            final java.lang.Object envRef) {
        this._envRef = envRef;
        this._choiceValue = envRef;
    }

    /**
     * Sets the value of field 'false'. The field 'false' has the
     * following description: Boolean false value
     * 
     * @param _false
     * @param false the value of field 'false'.
     */
    public void setFalse(
            final com.orient.jpdl.model.False _false) {
        this._false = _false;
        this._choiceValue = _false;
    }

    /**
     * Sets the value of field 'float'. The field 'float' has the
     * following description: 32-bit floating point number.
     * 
     * @param _float
     * @param float the value of field 'float'.
     */
    public void setFloat(
            final com.orient.jpdl.model.Float _float) {
        this._float = _float;
        this._choiceValue = _float;
    }

    /**
     * Sets the value of field 'int'. The field 'int' has the
     * following description: 32-bit signed integer.
     * 
     * @param _int
     * @param int the value of field 'int'.
     */
    public void setInt(
            final com.orient.jpdl.model.Int _int) {
        this._int = _int;
        this._choiceValue = _int;
    }

    /**
     * Sets the value of field 'jndi'. The field 'jndi' has the
     * following description: JNDI lookup based off the initial
     * context.
     * 
     * @param jndi the value of field 'jndi'.
     */
    public void setJndi(
            final com.orient.jpdl.model.Jndi jndi) {
        this._jndi = jndi;
        this._choiceValue = jndi;
    }

    /**
     * Sets the value of field 'list'. The field 'list' has the
     * following description: java.util.List instance.
     * 
     * @param list the value of field 'list'.
     */
    public void setList(
            final com.orient.jpdl.model.List list) {
        this._list = list;
        this._choiceValue = list;
    }

    /**
     * Sets the value of field 'long'. The field 'long' has the
     * following description: 64-bit signed integer.
     * 
     * @param _long
     * @param long the value of field 'long'.
     */
    public void setLong(
            final com.orient.jpdl.model.Long _long) {
        this._long = _long;
        this._choiceValue = _long;
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
     * Sets the value of field 'null'. The field 'null' has the
     * following description: The null value.
     * 
     * @param _null
     * @param null the value of field 'null'.
     */
    public void setNull(
            final java.lang.Object _null) {
        this._null = _null;
        this._choiceValue = _null;
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
     * Sets the value of field 'properties'. The field 'properties'
     * has the following description: java.util.Properties
     * instance.
     * 
     * @param properties the value of field 'properties'.
     */
    public void setProperties(
            final com.orient.jpdl.model.Properties properties) {
        this._properties = properties;
        this._choiceValue = properties;
    }

    /**
     * Sets the value of field 'ref'. The field 'ref' has the
     * following description: Reference to an object in the current
     * environment.
     * 
     * @param ref the value of field 'ref'.
     */
    public void setRef(
            final com.orient.jpdl.model.Ref ref) {
        this._ref = ref;
        this._choiceValue = ref;
    }

    /**
     * Sets the value of field 'set'. The field 'set' has the
     * following description: java.util.Set instance.
     * 
     * @param set the value of field 'set'.
     */
    public void setSet(
            final com.orient.jpdl.model.Set set) {
        this._set = set;
        this._choiceValue = set;
    }

    /**
     * Sets the value of field 'short'. The field 'short' has the
     * following description: 16-bit signed integer.
     * 
     * @param _short
     * @param short the value of field 'short'.
     */
    public void setShort(
            final com.orient.jpdl.model.Short _short) {
        this._short = _short;
        this._choiceValue = _short;
    }

    /**
     * Sets the value of field 'string'.
     * 
     * @param string the value of field 'string'.
     */
    public void setString(
            final com.orient.jpdl.model.String string) {
        this._string = string;
        this._choiceValue = string;
    }

    /**
     * Sets the value of field 'true'. The field 'true' has the
     * following description: Boolean true value.
     * 
     * @param _true
     * @param true the value of field 'true'.
     */
    public void setTrue(
            final com.orient.jpdl.model.True _true) {
        this._true = _true;
        this._choiceValue = _true;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled com.orient.jpdl.model.WireObjectGroup
     */
    public static com.orient.jpdl.model.WireObjectGroup unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.orient.jpdl.model.WireObjectGroup) org.exolab.castor.xml.Unmarshaller.unmarshal(com.orient.jpdl.model.WireObjectGroup.class, reader);
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
