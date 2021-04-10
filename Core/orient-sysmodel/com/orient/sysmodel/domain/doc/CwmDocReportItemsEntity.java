package com.orient.sysmodel.domain.doc;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * ${DESCRIPTION}
 *
 * @author enjoyjava
 * @create 2016-12-01 8:30 AM
 */
@Entity
@Table(name = "CWM_DOC_REPORT_ITEMS")
public class CwmDocReportItemsEntity {
    private Long id;
    private String bookmarkName;
    private Long docHandlerId;
    private CwmDocReportsEntity belongReport;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CWM_DOC_REPORT_ITEMS")})
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BELONG_REPORT")
    public CwmDocReportsEntity getBelongReport() {
        return belongReport;
    }

    public void setBelongReport(CwmDocReportsEntity belongReport) {
        this.belongReport = belongReport;
    }

    @Basic
    @Column(name = "BOOKMARK_NAME")
    public String getBookmarkName() {
        return bookmarkName;
    }

    public void setBookmarkName(String bookmarkName) {
        this.bookmarkName = bookmarkName;
    }


    @Basic
    @Column(name = "DOC_HANDLER_ID")
    public Long getDocHandlerId() {
        return docHandlerId;
    }

    public void setDocHandlerId(Long docHandlerId) {
        this.docHandlerId = docHandlerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CwmDocReportItemsEntity that = (CwmDocReportItemsEntity) o;

        if (id != that.id) return false;
        if (belongReport != that.belongReport) return false;
        if (docHandlerId != that.docHandlerId) return false;
        if (bookmarkName != null ? !bookmarkName.equals(that.bookmarkName) : that.bookmarkName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (bookmarkName != null ? bookmarkName.hashCode() : 0);
        result = 31 * result + (int) (docHandlerId ^ (docHandlerId >>> 32));
        return result;
    }
}
