//声明全局变量
var formvalue = "";
var flag = 1;
var index = 1;
var firstCell = "";
var secondCell = "";
var thirdCell = "";
var fourthCell = "";
var fifthCell = "";
var sixCell = "";
var sevenCell = "";
var eight = "";
var nine = "";
var cabinFlag = 1;
// $(function() {
//     //初始化第一行
//     firstCell = $("#row0 td:eq(0)").html();
//     secondCell = $("#row0 td:eq(1)").html();
//     thirdCell = $("#row0 td:eq(2)").html();
//     fourthCell = $("#row0 td:eq(3)").html();
// });

//-----------------新增一行-----------start---------------
function insertNewRow(hasDeviceId, rowNumber, carryCount, carryParameters) {
    //获取表格有多少行
    var rowLength = $("#specialTable tr").length;
    //这里的rowId就是row加上标志位的组合。是每新增一行的tr的id。
    var rowId = "row" + flag;

    //每次往下标为flag+1的下面添加tr,因为append是往标签内追加。所以用after
    var insertStr = "<tr id=" + rowId + ">"
        + "<td align='center' valign='middle' style='width: auto;' class=\"layui-input-block\">" + firstCell  + "<input type=\"text\" class=\"layui-input\" style=\"position:absolute;z-index:2;width: 80%;height: 100%;\" lay-verify=\"required\" onkeyup=\"search($(this))\" autocomplete=\"off\">\n"+ "<select  lay-filter=\"selectCarryNameSelect\" lay-verify=\"required\" lay-search>\n" +
        "                                <option value=\"\">请选择</option>\n" +
        "                            </select>" + "</td>"
        + "<td align='center' valign='middle' style='width: auto;'>" + secondCell + " <input  type=\"number\" class=\"deal\" style=\"width: 100%;height: 100%;border:none;\" onmousewheel=\"stopScrollFun()\" onDOMMouseScroll=\"stopScrollFun()\"/>" + "</td>"
        + "<td align='center' valign='middle' style='width: auto;'>" + thirdCell + " <input  type=\"number\"  class=\"deal\" style=\"width: 100%;height: 100%;border:none;\"onmousewheel=\"stopScrollFun()\" onDOMMouseScroll=\"stopScrollFun()\" />" + "</td>"
        + "<td align='center' valign='middle' style='width: auto;'>" + fourthCell + " <input  type=\"number\" class=\"deal\"  style=\"width: 100%;height: 100%;border:none;\" onmousewheel=\"stopScrollFun()\" onDOMMouseScroll=\"stopScrollFun()\"/>" + "</td>"
        + "<td align='center' valign='middle' style='width: auto;'>" + fifthCell + " <input  type=\"number\" class=\"deal\"  style=\"width: 100%;height: 100%;border:none;\" onmousewheel=\"stopScrollFun()\" onDOMMouseScroll=\"stopScrollFun()\" onkeyup=\"if(this.value.length==1){this.value=this.value.replace(/[^1-9]\\d*$/,' ')}else{this.value=this.value.replace(/\\D/g,' ')}\"\n" +
        "                                      />" + "</td>"
        + "<td align='center' valign='middle' style='width: auto;'>" + sixCell + "</td>"
        + "<td align='center' valign='middle' style='width: auto;'>" + sevenCell + " <input  type=\"text\"  style=\"width: 100%;height: 100%;border:none;\" />" + "</td>"
        + " <td align=\"center\" valign=\"middle\" style='width: auto;'>\n" + eight + "<input type=\"number\"\n" + "style=\"width: 100%;height: 100%;border:none;\" class=\"deal\" onmousewheel=\"stopScrollFun()\" onDOMMouseScroll=\"stopScrollFun()\" >\n" + "</td>"
        + " <td align=\"center\" valign=\"middle\">\n" + "<input type=\"number\"\n" + nine + "style=\"width: 100%;height: 100%;border:none;\"  class=\"deal\" onmousewheel=\"stopScrollFun()\" onDOMMouseScroll=\"stopScrollFun()\">\n" + "</td>"
        + "<td style='width:80px'><input type='button' name='delete' value='删除' style='width: 100%;height: 100%;border:none;' onclick='deleteSelectedRow(\"" + rowId + "\")' />";
    +"</td>"
    + "</tr>";
    //这里的行数减2，是因为要减去底部的一行和顶部的一行，剩下的为开始要插入行的索引
    $("#specialTable tr:eq(" + (rowLength - 8) + ")").after(insertStr); //将新拼接的一行插入到当前行的下面
    //为新添加的行里面的控件添加新的id属性。
    $("#" + rowId + " td:eq(0)").attr("id", "carryName" + flag);
    $("#" + rowId + " td:eq(1)").children().eq(0).attr("id", "airWeight" + flag);
    $("#" + rowId + " td:eq(2)").children().eq(0).attr("id", "pWaterVolume" + flag);
    $("#" + rowId + " td:eq(3)").children().eq(0).attr("id", "freshWaterVolume" + flag);
    $("#" + rowId + " td:eq(4)").children().eq(0).attr("id", "planCarryCount" + flag);
    $("#" + rowId + " td:eq(5)").attr("id", "netWeight" + flag);
    $("#" + rowId + " td:eq(6)").children().eq(0).attr("id", "connectWay" + flag);
    $("#" + rowId + " td:eq(7)").children().eq(0).attr("id", "length" + flag);
    $("#" + rowId + " td:eq(8)").children().eq(0).attr("id", "width" + flag);

    $("#" + rowId + " td:eq(0)").children().eq(0).attr("id", "cabinOutCarryToolName" + flag);
    $("#" + rowId + " td:eq(0)").children().eq(1).attr("id", "selectorCarryName" + flag);

    var selectorCarryName = $("#" + rowId + " td:eq(0)").children().eq(1)[0];
    for (var i = 0; i < carryParameters.length; i++) {
        if (carryParameters[i].isCabinOutOrIn != 'cabinIn') {
            var optionZuoxian = new Option(carryParameters[i].name, carryParameters[i].id);
            selectorCarryName.options.add(optionZuoxian);
        }
    }
    //使用layui下拉框必要的代码
    layui.use('form', function () {
        var form = layui.form;
        $("#selectorCarryName" + rowNumber).val(hasDeviceId);
        form.on('select(selectCarryNameSelect)', function (data) {
            var rowIndex = data.elem.parentNode.parentNode.rowIndex - 1;
            $("#selectorCarryName" + rowIndex).next().find("dl").removeAttr("style");
            var chooseId = $("#row" + rowIndex + " td:eq(0)").children().eq(1).val();

            //禁止选择重复行
            var specialTableRowCount = $("#specialTable tr").length - 8;
            if (specialTableRowCount > 0) {
                for (var j = 0; j < specialTableRowCount; j++) {
                    var selectorValue = $("#row" + j + " td:eq(0)").children().eq(1).val();
                    if (chooseId == selectorValue && rowIndex != j) {
                        $("#row" + rowIndex + " td:eq(0)").children().eq(0).val("");
                        $("#row" + rowIndex + " td:eq(1)").children().eq(0).val("");
                        $("#row" + rowIndex + " td:eq(2)").children().eq(0).val("");
                        $("#row" + rowIndex + " td:eq(3)").children().eq(0).val("");
                        $("#row" + rowIndex + " td:eq(4)").children().eq(0).val("");
                        $("#row" + rowIndex + " td:eq(5)").text("");
                        $("#row" + rowIndex + " td:eq(6)").children().eq(0).val("");
                        $("#row" + rowIndex + " td:eq(7)").children().eq(0).val("");
                        $("#row" + rowIndex + " td:eq(8)").children().eq(0).val("");
                        return;
                    }
                }
            }
            // var planCarryCount=$("#row" + rowIndex + " td:eq(4)").children().eq(0).val();
            if (chooseId == '') {
                $("#row" + rowIndex + " td:eq(0)").children().eq(0).val("");
                $("#row" + rowIndex + " td:eq(1)").children().eq(0).val("");
                $("#row" + rowIndex + " td:eq(2)").children().eq(0).val("");
                $("#row" + rowIndex + " td:eq(3)").children().eq(0).val("");
                $("#row" + rowIndex + " td:eq(4)").children().eq(0).val("");
                $("#row" + rowIndex + " td:eq(5)").text("");
                $("#row" + rowIndex + " td:eq(6)").children().eq(0).val("");
                $("#row" + rowIndex + " td:eq(7)").children().eq(0).val("");
                $("#row" + rowIndex + " td:eq(8)").children().eq(0).val("");
            } else {
                for (var i = 0; i < carryParameters.length; i++) {
                    var deviceId = carryParameters[i].id;
                    var airWeight = carryParameters[i].airWeight;
                    var deWaterVolume = carryParameters[i].deWaterVolume;
                    var freshWaterWeight = carryParameters[i].freshWaterWeight;
                    var planCarryCount = carryParameters[i].planCarryCount;
                    if (typeof planCarryCount=='undefined'){
                        planCarryCount=1;
                    }
                    var connectWay = carryParameters[i].connectWay;
                    var deviceName = carryParameters[i].name;
                    var length=carryParameters[i].length;
                    var width=carryParameters[i].width;
                    if (chooseId == deviceId) {
                        $("#row" + rowIndex + " td:eq(0)").children().eq(0).val(deviceName);
                        $("#row" + rowIndex + " td:eq(1)").children().eq(0).val(airWeight);
                        $("#row" + rowIndex + " td:eq(2)").children().eq(0).val(deWaterVolume);
                        $("#row" + rowIndex + " td:eq(3)").children().eq(0).val(freshWaterWeight);
                        $("#row" + rowIndex + " td:eq(4)").children().eq(0).val(planCarryCount);
                        $("#row" + rowIndex + " td:eq(6)").children().eq(0).val(connectWay);
                        $("#row" + rowIndex + " td:eq(7)").children().eq(0).val(length);
                        $("#row" + rowIndex + " td:eq(8)").children().eq(0).val(width);
                        $("#row" + rowIndex + " td:eq(7)").children().eq(0).attr("readOnly","true");
                        $("#row" + rowIndex + " td:eq(8)").children().eq(0).attr("readOnly","true");
                        // $("#row" + rowIndex + " td:eq(4)").text(planCarryCount);
                        //计算净重量
                        if (airWeight != null && airWeight != "") {
                            if (deWaterVolume != null && deWaterVolume != "") {
                                var density = $("#density").val();
                                if (density != null && density != "") {
                                    var netWeight = (airWeight - (deWaterVolume * 0.001 * density)) * planCarryCount;
                                    netWeight = (Math.round(netWeight * 10) / (10)).toFixed(1);
                                    $("#row" + rowIndex + " td:eq(5)").text(netWeight);

                                    // var specialTableRowCount = $("#specialTable tr").length - 6;
                                    // if (specialTableRowCount > 0) {
                                    //     var airAllWeight = 0;
                                    //     var pWaterAllVolume = 0;
                                    //     var freshWaterAllVolume = 0;
                                    //     for (var i = 0; i < specialTableRowCount; i++) {
                                    //         var airWeight = $('#airWeight' + i).val();
                                    //         var planCarryCount = $('#planCarryCount' + i).val();
                                    //         var pWaterVolume = $('#pWaterVolume' + i).val();
                                    //         var freshWaterVolume = $('#freshWaterVolume' + i).val();
                                    //         airAllWeight += airWeight * planCarryCount;
                                    //         pWaterAllVolume += pWaterVolume * planCarryCount;
                                    //         freshWaterAllVolume += freshWaterVolume * planCarryCount;
                                    //     }
                                    //     $('#airAllWeight').text(airAllWeight);
                                    //     $('#pWaterAllVolume').text(pWaterAllVolume);
                                    //     $('#freshWaterAllVolume').text(freshWaterAllVolume);
                                    //     var allNetWeight = airAllWeight - (pWaterAllVolume * ($("#density").text()));
                                    //     allNetWeight = (Math.round(allNetWeight * 10) / (10)).toFixed(1);
                                    //     $('#allNetWeight').text(allNetWeight);
                                    // }
                                }
                            }
                        }
                        break;
                    }
                }
            }
        });
        form.render();
    });

    //每插入一行，flag自增一次
    flag++;
    $("#combineRow td:eq(0)").attr("rowspan", flag + 1);
}

