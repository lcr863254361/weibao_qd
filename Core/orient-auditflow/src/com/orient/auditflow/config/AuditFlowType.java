package com.orient.auditflow.config;

import java.util.HashMap;
import java.util.Map;

/**
 * the audit flow type
 *
 * @author Seraph
 *         2016-08-01 上午10:10
 */
public enum AuditFlowType {
    WbsBaseLineAudit("WbsBaseLineAudit", "WBS基线审批"),
    WbsBaseLineEditAudit("WbsBaseLineEditAudit", "WBS基线修改审批"),
    ModelDataAudit("ModelDataAudit", "模型数据审批");

    AuditFlowType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    private final String name;
    private final String description;

    public static AuditFlowType fromString(String name) {
        return nameToEnumMap.get(name);
    }

    private static Map<String, AuditFlowType> nameToEnumMap = new HashMap<>();

    static {
        for (AuditFlowType type : AuditFlowType.values()) {
            nameToEnumMap.put(type.toString(), type);
        }
    }
}
