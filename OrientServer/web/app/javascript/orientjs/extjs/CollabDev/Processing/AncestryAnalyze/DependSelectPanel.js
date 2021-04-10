Ext.define('OrientTdm.CollabDev.Processing.AncestryAnalyze.DependSelectPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.DependSelectPanel',
    requires: [
        'OrientTdm.CollabDev.Designing.ResultSettings.DevData.ResultSettingsDevDataPanel'
    ],
    config: {
        nodeId: null,

        upStateMap: null,
        selectionForm: null,
        dataTab: null
    },
    initComponent: function () {
        var me = this;
        var nodeId = me.nodeId;

        var dataTab = Ext.create('OrientTdm.Common.Extend.Panel.OrientTabPanel', {
            region: "center",
            layout: "fit"
        });
        me.dataTab = dataTab;

        var upStateMap = null;
        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/ancestryAnalyze/getUpNodeStates.rdm', {nodeId: nodeId}, false, function (response) {
            var result = JSON.parse(response.responseText);
            upStateMap = result.results;
            me.upStateMap = upStateMap;
        }, false, me);

        var selectionForm = me.createSelectionForm(upStateMap);
        me.selectionForm = selectionForm;

        Ext.apply(me, {
            layout: "border",
            items: [selectionForm, dataTab]
        });
        me.callParent(arguments);
    },
    createDataPanel: function(nodeId, version, taskName) {
        var me = this;
        var dataPanel = Ext.getCmp(nodeId+"-"+version);
        if(dataPanel) {
            me.dataTab.setActiveTab(dataPanel);
        }
        else {
            dataPanel = Ext.create('OrientTdm.CollabDev.Designing.ResultSettings.DevData.ResultSettingsDevDataPanel', {
                id: nodeId+"-"+version,
                closable: true,
                title: taskName + "-" + version,
                nodeId: nodeId,
                nodeVersion: version,
                nodeType: "task",
                isGlobal: 1,
                isShowLeftBar: false,
                showOtherFunctionButtonType: 2,
                canCelldblclick: false
            });
            me.dataTab.add(dataPanel);
            me.dataTab.setActiveTab(dataPanel);
        }
        return dataPanel;
    },
    createSelectionForm: function(upStateMap) {
        var me = this;
        var combos = [];
        for (var upName in upStateMap) {
            var states = upStateMap[upName];
            var data = [];
            for (var i = 0; i < states.length; i++) {
                var state = states[i];
                data.push({
                    nodeId: state.nodeId,
                    version: state.nodeVersion,
                    planId: state.pid,
                    planVersion: state.pversion,
                    techStatus: state.techStatus,
                    approvalStatus: state.approvalStatus,
                    display: state.nodeVersion + " (" + state.techStatus + ", " + state.approvalStatus + ")"
                });
            }

            var maxValid = null;
            for (var i = 0; i < data.length; i++) {
                var d = data[i];
                if(d.approvalStatus == "有效") {
                    if(maxValid) {
                        if(maxValid.version < d.version) {
                            maxValid = d;
                        }
                    }
                    else {
                        maxValid = d;
                    }
                }
            }

            var combo = Ext.create("Ext.form.ComboBox", {
                fieldLabel: upName,
                name: "" + state.nodeId,
                allowBlank: false,
                store: Ext.create('Ext.data.Store', {
                    fields: ["nodeId", "version", "planId", "planVersion", "techStatus", "approvalStatus", "display"],
                    data: data
                }),
                queryMode: 'local',
                displayField: 'display',
                valueField: 'version',
                listeners: {
                    change: function(combo, newValue, oldValue) {
                        var nodeId = combo.name;
                        var version = newValue;
                        var taskName = combo.fieldLabel;
                        me.createDataPanel(nodeId, version, taskName);
                    }
                }
            });
            combo.setValue(maxValid.version);

            combos.push(combo);
        }

        var formPanel = Ext.create('Ext.form.Panel', {
            region: "north",
            height: 120,
            bodyPadding: 5,
            defaults: {
                anchor: '100%'
            },
            defaultType: 'combo',
            items: combos
        });

        return formPanel;
    },
    getDepends: function() {
        var me = this;
        var form = me.selectionForm.getForm();
        if (form.isValid()) {
            var values = form.getValues();
            return values;
        }
        else {
            return null;
        }
    }
});