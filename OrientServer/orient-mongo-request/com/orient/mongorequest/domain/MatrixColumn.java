package com.orient.mongorequest.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author GNY
 * @create 2018-05-31 15:33
 */
@Entity
@Table(name = "MONGO_MATRIX_COLUMN")
public class MatrixColumn implements Serializable {

    private Long id;
    private String columnName;
    private Integer columnOrder;
    private MatrixFileDesc belongMatrixFileDesc;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_MONGO_MATRIX_COLUMN")})
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "COLUMN_NAME")
    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    @Basic
    @Column(name = "COLUMN_ORDER")
    public Integer getColumnOrder() {
        return columnOrder;
    }

    public void setColumnOrder(Integer columnOrder) {
        this.columnOrder = columnOrder;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="MATRIX_FILE_DESC_ID")
    public MatrixFileDesc getBelongMatrixFileDesc() {
        return belongMatrixFileDesc;
    }

    public void setBelongMatrixFileDesc(MatrixFileDesc belongMatrixFileDesc) {
        this.belongMatrixFileDesc = belongMatrixFileDesc;
    }

}
