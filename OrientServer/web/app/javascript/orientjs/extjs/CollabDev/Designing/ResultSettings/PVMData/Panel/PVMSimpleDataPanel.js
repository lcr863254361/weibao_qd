/**
 * 简单检查，绑定ds建模的表
 */
Ext.define('OrientTdm.CollabDev.Designing.ResultSettings.PVMData.Panel.PVMSimpleDataPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.pvmSimpleDataPanel',
    requires: [],
    layout: 'fit',
    config: {
        taskCheckModelId: '',
        status: '',
        inited: false
    },
    initComponent: function () {
        var me = this;
        //获取数据
        var params = {
            taskCheckModelId: me.taskCheckModelId,
            status: me.status
        };
        var modelId = null;
        var customerFilter = null;
        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/CheckModel/getBusinessModelDesc.rdm', params, false, function (resp) {
            var respData = resp.decodedData.results;
            modelId = respData.modelId;
            customerFilter = me._createCustomIdFilter(respData.dataIds);
        });

        var modelGrid = Ext.create('OrientTdm.Common.Extend.Grid.OrientModelGrid', {
            //bindFile:true,
            modelId: modelId,
            isView: 0,
            customerFilter: [customerFilter],
            deleteUrl: serviceName + '/CheckModel/customDeleteModelData.rdm',
            showAnalysisBtns: false,
            afterInitComponent: function () {
                me._customGrid(this);
            }
        });
        modelGrid.on('customRefreshGrid', function () {
            modelGrid.fireEvent('refreshGridByCustomerFilter');
            if (me.up('resultSettingsPVMDataPanel').westPanelComponent) {
                me.up('resultSettingsPVMDataPanel').westPanelComponent.fireEvent('reconfigPVMData');
            }
        });

        Ext.apply(me, {
            items: [modelGrid]
        });
        me.callParent(arguments);
    },
    initEvents: function () {
        var me = this;
        me.mon(me, 'activate', me.reconfig, me);
        me.callParent();
    },
    _createCustomIdFilter: function (dataIds) {
        var customerFilter = new CustomerFilter('ID', CustomerFilter.prototype.SqlOperation.In, "", dataIds.join(','));
        return customerFilter;
    },
    reconfig: function () {
        var me = this;
        if (me.inited == false || null != me.hisTaskDetail) {
            me.inited = true;
        } else {
            var params = {
                taskCheckModelId: me.taskCheckModelId,
                status: me.status
            };
            OrientExtUtil.AjaxHelper.doRequest(serviceName + '/CheckModel/getBusinessModelDesc.rdm', params, false, function (resp) {
                var respData = resp.decodedData.results;
                var dataIds = respData.dataIds;
                var customerFilter = me._createCustomIdFilter(dataIds);
                var modelGrid = me.down('orientModelGrid');
                modelGrid.setCustomerFilter([customerFilter]);
                modelGrid.fireEvent('refreshGridByCustomerFilter', [customerFilter]);
            });
        }
    },
    _customGrid: function (gridPanel) {
        var me = this;

        var keepDictionart = ['详细', '附件', '导出'];
        //增加按钮
        var toolBar = gridPanel.dockedItems[0] || gridPanel.dockedItems.get(0);
        //只保留详细、查询、查询全部三个按钮
        var toRemoveItems = [];

        if (me.localMode) {
            keepDictionart = ['详细', '附件'];
            Ext.each(toolBar.items.items, function (btn) {
                if (!Ext.Array.contains(keepDictionart, btn.text)) {
                    toRemoveItems.push(btn);
                }
            });
        } else {
            if (me.status != '1') {
                Ext.each(toolBar.items.items, function (btn) {
                    if (!Ext.Array.contains(keepDictionart, btn.text)) {
                        toRemoveItems.push(btn);
                    }
                });
            }
            else {
                var operatDictionart = Ext.Array.clone(keepDictionart);
                operatDictionart.push('新增', '修改', '删除');
                Ext.each(toolBar.items.items, function (btn) {
                    if (!Ext.Array.contains(operatDictionart, btn.text)) {
                        toRemoveItems.push(btn);
                    }
                });
            }
        }

        Ext.each(toRemoveItems, function (toRemoveItem) {
            toolBar.remove(toRemoveItem);
        });
        //定制新增按钮 新增后增加绑定关系
        var addButton = gridPanel.dockedItems[0].down('button[text=新增]');
        if (addButton) {
            Ext.Function.interceptAfter(addButton, 'handler', function (button) {
                //新增表单出现后 相关定制
                var customPanel = button.orientBtnInstance.customPanel;
                if (customPanel) {
                    //修改保存后操作
                    customPanel.successCallback = Ext.Function.createInterceptor(customPanel.successCallback, me._bindRelation, me);
                }
            });
        }
    },
    _bindRelation: function (resp) {
        var me = this;
        var createDataIds = resp.results;
        var flag = false;
        var params = {
            taskCheckModelId: me.taskCheckModelId,
            toBindDataIds: createDataIds,
            status: me.status
        };
        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/CheckModel/createRelationDatas.rdm', params, false, function (resp) {
            var respData = resp.decodedData;
            var success = respData.success;
            if (success == true) {
                flag = true;
            } else {
                //给出提示信息 并删除记录
            }
        });
        return flag;
    }
});