<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 2020/2/11
  Time: 17:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
    String contextPath = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + contextPath + "/";
%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">
    var __ctx = '<%=request.getContextPath()%>';
    var hangduanId = '<%=request.getParameter("hangduanId")%>';
    var taskId = '<%=request.getParameter("taskId")%>';
    var taskName = '<%=request.getParameter("taskName")%>';
</script>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <script type="text/javascript" src="${ctx}/app/javascript/lib/jquery/jquery-1.7.2.min.js"></script>
    <link rel="stylesheet" href="${ctx}/app/javascript/lib/layui-2.5.5/src/css/layui.css">
    <script type="text/javascript"
            src="${ctx}/app/javascript/orientjs/extjs/TaskPrepareMgr/Accounting/js/FileSaver.js"></script>
    <script type="text/javascript"
            src="${ctx}/app/javascript/orientjs/extjs/TaskPrepareMgr/Accounting/js/jquery.wordexport.js"></script>

    <style>
        td {
            height: 25px;
            width: auto;
        }

        body {
            text-align: center;
        }
    </style>
</head>
<body>
<div id="divingReportWord" width="80%">
    <form action="" method="post" name="divingReportTable">
        <table width="50%" height="" border="1" align="center" cellspacing=0 cellpadding=0 table-layout：fixed>
            <tr>
                <td align="center" valign="middle" colspan="4">深海勇士号潜次报告</td>
            </tr>
            <tr>
                <td align="center" valign="middle">潜次</td>
                <td align="center" valign="middle">${taskName}</td>
                <td align="center" valign="middle">日期</td>
                <td align="center" valign="middle" id="date" name="date">${date}</td>
            </tr>
            <tr>
                <td align="center" valign="middle">布放时经度/°</td>
                <td align="center" valign="middle" name="jingdu">${jingdu}</td>
                <td align="center" valign="middle">布放时纬度/°</td>
                <td align="center" valign="middle" name="weidu">${weidu}</td>
            </tr>
            <tr>
                <td align="center" valign="middle">全程工作时长</td>
                <td align="center" valign="middle" name="totalWorkHours">${totalWorkHours}</td>
                <td align="center" valign="middle">下潜最大深度(M)</td>
                <td align="center" valign="middle" name="divingMaxDepth">${divingMaxDepth}</td>
            </tr>
            <tr>
                <td align="center" valign="middle">水中时长</td>
                <td align="center" valign="middle" name="waterHours">${waterHours}</td>
                <td align="center" valign="middle">水下作业时长</td>
                <td align="center" valign="middle" name="underWaterHours">${underWaterHours}</td>
            </tr>
            <tr>
                <td align="center" valign="middle">平均下潜速度</td>
                <td align="center" valign="middle" name="averageDivingSpeed">${averageDivingSpeed}</td>
                <td align="center" valign="middle">平均上浮速度</td>
                <td align="center" valign="middle" name="averageFloatSpeed">${averageFloatSpeed}</td>
            </tr>
            <tr>
                <td colspan="4" align="center" valign="middle">下潜人员</td>
            </tr>
            <tr>

                <td colspan="4" style="border: none" style="width: 100%;padding: 0">
                    <table id="divingPersons" height="" border="1" align="center" frame="void" width="100% "
                           cellspacing=0
                           cellpadding=0>
                        <tr>
                            <td align="center" valign="middle">左驾驶</td>
                            <td align="center" valign="middle">主驾驶</td>
                            <td align="center" valign="middle">右驾驶(科学家)</td>
                        </tr>
                        <tr height="20">
                            <td id="leftDriver" name="leftDriver">${leftDriver}</td>
                            <td id="mainDriver" name="mainDriver">${mainDriver}</td>
                            <td id="rightDriver" name="rightDriver">${rightDriver}</td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <%--把表格的sytle的table-layout：fixed，就是表格固定宽度，就是表格既要自适应他外面的容器，也不要撑出去，然后设置td的word-wrap：break-word；换行，问题就解决了--%>
                <td align="center" valign="middle">作业工具</td>
                <td colspan="3" style="width:100px;height:100px;word-wrap:break-word;" name="homeWorkTools">
                    <%--<textarea name="homeWorkTools"--%>
                    <%--?/class="textinput" style="overflow-y: scroll" cols="102" rows="7"--%>
                    <%--style="width:100%;height:100%;border:none;" readonly>${homeWorkTools}</textarea>--%>
                    ${homeWorkTools}</td>
            </tr>
            <tr>
                <td align="center" valign="middle">均衡情况</td>
                <%--添加 contentEditable="true"属性 在需要被设为可编辑的单元格内设置该属性即可。--%>
                <td colspan="3" style="width:100px;height:100px;word-wrap:break-word;" name="divingProgress"
                    id="divingProgress" contentEditable="true">
                    <%--<textarea name="divingProgress" id="divingProgress"--%>
                    <%--class="textinput" style="overflow-y: scroll" cols="102" rows="7"--%>
                    <%--style="width:100%;height:100%;border:none;">${divingProgress}</textarea>--%>
                    ${divingProgress}</td>
            </tr>
            <tr>
                <td align="center" valign="middle">采样情况</td>
                <td colspan="3" style="width:100px;height:100px;word-wrap:break-word;" name="sampleSituation">
                    <%--<textarea name="sampleSituation"--%>
                    <%--class="textinput" style="overflow-y: scroll" cols="102" rows="7"--%>
                    <%--style="width:100%;height:100%;border:none;"></textarea>--%>
                    ${sampleSituation}</td>
            </tr>
            <tr>
                <td align="center" valign="middle">水下作业环境描述
                </td>
                <td colspan="3" style="width:100px;height:100px;word-wrap:break-word;" name="waterDEnvironmentDesp"
                    id="waterDEnvironmentDesp" contentEditable="true">
                    <%--<textarea name="sampleSituation"--%>
                    <%--class="textinput" style="overflow-y: scroll" cols="102" rows="7"--%>
                    <%--style="width:100%;height:100%;border:none;"></textarea>--%>
                    ${waterDEnvironmentDesp}</td>
            </tr>
            <tr>
                <td align="center" valign="middle">潜水器状态及处理情况</td>
                <td colspan="3" style="width:100px;height:100px;word-wrap:break-word;" name="troubleHandle"
                    id="troubleHandle" contentEditable="true">
                    <%--<textarea name="troubleHandle" id="troubleHandle"--%>
                    <%--class="textinput" style="overflow-y: scroll" cols="102" rows="7"--%>
                    <%--style="width:100%;height:100%;border:none;">${troubleHandle}</textarea>--%>
                    ${troubleHandle}</td>
            </tr>
            <tr>
                <td align="center" valign="middle">其他</td>
                <td colspan="3" style="width:100px;height:100px;word-wrap:break-word;" name="summary" id="summary"
                    contentEditable="true">
                    <%--<textarea name="summary" id="summary"--%>
                    <%--class="textinput" style="overflow-y: scroll" cols="102" rows="7"--%>
                    <%--style="width:100%;height:100%;border:none;">${summary}</textarea>--%>
                    ${summary}</td>
            </tr>
        </table>
        <p style=" margin:0 auto; text-align:center;"><input type="button" id="reportSubmit" value="保存"
                                                             style="color: black;font-family: bold;font-size:large"
                                                             class="layui-btn layui-btn-primary"/>
            <input type="button" id="exportDivingReport" value="导出"   style="color: black;font-family: bold;font-size:large"
                   class="layui-btn layui-btn-primary"></p>

    </form>
