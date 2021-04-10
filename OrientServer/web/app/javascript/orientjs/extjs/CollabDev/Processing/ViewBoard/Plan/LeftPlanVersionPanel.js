/**
 * 计划版本迭代轮展示面板
 */
Ext.define('OrientTdm.CollabDev.Processing.ViewBoard.Plan.LeftPlanVersionPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.leftPlanVersionPanel',
    config: {
        nodeId: null
    },
    requires: [
        'OrientTdm.CollabDev.Processing.ViewBoard.Plan.WheelIterationModel',
        'OrientTdm.CollabDev.Processing.ViewBoard.Plan.PlanVersionBrowser'
    ],
    initComponent: function () {
        var me = this;

        Ext.apply(me, {
            layout: 'border',
            items: [
                {
                    xtype: 'combo',
                    region: 'north',
                    allowBlank: false,
                    store: Ext.create('Ext.data.Store', {
                            autoLoad: true,
                            model: 'OrientTdm.CollabDev.Processing.ViewBoard.Plan.WheelIterationModel',
                            proxy: {
                                type: 'ajax',
                                url: serviceName + '/nodeVersions/wheelsIterations.rdm',
                                reader: {
                                    type: 'json',
                                    successProperty: 'success',
                                    totalProperty: 'totalProperty',
                                    root: 'results',
                                    messageProperty: 'message'
                                },
                                extraParams: {
                                    nodeId: me.nodeId
                                }
                            }
                        }
                    ),
                    displayField: 'selectText',
                    valueField: 'wheelNumber',
                    listeners: {
                        select: function (combo, records, index) {
                            var record = records[0];
                            var planVersionBrowser = me.down('planVersionBrowser');
                            var store = planVersionBrowser.getStore();
                            store.proxy.url = serviceName + '/nodeVersions/listPlanVersionsByWheelNumber.rdm';
                            store.proxy.extraParams = {
                                nodeId: me.nodeId,
                                startVersion: record.data.startVersion,
                                endVersion: record.data.endVersion
                            };
                            store.load();
                        },
                        render: function (combo) {
                            //设置combo的默认选中值
                            combo.getStore().on('load', function (store, records, o) {
                                combo.setValue(records[0].get('selectText'));
                            })
                        }
                    }
                },
                {
                    xtype: 'planVersionBrowser',
                    id: 'plan-version-view',
                    autoScroll: true,
                    region: 'center',
                    nodeId: me.nodeId,
                    listeners: {
                        selectionchange: function (dataView, selectNodes) {
                            var planProgressMonitorPanel = me.up('planProgressMonitorPanel');
                            if (selectNodes.length > 0) {
                                planProgressMonitorPanel.fireEvent('refresh', selectNodes[0].data.version);
                            }
                        },
                        render: function (dataView) {
                            //dataView每次加载完数据后默认选中第一条
                            dataView.getStore().on('load', function (store, records, o) {
                                dataView.select(records[0]);
                            })
                        }
                    }
                }]
        });

        me.callParent(arguments);
    }
});