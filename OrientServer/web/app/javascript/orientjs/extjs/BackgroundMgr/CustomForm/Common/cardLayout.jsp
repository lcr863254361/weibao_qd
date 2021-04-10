<%--
  Created by TeddyJohnson.
  User: ZhangSheng
  Date: 2018/8/27 15:00
  Time: 16:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">
    var __ctx = '<%=request.getContextPath()%>';
    var __jsessionId = '<%=session.getId() %>';
    //获取知识推荐的显示卡片个数
    var knowledgeCnt = '<%=request.getParameter("knowledgeCnt")%>';

    //$("#row-fluid").appendTo(childdiv);
    //$("#formHtml").val(html);

</script>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Bootstrap Template</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap -->
    <link href="${ctx}/app/javascript/lib/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <!--jquery.js 一定要放在bootstrap.min.js之前，如果不放报错-->
    <script type="text/javascript" src="${ctx}/app/javascript/lib/jquery/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="${ctx}/app/javascript/lib/bootstrap/js/bootstrap.min.js"></script>
</head>
<script type="text/javascript">
    $(function () {
        var parentdiv = $("#knContent");
        var childdiv;
        if (knowledgeCnt == 6) {
            childdiv = '<div class="col-xs-6 span2">'
                    + '    <div class="thumbnail">'
                    + '        <div class="media">'
                    + '            <a class="pull-left" href="#">'
                    + '                <img class="img-circle" alt="140x140" src="${ctx}/app/javascript/lib/bootstrap/img/head.jpg"/>'
                    + '            </a>'
                    + '            <div class="media-body">'
                    + '                <h4 class="media-heading">总体设计规范 </h4>'
                    + '                <p>潘端端 </p>'
                    + '            </div>'
                    + '        </div>'
                    + '        <img alt="300x200" src="${ctx}/app/javascript/lib/bootstrap/img/people.jpg"/>'
                    + '        <div class="caption">'
                    + '            <h3>总体设计规范指导意见 </h3>'
                    + '            <p> <small>2018-06-01</small> </p>'
                    + '            <p> <samp><cite>子曰：“学而时习之，不亦说乎？有朋自远方来，不亦乐乎？人不知，而不愠，不亦君子乎？”</cite></samp> </p>'
                    + '            <p> <a class="btn btn-primary" href="#" onclick="onlineKnowledgePreview()">在线预览</a> <a class="btn" href="#" onclick="knowledgeDownlaod()">下载</a> </p>'
                    + '        </div>'
                    + '    </div>'
                    + '</div>';
        }
        else if (knowledgeCnt == 4) {
            childdiv = '<div class="col-xs-6 span3">'
                    + '    <div class="thumbnail">'
                    + '        <div class="media">'
                    + '            <a class="pull-left" href="#">'
                    + '                <img class="img-circle" alt="140x140" src="${ctx}/app/javascript/lib/bootstrap/img/head.jpg"/>'
                    + '            </a>'
                    + '            <div class="media-body">'
                    + '                <h4 class="media-heading">总体设计规范 </h4>'
                    + '                <p>潘端端 </p>'
                    + '            </div>'
                    + '        </div>'
                    + '        <img alt="300x200" src="${ctx}/app/javascript/lib/bootstrap/img/city.jpg"/>'
                    + '        <div class="caption">'
                    + '            <h3>总体设计规范指导意见 </h3>'
                    + '            <p> <small>2018-06-01</small> </p>'
                    + '            <p> <samp><cite>子曰：“学而时习之，不亦说乎？有朋自远方来，不亦乐乎？人不知，而不愠，不亦君子乎？”</cite></samp> </p>'
                    + '            <p> <a class="btn btn-primary" href="#" onclick="onlineKnowledgePreview()">在线预览</a> <a class="btn" href="#" onclick="knowledgeDownlaod()">下载</a> </p>'
                    + '        </div>'
                    + '    </div>'
                    + '</div>';
        }
        else if (knowledgeCnt == 3) {
            childdiv = '<div class="col-xs-6 span4">'
                    + '    <div class="thumbnail">'
                    + '        <div class="media">'
                    + '            <a class="pull-left" href="#">'
                    + '                <img class="img-circle" alt="140x140" src="${ctx}/app/javascript/lib/bootstrap/img/head.jpg"/>'
                    + '            </a>'
                    + '            <div class="media-body">'
                    + '                <h4 class="media-heading">总体设计规范 </h4>'
                    + '                <p>潘端端 </p>'
                    + '            </div>'
                    + '        </div>'
                    + '        <img alt="300x200" src="${ctx}/app/javascript/lib/bootstrap/img/sports.jpg"/>'
                    + '        <div class="caption">'
                    + '            <h3>总体设计规范指导意见 </h3>'
                    + '            <p> <small>2018-06-01</small> </p>'
                    + '            <p> <samp><cite>子曰：“学而时习之，不亦说乎？有朋自远方来，不亦乐乎？人不知，而不愠，不亦君子乎？”</cite></samp> </p>'
                    + '            <p> <a class="btn btn-primary" href="#" onclick="onlineKnowledgePreview()">在线预览</a> <a class="btn" href="#" onclick="knowledgeDownlaod()">下载</a> </p>'
                    + '        </div>'
                    + '    </div>'
                    + '</div>';
        }
        if (childdiv) {
            for (var i = 0; i < knowledgeCnt; i++) {
                parentdiv.append(childdiv);
            }
        }
    });

    //在线预览按钮的点击事件
    function onlineKnowledgePreview() {
//        var aa = 1;
    }

    //下载按钮的点击事件
    function knowledgeDownlaod() {
//        var aa = 1;
    }

    //    $(document).ready(function(){
    //        $("#previewBtn").click(function(){
    //            var aa=0;
    //        });
    //    })
