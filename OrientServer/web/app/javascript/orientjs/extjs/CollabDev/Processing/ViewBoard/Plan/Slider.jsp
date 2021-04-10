<%@ page import="com.orient.collabdev.model.NodeVersionVO" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: GNY
  Date: 2018/9/4
  Time: 10:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<%
    List<NodeVersionVO> list = (List<NodeVersionVO>) request.getAttribute("list");
    Integer wheelNumber = (Integer) request.getAttribute("wheelNumber");
    String nodeId = (String) request.getAttribute("nodeId");
%>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
    <link type="text/css" href="${ctx}/app/javascript/lib/swiper/swiper.min.css" rel="stylesheet"/>
    <script type="text/javascript" src="${ctx}/app/javascript/lib/jquery/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="${ctx}/app/javascript/lib/swiper/swiper.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/app/javascript/lib/ext-4.2/examples/shared/example.css"/>
    <script type="text/javascript" src="${ctx}/app/javascript/lib/ext-4.2/examples/shared/include-ext.js"></script>
    <script type="text/javascript" src="${ctx}/app/javascript/lib/ext-4.2/examples/shared/examples.js"></script>
    <script type="text/javascript" src="${ctx}/app/javascript/lib/ext-4.2/locale/ext-lang-zh_CN.js"></script>
    <script type="text/javascript"
            src="${ctx}/app/javascript/orientjs/extjs/CollabDev/Processing/ViewBoard/Plan/WheelIterationModel.js"></script>
    <style>
        html, body {
            position: relative;
            height: 100%;
        }

        body {
            background: #eee;
            font-family: Helvetica Neue, Helvetica, Arial, sans-serif;
            font-size: 14px;
            color: #000;
            margin: 0;
            padding: 0;
        }

        .swiper-container {
            width: 100%;
            height: 100%;
        }

        .swiper-slide {
            text-align: center;
            font-size: 15px;
            color: #fff;
            background: #404040;

            /* Center slide text vertically */
            /* display: -webkit-box;
             display: -ms-flexbox;
             display: -webkit-flex;
              display: flex;*/
            -webkit-box-pack: center;
            -ms-flex-pack: center;
            -webkit-justify-content: center;
            justify-content: center;
            -webkit-box-align: center;
            -ms-flex-align: center;
            -webkit-align-items: center;
            align-items: center;
        }

        .swiper-slide-active {
            text-align: center;
            font-size: 15px;
            color: #0fdf3c;
            background: #404040;

            /* Center slide text vertically */
            /* display: -webkit-box;
             display: -ms-flexbox;
             display: -webkit-flex;
             display: flex;*/
            -webkit-box-pack: center;
            -ms-flex-pack: center;
            -webkit-justify-content: center;
            justify-content: center;
            -webkit-box-align: center;
            -ms-flex-align: center;
            -webkit-align-items: center;
            align-items: center;
        }

        .customStyle {
            width: 100%;
            text-align: left;
        }
    </style>
    <script type="text/javascript">
        var serviceName = '<%=request.getContextPath()%>';
    </script>
</head>

<body>

<div>
    <div id="multiWheelCombo" style="background: #404040; width: 50%;float: left">
    </div>

    <div id="autoScroll" style="background: #ffffff;width: 50%;float: right">
    </div>

</div>

<div class="swiper-container" style="background: #404040">
    <ul class="swiper-wrapper" id="swiperWrapper">
        <c:forEach var="item" items="${list}">
            <li class="swiper-slide">
                <div class="customStyle">v${item.version}</div>
                <div class="customStyle">修改人:${item.updateUser}</div>
                <div class="customStyle">修改时间:<fmt:formatDate value="${item.updateTime}"
                                                              pattern="yyyy-MM-dd HH:mm:ss"/></div>
            </li>
        </c:forEach>
        <!-- 用来占位，因为最后两个无法滚动-->
        <div class="swiper-slide"></div>
        <div class="swiper-slide"></div>
        <div class="swiper-slide"></div>
    </ul>
</div>

