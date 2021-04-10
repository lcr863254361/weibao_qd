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
var cabinFlag = 1;
// $(function() {
//     //初始化第一行
//     firstCell = $("#row0 td:eq(0)").html();
//     secondCell = $("#row0 td:eq(1)").html();
//     thirdCell = $("#row0 td:eq(2)").html();
//     fourthCell = $("#row0 td:eq(3)").html();
// });

//-----------------新增一行-----------start---------------
function insertNewRow(hasDeviceId, rowNumber) {
    //获取表格有多少行
    var rowLength = $("#specialTable tr").length;
    //这里的rowId就是row加上标志位的组合。是每新增一行的tr的id。
    var rowId = "row" + flag;

    //每次往下标为flag+1的下面添加tr,因为append是往标签内追加。所以用after
    var insertStr = "<tr id=" + rowId + ">"
        + "<td align='center' valign='middle' style='width: auto;'>" + firstCell + "</td>"
        + "<td align='center' valign='middle' style='width: auto;'>" + secondCell + "</td>"
        + "<td align='center' valign='middle' style='width: auto;'>" + thirdCell + "</td>"
        + "<td align='center' valign='middle' style='width: auto;'>" + fourthCell + "</td>"
        + "<td align='center' valign='middle' style='width: auto;'>" + fifthCell  + "</td>"
        + "<td align='center' valign='middle' style='width: auto;'>" + sixCell + "</td>"
        + "<td align='center' valign='middle' style='width: auto;' colspan='2'>" + sevenCell +"</td>"
    //     + "<td style='width:80px'><input type='button' name='delete' value='删除' style='width: 100%;height: 100%;border:none;' onclick='deleteSelectedRow(\"" + rowId + "\")' />";
    // +"</td>"
    + "</tr>";
    //这里的行数减2，是因为要减去底部的一行和顶部的一行，剩下的为开始要插入行的索引
    $("#specialTable tr:eq(" + (rowLength - 7) + ")").after(insertStr); //将新拼接的一行插入到当前行的下面
    //为新添加的行里面的控件添加新的id属性。
    $("#" + rowId + " td:eq(0)").attr("id", "selectorCarryName" + flag);
    $("#" + rowId + " td:eq(1)").attr("id", "airWeight" + flag);
    $("#" + rowId + " td:eq(2)").attr("id", "pWaterVolume" + flag);
    $("#" + rowId + " td:eq(3)").attr("id", "freshWaterVolume" + flag);
    $("#" + rowId + " td:eq(4)").attr("id", "planCarryCount" + flag);
    $("#" + rowId + " td:eq(5)").attr("id", "netWeight" + flag);
    $("#" + rowId + " td:eq(6)").attr("id", "connectWay" + flag);
    $("#selectorCarryName" + rowNumber).text(hasDeviceId);

    //每插入一行，flag自增一次
    flag++;
    $("#combineRow td:eq(0)").attr("rowspan", flag + 1);
}

function cabinInsertNewRow(hasDeviceId, rowNumber) {
    //获取表格有多少行
    var rowLength = $("#cabinCarryTable tr").length;
    //这里的rowId就是row加上标志位的组合。是每新增一行的tr的id。
    var rowId = "cabinRow" + cabinFlag;

    //每次往下标为flag+1的下面添加tr,因为append是往标签内追加。所以用after
    var insertStr = "<tr id=" + rowId + ">"
        + "<td align='center' valign='middle' style='width: auto;'>" + firstCell + "</td>"
        + "<td align='center' valign='middle' style='width: auto;' colspan='3'>" + secondCell +"</td>"
        // + "<td align='center' valign='middle' style='width: auto;'>" + thirdCell + "</td>"
        // + "<td align='center' valign='middle' style='width: auto;'>" + fourthCell + "</td>"
        + "<td align='center' valign='middle' style='width: auto;'>" + fifthCell  + "</td>"
        + "<td align='center' valign='middle' style='width: auto;'>" + sixCell + "</td>"
        + "<td align='center' valign='middle' style='width: auto;' colspan='2'>" + sevenCell + "</td>"
    //     + "<td style='width:80px'><input type='button' name='delete' value='删除' style='width: 100%;height: 100%;border:none;' onclick='deleteCabinInSelectedRow(\"" + rowId + "\")' />";
    // +"</td>"
    + "</tr>";
    //这里的行数减2，是因为要减去底部的一行和顶部的一行，剩下的为开始要插入行的索引
    $("#cabinCarryTable tr:eq(" + (rowLength - 3) + ")").after(insertStr); //将新拼接的一行插入到当前行的下面
    //为新添加的行里面的控件添加新的id属性。
    $("#" + rowId + " td:eq(0)").attr("id", "cabinSelectorCarryName" + cabinFlag);
    $("#" + rowId + " td:eq(1)").attr("id", "cabinAirWeight" + cabinFlag);
    $("#" + rowId + " td:eq(2)").attr("id", "cabinPlanCarryCount" + cabinFlag);
    $("#" + rowId + " td:eq(3)").attr("id", "cabinNetWeight" + cabinFlag);
    $("#" + rowId + " td:eq(4)").attr("id", "cabinConnectWay" + cabinFlag);
    $("#cabinSelectorCarryName" + rowNumber).text(hasDeviceId);

    //每插入一行，flag自增一次
    cabinFlag++;
    $("#cabinCombineRow td:eq(0)").attr("rowspan", cabinFlag + 1);
}


