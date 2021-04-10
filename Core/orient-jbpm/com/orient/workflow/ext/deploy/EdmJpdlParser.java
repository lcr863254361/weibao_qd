package com.orient.workflow.ext.deploy;

import java.net.URL;

import javax.xml.XMLConstants;

import org.jbpm.internal.log.Log;
import org.jbpm.jpdl.internal.xml.JpdlParser; 

/**
 * @ClassName EdmJpdlParser
 * EDM的JPDL的解析类
 * @author zhulc@cssrc.com.cn
 * @date 2012-6-11
 */

public class EdmJpdlParser extends JpdlParser{
	private static final Log log = Log.getLog(EdmJpdlParser.class.getName());
	private static String emdJpdlXsd = "edmjpdl-4.4.xsd";
	
	
	/*扩展校验xsd方法*/
	public void setSchemaResources(String... schemaResources) {
	    // load resources from classpath
	    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	    int start=0;
	 /*   URL emdJpdlLocation = classLoader.getResource(emdJpdlXsd);
	   
	    int resourceLength = schemaResources.length;
	    
	    if(null!=emdJpdlLocation){
	    	start = 1;
	    	resourceLength = schemaResources.length+start;	    	
	    }
	   
	    if(null!=emdJpdlLocation){
	    	schemaSources[0] = emdJpdlXsd;
	    }*/
	    String[] schemaSources = new String[schemaResources.length];
	    for (int i = start; i < schemaResources.length; i++) {
	      String schemaResource = schemaResources[i];
	      URL schemaLocation = classLoader.getResource(schemaResource);
	      if (schemaLocation != null) {
	        log.info("loading schema resource: " + schemaResource);
	        schemaSources[i] = schemaLocation.toString();
	      }
	      else {
	        log.warn("skipping unavailable schema resource: " + schemaResource);
	      }
	    }

	    documentBuilderFactory.setValidating(true);
	    try {
	      // set xml schema as the schema language
	      documentBuilderFactory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage",
	        XMLConstants.W3C_XML_SCHEMA_NS_URI);
	      // set schema sources
	      documentBuilderFactory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaSource",
	        schemaSources);
	    }
	    catch (IllegalArgumentException e) {
	      log.warn("JAXP implementation does not support XML Schema, "
	        + "XML documents will not be checked for grammar errors", e);
	    }

	    try {
	      // validate the document only if a grammar is specified
	      documentBuilderFactory.setAttribute("http://apache.org/xml/features/validation/dynamic",
	        Boolean.TRUE);
	    }
	    catch (IllegalArgumentException e) {
	      log.warn("JAXP implementation is not Xerces, cannot enable dynamic validation, "
	        + "XML documents without schema location will not parse", e);
	    }

	     
	  }
}
