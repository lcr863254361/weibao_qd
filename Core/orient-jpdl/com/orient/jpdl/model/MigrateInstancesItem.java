/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * Class MigrateInstancesItem.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class MigrateInstancesItem implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Internal choice value storage
     */
    private java.lang.Object _choiceValue;

    /**
     * The migration handler specifies the name of a class to be
     * executed
     *  while migrating the process instance.
     */
    private com.orient.jpdl.model.MigrationHandler _migrationHandler;

    /**
     * One activity mapping will be present for each activity of
     * which the
     *  name changed.
     */
    private com.orient.jpdl.model.ActivityMapping _activityMapping;


      //----------------/
     //- Constructors -/
    //----------------/

    public MigrateInstancesItem() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'activityMapping'. The field
     * 'activityMapping' has the following description: One
     * activity mapping will be present for each activity of which
     * the
     *  name changed.
     * 
     * @return the value of field 'ActivityMapping'.
     */
    public com.orient.jpdl.model.ActivityMapping getActivityMapping(
    ) {
        return this._activityMapping;
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
     * Returns the value of field 'migrationHandler'. The field
     * 'migrationHandler' has the following description: The
     * migration handler specifies the name of a class to be
     * executed
     *  while migrating the process instance.
     * 
     * @return the value of field 'MigrationHandler'.
     */
    public com.orient.jpdl.model.MigrationHandler getMigrationHandler(
    ) {
        return this._migrationHandler;
    }

    /**
     * Sets the value of field 'activityMapping'. The field
     * 'activityMapping' has the following description: One
     * activity mapping will be present for each activity of which
     * the
     *  name changed.
     * 
     * @param activityMapping the value of field 'activityMapping'.
     */
    public void setActivityMapping(
            final com.orient.jpdl.model.ActivityMapping activityMapping) {
        this._activityMapping = activityMapping;
        this._choiceValue = activityMapping;
    }

    /**
     * Sets the value of field 'migrationHandler'. The field
     * 'migrationHandler' has the following description: The
     * migration handler specifies the name of a class to be
     * executed
     *  while migrating the process instance.
     * 
     * @param migrationHandler the value of field 'migrationHandler'
     */
    public void setMigrationHandler(
            final com.orient.jpdl.model.MigrationHandler migrationHandler) {
        this._migrationHandler = migrationHandler;
        this._choiceValue = migrationHandler;
    }

}
