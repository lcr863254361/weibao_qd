Ext.define('OrientTdm.ProductStructMgr.CenterPanel.StructDeviceGridPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.structDeviceGridPanel',
    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        templateName: TDM_SERVER_CONFIG.TPL_STRUCT_DEVICE,
        modelName: TDM_SERVER_CONFIG.STRUCT_DEVICE,
    },

    initComponent: function () {
        var me = this;
        var isOnlyShow=me.isOnlyShow;
        var customerFilter = me.customerFilter;
        var bindNode = me.bindNode;
        var modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);
        var templateId = OrientExtUtil.ModelHelper.getTemplateId(modelId, me.templateName);
        var modelGrid = Ext.create('OrientTdm.Common.Extend.Grid.OrientModelGrid', {
            region: 'center',
            modelId: modelId,
            isView: 0,
            templateId: templateId,
            customerFilter: customerFilter,
            afterInitComponent: function () {
                // if (me.bindNode.raw.level == 3) {
                //     // var toolbar = this.dockedItems[0];
                //     // toolbar.items.each(function (item, index) {
                //     //     toolbar.remove(item);
                //     // });
                //
                // }
                //构建完表格后 定制操作
                var addButton = this.dockedItems[0].down('button[text=新增]');
                Ext.Function.interceptAfter(addButton, 'handler', function (button) {
                    //新增表单出现后 相关定制
                    var customPanel = button.orientBtnInstance.customPanel;
                    if (customPanel) {
                        if (bindNode.raw.level == 3) {
                            var formValue = {};
                            var typeColumnName = "C_TYPE_" + modelId;
                            //object对象中动态设置值
                            formValue[typeColumnName] =  bindNode.raw.text;
                            customPanel.getForm().setValues(formValue);
                            //注入额外参数
                            customPanel.originalData = Ext.apply(customPanel.originalData || {}, {
                                T_STRUCT_SYSTEM_480_ID: bindNode.parentNode.raw.idList[0],
                            });
                        }else{
                            //注入额外参数
                            customPanel.originalData = Ext.apply(customPanel.originalData || {}, {
                                T_STRUCT_SYSTEM_480_ID: bindNode.raw.idList[0],
                            });
                        }
                    }
                });
            },
            afterOperate:function () {
                var treepanel = Ext.getCmp("productStructDashboard").westPanel;
                var store = treepanel.getStore();
                var selectNode = treepanel.getSelectionModel().getSelection()[0];
                if (Ext.isEmpty(selectNode))	//如果没有父节点，则pnode为根节点
                {
                    selectNode = store.getRootNode();
                }else if (selectNode.raw.level==3){
                    selectNode=selectNode.parentNode;
                }
                store.load({
                    node: selectNode,
                    callback: function (records) {
                        selectNode.appendChild(records);	//添加子节点
                        selectNode.set('leaf',false);
                        selectNode.expand(true);
                    }
                });
            }
        });
        var columns = modelGrid.columns;
        if (Ext.isArray(columns)) {
                columns.push({
                    xtype: 'actioncolumn',
                    text: '检查周期',
                    align: 'center',
                    width: 300,
                    hidden:isOnlyShow,
                    items: [{
                        iconCls: 'icon-detail',
                        tooltip: '检查周期',
                        handler: function (grid, rowIndex) {
                            var data = grid.getStore().getAt(rowIndex);
                            var deviceId = data.raw.ID;
                            var deviceName = data.raw['C_NAME_' + modelId];
                            var structSystemId = data.raw['T_STRUCT_SYSTEM_' + me.schemaId + '_ID'];
                            var deviceCycleCheckGridPanel = Ext.create('OrientTdm.ProductStructMgr.CenterPanel.DeviceCycleCheckGridPanel', {
                                title: '【' + deviceName + '】周期检查',
                                deviceId: deviceId,
                                deviceName: deviceName,
                                structSystemId: structSystemId,
                                iconCls: "icon-data-node",
                                bindNode: bindNode
                            });
                            var centerPanel = Ext.getCmp('productStructDashboard').centerPanel;
                            //移除所有面板
                            centerPanel.items.each(function (item, index) {
                                if (0 != index) {
                                    centerPanel.remove(item);
                                }
                            });
                            centerPanel.add(deviceCycleCheckGridPanel);
                            centerPanel.setActiveTab(1);
                        }
                    }
                    ]
                })
            }
        //使用一个新的store/columns配置项进行重新配置生成grid
        modelGrid.reconfigure(modelGrid.getStore(), columns);

        // if (me.bindNode.raw.level == 3) {
        //     modelGrid.getDockedItems('toolbar[dock="top"]')[0].items.items[0].hide();
        //     modelGrid.getDockedItems('toolbar[dock="top"]')[0].items.items[1].hide();
        //     modelGrid.getDockedItems('toolbar[dock="top"]')[0].items.items[4].hide();
        // }
        //刷新Grid时 同时刷新右侧树
        // modelGrid.on("customRefreshGrid", function (useQueryFilter) {
        //     var westPanel = Ext.getCmp('productStructDashboard').westPanel;
        //     if (westPanel) {
        //         westPanel.fireEvent("refreshCurrentNode", useQueryFilter);
        //     }
        // });
        Ext.apply(me, {
            layout: 'border',
            items: [modelGrid],
            modelGrid: modelGrid
        });
        me.callParent(arguments);
    }
});