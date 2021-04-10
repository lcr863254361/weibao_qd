//声明全局变量
var tableFlag = 1;
var firstCell = "";
var secondCell = "";
var thirdCell = "";
var fourthCell = "";
var fifthCell = "";
var sixCell = "";
var sevenCell = "";

function insertNewTable(depthDesityParameters, carryParameters) {
    var insertTableStr = "<form id=scientistPlanForm_"+tableFlag+" action="+serviceName+"/accountingForm/submitScientistDivingPlan.rdm" +" method=\"post\"\n" +
        "          name=\"formTable1\"\n" +
        "          class=\"layui-form\" enctype=\"multipart/form-data\">" +
        "       <table id=scientistPlanTable_" + tableFlag + " height=\"\" border=\"1\" align=\"center\" width=\"50%\" cellspacing=0 cellpadding=0 >\n" +
        "            <tr>\n" +
        "                <td align=\"center\" valign=\"middle\" colspan=\"4\">科学家下潜作业计划</td>\n" +
        "            </tr>\n" +
        "            <tr>\n" +
        "                <td align=\"center\" valign=\"middle\">表格填写人员</td>\n" +
        "                <td><input id=fillPerson_"+tableFlag+" name=fillPerson_"+tableFlag+" type=\"text\" \n" +
        "                           style=\"width: 100%;height: 100%;border:none;\"></td>\n" +
        "                <td align=\"center\" valign=\"middle\">作业海区</td>\n" +
        "                <td><input id=homeSeaArea_"+tableFlag+" name=homeSeaArea_"+tableFlag+" type=\"text\" \n" +
        "                           style=\"width: 100%;height: 100%;border:none;\"\n" +
        "                          >\n" +
        "                </td>\n" +
        "            </tr>\n" +
        "            <tr>\n" +
        "                <td align=\"center\" valign=\"middle\">计划下潜深度</td>\n" +
        "                <td><select  id=selectDivingDepth_" + tableFlag + " lay-filter=\"selectDivingDepthSelect\" lay-verify=\"required\"\n" +
        "                            lay-search>\n" +
        "                    <option value=\"-1\">请选择</option>\n" +
        "                </select></td>\n" +
        "                <td align=\"center\" valign=\"middle\">计划作业经度</td>\n" +
        "                <td><input id=planHomeJingdu_"+tableFlag+" name=planHomeJingdu_"+tableFlag+" type=\"text\" \n" +
        "                           style=\"width: 100%;height: 100%;border:none;\"></td>\n" +
        "            </tr>\n" +
        "            <tr>\n" +
        "                <td align=\"center\" valign=\"middle\">计划作业纬度</td>\n" +
        "                <td><input id=planHomeWeidu_"+tableFlag+" name=planHomeWeidu_"+tableFlag+" type=\"text\" \n" +
        "                           style=\"width: 100%;height: 100%;border:none;\"></td>\n" +
        "                <td align=\"center\" valign=\"middle\">密度(Kg/m³)</td>\n" +
        "                <td id=density_" + tableFlag + " name=density_"+tableFlag+"></td>\n" +
        "            </tr>\n" +
        "            <tr>\n" +
        "                <td align=\"center\" valign=\"middle\">作业地图</td>\n" +
        "                <td colspan=\"3\" id=\"pictures\"><input  id=uploadPics_"+tableFlag+" name=uploadPics_"+tableFlag+" type=\"file\"\n" +
        "                                                     style=\"width: 100%;height: 100%;border:none;\" value=\"上传图片\"\n" +
        "                                                     multiple onchange=\"newTableShowImg(this)\" accept=\"image/*\">\n" +
        "                </td>\n" +
        "            </tr>\n" +
        "            <tr>\n" +
        "                <td colspan=\"4\" style=\"border: none\" style=\"width: 100%;padding: 0\">\n" +
        "                    <table  id=cabinIn_" + tableFlag + " height=\"\" border=\"1\" align=\"center\" frame=\"void\" width=\"100% \"\n" +
        "                           cellspacing=0\n" +
        "                           cellpadding=0>\n" +
        "                        <tr id=cabinInCombineRow_" + tableFlag + ">\n" +
        "                            <td align=\"center\" valign=\"middle\" rowspan=\"2\">舱内携带的作业工具</td>\n" +
        "                            <td align=\"center\" valign=\"middle\">名称</td>\n" +
        "                            <td align=\"center\" valign=\"middle\" colspan=\"3\">空气中重量(Kg)</td>\n" +
        "                            <td align=\"center\" valign=\"middle\">计划携带数量</td>\n" +
        "                            <td align=\"center\" valign=\"middle\">净重量(Kg)</td>\n" +
        "                            <td align=\"center\" valign=\"middle\">与潜水器连接方式</td>\n" +
        "                            <td align=\"center\" valign=\"middle\">操作</td>\n" +
        "                        </tr>\n" +
        "                        <tr id=cabinInRow" + tableFlag + "_0" + " height=\"20\">\n" +
        "                            <td id=cabinCarryName"+tableFlag+"_0"+" align=\"center\" valign=\"middle\" style=\"width: auto;\">\n" +
        "                                <select id=cabinSelectorCarryName"+tableFlag+"_0"+" lay-filter=\"cabinSelectCarryNameSelect\"\n" +
        "                                        lay-verify=\"required\" lay-search>\n" +
        "                                    <option value=\"-1\">请选择</option>\n" +
        "                                </select>\n" +
        "                            </td>\n" +
        "                            <td align=\"center\" valign=\"middle\" colspan=\"3\">\n" +
        "                                <input id=cabinAirWeight"+tableFlag+"_0"+" type=\"text\"\n" +
        "                                       style=\"width: 100%;height: 100%;border:none;\">\n" +
        "                            </td>\n" +
        "                            <td align=\"center\" valign=\"middle\">\n" +
        "                                <input id=cabinPlanCarryCount"+tableFlag+"_0"+" type=\"text\" \n" +
        "                                       style=\"width: 100%;height: 100%;border:none;\"\n" +
        "                                       onkeyup=\"if(this.value.length==1){this.value=this.value.replace(/[^1-9]\\d*$/,' ')}else{this.value=this.value.replace(/\\D/g,' ')}\"\n" +
        "                                    /></td>\n" +
        "                            <td id=cabinNetWeight"+tableFlag+"_0"+" align=\"center\" valign=\"middle\"></td>\n" +
        "                            <td align=\"center\" valign=\"middle\">\n" +
        "                                <input id=cabinConnectWay"+tableFlag+"_0"+" type=\"text\" \n" +
        "                                       style=\"width: 100%;height: 100%;border:none;\">\n" +
        "                            </td>\n" +
        "                            <td style=\"width:80px\">\n" +
        "                                <input type=\"button\" name=\"delete\" value=\"删 除\"\n" +
        "                                       style=\"width: 100%;height: 100%;border:none;\"\n" +
        "                                       onclick=\"newTableDeleteCabinInSelectedRow(this)\"/>\n" +
        "                            </td>\n" +
        "                        </tr>\n" +
        "                        <tr>\n" +
        "                            <td align=\"center\" colspan=\"10\">\n" +
        "                                <br/>\n" +
        "                                <input id=addCabinInButton_"+tableFlag+" type=\"button\" name=\"insert\" value=\"增加一行\" style=\"width:80px\"\n" +
        "                                       onclick=\"insertCabinInRow(this)\"/>&nbsp&nbsp\n" +
        "                            </td>\n" +
        "                        </tr>\n" +
        "                        <tr>\n" +
        "                            <td align=\"center\" valign=\"middle\">舱内携带的作业工具总重量</td>\n" +
        "                            <td></td>\n" +
        "                            <td id=cabinAirAllWeight_"+tableFlag+" colspan=\"3\"></td>\n" +
        "                            <td></td>\n" +
        "                            <td id=cabinAllNetWeight_"+tableFlag+" align=\"center\" valign=\"middle\"></td>\n" +
        "                            <td></td>\n" +
        "                            <td><input id=calCabinInButton_"+tableFlag+" type=\"button\" value=\" 计算 \"\n" +
        "                                       style=\"width: 100%;height: 100%;border:none;\"\n" +
        "                                       onclick=\"calCabinInTotalAirWeight(this)\"/></td>\n" +
        "                        </tr>\n" +
        "                        <tr></tr>\n" +
        "                    </table>\n" +
        "                </td>\n" +
        "            </tr>\n" +
        "            <tr>\n" +
        "                <td colspan=\"4\" style=\"border: none\" style=\"width: 100%;padding: 0\">\n" +
        "                    <table id=cabinOut_" + tableFlag + " height=\"\" border=\"1\" align=\"center\" frame=\"void\" width=\"100% \"\n" +
        "                           cellspacing=0\n" +
        "                           cellpadding=0>\n" +
        "                        <tr id=cabinOutCombineRow_" + tableFlag + ">\n" +
        "                            <td align=\"center\" valign=\"middle\" rowspan=\"2\">舱外携带的作业工具</td>\n" +
        "                            <td align=\"center\" valign=\"middle\">名称</td>\n" +
        "                            <td align=\"center\" valign=\"middle\">空气中重量(Kg)</td>\n" +
        "                            <td align=\"center\" valign=\"middle\">排水体积(L)</td>\n" +
        "                            <td align=\"center\" valign=\"middle\">淡水中重量(Kg)</td>\n" +
        "                            <td align=\"center\" valign=\"middle\">计划携带数量</td>\n" +
        "                            <td align=\"center\" valign=\"middle\">净重量(Kg)</td>\n" +
        "                            <td align=\"center\" valign=\"middle\">与潜水器连接方式</td>\n" +
        "                            <td align=\"center\" valign=\"middle\">操作</td>\n" +
        "                        </tr>\n" +
        "                        <tr id=cabinOutRow" + tableFlag + "_0" + " height=\"20\">\n" +
        "                            <td id=carryName"+tableFlag+"_0"+" align=\"center\" valign=\"middle\" style=\"width: auto;\">\n" +
        "                                <select id=selectorCarryName"+tableFlag+"_0"+" lay-filter=\"selectCarryNameSelect\"\n" +
        "                                        lay-verify=\"required\" lay-search>\n" +
        "                                    <option value=\"-1\">请选择</option>\n" +
        "                                </select>\n" +
        "                            </td>\n" +
        "                            <td align=\"center\" valign=\"middle\">\n" +
        "                                <input id=airWeight"+tableFlag+"_0"+" type=\"text\"\n" +
        "                                       style=\"width: 100%;height: 100%;border:none;\">\n" +
        "                            </td>\n" +
        "                            <td align=\"center\" valign=\"middle\">\n" +
        "                                <input id=pWaterVolume"+tableFlag+"_0"+" type=\"text\"\n" +
        "                                       style=\"width: 100%;height: 100%;border:none;\"\n" +
        "                                       >\n" +
        "                            </td>\n" +
        "                            <td align=\"center\" valign=\"middle\">\n" +
        "                                <input id=freshWaterVolume"+tableFlag+"_0"+" type=\"text\"\n" +
        "                                       style=\"width: 100%;height: 100%;border:none;\"\n" +
        "                                     ></td>\n" +
        "                            <td align=\"center\" valign=\"middle\">\n" +
        "                                <input id=planCarryCount"+tableFlag+"_0"+" type=\"text\"\n" +
        "                                       style=\"width: 100%;height: 100%;border:none;\"\n" +
        "                                       onkeyup=\"if(this.value.length==1){this.value=this.value.replace(/[^1-9]\\d*$/,' ')}else{this.value=this.value.replace(/\\D/g,' ')}\"\n" +
        "                                       /></td>\n" +
        "                            <td id=netWeight"+tableFlag+"_0"+" align=\"center\" valign=\"middle\"></td>\n" +
        "                            <td align=\"center\" valign=\"middle\">\n" +
        "                                <input id=connectWay"+tableFlag+"_0"+" type=\"text\"\n" +
        "                                       style=\"width: 100%;height: 100%;border:none;\">\n" +
        "                            </td>\n" +
        "                            <td style=\"width:80px\">\n" +
        "                                <input type=\"button\" name=\"delete\" value=\"删 除\"\n" +
        "                                       style=\"width: 100%;height: 100%;border:none;\"\n" +
        "                                       onclick=\"newTableDeleteSelectedRow(this)\"/>\n" +
        "                            </td>\n" +
        "                        </tr>\n" +
        "                        <tr>\n" +
        "                            <td align=\"center\" colspan=\"9\">\n" +
        "                                <br/>\n" +
        "                                <input id=addCabinOutButton_"+tableFlag+" type=\"button\" name=\"insert\" value=\"增加一行\" style=\"width:80px\"\n" +
        "                                       onclick=\"insertCarryOutRow(this)\"/>&nbsp&nbsp\n" +
        "                            </td>\n" +
        "                        </tr>\n" +
        "                        <!--</div>-->\n" +
        "                        <tr>\n" +
        "                            <td align=\"center\" valign=\"middle\">舱外携带的作业工具总重量</td>\n" +
        "                            <td></td>\n" +
        "                            <td id=airAllWeight_"+tableFlag+"></td>\n" +
        "                            <td id=pWaterAllVolume_"+tableFlag+"></td>\n" +
        "                            <td id=freshWaterAllVolume_"+tableFlag+"></td>\n" +
        "                            <td></td>\n" +
        "                            <td id=allNetWeight_"+tableFlag+" align=\"center\" valign=\"middle\"></td>\n" +
        "                            <td></td>\n" +
        "                            <td><input id=calCabinOutButton_"+tableFlag+" type=\"button\" value=\" 计算 \"\n" +
        "                                       style=\"width: 100%;height: 100%;border:none;\"\n" +
        "                                       onclick=\"calCabinOutTotalAirWeight(this)\"/></td>\n" +
        "                        </tr>\n" +
        "                        <tr>\n" +
        "                            <td align=\"center\" valign=\"middle\">主要任务</td>\n" +
        "                            <td align=\"center\" valign=\"middle\" colspan=\"8\" style=\"width:100px;height:100px;\">\n" +
        "                                <!--<input name=\"mainTask\" type=\"text\" id=\"mainTask\">-->\n" +
        "                                <!--禁止 textarea 拉伸style=\"resize:none\" 去掉 textarea 的边框style=\"border:0px\"-->\n" +
        "                                <textarea id=mainTask_"+tableFlag+" name=mainTask_"+tableFlag+" \n" +
        "                                          class=\"textinput\" style=\"width:100%;height:100%;border:0px;resize: none;outline:none;border:none;overflow-y: scroll\n" +
        "                          \"></textarea>\n" +
        "                            </td>\n" +
        "                        </tr>\n" +
        "                        <tr>\n" +
        "                            <td align=\"center\" valign=\"middle\">作业过程</td>\n" +
        "                            <td align=\"center\" valign=\"middle\" colspan=\"8\" style=\"width:100px;height:100px;\">\n" +
        "\t\t\t\t\t\t<textarea id=workProgress_"+tableFlag+" name=workProgress_"+tableFlag+" \n" +
        "                                  class=\"textinput\"\n" +
        "                                  style=\"width:100%;height:100%;border:0px;resize: none;outline:none;border:none;overflow-y: scroll\"></textarea>\n" +
        "                                <!--<input name=\"workProgress\" type=\"text\" id=\"workProgress\">-->\n" +
        "                            </td>\n" +
        "                        </tr>\n" +
        "                        <tr>\n" +
        "                            <td align=\"center\" valign=\"middle\">注意事项</td>\n" +
        "                            <td align=\"center\" valign=\"middle\" colspan=\"8\" style=\"width:100px;height:100px;\">\n" +
        "\t\t\t\t\t\t<textarea id=attention_"+tableFlag+" name=attention_"+tableFlag+" \n" +
        "                                  class=\"textinput\"\n" +
        "                                  style=\"width:100%;height:100%;border:0px;resize: none;outline:none;border:none;overflow-y: scroll\"></textarea>\n" +
        "                                <!--<input name=\"attention\" type=\"text\" id=\"attention\">-->\n" +
        "                            </td>\n" +
        "                        </tr>\n" +
        "                        <tr>\n" +
        "                            <td align=\"center\" valign=\"middle\">图片详情</td>\n" +
        "                            <td id=picDetails_"+tableFlag+" colspan=\"8\">\n" +
        "                            </td>\n" +
        "                        </tr>\n" +
        "                    </table>\n" +
        "                </td>\n" +
        "            </tr>\n" +
        "        </table>" +
        "<p style=\" margin:0 auto; text-align:center;\"><input type=\"button\" id=scientistPlanSubmit_"+tableFlag+" value=\"保存\" class=\"layui-btn layui-btn-primary\" style=\"font-family: bold;font-size: large\"\n" +
        "       onclick=\"scientistPlanSave(this)\" />&nbsp&nbsp\n" +
        "            <input type=\"button\" id=agreeScientistPlanButton"+tableFlag+" value=\"提交\"\n" +
        "                   style=\"font-family: bold;font-size: large\"\n" +
        " class=\"layui-btn layui-btn-primary\"\n"+
        "             onclick=\"agreeScientistPlan(this)\"/></p>" +
        "</form>"
    var div = $('#scientistDivingPlanDiv');
    div.append(insertTableStr);
    var forms = document.getElementsByTagName('form');
    var selectorDivingDepth = forms[tableFlag][2];
    for (var j = 0; j < depthDesityParameters.length; j++) {
        var optionDepth = new Option(depthDesityParameters[j].depth, depthDesityParameters[j].id);
        selectorDivingDepth.options.add(optionDepth);
    }

    var cabinSelectorCarryName = forms[tableFlag][6];
    var selectorCarryName = forms[tableFlag][13];
    for (var j = 0; j < carryParameters.length; j++) {
        var optionZuoxian;
        if (carryParameters[j].isCabinOutOrIn == 'cabinIn') {
            optionZuoxian = new Option(carryParameters[j].name, carryParameters[j].id);
            cabinSelectorCarryName.options.add(optionZuoxian);
        } else {
            optionZuoxian = new Option(carryParameters[j].name, carryParameters[j].id);
            selectorCarryName.options.add(optionZuoxian);
        }
    }
    layui.use('form', function () {
        var form = layui.form;
        form.on('select(selectCarryNameSelect)', function (data) {
            var rowIndex = data.elem.parentNode.parentNode.rowIndex - 1;
            var tableId = data.elem.parentNode.parentNode.parentNode.parentElement.getAttribute('id');
            var tableIdArray = tableId.split("_");
            var rowId = tableIdArray[0] + "Row" + tableIdArray[1] + "_" + rowIndex;
            var chooseId = $("#" + rowId + " td:eq(0)").children().eq(0).val();
            // var planCarryCount=$("#row" + rowIndex + " td:eq(4)").children().eq(0).val();
            if (chooseId == -1) {
                $("#" + rowId + " td:eq(1)").children().eq(0).val("");
                $("#" + rowId + " td:eq(2)").children().eq(0).val("");
                $("#" + rowId + " td:eq(3)").children().eq(0).val("");
                $("#" + rowId + " td:eq(4)").children().eq(0).val("");
                $("#" + rowId + " td:eq(5)").text("");
                $("#" + rowId + " td:eq(6)").children().eq(0).val("");
            } else {
                for (var i = 0; i < carryParameters.length; i++) {
                    var deviceId = carryParameters[i].id;
                    var airWeight = carryParameters[i].airWeight;
                    var deWaterVolume = carryParameters[i].deWaterVolume;
                    var freshWaterWeight = carryParameters[i].freshWaterWeight;
                    var planCarryCount = carryParameters[i].planCarryCount;
                    var connectWay = carryParameters[i].connectWay;

                    if (chooseId == deviceId) {
                        $("#" + rowId + " td:eq(1)").children().eq(0).val(airWeight);
                        $("#" + rowId + " td:eq(2)").children().eq(0).val(deWaterVolume);
                        $("#" + rowId + " td:eq(3)").children().eq(0).val(freshWaterWeight);
                        $("#" + rowId + " td:eq(4)").children().eq(0).val(planCarryCount);
                        $("#" + rowId + " td:eq(6)").children().eq(0).val(connectWay);
                        //计算净重量
                        if (airWeight != null && airWeight != "") {
                            if (deWaterVolume != null && deWaterVolume != "") {
                                var density = $("#density_"+tableIdArray[1]).text();
                                if (density != null && density != "") {
                                    var netWeight = (airWeight - (deWaterVolume * 0.001 * density)) * planCarryCount;
                                    netWeight = (Math.round(netWeight * 10) / (10)).toFixed(1);
                                    $("#" + rowId + " td:eq(5)").text(netWeight);
                                }
                            }
                        }
                        break;
                    }
                }
            }
        });

        form.on('select(cabinSelectCarryNameSelect)', function (data) {
            var rowIndex = data.elem.parentNode.parentNode.rowIndex - 1;
            var tableId = data.elem.parentNode.parentNode.parentNode.parentElement.getAttribute('id');
            var tableIdArray = tableId.split("_");
            var rowId = tableIdArray[0] + "Row" + tableIdArray[1] + "_" + rowIndex;
            var chooseId = $("#" + rowId + " td:eq(0)").children().eq(0).val();
            if (chooseId == -1) {
                $("#" + rowId + " td:eq(1)").children().eq(0).val("");
                $("#" + rowId + " td:eq(2)").children().eq(0).val("");
                $("#" + rowId + " td:eq(3)").text("");
                $("#" + rowId + " td:eq(4)").children().eq(0).val("");
            } else {
                for (var i = 0; i < carryParameters.length; i++) {
                    var deviceId = carryParameters[i].id;
                    var airWeight = carryParameters[i].airWeight;
                    var planCarryCount = carryParameters[i].planCarryCount;
                    var connectWay = carryParameters[i].connectWay;

                    if (chooseId == deviceId) {
                        $("#" + rowId + " td:eq(1)").children().eq(0).val(airWeight);
                        $("#" + rowId + " td:eq(2)").children().eq(0).val(planCarryCount);
                        $("#" + rowId + " td:eq(4)").children().eq(0).val(connectWay);
                        // $("#row" + rowIndex + " td:eq(4)").text(planCarryCount);
                        //计算净重量
                        if (airWeight != null && airWeight != "") {
                            if (typeof planCarryCount == 'undefined') {
                                planCarryCount = 1;
                            }
                            var netWeight = airWeight * planCarryCount;
                            netWeight = (Math.round(netWeight * 10) / (10)).toFixed(1);
                            $("#" + rowId + " td:eq(3)").text(netWeight);
                        }
                        break;
                    }
                }
            }
        });

        form.on('select(selectDivingDepthSelect)', function (data) {
            var chooseId = data.value;
            var depthElementId = data.elem.getAttribute('id');
            var depthArray = depthElementId.split("_");
            if (chooseId == -1) {
                $('#density_' + depthArray[1]).text("");
            } else {
                for (var i = 0; i < depthDesityParameters.length; i++) {
                    var depthDesityId = depthDesityParameters[i].id;
                    var density = depthDesityParameters[i].density;
                    if (chooseId == depthDesityId) {
                        $('#density_' + depthArray[1]).text(density);
                    }
                }
                // calCabinOutTotalAirWeight();
            }
        });
        form.render();
    });
    tableFlag++;
};

