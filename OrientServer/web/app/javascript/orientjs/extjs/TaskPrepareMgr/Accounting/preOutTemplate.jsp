<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 2020/1/10
  Time: 10:43
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
    var seaArea = '<%=request.getParameter("seaArea")%>';
    var jingdu = '<%=request.getParameter("jingdu")%>';
    var weidu = '<%=request.getParameter("weidu")%>';
    var planDivingDepth = '<%=request.getParameter("planDivingDepth")%>';
    var divingType = '<%=request.getParameter("divingType")%>';
</script>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title></title>
    <script type="text/javascript" src="${ctx}/app/javascript/lib/jquery/jquery-1.7.2.min.js"></script>
    <link rel="stylesheet" href="${ctx}/app/javascript/lib/layui-2.5.5/src/css/layui.css">
    <script type="text/javascript"
            src="${ctx}/app/javascript/orientjs/extjs/TaskPrepareMgr/Accounting/js/rowOperation.js"></script>
    <style>
        td {
            height: 25px;
            width: auto;
        }
    </style>
</head>
<body style="margin-left: 0px;padding: 0">
<form action="" method="post" name="formTable1" class="layui-form" id="preOutTemplateForm"
      lay-filter="preOutTemplateForm">
    <table width="100%" height="50%" border="1" align="center" cellspacing=0 cellpadding=0>
        <tr>
            <td align="center" valign="middle" colspan="4">深海勇士均衡计算表</td>
        </tr>
        <tr>
            <td align="center" valign="middle">上一潜次</td>
            <td align="center" valign="middle" colspan="3" style="width: auto;">
                <%--<input name="beforeDiving" type="text" id="beforeDiving" style="width: 100%;height: 100%;border:none;">--%>
                <select id="selectorDivingType" lay-filter="selectUpDivingSelect" lay-verify="required" lay-search>
                    <option value="-1">请选择</option>
                </select>
            </td>
        </tr>
        <tr>
            <td align="center" valign="middle">作业海区</td>
            <td id="beforeWorkSeaArea" name="beforeWorkSeaArea">${beforeWorkSeaArea}</td>
            <td align="center" valign="middle">深度</td>
            <td name="beforeDepth" id="beforeDepth">${depth}
            </td>
        </tr>
        <tr>
            <td align="center" valign="middle">密度</td>
            <td name="beforeDesity" id="beforeDesity">
            </td>
            <td align="center" valign="middle">浮力损失</td>
            <td name="beforeBuoyancyLoss" id="beforeBuoyancyLoss">
            </td>
        </tr>
        <tr>
            <td align="center" valign="middle">上浮深度(M)</td>
            <td name="beforeFloatDepth" id="beforeFloatDepth">
            </td>
            <td align="center" valign="middle">计划上浮时长(min)</td>
            <td name="beforeFloatHours" id="beforeFloatHours">
            </td>
        </tr>
        <tr height="20">
            <td align="center" valign="middle">
                采样篮铁砂密度
            </td>
            <td name="beforeBasketIronDesity" id="beforeBasketIronDesity"></td>
            <td align="center" valign="middle">
                上浮压载密度
            </td>
            <td name="beforeComeupDesity" id="beforeComeupDesity"></td>
        </tr>
        <tr>
            <td align="center" valign="middle" colspan="2">状态</td>
            <td align="center" valign="middle">重量(kg)</td>
            <td align="center" valign="middle">排水体积(L)</td>
        </tr>
        <tr height="20px" id="personStatistics1">
            <td rowspan="3" align="center" valign="middle">
                人员
            </td>
            <td align="center" valign="middle" id="beforePerson0" name="beforePerson0" style="width:200px"></td>
            <td id="beforeWeight0" name="beforeWeight0" style="width: 200px"></td>
            <td id="beforePWaterVolume0" name="beforePWaterVolume0" style="width: 200px"></td>
        </tr>
        <tr height="20px" id="personStatistics2">
            <td align="center" valign="middle" id="beforePerson1" name="beforePerson1" style="width: 200px"></td>
            <td id="beforeWeight1" name="beforeWeight1" style="width: 200px"></td>
            <td id="beforePWaterVolume1" name="beforePWaterVolume1" style="width: 200px"></td>
        </tr>
        <tr height="20px" id="personStatistics3">
            <td align="center" valign="middle" id="beforePerson2" name="beforePerson2" style="width: 200px"></td>
            <td id="beforeWeight2" name="beforeWeight2" style="width: 200px"></td>
            <td id="beforePWaterVolume2" name="beforePWaterVolume2" style="width: 200px"></td>
        </tr>
        <tr height="20px" id="toolStatistics1">
            <td rowspan="2" align="center" valign="middle">
                工具
            </td>
            <td align="center" valign="middle">潜水器</td>
            <td id="beforeToolWeight0" name="beforeToolWeight0"></td>
            <td id="beforeToolPWaterVolume0" name="beforeToolPWaterVolume0"></td>
        </tr>
        <tr height="20px" id="toolStatistics2">
            <td align="center" valign="middle">科学家</td>
            <td id="beforeToolWeight1" name="beforeToolWeight1"></td>
            <td id="beforeToolPWaterVolume1" name="beforeToolPWaterVolume1"></td>
        </tr>
        <tr>
            <td colspan="2" align="center" valign="middle">舱内总重</td>
            <td name="beforeCabinWeight" id="beforeCabinWeight">
            </td>
            <td id="beforeCanbinPWaterVolume" name="beforeCanbinPWaterVolume">
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center" valign="middle">采样篮工具</td>
            <td name="beforeBasketWeight" id="beforeBasketWeight">
            </td>
            <td name="beforeBasketPWaterVolume" id="beforeBasketPWaterVolume">
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center" valign="middle">采样篮铁砂</td>
            <td name="beforeBasketIronWeight" id="beforeBasketIronWeight">
            </td>
            <td name="beforeBasketIronPWaterVolume" id="beforeBasketIronPWaterVolume">
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center" valign="middle">潜水器总重</td>
            <td id="beforeDDeviceWeight" name="beforeDDeviceWeight">
            </td>
            <td id="beforeDDevicePWaterVolume" name="beforeDDevicePWaterVolume">
            </td>
        </tr>
        <tr>
            <td align="center" valign="middle" colspan="2">合计</td>
            <td id="beforeTotalWeight1" name="beforeTotalWeight1"></td>
            <td id="beforeTotalPWaterVolume1" name="beforeTotalPWaterVolume1"></td>
        </tr>
        <!--<tr height="20"></tr>-->
        <tr>
            <td align="center" valign="middle" colspan="2">配重</td>
            <td align="center" valign="middle">重量(kg)</td>
            <td align="center" valign="middle">排水体积(L)</td>
        </tr>
        <tr>
            <td align="center" valign="middle" colspan="2">
                配重铅块
            </td>
            <td name="beforeLeadWeight" id="beforeLeadWeight">
            </td>
            <td name="beforeLeadPWaterVolume" id="beforeLeadPWaterVolume"></td>
        </tr>
        <tr>
            <td align="center" valign="middle" colspan="2">
                上浮压载
            </td>
            <td name="beforeComeupWeight" id="beforeComeupWeight">
            </td>
            <td id="beforeComeupPWaterVolume" name="beforeComeupPWaterVolume"></td>
        </tr>
        <tr>
            <td align="center" valign="middle" colspan="2">
                可调压载水
            </td>
            <td name="beforeAdjustWeight" id="beforeAdjustWeight">
            </td>
            <td id="beforeAdjustPWaterVolume" name="beforeAdjustPWaterVolume"></td>
        </tr>
        <tr>
            <td align="center" valign="middle" colspan="2">合计</td>
            <td id="beforeTotalWeight2" name="beforeTotalWeight2"></td>
            <td id="beforeTotalPWaterVolume2" name="beforeTotalPWaterVolume2"></td>
        </tr>
        <tr>
            <td align="center" valign="middle">
                均衡状态(浮力-重量)
            </td>
            <td colspan="3" id="beforeBalanceState" name="beforeBalanceState"></td>
        </tr>
        <!--<tr height="20"></tr>-->
    </table>
