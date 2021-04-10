/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * Process role.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class Swimlane implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _name.
     */
    private java.lang.String _name;

    /**
     * Expression that resolves to a userId referencing
     *  the person to whom the task or swimlane will be assigned.
     */
    private java.lang.String _assignee;

    /**
     * Expression language for the assignee attribute.
     */
    private java.lang.String _assigneeLang;

    /**
     * Expression that resolves to a comma separated
     *  list of userIds. All the referred people will be candidates
     * for
     *  take the task or swimlane.
     */
    private java.lang.String _candidateUsers;

    /**
     * Expression language for the
     *  candidate-users attribute.
     */
    private java.lang.String _candidateUsersLang;

    /**
     * Resolves to a comma separated list of groupIds.
     *  All the referred people will be candidates to
     *  take the task or swimlane.
     */
    private java.lang.String _candidateGroups;

    /**
     * Expression language for the
     *  candidate-groups attribute.
     */
    private java.lang.String _candidateGroupsLang;

    /**
     * Field _description.
     */
    private java.lang.String _description;


      //----------------/
     //- Constructors -/
    //----------------/

    public Swimlane() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'assignee'. The field 'assignee'
     * has the following description: Expression that resolves to a
     * userId referencing
     *  the person to whom the task or swimlane will be assigned.
     * 
     * @return the value of field 'Assignee'.
     */
    public java.lang.String getAssignee(
    ) {
        return this._assignee;
    }

    /**
     * Returns the value of field 'assigneeLang'. The field
     * 'assigneeLang' has the following description: Expression
     * language for the assignee attribute.
     * 
     * @return the value of field 'AssigneeLang'.
     */
    public java.lang.String getAssigneeLang(
    ) {
        return this._assigneeLang;
    }

    /**
     * Returns the value of field 'candidateGroups'. The field
     * 'candidateGroups' has the following description: Resolves to
     * a comma separated list of groupIds.
     *  All the referred people will be candidates to
     *  take the task or swimlane.
     * 
     * @return the value of field 'CandidateGroups'.
     */
    public java.lang.String getCandidateGroups(
    ) {
        return this._candidateGroups;
    }

    /**
     * Returns the value of field 'candidateGroupsLang'. The field
     * 'candidateGroupsLang' has the following description:
     * Expression language for the
     *  candidate-groups attribute.
     * 
     * @return the value of field 'CandidateGroupsLang'.
     */
    public java.lang.String getCandidateGroupsLang(
    ) {
        return this._candidateGroupsLang;
    }

    /**
     * Returns the value of field 'candidateUsers'. The field
     * 'candidateUsers' has the following description: Expression
     * that resolves to a comma separated
     *  list of userIds. All the referred people will be candidates
     * for
     *  take the task or swimlane.
     * 
     * @return the value of field 'CandidateUsers'.
     */
    public java.lang.String getCandidateUsers(
    ) {
        return this._candidateUsers;
    }

    /**
     * Returns the value of field 'candidateUsersLang'. The field
     * 'candidateUsersLang' has the following description:
     * Expression language for the
     *  candidate-users attribute.
     * 
     * @return the value of field 'CandidateUsersLang'.
     */
    public java.lang.String getCandidateUsersLang(
    ) {
        return this._candidateUsersLang;
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
     * Returns the value of field 'name'.
     * 
     * @return the value of field 'Name'.
     */
    public java.lang.String getName(
    ) {
        return this._name;
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
     * Sets the value of field 'assignee'. The field 'assignee' has
     * the following description: Expression that resolves to a
     * userId referencing
     *  the person to whom the task or swimlane will be assigned.
     * 
     * @param assignee the value of field 'assignee'.
     */
    public void setAssignee(
            final java.lang.String assignee) {
        this._assignee = assignee;
    }

    /**
     * Sets the value of field 'assigneeLang'. The field
     * 'assigneeLang' has the following description: Expression
     * language for the assignee attribute.
     * 
     * @param assigneeLang the value of field 'assigneeLang'.
     */
    public void setAssigneeLang(
            final java.lang.String assigneeLang) {
        this._assigneeLang = assigneeLang;
    }

    /**
     * Sets the value of field 'candidateGroups'. The field
     * 'candidateGroups' has the following description: Resolves to
     * a comma separated list of groupIds.
     *  All the referred people will be candidates to
     *  take the task or swimlane.
     * 
     * @param candidateGroups the value of field 'candidateGroups'.
     */
    public void setCandidateGroups(
            final java.lang.String candidateGroups) {
        this._candidateGroups = candidateGroups;
    }

    /**
     * Sets the value of field 'candidateGroupsLang'. The field
     * 'candidateGroupsLang' has the following description:
     * Expression language for the
     *  candidate-groups attribute.
     * 
     * @param candidateGroupsLang the value of field
     * 'candidateGroupsLang'.
     */
    public void setCandidateGroupsLang(
            final java.lang.String candidateGroupsLang) {
        this._candidateGroupsLang = candidateGroupsLang;
    }

    /**
     * Sets the value of field 'candidateUsers'. The field
     * 'candidateUsers' has the following description: Expression
     * that resolves to a comma separated
     *  list of userIds. All the referred people will be candidates
     * for
     *  take the task or swimlane.
     * 
     * @param candidateUsers the value of field 'candidateUsers'.
     */
    public void setCandidateUsers(
            final java.lang.String candidateUsers) {
        this._candidateUsers = candidateUsers;
    }

    /**
     * Sets the value of field 'candidateUsersLang'. The field
     * 'candidateUsersLang' has the following description:
     * Expression language for the
     *  candidate-users attribute.
     * 
     * @param candidateUsersLang the value of field
     * 'candidateUsersLang'.
     */
    public void setCandidateUsersLang(
            final java.lang.String candidateUsersLang) {
        this._candidateUsersLang = candidateUsersLang;
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
     * Sets the value of field 'name'.
     * 
     * @param name the value of field 'name'.
     */
    public void setName(
            final java.lang.String name) {
        this._name = name;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled com.orient.jpdl.model.Swimlane
     */
    public static com.orient.jpdl.model.Swimlane unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.orient.jpdl.model.Swimlane) org.exolab.castor.xml.Unmarshaller.unmarshal(com.orient.jpdl.model.Swimlane.class, reader);
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
