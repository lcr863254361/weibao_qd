package com.orient.background.util;

import com.orient.utils.ReflectUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2017-03-03 8:42 AM
 */
public class DBModelCompare<T> {

    public final static int ADD_ACTION = 1;
    public final static int UPDATE_ACTION = 2;
    public final static int DELETE_ACTION = 3;

    private List<T> exists = new ArrayList<>();

    public DBModelCompare(List<T> exists) {
        this.exists = exists;
    }

    public int doCompare(T target, String idProperty) {
        Long id = (Long) ReflectUtil.getFieldValue(target, idProperty);
        final Boolean[] needUpdate = {false};
        exists.forEach(loop -> {
            Long loopId = (Long) ReflectUtil.getFieldValue(loop, idProperty);
            if (loopId.intValue() == id.intValue()) {
                needUpdate[0] = true;
            }
        });
        if (needUpdate[0]) {
            return 2;
        } else {
            return 1;
        }
    }
}
