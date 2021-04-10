package com.orient.mongorequest.model;

import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author GNY
 * @create 2018-06-01 13:57
 */
public class FileVO implements Serializable {

    private Long id;
    private String name;
    private String suffix;

    public FileVO() {
    }

    public FileVO(Long id, String suffix, String name) {
        this.id = id;
        this.suffix = suffix;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

}
