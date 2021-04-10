/**
 * Created by panduanduan on 10/04/2017.
 */
Ext.define('OrientTdm.DestroyRepairTemplateMgr.DestroyTypePanel.DestroyTypePanel', {
    alias: 'widget.destroyTypePanel',
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    requires: [
        'OrientTdm.DestroyRepairTemplateMgr.DestroyTypePanel.DestroyTypeGridPanelList'
    ],
    config: {
        dirId: ''
    },
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
            layout: 'border',
            items: [
                {
                    region: 'center',
                    xtype: 'destroyTypeGridPanelList',
                    // dirId: me.dirId,
                    listeners: {
                        afterLoadData: function (records) {
                            var prjIds = [];
                            Ext.each(records, function (record) {
                                prjIds.push(record.get('id'));
                            });
                            // me.initStatisticPanel(prjIds);
                        }
                    }
                },
                // {
                //     region: 'south',
                //     height: 350,
                //     title: '统计视图',
                //     autoScroll: true,
                //     itemId: 'statisticPanel'
                // }
            ]
        });
        me.callParent(arguments);
    },
    initEvents: function () {
        var me = this;
        me.callParent();
    },
    initStatisticPanel: function (prjIds) {
        var me = this;
        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/StatisticSetUp/list.rdm', {
            name: '项目状态统计'
        }, true, function (resp) {
            var results = resp.decodedData.results;
            if (Ext.isEmpty(results)) {
                OrientExtUtil.Common.err(OrientExtLocal.prompt.error, '请联系管理员进行项目统计配置工作');
            } else {
                var statisticId = results[0].id;
                OrientExtUtil.StatisticUtil.constructChart(statisticId, {height: 300}, {
                    prjIds: prjIds
                }, function (statisticCharts) {
                    me.down('#statisticPanel').add(statisticCharts);
                });
            }
        });
    }
});