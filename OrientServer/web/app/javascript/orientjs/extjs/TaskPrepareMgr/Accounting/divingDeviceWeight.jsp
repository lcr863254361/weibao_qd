<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 2020/1/11
  Time: 14:41
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
    <%--<link rel="stylesheet" href="${ctx}/app/javascript/lib/layui-2.5.5/src/css/layui.css">--%>
    <script type="text/javascript"
            src="${ctx}/app/javascript/orientjs/extjs/TaskPrepareMgr/Accounting/js/divingDeviceRowOperation.js"></script>
    <script type="text/javascript"
            src="${ctx}/app/javascript/orientjs/extjs/TaskPrepareMgr/Accounting/js/select-ui.min.js"></script>
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
    <%--<script>--%>
    <%--$(document).ready(function () {--%>
    <%--$(".selectDiving").uedSelect({--%>
    <%--width:100--%>
    <%--})--%>
    <%--})--%>
    <%--</script>--%>
</head>
<body>
<form action="" method="post" name="formTable1">
    <table height="50%" border="1" align="center" width="50%" cellspacing=0 cellpadding=0>
        <tr>
            <td align="center" valign="middle">项目</td>
            <td align="center" valign="middle">设备</td>
            <td align="center" valign="middle">空气重量</td>
            <td align="center" valign="middle">排水体积</td>
            <td align="center" valign="middle">操作</td>
        </tr>
        <tr id="remianRow">
            <td align="center">相对潜次</td>
            <td align="center">
                <%--<input name="divingDevice" type="text" id="divingDevice" style="width: 100%;height: 100%;border:none;">--%>
                <select class="selectDiving" id="divingDevice" onchange="changeDivingType(this.value)">
                    <option value="0">无</option>
                    <c:forEach var="device" items="${showTaskList}">
                        <option value="${device.id}"
                            ${device.id eq chooseDivingTaskId?'selected':' '}>
                                ${device.name}
                        </option>
                    </c:forEach>
                </select>
            </td>
            <td>
                <input name="divingAirWeight" type="number" id="divingAirWeight"
                       style="width: 100%;height: 100%;border:none;" value="${divingAirWeight}" class="deal"
                       onmousewheel="stopScrollFun()"
                       onDOMMouseScroll="stopScrollFun()">
                <span id="divingAirWeightMsg"></span>
            </td>
            <td>
                <input name="divingPWaterVolume" type="number" id="divingPWaterVolume"
                       style="width: 100%;height: 100%;border:none;" value="${divingPWaterVolume}" class="deal"
                       onmousewheel="stopScrollFun()"
                       onDOMMouseScroll="stopScrollFun()">
                <span id="divingPwVolumeMsg"></span>
            </td>
            <td style="width:40px">
                <input type="button" name="clear" value="清空" style="width: 100%;height: 100%;border:none;"
                       onclick="clearSelectedRow('remianRow')"/>
            </td>
        </tr>
        <tr>
            <td colspan="5" style="border: none" style="width: 100%;padding: 0">
                <table id="decreaseTable" height="" border="1" align="center" frame="void" width="100% " cellspacing=0
                       cellpadding=0>
                    <tr id="deRow0">
                        <td align="center" valign="middle" style="width: 58px;">拆</td>
                        <td>
                            <input name="decreaseDevice0" type="text" id="decreaseDevice0"
                                   style="width: 100%;height: 100%;border:none;">
                        </td>
                        <td>
                            <input name="decreaseAirWeight0" type="number" id="decreaseAirWeight0"
                                   style="width: 100%;height: 100%;border:none;" class="deal"
                                   onmousewheel="stopScrollFun()"
                                   onDOMMouseScroll="stopScrollFun()">
                        </td>
                        <td>
                            <input name="decreasePWaterVolume0" type="number" id="decreasePWaterVolume0"
                                   style="width: 100%;height: 100%;border:none;" class="deal"
                                   onmousewheel="stopScrollFun()"
                                   onDOMMouseScroll="stopScrollFun()">
                        </td>
                        <td style="width:40px">
                            <input type="button" name="clear" value="清空" style="width: 100%;height: 100%;border:none;"
                                   onclick="clearSelectedRow('deRow0')"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="center" colspan="8">
                            <br/>
                            <input type="button" name="insert" value="增加一行" style="width:80px"
                                   onclick="insertNewRow(null,true)"/>

                        </td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td colspan="5" style="border: none" style="width: 100%;padding: 0">
                <table id="increaseTable" height="" border="1" align="center" frame="void" width="100% " cellspacing=0
                       cellpadding=0>
                    <tr id="inRow0">
                        <td align="center" valign="middle" style="width: 58px;">装</td>
                        <td>
                            <input name="increaseDevice0" type="text" id="increaseDevice0"
                                   style="width: 100%;height: 100%;border:none;">
                        </td>
                        <td>
                            <input name="increaseAirWeight0" type="number" id="increaseAirWeight0"
                                   style="width: 100%;height: 100%;border:none;" class="deal"
                                   onmousewheel="stopScrollFun()"
                                   onDOMMouseScroll="stopScrollFun()">
                        </td>
                        <td colspan="2">
                            <input name="increasePWaterVolume0" type="number" id="increasePWaterVolume0"
                                   style="width: 100%;height: 100%;border:none;" class="deal"
                                   onmousewheel="stopScrollFun()"
                                   onDOMMouseScroll="stopScrollFun()">
                        </td>
                        <td style="width:40px">
                            <input type="button" name="clear" value="清空" style="width:40px"
                                   onclick="clearSelectedRow('inRow0')"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="center" colspan="9">
                            <br/>
                            <input type="button" name="insert" value="增加一行" style="width:80px"
                                   onclick="insertNewRow(null,false)"/>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td align="center" valign="middle">合计</td>
            <td></td>
            <td id="totalAirWeight" name="totalAirWeight">${totalAirWeight}</td>
            <td colspan="2" id="totalPWaterVolume" name="totalPWaterVolume">${totalPWaterVolume}</td>
        </tr>
    </table>
    <p style=" margin:0 auto; text-align:center;"><input type="button" value="保存" id="deviceSubmit"
                                                         style="font-family: bold;font-size: large"/></p>
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

    //二级联动
    function changeDivingType(val) {
        $.ajax({
            url: __ctx + '/accountingForm/getRefDivingDeviceData.rdm?taskId=' + taskId + '&deviceId=' + val,
            type: "post",
            async: false,
            success: function (data) {
                var result = JSON.parse(data);
                if (result.length > 0) {
                    for (var i = 0; i < result.length; i++) {
                        $("#divingAirWeight").val(result[i].divingAirWeight);
                        $("#divingPWaterVolume").val(result[i].divingPWaterVolume);
                    }
                } else {
                    $("#divingAirWeight").val(" ");
                    $("#divingPWaterVolume").val(" ");
                }
                $("#totalAirWeight").text(" ");
                $("#totalPWaterVolume").text(" ");
            }
        });
    }

    $(document).ready(function () {

        var deTableArrays = new Array();
        <c:forEach items="${decreaseRowData}" var="item" varStatus="id">
        <%--var deviceId=${item.deviceId};--%>
        <%--var rowIndex=${item.rowNumber};--%>
        var module = {
            "deviceName": "${item.deviceName}",
            "airWeight": "${item.airWeight}",
            "pwVolume": "${item.pwVolume}",
            "rowIndex": "${item.rowNumber}"
        };
        deTableArrays.push(module);
        </c:forEach>
        for (var j = 0; j < deTableArrays.length; j++) {
            var rowIndex = deTableArrays[j].rowIndex;
            if (rowIndex == 0) {
                $("#decreaseDevice0").val(deTableArrays[j].deviceName);
                $("#decreaseAirWeight0").val(deTableArrays[j].airWeight);
                $("#decreasePWaterVolume0").val(deTableArrays[j].pwVolume);
            } else {
                insertNewRow(deTableArrays[j], true);
            }
        }
        var inTableArrays = new Array();
        <c:forEach items="${increaseRowData}" var="item" varStatus="id">
        <%--var deviceId=${item.deviceId};--%>
        <%--var rowIndex=${item.rowNumber};--%>
        var module = {
            "deviceName": "${item.deviceName}",
            "airWeight": "${item.airWeight}",
            "pwVolume": "${item.pwVolume}",
            "rowIndex": "${item.rowNumber}"
        };
        inTableArrays.push(module);
        </c:forEach>
        for (var j = 0; j < inTableArrays.length; j++) {
            var rowIndex = inTableArrays[j].rowIndex;
            if (rowIndex == 0) {
                $("#increaseDevice0").val(inTableArrays[j].deviceName);
                $("#increaseAirWeight0").val(inTableArrays[j].airWeight);
                $("#increasePWaterVolume0").val(inTableArrays[j].pwVolume);
            } else {
                insertNewRow(inTableArrays[j], false);
            }
        }
        $("#deviceSubmit").click(function () {
            if ($('#divingAirWeight').val().replace(' ', '') == '' || $('#divingAirWeight').val() == null) {
                $('#divingAirWeightMsg').css("color", 'red');
                $('#divingAirWeightMsg').text("潜水器空气重量不能为空!");
                return false;
            } else {
                $('#divingAirWeightMsg').text("");
            }
            if ($('#divingPWaterVolume').val().replace(' ', '') == '' || $('#divingPWaterVolume').val() == null) {
                $('#divingPwVolumeMsg').css("color", 'red');
                $('#divingPwVolumeMsg').text("潜水器排水体积不能为空!");
                return false;
            }
            var divingDevice = $("#divingDevice").val();
            var divingAirWeight = $("#divingAirWeight").val();
            var divingPWaterVolume = $("#divingPWaterVolume").val();
            var deTableRowData = GetValue(true);
            var inTableRowData = GetValue(false);

            var divingDeviceTableBean = {
                divingDevice: divingDevice,
                divingAirWeight: divingAirWeight,
                divingPWaterVolume: divingPWaterVolume,
                deTableRowData: JSON.stringify(deTableRowData),
                inTableRowData: JSON.stringify(inTableRowData),
                taskId: taskId
            };
            $.ajax({
                url: __ctx + '/accountingForm/submitDivingDeviceTable.rdm',
                type: "post",
                async: false,
                data: divingDeviceTableBean,
                success: function (data) {
                    //刷新当前页面
                    document.location.reload();
                }
            });
        });
    });
</script>
</body>
</html>
