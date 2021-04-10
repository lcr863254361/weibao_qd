package com.orient.pvm.eventparam;

import com.orient.web.base.OrientEventBus.OrientEventParams;

/**
 * Created by Administrator on 2016/8/10 0010.
 */
public class DeleteCheckModelEventParam extends OrientEventParams {

    private Long[] toDelIds;

    public Long[] getToDelIds() {
        return toDelIds;
    }

    public void setToDelIds(Long[] toDelIds) {
        this.toDelIds = toDelIds;
    }

    public DeleteCheckModelEventParam(Long[] toDelIds) {
        this.toDelIds = toDelIds;
    }


}
