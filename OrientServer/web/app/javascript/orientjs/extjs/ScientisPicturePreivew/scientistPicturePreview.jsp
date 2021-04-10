<%--
  Created by IntelliJ IDEA.
  User: SunHao
  Date: 2020/5/11
  Time: 16:17
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
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <link rel="stylesheet" href="${ctx}/app/javascript/lib/bootstrap/css/bootstrap.css">
    <script type="text/javascript" src="${ctx}/app/javascript/lib/jquery/jquery.js"></script>
    <script type="text/javascript" src="${ctx}/app/javascript/lib/qrcode/html2canvas.js"></script>
    <script type="text/javascript" src="${ctx}/app/javascript/lib/vue/vue.js"></script>
    <script type="text/javascript" src="${ctx}/app/javascript/lib/plupload/jquery-ui-1.8.22.js"></script>
    <script type="text/javascript" src="${ctx}/app/javascript/lib/plupload/jquery-rotate.js"></script>
    <style>
        [v-cloak] {
            display: none;
        }
        #contain {
            border: 1px solid #ccc;
            position: relative;
        }
        .tool{
            position: absolute;
            background-color: rgba(255,0,0,0.5);
            color:white;
            font-size: 10px;
            display: inline-block;
            display: inline-flex;
            align-items: center;
            justify-content: center;
        }
        .textCenter{
            display: flex;
            justify-content: center;
            align-items: center;
            margin: 0;
            padding: 0;
        }
        .tool:hover{
            cursor: pointer;
        }
        .widthNumber{
            position: absolute;
            top: 0;
            bottom: 0;
            margin: auto;
            left: 0px;
            width: 10px;
            color:black;
            font-size: 10px;
            display: flex;
            flex-direction: column;
            justify-content: center;
            transform:rotate(90deg);
            -ms-transform:rotate(90deg); 	/* IE 9 */
            -moz-transform:rotate(90deg); 	/* Firefox */
            -webkit-transform:rotate(90deg); /* Safari 和 Chrome */
            -o-transform:rotate(90deg); 	/* Opera */

        }
        .lengthNumber{
            position: absolute;
            top: 0px;
            right: 0;
            margin: auto;
            color:black;
            left: 0;
            font-size: 10px;
            display: flex;
            flex-direction: row;
            justify-content: center;
        }

    </style>
</head>
<body>


<div id="app" v-cloak>
    <div class="row m-3">
        <div id="contain" class=" m-0 p-0" :style="'width:'+workAearWidth*sacleCount+'px;height:'+workAearHeight*sacleCount+'px'"
             style="background-color: #c6bcbc;position: relative">
            <p class="widthNumber">{{workAearHeight+'cm'}}</p>
            <p class="lengthNumber">{{workAearWidth+'cm'}}</p>
        </div>

        <div class="outer col-md-3 ">
            <h4>舱外工具</h4>
            <ul class="list-group">
                <li class="list-group-item " v-for="(item,index) in spareParts"  >
                    <div class="form-inline">
                        <div class="form-group">
                            <label for="toolName">{{item.deviceName+'*'+item.cCarryCount3486}}</label>
                            <input type="number"  maxlength="3"  class="form-control ml-3" id="toolName" v-model="item.angle" placeholder="旋转角度" >
                            <span class="glyphicon glyphicon-plus ml-4 " style="cursor: pointer"  aria-hidden="true" @click="addTool(item,index)" ></span>
                        </div>
                    </div>

                </li>
            </ul>
            <h4 class="mt-2">作业区域(cm)</h4>
            <div class="mt-1 mb-1">
                <div class="form-row">
                    <div class="col-4">
                        <input type="text" class="form-control" placeholder="长度" maxlength="3" v-model="workAreaModel.width">
                    </div>
                    <div class="col-4">
                        <input type="text" class="form-control" placeholder="宽度" maxlength="3" v-model="workAreaModel.height">
                    </div>
                    <div class="col-4">
                        <button type="button" class="btn btn-warning w-100" @click="confirm">确认</button>
                    </div>
                </div>
            </div>
            <div class="alert alert-danger" role="alert">
                <p>提示：</p>
                <p>单击图形可以拖动工具</p>
                <p>双击图形可以删除工具</p>
                <p>右击图形可以旋转指定度数，再次右击可以恢复图形</p>
            </div>
            <button class="btn btn-info float-right mt-3 ml-2" type="button" @click="closeBasketTool">返回</button>
            <button class="btn btn-success float-right mt-3 ml-2" type="button" @click="exportPicture">发布</button>
            <button class="btn btn-primary float-right mt-3" type="button" @click="clearAllTool">清空</button>
        </div>


    </div>


