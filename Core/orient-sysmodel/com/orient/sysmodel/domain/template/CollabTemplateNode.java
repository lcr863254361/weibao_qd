package com.orient.sysmodel.domain.template;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.IndexColumn;

import javax.persistence.*;
import java.io.*;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

/**
 * a model contains original data of template node
 *
 * @author Seraph
 *         2016-09-13 上午10:02
 */
@Entity
@Table(name = "COLLAB_TEMPLATE_NODE")
public class CollabTemplateNode<T extends Serializable> {

    public static final String ROOT = "root";
    public static final String TEMPLATE_ID = "templateId";
    public static final String PARENT_ID = "";

    public CollabTemplateNode(){

    }

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_COLLAB_TEMPLATE_NODE")})
    private Long id;

    @Basic
    @Column(name = "TYPE")
    private String type;

    @Basic
    @Column(name = "ROOT")
    private boolean root;

    @JsonIgnore
    @Lob
    @Column( name = "SERIAL_DATA")
    private byte[] serialData;

    @Basic
    @Column(name = "OLD_DATA_ID")
    private String oldDataId;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private CollabTemplateNode parent;

    @JsonIgnore
    @OneToMany(mappedBy = "parent", cascade = {CascadeType.ALL})
    private List<CollabTemplateNode> children = new ArrayList<>();

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "INDEP_COMP_REF_ID")
    private CollabTemplateNode independentCompRef;

    @JsonIgnore
    @OneToMany(mappedBy = "independentCompRef", cascade = {CascadeType.ALL})
    private List<CollabTemplateNode> independentComponents = new ArrayList<>();

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "REL_COMP_REF_ID")
    private CollabTemplateNode relationCompRef;

    @JsonIgnore
    @OneToMany(mappedBy = "relationCompRef", cascade = {CascadeType.ALL})
    private List<CollabTemplateNode> relationComponents = new ArrayList<>();

    @Basic
    @Column(name = "TEMPLATE_ID")
    private String templateId;

    @Transient
    private T data;

    @Transient
    private String newDataId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOldDataId() {
        return oldDataId;
    }

    public void setOldDataId(String oldDataId) {
        this.oldDataId = oldDataId;
    }


    public CollabTemplateNode getParent() {
        return parent;
    }

    public void setParent(CollabTemplateNode parent) {
        this.parent = parent;
    }

    public List<CollabTemplateNode> getChildren() {
        return children;
    }

    public void setChildren(List<CollabTemplateNode> children) {
        this.children = children;
    }

    public List<CollabTemplateNode> getIndependentComponents() {
        return independentComponents;
    }

    public void setIndependentComponents(List<CollabTemplateNode> independentComponents) {
        this.independentComponents = independentComponents;
    }

    public List<CollabTemplateNode> getRelationComponents() {
        return relationComponents;
    }

    public void setRelationComponents(List<CollabTemplateNode> relationComponents) {
        this.relationComponents = relationComponents;
    }

    public boolean isRoot() {
        return root;
    }

    public void setRoot(boolean root) {
        this.root = root;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getNewDataId() {
        return newDataId;
    }

    public void setNewDataId(String newDataId) {
        this.newDataId = newDataId;
    }

    public byte[] getSerialData() {
        return serialData;
    }

    public void setSerialData(byte[] serialData) {
        this.serialData = serialData;
    }

    public CollabTemplateNode getIndependentCompRef() {
        return independentCompRef;
    }

    public void setIndependentCompRef(CollabTemplateNode independentCompRef) {
        this.independentCompRef = independentCompRef;
    }

    public CollabTemplateNode getRelationCompRef() {
        return relationCompRef;
    }

    public void setRelationCompRef(CollabTemplateNode relationCompRef) {
        this.relationCompRef = relationCompRef;
    }

    public void convertDataToSerialBytes(){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutput out = new ObjectOutputStream(bos)){
            out.writeObject(this.data);
            out.flush();
            this.serialData = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void convertSerialBytesToData(){
        if(this.getSerialData() == null){
            return;
        }

        ByteArrayInputStream bis = new ByteArrayInputStream(this.getSerialData());
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(bis);
            this.data= (T) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }
}
