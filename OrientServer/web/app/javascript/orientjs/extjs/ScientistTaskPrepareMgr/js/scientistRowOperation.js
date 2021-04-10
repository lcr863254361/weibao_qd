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
        + "<td align='center' valign='middle' style='width: auto;' class=\"layui-input-block\">" + firstCell + "<input type=\"text\" class=\"layui-input\" style=\"position:absolute;z-index:2;width: 80%;height: 100%;\" lay-verify=\"required\" onkeyup=\"search($(this))\" autocomplete=\"off\">\n" + "<select  lay-filter=\"selectCarryNameSelect\" lay-verify=\"required\" lay-search>\n" +
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
    $("#specialTable tr:eq(" + (rowLength - 9) + ")").after(insertStr); //将新拼接的一行插入到当前行的下面
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
            var specialTableRowCount = $("#specialTable tr").length - 9;
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
        $("#combineRow td:eq(0)").attr("rowspan", $("#specialTable tr").length - 8);
        autoSave();
    }
}

function deleteCabinInSelectedRow(rowID) {
    if (confirm("确定删除该行吗？")) {
        $("#" + rowID).remove();
        cabinFlag--;
        $("#cabinCombineRow td:eq(0)").attr("rowspan", $("#cabinCarryTable tr").length - 3);
        autoSave();
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

            if (chooseId == -1) {
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

function autoSave() {
    calCabinTotalAirWeight();
    caltotalAirWeight();
    var specialRowData = GetValue();
    specialRowData = JSON.stringify(specialRowData);
    var cabinInRowData = GetCabinInValue();
    cabinInRowData = JSON.stringify(cabinInRowData);
    var totalNetWeight = getTotalNetWeight();
    totalNetWeight = JSON.stringify(totalNetWeight);
    var planDivingDepth = $("#selectDivingDepth").val();
    var seaArea = $('#homeSeaArea').val();
    var density = $("#density").val();
    var f = document.getElementsByTagName("form")[0];
    // 获取表单内容
    var formData = new FormData(document.getElementById("scientistPlanForm"));
    formData.set('taskId', taskId);
    formData.set('divingPlanId', divingPlanId);
    formData.set('specialRowData', specialRowData);
    formData.set('cabinInRowData', cabinInRowData);
    formData.set('totalNetWeight', totalNetWeight);
    formData.set('planDivingDepth', planDivingDepth);
    formData.set('homeSeaArea', seaArea);
    formData.set("density", density);
    $.ajax({
        url: f.action,
        type: "post",
        async: false,
        data: formData,
        //  contentType:false,取消设置请求头
        contentType: false,
        //  processData:false,设置false解决illegal invocation错误
        processData: false,
        success: function (data) {
            //刷新当前页面
            document.location.reload();
        }
    });
}
