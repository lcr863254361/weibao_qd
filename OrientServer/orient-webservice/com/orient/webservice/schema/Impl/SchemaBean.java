package com.orient.webservice.schema.Impl;

import com.orient.edm.init.IContextLoadRun;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.metamodel.metaengine.impl.MetaUtilImpl;
import com.orient.sysmodel.service.file.FileService;
import com.orient.sysmodel.service.sys.SeqGeneratorService;
import com.orient.sysmodel.service.tbom.TbomService;

public class SchemaBean {

    protected MetaUtilImpl metaEngine = null;
    protected MetaDAOFactory metadaofactory = null;
    protected FileService fileService = null;
    protected TbomService tbomService = null;
    protected SeqGeneratorService seqGeneratorService = null;

    public IContextLoadRun getMetaEngine() {
        return metaEngine;
    }

    public void setMetaEngine(MetaUtilImpl metaEngine) {
        this.metaEngine = metaEngine;
    }

    public MetaDAOFactory getMetadaofactory() {
        return metadaofactory;
    }

    public void setMetadaofactory(MetaDAOFactory metadaofactory) {
        this.metadaofactory = metadaofactory;
    }

    public FileService getFileService() {
        return fileService;
    }

    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    public TbomService getTbomService() {
        return tbomService;
    }

    public void setTbomService(TbomService tbomService) {
        this.tbomService = tbomService;
    }

    public SeqGeneratorService getSeqGeneratorService() {
        return seqGeneratorService;
    }

    public void setSeqGeneratorService(SeqGeneratorService seqGeneratorService) {
        this.seqGeneratorService = seqGeneratorService;
    }


}