</div>

</body>

<script>

    var __ctx = '<%=request.getContextPath()%>';
    var taskId = '<%=request.getParameter("taskId")%>';
    var divingPlanId = '<%=request.getParameter("divingPlanId")%>';
    var modelId = '<%=request.getParameter("modelId")%>';
    //关闭右击浏览器监听
    document.oncontextmenu = function () {
        return false
    };

    const dataURLtoFile = (dataurl, filename) => {
        var arr = dataurl.split(',');
        var mime = arr[0].match(/:(.*?);/)[1];
        var bstr = atob(arr[1]);
        var n = bstr.length;
        var u8arr = new Uint8Array(n);
        while (n--) {
            u8arr[n] = bstr.charCodeAt(n);
        }
        return new File([u8arr], filename, {type: mime});
    };

    new Vue({
        el: '#app',
        data: {
            workAearWidth: 135,
            workAearHeight: 120,
            workAreaModel: {
                height: 120,
                width: 135
            },
            sacleCount: 6,
            unit: 'cm',
            //后台返回的工具
            spareParts: [],
            //存放所有工具
            toolsData: [],
            isChangeColor: 0,
            toolsDataColor: []
        },
        methods: {
            exportPicture() {
                if (this.toolsData.length == 0) {
                    alert("请先绘图")
                    return
                }
                new html2canvas(document.getElementById('contain')).then(canvas => {

                    var dataURL = canvas.toDataURL('image/jpeg')
                    const now = new Date();
                    const filename = "绘图预览.png";
                    const file = dataURLtoFile(dataURL, filename);
                    const formData = new FormData();

                    formData.append('filecontent', file);
                    formData.append('op', 'upload');
                    formData.append('divingPlanId', divingPlanId);
                    formData.append('taskId', taskId);

                    $.ajax({
                        url: __ctx + '/accountingForm/saveScientistPicturePreviewPic.rdm',
                        type: "post",
                        async: false,
                        data: formData,
                        //  contentType:false,取消设置请求头
                        contentType: false,
                        //  processData:false,设置false解决illegal invocation错误
                        processData: false,
                        success: function (data) {
                            data = JSON.parse(data);
                            if (data.success) {
                                alert("发布成功!")
                            } else {
                                alert("发布失败!")
                            }

                        }
                    });


                });
            },

            closeBasketTool(){
                window.location = '${ctx}/accountingForm/getScientistDivingPlanData.rdm?taskId=' + taskId + '&divingPlanId=' + divingPlanId+'&modelId='+modelId;
            },

            addTool(tool, index) {
                const count = this.toolsData.filter(item => item == tool.cDeviceId3486).length;
                if (count >= tool.cCarryCount3486) {
                    alert(tool.deviceName + '已达到最大携带数量');
                    return
                }

                let toolHtml = $("<div></div>")
                    .css("height", tool.cWidth3209 * this.sacleCount)
                    .css("width", tool.cLength3209 * this.sacleCount)
                    .css("position", "absolute")
                    .css("border", "1px solid black ")
                    .css("background-color", tool.backgroundColor)
                    .addClass("tool")
                    .attr("id", tool.cDeviceId3486 + count);

                // if (count == 0) {
                //     let object = new Object();
                //     object.deviceId = tool.cDeviceId3486;
                //     object.colorNumber = this.isChangeColor + 0.5;
                //     this.toolsDataColor.push(object);
                //     this.isChangeColor += 50;
                //     $('tool').removeAttr("background-color");
                //     $('#'+tool.cDeviceId3486 + count).css("background-color", "rgba(255, 0, 0, " +  this.isChangeColor+ ")");
                // } else if (count!=0) {
                //     let toolsDataColorTemp=this.toolsDataColor;
                //     for (let i = 0; i < toolsDataColorTemp.length; i++) {
                //         let deviceId = toolsDataColorTemp[i].deviceId;
                //         let color = toolsDataColorTemp[i].colorNumber;
                //         if (deviceId == tool.cDeviceId3486) {
                //             $('#' + tool.cDeviceId3486 + count).css("background-color", "rgba(255, 0, 0, " + color + ")");
                //         }
                //     }
                // }

                let toolHtmlName = $("<p></p>")
                    .text(tool.deviceName)
                    .css("width", "50%")
                    .addClass("textCenter")
                toolHtml.append(toolHtmlName)

                let toolHtmlInnerWidth = $("<p></p>")
                    .text(tool.cWidth3209 + "cm")
                    .addClass("widthNumber")
                toolHtml.append(toolHtmlInnerWidth)

                let toolHtmlInnerLength = $("<p></p>")
                    .text(tool.cLength3209 + "cm")
                    .addClass("lengthNumber")
                toolHtml.append(toolHtmlInnerLength)


                $('#contain').append(toolHtml)


                this.drag($('#' + tool.cDeviceId3486 + count)[0], tool)

                let that = this;

                $('#' + tool.cDeviceId3486 + count).dblclick(function (e) {
                    $(this).remove();
                    that.toolsData.splice(that.toolsData.indexOf(tool.cDeviceId3486), 1)
                })
                this.toolsData.push(tool.cDeviceId3486);

            },
            clearAllTool() {
                $('#contain div').remove();
                this.toolsData = [];
                this.toolsDataColor = [];
            },
            confirm() {
                this.workAearWidth = this.workAreaModel.width;
                this.workAearHeight = this.workAreaModel.height;
            },
            drag(dv, tool) {

                document.onselectstart = function () {
                    return false;
                };//解决拖动会选中文字的问题
                var x = 0;
                var y = 0;
                var l = 0;
                var t = 0;
                var isDown = false;
//鼠标按下事件
                dv.onmousedown = function (e) {
                    if (e.which == 1) {
                        //获取x坐标和y坐标
                        x = e.clientX;
                        y = e.clientY;

                        //获取左部和顶部的偏移量
                        l = dv.offsetLeft;
                        t = dv.offsetTop;
                        //开关打开
                        isDown = true;
                        //设置样式
                        dv.style.cursor = 'pointer';
                        dv.style.zIndex = '1000';
                    }
                    //鼠标移动

                    window.onmousemove = function (e) {
                        if (e.which == 1) {
                            if (isDown == false) {
                                return;
                            }
                            //获取x和y
                            var nx = e.clientX;
                            var ny = e.clientY;
                            //计算移动后的左偏移量和顶部的偏移量
                            var nl = nx - (x - l);
                            var nt = ny - (y - t);
                            dv.style.left = nl + 'px';
                            dv.style.top = nt + 'px';
                        }


                    }

                }

//鼠标抬起事件
                dv.onmouseup = function (e) {
                    if (e.which == 1) {
//开关关闭
                        isDown = false;
                        dv.style.cursor = 'default';
                    }

                }

                $(dv).mousedown(function (e) {
                    if (3 == e.which) {
                        if ($(this).css("transform") == 'none') {
                            $(this).css("transform", "rotate(" + tool.angle + "deg)")
                        } else {
                            $(this).css("transform", "")
                        }
                    }
                })
            }
        },
        created() {
            $.ajax({
                url: __ctx + '/accountingForm/getPlanCarryTools.rdm',
                data: {
                    taskId: taskId,
                    divingPlanId: divingPlanId
                },
                success: (data) => {
                    data = JSON.parse(data);
                    this.spareParts = data.results.filter(item => item.cCabinOutorin3486 == 'out');
                    if (this.spareParts.length > 0) {
                        for (let i = 0; i < this.spareParts.length; i++) {
                            var item = this.spareParts[i];
                            item.angle = '90';
                            item.backgroundColor= "rgba(255,"+this.isChangeColor+",0,0.5)";
                            this.isChangeColor+=50;
                        }
                    }
                },
                error: (error) => {
                    alert(error)
                }
            })
        },
        mounted() {
        },
        updated() {
        }
    })


</script>
</html>