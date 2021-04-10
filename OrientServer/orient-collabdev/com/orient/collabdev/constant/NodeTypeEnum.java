package com.orient.collabdev.constant;

import com.orient.collabdev.model.CollabDevNodeVO;

import java.util.HashMap;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-07-27 9:20 AM
 */
public enum NodeTypeEnum implements NodeAttributeConstruct {

    DIR(CollabDevConstants.NODE_TYPE_DIR) {
        @Override
        public void setIconCls(CollabDevNodeVO collabDevNodeVO) {
            collabDevNodeVO.setIconCls("icon-collabDev-catagory");
        }

        @Override
        public void setIsLeaf(CollabDevNodeVO collabDevNodeVO, PurposeHelper purpose) {
            collabDevNodeVO.setLeaf(false);
        }

        @Override
        public void setExpand(CollabDevNodeVO collabDevNodeVO, PurposeHelper purpose) {
            collabDevNodeVO.setExpanded(true);
        }
    }, PRJ(CollabDevConstants.NODE_TYPE_PRJ) {
        @Override
        public void setIconCls(CollabDevNodeVO collabDevNodeVO) {
            collabDevNodeVO.setIconCls("icon-collabDev-project");
        }

        @Override
        public void setIsLeaf(CollabDevNodeVO collabDevNodeVO, PurposeHelper purpose) {
            collabDevNodeVO.setLeaf(purpose.isLeaf());
        }

        @Override
        public void setExpand(CollabDevNodeVO collabDevNodeVO, PurposeHelper purpose) {
            collabDevNodeVO.setExpanded(!PurposeHelper.FORPROCESSING.equals(purpose));
        }
    }, PLAN(CollabDevConstants.NODE_TYPE_PLAN) {
        @Override
        public void setIconCls(CollabDevNodeVO collabDevNodeVO) {
            collabDevNodeVO.setIconCls("icon-collabDev-plan");
        }

        @Override
        public void setIsLeaf(CollabDevNodeVO collabDevNodeVO, PurposeHelper purpose) {
            collabDevNodeVO.setLeaf(false);
        }

        @Override
        public void setExpand(CollabDevNodeVO collabDevNodeVO, PurposeHelper purpose) {
            collabDevNodeVO.setExpanded(false);
        }
    }, TASK(CollabDevConstants.NODE_TYPE_TASK) {
        @Override
        public void setIconCls(CollabDevNodeVO collabDevNodeVO) {
            collabDevNodeVO.setIconCls("icon-collabDev-task");
        }

        @Override
        public void setIsLeaf(CollabDevNodeVO collabDevNodeVO, PurposeHelper purpose) {
            collabDevNodeVO.setLeaf(true);
        }

        @Override
        public void setExpand(CollabDevNodeVO collabDevNodeVO, PurposeHelper purpose) {
            collabDevNodeVO.setExpanded(false);
        }
    };

    private String name;

    NodeTypeEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    private static final Map<String, NodeTypeEnum> stringToEnum = new HashMap<>();

    static {
        for (NodeTypeEnum s : values()) {
            stringToEnum.put(s.toString(), s);
        }
    }

    public static NodeTypeEnum fromString(String name) {
        return stringToEnum.get(name);
    }
}
