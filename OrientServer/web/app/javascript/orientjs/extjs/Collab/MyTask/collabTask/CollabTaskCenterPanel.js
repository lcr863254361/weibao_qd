/**
 * Created by Seraph on 16/8/20.
 */
Ext.define('OrientTdm.Collab.MyTask.collabTask.CollabTaskCenterPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientTabPanel',
    requires: [
        "OrientTdm.Collab.common.util.RightControlledPanelHelper"
    ],
    config: {
        rootModelName: null,
        rootDataId: null,
        rootData: null,
        rootModelId: null,
        isHistory: false,
        //历史任务描述
        hisTaskDetail: null,
        //保存历史任务时 是否需要序列化至数据库
        isHistoryAble: true
    },
    initComponent: function () {
        var me = this;
        var params = me._initParam();
        var items = [];
        if (null != me.hisTaskDetail) {
            OrientExtUtil.AjaxHelper.doRequest(serviceName + "/gantt/getModelIdByName.rdm", {
                modelNames: [TDM_SERVER_CONFIG.TASK]
            }, false, function (response) {
                //计划模型信息
                var modelDataInfo = me.hisTaskDetail.getModelDataInfo(response.decodedData.results[TDM_SERVER_CONFIG.TASK]);
                //默认取第一个
                if (modelDataInfo.length > 0) {
                    var planInfo = modelDataInfo[0];
                    var modelDesc = Ext.decode(planInfo.modelDesc);
                    var modelData = planInfo.modelDataList[0];
                }
                //准备模型信息
                me.rootModelName = TDM_SERVER_CONFIG.TASK;
                me.rootModelId = modelDesc.modelId;
                me.rootDataId = modelData['ID'];
                params = me._initParam();
                //组件信息
                var hisComponent = me._initComponentFromHistory();
                if (hisComponent) {
                    items.push(hisComponent);
                }
                //其他功能
                var commonPanels = me._initCommonPanelsFromHistory();
                items = Ext.Array.merge(items, commonPanels);
                //流程监控信息
                var belongedFlowDiagram = Ext.create("OrientTdm.Collab.common.collabFlow.collabFlowPanel", {
                    title: '所属流程',
                    iconCls: 'icon-flow',
                    readOnly: true,
                    hisTaskDetail: me.hisTaskDetail
                });
                items.push(belongedFlowDiagram);
            });
        } else {
            OrientExtUtil.AjaxHelper.doRequest(serviceName + '/modelData/getGridModelDescAndData.rdm', params, false, function (response) {
                var modelDesc = response.decodedData.results.orientModelDesc;
                var modelData = response.decodedData.results.modelData;
                ////组件信息
                OrientExtUtil.AjaxHelper.doRequest(serviceName + '/ComponentBind/list.rdm', params, false, function (response) {
                    if (response.decodedData) {
                        var results = response.decodedData.results;
                        //取第一个
                        if (results.length > 0) {
                            me.componentBind = results[0];
                            var panel = Ext.create('OrientTdm.Common.Extend.Panel.OrientPanel', {
                                layout: 'border',
                                title: '任务处理',
                                modelName: me.rootModelName,
                                dataId: me.rootDataId,
                                modelId: me.rootModelId,
                                iconCls: 'icon-taskprocess'
                            });
                            var componentItem = me._createComponent();
                            panel.add(componentItem);
                            items.push(panel);
                        }
                    }
                });

                //其他信息
                var functions = ['离线数据', '设计数据'];
                OrientExtUtil.AjaxHelper.doRequest(serviceName + '/collabTeam/user/current/functions.rdm', params, false, function (response) {
                    var respJson = response.decodedData.results;
                    me.functionDescs = respJson;
                    for (var i = 0; i < respJson.length; i++) {
                        if (Ext.Array.contains(functions, respJson[i].name)) {
                            var panel;
                            if (items.length <= 0) {//fix bug: the first tab is empty
                                panel = CollabRightControlledPanelHelper.getPanelByTitle(respJson[i].name, {
                                        iconCls: respJson[i].iconCls,
                                        layout: 'border',
                                        title: respJson[i].name,
                                        modelName: me.rootModelName,
                                        dataId: me.rootDataId,
                                        modelId: me.rootModelId
                                    }, {
                                        '甘特图': {
                                            readOnly: true,
                                            enableControl: false
                                        },
                                        '设计数据': {
                                            initPrivateData: true
                                        },
                                        '数据流': {
                                            readOnly: true,
                                            hisTaskDetail: null,
                                            isHistory: null
                                        },
                                        '离线数据': {
                                            hisTaskDetail: null,
                                            isHistory: null
                                        },
                                        '工作组': {
                                            hisTaskDetail: null,
                                            isHistory: null
                                        }
                                    }
                                );
                            } else {
                                panel = Ext.create('OrientTdm.Common.Extend.Panel.OrientPanel', {
                                    iconCls: respJson[i].iconCls,
                                    layout: 'border',
                                    title: respJson[i].name,
                                    modelName: me.rootModelName,
                                    dataId: me.rootDataId,
                                    modelId: me.rootModelId
                                });
                            }

                            items.push(panel);
                        }
                    }
                });
                OrientExtUtil.AjaxHelper.doRequest(serviceName + '/projectTree/nodeInfo/parent.rdm', params, false, function (response) {
                    var respJson = response.decodedData;
                    var belongedFlowDiagram = Ext.create("OrientTdm.Collab.common.collabFlow.collabFlowPanel", {
                        iconCls: "icon-flow",
                        title: '所属流程',
                        readOnly: true,
                        modelName: respJson.modelName,
                        dataId: respJson.dataId
                    });
                    items.push(belongedFlowDiagram);
                });

            });
        }
        Ext.apply(me,
            {
                items: items,
                activeItem: 0,
                listeners: {
                    afterLayout: function () {
                        me.fireEvent('tabchange', me, this.items.get(0));
                    }
                }
            }
        );

        me.callParent(arguments);
    },
    initEvents: function () {
        var me = this;
        me.callParent();
        me.on('tabchange', me.tabChanged, me);
    },
    tabChanged: function (tabPanel, newCard, oldCard) {
        var me = this;
        if (!newCard.hasInited) {
            newCard.hasInited = true;
            if (newCard.title == '任务处理' && !newCard.down('baseComponent')) {
                var componentItem = me._createComponent();
                newCard.add(componentItem);
                return;
            }
            var panel = CollabRightControlledPanelHelper.getPanelByTitle(newCard.title, {
                    region: 'center',
                    modelName: newCard.modelName,
                    dataId: newCard.dataId,
                    modelId: newCard.modelId
                }, {
                    '甘特图': {
                        readOnly: true,
                        enableControl: false
                    },
                    '设计数据': {
                        initPrivateData: true
                    },
                    '数据流': {
                        readOnly: true,
                        hisTaskDetail: newCard.hisTaskDetail,
                        isHistory: newCard.isHistory
                    },
                    '离线数据': {
                        hisTaskDetail: newCard.hisTaskDetail,
                        isHistory: newCard.isHistory
                    },
                    '工作组': {
                        hisTaskDetail: newCard.hisTaskDetail,
                        isHistory: newCard.isHistory
                    }
                }
            );

            if (!Ext.isEmpty(panel)) {
                newCard.removeAll();
                newCard.add(panel);
                newCard.doLayout();
            }
        }
    },
    _createComponent: function () {
        var me = this;
        var retVal = null;
        var componentBind = me.componentBind;
        var componentDesc = componentBind.belongComponent;
        if (componentDesc) {
            me.componentDesc = componentDesc;
            Ext.require(componentBind.componentExtJsClass);
            retVal = Ext.create(componentBind.componentExtJsClass, {
                //title: componentDesc.componentname,
                flowTaskId: me.rootData.flowTaskId,
                modelId: me.rootModelId,
                dataId: me.rootDataId,
                region: 'center',
                componentId: componentDesc.id
            });
        }
        return retVal;
    },
    _initParam: function () {
        var me = this;
        var params = {
            modelName: me.rootModelName,
            modelId: me.rootModelId,
            dataId: me.rootDataId
        };
        return params;
    },
    /**
     *
     * 为后台历史任务引擎，提供输入参数，历史引擎根据参数保存相关历史信息至数据库
     */
    getHistoryData: function () {
        var me = this;
        //保存基本信息
        var retVal = {
            modelDataRequestList: [],
            sysDataRequests: [],
            extraData: {
                functionDescs: Ext.encode(me.functionDescs),
                componentBind: Ext.encode(me.componentBind)
            }
        };
        //保存基本信息
        retVal.modelDataRequestList.push({
            modelId: me.rootModelId,
            dataIds: [me.rootDataId]
        });
        //组件信息
        if (me.componentDesc) {
            retVal.sysDataRequests.push({
                sysTableName: 'CWM_COMPONENT',
                sysTableDataList: [me.componentDesc]
            });
        }
        return retVal;
    },
    _initComponentFromHistory: function () {
        var me = this;
        var componentSysData = me.hisTaskDetail.getSysData('CWM_COMPONENT');
        var retVal = null;
        if (componentSysData) {
            var componentBind = Ext.decode(me.hisTaskDetail.getExtraData('componentBind'));
            var componentInfo = componentSysData.oriSysDataList[0];
            Ext.require(componentBind.componentExtJsClass);
            retVal = Ext.create(componentBind.componentExtJsClass, {
                title: componentInfo.componentname,
                region: 'center',
                iconCls: 'icon-taskprocess',
                dataId: me.rootDataId,
                modelId: me.rootModelId,
                flowTaskId: me.hisTaskDetail.taskId,
                hisTaskDetail: me.hisTaskDetail
            });
        }
        return retVal;
    },
    _initCommonPanelsFromHistory: function () {
        var me = this;
        var exceptTabNames = ['工作包分解', '控制流', '工作组', '数据流', '任务处理'];
        var retVal = [];
        var functionDescs = Ext.decode(me.hisTaskDetail.getExtraData('functionDescs'));
        if (functionDescs && Ext.isArray(functionDescs)) {
            Ext.each(functionDescs, function (functionDesc) {
                if (!Ext.Array.contains(exceptTabNames, functionDesc.name)) {
                    var panel = Ext.create('OrientTdm.Common.Extend.Panel.OrientPanel', {
                        layout: 'border',
                        title: functionDesc.name,
                        iconCls: functionDesc.iconCls,
                        modelName: me.rootModelName,
                        dataId: me.rootDataId,
                        modelId: me.rootModelId,
                        hisTaskDetail: me.hisTaskDetail,
                        isHistory: true
                    });
                    retVal.push(panel);
                }
            });
        }
        return retVal;
    }
});