package com.orient.collabdev.constant;

import com.orient.collabdev.model.CollabDevNodeVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-07-27 9:20 AM
 */
public enum PurposeHelper implements PurposeHandler {

    EXPANDTOPRJ("expandToPrj") {
        @Override
        public boolean isLeaf() {
            return true;
        }
    }, EXPANDTOTASK("expandToTask") {

    }, EXPANDTOPLAN("expandToPlan") {
        //remove task node

        @Override
        public void handleNode(List<CollabDevNodeVO> collabDevNodeVOS) {
            List<CollabDevNodeVO> toRemoveNodes = collabDevNodeVOS.stream().filter(collabDevNodeVO -> CollabDevConstants.NODE_TYPE_TASK.equalsIgnoreCase(collabDevNodeVO.getType()))
                    .collect(Collectors.toList());
            collabDevNodeVOS.removeAll(toRemoveNodes);
        }
    }, FORPROCESSING("forProcessing") {

    };

    public boolean isLeaf() {
        return false;
    }

    @Override
    public void handleNode(List<CollabDevNodeVO> collabDevNodeVOS) {

    }

    private String name;

    PurposeHelper(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    private static final Map<String, PurposeHelper> stringToEnum = new HashMap<>();

    static {
        for (PurposeHelper s : values()) {
            stringToEnum.put(s.toString(), s);
        }
    }

    public static PurposeHelper fromString(String name) {
        return stringToEnum.get(name);
    }
}
