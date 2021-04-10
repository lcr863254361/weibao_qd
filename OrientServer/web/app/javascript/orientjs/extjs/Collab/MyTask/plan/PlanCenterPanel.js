/**
 * Created by Seraph on 16/7/26.
 */
Ext.define('OrientTdm.Collab.MyTask.plan.PlanCenterPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientTabPanel',
    requires: [
        "OrientTdm.Collab.common.util.RightControlledPanelHelper"
    ],
    config: {
        rootModelName: null,
        rootDataId: null,
        rootModelId: null,
        rootData: null,
        isHistoryAble: true,
        //历史任务描述
        hisTaskDetail: null
    },
    initComponent: function () {
        var me = this;
        var items = [];
        var params = me._initParam();
        var exceptTabNames = ['工作包分解', '工作组'];
        var hisPanelNames = ['数据流', '控制流'];
        if (null != me.hisTaskDetail) {
            OrientExtUtil.AjaxHelper.doRequest(serviceName + "/gantt/getModelIdByName.rdm", {
                modelNames: [TDM_SERVER_CONFIG.PLAN]
            }, false, function (response) {
                //计划模型信息
                var modelDataInfo = me.hisTaskDetail.getModelDataInfo(response.decodedData.results[TDM_SERVER_CONFIG.PLAN]);
                //默认取第一个
                if (modelDataInfo.length > 0) {
                    var planInfo = modelDataInfo[0];
                    var modelDesc = Ext.decode(planInfo.modelDesc);
                    var modelData = planInfo.modelDataList[0];
                    var inforForm = Ext.create('OrientTdm.Common.Extend.Form.OrientDetailModelForm', {
                        title: modelDesc.text + '基本信息',
                        bindModelName: modelDesc.dbName,
                        iconCls: 'icon-basicInfo',
                        modelDesc: modelDesc,
                        originalData: modelData
                    });
                    items.push(inforForm);
                }
                //准备模型信息
                me.rootModelName = TDM_SERVER_CONFIG.PLAN;
                me.rootModelId = modelDesc.modelId;
                me.rootDataId = modelData['ID'];
                params = me._initParam();
            });
        } else {
            OrientExtUtil.AjaxHelper.doRequest(serviceName + '/modelData/getGridModelDescAndData.rdm', params, false, function (response) {
                var modelDesc = response.decodedData.results.orientModelDesc;
                var modelData = response.decodedData.results.modelData;
                var inforForm = Ext.create('OrientTdm.Common.Extend.Form.OrientDetailModelForm', {
                    title: modelDesc.text + '基本信息',
                    bindModelName: modelDesc.dbName,
                    iconCls: 'icon-basicInfo',
                    modelDesc: modelDesc,
                    originalData: modelData
                });
                items.push(inforForm);
            });
        }
        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/collabTeam/user/current/functions.rdm', params, false, function (response) {
            var respJson = response.decodedData.results;
            for (var i = 0; i < respJson.length; i++) {
                var config = {
                    layout: 'border',
                    title: respJson[i].name,
                    modelName: me.rootModelName,
                    dataId: me.rootDataId,
                    modelId: me.rootModelId,
                    iconCls: respJson[i].iconCls
                };
                if (Ext.Array.contains(exceptTabNames, respJson[i].name)) {
                    continue;
                } else if (Ext.Array.contains(hisPanelNames, respJson[i].name) && null != me.hisTaskDetail) {
                    Ext.apply(config, {
                        hisTaskDetail: me.hisTaskDetail,
                        isHistory: true
                    });
                }
                var panel = Ext.create('OrientTdm.Common.Extend.Panel.OrientPanel', config);
                items.push(panel);
            }
        });
        Ext.apply(me,
            {
                items: items,
                activeItem: 0
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
        if (!newCard.hasInited) {
            newCard.hasInited = true;
            var panel = CollabRightControlledPanelHelper.getPanelByTitle(newCard.title, {
                region: 'center',
                modelName: newCard.modelName,
                dataId: newCard.dataId,
                modelId: newCard.modelId
            }, {
                '甘特图': {
                    readOnly: false,
                    enableControl: true
                },
                '设计数据': {
                    initPrivateData: true
                },
                '数据流': {
                    readOnly: false,
                    hisTaskDetail: newCard.hisTaskDetail,
                    isHistory: newCard.isHistory
                },
                '控制流': {
                    hisTaskDetail: newCard.hisTaskDetail,
                    isHistory: newCard.isHistory
                }
            });
            if (!Ext.isEmpty(panel)) {
                newCard.removeAll();
                newCard.add(panel);
                newCard.doLayout();
            }
        }
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
            modelDataRequestList: []
        };
        var baseInfoPanel = me.down('orientDetailModelForm');
        if (baseInfoPanel) {
            retVal.modelDataRequestList.push({
                modelId: baseInfoPanel.modelDesc.modelId,
                dataList: [baseInfoPanel.originalData]
            });
        }
        return retVal;
    }
});