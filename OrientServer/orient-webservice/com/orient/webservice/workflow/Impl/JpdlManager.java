package com.orient.webservice.workflow.Impl;

import com.orient.flow.config.FlowType;
import com.orient.flow.util.FlowTypeHelper;
import com.orient.utils.CommonTools;
import org.jbpm.api.Deployment;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.api.RepositoryService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


public class JpdlManager extends WorkFLowBean {

    /**
     * @Function Name:  getJpdlInfo
     * @Description: @return 数据库中所有的JPDL信息.
     * @Date Created:  2013-1-4 上午10:38:20
     * @Author: Pan Duan Duan
     * @Last Modified:     ,  Date Modified:
     */
    public String getJpdlInfo() {

        try {
            //得到流程定义处理service
            RepositoryService repositoryService = processEngine.getRepositoryService();
            //得到所有流程定义集合
            List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().orderDesc(ProcessDefinitionQuery.PROPERTY_DEPLOYMENT_TIMESTAMP).list();
            //拼接字符串
            StringBuffer sb = new StringBuffer();
            Map<String, List<String>> map = new HashMap<String, List<String>>();
            //遍历流程定义集合
            for (ProcessDefinition processDefinition : processDefinitions) {
                //得到部署ID
                String id = processDefinition.getDeploymentId();
                //根据部署ID得到资源文件名称集合
                Set<String> resourceNames = repositoryService.getResourceNames(id);
                //如果文件数量大于3 说明包含子流程
                if (resourceNames.size() > 3) {
                    //初始化主流程名称
                    String mainName = "";
                    //流程名称集合
                    List<String> list = new ArrayList<String>();
                    //遍历资源名称集合
                    for (Iterator<String> it = resourceNames.iterator(); it.hasNext(); ) {
                        //得到资源名称
                        String filename = it.next();
                        //如果是以png结尾 则说明是流程图文件
                        if (filename.endsWith(".png")) {
                            //得到流程名称
                            int fl = filename.length() - 4;
                            String name = filename.substring(0, fl);
                            //增加至流程名称集合
                            list.add(name);
                            //得到主流程名称
                            if (mainName.equals("")) {
                                mainName = name;
                                continue;
                            }
                            mainName = mainName.length() >= fl ? name : mainName;
                        }
                    }
                    //初始化子流程名称集合
                    List<String> nameList = new ArrayList<String>();
                    for (String name : list) {
                        if (!name.equals(mainName)) {
                            nameList.add(name.substring(mainName.length() + 1));
                        }
                    }
                    map.put(id, nameList);
                }
            }
            //遍历流程定义集合
            for (ProcessDefinition processDefinition : processDefinitions) {
                if (FlowTypeHelper.getFlowType(processDefinition.getName()).equals(FlowType.Audit)) {
                    //得到部署ID
                    String id = processDefinition.getDeploymentId();
                    if (map.containsKey(id) && map.get(id).contains(processDefinition.getName())) {
                        continue;
                    }
                    //得到部署对象
                    Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(processDefinition.getDeploymentId()).uniqueResult();
                    //部署人
                    String createUser = deployment.getName();
                    Date date = new Date(deployment.getTimestamp());
                    //部署时间
                    String createTime = CommonTools.time2String(date);
                    //流程定义状态
                    String state = deployment.getState();
                    //拼接返回结果
                    sb.append(processDefinition.getDeploymentId()).append(";,;.")
                            .append(processDefinition.getName()).append(";,;.")
                            .append(processDefinition.getKey()).append(";,;.")
                            .append(processDefinition.getVersion()).append(";,;.")
                            .append(processDefinition.getDescription() == null ? "" : processDefinition.getDescription()).append(";,;.");
                    // 增加流程定义文件的部署信息
                    sb.append(state).append(";,;.")
                            .append(createUser == null ? "" : createUser).append(";,;.")
                            .append(createTime).append(";;;;");
                }
            }
            //返回
            if (sb.length() >= 4) {
                return sb.substring(0, sb.length() - 4);
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    /**
     * @Function Name:  getJdpl
     * @Description: @param id
     * @Description: @return 根据deployId得到部署文件
     * @Date Created:  2013-1-4 上午10:53:01
     * @Author: Pan Duan Duan
     * @Last Modified:     ,  Date Modified:
     */
    public Map<String, byte[]> getJdpl(String id) {
        /**此处传来的name为ID，version和type都为空*/
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //根据ID得到所有部署文件名称集合
        Set<String> resourceNames = repositoryService.getResourceNames(id);
        Map<String, byte[]> fileMap = new HashMap<String, byte[]>();
        for (Iterator<String> it = resourceNames.iterator(); it.hasNext(); ) {
            String filename = it.next();
            //根据ID 以及 文件名称 得到文件流
            InputStream inputStream = repositoryService.getResourceAsStream(id, filename);
            //增加至返回结果中
            addFile(filename, inputStream, fileMap);
        }
        return fileMap;
    }

    /**
     * @Function Name:  getJpdl
     * @Description: @param name
     * @Description: @param version
     * @Description: @param type
     * @Description: @return
     * @Date Created:  2013-1-4 上午10:56:44
     * @Author: Pan Duan Duan
     * @Last Modified:     ,  Date Modified:
     */
    public String getJpdl(String name, String version, String type) {
        return null;
    }


    /**
     * @Function Name:  setJpdl
     * @Description: @param name
     * @Description: @param version
     * @Description: @param username
     * @Description: @param type
     * @Description: @param info
     * @Description: @return
     * @Date Created:  2013-1-4 上午10:57:10
     * @Author: Pan Duan Duan
     * @Last Modified:     ,  Date Modified:
     */
    public String setJpdl(String name, String version, String username, String type, String info) {
        return null;
    }


    /**
     * @Function Name:  deleteJpdl
     * @Description: @param name
     * @Description: @param version
     * @Description: @param username
     * @Description: @return
     * @Date Created:  2013-1-4 上午10:58:43
     * @Author: Pan Duan Duan
     * @Last Modified:     ,  Date Modified:
     */
    public String deleteJpdl(String name, String version, String username) {
        return null;
    }

    /**
     * @Function Name:  addFile
     * @Description: @param filename
     * @Description: @param inputStream
     * @Description: @param fileMap
     * @Date Created:  2013-1-4 上午10:54:36
     * @Author: Pan Duan Duan 将文件输入流转换成byte数组放到map中.
     * @Last Modified:     ,  Date Modified:
     */
    private void addFile(String filename, InputStream inputStream,
                         Map<String, byte[]> fileMap) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buff = new byte[256];
            int read;
            while ((read = inputStream.read(buff)) != -1) {
                byteArrayOutputStream.write(buff, 0, read);
            }
            fileMap.put(filename, byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
