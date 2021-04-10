/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * Waits while a sub process instance is being executed
 *  and continues when the sub process instance ends.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class SubProcess implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * References a sub-process by id. This means that a specific
     *  version of a process definition is referenced.
     */
    private java.lang.String _subProcessId;

    /**
     * References a sub-process by key. Therefore, the latest
     *  version of the process definition with the given key is
     * referenced.
     *  The latest version is resolved in each execution.
     */
    private java.lang.String _subProcessKey;

    /**
     * Expression that is evaluated when the sub process instance
     * ends.
     *  The value is then used for outcome transition mapping.
     */
    private java.lang.String _outcome;

    /**
     * Name of this activity. The name should be unique
     *  in the complete scope of the process.
     */
    private java.lang.String _name;

    /**
     * Graphical information used by process designer tool.
     */
    private java.lang.String _g;

    /**
     * Specifies async continuation.
     */
    private com.orient.jpdl.model.types.ContinueType _continue = com.orient.jpdl.model.types.ContinueType.fromValue("sync");

    /**
     * Field _description.
     */
    private java.lang.String _description;

    /**
     * Field _subProcessChoice.
     */
    private com.orient.jpdl.model.SubProcessChoice _subProcessChoice;


      //----------------/
     //- Constructors -/
    //----------------/

    public SubProcess() {
        super();
        setContinue(com.orient.jpdl.model.types.ContinueType.fromValue("sync"));
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'continue'. The field 'continue'
     * has the following description: Specifies async continuation.
     * 
     * @return the value of field 'Continue'.
     */
    public com.orient.jpdl.model.types.ContinueType getContinue(
    ) {
        return this._continue;
    }

    /**
     * Returns the value of field 'description'.
     * 
     * @return the value of field 'Description'.
     */
    public java.lang.String getDescription(
    ) {
        return this._description;
    }

    /**
     * Returns the value of field 'g'. The field 'g' has the
     * following description: Graphical information used by process
     * designer tool.
     * 
     * @return the value of field 'G'.
     */
    public java.lang.String getG(
    ) {
        return this._g;
    }

    /**
     * Returns the value of field 'name'. The field 'name' has the
     * following description: Name of this activity. The name
     * should be unique
     *  in the complete scope of the process.
     * 
     * @return the value of field 'Name'.
     */
    public java.lang.String getName(
    ) {
        return this._name;
    }

    /**
     * Returns the value of field 'outcome'. The field 'outcome'
     * has the following description: Expression that is evaluated
     * when the sub process instance ends.
     *  The value is then used for outcome transition mapping.
     * 
     * @return the value of field 'Outcome'.
     */
    public java.lang.String getOutcome(
    ) {
        return this._outcome;
    }

    /**
     * Returns the value of field 'subProcessChoice'.
     * 
     * @return the value of field 'SubProcessChoice'.
     */
    public com.orient.jpdl.model.SubProcessChoice getSubProcessChoice(
    ) {
        return this._subProcessChoice;
    }

    /**
     * Returns the value of field 'subProcessId'. The field
     * 'subProcessId' has the following description: References a
     * sub-process by id. This means that a specific
     *  version of a process definition is referenced.
     * 
     * @return the value of field 'SubProcessId'.
     */
    public java.lang.String getSubProcessId(
    ) {
        return this._subProcessId;
    }

    /**
     * Returns the value of field 'subProcessKey'. The field
     * 'subProcessKey' has the following description: References a
     * sub-process by key. Therefore, the latest
     *  version of the process definition with the given key is
     * referenced.
     *  The latest version is resolved in each execution.
     * 
     * @return the value of field 'SubProcessKey'.
     */
    public java.lang.String getSubProcessKey(
    ) {
        return this._subProcessKey;
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
     * Sets the value of field 'continue'. The field 'continue' has
     * the following description: Specifies async continuation.
     * 
     * @param _continue
     * @param continue the value of field 'continue'.
     */
    public void setContinue(
            final com.orient.jpdl.model.types.ContinueType _continue) {
        this._continue = _continue;
    }

    /**
     * Sets the value of field 'description'.
     * 
     * @param description the value of field 'description'.
     */
    public void setDescription(
            final java.lang.String description) {
        this._description = description;
    }

    /**
     * Sets the value of field 'g'. The field 'g' has the following
     * description: Graphical information used by process designer
     * tool.
     * 
     * @param g the value of field 'g'.
     */
    public void setG(
            final java.lang.String g) {
        this._g = g;
    }

    /**
     * Sets the value of field 'name'. The field 'name' has the
     * following description: Name of this activity. The name
     * should be unique
     *  in the complete scope of the process.
     * 
     * @param name the value of field 'name'.
     */
    public void setName(
            final java.lang.String name) {
        this._name = name;
    }

    /**
     * Sets the value of field 'outcome'. The field 'outcome' has
     * the following description: Expression that is evaluated when
     * the sub process instance ends.
     *  The value is then used for outcome transition mapping.
     * 
     * @param outcome the value of field 'outcome'.
     */
    public void setOutcome(
            final java.lang.String outcome) {
        this._outcome = outcome;
    }

    /**
     * Sets the value of field 'subProcessChoice'.
     * 
     * @param subProcessChoice the value of field 'subProcessChoice'
     */
    public void setSubProcessChoice(
            final com.orient.jpdl.model.SubProcessChoice subProcessChoice) {
        this._subProcessChoice = subProcessChoice;
    }

    /**
     * Sets the value of field 'subProcessId'. The field
     * 'subProcessId' has the following description: References a
     * sub-process by id. This means that a specific
     *  version of a process definition is referenced.
     * 
     * @param subProcessId the value of field 'subProcessId'.
     */
    public void setSubProcessId(
            final java.lang.String subProcessId) {
        this._subProcessId = subProcessId;
    }

    /**
     * Sets the value of field 'subProcessKey'. The field
     * 'subProcessKey' has the following description: References a
     * sub-process by key. Therefore, the latest
     *  version of the process definition with the given key is
     * referenced.
     *  The latest version is resolved in each execution.
     * 
     * @param subProcessKey the value of field 'subProcessKey'.
     */
    public void setSubProcessKey(
            final java.lang.String subProcessKey) {
        this._subProcessKey = subProcessKey;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled com.orient.jpdl.model.SubProcess
     */
    public static com.orient.jpdl.model.SubProcess unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.orient.jpdl.model.SubProcess) org.exolab.castor.xml.Unmarshaller.unmarshal(com.orient.jpdl.model.SubProcess.class, reader);
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
