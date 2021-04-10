<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ page import="com.zhuozhengsoft.pageoffice.*" %>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po" %>
<%
    String path = request.getContextPath();
    String belongPanelId = request.getParameter("itemId");
    belongPanelId = null == belongPanelId ? "" : belongPanelId;
    String reportName = request.getParameter("reportName");
    reportName = "".equals(reportName) ? "EmptyWord.doc" : reportName;
    String filePath = request.getSession().getServletContext().getRealPath("/DocTemplate/" + reportName);
    //******************************卓正PageOffice组件的使用*******************************
    PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
    //设置服务器页面
    poCtrl1.setServerPage(request.getContextPath() + "/poserver.zz");
    poCtrl1.setTitlebar(false);
    poCtrl1.setMenubar(false);

    //自定义工具栏
    poCtrl1.addCustomToolButton("打开", "doOpen()", 13);
    poCtrl1.addCustomToolButton("-", "", 2);
    poCtrl1.addCustomToolButton("另存为", "doSaveAs()", 11);
    poCtrl1.addCustomToolButton("-", "", 2);
    poCtrl1.addCustomToolButton("全屏/还原", "doSetFullScreen()", 4);

    poCtrl1.webOpen(filePath, OpenModeType.docNormalEdit, "");
    poCtrl1.setTagId("PageOfficeCtrl1");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

    <script type="text/javascript">
        var belongPanelId = '<%=belongPanelId%>';
        var parentExt = window.parent.Ext;
        if (parentExt && !parentExt.isEmpty(belongPanelId)) {
            var belongPanel = parentExt.getCmp(belongPanelId);
            //bind son window
            belongPanel.sonWindow = window;
        }
        //保存文件
        var doSave = function (reportName) {
            var reportName = encodeURIComponent(encodeURIComponent(reportName));
            document.getElementById('PageOfficeCtrl1').SaveFilePage = 'docTemplateSave.jsp?reportName=' + reportName;
            document.getElementById('PageOfficeCtrl1').WebSave();
        };

        //打开文件
        function doOpen() {
            document.getElementById('PageOfficeCtrl1').ShowDialog(1);
        }

        //另存文件
        function doSaveAs() {
            document.getElementById('PageOfficeCtrl1').ShowDialog(3);
        }

        //全屏/还原
        function doSetFullScreen() {
            document.getElementById('PageOfficeCtrl1').FullScreen = !document.getElementById('PageOfficeCtrl1').FullScreen;
        }
        /**
         * 获取所有标签
         */
        function doGetAllBookMark() {
            var bookName = '';
            var poCtrlDoc = document.getElementById("PageOfficeCtrl1").Document;
            for (var index = 1; index <= poCtrlDoc.BookMarks.Count; index++) {
                var bookname = poCtrlDoc.BookMarks(index).Name;
                bookName += bookname + ',';
            }
            bookName = '' == bookName ? '' : bookName.substring(0, bookName.length - 1);
            return bookName;
        }

        function init() {
            var obj = document.getElementById('PageOfficeCtrl1');
            obj.style.zIndex = -1;
        }

        function insertBookMark(bookMarks) {
            for (var bkName in bookMarks) {
                var bkText = bookMarks[bkName];
                var mac = "Function myfunc()" + " \r\n"
                        + "Dim r As Range " + " \r\n"
                        + "Set r = Application.Selection.Range " + " \r\n"
                        + "r.Text = \"" + bkText + "\"" + " \r\n"
                        + "Application.ActiveDocument.Bookmarks.Add Name:=\"PO_" + bkName + "\", Range:=r " + " \r\n"
//                        + "Application.ActiveDocument.Bookmarks(\"PO_" + bkName + "\").Select " + " \r\n"
                        + "End Function " + " \r\n";
                document.getElementById("PageOfficeCtrl1").RunMacro("myfunc", mac);

            }
        }

    </script>
</head>

<body onload="init()">
<div>
    <po:PageOfficeCtrl id="PageOfficeCtrl1"/>
</div>
</body>
</html>
