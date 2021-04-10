package com.aptx.utils.event;

import com.aptx.utils.bean.BaseObject;
import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;
import org.springframework.stereotype.Component;

@Component
public class DefaultDeadEventListener extends BaseObject {
    @Subscribe
    public void deadEvent(DeadEvent event) {
        Object source = event.getSource();
        Object evt = event.getEvent();
        logger.warn("DeadEvent: " +  event.toString());
    }
}