//-----------------删除一行，根据行ID删除-start--------
function deleteSelectedRow(rowID) {
    if (confirm("确定删除该行吗？")) {
        $("#" + rowID).remove();
        flag--;
        $("#combineRow td:eq(0)").attr("rowspan", $("#specialTable tr").length - 7);
        deleteAutoSave();
    }
}

function deleteCabinInSelectedRow(rowID) {
    if (confirm("确定删除该行吗？")) {
        $("#" + rowID).remove();
        cabinFlag--;
        $("#cabinCombineRow td:eq(0)").attr("rowspan", $("#cabinCarryTable tr").length - 3);
        deleteAutoSave();
    }
}

function cabinInsertNewRow(hasDeviceId, rowNumber, carryCount, carryParameters) {
    //获取表格有多少行
    var rowLength = $("#cabinCarryTable tr").length;
    //这里的rowId就是row加上标志位的组合。是每新增一行的tr的id。
    var rowId = "cabinRow" + cabinFlag;

    //每次往下标为flag+1的下面添加tr,因为append是往标签内追加。所以用after
    var insertStr = "<tr id=" + rowId + ">"
        + "<td align='center' valign='middle' style='width: auto;' class=\"layui-input-block\">" + firstCell + "<input type=\"text\" class=\"layui-input\" style=\"position:absolute;z-index:2;width: 80%;height: 100%;\" lay-verify=\"required\" onkeyup=\"search($(this))\" autocomplete=\"off\">\n" + "<select  lay-filter=\"cabinSelectCarryNameSelect\" lay-verify=\"required\" lay-search>\n" +
        "                                <option value=\"\">请选择</option>\n" +
        "                            </select>" + "</td>"
        + "<td align='center' valign='middle' style='width: auto;' colspan='3'>" + secondCell + " <input  type=\"number\" class=\"deal\"  style=\"width: 100%;height: 100%;border:none;\" onmousewheel=\"stopScrollFun()\" onDOMMouseScroll=\"stopScrollFun()\"/>" + "</td>"
        // + "<td align='center' valign='middle' style='width: auto;'>" + thirdCell + "</td>"
        // + "<td align='center' valign='middle' style='width: auto;'>" + fourthCell + "</td>"
        + "<td align='center' valign='middle' style='width: auto;'>" + fifthCell + " <input  type=\"number\" class=\"deal\" style=\"width: 100%;height: 100%;border:none;\" onmousewheel=\"stopScrollFun()\" onDOMMouseScroll=\"stopScrollFun()\" onkeyup=\"if(this.value.length==1){this.value=this.value.replace(/[^1-9]\\d*$/,' ')}else{this.value=this.value.replace(/\\D/g,' ')}\"\n" +
        "                                      />" + "</td>"
        + "<td align='center' valign='middle' style='width: auto;'>" + sixCell + "</td>"
        + "<td align='center' valign='middle' style='width: auto;'>" + sevenCell + " <input  type=\"text\"  style=\"width: 100%;height: 100%;border:none;\" />" + "</td>"
        + "<td style='width:80px'><input type='button' name='delete' value='删除' style='width: 100%;height: 100%;border:none;' onclick='deleteCabinInSelectedRow(\"" + rowId + "\")' />";
    +"</td>"
    + "</tr>";
    //这里的行数减2，是因为要减去底部的一行和顶部的一行，剩下的为开始要插入行的索引
    $("#cabinCarryTable tr:eq(" + (rowLength - 4) + ")").after(insertStr); //将新拼接的一行插入到当前行的下面
    //为新添加的行里面的控件添加新的id属性。
    $("#" + rowId + " td:eq(0)").attr("id", "cabinCarryName" + cabinFlag);
    $("#" + rowId + " td:eq(1)").children().eq(0).attr("id", "cabinAirWeight" + cabinFlag);
    // $("#" + rowId + " td:eq(2)").children().eq(0).attr("id", "cabinPWaterVolume" + cabinFlag);
    // $("#" + rowId + " td:eq(3)").children().eq(0).attr("id", "cabinFreshWaterVolume" + cabinFlag);
    $("#" + rowId + " td:eq(2)").children().eq(0).attr("id", "cabinPlanCarryCount" + cabinFlag);
    $("#" + rowId + " td:eq(3)").attr("id", "cabinNetWeight" + cabinFlag);
    $("#" + rowId + " td:eq(4)").children().eq(0).attr("id", "cabinConnectWay" + cabinFlag);

    $("#" + rowId + " td:eq(0)").children().eq(0).attr("id", "cabinCarryToolName" + cabinFlag);
    $("#" + rowId + " td:eq(0)").children().eq(1).attr("id", "cabinSelectorCarryName" + cabinFlag);

    var selectorCarryName = $("#" + rowId + " td:eq(0)").children().eq(1)[0];
    for (var i = 0; i < carryParameters.length; i++) {
        if (carryParameters[i].isCabinOutOrIn == 'cabinIn') {
            var optionZuoxian = new Option(carryParameters[i].name, carryParameters[i].id);
            selectorCarryName.options.add(optionZuoxian);
        }
    }
    //使用layui下拉框必要的代码
    layui.use('form', function () {
        var form = layui.form;
        $("#cabinSelectorCarryName" + rowNumber).val(hasDeviceId);
        form.on('select(cabinSelectCarryNameSelect)', function (data) {
            var rowIndex = data.elem.parentNode.parentNode.rowIndex - 1;
            $("#cabinSelectorCarryName" + rowIndex).next().find("dl").removeAttr("style");
            var chooseId = $("#cabinRow" + rowIndex + " td:eq(0)").children().eq(1).val();
            // var planCarryCount=$("#row" + rowIndex + " td:eq(4)").children().eq(0).val();

            //禁止选择重复行
            var cabinTableRowCount = $("#cabinCarryTable tr").length - 4;
            if (cabinTableRowCount > 0) {
                for (var j = 0; j < cabinTableRowCount; j++) {
                    var selectorValue = $("#cabinRow" + j + " td:eq(0)").children().eq(1).val();
                    if (chooseId == selectorValue && (rowIndex != j)) {
                        $("#cabinRow" + rowIndex + " td:eq(0)").children().eq(0).val("");
                        $("#cabinRow" + rowIndex + " td:eq(1)").children().eq(0).val("");
                        $("#cabinRow" + rowIndex + " td:eq(2)").children().eq(0).val("");
                        $("#cabinRow" + rowIndex + " td:eq(3)").text("");
                        $("#cabinRow" + rowIndex + " td:eq(4)").children().eq(0).val("");
                        return;
                    }
                }
            }

            if (chooseId =='') {
                $("#cabinRow" + rowIndex + " td:eq(0)").children().eq(0).val("");
                $("#cabinRow" + rowIndex + " td:eq(1)").children().eq(0).val("");
                $("#cabinRow" + rowIndex + " td:eq(2)").children().eq(0).val("");
                $("#cabinRow" + rowIndex + " td:eq(3)").text("");
                $("#cabinRow" + rowIndex + " td:eq(4)").children().eq(0).val("");
            } else {
                for (var i = 0; i < carryParameters.length; i++) {
                    var deviceId = carryParameters[i].id;
                    var airWeight = carryParameters[i].airWeight;
                    var planCarryCount = carryParameters[i].planCarryCount;
                    if (typeof planCarryCount=='undefined'){
                        planCarryCount=1;
                    }
                    var connectWay = carryParameters[i].connectWay;
                    var deviceName = carryParameters[i].name;
                    if (chooseId == deviceId) {
                        $("#cabinRow" + rowIndex + " td:eq(0)").children().eq(0).val(deviceName);
                        $("#cabinRow" + rowIndex + " td:eq(1)").children().eq(0).val(airWeight);
                        $("#cabinRow" + rowIndex + " td:eq(2)").children().eq(0).val(planCarryCount);
                        $("#cabinRow" + rowIndex + " td:eq(4)").children().eq(0).val(connectWay);
                        // $("#row" + rowIndex + " td:eq(4)").text(planCarryCount);
                        //计算净重量
                        if (airWeight != null && airWeight != "") {
                            var netWeight = airWeight * planCarryCount;
                            netWeight = (Math.round(netWeight * 10) / (10)).toFixed(1);
                            $("#cabinRow" + rowIndex + " td:eq(3)").text(netWeight);
                        }
                        break;
                    }
                }
            }
        });
        form.render();
    });

    //每插入一行，flag自增一次
    cabinFlag++;
    $("#cabinCombineRow td:eq(0)").attr("rowspan", cabinFlag + 1);
}

