/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model.types;

/**
 * Enumeration JoinLockmodeType.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public enum JoinLockmodeType implements java.io.Serializable {


      //------------------/
     //- Enum Constants -/
    //------------------/

    /**
     * Constant NONE
     */
    NONE("none"),
    /**
     * Constant READ
     */
    READ("read"),
    /**
     * Constant UPGRADE
     */
    UPGRADE("upgrade"),
    /**
     * Constant UPGRADE_NOWAIT
     */
    UPGRADE_NOWAIT("upgrade_nowait"),
    /**
     * Constant WRITE
     */
    WRITE("write");

      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field value.
     */
    private final java.lang.String value;


      //----------------/
     //- Constructors -/
    //----------------/

    private JoinLockmodeType(final java.lang.String value) {
        this.value = value;
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method fromValue.
     * 
     * @param value
     * @return the constant for this value
     */
    public static com.orient.jpdl.model.types.JoinLockmodeType fromValue(
            final java.lang.String value) {
        for (JoinLockmodeType c: JoinLockmodeType.values()) {
            if (c.value.equals(value)) {
                return c;
            }
        }
        throw new IllegalArgumentException(value);
    }

    /**
     * 
     * 
     * @param value
     */
    public void setValue(
            final java.lang.String value) {
    }

    /**
     * Method toString.
     * 
     * @return the value of this constant
     */
    public java.lang.String toString(
    ) {
        return this.value;
    }

    /**
     * Method value.
     * 
     * @return the value of this constant
     */
    public java.lang.String value(
    ) {
        return this.value;
    }

}
