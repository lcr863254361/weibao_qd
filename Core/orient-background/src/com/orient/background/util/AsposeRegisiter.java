package com.orient.background.util;

import com.aspose.words.License;
import com.orient.edm.init.IContextLoadRun;
import com.orient.utils.PathTools;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-06-29 15:02
 */
@Component("AsposeRegisiter")
public class AsposeRegisiter implements IContextLoadRun {

    @Override
    public boolean modelLoadRun(WebApplicationContext contextLoad) {
        try {
            InputStream is = new FileInputStream(PathTools.getClassPath() + File.separator + "license.xml");
            License aposeLic = new License();
            aposeLic.setLicense(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
