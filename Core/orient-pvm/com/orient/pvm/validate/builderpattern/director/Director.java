package com.orient.pvm.validate.builderpattern.director;

import com.orient.pvm.bean.CheckTemplateParseResult;
import com.orient.pvm.validate.builderpattern.Builder;

/**
 * Created by Administrator on 2016/8/9 0009.
 */
public interface Director {
    CheckTemplateParseResult doBuild(Builder builder);
}