//-----------------新增一行-----------start---------------
function insertCarryOutRow(param) {
    var insertFlag;
    var tableElementId = param.parentElement.parentElement.parentElement.parentElement.id;
    var tableIdArray = tableElementId.split("_");
    //获取表格有多少行
    var rowLength = $("#" + tableElementId + " tr").length;
    var rowCount = rowLength - 7;
    //这里的rowId就是row加上标志位的组合。是每新增一行的tr的id。
    insertFlag = rowCount;
    var rowId = tableIdArray[0] + "Row" + tableIdArray[1] + "_" + insertFlag;

    //每次往下标为flag+1的下面添加tr,因为append是往标签内追加。所以用after
    var insertStr = "<tr id=" + rowId + ">"
        + "<td id=carryName"+tableIdArray[1]+"_"+insertFlag+" align='center' valign='middle' style='width: auto;'>" + firstCell + "<select  id=selectorCarryName"+tableIdArray[1]+"_"+insertFlag+" lay-filter=\"selectCarryNameSelect\" lay-verify=\"required\" lay-search>\n" +
        "                                <option value=\"-1\">请选择</option>\n" +
        "                            </select>" + "</td>"
        + "<td align='center' valign='middle' style='width: auto;'>" + secondCell + " <input  id=airWeight"+tableIdArray[1]+"_"+insertFlag+" type=\"text\"  style=\"width: 100%;height: 100%;border:none;\" />" + "</td>"
        + "<td align='center' valign='middle' style='width: auto;'>" + thirdCell + " <input  id=pWaterVolume"+tableIdArray[1]+"_"+insertFlag+" type=\"text\"  style=\"width: 100%;height: 100%;border:none;\" />" + "</td>"
        + "<td align='center' valign='middle' style='width: auto;'>" + fourthCell + " <input id=freshWaterVolume"+tableIdArray[1]+"_"+insertFlag+" type=\"text\"  style=\"width: 100%;height: 100%;border:none;\" />" + "</td>"
        + "<td align='center' valign='middle' style='width: auto;'>" + fifthCell + " <input id=planCarryCount"+tableIdArray[1]+"_"+insertFlag+" type=\"text\"  style=\"width: 100%;height: 100%;border:none;\" onkeyup=\"if(this.value.length==1){this.value=this.value.replace(/[^1-9]\\d*$/,' ')}else{this.value=this.value.replace(/\\D/g,' ')}\"\n" +
        "                                      />" + "</td>"
        + "<td id=netWeight"+tableIdArray[1]+"_"+insertFlag+" align='center' valign='middle' style='width: auto;'>" + sixCell + "</td>"
        + "<td align='center' valign='middle' style='width: auto;'>" + sevenCell + " <input  id=connectWay"+tableIdArray[1]+"_"+insertFlag+" type=\"text\"  style=\"width: 100%;height: 100%;border:none;\" />" + "</td>"
        + "<td style='width:80px'><input type='button' name='delete' value='删除' style='width: 100%;height: 100%;border:none;' onclick='newTableDeleteSelectedRow(\"" + rowId + "\")' />";
    +"</td>"
    + "</tr>";
    //这里的行数减2，是因为要减去底部的一行和顶部的一行，剩下的为开始要插入行的索引
    $("#" + tableElementId + " tr:eq(" + (rowLength - 7) + ")").after(insertStr); //将新拼接的一行插入到当前行的下面

    var combineRowElementId = param.parentElement.parentElement.parentElement.children[0].getAttribute('id');
    $("#" + combineRowElementId + " td:eq(0)").attr("rowspan", insertFlag + 2);
    commonCarryTool(rowId);
    layui.use('form', function () {
        var form = layui.form;
        form.render('select');
    });
}

