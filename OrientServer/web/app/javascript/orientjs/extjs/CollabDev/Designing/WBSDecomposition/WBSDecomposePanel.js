/**
 * WBS分解tab页，上面是甘特图，下面是任务包分解的grid
 */
Ext.define('OrientTdm.CollabDev.Designing.WBSDecomposition.WBSDecomposePanel', {
    extend: 'OrientTdm.CollabDev.Common.BaseVersionPanel',
    alias: 'widget.wbsDecomposePanel',
    config: {},
    requires: [
        'OrientTdm.CollabDev.Designing.WBSDecomposition.DesignPlanGanttPanel',
        'OrientTdm.CollabDev.Designing.WBSDecomposition.DesignTaskPanel'
    ],
    initComponent: function () {
        var _this = this;

        Ext.apply(_this, {
            iconCls: 'icon-collabDev-WBSSetting',
            title: '1.WBS分解',
            layout: {
                type: 'vbox',
                align: 'stretch'
            },
            items: [
                {
                    xtype: 'container',
                    title: '计划分解',
                    flex: 1
                }, {
                    xtype: 'container',
                    title: '数据包分解',
                    flex: 1,
                    padding: '10 0 0 0'
                }
            ]
        });

        _this.callParent(arguments);

    },
    initEvents: function () {
        var _this = this;
        _this.mon(_this, 'refreshByNewNode', _this.refreshByNewNode, _this);
        _this.callParent();
    },
    refreshByNewNode: function () {
        var _this = this;
        if (!_this.getInited()) {
            _this.setInited(true);
            _this.removeAll();
            _this.add([
                {
                    xtype: 'designPlanGanttPanel',
                    projectId: _this.bmDataId,
                    projectNodeId: _this.nodeId,
                    projectNodeVersion: _this.nodeVersion,
                    projectStatus: _this.status,
                    flex: 1
                }, {
                    xtype: 'designTaskPanel',
                    itemId: 'designTaskPanel',
                    flex: 1
                }
            ]);
        } else {
            //刷新gantt区域
            var ganttPanel = _this.down('designPlanGanttPanel');
            ganttPanel.projectId = _this.bmDataId;
            ganttPanel.projectNodeId = _this.nodeId;
            ganttPanel.projectStatus = _this.status;
            var taskStore = ganttPanel.taskStore;
            taskStore.getProxy().setExtraParam('parentNodeId', _this.nodeId);
            taskStore.getProxy().setExtraParam('projectId', _this.bmDataId);
            taskStore.load();
            var dependencyStore = ganttPanel.dependencyStore;
            dependencyStore.getProxy().setExtraParam('projectNodeId', _this.nodeId);
            dependencyStore.getProxy().setExtraParam('projectId', _this.bmDataId);
            dependencyStore.load();
            ganttPanel.fireEvent('cascade');
            //刷新数据包分解区域,如果原来数据包分解区域有数据，树节点切换后应该显示为空
            var designTaskPanel = _this.down('designTaskPanel');
            designTaskPanel.refreshDataByPlanNodeId('-1');
            designTaskPanel.disableButtons();
        }
    }
});