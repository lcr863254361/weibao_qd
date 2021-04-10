<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 2020/2/26
  Time: 16:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--<%@include file="/app/javascript/orientjs/extjs/ScientisPicturePreivew/scientistPicturePreview.jsp" %>--%>
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
    var divingPlanId = '<%=request.getParameter("divingPlanId")%>';
    var modelId = '<%=request.getParameter("modelId")%>';
    //json字符串转为json数组
    if (typeof'${scientistPlanTableBeanList}' != "undefined") {
        var scientistPlanTableJsonString = '${scientistPlanTableBeanList}';
        var scientistPlanTableJsonStringReplace = scientistPlanTableJsonString.replace(/[\r]/g, "\\r").replace(/[\n]/g, "\\n");
        var scientistPlanTableBeanList = eval(scientistPlanTableJsonStringReplace);
    }
    if (typeof'${homeMapJson}' != "undefined") {
        var homeMapPathList = eval('${homeMapJson}');
    }
    if (typeof'${showCarryToolList}' != "undefined") {
        var showCarryToolList = eval('${showCarryToolList}');
    }
    var attachArray = '';
    if ('${attachJson}' != '') {
        attachArray = eval('${attachJson}');
    }
    var serviceName = '<%=contextPath%>';
    var fileId = '<%=request.getAttribute("fileId")%>';
</script>
<html>
<head>
    <meta charset="utf-8"/>
    <title></title>
    <script type="text/javascript" src="${ctx}/app/javascript/lib/jquery/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="${ctx}/app/javascript/lib/jquery/jquery.form.js"></script>
    <link rel="stylesheet" href="${ctx}/app/javascript/lib/layui-2.5.5/src/css/layui.css">
    <script type="text/javascript"
            src="${ctx}/app/javascript/orientjs/extjs/ScientistTaskPrepareMgr/js/scientistRowOperation.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/app/javascript/lib/ext-4.2/resources/css/ext-all.css"/>
    <script type="text/javascript" src="${ctx}/app/javascript/lib/ext-4.2/ext-all.js"></script>

    <style type="text/css">
        td {
            height: 25px;
            width: auto;
        }

        body {
            text-align: center;
        }

        .line {
            height: 50px;
            border-top: 1px solid #ddd;
            text-align: center;
        }

        .line input {
            position: relative;
            top: 3px;
            background: #fff;
            padding: 0 20px;
            margin-top: 0px;
        }

        /*去掉input 类型number中输入框右边的上下箭头按钮 */
        .deal::-webkit-outer-spin-button {
            -webkit-appearance: none;
        }

        .deal::-webkit-inner-spin-button {
            -webkit-appearance: none;
        }

        span {
            color: red;
        }
    </style>