function commonCarryTool(rowId) {
    var selectorCarryName = $("#" + rowId + " td:eq(0) select")[0];
    for (var j = 0; j < carryParameters.length; j++) {
        var optionZuoxian;
        if (carryParameters[j].isCabinOutOrIn != 'cabinIn') {
            optionZuoxian = new Option(carryParameters[j].name, carryParameters[j].id);
            selectorCarryName.options.add(optionZuoxian);
        }
    }
}

function insertCabinInRow(param) {
    var insertFlag;
    var tableElementId = param.parentElement.parentElement.parentElement.parentElement.id;
    var tableIdArray = tableElementId.split("_");
    //获取表格有多少行
    var rowLength = $("#" + tableElementId + " tr").length;
    var rowCount = rowLength - 4;
    //这里的rowId就是row加上标志位的组合。是每新增一行的tr的id。
    insertFlag = rowCount;
    var rowId = tableIdArray[0] + "Row" + tableIdArray[1] + "_" + insertFlag;

    //每次往下标为flag+1的下面添加tr,因为append是往标签内追加。所以用after
    var insertStr = "<tr id=" + rowId + ">"
        + "<td id=cabinCarryName"+tableIdArray[1] + "_" + insertFlag+" align='center' valign='middle' style='width: auto;'>" + firstCell + "<select  id=cabinSelectorCarryName"+tableIdArray[1] + "_" + insertFlag+" lay-filter=\"cabinSelectCarryNameSelect\" lay-verify=\"required\" lay-search>\n" +
        "                                <option value=\"-1\">请选择</option>\n" +
        "                            </select>" + "</td>"
        + "<td align='center' valign='middle' style='width: auto;' colspan='3'>" + secondCell + " <input id=cabinAirWeight"+tableIdArray[1] + "_" + insertFlag+" type=\"text\"  style=\"width: 100%;height: 100%;border:none;\" />" + "</td>"
        // + "<td align='center' valign='middle' style='width: auto;'>" + thirdCell + "</td>"
        // + "<td align='center' valign='middle' style='width: auto;'>" + fourthCell + "</td>"
        + "<td align='center' valign='middle' style='width: auto;'>" + fifthCell + " <input id=cabinPlanCarryCount"+tableIdArray[1] + "_" + insertFlag+" type=\"text\"  style=\"width: 100%;height: 100%;border:none;\" onkeyup=\"if(this.value.length==1){this.value=this.value.replace(/[^1-9]\\d*$/,' ')}else{this.value=this.value.replace(/\\D/g,' ')}\"\n" +
        "                                      />" + "</td>"
        + "<td id=cabinNetWeight"+tableIdArray[1] + "_" + insertFlag+" align='center' valign='middle' style='width: auto;'>" + sixCell + "</td>"
        + "<td align='center' valign='middle' style='width: auto;'>" + sevenCell + " <input id=cabinConnectWay"+tableIdArray[1] + "_" + insertFlag+" type=\"text\"  style=\"width: 100%;height: 100%;border:none;\" />" + "</td>"
        + "<td style='width:80px'><input type='button' name='delete' value='删除' style='width: 100%;height: 100%;border:none;' onclick='newTableDeleteCabinInSelectedRow(\"" + rowId + "\")' />";
    +"</td>"
    + "</tr>";
    //这里的行数减2，是因为要减去底部的一行和顶部的一行，剩下的为开始要插入行的索引
    $("#" + tableElementId + " tr:eq(" + (rowLength - 4) + ")").after(insertStr); //将新拼接的一行插入到当前行的下面
    //为新添加的行里面的控件添加新的id属性。
    // $("#" + rowId + " td:eq(0)").attr("id", "cabinCarryName" + tableIdArray[1] + "_" + insertFlag);
    // $("#" + rowId + " td:eq(1)").children().eq(0).attr("id", "cabinAirWeight" + tableIdArray[1] + "_" + insertFlag);
    // $("#" + rowId + " td:eq(2)").children().eq(0).attr("id", "cabinPlanCarryCount" + tableIdArray[1] + "_" + insertFlag);
    // $("#" + rowId + " td:eq(3)").attr("id", "cabinNetWeight" + tableIdArray[1] + "_" + insertFlag);
    // $("#" + rowId + " td:eq(4)").children().eq(0).attr("id", "cabinConnectWay" + tableIdArray[1] + "_" + insertFlag);
    // $("#" + rowId + " td:eq(0)").children().eq(0).attr("id", "cabinSelectorCarryName" + tableIdArray[1] + "_" + insertFlag);

    var selectorCarryName = $("#" + rowId + " td:eq(0)").children().eq(0)[0];
    for (var i = 0; i < carryParameters.length; i++) {
        if (carryParameters[i].isCabinOutOrIn == 'cabinIn') {
            var optionZuoxian = new Option(carryParameters[i].name, carryParameters[i].id);
            selectorCarryName.options.add(optionZuoxian);
        }
    }
    var firstRowElementId = param.parentElement.parentElement.parentElement.children[0].getAttribute('id');
    $("#" + firstRowElementId + " td:eq(0)").attr("rowspan", insertFlag + 2);
    layui.use('form', function () {
        var form = layui.form;
        form.render('select');
    });
}

