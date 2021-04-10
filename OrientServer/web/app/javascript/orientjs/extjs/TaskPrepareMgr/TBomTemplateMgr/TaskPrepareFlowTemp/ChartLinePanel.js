Ext.define('OrientTdm.TaskPrepareMgr.TBomTemplateMgr.TaskPrepareFlowTemp.ChartLinePanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    height:800,
    alias: 'widget.chartLinePanel',
    config:{
        voyageId:''
    },
    initComponent: function () {
        var me = this;
        var statesx = Ext.create('Ext.data.Store', {
            fields: ['abbr', 'name'],
            data : [
                {"abbr":"C_CONDUCTIVITY", "name":"电导率"},
                {"abbr":"C_TEMPERATURE", "name":"温度"},
                {"abbr":"C_DEPTH", "name":"深度1"},
                {"abbr":"C_SOUND", "name":"声度"},
                {"abbr":"C_SALINITY", "name":"盐度"},
                {"abbr":"C_DENSITY", "name":"密度"},
                {"abbr":"C_DEPTH_2", "name":"深度2"},
                {"abbr":"C_AFTER_LONGITUDE", "name":"融合后经度"},
                {"abbr":"C_AFTER_LATITUDE", "name":"融合后维度"},
                {"abbr":"C_TIME", "name":"时间"}

            ]
        });
        var statesy = Ext.create('Ext.data.Store', {
            fields: ['abbr', 'name'],
            data : [
                {"abbr":"", "name":"无"},
                {"abbr":"C_CONDUCTIVITY", "name":"电导率"},
                {"abbr":"C_TEMPERATURE", "name":"温度"},
                {"abbr":"C_DEPTH", "name":"深度1"},
                {"abbr":"C_SOUND", "name":"声度"},
                {"abbr":"C_SALINITY", "name":"盐度"},
                {"abbr":"C_DENSITY", "name":"密度"},
                {"abbr":"C_DEPTH_2", "name":"深度2"},
                {"abbr":"C_AFTER_LONGITUDE", "name":"融合后经度"},
                {"abbr":"C_AFTER_LATITUDE", "name":"融合后维度"}

            ]
        });
        var statesy_must = Ext.create('Ext.data.Store', {
            fields: ['abbr', 'name'],
            data : [
                {"abbr":"C_CONDUCTIVITY", "name":"电导率"},
                {"abbr":"C_TEMPERATURE", "name":"温度"},
                {"abbr":"C_DEPTH", "name":"深度1"},
                {"abbr":"C_SOUND", "name":"声度"},
                {"abbr":"C_SALINITY", "name":"盐度"},
                {"abbr":"C_DENSITY", "name":"密度"},
                {"abbr":"C_DEPTH_2", "name":"深度2"},
                {"abbr":"C_AFTER_LONGITUDE", "name":"融合后经度"},
                {"abbr":"C_AFTER_LATITUDE", "name":"融合后维度"}

            ]
        });

       var XComboBox =  Ext.create('Ext.form.ComboBox', {
            fieldLabel: 'X轴坐标',
            allowBlank:false,
            blankText:'请选择X轴坐标',
            name:'x',
            store: statesx,
            queryMode: 'local',
            displayField: 'name',
            valueField: 'abbr'
        });
        var YComboBox1 =  Ext.create('Ext.form.ComboBox', {
            fieldLabel: 'Y轴坐标',
            name:'y1',
            allowBlank:false,
            blankText:'请选择Y轴坐标',
            store: statesy_must,
            queryMode: 'local',
            displayField: 'name',
            valueField: 'abbr'
        });
        var YComboBox2 =  Ext.create('Ext.form.ComboBox', {
            fieldLabel: 'Y轴坐标',
            name:'y2',
            store: statesy,
            queryMode: 'local',
            displayField: 'name',
            valueField: 'abbr'
        });
        var YComboBox3 =  Ext.create('Ext.form.ComboBox', {
            fieldLabel: 'Y轴坐标',
            name:'y3',
            store: statesy,
            queryMode: 'local',
            displayField: 'name',
            valueField: 'abbr'
        });


        var selectPanel = Ext.create("Ext.form.Panel",{
            title:'参数设置',
            region:'north',
            weight:1200,
            layout: 'hbox',
            bodyPadding: 10,
            url:'analysis/getChartByParam.rdm?voyageId='+this.voyageId,
            items:[XComboBox,YComboBox1,YComboBox2,YComboBox3],
            bbar:[
                {
                    text:'生成折线',
                    handler: function() {
                        var xValue=XComboBox.getValue();
                        var form = this.up('form').getForm();
                        if (form.isValid()) {
                            form.submit({
                                success: function(form, action) {
                                    retData = action.response.decodedData.results
                                    var msg = action.response.decodedData.msg
                                    if(retData[0].data.length==0){
                                        Ext.Msg.alert('提示','未获取到相关数据！');
                                        return;
                                    }
                                    var chartLinePanel = Ext.getCmp("chartLinePanel");
                                    chartLinePanel.remove(Ext.getCmp("uploadDataAnalysis2"));
                                    var chartPanel
                                    if(xValue=="C_TIME"){
                                         chartPanel = Ext.create("OrientTdm.TaskPrepareMgr.TBomTemplateMgr.TaskPrepareFlowTemp.UploadDataAnalysis2ShowTime",{
                                             layout:'fit',
                                            region:'center',
                                            id:'uploadDataAnalysis2',
                                            data:retData,
                                             msg:msg
                                        })
                                    }else {
                                         chartPanel = Ext.create("OrientTdm.TaskPrepareMgr.TBomTemplateMgr.TaskPrepareFlowTemp.UploadDataAnalysis2",{
                                             layout:'fit',
                                            region:'center',
                                            id:'uploadDataAnalysis2',
                                            data:retData,
                                             msg:msg
                                        })
                                    }
                                    chartLinePanel.add(chartPanel)
                                    var myw = screen.availWidth;   //定义一个myw，接受到当前全屏的宽
                                    var myh = screen.availHeight;  //定义一个myw，接受到当前全屏的高
                                    window.moveTo(0, 0);           //把window放在左上脚
                                    window.resizeTo(myw, myh);     //把当前窗体的长宽跳转为myw和myh
                                },
                                failure: function(form, action) {
                                    Ext.Msg.alert('Failed', action.result ? action.result.msg : 'No response');
                                }
                            });
                        }
                    }
                }
                ]
        })
        Ext.apply(me, {
            layout: 'border',
            items: [selectPanel],
            northPanel: selectPanel,
            //centerPanel: chartPanel
        });
        me.callParent(arguments);
    }
});