</div>
<script src="${ctx}/app/javascript/lib/layui-2.5.5/src/layui.js" charset="utf-8"></script>
<script>
    $(function () {
        $('#exportDivingReport').click(function (event) {

            // var rules = "table{width=\"100%\" height=\"\" border=\"1\" align=\"center\" cellspacing=0 cellpadding=0}table td{ height: 25px;\n" +
            //     "                    width: 120px;}",
            var rules = "body{color:#000;font-size:20px;}h1{text-align:center;}.sp {display: block;width: 30%; float:left;}.tit {display: block;margin: 20px 34% 0px;}";
            ss = document.styleSheets;
            for (var i = 0; i < ss.length; ++i) {
                for (var x = 0; x < ss[i].cssRules.length; ++x) {
                    rules += ss[i].cssRules[x].cssText;
                }
            }
            $("#divingReportWord").wordExport('深海勇士号潜次报告', rules);
        });
    });
    $(document).ready(function () {
        $("#reportSubmit").click(function () {
            var divingProgress = $("#divingProgress").text();
            var summary = $("#summary").text();
            var troubleHandle = $("#troubleHandle").text();
            var waterDEnvironmentDesp=$("#waterDEnvironmentDesp").text();
            var divingReportTableBean = {
                divingProgress: divingProgress,
                troubleHandle: troubleHandle,
                summary: summary,
                taskId: taskId,
                taskName: taskName,
                waterDEnvironmentDesp:waterDEnvironmentDesp
            };
            $.ajax({
                url: __ctx + '/accountingForm/submitDivingReportTable.rdm',
                type: "post",
                async: false,
                data: divingReportTableBean,
                success: function (data) {
                    //刷新当前页面
                    document.location.reload();
                }
            });
        });
    });
</script>
</body>
</html>

