/**
 * Created by enjoy on 2016/4/6 0006.
 */
Ext.define('OrientTdm.Collab.ProjectMng.mainFrame.ProjectList', {
    extend: 'OrientTdm.Common.Extend.Grid.OrientGrid',
    alias: 'widget.projectList',
    requires: [
        'OrientTdm.Collab.ProjectMng.mainFrame.model.ProjectGridModel'
    ],
    config: {
        dirId: ''
    },
    beforeInitComponent: Ext.emptyFn,
    afterInitComponent: Ext.emptyFn,
    usePage: false,
    initComponent: function () {
        var me = this;
        me.callParent(arguments);
    },
    //视图初始化
    createToolBarItems: function () {
        return null;
    },
    createColumns: function () {
        return [
            {
                header: '项目名称',
                width: 150,
                sortable: true,
                dataIndex: 'name',
                filter: {
                    type: 'string'
                }
            },
            {
                header: '项目状态',
                width: 100,
                sortable: true,
                dataIndex: 'status',
                filter: {
                    type: 'string'
                }
            },
            {
                header: '项目负责人',
                width: 100,
                sortable: true,
                dataIndex: 'principal',
                filter: {
                    type: 'string'
                }
            }, {
                header: '计划开始时间',
                flex: 1,
                sortable: true,
                dataIndex: 'plannedStartDate',
                filter: {
                    type: 'string'
                }
            }, {
                header: '计划结束时间',
                flex: 1,
                sortable: true,
                dataIndex: 'plannedEndDate',
                filter: {
                    type: 'string'
                }
            }, {
                header: '实际开始时间',
                flex: 1,
                sortable: true,
                dataIndex: 'actualStartDate',
                filter: {
                    type: 'string'
                }
            }, {
                header: '实际结束时间',
                flex: 1,
                sortable: true,
                dataIndex: 'actualEndDate',
                filter: {
                    type: 'string'
                }
            }
        ];
    },
    createStore: function () {
        var me = this;
        var retVal = Ext.create('Ext.data.Store', {
            model: 'OrientTdm.Collab.ProjectMng.mainFrame.model.ProjectGridModel',
            autoLoad: true,
            proxy: {
                type: 'ajax',
                api: {
                    'read': serviceName + '/projectTree/listPrjAsGrid.rdm'
                },
                reader: {
                    type: 'json',
                    successProperty: 'success',
                    totalProperty: 'totalProperty',
                    root: 'results',
                    messageProperty: 'msg'
                },
                extraParams: {
                    dirId: me.dirId
                }
            }
        });
        this.store = retVal;
        return retVal;
    }
});