function deleteAutoSave() {
    calCabinTotalAirWeight();
    caltotalAirWeight();
    var divingPlanTableData = packageData();
    $.ajax({
        url: __ctx + '/accountingForm/submitDivingPlanTable.rdm',
        type: "post",
        async: false,
        data: divingPlanTableData,
        success: function (data) {
            //刷新当前页面
            document.location.reload();
        }
    });
}

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

function changeDensity() {
    var chooseId = $("#selectDivingDepth").val();
    if (chooseId =='') {
        $('#density').val("");
    } else {
        caltotalAirWeight();
    }
}

// 计算携带工具重量
function caltotalAirWeight() {
    var density = $("#density").val();
    var specialTableRowCount = $("#specialTable tr").length - 8;
    if (specialTableRowCount > 0) {
        var airAllWeight = 0;
        var pWaterAllVolume = 0;
        var freshWaterAllVolume = 0;
        for (var i = 0; i < specialTableRowCount; i++) {
            var airWeight = Number($('#airWeight' + i).val());
            var pWaterVolume = Number($('#pWaterVolume' + i).val());
            var freshWaterVolume = Number($('#freshWaterVolume' + i).val());
            var planCarryCount = Number($('#planCarryCount' + i).val());
            var netWeight = Number((airWeight - (pWaterVolume * 0.001 * density)) * planCarryCount);
            netWeight = (Math.round(netWeight * 10) / (10)).toFixed(1);
            //计算净重量
            $("#netWeight" + i).text(netWeight);
            airAllWeight += airWeight * planCarryCount;
            pWaterAllVolume += pWaterVolume * planCarryCount;
            freshWaterAllVolume += freshWaterVolume * planCarryCount;
        }
        airAllWeight = (Math.round(airAllWeight * 10) / (10)).toFixed(1);
        pWaterAllVolume = (Math.round(pWaterAllVolume * 10) / (10)).toFixed(1);
        freshWaterAllVolume = (Math.round(freshWaterAllVolume * 10) / (10)).toFixed(1);
        $('#airAllWeight').text(Number(airAllWeight));
        $('#pWaterAllVolume').text(Number(pWaterAllVolume));
        $('#freshWaterAllVolume').text(Number(freshWaterAllVolume));
        var allNetWeight = Number(Number(airAllWeight) - (Number(pWaterAllVolume) * 0.001 * (Number($("#density").val()))));
        allNetWeight = (Math.round(allNetWeight * 10) / (10)).toFixed(1);

        $('#allNetWeight').text(Number(allNetWeight));
        $('#basketWeight').text(Number(airAllWeight));
    } else {
        $('#airAllWeight').text("");
        $('#pWaterAllVolume').text("");
        $('#freshWaterAllVolume').text("");
        $('#allNetWeight').text("");
    }
}

