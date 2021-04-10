<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
    String contextPath = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + contextPath + "/";

%>
<!DOCTYPE html>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html locale="true">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta http-equiv="Access-Control-Allow-Origin" content="*"/>
    <script type="text/javascript" src="app/javascript/lib/jquery/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="app/javascript/lib/layer/layer.js"></script>
    <script type="text/javascript" src="app/javascript/lib/layui-2.5.5/src/layui.js"></script>
    <link rel="stylesheet" href="app/javascript/lib/layui-2.5.5/src/css/layui.css"  media="all">
    <title>消息通知</title>
    <script>
        setInterval(getNotify,10000)
        function getNotify() {
            $.ajax({
                url:"<%=contextPath%>/informMgr/notice.rdm",
                dataType:"json", //返回格式为json
                async:true,
                type:"GET",
                success:function(req){
                    var result = req.results;
                    var $ol = $(".layui-timeline");
                    $ol.empty()
                    $.each(result, function (n, value) {
                        var $li  = $("<li></li>")
                        $li.addClass("layui-timeline-item")
                        $i=$("<i class=\"layui-icon layui-timeline-axis\"></i>")
                        $div1=$("<div></div>").addClass("layui-timeline-content layui-text")
                        $div2=$("<div></div>").addClass("layui-timeline-title")
                        var $a = $("<a></a>")
                        if(value.C_TYPE_3566=="check"){   //检查表
                            $a.attr("href","CurrentTaskMgr/getCheckTableCaseHtml.rdm?instanceId="+value.C_TABLE_ID_3566)
                        }
                        if(value.C_TYPE_3566=="plan"){   //计划表
                            $a.attr("href","accountingForm/getDivingPlanTableData.rdm?taskId="+value.C_TASK_ID_3566)
                        }
                        if(value.C_TYPE_3566=="balance"){   //balance
                            $a.attr("href","accountingForm/getOutTemplateTable.rdm?taskId="+value.C_TASK_ID_3566+"&taskName="+value.C_TABLE_NAME_3566)
                        }
                        if(value.C_TYPE_3566=="preview"){   //balance
                            $a.attr("href","accountingForm/getScientistPicturePreviewFile.rdm?divingPlanId="+value.C_TABLE_ID_3566)
                        }
                        $a.text(value.C_UPLOAD_TIME_3566+",  "+value.C_UPLOAD_PERSON_3566+value.C_STATE_3566+" - "+value.C_TABLE_NAME_3566+"   ")
                        $div2.append($a)
                        $div1.append($div2)
                        $li.append($i)
                        $li.append($div1)
                        $ol.append($li)
                    })
                    $("#content").append($ol)
                    $("a").click(function(){
                        url = $(this).attr(("href"));
                          layer.ready(function(){
                          var index =  layer.open({
                                type: 2,
                                title: '数据页',
                                maxmin: true,
                                content: url,
                                end: function(){
                                    layer.tips('Hi', '#about', {tips: 1})
                                }
                            });
                            layer.full(index);
                        });
                        return false;
                    })
                },
                complete:function(){

                },
                error:function(){
                    alert("error！")
                }
            });
          }
    </script>


    <style>
        .xue{
            background-image: url("app/images/background/5-dots.png");
            background-repeat: repeat;
        }
    </style>
        </head>
<body id="mbody" onload="getNotify()" class="xue" style="text-align: center">

<div class="layui-container">
    <div class="layui-col-xs6 layui-col-sm6 layui-col-md4">
        <%-- <div  style="width: 500px; margin: 0 auto">--%>
        <h1>记录查看</h1>
        <ul class="layui-timeline">
        </ul>
        <%-- </div>--%>
    </div>
</div>

</body>
</html>