package com.orient.edm.init;

import org.springframework.web.context.WebApplicationContext;

public interface  IContextLoadRun {

    boolean modelLoadRun(WebApplicationContext contextLoad);

}