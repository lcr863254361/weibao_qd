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

import com.orient.jpdl.model.ProcessChoiceItem;

/**
 * Class ProcessChoiceItemDescriptor.
 * 
 * @version $Revision$ $Date$
 */
public class ProcessChoiceItemDescriptor extends org.exolab.castor.xml.util.XMLClassDescriptorImpl {


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

    public ProcessChoiceItemDescriptor() {
        super();
        _nsURI = "http://jbpm.org/4.4/jpdl";
        _elementDefinition = false;

        //-- set grouping compositor
        setCompositorAsChoice();
        org.exolab.castor.xml.util.XMLFieldDescriptorImpl  desc           = null;
        org.exolab.castor.mapping.FieldHandler             handler        = null;
        org.exolab.castor.xml.FieldValidator               fieldValidator = null;
        //-- initialize attribute descriptors

        //-- initialize element descriptors

        //-- _swimlane
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.Swimlane.class, "_swimlane", "swimlane", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                ProcessChoiceItem target = (ProcessChoiceItem) object;
                return target.getSwimlane();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    ProcessChoiceItem target = (ProcessChoiceItem) object;
                    target.setSwimlane( (com.orient.jpdl.model.Swimlane) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.Swimlane();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.Swimlane");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _swimlane
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _on
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.On.class, "_on", "on", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                ProcessChoiceItem target = (ProcessChoiceItem) object;
                return target.getOn();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    ProcessChoiceItem target = (ProcessChoiceItem) object;
                    target.setOn( (com.orient.jpdl.model.On) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.On();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.On");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _on
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _timer
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.Timer.class, "_timer", "timer", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                ProcessChoiceItem target = (ProcessChoiceItem) object;
                return target.getTimer();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    ProcessChoiceItem target = (ProcessChoiceItem) object;
                    target.setTimer( (com.orient.jpdl.model.Timer) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.Timer();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.Timer");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _timer
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _variable
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.Variable.class, "_variable", "variable", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                ProcessChoiceItem target = (ProcessChoiceItem) object;
                return target.getVariable();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    ProcessChoiceItem target = (ProcessChoiceItem) object;
                    target.setVariable( (com.orient.jpdl.model.Variable) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.Variable();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.Variable");
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _variable
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _activityGroup
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(com.orient.jpdl.model.ActivityGroup.class, "_activityGroup", "-error-if-this-is-used-", org.exolab.castor.xml.NodeType.Element);
        handler = new org.exolab.castor.xml.XMLFieldHandler() {
            @Override
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                ProcessChoiceItem target = (ProcessChoiceItem) object;
                return target.getActivityGroup();
            }
            @Override
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    ProcessChoiceItem target = (ProcessChoiceItem) object;
                    target.setActivityGroup( (com.orient.jpdl.model.ActivityGroup) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            @Override
            @SuppressWarnings("unused")
            public java.lang.Object newInstance(java.lang.Object parent) {
                return new com.orient.jpdl.model.ActivityGroup();
            }
        };
        desc.setSchemaType("com.orient.jpdl.model.ActivityGroup");
        desc.setHandler(handler);
        desc.setContainer(true);
        desc.setClassDescriptor(new com.orient.jpdl.model.descriptors.ActivityGroupDescriptor());
        desc.setNameSpaceURI("http://jbpm.org/4.4/jpdl");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);
        addSequenceElement(desc);

        //-- validation code for: _activityGroup
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
        return com.orient.jpdl.model.ProcessChoiceItem.class;
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
