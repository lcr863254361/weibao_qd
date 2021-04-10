/**
 * Created by Seraph on 16/7/29.
 */
Ext.define('OrientTdm.Collab.common.collabFlow.collabFlowPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.collabFlowPanel',
    requires: [
        "OrientTdm.FlowCommon.flowDiagram.flowDiagram",
        "OrientTdm.FlowCommon.flowDiagram.dataObtainer.simpleFlowDataObtainer",
        "OrientTdm.FlowCommon.flowDiagram.dataObtainer.collabFlowDataObtainer",
        'OrientTdm.Collab.common.collabFlow.HorizontalFlowStatusPanel',
        'OrientTdm.Collab.common.collabFlow.FlowOverViewPanel',
        "OrientTdm.Common.Util.HtmlTriggerHelper"
    ],
    config: {
        modelName: null,
        dataId: null,
        readOnly: false,
        flowDiagram: null,
        flowInfo: null,
        sid: null,
        localMode: false,
        localData: null,
        hisTaskDetail: null
    },
    initComponent: function () {
        var me = this;
        var sid = new Date().getTime();
        me.sid = sid;
        var overViewContainedId = 'overViewContainer_' + sid;
        me.overViewContainedId = overViewContainedId;
        var centerPanel = Ext.create("OrientTdm.Common.Extend.Panel.OrientPanel", {
            region: 'center',
            padding: '0 0 0 0',
            border: false,
            listeners: {
                afterrender: function (panel) {
                    me.doLoadFlowDiagram();
                }
            },
            html: '<div id="curflowContainer_' + sid + '" style="z-index:1;position:relative;overflow:hidden;top:0px;right:0px;width:100%;height:100%;border-style:none;border-color:lightgray;"></div>'
        });

        var westPanel = Ext.create("OrientTdm.Common.Extend.Panel.OrientPanel", {
            region: 'west',
            width: 38,
            border: false,
            listeners: {
                afterrender: function (panel) {

                }
            },
            html: '<div id="flowDiagCtrl_' + sid + '"  style="z-index:1;position:relative;overflow:hidden;top:0px;right:0px;width:100%;' +
            'height:100%;border-style:none;border-color:#e9e9e9;background-color:#e9e9e9;"></div>'

        });
        var southPanel = Ext.create("OrientTdm.Collab.common.collabFlow.HorizontalFlowStatusPanel", {
            region: 'south',
            height: 38,
            margin: '0 0 0 38',
            border: false

        });

        var toolbarItems = me.createToolbar.call(me);
        var toolBar = toolbarItems && toolbarItems.length > 0 ? Ext.create('Ext.toolbar.Toolbar', {
            items: toolbarItems
        }) : null;
        Ext.apply(this, {
            layout: 'border',
            items: [centerPanel, westPanel, southPanel],
            centerPanel: centerPanel,
            westPanel: westPanel,
            southPanel: southPanel,
            tbar: toolBar,
            listeners: {
                removed: function () {

                }
            }
        });

        this.callParent(arguments);
    },
    createToolbar: function () {
        var me = this;

        if (me.readOnly || null != me.hisTaskDetail || me.localMode) {
            return [];
        } else {
            return [
                {
                    iconCls: 'icon-flowDesigner',
                    text: '流程设计器',
                    scope: this,
                    itemId: 'startFlowDesigner',
                    handler: Ext.bind(me.doStartFlowDesigner, me)
                }, {
                    iconCls: 'icon-startCollabFlow',
                    text: '启动',
                    itemId: 'startPi',
                    scope: this,
                    handler: Ext.bind(me.doStart, me)
                }, {
                    iconCls: 'icon-suspendFlow',
                    text: '暂停',
                    itemId: 'suspendPi',
                    scope: this,
                    handler: Ext.bind(me.doSuspend, me)
                }, {
                    iconCls: 'icon-resumeFlow',
                    text: '恢复',
                    itemId: 'resumePi',
                    scope: this,
                    handler: Ext.bind(me.doResume, me)
                }, {
                    iconCls: 'icon-stopFlow',
                    text: '关闭',
                    itemId: 'closeFlow',
                    scope: this,
                    handler: Ext.bind(me.doClose, me)
                }, {
                    iconCls: 'icon-assign',
                    text: '设置人员',
                    itemId: 'setAssignee',
                    scope: this,
                    handler: Ext.bind(me.doSetAssignee, me)
                }
            ];
        }
    },
    doRefresh: function () {

    },
    doStart: function () {
        var me = this;
        var url = '/' + me.modelName.toLowerCase() + '/start.rdm';
        var params = {
            dataId: me.dataId
        };
        OrientExtUtil.AjaxHelper.doRequest(serviceName + url, params, false, function (response) {
            var retV = Ext.decode(response.responseText);
            if (retV.success) {
                me.doReloadFlowDiagram();
            }
        });
    },
    doSuspend: function () {
        var me = this;
        var url = '/' + me.modelName.toLowerCase() + '/suspend.rdm';

        var params = {
            dataId: me.dataId
        };
        OrientExtUtil.AjaxHelper.doRequest(serviceName + url, params, false, function (response) {
            var retV = Ext.decode(response.responseText);
            if (retV.success) {
                Ext.Msg.alert("提示", "暂停成功");
            } else {

            }

        });
    },
    doResume: function () {
        var me = this;
        var url = '/' + me.modelName.toLowerCase() + '/resume.rdm';

        var params = {
            dataId: me.dataId
        };
        OrientExtUtil.AjaxHelper.doRequest(serviceName + url, params, false, function (response) {
            var retV = Ext.decode(response.responseText);
            if (retV.success) {
                Ext.Msg.alert("提示", "恢复成功");
            } else {

            }
        });
    },
    doClose: function () {
        var me = this;
        var url = '/' + me.modelName.toLowerCase() + '/close.rdm';

        var params = {
            dataId: me.dataId
        };
        OrientExtUtil.AjaxHelper.doRequest(serviceName + url, params, false, function (response) {
            var retV = Ext.decode(response.responseText);
            if (retV.success) {
                Ext.Msg.alert("提示", "关闭成功");
                me.doReloadFlowDiagram();
            } else {

            }
        });
    },
    doReloadFlowDiagram: function () {
        var me = this;
        var ct = me.ownerCt;
        var config = {
            region: 'center',
            modelName: me.modelName,
            dataId: me.dataId
        };
        ct.remove(me);
        var newCollabFlowPanel = Ext.create("OrientTdm.Collab.common.collabFlow.collabFlowPanel", config);
        ct.add(newCollabFlowPanel);
        ct.doLayout();
    },
    doLoadFlowDiagram: function () {
        var me = this;

        if (me.localMode && me.localData.hasPd) {
            me.centerPanel.removeAll();
            me.westPanel.removeAll();

            me.flowDiagram = new OrientTdm.FlowCommon.flowDiagram.flowDiagram();

            me.flowDiagram.initByLocal(document.getElementById('curflowContainer_' + me.sid), me.localData.jpdl);
            me.flowDiagram.updateOverView(document.getElementById(me.overViewContainedId));
            me.flowDiagram.createGraphCtrlToolbar(document.getElementById('flowDiagCtrl_' + me.sid));

            return;
        }

        if (me.hisTaskDetail != null) {
            var flowMonitData = me.hisTaskDetail.getFlowMonitData();
            var jpdlDesc = flowMonitData.jpdlDesc;
            var flowTaskNodeModelList = flowMonitData.flowTaskNodeModelList;
            me.centerPanel.removeAll();
            me.westPanel.removeAll();
            me.flowDiagram = new OrientTdm.FlowCommon.flowDiagram.flowDiagram();
            me.flowDiagram.initByLocal(document.getElementById('curflowContainer_' + me.sid), jpdlDesc);
            me.flowDiagram.createGraphCtrlToolbar(document.getElementById('flowDiagCtrl_' + me.sid));
            me.flowDiagram.updateOverView(document.getElementById(me.overViewContainedId));
            me.flowDiagram.updateNodeStatusByLocal(flowTaskNodeModelList);
        } else {
            var params = {
                modelName: me.modelName,
                dataId: me.dataId
            };
            OrientExtUtil.AjaxHelper.doRequest(serviceName + '/collabFlow/flowInfo.rdm', params, false, function (response) {
                me.centerPanel.removeAll();
                me.westPanel.removeAll();
                me.flowInfo = Ext.decode(response.responseText);
                if ((Ext.isEmpty(me.flowInfo.piId)) && (Ext.isEmpty(me.flowInfo.pdId))) {
                    return;
                }
                me.flowDiagram = new OrientTdm.FlowCommon.flowDiagram.flowDiagram();
                var flowDataObtainer = new OrientTdm.FlowCommon.flowDiagram.dataObtainer.collabFlowDataObtainer();
                me.flowDiagram.init(document.getElementById('curflowContainer_' + me.sid), flowDataObtainer, me.flowInfo);
                me.flowDiagram.updateOverView(document.getElementById(me.overViewContainedId));
                if ((!Ext.isEmpty(me.flowInfo.piId)) || (!Ext.isEmpty(me.flowInfo.flowTaskId))) {
                    me.flowDiagram.updateNodeStatus(me.flowInfo);
                }
                me.flowDiagram.createGraphCtrlToolbar(document.getElementById('flowDiagCtrl_' + me.sid));
            });
        }

    },
    doSetAssignee: function () {
        var me = this;
        if (Ext.isEmpty(me.flowDiagram)) {
            Ext.Msg.alert('提示', '流程未启动或未定义');
            return;
        }

        var selectedTaskInfo = me.flowDiagram.getCurSelNodeAttr();
        if (Ext.isEmpty(selectedTaskInfo)) {
            Ext.Msg.alert('提示', '未选择待设置节点');
            return;
        }

        /*        var assignUserPanel = Ext.create('OrientTdm.Collab.common.collabFlow.AssignUserPanel', {
         taskInfo: selectedTaskInfo,
         piId: me.flowInfo.piId
         });*/

        var win = Ext.create('Ext.Window', {
            plain: true,
            height: 600,
            width: 800,
            layout: 'fit',
            maximizable: false,
            title: '设置执行人',
            modal: true
        });

        var userSelectorPanel = Ext.create('OrientTdm.Common.Extend.Form.Selector.ChooseUserPanel', {
            multiSelect: true,
            selectedValue: selectedTaskInfo.assignee,
            saveAction: function (saveData, callback) {
                Ext.getBody().mask("请稍后...", "x-mask-loading");
                var showValues = Ext.Array.pluck(saveData, 'name').join(',');
                var realValues = Ext.Array.pluck(saveData, 'userName').join(',');
                var selectedIds = Ext.Array.pluck(saveData, 'id');


                if (selectedTaskInfo.nodeType === 'custom') {
                    var params = {
                        piId: me.flowInfo.piId,
                        parModelName: me.modelName,
                        parDataId: me.dataId,
                        taskName: selectedTaskInfo.name,
                        assignee: realValues,
                        assigneeIds: selectedIds.join(',')
                    };

                    OrientExtUtil.AjaxHelper.doRequest(serviceName + '/collabCounterSign/assignee/set.rdm', params, false, function (response) {
                        var retV = response.decodedData;
                        var success = retV.success;
                        me._setAssigneeSuccessCb(success, retV.msg, selectedTaskInfo, selectedIds, callback);
                    });
                } else {
                    var params = {
                        taskName: selectedTaskInfo.name,
                        piId: me.flowInfo.piId,
                        assignee: realValues
                    };

                    OrientExtUtil.AjaxHelper.doRequest(serviceName + '/flow/task/assignee/set.rdm', params, false, function (response) {
                        var retV = response.decodedData;
                        var success = retV.success;
                        me._setAssigneeSuccessCb(success, retV.msg, selectedTaskInfo, selectedIds, callback);
                    });
                }

            }
        });

        win.add(userSelectorPanel);
        win.show();
    },
    doStartFlowDesigner: function () {
        var me = this;
        var params = {
            modelName: me.modelName,
            dataId: me.dataId,
            WebServices: contextPath
        };
        HtmlTriggerHelper.startUpTool("workFlow", "null", params, "=");
    },
    _setAssigneeSuccessCb: function (success, msg, selectedTaskInfo, selectedIds, callback) {
        var me = this;
        if (success) {
            if (callback) {
                callback.call(this);
            }
            me.doReloadFlowDiagram();//重新加载流程图
            //将选中的人员加入到对应任务的执行人中
            OrientExtUtil.AjaxHelper.doRequest(serviceName + '/collabTeam/saveAssignedUsersByRolename.rdm', {
                modelName: me.modelName,
                dataId: me.dataId,
                taskName: selectedTaskInfo.name,
                selectedIds: selectedIds,
                roleName: '执行人'
            }, false, function (response) {
                var retV = response.decodedData;
                var success = retV.success;
                if (!success) {
                    Ext.Msg.alert('提示', retV.msg);
                }
            });
        } else {
            Ext.Msg.alert('提示', msg);
        }
    }
});