//计算舱内携带的作业工具重量
function calCabinTotalAirWeight() {
    var cabinCarryTableRowCount = $("#cabinCarryTable tr").length - 4;
    if (cabinCarryTableRowCount > 0) {
        var airAllWeight = 0;
        for (var i = 0; i < cabinCarryTableRowCount; i++) {
            var airWeight = Number($('#cabinAirWeight' + i).val());
            var planCarryCount = Number($('#cabinPlanCarryCount' + i).val());
            var netWeight = Number(airWeight * planCarryCount);
            netWeight = (Math.round(netWeight * 10) / (10)).toFixed(1);
            //计算净重量
            $("#cabinNetWeight" + i).text(netWeight);
            airAllWeight += airWeight * planCarryCount;
        }
        airAllWeight = (Math.round(airAllWeight * 10) / (10)).toFixed(1);
        $('#cabinAirAllWeight').text(Number(airAllWeight));
        var allNetWeight = Number(airAllWeight);
        allNetWeight = (Math.round(allNetWeight * 10) / (10)).toFixed(1);
        $('#cabinAllNetWeight').text(Number(allNetWeight));
    } else {
        $('#cabinAirAllWeight').text("");
        $('#cabinAllNetWeight').text("");
    }
}

//计算计划水中时间
function calPlanWaterTime() {
    var floatToWTime = $("#planFloatToWTime").val();
    var positionTime = $("#positionTime").val();
    calPlanWaterTimeFunction(floatToWTime,positionTime)
};

