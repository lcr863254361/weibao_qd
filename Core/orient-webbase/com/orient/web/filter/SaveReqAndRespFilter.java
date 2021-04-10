/*---------------------------------------------------------------------------------------------------------
 *
 *   Developer：zhulc@cssrc.com.cn
 * Create Date：2012-12-10 下午03:38:24
 * Description：1.过滤器
 *              2.
 *              3.
 *   Copyright (C) 2012 jeff. All rights reserved.
 *
 *---------------------------------------------------------------------------------------------------------
 */

package com.orient.web.filter;

import com.orient.web.util.RequestUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class SaveReqAndRespFilter implements Filter {

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        RequestUtil.setHttpServletRequest((HttpServletRequest) request);
        RequestUtil.setHttpServletResponse((HttpServletResponse) response);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
        return;
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }

}
