<%--
  Created by IntelliJ IDEA.
  User: DuanDuanPan
  Date: 2016/8/3 0003
  Time: 14:08
  若要使用系统中基于Ext的相关组件，则需要include此jsp
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="chrome=1">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="Shortcut Icon" href="${ctx}/app/images/orient.ico"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/app/styles/orient/portal.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/app/styles/orient/calendar.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/app/styles/orient/icon.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/app/styles/orient/picsPreview.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/app/styles/orient/chosenuserview.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/app/styles/orient/informState.css"/>

    <link rel="stylesheet" type="text/css" href="${ctx}/app/styles/orient/customGridCss.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/app/styles/orient/orientdataview.css"/>

    <link rel="stylesheet" type="text/css" href="${ctx}/app/styles/orient/waterDownTextfield.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/app/styles/orient/destroyCss.css"/>
    <link rel="stylesheet" type="text/css"
          href="${ctx}/app/javascript/lib/ext-4.2/examples/ux/statusbar/css/statusbar.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/app/javascript/lib/ext-4.2/examples/view/data-view.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/app/javascript/lib/ext-4.2/examples/shared/example.css"/>
    <script type="text/javascript" src="${ctx}/app/javascript/lib/ext-4.2/examples/shared/include-ext.js"></script>
    <script type="text/javascript" src="${ctx}/app/javascript/lib/ext-4.2/examples/shared/examples.js"></script>
    <script type="text/javascript" src="${ctx}/app/javascript/lib/ext-4.2/locale/ext-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${ctx}/app/javascript/orientjs/extjs/Common/Util/OrientValidator.js"></script>
    <script type="text/javascript" src="${ctx}/app/javascript/lib/swfupload-2.5/swfupload.js"></script>
    <script type="text/javascript" src="${ctx}/app/javascript/lib/soundmanager/script/soundmanager2.js"></script>
    <script type="text/javascript" src="${ctx}/app/javascript/lib/html5/html5.js"></script>
    <script type="text/javascript" src="${ctx}/app/javascript/lib/export/jquery.js"></script>
    <script type="text/javascript" src="${ctx}/app/javascript/lib/export/FileSaver.js"></script>
    <script type="text/javascript" src="${ctx}/app/javascript/lib/highcharts/highstock.js"></script>
    <script type="text/javascript" src="${ctx}/app/javascript/lib/highcharts/highcharts.js"></script>
    <script type="text/javascript" src="${ctx}/app/javascript/lib/highcharts/boost.js"></script>
    <%--<script type="text/javascript" src="${ctx}/app/javascript/lib/qrcode/jquery-qrcode.min.js"></script>--%>
    <%--<script type="text/javascript" src="${ctx}/app/javascript/lib/qrcode/jquery-3.4.1.min.js"></script>--%>

<%--Orient Ext 基础依赖--%>
    <script type="text/javascript">
        //定义全局变量
        var contextPath = '<%=basePath%>';
        var serviceName = '<%=request.getContextPath()%>';
        var globalPageSize = '${globalPageSize}';
        var userId = '${userId}';
        var username = "${username}>";
        var userAllName = "${userAllName}";
        var deptId = "${deptId}";
        var deptName = "${deptName}";
        //服务端参数
        var TDM_SERVER_CONFIG = Ext.decode('${TDM_SERVER_CONFIG}');
        Ext.Loader.setConfig({
            enabled: true,
            disableCaching: false
        });
        Ext.Loader.setPath({
            'OrientTdm': serviceName + '/app/javascript/orientjs/extjs',
            'Ext.ux': serviceName + '/app/javascript/lib/ext-4.2/examples/ux'
        });
        (function () {
            function globalExtAjaxSetting(conn, response, options) {
                //校验是否是json串
                if (response.responseText.indexOf("奥蓝托试验数据管理系统") != -1) {
                    //未登录 跳转至登录页面
                    Ext.Msg.alert("提示", "请先登录系统", function () {
                        top.window.location.href = serviceName + "/index.jsp";
                    });
                } else {
                    try {
                        var respData = Ext.decode(response.responseText);
                        if (respData.alertMsg == true) {
                            if (respData.success == false) {
                                OrientExtUtil.Common.err('失败', respData.msg);
                            } else if (respData.success == true && !Ext.isEmpty(respData.msg)) {
                                OrientExtUtil.Common.tip('成功', respData.msg);
                            }
                        }
                        response.decodedData = respData;
                    } catch (ex) {
                        response.decodedData = response.responseText;
                    }
                }
            }

            function globalExtAjaxException(conn, response, options) {

            }

            //Ext Ajax请求
            if (Ext) {
                Ext.Ajax.on('requestcomplete', globalExtAjaxSetting, this);
                Ext.Ajax.on('requestexception', globalExtAjaxException, this);
                //必填项显示*
                Ext.form.field.Base.override({
                    initComponent: Ext.Function.createInterceptor(function () {
                        if (this.allowBlank !== undefined && !this.allowBlank) {
                            if (this.fieldLabel) {
                                this.afterLabelTextTpl = '<span style="color:red;font-weight:bold" data-qtip="必填项">*</span>'
                            }
                        }
                        this.callParent(arguments);
                    })
                });
            }
        })();
    </script>
    <script type="text/javascript" src="${ctx}/app/javascript/orientjs/extjs/BaseRequires.js"></script>
</head>
<body>

</body>
</html>

