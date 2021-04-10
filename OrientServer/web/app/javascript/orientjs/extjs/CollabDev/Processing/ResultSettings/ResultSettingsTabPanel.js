Ext.define('OrientTdm.CollabDev.Processing.ResultSettings.ResultSettingsTabPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientTabPanel',
    alias: 'widget.processingResultSettingsTabPanel',
    config: {
        nodeId: null,
        nodeVersion: null,
        bmDataId: null,
        type: null
    },
    requires: [
        'OrientTdm.Common.Extend.Form.Field.MultiComboBox',
        'OrientTdm.CollabDev.Processing.AncestryAnalyze.SubmitDataPackPanel',
        'OrientTdm.CollabDev.Processing.AncestryAnalyze.RollbackDataPackPanel'
    ],
    tabPosition: 'bottom',
    initComponent: function () {
        var me = this;

        me.lbar = me.createLeftBar.call(me);
        Ext.apply(me,
            {
                items: [],
                activeItem: 0
            }
        );

        me.callParent(arguments);
    },
    createLeftBar: function () {
        var me = this;
        var retVal = [];
        retVal.push(
            {
                xtype: 'buttongroup',
                itemId: 'projectOperation',
                hidden: true,
                title: '操作',
                columns: 1,
                items: [
                    {
                        iconCls: 'icon-startproject',
                        tooltip: '启动项目',
                        itemId: 'startUpProject',
                        scope: me,
                        handler: me._startUpProject
                    },
                    {
                        iconCls: 'icon-collabDev-submit',
                        tooltip: '完成项目',
                        itemId: 'completeProject',
                        scope: me,
                        handler: me.submitProject
                    },
                    {
                        iconCls: 'icon-collabDev-compare-32',
                        tooltip: '归档项目',
                        itemId: 'fileProject',
                        scope: me,
                        handler: me.fileProject
                    },
                    {
                        iconCls: 'icon-collabDev-worktime-32',
                        tooltip: '工作量填写',
                        itemId: 'worktime',
                        scope: me,
                        handler: me.fileProject
                    }
                ]
            },
            {
                xtype: 'buttongroup',
                itemId: 'planOperation',
                hidden: true,
                title: '操作',
                columns: 1,
                items: [
                    {
                        iconCls: 'icon-collabDev-submit',
                        tooltip: '提交工作包',
                        itemId: 'submitPlan',
                        scope: me,
                        handler: me.submitPlan
                    },
                    {
                        iconCls: 'icon-startproject',
                        tooltip: '强制启动工作包',
                        itemId: 'startUpPlan',
                        scope: me,
                        handler: me.startUpPlan
                    }
                ]
            },
            {
                xtype: 'buttongroup',
                itemId: 'taskOperation',
                hidden: true,
                title: '操作',
                columns: 1,
                items: [
                    {
                        iconCls: 'icon-collabDev-submit',
                        tooltip: '提交数据包',
                        itemId: 'submitPackage',
                        scope: me,
                        handler: me._submitPackage
                    },
                    {
                        iconCls: 'icon-collabDev-keep',
                        tooltip: '无需更改',
                        itemId: 'keep',
                        scope: me,
                        handler: me._updateNeedless
                    }, {
                        iconCls: 'icon-collabDev-callback',
                        tooltip: '数据回滚',
                        itemId: 'callbackSubmit',
                        scope: me,
                        handler: me._rollbackPackage
                    },
                    {
                        iconCls: 'icon-collabDev-feedback',
                        tooltip: '向上游反馈',
                        itemId: 'feedback',
                        scope: me,
                        handler: me._feedback
                    },
                    {
                        cls: 'icon-collabDev-compare-32 ',
                        tooltip: '版本对比',
                        itemId: 'versionCompare',
                        scope: me,
                        handler: me.versionCompare
                    },
                    {
                        cls: 'icon-collabDev-forceUpdate',
                        tooltip: '强制修改',
                        itemId: 'forceUpdate',
                        scope: me,
                        handler: me._forceModify
                    }
                ]
            },
            {
                xtype: 'buttongroup',
                itemId: 'superButtonGroup',
                hidden: true,
                title: '高级',
                columns: 1,
                items: [
                    {
                        iconCls: 'icon-collabDev-idesign',
                        tooltip: 'IDesigner',
                        itemId: 'IDesigner',
                        scope: this,
                        handler: me._IDesigner
                    }, {
                        iconCls: 'icon-collabDev-designerCenter',
                        tooltip: 'DesignerCenter',
                        itemId: 'DesignerCenter',
                        scope: this,
                        handler: me._DesignerCenter
                    },
                    {
                        iconCls: 'icon-collabDev-calculateFlow',
                        tooltip: '计算流程',
                        itemId: 'calculateFlow',
                        scope: this,
                        handler: me._calculateFlow
                    }
                ]
            }
        );
        return retVal;
    },
    //提交数据包
    _submitPackage: function () {
        var me = this;
        //tip on data not save
        var devGrids = Ext.ComponentQuery.query('devDataSettingGrid', me);
        for(var i=0; i<devGrids.length; i++) {
            var devGrid = devGrids[i];
            var treeStore = devGrid.getStore();
            var modifyRecords = treeStore.getModifiedRecords();
            if(modifyRecords.length != 0) {
                OrientExtUtil.Common.info("提示", "请保存数据后提交！");
                return;
            }
        }

        //validateComponent
        var boardPanel = me.up('processingResultSettingsBoard');
        if (boardPanel) {
            var componentTab = boardPanel.down('componentDataPanel');
            if (componentTab) {
                componentTab.validateComponents();
            }
        }

        var nodeId = me.nodeId;
        var submitDataPackPanel = Ext.create('OrientTdm.CollabDev.Processing.AncestryAnalyze.SubmitDataPackPanel', {
            nodeId: nodeId,
            successCallback: Ext.Function.bind(me.refresh, me)
        });
        var win = Ext.create('Ext.window.Window', {
            title: '选择数据包依赖',
            height: 600,
            width: 900,
            layout: 'fit',
            maximizable: true,
            modal: true,
            items: submitDataPackPanel
        });
        win.show();

    },
    //无需更改
    _updateNeedless: function () {
        var me = this;
        var nodeId = me.nodeId;
        Ext.Ajax.request({
            url: serviceName + '/ancestryAnalyze/needlessStateAnalyze.rdm',
            params: {
                nodeId: nodeId
            },
            success: function (response) {
                //OrientExtUtil.Common.tip("消息", "提交成功");
                me.refresh();
            }
        });
    },
    //撤回
    _rollbackPackage: function () {
        var me = this;
        var nodeId = me.nodeId;
        var rollbackDataPackPanel = Ext.create('OrientTdm.CollabDev.Processing.AncestryAnalyze.RollbackDataPackPanel', {
            nodeId: nodeId,
            successCallback: Ext.Function.bind(me.refresh, me)
        });
        var win = Ext.create('Ext.window.Window', {
            title: '选择撤回版本及数据包依赖',
            height: 700,
            width: 900,
            layout: 'fit',
            maximizable: true,
            modal: true,
            items: [rollbackDataPackPanel]
        });
        win.show();
    },
    //向上游反馈
    _feedback: function () {
        var me = this;
        Ext.Ajax.request({
            url: serviceName + '/ancestryAnalyze/getUpNodes.rdm',
            params: {
                nodeId: me.nodeId
            },
            success: function (response) {
                var result = JSON.parse(response.responseText);
                var upNodes = result.results;
                if(!upNodes || upNodes.length==0) {
                    OrientExtUtil.Common.info("提示", "该数据包不存在上游数据包！");
                }
                else {
                    me._showRelationWin(me.nodeId, upNodes, me._saveFeedback);
                }
            }
        });
    },
    _showRelationWin: function (nodeId, upNodes, callback) {
        var me = this;

        var data = [];
        for (var i = 0; i < upNodes.length; i++) {
            var upNode = upNodes[i];
            data.push({
                name: upNode.name,
                value: upNode.id + "-" + upNode.version
            });
        }

        var formPanel = Ext.create('Ext.form.Panel', {
            bodyPadding: 5,
            defaults: {
                anchor: '100%'
            },
            defaultType: 'textfield',
            items: [{
                xtype: 'multicombobox',
                fieldLabel: "上游数据包",
                name: "upNodes",
                allowBlank: false,
                store: Ext.create('Ext.data.Store', {
                    fields: ['name', 'value'],
                    data: data
                }),
                queryMode: 'local',
                displayField: 'name',
                valueField: 'value'
            },
                {
                    xtype: 'textarea',
                    height: 180,
                    name: 'content',
                    fieldLabel: '消息内容',
                    anchor: '100%'
                }],
            buttons: [{
                text: '确定',
                handler: function () {
                    var form = formPanel.getForm();
                    if (form.isValid()) {
                        debugger;
                        var upNodes = form.getValues().upNodes.split(",");
                        var upNodeMap = {};
                        for (var i = 0; i < upNodes.length; i++) {
                            var kv = upNodes[i].split("-");
                            upNodeMap[kv[0]] = new Number(kv[1]);
                        }
                        callback.call(me, upNodeMap, form.getValues().content);
                        win.close();
                    }
                }
            }, {
                text: '取消',
                handler: function () {
                    win.close();
                }
            }]
        });

        var win = Ext.create('Ext.window.Window', {
            title: '选择上游数据包',
            height: 300,
            width: 400,
            layout: 'fit',
            items: formPanel
        }).show();
    },
    _saveFeedback: function (upNodeMap, content) {
        var me = this;
        Ext.Ajax.request({
            url: serviceName + '/ancestryAnalyze/feedback.rdm',
            params: {
                nodeId: me.nodeId,
                version: me.nodeVersion,
                toNodeMap: JSON.stringify(upNodeMap),
                content: content
            },
            success: function (response) {

            }
        });
    },
    refresh: function () {
        var me = this;
        //刷新自身数据面板中的treeGrid,combo以及左侧导航树
        var combo = me.down('processingDevDataMainTabPanel').down('selfDevDataPanel').down('#taskVersionSwitchCombo');
        //重新加载combo的数据
        combo.store.load({
            callback: function () {
                //选中combo的第一条
                var version = combo.store.getAt(0).get('version');
                combo.setValue(version);
                //刷新selfDevDataPanel的nodeVersion
                var panel = me.down('processingDevDataMainTabPanel').down('selfDevDataPanel');
                panel.nodeVersion = version;
                //刷新自身研发数据中的grid
                var gird = me.down('processingDevDataMainTabPanel').down('selfDevDataPanel').down('devDataSettingGrid');
                gird.reloadData(version);
                gird.nodeVersion = version;
                me.nodeVersion = version;
                //刷新导航树树节点
                var treePanel = Ext.ComponentQuery.query('processingDevPrjTreePanel')[0];
                var selectedNode = OrientExtUtil.TreeHelper.getSelectNodes(treePanel)[0];
                treePanel.getStore().load({
                    node: selectedNode.parentNode
                });
            }
        });
    },
    restructureTabs: function (nodeId, nodeVersion, type, bmDataId) {
        var me = this;
        me.nodeId = nodeId;
        me.nodeVersion = nodeVersion;
        me.type = type;
        me.bmDataId = bmDataId;
        //先移除所有的内部面板
        me.removeAll();

        var params = {
            nodeId: nodeId
        };
        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/resultSettings/getTabs.rdm', params, true, function (resp) {
            var result = Ext.decode(resp.responseText).results;
            //在设计节点，用户可能没有绑定研发数据，组件数据或离线数据中的任何一个，所以当点击进入这个面板就为空，需要默认绑定研发数据
            if (result.hasDevData) {
                var devDataPanel = Ext.create('OrientTdm.CollabDev.Processing.ResultSettings.DevData.DevDataMainTabPanel', {
                    itemId: 'devDataPanel-' + nodeId,
                    nodeId: nodeId,
                    nodeVersion: nodeVersion,
                    bmDataId: bmDataId,
                    type: type
                });
                me.add(devDataPanel);
            }

            if (result.hasModelData) {
                var modelDataPanel = Ext.create('OrientTdm.CollabDev.Designing.ResultSettings.ComponentData.ComponentDataPanel', {
                    itemId: 'modelDataPanel-' + nodeId,
                    nodeId: nodeId
                });
                me.add(modelDataPanel);
            }
            if (result.hasPVMData) {
                var pvmDataPanel = Ext.create('OrientTdm.CollabDev.Designing.ResultSettings.PVMData.ResultSettingsPVMDataPanel', {
                    itemId: 'pvmDataPanel-' + nodeId,
                    nodeId: nodeId
                });
                me.add(pvmDataPanel);
            }
            me.setActiveTab(0);
        });

        //根据节点的类型，动态显示对应的按钮
        me.resetLeftBar(me.type);
    },
    resetLeftBar: function (type) {
        var me = this;
        //重置左侧按钮
        me.down('#projectOperation').enable();
        me.down('#planOperation').enable();
        me.down('#taskOperation').enable();
        me.down('#superButtonGroup').enable();
        switch (type) {
            case TDM_SERVER_CONFIG.NODE_TYPE_TASK:
                me.down('#projectOperation').hide();
                me.down('#planOperation').hide();
                me.down('#taskOperation').show();
                me.down('#superButtonGroup').show();
                break;
            case TDM_SERVER_CONFIG.NODE_TYPE_PLAN:
                me.down('#projectOperation').hide();
                me.down('#planOperation').show();
                me.down('#taskOperation').hide();
                me.down('#superButtonGroup').hide();
                break;
            case TDM_SERVER_CONFIG.NODE_TYPE_PRJ:
                me.down('#projectOperation').show();
                me.down('#planOperation').hide();
                me.down('#taskOperation').hide();
                me.down('#superButtonGroup').hide();
                break;
            default:
                break;
        }
    },
    _startUpProject: function () {
        var me = this;
        var params = {
            projectNodeId: me.nodeId
        };

        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/projectDrive/start.rdm', params, false, function (response) {
            /* var retV = response.decodedData;
             var success = retV.success;*/
        });
    },
    submitPlan: function () {
        var me = this;
        Ext.Ajax.request({
            url: serviceName + '/planDrive/submit.rdm',
            params: {
                planNodeId: me.nodeId
            },
            success: function (response) {
            }
        });
    },
    submitProject: function () {
        var me = this;
        Ext.Ajax.request({
            url: serviceName + '/projectDrive/submit.rdm',
            params: {
                projectNodeId: me.nodeId
            },
            success: function (response) {
            }
        });
    },
    startUpPlan: function () {
        var me = this;
        Ext.Ajax.request({
            url: serviceName + '/planDrive/startup.rdm',
            params: {
                planNodeId: me.nodeId
            },
            success: function (response) {
            }
        });
    }
});