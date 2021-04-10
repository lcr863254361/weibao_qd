package com.orient.modeldata.analyze.config;

import com.orient.modeldata.analyze.bean.Configuration;
import com.orient.modeldata.analyze.strategy.AnalyzeStrategy;
import com.orient.modeldata.analyze.strategy.JarBasedAnalyzeStrategy;

import java.io.IOException;
import java.util.jar.JarFile;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-11-23 16:28
 */
public class JarBasedAnalyzeConfig extends AnalyzeConfig {
    private JarFile jarFile;

    public JarBasedAnalyzeConfig(Configuration config) {
        super(config);

        //jarPath
        String jarPath = config.getJarPath();
        try {
            this.jarFile = new JarFile(jarPath);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public AnalyzeStrategy buildAnalyzeStrategy() {
        return new JarBasedAnalyzeStrategy(this);
    }

    public JarFile getJarFile() {
        return jarFile;
    }

    public void setJarFile(JarFile jarFile) {
        this.jarFile = jarFile;
    }
}
