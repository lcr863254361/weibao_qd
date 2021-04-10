package com.orient.sysmodel.domain.form;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

/**
 * Created by enjoy on 2016/3/15 0015.
 */
@Entity
@Table(name = "FREEMARK_TEMPLATE")
public class FreemarkTemplateEntity {
    private Long id;
    private String name;
    private String alias;
    private String type;
    private String macroAlias;
    private String html;
    private Long canedit;
    private String desc;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence",strategy = "sequence",parameters = {@Parameter(name="sequence",value="SEQ_FREEMARK_TEMPLATE")})
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "NAME", nullable = true, insertable = true, updatable = true, length = 200)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "ALIAS", nullable = true, insertable = true, updatable = true, length = 200)
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Basic
    @Column(name = "TYPE", nullable = true, insertable = true, updatable = true, length = 200)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "MACRO_ALIAS", nullable = true, insertable = true, updatable = true, length = 200)
    public String getMacroAlias() {
        return macroAlias;
    }

    public void setMacroAlias(String macroAlias) {
        this.macroAlias = macroAlias;
    }

    @Basic
    @Column(name = "HTML", nullable = true, insertable = true, updatable = true)
    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    @Basic
    @Column(name = "CANEDIT", nullable = true, insertable = true, updatable = true, precision = -127)
    public Long getCanedit() {
        return canedit;
    }

    public void setCanedit(Long canedit) {
        this.canedit = canedit;
    }

    @Basic
    @Column(name = "DESCR", nullable = true, insertable = true, updatable = true, length = 400)
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FreemarkTemplateEntity that = (FreemarkTemplateEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (alias != null ? !alias.equals(that.alias) : that.alias != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (macroAlias != null ? !macroAlias.equals(that.macroAlias) : that.macroAlias != null) return false;
        if (html != null ? !html.equals(that.html) : that.html != null) return false;
        if (canedit != null ? !canedit.equals(that.canedit) : that.canedit != null) return false;
        if (desc != null ? !desc.equals(that.desc) : that.desc != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (alias != null ? alias.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (macroAlias != null ? macroAlias.hashCode() : 0);
        result = 31 * result + (html != null ? html.hashCode() : 0);
        result = 31 * result + (canedit != null ? canedit.hashCode() : 0);
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        return result;
    }
}
