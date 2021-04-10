<%--
  Created by IntelliJ IDEA.
  User: 李彩荣
  Date: 2021/2/27
  Time: 10:05
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
    <meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=no,
    width=device-width,initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <link rel="stylesheet" href="${ctx}/app/javascript/lib/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="${ctx}/app/javascript/lib/element/index.css">
    <link rel="stylesheet" href="${ctx}/app/javascript/lib/viewer/viewer.css">
    <script type="text/javascript" src="${ctx}/app/javascript/lib/jquery/jquery.js"></script>
    <script type="text/javascript" src="${ctx}/app/javascript/lib/vue/vue.js"></script>
    <script type="text/javascript" src="${ctx}/app/javascript/lib/element/index.js"></script>
    <script type="text/javascript" src="${ctx}/app/javascript/lib/viewer/viewer.js"></script>
    <style>
        .el-form-item__label {
            padding: 0;
        }

        [v-cloak] {
            display: none;
        }
    </style>
</head>
<body>

<div id="app" v-cloak class="container mt-3 mb-3 col-xs-1" width="auto" height="auto">

    <el-row>
        <el-form ref="form" label-width="80px" label-position="top">
            <el-form-item label="语音记录">
                <div width="48px" height="50px">
                    <audio src="../${voiceUrl}" controls width="48px" height="50px">
                    </audio>
                </div>
            </el-form-item>
            <el-form-item label="多媒体记录" v-if="srcList.length>0">
                <el-row id="images">
                    <img v-for="item,index in srcList" :src="item" alt="Picture 2"
                         style="width: 100px; height: 100px"/>
                </el-row>
            </el-form-item>
            <el-form-item label="工作内容">
                <el-input type="textarea" rows="3" value="${detailContent}" readOnly></el-input>
            </el-form-item>
        </el-form>
    </el-row>

</div>

</body>

<script>
    var __ctx = '<%=request.getContextPath()%>';
    var detailContent = '<%=request.getAttribute("detailContent")%>';
    var voiceUrl = '<%=request.getAttribute("voiceUrl")%>';
    new Vue({
        el: '#app',
        components: {},
        data: {
            srcList: [],
            voiceDetail: [],
            voiceUrl: '',
            detailContent: ''
        },
        mounted() {
            var imageUrls = ${imageUrls};
            if (imageUrls!=1) {
                imageUrls.forEach(item => {
                    this.srcList.push('../' + item)
                });
            }
        },
        created() {
            this.voiceUrl = voiceUrl;
            this.detailContent = detailContent;
        },
        updated() {
            if (this.srcList.length > 0) {
                const gallery2 = new Viewer(document.getElementById('images'), {
                    toggleOnDblclick: false,
                });
            }
        },
        method:{

        }
    })

</script>
</html>