function calPlanWaterTimeFunction(floatToWTime,positionTime) {
    if (floatToWTime != null && floatToWTime != "") {
        if (positionTime != null && positionTime != "") {
            var positionTimeShiftMinutes = parseInt(positionTime.substr(0, 2) * 60) + parseInt(positionTime.substr(2, 2));
            var floatToWTimeShiftMinutes = parseInt(floatToWTime.substr(0, 2) * 60) + parseInt(floatToWTime.substr(2, 2));
            var differentMinuteCount;
            //浮至水面时间小于各就各位时间，说明浮至水面时间已经是第二天
            if (floatToWTimeShiftMinutes <= positionTimeShiftMinutes) {
                differentMinuteCount = 24 * 60 - positionTimeShiftMinutes + floatToWTimeShiftMinutes;
            } else if (floatToWTimeShiftMinutes > positionTimeShiftMinutes) {
                //浮至水面时间是当天
                differentMinuteCount = floatToWTimeShiftMinutes - positionTimeShiftMinutes;
            }
            differentMinuteCount=differentMinuteCount-10;
            //计算水下呆的小时数
            var hours = parseInt((differentMinuteCount)/ 60);
            if (hours < 10) {
                hours = "0" + hours;
            }
            //小时后剩下的分钟数
            var remainMinutes = differentMinuteCount % 60;
            var allMinutes = remainMinutes;
            if (allMinutes < 10) {
                allMinutes = "0" + allMinutes;
            }
            //合并
            if (allMinutes != 0) {
                //合并
                var allHoursMinutes = "" + hours + allMinutes;
                $('#planWaterTime').text(allHoursMinutes);
            } else {
                var allHoursMinutes = "" + hours;
                $('#planWaterTime').text(allHoursMinutes);
            }
        }
    }
}

