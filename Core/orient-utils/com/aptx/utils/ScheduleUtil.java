package com.aptx.utils;

import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public abstract class ScheduleUtil {
    public static final ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(25);
    public static final ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    public static <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay) {
        return scheduledThreadPool.schedule(callable, delay, TimeUnit.MILLISECONDS);
    }

    public static ScheduledFuture<?> schedule(Runnable command, long delay) {
        return scheduledThreadPool.schedule(command, delay, TimeUnit.MILLISECONDS);
    }

    public static ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period) {
        return scheduledThreadPool.scheduleAtFixedRate(command, initialDelay, period, TimeUnit.MILLISECONDS);
    }

    public static ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay) {
        return scheduledThreadPool.scheduleWithFixedDelay(command, initialDelay, delay, TimeUnit.MILLISECONDS);
    }

    public static <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) {
        try {
            return cachedThreadPool.invokeAll(tasks);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
            return Lists.newArrayList();
        }
    }

    public static <T> T invokeAny(Collection<? extends Callable<T>> tasks) {
        try {
            return cachedThreadPool.invokeAny(tasks);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> Future<T> submit(Callable<T> task) {
        return cachedThreadPool.submit(task);
    }

    public static Future<?> submit(Runnable task) {
        return cachedThreadPool.submit(task);
    }

    public static <T> T retry(Callable<T> task, long[] delays) {
        for(long delay : delays) {
            try {
                TimeUnit.MILLISECONDS.sleep(delay);
                Future<T> future = cachedThreadPool.submit(task);
                T result = future.get();
                return result;
            }
            catch (Throwable t) {
                t.printStackTrace();
            }
        }
        return null;
    }

    public static <T> T retry(Callable<T> task, long initialDelay, long period, int maxTimes) {
        long[] delays = new long[maxTimes];
        delays[0] = initialDelay;
        for(int i=1; i<maxTimes; i++) {
            delays[i] = period;
        }
        return retry(task, delays);
    }

    public static <T> T retry(Callable<T> task, long initialDelay, long startDelay, long multiple, int maxTimes) {
        long[] delays = new long[maxTimes];
        delays[0] = initialDelay;
        long delay = startDelay;
        for(int i=1; i<maxTimes; i++) {
            delays[i] = delay;
            delay = delay * multiple;
        }
        return retry(task, delays);
    }
}
