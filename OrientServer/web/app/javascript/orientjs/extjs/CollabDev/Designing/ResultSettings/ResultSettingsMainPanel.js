Ext.define('OrientTdm.CollabDev.Designing.ResultSettings.ResultSettingsMainPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientTabPanel',
    alias: 'widget.resultSettingsMainPanel',
    config: {
        nodeId: '',
        nodeVersion: ''
    },
    initComponent: function () {

        var me = this;
        Ext.apply(me,
            {
                items: [],
                activeItem: 0
            }
        );

        me.callParent(arguments);
    },
    restructureTabs: function (nodeId, nodeVersion, checkBoxPannel) {
        var me = this;
        me.nodeId = nodeId;
        me.nodeVersion = nodeVersion;
        //先移除所有的内部面板
        me.removeAll();
        var params = {
            nodeId: nodeId
        };
        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/resultSettings/getTabs.rdm', params, true, function (resp) {
            var result = Ext.decode(resp.responseText).results;
            if (result.hasDevData) {
                var devDataPanel = Ext.create('OrientTdm.CollabDev.Designing.ResultSettings.DevData.ResultSettingsDevDataPanel', {
                    itemId: 'devDataPanel-' + nodeId,
                    nodeId: nodeId,
                    nodeVersion: nodeVersion,
                    isGlobal: 1,
                    showOtherFunctionButtonType: 0,
                    title: '研发数据'
                });
                me.add(devDataPanel);
                checkBoxPannel.modifyDevCheckBoxStatus(true);
            } else {
                checkBoxPannel.modifyDevCheckBoxStatus(false);
            }
            if (result.hasModelData) {
                var modelDataPanel = Ext.create('OrientTdm.CollabDev.Designing.ResultSettings.ComponentData.ComponentDataPanel', {
                    itemId: 'modelDataPanel-' + nodeId,
                    nodeId: nodeId
                });
                me.add(modelDataPanel);
                checkBoxPannel.modifyModelCheckBoxStatus(true);
            } else {
                checkBoxPannel.modifyModelCheckBoxStatus(false);
            }
            if (result.hasPVMData) {
                var pvmDataPanel = Ext.create('OrientTdm.CollabDev.Designing.ResultSettings.PVMData.ResultSettingsPVMDataPanel', {
                    itemId: 'pvmDataPanel-' + nodeId,
                    nodeId: nodeId
                });
                me.add(pvmDataPanel);
                checkBoxPannel.modifyPVMCheckBoxStatus(true);
            } else {
                checkBoxPannel.modifyPVMCheckBoxStatus(false);
            }
            me.setActiveTab(0);
        });
    }
});