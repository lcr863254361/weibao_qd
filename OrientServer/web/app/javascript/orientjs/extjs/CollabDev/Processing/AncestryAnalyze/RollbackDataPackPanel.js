Ext.define('OrientTdm.CollabDev.Processing.AncestryAnalyze.RollbackDataPackPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.RollbackDataPackPanel',
    requires: [
        'OrientTdm.CollabDev.Processing.AncestryAnalyze.DependSelectPanel'
    ],
    config: {
        nodeId: null,
        successCallback: function() {},

        hisVersionForm: null,
        dependSelectPanel: null
    },
    initComponent: function () {
        var me = this;
        var nodeId = me.nodeId;

        var hisVersionForm = me.createHisVersionForm(nodeId);
        me.hisVersionForm = hisVersionForm;

        var dependSelectPanel = Ext.create('OrientTdm.CollabDev.Processing.AncestryAnalyze.DependSelectPanel', {
            title: '选择数据包依赖',
            nodeId: nodeId,
            region: "center"
        });
        me.dependSelectPanel = dependSelectPanel;
        var upStateMap = dependSelectPanel.upStateMap;
        if(!upStateMap || JSON.stringify(upStateMap) == "{}") {
            dependSelectPanel.hide();
        }

        Ext.apply(me, {
            layout: "border",
            items: [hisVersionForm, dependSelectPanel],
            buttons: [{
                text: '保存',
                handler: function () {
                    var toVersion = me.getToVersion();
                    var depends = dependSelectPanel.getDepends();
                    if(toVersion) {
                        me.rollbackDataPack(nodeId, toVersion, depends);
                        me.up("window").close();
                    }
                }
            }, {
                text: '取消',
                handler: function () {
                    me.up("window").close();
                }
            }]
        });
        me.callParent(arguments);
    },
    createHisVersionForm: function(nodeId) {
        var me = this;
        var hisNodes = null;
        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/ancestryAnalyze/getAllHisNodes.rdm', {nodeId: nodeId}, false, function (response) {
            var result = JSON.parse(response.responseText);
            hisNodes = result.results;
        }, false, me);

        var data = [];
        for (var i = 0; i < hisNodes.length; i++) {
            var hisNode = hisNodes[i];
            data.push({
                name: "V " + hisNode.version,
                value: hisNode.version
            });
        }
        var versionPanel = Ext.create('Ext.form.Panel', {
            title: '选择撤回版本',
            region: "north",
            height: 70,
            bodyPadding: 5,
            defaults: {
                anchor: '100%'
            },
            defaultType: 'combo',
            items: [{
                fieldLabel: "历史版本",
                name: "rollbackToVersion",
                allowBlank: false,
                store: Ext.create('Ext.data.Store', {
                    fields: ['name', 'value'],
                    data: data
                }),
                queryMode: 'local',
                displayField: 'name',
                valueField: 'value'
            }]
        });
        return versionPanel;
    },
    getToVersion: function() {
        var me = this;
        var hisVersionForm = me.hisVersionForm.getForm();
        if (hisVersionForm.isValid()) {
            var toVersion = hisVersionForm.getValues().rollbackToVersion;
            return toVersion;
        }
        else {
            return null;
        }
    },
    rollbackDataPack: function (nodeId, toVersion, depends) {
        var me = this;
        Ext.Ajax.request({
            url: serviceName + '/ancestryAnalyze/rollbackStateAnalyze.rdm',
            params: {
                nodeId: nodeId,
                rollbackToVersion: toVersion,
                depends: JSON.stringify(depends)
            },
            success: function (response) {
                me.successCallback();
            }
        });
    }
});