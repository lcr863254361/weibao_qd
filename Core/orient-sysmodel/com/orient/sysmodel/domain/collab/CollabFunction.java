package com.orient.sysmodel.domain.collab;

import com.orient.utils.CommonTools;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Collab Function
 *
 * @author Seraph
 * 2016-07-08 上午10:47
 */
@Entity
@Table(name = "COLLAB_FUNCTION")
public class CollabFunction implements Comparable<CollabFunction>, Serializable {

    public static final String BELONGED_MODEL = "belongedModel";
    private String id;
    private String name;
    private String commentInfo;
    private String displayOrder;
    private String iconCls;
    private String stage;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_COLLAB_FUNCTION")})
    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    @Basic
    @Column(name = "COMMENT_INFO")
    public String getCommentInfo() {
        return commentInfo;
    }

    public void setCommentInfo(String commentInfo) {
        this.commentInfo = commentInfo;
    }

    @Basic
    @Column(name = "DISPLAY_ORDER")
    public String getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(String displayOrder) {
        this.displayOrder = displayOrder;
    }

    @Basic
    @Column(name = "ICON_CLS")
    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    @Basic
    @Column(name = "STAGE")
    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof CollabFunction)) {
            return false;
        }

        CollabFunction function = (CollabFunction) obj;
        if (!this.id.equals(function.id)) {
            return false;
        }

        if (!(this.name == null ? function.name == null : this.name.equals(function.name))) {
            return false;
        }

        return true;
    }

   /* @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31).append(this.id).append(belongedModel).append(name).toHashCode();
    }*/

    @Override
    public int compareTo(CollabFunction o) {
        if (!CommonTools.isNumber(this.getDisplayOrder()) || !CommonTools.isNumber(o.getDisplayOrder())) {
            return 0;
        }
        return Integer.valueOf(this.getDisplayOrder()) - Integer.valueOf(o.getDisplayOrder());
    }

}
