Ext.define('OrientTdm.HistoryCheckTableCompareMgr.HistoryCheckInstFilterPanel', {
    extend: 'OrientTdm.Common.Extend.Form.OrientForm',
    buttonAlign: 'center',
    initComponent: function () {
        var me = this;
        Ext.apply(this, {
            items: [
                {
                    xtype: 'fieldcontainer',
                    layout: 'hbox',
                    combineErrors: true,
                    defaults: {
                        flex: 1
                    },
                    items: [
                        {
                            name: 'startDate',
                            xtype: 'datefield',
                            fieldLabel: '检查时间',
                            margin: '0 5 0 0',
                            flex: 0.5,
                            format: 'Y-m-d',
                            // editable:false,
                            maxValue: new Date()  // limited to the current date or prior
                        }, {
                            name: 'endDate',
                            xtype: 'datefield',
                            labelWidth: 30,
                            fieldLabel: '至',
                            flex: 0.5,
                            format: 'Y-m-d',
                            // editable:false,
                            maxValue: new Date()  // limited to the current date or prior
                        }
                    ]
                }
            ],
            buttons: [
                {
                    text: '查询',
                    iconCls: 'icon-query',
                    handler: function () {
                        me._doQuery();
                    }
                },
                {
                    text: '清空',
                    iconCls: 'icon-clear',
                    handler: function () {
                        this.up('form').getForm().reset();
                        me._doQuery();
                    }
                },
                {
                    text: '导出',
                    iconCls: 'icon-export',
                    handler: function () {
                        var me = this;
                        var formValue = OrientExtUtil.FormHelper.generateFormData(me.ownerCt.ownerCt.getForm());
                        // var params = [];
                        var object = new Object;
                        for (var proName in formValue) {
                            var proValue = formValue[proName];
                            if (!Ext.isEmpty(proValue) && proName === 'startDate') {
                                proValue = proValue + " 00:00:00"
                            }
                            if (!Ext.isEmpty(proValue) && proName === 'endDate') {
                                proValue = proValue + " 23:59:59"
                            }
                            if (!Ext.isEmpty(proValue)) {
                                object[proName] = proValue;
                                // params.push(object);
                            }
                        }
                        var filters = '';
                        if (object != null) {
                            filters = JSON.stringify(object);
                        }
                        var modelGridPanel = this.ownerCt.ownerCt.ownerCt.centerPanel;
                        var selections = modelGridPanel.getSelectionModel().getSelection();
                        var exportAll = false;
                        if (selections.length === 0) {
                            Ext.MessageBox.confirm('提示', '是否导出所有历史检查项信息？', function (btn) {
                                if (btn == 'yes') {
                                    exportAll = true;
                                    window.location.href = serviceName + '/formTemplate/exportHistoryCheckItemData.rdm?exportAll=' + exportAll + '&filters=' + filters + '&cellId=' + me.ownerCt.ownerCt.cellId + '&columns=' + me.ownerCt.ownerCt.columns + '&checkTempName=' + me.ownerCt.ownerCt.checkTempName;
                                }
                            });
                        }
                    }
                }
            ]
        });
        me.callParent(arguments);
    },
    _doQuery: function () {
        var me = this;
        var formValue = OrientExtUtil.FormHelper.generateFormData(me.getForm());
        if (me.ownerCt.centerPanel) {
            me.ownerCt.centerPanel.fireEvent("filterByFilter", formValue);
        }
    }
});