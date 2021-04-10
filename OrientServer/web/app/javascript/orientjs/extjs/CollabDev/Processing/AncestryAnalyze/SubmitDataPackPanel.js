Ext.define('OrientTdm.CollabDev.Processing.AncestryAnalyze.SubmitDataPackPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.SubmitDataPackPanel',
    requires: [
        'OrientTdm.CollabDev.Processing.AncestryAnalyze.DependSelectPanel'
    ],
    config: {
        nodeId: null,
        successCallback: function() {},

        dependSelectPanel: null
    },
    initComponent: function () {
        var me = this;
        var nodeId = me.nodeId;
        var dependSelectPanel = Ext.create('OrientTdm.CollabDev.Processing.AncestryAnalyze.DependSelectPanel', {
            nodeId: nodeId
        });
        me.dependSelectPanel = dependSelectPanel;

        Ext.apply(me, {
            layout: "fit",
            items: [dependSelectPanel],
            buttons: [{
                text: '保存',
                handler: function () {
                    var depends = dependSelectPanel.getDepends();
                    me.submitDataPack(nodeId, depends);
                    me.up("window").close();
                }
            }, {
                text: '取消',
                handler: function () {
                    me.up("window").close();
                }
            }],
            listeners: {
                added: function(thisPanel, container, pos) {
                    var upStateMap = dependSelectPanel.upStateMap;
                    if(!upStateMap || JSON.stringify(upStateMap) == "{}") {
                        me.submitDataPack(nodeId, {});
                        me.up("window").close();
                    }
                }
            }
        });
        me.callParent(arguments);
    },
    submitDataPack: function (nodeId, depends) {
        var me = this;
        Ext.Ajax.request({
            url: serviceName + '/ancestryAnalyze/taskStateAnalyze.rdm',
            params: {
                nodeId: nodeId,
                depends: JSON.stringify(depends)
            },
            success: function (response) {
                me.successCallback();
            }
        });
    }
});