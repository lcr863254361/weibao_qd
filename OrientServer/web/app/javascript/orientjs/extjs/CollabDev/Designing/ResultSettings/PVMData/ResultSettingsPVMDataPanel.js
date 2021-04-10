/**
 * 离线检查面板
 */
Ext.define('OrientTdm.CollabDev.Designing.ResultSettings.PVMData.ResultSettingsPVMDataPanel', {
    extend: 'OrientTdm.CollabDev.Common.BaseVersionPanel',
    alias: 'widget.resultSettingsPVMDataPanel',
    config: {
        nodeId: null
    },
    initComponent: function () {
        var me = this;

        var checkModelTree = Ext.create('OrientTdm.CollabDev.Designing.ResultSettings.PVMData.Panel.PVMNavigationTree', {
            itemId: 'checkModelTree',
            region: 'west',
            title: '检查模型',
            animCollapse: true,
            width: 280,
            minWidth: 150,
            maxWidth: 400,
            split: true,
            collapsible: true,
            nodeId: me.nodeId
        });

        var checkModelData = Ext.create('OrientTdm.Common.Extend.Panel.OrientTabPanel', {
            itemId: 'checkModelData',
            title: '离线数据',
            region: 'center',
            items: [
                {
                    title: '简介',
                    iconCls: 'icon-basicInfo',
                    //html: '<h1>检查表管理...此处可也添加HTML，介绍功能点主要用途</h1>'
                    html: '<iframe width="100%" height="100%" marginwidth="0" framespacing="0" marginheight="0" frameborder="0" src = "' + serviceName +
                    '/app/views/introduction/OfflineData.jsp?"></iframe>'
                }
            ]
        });
        Ext.apply(me, {
            title: '离线检查数据',
            icon: null,
            layout: 'border',
            items: [checkModelTree, checkModelData]
        });
        me.callParent(arguments);
    },
    initEvents: function () {
        var me = this;
        me.callParent(arguments);
    },
    saveStatus: function (newStatus, taskCheckModelId, sourcePanel) {
        var me = this;
        var params = {
            taskCheckModelId: taskCheckModelId,
            toSaveStatus: newStatus
        };
        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/CheckModel/updateRelationDatas.rdm', params, false, function (resp) {
            var respData = resp.decodedData;
            var success = respData.success;
            if (success == true) {
                var treePanel = me.westPanelComponent;
                me.centerPanelComponent.remove(sourcePanel, true);
                //刷新左侧的树
                if (treePanel) {
                    treePanel.fireEvent('refreshTreeAndSelOne', taskCheckModelId, true);
                }
            } else {
                //给出提示信息 并删除记录
            }
        });
    }
});