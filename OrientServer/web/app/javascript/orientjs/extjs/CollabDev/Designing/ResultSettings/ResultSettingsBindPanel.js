Ext.define('OrientTdm.CollabDev.Designing.ResultSettings.ResultSettingsBindPanel', {
    extend: 'OrientTdm.Common.Extend.Form.OrientForm',
    alias: 'widget.resultSettingsBindPanel',
    config: {
        nodeId: null,
        nodeVersion: null
    },
    initComponent: function () {
        var me = this;


        Ext.apply(me, {
            layout: {
                type: 'vbox',
                align: 'stretch'
            },
            items: [{
                xtype: 'fieldcontainer',
                //anchor:'100%',
                layout: {
                    type: 'hbox',
                    //padding: '10',
                    pack: 'end',
                    align: 'stretch'
                },

                combineErrors: true,
                defaults: {margin: '0 5 0 0'},
                /* defaults: {
                 flex: 1
                 },*/
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: '交付物类型',
                    labelAlign: 'right'
                }, {
                    xtype: 'checkboxfield',
                    boxLabel: '研发数据',
                    margin: '0 10 0 0',
                    inputValue: '1',
                    readOnly: false,
                    itemId: 'devData',
                    listeners: {
                        change: function (el, checked) {
                            me.handlerCheckBoxClick(1, checked);
                        }
                    }
                }, {
                    xtype: 'checkboxfield',
                    boxLabel: '组件数据',
                    margin: '0 10 0 0',
                    inputValue: '2',
                    readOnly: false,
                    itemId: 'modelData',
                    listeners: {
                        change: function (el, checked) {
                            me.handlerCheckBoxClick(2, checked);
                        }
                    }
                }, {
                    xtype: 'checkboxfield',
                    boxLabel: '离线数据',
                    margin: '0 10 0 0',
                    inputValue: '3',
                    readOnly: false,
                    itemId: 'pvmData',
                    listeners: {
                        change: function (el, checked) {
                            me.handlerCheckBoxClick(3, checked);
                        }
                    }
                }]
            }]

        });

        me.callParent(arguments);
    },
    handlerCheckBoxClick: function (type, checked) {
        var _this = this;

        var params = {
            nodeId: _this.nodeId,
            devType: type,
            checked: checked
        };
        var resultSettingsMainPanel = _this.ownerCt.centerPanel;

        if (checked) {
            var toAddPanel = null;
            OrientExtUtil.AjaxHelper.doRequest(serviceName + '/resultSettings/nodeBind.rdm', params, false, function (resp) {
                var retV = Ext.decode(resp.responseText);
                if (retV.success) {
                    switch (type) {
                        case 1:
                            toAddPanel = Ext.create('OrientTdm.CollabDev.Designing.ResultSettings.DevData.ResultSettingsDevDataPanel', {
                                title: '研发数据',
                                itemId: 'devDataPanel-' + _this.nodeId,
                                nodeId: _this.nodeId,
                                nodeVersion: _this.nodeVersion
                            });
                            break;
                        case 2:
                            toAddPanel = Ext.create('OrientTdm.CollabDev.Designing.ResultSettings.ComponentData.ComponentDataPanel', {
                                itemId: 'modelDataPanel-' + _this.nodeId,
                                nodeId: _this.nodeId
                            });
                            break;
                        case 3:
                            toAddPanel = Ext.create('OrientTdm.CollabDev.Designing.ResultSettings.PVMData.ResultSettingsPVMDataPanel', {
                                    itemId: 'pvmDataPanel-' + _this.nodeId,
                                    nodeId: _this.nodeId
                                }
                            );
                            break;
                    }
                }
            });
            if (!resultSettingsMainPanel.contains(toAddPanel)) {
                resultSettingsMainPanel.add(toAddPanel);
            }
        } else {
            var toDelPanel = null;
            OrientExtUtil.AjaxHelper.doRequest(serviceName + '/resultSettings/nodeBind.rdm', params, false, function (resp) {
                var retV = Ext.decode(resp.responseText);
                if (retV.success) {
                    switch (type) {
                        case 1:
                            toDelPanel = resultSettingsMainPanel.child('panel[itemId=' + 'devDataPanel-' + _this.nodeId + ']');
                            break;
                        case 2:
                            toDelPanel = resultSettingsMainPanel.child('panel[itemId=' + 'modelDataPanel-' + _this.nodeId + ']');
                            break;
                        case 3:
                            toDelPanel = resultSettingsMainPanel.child('panel[itemId=' + 'pvmDataPanel-' + _this.nodeId + ']');
                            break;
                    }
                }
            });
            resultSettingsMainPanel.remove(toDelPanel);
        }
        resultSettingsMainPanel.setActiveTab(resultSettingsMainPanel.items.length - 1);
    },
    modifyDevCheckBoxStatus: function (checked) {
        var me = this;
        me.down('#devData').setValue(checked)
    },
    modifyModelCheckBoxStatus: function (checked) {
        var me = this;
        me.down('#modelData').setValue(checked)
    },
    modifyPVMCheckBoxStatus: function (checked) {
        var me = this;
        me.down('#pvmData').setValue(checked)
    }
});