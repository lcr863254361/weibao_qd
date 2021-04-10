Ext.define('OrientTdm.TaskPrepareMgr.Accounting.CurrentOutTemplateGridPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.currentOutTemplateGridPanel',
    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        templateName: TDM_SERVER_CONFIG.TPL_BALANCE_COUNT,
        modelName: TDM_SERVER_CONFIG.BALANCE_COUNT
    },

    initComponent: function () {
        var me = this;
        var modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);
        var taskId = me.taskId;
        var taskName = me.taskName;
        var flowId=me.hangduanId;
        var templateId = OrientExtUtil.ModelHelper.getTemplateId(modelId, me.templateName);
        var customerFilter = new CustomerFilter('T_DIVING_TASK_' + me.schemaId + '_ID', CustomerFilter.prototype.SqlOperation.Equal, '', me.taskId);
        var modelGrid = Ext.create('OrientTdm.Common.Extend.Grid.OrientModelGrid', {
            region: 'center',
            modelId: modelId,
            isView: 0,
            templateId: templateId,
            queryUrl: serviceName + "/accountingForm/getScientistPlanData.rdm?taskName=" + taskName,
            customerFilter: [customerFilter],
            afterInitComponent: function () {
                //排序
                this.getStore().sort([{
                    "property": "C_SERIAL_NUMBER_" + modelId,
                    "direction": "ASC"
                }]);
            }
        });
        //增加查看按钮
        var columns = modelGrid.columns;
        if (Ext.isArray(columns)) {
            columns.push({
                xtype: 'actioncolumn',
                text: '编辑表格',
                align: 'center',
                width: 300,
                items: [{
                    iconCls: 'icon-detail',
                    tooltip: '编辑表格',
                    handler: function (grid, rowIndex) {
                        var me = this;
                        var data = grid.getStore().getAt(rowIndex);
                        var peizhongId = data.raw.ID;
                        var taskId = grid.up('panel').up('panel').taskId;
                        var currentOutTemplate = Ext.create('OrientTdm.Common.Extend.Panel.OrientPanel', {
                            layout: 'absolute',
                            region: 'east',
                            items: [{
                                layout: "fit",
                                // html:'<iframe  frameborder="0" width="800" height="800" style="margin-left: 0px" marginwidth="0" src="'+'app/javascript/orientjs/extjs/TaskPrepareMgr/Accounting/currentOutTemplate.jsp?hangduanId='+me.flowId+ '&taskId=' + me.taskId
                                //     + '&seaArea=' + me.seaArea + '&jingdu=' + me.jingdu+ '&weidu=' + me.weidu+'&planDivingDepth='+me. planDivingDepth
                                //     +'"></iframe>'
                                html: '<iframe  id="currentOutTemplateIframe" frameborder="0" width="1000" height="100%" style="margin-left: 0px "marginwidth="0"  src="' + 'accountingForm/getOutTemplateTable.rdm?taskId=' + taskId + '&taskName=' + taskName + '&hangduanId=' + flowId+'&peizhongId='+peizhongId+'&isCanEdit='+true+'&isOnlyView='+true
                                    + '"></iframe>'
                            }]
                        });

                        var beforeOutTemplate = Ext.create('OrientTdm.Common.Extend.Panel.OrientPanel', {
                            layout: 'absolute',
                            region: 'west',
                            split: true,
                            items: [{
                                layout: "fit",
                                // html:'<iframe  frameborder="0" width="800" height="800" style="margin-left: 0px" src="'+'app/javascript/orientjs/extjs/TaskPrepareMgr/Accounting/preOutTemplate.jsp?hangduanId='+me.flowId+ '&taskId=' + me.taskId
                                //     + '&seaArea=' + me.seaArea + '&jingdu=' + me.jingdu+ '&weidu=' + me.weidu+'&planDivingDepth='+me. planDivingDepth
                                //     +'"></iframe>'
                                html: '<iframe  id="preOutTemplateIframe" frameborder="0" width="900" height="100%" style="margin-left: 0px " src="' + 'accountingForm/getDivingTypeData.rdm?hangduanId=' +flowId
                                    + '"></iframe>'
                            }]
                        });

                        var win = Ext.create('Ext.window.Window', {
                            id:'peizhongWin',
                            title: '配重输出模板表',
                            titleAlign: 'center',
                            height: 0.9 * globalHeight,
                            width: 0.8 * globalWidth,
                            region: 'center',
                            maximizable: true,
                            modal: true,
                            constrain: true,   //限制窗口不超出浏览器边界,
                            layout: {
                                type: 'border',
                            },
                            buttons: [
                                {
                                    text: '关闭',
                                    iconCls: 'icon-close',
                                    handler: function () {
                                        this.up('window').close();
                                    }
                                }
                            ],
                            buttonAlign: 'center',
                            listeners: {
                                'afterrender': function () {
                                    if (Ext.get('currentOutTemplateIframe') || Ext.get('preOutTemplateIframe')) {
                                        window.onresize = function () {
                                            if (Ext.getCmp("peizhongWin")==undefined) {
                                                return;
                                            }
                                            changeFrameHeight();
                                        };
                                        changeFrameHeight();
                                        function changeFrameHeight() {
                                            win.setHeight(document.documentElement.clientHeight);
                                            win.setWidth(document.documentElement.clientWidth);
                                            win.center();
                                            var cwin = document.getElementById('currentOutTemplateIframe');
                                            cwin.height = document.documentElement.clientHeight-60 ;
                                            cwin.width = document.documentElement.clientWidth / 2 ;
                                            currentOutTemplate.setHeight(cwin.height);
                                            currentOutTemplate.setWidth(cwin.width);
                                            var preCwin = document.getElementById('preOutTemplateIframe');
                                            preCwin.height = document.documentElement.clientHeight-40 ;
                                            preCwin.width = document.documentElement.clientWidth / 2 ;
                                            beforeOutTemplate.setHeight(preCwin.height);
                                            beforeOutTemplate.setWidth(preCwin.width);
                                        }
                                    }
                                    // window.parent.document.getElementById('currentOutTemplateIframe').contentWindow.document.location.reload();
                                    // window.parent.document.getElementById('preOutTemplateIframe').contentWindow.document.location.reload();
                                }
                            },
                            items: [beforeOutTemplate,currentOutTemplate]
                        });
                        win.show();
                    }
                }
                ]
            })
        }
        //使用一个新的store/columns配置项进行重新配置生成grid
        modelGrid.reconfigure(modelGrid.getStore(), columns);
        Ext.apply(me, {
            layout: 'border',
            items: [modelGrid],
            modelGrid: modelGrid
        });
        me.callParent(arguments);
    }
});