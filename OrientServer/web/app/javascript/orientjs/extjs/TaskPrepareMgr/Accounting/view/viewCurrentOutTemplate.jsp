<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 2020/8/27
  Time: 16:06
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
            <td>${taskName}</td>
            <td align="center" valign="middle">填表日期</td>
            <td>${fillTableDate}</td>
        </tr>
        <tr>
            <td align="center" valign="middle">作业海区</td>
            <td id="seaAreaName" name="seaAreaName">${seaAreaName}</td>
            <td align="center" valign="middle">下潜深度(M)</td>
            <td id="divingDepth" name="divingDepth">${depth}
        </tr>
        <tr>
            <td align="center" valign="middle">密度</td>
            <td name="desity" id="desity" name="desity">${desity}</td>
            <td align="center" valign="middle">浮力损失</td>
            <td name="buoyancyLoss" type="text" id="buoyancyLoss">${buoyancyLoss}</td>
        </tr>
        <tr>
            <td align="center" valign="middle">
                上浮深度(M)
            </td>
            <td id="floatDepth" name="floatDepth">${floatDepth}</td>
            <td align="center" valign="middle">计划上浮时长(min)</td>
            <td name="planFloatHours" id="planFloatHours">${planFloatHours}
            </td>
        </tr>
        <tr height="20">

            <td align="center" valign="middle">采样篮铁砂密度</td>
            <td name="basketIronDensity" id="basketIronDensity">${basketIronDensity} </td>
            <td align="center" valign="middle">
                上浮压载密度
            </td>
            <td colspan="2" name="comeupDesity" id="comeupDesity">
                ${comeupDesity}</td>
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
            </td>
            <td name="weight0" id="weight0">
            </td>
            <td align="center" valign="middle">
            </td>
        </tr>
        <tr height="20px" id="personStatistics2">
            <td align="center" valign="middle" style="width: auto;" id="selectPerson1" name="selectPerson1">
            </td>
            <td name="weight1" id="weight1">
            </td>
            <td align="center" valign="middle">
            </td>
        </tr>
        <tr height="20px" id="personStatistics3">
            <td align="center" valign="middle" style="width: auto;" id="selectPerson2" name="selectPerson2">
            </td>
            <td name="weight2" id="weight2">
            </td>
            <td align="center" valign="middle">
            </td>
        </tr>
        <tr height="20px" id="toolStatistics1">
            <td rowspan="2" align="center" valign="middle">
                工具
            </td>
            <td align="center" valign="middle">潜水器</td>
            <td name="toolWeight0" id="toolWeight0">
            </td>
            <td align="center" valign="middle">
            </td>
        </tr>
        <tr height="20px" id="toolStatistics2">
            <td align="center" valign="middle">科学家</td>
            <td name="toolWeight1" id="toolWeight1">
            </td>
            <td align="center" valign="middle">
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center" valign="middle">舱内总重</td>
            <td name="cabinWeight" id="cabinWeight" onchange="calCurrentTotalWeight0(this.value,this.id)">
            </td>
            <td id="canbinPWaterVolume" name="canbinPWaterVolume ">
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center" valign="middle">采样篮工具</td>
            <td name="basketWeight" id="basketWeight">${basketWeight}
            </td>
            <td name="basketPWaterVolume" id="basketPWaterVolume">${basketPWaterVolume}
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center" valign="middle">采样篮铁砂</td>
            <td name="basketIronWeight" id="basketIronWeight">
                ${basketIronWeight}
            </td>
            <td name="basketIronPWaterVolume" id="basketIronPWaterVolume">
                ${basketIronPWaterVolume}
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center" valign="middle">潜水器总重</td>
            <td id="dDeviceWeight" name="dDeviceWeight"
                onchange="calCurrentTotalWeight0(this.value,this.id)">${dDeviceWeight}
            </td>
            <td id="dDevicePWaterVolume" name="dDevicePWaterVolume"
                onchange="calCurrentTotalPwVolume0(this.value,this.id)">${dDevicePWaterVolume}
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
            <td name="leadWeight" type="number" id="leadWeight">
                ${leadWeight}
            </td>
            <td name="leadPWaterVolume" id="leadPWaterVolume"></td>
        </tr>
        <tr>
            <td align="center" valign="middle" colspan="2">
                上浮压载
            </td>
            <td name="comeupWeight" id="comeupWeight">
                ${comeupWeight}
            </td>
            <td id="comeupPWaterVolume" name="comeupPWaterVolume"></td>
        </tr>
        <tr>
            <td align="center" valign="middle" colspan="2">
                可调压载水
            </td>
            <td name="adjustWeight" id="adjustWeight">
                ${adjustWeight}
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
            <td align="center" valign="middle">
                均衡状态(浮力-重量)
            </td>
            <td colspan="3" id="balanceState" name="balanceState" onfocus="calBalanceState()"></td>
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
            <td rowspan="2" name="peizhongPeople" id="peizhongPeople">
                ${peizhongPeople}
            </td>
        </tr>
        <tr>
            <td align="center" valign="middle">下潜压载</td>
            <td name="divingLoad" id="divingLoad">
                ${divingLoad}
            </td>
        </tr>
        <tr>
            <td align="center" valign="middle">上浮压载</td>
            <td name="comeupLoad" id="comeupLoad">${comeupWeight}
            </td>
            <td align="center" valign="middle" rowspan="2">校核人</td>
            <td rowspan="2" name="checker" id="checker">
                ${checker}
            </td>
        </tr>
        <tr>
            <td align="center" valign="middle">可调压载水舱液位</td>
            <td name="adjustLoad" id="adjustLoad">${adjustWeight}
            </td>
        </tr>
        <tr>
            <td align="center" valign="middle">艏部水银液位</td>
            <td name="mercury" id="mercury">
                ${mercury}
            </td>
            <td align="center" valign="middle" rowspan="2">部门长</td>
            <td rowspan="2" name="departLeader" id="departLeader">
                ${departLeader}
            </td>
        </tr>
        <tr>
            <td align="center" valign="middle">采样篮工具重量</td>
            <td name="currentBasketWeight" id="currentBasketWeight">${basketWeight}
            </td>
        </tr>
        <tr>
            <td align="center" valign="middle">说明</td>
            <td colspan="3" name="explain" id="explain">
                ${explain}
            </td>
        </tr>
    </table>
    <p id="buttonGroup" style=" margin:0 auto; text-align:center;"><input type="button" id="editOutTemplate" value="编辑"
                                                                          style="color: black;font-family: bold;font-size:large"
                                                                          class="layui-btn layui-btn-primary"/>&nbsp&nbsp<input
            type="button" id="outTemplateExport" value="导出" style="color: black;font-family: bold;font-size: large"
            class="layui-btn layui-btn-primary"/>
    </p>
