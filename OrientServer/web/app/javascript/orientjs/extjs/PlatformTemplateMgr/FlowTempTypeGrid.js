/**
 * Created by User on 2019/5/23.
 */
Ext.define('OrientTdm.PlatformTemplateMgr.FlowTempTypeGrid', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.FlowTempTypeGrid',
    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        templateName: TDM_SERVER_CONFIG.TPL_FLOW_TEMP_TYPE,
        modelName: TDM_SERVER_CONFIG.FLOW_TEMP_TYPE
    },

    initComponent: function () {
        var me = this;
        modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);
        var store = Ext.create('Ext.data.Store', {

            fields: ["id", "flowTempType"],
            proxy: {
                type: 'ajax',
                url: serviceName + "/FlowTempMgr/queryFlowTempTypeList.rdm",
                reader: {
                    type: 'json',
                    root: 'results'
                }
            },
            sorters: [{
                direction: 'desc'
            }],
            autoLoad: true
        });
        var columns = [
            {id: 'flowTempHeadId', text: '流程模板名称', dataIndex: 'flowTempType', flex: 1, align: 'center',
                renderer: function (value, meta, record) {
                    value = value || '';
                    meta.tdAttr = 'data-qtip="' + value + '"';
                    return value;
                }}
        ];
        var girdPanel = Ext.create('Ext.grid.Panel', {
            renderTo: Ext.getBody(),
            hideHeaders: true,
            store: store,
            columns: columns,
            buttonAlign: 'center',
            id: 'flowTypeGridPanel',
            tbar: ['', '', '', {
                xtype: 'button',
                itemId: 'increase',
                //text: '<b> <span style="font-size: 15px;">添&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;加&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类</span></b>',
                text: '添加',
                iconCls: 'icon-create',
                scope: this,
                handler: this.createFlowType
            }, '',
                {
                    xtype: 'button',
                    itemId: 'update',
                    //text: '<b> <span style="font-size: 15px;">添&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;加&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类</span></b>',
                    text: '修改',
                    iconCls: 'icon-update',
                    scope: this,
                    handler: this.updateFlowType
                }, '',
                {
                    xtype: 'button',
                    itemId: 'delete',
                    //text: '<b> <span style="font-size: 15px;">添&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;加&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类</span></b>',
                    text: '删除',
                    iconCls: 'icon-delete',
                    scope: this,
                    handler: this.deleteFlowType
                }
            ]
        });

        girdPanel.addListener('itemclick', function (grid, record, item, index, e) {
            var flowTempTypeId = record.data.id;
            var flowTempCenterPanel = me.up('platformTemplateMgrDashBoard').centerPanel;
            flowTempCenterPanel.items.each(function (item, index) {
                flowTempCenterPanel.remove(item);
            });

            //创建中间面板
            var mxGraphEditorPanel = Ext.create('OrientTdm.PlatformTemplateMgr.MxGraphEditor.MxGraphEditorPanel', {
                region: 'center',
                flowTempTypeId: flowTempTypeId,
                backButtonShow: false
            });
            var teststageTabPanel = Ext.create('OrientTdm.Common.Extend.Panel.OrientTabPanel', {
                layout: 'fit',
                height: '45%',
                region: 'south',
                animCollapse: true,
                collapsible: true,
                header: true,
                id:'teststageTabPanelowner'
                //nodeId: me.nodeId,
                //nodeText: me.nodeText
            });
            var attendPostMgrPanel = Ext.create("OrientTdm.PlatformTemplateMgr.Center.TabPanel.AttendPostMgrPanel", {
                region: 'center',
                padding: '0 0 0 0',
                readModel: false,
                flowTempTypeId:flowTempTypeId
            });
            var checkTableMgrPanel = Ext.create("OrientTdm.PlatformTemplateMgr.Center.TabPanel.CheckTableMgrPanel", {
                region: 'center',
                padding: '0 0 0 0',
                //hangciId: me.platformId
                flowTempTypeId:flowTempTypeId
            });

            teststageTabPanel.add(
                {
                    layout: 'border',
                    title: '参与岗位',
                    //iconCls: treeNode.get('iconCls'),
                    items: [
                        attendPostMgrPanel
                    ],
                    attendPostMgrPanel: attendPostMgrPanel,
                    listeners: {
                        // beforeactivate: function (comp, obj) {
                        //     if (typeof obj.removeAll === 'function') {
                        //         obj.removeAll();
                        //     }
                        // },
                        // activate: function (comp, obj) {
                        //     var mxGraphCenterPanel = me.up('platformTemplateMgrDashBoard').centerPanel.mxGraphEditorPanel;
                        //     console.log(mxGraphCenterPanel.nodeId);
                        //     me.addToolMgrPanel(comp, me.stage);
                        // },
                        'beforeshow':function (t,n) {
                            console.log(t.ownerCt);
                                var toolbar =t.ownerCt.getActiveTab().items.items[0].child(xtype = 'grid').getDockedItems('toolbar[dock="top"]')[0];
                                if (!Ext.isEmpty(t.ownerCt.nodeId)) {
                                    toolbar.setDisabled(false);
                                }else{
                                    toolbar.setDisabled(true);
                                }
                            t.ownerCt.getActiveTab().items.items[0].refreshPostfilePanelByNode(t.ownerCt.nodeId,t.ownerCt.nodeText);
                        }
                    }
                }, {
                    layout: 'border',
                    title: '检查表格',
                    //iconCls: treeNode.get('iconCls'),
                    items: [
                        checkTableMgrPanel
                    ],
                    checkTableMgrPanel: checkTableMgrPanel,
                    listeners: {
                        // beforeactivate: function (comp, obj) {
                        //     if (typeof obj.removeAll === 'function') {
                        //         obj.removeAll();
                        //     }
                        // },
                        // activate: function (comp, obj) {
                        //     me.addToolMgrPanel(comp, me.stage);
                        // }
                        'beforeshow':function (t,n) {
                            console.log(t.ownerCt);
                                var toolbar =t.ownerCt.getActiveTab().items.items[0].child(xtype = 'grid').getDockedItems('toolbar[dock="top"]')[0];
                                if (!Ext.isEmpty(t.ownerCt.nodeId)) {
                                    toolbar.setDisabled(false);
                                }else{
                                    toolbar.setDisabled(true);
                                }
                            t.ownerCt.getActiveTab().items.items[0].refreshCheckfilePanelByNode(t.ownerCt.nodeId,t.ownerCt.nodeText);
                        }
                    }
                });
            teststageTabPanel.setActiveTab(0);
            flowTempCenterPanel.add({
                layout: 'border',
                title:'流程模板设计',
                items: [mxGraphEditorPanel,teststageTabPanel],
                mxGraphEditorPanel: mxGraphEditorPanel,
                stageTabPanel: teststageTabPanel
            });
            flowTempCenterPanel.setActiveTab(0);
        });
        girdPanel.on("itemcontextmenu", function (his, record, item, index, e) {
            e.preventDefault();
            e.stopEvent();
            var contextMenu = Ext.create('Ext.menu.Menu', {
                items: [{
                    xtype: 'button',
                    itemId: 'increase',
                    scale: 'medium',
                    //pressed: true,
                    iconAlign: 'left',
                    //text: '<b> <span style="font-size: 15px;">添&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;加&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类</span></b>',
                    text: '添加',
                    iconCls: 'icon-create',
                    scope: this,
                    handler: me.createFlowType
                },
                    {
                        xtype: 'button',
                        itemId: 'update',
                        scale: 'medium',
                        //   pressed: true,
                        iconAlign: 'left',
                        //text: '<b> <span style="font-size: 15px;">添&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;加&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类</span></b>',
                        text: '修改',
                        iconCls: 'icon-update',
                        scope: this,
                        handler: me.updateFlowType
                    },
                    {
                        xtype: 'button',
                        itemId: 'delete',
                        scale: 'medium',
                        //   pressed: true,
                        iconAlign: 'left',
                        //text: '<b> <span style="font-size: 15px;">添&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;加&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类</span></b>',
                        text: '删除',
                        iconCls: 'icon-delete',
                        scope: this,
                        handler: me.deleteFlowType
                    }
                ]
            });

            contextMenu.showAt(e.getXY());

        });
        Ext.apply(me, {
            layout: 'fit',
            items: [girdPanel],
            modelGrid: girdPanel
        });
        me.callParent(arguments);
    },
    createFlowType: function () {
        var me = this;

        //弹出新增面板窗口
        var createWin = Ext.create('Ext.Window', {
            title: '新增流程模板类别',
            plain: true,
            height: 0.5 * globalHeight,
            width: 0.7 * globalWidth,
            layout: 'fit',
            maximizable: true,
            modal: true,
            id: 'flowTypeWin',
            items: [Ext.create('OrientTdm.PlatformTemplateMgr.FlowTempTypeMgr.AddFlowTempTypePanel', {
                bindModelName: "T_FLOW_TEMP_TYPE_480",
                successCallback: function (resp, callBackArguments) {
                    Ext.getCmp("flowTypeGridPanel").store.load();
                    createWin.close();
                }
            })]
        });
        createWin.show();
    },

    deleteFlowType: function () {
        var me = this;
        var consumeArr = Ext.getCmp('flowTypeGridPanel').getSelectionModel().getSelection();
        if (consumeArr.length === 0) {
            Ext.Msg.alert("提示", "无选中数据");
        } else {
            var flowTempTypeId = consumeArr[0].raw['id'];
            Ext.Msg.confirm("友情提示", "是否确认删除",
                function (btn) {
                    if (btn == 'yes') {
                        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/FlowTempMgr/delFlowTempTypeById.rdm', {
                            flowTempTypeId: flowTempTypeId
                        }, false, function (resp) {
                            Ext.getCmp('flowTypeGridPanel').store.load();
                            //Ext.getCmp('consumeMaterialOwner').store.load();
                            var flowTempCenterPanel = me.up('platformTemplateMgrDashBoard').centerPanel;
                            flowTempCenterPanel.items.each(function (item, index) {
                                flowTempCenterPanel.remove(item);
                            });
                        });
                    }
                });
        }
    },

    updateFlowType: function () {
        var me = this;
        var consumeArr = Ext.getCmp('flowTypeGridPanel').getSelectionModel().getSelection();
        if (consumeArr.length === 0) {
            Ext.Msg.alert("提示", "无选中数据");
        } else {
            var flowTypeName = consumeArr[0].raw['flowTempType'];
            var flowTempTypeId = consumeArr[0].raw['id'];
            var updateWin = Ext.create('Ext.Window', {
                title: '修改流程模板类别',
                plain: true,
                height: 0.5 * globalHeight,
                width: 0.7 * globalWidth,
                layout: 'fit',
                maximizable: true,
                modal: true,
                id: 'updateFlowTypeWin',
                items: [Ext.create('OrientTdm.PlatformTemplateMgr.FlowTempTypeMgr.UpdateFlowTempTypePanel', {
                    bindModelName: "T_FLOW_TEMP_TYPE_480",
                    // originalData:checkArr[0]
                    flowTempTypeId: flowTempTypeId,
                    successCallback: function (resp, callBackArguments) {
                        Ext.getCmp("flowTypeGridPanel").getSelectionModel().clearSelections();
                        Ext.getCmp("flowTypeGridPanel").store.load();
                        updateWin.close();
                    }
                })]
            });
            Ext.getCmp('flowTypeNameId').setValue(flowTypeName);
            updateWin.show();
        }
    },
    //根据tab页是否激活创建页面
    addToolMgrPanel: function (comp, stage) {
        var me = this;
        me.stage = stage;
        // me.createNodeFilePanel(stage,true);
    }
});