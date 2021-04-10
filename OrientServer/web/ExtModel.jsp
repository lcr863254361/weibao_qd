<%--
  Created by IntelliJ IDEA.
  User: enjoy
  Date: 2016/3/9 0009
  Time: 17:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/app/views/include/ExtEnvironment.jsp" %>
<%

%>
<html>
<head>
    <title>全寿命运维、测试和支撑保障系统</title>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/app/javascript/lib/awesome/css/font-awesome.min.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/app/javascript/lib/buttons/css/buttons.css"/>
    <script type="text/javascript" src="${ctx}/app/javascript/orientjs/extjs/portal.js"></script>


</head>

<script type="text/javascript">
    mxBasePath = serviceName + '/app/javascript/lib/mxgraph/src';
    //全局最大寬度 高度
    var globalWidth = Ext.getBody().getViewSize().width;
    var globalHeight = Ext.getBody().getViewSize().height;
    //首页消息和任务展示条数
    var msgPageCnt = 25;

    Ext.onReady(function () {
        Ext.tip.QuickTipManager.init();
        Ext.create('OrientTdm.Portal');
    });

    //按钮矢量图标动画控制
    function _btn_onmouseout(me) {
        //停止ICON转动
        Ext.get(me).down('i').removeCls('fa-spin');
    }

    //按钮矢量图标动画控制
    function _btn_onmouseover(me) {
        //ICON转动
        Ext.get(me).down('i').addCls('fa-spin');
    }

    //显示系统时钟
    function showTime() {
        Ext.get('rTime').update(Ext.Date.format(new Date(), 'H:i:s'));
    }

    Ext.EventManager.on(window, 'load', function () {
        OrientExtUtil.Common.job(function () {
            Ext.get('loading').fadeOut({
                duration: 1000, //遮罩渐渐消失
                remove: true
            });
            Ext.get('loading-mask').fadeOut({
                duration: 1000,
                remove: true
            });
        }, 1000); //做这个延时，只是为在Dom加载很快的时候GIF动画效果更稍微显著一点

    });

    window.onload = function () {
        showTime();
        OrientExtUtil.Common.task(showTime, 1000);

    };

    function showUserMsgs() {
        var msgWin = Ext.create('OrientTdm.HomePage.Msg.MsgWindow', {});
        msgWin.show();
    }

    function doSearch() {
        OrientExtUtil.WindowHelper.createWindow({
            xtype: 'searchDashbord'
        }, {
            title: '综合搜索'
        }, globalHeight * 0.8, globalWidth * 0.8);
    }

</script>
<body>
<%-- 显示loading --%>
<div id="loading-mask"></div>
<div id="loading">
    <img width="100px" height="100px" src="app/images/gif/pageload/orient_loading.gif">
</div>
<div id="_id_north_el" class="x-hidden north_el">
    <table width="100%">
        <tr>
            <td width="420px"><img src="app/images/logo/left-logo.png"></td>
            <td align="left">
                <div class="button-group">
                </div>
            </td>
            <td align="right" style="padding: 5px;" width="600px">
                <table style="border-spacing: 1px;">
                    <tr>
                        <td colspan="4" class="main_text">
                            <nobr>
                                <i class="fa fa-rss"></i> ${date} ${week} <span id="rTime"></span>
                            </nobr>
                        </td>
                    <tr>
                    <tr align="right">
                        <td>
                            <font size="1"> 欢迎您, ${userAllName}.
                                所属部门:${deptName}</font></td>
                    </tr>
                    <tr align="right">
                        <td>
                            <a class="button-small button-pill" href="#" onmouseout="_btn_onmouseout(this)"
                               onclick="showUserMsgs()" onmouseover="_btn_onmouseover(this);">
                                <i class="fa fa-comment-o"></i> 消息
                                <span style="color: red" id="msgCnt">(${msgCnt})</span>
                            </a>
                            <a class="button-small button-pill" href="#" onmouseout="_btn_onmouseout(this)"
                               onclick="doSearch()" onmouseover="_btn_onmouseover(this);">
                                <i class="fa fa-search"></i> 搜索
                            </a>
                            <a class="button-small button-pill" href="${ctx}/doLogout.rdm"
                               onmouseout="_btn_onmouseout(this)" onmouseover="_btn_onmouseover(this);">
                                <i class="fa fa-power-off"></i> 退出
                            </a>
                        </td>
                    <tr>
                </table>
            </td>
        </tr>
    </table>
</div>

<div id="baseDivid">
    <iframe scrolling=auto id='hiddenPanelframe' frameborder=0 height="0" width="0"></iframe>
</div>

<link rel="stylesheet" type="text/css" href="${ctx}/app/javascript/lib/codemirror/lib/codemirror.css"/>
<link rel="stylesheet" type="text/css"
      href="${ctx}/app/javascript/orientjs/extjs/FlowCommon/flowDiagram/assets/css/mxgraph.css"/>
<link href="${ctx}/app/javascript/lib/ext-gantt/resources/css/sch-gantt-all.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/app/javascript/lib/ext-gantt/resources/css/custom.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/app/javascript/lib/jquery/css/jquery.qtip.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/app/styles/other/css/MultipleComboBox/MultipleComboBox.css">
<link rel="stylesheet" type="text/css" href="${ctx}/app/javascript/lib/ext-4.2/examples/view/data-view.css">

<%--<script type="text/javascript" src="${ctx}/app/javascript/lib/plotly-1.7.0/dist/plotly.js"></script>--%>
<script src="${ctx}/app/javascript/lib/ext-gantt/gnt-all-debug.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/app/javascript/lib/mxgraph/src/debug/mxClient.js"></script>
<script type="text/javascript" src="${ctx}/app/javascript/lib/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}/app/javascript/lib/jquery/jquery.qtip.js"></script>
<script type="text/javascript" src="${ctx}/app/javascript/lib/buttons/js/buttons.js"></script>
<%--二维码--%>
<script type="text/javascript" src="${ctx}/app/javascript/lib/qrcode/jquery-qrcode.min.js"></script>
<script type="text/javascript" src="${ctx}/app/javascript/lib/qrcode/jquery.jqprint-0.3.js"></script>
<script type="text/javascript" src="${ctx}/app/javascript/lib/qrcode/html2canvas.js"></script>
<script type="text/javascript" src="${ctx}/app/javascript/lib/qrcode/qrcode.min.js"></script>

<%--<script type="text/javascript" src="${ctx}/app/javascript/lib/qrcode/jquery-3.4.1.min.js"></script>--%>
<%--富文本框--%>
<script type="text/javascript" src="${ctx}/app/javascript/lib/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="${ctx}/app/javascript/lib/codemirror/lib/codemirror.js"></script>
<script type="text/javascript" src="${ctx}/app/javascript/lib/codemirror/mode/sql/sql.js"></script>
<script type="text/javascript" src="${ctx}/app/javascript/lib/codemirror/mode/javascript/javascript.js"></script>
<%--绘图--%>
<%--<script type="text/javascript" src="${ctx}/app/javascript/lib/echart/echarts.min.js"></script>--%>
<script type="text/javascript" src="${ctx}/app/javascript/lib/echart-4.1/echarts.js"></script>
<script type="text/javascript" src="${ctx}/app/javascript/lib/echart-4.1/echarts-gl.js"></script>

<script>
    $(window).bind("beforeunload", function () {
        //修复登陆后关闭浏览器tab 重新打开新的tab 输入http://localhost:8080/OrientTDM/ 无法访问的bug
        //OrientExtUtil.AjaxHelper.doRequest(serviceName + '/doLogout.rdm', {}, true);
    });
</script>
</body>
</html>
