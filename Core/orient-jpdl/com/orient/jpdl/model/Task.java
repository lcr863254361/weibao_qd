/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * Creates a task in the task component.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class Task implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _swimlane.
     */
    private java.lang.String _swimlane;

    /**
     * Resource name of the form in the deployment.
     */
    private java.lang.String _form;

    /**
     * Field _duedate.
     */
    private java.lang.String _duedate;

    /**
     * Field _onTransition.
     */
    private com.orient.jpdl.model.types.TaskOnTransitionType _onTransition = com.orient.jpdl.model.types.TaskOnTransitionType.fromValue("cancel");

    /**
     * Field _completion.
     */
    private java.lang.String _completion = "complete";

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

    /**
     * Field _assignmentHandler.
     */
    private com.orient.jpdl.model.AssignmentHandler _assignmentHandler;

    /**
     * Field _notification.
     */
    private com.orient.jpdl.model.Notification _notification;

    /**
     * Field _reminder.
     */
    private com.orient.jpdl.model.Reminder _reminder;

    /**
     * Field _taskChoice.
     */
    private com.orient.jpdl.model.TaskChoice _taskChoice;


      //----------------/
     //- Constructors -/
    //----------------/

    public Task() {
        super();
        setOnTransition(com.orient.jpdl.model.types.TaskOnTransitionType.fromValue("cancel"));
        setCompletion("complete");
        setContinue(com.orient.jpdl.model.types.ContinueType.fromValue("sync"));
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
     * Returns the value of field 'assignmentHandler'.
     * 
     * @return the value of field 'AssignmentHandler'.
     */
    public com.orient.jpdl.model.AssignmentHandler getAssignmentHandler(
    ) {
        return this._assignmentHandler;
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
     * Returns the value of field 'completion'.
     * 
     * @return the value of field 'Completion'.
     */
    public java.lang.String getCompletion(
    ) {
        return this._completion;
    }

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
     * Returns the value of field 'duedate'.
     * 
     * @return the value of field 'Duedate'.
     */
    public java.lang.String getDuedate(
    ) {
        return this._duedate;
    }

    /**
     * Returns the value of field 'form'. The field 'form' has the
     * following description: Resource name of the form in the
     * deployment.
     * 
     * @return the value of field 'Form'.
     */
    public java.lang.String getForm(
    ) {
        return this._form;
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
     * Returns the value of field 'notification'.
     * 
     * @return the value of field 'Notification'.
     */
    public com.orient.jpdl.model.Notification getNotification(
    ) {
        return this._notification;
    }

    /**
     * Returns the value of field 'onTransition'.
     * 
     * @return the value of field 'OnTransition'.
     */
    public com.orient.jpdl.model.types.TaskOnTransitionType getOnTransition(
    ) {
        return this._onTransition;
    }

    /**
     * Returns the value of field 'reminder'.
     * 
     * @return the value of field 'Reminder'.
     */
    public com.orient.jpdl.model.Reminder getReminder(
    ) {
        return this._reminder;
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
     * Returns the value of field 'taskChoice'.
     * 
     * @return the value of field 'TaskChoice'.
     */
    public com.orient.jpdl.model.TaskChoice getTaskChoice(
    ) {
        return this._taskChoice;
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
     * Sets the value of field 'assignmentHandler'.
     * 
     * @param assignmentHandler the value of field
     * 'assignmentHandler'.
     */
    public void setAssignmentHandler(
            final com.orient.jpdl.model.AssignmentHandler assignmentHandler) {
        this._assignmentHandler = assignmentHandler;
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
     * Sets the value of field 'completion'.
     * 
     * @param completion the value of field 'completion'.
     */
    public void setCompletion(
            final java.lang.String completion) {
        this._completion = completion;
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
     * Sets the value of field 'duedate'.
     * 
     * @param duedate the value of field 'duedate'.
     */
    public void setDuedate(
            final java.lang.String duedate) {
        this._duedate = duedate;
    }

    /**
     * Sets the value of field 'form'. The field 'form' has the
     * following description: Resource name of the form in the
     * deployment.
     * 
     * @param form the value of field 'form'.
     */
    public void setForm(
            final java.lang.String form) {
        this._form = form;
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
     * Sets the value of field 'notification'.
     * 
     * @param notification the value of field 'notification'.
     */
    public void setNotification(
            final com.orient.jpdl.model.Notification notification) {
        this._notification = notification;
    }

    /**
     * Sets the value of field 'onTransition'.
     * 
     * @param onTransition the value of field 'onTransition'.
     */
    public void setOnTransition(
            final com.orient.jpdl.model.types.TaskOnTransitionType onTransition) {
        this._onTransition = onTransition;
    }

    /**
     * Sets the value of field 'reminder'.
     * 
     * @param reminder the value of field 'reminder'.
     */
    public void setReminder(
            final com.orient.jpdl.model.Reminder reminder) {
        this._reminder = reminder;
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
     * Sets the value of field 'taskChoice'.
     * 
     * @param taskChoice the value of field 'taskChoice'.
     */
    public void setTaskChoice(
            final com.orient.jpdl.model.TaskChoice taskChoice) {
        this._taskChoice = taskChoice;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled com.orient.jpdl.model.Task
     */
    public static com.orient.jpdl.model.Task unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.orient.jpdl.model.Task) org.exolab.castor.xml.Unmarshaller.unmarshal(com.orient.jpdl.model.Task.class, reader);
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
