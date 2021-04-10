package com.orient.sysmodel.domain.quantity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2017-07-11 9:24
 */
@Entity
@Table(name = "CF_QUANTITY_TEMPLATE")
public class CfQuantityTemplateDO {
    private Long id;
    private String name;

    private List<CfQuantityTemplateRelationDO> templateRelations;

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CF_QUANTITY_TEMPLATE")})
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "belongTemplate", cascade = CascadeType.REMOVE)
    @org.hibernate.annotations.OrderBy(clause = "to_number(ID) asc")
    public List<CfQuantityTemplateRelationDO> getTemplateRelations() {
        return templateRelations;
    }

    public void setTemplateRelations(List<CfQuantityTemplateRelationDO> templateRelations) {
        this.templateRelations = templateRelations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CfQuantityTemplateDO that = (CfQuantityTemplateDO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
