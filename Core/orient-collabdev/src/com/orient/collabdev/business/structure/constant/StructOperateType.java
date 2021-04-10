package com.orient.collabdev.business.structure.constant;

import java.util.HashMap;
import java.util.Map;

import static com.orient.collabdev.business.structure.constant.StructOperateConstant.*;

/**
 * ProcessType
 *
 * @author Seraph
 * 2016-08-12 下午3:36
 */
public enum StructOperateType {

    CREATE(STRUCT_OPERATE_CREATE),
    DELETE(STRUCT_OPERATE_DELETE),
    UPDATE(STRUCT_OPERATE_UPDATE);

    StructOperateType(String type) {
        this.type = type;
    }

    private final String type;

    @Override
    public String toString() {
        return this.type;
    }

    private static final Map<String, StructOperateType> stringToEnum = new HashMap<>();

    static {
        for (StructOperateType s : values()) {
            stringToEnum.put(s.toString(), s);
        }
    }

    public static StructOperateType fromString(String name) {
        return stringToEnum.get(name);
    }
}