</head>
<body>
<div id="scientistDivingPlanDiv">
    <form id="scientistPlanForm" action="<%=contextPath%>/accountingForm/submitScientistDivingPlan.rdm" method="post"
          name="formTable1"
          class="layui-form" enctype="multipart/form-data">
        <!--<div id="planTable1">-->
        <table height="auto" border="1" align="center" width="auto" cellspacing=0 cellpadding=0>
            <tr>
                <td align="center" valign="middle" colspan="4">科学家下潜作业计划</td>
            </tr>
            <tr>
                <td align="center" valign="middle">表格填写人员</td>
                <td><input name="fillPerson" type="text" id="fillPerson"
                           style="width: 100%;height: 100%;border:none;" value="${fillPerson}"></td>
                <td align="center" valign="middle">作业海区</td>
                <%--<td><input name="homeSeaArea" type="text" id="homeSeaArea"--%>
                <%--style="width: 100%;height: 100%;border:none;"--%>
                <%--value="${homeSeaArea}">--%>
                <%--</td>--%>
                <td><select id="homeSeaArea" lay-filter="selectDepthDesityTypeFilter" lay-verify="required"
                            lay->
                    <option value="">请选择</option>
                </select></td>
            </tr>
            <tr>
                <td align="center" valign="middle">计划作业经度<br>(+：东经 -：西经)</td>
                <td><input name="planHomeJingdu" type="number" id="planHomeJingdu"
                           style="width: 100%;height: 100%;border:none;" class="deal" value="${planHomeJingdu}"
                           onmousewheel="stopScrollFun()" onDOMMouseScroll="stopScrollFun()"></td>
                <td align="center" valign="middle">计划作业纬度<br>(+：北纬 -：南纬)</td>
                <td><input name="planHomeWeidu" type="number" id="planHomeWeidu"
                           style="width: 100%;height: 100%;border:none;" class="deal" value="${planHomeWeidu}"
                           onmousewheel="stopScrollFun()" onDOMMouseScroll="stopScrollFun()"></td>
            </tr>
            <tr>
                <td align="center" valign="middle">计划下潜深度(M)</td>
                <td class="layui-input-block">
                    <input type="number" name="planDivingDepth" id="planDivingDepth"
                           class="layui-input deal"
                           style="position:absolute;z-index:2;width: 80%;height: 100%;border:none;"
                           lay-verify="required" autocomplete="off"
                           onkeyup="search($(this))" onmousewheel="stopScrollFun()"
                           onDOMMouseScroll="stopScrollFun()"/>
                    <select id="selectDivingDepth"
                            lay-filter="selectDivingDepthSelect"
                            lay-verify="required"
                            lay-search>
                        <option value="">请选择</option>
                    </select></td>
                <td align="center" valign="middle">密度(Kg/m³)</td>
                <td align="center" valign="middle"><input name="density" type="number" id="density"
                                                          style="width: 100%;height: 100%;border:none;"
                                                          onchange="changeDensity()" class="deal"
                                                          onmousewheel="stopScrollFun()"
                                                          onDOMMouseScroll="stopScrollFun()"></td>
            </tr>
            <tr>
                <td align="center" valign="middle">计划上浮深度</td>
                <td class="layui-input-block" colspan="3">
                    <input type="number" name="planFloatDepth" id="planFloatDepth"
                           class="layui-input deal"
                           style="position:absolute;z-index:2;width: 80%;height: 100%;border:none;"
                           lay-verify="required" autocomplete="off"
                           onkeyup="search($(this))" onmousewheel="stopScrollFun()"
                           onDOMMouseScroll="stopScrollFun()"/>
                    <select id="selectPlanFloatDepth"
                            lay-filter="selectPlanFloatDepth"
                            lay-verify="required"
                            lay-search>
                        <option value="">请选择</option>
                    </select></td>
            </tr>
            <tr>
                <td colspan="4" style="border: none" style="width: 100%;padding: 0">
                    <table id="cabinCarryTable" height="" border="1" align="center" frame="void" width="100% "
                           cellspacing=0
                           cellpadding=0>
                        <tr id="cabinCombineRow">
                            <td align="center" valign="middle" rowspan="2">舱内携带的作业工具</td>
                            <td align="center" valign="middle">名称</td>
                            <td align="center" valign="middle" colspan="3">空气中重量(Kg)</td>
                            <%--<td align="center" valign="middle">排水体积(L)</td>--%>
                            <%--<td align="center" valign="middle">淡水中重量(Kg)</td>--%>
                            <td align="center" valign="middle">计划携带数量</td>
                            <td align="center" valign="middle">净重量(Kg)</td>
                            <td align="center" valign="middle">与潜水器连接方式</td>
                            <td align="center" valign="middle">操作</td>
                        </tr>
                        <tr id="cabinRow0" height="20">
                            <td id="cabinName0" name="cabinName0" align="center" valign="middle" style="width: auto;"
                                class="layui-input-block">
                                <input type="text" name="cabinCarryToolName0" id="cabinCarryToolName0"
                                       class="layui-input" style="position:absolute;z-index:2;width: 80%;height: 100%;"
                                       lay-verify="required" autocomplete="off" onkeyup="search($(this));"/>
                                <select type="text" id="cabinSelectorCarryName0"
                                        lay-filter="cabinSelectCarryNameSelect0"
                                        lay-verify="required" autocomplete="off" placeholder="携带工具全称"
                                        class="layui-select" lay-search>
                                    <option value="">请选择</option>
                                </select>
                            </td>
                            <td align="center" valign="middle" colspan="3">
                                <input name="cabinAirWeight0" type="number" id="cabinAirWeight0"
                                       style="width: 100%;height: 100%;border:none;" value="${cabinAirWeight0}"
                                       class="deal" onmousewheel="stopScrollFun()" onDOMMouseScroll="stopScrollFun()">
                            </td>
                            <%--<td align="center" valign="middle">--%>
                            <%--<input name="cabinPWaterVolume0" type="text" id="cabinPWaterVolume0"--%>
                            <%--style="width: 100%;height: 100%;border:none;"--%>
                            <%--value="${cabinPWaterVolume0}">--%>
                            <%--</td>--%>
                            <%--<td align="center" valign="middle">--%>
                            <%--<input name="cabinFreshWaterVolume0" type="text" id="cabinFreshWaterVolume0"--%>
                            <%--style="width: 100%;height: 100%;border:none;"--%>
                            <%--value="${cabinFreshWaterVolume0}">--%>
                            <%--</td>--%>
                            <td align="center" valign="middle">
                                <input name="cabinPlanCarryCount0" type="number" id="cabinPlanCarryCount0"
                                       style="width: 100%;height: 100%;border:none;"
                                       onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]\d*$/,' ')}else{this.value=this.value.replace(/\D/g,' ')}"
                                       value="${cabinPlanCarryCount0}"
                                       class="deal" onmousewheel="stopScrollFun()" onDOMMouseScroll="stopScrollFun()"/>
                            </td>
                            <td id="cabinNetWeight0" name="cabinNetWeight0" align="center" valign="middle"></td>
                            <td align="center" valign="middle">
                                <input name="cabinConnectWay0" type="text" id="cabinConnectWay0"
                                       style="width: 100%;height: 100%;border:none;" value="${cabinConnectWay0}">
                            </td>
                            <td style="width:80px">
                                <input type="button" name="delete" value="删除"
                                       style="width: 100%;height: 100%;border:none;"
                                       onclick="deleteCabinInSelectedRow('cabinRow0')"/>
                            </td>
                        </tr>
                        <tr>
                            <td align="center" colspan="10">
                                <br/>
                                <input type="button" name="insert" value="增加一行"
                                       style="color: black;font-family: bold;font-size: large;width:100px"
                                       onclick="cabinInsertNewRow('','','',carryParameters)"/>&nbsp&nbsp
                            </td>
                        </tr>
                        <tr>
                            <td align="center" valign="middle">舱内携带的作业工具总重量</td>
                            <td></td>
                            <td id="cabinAirAllWeight" colspan="3"></td>
                            <%--<td id="cabinPWaterAllVolume"></td>--%>
                            <%--<td id="cabinFreshWaterAllVolume"></td>--%>
                            <td></td>
                            <td align="center" valign="middle" id="cabinAllNetWeight"></td>
                            <td></td>
                            <td><input type="button" value=" 计算 "
                                       style="width: 100%;height: 100%;border:none;"
                                       onclick="calCabinTotalAirWeight()"/></td>
                        </tr>
                        <tr></tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td colspan="4" style="border: none" style="width: 100%;padding: 0">
                    <table id="specialTable" height="" border="1" align="center" frame="void" width="100% "
                           cellspacing=0
                           cellpadding=0>
                        <!--<div id="specialTableDiv">-->
                        <tr id="combineRow">
                            <td align="center" valign="middle" rowspan="2">舱外携带的作业工具</td>
                            <td align="center" valign="middle">名称</td>
                            <td align="center" valign="middle">空气中重量(Kg)</td>
                            <td align="center" valign="middle">排水体积(L)</td>
                            <td align="center" valign="middle">淡水中重量(Kg)</td>
                            <td align="center" valign="middle">计划携带数量(个)</td>
                            <td align="center" valign="middle">净重量(Kg)</td>
                            <td align="center" valign="middle">与潜水器连接方式</td>
                            <td align="center" valign="middle">长度(cm)</td>
                            <td align="center" valign="middle">宽度(cm)</td>
                            <td align="center" valign="middle">操作</td>
                        </tr>
                        <tr id="row0" height="20">
                            <td id="name0" name="name0" align="center" valign="middle" style="width: auto;"
                                class="layui-input-block">
                                <input type="text" name="cabinOutCarryToolName0" id="cabinOutCarryToolName0"
                                       class="layui-input" style="position:absolute;z-index:2;width: 80%;height: 100%;"
                                       lay-verify="required" onkeyup="search($(this))" autocomplete="off">
                                <select id="selectorCarryName0" lay-filter="selectCarryNameSelect0"
                                        lay-verify="required" lay-search>
                                    <option value="">请选择</option>
                                </select>
                            </td>
                            <td align="center" valign="middle">
                                <input name="airWeight0" type="number" id="airWeight0"
                                       style="width: 100%;height: 100%;border:none;" value="${airWeight0}" class="deal"
                                       onmousewheel="stopScrollFun()" onDOMMouseScroll="stopScrollFun()">
                            </td>
                            <td align="center" valign="middle">
                                <input name="pWaterVolume0" type="number" id="pWaterVolume0"
                                       style="width: 100%;height: 100%;border:none;"
                                       value="${pWaterVolume0}" class="deal" onmousewheel="stopScrollFun()"
                                       onDOMMouseScroll="stopScrollFun()">
                            </td>
                            <td align="center" valign="middle">
                                <input name="freshWaterVolume0" type="number" id="freshWaterVolume0"
                                       style="width: 100%;height: 100%;border:none;"
                                       value="${freshWaterVolume0}"
                                       class="deal" onmousewheel="stopScrollFun()" onDOMMouseScroll="stopScrollFun()">
                            </td>
                            <td align="center" valign="middle">
                                <input name="planCarryCount0" type="number" id="planCarryCount0"
                                       style="width: 100%;height: 100%;border:none;"
                                       onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]\d*$/,' ')}else{this.value=this.value.replace(/\D/g,' ')}"
                                       value="${planCarryCount0}" class="deal" onmousewheel="stopScrollFun()"
                                       onDOMMouseScroll="stopScrollFun()"/></td>
                            <td id="netWeight0" name="netWeight0" align="center" valign="middle"></td>
                            <td align="center" valign="middle">
                                <input name="connectWay0" type="text" id="connectWay0"
                                       style="width: 100%;height: 100%;border:none;" value="${connectWay0}">
                            </td>
                            <td align="center" valign="middle">
                                <input name="length0" type="number" id="length0"
                                       style="width: 100%;height: 100%;border:none;" class="deal"
                                       onmousewheel="stopScrollFun()" onDOMMouseScroll="stopScrollFun()">
                            </td>
                            <td align="center" valign="middle">
                                <input name="width0" type="number" id="width0"
                                       style="width: 100%;height: 100%;border:none;" class="deal"
                                       onmousewheel="stopScrollFun()" onDOMMouseScroll="stopScrollFun()">
                            </td>
                            <td style="width:80px">
                                <input type="button" name="delete" value="删除"
                                       style="width: 100%;height: 100%;border:none;"
                                       onclick="deleteSelectedRow('row0')"/>
                            </td>
                        </tr>
                        <tr>
                            <td align="center" colspan="11">
                                <br/>
                                <input type="button" name="insert" value="增加一行"
                                       style="color: black;font-family: bold;font-size: large;width:100px"
                                       onclick="insertNewRow('','','',carryParameters)"/>&nbsp&nbsp
                                <%--<input type="button" value=" 保    存 " style="width:80px" onclick="GetValue()"/>--%>
                            </td>
                        </tr>
                        <!--</div>-->
                        <tr>
                            <td align="center" valign="middle">舱外携带的作业工具总重量</td>
                            <td></td>
                            <td id="airAllWeight"></td>
                            <td id="pWaterAllVolume"></td>
                            <td id="freshWaterAllVolume"></td>
                            <td></td>
                            <td align="center" valign="middle" id="allNetWeight"></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td><input type="button" value=" 计算 "
                                       style="width: 100%;height: 100%;border:none;"
                                       onclick="caltotalAirWeight()"/></td>
                        </tr>
                        <tr>
                            <td align="center" valign="middle">主要任务</td>
                            <td align="center" valign="middle" colspan="10" style="width:100px;height:100px;">
                                <!--<input name="mainTask" type="text" id="mainTask">-->
                                <!--禁止 textarea 拉伸style="resize:none" 去掉 textarea 的边框style="border:0px"-->
                                <textarea id="mainTask" name="mainTask"
                                          class="textinput" style="width:100%;height:100%;border:0px;resize: none;outline:none;border:none;overflow-y: scroll
                          ">${mainTask}</textarea>
                            </td>
                        </tr>
                        <tr>
                            <td align="center" valign="middle">作业过程</td>
                            <td align="center" valign="middle" colspan="10" style="width:100px;height:100px;">
						<textarea id="workProgress" name="workProgress"
                                  class="textinput"
                                  style="width:100%;height:100%;border:0px;resize: none;outline:none;border:none;overflow-y: scroll">${workProgress}</textarea>
                                <!--<input name="workProgress" type="text" id="workProgress">-->
                            </td>
                        </tr>
                        <tr>
                            <td align="center" valign="middle">注意事项</td>
                            <td align="center" valign="middle" colspan="10" style="width:100px;height:100px;">
						<textarea id="attention" name="attention"
                                  class="textinput"
                                  style="width:100%;height:100%;border:0px;resize: none;outline:none;border:none;overflow-y: scroll">${attention}</textarea>
                                <!--<input name="attention" type="text" id="attention">-->
                            </td>
                        </tr>
                        <tr>
                            <td align="center" valign="middle">作业地图<span>(请点击保存按钮后，再上传附件)</span></td>
                            <%--<td colspan="8" id="pictures"><input id="uploadPics" type="file" name="uploadPics"--%>
                            <%--style="width: 100%;height: 100%;border:none;" value="上传图片"--%>
                            <%--multiple onchange="showImg(this)">--%>
                            <td colspan="10" id="pictures">
                                <input id="uploadPics" type="button" name="uploadPics"
                                       style="width: 100%;height: 100%;border:none;text-align:left;"
                                       value="上传附件"
                                       multiple onchange="showImg(this)">
                            </td>
                        </tr>
                        <tr>
                            <td align="center" valign="middle">附件详情</td>
                            <td id="picDetails" colspan="10">
                            </td>
                        </tr>
                        <tr>
                            <td align="center" valign="middle">采样篮图片详情</td>
                            <td id="basketImageDetial" colspan="10">
                                <%--<img--%>
                                <%--:src="'${ctx}/orientForm/download.rdm?fileId='+filedId"--%>
                                <%-->--%>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        <p style=" margin:0 auto; text-align:center;"><input type="button" id="scientistPlanSubmit" value="保存"
                                                             style="color: black;font-family: bold;font-size: large"
                                                             class="layui-btn layui-btn-primary"
        <%--onclick="scientistSubmit()"--%>
        />&nbsp&nbsp
            <input type="button" id="agreeScientistPlanButton" value="提交"
                   style="color: black;font-family: bold;font-size: large"
                   class="layui-btn layui-btn-primary"
            />
            <input type="button" id="skipSampleBasketButton" value="转到采样篮工具页面"
                   style="color: black;font-family: bold;font-size: large"
                   class="layui-btn layui-btn-primary"
            />
        </p>
    </form>
    <script src="${ctx}/app/javascript/lib/layui-2.5.5/src/layui.js" charset="utf-8"></script>
    <script>

        if (fileId != "") {
            var basketPic = typeof '${basketPicMap}';
            if (typeof basketPic != "undefined") {
                var basketPicMap = '${basketPicMap}';
                if (basketPicMap != '') {
                    basketPicMap = eval('(' + basketPicMap + ')');
                    $("#basketImageDetial").append([
                        '<a class="example-image-link" ' + '" href="' + __ctx + '/orientForm/download.rdm?fileId=' + basketPicMap.fileId + '" data-lightbox="orientImage" data-title="' + basketPicMap.fileName + '">',
                        '<img id="example-image' + '" src="' + __ctx + '/preview/imageSuoluetu/' + basketPicMap.sltfileName + '"' +
                        '" onclick="' + 'javaScript:window.open(\'' + __ctx + '/preview/imageSuoluetu/' + basketPicMap.finalName + '\')' +
                        '"></img>' + '&nbsp;&nbsp;',
                        '</a>'
                    ].join(""));
                }
            }
        }

        // new Vue({
        //     el:'#basketImageDetial',
        //     data:{
        //         filedId:''
        //     },
        //     mounted(){
        //         const viewer = new Viewer(document.getElementById('basketImageDetial'), {
        //             toggleOnDblclick:false
        //         });
        //     },
        //     created(){
        //         this.filedId=fileId;
        //     }
        // })

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
            if (chooseId == -1) {
                $('#density').val("");
            } else {
                caltotalAirWeight();
            }
        }

        // 计算携带工具重量
        function caltotalAirWeight(value) {
            var density = $("#density").val();

            var specialTableRowCount = $("#specialTable tr").length - 9;
            if (specialTableRowCount > 0) {
                var airAllWeight = 0;
                var pWaterAllVolume = 0;
                var freshWaterAllVolume = 0;
                for (var i = 0; i < specialTableRowCount; i++) {
                    var airWeight = Number($('#airWeight' + i).val());
                    var planCarryCount = Number($('#planCarryCount' + i).val());
                    var pWaterVolume = Number($('#pWaterVolume' + i).val());
                    var freshWaterVolume = Number($('#freshWaterVolume' + i).val());
                    // var planCarryCount=Number($('#planCarryCount'+i).val());
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

        $('#scientistPlanSubmit').click(function () {
            calCabinTotalAirWeight();
            caltotalAirWeight();
            var specialRowData = GetValue();
            specialRowData = JSON.stringify(specialRowData);
            var cabinInRowData = GetCabinInValue();
            cabinInRowData = JSON.stringify(cabinInRowData);
            var totalNetWeight = getTotalNetWeight();
            totalNetWeight = JSON.stringify(totalNetWeight);
            // var planDivingDepth = $("#selectDivingDepth").val();
            var planDivingDepth = $("#planDivingDepth").val();
            var planFloatDepth = $("#planFloatDepth").val();
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
            formData.set('planFloatDepth', planFloatDepth);
            formData.set('homeSeaArea', seaArea);
            formData.set("density", density);
            formData.set('attachFiles', attachArray == '' ? '' : JSON.stringify(attachArray));
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
        });

        $('#agreeScientistPlanButton').click(function () {
            calCabinTotalAirWeight();
            caltotalAirWeight();
            var specialRowData = GetValue();
            specialRowData = JSON.stringify(specialRowData);
            var cabinInRowData = GetCabinInValue();
            cabinInRowData = JSON.stringify(cabinInRowData);
            var totalNetWeight = getTotalNetWeight();
            totalNetWeight = JSON.stringify(totalNetWeight);
            // var planDivingDepth = $("#selectDivingDepth").val();
            var planDivingDepth = $("#planDivingDepth").val();
            var planFloatDepth = $("#planFloatDepth").val();
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
            formData.set('planFloatDepth', planFloatDepth),
                formData.set('attachFiles', attachArray == '' ? '' : JSON.stringify(attachArray));
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
                    $.ajax({
                        url: serviceName + '/accountingForm/agreeScientistPlanTable.rdm',
                        type: "post",
                        async: false,
                        data: {'taskId': taskId, 'divingPlanId': divingPlanId},
                        success: function (data) {
                            var msg = JSON.parse(data).msg;
                            alert(msg);
                            //刷新当前页面
                            document.location.reload();
                        }
                    });
                }
            })
        });

        $('#uploadPics').click(function () {
            window.location = " ${ctx}/app/javascript/orientjs/extjs/ScientistTaskPrepareMgr/h5FileUpload.jsp?taskId=" + taskId + "&divingPlanId=" + divingPlanId + "&modelId=" + modelId;
        });

        $('#skipSampleBasketButton').click(function () {
            window.location = 'getScientistPicturePreview.rdm?taskId=' + taskId + '&divingPlanId=' + divingPlanId + "&modelId=" + modelId;
        });

        //-----------------获取表单中的值----start--------------
        function GetValue() {
            var value = "";
            var specialRowData = [];
            var find = false;
            $("#specialTable tr").each(function (i) {
                if (i >= 1 && i <= $("#specialTable tr").length - 9) {
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
                            if (selectorValue != '' && (isRepeatDevice === selectorValue)) {
                                find = true;
                                break;
                            }
                        }
                    }
                    if (!find) {
                        if (newDeviceName != '') {
                            specialRowData.push(singleObject);
                        }
                    }
                }
            });
            return specialRowData;
        }

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
                            if (selectorValue != '' && (isRepeatDevice === selectorValue)) {
                                find = true;
                                break;
                            }
                        }
                    }
                    if (!find) {
                        if (newDeviceName != '') {
                            cabinInRowData.push(singleObject);
                        }
                    }
                }
            });
            return cabinInRowData;
        }

        function getTotalNetWeight() {
            var totalNetWeight = [];
            var cabinInNetWeight = $('#cabinAirAllWeight').text();
            var cabinOutNetWeight = $('#allNetWeight').text();
            var pWaterAllVolume = $('#pWaterAllVolume').text();
            var cabinOutAirAllWeight = $('#airAllWeight').text();
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

        $.ajax({
            url: __ctx + '/accountingForm/getDepthDesityTypeData.rdm',
            async: false,
            success: function (data) {
                // console.log(data);
                //JSON.parse()字符串解析成json对象
                var result = JSON.parse(data).results;
                var depthDesityType = document.getElementById("homeSeaArea");
                for (var i = 0; i < result.length; i++) {
                    var optionDepthDesityType = new Option(result[i].depthDesityTypeName, result[i].id);
                    depthDesityType.options.add(optionDepthDesityType);
                }
            }
        });

        var carryParameters;
        $.ajax({
            url: __ctx + '/accountingForm/getCarryToolList.rdm',
            async: false,
            success: function (data) {
                // console.log(data);
                //JSON.parse()字符串解析成json对象
                var result = JSON.parse(data).results;
                var selectorCarryName = document.getElementById("selectorCarryName0");
                var cabinSelectorCarryName = document.getElementById("cabinSelectorCarryName0");
                for (var i = 0; i < result.length; i++) {
                    var optionZuoxian;
                    if (result[i].isCabinOutOrIn == 'cabinIn') {
                        optionZuoxian = new Option(result[i].name, result[i].id);
                        cabinSelectorCarryName.options.add(optionZuoxian);
                    } else {
                        optionZuoxian = new Option(result[i].name, result[i].id);
                        selectorCarryName.options.add(optionZuoxian);
                    }
                }
                carryParameters = result;
            }
        });

        $(document).ready(function () {
            layui.use('form', function () {

                var form = layui.form;
                <%--$("#selectDivingDepth").val(${planDivingDepth});--%>
                $('#density').val(${density});

                var depthDesityParameters;
                form.on('select(selectDivingDepthSelect)', function () {
                    var chooseId = $("#selectDivingDepth").val();
                    if (chooseId == '') {
                        $('#density').val("");
                        $("#planDivingDepth").val("");
                    } else {
                        for (var i = 0; i < depthDesityParameters.length; i++) {
                            var depthDesityId = depthDesityParameters[i].id;
                            var density = depthDesityParameters[i].density;
                            var depth = depthDesityParameters[i].depth;
                            if (chooseId == depthDesityId) {
                                //选择深度 赋值给input框
                                $("#planDivingDepth").val(depth);
                                $('#density').val(density);
                                break;
                            }
                        }
                    }
                    $("#selectDivingDepth").next().find("dl").removeAttr("style");
                    caltotalAirWeight();
                });

                form.on('select(selectPlanFloatDepth)', function () {
                    var chooseId = $("#selectPlanFloatDepth").val();
                    (function () {
                        for (var i = 0; i < depthDesityParameters.length; i++) {
                            var depthDesityId = depthDesityParameters[i].id;
                            var depth = depthDesityParameters[i].depth;
                            if (chooseId == depthDesityId) {
                                //选择计划上浮深度 赋值给input框
                                $("#planFloatDepth").val(depth);
                                break;
                            }
                        }
                        $("#selectPlanFloatDepth").next().find("dl").removeAttr("style");
                    })();
                });

                form.on('select(selectDepthDesityTypeFilter)', function () {
                    var chooseId = $("#homeSeaArea").val();
                    $('#planDivingDepth').val('')
                    $('#selectDivingDepth').empty();
                    $('#density').val("");
                    $('#planFloatDepth').val('');
                    $('#selectPlanFloatDepth').empty();
                    if (chooseId != '') {
                        $.ajax({
                            url: __ctx + '/accountingForm/getDepthDesitySelectData.rdm',
                            async: false,
                            data: {
                                "deptyDesityTypeId": chooseId
                            },
                            success: function (data) {
                                // console.log(data);
                                //JSON.parse()字符串解析成json对象
                                var result = JSON.parse(data).results;
                                var selectorDivingDepth = document.getElementById("selectDivingDepth");
                                var selectPlanFloatDepth = document.getElementById("selectPlanFloatDepth");
                                // var defaultOption = new Option("请选择", "");
                                // selectorDivingDepth.options.add(defaultOption);
                                // defaultOption = new Option("请选择", "");
                                // selectPlanFloatDepth.options.add(defaultOption);
                                (function () {
                                    for (var i = 0; i < result.length; i++) {
                                        var optionDepth = new Option(result[i].depth, result[i].id);
                                        selectorDivingDepth.options.add(optionDepth);
                                        optionDepth = new Option(result[i].depth, result[i].id);
                                        selectPlanFloatDepth.options.add(optionDepth);
                                    }
                                    depthDesityParameters = result;
                                })();
                            }
                        });
                    }
                    caltotalAirWeight();
                    form.render('select');
                });

                if (typeof scientistPlanTableBeanList != 'undefined') {
                    for (var i = 0; i < scientistPlanTableBeanList.length; i++) {
                        var tableState = scientistPlanTableBeanList[i].tableState;
                        $('#fillPerson').val(scientistPlanTableBeanList[i].fillPerson);
                        $('#homeSeaArea').val(scientistPlanTableBeanList[i].homeSeaArea);
                        $('#density').val(scientistPlanTableBeanList[i].density);
                        $('#planHomeJingdu').val(scientistPlanTableBeanList[i].planHomeJingdu);
                        $('#planHomeWeidu').val(scientistPlanTableBeanList[i].planHomeWeidu);
                        $('#mainTask').val(scientistPlanTableBeanList[i].mainTask);
                        $('#workProgress').val(scientistPlanTableBeanList[i].workProgress);
                        $('#attention').val(scientistPlanTableBeanList[i].attention);
                        if ("当前" == tableState) {
                            $("#agreeScientistPlanButton").removeClass(" layui-btn-primary");
                            $('#agreeScientistPlanButton').addClass("layui-btn");
                        }
                        if (typeof showCarryToolList != 'undefined') {
                            for (var j = 0; j < showCarryToolList.length; j++) {
                                var rowIndex = showCarryToolList[j].rowNumber;
                                var isCabinOutOrIn = showCarryToolList[j].isCabinOutOrIn;
                                if (showCarryToolList[j].rowNumber != 0) {
                                    if ("in" == isCabinOutOrIn) {
                                        cabinInsertNewRow(showCarryToolList[j].deviceId, showCarryToolList[j].rowNumber, showCarryToolList[j].carryCount, carryParameters);
                                    } else {
                                        insertNewRow(showCarryToolList[j].deviceId, showCarryToolList[j].rowNumber, showCarryToolList[j].carryCount, carryParameters);
                                    }
                                }
                                if ("in" == isCabinOutOrIn) {
                                    $("#cabinSelectorCarryName" + rowIndex).val(showCarryToolList[j].deviceId);
                                    $("#cabinPlanCarryCount" + rowIndex).val(showCarryToolList[j].carryCount);
                                    $('#cabinAirWeight' + rowIndex).val(showCarryToolList[j].airWeight);
                                    $('#cabinConnectWay' + rowIndex).val(showCarryToolList[j].connectWay);
                                    var options = $("#cabinSelectorCarryName" + rowIndex).find("option");
                                    (function () {
                                        for (var i = 0; i < options.length; i++) {
                                            if (options[i].value == (showCarryToolList[j].deviceId)) {
                                                $('#cabinCarryToolName' + rowIndex).val(options[i].innerHTML);
                                                break;
                                            }
                                        }
                                    })();
                                    calCabinTotalAirWeight();
                                } else {
                                    $("#selectorCarryName" + rowIndex).val(showCarryToolList[j].deviceId);
                                    $("#planCarryCount" + rowIndex).val(showCarryToolList[j].carryCount);
                                    $('#airWeight' + rowIndex).val(showCarryToolList[j].airWeight);
                                    $('#pWaterVolume' + rowIndex).val(showCarryToolList[j].pWaterVolume);
                                    $('#freshWaterVolume' + rowIndex).val(showCarryToolList[j].freshWaterWeight);
                                    $('#connectWay' + rowIndex).val(showCarryToolList[j].connectWay);
                                    var options = $("#selectorCarryName" + rowIndex).find("option");
                                    (function () {
                                        // for (var i = 0; i < options.length; i++) {
                                        //     if (options[i].value == (showCarryToolList[j].deviceId)) {
                                        //         $('#cabinOutCarryToolName' + rowIndex).val(options[i].innerHTML);
                                        //     }
                                        // }
                                        for (var i = 0; i < carryParameters.length; i++) {
                                            if (carryParameters[i].isCabinOutOrIn != 'cabinIn') {
                                                if (carryParameters[i].id == (showCarryToolList[j].deviceId)) {
                                                    $('#cabinOutCarryToolName' + rowIndex).val(carryParameters[i].name);
                                                    $('#length' + rowIndex).val(carryParameters[i].length);
                                                    $('#length' + rowIndex).attr("readOnly", "true");
                                                    $('#width' + rowIndex).val(carryParameters[i].width);
                                                    $('#width' + rowIndex).attr("readOnly", "true");
                                                    break;
                                                }
                                            }
                                        }
                                    })();
                                    caltotalAirWeight();
                                }
                            }
                        }
                        if ($("#homeSeaArea").val() != '') {
                            $.ajax({
                                url: __ctx + '/accountingForm/getDepthDesitySelectData.rdm',
                                async: false,
                                data: {
                                    "deptyDesityTypeId": $("#homeSeaArea").val()
                                },
                                success: function (data) {
                                    // console.log(data);
                                    //JSON.parse()字符串解析成json对象
                                    var result = JSON.parse(data).results;
                                    var selectorDivingDepth = document.getElementById("selectDivingDepth");
                                    var selectPlanFloatDepth = document.getElementById('selectPlanFloatDepth');
                                    (function () {
                                        for (var i = 0; i < result.length; i++) {
                                            var optionDepth = new Option(result[i].depth, result[i].id);
                                            selectorDivingDepth.options.add(optionDepth)
                                            optionDepth = new Option(result[i].depth, result[i].id);
                                            selectPlanFloatDepth.options.add(optionDepth);
                                        }
                                    })();
                                    depthDesityParameters = result;
                                }
                            });
                            (function () {
                                var options = $("#selectDivingDepth").find("option");
                                var isExist = false;
                                for (var h = 0; h < options.length; h++) {
                                    if (options[h].innerHTML == (scientistPlanTableBeanList[i].planDivingDepth)) {
                                        $('#planDivingDepth').val(options[h].innerHTML);
                                        $('#selectDivingDepth').val(options[h].value);
                                        isExist = true;
                                        break;
                                    }
                                }
                                if (!isExist) {
                                    $('#planDivingDepth').val(scientistPlanTableBeanList[i].planDivingDepth);
                                }
                                options = $("#selectPlanFloatDepth").find("option");
                                isExist = false;
                                for (var k = 0; k < options.length; k++) {
                                    if (options[k].innerHTML == (scientistPlanTableBeanList[i].planFloatDepth)) {
                                        $('#planFloatDepth').val(options[k].innerHTML);
                                        $('#selectPlanFloatDepth').val(options[k].value);
                                        isExist = true;
                                        break;
                                    }
                                }
                                if (!isExist) {
                                    $('#planFloatDepth').val(scientistPlanTableBeanList[i].planFloatDepth);
                                }
                            })();
                        }
                         var isSubmitTable=scientistPlanTableBeanList[i].isSubmitTable;
                        if (typeof isSubmitTable != "undefined" && isSubmitTable == 'submit') {
                            $("input").attr("readonly", true);
                            $('select').attr("disabled", "disabled");
                            $("input[type='button']").attr("disabled", true);
                            $("textarea").attr("readonly", true);
                            $("#scientistPlanSubmit").remove();
                            $("#agreeScientistPlanButton").remove();
                            $("#skipSampleBasketButton").remove();
                        }
                    }
                    form.render('select');
                    if (homeMapPathList != "") {
                        if (homeMapPathList.length > 0) {
                            (function () {
                                for (var i = 0; i < homeMapPathList.length; i++) {
                                    $("#picDetails").append([
                                        '<a id="example-image-link" ' + '" onclick="' + 'javaScript:window.open(\'' + __ctx + '/preview' + homeMapPathList[i].filePath + '\')' + '" href="' + __ctx + '/orientForm/download.rdm?fileId=' + homeMapPathList[i].fileId + '" data-lightbox="orientImage" data-title="' + homeMapPathList[i].fileName + '">' + homeMapPathList[i].fileName
                                        + '&nbsp;&nbsp;',
                                        '</a>'
                                    ].join(""));
                                }
                            })()
                        }
                    }
                }
                if (attachArray != '') {
                    var uploadPics = document.getElementById("picDetails");
                    uploadPics.innerHTML = '';
                    (function () {
                        for (let i = 0; i < attachArray.length; i++) {
                            $("#picDetails").append([
                                '<a id="example-image-link" ' + '" onclick="' + 'javaScript:window.open(\'' + __ctx + '/preview' + attachArray[i].filePath + '\')' + '" href="' + __ctx + '/orientForm/download.rdm?fileId=' + attachArray[i].fileId + '" data-lightbox="orientImage" data-title="' + attachArray[i].filename + '">' + attachArray[i].filename + '&nbsp;&nbsp;',
                                // '<img id="example-image' + i + '" src="' + __ctx + '/preview/imageSuoluetu/' + homeMapPathList[i].sltFilePath + '"' +
                                // '" onclick="' + 'javaScript:window.open(\'' + __ctx + '/preview/imageSuoluetu/' + homeMapPathList[i].filePath + '\')' +
                                // '"></img>' + '&nbsp;&nbsp;',
                                '</a>'
                            ].join(""));
                        }
                    })();
                }

                form.on('select(selectCarryNameSelect0)', function () {
                    var chooseId = $("#selectorCarryName0").val();
                    if (chooseId == '') {
                        $('#airWeight0').val("");
                        $('#pWaterVolume0').val("");
                        $('#freshWaterVolume0').val("");
                        $('#planCarryCount0').val("");
                        $("#netWeight0").text("");
                        $('#connectWay0').val("");
                        $('#length0').val("");
                        $('#width0').val("");
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
                            var length = carryParameters[i].length;
                            var width = carryParameters[i].width;
                            if (chooseId == deviceId) {
                                //选择舱外携带的作业工具 赋值给input框
                                $("#cabinOutCarryToolName0").val(deviceName);
                                $('#airWeight0').val(airWeight);
                                $('#pWaterVolume0').val(deWaterVolume);
                                $('#freshWaterVolume0').val(freshWaterWeight);
                                $('#planCarryCount0').val(planCarryCount);
                                $('#connectWay0').val(connectWay);
                                $('#length0').val(length);
                                $('#width0').val(width);
                                $('#length0').attr("readOnly", "true");
                                $('#width0').attr("readOnly", "true");
                                //计算净重量
                                if (airWeight != null && airWeight != "") {
                                    if (deWaterVolume != null && deWaterVolume != "") {
                                        var density = $("#density").val();
                                        if (density != null && density != "") {
                                            var netWeight = (airWeight - (deWaterVolume * 0.001 * density)) * planCarryCount;
                                            netWeight = (Math.round(netWeight * 10) / (10)).toFixed(1);
                                            $("#netWeight0").text(netWeight);
                                        }
                                    }
                                }
                                break;
                            }
                        }
                    }
                    $("#selectorCarryName0").next().find("dl").removeAttr("style");
                });
                form.on('select(cabinSelectCarryNameSelect0)', function () {
                    var chooseId = $("#cabinSelectorCarryName0").val();
                    if (chooseId == '') {
                        $("#cabinCarryToolName0").val("");
                        $('#cabinAirWeight0').val("");
                        $('#cabinPlanCarryCount0').val("");
                        $('#cabinConnectWay0').val("");
                        $("#cabinNetWeight0").text("");
                        $('#connectWay0').val("");
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
                                //选择舱内携带的作业工具 赋值给input框
                                $("#cabinCarryToolName0").val(deviceName);
                                $('#cabinAirWeight0').val(airWeight);
                                $('#cabinPlanCarryCount0').val(planCarryCount);
                                $('#cabinConnectWay0').val(connectWay);
                                //计算净重量
                                if (airWeight != null && airWeight != "") {
                                    if (typeof planCarryCount == 'undefined') {
                                        planCarryCount = 1;
                                    }
                                    var netWeight = airWeight * planCarryCount;
                                    netWeight = (Math.round(netWeight * 10) / (10)).toFixed(1);
                                    $("#cabinNetWeight0").text(netWeight);
                                }
                                break;
                            }
                        }
                    }
                    $("#cabinSelectorCarryName0").next().find("dl").removeAttr("style");
                });
                form.render();

                window.search = function (param) {
                    //小写转为大写
                    var Uppervalue = $("#" + param[0].id).val().toUpperCase();
                    //大写转为小写
                    var lowerValue= $("#" + param[0].id).val().toLowerCase();
                    form.render('select');
                    $("#" + param[0].nextElementSibling.id).next().find("dl").css({"display": "block"});
                    var dl = $("#" + param[0].nextElementSibling.id).next().find("dl").children();
                    var j = -1;
                    for (var i = 0; i < dl.length; i++) {
                        if (dl[i].innerHTML.indexOf(Uppervalue) <=-1&&(dl[i].innerHTML.indexOf(lowerValue) <= -1)) {
                            dl[i].style.display = "none";
                            j++;
                        }
                        if (j == dl.length - 1) {
                            $("#" + param[0].nextElementSibling.id).next().find("dl").css({"display": "none"});
                        }
                    }
                }
            });

        });
    </script>
</div>
</body>
</html>