</form>

<script src="${ctx}/app/javascript/lib/layui-2.5.5/src/layui.js" charset="utf-8"></script>
<script>
    var showTaskList = ${showTaskList};
    var selectorTaskName = document.getElementById("selectorDivingType");
    for (var i = 0; i < showTaskList.length; i++) {
        var optionZuoxian = new Option(showTaskList[i].name, showTaskList[i].id);
        selectorTaskName.options.add(optionZuoxian);
    }

    var personWeightList;
    $.ajax({
        url: __ctx + '/accountingForm/getPersonWeight.rdm',
        async: false,
        data: {
            "hangduanId": hangduanId
        },
        success: function (data) {
            // console.log(data);
            //JSON.parse()字符串解析成json对象
            var result = JSON.parse(data).results;
            personWeightList = result;
        }
    });

    //计算舱内总重
    function calPreCabinWeight(value) {
        var personWeight0 = $('#beforeWeight0').text();
        var personWeight1 = $('#beforeWeight1').text();
        var personWeight2 = $('#beforeWeight2').text();
        var toolWeight0 = $('#beforeToolWeight0').text();
        var toolWeight1 = $('#beforeToolWeight1').text();
        var cabinWeight = Number(personWeight0) + Number(personWeight1) + Number(personWeight2) + Number(toolWeight0) + Number(toolWeight1);
        $('#beforeCabinWeight').text(cabinWeight);
    }

    function calPreCabinPWaterVolume() {
        var beforePWaterVolume0 = $('#beforePWaterVolume0').text();
        var beforePWaterVolume1 = $('#beforePWaterVolume1').text();
        var beforePWaterVolume2 = $('#beforePWaterVolume2').text();
        var beforeToolPWaterVolume0 = $('#beforeToolPWaterVolume0').text();
        var beforeToolPWaterVolume1 = $('#beforeToolPWaterVolume1').text();
        var cabinPWaterVolume = Number(beforePWaterVolume0) + Number(beforePWaterVolume1) + Number(beforePWaterVolume2) + Number(beforeToolPWaterVolume0) + Number(beforeToolPWaterVolume1);
        $('#beforeCanbinPWaterVolume').text(cabinPWaterVolume);
    }

    //计算合计1重力
    function calPreTotalWeight1() {
        var basketWeight = Number($('#beforeBasketWeight').text());
        var basketIronWeight = Number($('#beforeBasketIronWeight').text());
        var dDeviceWeight = Number($('#beforeDDeviceWeight').text());
        var cabinWeight = Number($('#beforeCabinWeight').text());
        var totalWeight0 = dDeviceWeight + cabinWeight + basketWeight + basketIronWeight;
        totalWeight0 = (Math.round(totalWeight0 * 10) / (10)).toFixed(1);
        $('#beforeTotalWeight1').text(totalWeight0);
    }

    //计算合计1排水体积
    function calPreTotalPwVolume1() {
        var basketPWaterVolume = Number($('#beforeBasketPWaterVolume').text());
        var basketIronPWaterVolume = Number($('#beforeBasketIronPWaterVolume').text());
        var dDevicePWaterVolume = Number($('#beforeDDevicePWaterVolume').text());
        var buoyancyLoss = Number($('#beforeBuoyancyLoss').text());
        if (typeof buoyancyLoss != "undefined") {
            var totalPwVolume0 = Number(dDevicePWaterVolume) + basketPWaterVolume + basketIronPWaterVolume - buoyancyLoss;
            totalPwVolume0 = (Math.round(totalPwVolume0 * 10) / (10)).toFixed(1);
            $('#beforeTotalPWaterVolume1').text(totalPwVolume0);
        }
    }

    //计算配重铅块排水体积
    function calPreLeadPWaterVolume() {
        var beforeLeadWeight = Number($('#beforeLeadWeight').text());
        if (beforeLeadWeight != "") {
            var leadPwVolume = (beforeLeadWeight - 4.2) / 11 + (4.2 / 7.85);
            leadPwVolume = (Math.round(leadPwVolume * 10) / (10)).toFixed(1);
            $('#beforeLeadPWaterVolume').text(leadPwVolume);
        }
    }

    //计算上浮压载排水体积
    function calPreComeupPWVolume() {
        var beforeComeupWeight = Number($('#beforeComeupWeight').text());
        var beforeComeupDesity = Number($('#beforeComeupDesity').text());
        var comeupPwVolume = beforeComeupWeight / beforeComeupDesity;
        comeupPwVolume = comeupPwVolume || 0;
        comeupPwVolume = (Math.round(comeupPwVolume * 10) / (10)).toFixed(1);
        $('#beforeComeupPWaterVolume').text(comeupPwVolume);
    }

    //计算合计重力2
    function calPreTotalWeight2() {
        var comeupWeight = Number($('#beforeComeupWeight').text());
        var leadWeight = Number($('#beforeLeadWeight').text());
        var adjustWeight = Number($('#beforeAdjustWeight').text());
        var totalWeight2 = adjustWeight + comeupWeight + leadWeight;
        $('#beforeTotalWeight2').text(totalWeight2);
    }

    //计算合计排水体积2
    function calPreTotalPwVolume2() {
        var beforeComeupWeight = Number($('#beforeComeupWeight').text());
        var beforeComeupDesity = Number($('#beforeComeupDesity').text());
        var beforeComeupPWaterVolume = beforeComeupWeight / beforeComeupDesity;
        beforeComeupPWaterVolume = beforeComeupPWaterVolume || 0;
        var beforeLeadWeight = Number($('#beforeLeadWeight').text());
        var beforeLeadPWaterVolume = 0;
        if (beforeLeadWeight != '') {
            beforeLeadPWaterVolume = (beforeLeadWeight - 4.2) / 11 + (4.2 / 7.85);
        }
        // var beforeComeupPWaterVolume = Number($('#beforeComeupPWaterVolume').text());
        // var beforeLeadPWaterVolume = Number($('#beforeLeadPWaterVolume').text());
        var totalPwVolume2 = beforeComeupPWaterVolume + beforeLeadPWaterVolume;
        totalPwVolume2 = totalPwVolume2 || 0;
        totalPwVolume2 = (Math.round(totalPwVolume2 * 10) / (10)).toFixed(1);
        $('#beforeTotalPWaterVolume2').text(totalPwVolume2);
    }

    //计算均衡状态
    function calbeforeBalanceState() {
        var beforeTotalPWaterVolume1 = Number($('#beforeTotalPWaterVolume1').text());
        var beforeLeadPWaterVolume = Number($('#beforeLeadPWaterVolume').text());
        var beforeComeupPWaterVolume = Number($('#beforeComeupPWaterVolume').text());
        var beforeDesity = Number($('#beforeDesity').text());
        var beforeTotalWeight1 = Number($('#beforeTotalWeight1').text());
        var beforeLeadWeight = Number($('#beforeLeadWeight').text());
        var beforeComeupWeight = Number($('#beforeComeupWeight').text());
        var beforeAdjustWeight = Number($('#beforeAdjustWeight').text());
        var balanceState = (beforeTotalPWaterVolume1 + beforeLeadPWaterVolume + beforeComeupPWaterVolume) * beforeDesity * 0.001 -
            (beforeTotalWeight1 + beforeLeadWeight + beforeComeupWeight + beforeAdjustWeight);
        balanceState = (Math.round(balanceState * 10) / (10)).toFixed(1);
        $('#beforeBalanceState').text(balanceState);
    }

    layui.use('form', function () {
        var form = layui.form;
        form.on('select(selectUpDivingSelect)', function (data) {
            $("#beforeWorkSeaArea").text(" ");
            $("#beforeFloatDepth").text(" ");
            $("#beforeFloatHours").text(" ");
            $("#beforeBasketIronDesity").text(" ");
            $("#beforeDepth").text(" ");
            $("#beforeDesity").text(" ");
            $("#beforeBuoyancyLoss").text(" ");
            $("#beforeBasketWeight").text(" ");
            $("#beforeBasketPWaterVolume").text(" ");
            $("#beforeBasketIronWeight").text("");
            $("#beforeBasketIronPWaterVolume").text("");
            $("#beforeLeadWeight").text(" ");
            $("#beforeComeupWeight").text(" ");
            $("#beforeAdjustWeight").text(" ");
            $("#beforeDDeviceWeight").text(" ");
            $("#beforeDDevicePWaterVolume").text(" ");
            $("#beforePerson0").text(" ");
            $("#beforeWeight0").text(" ");
            $("#beforepPWaterVolume0").text(" ");
            $("#beforePerson1").text(" ");
            $("#beforeWeight1").text(" ");
            $("#beforePWaterVolume1").text(" ");
            $("#beforePerson2").text(" ");
            $("#beforeWeight2").text(" ");
            $("#beforePWaterVolume2").text(" ");
            $("#beforeToolWeight0").text(" ");
            $("#beforeToolPWaterVolume0").text(" ");
            $("#beforeToolWeight1").text(" ");
            $("#beforeToolPWaterVolume1").text(" ");
            $('#beforeCabinWeight').text(" ");
            $('#beforeTotalWeight1').text(" ");
            $('#beforeTotalPWaterVolume1').text(" ");
            $('#beforeLeadPWaterVolume').text(" ");
            $('#beforeComeupPWaterVolume').text(" ");
            $('#beforeTotalWeight2').text(" ");
            $('#beforeTotalPWaterVolume2').text(" ");
            $('#beforeBalanceState').text(" ");
            $('#beforeComeupDesity').text(" ");
            $.ajax({
                url: __ctx + '/accountingForm/getPreRefOutTemplateData.rdm',
                async: true,
                data: 'taskId=' + data.value,
                success: function (data) {
                    // console.log(data);
                    var result = JSON.parse(data);
                    if (result.length > 0) {
                        for (var i = 0; i < result.length; i++) {
                            $("#beforeWorkSeaArea").text(result[i].beforeWorkSeaArea);
                            $("#beforeDepth").text(result[i].depth);
                            $("#beforeDesity").text(result[i].desity);
                            $("#beforeBuoyancyLoss").text(result[i].buoyancyLoss);
                            $("#beforeFloatDepth").text(result[i].beforeFloatDepth);
                            $("#beforeFloatHours").text(result[i].beforeFloatHours);
                            $("#beforeBasketIronDesity").text(result[i].basketIronDensity);
                            $("#beforeBasketWeight").text(result[i].basketWeight);
                            $("#beforeBasketPWaterVolume").text(result[i].basketPWaterVolume);
                            $("#beforeBasketIronWeight").text(result[i].basketIronWeight);
                            $("#beforeBasketIronPWaterVolume").text(result[i].basketIronPWaterVolume);
                            $("#beforeLeadWeight").text(result[i].leadWeight);
                            $("#beforeComeupWeight").text(result[i].comeupWeight);
                            $("#beforeAdjustWeight").text(result[i].adjustWeight);
                            $("#beforeDDeviceWeight").text(result[i].dDeviceWeight);
                            $("#beforeDDevicePWaterVolume").text(result[i].dDevicePWaterVolume);
                            $("#beforeComeupDesity").text(result[i].comeupDesity);
                            // if (result[i].personStatisticsRowData != '') {
                            //     var personStatisticsRowData = JSON.parse(result[i].personStatisticsRowData);
                            //     setValue(personStatisticsRowData, true);
                            // }
                            var selectPerson0 = result[i].leftDriver;
                            var selectPerson1 = result[i].mainDriver;
                            var selectPerson2 = result[i].rightDriver;
                            (function () {
                                for (var j = 0; j < personWeightList.length; j++) {
                                    if (personWeightList[j].id == selectPerson0) {
                                        $('#beforePerson0').text(personWeightList[j].name);
                                        // $('#beforeWeight0').text(personWeightList[j].weight);
                                    } else if (personWeightList[j].id == selectPerson1) {
                                        $('#beforePerson1').text(personWeightList[j].name);
                                        // $('#beforeWeight1').text(personWeightList[j].weight);
                                    } else if (personWeightList[j].id == selectPerson2) {
                                        $('#beforePerson2').text(personWeightList[j].name);
                                        // $('#beforeWeight2').text(personWeightList[j].weight);
                                    }
                                }
                            })();
                            if (result[i].personStatisticsRowData != '') {
                                var toolStatisticsRowData = JSON.parse(result[i].personStatisticsRowData);
                                setValue(toolStatisticsRowData, true);
                            }
                            if (result[i].toolStatisticsRowData != '') {
                                var toolStatisticsRowData = JSON.parse(result[i].toolStatisticsRowData);
                                setValue(toolStatisticsRowData, false);
                            }
                        }
                        calPreCabinWeight();
                        calPreCabinPWaterVolume();
                        calPreTotalWeight1();
                        calPreTotalPwVolume1();
                        calPreLeadPWaterVolume();
                        calPreComeupPWVolume();
                        calPreTotalWeight2();
                        calPreTotalPwVolume2();
                        calbeforeBalanceState();
                    }
                }
            });
        });
        form.render();
    });

    function setValue(statisticsRowData, isPersonOrTool) {
        var trArrays = [];
        if (isPersonOrTool) {
            trArrays = $("#personStatistics1");
            trArrays.push($("#personStatistics2"));
            trArrays.push($("#personStatistics3"));
        } else {
            trArrays = $("#toolStatistics1");
            trArrays.push($("#toolStatistics2"));
        }
        trArrays.each(function (i) {
            if (i >= 0 && i < trArrays.length) {
                //获取每行第一个单元格里的selector控件的值
                if (statisticsRowData.length > 0) {
                    if (i == 0) {
                        if (isPersonOrTool) {
                            var name = $(this).children().eq(1).text();
                            var tr = $(this);
                            (function () {
                                for (var j = 0; j < statisticsRowData.length; j++) {
                                    if (name == statisticsRowData[j].name) {
                                        tr.children().eq(2).text(statisticsRowData[j].weight);
                                        break;
                                    }
                                }
                            }())
                        } else {
                            $(this).children().eq(2).text(statisticsRowData[i].weight);
                        }
                        // $(this).children().eq(3).text(statisticsRowData[i].pwVolume);
                    } else {
                        if (isPersonOrTool) {
                            var name = $(this).children().eq(0).text();
                            var tr = $(this);
                            (function () {
                                for (var j = 0; j < statisticsRowData.length; j++) {
                                    if (name == statisticsRowData[j].name) {
                                        tr.children().eq(1).text(statisticsRowData[j].weight);
                                        break;
                                    }
                                }
                            }());
                        } else {
                            $(this).children().eq(1).text(statisticsRowData[i].weight);
                        }
                        // $(this).children().eq(2).text(statisticsRowData[i].pwVolume);
                    }
                }
            }
        });
    }

    function getNum(val) {
        if (isNaN(val)) {
            return 0;
        }
        return val;
    }
</script>

</body>
</html>
