package com.orient.web.filter;

import com.orient.edm.sysCtrl.SystemControlPoint;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * when the system is locked, block all the request
 *
 * @author Seraph
 *         2016-10-12 下午2:43
 */
public class SystemLockFilter implements Filter, SystemControlPoint {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.register();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if(locked.get()){
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    @Override
    public void destroy() {

    }

    @Override
    public void lock() {
        if(!locked.compareAndSet(false, true)){
            return;
        }

        latch = new CountDownLatch(1);
    }

    @Override
    public void unlock() {
        if(!locked.compareAndSet(true, false)){
            return;
        }

        latch.countDown();
    }

    private AtomicBoolean locked = new AtomicBoolean(false);
    private CountDownLatch latch = new CountDownLatch(1);
}
