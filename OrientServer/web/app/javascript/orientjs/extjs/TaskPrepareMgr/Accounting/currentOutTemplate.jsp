<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 2020/1/10
  Time: 10:35
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
    var taskId = '<%=request.getParameter("taskId")%>';
    var hangduanId = '<%=request.getParameter("hangduanId")%>';
    var taskName = '<%=request.getParameter("taskName")%>';
    var peizhongId = '<%=request.getParameter("peizhongId")%>';
    if (hangduanId == 'null') {
        hangduanId = '${hangduanId}';
    }
    var isCanEdit = '${isCanEdit}';
    var isSubmitTable = '${isSubmitTable}';
    var personStatisticsRowData = '${personStatisticsRowData}';
    var toolStatisticsRowData = '${toolStatisticsRowData}';
    if (personStatisticsRowData != '') {
        var personStatisticsRowData = JSON.parse('${personStatisticsRowData}');
    }
    if (toolStatisticsRowData != '') {
        var toolStatisticsRowData = JSON.parse('${toolStatisticsRowData}');
    }

</script>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title></title>
    <script type="text/javascript" src="${ctx}/app/javascript/lib/jquery/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="${ctx}/app/javascript/lib/export/jquery.js"></script>
    <script type="text/javascript" src="${ctx}/app/javascript/lib/export/FileSaver.js"></script>
    <script type="text/javascript" src="${ctx}/app/javascript/lib/export/jquery.wordexport.js"></script>
    <script type="text/javascript" src="${ctx}/app/javascript/lib/ext-4.2/examples/shared/include-ext.js"></script>
    <script type="text/javascript"
            src="${ctx}/app/javascript/orientjs/extjs/Common/Extend/Panel/OrientPanel.js"></script>
    <script type="text/javascript" src="${ctx}/app/javascript/orientjs/extjs/Common/Util/OrientExtUtil.js"></script>
    <link rel="stylesheet" href="${ctx}/app/javascript/lib/layui-2.5.5/src/css/layui.css">
    <script type="text/javascript"
            src="${ctx}/app/javascript/orientjs/extjs/TaskPrepareMgr/Accounting/js/rowOperation.js"></script>
    <style>
        td {
            height: 25px;
            width: auto;
        }

        /*去掉input 类型number中输入框右边的上下箭头按钮 */
        .deal::-webkit-outer-spin-button {
            -webkit-appearance: none;
        }

        .deal::-webkit-inner-spin-button {
            -webkit-appearance: none;
        }
    </style>
