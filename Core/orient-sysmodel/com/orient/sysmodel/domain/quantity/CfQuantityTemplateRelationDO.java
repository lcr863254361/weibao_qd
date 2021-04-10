package com.orient.sysmodel.domain.quantity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2017-07-11 9:24
 */
@Entity
@Table(name = "CF_QUANTITY_TEMPLATE_RELATION")
public class CfQuantityTemplateRelationDO {
    private Long id;
    private CfQuantityTemplateDO belongTemplate;
    private CfQuantityDO belongQuantity;

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CF_QUANTITY_TR")})
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEMPLATE_ID")
    public CfQuantityTemplateDO getBelongTemplate() {
        return belongTemplate;
    }

    public void setBelongTemplate(CfQuantityTemplateDO belongTemplate) {
        this.belongTemplate = belongTemplate;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QUANTITY_ID")
    public CfQuantityDO getBelongQuantity() {
        return belongQuantity;
    }

    public void setBelongQuantity(CfQuantityDO belongQuantity) {
        this.belongQuantity = belongQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CfQuantityTemplateRelationDO that = (CfQuantityTemplateRelationDO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        return result;
    }
}
