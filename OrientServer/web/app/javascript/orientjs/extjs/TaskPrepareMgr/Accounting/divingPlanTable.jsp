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
    if (hangduanId == 'null') {
        hangduanId = '${hangduanId}';
    }
    var isCanEdit = '${isCanEdit}';
    var isSubmitTable = '${isSubmitTable}';
    if (typeof'${homeMapPathList}' != "undefined") {
        var homeMapPathList = eval('${homeMapPathList}');
    }
    if (typeof'${showCarryToolList}' != "undefined") {
        var showCarryToolList = eval('${showCarryToolList}');
    }
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

        /*去掉input 类型number中输入框右边的上下箭头按钮 */
        .deal::-webkit-outer-spin-button {
            -webkit-appearance: none;
        }

        .deal::-webkit-inner-spin-button {
            -webkit-appearance: none;
        }
    </style>
</head>
<body>
<div id="divingPlanWord">
    <%--<input type="button" id="exportDivingPlanWord" value="导出word"></input>--%>
    <form action="" method="" name="formTable1" class="layui-form" autocomplete="off">
        <!--<div id="planTable1">-->
        <table height="" border="1" align="center" width="80%" cellspacing=0 cellpadding=0>
            <tr>
                <td align="center" valign="middle" colspan="4">“深海勇士”号载人潜水器海上试验潜次计划</td>
            </tr>
            <tr>
                <td align="center" valign="middle">编号</td>
                <td><input name="numberContent" type="text" id="numberContent"
                           style="width: 100%;height: 100%;border:none;" value="${numberContent}"></td>
                <td align="center" valign="middle">下潜日期</td>
                <td align="center" valign="middle">
                    <input name="divingDate" type="text" id="divingDate" placeholder="yyyy-MM-dd"
                           style="width: 100%;height: 100%;border:none;" value="${divingDate}"></td>
            </tr>
            <tr>
                <td align="center" valign="middle">各就各位时间</td>
                <td><input name="positionTime" type="text" id="positionTime"
                           style="width: 100%;height: 100%;border:none;"
                           value="${positionTime}" onblur="calPlanWaterTime()">
                </td>
                <td align="center" valign="middle">计划浮至水面时间</td>
                <td><input name="planFloatToWTime" type="text" id="planFloatToWTime"
                           style="width: 100%;height: 100%;border:none;" value="${planFloatToWTime}"
                           onblur="calPlanWaterTime()"></td>
            </tr>
            <tr>
                <td align="center" valign="middle">计划水中时间</td>
                <td id="planWaterTime" name="planWaterTime">${planWaterTime}</td>

                <td align="center" valign="middle">计划抛载时间</td>
                <td><input name="palnThrowTime" type="text" id="palnThrowTime"
                           style="width: 100%;height: 100%;border:none;" value="${palnThrowTime}"></td>
            </tr>
            <tr>
                <td align="center" valign="middle">计划上浮时间(min)</td>
                <td name="planFloatTime">${planFloatTime}</td>

                <td align="center" valign="middle" name='divingType' id="divingType">下潜类型</td>
                <td>
                    <input name="divingTypeCheckBox" type="checkbox" value="工程海试" lay-skin="primary"
                           lay-filter="divingTypeCheckbox" title="工程海试" , id="project"
                    <%--{{#if (${divingType eq ',工程,'}){}} checked--%>
                    <%--{{#}}}--%>
                    >
                    <input name="divingTypeCheckBox" type="checkbox" value="科考应用" lay-skin="primary"
                           lay-filter="divingTypeCheckbox" title="科考应用" id="apply"
                    <%--{{#if (${divingType eq ',应用,'}){}} checked--%>
                    <%--{{#}}}--%>
                    >
                    <input name="divingTypeCheckBox" type="checkbox" value="工程应用" lay-skin="primary"
                           lay-filter="divingTypeCheckbox" title="工程应用" id="train"
                    <%--{{#if (${divingType eq ',培训,'}){}} checked--%>
                    <%--{{#}}}--%>
                    >
                    <input name="divingTypeCheckBox" type="checkbox" value="搜索打捞" lay-skin="primary"
                           lay-filter="divingTypeCheckbox" title="搜索打捞" id="searchSalvage"
                    >
                </td>
            </tr>
            <tr>
                <td align="center" valign="middle">经度(°)<br>(+：东经 -：西经)</td>
                <td>
                    <input name="longtitude" type="number" id="longtitude"
                           style="width: 100%;height: 100%;border:none;" value="${longtitude}" class="deal"
                           onmousewheel="stopScrollFun()" onDOMMouseScroll="stopScrollFun()"></td>
                <td align="center" valign="middle">纬度(°)<br>(+：北纬 -：南纬)</td>
                <td>
                    <input name="latitude" type="number" id="latitude"
                           style="width: 100%;height: 100%;border:none;" value="${latitude}" class="deal"
                           onmousewheel="stopScrollFun()" onDOMMouseScroll="stopScrollFun()"></td>
            </tr>
            <tr>
                <td align="center" valign="middle">海区</td>
                <%--<td><input name="seaArea" type="text" id="seaArea"--%>
                <%--style="width: 100%;height: 100%;border:none;" value="${seaArea}"></td>--%>
                <td><select id="seaArea" lay-filter="selectDepthDesityTypeFilter" lay-verify="required"
                            lay-search>
                    <option value="">请选择</option>
                </select></td>

                <td align="center" valign="middle">计划下潜深度(M)</td>
                <td style="width: auto;" class="layui-input-block">
                    <input type="number" name="planDivingDepth" id="planDivingDepth"
                           class="layui-input deal"
                           style="position:absolute;z-index:2;width: 80%;height: 100%;border:none;"
                           lay-verify="required" autocomplete="off"
                           onkeyup="search($(this))" onmousewheel="stopScrollFun()"
                           onDOMMouseScroll="stopScrollFun()"/>
                    <select id="selectDivingDepth" lay-filter="selectDivingDepthSelect" lay-verify="required"
                            lay-search>
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td align="center" valign="middle">密度(Kg/m³)</td>
                <td align="center" valign="middle"><input name="density" type="number" id="density"
                                                          style="width: 100%;height: 100%;border:none;"
                                                          onchange="changeDensity()" class="deal"
                                                          onmousewheel="stopScrollFun()"
                                                          onDOMMouseScroll="stopScrollFun()"></td>
                <td align="center" valign="middle">计划上浮深度(M)</td>
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
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td align="center" valign="middle">作业点</td>
                <td align="center" valign="middle" colspan="3">
                    <input name="homeWorkPoint" type="text" id="homeWorkPoint"
                           style="width: 100%;height: 100%;border:none;" value="${homeWorkPoint}"></td>
            </tr>
            <tr>
                <td align="center" valign="middle" width="">左舷</td>
                <td align="center" valign="middle" colspan="2">主驾</td>
                <td align="center" valign="middle">右舷</td>
            </tr>
            <tr height="20">
                <td align="center" valign="middle" width="">
                    <div class="layui-form" lay-filter="selectZuoxianDiv" style="margin: 0 auto">
                        <select id="selectZuoxian" lay-filter="selectZuoxianSelect" lay-verify="required" lay-search>
                            <option value="">请选择</option>
                        </select>
                    </div>
                </td>
                <td align="center" valign="middle" colspan="2">
                    <div class="layui-form" lay-filter="selectMainDriverDiv">
                        <select id="selectMainDriver" lay-verify="required" lay-search>
                            <option value="">请选择</option>
                        </select>
                    </div>
                </td>
                <td align="center" valign="middle">
                    <div class="layui-form" lay-filter="selectYouxianDiv">
                        <select id="selectYouxian" lay-verify="required" lay-search>
                            <option value="">请选择</option>
                        </select>
                    </div>
                </td>
            </tr>
            <tr>
                <td align="center" valign="middle">下潜压载(Kg)</td>
                <td align="center" valign="middle" id="divingLoad" name="divingLoad">${divingLoad}</td>
                <td align="center" valign="middle">上浮压载(Kg)</td>
                <td align="center" valign="middle" name="comeupLoad">${comeupLoad}</td>
            </tr>
            <tr>
                <td align="center" valign="middle">可调压载水舱液(L)</td>
                <td align="center" valign="middle" name="adjustLoad">${adjustLoad}</td>
                <td align="center" valign="middle">配重铅块(Kg)</td>
                <td align="center" valign="middle" name="peizhongQk">${peizhongQk}</td>
            </tr>
            <tr>
                <td align="center" valign="middle">采样篮工具重量(Kg)</td>
                <td align="center" valign="middle" id="basketWeight" name="basketWeight"></td>
                <td align="center" valign="middle">艏部水银液位(L)</td>
                <td align="center" valign="middle" name="mercury">${mercury}</td>
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
                                <select id="cabinSelectorCarryName0" lay-filter="cabinSelectCarryNameSelect0"
                                        lay-verify="required" lay-search>
                                    <option value="">请选择</option>
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
                                <input type="button" name="delete" value="删除"
                                       style="width: 100%;height: 100%;border:none;"
                                       onclick="deleteCabinInSelectedRow('cabinRow0')"/>
                            </td>
                        </tr>
                        <tr>
                            <td align="center" colspan="11">
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
                            <td align="center" valign="middle">计划携带数量</td>
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
                            <td align="center" valign="middle" colspan="10"
                                style="width:100px;height:100px;">
                                <!--<input name="mainTask" type="text" id="mainTask">-->
                                <!--禁止 textarea 拉伸style="resize:none" 去掉 textarea 的边框style="border:0px"-->
                                <textarea id="mainTask" name="mainTask"
                                          class="textinput" style="width:100%;height:100%;border:0px;resize: none;outline:none;border:none;overflow-y: scroll
                          ">${mainTask}</textarea>
                            </td>
                        </tr>
                        <tr>
                            <td align="center" valign="middle">作业过程</td>
                            <td align="center" valign="middle" colspan="10"
                                style="width:100px;height:100px;">
						<textarea id="workProgress" name="workProgress"
                                  class="textinput"
                                  style="width:100%;height:100%;border:0px;resize: none;outline:none;border:none;overflow-y: scroll">${workProgress}</textarea>
                                <!--<input name="workProgress" type="text" id="workProgress">-->
                            </td>
                        </tr>
                        <tr>
                            <td align="center" valign="middle">注意事项</td>
                            <td align="center" valign="middle" colspan="10"
                                style="width:100px;height:100px;">
						<textarea id="attention" name="attention"
                                  class="textinput"
                                  style="width:100%;height:100%;border:0px;resize: none;outline:none;border:none;overflow-y: scroll">${attention}</textarea>
                                <!--<input name="attention" type="text" id="attention">-->
                            </td>
                        </tr>
                        <tr>
                            <td align="center" valign="middle">作业地图附件详情</td>
                            <td id="picDetails" colspan="10">
                            </td>
                        </tr>
                        <tr>
                            <td align="center" valign="middle">采样篮图片详情</td>
                            <td id="basketPicDetail" colspan="10">
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        <p id="buttonGroup" style=" margin:0 auto; text-align:center;"><input type="button"
                                                                              id="saveDivingPlan" value="保存"
                                                                              style="color: black;font-family: bold;font-size: large"
                                                                              class="layui-btn layui-btn-primary"/>&nbsp&nbsp<input
                type="button" id="planSubmit" value="发布" style="color: black;font-family: bold;font-size: large"
                class="layui-btn layui-btn-primary"/></p>
        <!--</div>-->
    </form>
</div>
<script src="${ctx}/app/javascript/lib/layui-2.5.5/src/layui.js" charset="utf-8"></script>
<script>

    layui.config({
        base: '${ctx}/app/javascript/lib/layui-2.5.5/',
        version: true
    });

    if (typeof homeMapPathList != "undefined") {
        if (homeMapPathList.length > 0) {
            // for (var i = 0; i < homeMapPathList.length; i++) {
            //     // $("#picDetails").append([
            //     //     '<a class="example-image-link" href="' + __ctx + '/orientForm/download.rdm?fileId=' + homeMapPathList[i].fileId + '" data-lightbox="orientImage" data-title="' + homeMapPathList[i].filename + '">',
            //     //     '<img id="example-image' + i + '" src="' + __ctx + '/preview/imageSuoluetu/' + homeMapPathList[i].sltFilePath + '"></img>' + '&nbsp;&nbsp;',
            //     //     '</a>'
            //     // ].join(""));
            //
            //     $("#picDetails").append([
            //         '<a class="example-image-link" ' + '" href="' + __ctx + '/orientForm/download.rdm?fileId=' + homeMapPathList[i].fileId + '" data-lightbox="orientImage" data-title="' + homeMapPathList[i].filename + '">',
            //         '<img id="example-image' + i + '" src="' + __ctx + '/preview/imageSuoluetu/' + homeMapPathList[i].sltFilePath + '"' +
            //         '" onclick="' + 'javaScript:window.open(\'' + __ctx + '/preview/imageSuoluetu/' + homeMapPathList[i].filePath + '\')' +
            //         '"></img>' + '&nbsp;&nbsp;',
            //         '</a>'
            //     ].join(""));
            // }

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
    // $('#exportDivingPlanWord').click(function (event) {
    //     $("#divingPlanWord").wordExport('“深海勇士”号载人潜水器海上试验潜次计划');
    // });

    if (basketPicFileId != "") {
        var basketPic = typeof '${basketPicMap}';
        if (typeof basketPic != "undefined") {
            var basketPicMap = '${basketPicMap}';
            if (basketPicMap != '') {
                basketPicMap = eval('(' + basketPicMap + ')');
                $("#basketPicDetail").append([
                    '<a class="example-image-link" ' + '" href="' + __ctx + '/orientForm/download.rdm?fileId=' + basketPicMap.fileId + '" data-lightbox="orientImage" data-title="' + basketPicMap.filename + '">',
                    '<img id="example-image' + '" src="' + __ctx + '/preview/imageSuoluetu/' + basketPicMap.sltFilePath + '"' +
                    '" onclick="' + 'javaScript:window.open(\'' + __ctx + '/preview/imageSuoluetu/' + basketPicMap.filePath + '\')' +
                    '"></img>' + '&nbsp;&nbsp;',
                    '</a>'
                ].join(""));
            }
        }
    }

    // $(function () {
    //     $(window).resize(function () {
    //         var cliWidth= document.documentElement.clientWidth;
    //         var cliHeight = document.documentElement.clientHeight;
    //         var divWidth=cliWidth;
    //         var divHeight=cliHeight- 140;
    //         $('#divingPlanWord').css("width",divWidth+"px");
    //         $('#divingPlanWord').css("height",divHeight+"px");
    //
    //     })
    // });

    $.ajax({
        url: __ctx + '/accountingForm/getPersons.rdm',
        async: false,
        data: {
            "hangduanId": hangduanId,
            "taskId": taskId
        },
        success: function (data) {
            // console.log(data);
            //JSON.parse()字符串解析成json对象
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
            //JSON.parse()字符串解析成json对象
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
        //默认选中
        if (${divingType== '工程海试'}) {
            $("#project").prop('checked', true);
        } else if (${divingType == '科考应用'}) {
            $("#apply").prop('checked', true);
        } else if (${divingType eq '工程应用'}) {
            $("#train").prop('checked', true);
        } else if (${divingType eq '搜索打捞'}) {
            $("#searchSalvage").prop('checked', true);
        }


        <%--$("#mainTask").val(${mainTask});--%>
        <%--$("#workProgress").val(${workProgress});--%>
        <%--$("#attention").val(${attention});--%>
        if (typeof isSubmitTable != "undefined" && isSubmitTable == 'submit') {

        } else {
            layui.use('laydate', function () {
                var laydate = layui.laydate;
                //执行一个laydate实例
                laydate.render({
                    elem: '#positionTime', //指定各就各位元素
                    type: 'time',
                    format: 'HHmm',
                    done: function (value, date, endDate) {
                        donePositionTimeCalPlanWaterTime(value, date, endDate);
                    }
                });
                laydate.render({
                    elem: '#palnThrowTime', //指定元素
                    type: 'time',
                    format: 'HHmm'
                });
                laydate.render({
                    elem: '#divingDate', //指定元素
                    type: 'date',
                    // format: 'HHmmss'
                });
                //浮至水面时间点击确定按钮后触发此done事件
                laydate.render({
                    elem: '#planFloatToWTime', //指定元素
                    type: 'time',
                    format: 'HHmm',
                    done: function (value, date, endDate) {
                        donePlanFloatToWTimeCalPlanWaterTime(value, date, endDate);
                    }
                })
            });
        }
        // $("input[name='divingTypeCheckBox']").on('click', function () {
        //     //取消全部checkBox的选中
        //     $("input[name='divingTypeCheckBox']").prop("checked", false);
        //     // 设置选中当前
        //     $(this).prop("checked", true);
        // });

        layui.use('form', function () {
            var form = layui.form;
            //默认下拉框选中
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
                        // console.log(data);
                        //JSON.parse()字符串解析成json对象
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
                            //JSON.parse()字符串解析成json对象
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

            <%--var specialTableArrays = new Array();--%>
            <%--<c:forEach items="${showCarryToolList}" var="item" varStatus="id">--%>
            <%--&lt;%&ndash;var deviceId=${item.deviceId};&ndash;%&gt;--%>
            <%--&lt;%&ndash;var rowIndex=${item.rowNumber};&ndash;%&gt;--%>
            <%--var module = {--%>
            <%--"deviceId": "${item.deviceId}",--%>
            <%--"rowIndex": "${item.rowNumber}",--%>
            <%--"carryCount": "${item.carryCount}",--%>
            <%--"isCabinOutOrIn": "${item.isCabinOutOrIn}"--%>
            <%--};--%>
            <%--specialTableArrays.push(module);--%>
            <%--</c:forEach>--%>
            if (typeof showCarryToolList != 'undefined') {
                for (var j = 0; j < showCarryToolList.length; j++) {
                    var rowIndex = showCarryToolList[j].rowNumber;
                    var planCarryCount = showCarryToolList[j].carryCount;
                    var isCabinOutOrIn = showCarryToolList[j].isCabinOutOrIn;
                    // if (showCarryToolList[j].rowNumber == 0) {
                    //     if ("in" == isCabinOutOrIn) {
                    //         $("#cabinSelectorCarryName0").val(showCarryToolList[j].deviceId);
                    //         $("#cabinPlanCarryCount0").val(showCarryToolList[j].carryCount);
                    //     } else {
                    //         $("#selectorCarryName0").val(showCarryToolList[j].deviceId);
                    //         $("#planCarryCount0").val(showCarryToolList[j].carryCount);
                    //     }
                    //     // break;
                    // }
                    if (showCarryToolList[j].rowNumber != 0) {
                        if ("in" == isCabinOutOrIn) {
                            cabinInsertNewRow(showCarryToolList[j].deviceId, showCarryToolList[j].rowNumber, showCarryToolList[j].carryCount, carryParameters);
                        } else {
                            insertNewRow(showCarryToolList[j].deviceId, showCarryToolList[j].rowNumber, showCarryToolList[j].carryCount, carryParameters);
                        }
                    }

                    // var cabinOut = recordRed.cabinOut;
                    // var cabinIn = recordRed.cabinIn;
                    // if ("out" == isCabinOutOrIn) {
                    //     if (cabinOut.length != 0) {
                    //         for (var h = 0; h < cabinOut.length; h++) {
                    //             var deviceId = cabinOut[h].deviceId;
                    //             if (showCarryToolList[j].deviceId == deviceId) {
                    //                 $('#selectorCarryName' + rowIndex).next().find('input').css('color', 'red');
                    //                 break;
                    //             }
                    //         }
                    //     }
                    // }

                    // for (var i = 0; i < carryParameters.length; i++) {
                    //     var deviceId = carryParameters[i].id;
                    var airWeight = showCarryToolList[j].airWeight;
                    var deWaterVolume = showCarryToolList[j].deWaterVolume;
                    var freshWaterWeight = showCarryToolList[j].freshWaterWeight;
                    var connectWay = showCarryToolList[j].connectWay;
                    // var planCarryCount = carryParameters[i].planCarryCount;
                    // if (specialTableArrays[j].deviceId == deviceId) {
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
                    // form.render('select');
                    // if ("in" == isCabinOutOrIn) {
                    //     if (cabinIn.length != 0) {
                    //         for (var t = 0; t < cabinIn.length; t++) {
                    //             var deviceId = cabinIn[t].deviceId;
                    //             if (showCarryToolList[j].deviceId == deviceId) {
                    //                 $('#cabinSelectorCarryName' + rowIndex).next().find('input').css('color', 'red');
                    //                 break;
                    //             }
                    //         }
                    //     }
                    // }

                    // break;
                    // }
                    // }
                }
            }
            var selectDivingDepthRedFlag;
            if (typeof isSubmitTable != "undefined" && isSubmitTable == 'submit') {
                $("input").attr("readonly", true);
                $('select').attr("disabled", "disabled");
                $("input[type='checkbox']").attr("disabled", true);
                $("input[type='button']").attr("disabled", true);
                // $("#exportDivingPlanWord").attr("disabled", false);
                $("textarea").attr("readonly", true);
                $("#buttonGroup").remove();
                //计划标红
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

            // if (typeof isCanEdit != "undefined" && isCanEdit == 'false') {
            //     $("input").attr("readonly", true);
            //     // $('select').attr("disabled", "disabled");
            //     // $("input[type='checkbox']").attr("disabled", true);
            //     $("input[type='button']").attr("disabled", true);
            //     $("textarea").attr("readonly", true);
            //     $("#buttonGroup").remove();
            // }

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
                        if (typeof planCarryCount == 'undefined') {
                            planCarryCount = 1;
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
                                        //     var allNetWeight = airAllWeight - pWaterVolume * ($("#density").text());
                                        //     $('#allNetWeight').text(allNetWeight);
                                        // }
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
                        if (typeof planCarryCount == 'undefined') {
                            planCarryCount = 1;
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
            if (typeof isSubmitTable != "undefined" && isSubmitTable == 'submit') {
                //标红
                if (selectDivingDepthRedFlag == 1) {
                    $('#selectDivingDepth').next().find('input').css("background-color", "red");
                    $('#density').css("background-color", "red");
                }
                if (recordRed != '') {
                    var cabinOut = recordRed.cabinOut;
                    var cabinIn = recordRed.cabinIn;
                    if (typeof showCarryToolList != 'undefined') {
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
                //小写转为大写
                var Uppervalue = $("#" + param[0].id).val().toUpperCase();
                //大写转为小写
                var lowerValue = $("#" + param[0].id).val().toLowerCase();
                form.render('select');
                $("#" + param[0].nextElementSibling.id).next().find("dl").css({"display": "block"});
                var dl = $("#" + param[0].nextElementSibling.id).next().find("dl").children();
                var j = -1;
                for (var i = 0; i < dl.length; i++) {
                    if (dl[i].innerHTML.indexOf(Uppervalue) <= -1 && (dl[i].innerHTML.indexOf(lowerValue) <= -1)) {
                        dl[i].style.display = "none";
                        j++;
                    }
                    if (j == dl.length - 1) {
                        $("#" + param[0].nextElementSibling.id).next().find("dl").css({"display": "none"});
                    }
                }
            }

        });

        // $('#seaArea').text(seaArea);
        // $('#longtitude').text(jingdu);
        // $('#latitude').text(weidu);
        // $('#planDivingDepth').text(planDivingDepth);

        $("#saveDivingPlan").click(function () {
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
        });

        $("#planSubmit").click(function () {
            var divingPlanTableData = packageData();
            $.ajax({
                url: __ctx + '/accountingForm/submitDivingPlanTable.rdm?submitType=' + true,
                type: "post",
                async: false,
                data: divingPlanTableData,
                success: function (data) {
                    // $("input").attr("readonly",true);
                    // $("input[type='checkbox']").attr("disabled",true);
                    // $("input[type='button']").attr("disabled",true);
                    // $("textarea").attr("readonly",true);
                    // $("#buttonGroup").remove();
                    // layui.use('form', function () {
                    //     var form = layui.form;
                    //     $('select').attr("disabled","disabled");
                    //     form.render('select');
                    // });
                    //刷新当前页面
                    document.location.reload();
                }
            });
        });

    });
</script>

</body>
</html>
