<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
    String contextPath = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + contextPath + "/";
%>
<!DOCTYPE html>
<html locale="true">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta http-equiv="Access-Control-Allow-Origin" content="*"/>

    <title>消息通知</title>
    <style type="text/css">

        #title {
            background: #FFF padding : 10 px 20 px;
            font-size: 30px;
            font-family: Verdana;
            color: #000000;
        }

        #name {
            background: #FFF padding : 10 px 20 px;
            font-size: 26px;
            font-family: Verdana;
            color: #5b7da3;
        }

        thead, tr {
            border-top-width: 1px;
            border-top-style: solid;
            border-top-color: #a8bfde;
        }

        {
            border-bottom-width: 1px
        ;
            border-bottom-style: solid
        ;
            border-bottom-color: #a8bfde
        ;
        }
        /* Padding and font style */
        td, th {
            padding: 5px 10px;
            font-size: 18px;
            font-family: Verdana;
            color: #5b7da3;
        }

        /* Alternating background colors */
        tr:nth-child(even) {
            background: #d3dfed
        }

        tr:nth-child(odd) {
            background: #FFF
        }

        table {
            margin-top: 20px;
            margin-bottom: 20px;
            align-self: center;
            -moz-box-shadow: 2px 2px 5px #333333;
            -webkit-box-shadow: 2px 2px 5px #333333;
            box-shadow: 0px 0px 16px #e1e1e1;
        }
    </style>
    <script>
        function getNotify() {
            var url = '<%=contextPath%>/api/download/getInform.rdm';
            var xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function () {

                if (xhr.readyState === 4) {
                    if (xhr.status === 200) {
                        return success(xhr.responseText);
                    } else {
                        alert(xhr.status)
                    }
                }
            }
            xhr.open('GET', url);
            xhr.send();
        }

        function success(data) {
            var tablenode = document.getElementById('content');
            var infos = JSON.parse(data);
            var width = document.body.clientWidth;

            if (infos.code == 200) {
                var result = infos.result;
                if (result == null)
                    return;

                var stausName = infos.result.name;
                var status = document.getElementById('name');
                status.innerHTML = "当前状态：" + stausName;

                var notifies = infos.result.informBeanList;

                for (var i = 0; i < notifies.length; i++) {

                    var table = document.createElement('table');
                    table.setAttribute("wdith", "100%");
                    table.style.width = width + "px";


                    var row = document.createElement('tr'); //创建行
                    row.setAttribute("width", "100%");
                    var titleTd = document.createElement('td'); //创建第一列id
                    titleTd.setAttribute("width", "30%");
                    titleTd.innerHTML = notifies[i].department
                    row.appendChild(titleTd); //加入行  ，下面类似

                    var authorTd = document.createElement('td');//创建第二列name
                    authorTd.innerHTML = notifies[i].date;
                    authorTd.setAttribute("width", "50%");
                    row.appendChild(authorTd);


                    var rowContent = document.createElement('tr'); //创建行
                    var contentTd = document.createElement('td');//创建第三列job
                    rowContent.setAttribute("width", "100%");
                    contentTd.setAttribute("width", "100%");
                    contentTd.setAttribute("colspan", 2);
                    contentTd.innerHTML = notifies[i].informContent;
                    rowContent.appendChild(contentTd);


                    console.log(row.innerHTML);
                    //将信息追加
                    //tablenode.appendChild(row);
                    //tablenode.appendChild(rowContent);

                    table.appendChild(row)
                    table.appendChild(rowContent)


                    tablenode.appendChild(table)
                }
            } else {
                tablenode.innerHTML = infos.msg
            }
        }
    </script>
</head>
<body id="mbody" onload="getNotify()">
<p align="center" id="title"><b>通知列表</b></p>
<!-- <button type="submit" onclick="getWeather()">查询</button> -->
<!-- <p id="infos"></p> -->

<p align="center" id="name">类别</p>

<div id="content">

</div>
</body>
</html>