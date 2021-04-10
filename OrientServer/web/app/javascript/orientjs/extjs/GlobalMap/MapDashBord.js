/**
 * Created by liuyangchao on 2019/3/2.
 */
Ext.define('OrientTdm.GlobalMap.MapDashBord',{
    extend:'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias:'widget.MapDashBord',
    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        modelName: TDM_SERVER_CONFIG.DIVING_TASK,
        pointsList: '',
        clearcup: new Object(),
        mapId: ''
    },
    requires: [
        'OrientTdm.TaskPrepareMgr.TBomTemplateMgr.TaskPrepareFlowTemp.DivingTaskMgrGrid',
        'OrientTdm.GlobalMap.MapTaskPanel'
    ],
    initRwData: function (results, params) {
        var me = this;
        var flowId = results[params.dataIndex].flowId;
        var taskId = results[params.dataIndex].id;
        var taskName = results[params.dataIndex].name;
        //选择模板
        var mapTaskPanel = Ext.create('OrientTdm.GlobalMap.MapTaskPanel', {
            taskId: taskId,
            flowId: flowId,
            taskName: taskName
        });
        var win = Ext.create('Ext.Window', {
                plain: true,
                title: '任务查看',
                height: '90%',
                width: '90%',
                layout: 'fit',
                maximizable: true,
                modal: true,
                items: [mapTaskPanel]
            }
        );
        win.show();
    },
    initComponent:function(){
        var me=this;
        // var date = new Date();
        // me.mapId = date.getTime();
        var centerPanel=Ext.create('OrientTdm.Common.Extend.Panel.OrientPanel',{
            region:'center',
            layout:'fit',
            padding:'0 0 0 5',
            html: '<div id="main111" style="height:100%; width:100%; position:absolute;"></div>'
        });

        Ext.apply(this,{
            title: '潜次视图',
            layout:'border',
            items:[centerPanel],
            listeners:{
                afterlayout:function(){
                    // 基于准备好的dom，初始化echarts实例
                    var me = this;
                    //忽略我的命名
                    var myChartDom = document.getElementById('main111');
                    var myChartInstance = echarts.getInstanceByDom(myChartDom);
                    console.log(myChartInstance);
                    if(myChartInstance != null && myChartInstance != "" && typeof  myChartInstance != undefined){
                        //如果内存中存在实例就释放掉。
                        myChartInstance.dispose();
                    }
                    var newChart = document.getElementById('main111');
                    myChartInstance = echarts.init(newChart);
                    var modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);
                    OrientExtUtil.AjaxHelper.doRequest(serviceName + '/mapDatas/getPoints.rdm', {
                        schemaId: me.schemaId,
                        modelId: modelId
                    }, true, function (resp) {
                        var results = resp.decodedData.results;
                        // var result = results.map(function(item){
                        //     return Object.values(item)
                        // });
                        var data_filter = [];
                        $.each(results, function(key, value){
                            data_filter.push(Ext.decode(value.value));
                        });
                        // var data_filter = [[-83, 76.5, 0],[-78, 73, 0]];
                        // console.log(data_filter);
                        var option = {
                            visualMap: {
                                show: false,
                                type: 'piecewise',
                                inRange: {
                                    symbolSize: 15,
                                    color: '#FF0000',
                                    colorAlpha: 1
                                }
                            },
                            backgroundColor: '#000',
                            globe: {
                                baseTexture: "app/images/data-gl/asset/world.topo.bathy.200401.jpg",
                                heightTexture: "app/images/data-gl/asset/world.topo.bathy.200401.jpg",
                                displacementScale: 0.04,
                                displacementQuality: 'ultra',
                                shading: 'realistic',
                                environment: 'app/images/data-gl/asset/starfield.jpg',
                                realisticMaterial: {
                                    roughness: 0.9
                                },
                                postEffect: {
                                    enable: true
                                },
                                light: {
                                    main: {
                                        intensity: 5,
                                        shadow: true
                                    },
                                    ambientCubemap: {
                                        texture: 'app/images/data-gl/asset/pisa.hdr',
                                        diffuseIntensity: 0.2
                                    }
                                },
                                viewControl: {
                                    autoRotate: false
                                }
                            },
                            tooltip: {
                                trigger: 'item',
                                formatter: function (params) {
                                    // console.log(params);
                                    var rwName = results[params.dataIndex].name;
                                    return '任务名称：' + rwName;
                                }
                            },
                            series: {
                                type: 'scatter3D',
                                coordinateSystem: 'globe',
                                blendMode: 'lighter',
                                symbolSize: 2,
                                scaleLimit: {min:0.8, max:1},
                                itemStyle: {
                                    color: 'rgb(255, 0, 0)',
                                    opacity: 1
                                },
                                data: data_filter
                            }
                        };
                        myChartInstance.on('click', function(param){
                            console.log(param);
                            me.initRwData(results, param);
                        });
                        myChartInstance.clear();
                        // me.clearcup.clean();
                        myChartInstance.setOption(option);

                        // me.clearcup.clear();
                    });
                }
            }
        });
        me.callParent(arguments);
    },
    afterInitComponent: function(){

    }


})