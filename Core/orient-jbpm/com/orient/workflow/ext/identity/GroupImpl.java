package com.orient.workflow.ext.identity;

import java.io.Serializable;
import java.math.BigDecimal;

import org.jbpm.api.identity.Group;

public class GroupImpl implements Serializable, Group {

  private static final long serialVersionUID = 1L;
  private BigDecimal db_id;
  private String name;
  private String memo;
  private String type;
  private String status;

  // Constructors

  /** default constructor */
  public GroupImpl() {
  }

  /** minimal constructor */
  public GroupImpl(BigDecimal id, String name) {
    this.db_id = id;
    this.name = name;
  }

  /** full constructor */
  public GroupImpl(BigDecimal id, String name, String memo, String type, String status) {
    this.db_id = id;
    this.name = name;
    this.memo = memo;
    this.type = type;
    this.status = status;
  }

  // Property accessors


  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMemo() {
    return this.memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }


  public String getId() {
    return this.name;
  }

  
  public BigDecimal getDb_id() {
    return db_id;
  }

  
  public void setDb_id(BigDecimal dbId) {
    db_id = dbId;
  }
  
}
