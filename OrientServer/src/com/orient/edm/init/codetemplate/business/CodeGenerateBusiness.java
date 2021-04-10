package com.orient.edm.init.codetemplate.business;

import com.orient.edm.init.FileServerConfig;
import com.orient.edm.init.codetemplate.bean.CodeGeneraterBean;
import com.orient.web.form.engine.FreemarkEngine;
import com.orient.utils.FileOperator;
import com.orient.utils.PathTools;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-06-25 15:23
 */
@Component
public class CodeGenerateBusiness {

    @Autowired
    FileServerConfig fileServerConfig;

    @Autowired
    FreemarkEngine freemarkEngine;

    public String doGemerateCode(CodeGeneraterBean codeGeneraterBean) {
        String fileServerHome = fileServerConfig.getFtpHome();
        String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        //模板所在文件夹
        StringBuffer templatePath = new StringBuffer(PathTools.getClassPath()).append(File.separator)
                .append("com").append(File.separator)
                .append("orient").append(File.separator)
                .append("edm").append(File.separator)
                .append("init").append(File.separator)
                .append("codetemplate").append(File.separator)
                .append("config").append(File.separator);

        //生成临时文件夹
        String tmpDirName = fileServerHome+File.separator + currentTime;
        File tmpDir = new File(tmpDirName);
        tmpDir.mkdir();
        //生成dao
        generateDaoFile(tmpDir,codeGeneraterBean,templatePath);
        //生成service
        generateServiceFile(tmpDir,codeGeneraterBean,templatePath);
        //生成business
        generateBusinessFile(tmpDir,codeGeneraterBean,templatePath);
        //生成controller
        generateControllerFile(tmpDir,codeGeneraterBean,templatePath);
        //压缩文件夹
        String outZipName = tmpDirName + "_generateCode" + ".zip";
        try {
            FileOperator.zip(tmpDirName,outZipName,"");
            //删除临时文件夹
            FileOperator.delFoldsWithChilds(tmpDirName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currentTime+ "_generateCode" + ".zip";
    }

    private void generateControllerFile(File tmpDir, CodeGeneraterBean codeGeneraterBean, StringBuffer templatePath) {
        //创建controller文件夹
        String controllerPath = tmpDir.getAbsolutePath() + File.separator + "controller";
        FileOperator.createFolder(controllerPath);
        Map<String, Object> fieldsMap = getCommonTemplateVars(tmpDir,codeGeneraterBean);
        String name = codeGeneraterBean.getName();
        //创建controller接口
        String controllerTemplate = FileOperator.readFile(templatePath.toString() + "OrientController.ftl");
        try {
//            fieldsMap.put("BUSINESSPATH", codeGeneraterBean.getPackagePath() + ".business." + name + "Business");
            //生成controller接口文件
            fieldsMap.put("PACKAGE_NAME", codeGeneraterBean.getPackagePath() + ".controller");
            String controllerCode = freemarkEngine.parseByStringTemplate(fieldsMap, controllerTemplate);
            FileOperator.createFile(controllerPath+File.separator+name+"Controller.java",controllerCode.getBytes("UTF-8"));
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateBusinessFile(File tmpDir, CodeGeneraterBean codeGeneraterBean, StringBuffer templatePath) {
        //创建business文件夹
        String businessPath = tmpDir.getAbsolutePath() + File.separator + "business";
        FileOperator.createFolder(businessPath);
        Map<String, Object> fieldsMap = getCommonTemplateVars(tmpDir,codeGeneraterBean);
        String name = codeGeneraterBean.getName();
        //创建business接口
        String businessTemplate = FileOperator.readFile(templatePath.toString() + "OrientBusiness.ftl");
        try {
//            fieldsMap.put("SERVICEPATH", codeGeneraterBean.getPackagePath() + ".service.I" + name + "Service");
            //生成business接口文件
            fieldsMap.put("PACKAGE_NAME", codeGeneraterBean.getPackagePath() + ".business");
            String businessCode = freemarkEngine.parseByStringTemplate(fieldsMap, businessTemplate);
            FileOperator.createFile(businessPath+File.separator+name+"Business.java",businessCode.getBytes("UTF-8"));
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateServiceFile(File tmpDir, CodeGeneraterBean codeGeneraterBean, StringBuffer templatePath) {
        //创建service文件夹
        String servicePath = tmpDir.getAbsolutePath() + File.separator + "service";
        String serviceImplPath = servicePath + File.separator + "impl";
        FileOperator.createFolder(servicePath);
        FileOperator.createFolder(serviceImplPath);
        Map<String, Object> fieldsMap = getCommonTemplateVars(tmpDir,codeGeneraterBean);
        String name = codeGeneraterBean.getName();
        //创建service接口
        String serviceTemplate = FileOperator.readFile(templatePath.toString() + "IOrientService.ftl");
        String serviceImplTemplate = FileOperator.readFile(templatePath.toString() + "OrientService.ftl");
        try {
            //生成service接口文件
            fieldsMap.put("PACKAGE_NAME", codeGeneraterBean.getPackagePath() + ".service");
            String serviceCode = freemarkEngine.parseByStringTemplate(fieldsMap, serviceTemplate);
            FileOperator.createFile(servicePath+File.separator+"I"+name+"Service.java",serviceCode.getBytes("UTF-8"));
            //生成service实现文件
            fieldsMap.put("PACKAGE_NAME", codeGeneraterBean.getPackagePath() + ".service.impl");
//            fieldsMap.put("DAOPATH", codeGeneraterBean.getPackagePath() + ".dao.I" + name + "Dao");
            fieldsMap.put("LOWERNAME", name.substring(0,1).toLowerCase() + name.substring(1,name.length()));
            String serviceImplCode = freemarkEngine.parseByStringTemplate(fieldsMap, serviceImplTemplate);
            FileOperator.createFile(serviceImplPath+File.separator+name+"Service.java",serviceImplCode.getBytes("UTF-8"));
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateDaoFile(File tmpDir, CodeGeneraterBean codeGeneraterBean, StringBuffer templatePath) {
        //创建dao文件夹
        String daoPath = tmpDir.getAbsolutePath() + File.separator + "dao";
        String daoImplPath = daoPath + File.separator + "impl";
        FileOperator.createFolder(daoPath);
        FileOperator.createFolder(daoImplPath);
        Map<String, Object> fieldsMap = getCommonTemplateVars(tmpDir,codeGeneraterBean);
        //创建dao接口
        String daoTemplate = FileOperator.readFile(templatePath.toString() + "IOrientDao.ftl");
        String daoImplTemplate = FileOperator.readFile(templatePath.toString() + "OrientDao.ftl");
        try {
            //生成dao接口文件
            fieldsMap.put("PACKAGE_NAME", codeGeneraterBean.getPackagePath() + ".dao");
            String daoCode = freemarkEngine.parseByStringTemplate(fieldsMap, daoTemplate);
            FileOperator.createFile(daoPath+File.separator+"I"+codeGeneraterBean.getName()+"Dao.java",daoCode.getBytes("UTF-8"));
            //生成dao实现文件
            fieldsMap.put("PACKAGE_NAME", codeGeneraterBean.getPackagePath() + ".dao.impl");
            String daoImplCode = freemarkEngine.parseByStringTemplate(fieldsMap, daoImplTemplate);
            FileOperator.createFile(daoImplPath+File.separator+codeGeneraterBean.getName()+"Dao.java",daoImplCode.getBytes("UTF-8"));
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String,Object> getCommonTemplateVars(File tmpDir, CodeGeneraterBean codeGeneraterBean) {
        String name = codeGeneraterBean.getName();
        Map<String, Object> commonTemplateVars = new HashMap<>();
        commonTemplateVars.put("DESCRIPTION", codeGeneraterBean.getDesc());
        commonTemplateVars.put("CURRENTDATE", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        commonTemplateVars.put("NAME",codeGeneraterBean.getName());
        commonTemplateVars.put("BASEPACKAGE",codeGeneraterBean.getPackagePath());
        commonTemplateVars.put("HIBERNATEBEANNAME", codeGeneraterBean.getHibernateBeanName().substring(codeGeneraterBean.getHibernateBeanName().lastIndexOf(".")+1));
        commonTemplateVars.put("HIBERNATEBEANPATH", codeGeneraterBean.getHibernateBeanName());
        commonTemplateVars.put("LOWERNAME", name.substring(0,1).toLowerCase() + name.substring(1,name.length()));
        return commonTemplateVars;
    }

    public String getZipPath(String zipName) {
        return fileServerConfig.getFtpHome() + File.separator + zipName;
    }
}