</form>
<script src="${ctx}/app/javascript/lib/layui-2.5.5/src/layui.js" charset="utf-8"></script>
<script>
    if (typeof jQuery !== "undefined" && typeof saveAs !== "undefined") {
        (function ($) {
            $.fn.wordExport2 = function (fileName) {
                fileName = typeof fileName !== 'undefined' ? fileName : "jQuery-Word-Export";
                var static = {
                    mhtml: {
                        top: "Mime-Version: 1.0\nContent-Base: " + location.href + "\nContent-Type: Multipart/related; boundary=\"NEXT.ITEM-BOUNDARY\";type=\"text/html\"\n\n--NEXT.ITEM-BOUNDARY\nContent-Type: text/html; charset=\"utf-8\"\nContent-Location: " + location.href + "\n\n<!DOCTYPE html>\n<html>\n_html_</html>",
                        head: "<head>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n<style>\n_styles_\n</style>\n</head>\n",
                        body: "<body>_body_</body>"
                    }
                };
                var options = {
                    maxWidth: 624
                };
                // Clone selected element before manipulating it
                var markup = $(this).clone();

                // Remove hidden elements from the output
                markup.each(function () {
                    var self = $(this);
                    if (self.is(':hidden'))
                        self.remove();
                });

                // Embed all images using Data URLs
                var images = Array();
                var img = markup.find('img');
                for (var i = 0; i < img.length; i++) {
                    // Calculate dimensions of output image
                    var w = Math.min(img[i].width, options.maxWidth);
                    var h = img[i].height * (w / img[i].width);
                    // Create canvas for converting image to data URL
                    var canvas = document.createElement("CANVAS");
                    canvas.width = w;
                    canvas.height = h;
                    // Draw image to canvas
                    var context = canvas.getContext('2d');
                    context.drawImage(img[i], 0, 0, w, h);
                    // Get data URL encoding of image
                    var uri = canvas.toDataURL("image/png");
                    $(img[i]).attr("src", img[i].src);
                    img[i].width = w;
                    img[i].height = h;
                    // Save encoded image to array
                    images[i] = {
                        type: uri.substring(uri.indexOf(":") + 1, uri.indexOf(";")),
                        encoding: uri.substring(uri.indexOf(";") + 1, uri.indexOf(",")),
                        location: $(img[i]).attr("src"),
                        data: uri.substring(uri.indexOf(",") + 1)
                    };
                }

                // Prepare bottom of mhtml file with image data
                var mhtmlBottom = "\n";
                for (var i = 0; i < images.length; i++) {
                    mhtmlBottom += "--NEXT.ITEM-BOUNDARY\n";
                    mhtmlBottom += "Content-Location: " + images[i].location + "\n";
                    mhtmlBottom += "Content-Type: " + images[i].type + "\n";
                    mhtmlBottom += "Content-Transfer-Encoding: " + images[i].encoding + "\n\n";
                    mhtmlBottom += images[i].data + "\n\n";
                }
                mhtmlBottom += "--NEXT.ITEM-BOUNDARY--";

                //TODO: load css from included stylesheet
                var styles = "";

                // Aggregate parts of the file together
                var fileContent = static.mhtml.top.replace("_html_", static.mhtml.head.replace("_styles_", styles) + static.mhtml.body.replace("_body_", markup.html())) + mhtmlBottom;

                // Create a Blob with the file contents
                var blob = new Blob([fileContent], {
                    type: "application/msword;charset=utf-8"
                });
                saveAs(blob, fileName + ".doc");
            };
        })(jQuery);
    } else {
        if (typeof jQuery === "undefined") {
            console.error("jQuery Word Export: missing dependency (jQuery)");
        }
        if (typeof saveAs === "undefined") {
            console.error("jQuery Word Export: missing dependency (FileSaver.js)");
        }
    }


    // if (typeof isSubmitTable != "undefined" && isSubmitTable == 'submit') {
    //
    // } else {
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
                <%--var selectPerson0 = '${leftDriver}';--%>
                <%--var selectPerson1 = '${mainDriver}';--%>
                <%--var selectPerson2 = '${rightDriver}';--%>
                <%--if (selectPerson0 == '-1') {--%>
                <%--$('#selectPerson0').text('');--%>
                <%--$('#weight0').text('');--%>
                <%--}--%>
                <%--if (selectPerson1 == '-1') {--%>
                <%--$('#selectPerson1').text('');--%>
                <%--$('#weight1').text('');--%>
                <%--}--%>
                <%--if (selectPerson2 == '-1') {--%>
                <%--$('#selectPerson2').text('');--%>
                <%--$('#weight2').text('');--%>
                <%--}--%>
                <%--for (var i = 0; i < result.length; i++) {--%>
                <%--if (result[i].id == selectPerson0) {--%>
                <%--$('#selectPerson0').text(result[i].name);--%>
                <%--$('#weight0').text(result[i].weight);--%>
                <%--} else if (result[i].id == selectPerson1) {--%>
                <%--$('#selectPerson1').text(result[i].name);--%>
                <%--$('#weight1').text(result[i].weight);--%>
                <%--} else if (result[i].id == selectPerson2) {--%>
                <%--$('#selectPerson2').text(result[i].name);--%>
                <%--$('#weight2').text(result[i].weight);--%>
                <%--}--%>
                <%--}--%>
            }
        })
    }
    // var depthDesityParameters;
    <%--$.ajax({--%>
    <%--url: __ctx + '/accountingForm/getDepthDesitySelectData.rdm',--%>
    <%--async: false,--%>
    <%--data: {"deptyDesityTypeId": "", "peizhongId": peizhongId},--%>
    <%--success: function (data) {--%>
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
        var personWeight0 = $('#weight0').text();
        var personWeight1 = $('#weight1').text();
        var personWeight2 = $('#weight2').text();
        var toolWeight0 = $('#toolWeight0').text();
        var toolWeight1 = $('#toolWeight1').text();
        var cabinWeight = Number(personWeight0) + Number(personWeight1) + Number(personWeight2) + Number(toolWeight0) + Number(toolWeight1);
        $('#cabinWeight').text(cabinWeight);
        var basketWeight = Number($('#basketWeight').text());
        //采样篮铁砂重力
        var basketIronWeight = Number($('#basketIronWeight').text());
        var dDeviceWeight = Number($('#dDeviceWeight').text());
        //计算合计1重力
        var totalWeight0 = Number(cabinWeight) + basketWeight + basketIronWeight + dDeviceWeight;
        totalWeight0 = (Math.round(totalWeight0 * 10) / (10)).toFixed(1);
        $('#totalWeight0').text(totalWeight0);
    }

    function calLeadPWaterVolume() {
        var leadWeight = Number($('#leadWeight').text());
        var comeupWeight = Number($('#comeupWeight').text());
        var comeupDesity = Number($('#comeupDesity').text());
        var comeupPwVolume = Number(comeupWeight / comeupDesity);
        var adjustWeight = Number($('#adjustWeight').text());
        // var comeupPwVolume = Number($('#comeupPWaterVolume').text());
        $('#peizhongQk').text(leadWeight);
        if (leadWeight != 0) {
            //计算配重铅块排水体积
            var leadPwVolume = (Number(leadWeight) - 4.2) / 11 + (4.2 / 7.85);
            var leadPwVolumes = (Math.round(leadPwVolume * 10) / (10)).toFixed(1);
            $('#leadPWaterVolume').text(leadPwVolumes);
            // var leadWeight=Number($('#leadWeight').val());
            //计算合计重量
            var totalWeight1 = Number(leadWeight) + comeupWeight + adjustWeight;
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
    function calComeupPWVolume() {
        // var comeupWeight=Number($('#comeupWeight').val());
        var leadWeight = Number($('#leadWeight').text());
        var adjustWeight = Number($('#adjustWeight').text());
        // var leadPWaterVolume = Number($('#leadPWaterVolume').text());
        var leadPWaterVolume = 0;
        if (leadWeight != 0) {
            leadPWaterVolume = (leadWeight - 4.2) / 11 + (4.2 / 7.85);
        }
        var comeupWeight = Number($('#comeupWeight').text());
        $('#comeupLoad').text(comeupWeight);
        var comeupDesity = Number($('#comeupDesity').text());
        var comeupPwVolume = comeupWeight / comeupDesity;
        var comeupPwVolumes = (Math.round(comeupPwVolume * 10) / (10)).toFixed(1);
        $('#comeupPWaterVolume').text(comeupPwVolumes);
        var totalWeight1 = comeupWeight + leadWeight + adjustWeight;
        $('#totalWeight1').text(totalWeight1);
        //计算合计排水体积
        var totalPwVolume1 = Number(comeupPwVolume) + leadPWaterVolume;
        totalPwVolume1 = (Math.round(totalPwVolume1 * 10) / (10)).toFixed(1);
        $('#totalPWaterVolume1').text(totalPwVolume1);
    }

    //计算合计重力2
    function calTotalWeight1(value) {
        var comeupWeight = Number($('#comeupWeight').text());
        var leadWeight = Number($('#leadWeight').text());
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
        $('#currentBasketWeight').text(cabinWeight);
        var basketWeight = Number($('#basketWeight').text());
        var basketIronWeight = Number($('#basketIronWeight').val());
        var dDeviceWeight = Number($('#dDeviceWeight').text());
        var totalWeight0 = cabinWeight + basketWeight + basketIronWeight + dDeviceWeight;
        totalWeight0 = (Math.round(totalWeight0 * 10) / (10)).toFixed(1);
        $('#totalWeight0').text(totalWeight0);
    }

    //计算合计1排水体积
    function calCurrentTotalPwVolume0() {
        var basketIronPWaterVolume = Number($('#basketIronPWaterVolume').text());
        var basketPWaterVolume = Number($('#basketPWaterVolume').text());
        var dDevicePWaterVolume = Number($('#dDevicePWaterVolume').text());
        var buoyancyLoss = Number($('#buoyancyLoss').text());
        var totalPwVolume0 = basketPWaterVolume + basketIronPWaterVolume + dDevicePWaterVolume - buoyancyLoss;
        totalPwVolume0 = (Math.round(totalPwVolume0 * 10) / (10)).toFixed(1);
        $('#totalPWaterVolume0').text(totalPwVolume0);
    }

    //计算均衡状态
    window.onload = function calBalanceState() {
        var totalPWaterVolume0 = Number($('#totalPWaterVolume0').text());
        var leadPWaterVolume = Number($('#leadPWaterVolume').text());
        var comeupPWaterVolume = Number($('#comeupPWaterVolume').text());
        var desity = Number($('#desity').text());
        var totalWeight0 = Number($('#totalWeight0').text());
        var leadWeight = Number($('#leadWeight').text());
        var comeupWeight = Number($('#comeupWeight').text());
        var adjustWeight = Number($('#adjustWeight').text());
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

    $("#editOutTemplate").click(function () {
        // $.ajax({
        //     url: __ctx + '/accountingForm/getOutTemplateTable.rdm',
        //     type: "post",
        //     async: false,
        //     data: {'isOnlyView':false},
        //     success: function (data) {
        //刷新当前页面
        // document.location.reload();
        window.parent.document.getElementById('currentOutTemplateIframe').src = 'accountingForm/getOutTemplateTable.rdm?taskId=' + taskId + '&taskName=' + taskName + '&hangduanId=' + hangduanId + '&peizhongId=' + peizhongId + '&isCanEdit=' + true + '&isOnlyView=' + false
        // }
        // });
    });

    $(document).ready(function () {

        var selectPerson0 = '${leftDriver}';
        var selectPerson1 = '${mainDriver}';
        var selectPerson2 = '${rightDriver}';
        (function () {
            for (var j = 0; j < personWeightList.length; j++) {
                if (personWeightList[j].id == selectPerson0) {
                    $('#selectPerson0').text(personWeightList[j].name);
                } else if (personWeightList[j].id == selectPerson1) {
                    $('#selectPerson1').text(personWeightList[j].name);
                } else if (personWeightList[j].id == selectPerson2) {
                    $('#selectPerson2').text(personWeightList[j].name);
                }
            }
        })();

        setValue(toolStatisticsRowData, false);
        setValue(personStatisticsRowData, true);

        $('#peizhongQk').text($('#leadWeight').text());
        $('#comeupLoad').text($('#comeupWeight').text());
        $('#adjustLoad').text($('#adjustWeight').text());
        $('#currentBasketWeight').text($('#basketWeight').text());
        //计算舱内总重
        calCabinWeight();
        //计算合计1排水体积
        calCurrentTotalPwVolume0();
        //计算配重铅块排水体积
        calLeadPWaterVolume();
        //计算上浮压载排水体积
        calComeupPWVolume();

        if (typeof isCanEdit != "undefined" && isCanEdit == 'false') {
            $("input").attr("readonly", true);
            $('select').attr("disabled", "disabled");
            $("input[type='button']").attr("disabled", true);
            $("textarea").attr("readonly", true);
            $("#buttonGroup").remove();
        }

        $("#outTemplateExport").click(function () {
            $("#myWord").wordExport2('深海勇士均衡计算表');
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

    })
</script>
</body>
</html>

