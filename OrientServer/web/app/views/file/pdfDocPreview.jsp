<%@ page language="java" import="java.util.*,
                                 com.orient.edm.init.FileServerConfig,
                                 com.orient.edm.init.OrientContextLoaderListener,
                                 com.orient.web.util.UserContextUtil" pageEncoding="gb2312" %>
<%@ page import="com.zhuozhengsoft.pageoffice.*, com.zhuozhengsoft.pageoffice.wordwriter.*,java.awt.*" %>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po" %>
<%

    //文件服务器信息
    FileServerConfig fileServerConfig = (FileServerConfig) OrientContextLoaderListener.Appwac.getBean("fileServerConfig");
    String ftpHome = fileServerConfig.getFtpHome();
    //文件相关信息
    String fileName = (String)request.getSession().getAttribute("fileName");
    //文件路径
    String filePath = ftpHome + fileName;
    PDFCtrl poCtrl1 = new PDFCtrl(request);
    poCtrl1.setServerPage(request.getContextPath() + "/poserver.zz"); //此行必须
    // Create custom toolbar
    poCtrl1.addCustomToolButton("打印", "Print()", 6);
    poCtrl1.addCustomToolButton("隐藏/显示书签", "SetBookmarks()", 0);
    poCtrl1.addCustomToolButton("-", "", 0);
    poCtrl1.addCustomToolButton("实际大小", "SetPageReal()", 16);
    poCtrl1.addCustomToolButton("适合页面", "SetPageFit()", 17);
    poCtrl1.addCustomToolButton("适合宽度", "SetPageWidth()", 18);
    poCtrl1.addCustomToolButton("-", "", 0);
    poCtrl1.addCustomToolButton("首页", "FirstPage()", 8);
    poCtrl1.addCustomToolButton("上一页", "PreviousPage()", 9);
    poCtrl1.addCustomToolButton("下一页", "NextPage()", 10);
    poCtrl1.addCustomToolButton("尾页", "LastPage()", 11);
    poCtrl1.addCustomToolButton("-", "", 0);
    poCtrl1.webOpen(filePath);
    poCtrl1.setTagId("PDFCtrl1"); //此行必须
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>在线预览</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
</head>

<body>
<!--**************   卓正 PageOffice 客户端代码开始    ************************-->
<script language="javascript" type="text/javascript">
    function AfterDocumentOpened() {
        //alert(document.getElementById("PDFCtrl1").Caption);
    }
    function SetBookmarks() {
        document.getElementById("PDFCtrl1").BookmarksVisible = !document.getElementById("PDFCtrl1").BookmarksVisible;
    }

    function Print() {
        document.getElementById("PDFCtrl1").ShowDialog(4);
    }
    function SwitchFullScreen() {
        document.getElementById("PDFCtrl1").FullScreen = !document.getElementById("PDFCtrl1").FullScreen;
    }
    function SetPageReal() {
        document.getElementById("PDFCtrl1").SetPageFit(1);
    }
    function SetPageFit() {
        document.getElementById("PDFCtrl1").SetPageFit(2);
    }
    function SetPageWidth() {
        document.getElementById("PDFCtrl1").SetPageFit(3);
    }
    function ZoomIn() {
        document.getElementById("PDFCtrl1").ZoomIn();
    }
    function ZoomOut() {
        document.getElementById("PDFCtrl1").ZoomOut();
    }
    function FirstPage() {
        document.getElementById("PDFCtrl1").GoToFirstPage();
    }
    function PreviousPage() {
        document.getElementById("PDFCtrl1").GoToPreviousPage();
    }
    function NextPage() {
        document.getElementById("PDFCtrl1").GoToNextPage();
    }
    function LastPage() {
        document.getElementById("PDFCtrl1").GoToLastPage();
    }
    function RotateRight() {
        document.getElementById("PDFCtrl1").RotateRight();
    }
    function RotateLeft() {
        document.getElementById("PDFCtrl1").RotateLeft();
    }
</script>
<!--**************   卓正 PageOffice 客户端代码结束    ************************-->
<div style="width:auto; height:600px;">
    <po:PDFCtrl id="PDFCtrl1"/>
</div>
</body>
</html>