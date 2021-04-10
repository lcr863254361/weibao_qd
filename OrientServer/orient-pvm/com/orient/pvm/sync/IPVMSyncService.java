package com.orient.pvm.sync;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2017-03-21 9:27
 */
public interface IPVMSyncService {
    public boolean login(String userName, String password, String PADCode);
    public String getUsers();
    public String getCheckTables(String userId);
    public String getCheckTableInfo(String checkTableId);
    public String uploadCheckTable(String xmlContent);
    public  String getHTMLContentById(String checkModelId);
    public String uploadHTMLContentById(String checkModelId, String htmlContent);

}
