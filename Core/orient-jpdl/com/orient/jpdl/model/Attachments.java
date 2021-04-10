/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model;

/**
 * Class Attachments.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class Attachments implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _attachmentList.
     */
    private java.util.List<com.orient.jpdl.model.Attachment> _attachmentList;


      //----------------/
     //- Constructors -/
    //----------------/

    public Attachments() {
        super();
        this._attachmentList = new java.util.ArrayList<com.orient.jpdl.model.Attachment>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vAttachment
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addAttachment(
            final com.orient.jpdl.model.Attachment vAttachment)
    throws java.lang.IndexOutOfBoundsException {
        this._attachmentList.add(vAttachment);
    }

    /**
     * 
     * 
     * @param index
     * @param vAttachment
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addAttachment(
            final int index,
            final com.orient.jpdl.model.Attachment vAttachment)
    throws java.lang.IndexOutOfBoundsException {
        this._attachmentList.add(index, vAttachment);
    }

    /**
     * Method enumerateAttachment.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<? extends com.orient.jpdl.model.Attachment> enumerateAttachment(
    ) {
        return java.util.Collections.enumeration(this._attachmentList);
    }

    /**
     * Method getAttachment.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the com.orient.jpdl.model.Attachment at
     * the given index
     */
    public com.orient.jpdl.model.Attachment getAttachment(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._attachmentList.size()) {
            throw new IndexOutOfBoundsException("getAttachment: Index value '" + index + "' not in range [0.." + (this._attachmentList.size() - 1) + "]");
        }

        return (com.orient.jpdl.model.Attachment) _attachmentList.get(index);
    }

    /**
     * Method getAttachment.Returns the contents of the collection
     * in an Array.  <p>Note:  Just in case the collection contents
     * are changing in another thread, we pass a 0-length Array of
     * the correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public com.orient.jpdl.model.Attachment[] getAttachment(
    ) {
        com.orient.jpdl.model.Attachment[] array = new com.orient.jpdl.model.Attachment[0];
        return (com.orient.jpdl.model.Attachment[]) this._attachmentList.toArray(array);
    }

    /**
     * Method getAttachmentCount.
     * 
     * @return the size of this collection
     */
    public int getAttachmentCount(
    ) {
        return this._attachmentList.size();
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
     * Method iterateAttachment.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<? extends com.orient.jpdl.model.Attachment> iterateAttachment(
    ) {
        return this._attachmentList.iterator();
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
     */
    public void removeAllAttachment(
    ) {
        this._attachmentList.clear();
    }

    /**
     * Method removeAttachment.
     * 
     * @param vAttachment
     * @return true if the object was removed from the collection.
     */
    public boolean removeAttachment(
            final com.orient.jpdl.model.Attachment vAttachment) {
        boolean removed = _attachmentList.remove(vAttachment);
        return removed;
    }

    /**
     * Method removeAttachmentAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public com.orient.jpdl.model.Attachment removeAttachmentAt(
            final int index) {
        java.lang.Object obj = this._attachmentList.remove(index);
        return (com.orient.jpdl.model.Attachment) obj;
    }

    /**
     * 
     * 
     * @param index
     * @param vAttachment
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setAttachment(
            final int index,
            final com.orient.jpdl.model.Attachment vAttachment)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._attachmentList.size()) {
            throw new IndexOutOfBoundsException("setAttachment: Index value '" + index + "' not in range [0.." + (this._attachmentList.size() - 1) + "]");
        }

        this._attachmentList.set(index, vAttachment);
    }

    /**
     * 
     * 
     * @param vAttachmentArray
     */
    public void setAttachment(
            final com.orient.jpdl.model.Attachment[] vAttachmentArray) {
        //-- copy array
        _attachmentList.clear();

        for (int i = 0; i < vAttachmentArray.length; i++) {
                this._attachmentList.add(vAttachmentArray[i]);
        }
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled com.orient.jpdl.model.Attachments
     */
    public static com.orient.jpdl.model.Attachments unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.orient.jpdl.model.Attachments) org.exolab.castor.xml.Unmarshaller.unmarshal(com.orient.jpdl.model.Attachments.class, reader);
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
