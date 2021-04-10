/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * jBPM Process Definition Language definition.
 *  This is the top level element in a process definition file.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class Process implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The process name. Multiple processes can be deployed with
     * the same
     *  name, as long as they have a different version.
     */
    private java.lang.String _name;

    /**
     * The process category(main or sub) .
     */
    private java.lang.String _category;

    /**
     * The key can be used to provide a short acronym that replaces
     * the name
     *  as the basis for the generated process definition id.
     */
    private java.lang.String _key;

    /**
     * Indicates the version number of this process definition
     * among its
     *  homonyms. By specifying a version, automatic deployment can
     * tell whether this
     *  process is already deployed.
     */
    private int _version;

    /**
     * keeps track of state for field: _version
     */
    private boolean _has_version;

    /**
     * Field _description.
     */
    private java.lang.String _description;

    /**
     * Field _processChoice.
     */
    private com.orient.jpdl.model.ProcessChoice _processChoice;

    /**
     * Information to migrate instances of previously deployed
     *  process definitions to the new version.
     */
    private com.orient.jpdl.model.MigrateInstances _migrateInstances;


      //----------------/
     //- Constructors -/
    //----------------/

    public Process() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     */
    public void deleteVersion(
    ) {
        this._has_version= false;
    }

    /**
     * Returns the value of field 'category'. The field 'category'
     * has the following description: The process category(main or
     * sub) .
     * 
     * @return the value of field 'Category'.
     */
    public java.lang.String getCategory(
    ) {
        return this._category;
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
     * Returns the value of field 'key'. The field 'key' has the
     * following description: The key can be used to provide a
     * short acronym that replaces the name
     *  as the basis for the generated process definition id.
     * 
     * @return the value of field 'Key'.
     */
    public java.lang.String getKey(
    ) {
        return this._key;
    }

    /**
     * Returns the value of field 'migrateInstances'. The field
     * 'migrateInstances' has the following description:
     * Information to migrate instances of previously deployed
     *  process definitions to the new version.
     * 
     * @return the value of field 'MigrateInstances'.
     */
    public com.orient.jpdl.model.MigrateInstances getMigrateInstances(
    ) {
        return this._migrateInstances;
    }

    /**
     * Returns the value of field 'name'. The field 'name' has the
     * following description: The process name. Multiple processes
     * can be deployed with the same
     *  name, as long as they have a different version.
     * 
     * @return the value of field 'Name'.
     */
    public java.lang.String getName(
    ) {
        return this._name;
    }

    /**
     * Returns the value of field 'processChoice'.
     * 
     * @return the value of field 'ProcessChoice'.
     */
    public com.orient.jpdl.model.ProcessChoice getProcessChoice(
    ) {
        return this._processChoice;
    }

    /**
     * Returns the value of field 'version'. The field 'version'
     * has the following description: Indicates the version number
     * of this process definition among its
     *  homonyms. By specifying a version, automatic deployment can
     * tell whether this
     *  process is already deployed.
     * 
     * @return the value of field 'Version'.
     */
    public int getVersion(
    ) {
        return this._version;
    }

    /**
     * Method hasVersion.
     * 
     * @return true if at least one Version has been added
     */
    public boolean hasVersion(
    ) {
        return this._has_version;
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
     * Sets the value of field 'category'. The field 'category' has
     * the following description: The process category(main or sub)
     * .
     * 
     * @param category the value of field 'category'.
     */
    public void setCategory(
            final java.lang.String category) {
        this._category = category;
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
     * Sets the value of field 'key'. The field 'key' has the
     * following description: The key can be used to provide a
     * short acronym that replaces the name
     *  as the basis for the generated process definition id.
     * 
     * @param key the value of field 'key'.
     */
    public void setKey(
            final java.lang.String key) {
        this._key = key;
    }

    /**
     * Sets the value of field 'migrateInstances'. The field
     * 'migrateInstances' has the following description:
     * Information to migrate instances of previously deployed
     *  process definitions to the new version.
     * 
     * @param migrateInstances the value of field 'migrateInstances'
     */
    public void setMigrateInstances(
            final com.orient.jpdl.model.MigrateInstances migrateInstances) {
        this._migrateInstances = migrateInstances;
    }

    /**
     * Sets the value of field 'name'. The field 'name' has the
     * following description: The process name. Multiple processes
     * can be deployed with the same
     *  name, as long as they have a different version.
     * 
     * @param name the value of field 'name'.
     */
    public void setName(
            final java.lang.String name) {
        this._name = name;
    }

    /**
     * Sets the value of field 'processChoice'.
     * 
     * @param processChoice the value of field 'processChoice'.
     */
    public void setProcessChoice(
            final com.orient.jpdl.model.ProcessChoice processChoice) {
        this._processChoice = processChoice;
    }

    /**
     * Sets the value of field 'version'. The field 'version' has
     * the following description: Indicates the version number of
     * this process definition among its
     *  homonyms. By specifying a version, automatic deployment can
     * tell whether this
     *  process is already deployed.
     * 
     * @param version the value of field 'version'.
     */
    public void setVersion(
            final int version) {
        this._version = version;
        this._has_version = true;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled com.orient.jpdl.model.Process
     */
    public static com.orient.jpdl.model.Process unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.orient.jpdl.model.Process) org.exolab.castor.xml.Unmarshaller.unmarshal(com.orient.jpdl.model.Process.class, reader);
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
