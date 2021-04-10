<%--
  Created by IntelliJ IDEA.
  User: 李彩荣
  Date: 2020/12/30
  Time: 14:44
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
    width=device-width,initial-scale=1.0"/>
    <link rel="stylesheet" href="${ctx}/app/javascript/lib/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="${ctx}/app/javascript/lib/element/index.css">
    <script type="text/javascript" src="${ctx}/app/javascript/lib/jquery/jquery.js"></script>
    <script type="text/javascript" src="${ctx}/app/javascript/lib/vue/vue.js"></script>
    <script type="text/javascript" src="${ctx}/app/javascript/lib/element/index.js"></script>

    <style>
        [v-cloak] {
            display: none;
        }

        .el-timeline {
            padding-left: 10px;
        }

    </style>
</head>
<body>


<div id="app" v-cloak class="container mt-3 col-xs-1">
    <el-card>
        <el-collapse accordion>
            <el-collapse-item v-for="item,index in data" :key="index">
                <template slot="title">
                    <span class="mr-2">{{'任务: '+item.cTaskName3208}}</span>
                    <el-tag
                            closable
                            class="mr-1"
                            size="mini" :closable="false"
                            type="primary">
                        {{item.informLogList.length}}
                    </el-tag>
                    <el-tag
                            closable
                            size="mini" :closable="false"
                            :type="getType(item.cState3208)">
                        {{item.cState3208}}
                    </el-tag>
                </template>
                <el-timeline v-if="item.informLogList.length>0">
                    <el-timeline-item :timestamp="line.cUploadTime3566" placement="top" type="primary" class="p-0 m-0"
                                      v-for="line,index1 in item.informLogList" :key="index1">
                        <el-card class="p-0" v-bind:style="isException(line.cIsException3566)">
                            <p class="mb-1">{{line.cState3566}} {{line.cTableName3566}}</p>
                            <p class="mb-1">{{line.cUploadPerson3566}}</p>
                            <el-button target="_blank" size="small" type="primary" @click="showDetail(line)">查看详情
                            </el-button>
                        </el-card>

                    </el-timeline-item>
                </el-timeline>
                <p v-else>该任务暂无通知</p>
            </el-collapse-item>
        </el-collapse>
    </el-card>

</div>

</body>

<script>

    var __ctx = '<%=request.getContextPath()%>';

    new Vue({
        el: '#app',
        data: {
            data: []
        },
        methods: {
            getType(status) {
                let type = 'info';
                if (status === '进行中')
                    type = 'warning'
                if (status === '已结束')
                    type = 'success'
                return type
            },
            isException(state) {
                if (state === '异常') {
                    return {
                        background: "#c3272a"
                    }
                }
            },
            start() {
                setInterval(this.getData, 10000)
            },
            showDetail(item) {
                // this.dialogVisible=true;
                // this.selectHref=item.href;
                window.location.href = item.href;
            },
            getData() {
                $.ajax({
                    url: "<%=contextPath%>/informMgr/currentHangduanNotice.rdm?isOnlyShowPlan=true",
                    dataType: "json", //返回格式为json
                    async: true,
                    type: "GET",
                    success: (data) => {
                        this.data = data.results;
                        this.data.forEach((item1, index1) => {
                            item1.informLogList.forEach((item, index) => {
                                if (item.cType3566 == "plan") {   //计划表
                                    this.data[index1].informLogList[index].href = "accountingForm/getDivingPlanTableData.rdm?isPhone=true&taskId=" + item.cTaskId3566 + '&isScientist=' + false + '&divingPlanId=' + item.cTableId3566;
                                }
                            })

                        })
                        console.log(this.data)
                    }
                });
            },
            // 自定义表头列宽
            flexColumnWidth(str) {
                let flexWidth = 0
                for (const char of str) {
                    if ((char >= 'A' && char <= 'Z') || (char >= 'a' && char <= 'z')) {
                        // 如果是英文字符，为字符分配8个单位宽度
                        flexWidth += 8
                    } else if (char >= '\u4e00' && char <= '\u9fa5') {
                        // 如果是中文字符，为字符分配20个单位宽度
                        flexWidth += 20
                    } else {
                        // 其他种类字符，为字符分配5个单位宽度
                        flexWidth += 5
                    }
                }
                if (flexWidth < 50) {
                    // 设置最小宽度
                    flexWidth = 70
                }
                if (flexWidth > 250) {
                    // 设置最大宽度
                    flexWidth = 300
                }
                return flexWidth + 'px'
            }
        },
        created() {
            this.getData();
            this.start();
        }
    })

</script>
</html>
