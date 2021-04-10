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
            <el-collapse-item v-bind:title="hangduanName+'-流动岗详情-'+flowPostPublishTime" v-if="hangduanName!=''">
                <el-collapse accordion>
                    <el-table style="width: 100%" border :data="flowPostData" style="width: 100%"
                              height="400">
                        <template v-for="(item,index) in flowPostHeader">
                            <el-table-column :prop="item.column_name" :label="item.column_comment" :key="index"
                                             :width="flexColumnWidth(item.column_comment)"
                                             v-if="item.column_name != 'id'"
                                             :fixed="item.column_comment==='潜次名称'?true:false"></el-table-column>
                        </template>
                    </el-table>
                </el-collapse>
            </el-collapse-item>
            <p v-else>该航段暂未发布流动岗</p>

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
            data: [],
            flowPostData: [],
            flowPostPublishTime: '',
            hangduanName: '',
            flowPostHeader: []
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
                    url: "<%=contextPath%>/informMgr/currentHangduanNotice.rdm?isOnlyShowPlan=false",
                    dataType: "json", //返回格式为json
                    async: true,
                    type: "GET",
                    success: (data) => {
                        this.data = data.results;
                        this.data.forEach((item1, index1) => {
                            item1.informLogList.forEach((item, index) => {
                                if (item.cType3566 == "check") {   //检查表
                                    this.data[index1].informLogList[index].href = "CurrentTaskMgr/getCheckTableCaseHtml.rdm?instanceId=" + item.cTableId3566 + "&isShowByInform=" + true;
                                }
                                if (item.cType3566 == "plan" || item.cType3566 == "scientistPlan") {   //计划表
                                    let isScientist = (item.cType3566 === 'scientistPlan' ? true : false)
                                    this.data[index1].informLogList[index].href = "accountingForm/getDivingPlanTableData.rdm?isPhone=true&taskId=" + item.cTaskId3566 + '&isScientist=' + isScientist + '&divingPlanId=' + item.cTableId3566;
                                }
                                if (item.cType3566 == "balance") {   //balance
                                    this.data[index1].informLogList[index].href = "accountingForm/getOutTemplateTable.rdm?isPhone=true&taskId=" + item.cTaskId3566 + "&taskName=" + item.cTaskName3566 + "&peizhongId=" + item.cTableId3566 + "&isCanEdit=" + false;
                                }
                                if (item.cType3566 == "preview") {
                                    this.data[index1].informLogList[index].href = "accountingForm/getScientistPicturePreviewFile.rdm?divingPlanId=" + item.cTableId3566;
                                }
                            })

                        })
                        console.log(this.data)
                    }
                });

                $.ajax({
                    url: "<%=contextPath%>/informMgr/getCurrentHangduanFlowPost.rdm",
                    dataType: "json", //返回格式为json
                    async: true,
                    type: "GET",
                    success: (data) => {
                        //使用ES6的Object.keys()方法判断对象是否为空对象的几种方法
                        var result = Object.keys(data.results);
                        if (result.length != 0) {
                            this.hangduanName = data.results.hangduanName;
                            this.flowPostPublishTime = data.results.publishTime;
                            this.flowPostData = JSON.parse(data.results.flowPost);
                            this.flowPostHeader = JSON.parse(data.results.flowPostHeader);
                        }
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