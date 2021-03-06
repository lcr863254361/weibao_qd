<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ page contentType="application/msword; charset=gb2312" %>--%>
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
    var isSubmitTable = '${isSubmitTable}';
    if (typeof'${homeMapPathList}' != "undefined") {
        var homeMapPathList = eval('${homeMapPathList}');
    }
    if (typeof'${showCarryToolList}' != "undefined") {
        var showCarryToolList = eval('${showCarryToolList}');
    }
    debugger;
    var ss = typeof '${recordRed}';
    if (typeof ss != "undefined") {
        var recordRed = '${recordRed}';
        if (recordRed != '') {
            recordRed = eval('(' + recordRed + ')');
        }
    }
    var basketPicFileId = '${basketPicFileId}';
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
    <script type="text/javascript"
            src="${ctx}/app/javascript/orientjs/extjs/TaskPrepareMgr/Accounting/js/FileSaver.js"></script>
    <script type="text/javascript"
            src="${ctx}/app/javascript/orientjs/extjs/TaskPrepareMgr/Accounting/js/jquery.wordexport.js"></script>
    <style type="text/css">
        td {
            height: 25px;
            width: auto;
        }

        body {
            text-align: center;
        }

        /*??????input ??????number??????????????????????????????????????? */
        .deal::-webkit-outer-spin-button {
            -webkit-appearance: none;
        }

        .deal::-webkit-inner-spin-button {
            -webkit-appearance: none;
        }
    </style>
