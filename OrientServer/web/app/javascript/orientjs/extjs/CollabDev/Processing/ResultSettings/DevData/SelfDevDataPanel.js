Ext.define('OrientTdm.CollabDev.Processing.ResultSettings.DevData.SelfDevDataPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.selfDevDataPanel',
    config: {
        nodeId: null,
        nodeVersion: null,
        type: null,
        isGlobal: null,
        showOtherFunctionButtonType: null
    },
    requires: [
        'OrientTdm.CollabDev.Designing.ResultSettings.DevData.ResultSettingsDevDataPanel',
        'OrientTdm.CollabDev.Processing.ResultSettings.DevData.Model.TaskVersionModel'
    ],
    initComponent: function () {

        var me = this;

        Ext.apply(me, {
            layout: 'fit',
            icon: null,
            dockedItems: [
                {
                    xtype: 'toolbar',
                    dock: 'top',
                    hidden: me.type != TDM_SERVER_CONFIG.NODE_TYPE_TASK,
                    items: [
                        {
                            xtype: 'combo',
                            width: 350,
                            itemId: 'taskVersionSwitchCombo',
                            fieldLabel: '版本切换',
                            editable: false,
                            labelAlign: 'right',
                            displayField: 'selectText',
                            valueField: 'version',
                            store: Ext.create('Ext.data.Store', {
                                    autoLoad: true,
                                    model: 'OrientTdm.CollabDev.Processing.ResultSettings.DevData.Model.TaskVersionModel',
                                    proxy: {
                                        type: 'ajax',
                                        url: serviceName + '/nodeVersions.rdm',
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
                            listeners: {
                                select: function (combo, records, index) {
                                    var grid = me.down('devDataSettingGrid');
                                    var panel = me.up('processingDevDataMainTabPanel').up('processingResultSettingsTabPanel');
                                    var version = records[0].data.version;
                                    if (me.nodeVersion != version) {  //当切换到非当前版本时，左侧的按钮禁止使用,grid条目禁止双击编辑
                                        panel.down('#taskOperation').disable();
                                        panel.down('#superButtonGroup').disable();
                                        grid.down('#baseButtonGroup').disable();
                                        grid.canCelldblclick = false;
                                    } else {//当切换回当前版本时，左侧的按钮可以使用，grid条目可以双击编辑
                                        panel.down('#taskOperation').enable();
                                        panel.down('#superButtonGroup').enable();
                                        grid.down('#baseButtonGroup').enable();
                                        grid.canCelldblclick = true;
                                    }
                                    grid.nodeVersion = version;
                                    grid.reloadData(version);
                                },
                                afterRender: function (combo) {
                                    //combo默认选中当前版本
                                    combo.setValue(me.nodeVersion);
                                }
                            }
                        }
                    ]
                }
            ],
            items: [
                {
                    xtype: 'resultSettingsDevDataPanel',
                    flex: 10,
                    nodeId: me.nodeId,
                    nodeVersion: me.nodeVersion,
                    isGlobal: 1,
                    nodeType: me.type,
                    showOtherFunctionButtonType: 1
                }]
        });

        me.callParent(arguments);
    }
});