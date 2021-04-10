/*
 * Copyright (c) 2016. Orient Company
 *
 */

package com.orient.web.base.OrientEventBus;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.AbstractApplicationEventMulticaster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

/**
 * Created by mengbin on 16/3/28.
 * Purpose:
 * Detail:
 */
public class OrientApplicationEventMulticaster extends AbstractApplicationEventMulticaster {

    private Executor taskExecutor = null;

    public OrientApplicationEventMulticaster() {
    }

    public OrientApplicationEventMulticaster(BeanFactory beanFactory) {
        this.setBeanFactory(beanFactory);
    }

    public void setTaskExecutor(Executor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    protected Executor getTaskExecutor() {
        return this.taskExecutor;
    }

    @Override
    public void multicastEvent(ApplicationEvent applicationEvent) {
        Collection<ApplicationListener> listeners = getApplicationListeners(applicationEvent);
        if (applicationEvent instanceof OrientEvent) {
            Collection<OrientEventListener> Olisteners = new ArrayList<>();
            //设置根据Source设置Order
            listeners.stream().filter(listener -> listener instanceof OrientEventListener).forEach(listener -> {
                OrientEventListener oListenter = (OrientEventListener) listener;
                int order = oListenter.setExecutionOrder(applicationEvent.getSource() instanceof Class ? (Class<?>) applicationEvent.getSource() : applicationEvent.getSource().getClass(), applicationEvent.getClass().getName(), oListenter.getClass().getName());
                if (order != -1) {
                    Olisteners.add(oListenter);
                }
            });
            //根据Order 进行排序
            Collection<OrientEventListener> sortedList = Olisteners.stream().sorted((a, b) -> a.compareTo(b)).collect(Collectors.toList());
            listeners.clear();
            listeners.addAll(sortedList);
        }


        for (Iterator it = listeners.iterator(); it.hasNext(); ) {
            final ApplicationListener listener = (ApplicationListener) it.next();
            Executor executor = getTaskExecutor();
            if (executor != null) {
                executor.execute(new Runnable() {
                    public void run() {
                        listener.onApplicationEvent(applicationEvent);
                    }
                });
            } else {
                listener.onApplicationEvent(applicationEvent);
            }
        }
    }


}
