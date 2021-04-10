Ext.define('OrientTdm.CollabDev.Processing.ViewBoard.ViewBoardMainCardPanel', {
    extend: 'OrientTdm.CollabDev.Common.BaseVersionPanel',
    alias: 'widget.viewBoardMainCardPanel',
    config: {
        currentNode: null
    },
    initComponent: function () {
        var _this = this;

        Ext.apply(_this, {
            iconCls: 'icon-collabDev-dashbord',
            layout: 'card',
            items: []
        });

        _this.callParent(arguments);

    },
    initEvents: function () {
        var _this = this;
        _this.mon(_this, 'refreshByNewNode', _this.refreshByNewNode, _this);
        _this.callParent();
    },
    switchPage: function (index) {   //根据下标切换页面
        var _this = this;
        var layout = _this.getLayout();
        layout.setActiveItem(index);
    },
    navigation: function (direction) {  //根据方向切换页面
        var _this = this;
        var layout = _this.getLayout();
        layout[direction]();
    },
    refreshByNewNode: function () {
        var _this = this;
        switch (_this.type) {  //根据节点类型，card切换到对应的看板
            case TDM_SERVER_CONFIG.NODE_TYPE_PRJ:
                _this.moveToProjectViewBoard();
                _this.setTitle('项目看板');
                break;
            case TDM_SERVER_CONFIG.NODE_TYPE_PLAN:
                _this.moveToPlanViewBoard();
                _this.setTitle('工作包看板');
                break;
            case TDM_SERVER_CONFIG.NODE_TYPE_TASK:
                _this.moveToTaskViewBoard();
                _this.setTitle('工作项看板');
                break;
            default:
                break;
        }
    },
    moveToProjectViewBoard: function () {
        var _this = this;
        var config = {
            nodeId: _this.nodeId,
            nodeVersion: _this.nodeVersion,
            bmDataId: _this.bmDataId
        };
        if (_this.items.length == 0) {
            _this.add(Ext.create('OrientTdm.CollabDev.Processing.ViewBoard.Project.ProjectViewDashboard', config));
            _this.switchPage(0);
        } else {
            var containProjectViewDashboard = false;
            var index;
            var itemPanel = null;
            for (var i = 0; i < _this.items.length; i++) {
                var item = _this.items.get(i);
                if (item.xtype == 'projectViewDashboard') {
                    index = i;
                    itemPanel = item;
                    containProjectViewDashboard = true;
                    break;
                }
            }
            if (containProjectViewDashboard) {
                _this.switchPage(index);
                itemPanel.nodeId = _this.nodeId;
                itemPanel.nodeVersion = _this.nodeVersion;
                itemPanel.bmDataId = _this.bmDataId;
                itemPanel.refresh();
            } else {
                _this.add(Ext.create('OrientTdm.CollabDev.Processing.ViewBoard.Project.ProjectViewDashboard', config));
                _this.navigation('next');
            }
        }
    },
    moveToPlanViewBoard: function () {
        var _this = this;
        var config = {
            nodeId: _this.nodeId,
            nodeVersion: _this.nodeVersion,
            bmDataId: _this.bmDataId
        };
        if (_this.items.length == 0) {
            _this.add(Ext.create('OrientTdm.CollabDev.Processing.ViewBoard.Plan.PlanViewDashboard', config));
            _this.switchPage(0);
        } else {
            var containPlanViewDashboard = false;
            var itemPanel = null;
            var index;
            for (var i = 0; i < _this.items.length; i++) {
                var item = _this.items.get(i);
                if (item.xtype == 'planViewDashboard') {
                    index = i;
                    itemPanel = item;
                    containPlanViewDashboard = true;
                    break;
                }
            }
            if (containPlanViewDashboard) {
                _this.switchPage(index);
                itemPanel.nodeId = _this.nodeId;
                itemPanel.nodeVersion = _this.nodeVersion;
                itemPanel.bmDataId = _this.bmDataId;
                itemPanel.refresh();
            } else {
                _this.add(Ext.create('OrientTdm.CollabDev.Processing.ViewBoard.Plan.PlanViewDashboard', config));
                _this.navigation('next');
            }
        }
    },
    moveToTaskViewBoard: function () {
        var _this = this;
        var config = {
            nodeId: _this.nodeId,
            nodeVersion: _this.nodeVersion,
            bmDataId: _this.bmDataId
        };
        if (_this.items.length == 0) {
            _this.add(Ext.create('OrientTdm.CollabDev.Processing.ViewBoard.Task.TaskViewDashboard', config));
            _this.switchPage(0);
        } else {
            var containTaskViewDashboard = false;
            var itemPanel = null;
            var index;
            for (var i = 0; i < _this.items.length; i++) {
                var item = _this.items.get(i);
                if (item.xtype == 'taskViewDashboard') {
                    index = i;
                    itemPanel = item;
                    containTaskViewDashboard = true;
                    break;
                }
            }
            if (containTaskViewDashboard) {
                _this.switchPage(index);
                itemPanel.nodeId = _this.nodeId;
                itemPanel.nodeVersion = _this.nodeVersion;
                itemPanel.bmDataId = _this.bmDataId;
                itemPanel.refresh();
            } else {
                _this.add(Ext.create('OrientTdm.CollabDev.Processing.ViewBoard.Task.TaskViewDashboard', config));
                _this.navigation('next');
            }
        }
    }
});