package com.aptx.utils.bean;

import com.aptx.utils.GsonUtil;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintStream;
import java.io.Serializable;

public abstract class BaseObject implements Serializable, Cloneable {
    public static final transient Gson gson = GsonUtil.DEFAULT_GSON;

    public static final transient PrintStream out = System.out;
    public static final transient PrintStream err = System.err;

    protected final transient Logger logger = LoggerFactory.getLogger(this.getClass());

}
