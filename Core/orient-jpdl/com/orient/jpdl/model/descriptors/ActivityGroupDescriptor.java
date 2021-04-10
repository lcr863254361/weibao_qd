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

import com.orient.jpdl.model.ActivityGroup;

/**
 * Class ActivityGroupDescriptor.
 * 
 * @version $Revision$ $Date$
 */
public class ActivityGroupDescriptor extends org.exolab.castor.xml.util.XMLClassDescriptorImpl {


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

    public ActivityGroupDescriptor() {
        super();
        _nsURI = "http://jbpm.org/4.4/jpdl";
        _xmlName = "activityGroup";
        _elementDefinition = false;

        //-- set grouping compositor
        setCompositorAsChoice();
        org.exolab.castor.xml.util.XMLFieldDescriptorImpl  desc           = null;
        org.exolab.castor.mapping.FieldHandler             handler        = null;
        org.exolab.castor.xml.FieldValidator               fieldValidator = null;
        //-- initialize attribute descriptors

        //-- initialize element descriptors

        //-- _start
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.Start.class, "_start", "start", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                ActivityGroup target = (ActivityGroup) object;
                return target.getStart();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    ActivityGroup target = (ActivityGroup) object;
                    target.setStart( (com.orient.jpdl.model.Start) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.Start();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.Start");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _start
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _end
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.End.class, "_end", "end", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                ActivityGroup target = (ActivityGroup) object;
                return target.getEnd();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    ActivityGroup target = (ActivityGroup) object;
                    target.setEnd( (com.orient.jpdl.model.End) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.End();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.End");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _end
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _endCancel
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.EndCancel.class, "_endCancel", "end-cancel", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                ActivityGroup target = (ActivityGroup) object;
                return target.getEndCancel();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    ActivityGroup target = (ActivityGroup) object;
                    target.setEndCancel( (com.orient.jpdl.model.EndCancel) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.EndCancel();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.EndCancel");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _endCancel
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _endError
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.EndError.class, "_endError", "end-error", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                ActivityGroup target = (ActivityGroup) object;
                return target.getEndError();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    ActivityGroup target = (ActivityGroup) object;
                    target.setEndError( (com.orient.jpdl.model.EndError) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.EndError();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.EndError");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _endError
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _state
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.State.class, "_state", "state", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                ActivityGroup target = (ActivityGroup) object;
                return target.getState();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    ActivityGroup target = (ActivityGroup) object;
                    target.setState( (com.orient.jpdl.model.State) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.State();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.State");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _state
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _decision
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.Decision.class, "_decision", "decision", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                ActivityGroup target = (ActivityGroup) object;
                return target.getDecision();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    ActivityGroup target = (ActivityGroup) object;
                    target.setDecision( (com.orient.jpdl.model.Decision) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.Decision();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.Decision");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _decision
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _foreach
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.Foreach.class, "_foreach", "foreach", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                ActivityGroup target = (ActivityGroup) object;
                return target.getForeach();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    ActivityGroup target = (ActivityGroup) object;
                    target.setForeach( (com.orient.jpdl.model.Foreach) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.Foreach();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.Foreach");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _foreach
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _fork
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.Fork.class, "_fork", "fork", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                ActivityGroup target = (ActivityGroup) object;
                return target.getFork();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    ActivityGroup target = (ActivityGroup) object;
                    target.setFork( (com.orient.jpdl.model.Fork) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.Fork();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.Fork");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _fork
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _join
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.Join.class, "_join", "join", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                ActivityGroup target = (ActivityGroup) object;
                return target.getJoin();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    ActivityGroup target = (ActivityGroup) object;
                    target.setJoin( (com.orient.jpdl.model.Join) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.Join();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.Join");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _join
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _script
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.Script.class, "_script", "script", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                ActivityGroup target = (ActivityGroup) object;
                return target.getScript();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    ActivityGroup target = (ActivityGroup) object;
                    target.setScript( (com.orient.jpdl.model.Script) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.Script();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.Script");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _script
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _hql
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.Hql.class, "_hql", "hql", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                ActivityGroup target = (ActivityGroup) object;
                return target.getHql();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    ActivityGroup target = (ActivityGroup) object;
                    target.setHql( (com.orient.jpdl.model.Hql) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.Hql();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.Hql");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _hql
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _sql
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.Sql.class, "_sql", "sql", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                ActivityGroup target = (ActivityGroup) object;
                return target.getSql();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    ActivityGroup target = (ActivityGroup) object;
                    target.setSql( (com.orient.jpdl.model.Sql) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.Sql();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.Sql");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _sql
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _mail
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.Mail.class, "_mail", "mail", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                ActivityGroup target = (ActivityGroup) object;
                return target.getMail();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    ActivityGroup target = (ActivityGroup) object;
                    target.setMail( (com.orient.jpdl.model.Mail) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.Mail();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.Mail");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _mail
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _jms
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.Jms.class, "_jms", "jms", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                ActivityGroup target = (ActivityGroup) object;
                return target.getJms();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    ActivityGroup target = (ActivityGroup) object;
                    target.setJms( (com.orient.jpdl.model.Jms) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.Jms();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.Jms");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _jms
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _java
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.Java.class, "_java", "java", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                ActivityGroup target = (ActivityGroup) object;
                return target.getJava();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    ActivityGroup target = (ActivityGroup) object;
                    target.setJava( (com.orient.jpdl.model.Java) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.Java();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.Java");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _java
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _assign
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.Assign.class, "_assign", "assign", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                ActivityGroup target = (ActivityGroup) object;
                return target.getAssign();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    ActivityGroup target = (ActivityGroup) object;
                    target.setAssign( (com.orient.jpdl.model.Assign) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.Assign();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.Assign");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _assign
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _daq
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.Daq.class, "_daq", "daq", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                ActivityGroup target = (ActivityGroup) object;
                return target.getDaq();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    ActivityGroup target = (ActivityGroup) object;
                    target.setDaq( (com.orient.jpdl.model.Daq) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.Daq();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.Daq");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _daq
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _simulation
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.Simulation.class, "_simulation", "simulation", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                ActivityGroup target = (ActivityGroup) object;
                return target.getSimulation();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    ActivityGroup target = (ActivityGroup) object;
                    target.setSimulation( (com.orient.jpdl.model.Simulation) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.Simulation();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.Simulation");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _simulation
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _analysis
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.Analysis.class, "_analysis", "analysis", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                ActivityGroup target = (ActivityGroup) object;
                return target.getAnalysis();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    ActivityGroup target = (ActivityGroup) object;
                    target.setAnalysis( (com.orient.jpdl.model.Analysis) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.Analysis();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.Analysis");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _analysis
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _custom
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.Custom.class, "_custom", "custom", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                ActivityGroup target = (ActivityGroup) object;
                return target.getCustom();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    ActivityGroup target = (ActivityGroup) object;
                    target.setCustom( (com.orient.jpdl.model.Custom) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.Custom();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.Custom");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _custom
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _task
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.Task.class, "_task", "task", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                ActivityGroup target = (ActivityGroup) object;
                return target.getTask();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    ActivityGroup target = (ActivityGroup) object;
                    target.setTask( (com.orient.jpdl.model.Task) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.Task();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.Task");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _task
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _subProcess
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.SubProcess.class, "_subProcess", "sub-process", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                ActivityGroup target = (ActivityGroup) object;
                return target.getSubProcess();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    ActivityGroup target = (ActivityGroup) object;
                    target.setSubProcess( (com.orient.jpdl.model.SubProcess) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.SubProcess();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.SubProcess");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _subProcess
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _group
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.Group.class, "_group", "group", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                ActivityGroup target = (ActivityGroup) object;
                return target.getGroup();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    ActivityGroup target = (ActivityGroup) object;
                    target.setGroup( (com.orient.jpdl.model.Group) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.Group();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.Group");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _group
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _rulesDecision
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.RulesDecision.class, "_rulesDecision", "rules-decision", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                ActivityGroup target = (ActivityGroup) object;
                return target.getRulesDecision();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    ActivityGroup target = (ActivityGroup) object;
                    target.setRulesDecision( (com.orient.jpdl.model.RulesDecision) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.RulesDecision();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.RulesDecision");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _rulesDecision
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _rules
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.Rules.class, "_rules", "rules", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                ActivityGroup target = (ActivityGroup) object;
                return target.getRules();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    ActivityGroup target = (ActivityGroup) object;
                    target.setRules( (com.orient.jpdl.model.Rules) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.Rules();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.Rules");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _rules
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
        return com.orient.jpdl.model.ActivityGroup.class;
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
