package com.orient.sysmodel.domain.form;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by enjoy on 2016/3/14 0014.
 */
@Entity
@Table(name = "MODEL_BTN_TYPE")
public class ModelBtnTypeEntity {
    private Long id;
    private String name;
    private Long issystem;
    private String code;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence",strategy = "sequence",parameters = {@Parameter(name="sequence",value="SEQ_MODEL_BTN_TYPE")})
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "NAME", nullable = true, insertable = true, updatable = true, length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "ISSYSTEM", nullable = true, insertable = true, updatable = true, precision = -127)
    public Long getIssystem() {
        return issystem;
    }

    public void setIssystem(Long issystem) {
        this.issystem = issystem;
    }

    @Basic
    @Column(name = "CODE", nullable = true, insertable = true, updatable = true, length = 100)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ModelBtnTypeEntity that = (ModelBtnTypeEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (issystem != null ? !issystem.equals(that.issystem) : that.issystem != null) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (issystem != null ? issystem.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        return result;
    }
}
