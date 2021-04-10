/**
 * 项目看板
 * @modifiedby ZhangSheng 2018-09-04 19:42
 */
Ext.define('OrientTdm.CollabDev.Processing.ViewBoard.Project.ProjectViewDashboard', {
    extend: 'OrientTdm.CollabDev.Common.BaseVersionPanel',
    alias: 'widget.projectViewDashboard',
    config: {
        bmDataId: null,
        nodeId: null
    },
    initComponent: function () {
        var me = this;

        var baseInfoForm = Ext.create('OrientTdm.CollabDev.Processing.Common.NodeBaseInfoPanel', {
            modelName: TDM_SERVER_CONFIG.PROJECT,
            templateName: TDM_SERVER_CONFIG.TPL_VIEWBOARD_PROJECT,
            dataId: me.bmDataId,
            height: 200
        });

        var ganttPanel = Ext.create('OrientTdm.CollabDev.Processing.ViewBoard.Project.ProjectProgressGanttPanel', {
                title: '项目进度',
                projectId: me.bmDataId,
                projectNodeId: me.nodeId,
                flex: 1,
                tools: [
                    {
                        type: 'down',
                        handler: function (event, toolEl, panelHeader) {
                            me.showAllGanttPanel();
                        }
                    },
                    {
                        type: 'up',
                        handler: function (event, toolEl, panelHeader) {
                            me.back();
                        }
                    }
                ]
            })
        ;

        var bottomPanel = Ext.create('OrientTdm.Common.Extend.Panel.OrientPanel', {
            title: '统计信息',
            layout: 'column',
            itemId: 'statisticPanel',
            items: [{
                columnWidth: .50,
                itemId: 'collabTaskStatisticPanel'
            }, {
                columnWidth: .50,
                itemId: 'commitDataPerDayStatisticPanel'
            }],
            flex: 1,
            tools: [
                {
                    type: 'up',
                    handler: function (event, toolEl, panelHeader) {
                        me.showAllStaticticPanel();
                    }
                },
                {
                    type: 'down',
                    handler: function (event, toolEl, panelHeader) {
                        me.back();
                    }
                }
            ]
        });

        Ext.apply(me, {
            autoScroll: true,
            layout: {
                type: 'vbox',
                align: 'stretch'
            },
            items: [baseInfoForm, ganttPanel, bottomPanel]
        });

        //刷新统计信息
        me.initStatisticPanel(me.nodeId);

        me.callParent(arguments);
    },
    /*onRender: function () { //
        var _this = this;
        _this.callParent();
        _this.mon(_this, {
            afterrender: function (view) {
                view.el.on('mousewheel', function (e) {  //鼠标滚动事件监听
                    var detlta = (e.browserEvent.wheelDelta && (e.browserEvent.wheelDelta > 0 ? 1 : -1)) || (e.browserEvent.detail && (e.browserEvent.detail > 0 ? -1 : 1))
                    if (detlta > 0) {
                        //鼠标向上滚
                    } else if (detlta < 0) {
                        //鼠标向下滚
                        view.el.scroll('down', 500);
                    }
                })
            }
        })
    },*/
    refresh: function () {
        var _this = this;
        //刷新gantt区域
        var ganttPanel = _this.down('projectProgressGanttPanel');
        ganttPanel.projectId = _this.bmDataId;
        ganttPanel.projectNodeId = _this.nodeId;
        var taskStore = ganttPanel.taskStore;
        taskStore.getProxy().setExtraParam('parentNodeId', _this.nodeId);
        taskStore.getProxy().setExtraParam('projectId', _this.bmDataId);
        taskStore.load();
        var dependencyStore = ganttPanel.dependencyStore;
        dependencyStore.getProxy().setExtraParam('projectNodeId', _this.nodeId);
        dependencyStore.getProxy().setExtraParam('projectId', _this.bmDataId);
        dependencyStore.getProxy().setExtraParam('projectNodeVersion', _this.nodeVersion);
        dependencyStore.load();
        ganttPanel.fireEvent('cascade');
        //刷新基础信息面板
        var nodeBaseInfoPanel = _this.down('nodeBaseInfoPanel');
        nodeBaseInfoPanel.dataId = _this.bmDataId;
        nodeBaseInfoPanel.restructurePanel();

        //刷新统计信息
        _this.initStatisticPanel(_this.nodeId);
    },
    //刷新统计信息面板--ZhangSheng 2018.9.4
    initStatisticPanel: function (prjId) {
        var _this = this;
        //刷新数据包完成量统计饼图
        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/StatisticSetUp/list.rdm', {
            name: '数据包完成量统计'
        }, true, function (resp) {
            var results = resp.decodedData.results;
            if (Ext.isEmpty(results)) {
                OrientExtUtil.Common.err(OrientExtLocal.prompt.error, '请联系管理员进行数据包完成量统计配置工作');
            } else {
                var coolabTaskStatisticId = results[0].id;
                OrientExtUtil.StatisticUtil.constructChart(coolabTaskStatisticId, {height: 300}, {
                    prjId: prjId
                }, function (collabTaskStatisticPie) {
                    _this.down('#statisticPanel').down('#collabTaskStatisticPanel').removeAll();
                    _this.down('#statisticPanel').down('#collabTaskStatisticPanel').add(collabTaskStatisticPie);
                });
            }
        });

        //刷新每日数据提交量折线堆叠图
        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/StatisticSetUp/list.rdm', {
            name: '每日数据提交量'
        }, true, function (resp) {
            var results = resp.decodedData.results;
            if (Ext.isEmpty(results)) {
                OrientExtUtil.Common.err(OrientExtLocal.prompt.error, '请联系管理员进行每日数据提交量配置工作');
            } else {
                var commitDataPerDayStatisticId = results[0].id;
                OrientExtUtil.StatisticUtil.constructChart(commitDataPerDayStatisticId, {height: 300}, {
                    prjId: prjId
                }, function (commitDataPerDayStatisticChart) {
                    _this.down('#statisticPanel').down('#commitDataPerDayStatisticPanel').removeAll();
                    _this.down('#statisticPanel').down('#commitDataPerDayStatisticPanel').add(commitDataPerDayStatisticChart);
                });
            }
        });
    },
    showAllGanttPanel: function () {
        var me = this;
        var ganttPanel = me.down('projectProgressGanttPanel');
        var bottomPanel = me.down('#statisticPanel');
        ganttPanel.flex = 2;
        bottomPanel.flex = -1;
        me.doLayout();
    },
    back: function () {
        var me = this;
        var ganttPanel = me.down('projectProgressGanttPanel');
        var bottomPanel = me.down('#statisticPanel');
        ganttPanel.flex = 1;
        bottomPanel.flex = 1;
        me.doLayout();
    },
    showAllStaticticPanel: function () {
        var me = this;
        var ganttPanel = me.down('projectProgressGanttPanel');
        var bottomPanel = me.down('#statisticPanel');
        ganttPanel.flex = -1;
        bottomPanel.flex = 2;
        me.doLayout();
    }
});