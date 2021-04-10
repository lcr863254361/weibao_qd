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
    <meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=no,
    width=device-width,initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <link rel="stylesheet" href="${ctx}/app/javascript/lib/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="${ctx}/app/javascript/lib/element/index.css">
    <link rel="stylesheet" href="${ctx}/app/javascript/lib/viewer/viewer.css">
    <script type="text/javascript" src="${ctx}/app/javascript/lib/jquery/jquery.js"></script>
    <script type="text/javascript" src="${ctx}/app/javascript/lib/vue/vue.js"></script>
    <script type="text/javascript" src="${ctx}/app/javascript/lib/element/index.js"></script>
    <script type="text/javascript" src="${ctx}/app/javascript/lib/viewer/viewer.js"></script>
    <style>
        .el-form-item__label {
            padding: 0;
        }

        [v-cloak] {
            display: none;
        }
    </style>
</head>
<body>

<div id="app" v-cloak class="container mt-3 mb-3 col-xs-1">

    <el-row>
        <el-form ref="form" label-width="80px" label-position="top">
            <el-form-item label="编号" v-if="${!isScientist}">
                <el-input value="${numberContent}" readOnly></el-input>
            </el-form-item>
            <el-form-item label="下潜日期" v-if="${!isScientist}">
                <el-input value="${divingDate}" readOnly></el-input>
            </el-form-item>
            <el-form-item label="表格填写人员" v-if="${isScientist}">
                <el-input value="${fillPerson}" readOnly></el-input>
            </el-form-item>
            <el-form-item label="各就各位时间" v-if="${!isScientist}">
                <el-input :value="'${positionTime}'|formatTime" readOnly></el-input>
            </el-form-item>
            <el-form-item label="计划浮至水面时间" v-if="${!isScientist}">
                <el-input :value="'${planFloatToWTime}'|formatTime" readOnly></el-input>
            </el-form-item>
            <el-form-item label="计划水中时间" v-if="${!isScientist}">
                <el-input :value="'${planWaterTime}'|formatTime" readOnly></el-input>
            </el-form-item>
            <el-form-item label="计划抛载时间" v-if="${!isScientist}">
                <el-input :value="'${palnThrowTime}'|formatTime" readOnly></el-input>
            </el-form-item>
            <el-form-item label="计划上浮时间(min)" v-if="${!isScientist}">
                <el-input :value="'${planFloatTime}'" readOnly></el-input>
            </el-form-item>
            <el-form-item label="下潜类型" v-if="${!isScientist}">
                <el-input value="${divingType}" readOnly></el-input>
            </el-form-item>
            <el-form-item label="经度">
                <el-input value="${longtitude}" readOnly></el-input>
            </el-form-item>
            <el-form-item label="纬度">
                <el-input value="${latitude}" readOnly></el-input>
            </el-form-item>
            <el-form-item label="海区">
                <el-input :value="seaArea" readOnly></el-input>
            </el-form-item>
            <el-form-item label="计划下潜深度(M)">
                <el-input :value="${planDivingDepth}" readOnly></el-input>
            </el-form-item>
            <el-form-item label="密度(Kg/m3)">
                <el-input value="${density}" readOnly></el-input>
            </el-form-item>
            <el-form-item label="计划上浮深度(M)">
                <el-input :value="${planFloatDepth}" readOnly></el-input>
            </el-form-item>
            <el-form-item label="作业点" v-if="${!isScientist}">
                <el-input value="${homeWorkPoint}" readOnly></el-input>
            </el-form-item>
            <el-form-item label="左舷" v-if="${!isScientist}">
                <el-input :value="getPerson('${selectZuoxian}')" readOnly></el-input>
            </el-form-item>
            <el-form-item label="主驾" v-if="${!isScientist}">
                <el-input :value="getPerson('${selectMainDriver}')" readOnly></el-input>
            </el-form-item>
            <el-form-item label="右舷" v-if="${!isScientist}">
                <el-input :value="getPerson('${selectYouxian}')" readOnly></el-input>
            </el-form-item>
            <el-form-item label="下潜压载(Kg)" v-if="${!isScientist}">
                <el-input value="${divingLoad}" readOnly></el-input>
            </el-form-item>
            <el-form-item label="上浮压载(Kg)" v-if="${!isScientist}">
                <el-input value="${comeupLoad}" readOnly></el-input>
            </el-form-item>
            <el-form-item label="可调压载水舱液(L)" v-if="${!isScientist}">
                <el-input value="${adjustLoad}" readOnly></el-input>
            </el-form-item>
            <el-form-item label="配重铅块(Kg" v-if="${!isScientist}">
                <el-input value="${peizhongQk}" readOnly></el-input>
            </el-form-item>
            <el-form-item label="采样篮工具重量(Kg)" v-if="${!isScientist}">
                <el-input :value="basketWeight" readOnly></el-input>
            </el-form-item>
            <el-form-item label="艏部水银液位(L)" v-if="${!isScientist}">
                <el-input value="${mercury}" readOnly></el-input>
            </el-form-item>
            <el-form-item label="舱内携带工具">
                <el-table
                        size="mini"
                        show-summary
                        :summary-method="getInSummaries"
                        :data="cabinSelectorCarryName"
                >
                    <el-table-column
                            prop="name"
                            label="名称"
                    >
                    </el-table-column>
                    <el-table-column
                            prop="airWeight"
                            label="空气中重量(Kg)"
                    >
                    </el-table-column>
                    <el-table-column
                            prop="carryCount"
                            label="计划携带数量">
                    </el-table-column>
                    <el-table-column
                            prop="netWeight"
                            label="净重量(Kg)">
                    </el-table-column>
                    <el-table-column
                            prop="connectWay"
                            label="与潜水器连接方式">
                    </el-table-column>

                </el-table>
            </el-form-item>


            <el-form-item label="舱外携带的作业工具">
                <el-table
                        size="mini"
                        show-summary
                        :summary-method="getOutSummaries"
                        :data="selectorCarryName"
                >
                    <el-table-column
                            prop="name"
                            label="名称"
                    >
                    </el-table-column>
                    <el-table-column
                            prop="airWeight"
                            label="空气中重量(Kg)"
                    >
                    </el-table-column>
                    <el-table-column
                            prop="deWaterVolume"
                            label="排水体积(L)">
                    </el-table-column>
                    <el-table-column
                            prop="freshWaterWeight"
                            label="淡水中重量(Kg)">
                    </el-table-column>
                    <el-table-column
                            prop="carryCount"
                            label="计划携带数量">
                    </el-table-column>
                    <el-table-column
                            prop="netWeight"
                            label="净重量(Kg)">
                    </el-table-column>
                    <el-table-column
                            prop="connectWay"
                            label="与潜水器连接方式">
                    </el-table-column>

                </el-table>
            </el-form-item>
            <el-form-item label="主要任务">
                <el-input type="textarea" rows="3" value="${mainTask}" readOnly></el-input>
            </el-form-item>
            <el-form-item label="作业过程">
                <el-input type="textarea" rows="3" value="${workProgress}" readOnly></el-input>
            </el-form-item>
            <el-form-item label="注意事项">
                <el-input type="textarea" rows="3" value="${attention}" readOnly></el-input>
            </el-form-item>
            <el-form-item label="图片详情" v-if="srcList.length>0">
                <el-row id="images1">
                    <%--<a v-for="item,index in srcList" :href="'${ctx}/orientForm/download.rdm?fileId='+item.fileId" alt="Picture 1" @click="showDetail(item)">{{item.fileName}}&nbsp;&nbsp;&nbsp;</a>--%>
                    <a v-for="item,index in srcList"
                       alt="Picture 1" @click="showDetail(item)">{{item.fileName}}&nbsp;&nbsp;&nbsp;</a>
                </el-row>
            </el-form-item>
            <el-form-item label="采样篮图片详情" v-if="srcList2.length>0">
                <el-row id="images2">
                    <img v-for="item,index in srcList2" :src="item" alt="Picture 2"
                         style="width: 100px; height: 100px"/>
                </el-row>
            </el-form-item>

        </el-form>

        <el-button @click="back ">返回</el-button>

    </el-row>