</head>
<body style="margin-left: 0px;padding: 0" id="myWord">
<form action="" method="post" name="formTable1" class="layui-form" lay-filter="currOutTemplateForm" autocomplete="off">
    <table width="100%" height="90%" border="1" align="center" cellspacing=0 cellpadding=0>
        <tr>
            <td align="center" valign="middle" colspan="4">深海勇士均衡计算表</td>
        </tr>
        <tr>
            <td align="center" valign="middle">潜次</td>
            <td id="diving" name="diving">${taskName}</td>
            <td align="center" valign="middle">填表日期</td>
            <td><input name="fillTableDate" type="text" id="fillTableDate" placeholder="yyyy-MM-dd"
                       style="width: 100%;height: 100%;border:none;"
                       value="${fillTableDate}"></td>
        </tr>
        <tr>
            <td align="center" valign="middle">作业海区</td>
            <td><select id="workSeaArea" lay-filter="workSeaAreaSelectFilter" lay-verify="required"
                        lay->
                <option value="">请选择</option>
            </select></td>
            <td align="center" valign="middle">下潜深度(M)</td>
            <td id="selectDepth" name="depth">${depth}
                <%--<input name="depth" type="text" id="depth" style="width: 100%;height: 100%;border:none;"--%>
                <%--value="${depth}">--%>
                <%--<select id="selectDepth" lay-filter="selectDepthSelect" lay-verify="required"--%>
                <%--lay-search>--%>
                <%--<option value="-1">请选择</option>--%>
                <%--</select>--%>
            </td>
        </tr>
        <tr>
            <td align="center" valign="middle">密度</td>
            <td>
                <input name="desity" type="number" id="desity" style="width: 100%;height: 100%;border:none;"
                       value="${desity}" onblur="calBuoyancyLoss()" class="deal"
                       onmousewheel="stopScrollFun()" onDOMMouseScroll="stopScrollFun()">
            </td>
            <td align="center" valign="middle">浮力损失</td>
            <td name="buoyancyLoss" type="text" id="buoyancyLoss">${buoyancyLoss}
                <%--<input name="buoyancyLoss" type="text" id="buoyancyLoss" style="width: 100%;height: 100%;border:none;"--%>
                <%--value="${buoyancyLoss}" onchange="calCurrentTotalPwVolume0(this.value,this.id)">--%>
            </td>

        </tr>
        <tr height="20">
            <td align="center" valign="middle">
                上浮深度(M)
            </td>
            <td id="floatDepth" name="floatDepth">${floatDepth}</td>
            <td align="center" valign="middle">计划上浮时长(min)</td>
            <td>
                <input type="number" name="planFloatHours" id="planFloatHours"
                       style="width: 100%;height: 100%;border:none;" value="${planFloatHours}" class="deal"
                       onmousewheel="stopScrollFun()" onDOMMouseScroll="stopScrollFun()">
            </td>

        </tr>

        <tr>
            <td align="center" valign="middle">采样篮铁砂密度</td>
            <td>
                <input name="basketIronDensity" type="number" id="basketIronDensity"
                       style="width: 100%;height: 100%;border:none;"
                       value="${basketIronDensity}" onchange="calBasketIronPwVolume()" class="deal"
                       onmousewheel="stopScrollFun()" onDOMMouseScroll="stopScrollFun()">
            </td>

            <td align="center" valign="middle">
                上浮压载密度
            </td>
            <td colspan="2"><input name="comeupDesity" type="number" id="comeupDesity"
                                   style="width: 100%;height: 100%;border:none;"
                                   value="${comeupDesity}" onchange="calComeupPWVolume(this.value)" class="deal"
                                   onmousewheel="stopScrollFun()" onDOMMouseScroll="stopScrollFun()"></td>

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
            <td align="center" valign="middle" style="width: auto;" id="selectPerson0" name="selectPerson0">
                <%--<input name="currentPerson0" type="text" id="currentPerson0"--%>
                <%--style="width: 100%;height: 100%;border:none;">--%>
                <%--<select id="selectPerson0" lay-filter="selectPersonSelect0"--%>
                <%--lay-verify="required" lay-search>--%>
                <%--<option value="-1">请选择</option>--%>
                <%--</select>--%>
            </td>
            <td>
                <input name="weight0" type="number" id="weight0" style="width: 100%;height: 100%;border:none;"
                       onchange="calCabinWeight(this.value);return false;" class="deal"
                       onmousewheel="stopScrollFun()" onDOMMouseScroll="stopScrollFun()">
            </td>
            <td align="center" valign="middle">
                <%--<input name="pWaterVolume0" type="text" id="pWaterVolume0"--%>
                <%--style="width: 100%;height: 100%;border:none;"--%>
                <%--onchange="calCabinpWaterVolume(this.value);return false;">--%>
            </td>
        </tr>
        <tr height="20px" id="personStatistics2">
            <td align="center" valign="middle" style="width: auto;" id="selectPerson1" name="selectPerson1">
                <%--<input name="currentPerson1" type="text" id="currentPerson1"--%>
                <%--style="width: 100%;height: 100%;border:none;">--%>
                <%--<select id="selectPerson1" lay-filter="selectPersonSelect1" lay-verify="required" lay-search>--%>
                <%--<option value="-1">请选择</option>--%>
                <%--</select>--%>
            </td>
            <td>
                <input name="weight1" type="number" id="weight1" style="width: 100%;height: 100%;border:none;"
                       onchange="calCabinWeight(this.value);return false;" class="deal"
                       onmousewheel="stopScrollFun()" onDOMMouseScroll="stopScrollFun()">
            </td>
            <td align="center" valign="middle">
                <%--<input name="pWaterVolume1" type="text" id="pWaterVolume1"--%>
                <%--style="width: 100%;height: 100%;border:none;"--%>
                <%--onchange="calCabinpWaterVolume(this.value);return false;">--%>
            </td>
        </tr>
        <tr height="20px" id="personStatistics3">
            <td align="center" valign="middle" style="width: auto;" id="selectPerson2" name="selectPerson2">
                <%--<input name="currentPerson2" type="text" id="currentPerson2"--%>
                <%--style="width: 100%;height: 100%;border:none;">--%>
                <%--<select id="selectPerson2" lay-filter="selectPersonSelect2" lay-verify="required" lay-search>--%>
                <%--<option value="-1">请选择</option>--%>
                <%--</select>--%>
            </td>
            <td>
                <input name="weight2" type="number" id="weight2" style="width: 100%;height: 100%;border:none;"
                       onchange="calCabinWeight(this.value);return false;" class="deal"
                       onmousewheel="stopScrollFun()" onDOMMouseScroll="stopScrollFun()">
            </td>
            <td align="center" valign="middle">
                <%--<input name="pWaterVolume2" type="text" id="pWaterVolume2"--%>
                <%--style="width: 100%;height: 100%;border:none;"--%>
                <%--onchange="calCabinpWaterVolume(this.value);return false;">--%>
            </td>
        </tr>
        <tr height="20px" id="toolStatistics1">
            <td rowspan="2" align="center" valign="middle">
                工具
            </td>
            <td align="center" valign="middle">潜水器</td>
            <td>
                <input name="toolWeight0" type="number" id="toolWeight0" style="width: 100%;height: 100%;border:none;"
                       onchange="calCabinWeight(this.value);return false;" class="deal"
                       onmousewheel="stopScrollFun()" onDOMMouseScroll="stopScrollFun()">
            </td>
            <td align="center" valign="middle">
                <%--<input name="toolPWaterVolume0" type="text" id="toolPWaterVolume0"--%>
                <%--style="width: 100%;height: 100%;border:none;"--%>
                <%--onchange="calCabinpWaterVolume(this.value);return false;">--%>
            </td>
        </tr>
        <tr height="20px" id="toolStatistics2">
            <td align="center" valign="middle">科学家</td>
            <td>
                <input name="toolWeight1" type="number" id="toolWeight1" style="width: 100%;height: 100%;border:none;"
                       onchange="calCabinWeight(this.value);return false;" class="deal"
                       onmousewheel="stopScrollFun()" onDOMMouseScroll="stopScrollFun()">
            </td>
            <td align="center" valign="middle">
                <%--<input name="toolPWaterVolume1" type="text" id="toolPWaterVolume1"--%>
                <%--style="width: 100%;height: 100%;border:none;"--%>
                <%--onchange="calCabinpWaterVolume(this.value);return false;">--%>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center" valign="middle">舱内总重</td>
            <td name="cabinWeight" id="cabinWeight" onchange="calCurrentTotalWeight0()">
            </td>
            <td id="canbinPWaterVolume" name="canbinPWaterVolume ">
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center" valign="middle">采样篮工具</td>
            <td name="basketWeight" id="basketWeight">${basketWeight}
                <%--<input name="basketWeight" type="text" id="basketWeight" style="width: 100%;height: 100%;border:none;"--%>
                <%--value="${basketWeight}" onchange="calCurrentTotalWeight0(this.value,this.id)">--%>
            </td>
            <td name="basketPWaterVolume" id="basketPWaterVolume">${basketPWaterVolume}
                <%--<input name="basketPWaterVolume" type="text" id="basketPWaterVolume"--%>
                <%--style="width: 100%;height: 100%;border:none;" value="${basketPWaterVolume}"--%>
                <%--onchange="calCurrentTotalPwVolume0(this.value,this.id)">--%>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center" valign="middle">采样篮铁砂</td>
            <td>
                <input name="basketIronWeight" type="number" id="basketIronWeight"
                       style="width: 100%;height: 100%;border:none;"
                       value="${basketIronWeight}" onchange="calBasketIronPwVolume()" class="deal"
                       onmousewheel="stopScrollFun()" onDOMMouseScroll="stopScrollFun()">
            </td>
            <td name="basketIronPWaterVolume" id="basketIronPWaterVolume">${basketIronPWaterVolume}
                <%--<input name="basketIronPWaterVolume" type="number" id="basketIronPWaterVolume"--%>
                <%--style="width: 100%;height: 100%;border:none;" value="${basketIronPWaterVolume}"--%>
                <%--onchange="calCurrentTotalPwVolume0()" class="deal"--%>
                <%--onmousewheel="stopScrollFun()" onDOMMouseScroll="stopScrollFun()">--%>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center" valign="middle">潜水器总重</td>
            <td id="dDeviceWeight" name="dDeviceWeight"
                onchange="calCurrentTotalWeight0()">${dDeviceWeight}
            </td>
            <td id="dDevicePWaterVolume" name="dDevicePWaterVolume"
                onchange="calCurrentTotalPwVolume0()">${dDevicePWaterVolume}
            </td>
        </tr>
        <tr>
            <td align="center" valign="middle" colspan="2">合计</td>
            <td id="totalWeight0" name="totalWeight0"></td>
            <td id="totalPWaterVolume0" name="totalPWaterVolume0"></td>
        </tr>
        <tr>
            <td align="center" valign="middle" colspan="2">
                变化
            </td>
            <td align="center" valign="middle">重力(kg)</td>
            <td align="center" valign="middle">排水体积(L)</td>
        </tr>
        <tr>
            <td align="center" valign="middle" colspan="2"><input type="button" name="show1" value="显示"
                                                                  style="width:40px" onclick="changeShow1()"/></td>
            <td id="changeWeight1" name="changeWeight1"></td>
            <td id="changePwVolume1" name="changePwVolume1"></td>
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
            <td>
                <input name="leadWeight" type="number" id="leadWeight" style="width: 100%;height: 100%;border:none;"
                       value="${leadWeight}" onchange="calLeadPWaterVolume(this.value)" class="deal"
                       onmousewheel="stopScrollFun()" onDOMMouseScroll="stopScrollFun()">
            </td>
            <td name="leadPWaterVolume" id="leadPWaterVolume"></td>
        </tr>
        <tr>
            <td align="center" valign="middle" colspan="2">
                上浮压载
            </td>
            <td>
                <input name="comeupWeight" type="number" id="comeupWeight" style="width: 100%;height: 100%;border:none;"
                       value="${comeupWeight}" onchange="calComeupPWVolume(this.value)" class="deal"
                       onmousewheel="stopScrollFun()" onDOMMouseScroll="stopScrollFun()">
            </td>
            <td id="comeupPWaterVolume" name="comeupPWaterVolume"></td>
        </tr>
        <tr>
            <td align="center" valign="middle" colspan="2">
                可调压载水
            </td>
            <td>
                <input name="adjustWeight" type="number" id="adjustWeight" style="width: 100%;height: 100%;border:none;"
                       value="${adjustWeight}" onchange="calTotalWeight1(this.value)" class="deal"
                       onmousewheel="stopScrollFun()" onDOMMouseScroll="stopScrollFun()">
            </td>
            <td></td>
        </tr>
        <tr>
            <td align="center" valign="middle" colspan="2">合计</td>
            <td id="totalWeight1" name="totalWeight1"></td>
            <td id="totalPWaterVolume1" name="totalPWaterVolume1"></td>
        </tr>
        <tr>
            <td align="center" valign="middle" colspan="2">
                变化
            </td>
            <td align="center" valign="middle">重力(kg)</td>
            <td align="center" valign="middle">排水体积(L)</td>
        </tr>
        <tr>
            <td align="center" valign="middle" colspan="2"><input type="button" name="show2" value="显示"
                                                                  style="width:40px" onclick="changeShow2()"/></td>
            <td id="changeWeight2" name="changeWeight2"></td>
            <td id="changePwVolume2" name="changePwVolume2"></td>
        </tr>
        <!--	<tr height="20"></tr>-->
        <tr>
            <td colspan="2" align="center" valign="middle">
                均衡状态(浮力-重量)
            </td>
            <td id="balanceState" name="balanceState" onfocus="calBalanceState()"></td>
            <td><input type="button" name="show2" value="计算"
                       style="width: 100%;height: 100%;border:none;" onclick="calBalanceState()"/></td>
        </tr>
        <!--<tr height="20"></tr>-->
        <tr>
            <td colspan="4" align="center" valign="middle">${taskName}配重</td>
        </tr>
        <tr>
            <td align="center" valign="middle">配重铅块</td>
            <td name="peizhongQk" id="peizhongQk">${leadWeight}
            </td>
            <td align="center" valign="middle" rowspan="2">配重人</td>
            <td rowspan="2">
                <input name="peizhongPeople" type="text" id="peizhongPeople"
                       style="width: 100%;height: 100%;border:none;" value="${peizhongPeople}">
            </td>
        </tr>
        <tr>
            <td align="center" valign="middle">下潜压载</td>
            <td>
                <input name="divingLoad" type="number" id="divingLoad" style="width: 100%;height: 100%;border:none;"
                       value="${divingLoad}" class="deal"
                       onmousewheel="stopScrollFun()" onDOMMouseScroll="stopScrollFun()">
            </td>
        </tr>
        <tr>
            <td align="center" valign="middle">上浮压载</td>
            <td name="comeupLoad" id="comeupLoad">${comeupWeight}
            </td>
            <td align="center" valign="middle" rowspan="2">校核人</td>
            <td rowspan="2">
                <input name="checker" type="text" id="checker" style="width: 100%;height: 100%;border:none;"
                       value="${checker}">
            </td>
        </tr>
        <tr>
            <td align="center" valign="middle">可调压载水舱液位</td>
            <td name="adjustLoad" id="adjustLoad">${adjustWeight}
            </td>
        </tr>
        <tr>
            <td align="center" valign="middle">艏部水银液位</td>
            <td>
                <input name="mercury" type="number" id="mercury" style="width: 100%;height: 100%;border:none;"
                       value="${mercury}" class="deal"
                       onmousewheel="stopScrollFun()" onDOMMouseScroll="stopScrollFun()">
            </td>
            <td align="center" valign="middle" rowspan="2">部门长</td>
            <td rowspan="2">
                <input name="departLeader" type="text" id="departLeader" style="width: 100%;height: 100%;border:none;"
                       value="${departLeader}">
            </td>
        </tr>
        <tr>
            <td align="center" valign="middle">采样篮工具重量</td>
            <td name="currentBasketWeight" id="currentBasketWeight">${basketWeight}
            </td>
        </tr>
        <tr>
            <td align="center" valign="middle">说明</td>
            <td colspan="3">
                <input name="explain" type="text" id="explain" style="width: 100%;height: 100%;border:none;"
                       value="${explain}">
            </td>
        </tr>
    </table>
    <p id="buttonGroup" style=" margin:0 auto; text-align:center;"><input type="button" id="outTemplateSave" value="保存"
                                                                          style="color: black;font-family: bold;font-size:large"
                                                                          class="layui-btn layui-btn-primary"/>&nbsp&nbsp<input
            type="button" id="outTemplateSubmit" value="发布" style="color: black;font-family: bold;font-size: large"
            class="layui-btn layui-btn-primary"/>
    </p>
