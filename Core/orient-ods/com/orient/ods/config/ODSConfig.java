package com.orient.ods.config;

import com.orient.config.ConfigInfo;
import com.orient.utils.CommonTools;

/**
 * Created by mengbin on 16/7/15.
 * Purpose:
 * Detail:
 */
public class ODSConfig extends ConfigInfo {

    public static final String  ODSDIRROOT;
    public static final String  ODSFILESUFFIX;

    public static final String  ODSFILETEMPLATENAME;


    private static final String configFileName = "config.properties";

    static {
        ODSDIRROOT = getPropertyValueConfigured("ODS.DIRRoot", configFileName,"");
        ODSFILESUFFIX = getPropertyValueConfigured("ODS.FileSuffix", configFileName,"");
        ODSFILETEMPLATENAME = getPropertyValueConfigured("ODS.TemplateFile", configFileName,"");
    }
}
