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
    <link rel="stylesheet" href="${ctx}/app/javascript/lib/element/index.css">
    <script type="text/javascript" src="${ctx}/app/javascript/lib/jquery/jquery.js"></script>
    <script type="text/javascript" src="${ctx}/app/javascript/lib/vue/vue.js"></script>
    <script type="text/javascript" src="${ctx}/app/javascript/lib/element/index.js"></script>
    <style>
        .el-form-item__label{
            padding: 0;
        }
        [v-cloak] {
            display: none;
        }
    </style>
</head>
<body>


<div id="app" >
    <el-form ref="form" label-width="80px" label-position="top" >
        <el-form-item label="潜次" >
            <el-input value="${taskName}" readOnly ></el-input>
        </el-form-item>
        <el-form-item label="填表日期">
            <el-input value="${fillTableDate}" readOnly></el-input>
        </el-form-item>
        <el-form-item label="本潜次">
            <el-input value="${taskName}" readOnly></el-input>
        </el-form-item>
        <el-form-item label="深度">
            <el-input :value="depth" readOnly></el-input>
        </el-form-item>

        <el-form-item label="密度">
            <el-input :value="density" readOnly></el-input>
        </el-form-item>

        <el-form-item label="浮力损失">
            <el-input :value="buoyancyLoss" readOnly></el-input>
        </el-form-item>
        <el-form-item label="人员">
            <el-input :value="getPerson('${leftDriver}')+' , '+getPerson('${mainDriver}')+' , '+getPerson('${rightDriver}')" readOnly></el-input>
        </el-form-item>
        <el-form-item label="下潜压载(Kg)">
            <el-input value="${divingLoad}" readOnly></el-input>
        </el-form-item>
        <el-form-item label="上浮压载(Kg)">
            <el-input value="${comeupLoad}" readOnly></el-input>
        </el-form-item>
        <el-form-item label="可调压载水舱液(L)">
            <el-input value="${adjustLoad}" readOnly></el-input>
        </el-form-item>
        <el-form-item label="配重铅块(Kg">
            <el-input value="${peizhongQk}" readOnly></el-input>
        </el-form-item>
        <el-form-item label="艏部水银液位(L)">
            <el-input value="${mercury}" readOnly></el-input>
        </el-form-item>
        <el-form-item label="舱内携带工具">
            <el-table
                    size="mini"
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


        <el-form-item label="主要任务">
            <el-input type="textarea" rows="3"  value="${mainTask}"  readOnly></el-input>
        </el-form-item>
        <el-form-item label="作业过程">
            <el-input type="textarea" rows="3"  value="${workProgress}"  readOnly></el-input>
        </el-form-item>
        <el-form-item label="注意事项">
            <el-input type="textarea" rows="3"  value="${attention}"  readOnly></el-input>
        </el-form-item>

    </el-form>

</div>

</body>

<script>

    var __ctx = '<%=request.getContextPath()%>';
    var taskId = '<%=request.getParameter("taskId")%>';

    new Vue({
        el:'#app',
        data(){
          return{
              depthDesityParameters:'',
              density:"",
              depth:'',
              buoyancyLoss:'',
              persons:[]
          }
        },

        created(){
            this.loadingData();
        },
        methods: {
            loadingData(){

                $.ajax({
                    url: __ctx + '/accountingForm/getDepthDesitySelectData.rdm',
                    async: false,
                    success:(data)=> {
                        console.log(data)
                        this.depthDesityParameters = JSON.parse(data).results;
                        this.density = this.depthDesityParameters.find(item=>item.id==='${depth}').density;
                        this.depth = this.depthDesityParameters.find(item=>item.id==='${depth}').depth;
                        this.buoyancyLoss = 5327.24 * 13 / this.density + 9716 * this.density * 9.80665 * this.depth / 2800000000 + 69.5 * this.depth / 4500;
                        this.buoyancyLoss = (Math.round(this.buoyancyLoss * 10) / (10)).toFixed(1);
                    }
                });
                $.ajax({
                    url: __ctx + '/accountingForm/getPersons.rdm',
                    async: false,
                    data: {
                        "hangduanId": '${hangduanId}',
                        "taskId": taskId
                    },
                    success: (data)=> {
                        console.log(data)
                        this.persons=JSON.parse(data).results;
                    }
                });
            },
            getPerson(id){
                console.log(id)
                return this.persons.find(item=>item.id===id).name;
            },

        }
    })




</script>
</html>