</script>
<%--设置bootstrap栅格在网页中垂直居中--%>
<body style="padding-top:5%;padding-bottom:5%">
<%--<style type="text/css">--%>
<%--.thumbnail{--%>
<%--position:relative;--%>
<%--top:50%;--%>
<%--transform:translateY(50%);--%>
<%--}--%>
<%--</style>--%>
<div class="container-fluid" id="LG">
    <div class="row-fluid" id="knContent">
        <%--<div class="col-xs-6 span2">--%>
        <%--<div class="thumbnail">--%>
        <%--<div class="media">--%>
        <%--<a class="pull-left" href="#"><img class="img-circle" alt="140x140"--%>
        <%--src="${ctx}/app/javascript/lib/bootstrap/img/head.jpg"/></a>--%>

        <%--<div class="media-body">--%>
        <%--<h4 class="media-heading">--%>
        <%--总体设计规范--%>
        <%--</h4>--%>

        <%--<p>--%>
        <%--潘端端--%>
        <%--</p>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--<img alt="300x200" src="${ctx}/app/javascript/lib/bootstrap/img/people.jpg"/>--%>

        <%--<div class="caption">--%>
        <%--<h3>--%>
        <%--总体设计规范指导意见--%>
        <%--</h3>--%>

        <%--<p>--%>
        <%--<small>2018-06-01</small>--%>
        <%--</p>--%>
        <%--<p>--%>
        <%--<samp><cite>子曰：“学而时习之，不亦说乎？有朋自远方来，不亦乐乎？人不知，而不愠，不亦君子乎？”</cite></samp>--%>
        <%--</p>--%>

        <%--<p>--%>
        <%--<a class="btn btn-primary" href="#">在线预览</a> <a class="btn" href="#">下载</a>--%>
        <%--</p>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--<div class="col-xs-6 span2">--%>
        <%--<div class="thumbnail">--%>
        <%--<div class="media">--%>
        <%--<a class="pull-left" href="#"><img class="img-circle" alt="140x140"--%>
        <%--src="${ctx}/app/javascript/lib/bootstrap/img/head.jpg"/></a>--%>

        <%--<div class="media-body">--%>
        <%--<h4 class="media-heading">--%>
        <%--总体设计规范--%>
        <%--</h4>--%>

        <%--<p>--%>
        <%--潘端端--%>
        <%--</p>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--<img alt="300x200" src="${ctx}/app/javascript/lib/bootstrap/img/city.jpg"/>--%>

        <%--<div class="caption">--%>
        <%--<h3>--%>
        <%--总体设计规范指导意见--%>
        <%--</h3>--%>

        <%--<p>--%>
        <%--<small>2018-06-01</small>--%>
        <%--</p>--%>
        <%--<p>--%>
        <%--<samp><cite>子曰：“学而时习之，不亦说乎？有朋自远方来，不亦乐乎？人不知，而不愠，不亦君子乎？”</cite></samp>--%>
        <%--</p>--%>

        <%--<p>--%>
        <%--<a class="btn btn-primary" href="#">在线预览</a> <a class="btn" href="#">下载</a>--%>
        <%--</p>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--<div class="col-xs-6 span2">--%>
        <%--<div class="thumbnail">--%>
        <%--<div class="media">--%>
        <%--<a class="pull-left" href="#"><img class="img-circle" alt="140x140"--%>
        <%--src="${ctx}/app/javascript/lib/bootstrap/img/head.jpg"/></a>--%>

        <%--<div class="media-body">--%>
        <%--<h4 class="media-heading">--%>
        <%--总体设计规范--%>
        <%--</h4>--%>

        <%--<p>--%>
        <%--潘端端--%>
        <%--</p>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--<img alt="300x200" src="${ctx}/app/javascript/lib/bootstrap/img/sports.jpg">--%>

        <%--<div class="caption">--%>
        <%--<h3>--%>
        <%--总体设计规范指导意见--%>
        <%--</h3>--%>

        <%--<p>--%>
        <%--<small>2018-06-01</small>--%>
        <%--</p>--%>
        <%--<p>--%>
        <%--<samp><cite>子曰：“学而时习之，不亦说乎？有朋自远方来，不亦乐乎？人不知，而不愠，不亦君子乎？”</cite></samp>--%>
        <%--</p>--%>

        <%--<p>--%>
        <%--<a class="btn btn-primary" href="#">在线预览</a> <a class="btn" href="#">下载</a>--%>
        <%--</p>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--<div class="col-xs-6 span2">--%>
        <%--<div class="thumbnail">--%>
        <%--<div class="media">--%>
        <%--<a class="pull-left" href="#"><img class="img-circle" alt="140x140"--%>
        <%--src="${ctx}/app/javascript/lib/bootstrap/img/head.jpg"/></a>--%>

        <%--<div class="media-body">--%>
        <%--<h4 class="media-heading">--%>
        <%--总体设计规范--%>
        <%--</h4>--%>

        <%--<p>--%>
        <%--潘端端--%>
        <%--</p>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--<img alt="300x200" src="${ctx}/app/javascript/lib/bootstrap/img/sports.jpg">--%>

        <%--<div class="caption">--%>
        <%--<h3>--%>
        <%--总体设计规范指导意见--%>
        <%--</h3>--%>

        <%--<p>--%>
        <%--<small>2018-06-01</small>--%>
        <%--</p>--%>
        <%--<p>--%>
        <%--<samp><cite>子曰：“学而时习之，不亦说乎？有朋自远方来，不亦乐乎？人不知，而不愠，不亦君子乎？”</cite></samp>--%>
        <%--</p>--%>

        <%--<p>--%>
        <%--<a class="btn btn-primary" href="#">在线预览</a> <a class="btn" href="#">下载</a>--%>
        <%--</p>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--<div class="col-xs-6 span2">--%>
        <%--<div class="thumbnail">--%>
        <%--<div class="media">--%>
        <%--<a class="pull-left" href="#"><img class="img-circle" alt="140x140"--%>
        <%--src="${ctx}/app/javascript/lib/bootstrap/img/head.jpg"/></a>--%>

        <%--<div class="media-body">--%>
        <%--<h4 class="media-heading">--%>
        <%--总体设计规范--%>
        <%--</h4>--%>

        <%--<p>--%>
        <%--潘端端--%>
        <%--</p>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--<img alt="300x200" src="${ctx}/app/javascript/lib/bootstrap/img/sports.jpg">--%>

        <%--<div class="caption">--%>
        <%--<h3>--%>
        <%--总体设计规范指导意见--%>
        <%--</h3>--%>

        <%--<p>--%>
        <%--<small>2018-06-01</small>--%>
        <%--</p>--%>
        <%--<p>--%>
        <%--<samp><cite>子曰：“学而时习之，不亦说乎？有朋自远方来，不亦乐乎？人不知，而不愠，不亦君子乎？”</cite></samp>--%>
        <%--</p>--%>

        <%--<p>--%>
        <%--<a class="btn btn-primary" href="#">在线预览</a> <a class="btn" href="#">下载</a>--%>
        <%--</p>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--<div class="col-xs-6 span2">--%>
        <%--<div class="thumbnail">--%>
        <%--<div class="media">--%>
        <%--<a class="pull-left" href="#"><img class="img-circle" alt="140x140"--%>
        <%--src="${ctx}/app/javascript/lib/bootstrap/img/head.jpg"/></a>--%>

        <%--<div class="media-body">--%>
        <%--<h4 class="media-heading">--%>
        <%--总体设计规范--%>
        <%--</h4>--%>

        <%--<p>--%>
        <%--潘端端--%>
        <%--</p>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--<img alt="300x200" src="${ctx}/app/javascript/lib/bootstrap/img/sports.jpg">--%>

        <%--<div class="caption">--%>
        <%--<h3>--%>
        <%--总体设计规范指导意见--%>
        <%--</h3>--%>

        <%--<p>--%>
        <%--<small>2018-06-01</small>--%>
        <%--</p>--%>
        <%--<p>--%>
        <%--<samp><cite>子曰：“学而时习之，不亦说乎？有朋自远方来，不亦乐乎？人不知，而不愠，不亦君子乎？”</cite></samp>--%>
        <%--</p>--%>

        <%--<p>--%>
        <%--<a class="btn btn-primary" href="#">在线预览</a> <a class="btn" href="#">下载</a>--%>
        <%--</p>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--</div>--%>
    </div>
</div>
</body>
</html>