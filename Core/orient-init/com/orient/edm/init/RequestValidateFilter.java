package com.orient.edm.init;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.edm.nio.util.CommonTools;

public class RequestValidateFilter implements Filter{

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		
		 HttpServletRequest httpServletRequest = (HttpServletRequest) request;
         HttpServletResponse httpServletResponse = (HttpServletResponse) response;
         String operationType = CommonTools.null2String(request.getParameter("operationType"));
         if("doUploadTemplate".equals(operationType) || "doDownLoadTemplate".equals(operationType) || "doExportTemplateData".equals(operationType)){
        	filterChain.doFilter(httpServletRequest, httpServletResponse);
         }
         return;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