</head>
<body>
    <form action="" method="" name="formTable1" class="layui-form" autocomplete="off">
        <!--<div id="planTable1">-->
        <table height="" border="1" align="center" width="80%" cellspacing=0 cellpadding=0>
            <tr>
                <td align="center" valign="middle" colspan="4">????????????????????????????????????????????????????????????</td>
            </tr>
            <tr>
                <td align="center" valign="middle">??????</td>
                <td><input name="numberContent" type="text" id="numberContent"
                           style="width: 100%;height: 100%;border:none;" value="${numberContent}"></td>
                <td align="center" valign="middle">????????????</td>
                <td align="center" valign="middle">
                    <input name="divingDate" type="text" id="divingDate" placeholder="yyyy-MM-dd"
                           style="width: 100%;height: 100%;border:none;" value="${divingDate}"></td>
            </tr>
            <tr>
                <td align="center" valign="middle">??????????????????</td>
                <td><input name="positionTime" type="text" id="positionTime"
                           style="width: 100%;height: 100%;border:none;"
                           value="${positionTime}" onblur="calPlanWaterTime()">
                </td>
                <td align="center" valign="middle">????????????????????????</td>
                <td><input name="planFloatToWTime" type="text" id="planFloatToWTime"
                           style="width: 100%;height: 100%;border:none;" value="${planFloatToWTime}"
                           onblur="calPlanWaterTime()"></td>
            </tr>
            <tr>
                <td align="center" valign="middle">??????????????????</td>
                <td id="planWaterTime" name="planWaterTime">${planWaterTime}</td>

                <td align="center" valign="middle">??????????????????</td>
                <td><input name="palnThrowTime" type="text" id="palnThrowTime"
                           style="width: 100%;height: 100%;border:none;" value="${palnThrowTime}"></td>
            </tr>
            <tr>
                <td align="center" valign="middle">??????????????????(min)</td>
                <td name="planFloatTime">${planFloatTime}</td>

                <td align="center" valign="middle" name='divingType' id="divingType">????????????</td>
                <td maxlength="10">
                    <input name="divingTypeCheckBox" type="checkbox" value="????????????" lay-skin="primary"
                           lay-filter="divingTypeCheckbox" title="????????????" , id="project"
                    <%--{{#if (${divingType eq ',??????,'}){}} checked--%>
                    <%--{{#}}}--%>
                    >
                    <input name="divingTypeCheckBox" type="checkbox" value="????????????" lay-skin="primary"
                           lay-filter="divingTypeCheckbox" title="????????????" id="apply"
                    <%--{{#if (${divingType eq ',??????,'}){}} checked--%>
                    <%--{{#}}}--%>
                    >
                    <input name="divingTypeCheckBox" type="checkbox" value="????????????" lay-skin="primary"
                           lay-filter="divingTypeCheckbox" title="????????????" id="train"
                    <%--{{#if (${divingType eq ',??????,'}){}} checked--%>
                    <%--{{#}}}--%>
                    >
                    <input name="divingTypeCheckBox" type="checkbox" value="????????????" lay-skin="primary"
                           lay-filter="divingTypeCheckbox" title="????????????" id="searchSalvage"
                    >
                </td>
            </tr>
            <tr>
                <td align="center" valign="middle">??????(??)<br>(+????????? -?????????)</td>
                <td>
                    <input name="longtitude" type="number" id="longtitude"
                           style="width: 100%;height: 100%;border:none;" value="${longtitude}" class="deal"
                           onmousewheel="stopScrollFun()" onDOMMouseScroll="stopScrollFun()"></td>
                <td align="center" valign="middle">??????(??)<br>(+????????? -?????????)</td>
                <td>
                    <input name="latitude" type="number" id="latitude"
                           style="width: 100%;height: 100%;border:none;" value="${latitude}" class="deal"
                           onmousewheel="stopScrollFun()" onDOMMouseScroll="stopScrollFun()"></td>
            </tr>
            <tr>
                <td align="center" valign="middle">??????</td>
                <%--<td><input name="seaArea" type="text" id="seaArea"--%>
                <%--style="width: 100%;height: 100%;border:none;" value="${seaArea}"></td>--%>
                <td><select id="seaArea" lay-filter="selectDepthDesityTypeFilter" lay-verify="required"
                            lay-search>
                    <option value="">?????????</option>
                </select></td>

                <td align="center" valign="middle">??????????????????(M)</td>
                <td style="width: auto;" class="layui-input-block">
                    <input type="number" name="planDivingDepth" id="planDivingDepth"
                           class="layui-input deal"
                           style="position:absolute;z-index:2;width: 80%;height: 100%;border:none;"
                           lay-verify="required" autocomplete="off"
                           onkeyup="search($(this))" onmousewheel="stopScrollFun()"
                           onDOMMouseScroll="stopScrollFun()"/>
                    <select id="selectDivingDepth" lay-filter="selectDivingDepthSelect" lay-verify="required"
                            lay-search>
                        <option value="">?????????</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td align="center" valign="middle">??????(Kg/m??)</td>
                <td align="center" valign="middle"><input name="density" type="number" id="density"
                                                          style="width: 100%;height: 100%;border:none;"
                                                          onchange="changeDensity()" class="deal"
                                                          onmousewheel="stopScrollFun()"
                                                          onDOMMouseScroll="stopScrollFun()"></td>
                <td align="center" valign="middle">??????????????????(M)</td>
                <td style="width: auto;" class="layui-input-block">
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
                        <option value="">?????????</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td align="center" valign="middle">?????????</td>
                <td align="center" valign="middle" colspan="3">
                    <input name="homeWorkPoint" type="text" id="homeWorkPoint"
                           style="width: 100%;height: 100%;border:none;" value="${homeWorkPoint}"></td>
            </tr>
            <tr>
                <td align="center" valign="middle" width="">??????</td>
                <td align="center" valign="middle" colspan="2">??????</td>
                <td align="center" valign="middle">??????</td>
            </tr>
            <tr height="20">
                <td align="center" valign="middle" width="">
                    <div class="layui-form" lay-filter="selectZuoxianDiv" style="margin: 0 auto">
                        <select id="selectZuoxian" lay-filter="selectZuoxianSelect" lay-verify="required" lay-search>
                            <option value="">?????????</option>
                        </select>
                    </div>
                </td>
                <td align="center" valign="middle" colspan="2">
                    <div class="layui-form" lay-filter="selectMainDriverDiv">
                        <select id="selectMainDriver" lay-verify="required" lay-search>
                            <option value="">?????????</option>
                        </select>
                    </div>
                </td>
                <td align="center" valign="middle">
                    <div class="layui-form" lay-filter="selectYouxianDiv">
                        <select id="selectYouxian" lay-verify="required" lay-search>
                            <option value="">?????????</option>
                        </select>
                    </div>
                </td>
            </tr>
            <tr>
                <td align="center" valign="middle">????????????(Kg)</td>
                <td align="center" valign="middle" id="divingLoad" name="divingLoad">${divingLoad}</td>
                <td align="center" valign="middle">????????????(Kg)</td>
                <td align="center" valign="middle" name="comeupLoad">${comeupLoad}</td>
            </tr>
            <tr>
                <td align="center" valign="middle">?????????????????????(L)</td>
                <td align="center" valign="middle" name="adjustLoad">${adjustLoad}</td>
                <td align="center" valign="middle">????????????(Kg)</td>
                <td align="center" valign="middle" name="peizhongQk">${peizhongQk}</td>
            </tr>
            <tr>
                <td align="center" valign="middle">?????????????????????(Kg)</td>
                <td align="center" valign="middle" id="basketWeight" name="basketWeight"></td>
                <td align="center" valign="middle">??????????????????(L)</td>
                <td align="center" valign="middle" name="mercury">${mercury}</td>
            </tr>
            <tr>
                <td colspan="4" style="border: none" style="width: 100%;padding: 0">
                    <table id="cabinCarryTable" height="" border="1" align="center" frame="void" width="100% "
                           cellspacing=0
                           cellpadding=0>
                        <tr id="cabinCombineRow">
                            <td align="center" valign="middle" rowspan="2">???????????????????????????</td>
                            <td align="center" valign="middle">??????</td>
                            <td align="center" valign="middle" colspan="3">???????????????(Kg)</td>
                            <%--<td align="center" valign="middle">????????????(L)</td>--%>
                            <%--<td align="center" valign="middle">???????????????(Kg)</td>--%>
                            <td align="center" valign="middle">??????????????????</td>
                            <td align="center" valign="middle">?????????(Kg)</td>
                            <td align="center" valign="middle">????????????????????????</td>
                            <td align="center" valign="middle">??????</td>
                        </tr>
                        <tr id="cabinRow0" height="20">
                            <td id="cabinName0" name="cabinName0" align="center" valign="middle" style="width: auto;"
                                class="layui-input-block">
                                <input type="text" name="cabinCarryToolName0" id="cabinCarryToolName0"
                                       class="layui-input" style="position:absolute;z-index:2;width: 80%;height: 100%;"
                                       lay-verify="required" autocomplete="off" onkeyup="search($(this));"/>
                                <select id="cabinSelectorCarryName0" lay-filter="cabinSelectCarryNameSelect0"
                                        lay-verify="required" lay-search>
                                    <option value="">?????????</option>
                                </select>
                            </td>
                            <td align="center" valign="middle" colspan="3">
                                <input name="cabinAirWeight0" type="number" id="cabinAirWeight0"
                                       style="width: 100%;height: 100%;border:none;" value="${cabinAirWeight0}"
                                       class="deal"
                                       onmousewheel="stopScrollFun()"
                                       onDOMMouseScroll="stopScrollFun()">
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
                                       value="${cabinPlanCarryCount0}" class="deal"
                                       onmousewheel="stopScrollFun()"
                                       onDOMMouseScroll="stopScrollFun()"/></td>
                            <td id="cabinNetWeight0" name="cabinNetWeight0" align="center" valign="middle"></td>
                            <td align="center" valign="middle">
                                <input name="cabinConnectWay0" type="text" id="cabinConnectWay0"
                                       style="width: 100%;height: 100%;border:none;" value="${cabinConnectWay0}">
                            </td>
                            <td style="width:80px">
                                <input type="button" name="delete" value="??????"
                                       style="width: 100%;height: 100%;border:none;"
                                       onclick="deleteCabinInSelectedRow('cabinRow0')"/>
                            </td>
                        </tr>
                        <tr>
                            <td align="center" colspan="11">
                                <br/>
                                <input type="button" name="insert" value="????????????"
                                       style="color: black;font-family: bold;font-size: large;width:100px"
                                       onclick="cabinInsertNewRow('','','',carryParameters)"/>&nbsp&nbsp
                            </td>
                        </tr>
                        <tr>
                            <td align="center" valign="middle">????????????????????????????????????</td>
                            <td></td>
                            <td id="cabinAirAllWeight" colspan="3"></td>
                            <%--<td id="cabinPWaterAllVolume"></td>--%>
                            <%--<td id="cabinFreshWaterAllVolume"></td>--%>
                            <td></td>
                            <td align="center" valign="middle" id="cabinAllNetWeight"></td>
                            <td></td>
                            <td><input type="button" value=" ?????? "
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
                            <td align="center" valign="middle" rowspan="2">???????????????????????????</td>
                            <td align="center" valign="middle">??????</td>
                            <td align="center" valign="middle">???????????????(Kg)</td>
                            <td align="center" valign="middle">????????????(L)</td>
                            <td align="center" valign="middle">???????????????(Kg)</td>
                            <td align="center" valign="middle">??????????????????</td>
                            <td align="center" valign="middle">?????????(Kg)</td>
                            <td align="center" valign="middle">????????????????????????</td>
                            <td align="center" valign="middle">??????(cm)</td>
                            <td align="center" valign="middle">??????(cm)</td>
                            <td align="center" valign="middle">??????</td>
                        </tr>
                        <tr id="row0" height="20">
                            <td id="name0" name="name0" align="center" valign="middle" style="width: auto;"
                                class="layui-input-block">
                                <input type="text" name="cabinOutCarryToolName0" id="cabinOutCarryToolName0"
                                       class="layui-input" style="position:absolute;z-index:2;width: 80%;height: 100%;"
                                       lay-verify="required" onkeyup="search($(this))" autocomplete="off">
                                <select id="selectorCarryName0" lay-filter="selectCarryNameSelect0"
                                        lay-verify="required" lay-search>
                                    <option value="">?????????</option>
                                </select>
                            </td>
                            <td align="center" valign="middle">
                                <input name="airWeight0" type="number" id="airWeight0"
                                       style="width: 100%;height: 100%;border:none;" value="${airWeight0}" class="deal"
                                       onmousewheel="stopScrollFun()"
                                       onDOMMouseScroll="stopScrollFun()">
                            </td>
                            <td align="center" valign="middle">
                                <input name="pWaterVolume0" type="number" id="pWaterVolume0"
                                       style="width: 100%;height: 100%;border:none;"
                                       value="${pWaterVolume0}" class="deal"
                                       onmousewheel="stopScrollFun()"
                                       onDOMMouseScroll="stopScrollFun()">
                            </td>
                            <td align="center" valign="middle">
                                <input name="freshWaterVolume0" type="number" id="freshWaterVolume0"
                                       style="width: 100%;height: 100%;border:none;"
                                       value="${freshWaterVolume0}" class="deal"
                                       onmousewheel="stopScrollFun()"
                                       onDOMMouseScroll="stopScrollFun()"></td>
                            <td align="center" valign="middle">
                                <input name="planCarryCount0" type="number" id="planCarryCount0"
                                       style="width: 100%;height: 100%;border:none;"
                                       onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]\d*$/,' ')}else{this.value=this.value.replace(/\D/g,' ')}"
                                       value="${planCarryCount0}" class="deal"
                                       onmousewheel="stopScrollFun()"
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
                                <input type="button" name="delete" value="??????"
                                       style="width: 100%;height: 100%;border:none;"
                                       onclick="deleteSelectedRow('row0')"/>
                            </td>
                        </tr>
                        <tr>
                            <td align="center" colspan="11">
                                <br/>
                                <input type="button" name="insert" value="????????????"
                                       style="color: black;font-family: bold;font-size: large;width:100px"
                                       onclick="insertNewRow('','','',carryParameters)"/>&nbsp&nbsp
                            </td>
                        </tr>
                        <!--</div>-->
                        <tr>
                            <td align="center" valign="middle">????????????????????????????????????</td>
                            <td></td>
                            <td id="airAllWeight"></td>
                            <td id="pWaterAllVolume"></td>
                            <td id="freshWaterAllVolume"></td>
                            <td></td>
                            <td align="center" valign="middle" id="allNetWeight"></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td><input type="button" value=" ?????? "
                                       style="width: 100%;height: 100%;border:none;"
                                       onclick="caltotalAirWeight()"/></td>
                        </tr>
                        <tr>
                            <td align="center" valign="middle">????????????</td>
                            <td align="center" valign="middle" colspan="10"
                                style="width:100px;height:100px;">
                                <!--<input name="mainTask" type="text" id="mainTask">-->
                                <!--?????? textarea ??????style="resize:none" ?????? textarea ?????????style="border:0px"-->
                                <textarea id="mainTask" name="mainTask"
                                          class="textinput" style="width:100%;height:100%;border:0px;resize: none;outline:none;border:none;overflow-y: scroll
                          ">${mainTask}</textarea>
                            </td>
                        </tr>
                        <tr>
                            <td align="center" valign="middle">????????????</td>
                            <td align="center" valign="middle" colspan="10"
                                style="width:100px;height:100px;">
						<textarea id="workProgress" name="workProgress"
                                  class="textinput"
                                  style="width:100%;height:100%;border:0px;resize: none;outline:none;border:none;overflow-y: scroll">${workProgress}</textarea>
                                <!--<input name="workProgress" type="text" id="workProgress">-->
                            </td>
                        </tr>
                        <tr>
                            <td align="center" valign="middle">????????????</td>
                            <td align="center" valign="middle" colspan="10"
                                style="width:100px;height:100px;">
						<textarea id="attention" name="attention"
                                  class="textinput"
                                  style="width:100%;height:100%;border:0px;resize: none;outline:none;border:none;overflow-y: scroll">${attention}</textarea>
                                <!--<input name="attention" type="text" id="attention">-->
                            </td>
                        </tr>
                        <tr>
                            <td align="center" valign="middle">????????????????????????</td>
                            <td id="picDetails" colspan="10">
                            </td>
                        </tr>
                        <tr>
                            <td align="center" valign="middle">?????????????????????</td>
                            <td id="basketPicDetail" colspan="10">
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        <p id="buttonGroup" style=" margin:0 auto; text-align:center;"><input type="button" id="editDivingPlan" value="??????"
                                                                              style="color: black;font-family: bold;font-size: large"
                                                                              class="layui-btn layui-btn-primary"/>&nbsp&nbsp<input type="button"
                                                                              id="saveDivingPlan" value="??????"
                                                                              style="color: black;font-family: bold;font-size: large"
                                                                              class="layui-btn layui-btn-primary"/>&nbsp&nbsp<input
                type="button" id="planSubmit" value="??????" style="color: black;font-family: bold;font-size: large"
                class="layui-btn layui-btn-primary"/></p>
        <!--</div>-->
    </form>
    <script src="${ctx}/app/javascript/lib/layui-2.5.5/src/layui.js" charset="utf-8"></script>
    <script>
        if (typeof homeMapPathList != "undefined") {
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

        if (basketPicFileId != "") {
            var basketPic = typeof '${basketPicMap}';
            if (typeof basketPic != "undefined") {
                var basketPicMap = '${basketPicMap}';
                if (basketPicMap != '') {
                    basketPicMap = eval('(' + basketPicMap + ')');
                    $("#basketPicDetail").append([
                        '<a class="example-image-link" ' + '" href="' + __ctx + '/orientForm/download.rdm?fileId=' + basketPicMap.fileId + '" data-lightbox="orientImage" data-title="' + basketPicMap.filename + '">',
                        '<img id="example-image'+ '" src="' + __ctx + '/preview/imageSuoluetu/' + basketPicMap.sltFilePath + '"' +
                        '" onclick="' + 'javaScript:window.open(\'' + __ctx + '/preview/imageSuoluetu/' + basketPicMap.filePath + '\')' +
                        '"></img>' + '&nbsp;&nbsp;',
                        '</a>'
                    ].join(""));
                }
            }
        }
        $.ajax({
            url: __ctx + '/accountingForm/getPersons.rdm',
            async: false,
            data: {
                "hangduanId": hangduanId,
                "taskId": taskId
            },
            success: function (data) {
                // console.log(data);
                //JSON.parse()??????????????????json??????
                var result = JSON.parse(data).results;
                var selectorZuoxian = document.getElementById("selectZuoxian");
                var selectorMainDriver = document.getElementById("selectMainDriver");
                var selectorYouxian = document.getElementById("selectYouxian");
                for (var i = 0; i < result.length; i++) {
                    var optionZuoxian = new Option(result[i].name, result[i].id);
                    var optionMainDriver = new Option(result[i].name, result[i].id);
                    var optionYouxian = new Option(result[i].name, result[i].id);
                    selectorZuoxian.options.add(optionZuoxian);
                    selectorMainDriver.options.add(optionMainDriver);
                    selectorYouxian.options.add(optionYouxian);
                }
            }
        });

        $.ajax({
            url: __ctx + '/accountingForm/getDepthDesityTypeData.rdm',
            async: false,
            success: function (data) {
                // console.log(data);
                //JSON.parse()??????????????????json??????
                var result = JSON.parse(data).results;
                var depthDesityType = document.getElementById("seaArea");
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
                //JSON.parse()??????????????????json??????
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

        function useLaydate() {
            layui.use('laydate', function () {
                var laydate = layui.laydate;
                //????????????laydate??????
                laydate.render({
                    elem: '#positionTime', //????????????????????????
                    type: 'time',
                    format: 'HHmm',
                    done: function (value, date, endDate) {
                        donePositionTimeCalPlanWaterTime(value, date, endDate);
                    }
                });
                laydate.render({
                    elem: '#palnThrowTime', //????????????
                    type: 'time',
                    format: 'HHmm'
                });
                laydate.render({
                    elem: '#divingDate', //????????????
                    type: 'date',
                    // format: 'HHmmss'
                });
                //????????????????????????????????????????????????done??????
                laydate.render({
                    elem: '#planFloatToWTime', //????????????
                    type: 'time',
                    format: 'HHmm',
                    done: function (value, date, endDate) {
                        donePlanFloatToWTimeCalPlanWaterTime(value,date,endDate);
                    }
                })
            });
        };

        $(document).ready(function () {
            //????????????
            if (${divingType== '????????????'}) {
                $("#project").prop('checked', true);
            } else if (${divingType == '????????????'}) {
                $("#apply").prop('checked', true);
            } else if (${divingType eq '????????????'}) {
                $("#train").prop('checked', true);
            }else if (${divingType eq '????????????'}) {
                $("#searchSalvage").prop('checked', true);
            }

            if (typeof isSubmitTable != "undefined" && isSubmitTable == 'submit') {
            } else {
                useLaydate();
            }
            layui.use('form', function () {
                var form = layui.form;
                //?????????????????????
                $("#selectZuoxian").val(${selectZuoxian});
                $("#selectMainDriver").val(${selectMainDriver});
                $("#selectYouxian").val(${selectYouxian});
                $('#density').val(${density});

                $("#seaArea").val(${seaArea});

                var depthDesityParameters;
                if ($("#seaArea").val() != '') {
                    $.ajax({
                        url: __ctx + '/accountingForm/getDepthDesitySelectData.rdm',
                        async: false,
                        data: {
                            "deptyDesityTypeId": $("#seaArea").val()
                        },
                        success: function (data) {
                            //JSON.parse()??????????????????json??????
                            var result = JSON.parse(data).results;
                            var selectorDivingDepth = document.getElementById("selectDivingDepth");
                            var selectPlanFloatDepth = document.getElementById('selectPlanFloatDepth');
                            (function () {
                                for (var i = 0; i < result.length; i++) {
                                    var optionDepth = new Option(result[i].depth, result[i].id);
                                    selectorDivingDepth.options.add(optionDepth);
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

                        var planDivingDepth = '${planDivingDepth}';
                        var planFloatDepth = '${planFloatDepth}';

                        for (var h = 0; h < options.length; h++) {
                            if (options[h].innerHTML == planDivingDepth) {
                                $('#planDivingDepth').val(options[h].innerHTML);
                                $('#selectDivingDepth').val(options[h].value);
                                isExist = true;
                                break;
                            }
                        }
                        if (!isExist) {
                            $('#planDivingDepth').val(planDivingDepth);
                        }
                        options = $("#selectPlanFloatDepth").find("option");
                        isExist = false;
                        for (var k = 0; k < options.length; k++) {
                            if (options[k].innerHTML == planFloatDepth) {
                                $('#planFloatDepth').val(options[k].innerHTML);
                                $('#selectPlanFloatDepth').val(options[k].value);
                                isExist = true;
                                break;
                            }
                        }
                        if (!isExist) {
                            $('#planFloatDepth').val(planFloatDepth);
                        }
                    })();
                }

                form.on('select(selectDepthDesityTypeFilter)', function () {
                    var chooseId = $("#seaArea").val();

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
                                //JSON.parse()??????????????????json??????
                                var result = JSON.parse(data).results;
                                var selectorDivingDepth = document.getElementById("selectDivingDepth");
                                var selectPlanFloatDepth = document.getElementById("selectPlanFloatDepth");
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

                form.on('select(selectPlanFloatDepth)', function () {
                    var chooseId = $("#selectPlanFloatDepth").val();
                    (function () {
                        for (var i = 0; i < depthDesityParameters.length; i++) {
                            var depthDesityId = depthDesityParameters[i].id;
                            var depth = depthDesityParameters[i].depth;
                            if (chooseId == depthDesityId) {
                                //???????????????????????? ?????????input???
                                $("#planFloatDepth").val(depth);
                                break;
                            }
                        }
                        $("#selectPlanFloatDepth").next().find("dl").removeAttr("style");
                    })();
                });

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
                                //???????????? ?????????input???
                                $("#planDivingDepth").val(depth);
                                $('#density').val(density);
                                break;
                            }
                        }
                    }
                    $("#selectDivingDepth").next().find("dl").removeAttr("style");
                    caltotalAirWeight();
                });
                if (typeof showCarryToolList != 'undefined') {
                    for (var j = 0; j < showCarryToolList.length; j++) {
                        var rowIndex = showCarryToolList[j].rowNumber;
                        var planCarryCount = showCarryToolList[j].carryCount;
                        var isCabinOutOrIn = showCarryToolList[j].isCabinOutOrIn;
                        if (showCarryToolList[j].rowNumber != 0) {
                            if ("in" == isCabinOutOrIn) {
                                cabinInsertNewRow(showCarryToolList[j].deviceId, showCarryToolList[j].rowNumber, showCarryToolList[j].carryCount, carryParameters);
                            } else {
                                insertNewRow(showCarryToolList[j].deviceId, showCarryToolList[j].rowNumber, showCarryToolList[j].carryCount, carryParameters);
                            }
                        }
                        var airWeight = showCarryToolList[j].airWeight;
                        var deWaterVolume = showCarryToolList[j].deWaterVolume;
                        var freshWaterWeight = showCarryToolList[j].freshWaterWeight;
                        var connectWay = showCarryToolList[j].connectWay;
                        if (showCarryToolList[j].isCabinOutOrIn == 'in') {
                            $("#cabinSelectorCarryName" + rowIndex).val(showCarryToolList[j].deviceId);
                            $('#cabinAirWeight' + rowIndex).val(airWeight);
                            $('#cabinPlanCarryCount' + rowIndex).val(planCarryCount);
                            $('#cabinConnectWay' + rowIndex).val(connectWay);
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
                            $('#airWeight' + rowIndex).val(airWeight);
                            $('#pWaterVolume' + rowIndex).val(deWaterVolume);
                            $('#freshWaterVolume' + rowIndex).val(freshWaterWeight);
                            $('#planCarryCount' + rowIndex).val(planCarryCount);
                            $('#connectWay' + rowIndex).val(connectWay);
                            (function () {
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
                var selectDivingDepthRedFlag;
                if (typeof isSubmitTable != "undefined" && isSubmitTable == 'submit') {
                    $("input").attr("readonly", true);
                    $('select').attr("disabled", "disabled");
                    $("input[type='checkbox']").attr("disabled", true);
                    // $("input[type='button']").attr("disabled",true);
                    $("#planSubmit").attr("disabled", true);
                    $("#saveDivingPlan").attr("disabled", true);
                    $("textarea").attr("readonly", true);
                    //????????????
                    if (recordRed != '') {
                        var plan = recordRed.plan;
                        if (plan.length != 0) {
                            for (var k = 0; k < plan.length; k++) {
                                var name = plan[k].name;
                                if (name == 'seaArea') {
                                    // $('#seaArea').addClass('red');
                                    $('#seaArea').css("background-color", "red");
                                }
                                if (name == 'longtitude') {
                                    $('#longtitude').css("background-color", "red");
                                }
                                if (name == 'latitude') {
                                    $('#latitude').css("background-color", "red");
                                }
                                if (name == 'mainTask') {
                                    $('#mainTask').css("background-color", "red");
                                }
                                if (name == 'workProgress') {
                                    $('#workProgress').css("background-color", "red");
                                }
                                if (name == 'attention') {
                                    $('#attention').css("background-color", "red");
                                }
                                if (name == 'selectDivingDepth') {
                                    selectDivingDepthRedFlag = 1;
                                } else if (name == 'density') {
                                    $('#density').css("background-color", "red");
                                }
                            }
                        }
                    }
                }
                form.on('checkbox(divingTypeCheckbox)', function (data) {
                    if (data.elem.checked) {
                        $("input[name='divingTypeCheckBox']").prop('checked', false);
                        $(data.elem).prop('checked', true);
                        form.render('checkbox');
                    }
                });
                form.on('select(selectCarryNameSelect0)', function () {
                    var chooseId = $("#selectorCarryName0").val();
                    if (chooseId == '') {
                        $("#cabinOutCarryToolName0").val("");
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
                                //????????????????????????????????? ?????????input???
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
                                //???????????????
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
                                //????????????????????????????????? ?????????input???
                                $("#cabinCarryToolName0").val(deviceName);
                                $('#cabinAirWeight0').val(airWeight);
                                $('#cabinPlanCarryCount0').val(planCarryCount);
                                $('#cabinConnectWay0').val(connectWay);
                                //???????????????
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
                if (typeof isSubmitTable != "undefined" && isSubmitTable == 'submit') {
                    //??????
                    if (selectDivingDepthRedFlag == 1) {
                        $('#selectDivingDepth').next().find('input').css("background-color", "red");
                        $('#density').css("background-color", "red");
                    }
                    if (recordRed != '') {
                        var cabinOut = recordRed.cabinOut;
                        var cabinIn = recordRed.cabinIn;
                        if (typeof showCarryToolList != "undefined") {
                            for (var d = 0; d < showCarryToolList.length; d++) {
                                var rowIndex = showCarryToolList[d].rowNumber;
                                var isCabinOutOrIn = showCarryToolList[d].isCabinOutOrIn;
                                if ("in" == isCabinOutOrIn) {
                                    if (cabinIn.length != 0) {
                                        for (var t = 0; t < cabinIn.length; t++) {
                                            var deviceId = cabinIn[t].deviceId;
                                            if (showCarryToolList[d].deviceId == deviceId) {
                                                $('#cabinSelectorCarryName' + rowIndex).next().find('input').css("background-color", "red");
                                                break;
                                            }
                                        }
                                    }
                                }
                                if ("out" == isCabinOutOrIn) {
                                    if (cabinOut.length != 0) {
                                        for (var h = 0; h < cabinOut.length; h++) {
                                            var deviceId = cabinOut[h].deviceId;
                                            if (showCarryToolList[d].deviceId == deviceId) {
                                                $('#selectorCarryName' + rowIndex).next().find('input').css("background-color", "red");
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                window.search = function (param) {
                    //??????????????????
                    var Uppervalue = $("#" + param[0].id).val().toUpperCase();
                    //??????????????????
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
            $("#editDivingPlan").click(function () {
                $("input").attr("readonly", false);
                $('select').attr("disabled", false);
                $("input[type='checkbox']").attr("disabled", false);
                $("#planSubmit").attr("disabled", false);
                $("#saveDivingPlan").attr("disabled", false);
                $("textarea").attr("readonly", false);
                layui.use('form', function () {
                    var form = layui.form;
                    form.render('select');
                    form.render('checkbox');
                });
                useLaydate();
            });

            $("#saveDivingPlan").click(function () {
                var divingPlanTableData = packageData();
                $.ajax({
                    url: __ctx + '/accountingForm/submitDivingPlanTable.rdm?submitType=' + false,
                    type: "post",
                    async: false,
                    data: divingPlanTableData,
                    success: function (data) {
                        //??????????????????
                        document.location.reload();
                    }
                });
            });

            $("#planSubmit").click(function () {
                calCabinTotalAirWeight();
                caltotalAirWeight();
                var divingPlanTableData = packageData();
                $.ajax({
                    url: __ctx + '/accountingForm/submitDivingPlanTable.rdm?submitType=' + true,
                    type: "post",
                    async: false,
                    data: divingPlanTableData,
                    success: function (data) {
                        //??????????????????
                        document.location.reload();
                    }
                });
            });

        });
    </script>

</body>
</html>
