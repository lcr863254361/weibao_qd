/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model.descriptors;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import com.orient.jpdl.model.WireObjectGroup;

/**
 * Class WireObjectGroupDescriptor.
 * 
 * @version $Revision$ $Date$
 */
public class WireObjectGroupDescriptor extends org.exolab.castor.xml.util.XMLClassDescriptorImpl {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _elementDefinition.
     */
    private boolean _elementDefinition;

    /**
     * Field _nsPrefix.
     */
    private java.lang.String _nsPrefix;

    /**
     * Field _nsURI.
     */
    private java.lang.String _nsURI;

    /**
     * Field _xmlName.
     */
    private java.lang.String _xmlName;

    /**
     * Field _identity.
     */
    private org.exolab.castor.xml.XMLFieldDescriptor _identity;


      //----------------/
     //- Constructors -/
    //----------------/

    public WireObjectGroupDescriptor() {
        super();
        _nsURI = "http://jbpm.org/4.4/jpdl";
        _xmlName = "wireObjectGroup";
        _elementDefinition = false;

        //-- set grouping compositor
        setCompositorAsChoice();
        org.exolab.castor.xml.util.XMLFieldDescriptorImpl  desc           = null;
        org.exolab.castor.mapping.FieldHandler             handler        = null;
        org.exolab.castor.xml.FieldValidator               fieldValidator = null;
        //-- initialize attribute descriptors

        //-- initialize element descriptors

        //-- _null
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(java.lang.Object.class, "_null", "null", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                WireObjectGroup target = (WireObjectGroup) object;
                return target.getNull();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    WireObjectGroup target = (WireObjectGroup) object;
                    target.setNull( (java.lang.Object) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new java.lang.Object();
            }
        };
        desc.setSchemaType("java.lang.Object");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _null
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _ref
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.Ref.class, "_ref", "ref", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                WireObjectGroup target = (WireObjectGroup) object;
                return target.getRef();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    WireObjectGroup target = (WireObjectGroup) object;
                    target.setRef( (com.orient.jpdl.model.Ref) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.Ref();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.Ref");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _ref
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _envRef
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(java.lang.Object.class, "_envRef", "env-ref", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                WireObjectGroup target = (WireObjectGroup) object;
                return target.getEnvRef();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    WireObjectGroup target = (WireObjectGroup) object;
                    target.setEnvRef( (java.lang.Object) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new java.lang.Object();
            }
        };
        desc.setSchemaType("java.lang.Object");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _envRef
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _jndi
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.Jndi.class, "_jndi", "jndi", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                WireObjectGroup target = (WireObjectGroup) object;
                return target.getJndi();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    WireObjectGroup target = (WireObjectGroup) object;
                    target.setJndi( (com.orient.jpdl.model.Jndi) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.Jndi();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.Jndi");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _jndi
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _list
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.List.class, "_list", "list", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                WireObjectGroup target = (WireObjectGroup) object;
                return target.getList();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    WireObjectGroup target = (WireObjectGroup) object;
                    target.setList( (com.orient.jpdl.model.List) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.List();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.List");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _list
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _map
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.Map.class, "_map", "map", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                WireObjectGroup target = (WireObjectGroup) object;
                return target.getMap();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    WireObjectGroup target = (WireObjectGroup) object;
                    target.setMap( (com.orient.jpdl.model.Map) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.Map();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.Map");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _map
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _set
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.Set.class, "_set", "set", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                WireObjectGroup target = (WireObjectGroup) object;
                return target.getSet();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    WireObjectGroup target = (WireObjectGroup) object;
                    target.setSet( (com.orient.jpdl.model.Set) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.Set();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.Set");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _set
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _properties
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.Properties.class, "_properties", "properties", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                WireObjectGroup target = (WireObjectGroup) object;
                return target.getProperties();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    WireObjectGroup target = (WireObjectGroup) object;
                    target.setProperties( (com.orient.jpdl.model.Properties) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.Properties();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.Properties");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _properties
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _object
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.Object.class, "_object", "object", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                WireObjectGroup target = (WireObjectGroup) object;
                return target.getObject();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    WireObjectGroup target = (WireObjectGroup) object;
                    target.setObject( (com.orient.jpdl.model.Object) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.Object();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.Object");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _object
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _string
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.String.class, "_string", "string", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                WireObjectGroup target = (WireObjectGroup) object;
                return target.getString();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    WireObjectGroup target = (WireObjectGroup) object;
                    target.setString( (com.orient.jpdl.model.String) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.String();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.String");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _string
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _byte
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.Byte.class, "_byte", "byte", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                WireObjectGroup target = (WireObjectGroup) object;
                return target.getByte();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    WireObjectGroup target = (WireObjectGroup) object;
                    target.setByte( (com.orient.jpdl.model.Byte) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.Byte();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.Byte");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _byte
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _char
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.Char.class, "_char", "char", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                WireObjectGroup target = (WireObjectGroup) object;
                return target.getChar();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    WireObjectGroup target = (WireObjectGroup) object;
                    target.setChar( (com.orient.jpdl.model.Char) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.Char();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.Char");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _char
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _double
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.Double.class, "_double", "double", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                WireObjectGroup target = (WireObjectGroup) object;
                return target.getDouble();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    WireObjectGroup target = (WireObjectGroup) object;
                    target.setDouble( (com.orient.jpdl.model.Double) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.Double();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.Double");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _double
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _false
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.False.class, "_false", "false", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                WireObjectGroup target = (WireObjectGroup) object;
                return target.getFalse();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    WireObjectGroup target = (WireObjectGroup) object;
                    target.setFalse( (com.orient.jpdl.model.False) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.False();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.False");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _false
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _float
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.Float.class, "_float", "float", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                WireObjectGroup target = (WireObjectGroup) object;
                return target.getFloat();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    WireObjectGroup target = (WireObjectGroup) object;
                    target.setFloat( (com.orient.jpdl.model.Float) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.Float();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.Float");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _float
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _int
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.Int.class, "_int", "int", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                WireObjectGroup target = (WireObjectGroup) object;
                return target.getInt();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    WireObjectGroup target = (WireObjectGroup) object;
                    target.setInt( (com.orient.jpdl.model.Int) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.Int();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.Int");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _int
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _long
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.Long.class, "_long", "long", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                WireObjectGroup target = (WireObjectGroup) object;
                return target.getLong();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    WireObjectGroup target = (WireObjectGroup) object;
                    target.setLong( (com.orient.jpdl.model.Long) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.Long();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.Long");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _long
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _short
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.Short.class, "_short", "short", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                WireObjectGroup target = (WireObjectGroup) object;
                return target.getShort();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    WireObjectGroup target = (WireObjectGroup) object;
                    target.setShort( (com.orient.jpdl.model.Short) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.Short();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.Short");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _short
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _true
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.True.class, "_true", "true", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                WireObjectGroup target = (WireObjectGroup) object;
                return target.getTrue();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    WireObjectGroup target = (WireObjectGroup) object;
                    target.setTrue( (com.orient.jpdl.model.True) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.True();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.True");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _true
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method getAccessMode.
     * 
     * @return the access mode specified for this class.
     */
    @Override()
    public org.exolab.castor.mapping.AccessMode getAccessMode(
    ) {
        return null;
    }

    /**
     * Method getIdentity.
     * 
     * @return the identity field, null if this class has no
     * identity.
     */
    @Override()
    public org.exolab.castor.mapping.FieldDescriptor getIdentity(
    ) {
        return _identity;
    }

    /**
     * Method getJavaClass.
     * 
     * @return the Java class represented by this descriptor.
     */
    @Override()
    public java.lang.Class getJavaClass(
    ) {
        return com.orient.jpdl.model.WireObjectGroup.class;
    }

    /**
     * Method getNameSpacePrefix.
     * 
     * @return the namespace prefix to use when marshaling as XML.
     */
    @Override()
    public java.lang.String getNameSpacePrefix(
    ) {
        return _nsPrefix;
    }

    /**
     * Method getNameSpaceURI.
     * 
     * @return the namespace URI used when marshaling and
     * unmarshaling as XML.
     */
    @Override()
    public java.lang.String getNameSpaceURI(
    ) {
        return _nsURI;
    }

    /**
     * Method getValidator.
     * 
     * @return a specific validator for the class described by this
     * ClassDescriptor.
     */
    @Override()
    public org.exolab.castor.xml.TypeValidator getValidator(
    ) {
        return this;
    }

    /**
     * Method getXMLName.
     * 
     * @return the XML Name for the Class being described.
     */
    @Override()
    public java.lang.String getXMLName(
    ) {
        return _xmlName;
    }

    /**
     * Method isElementDefinition.
     * 
     * @return true if XML schema definition of this Class is that
     * of a global
     * element or element with anonymous type definition.
     */
    public boolean isElementDefinition(
    ) {
        return _elementDefinition;
    }

}
