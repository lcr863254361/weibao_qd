package com.orient.sysmodel.domain.file;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

/**
 * Created by mengbin on 16/7/15.
 * Purpose:
 * Detail:
 */
@Entity
@Table(name = "CWM_ODS_FILE")
public class OdsFile {
    private long id;
    private String cwmFileId;
    private String atfxfilefullname;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@Parameter(name = "sequence", value = "SEQ_CWM_HADOOP_FILE")})
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "CWM_FILE_ID", nullable = false, precision = 0)
    public String getCwmFileId() {
        return cwmFileId;
    }

    public void setCwmFileId(String cwmFileId) {
        this.cwmFileId = cwmFileId;
    }

    @Basic
    @Column(name = "ATFXFILEFULLNAME", nullable = true, length = 4000)
    public String getAtfxfilefullname() {
        return atfxfilefullname;
    }

    public void setAtfxfilefullname(String atfxfilefullname) {
        this.atfxfilefullname = atfxfilefullname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OdsFile odsFile = (OdsFile) o;

        if (id != odsFile.id) return false;
        if (cwmFileId != null ? !cwmFileId.equals(odsFile.cwmFileId) : odsFile.cwmFileId != null) return false;
        if (atfxfilefullname != null ? !atfxfilefullname.equals(odsFile.atfxfilefullname) : odsFile.atfxfilefullname != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (cwmFileId != null ? cwmFileId.hashCode() : 0);
        result = 31 * result + (atfxfilefullname != null ? atfxfilefullname.hashCode() : 0);
        return result;
    }
}
