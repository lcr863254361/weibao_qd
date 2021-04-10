package com.orient.web.base.OrientEventBus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;

/**
 * Created by mengbin on 16/3/24.
 * Purpose:
 * Detail:
 */
public class OrientEventListener implements SmartApplicationListener {

    protected int executeOrder = 0;

    @Autowired
    protected ListenerOrderService listenerOrderService;

    /**
     * @param aClass
     * @return
     */
    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> aClass) {
        if (!isOrientEvent(aClass)) {
            return false;
        }
        return true;
    }


    /**
     * aClass 必须继承于 OrientEvent
     *
     * @param aClass
     * @return
     */
    public boolean isOrientEvent(Class<? extends ApplicationEvent> aClass) {
        if (!OrientEvent.class.isAssignableFrom(aClass)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean supportsSourceType(Class<?> aClass) {
        return true;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        return;
    }

    @Override
    public int getOrder() {
        return executeOrder;
    }

    /**
     * 根据配置文件设置事件的执行顺序与确定是否要执行
     *
     * @param source       事件发起源
     * @param eventClass   事件类的名称
     * @param listenerName 事件监听类的名称
     * @return 返回为Order值, 如果为-1,则表示该事件监听器无需执行
     */
    public int setExecutionOrder(Class<?> source, String eventClass, String listenerName) {
        String sourceName = source.getName();
        executeOrder = listenerOrderService.getOrder(sourceName, eventClass, listenerName);
        return executeOrder;
    }

    /**
     * 忽略当前的执行
     *
     * @param applicationEvent
     * @return
     */
    protected boolean isAbord(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof OrientEvent) {
            OrientEvent oEvent = (OrientEvent) applicationEvent;
            return oEvent.isAboard();
        }
        return false;
    }


    public int compareTo(SmartApplicationListener listener) {

        if (this.getOrder() == listener.getOrder()) {
            return 0;
        } else if (this.getOrder() > listener.getOrder()) {
            return 1;
        } else {
            return -1;
        }

    }
}
