/**
 * Created by User on 2019/1/28.
 */

Ext.define('OrientTdm.TaskPrepareMgr.TBomTemplateMgr.TaskPrepareFlowTemp.HangduanMgrGrid', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.hangduanMgrGrid',
    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        templateName: TDM_SERVER_CONFIG.TPL_HANGDUAN,
        modelName: TDM_SERVER_CONFIG.HANGDUAN
    },

    initComponent: function () {
        var me = this;
        var hangciTimeData=[];
        var params = {
            hangciId: me.flowId
        };
        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/taskPrepareController/getHangciTime.rdm', params, false, function (resp) {
            hangciTimeData = resp.decodedData.results
        });
        var modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);
        var templateId = OrientExtUtil.ModelHelper.getTemplateId(modelId, me.templateName);
        var customerFilter = new CustomerFilter('T_HANGCI_' + me.schemaId + '_ID', CustomerFilter.prototype.SqlOperation.Equal, '', me.flowId);
        var modelGrid = Ext.create('OrientTdm.Common.Extend.Grid.OrientModelGrid', {
            region: 'center',
            modelId: modelId,
            isView: 0,
            templateId: templateId,
            customerFilter: [customerFilter],
            createUrl: serviceName + "/taskPrepareController/saveHangduanData.rdm?flowId=" + me.flowId,
            updateUrl: serviceName + "/taskPrepareController/updateHangduanData.rdm",
            afterInitComponent: function () {
                var me = this;
                var toolbar = me.dockedItems[0];
                // toolbar.add({
                //     iconCls: 'icon-delete',
                //     text: '级联删除',
                //     handler:function(){
                //         if(!OrientExtUtil.GridHelper.hasSelected(modelGrid)){
                //             return;
                //         }
                //         var selectRecords=OrientExtUtil.GridHelper.getSelectedRecord(modelGrid);
                //         var ids=[];
                //         Ext.each(selectRecords,function(s){
                //             ids.push(s.data.id);
                //         });
                //         OrientExtUtil.AjaxHelper.doRequest(serviceName+'/taskPrepareController/deleteHangduanData.rdm',{
                //             id:ids.toString()
                //         },false,function(resp){
                //             var ret=Ext.decode(resp.responseText);
                //             if(ret.success){
                //                 modelGrid.fireEvent('refreshGrid');
                //                 Ext.Msg.alert("提示","删除成功！")
                //                 Ext.getCmp("tBomTemplateTreeOwner1").doRefresh();
                //                 modelGrid.on("customRefreshGrid", function (useQueryFilter) {
                //                     if (Ext.getCmp("tBomTreePanel")) {
                //                         Ext.getCmp("tBomTreePanel").fireEvent("refreshCurrentNode", useQueryFilter);
                //                     }
                //                 });
                //             }
                //         })
                //     },
                //     scope: me
                // });
                var addButton = toolbar.child('[text=新增]');
                if (addButton) {
                    Ext.Function.interceptAfter(addButton, 'handler', function (button) {
                        //设置航段时间在航次范围内进行选择
                        var customPanel = button.orientBtnInstance.customPanel;
                        if (customPanel) {
                            var planStartTimeField = customPanel.down('[name=C_PLAN_START_TIME_' + modelId + ']');
                            var planEndTimeField = customPanel.down('[name=C_PLAN_END_TIME_' + modelId + ']');
                            if (hangciTimeData.length = 2) {
                                planStartTimeField.setMinValue(hangciTimeData[0]);
                                planStartTimeField.setMaxValue(hangciTimeData[1]);
                                planEndTimeField.setMinValue(hangciTimeData[0]);
                                planEndTimeField.setMaxValue(hangciTimeData[1]);
                            }
                        }
                    });
                }
                var modifyButton = toolbar.child('[text=修改]');
                if (modifyButton) {
                    Ext.Function.interceptAfter(modifyButton, 'handler', function (button) {
                        //设置航段时间在航次范围内进行选择
                        var customPanel = button.orientBtnInstance.customPanel;
                        if (customPanel) {
                            var planStartTimeField = customPanel.down('[name=C_PLAN_START_TIME_' + modelId + ']');
                            var planEndTimeField = customPanel.down('[name=C_PLAN_END_TIME_' + modelId + ']');
                            if (hangciTimeData.length = 2) {
                                planStartTimeField.setMinValue(hangciTimeData[0]);
                                planStartTimeField.setMaxValue(hangciTimeData[1]);
                                planEndTimeField.setMinValue(hangciTimeData[0]);
                                planEndTimeField.setMaxValue(hangciTimeData[1]);
                            }
                        }
                    });
                }
            }
        });
        //Ext.getCmp("tBomTemplateTreeOwner1").doRefresh();
        //刷新Grid时 同时刷新右侧树
        modelGrid.on("customRefreshGrid", function (useQueryFilter) {
            var westPanel=me.ownerCt.ownerCt.ownerCt.westPanel;
            if (westPanel) {
                westPanel.fireEvent("refreshCurrentNode", useQueryFilter);
            }
        });
        Ext.apply(me, {
            layout: 'border',
            items: [modelGrid],
            modelGrid: modelGrid
        });
        me.callParent(arguments);
    }
});