//按下计划浮至水面时间按钮后计算计划水中时间
function donePlanFloatToWTimeCalPlanWaterTime(value, date, endDate) {
    var floatToWTime = value;
    var positionTime = $("#positionTime").val();
    calPlanWaterTimeFunction(floatToWTime,positionTime);
};

//按下各就各位时间按钮后计算计划水中时间
function donePositionTimeCalPlanWaterTime(value, date, endDate) {
    var floatToWTime = $("#planFloatToWTime").val();
    var positionTime = value;
    calPlanWaterTimeFunction(floatToWTime,positionTime);
};

//-----------------获取表单中的值----start--------------
function GetValue() {
    var specialRowData = [];
    var find = false;
    $("#specialTable tr").each(function (i) {
        if (i >= 1 && i <= $("#specialTable tr").length - 8) {
            //获取每行第一个单元格里的selector控件的值
            var singleObject = new Object();
            var selectorValue = $(this).children().eq(0).children().eq(1).val();
            var newDeviceName = $(this).children().eq(0).children().eq(0).val();
            if (selectorValue == '') {
                singleObject.newDeviceName = newDeviceName;
                singleObject.length = $(this).children().eq(7).children().eq(0).val();
                singleObject.width = $(this).children().eq(8).children().eq(0).val();
            }
            singleObject.deviceId = selectorValue;
            singleObject.airWeight = $(this).children().eq(1).children().eq(0).val();
            singleObject.pWaterVolume = $(this).children().eq(2).children().eq(0).val();
            singleObject.freshWeight = $(this).children().eq(3).children().eq(0).val();
            singleObject.carryCount = $(this).children().eq(4).children().eq(0).val();
            singleObject.connectWay = $(this).children().eq(6).children().eq(0).val();
            var rowNumer = i - 1;
            singleObject.rowNumber = rowNumer;
            if (specialRowData.length > 0) {
                for (var j = 0; j < specialRowData.length; j++) {
                    var isRepeatDevice = specialRowData[j].deviceId;
                    if (isRepeatDevice === selectorValue) {
                        find = true;
                        break;
                    }
                }
            }
            if (!find) {
                if (newDeviceName != '') {
                    // value += selectorValue + "#";
                    specialRowData.push(singleObject);
                }
            }
        }
    });
    return specialRowData;
};

