/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.orient.jpdl.model.types;

/**
 * Enumeration BooleanValueType.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public enum BooleanValueType implements java.io.Serializable {


      //------------------/
     //- Enum Constants -/
    //------------------/

    /**
     * Constant TRUE
     */
    TRUE("true"),
    /**
     * Constant ON
     */
    ON("on"),
    /**
     * Constant ENABLED
     */
    ENABLED("enabled"),
    /**
     * Constant FALSE
     */
    FALSE("false"),
    /**
     * Constant OFF
     */
    OFF("off"),
    /**
     * Constant DISABLED
     */
    DISABLED("disabled");

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

    private BooleanValueType(final java.lang.String value) {
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
    public static com.orient.jpdl.model.types.BooleanValueType fromValue(
            final java.lang.String value) {
        for (BooleanValueType c: BooleanValueType.values()) {
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
