package com.orient.background.doctemplate.transform;

import java.util.HashMap;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2016-12-07 8:59 PM
 */
public class DocTransformRegister {

    private Map<String, IDocTransform> transformMap = new HashMap<>();

    public void setTransformMap(Map<String, IDocTransform> transformMap) {
        this.transformMap = transformMap;
    }

    public IDocTransform getDocTransform(String key) {
        return transformMap.get(key);
    }
}
