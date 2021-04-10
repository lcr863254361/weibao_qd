package com.orient.flow.controller;

import com.orient.collab.business.GanttBusiness;
import com.orient.collab.util.JPDLTool;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.edm.sysCtrl.SystemManager;
import com.orient.flow.util.FlowTypeHelper;
import com.orient.jpdl.model.Process;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.domain.template.CollabTemplateNode;
import com.orient.sysmodel.domain.user.UserDAO;
import com.orient.sysmodel.service.collab.ICollabFunctionService;
import com.orient.sysmodel.service.collab.ICollabRoleService;
import com.orient.sysmodel.service.template.impl.CollabTemplateNodeService;
import com.orient.sysmodel.service.template.impl.CollabTemplateService;
import com.orient.template.business.core.TemplateEngine;
import com.orient.web.base.BaseController;
import com.orient.web.base.CommonResponseData;
import com.orient.workflow.bean.AssignUser;
import com.orient.workflow.bean.JBPMInfo;
import com.orient.workflow.service.impl.DeployServiceImpl;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author Seraph
 *         2016-07-02 下午1:33
 */
@Controller
@RequestMapping("/test")
public class TestController extends BaseController {

    @RequestMapping("/formSubmit")
    @ResponseBody
    public void formSubmit(HttpServletRequest request, HttpServletResponse response) {
        String url = request.getParameter("url");
        String method = request.getParameter("method");
        String sendDataType = request.getParameter("sendDataType");
        String receiveDataType = request.getParameter("receiveDataType");
        String requestData = request.getParameter("requestData");

        CloseableHttpClient httpClient = HttpClients.createDefault();

        ContentType sendCt;
        if (sendDataType.equalsIgnoreCase("xml")) {
            sendCt = ContentType.APPLICATION_XML;
        } else {
            sendCt = ContentType.APPLICATION_JSON;
        }

        HttpRequestBase httpRequestBase;
        if (method.equalsIgnoreCase("get")) {
            httpRequestBase = new HttpGet(url);
        } else if (method.equalsIgnoreCase("post")) {
            HttpEntity httpEntity = new StringEntity(requestData, sendCt);
            httpRequestBase = new HttpPost(url);
            ((HttpPost) httpRequestBase).setEntity(httpEntity);
        } else if (method.equalsIgnoreCase("put")) {
            HttpEntity httpEntity = new StringEntity(requestData, sendCt);
            httpRequestBase = new HttpPut(url);
            ((HttpPut) httpRequestBase).setEntity(httpEntity);
        } else if (method.equalsIgnoreCase("delete")) {
            httpRequestBase = new HttpDelete(url);
        } else {
            httpRequestBase = new HttpGet(url);
        }

        ContentType acceptCt;
        if (receiveDataType.equalsIgnoreCase("xml")) {
            acceptCt = ContentType.APPLICATION_XML;
        } else {
            acceptCt = ContentType.APPLICATION_JSON;
        }
        httpRequestBase.addHeader("accept", acceptCt.toString());

        CloseableHttpResponse clientResponse = null;
        try {
            clientResponse = httpClient.execute(httpRequestBase);
            HttpEntity entity = clientResponse.getEntity();
            String result = IOUtils.toString(entity.getContent());

            response.getOutputStream().print(result);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @RequestMapping(value="/testLock", method= RequestMethod.GET)
    @ResponseBody
    public Object testLock(){
        SystemManager.getManager().lock();
        return new CommonResponseData(true, "xxx");
    }

    @RequestMapping(value="/testTemplate", method= RequestMethod.GET)
    @ResponseBody
    public Object testTemplate(){

//        Project projet = this.orientSqlEngine.getTypeMappingBmService().getById(Project.class, "261");
//        CollabTemplateNode templateNode = this.templateEngine.doExport(projet, new CollabTemplate());
//
//        this.collabTemplateNodeService.save(templateNode);

        CollabTemplateNode collabTemplateNode = this.collabTemplateNodeService.getById(220L);

//        this.collabTemplateNodeService.delete(collabTemplateNode);
//        this.templateEngine.doImport(templateNode);

        List<CollabTemplateNode> children = collabTemplateNode.getChildren();
        return collabTemplateNode;
    }

    @RequestMapping(value="/demoGet", method= RequestMethod.GET)
    @ResponseBody
    public CommonResponseData demoGet() throws Exception{
//        List<Plan> plans = this.ganttBusiness.getSuccessorPlans("42");
//        plans = this.ganttBusiness.getPredecessorPlans("22");

        InputStream inputStream = new FileInputStream(new File("/Users/Seraph/Downloads/x.jpdl.xml"));
        try {
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            Process process = Process.unmarshal(reader);
            inputStream.close();

            JPDLTool.getInstance().updateJpdlContentWithCollabTasks(process, new ArrayList<>());
        } catch (MarshalException e) {
            e.printStackTrace();
        } catch (ValidationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new CommonResponseData(true, "xxx");
    }


//    http://localhost:8080/OrientEDM/collabFlow/jpdl/generateOrUpdate.rdm?modelName=PLAN&dataId=10
    /**
     * see JBPMUploadServlet
     * @return
     */
    @RequestMapping(value="/deploy", method= RequestMethod.GET)
    @ResponseBody
    public CommonResponseData deployTest(){
        String downloadFilePath = "/Users/Seraph/Downloads/CB_PLAN_661.zip";
        ProcessEngine processEngine = (ProcessEngine) OrientContextLoaderListener.Appwac.getBean("processEngine");
        DeployServiceImpl deployService = OrientContextLoaderListener.Appwac.getBean(DeployServiceImpl.class);
        String userShowName = "spf";

        Map<String,String> variables = new HashMap<String, String>();
//        variables.put(WorkFlowConstants.AUDIT_FLOW, WorkFlowConstants.AUDIT_FLOW);
        variables.put("CB_PLAN", "661");
        String pdId = deployService.deploy(downloadFilePath, userShowName, variables, false);

        String rootType = "";
        if(rootType.equals("")){
            //更新审批流程定义的内存
        }

        FlowTypeHelper.refresh();

        return new CommonResponseData(true, "xxx");
    }

    /**
     * startProcessTest see FlowExecutionBusiness
     */
    @RequestMapping(value="/startProcess", method= RequestMethod.GET)
    @ResponseBody
    public CommonResponseData startProcessTest(){


        ProcessEngine processEngine = (ProcessEngine) OrientContextLoaderListener.Appwac.getBean("processEngine");


        AssignUser user = new AssignUser();
        user.setCandidateUsers("pdd");
        user.setCurrentUser("pdd");
        Map<String, AssignUser> variables = new HashMap<String, AssignUser>();
        variables.put("任务1", user);
        Map<String,Object> eObj = new HashMap<String,Object>();
        eObj.put(JBPMInfo.DynamicUserAssign, variables);
        ProcessInstance processInstance = processEngine.getExecutionService()
                .startProcessInstanceByKey("Test1",eObj);

        List<ProcessInstance> pis = processEngine.getExecutionService().createProcessInstanceQuery().list();
        List<Task> taskList = processEngine.getTaskService().findPersonalTasks("jbpm1");
        for(Task task:taskList){
            TaskImpl taskImpl =(TaskImpl)task;
            System.out.println(taskImpl.getExecutionId());
        }

//        //流程实例处理Service
//        ExecutionService executionService = processEngine.getExecutionService();
//        // 根据流程定义id启动实例
//        Map<String, Object> variables = UtilFactory.newHashMap();
//        String pdId = "EDM流程-1";
//        ProcessInstance processInstance = executionService.startProcessInstanceById(pdId, variables);

//        ProcessInstance pi = executionService.createProcessInstanceQuery().processInstanceId(processInstance.getId()).uniqueResult();
        return new CommonResponseData(true, "xxx");
    }

    @RequestMapping(value="/testTeamDao", method= RequestMethod.GET)
    @ResponseBody
    @Transactional
    public CommonResponseData teamDaoTest(){

//        CollabFunction function = new CollabFunction();
//        function.setName("测试功能点1");
//        function.setBelongedModel("PROJECT");
//        function.setCommentInfo("o");
//
//        this.collabFunctionService.save(function);
//
//        CollabFunction function2 = new CollabFunction();
//        function2.setName("测试功能点2");
//        function2.setBelongedModel("PROJECT");
//        function2.setCommentInfo("o");
//
//        this.collabFunctionService.save(function2);
//
//        function = this.collabFunctionService.getByName("测试功能点1");
//        function2 = this.collabFunctionService.getByName("测试功能点2");
//
//        Set<CollabFunction> functions = UtilFactory.newHashSet();
//        functions.add(function);
//        functions.add(function2);
//
//        CollabRole role = new CollabRole();
//        role.setName("测试角色1");
//        role.setModelName("PROJECT");
//        role.setDataId("1");
//        role.setFunctions(functions);
//
//        User user = this.userDAO.findById("180");
//
//        Set<CollabRole.User> users = new HashSet<>();
//        CollabRole.User collabUser = new CollabRole.User();
//        collabUser.setId(user.getId());
//        users.add(collabUser);
//
//        role.setUsers(users);
//        this.collabRoleService.save(role);
//
//        CollabRole r = this.collabRoleService.getByName("测试角色1");


//        CollabFunction function = this.collabFunctionService.getByName("工作组");
//
//        Set<CollabFunction> functions = UtilFactory.newHashSet();
//        functions.add(function);
//
//        CollabRole role = new CollabRole();
//        role.setName("测试角色1");
//        role.setModelName("PLAN");
//        role.setDataId("10");
//        role.setFunctions(functions);
//
//        User user = this.userDAO.findById("180");
//
//        Set<CollabRole.User> users = new HashSet<>();
//        CollabRole.User collabUser = new CollabRole.User();
//        collabUser.setId(user.getId());
//        users.add(collabUser);
//
//        role.setUsers(users);
//        this.collabRoleService.save(role);

//        CollabRole r = this.collabRoleService.getByName("测试角色1");


//        CollabRole role = new CollabRole();
//        role.setName("测试角色1");
//        role.setModelName("PROJECT");
//        role.setDataId("1");
//        role.setId(904L);
//
//        this.collabRoleService.save(role);

//        this.metaDAOFactory.getJdbcTemplate().update("update COLLAB_ROLE t set t.NAME='工作组' where t.NAME = '团队'");

//        ProducerBindBean producerBindBean = new ProducerBindBean();
//        producerBindBean.setI(100);
//        producerBindBean.setX("s");
//        messageEngine.send("hello", producerBindBean);
//        messageEngine.send("x", producerBindBean);
        return new CommonResponseData(true, "xxx");
    }

//    @Autowired
//    private MessageEngine messageEngine;

    @Autowired
    @Qualifier("UserDAO")
    private UserDAO userDAO;

    @Autowired
    private ICollabFunctionService collabFunctionService;
    @Autowired
    private ICollabRoleService collabRoleService;
    @Autowired
    private GanttBusiness ganttBusiness;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    protected ISqlEngine orientSqlEngine;
    @Autowired
    private CollabTemplateService collabTemplateService;
    @Autowired
    private CollabTemplateNodeService collabTemplateNodeService;
    @Autowired
    private MetaDAOFactory metaDAOFactory;
}

