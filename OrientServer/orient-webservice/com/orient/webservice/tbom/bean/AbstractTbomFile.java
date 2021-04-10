/*
 * Title: AbstractTbomFile.java
 * Company: DHC
 * Author: 
 * Date: Nov 5, 2009 9:56:59 AM
 * Version: 4.0
 */
package com.orient.webservice.tbom.bean;


/**
 * The Class AbstractTbomFile.
 *
 * @author xuxj
 * @version 创建时间：2009-3-25 下午01:15:46 类说明
 */
public class AbstractTbomFile {

    /**
     * The id.
     */
    private String id;

    /**
     * The name.
     */
    private String name;

    /**
     * The description.
     */
    private String description;

    /**
     * The type.
     */
    private String type;

    /**
     * The date.
     */
    private String date;

    /**
     * The size.
     */
    private String size;

    /**
     * Instantiates a new abstract tbom file.
     *
     * @param id          the id
     * @param name        the name
     * @param description the description
     * @param type        the type
     * @param date        the date
     * @param size        the size
     */
    public AbstractTbomFile(String id, String name, String description, String type, String date, String size) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.date = date;
        this.size = size;
    }

    /**
     * Instantiates a new abstract tbom file.
     */
    public AbstractTbomFile() {
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type.
     *
     * @param type the new type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the date.
     *
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date.
     *
     * @param date the new date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Sets the size.
     *
     * @param size the new size
     */
    public void setSize(String size) {
        this.size = size;
    }

    /**
     * Gets the size.
     *
     * @return the size
     */
    public String getSize() {
        return size;
    }
}