</div>

</body>

<script>


    var __ctx = '<%=request.getContextPath()%>';
    var taskId = '<%=request.getParameter("taskId")%>';
    var isSubmitTable = '${isSubmitTable}';
    new Vue({
        el: '#app',
        filters: {
            formatTime(val) {
                if (val)
                    return val.substring(0, 2) + ':' + val.substring(2, 4)
                else return;
            }
        },
        components: {},
        data: {
            srcList: [],
            srcList2: [],
            depthDesityParameters: [],
            persons: [],
            selectorCarryName: [],
            cabinSelectorCarryName: [],
            showCarryToolList: [],
            density: '',
            // depth:'',
            basketWeight: '',
            seaArea: ''
        },

        mounted() {
            var showCarryToolList = '${showCarryToolList}';
            if (showCarryToolList != '') {
                this.showCarryToolList = JSON.parse('${showCarryToolList}');
            }
            this.loadingData();
        },
        updated() {
            // if(this.srcList.length>0){
            //     const gallery1 = new Viewer(document.getElementById('images1'),{
            //         toggleOnDblclick:false,
            //     });
            // }
            if (this.srcList2.length > 0) {
                const gallery2 = new Viewer(document.getElementById('images2'), {
                    toggleOnDblclick: false,
                });
            }
        },
        methods: {
            showDetail(item) {
                //"_blank" 新打开一个窗口 "_self" 覆盖当前的窗口
                window.open(__ctx + '/preview' + item.filePath, "_blank")
                <%--window.location.href = '${ctx}/orientForm/download.rdm?fileId=' + item.fileId;--%>
            },
            back() {
                window.history.back();
            },
            getInSummaries(param) {
                const {columns, data} = param;
                const sums = [];
                columns.forEach((column, index) => {
                    if (index === columns.length - 1)
                        return;
                    if (index === 2)
                        return;
                    if (index === 0) {
                        sums[index] = '工具总重量';
                        return;
                    }
                    if (index === 1) {
                        const values = data.map(item => Number(item[column.property]) * Number(item.carryCount));
                        sums[index] = values.reduce((prev, curr) => {
                            const value = Number(curr);
                            if (!isNaN(value)) {
                                return prev + curr;
                            } else {
                                return prev;
                            }
                        }, 0);
                        sums[index] = this.formatNumber(sums[index]);
                        return;

                    }
                    const values = data.map(item => Number(item[column.property]));
                    if (!values.every(value => isNaN(value))) {
                        sums[index] = values.reduce((prev, curr) => {
                            const value = Number(curr);
                            if (!isNaN(value)) {
                                return prev + curr;
                            } else {
                                return prev;
                            }
                        }, 0);
                    } else {
                        sums[index] = 'N/A';
                    }
                    sums[index] = this.formatNumber(sums[index]);

                });

                return sums;

            },
            getOutSummaries(param) {
                const {columns, data} = param;
                const sums = [];
                columns.forEach((column, index) => {
                    if (index === columns.length - 1)
                        return;
                    if (index === 4)
                        return;
                    if (index === 0) {
                        sums[index] = '工具总重量';
                        return;
                    }
                    if (index === 1 || index === 2 || index === 3) {
                        const values = data.map(item => Number(item[column.property]) * Number(item.carryCount));
                        sums[index] = values.reduce((prev, curr) => {
                            const value = Number(curr);
                            if (!isNaN(value)) {
                                return prev + curr;
                            } else {
                                return prev;
                            }
                        }, 0);
                        sums[index] = this.formatNumber(sums[index]);
                        if (index === 1)
                            this.basketWeight = sums[index];
                        return;

                    }

                    if (index == 5) {
                        const values = data.map(item => Number(item[column.property]));
                        if (!values.every(value => isNaN(value))) {
                            sums[index] = values.reduce((prev, curr) => {
                                const value = Number(curr);
                                if (!isNaN(value)) {
                                    return prev + curr;
                                } else {
                                    return prev;
                                }
                            }, 0);
                        } else {
                            sums[index] = 'N/A';
                        }
                        sums[index] = this.formatNumber(sums[index]);
                    }
                });

                return sums;
            },
            formatNumber(orl) {
                return parseFloat(orl).toFixed(1);
            },
            loadingData() {
                //判断作业地图是否为空
                var homeMapPathList = '${homeMapPathList}';
                if (homeMapPathList != '') {
                    let parse = JSON.parse('${homeMapPathList}');
                    <%--this.srcList=parse.map(item=> '${ctx}/orientForm/download.rdm?fileId='+item.fileId)--%>
                    this.srcList = parse;
                }
                var basketPicMap = '${basketPicMap}';
                if (basketPicMap != '') {
                    let parse = eval('(' + basketPicMap + ')');
                    this.srcList2.push('${ctx}/orientForm/download.rdm?fileId=' + parse.fileId)
                }

                <%--$.ajax({--%>
                <%--url: __ctx + '/accountingForm/getDepthDesitySelectData.rdm',--%>
                <%--async: false,--%>
                <%--data: {--%>
                <%--"deptyDesityTypeId": '${seaArea}'--%>
                <%--},--%>
                <%--success: (data)=> {--%>
                <%--let result = JSON.parse(data).results;--%>
                <%--this.depth=result.find(item=>item.id=== '${planDivingDepth}').depth--%>
                <%--}--%>
                <%--});--%>

                $.ajax({
                    url: __ctx + '/accountingForm/getPersons.rdm',
                    async: false,
                    data: {
                        "hangduanId": '${hangduanId}',
                        "taskId": taskId
                    },
                    success: (data) => {
                        this.persons = JSON.parse(data).results;
                    }
                });
                $.ajax({
                    url: __ctx + '/accountingForm/getDepthDesityTypeData.rdm',
                    async: false,
                    success: (data) => {
                        let result = JSON.parse(data).results;
                        this.seaArea = result.find(item => item.id === '${seaArea}').depthDesityTypeName
                    }
                });
                $.ajax({
                    url: __ctx + '/accountingForm/getCarryToolList.rdm',
                    async: false,
                    data: {
                        "taskId": taskId
                    },
                    success: (data) => {
                        let result = JSON.parse(data).results;
                        this.cabinSelectorCarryName =
                            this.showCarryToolList
                                .map(item => {
                                    let netWeight = this.caculateInNetWeight(item.airWeight, item.deWaterVolume, item.carryCount);
                                    return {
                                        ...result.find(item1 => item1.id === item.deviceId),
                                        ...item,
                                        netWeight
                                    }
                                })
                                .filter(item => {
                                    return item.isCabinOutOrIn === 'in'
                                })
                        this.selectorCarryName = this.showCarryToolList
                            .map(item => {
                                let netWeight = this.caculateNetWeight(item.airWeight, item.deWaterVolume, item.carryCount);
                                return {
                                    ...result.find(item1 => item1.id === item.deviceId),
                                    ...item,
                                    netWeight
                                }
                            })
                            .filter(item => {
                                return item.isCabinOutOrIn !== 'in'
                            })
                    }
                });
            },

            getPerson(id) {
                if (this.persons.length > 0) {
                    let p = this.persons.find(item => item.id === id);
                    if (p)
                        return p.name
                }
                return ''
            },
            caculateNetWeight(airWeight, deWaterVolume, planCarryCount) {
                let netWeight = (Number(airWeight) - (Number(deWaterVolume) * 0.001 * Number('${density}'))) * Number(planCarryCount);
                netWeight = (Math.round(netWeight * 10) / (10)).toFixed(1);
                return netWeight;
            },
            caculateInNetWeight(airWeight, deWaterVolume, planCarryCount) {
                let netWeight = airWeight * planCarryCount;
                netWeight = (Math.round(netWeight * 10) / (10)).toFixed(1);
                return netWeight;
            }
        }
    })


</script>
</html>