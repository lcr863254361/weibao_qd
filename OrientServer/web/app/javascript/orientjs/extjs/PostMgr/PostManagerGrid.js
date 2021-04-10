/**
 * Created by User on 2019/1/9.
 */
Ext.define('OrientTdm.PostMgr.PostManagerGrid', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.postManagerGrid',
    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        templateName: TDM_SERVER_CONFIG.TPL_POST_MGR,
        modelName: TDM_SERVER_CONFIG.POST_MGR,
    },

    initComponent: function () {
        var me = this;
        var modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);
        var templateId = OrientExtUtil.ModelHelper.getTemplateId(modelId, me.templateName);
        var modelGrid = Ext.create('OrientTdm.Common.Extend.Grid.OrientModelGrid', {
            region: 'center',
            modelId: modelId,
            isView: 0,
            id: 'postManagerGrid_1',
            templateId: templateId,
            afterInitComponent: function () {
                var toolbar = this.dockedItems[0];
                toolbar.add(
                    {
                        iconCls: 'icon-import',
                        text: '导入岗位',
                        disabled: false,
                        itemId: 'import',
                        scope: me,
                        handler: me.ImportpostWin
                    }, {
                        iconCls: 'icon-export',
                        text: '导出岗位',
                        disabled: false,
                        itemId: 'export',
                        scope: me,
                        handler: me.exportPostData
                    },
                    {
                        text: '删除',
                        iconCls: 'icon-delete',
                        handler: me._deletePostTemp,
                        scope: me
                    },
                    //排序
                    // this.getStore().sort([{
                    //     "property": "C_POST_TYPE_" +modelId ,
                    //     "direction": "ASC"
                    // }])
                    //{
                    //    text: '添加岗位',
                    //    hidden:true,
                    //    icon: serviceName + '/app/images/icons/default/background/bind.png',
                    //    handler: me._bindPostTemp,
                    //    scope: me
                    //}


                    //添加搜素条件
                    //{
                    //    xtype: 'label',
                    //    text: '请输入岗位名称: '
                    //},
                );
            }
        });
        if (me.buttonShow) {
            console.log(modelGrid.getDockedItems('toolbar[dock="top"]')[0]);
            modelGrid.getDockedItems('toolbar[dock="top"]')[0].items.items[0].setVisible(false);
            modelGrid.getDockedItems('toolbar[dock="top"]')[0].items.items[1].setVisible(false);
            modelGrid.getDockedItems('toolbar[dock="top"]')[0].items.items[2].setVisible(false);
            modelGrid.getDockedItems('toolbar[dock="top"]')[0].items.items[3].setVisible(false);
            modelGrid.getDockedItems('toolbar[dock="top"]')[0].items.items[4].setVisible(false);
            modelGrid.getDockedItems('toolbar[dock="top"]')[0].items.items[5].setVisible(false);
            modelGrid.getDockedItems('toolbar[dock="top"]')[0].items.items[6].setVisible(false);
        }

        Ext.apply(me, {
            layout: 'border',
            items: [modelGrid],
            modelGrid: modelGrid
        });
        me.callParent(arguments);
    },

    _deletePostTemp: function () {
        var me = this;
        var modelGrid = me.modelGrid;
        if (!OrientExtUtil.GridHelper.hasSelected(modelGrid)) {
            return;
        }
        var selectRecords = OrientExtUtil.GridHelper.getSelectedRecord(modelGrid);
        var ids = [];
        Ext.each(selectRecords, function (s) {
            ids.push(s.data.id);
        });
        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/PostMgrController/deletePostData.rdm', {
            id: ids.toString()
        }, false, function (resp) {
            var ret = Ext.decode(resp.responseText);
            if (ret.success) {
                modelGrid.fireEvent('refreshGrid');
                Ext.Msg.alert("提示", "删除成功！")
            }
        })
    },

    ImportpostWin: function () {
        var me = this;
        var win = Ext.create("Ext.Window", {
            title: '新增岗位',
            plain: true,
            height: 100,
            width: '70%',
            layout: 'fit',
            maximizable: true,
            modal: true,
            items: [
                {
                    xtype: 'form',
                    bodyPadding: 10,
                    layout: 'anchor',
                    defaults: {
                        anchor: '100%',
                        labelAlign: 'left',
                        msgTarget: 'side',
                        labelWidth: 90
                    },
                    items: [{
                        xtype: 'filefield',
                        buttonText: '',
                        fieldLabel: '导入岗位',
                        buttonConfig: {
                            iconCls: 'icon-upload'
                        },
                        listeners: {
                            'change': function (fb, v) {
                                if (v.substr(v.length - 3) != "xls" && v.substr(v.length - 4) != "xlsx") {
                                    OrientExtUtil.Common.info('提示', '请选择Excel文件！');
                                    return;
                                }
                            }
                        }
                    }]
                }
            ],
            buttons: [
                {
                    text: '导入',
                    handler: function () {
                        var form = win.down("form").getForm();
                        if (form.isValid()) {
                            form.submit({
                                url: serviceName + '/PostMgrController/importPostData.rdm',
                                waitMsg: '导入岗位...',
                                success: function (form, action) {
                                    OrientExtUtil.Common.tip('成功', action.result.msg);
                                    win.close();
                                    me.fireEvent("refreshGrid");
                                },
                                failure: function (form, action) {
                                    switch (action.failureType) {
                                        case Ext.form.action.Action.CLIENT_INVALID:
                                            OrientExtUtil.Common.err('失败', '文件路径存在错误');
                                            break;
                                        case Ext.form.action.Action.CONNECT_FAILURE:
                                            OrientExtUtil.Common.err('失败', '无法连接服务器');
                                            break;
                                        case Ext.form.action.Action.SERVER_INVALID:
                                            OrientExtUtil.Common.err('失败', action.result.msg);
                                    }
                                }
                            });
                        }
                    }
                }
            ]
        });
        win.show();
    },

    exportPostData: function () {
        var exportButton = this;
        var grid = exportButton.modelGrid
        var selections = grid.getSelectionModel().getSelection();
        var toExportIds = OrientExtUtil.GridHelper.getSelectRecordIds(grid);
        var exportAll = false;
        if (selections.length === 0) {
            Ext.MessageBox.confirm('提示', '是否导出所有岗位信息？', function (btn) {
                if (btn == 'yes') {
                    exportAll = true;
                    window.location.href = serviceName + '/PostMgrController/exportPostData.rdm?exportAll=' + exportAll + '&toExportIds=' + toExportIds;
                }

            });
        } else {
            window.location.href = serviceName + '/PostMgrController/exportPostData.rdm?exportAll=' + exportAll + '&toExportIds=' + toExportIds;
        }
    }
});