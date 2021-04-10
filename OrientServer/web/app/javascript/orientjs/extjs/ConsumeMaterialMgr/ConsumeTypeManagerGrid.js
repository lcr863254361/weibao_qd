/**
 * Created by User on 2019/5/17.
 */
Ext.define('OrientTdm.ConsumeMaterialMgr.ConsumeTypeManagerGrid', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.consumeTypeManagerGrid',
    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        modelName: TDM_SERVER_CONFIG.CONSUME_MATERIAL,
        modelId: ''
    },
    initComponent: function () {
        var me = this;
        modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);

        var store = Ext.create('Ext.data.Store', {

            fields: ["id", "consumeType"],
            proxy: {
                type: 'ajax',
                url: serviceName + "/ConsumeMaterialMgr/queryConsumeTypeList.rdm",
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
            {id: 'consumeHeadId', text: '耗材类别名称', dataIndex: 'consumeType', flex: 1, align: 'center'}
        ];
        var girdPanel = Ext.create('Ext.grid.Panel', {
            renderTo: Ext.getBody(),
            hideHeaders: true,
            store: store,
            columns: columns,
            buttonAlign: 'center',
            id:'consumeTypeGridPanel',
            tbar: ['','','',{
                xtype: 'button',
                itemId: 'increase',
                //text: '<b> <span style="font-size: 15px;">添&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;加&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类</span></b>',
                text: '添加',
                iconCls: 'icon-create',
                scope: this,
                handler: this.createConsumeType
            },'',
                {
                    xtype: 'button',
                    itemId: 'update',
                    //text: '<b> <span style="font-size: 15px;">添&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;加&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类</span></b>',
                    text: '修改',
                    iconCls: 'icon-update',
                    scope: this,
                    handler: this.updateConsumeType
                },'',
                {
                    xtype: 'button',
                    itemId: 'delete',
                    //text: '<b> <span style="font-size: 15px;">添&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;加&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类</span></b>',
                    text: '删除',
                    iconCls: 'icon-delete',
                    scope: this,
                    handler: this.deleteConsumeType
                }
            ]
        });

        girdPanel.addListener('itemclick', function (grid, record, item, index, e) {
            var consumeTypeId = record.data.id;
            //var checkTypeName=record.data.checkType
            var consumeDetailCenterPanel = me.up('consumeManagerDashboard').centerPanel;
            consumeDetailCenterPanel.items.each(function (item, index) {
                consumeDetailCenterPanel.remove(item);
            });
            var consumeDetailGridPanel = Ext.create('OrientTdm.ConsumeMaterialMgr.ConsumeDetailMgr.ConsumeDetailGridPanel', {
                region: 'center',
                consumeTypeId: consumeTypeId,
            });

            consumeDetailCenterPanel.add({
                title: '耗材列表',
                layout: 'border',
                items: [consumeDetailGridPanel]
            });
            consumeDetailCenterPanel.setActiveTab(0);
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
                    handler: me.createConsumeType
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
                        handler: me.updateConsumeType
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
                        handler: me.deleteConsumeType
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
    createConsumeType: function () {
        var me = this;

        //弹出新增面板窗口
        var createWin = Ext.create('Ext.Window', {
            title: '新增耗材类别',
            plain: true,
            height: 0.5 * globalHeight,
            width: 0.7 * globalWidth,
            layout: 'fit',
            maximizable: true,
            modal: true,
            id:'consumeTypeWin',
            items: [Ext.create('OrientTdm.ConsumeMaterialMgr.ConsumeTypeMgr.AddConsumeTypePanel', {
                bindModelName: "T_CONSUME_MATERIAL_480",
                successCallback: function (resp, callBackArguments) {
                    Ext.getCmp("consumeTypeGridPanel").store.load();
                    createWin.close();
                }
            })]
        });
        createWin.show();
    },

    deleteConsumeType: function () {
        var me = this;
        var consumeArr= Ext.getCmp('consumeTypeGridPanel').getSelectionModel().getSelection();
        if (consumeArr.length === 0) {
            Ext.Msg.alert("提示", "无选中数据");
        } else {
            var consumeTypeId = consumeArr[0].raw['id'];
            Ext.Msg.confirm("友情提示", "是否确认删除",
                function (btn) {
                    if (btn == 'yes') {
                        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/ConsumeMaterialMgr/delConsumeTypeById.rdm', {
                            consumeTypeId: consumeTypeId
                        }, false, function (resp) {
                            Ext.getCmp('consumeTypeGridPanel').store.load();
                            Ext.getCmp('consumeMaterialOwner').store.load();
                        });
                    }
                });
        }
    },

    updateConsumeType: function () {
        // var me = this;
        var consumeArr = Ext.getCmp('consumeTypeGridPanel').getSelectionModel().getSelection();
        if (consumeArr.length === 0) {
            Ext.Msg.alert("提示", "无选中数据");
        } else {
            var consumeTypeName = consumeArr[0].raw['consumeType'];
            var consumeTypeId = consumeArr[0].raw['id'];
            var updateWin = Ext.create('Ext.Window', {
                title: '修改耗材类别',
                plain: true,
                height: 0.5 * globalHeight,
                width: 0.7 * globalWidth,
                layout: 'fit',
                maximizable: true,
                modal: true,
                id:'updateconsumeTypeWin',
                items: [Ext.create('OrientTdm.ConsumeMaterialMgr.ConsumeTypeMgr.UpdateConsumeTypePanel', {
                    bindModelName: "T_CONSUME_MATERIAL_480",
                    // originalData:checkArr[0]
                    consumeTypeId: consumeTypeId,
                    successCallback: function () {
                        Ext.getCmp("consumeTypeGridPanel").getSelectionModel().clearSelections();
                        Ext.getCmp("consumeTypeGridPanel").store.load();
                        updateWin.close();
                    },
                })]
            });
            Ext.getCmp('consumeTypeNameId').setValue(consumeTypeName);
            updateWin.show();
        }
    }
});