//获取舱内携带作业工具的数据
function GetCabinInValue() {
    var cabinInRowData = [];
    var find = false;
    $("#cabinCarryTable tr").each(function (i) {
        if (i >= 1 && i <= $("#cabinCarryTable tr").length - 4) {
            //获取每行第一个单元格里的selector控件的值
            var singleObject = new Object();
            var selectorValue = $(this).children().eq(0).children().eq(1).val();
            var newDeviceName = $(this).children().eq(0).children().eq(0).val();
            if (selectorValue == '') {
                singleObject.newDeviceName = newDeviceName;
            }
            singleObject.deviceId = selectorValue;
            singleObject.airWeight = $(this).children().eq(1).children().eq(0).val();
            singleObject.carryCount = $(this).children().eq(2).children().eq(0).val();
            singleObject.connectWay = $(this).children().eq(4).children().eq(0).val();
            var rowNumer = i - 1;
            singleObject.rowNumber = rowNumer;
            if (cabinInRowData.length > 0) {
                for (var j = 0; j < cabinInRowData.length; j++) {
                    var isRepeatDevice = cabinInRowData[j].deviceId;
                    if (isRepeatDevice === selectorValue) {
                        find = true;
                        break;
                    }
                }
            }
            if (!find) {
                if (newDeviceName != '') {
                    // value += selectorValue + "#";
                    cabinInRowData.push(singleObject);
                }
            }
        }
    });
    return cabinInRowData;
};