//-----------------删除一行，根据行ID删除-start--------
function newTableDeleteSelectedRow(rowID) {
    if (confirm("确定删除该行吗？")) {
        if (rowID.type == 'button') {
            //删除第一行
            rowID = rowID.parentElement.parentElement.id;
        }
        var combineRowElementId = $("#" + rowID)[0].parentElement.children[0].getAttribute('id');
        var tableElementId = $("#" + rowID)[0].parentElement.parentElement.id;
        $("#" + rowID).remove();
        $("#" + combineRowElementId + " td:eq(0)").attr("rowspan", $("#" + tableElementId + " tr").length - 6);
    }
}

function newTableDeleteCabinInSelectedRow(rowID) {
    if (confirm("确定删除该行吗？")) {
        if (rowID.type == 'button') {
            //删除第一行
            rowID = rowID.parentElement.parentElement.id;
        }
        var combineRowElementId = $("#" + rowID)[0].parentElement.children[0].getAttribute('id');
        var tableElementId = $("#" + rowID)[0].parentElement.parentElement.id;
        $("#" + rowID).remove();
        $("#" + combineRowElementId + " td:eq(0)").attr("rowspan", $("#" + tableElementId + " tr").length - 3);
    }
}

// 计算舱外携带工具重量
function calCabinOutTotalAirWeight(param) {
    var cabinOutTableEleId=param.parentElement.parentElement.parentElement.parentElement.id;
    var cabinOutTableIdArray=cabinOutTableEleId.split("_");
    var density = $("#density_"+cabinOutTableIdArray[1]).text();

    var specialTableRowCount = $("#"+cabinOutTableEleId+" tr").length - 7;
    if (specialTableRowCount > 0) {
        var airAllWeight = 0;
        var pWaterAllVolume = 0;
        var freshWaterAllVolume = 0;
        for (var i = 0; i < specialTableRowCount; i++) {
            var airWeight = Number($('#airWeight' +cabinOutTableIdArray[1]+"_"+ i).val());
            var planCarryCount = Number($('#planCarryCount' +cabinOutTableIdArray[1]+"_"+i).val());
            var pWaterVolume = Number($('#pWaterVolume' +cabinOutTableIdArray[1]+"_"+ i).val());
            var freshWaterVolume = Number($('#freshWaterVolume' +cabinOutTableIdArray[1]+"_"+ i).val());
            // var planCarryCount=Number($('#planCarryCount'+i).val());
            var netWeight = Number((airWeight - (pWaterVolume * 0.001 * density)) * planCarryCount);
            netWeight = (Math.round(netWeight * 10) / (10)).toFixed(1);
            //计算净重量
            $("#netWeight" +cabinOutTableIdArray[1]+"_"+ i).text(netWeight);
            airAllWeight += airWeight * planCarryCount;
            pWaterAllVolume += pWaterVolume * planCarryCount;
            freshWaterAllVolume += freshWaterVolume * planCarryCount;
        }
        airAllWeight=(Math.round(airAllWeight * 10) / (10)).toFixed(1);
        pWaterAllVolume=(Math.round(pWaterAllVolume * 10) / (10)).toFixed(1);
        freshWaterAllVolume=(Math.round(freshWaterAllVolume * 10) / (10)).toFixed(1);
        $('#airAllWeight_'+cabinOutTableIdArray[1]).text(Number(airAllWeight));
        $('#pWaterAllVolume_'+cabinOutTableIdArray[1]).text(Number(pWaterAllVolume));
        $('#freshWaterAllVolume_'+cabinOutTableIdArray[1]).text(Number(freshWaterAllVolume));
        var allNetWeight = Number(Number(airAllWeight) - (Number(pWaterAllVolume) * 0.001 * (Number($("#density_"+cabinOutTableIdArray[1]).text()))));
        allNetWeight = (Math.round(allNetWeight * 10) / (10)).toFixed(1);

        $('#allNetWeight_'+cabinOutTableIdArray[1]).text(Number(allNetWeight));
    } else {
        $('#airAllWeight_'+cabinOutTableIdArray[1]).text("");
        $('#pWaterAllVolume_'+cabinOutTableIdArray[1]).text("");
        $('#freshWaterAllVolume_'+cabinOutTableIdArray[1]).text("");
        $('#allNetWeight_'+cabinOutTableIdArray[1]).text("");
    }
}