</form>
<script src="${ctx}/app/javascript/lib/layui-2.5.5/src/layui.js" charset="utf-8"></script>
<script>
    //type=number 禁用滚轮事件
    function stopScrollFun(evt) {
        evt = evt || window.event;
        if (evt.preventDefault) {
            //Firefox
            evt.preventDefault();
            evt.stopPropagation();
        } else {
            //IE
            evt.cancelBubble = true;
            evt.returnValue = false;
        }
        return false;
    }

    // if (typeof isSubmitTable != "undefined" && isSubmitTable == 'submit') {
    //
    // } else {
    layui.use('laydate', function () {
        var laydate = layui.laydate;
        //常规用法
        laydate.render({
            elem: '#fillTableDate'
        });
    });
    // }
    var personWeightList;
    if (hangduanId != '') {
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
                // var selectPerson0 = document.getElementById("selectPerson0");
                // var selectPerson1 = document.getElementById("selectPerson1");
                // var selectPerson2 = document.getElementById("selectPerson2");
                var selectPerson0 = '${leftDriver}';
                var selectPerson1 = '${mainDriver}';
                var selectPerson2 = '${rightDriver}';
                for (var i = 0; i < result.length; i++) {
                    if (result[i].id == selectPerson0) {
                        $('#selectPerson0').text(result[i].name);
                        $('#weight0').val(result[i].weight);
                    } else if (result[i].id == selectPerson1) {
                        $('#selectPerson1').text(result[i].name);
                        $('#weight1').val(result[i].weight);
                    } else if (result[i].id == selectPerson2) {
                        $('#selectPerson2').text(result[i].name);
                        $('#weight2').val(result[i].weight);
                    }
                    // var optionPerson0 = new Option(result[i].name, result[i].id);
                    // var optionPerson1 = new Option(result[i].name, result[i].id);
                    // var optionPerson2 = new Option(result[i].name, result[i].id);
                    // selectPerson0.options.add(optionPerson0);
                    // selectPerson1.options.add(optionPerson1);
                    // selectPerson2.options.add(optionPerson2);
                }
            }
        })
    }

    $.ajax({
        url: __ctx + '/accountingForm/getDepthDesityTypeData.rdm',
        async: false,
        success: function (data) {
            // console.log(data);
            //JSON.parse()字符串解析成json对象
            var result = JSON.parse(data).results;
            var depthDesityType = document.getElementById("workSeaArea");
            for (var i = 0; i < result.length; i++) {
                var optionDepthDesityType = new Option(result[i].depthDesityTypeName, result[i].id);
                depthDesityType.options.add(optionDepthDesityType);
            }
        }
    })

    <%--var depthDesityParameters;--%>
    <%--$.ajax({--%>
    <%--url: __ctx + '/accountingForm/getDepthDesitySelectData.rdm',--%>
    <%--async: false,--%>
    <%--data: {"deptyDesityTypeId": "", "peizhongId": peizhongId},--%>
    <%--success: function (data) {--%>
    <%--// console.log(data);--%>
    <%--//JSON.parse()字符串解析成json对象--%>
    <%--var result = JSON.parse(data).results;--%>
    <%--var depthId = '${depth}';--%>
    <%--for (var i = 0; i < result.length; i++) {--%>
    <%--if (result[i].id == depthId) {--%>
    <%--$('#selectDepth').text(result[i].depth);--%>
    <%--var depth = result[i].depth;--%>
    <%--var desity = Number($('#desity').text());--%>
    <%--if (typeof desity != "undefined" && typeof depth != "undefined") {--%>
    <%--var buoyancyLoss = 5327.24 * 13 / desity + 9716 * desity * 9.80665 * depth / 2800000000 + 69.5 * depth / 4500;--%>
    <%--buoyancyLoss = (Math.round(buoyancyLoss * 10) / (10)).toFixed(1);--%>
    <%--$('#buoyancyLoss').text(buoyancyLoss)--%>
    <%--}--%>
    <%--break;--%>
    <%--}--%>
    <%--}--%>
    <%--depthDesityParameters = result;--%>
    <%--}--%>
    <%--});--%>

    //计算舱内总重
    function calCabinWeight() {
        var personWeight0 = $('#weight0').val();
        var personWeight1 = $('#weight1').val();
        var personWeight2 = $('#weight2').val();
        var toolWeight0 = $('#toolWeight0').val();
        var toolWeight1 = $('#toolWeight1').val();
        var cabinWeight = Number(personWeight0) + Number(personWeight1) + Number(personWeight2) + Number(toolWeight0) + Number(toolWeight1);
        $('#cabinWeight').text(cabinWeight);
        var basketWeight = Number($('#basketWeight').text());
        //采样篮铁砂重力
        var basketIronWeight = Number($('#basketIronWeight').val());
        var dDeviceWeight = Number($('#dDeviceWeight').text());
        //计算合计1重力
        var totalWeight0 = Number(cabinWeight) + basketWeight + basketIronWeight + dDeviceWeight;
        totalWeight0 = (Math.round(totalWeight0 * 10) / (10)).toFixed(1);
        $('#totalWeight0').text(totalWeight0);
    }

    //计算舱内排水体积
    // function calCabinpWaterVolume(value) {
    // var pWaterVolume0 = $('#pWaterVolume0').val();
    // var pWaterVolume1 = $('#pWaterVolume1').val();
    // var pWaterVolume2 = $('#pWaterVolume2').val();
    //     var toolPWaterVolume0 = $('#toolPWaterVolume0').val();
    //     var toolPWaterVolume1 = $('#toolPWaterVolume1').val();
    //     var cabinPWaterVolume = Number(pWaterVolume0) + Number(pWaterVolume1) + Number(pWaterVolume2) + Number(toolPWaterVolume0) + Number(toolPWaterVolume1);
    //     $('#canbinPWaterVolume').text(cabinPWaterVolume);
    // }

    function calLeadPWaterVolume(value) {
        var comeupWeight = Number($('#comeupWeight').val());
        var comeupDesity = Number($('#comeupDesity').val());
        var comeupPwVolume = Number(comeupWeight / comeupDesity);
        var adjustWeight = Number($('#adjustWeight').val());
        // var comeupPwVolume = Number($('#comeupPWaterVolume').text());
        $('#peizhongQk').text(value);
        if (value != "") {
            //计算配重铅块排水体积
            var leadPwVolume = (Number(value) - 4.2) / 11 + (4.2 / 7.85);
            var leadPwVolumes = (Math.round(leadPwVolume * 10) / (10)).toFixed(1);
            $('#leadPWaterVolume').text(leadPwVolumes);
            // var leadWeight=Number($('#leadWeight').val());
            //计算合计重量
            var totalWeight1 = Number(value) + comeupWeight + adjustWeight;
            $('#totalWeight1').text(totalWeight1);
            //计算合计排水体积
            // var adjustPwVolume=$('#adjustPWaterVolume').text();
            var totalPwVolume1 = leadPwVolume + comeupPwVolume;
            totalPwVolume1 = (Math.round(totalPwVolume1 * 10) / (10)).toFixed(1);
            $('#totalPWaterVolume1').text(totalPwVolume1);
        } else {
            $('#leadPWaterVolume').text("");
            var totalWeight1 = comeupWeight + adjustWeight;
            $('#totalWeight1').text(totalWeight1);

            var totalPwVolume1 = comeupPwVolume;
            $('#totalPWaterVolume1').text(totalPwVolume1);
        }
    }

    //计算上浮压载排水体积
    function calComeupPWVolume(value) {
        // var comeupWeight=Number($('#comeupWeight').val());
        var leadWeight = Number($('#leadWeight').val());
        var adjustWeight = Number($('#adjustWeight').val());
        // var leadPWaterVolume = Number($('#leadPWaterVolume').text());
        var leadPWaterVolume = 0;
        if (leadWeight != 0) {
            leadPWaterVolume = (leadWeight - 4.2) / 11 + (4.2 / 7.85);
        }
        var comeupWeight = Number($('#comeupWeight').val());
        $('#comeupLoad').text(comeupWeight);
        if (value != "") {
            var comeupDesity = Number($('#comeupDesity').val());
            var comeupPwVolume = comeupWeight / comeupDesity;
            var comeupPwVolumes = (Math.round(comeupPwVolume * 10) / (10)).toFixed(1);
            $('#comeupPWaterVolume').text(comeupPwVolumes);
            var totalWeight1 = comeupWeight + leadWeight + adjustWeight;
            $('#totalWeight1').text(totalWeight1);
            //计算合计排水体积
            var totalPwVolume1 = Number(comeupPwVolume) + leadPWaterVolume;
            totalPwVolume1 = (Math.round(totalPwVolume1 * 10) / (10)).toFixed(1);
            $('#totalPWaterVolume1').text(totalPwVolume1);
        } else {
            $('#comeupPWaterVolume').text("");
            var totalWeight1 = leadWeight + adjustWeight;
            $('#totalWeight1').text(totalWeight1);

            var totalPwVolume1 = leadPWaterVolume;
            totalPwVolume1 = (Math.round(totalPwVolume1 * 10) / (10)).toFixed(1);
            $('#totalPWaterVolume1').text(totalPwVolume1);
        }
    }

    //计算合计重力2
    function calTotalWeight1(value) {
        var comeupWeight = Number($('#comeupWeight').val());
        var leadWeight = Number($('#leadWeight').val());
        $('#adjustLoad').text(value);
        if (value != "") {
            var totalWeight1 = Number(value) + comeupWeight + leadWeight;
            $('#totalWeight1').text(totalWeight1);
        } else {
            var totalWeight1 = comeupWeight + leadWeight;
            $('#totalWeight1').text(totalWeight1);
        }
    }

    //计算合计1重力
    function calCurrentTotalWeight0() {
        var cabinWeight = Number($('#cabinWeight').text());
        var basketWeight = Number($('#basketWeight').text());
        var basketIronWeight = Number($('#basketIronWeight').val());
        var dDeviceWeight = Number($('#dDeviceWeight').text());
        var totalWeight0 = cabinWeight + basketWeight + basketIronWeight + dDeviceWeight;
        totalWeight0 = (Math.round(totalWeight0 * 10) / (10)).toFixed(1);
        $('#totalWeight0').text(totalWeight0);
    }

    //计算合计1排水体积
    function calCurrentTotalPwVolume0() {
        var buoyancyLoss = Number($('#buoyancyLoss').text());
        var basketIronPWaterVolume = Number($('#basketIronPWaterVolume').text());
        var basketPWaterVolume = Number($('#basketPWaterVolume').text());
        var dDevicePWaterVolume = Number($('#dDevicePWaterVolume').text());
        var totalPwVolume0 = basketPWaterVolume + basketIronPWaterVolume + dDevicePWaterVolume - buoyancyLoss;
        totalPwVolume0 = (Math.round(totalPwVolume0 * 10) / (10)).toFixed(1);
        $('#totalPWaterVolume0').text(totalPwVolume0);
    }

    //计算采样篮铁砂排水体积
    function calBasketIronPwVolume() {
        var basketIronDensity = Number($('#basketIronDensity').val());
        var basketIronWeight = Number($('#basketIronWeight').val());
        var basketIronPwVolume = basketIronWeight / basketIronDensity;
        basketIronPwVolume = (Math.round(basketIronPwVolume * 10) / (10)).toFixed(1);
        $('#basketIronPWaterVolume').text(basketIronPwVolume);
        calCurrentTotalWeight0();
        calCurrentTotalPwVolume0();
    }

    //计算均衡状态
    window.onload = calBalanceState;

    function calBalanceState() {
        var totalPWaterVolume0 = Number($('#totalPWaterVolume0').text());
        var leadPWaterVolume = Number($('#leadPWaterVolume').text());
        var comeupPWaterVolume = Number($('#comeupPWaterVolume').text());
        var desity = Number($('#desity').val());
        var totalWeight0 = Number($('#totalWeight0').text());
        var leadWeight = Number($('#leadWeight').val());
        var comeupWeight = Number($('#comeupWeight').val());
        var adjustWeight = Number($('#adjustWeight').val());
        var balanceState = (totalPWaterVolume0 + leadPWaterVolume + comeupPWaterVolume) * desity * 0.001 -
            (totalWeight0 + leadWeight + comeupWeight + adjustWeight);
        balanceState = (Math.round(balanceState * 10) / (10)).toFixed(1);
        $('#balanceState').text(balanceState);
    }

    //计算变化1的重力及体积
    function changeShow1() {
        var currentWindow = window.parent.document.getElementById('preOutTemplateIframe').contentWindow;
        var beforeTotalWeight1 = Number(currentWindow.document.getElementById("beforeTotalWeight1").innerHTML);
        var currentTotalWeight1 = Number($('#totalWeight0').text());
        var changeWeight1 = currentTotalWeight1 - beforeTotalWeight1;
        changeWeight1 = (Math.round(changeWeight1 * 10) / (10)).toFixed(1);
        $('#changeWeight1').text(changeWeight1);
        var beforeTotalPWaterVolume1 = Number(currentWindow.document.getElementById("beforeTotalPWaterVolume1").innerHTML);
        var totalPWaterVolume0 = Number($('#totalPWaterVolume0').text());
        var changePwVolume1 = totalPWaterVolume0 - beforeTotalPWaterVolume1;
        changePwVolume1 = (Math.round(changePwVolume1 * 10) / (10)).toFixed(1);
        $('#changePwVolume1').text(changePwVolume1);
    }

    function changeShow2() {
        // document.getElementById('preOutTemplateIframe');
        //先获取父窗口，再获取子窗口
        var currentWindow = window.parent.document.getElementById('preOutTemplateIframe').contentWindow;
        var beforetTotalWeight2 = Number(currentWindow.document.getElementById('beforeTotalWeight2').innerHTML);
        var currentTotalWeight2 = Number($('#totalWeight1').text());
        var changeWeight2 = currentTotalWeight2 - beforetTotalWeight2;
        changeWeight2 = (Math.round(changeWeight2 * 10) / (10)).toFixed(1);
        $('#changeWeight2').text(changeWeight2);
        var beforeTotalPWaterVolume2 = Number(currentWindow.document.getElementById("beforeTotalPWaterVolume2").innerHTML);
        var totalPWaterVolume2 = Number($('#totalPWaterVolume1').text());
        var changePwVolume2 = totalPWaterVolume2 - beforeTotalPWaterVolume2;
        changePwVolume2 = (Math.round(changePwVolume2 * 10) / (10)).toFixed(1);
        $('#changePwVolume2').text(changePwVolume2);
    }

    /**
     * 计算浮力损失
     */
    function calBuoyancyLoss() {
        var divingDepth = $('#selectDepth').text();
        var desity = $('#desity').val();
        if (desity != '' && divingDepth != '') {
            var buoyancyLoss = 5327.24 * 13 / desity + 9716 * desity * 9.80665 * divingDepth / 2800000000 + 69.5 * divingDepth / 4500;
            buoyancyLoss = (Math.round(buoyancyLoss * 10) / (10)).toFixed(1);
            $('#buoyancyLoss').text(buoyancyLoss)
        }
        calCurrentTotalPwVolume0();
        calBalanceState();
    }

    $(document).ready(function () {

        <%--$("#selectDepth").text(${depth});--%>
        <%--for (var i = 0; i < depthDesityParameters.length; i++) {--%>
        <%--var depthDesityId = depthDesityParameters[i].id;--%>
        <%--var density = depthDesityParameters[i].density;--%>
        <%--if ($("#selectDepth").val() == depthDesityId) {--%>
        <%--$('#desity').text(density);--%>
        <%--}--%>
        <%--}--%>
        <%--$(function () {--%>
        <%--var desity = Number($('#desity').text());--%>
        <%--// var depthId = $("#selectDepth").val();--%>
        <%--var depthId = ${depth};--%>
        <%--var depth;--%>
        <%--for (var i = 0; i < depthDesityParameters.length; i++) {--%>
        <%--if (depthId == depthDesityParameters[i].id) {--%>
        <%--depth = depthDesityParameters[i].depth;--%>
        <%--}--%>
        <%--}--%>
        <%--if (typeof desity != "undefined" && typeof depth != "undefined") {--%>
        <%--var buoyancyLoss = 5327.24 * 13 / desity + 9716 * desity * 9.80665 * depth / 2800000000 + 69.5 * depth / 4500;--%>
        <%--buoyancyLoss = (Math.round(buoyancyLoss * 10) / (10)).toFixed(1);--%>
        <%--$('#buoyancyLoss').text(buoyancyLoss)--%>
        <%--}--%>
        <%--});--%>
        $('#workSeaArea').val(${workSeaArea});
        setValue(personStatisticsRowData, true);
        setValue(toolStatisticsRowData, false);

        $('#peizhongQk').text($('#leadWeight').val());
        $('#comeupLoad').text($('#comeupWeight').val());
        $('#adjustLoad').text($('#adjustWeight').val());
        $('#currentBasketWeight').text($('#basketWeight').text());

        $(function () {
            $("input").trigger("change");
        });

        layui.use('form', function () {
            var form = layui.form;

            form.on('select(workSeaAreaSelectFilter)', function () {
                var chooseId = $("#workSeaArea").val();
                $('#desity').val("");
                var divingDepth = $('#selectDepth').text();
                $('#buoyancyLoss').text("");
                if (chooseId != '' && divingDepth != '') {
                    $.ajax({
                        url: __ctx + '/accountingForm/getDensityBySeaAreaAndDepth.rdm',
                        async: false,
                        data: {
                            "deptyDesityTypeId": chooseId,
                            "divingDepth": divingDepth
                        },
                        success: function (data) {
                            //json字符串转为json数组
                            var result = JSON.parse(data).results;
                            if (result != null) {
                                $('#desity').val(result);
                                calBuoyancyLoss();
                            }
                        }
                    });
                }
                calBalanceState();
                form.render('select');
            });
        });

        if (typeof isCanEdit != "undefined" && isCanEdit == 'false') {
            $("input").attr("readonly", true);
            $('select').attr("disabled", "disabled");
            $("input[type='button']").attr("disabled", true);
            $("textarea").attr("readonly", true);
            $("#buttonGroup").remove();
        }

        // form.on('select(selectDepthSelect)', function () {
        //     var chooseId = $("#selectDepth").val();
        //     if (chooseId == -1) {
        //         $('#desity').text("");
        //     } else {
        //         for (var i = 0; i < depthDesityParameters.length; i++) {
        //             var depthDesityId = depthDesityParameters[i].id;
        //             var depth=depthDesityParameters[i].depth;
        //             var density = depthDesityParameters[i].density;
        //             if (chooseId == depthDesityId) {
        //                 $('#desity').text(density);
        //                 var buoyancyLoss = 5327.24 * 13 / density + 9716 * density * 9.80665 * depth / 2800000000 + 69.5 * depth / 4500
        //                 buoyancyLoss=(Math.round(buoyancyLoss * 10) / (10)).toFixed(1);
        //                 $('#buoyancyLoss').text(buoyancyLoss)
        //             }
        //         }
        //         var buoyancyLoss = Number($('#buoyancyLoss').text());
        //         var basketPWaterVolume = Number($('#basketPWaterVolume').text());
        //         var basketIronPWaterVolume = Number($('#basketIronPWaterVolume').val());
        //         var dDevicePWaterVolume = Number($('#dDevicePWaterVolume').text());
        //         var totalPwVolume0 = Number(dDevicePWaterVolume) + basketPWaterVolume+basketIronPWaterVolume - buoyancyLoss;
        //         totalPwVolume0 = (Math.round(totalPwVolume0 * 10) / (10)).toFixed(1);
        //         $('#totalPWaterVolume0').text(totalPwVolume0);
        //     }
        // });

        // form.on('select(selectPersonSelect0)', function (data) {
        //     var chooseId = $("#selectPerson0").val();
        //     if (chooseId == -1) {
        //         $('#weight0').text("");
        //     } else {
        //         for (var i = 0; i < personWeightList.length; i++) {
        //             var personId = personWeightList[i].id;
        //             var weight = personWeightList[i].weight;
        //             if (chooseId == personId) {
        //                 $('#weight0').text(weight);
        //             }
        //         }
        //     }
        //     calCabinWeight();
        // });
        // form.on('select(selectPersonSelect1)', function (data) {
        //     var chooseId = $("#selectPerson1").val();
        //     if (chooseId == -1) {
        //         $('#weight1').text("");
        //     } else {
        //         for (var i = 0; i < personWeightList.length; i++) {
        //             var personId = personWeightList[i].id;
        //             var weight = personWeightList[i].weight;
        //             if (chooseId == personId) {
        //                 $('#weight1').text(weight);
        //             }
        //         }
        //     }
        //     calCabinWeight();
        // });
        //     form.on('select(selectPersonSelect2)', function (data) {
        //         var chooseId = $("#selectPerson2").val();
        //         if (chooseId == -1) {
        //             $('#weight2').text("");
        //         } else {
        //             for (var i = 0; i < personWeightList.length; i++) {
        //                 var personId = personWeightList[i].id;
        //                 var weight = personWeightList[i].weight;
        //                 if (chooseId == personId) {
        //                     $('#weight2').text(weight);
        //                 }
        //             }
        //         }
        //         calCabinWeight();
        //     });
        //     form.render();
        // });

        //封装数据
        function packageData() {
            calBuoyancyLoss();
            var fillTableDate = $("#fillTableDate").val();
            var depth = $("#selectDepth").text();
            var floatDepth=$("#floatDepth").text();
            var desity = $("#desity").val();
            var buoyancyLoss = $("#buoyancyLoss").text();
            var comeupDesity = $("#comeupDesity").val();
            var basketWeight = $("#basketWeight").text();
            var basketIronWeight = $("#basketIronWeight").val();
            var basketPWaterVolume = $("#basketPWaterVolume").text();
            var basketIronPWaterVolume = $("#basketIronPWaterVolume").text();
            var leadWeight = $("#leadWeight").val();
            var comeupWeight = $("#comeupWeight").val();
            var adjustWeight = $("#adjustWeight").val();
            var divingLoad = $("#divingLoad").val();
            var mercury = $("#mercury").val();
            var personStatisticsRowData = GetValue(true);
            var toolStatisticsRowData = GetValue(false);
            var peizhongPeople = $("#peizhongPeople").val();
            var checker = $("#checker").val();
            var departLeader = $("#departLeader").val();
            var explain = $("#explain").val();
            var planFloatHours = $("#planFloatHours").val();
            var workSeaArea = $("#workSeaArea").val();
            var basketIronDensity = $("#basketIronDensity").val();
            var cabinWeight=$("#cabinWeight").text();
            var totalWeight0=$("#totalWeight0").text();
            var totalPWaterVolume0=$("#totalPWaterVolume0").text();
            var leadPWaterVolume=$("#leadPWaterVolume").text();
            var comeupPWaterVolume=$("#comeupPWaterVolume").text();
            var totalWeight1=$("#totalWeight1").text();
            var totalPWaterVolume1=$("#totalPWaterVolume1").text();
            var balanceState=$("#balanceState").text();
            var currentOutTemplateBean = {
                peizhongId: peizhongId,
                fillTableDate: fillTableDate,
                depth: depth,
                desity: desity,
                buoyancyLoss: buoyancyLoss,
                comeupDesity: comeupDesity,
                basketWeight: basketWeight,
                basketIronWeight: basketIronWeight,
                basketPWaterVolume: basketPWaterVolume,
                basketIronPWaterVolume: basketIronPWaterVolume,
                leadWeight: leadWeight,
                comeupWeight: comeupWeight,
                adjustWeight: adjustWeight,
                divingLoad: divingLoad,
                mercury: mercury,
                personStatisticsRowData: JSON.stringify(personStatisticsRowData),
                toolStatisticsRowData: JSON.stringify(toolStatisticsRowData),
                peizhongPeople: peizhongPeople,
                checker: checker,
                departLeader: departLeader,
                explain: explain,
                planFloatHours: planFloatHours,
                workSeaArea: workSeaArea,
                basketIronDensity: basketIronDensity,
                taskId: taskId,
                taskName: taskName,
                cabinWeight:cabinWeight,
                totalWeight0:totalWeight0,
                totalPWaterVolume0:totalPWaterVolume0,
                leadPWaterVolume:leadPWaterVolume,
                comeupPWaterVolume:comeupPWaterVolume,
                totalWeight1:totalWeight1,
                totalPWaterVolume1:totalPWaterVolume1,
                balanceState:balanceState,
                floatDepth:floatDepth
            };
            return currentOutTemplateBean;
        }

        $("#outTemplateSave").click(function () {
            var currentOutTemplateBean = packageData();
            $.ajax({
                url: __ctx + '/accountingForm/submitOutTemplateTable.rdm',
                type: "post",
                async: false,
                data: currentOutTemplateBean,
                success: function (data) {
                    //刷新当前页面
                    // document.location.reload();
                    window.parent.document.getElementById('currentOutTemplateIframe').src = 'accountingForm/getOutTemplateTable.rdm?taskId=' + taskId + '&taskName=' + taskName + '&hangduanId=' + hangduanId + '&peizhongId=' + peizhongId + '&isCanEdit=' + true + '&isOnlyView=' + true
                }
            });
        });

        $("#outTemplateSubmit").click(function () {
            var currentOutTemplateBean = packageData();
            $.ajax({
                url: __ctx + '/accountingForm/submitOutTemplateTable.rdm?submitType=' + true,
                type: "post",
                async: false,
                data: currentOutTemplateBean,
                success: function (data) {
                    //刷新当前页面
                    // document.location.reload();
                    window.parent.document.getElementById('currentOutTemplateIframe').src = 'accountingForm/getOutTemplateTable.rdm?taskId=' + taskId + '&taskName=' + taskName + '&hangduanId=' + hangduanId + '&peizhongId=' + peizhongId + '&isCanEdit=' + true + '&isOnlyView=' + true
                }
            });
        });
        // $("#outTemplateExport").click(function () {
        //     $("#myWord").wordExport2('深海勇士均衡计算表');
        // });

        //-----------------获取表单中的值----start--------------
        function GetValue(isPersonOrTool) {
            var tableRowData = [];
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
                    if (i == 0) {
                        if (isPersonOrTool) {
                            var name = $(this).children().eq(1).text();
                            //去掉所有空格
                            name = name.replace(/\s*/g, "");
                            //去掉回车换行
                            name = name.replace(/[\r\n]/g, "");
                        }
                        var weight = $(this).children().eq(2).children().eq(0).val();
                        //去掉所有空格
                        weight = weight.replace(/\s*/g, "");
                        //去掉回车换行
                        weight = weight.replace(/[\r\n]/g, "");
                        // var pwVolume = $(this).children().eq(3).children().eq(0).val();
                    } else {
                        if (isPersonOrTool) {
                            var name = $(this).children().eq(0).text();
                            //去掉所有空格
                            name = name.replace(/\s*/g, "");
                            //去掉回车换行
                            name = name.replace(/[\r\n]/g, "");
                        }
                        var weight = $(this).children().eq(1).children().eq(0).val();
                        //去掉所有空格
                        weight = weight.replace(/\s*/g, "");
                        //去掉回车换行
                        weight = weight.replace(/[\r\n]/g, "");

                        // var pwVolume = $(this).children().eq(2).children().eq(0).val();
                    }
                    // if (name != null && name != "") {
                    var singleObject = new Object();
                    if (isPersonOrTool) {
                        singleObject.name = name;
                    }
                    singleObject.weight = weight;
                    // singleObject.pwVolume = pwVolume;
                    // singleObject.rowNumber =i;
                    if (isPersonOrTool) {
                        if (singleObject.name != '') {
                            tableRowData.push(singleObject);
                        }
                    } else {
                        tableRowData.push(singleObject);
                    }
                    // }
                }
            });
            return tableRowData;
        }

        function setValue(statisticsRowData, isPersonOrTool) {
            var tableRowData = [];
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
                                            tr.children().eq(2).children().eq(0).val(statisticsRowData[j].weight);
                                            break;
                                        }
                                    }
                                }())
                            } else {
                                $(this).children().eq(2).children().eq(0).val(statisticsRowData[i].weight);
                            }
                            // $(this).children().eq(3).children().eq(0).val(statisticsRowData[i].pwVolume);
                        } else {
                            if (isPersonOrTool) {
                                var name = $(this).children().eq(0).text();
                                var tr = $(this);
                                (function () {
                                    for (var j = 0; j < statisticsRowData.length; j++) {
                                        if (name == statisticsRowData[j].name) {
                                            tr.children().eq(1).children().eq(0).val(statisticsRowData[j].weight);
                                            break;
                                        }
                                    }
                                }())
                            } else {
                                $(this).children().eq(1).children().eq(0).val(statisticsRowData[i].weight);
                            }
                            // $(this).children().eq(2).children().eq(0).val(statisticsRowData[i].pwVolume);
                        }
                    }
                }
            });
        }
    })
</script>
</body>
</html>
