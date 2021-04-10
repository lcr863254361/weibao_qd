package com.orient.edm.init;

import com.edm.nio.server.EDMNioServerFactory;
import com.edm.nio.server.NioServer;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class OrientContextLoaderListener extends org.springframework.web.context.ContextLoaderListener {

    static Logger log = Logger.getLogger(OrientContextLoaderListener.class);
    public static ApplicationContext Appwac = null;

    @Override
    protected WebApplicationContext createWebApplicationContext(ServletContext servletContext) {
        Class<?> contextClass = determineContextClass(servletContext);
        if (!ConfigurableWebApplicationContext.class.isAssignableFrom(contextClass)) {
            throw new ApplicationContextException("Custom context class [" + contextClass.getName() + "] is not of type ["
                    + ConfigurableWebApplicationContext.class.getName() + "]");
        }
        ConfigurableWebApplicationContext wac = (ConfigurableWebApplicationContext) BeanUtils.instantiateClass(contextClass);
        wac.setServletContext(servletContext);
        createIMSLLicense(wac);

        //initRabbitMQ(wac);

        StringBuffer sb = new StringBuffer();
        sb.append(servletContext.getInitParameter(CONFIG_LOCATION_PARAM));
        boolean installed = false;
        String jdbcURL = null;
        String type = "0";
        try {
            Resource resource = wac.getResource("classpath:jdbc.properties");
            Properties properties = PropertiesLoaderUtils.loadProperties(resource);
            type = properties.getProperty("sysTables.type");
            if ("1".equals(type)) {
                installed = true;
                jdbcURL = properties.getProperty("jdbc.url");
                sb.append(" classpath*:springdao/*.xml");
            } else {
                //sb.append(" classpath*:springdao/gwtserviceContext.xml");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        wac.setConfigLocation(sb.toString());
        wac.getConfigLocations();
        customizeContext(servletContext, wac);
        wac.refresh();
        if (installed) {
            startSocketServer(wac);
        }

        Appwac = wac;
        if ("1".equals(type)) {
            InitLoadStart start = (InitLoadStart) wac.getBean("initContextLoad");
            start.loadModules(wac);
        } else {
            DBInitBusiness dbInitBusiness = (DBInitBusiness) wac.getBean("DBInitBusiness");
            try {
                dbInitBusiness.setWac(wac);
                String msg = dbInitBusiness.doDBInit();
                System.out.println("      _   _\n" +
                        "     ((\\o/))\n" +
                        ".-----//^\\\\-----.\n" +
                        "|    /`| |`\\    |\n" +
                        "|      | |      |\n" +
                        "|      | |      |\n" +
                        "`------===------'\n" + msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return wac;
    }

    public static void createIMSLLicense(ConfigurableWebApplicationContext wac) {
        Resource resource = wac.getResource("/WEB-INF/license.dat");
        try {
            File IMSLLicenseFile = resource.getFile();
            String path = IMSLLicenseFile.getAbsolutePath();
            System.setProperty("com.imsl.license.path", path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动文件服务及post服务
     * <p>
     * <p>根据FtpConfig信息启动文件服务</p>
     * <p>根据配置启动socket的post服务</p>
     *
     * @param wac
     * @author [创建人] qjl <br/>
     * [创建时间] 2014-7-31 下午4:30:49 <br/>
     * @see
     */
    public static void startSocketServer(ConfigurableWebApplicationContext wac) {
        try {

            EDMNioServerFactory serverFactory = (EDMNioServerFactory) wac.getBean("EDMServerFactory");
            NioServer fileServer = serverFactory.getNioServers().get("FileServer");

            FileServerConfig fileServerConfig = (FileServerConfig) wac.getBean("fileServerConfig");
            //创建附件 目录
            if (!new File(fileServerConfig.getFtpHome()).exists()) {
                new File(fileServerConfig.getFtpHome()).mkdirs();
            }
            BeanUtils.copyProperties(fileServerConfig, fileServer);
            fileServer.getIoHandler().setHomeDir(fileServerConfig.getFtpHome());
            fileServer.setJdbcTemplate((JdbcTemplate) wac.getBean("jdbcTemplate"));
            fileServer.start();

            log.info("NIO文件服务>>>>> 成功启动");
            NioServer postServer = serverFactory.getNioServers().get("PostServer");
            postServer.start();
            log.info("NIOPost服务>>>>> 成功启动");

        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("NIO文件服务>>>>> 启动失败", e);
        }
    }

    /**
     * 初始化RabbitMQ
     */
    @Deprecated
    private void initRabbitMQ(ConfigurableWebApplicationContext wac) {
        String exchangeName = "CommonExchange";
        String queueName = "CommonQueue";
        try {
            Resource resource = wac.getResource("classpath:mq.properties");
            Properties properties = PropertiesLoaderUtils.loadProperties(resource);
            String hostname = properties.getProperty("mq_hostname");
            String port = properties.getProperty("mq_port");
            String username = properties.getProperty("mq_username");
            String password = properties.getProperty("mq_password");
            String virtualhost = properties.getProperty("mq_virtualhost");

            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(hostname);
            factory.setPort(Integer.valueOf(port));
            factory.setUsername(username);
            factory.setPassword(password);
            factory.setVirtualHost(virtualhost);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(exchangeName, "topic");
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, exchangeName, "*");
            channel.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
