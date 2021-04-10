/**
 * Created by User on 2018/12/12.
 */
Ext.define('OrientTdm.FormTemplateMgr.CheckTypeManagerGrid', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.checkTypeManagerGrid',
    id: 'checkTypeGrid',
    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        modelName: TDM_SERVER_CONFIG.CHECK_TYPE,
        modelId: ''
    },
    initComponent: function () {
        var me = this;
        modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);
        //this.dockedItems = [{
        //     //xtype: 'toolbar',
        //     //dock: 'top',
        //    buttons:
        //}];

        var store = Ext.create('Ext.data.Store', {

            fields: ["id", "checkType"],
            proxy: {
                type: 'ajax',
                url: serviceName + "/formTemplate/queryCheckTypeList.rdm",
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
            {
                id: 'checkHeaderId', text: '检查名称', dataIndex: 'checkType', flex: 1, align: 'center',
                renderer: function (value, meta, record) {
                    value = value || '';
                    meta.tdAttr = 'data-qtip="' + value + '"';
                    return value;
                }
            }
        ];
        var girdPanel = Ext.create('Ext.grid.Panel', {
            renderTo: Ext.getBody(),
            hideHeaders: true,
            store: store,
            columns: columns,
            buttonAlign: 'center',
            id: 'checkTypeGridPanel',
            tbar: ['', {
                xtype: 'button',
                itemId: 'increase',
                //text: '<b> <span style="font-size: 15px;">添&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;加&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类</span></b>',
                text: '添加',
                iconCls: 'icon-create',
                scope: this,
                handler: this.createCheckType
            },
                {
                    xtype: 'button',
                    itemId: 'update',
                    //text: '<b> <span style="font-size: 15px;">添&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;加&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类</span></b>',
                    text: '修改',
                    iconCls: 'icon-update',
                    scope: this,
                    handler: this.updateCheckType
                },
                {
                    xtype: 'button',
                    itemId: 'delete',
                    //text: '<b> <span style="font-size: 15px;">添&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;加&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类</span></b>',
                    text: '删除',
                    iconCls: 'icon-delete',
                    scope: this,
                    handler: this.deleteCheckType
                }
            ]
            //selType: 'rowmodel',
            //plugins: [
            //    Ext.create('Ext.grid.plugin.RowEditing', {
            //        clicksToEdit: 1
            //    })
            //]
        });

        girdPanel.addListener('itemclick', function (grid, record, item, index, e) {
            var checkTypeId = record.data.id;
            var checkTypeName = record.data.checkType
            var formTemplateCenterPanel = me.up('formTempManagerDashboard').centerPanel;
            formTemplateCenterPanel.items.each(function (item, index) {
                formTemplateCenterPanel.remove(item);
            });
            var formTemplateMgrPanel = Ext.create('OrientTdm.FormTemplateMgr.FormTemplatePanel.FormTemplateMgrPanel', {
                region: 'center',
                checkTypeId: checkTypeId,
                checkTypeName: checkTypeName
            });

            formTemplateCenterPanel.add({
                title: '检查表模板列表',
                layout: 'border',
                items: [formTemplateMgrPanel]
            });
            formTemplateCenterPanel.setActiveTab(0);
        });

        //girdPanel.on("itemclick", function (grid, record, item, index, e) {
        //    var checkTypeId = record.data.id;
        //    var formTemplateCenterPanel = me.up('formTempManagerDashboard').centerPanel;
        //    formTemplateCenterPanel.items.each(function (item, index) {
        //        formTemplateCenterPanel.remove(item);
        //    });
        //    var formTemplateMgrPanel = Ext.create('OrientTdm.FormTemplateMgr.FormTemplatePanel.FormTemplateMgrPanel', {
        //        region: 'center',
        //        checkTypeId: checkTypeId
        //    });
        //
        //    formTemplateCenterPanel.add({
        //        title: '检查表模板列表',
        //        layout: 'border',
        //        items: [formTemplateMgrPanel]
        //    });
        //    formTemplateCenterPanel.setActiveTab(0);
        //});
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
                    handler: me.createCheckType
                }, {
                    xtype: 'button',
                    itemId: 'update',
                    scale: 'medium',
                    //   pressed: true,
                    iconAlign: 'left',
                    //text: '<b> <span style="font-size: 15px;">添&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;加&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类</span></b>',
                    text: '修改',
                    iconCls: 'icon-update',
                    scope: this,
                    handler: me.updateCheckType
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
                        handler: me.deleteCheckType
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
    createCheckType: function () {
        var me = this;

        //弹出新增面板窗口
        var createWin = Ext.create('Ext.Window', {
            title: '新增检查类别',
            plain: true,
            height: 0.5 * globalHeight,
            width: 0.7 * globalWidth,
            layout: 'fit',
            maximizable: true,
            modal: true,
            id: 'checkTypeWin',
            items: [Ext.create('OrientTdm.FormTemplateMgr.FormPanel.AddCheckTypeFormPanel', {
                bindModelName: "T_CHECK_TYPE_480",
                successCallback: function (resp, callBackArguments) {
                    Ext.getCmp('checkTypeGrid').modelGrid.store.load();
                    createWin.close();
                }
            })]
        });
        createWin.show();
    },

    deleteCheckType: function () {
        var me = this;
        //console.log(Ext.ComponentQuery.query('grid')[0].getSelectionModel().getSelection());
        //var checkArr = Ext.ComponentQuery.query('grid')[0].getSelectionModel().getSelection();
        var checkArr = Ext.getCmp('checkTypeGridPanel').getSelectionModel().getSelection();
        if (checkArr.length === 0) {
            Ext.Msg.alert("提示", "无选中数据");
        } else {
            var checkTypeId = checkArr[0].raw['id'];
            //var selectList=me.getSelectionModel().getSelection();
            // var checkTypeId=selectList[0].raw.ID;
            Ext.Msg.confirm("友情提示", "是否确认删除",
                function (btn) {
                    if (btn == 'yes') {
                        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/formTemplate/delCheckTypeById.rdm', {
                            checkTypeId: checkTypeId
                        }, false, function (resp) {
                            //Ext.MessageBox.alert('提示', '删除成功');
                            console.log(me);
                            Ext.getCmp('checkTypeGrid').modelGrid.store.load();
                            var formTemplateCenterPanel = me.up('formTempManagerDashboard').centerPanel;
                            if (formTemplateCenterPanel.items.items[0].items.items[0].items.items[2]) {
                                formTemplateCenterPanel.items.items[0].items.items[0].items.items[0].fireEvent('refreshTree', false);
                                formTemplateCenterPanel.items.items[0].items.items[0].items.items[2].items.each(function (item, index) {
                                    formTemplateCenterPanel.items.items[0].items.items[0].items.items[2].remove(item);
                                })
                            }
                        });
                    }
                });
        }
    },

    updateCheckType: function () {
        var me = this;
        // var checkArr = Ext.ComponentQuery.query('grid')[0].getSelectionModel().getSelection();
        var checkArr = Ext.getCmp('checkTypeGridPanel').getSelectionModel().getSelection();
        if (checkArr.length === 0) {
            Ext.Msg.alert("提示", "无选中数据");
        } else {
            // var checkTypeName = Ext.ComponentQuery.query('grid')[0].getSelectionModel().getSelection()[0].raw['checkType'];
            // var checkTypeId = Ext.ComponentQuery.query('grid')[0].getSelectionModel().getSelection()[0].raw['id'];
            var checkTypeId = checkArr[0].raw['id'];
            var checkTypeName =checkArr[0].raw['checkType'];
            var updateWin = Ext.create('Ext.Window', {
                title: '修改检查类别',
                plain: true,
                height: 0.5 * globalHeight,
                width: 0.7 * globalWidth,
                layout: 'fit',
                maximizable: true,
                modal: true,
                items: [Ext.create('OrientTdm.FormTemplateMgr.FormPanel.UpdateCheckTypeFormPanel', {
                    bindModelName: "T_CHECK_TYPE_480",
                    // originalData:checkArr[0]
                    checkTypeId: checkTypeId,
                    successCallback: function (resp, callBackArguments) {
                        Ext.getCmp("checkTypeGrid").modelGrid.getSelectionModel().clearSelections();
                        Ext.getCmp('checkTypeGrid').modelGrid.store.load();
                        updateWin.close();
                    }
                })]
            });
            console.log(Ext.getCmp('checkTypeNameId'));
            Ext.getCmp('checkTypeNameId').setValue(checkTypeName);
            updateWin.show();
        }
    }
});