//计算舱内携带的作业工具重量
function calCabinInTotalAirWeight(param) {
    var cabinInTableEleId=param.parentElement.parentElement.parentElement.parentElement.id;
    var cabinInTableIdArray=cabinInTableEleId.split("_");

    var cabinCarryTableRowCount = $("#"+cabinInTableEleId+" tr").length - 4;
    if (cabinCarryTableRowCount > 0) {
        var airAllWeight = 0;
        for (var i = 0; i < cabinCarryTableRowCount; i++) {
            var airWeight = Number($('#cabinAirWeight' +cabinInTableIdArray[1]+"_"+ i).val());
            var planCarryCount = Number($('#cabinPlanCarryCount' + cabinInTableIdArray[1]+"_"+i).val());
            var netWeight = Number(airWeight * planCarryCount);
            netWeight = (Math.round(netWeight * 10) / (10)).toFixed(1);
            //计算净重量
            $("#cabinNetWeight" + cabinInTableIdArray[1]+"_"+i).text(netWeight);
            airAllWeight += airWeight * planCarryCount;
        }
        airAllWeight = (Math.round(airAllWeight * 10) / (10)).toFixed(1);
        $('#cabinAirAllWeight_'+cabinInTableIdArray[1]).text(Number(airAllWeight));
        var allNetWeight = Number(airAllWeight);
        allNetWeight = (Math.round(allNetWeight * 10) / (10)).toFixed(1);
        $('#cabinAllNetWeight_'+cabinInTableIdArray[1]).text(Number(allNetWeight));
    } else {
        $('#cabinAirAllWeight_'+cabinInTableIdArray[1]).text("");
        $('#cabinAllNetWeight_'+cabinInTableIdArray[1]).text("");
    }
}

