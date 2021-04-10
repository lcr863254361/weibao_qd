/*
 * Copyright (c) 2016. Orient Company
 *
 */

package com.orient.ods.atfx.business;

import EDU.oswego.cs.dl.util.concurrent.ConcurrentHashMap;
import com.orient.ods.atfx.model.AtfxSession;
import com.orient.ods.config.ODSConfig;
import com.orient.utils.PathTools;
import de.rechner.openatfx.AoServiceFactory;
import de.rechner.openatfx.util.FileUtil;
import org.asam.ods.AoException;
import org.asam.ods.AoSession;
import org.omg.CORBA.ORB;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;
import java.util.Map;

/**
 * Created by mengbin on 16/3/18.
 * Purpose:
 * Detail:
 */
@Service
public class AtfxFileMangrBusiness {


    public static Map<String, AtfxSession> aoSessionPool = new ConcurrentHashMap();

    public static AtfxSession openFile(String atfxFileNamePath, StringBuffer efforInfo) {
        AtfxSession atfxSession = aoSessionPool.get(atfxFileNamePath);
        if (atfxSession != null) {
            atfxSession.setLastAccessTime(new Date());

            return atfxSession;
        }

        try {
            ORB orb = ORB.init(new String[0], System.getProperties());
            AoSession aoSession = AoServiceFactory.getInstance().newAoFactory(orb).newSession("FILENAME=" + new File(atfxFileNamePath));
            atfxSession = new AtfxSession(aoSession);
            aoSessionPool.put(atfxFileNamePath, atfxSession);

        } catch (AoException e) {
            efforInfo.append(e.getMessage());
            return null;
        }
        return atfxSession;
    }

    /**
     * @param atfxFileNamePath
     */
    public static void closeFile(String atfxFileNamePath) {
        AtfxSession atfxSession = aoSessionPool.get(atfxFileNamePath);
        if (atfxSession != null) {

            try {

                atfxSession.getAoSession().close();
                aoSessionPool.remove(atfxFileNamePath);
            } catch (AoException e) {

            }
        }
    }


    /**
     * 根据模版
     *
     * @param newFilePath
     * @return
     */
    public static AtfxSession createExportFile(String newFilePath) {

        try {
            ORB orb = ORB.init(new String[0], System.getProperties());
            String templateFilePath = ODSConfig.ODSFILETEMPLATENAME;
            String WebRootPath = PathTools.getRootPath();
            templateFilePath = WebRootPath + File.separator + templateFilePath;
            File templateFile = new File(templateFilePath);

            File newFile = new File(newFilePath);
            AtfxSession atfxSession = aoSessionPool.get(newFilePath);
            if (atfxSession != null) {
                try {
                    atfxSession.getAoSession().close();
                    aoSessionPool.remove(newFilePath);
                } catch (AoException e) {

                }
            }
            if (newFile.exists()) {
                newFile.delete();
            }
            newFile.createNewFile();
            FileUtil.copyFile(templateFile, newFile);

            AoSession aoSession = AoServiceFactory.getInstance().newAoFactory(orb)
                    .newSession("FILENAME=" + newFile);

            atfxSession = new AtfxSession(aoSession);
            aoSessionPool.put(newFilePath, atfxSession);
            aoSession.startTransaction();


            return atfxSession;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean closeExportFile(AtfxSession atfxSession) {
        try {

            atfxSession.getAoSession().commitTransaction();
            //   atfxSession.getAoSession().close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


}
