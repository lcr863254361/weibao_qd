/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * Class ActivityGroup.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class ActivityGroup implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Internal choice value storage
     */
    private java.lang.Object _choiceValue;

    /**
     * Start event.
     */
    private com.orient.jpdl.model.Start _start;

    /**
     * End event.
     */
    private com.orient.jpdl.model.End _end;

    /**
     * End cancel event.
     */
    private com.orient.jpdl.model.EndCancel _endCancel;

    /**
     * End cancel event.
     */
    private com.orient.jpdl.model.EndError _endError;

    /**
     * Wait state. When an execution arrives in this activity,
     *  the execution stops until an external trigger is delivered
     * through
     *  execution.signal() or
     * execution.getActivityInstance().signal()
     */
    private com.orient.jpdl.model.State _state;

    /**
     * Decision gateway: selects one path out of many alternatives.
     *  When an execution arrives, it takes exactly one outgoing
     * transition.
     */
    private com.orient.jpdl.model.Decision _decision;

    /**
     * Spawns concurrent paths of execution
     *  over each element of a collection.
     */
    private com.orient.jpdl.model.Foreach _foreach;

    /**
     * Spawns multiple concurrent paths of
     *  execution.
     */
    private com.orient.jpdl.model.Fork _fork;

    /**
     * Spawns multiple concurrent paths of
     *  execution.
     */
    private com.orient.jpdl.model.Join _join;

    /**
     * Evaluates a piece of text as a script.
     */
    private com.orient.jpdl.model.Script _script;

    /**
     * Performs a Hibernate query.
     */
    private com.orient.jpdl.model.Hql _hql;

    /**
     * Performs a Hibernate SQL query.
     */
    private com.orient.jpdl.model.Sql _sql;

    /**
     * Sends an email.
     */
    private com.orient.jpdl.model.Mail _mail;

    /**
     * Sends an email.
     */
    private com.orient.jpdl.model.Jms _jms;

    /**
     * Invokes a method on a java object.
     *  Either the java class is instantiated with reflection,
     *  or the java object is fetched from the environment.
     *  Values are then injected into the fields and.
     *  Finally a method is invoked.
     */
    private com.orient.jpdl.model.Java _java;

    /**
     * Assigns a source value to a target location.
     */
    private com.orient.jpdl.model.Assign _assign;

    /**
     * create a daq activity.
     */
    private com.orient.jpdl.model.Daq _daq;

    /**
     * create a simulation activity.
     */
    private com.orient.jpdl.model.Simulation _simulation;

    /**
     * create a analysis activity.
     */
    private com.orient.jpdl.model.Analysis _analysis;

    /**
     * Calls a user defined, custom implementation of
     *  ActivityBehaviour.
     */
    private com.orient.jpdl.model.Custom _custom;

    /**
     * Creates a task in the task component.
     */
    private com.orient.jpdl.model.Task _task;

    /**
     * Waits while a sub process instance is being executed
     *  and continues when the sub process instance ends.
     */
    private com.orient.jpdl.model.SubProcess _subProcess;

    /**
     * Scope enclosing a number of activities.
     */
    private com.orient.jpdl.model.Group _group;

    /**
     * Selects one outgoing transition based on evaluation
     *  of rules.
     */
    private com.orient.jpdl.model.RulesDecision _rulesDecision;

    /**
     * Evaluates rules after feeding in some facts in a stateful
     *  knowledge session.
     */
    private com.orient.jpdl.model.Rules _rules;


      //----------------/
     //- Constructors -/
    //----------------/

    public ActivityGroup() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'analysis'. The field 'analysis'
     * has the following description: create a analysis activity.
     * 
     * @return the value of field 'Analysis'.
     */
    public com.orient.jpdl.model.Analysis getAnalysis(
    ) {
        return this._analysis;
    }

    /**
     * Returns the value of field 'assign'. The field 'assign' has
     * the following description: Assigns a source value to a
     * target location.
     * 
     * @return the value of field 'Assign'.
     */
    public com.orient.jpdl.model.Assign getAssign(
    ) {
        return this._assign;
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
     * Returns the value of field 'custom'. The field 'custom' has
     * the following description: Calls a user defined, custom
     * implementation of
     *  ActivityBehaviour.
     * 
     * @return the value of field 'Custom'.
     */
    public com.orient.jpdl.model.Custom getCustom(
    ) {
        return this._custom;
    }

    /**
     * Returns the value of field 'daq'. The field 'daq' has the
     * following description: create a daq activity.
     * 
     * @return the value of field 'Daq'.
     */
    public com.orient.jpdl.model.Daq getDaq(
    ) {
        return this._daq;
    }

    /**
     * Returns the value of field 'decision'. The field 'decision'
     * has the following description: Decision gateway: selects one
     * path out of many alternatives.
     *  When an execution arrives, it takes exactly one outgoing
     * transition.
     * 
     * @return the value of field 'Decision'.
     */
    public com.orient.jpdl.model.Decision getDecision(
    ) {
        return this._decision;
    }

    /**
     * Returns the value of field 'end'. The field 'end' has the
     * following description: End event.
     * 
     * @return the value of field 'End'.
     */
    public com.orient.jpdl.model.End getEnd(
    ) {
        return this._end;
    }

    /**
     * Returns the value of field 'endCancel'. The field
     * 'endCancel' has the following description: End cancel event.
     * 
     * @return the value of field 'EndCancel'.
     */
    public com.orient.jpdl.model.EndCancel getEndCancel(
    ) {
        return this._endCancel;
    }

    /**
     * Returns the value of field 'endError'. The field 'endError'
     * has the following description: End cancel event.
     * 
     * @return the value of field 'EndError'.
     */
    public com.orient.jpdl.model.EndError getEndError(
    ) {
        return this._endError;
    }

    /**
     * Returns the value of field 'foreach'. The field 'foreach'
     * has the following description: Spawns concurrent paths of
     * execution
     *  over each element of a collection.
     * 
     * @return the value of field 'Foreach'.
     */
    public com.orient.jpdl.model.Foreach getForeach(
    ) {
        return this._foreach;
    }

    /**
     * Returns the value of field 'fork'. The field 'fork' has the
     * following description: Spawns multiple concurrent paths of
     *  execution.
     * 
     * @return the value of field 'Fork'.
     */
    public com.orient.jpdl.model.Fork getFork(
    ) {
        return this._fork;
    }

    /**
     * Returns the value of field 'group'. The field 'group' has
     * the following description: Scope enclosing a number of
     * activities.
     * 
     * @return the value of field 'Group'.
     */
    public com.orient.jpdl.model.Group getGroup(
    ) {
        return this._group;
    }

    /**
     * Returns the value of field 'hql'. The field 'hql' has the
     * following description: Performs a Hibernate query.
     * 
     * @return the value of field 'Hql'.
     */
    public com.orient.jpdl.model.Hql getHql(
    ) {
        return this._hql;
    }

    /**
     * Returns the value of field 'java'. The field 'java' has the
     * following description: Invokes a method on a java object.
     *  Either the java class is instantiated with reflection,
     *  or the java object is fetched from the environment.
     *  Values are then injected into the fields and.
     *  Finally a method is invoked.
     * 
     * @return the value of field 'Java'.
     */
    public com.orient.jpdl.model.Java getJava(
    ) {
        return this._java;
    }

    /**
     * Returns the value of field 'jms'. The field 'jms' has the
     * following description: Sends an email.
     * 
     * @return the value of field 'Jms'.
     */
    public com.orient.jpdl.model.Jms getJms(
    ) {
        return this._jms;
    }

    /**
     * Returns the value of field 'join'. The field 'join' has the
     * following description: Spawns multiple concurrent paths of
     *  execution.
     * 
     * @return the value of field 'Join'.
     */
    public com.orient.jpdl.model.Join getJoin(
    ) {
        return this._join;
    }

    /**
     * Returns the value of field 'mail'. The field 'mail' has the
     * following description: Sends an email.
     * 
     * @return the value of field 'Mail'.
     */
    public com.orient.jpdl.model.Mail getMail(
    ) {
        return this._mail;
    }

    /**
     * Returns the value of field 'rules'. The field 'rules' has
     * the following description: Evaluates rules after feeding in
     * some facts in a stateful
     *  knowledge session.
     * 
     * @return the value of field 'Rules'.
     */
    public com.orient.jpdl.model.Rules getRules(
    ) {
        return this._rules;
    }

    /**
     * Returns the value of field 'rulesDecision'. The field
     * 'rulesDecision' has the following description: Selects one
     * outgoing transition based on evaluation
     *  of rules.
     * 
     * @return the value of field 'RulesDecision'.
     */
    public com.orient.jpdl.model.RulesDecision getRulesDecision(
    ) {
        return this._rulesDecision;
    }

    /**
     * Returns the value of field 'script'. The field 'script' has
     * the following description: Evaluates a piece of text as a
     * script.
     * 
     * @return the value of field 'Script'.
     */
    public com.orient.jpdl.model.Script getScript(
    ) {
        return this._script;
    }

    /**
     * Returns the value of field 'simulation'. The field
     * 'simulation' has the following description: create a
     * simulation activity.
     * 
     * @return the value of field 'Simulation'.
     */
    public com.orient.jpdl.model.Simulation getSimulation(
    ) {
        return this._simulation;
    }

    /**
     * Returns the value of field 'sql'. The field 'sql' has the
     * following description: Performs a Hibernate SQL query.
     * 
     * @return the value of field 'Sql'.
     */
    public com.orient.jpdl.model.Sql getSql(
    ) {
        return this._sql;
    }

    /**
     * Returns the value of field 'start'. The field 'start' has
     * the following description: Start event.
     * 
     * @return the value of field 'Start'.
     */
    public com.orient.jpdl.model.Start getStart(
    ) {
        return this._start;
    }

    /**
     * Returns the value of field 'state'. The field 'state' has
     * the following description: Wait state. When an execution
     * arrives in this activity,
     *  the execution stops until an external trigger is delivered
     * through
     *  execution.signal() or
     * execution.getActivityInstance().signal()
     * 
     * @return the value of field 'State'.
     */
    public com.orient.jpdl.model.State getState(
    ) {
        return this._state;
    }

    /**
     * Returns the value of field 'subProcess'. The field
     * 'subProcess' has the following description: Waits while a
     * sub process instance is being executed
     *  and continues when the sub process instance ends.
     * 
     * @return the value of field 'SubProcess'.
     */
    public com.orient.jpdl.model.SubProcess getSubProcess(
    ) {
        return this._subProcess;
    }

    /**
     * Returns the value of field 'task'. The field 'task' has the
     * following description: Creates a task in the task component.
     * 
     * @return the value of field 'Task'.
     */
    public com.orient.jpdl.model.Task getTask(
    ) {
        return this._task;
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
     * Sets the value of field 'analysis'. The field 'analysis' has
     * the following description: create a analysis activity.
     * 
     * @param analysis the value of field 'analysis'.
     */
    public void setAnalysis(
            final com.orient.jpdl.model.Analysis analysis) {
        this._analysis = analysis;
        this._choiceValue = analysis;
    }

    /**
     * Sets the value of field 'assign'. The field 'assign' has the
     * following description: Assigns a source value to a target
     * location.
     * 
     * @param assign the value of field 'assign'.
     */
    public void setAssign(
            final com.orient.jpdl.model.Assign assign) {
        this._assign = assign;
        this._choiceValue = assign;
    }

    /**
     * Sets the value of field 'custom'. The field 'custom' has the
     * following description: Calls a user defined, custom
     * implementation of
     *  ActivityBehaviour.
     * 
     * @param custom the value of field 'custom'.
     */
    public void setCustom(
            final com.orient.jpdl.model.Custom custom) {
        this._custom = custom;
        this._choiceValue = custom;
    }

    /**
     * Sets the value of field 'daq'. The field 'daq' has the
     * following description: create a daq activity.
     * 
     * @param daq the value of field 'daq'.
     */
    public void setDaq(
            final com.orient.jpdl.model.Daq daq) {
        this._daq = daq;
        this._choiceValue = daq;
    }

    /**
     * Sets the value of field 'decision'. The field 'decision' has
     * the following description: Decision gateway: selects one
     * path out of many alternatives.
     *  When an execution arrives, it takes exactly one outgoing
     * transition.
     * 
     * @param decision the value of field 'decision'.
     */
    public void setDecision(
            final com.orient.jpdl.model.Decision decision) {
        this._decision = decision;
        this._choiceValue = decision;
    }

    /**
     * Sets the value of field 'end'. The field 'end' has the
     * following description: End event.
     * 
     * @param end the value of field 'end'.
     */
    public void setEnd(
            final com.orient.jpdl.model.End end) {
        this._end = end;
        this._choiceValue = end;
    }

    /**
     * Sets the value of field 'endCancel'. The field 'endCancel'
     * has the following description: End cancel event.
     * 
     * @param endCancel the value of field 'endCancel'.
     */
    public void setEndCancel(
            final com.orient.jpdl.model.EndCancel endCancel) {
        this._endCancel = endCancel;
        this._choiceValue = endCancel;
    }

    /**
     * Sets the value of field 'endError'. The field 'endError' has
     * the following description: End cancel event.
     * 
     * @param endError the value of field 'endError'.
     */
    public void setEndError(
            final com.orient.jpdl.model.EndError endError) {
        this._endError = endError;
        this._choiceValue = endError;
    }

    /**
     * Sets the value of field 'foreach'. The field 'foreach' has
     * the following description: Spawns concurrent paths of
     * execution
     *  over each element of a collection.
     * 
     * @param foreach the value of field 'foreach'.
     */
    public void setForeach(
            final com.orient.jpdl.model.Foreach foreach) {
        this._foreach = foreach;
        this._choiceValue = foreach;
    }

    /**
     * Sets the value of field 'fork'. The field 'fork' has the
     * following description: Spawns multiple concurrent paths of
     *  execution.
     * 
     * @param fork the value of field 'fork'.
     */
    public void setFork(
            final com.orient.jpdl.model.Fork fork) {
        this._fork = fork;
        this._choiceValue = fork;
    }

    /**
     * Sets the value of field 'group'. The field 'group' has the
     * following description: Scope enclosing a number of
     * activities.
     * 
     * @param group the value of field 'group'.
     */
    public void setGroup(
            final com.orient.jpdl.model.Group group) {
        this._group = group;
        this._choiceValue = group;
    }

    /**
     * Sets the value of field 'hql'. The field 'hql' has the
     * following description: Performs a Hibernate query.
     * 
     * @param hql the value of field 'hql'.
     */
    public void setHql(
            final com.orient.jpdl.model.Hql hql) {
        this._hql = hql;
        this._choiceValue = hql;
    }

    /**
     * Sets the value of field 'java'. The field 'java' has the
     * following description: Invokes a method on a java object.
     *  Either the java class is instantiated with reflection,
     *  or the java object is fetched from the environment.
     *  Values are then injected into the fields and.
     *  Finally a method is invoked.
     * 
     * @param java the value of field 'java'.
     */
    public void setJava(
            final com.orient.jpdl.model.Java java) {
        this._java = java;
        this._choiceValue = java;
    }

    /**
     * Sets the value of field 'jms'. The field 'jms' has the
     * following description: Sends an email.
     * 
     * @param jms the value of field 'jms'.
     */
    public void setJms(
            final com.orient.jpdl.model.Jms jms) {
        this._jms = jms;
        this._choiceValue = jms;
    }

    /**
     * Sets the value of field 'join'. The field 'join' has the
     * following description: Spawns multiple concurrent paths of
     *  execution.
     * 
     * @param join the value of field 'join'.
     */
    public void setJoin(
            final com.orient.jpdl.model.Join join) {
        this._join = join;
        this._choiceValue = join;
    }

    /**
     * Sets the value of field 'mail'. The field 'mail' has the
     * following description: Sends an email.
     * 
     * @param mail the value of field 'mail'.
     */
    public void setMail(
            final com.orient.jpdl.model.Mail mail) {
        this._mail = mail;
        this._choiceValue = mail;
    }

    /**
     * Sets the value of field 'rules'. The field 'rules' has the
     * following description: Evaluates rules after feeding in some
     * facts in a stateful
     *  knowledge session.
     * 
     * @param rules the value of field 'rules'.
     */
    public void setRules(
            final com.orient.jpdl.model.Rules rules) {
        this._rules = rules;
        this._choiceValue = rules;
    }

    /**
     * Sets the value of field 'rulesDecision'. The field
     * 'rulesDecision' has the following description: Selects one
     * outgoing transition based on evaluation
     *  of rules.
     * 
     * @param rulesDecision the value of field 'rulesDecision'.
     */
    public void setRulesDecision(
            final com.orient.jpdl.model.RulesDecision rulesDecision) {
        this._rulesDecision = rulesDecision;
        this._choiceValue = rulesDecision;
    }

    /**
     * Sets the value of field 'script'. The field 'script' has the
     * following description: Evaluates a piece of text as a
     * script.
     * 
     * @param script the value of field 'script'.
     */
    public void setScript(
            final com.orient.jpdl.model.Script script) {
        this._script = script;
        this._choiceValue = script;
    }

    /**
     * Sets the value of field 'simulation'. The field 'simulation'
     * has the following description: create a simulation activity.
     * 
     * @param simulation the value of field 'simulation'.
     */
    public void setSimulation(
            final com.orient.jpdl.model.Simulation simulation) {
        this._simulation = simulation;
        this._choiceValue = simulation;
    }

    /**
     * Sets the value of field 'sql'. The field 'sql' has the
     * following description: Performs a Hibernate SQL query.
     * 
     * @param sql the value of field 'sql'.
     */
    public void setSql(
            final com.orient.jpdl.model.Sql sql) {
        this._sql = sql;
        this._choiceValue = sql;
    }

    /**
     * Sets the value of field 'start'. The field 'start' has the
     * following description: Start event.
     * 
     * @param start the value of field 'start'.
     */
    public void setStart(
            final com.orient.jpdl.model.Start start) {
        this._start = start;
        this._choiceValue = start;
    }

    /**
     * Sets the value of field 'state'. The field 'state' has the
     * following description: Wait state. When an execution arrives
     * in this activity,
     *  the execution stops until an external trigger is delivered
     * through
     *  execution.signal() or
     * execution.getActivityInstance().signal()
     * 
     * @param state the value of field 'state'.
     */
    public void setState(
            final com.orient.jpdl.model.State state) {
        this._state = state;
        this._choiceValue = state;
    }

    /**
     * Sets the value of field 'subProcess'. The field 'subProcess'
     * has the following description: Waits while a sub process
     * instance is being executed
     *  and continues when the sub process instance ends.
     * 
     * @param subProcess the value of field 'subProcess'.
     */
    public void setSubProcess(
            final com.orient.jpdl.model.SubProcess subProcess) {
        this._subProcess = subProcess;
        this._choiceValue = subProcess;
    }

    /**
     * Sets the value of field 'task'. The field 'task' has the
     * following description: Creates a task in the task component.
     * 
     * @param task the value of field 'task'.
     */
    public void setTask(
            final com.orient.jpdl.model.Task task) {
        this._task = task;
        this._choiceValue = task;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled com.orient.jpdl.model.ActivityGroup
     */
    public static com.orient.jpdl.model.ActivityGroup unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.orient.jpdl.model.ActivityGroup) org.exolab.castor.xml.Unmarshaller.unmarshal(com.orient.jpdl.model.ActivityGroup.class, reader);
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
