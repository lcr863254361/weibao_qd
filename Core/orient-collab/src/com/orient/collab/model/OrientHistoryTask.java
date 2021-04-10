package com.orient.collab.model;

/**
 * HistoryTask
 *
 * @author Seraph
 *         2016-08-24 下午2:56
 */
public class OrientHistoryTask {

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getActualStartDate() {
        return actualStartDate;
    }

    public String getActualEndDate() {
        return actualEndDate;
    }

    public String getPiId() {
        return piId;
    }

    public String getFlowTaskId() {
        return flowTaskId;
    }

    public String getModelName() {
        return modelName;
    }

    public String getDataId() {
        return dataId;
    }

    public String getDescription() {
        return description;
    }

    public String getAuditType() {
        return auditType;
    }

    public String getModelId() {
        return modelId;
    }

    public String getGroup() {
        return group;
    }

    public String getId() {return id;}

    public static class Builder {
        public Builder(String type, String name, String group){
            this.type = type;
            this.name = name;
            this.group = group;
        }

        public Builder actualStartDate(String actualStartDate){
            this.actualStartDate = actualStartDate;
            return this;
        }

        public Builder actualEndDate(String actualEndDate){
            this.actualEndDate = actualEndDate;
            return this;
        }

        public Builder piId(String piId){
            this.piId = piId;
            return this;
        }

        public Builder flowTaskId(String flowTaskId){
            this.flowTaskId = flowTaskId;
            return this;
        }

        public Builder modelName(String modelName){
            this.modelName = modelName;
            return this;
        }

        public Builder dataId(String dataId){
            this.dataId = dataId;
            return this;
        }

        public Builder description(String description){
            this.description = description;
            return this;
        }

        public Builder auditType(String auditType){
            this.auditType = auditType;
            return this;
        }

        public Builder modelId(String modelId){
            this.modelId = modelId;
            return this;
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public OrientHistoryTask build(){
            return new OrientHistoryTask(this);
        }

        private final String type;
        private final String name;
        private final String group;
        private String actualStartDate;
        private String actualEndDate;
        private String piId;
        private String flowTaskId;
        private String modelName;
        private String dataId;
        private String description;
        private String auditType;
        private String modelId;
        private String id;//唯一标识符
    }

    private OrientHistoryTask(Builder builder){

        this.type = builder.type;
        this.name = builder.name;
        this.group = builder.group;
        this.actualStartDate = builder.actualStartDate;
        this.actualEndDate = builder.actualEndDate;
        this.piId = builder.piId;
        this.flowTaskId = builder.flowTaskId;
        this.modelName = builder.modelName;
        this.dataId = builder.dataId;
        this.description = builder.description;
        this.auditType = builder.auditType;
        this.modelId = builder.modelId;
        this.id = builder.id;
    }

    private String type;
    private String name;
    private String actualStartDate;
    private String actualEndDate;
    private String piId;
    private String flowTaskId;
    private String modelName;
    private String dataId;
    private String description;
    private String auditType;
    private String group;
    private String id;//唯一标识符

    private String modelId;
}