function scientistPlanSave(param) {
    var bigTableEleId=param.parentElement.parentElement.children[0].id;
    var bigTableIdArray=bigTableEleId.split("_");
    var calCabinOutButton=$('#calCabinOutButton_'+bigTableIdArray[1])[0];
    var calCabinInButton=$('#calCabinInButton_'+bigTableIdArray[1])[0];
    calCabinInTotalAirWeight(calCabinInButton);
    calCabinOutTotalAirWeight(calCabinOutButton);
    var specialRowData = getCabinOutRowData(bigTableIdArray[1]);
    specialRowData = JSON.stringify(specialRowData);
    var cabinInRowData = getCabinInRowData(bigTableIdArray[1]);
    cabinInRowData= JSON.stringify(cabinInRowData);
    var totalNetWeight=getNewTableTotalNetWeight(bigTableIdArray[1]);
    totalNetWeight=JSON.stringify(totalNetWeight);
    var planDivingDepth = $("#selectDivingDepth_"+bigTableIdArray[1]).val();
    var currentFormEleId=param.parentElement.parentElement.id;
    var f = $('#'+currentFormEleId)[0];
    // 获取表单内容
    var formData = new FormData(document.getElementById(currentFormEleId));
    formData.set('tableFlag',bigTableIdArray[1]);
    $.ajax({
        url: f.action + "?taskId=" + taskId + "&specialRowData=" + specialRowData +"&cabinInRowData="+cabinInRowData+"&totalNetWeight="+totalNetWeight+ "&planDivingDepth=" + planDivingDepth,
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

function agreeScientistPlan(param) {
    var bigTableEleId=param.parentElement.parentElement.children[0].id;
    var bigTableIdArray=bigTableEleId.split("_");
    var tableFlag=bigTableIdArray[1];
    $.ajax({
        url: serviceName + '/accountingForm/agreeScientistPlanTable.rdm',
        type: "post",
        async: false,
        data: {'taskId':taskId,'tableFlag':tableFlag},
        success: function (data) {
            var msg=JSON.parse(data).msg;
            alert(msg);
            //刷新当前页面
            document.location.reload();
        }
    });
}

//-----------------获取表单中的值----start--------------
function getCabinOutRowData(cabinOutFlag) {
    var specialRowData = [];
    var find = false;
    $('#cabinOut_'+cabinOutFlag+' tr').each(function (i) {
        if (i >= 1 && i <= $('#cabinOut_'+cabinOutFlag+' tr').length - 7) {
            //获取每行第一个单元格里的selector控件的值
            var singleObject = new Object();
            var selectorValue = $(this).children().eq(0).children().eq(0).val();
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
                if (selectorValue != -1) {
                    // value += selectorValue + "#";
                    specialRowData.push(singleObject);
                }
            }
        }
    });
    return specialRowData;
}

//获取舱内携带作业工具的数据
function getCabinInRowData(cabinInFlag) {
    var cabinInRowData = [];
    var find = false;
    $('#cabinIn_'+cabinInFlag+' tr').each(function (i) {
        if (i >= 1 && i <= $('#cabinIn_'+cabinInFlag+' tr').length - 4) {
            //获取每行第一个单元格里的selector控件的值
            var singleObject = new Object();
            var selectorValue = $(this).children().eq(0).children().eq(0).val();
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
                if (selectorValue != -1) {
                    // value += selectorValue + "#";
                    cabinInRowData.push(singleObject);
                }
            }
        }
    });
    return cabinInRowData;
}

function getNewTableTotalNetWeight(tableFlag){
    var totalNetWeight = [];
    var cabinInNetWeight=$('#cabinAirAllWeight_'+tableFlag).text();
    var cabinOutNetWeight=$('#allNetWeight_'+tableFlag).text();
    var pWaterAllVolume=$('#pWaterAllVolume_'+tableFlag).text();
    if (typeof cabinInNetWeight != 'undefined'){
        var singleObject = new Object();
        singleObject.isTotalCabinOutOrIn="in";
        singleObject.allNetWeight=cabinInNetWeight;
        totalNetWeight.push(singleObject);
    }
    if (typeof cabinOutNetWeight != 'undefined'){
        var singleObject=new Object();
        singleObject.isTotalCabinOutOrIn="out";
        singleObject.allNetWeight=cabinOutNetWeight;
        singleObject.pWaterAllVolume=pWaterAllVolume;
        totalNetWeight.push(singleObject);
    }
    return totalNetWeight;
}

function newTableShowImg(cell) {
    var uploadImgeEleId=cell.id;
    var uploadImgArrays=uploadImgeEleId.split("_");
    if (cell.files.length > 0) {
        var uploadPics = document.getElementById("picDetails_"+uploadImgArrays[1]);
        //移除图片孩子节点
        var childImgs = uploadPics.children;
        if (childImgs.length > 0) {
            for (var i = 0; i < childImgs.length; i++) {
                var img = document.getElementById(childImgs[i].id);
                uploadPics.removeChild(img);
                childImgs = uploadPics.children;
                i--;
            }
        }
        // uploadPics.getChild();
        for (var i = 0; i < cell.files.length; i++) {
            var img = document.createElement("img");
            img.id = "img" + i;
            img.setAttribute("width", "50");
            img.src = window.URL.createObjectURL(cell.files[i]);
            img.width = 200;
            uploadPics.appendChild(img);
        }
    }
}
