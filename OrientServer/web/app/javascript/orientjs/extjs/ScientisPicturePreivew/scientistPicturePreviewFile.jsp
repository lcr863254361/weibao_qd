<%--
  Created by IntelliJ IDEA.
  User: SunHao
  Date: 2020/5/11
  Time: 16:17
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
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=no,
    width=device-width,initial-scale=1.0" />
    <link rel="stylesheet" href="${ctx}/app/javascript/lib/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="${ctx}/app/javascript/lib/element/index.css">
    <script type="text/javascript" src="${ctx}/app/javascript/lib/jquery/jquery.js"></script>
    <script type="text/javascript" src="${ctx}/app/javascript/lib/vue/vue.js"></script>
    <script type="text/javascript" src="${ctx}/app/javascript/lib/element/index.js"></script>
    <script type="text/javascript" src="${ctx}/app/javascript/lib/viewer/viewer.js"></script>
    <link rel="stylesheet" href="${ctx}/app/javascript/lib/viewer/viewer.css">

</head>
<body>


<div id="app" >
    <div id="images" v-if="filedId!='null' && filedId!='' && filedId!=null ">
        <img
             :src="'${ctx}/orientForm/download.rdm?fileId='+filedId"
        >
    </div>

    <p v-else>暂无图片，请先发布绘图</p>


</div>

</body>

<script>

    var __ctx = '<%=request.getContextPath()%>';
    var taskId = '<%=request.getParameter("taskId")%>';
    var fileId = '<%=request.getAttribute("fileId")%>';
    var divingPlanId = '<%=request.getParameter("divingPlanId")%>';

    new Vue({
        el:'#app',
        data:{
            filedId:''
        },
        mounted(){
            const viewer = new Viewer(document.getElementById('images'), {
                toggleOnDblclick:false
            });
        },
        created(){
            this.filedId=fileId;
        }
    })




</script>
</html>