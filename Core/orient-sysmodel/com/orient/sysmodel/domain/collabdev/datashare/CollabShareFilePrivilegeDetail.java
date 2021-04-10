package com.orient.sysmodel.domain.collabdev.datashare;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @Description
 * @Author GNY
 * @Date 2018/7/27 10:12
 * @Version 1.0
 **/
@Entity
@Table(name = "CB_SHARE_FILE_PRIV_DETAIL")
public class CollabShareFilePrivilegeDetail {

    private String id;
    private String cbShareFilePrivilegeDGId;
    private String filterName;
    private String filterOperation;
    private String filterValue;
    private String filterConnection;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CB_SHARE_FILE_PRIV_DETAIL")})
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "CB_SHARE_FILE_PRIVILEGE_DG_ID")
    public String getCbShareFilePrivilegeDGId() {
        return cbShareFilePrivilegeDGId;
    }

    public void setCbShareFilePrivilegeDGId(String cbShareFilePrivilegeDGId) {
        this.cbShareFilePrivilegeDGId = cbShareFilePrivilegeDGId;
    }

    @Basic
    @Column(name = "FILTER_NAME")
    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    @Basic
    @Column(name = "FILTER_OPERATION")
    public String getFilterOperation() {
        return filterOperation;
    }

    public void setFilterOperation(String filterOperation) {
        this.filterOperation = filterOperation;
    }

    @Basic
    @Column(name = "FILTER_VALUE")
    public String getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }

    @Basic
    @Column(name = "FILTER_CONNECTION")
    public String getFilterConnection() {
        return filterConnection;
    }

    public void setFilterConnection(String filterConnection) {
        this.filterConnection = filterConnection;
    }

}
