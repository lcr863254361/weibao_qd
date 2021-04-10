package com.orient.modeldata.analyze.parser;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-11-24 15:38
 */
public class JarBasedParser implements Parser {
    private Parser parser;
    private Method readHeadersMethod;
    private Method getHeadersMethod;
    private Method readRecordMethod;
    private Method getValuesMethod;

    public JarBasedParser(File file, JarFile jarFile) {
        try {
            String mainClassName = "com.orient.Main";
            Manifest manifest = jarFile.getManifest();
            if (manifest != null) {
                String mainClassVal = manifest.getMainAttributes().getValue("Main-Class");
                if(mainClassVal!=null && !"".equals(mainClassVal)) {
                    mainClassName = mainClassVal;
                }
            }

            mainClassName = mainClassName.replaceAll("/", ".");
            File workDir = File.createTempFile("unjar", "", new File(System.getProperty("user.dir")));
            workDir.delete();
            workDir.mkdirs();

            unJar(jarFile, workDir);
            List<URL> classPath = new ArrayList<>();
            classPath.add(new File(workDir+"/").toURL());
            classPath.add(new File(jarFile.getName()).toURL());
            ClassLoader loader = new URLClassLoader(classPath.toArray(new URL[0]), Thread.currentThread().getContextClassLoader());

            Class<?> mainClass = loader.loadClass(mainClassName);
            Constructor constructor = mainClass.getConstructor(File.class);
            this.parser = (Parser) constructor.newInstance(file);
            this.readHeadersMethod = mainClass.getMethod("readHeaders", int.class);
            this.getHeadersMethod = mainClass.getMethod("getHeaders", null);
            this.readRecordMethod = mainClass.getMethod("readRecord", null);
            this.getValuesMethod = mainClass.getMethod("getValues", null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean readHeaders(int row) {
        try {
            return (boolean) this.readHeadersMethod.invoke(parser, row);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<String> getHeaders() {
        try {
            return (List<String>) this.getHeadersMethod.invoke(parser, null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean readRecord() {
        try {
            return (boolean) this.readRecordMethod.invoke(parser, null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //添加判断当前行是否为空的接口 TeddyJohnson 2018.7.27
    @Override
    public boolean readCurrentRecord() {
        return false;
    }

    @Override
    public List<String> getValues() {
        try {
            return (List<String>) this.getValuesMethod.invoke(parser, null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void unJar(JarFile jarFile, File toDir) throws IOException {
        try {
            Enumeration entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = (JarEntry) entries.nextElement();
                if (!entry.isDirectory()) {
                    InputStream in = jarFile.getInputStream(entry);
                    try {
                        File file = new File(toDir, entry.getName());
                        if (!file.getParentFile().mkdirs()) {
                            if (!file.getParentFile().isDirectory()) {
                                throw new IOException("Mkdirs failed to create " + file.getParentFile().toString());
                            }
                        }
                        OutputStream out = new FileOutputStream(file);
                        try {
                            byte[] buffer = new byte[8192];
                            int i;
                            while ((i = in.read(buffer)) != -1) {
                                out.write(buffer, 0, i);
                            }
                        }
                        finally {
                            out.close();
                        }
                    }
                    finally {
                        in.close();
                    }
                }
            }
        }
        finally {
            jarFile.close();
        }
    }
}