<!-- Initialize Swiper -->
<script>

    var refreshPedigreePanel = function (scope) {
        var ul = document.getElementById('swiperWrapper');
        var lis = ul.getElementsByTagName('li');
        var li = lis[scope.activeIndex];
        var firstDiv = li.getElementsByTagName('div')[0];
        var innerHTML = firstDiv.innerHTML;
        var version = innerHTML.substring(1, innerHTML.length);
        var panel = parent.Ext.getCmp('planVersionSlidePanel');
        panel.refreshPedigreeComponent(version);
    };

    var swiper = new Swiper('.swiper-container', {
        spaceBetween: 0,
        grabCursor: true,
        speed: 3500,
        centeredSlides: false,
        direction: 'vertical',
        autoplay: {
            delay: 4000,
            disableOnInteraction: false
        },
        slideToClickedSlide: true,
        slidesPerView: 4,
        loopedSlides: 5,
        observer: true,
        observerParents: false,
        on: {
            slideChangeTransitionEnd: function () {  //从一个slide过渡到另一个slide结束时执行
                refreshPedigreePanel(this);
            },
            tap: function () { //点击事件，如果是click事件会有300ms的延迟
                refreshPedigreePanel(this);
            },
            init: function () { //初始化的时候，也需要调用refreshPedigreePanel方法
                refreshPedigreePanel(this);
            }
        }
    });

    swiper.autoplay.stop();


</script>

</body>
</html>

<script type="text/javascript">

    var multiWheelCombo = Ext.create('Ext.form.ComboBox', {
        allowBlank: true,
        store: Ext.create('Ext.data.Store', {
                autoLoad: true,
                model: 'OrientTdm.CollabDev.Processing.ViewBoard.Plan.WheelIterationModel',
                proxy: {
                    type: 'ajax',
                    url: serviceName + '/nodeVersions/wheelsIterations.rdm',
                    reader: {
                        type: 'json',
                        successProperty: 'success',
                        totalProperty: 'totalProperty',
                        root: 'results',
                        messageProperty: 'message'
                    },
                    extraParams: {
                        nodeId: ${nodeId}
                    }
                }
            }
        ),
        displayField: 'selectText',
        valueField: 'wheelNumber',
        listeners: {
            select: function (combo, records, index) {
                var record = records[0];
                Ext.Ajax.request({
                    url: serviceName + '/nodeVersions/listPlanVersionsByWheelNumber.rdm',
                    params: {
                        nodeId: ${nodeId},
                        startVersion: record.data.startVersion,
                        endVersion: record.data.endVersion
                    },
                    success: function (response) {
                        var datas = Ext.decode(response.responseText).results;
                        renderSwiperWrapperByData(datas);
                    }
                });
            },
            change: function (combo, newValue, oldValue) {
            },
            afterRender: function (combo) {
                //默认选中最后一轮轮迭代
                combo.setValue("第" + ${wheelNumber}+"轮迭代");
            }
        },
        renderTo: 'multiWheelCombo'
    });

    function renderSwiperWrapperByData(datas) {

        $('#swiperWrapper').empty();
        for (var i = 0; i < datas.length; i++) {
            var version = 'v' + datas[i].version;
            var updateUser = '修改人:' + datas[i].updateUser;
            var updateTime = '修改时间:' + datas[i].updateTime;
            var li = '<li class= "swiper-slide">' +
                '<div class= "customStyle">' + version + '</div>' +
                '<div class= "customStyle">' + updateUser + '</div>' +
                '<div class= "customStyle">' + updateTime + '</div>' +
                '</li>';
            $('#swiperWrapper').append(li);
            if (i == datas.length - 1) {
                var div = '<div class="swiper-slide">' + '</div>';
                $('#swiperWrapper').append(div);
                $('#swiperWrapper').append(div);
                $('#swiperWrapper').append(div);

            }
        }
        swiper.updateSlides();          //重新计划slides的数量
        swiper.slideTo(0, 1, true);    //切换到第一个slide
        refreshPedigreePanel(swiper);   //刷新右边的谱系图
    }

    var checkBox = Ext.create('Ext.form.field.Checkbox', {
        fieldLabel: '是否滚动',
        labelAlign: 'right',
        checked: false,
        listeners: {
            change: function (el, checked) {
                if (checked) {
                    swiper.autoplay.start();
                } else {
                    swiper.autoplay.stop();
                }
            }
        },
        renderTo: 'autoScroll'
    });

</script>