function getTotalNetWeight() {
    var totalNetWeight = [];
    var cabinInNetWeight = $('#cabinAirAllWeight').text();
    var cabinOutAirAllWeight = $('#airAllWeight').text();
    var cabinOutNetWeight = $('#allNetWeight').text();
    var pWaterAllVolume = $('#pWaterAllVolume').text();

    if (typeof cabinInNetWeight != 'undefined') {
        var singleObject = new Object();
        singleObject.isTotalCabinOutOrIn = "in";
        singleObject.allNetWeight = cabinInNetWeight;
        totalNetWeight.push(singleObject);
    }
    if (typeof cabinOutNetWeight != 'undefined') {
        var singleObject = new Object();
        singleObject.isTotalCabinOutOrIn = "out";
        singleObject.allNetWeight = cabinOutNetWeight;
        singleObject.pWaterAllVolume = pWaterAllVolume;
        singleObject.airAllWeight = cabinOutAirAllWeight;
        totalNetWeight.push(singleObject);
    }
    return totalNetWeight;
}

//封装数据
function packageData() {
    var numberContent = $("#numberContent").val();
    var positionTime = $("#positionTime").val();
    var palnThrowTime = $("#palnThrowTime").val();
    var planFloatToWTime = $("#planFloatToWTime").val();
    var divingType = $("input[name='divingTypeCheckBox']:checked").val();
    var seaArea = $("#seaArea").val();
    var jingdu = $("#longtitude").val();
    var weidu = $("#latitude").val();
    var planWaterTime = $("#planWaterTime").text();
    var divingDate = $("#divingDate").val();
    var density = $("#density").val();
    // var planDivingDepth = $("#selectDivingDepth").val();
    var planDivingDepth = $("#planDivingDepth").val();
    var planFloatDepth = $("#planFloatDepth").val();
    var selectZuoxian = $("#selectZuoxian").val();
    var selectMainDriver = $("#selectMainDriver").val();
    var selectYouxian = $("#selectYouxian").val();
    var specialRowData = GetValue();
    var cabinInRowData = GetCabinInValue();
    var mainTask = $("#mainTask").val();
    var workProgress = $("#workProgress").val();
    var attention = $("#attention").val();
    var homeWorkPoint = $("#homeWorkPoint").val();
    var totalNetWeight = getTotalNetWeight();
    var divingPlanTableData = {
        numberContent: numberContent,
        positionTime: positionTime,
        palnThrowTime: palnThrowTime,
        planFloatToWTime: planFloatToWTime,
        divingType: divingType,
        seaArea: seaArea,
        jingdu: jingdu,
        weidu: weidu,
        planWaterTime: planWaterTime,
        divingDate: divingDate,
        density: density,
        planDivingDepth: planDivingDepth,
        planFloatDepth: planFloatDepth,
        selectZuoxian: selectZuoxian,
        selectMainDriver: selectMainDriver,
        selectYouxian: selectYouxian,
        specialRowData: JSON.stringify(specialRowData),
        cabinInRowData: JSON.stringify(cabinInRowData),
        totalNetWeight: JSON.stringify(totalNetWeight),
        mainTask: mainTask,
        workProgress: workProgress,
        attention: attention,
        taskId: taskId,
        homeWorkPoint: homeWorkPoint
    };
    return divingPlanTableData;
}





         
